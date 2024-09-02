#!/usr/bin/env python2.3
'''
WirelessAutomation
Copyright (c) 2012 Apple. All rights reserved.
'''
from time import sleep
from copy import deepcopy
from common.WirelessLogging import Sleep
from common.Scripter import scripter
from common.WirelessException import TestFailError, BTPowerError, WiFiPowerError, WiFiScanError, WiFiAssociationError, OSLeaksError
from plistlib import readPlistFromString
from LogInit import LogIt
from TrafficGen.Basic import Basic
from DUT.DUT_Utils import DUT_Utils
from WiFi.AWDL import AWDL
from common.WirelessCommon import BTDevice
from WiFi.Firmware_Control import Firmware_Control
from WiFi.UI_Control import UI_Control
from WiFi.Apple80211 import Apple80211
from WiFi.wl import wl
from WiFi.WiFiManager import WiFiManager
import re

class Control(object):
    '''
    Class for controlling and querying wifi related methods
    '''

    def __init__(self, dut):
        self.logger = LogIt()
        self.dut = dut
        self.traffic = Basic(self.dut)
        self.utils = DUT_Utils(self.dut)
        self.awdl = AWDL(self.dut)
        self.apple80211 = Apple80211(self.dut.os)
        self.wl = wl(self.dut.os)
        self.maxSupportedChannel = 0
        self.busInterface = None
        self.firmwareControl = Firmware_Control(self.dut)
        self.uiControl = UI_Control(self.dut)
        self.apIsOn5Ghz = None
        try:
            self.Has5Ghz = self.dut.getWiFi().IsDeviceMultiBand()
        except:
            self.Has5Ghz = self.firmwareControl.Has5Ghz()
        self.MAX_PH_CLIENTS = False
        self.noUI = False or self.dut.isAppleTV()
        self.currentLeaks = {}
        self.leakStackLoggingEnabled = {}
        self.wifiManager = WiFiManager(self.dut.os)
        self._scanTimes = []

    def Configure(self, noUI=False, forgetAll=True, autoLockToNever=True, enableWifidLeaks=False, enableConfigdLeaks=False,
                  enableBluetooth=False, airplaneMode=False, enablePowerLogs=False):
        self.logger.Info("Configuring dut %s (mac %s)", self.dut.name, self.dut.wifiaddr)
        self.noUI = noUI
        self.vendorId = self.GetVendorId()
        rebootNeeded = False

        if not self.dut.getWiFi().GetPower():
            self.dut.getWiFi().On()
        # And forget all networks.
        if forgetAll:
            self.dut.getWiFi().ForgetAll()

        if self.AddProcessToLeaksMonitor('configd', enableStackLogging=enableConfigdLeaks):
            rebootNeeded = True

        self.AddProcessToLeaksMonitor('wifid', enableStackLogging=enableWifidLeaks)
        # self.AddProcessToLeaksMonitor('corecaptured', enableStackLogging=True, waitForDaemonToLaunch=False)

        if rebootNeeded:
            self.logger.Info("Rebooting to work around 11763989")
            self.dut.getOS().Reboot()
            self.dut.getWiFi().Reattach()
            for process in self.currentLeaks:
                self.CheckLeaks(process, logFailOnLeaks=True)  # Reset the counters, but log failure on leaks on boot now that rdars are fixed (11A426+ -- 13645995)
            # Set things up again in case the reboot / firmware reload changed something
            self.dut.getWiFi().Reattach()
            self.awdl = AWDL(self.dut)
            self.firmwareControl = Firmware_Control(self.dut)

        if autoLockToNever and not self.noUI:
            self.dut.getOS().SetAutoLockTime(-1)

        if not self.noUI:
            self.Unlock(goToSpringBoard=True)

        if enableBluetooth:
            self.EnableBT()
        else:
            self.DisableBT()

        if airplaneMode:
            self.dut.TurnOnAirplaneMode()
        else:
            self.dut.TurnOffAirplaneMode()

        self.WiFiOn()
        self.GetWLANBusInterface()

    def EnableDiagnosticCollection(self):
        cmd = '/usr/local/bin/profilectl setbool allowDiagnosticSubmission 1'
        result = self.dut.getOS().Execute(cmd, True, True)

    def DisableDiagnosticCollection(self):
        cmd = '/usr/local/bin/profilectl setbool allowDiagnosticSubmission 0'
        result = self.dut.getOS().Execute(cmd, True, True)

    def NoWiFi(self):
        '''
        check for wifi interface availability
        '''
        self.WiFiManagerDevice()
        # Check UI for NO-WiFi
        if not self.noUI:
            cmd = "scripter -i Settings.js -c 'settings = UIAApp.application(\"com.apple.Preferences\"); var WiFiTableCellValue = settings.getWiFiTableCell().value(); if(WiFiTableCellValue == \"No Wi-Fi\") {throw \"No Wi-Fi Showed up in UI\";} else {throw WiFiTableCellValue}'"
            result = self.dut.getOS().Execute(cmd, True, True)
            if "No Wi-Fi" in result.standardOutput:
                raise TestFailError, TestFailError("Test failed, UI shows No Wi-Fi")
            else:
                self.logger.Pass("UI does not show No-WiFi")

    def GetIsHSICDevice(self):
        '''
        Keep for now, delete when WAF implements
        <rdar> description
        '''
        return self.GetWLANBusInterface() == 'HSIC'

    def GetWLANBusInterface(self):
        if self.busInterface == None:
            busType = self.dut.getOS().Execute('/usr/sbin/kextstat | grep AppleBCMWLANBusInterface*', True, True)
        else:
            return self.busInterface
        if 'SDIO' in busType.standardOutput:
            self.busInterface = 'SDIO'
        elif 'HSIC' in busType.standardOutput:
            self.busInterface = 'HSIC'
        else:
            return False
        return self.busInterface

    def CheckWiFiPowerOff(self, iteration=1):
        '''
        Check the WiFi driver also returns off after turn WiFi power off
        '''
        testName = "Check if WiFi is off"
        try:
            resultDict = self.dut.getOS().Execute("apple80211 -state", runAsRoot=True)
            if resultDict is not None:
                associateState = str(resultDict.standardOutput)
                powerOffState = str(resultDict.standardError)
                errorMessage = "apple80211 -state: %s %s" % (associateState, powerOffState)

                if powerOffState.find("Device power is off") >= 0:
                    self.logger.Pass("apple80211 -state returns device power off")
                else:
                    if iteration == 1:
                        self.logger.Info("apple80211 -state returns device not associated or is scanning, wait for 5 seconds and check again")
                        Sleep(5, self.logger)
                        self.CheckWiFiPowerOff(2)
                    else:
                        self.StopTestOnFailure(testName, errorMessage)
        except WiFiPowerError:
            self.stopTestOnFailure(testName, errorMessage)

    def CheckPowerOn(self):
        '''
        Check the WiFi driver also returns off after turn WiFi power on
        '''
        testName = "Check if WiFi is on"
        try:
            resultDict = self.dut.getOS().Execute("apple80211 -state", runAsRoot=True)
            if resultDict is not None:
                associateState = str(resultDict.standardOutput)
                powerOffState = str(resultDict.standardError)
                errorMessage = "apple80211 -state: %s %s" % (associateState, powerOffState)

                if powerOffState.find("Device power is off") >= 0:
                    self.logger.Fail("apple80211 -state returns device power off")
                    self.StopTestOnFailure(testName, errorMessage)

                self.logger.Pass("apple80211 -state returns the power is not off")

        except WiFiPowerError:
            self.stopTestOnFailure(testName, errorMessage)

    def GetEAPState(self):
        '''
        Returns EAP Type is it exists, returns None if it doesn't. Return value is a dict of all the EAP values.
        Written by Quint Friesen <qfriesen@apple.com>
        '''
        eapType = self.dut.getOS().Execute('eapoltest state %s' % self.WiFiManagerDevice())
        if eapType.standardOutput == 'EAPOLControlCopyStateAndStatus(%s) returned 2' % self.WiFiManagerDevice():
            return False
        else:
            eapList = readPlistFromString(eapType.standardOutput[202:].replace('\n', '').replace('\t', ''))
            return eapList

    def VerifyEAPType(self, expectedEAPType):
        '''
        Check if the device EAP type matches the expected EAP type for the test
        Written by Dani Gleser <gleser@apple.com>
        '''
        try:
            eapType = self.GetEAPState()
        except:
            self.logger.Error("Could not get EAP type from Device check WiFi connection")
            return False
        if eapType['EAPType'] == expectedEAPType:
            self.logger.Info("EAP Types Matched for type: %s" % eapType['EAPType'])
            return True
        else:
            self.logger.Fail('Failed Match EAP Type - Expected: %s and Got: %s' % (expectedEAPType, eapType['EAPType']))
            return False

    def IsNetworkInAutoJoinList(self, ap):
        '''
        Check if the AP ssid is on Auto Join List
        '''
        cmd = '/usr/local/bin/mobilewifitool autojoin list'
        result = self.dut.getOS().Execute(cmd)
        output = str(result.standardOutput)
        if output.find(ap.ssid):
            self.logger.Info("Found %s in Auto Join list" % ap.ssid)
            return True
        else:
            self.logger.Fail("Failed to find %s in Auto Join list: %s" % (ap.ssid, output))
            return False

    def VerifyJoin(self, network, forgetAll=False):
        ''' Returns True if DUT successfully joins the network and is associated '''

        # Option to forget all networks
        if forgetAll:
            self.dut.wifi.ForgetAll()

        self.dut.wifi.Join(network)  # Returns scanned network, not network joined
        network = self.dut.wifi.GetNetwork()  # Returns network joined

        return self.VerifyNetwork(network)

    def VerifyNetwork(self, network):
        ''' Returns False if not connected to the given network '''
        if not self.VerifyConnection(network):
            return False

        # Check RF channel, bssid
        curNetwork = self.dut.getWiFi().GetNetwork()
        if (network.channel > 0) and (network.channel != curNetwork.channel):
            self.logger.Warning('VerifyNetwork failed, not connected to channel %d, got %d' % \
                                    (network.channel, curNetwork.channel))
            return False

        if (network.bssid) and (network.bssid != curNetwork.bssid):
            self.logger.Warning('VerifyNetwork failed, not connected to bssid %s, got %s' % \
                                    (network.bssid, curNetwork.bssid))
            return False

        return True

    def VerifyNotConnected(self, timeout=20):
        ''' Returns True if DUT is not associated to any network '''
        self.logger.Info('Verify DUT is not associated')

        # Verify not connected to network (poll up to timeout sec)
        curNetwork = None
        for i in range(timeout):
            sleep(1)
            curNetwork = self.dut.wifi.GetNetwork()
            if curNetwork:
                self.logger.Debug('VerifyNotConnected, DUT is still associated to %s' % curNetwork.ssid)
                continue

            # Check WIFI
            if self.dut.wifi.IsActive():
                self.logger.Debug('VerifyNotConnected, DUT WIFI is active')
                continue
            if self.dut.wifi.HasTcpipSettings():
                self.logger.Debug('VerifyNotConnected, DUT WIFI has TCPIP settings')
                continue

            # DUT is not connected, break
            return True

        # DUT is still connected after timeout
        self.logger.Warning('VerifyNotConnected failed, DUT is still associated to %s' % curNetwork)
        return False

    def VerifyConnection(self, ap, timeout=60, checkPrimaryInterface=True, logFail=True, check5Ghz=True, logFailIfNotOn5Ghz=False):
        '''
        Check if connected with correct AP, IP address
        '''
        # verify network is active
        self.logger.Info('Waiting for up to %s seconds for network to become active', timeout)
        if self.dut.getWiFi().WaitActive(timeout):
            self.logger.Info('Verified network is active.')
        else:
            if logFail:
                self.logger.Fail('Network is not active after timeout.')
            else:
                self.logger.Warning('Network is not active after timeout.')
            return False
        # verify correct network
        if self.dut.getWiFi().WaitForNetwork(networkExpected=True, timeout=timeout):
            if ap == self.dut.getWiFi().GetNetwork():
                self.logger.Pass('Verified network is %s.', ap)
            else:
                actualNetwork = self.dut.getWiFi().GetNetwork()
                self.logger.Info('Network need to connect is %s.', ap)
                self.logger.Fail('Actual network is %s.', actualNetwork)
                return False
        else:
            self.logger.Fail('We do not have a current network.')
            return False

        if not checkPrimaryInterface:
            return True

        if self.dut.getWiFi().WaitForTcpipSettings(timeout):
            self.logger.Info('Verified network has an IP address.')
        else:
            if logFail:
                self.logger.Fail('Unable to get an IP address for network.')
            else:
                self.logger.Warning('Unable to get an IP address for network.')
            return False

        if self.dut.getWiFi().WaitForPrimaryInterface(timeout=timeout):
            self.logger.Pass('WiFi is primary network interface')
        else:
            if logFail:
                self.logger.Fail('WiFi is not primary network interface')
            else:
                self.logger.Warning('WiFi is not primary network interface')
            return False

        if check5Ghz and (self.apIsOn5Ghz == None and self.Has5Ghz):
            self.apIsOn5Ghz = self.IsAPOn5Ghz(ap)

        self.IsConnectedTo5GhzWithWait(ap, logFail=(logFail and logFailIfNotOn5Ghz))
        return True

    def VerifyConnectionAndPing(self, ap, pingDestination="www.google.com", timeout=60, pingCount=10, checkPrimaryInterface=True, logFail=True,
                                check5Ghz=True, logFailIfNotOn5Ghz=False):
        '''
        Check if connected with correct AP, IP address, check network connection though ping
        '''
        if self.VerifyConnection(ap, timeout, checkPrimaryInterface, logFail, check5Ghz, logFailIfNotOn5Ghz):
            self.firmwareControl.CheckBCMPSMode()
            if self.traffic.PingTest(self.dut, pingDestination, pingCount):
                return True
        self.CollectWiFiDebugInfo()
        self.GetMobilityInfoLogs()
        return False

    def ConnectVerifyAPAndPing(self, ap, pingDestination="www.google.com", checkPrimaryInterface=True, timeout=60, pingCount=10, joinFromUI=True,
                               logFail=True, tries=1, forceJoinUI=False, check5Ghz=True, isWapiEnterprise=False, logFailIfNotOn5Ghz=False):
        '''
        Join an AP, verify connection, check valid IP address, check network connection though ping
        return network object
        '''
        for _try in range(1, tries + 1):
            network = self.ConnectAP(ap, joinFromUI=joinFromUI, logFail=(logFail and _try == tries), forceJoinUI=forceJoinUI,
                                     isWapiEnterprise=isWapiEnterprise)
            if network and self.VerifyConnectionAndPing(ap, pingDestination, timeout, pingCount, checkPrimaryInterface,
                                                        (logFail and _try == tries), check5Ghz, logFailIfNotOn5Ghz):
                return network
        return False

    def ConnectVerifyAP(self, ap, checkPrimaryInterface=True, timeout=60, joinFromUI=True, logFail=True, tries=1,
                        forceJoinUI=False, check5Ghz=True, isWapiEnterprise=False):
        '''
        Join an AP, verify connection, check valid IP address
        return network object
        '''
        for _try in range(1, tries + 1):
            network = self.ConnectAP(ap, joinFromUI=joinFromUI, logFail=(logFail and _try == tries), forceJoinUI=forceJoinUI,
                                     isWapiEnterprise=isWapiEnterprise)
            if network and self.VerifyConnection(ap, timeout, checkPrimaryInterface, (logFail and _try == tries), check5Ghz):
                return network
        return False

    def ConnectAP(self, ap, joinFromUI=True, logFail=True, forceJoinUI=False, useProfileForUI=False, isWapiEnterprise=False):
        '''
        Join an AP, verify connection, check valid IP address, return network object
        '''
        self.logger.Info("DUT (%s) connecting To: %s" % (self.dut.udid, ap.ssid))
        try:
            if not self.noUI and (((ap.isEap) and joinFromUI) or forceJoinUI):
                self.dut.getWiFi().JoinWithUI(ap, profile=useProfileForUI)
                network = True
            elif isWapiEnterprise:
                return self.uiControl.JoinWapiEnterpriseUI(ap, logFail=logFail)
                network = True
            else:
                network = self.dut.getWiFi().Join(ap)
                if self.apIsOn5Ghz == None and self.Has5Ghz:
                    self.apIsOn5Ghz = self.IsAPOn5Ghz(ap)
            try:
                self._scanTimes.append(self.dut.getWiFi().GetTimeTakenForLastScan())
            except:
                pass
            return network
        except (WiFiScanError, WiFiAssociationError, TestFailError) as error:
            if logFail:
                self.logger.Fail("Failed to join the network: %s with error: %s", ap.ssid, error)
            else:
                self.logger.Warning("Failed to join the network: %s with error: %s", ap.ssid, error)
            return False

    def VerifyPing(self, dest=None, count=10, maxFailurePercent=90, **kwargs):
        ''' Returns True if DUT can ping url destination '''
        self.logger.Info('Verify DUT can ping %s' % repr(dest))

        # Randomize some url, if not provided
        if dest is None:
            from random import randint
            dest = ['www.yahoo.com', 'www.espn.com', 'www.google.com'][randint(0, 2)]

        # Use ping utility to send from DUT OS to dest
        from TrafficGen.Ping import Ping
        p = Ping()
        return p.Ping(
                dut=self.dut,
                dest=dest,
                count=count,
                maxFailurePercent=maxFailurePercent,
                **kwargs
            )

    def VerifyBrowse(self, url='http://www.google.com'):
        ''' Test browse w/ UI (Safari), otherwise raises an error '''
        self.logger.Info('Verify DUT browse')

        # Skip if AppleTV
        if 'J33' in self.dut.hardware:
            self.logger.Warning('This is an AppleTV, skip Safari test')
            return True

        # Use scripter to open safari
        from common.Scripter import ScripterDictDispatch
        scripter = ScripterDictDispatch(self.dut)
        try:
            # Raises TestFailError if fails
            scripter.safari.LoadPage(url)
        except Exception, e:
            self.logger.Warning(str(e))
            return False

        # Browse was successful
        return True

    def VerifyAutoJoinSleepWake(self, network):
        '''
        Verifies the DUT auto join after autoJoinMethod
        Method can be (sleepWake, apOffOn, apReboot, wifiOffOn, deviceReboot)
        '''
        self.logger.Info('Verify DUT AutoJoin (sleepWake)')
        self.dut.os.SleepCycler(sleep=10, wake=1)
        return self.VerifyConnection(network)

    def VerifyAutoJoinWiFiOffOn(self, network):
        ''' Returns True if DUT performs autojoin after WIFI is disabled then enabled '''
        self.logger.Info('Verify DUT AutoJoin (wifiOffOn)')

        # Disable WIFI
        self.dut.wifi.Off()
        sleep(3)

        # Verify not associated
        if not self.VerifyNotConnected():
            return False

        # Enable WIFI
        self.dut.wifi.On()

        # DUT should autojoin
        return self.VerifyConnection(network)

    def VerifyAutoJoinDeviceReboot(self, network):
        ''' Returns True if DUT performs autojoin after DUT reboot '''
        self.logger.Info('Verify DUT AutoJoin (deviceReboot)')
        self.dut.os.Reboot()
        self.dut.WaitForConnect()
        return self.VerifyConnection(network)

    def VerifyAutoJoinApOffOn(self, ap, network):
        ''' Returns True if DUT performs autojoin after AP off/on '''

        # Save config and disable radios
        origCfg = deepcopy(ap.ap)
        for radioCfg in ap.ap['radios']:
            radioCfg['apRadioPower'] = 'off'
        ap.SetupAP()

        # Verify not associated
        if not self.VerifyNotConnected():
            return False

        # Restore AP config
        ap.ap = origCfg
        ap.SetupAP()

        return self.VerifyConnection(network, timeout=120)

    def VerifyAutoJoinApReboot(self, ap, network):
        ''' Returns True if DUT performs autojoin after AP reboot '''
        ap.Reboot()  # should wait until complete
        return self.VerifyConnection(network)

    def StopTestOnFailure(self, testName, errorMessage):
        '''
        Report Fail reason and stop the test by throw TestFailError
        update to call the LogParse methods to get and grep logs for errors
        '''
        self.logger.Fail("Stop Test: %s Failed", testName)
        raise TestFailError, TestFailError("Error Message is: %s", errorMessage)

    def EnableBT(self):
        self.SetBluetoothPower(1)

    def DisableBT(self):
        self.SetBluetoothPower(0)

    def FWReset(self):
        '''
        Resets the firmware
        '''
        self.logger.Info("Trigger watchdog")
        out = self.dut.getOS().Execute('apple80211 --ssid=w', True, True)
        if out.returnCode:
            self.logger.Fail("Watchdog failed: %s / %s", str(out.standardOutput).strip(), str(out.standardError).strip())
            return False
        else:
            self.logger.Pass("Watchdog successful: %s", str(out.standardOutput.strip()))
        return True

    def FWResetViaFirmwareLoader(self):
        '''
        Triggers wifiFirmwareLoader
        '''
        self.logger.Info("Triggerring wifiFirmwareLoader")
        self.dut.getOS().Execute('/usr/libexec/wifiFirmwareLoader', runAsRoot=True)

    def CheckDriverStatus(self):
        '''
        Checks the status of the wifi driver
        '''
        self.logger.Info("Check apple80211 -driver information")
        resultDict = self.dut.getOS().Execute("apple80211 -driver", runAsRoot=True)
        if resultDict is not None:
            stdOut = str(resultDict.standardOutput).strip()
            stdErr = str(resultDict.standardError).strip()
            if stdErr.find("Resource temporarily unavailable") >= 0 or stdErr.find("ioctl: Socket is not connected") >= 0 or stdErr.find("No 802.11 interface found") >= 0:
                raise TestFailError, TestFailError("Stop the test because apple80211 -driver returns: %s", stdErr)
            else:
                self.logger.Info("apple80211 -driver: %s %s", stdOut, stdErr)
                return True

    def CheckFirmwareStatus(self):
        '''
        Checks the status of the wifi firmware
        '''
        resultDict = self.dut.getOS().Execute("wl ver", runAsRoot=True)
        if resultDict is not None:
            stdOut = str(resultDict.standardOutput).strip()
            stdErr = str(resultDict.standardError).strip()
            if stdErr.find("wl driver adapter not found") >= 0:
                raise TestFailError, TestFailError("Stop the test because wl ver returns: %s", stdErr)
            else:
                self.logger.Info("wl ver: %s %s", stdOut, stdErr)

    def GetProductVersion(self):
        ''' Returns the SW product version '''
        return self.dut.getOS().Execute('sw_vers -productVersion').standardOutput.rstrip()

    def GetMacAddress(self):
        ''' Returns MAC address for WIFI interface '''
        ifname = self.dut.wifi.GetInterfaceName()
        ret = self.dut.getOS().Execute('/sbin/ifconfig %s | grep ether | awk \'{print $2}\'' % ifname).standardOutput.rstrip()
        if ret.startswith('ifconfig: '):
            self.logger.Warning('Failed to get MAC address, error %s' % repr(ret))
        return ret

    def GetBssid(self):
        ''' Returns current BSSID or '' '''
        ret = self.dut.getOS().Execute('wl bssid').standardOutput.rstrip()

        # Warn on error
        if ret == 'wl: Not Associated':
            self.logger.Warning('Failed to get BSSID, not associated')
            return ''
        elif ret == 'wl driver adapter not found':
            self.logger.Warning('Failed to get BSSID, WiFI is not on')
            return ''

        return ret.lower()

    def GetDataRate(self):
        ''' Returns data rate (float) '''
        return float(self.dut.getOS().Execute('wl rate').standardOutput.replace('Mbps', '').strip())

    def GetRssi(self):
        ''' Returns RSSI or -174.0 (noise floor dBm/Hz) in dBm '''
        ret = self.dut.getOS().Execute('wl rssi').standardOutput.rstrip()

        # Warn on error
        if ret == 'wl driver adapter not found':
            self.logger.Warning('Failed to run %s, WiFI is not on' % repr(ret))
            return -174.0
        elif int(ret) == 0:
            self.logger.Warning('Returned RSSI = 0 dBm, check WiFi connected to AP')
            return -174.0

        return float(ret)

    def SetBluetoothPowerOn(self, i=""):
        '''
        Turns on BT power
        '''
        try:
            if not issubclass(self.dut.__class__, BTDevice):
                self.logger.Info("Not turning on BT -- device is not a BT device")
                return 0
            self.dut.getBT().On()
            self.logger.Info("Bluetooth is on")
        except Exception as _err1:
            try:
                # Try again let BTServer sleep 15 seconds
                self.logger.Fail("BluetoothTest setup - Failed to Power on Bluetooth %s: %s", self.dut.name, _err1)
                Sleep(10, self.logger)
                self.dut.getBT().On()
            except Exception as _err2:
                # Ok time to kill the beast
                self.logger.Fail("BluetoothTest setup - Failed to Power on Bluetooth %s, executing kill command on BTServer process: %s", self.dut.name, _err2)
                Sleep(5, self.logger)
                result = self.dut.getOS().Execute("/usr/bin/killall BTServer")
                if result is not None:
                    self.dut.getBT().HandleIfBTServerCrashed()
                    try:
                        self.dut.getBT().On()
                    except:
                        raise BTPowerError, BTPowerError("Failed to power on device - 3rd attempt.")
                else:
                    raise BTPowerError, BTPowerError("Failed to kill BTServer")

    def SetBluetoothPowerOff(self, i=""):
        '''
        Turns off BT power
        '''
        if not issubclass(self.dut.__class__, BTDevice):
            self.logger.Info("Not turning on BT -- device is not a BT device")
            return 0
        self.dut.getBT().Off()
        self.logger.Info("Bluetooth is off")

    def SetBluetoothPower(self, power):
        '''
        Macro to toggle BT power on and off
        '''

        if power == 0:
            self.SetBluetoothPowerOff()
        else:
            self.SetBluetoothPowerOn()

    def WiFiOff(self, useUI=False):
        '''
        Method for turning WiFi off and validating the operations were successful.
        '''
        if useUI and self.noUI:
            self.logger.Warning("This test has noUI set to True. UI commands may fail")
        if self.noUI or not useUI:
            self.dut.getWiFi().Off()
        else:
            self.uiControl.SetWiFiUI(on=False)
        self.CheckWiFiPowerOff()

    def WiFiOn(self, useUI=False):
        '''
        Method for turning WiFi off and validating the operations were successful.
        '''
        if useUI and self.noUI:
            self.logger.Warning("This test has noUI set to True. UI commands may fail")
        if self.noUI or not useUI:
            self.dut.getWiFi().On()
        else:
            self.uiControl.SetWiFiUI(on=True)
        self.CheckPowerOn()

    def WiFiPower(self, power="", useUI=False):
        '''
        Method to turn WiFi power on and off
        '''
        if power == 0:
            self.WiFiOff(useUI=useUI)
        elif power == 1:
            self.WiFiOn(useUI=useUI)
        else:
            self.logger.Fail("Invalid power passed to WiFiPower toggle: %s", power)

    def UIAirplaneModeOff(self):
        '''
        Method for toggling Airplane mode off using the UI and validating the operations were successful.
        '''
        cmdOff = 'interact -uiautomation YES "s = app.mainWindow().tableViews()[0].cells()["Airplane Mode"].switches()[0]; s.setValue(parseInt(0))"'
        self.logger.Info('AirplaneMode Off through UI')
        self.dut.getOS().Execute(cmdOff, runAsRoot=True)
        Sleep(10, self.logger)
        # Check if WiFi is on.
        if not self.dut.getWiFi().GetPower():
            if self.dut.GetAirplaneMode():
                self.logger.Warning('Airplane mode did not turn off, could be UIA interact issue, turn it off again and continue tests...')
                # self.dut.ui.springboard.DismissAll()
                test = ' settingsTest_changeSingleSwitch "Airplane Mode" 0'
                try:
                    _result = scripter(self.dut, 'Turn off Airplane Mode', test, block=True)
                    self.CheckPowerOn()
                    if not self.dut.getWiFi().GetPower():
                        raise TestFailError, TestFailError('WiFi is still off after AirplaneMode is disabled')
                    else:
                        self.logger.Pass('WiFi is on after Airplane Mode is Off')
                except TestFailError:
                    self.logger.Fail('Failed to Turn off Airplane Mode')
            else:
                raise TestFailError, TestFailError('WiFi is still off after AirplaneMode is disabled')
        else:
            self.logger.Pass('WiFi is on after Airplane Mode is Off')

    def UIAirplaneModeOn(self):
        '''
        Method for toggling Airplane mode on using the UI and validating the operations were successful.
        '''

        self.logger.Info('Try unload the plist before Enable/Disable Tethering')
        self.dut.getOS().DisableUSBEthernetSharing()
        self.logger.Info('Navigate to Settings and turn off Airplane Mode first...')
        test = ' settingsTest_changeSingleSwitch "Airplane Mode" 0'
        try:
            _result = scripter(self.dut, 'Turn ON Airplane Mode', test, block=True)
        except TestFailError:
            self.logger.Fail('Failed to Turn ON Airplane Mode')
        self.logger.Info('AirplaneMode ON through UI')
        # Enable UIA
        cmdOn = 'interact -uiautomation YES "s = app.mainWindow().tableViews()[0].cells()["Airplane Mode"].switches()[0]; s.setValue(parseInt(1))"'
        try:
            _resultDict = self.dut.getOS().Execute(cmdOn, runAsRoot=True)
            # Check if WiFi is off.
            if not self.dut.getWiFi().GetPower():
                self.logger.Pass('WiFi is off after Airplane Mode is ON')
            else:
                if not self.dut.GetAirplaneMode():
                    self.logger.Warning('Airplane mode did not turn on, could be UIA interact issue, turn it on again and continue tests...')
                    # self.dut.ui.springboard.DismissAll()
                    test = ' settingsTest_changeSingleSwitch "Airplane Mode" 1'
                    try:
                        _result = scripter(self.dut, 'Turn ON Airplane Mode', test, block=True)
                        if not self.dut.getWiFi().GetPower():
                            self.logger.Pass('WiFi is OFF after Airplane Mode is ON')
                        else:
                            raise TestFailError, TestFailError('WiFi is still ON after AirplaneMode is enabled')
                    except TestFailError:
                        self.logger.Fail('Failed to Turn ON Airplane Mode')
                else:
                    raise TestFailError, TestFailError('WiFi is still ON after AirplaneMode is enabled')
            self.CheckWiFiPowerOff()
        except WiFiPowerError:
            self.logger.Fail('Airplane Mode ON Fail')

    def UIAirplaneMode(self, i, power):
        if power == 0:
            self.UIAirplaneModeOff(i)
        else:
            self.UIAirplaneModeOn(i)

    def WiFiManagerDevice(self):
        resultDict = self.dut.getOS().Execute("/usr/local/bin/mobilewifitool -- manager devices", runAsRoot=True)
        if resultDict is not None:
            output_State = str(resultDict.standardOutput)
            error_State = str(resultDict.standardError)
            if error_State.find("no devices") >= 0:
                self.logger.Fail("Fail the test because WiFi Manager reports No WiFi")
                self.error_NoWiFi = True
                raise TestFailError, TestFailError("Stop the test because mobilewifitool -- manager devices returns: %s", error_State)
            else:
                return str(output_State[output_State.find("en"):]).strip()

    def CheckLeaks(self, processName, logFailOnLeaks=True):
        """
        Common function to check for leaks
        """
        if processName in self.currentLeaks:
            if not processName in self.leakStackLoggingEnabled:
                self.logger.Warning("Device may not have stack trace logging enabled for: %s", processName)

        try:
            result = self.dut.getOS().Leaks(processName)
        except OSLeaksError as _err:
            self.logger.Warning("%s", _err)
            return False
        newLeaks = result[1] - self.currentLeaks[processName] if processName in self.currentLeaks else 0
        if result[0] and newLeaks > 0:
            if logFailOnLeaks:
                self.logger.Fail("Found leaks in %s, total new leaks: %s. Please check ./Logs folder for leaks log", processName, newLeaks)
            else:
                self.logger.Warning("Found leaks in %s, total new leaks: %s. Please check ./Logs folder for leaks log", processName, newLeaks)
            self.logger.Info("Setting current leaks for %s to %s", processName, newLeaks)
            self.currentLeaks[processName] = newLeaks
            return True
        self.logger.Pass("No new %s leaks found, %d total", processName, result[1])

        return False


    def CheckWifidLeaks(self, logFailOnLeaks=True):
        """
        Check wifid leaks if wifid stack trace logging is enabled
        """
        return self.CheckLeaks(processName='wifid', logFailOnLeaks=True)

    def CheckConfigdLeaks(self, logFailOnLeaks=True):
        """
        Check configd leaks
        """
        return self.CheckLeaks(processName='configd', logFailOnLeaks=True)

    def Unlock(self, goToSpringBoard=True):
        """
        Unlocks the device if UI is available, go to SpringBoard if goToSperingBoard set to true
        """
        if not self.noUI:
            try:
                # work around for <rdar> description
                self.logger.Info("Unlock display")
                self.dut.ui.Unlock()
                # self.logger.Info("result is: %s error is: %s", result.standardOutput, result.standardError)
                if goToSpringBoard:
                    # self.dut.ui.springboard.DismissAll()
                    self.dut.ui.springboard.Home()
            except:
                self.logger.Warning('Failed to unlock springboard, probably hit the problem: rdar://problem/9394726 Pop-up box causes script errors, continue testing..')
        else:
            self.logger.Info("NoUI is set, skipping unlock")
            if not self.dut.isAppleTV():
                self.logger.Info("Pressing the home button to make sure device attempts autojoin")
                self.dut.getOS().Execute("eventer h")

    def Lock(self):
        """
        Locks the device if UI is available
        """
        if not self.noUI:
            self.logger.Info('Turn off SpringBoard')
            self.dut.getOS().Execute('scripter -i SpringBoard.js -c "UIATarget.localTarget().lock()"', True, True)

    def EnableWoW(self, changeBootArgsIfNeeded=False):
        """
        If WoW is supported, run mobilewifitool in the background.
        """
        if not self.dut.getWiFi().IsWoWSupported():
            if changeBootArgsIfNeeded:
                RemoveBootArgs(self.dut, 'bcom.feature.wow=0x0', False)
                self.EnableWoW()
            else:
                self.logger.Fail("The test device has WoW support disabled, make sure 'bcom.feature.wow=0' is not in boot-args and reboot the device")
                return False

        self.logger.Info("Setting WoW to 1")
        self.dut.getOS().purpleDevice._cam_device.os().asRoot().launchedNoHUPTaskWithCommand_arguments_("/bin/bash", ['-c', "echo 'wow 1' | mobilewifitool"])
        # Innsbruck 11A4xx: this leaves mobilewifitool running
        # TODO:Test this code path for Brighton.
        # self.logger.Info("Running mobilewifitool in the background")
        # Broken by WAF for now :(
        # self.dut.getOS().Execute("nohup -- mobilewifitool", False, True)
        return True

    def DisableWoW(self, changeBootArgsIfNeeded=False):
        if self.dut.getWiFi().IsWoWSupported():
            if changeBootArgsIfNeeded:
                AddBootArgs(self.dut, 'bcom.feature.wow=0x0', False)
                self.DisableWoW()

        if not self.dut.getWiFi().IsWoWSupported():
            self.logger.Info("WoW is not supported")
            return True

        if self.dut.getWiFi().IsWoWEnabled():
            self.dut.getOS().Kill('mobilewifitool')
            if self.dut.getWiFi().IsWoWEnabled():
                self.logger.Info("Killed mobilewifitool, but WoW is still enabled. Other clients must exist.")
                return False
        return True

    def IsOn5Ghz(self):
        """
        Returns whether the device is currently on a 5Ghz channel
        """
        try:
            currentChannel = self.dut.getWiFi().GetNetwork().channel
        except Exception as e:
            self.logger.Warning("Caught error trying to get channel info: %s", e)
            return False
        if currentChannel > 14:
            return True
        return False

    def IsOn24Ghz(self):
        """
        Returns whether the device is currently on a 2.4Ghz channel
        """
        return not self.IsOn5Ghz()

    def GetMobilityInfoLogs(self):
        """
        Runs get-mobility-info
        """
        self.logger.Info("Running 'get-mobility-info'")
        self.dut.getOS().Execute("/usr/local/bin/get-mobility-info", True, True)

    def CollectWiFiDebugInfo(self):
        """
        Runs collectWiFiDebugInfo
        """
        self.logger.Info("Running 'collectWiFiDebugInfo'")
        self.dut.getOS().Execute("if [[ -e /usr/local/bin/collectWiFiDebugInfo.sh ]] ; then /usr/local/bin/collectWiFiDebugInfo.sh ; fi", True, True)


    def EnableAskToJoin(self):
        """
        Enable Ask To Join
        """
        result = self.dut.getOS().Execute("mobilewifitool -- asktojoin 1", True, True)
        if result.standardOutput.find('Ask To Join is On') >= 0:
            return True
        else:
            self.logger.Info("Failed to turn on Ask to Join, command returns: %s, error: %s", result.standardOutput, result.standardError)
            return False

    def DisableAskToJoin(self):
        """
        Disable Ask To Join
        """
        result = self.dut.getOS().Execute("mobilewifitool -- asktojoin 0", True, True)
        if result.standardOutput.find('Ask To Join is Off') >= 0:
            return True
        else:
            self.logger.Info("Failed to turn off Ask to Join, command returns: %s, error: %s", result.standardOutput, result.standardError)
            return False

    def IsAskToJoinWindowPresent(self):
        """
        Check if Ask To Join Window showed up
        """
        self.logger.Info("Check if Ask to join window is up")
        cmd = "scripter -c \"UIATarget.localTarget().frontMostApp().alert().elements().firstWithPredicate('name contains \\'Select a Wireless Network\\' and isVisible == 1')\""
        # self.logger.Info("cmd is %s", cmd)
        result = self.dut.getOS().Execute(cmd, True, True)
        # self.logger.Info("result is: %s, error is %s", result.standardOutput, result.standardError)
        if result.standardOutput.find('UIAElementNil') >= 0:
            self.logger.Info("The Ask To Join Window did not show up")
            return False
        elif result.standardOutput.find('object') >= 0:
            self.logger.Info("Ask to Join Window showed up")
            return True
        else:
            self.logger.Info("Did not find the window: returns: %s; Error: %s", result.standardOutput, result.standardError)
            return False

    def ResetDHCPState(self):
        self.dut.getOS().Execute('ipconfig set %s NONE' % self.WiFiManagerDevice(), block=True, runAsRoot=True)
        self.dut.getOS().Execute('echo "notify Setup:/Network/Service/0/IPv4" | scutil', block=True, runAsRoot=True)

    def SetSiri(self, enable=True):
        '''
        Enable or Disable Siri from UI Settings
        '''
        if enable:
            testName = 'Enable Siri'
            test = ' settingsTest_changeSingleSwitch General Siri Siri 1'
        else:
            testName = 'Disable Siri'
            test = ' settingsTest_changeSingleSwitch General Siri Siri 1'
        try:
            if scripter(self.dut, testName, test, block=True):
                return True
            else:
                self.logger.Info('Failed to %s through UI', testName)
                return False
        except TestFailError:
            self.logger.Info('Failed to %s through UI', testName)
            return False

    def ActivateSiriWithUI(self):
        '''
        Bring Up Siri UI to activate Siri
        '''
        cmd = "scripter -c \"UIATarget.localTarget().holdMenu(2)\""

        try:
            result = self.dut.getOS().Execute(cmd, block=True, runAsRoot=True)
            if result and result.returnCode == 0:
                self.logger.Info("Activate Siri Pass")
                return True
        except TestFailError as error:
            self.logger.Info("Activate Siri Fail: %s", error)
        self.logger.Info("Get a Screen Shot because test failed")
        self.dut.ui.Screenshot("Fail_ActivateSiri")
        return False

    def DeactivateSiriWithUI(self):
        '''
        Deactivate Siri from UI
        '''
        cmd = "scripter -c \"UIATarget.localTarget().holdMenu(0)\""

        try:
            result = self.dut.getOS().Execute(cmd, block=True, runAsRoot=True)
            if result and result.returnCode == 0:
                self.logger.Info("Deactivate Siri Pass")
                return True
        except TestFailError as error:
            self.logger.Info("Deactivate Siri Fail: %s", error)
        self.logger.Info("Get a Screen Shot because test failed")
        self.dut.ui.Screenshot("Fail_DeactivateSiri")
        return False

    def CheckForLeaks(self, logFailOnLeaks=True):
        for process in self.currentLeaks:
            self.CheckLeaks(process)

    def AddProcessToLeaksMonitor(self, processName, enableStackLogging=True, waitForDaemonToLaunch=True):
        '''
        Start monitoring this process for leaks -- enable stack logging and record the value of leaks when we start
        Returns true if the plist had to be modified.
        '''
        changedPlist = False
        try:
            envVars = self.utils.DefaultsReadFromPlist(plistName='/System/Library/LaunchDaemons/com.apple.{0}.plist'.format(processName), variable='EnvironmentVariables')
            leaksAlreadyEnabled = envVars and (('MallocStackLogging' in envVars and envVars['MallocStackLogging']) and ('MallocStackLoggingNoCompact' in envVars and envVars['MallocStackLoggingNoCompact'] == "YES"))
            if not leaksAlreadyEnabled and enableStackLogging:
                self.dut.getOS().AddLeaksPlist(processName, mallocStackLogNoCompact=True)
                changedPlist = True
                self.logger.Info("Enabled leak stack logging for: %s", processName)
            else:
                self.logger.Info("Leak stack logging already enabled (or not requested) for: %s", processName)
        except Exception as _err:
            self.logger.Warning("Caught %s, likely daemon does not exist on the device. Leaks logging not enabled for %s", _err, processName)
            return False

        self.leakStackLoggingEnabled[processName] = True
        self.CheckLeaks(processName, logFailOnLeaks=False)

        return changedPlist

    def IsAPOn5Ghz(self, ap):
        channels = ""
        self.logger.Info("Testing for presense of AP on channels higher than 14 (5Ghz)")
        for _i in range(1, 3):
            if self.IsConnectedTo5Ghz():
                self.logger.Info("Device is connected on 5Ghz, will wait for roam scans from now on")
                return True
            self.logger.Info("Device isn't on 5Ghz yet, scanning")
            # channels += self.dut.getOS().Execute('wifitool -S ' + ap.ssid + ' | grep channel | cut -d":" -f2').standardOutput
            channels += self.dut.getOS().Execute("mobilewifitool -- scan -i " + self.WiFiManagerDevice() + " --ssid=" + ap.ssid + " | awk '/ch/ { print $14 }'").standardOutput
            for _x in channels.split():
                try:
                    if int(x) > 14:
                        self.logger.Info("AP is on 5Ghz, will wait for roam scans from now on")
                        return True
                except:
                    pass
            Sleep(5, self.logger)
        self.logger.Info("Did not see AP on 5Ghz")
        return False

    def IsConnectedTo5Ghz(self):
        network = self.dut.getWiFi().GetNetwork()
        return network and network.channel > 14

    def IsConnectedTo5GhzWithWait(self, ap, logFail=False):
        if self.Has5Ghz and self.apIsOn5Ghz:
            self.logger.Info("Checking if the device is on 5Ghz")
            if self.IsConnectedTo5Ghz():
                self.logger.Info("Device is on 5Ghz")
                return True
            else:
                Sleep(12, self.logger, "Waiting 12 seconds for roam-scans")
                if self.IsConnectedTo5Ghz():
                        self.logger.Info("Device is on 5Ghz")
                        return True
                if any(item.rssi >= -60 for item in self.dut.getWiFi().Scan(ssid=ap.ssid)):
                    if logFail:
                        self.logger.Fail("AP found stronger than -60dBm but dut has not roamed")
                    else:
                        self.logger.Warning("AP found stronger than -60dBm but dut has not roamed")
                    return False
                else:
                    self.logger.Info("AP found on 5Ghz but weaker than -60dBm. Roam won't occur")
                    return True

    def PersonalHotspotOn(self, password):
        # Enable Personal Hotspot and WiFi should not join any WiFi Network
        result = self.SetPersonalHotspot(on=True, password=password)

        if result:
            if self.dut.getWiFi().WaitForNetwork(timeout=5):
                actual_network = self.dut.getWiFi().GetNetwork()
                raise TestFailError, TestFailError("WiFi still connected with %s after enable Personal Hotspot", actual_network.ssid)
            else:
                self.logger.Pass("WiFi is not joined to a network after Personal Hotspot is enabled")
        self.MAX_PH_CLIENTS = self._GetMaxAssocClients()  # Update
        if not ((self.dut.hardware == 'N90' and self.MAX_PH_CLIENTS == 3) or self.MAX_PH_CLIENTS == 5):
            self.logger.Fail("Max PH clients is unexpected. We expect 3 for N90, and 5 for all other platforms. Device reports %d for 'wl maxassoc'", self.MAX_PH_CLIENTS)
            result = False
        else:
            self.logger.Pass("Maximum number of PH clients supported: %d", self.MAX_PH_CLIENTS)
        return result

    def PersonalHotspotOff(self, ap=False):
        # Disable Personal Hotspot and WiFi should join previous joined WiFi network
        result = self.SetPersonalHotspot(on=False)
        self.PersonalHotspotClientNumberCheck(0)
        if result and ap and not self.VerifyConnectionAndPing(ap):
            self.logger.Fail("WiFi did not join the network %s after Personal Hotspot is disabled", ap.ssid)
            return True
        return result

    def SetPersonalHotspot(self, on=True, password="12345678"):
        self.dut.getOS().DisableUSBEthernetSharing()
        if on:
            test = " settingsTest_setPersonalHotspot 1 %s " % (password)
            try:
                result = scripter(self.dut, "Turn on WiFi Personal Hotspot", test, block=True)
            except TestFailError:
                self.logger.Fail("Failed to turn on Personal Hotspot through UI")
        else:
            try:
                result = scripter(self.dut, "Turn off WiFi Personal Hotspot", " settingsTest_setPersonalHotspot 0", block=True)
            except TestFailError:
                self.logger.Fail("Failed to turn off Personal Hotspot through UI")

        if result:
            if on:
                self.logger.Pass("Enable Personal Hotspot on %s Pass.", self.dut.hardware)
            else:
                self.logger.Pass("Disable Personal Hotspot on %s Pass.", self.dut.hardware)
        else:
            if on:
                self.logger.Fail("Enable Personal Hotspot on %s Fail.", self.dut.hardware)
            else:
                self.logger.Fail("Disable Personal Hotspot on %s Fail.", self.dut.hardware)

        return result

    def NumberOfPHClients(self):

        output = self.dut.getOS().Execute('apple80211 --stalist')

        if output is not None:
            station_result = output.standardOutput
            if output.standardError != "":
                self.logger.Warning("Check personal hotspot clients failed, apple80211 reported error: %s", output.standardError)
                return 0
        else:
            self.logger.Fail("Check personal hotspot clients failed, could not get any result from apple80211 --stalist")
            return -1

        result = re.split('Station', station_result)
        i = len(result) - 2
        self.logger.Info("Personal Hotspot has %s client(s) joined", i)
        self.logger.Debug("apple80211 --stalist reports: \n%s", station_result)
        i = 0 if i < 0 else i
        return i

    def PersonalHotspotClientNumberCheck(self, number):
        ''' Returns True if current associated clients is >= number '''

        # Lazy update for max associated clients
        if self.MAX_PH_CLIENTS == False:
            self.MAX_PH_CLIENTS = self._GetMaxAssocClients()

        current_number = self.NumberOfPHClients()
        if number < 0:
            number = 0
        if current_number - number == 1:
            Sleep(70, self.logger, "Current number (" + str(current_number) + ") is 1 higher than expected, waiting for 70 seconds for idle disconnect")
            current_number = self.NumberOfPHClients()
        if current_number > self.MAX_PH_CLIENTS:
            self.logger.Fail("Current number of clients is higher than max number allowed")
            return False
        elif (number > self.MAX_PH_CLIENTS and current_number == self.MAX_PH_CLIENTS):
            self.logger.Pass("There are %s (max) clients joined with the Personal Hotspot (checking for %s)", current_number, number)
            return True
        elif number == current_number or (number < 0 and current_number == 0):
            self.logger.Pass("There are %s clients joined with the Personal Hotspot (checking for %s)", current_number, number)
            return True
        else:
            self.logger.Fail("There are %s clients joined with the Personal Hotspot (checking for %s)", current_number, number)
            return False

    def GetVendorId(self):
        '''
        Get Vendor ID and check if OTP value matches the Vendor ID
        '''
        vendorId = ""
        if 'J1' in self.dut.hardware or 'J2' in self.dut.hardware:
            self.logger.Info("J serial does not support vendor ID")
            return vendorId

        ioregData = self.dut.getOS().Execute('/usr/sbin/ioreg -c AppleBCMWLANCore -w0 -rd1').standardOutput

        vendorIdIndex = ioregData.find('vendor-id')
        vendorId = ioregData[vendorIdIndex:ioregData.find('\n', vendorIdIndex)].split()[-1].strip('"')

        otpIndex = ioregData.find('ModuleDictionary')
        OtpString = ioregData[otpIndex:ioregData.find('\n', otpIndex)]
        OtpValue = OtpString[OtpString.find('V=') + 2:OtpString.find('V=') + 3]

        if self.dut.hardware == 'N88':
            self.logger.Info("N88 does not have info in module-info string, vendor-id: %s", vendorId)
            return vendorId


        if OtpValue[0].lower() == vendorId[0].lower():
            self.logger.Info("OTP value matches Vendor ID")
            self.logger.Info("Vendor ID is %s, OTP value is %s", vendorId, OtpValue[0])
        else:
            self.logger.Info("ioreg dump is: \n%s", ioregData)
            self.logger.Fail("OTP value and Vendor ID mismatch: OTP shows %s, Vendor ID shows %s", OtpValue[0], vendorId)
        return vendorId

    def PersonalHotspot_OffTimerTest(self, password):
        if self.PersonalHotspotOn(password):
            self.logger.Info("Check station list after turn on personal hotspot")
            self.Lock()
            timer_string = "Starting MIS idle timer"
            expire_string = "MIS idle timer expired. MIS is Enabled, associated clients = 0, discovery is Disabled"
            stop_string = "Stopping MIS session"
            # check the timer string from syslog
            Sleep(5, self.logger, "wait for MIS timer to start")
            cmd = "syslog -k Time ge -15"
            resultDict = self.dut.getOS().Execute(cmd)
            if resultDict is not None:
                syslog_result = resultDict.standardOutput
                if syslog_result.find(timer_string) >= 0:
                    self.logger.Pass("Found '%s' in syslog, timer stated", timer_string)
                else:
                    self.logger.Fail("Did not find '%s' in syslog, timer did not fire", timer_string)
            else:
                self.logger.Fail("Get Personal Hotspot Timer information from syslog failed")

            Sleep(90, self.logger, "Waiting for 90 seconds for the timer to expire")
            resultDict = self.dut.getOS().Execute(cmd)
            if resultDict is not None:
                syslog_result = resultDict.standardOutput
                if syslog_result.find(expire_string) >= 0:
                    self.logger.Pass("Find '%s' in syslog, timer expired", expire_string)

                    if syslog_result.find(stop_string) >= 0:
                        self.logger.Pass("Found '%s' in syslog, Personal Hotspot stopped", stop_string)
                    else:
                        self.logger.Fail("Did not find '%s' in syslog", stop_string)
                else:
                    self.logger.Fail("Did not find '%s' in syslog", expire_string)
            else:
                self.logger.Fail("Get Personal Hotspot Timer information from syslog failed")

        else:
            self.logger.Fail("Could not enable Personal Hotspot, skip the Personal Hotspot test by turning off display")

    def wifitool(self, command):
        cmd = "/usr/local/bin/wifitool " + command
        return self.dut.getOS().Execute(cmd, True, True)

    def wifitool_scan(self, network="", attempts=5):
        for i in range(0, attempts):
            if network == "":
                result = self.wifitool("-s")
            else:
                result = self.wifitool("-S " + network)
            if len(str(result.standardOutput.split("\n"))) > 0 and network in result.standardOutput:
                self.logger.Pass("wifitool scan succeeded")
                return True
            self.logger.Info("Attempt %d: stdout: %s, stderr: %s", i + 1, result.standardOutput.split(), str(result.standardError))
            Sleep(3, self.logger, "Waiting to scan again")
        self.logger.Fail("Failed to find network(s).")
        return False

    def wifitool_join(self, network, encryption='none', password='', tries=1):
        if not self.wifitool_scan(network):
            self.logger.Fail("Can not join network since it was not found during scan")
            return False
        command = "-j " + network + " -e " + encryption
        if not encryption.lower() == 'none':
            command += " -p " + password
        for i in range(0, tries):
            status = self.wifitool(command)
            if status.standardOutput.find('SUCCEED') >= 0:
                self.logger.Pass(status.standardOutput.strip())
                return True
            self.logger.Warning("Attempt %d failed, trying again", i + 1)

        self.logger.Fail("stdout: %s; stderr: %s", status.standardOutput.replace('\n', ' '), status.standardError.replace('\n', ' '))
        return False

    def VerifyWoWEnabled(self):
        if self.dut.getWiFi().IsWoWEnabled():
            if self.apple80211.GetWowEnable():
                self.logger.Info("WiFi Manager and apple80211 both report wow is enabled")
                return True
            else:
                raise TestFailError, TestFailError("WiFiManager and Wow status mismatch, wifimanager return wow is enabled, but apple80211 return wow disabled")
                # self.logger.Fail("WiFiManager and Wow status mismatch, wifimanager return wow is enabled, but apple80211 return wow disabled")
        else:
            if not self.apple80211.GetWowEnable():
                self.logger.Info("WiFi Manager and apple80211 both report wow is disabled")
                return False
            else:
                if self.wl.SupportLpas():
                    self.logger.Info("WiFi Manager report wow disabled, apple80211 report wow enabled because device supports LPAS")
                    if self.wifiManager.IsLpasEnabled():
                        self.logger.Info("LPAS is enabled")
                        return False
                    else:
                        self.logger.Fail("Wow is disabled, but LPAS is not enabled")
                else:
                    raise TestFailError, TestFailError("WiFiManager and Wow status mismatch, wifimanager return wow is disabled, but apple80211 return wow enabled")
                # self.logger.Fail("WiFiManager and Wow status mismatch, wifimanager return wow is disabled, but apple80211 return wow enabled")

    def CheckMemUse(self):
        _memUse = self.wl.MemUse()
        if not _memUse:
            return False
        heapFree = int(_memUse[_memUse.find('(') + 1:_memUse.find('K)')])
        packetPoolAvailable = int(_memUse[_memUse.find('pool:') + len('pool: '):_memUse.find('of') - 1])
        returnValue = False
        if heapFree < 4:
            self.logger.Warning("memuse heap is low: %d", heapFree)
            returnValue = True
        else:
            self.logger.Info("memuse is OK: %d", heapFree)

        if packetPoolAvailable < 4:
            self.logger.Warning("packet pool is low: %d", packetPoolAvailable)
            returnValue = True
        else:
            self.logger.Info("packet pool is OK: %d", packetPoolAvailable)
        return returnValue

    def _GetMaxAssocClients(self):
        ''' Returns int for Personal Hotspot max associated clients '''
        return int(self.dut.getOS().Execute('wl maxassoc').standardOutput.split()[0])


def EncryptionFromAPDictionary(WiFinetworkObject):
    if WiFinetworkObject.isWpa:
        encryption = 'wpa2'
    elif WiFinetworkObject.isWep:
        encryption = 'wep'
    elif WiFinetworkObject.isEap:
        encryption = 'wpa2Enterprise'
    else:
        encryption = 'none'
    return encryption

def ModifyBootArgs(dut, bootArgsNeedToAdd, addIntoBootArgs=True, useDefaultBootArgs=False):
    '''
    Modify boot-args into device
    If addIntoBootArgs == True: add the bootArgsNeedToAdd to existing boot-args.
    If addIntoBootArgs == False: remove the bootArgsNeedToAdd from existing boot-args.
    If useDefaltBootArgs set as True or could not get the current boot-args,
    it will modify the bootArgsNeedToAdd based on 'serial=3 debug=0x14e cs_enforcement_disable=1 amfi=3'
    This function will reboot device after changing the boot-args
    '''
    result = False
    dut.logger.Info("Modify exsisting boot-args with boot-args %s.", bootArgsNeedToAdd)
    defaultBootArgs = 'serial=3 debug=0x14e cs_enforcement_disable=1 amfi=3'
    if useDefaultBootArgs:
        bootArgString = defaultBootArgs
    else:
        result = dut.getOS().Execute('/usr/sbin/nvram boot-args', runAsRoot=True)
        if result is not None:
            bootArgString = result.standardOutput.replace('boot-args', '', 1).strip()
        else:
            dut.Warning("Could not get the current boot-args, will use the default.")
            bootArgString = defaultBootArgs

    if (bootArgsNeedToAdd in bootArgString) == addIntoBootArgs:
        dut.logger.Info("%s is already " + ("added" if addIntoBootArgs else "removed") + ".", bootArgsNeedToAdd)
        result = True
    else:
        if addIntoBootArgs:
            # Add bootArgsNeedToAdd the existing boot-args
            newBootArgs = ' '.join([bootArgString, bootArgsNeedToAdd])
        else:
            # Remove bootArgsNeedToAdd from the existing boot-args
            newBootArgs = bootArgString.replace(bootArgsNeedToAdd, '').strip()

        dut.getOS().Execute("/usr/sbin/nvram boot-args='" + newBootArgs + "'", runAsRoot=True)
        dut.getOS().Reboot()
        dut.logger.Info("boot-args has been configured.")
        result = True
    return result

def GetBootArgs(dut):
    ''' Returns a dict containing boot-args configured for nvram '''
    ret = dut.os.Execute('/usr/sbin/nvram boot-args', runAsRoot=True)
    m = re.search('^boot-args\s+(.*?)$', ret.standardOutput)
    if not m:
        LogIt().Warning('Failed to find boot args, got %s' % ret)
        return ''
    return dict([item.split('=') for item in m.group(1).split(' ')])

def AddBootArgs(dut, bootArgsNeedToAdd, useDefaultBootArgs=False):
    '''
    Add boot-args into device
    If useDefaltBootArgs set as True or could not get the current boot-args,
    it will modify the bootArgsNeedToAdd based on 'serial=3 debug=0x14e cs_enforcement_disable=1 amfi=3'
    This function will reboot device after changing the boot-args
    '''
    return ModifyBootArgs(dut, bootArgsNeedToAdd, addIntoBootArgs=True, useDefaultBootArgs=useDefaultBootArgs)

def RemoveBootArgs(dut, bootArgsNeedToAdd, useDefaultBootArgs=False):
    '''
    Add boot-args into device
    If useDefaltBootArgs set as True or could not get the current boot-args,
    it will modify the bootArgsNeedToAdd based on 'serial=3 debug=0x14e cs_enforcement_disable=1 amfi=3'
    This function will reboot device after changing the boot-args
    '''
    return ModifyBootArgs(dut, bootArgsNeedToAdd, addIntoBootArgs=False, useDefaultBootArgs=useDefaultBootArgs)

def DestroyBuddySetup(dut):
    '''
    Undo buddy plists so that buddy / Setup.app is front-most again
    '''
    dut.logger.Info("Removing buddy setup, device will end up back in buddy")
    dut.getOS().Execute("/sbin/mount -uw /", True, True)
    dut.getOS().Execute("/bin/rm /private/var/mobile/Library/Preferences/com.apple.purplebuddy.plist", True, True)
    dut.getOS().Execute("/bin/rm /private/var/mobile/Library/Preferences/com.apple.springboard.plist", True, True)
    for process in ["com.apple.SpringBoard", "com.apple.backboardd", "com.apple.CommCenter" ]:
        dut.getOS().Execute("/bin/launchctl stop " + process, True, True)
    Sleep(15, dut.logger, "Waiting for process to re-spawn")

def GetOutOfBuddy(dut):
    '''
    Modify plists to get out of buddy aka Setup.app
    '''
    dut.logger.Info("Fixing buddy")
    if dut.isAppleTV():
        dut.Execute("/usr/bin/defaults write com.apple.frontrow ATVSetupDone -bool YES", True)
        dut.getOS().Execute("/usr/bin/killall AppleTV")
    else:
        dut.getOS().Execute("/usr/bin/defaults write com.apple.purplebuddy SetupDone -bool YES", True)
        dut.getOS().Execute("/usr/bin/defaults write com.apple.purplebuddy ForceNoBuddy -bool YES", True)
    for process in ['com.apple.SpringBoard', 'com.apple.backboardd', 'com.apple.CommCenter']:
        dut.getOS().Execute("/bin/launchctl stop " + process, True, True)
    Sleep(15, dut.logger, "Waiting for process to re-spawn")
#----------------------------------------------------------------------
#  MAIN
#----------------------------------------------------------------------
def SetConfigurationTag(logger, ap):
    if ap.isWpa or ap.isWpa2:
        logger.SetTestProperty("configurationtag", " - WPA")
    elif ap.isEap:
        logger.SetTestProperty("configurationtag", " - EAP")
    elif ap.isWep:
        logger.SetTestProperty("configurationtag", " - WEP")
    else:
        logger.SetTestProperty("configurationtag", " - Open")
    if ap.isHidden:
        logger.SetTestProperty("configurationtag", "(Hidden)")

if __name__ == '__main__':
    import CoreAutomation as cam
    import WirelessAutomation as waf
    from LogInit import LogIt
    from APControl.SohoAP import GetAppleWiFiNetwork

    duts = [waf.WiFiDevice(dev, logger=LogIt()) for dev in cam.CAMEmbeddedDevice.allDevices()]  # @UndefinedVariable
    ctrl = Control(duts[0])

    print '-' * 65
    print 'WIFI info:'
    print ctrl.GetProductVersion()

    print '-' * 65

    network = GetAppleWiFiNetwork()
    assert ctrl.VerifyJoin(network)
    assert ctrl.VerifyBrowse()
    assert ctrl.VerifyAutoJoinWiFiOffOn(network)
    assert ctrl.VerifyAutoJoinSleepWake(network)
    assert ctrl.VerifyAutoJoinDeviceReboot(network)

