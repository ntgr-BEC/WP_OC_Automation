#!/usr/bin/env python2.3
'''
WirelessAutomation

Created by Anthony Murabito 5/8/2012
Copyright (c) 2012 Apple. All rights reserved.
'''

from common.WirelessLogging import Sleep
from LogInit import LogIt
from Utilities.InterfaceInfo import InterfaceInfo
from common.WirelessException import TestFailError
from common.WirelessCommon import iPadDevice
from WiFi.Apple80211 import Apple80211
from WiFi.wl import wl

class AWDL(object):
    '''
    Class for performing common functions on AWDL devices
    '''
    def __init__(self, dut):
        self.logger = LogIt()
        self.dut = dut
        self.supported = None
        self.apple80211 = Apple80211(self.dut.getOS())
        self.wl = wl(self.dut.getOS())
        self.awdl_if = ""
        self.awdl_if_info = None
        self._init()

    def _init(self):
        if not self.IsAWDLSupported():
            self.logger.Info("AWDL is not supported on this hardware (%s)", self.dut.udid)
            return

        ifconfig = self.dut.getOS().Execute('/sbin/ifconfig').standardOutput
        if 'awdl0' in ifconfig:
            self.logger.Pass("AWDL detected on awdl0 interface")
            self.awdl_if = "awdl0"
        elif 'awdl1' in ifconfig:
            self.logger.Pass("AWDL detected on awdl1 interface")
            self.awdl_if = "awdl1"
        else:
            self.logger.Fail("AWDL interface not detected!")

        self.awdl_if_info = InterfaceInfo(self.awdl_if, self.dut)

    def ForceEnableAWDL(self, dut=False):
        '''
        Forces enabling AWDL via driver/family
        '''
        dut = self.__GetDut(dut)
        self.apple80211._run('-awdl_enable=1')
        if not self.IsAWDLEnabled():
            self.logger.Fail("Unable to force enable AWDL on %s", self.dut.udid)
            return False
        self.logger.Pass("Force-enabled AWDL on %s", self.dut.udid)
        return True

    def EnableAWDL(self, dut=False, setForceEnable=True, leaveRunning=False):
        '''
        Enables AWDL via dns query, or forces it if that doesn't work
        '''
        dut = self.__GetDut(dut)
        self.BonjourDiscoverAWDL(dut)
        if not self.IsAWDLEnabled():
            dut.getOS().Kill("dns-sd")
            self.logger.Warning("AWDL not enabled after bonjour discovery trigger on %s", self.dut.udid)
            self.PrintAWDLStatus()
            if not setForceEnable:
                return False
            else:
                return self.ForceEnableAWDL()
        if not leaveRunning:
            dut.getOS().Kill("dns-sd")
        return True

    def ForceDisableAWDL(self, dut=False):
        '''
        Disables AWDL via driver/family
        '''
        dut = self.__GetDut(dut)
        self.apple80211._run('-awdl_enable=0')
        if self.IsAWDLEnabled():
            self.logger.Fail("Failed to disable AWDL on %s", self.dut.udid)

    def DisableAWDL(self, dut=False):
        '''
        Disable AWDL
            .
        '''
        dut = self.__GetDut(dut)
        self.ForceDisableAWDL(dut)

    def EnableAWDLSolo(self, dut=False):
        self.apple80211._run("awdl0 -ctl=awdl_solo=1")
        if not self.IsAWDLSoloEnabled():
            self.logger.Fail("Unable to turn AWDL Solo Mode on %s", self.dut.udid)
            return False
        self.logger.Pass("Successfully Enabled AWDL Solo Mode on %s", self.dut.udid)
        return True

    def IsAWDLSoloEnabled(self, dut=False):
        dut = self.__GetDut(dut)
        result = self.apple80211._run("awdl0 -ctl=awdl_solo")
        if result.find('awdl_solo = 1') >= 0:
            self.logger.Info("AWDL Solo is enabled on %s", self.dut.udid)
            return True
        elif result.find('awdl_solo = 0') >= 0:
            self.logger.Info("AWDL Solo is disabled on %s", self.dut.udid)
            return False
        else:
            self.logger.Info("Failed to get AWDL Solo status  on %s, command returns: %s", self.dut.udid, result)
            return False

    def DisableFirewalls(self, dut=False):
        '''
        Disables the ipv4 and ipv6 interface restrictions for AWDL. needed for iperf / APV
        '''
        dut = self.__GetDut(dut)
        self.logger.Info("Disabling firewalls on %s", self.dut.udid)
        removeFirewall4 = "/usr/sbin/sysctl -w net.inet.ip.restrictrecvif=0"
        removeFirewall6 = "/usr/sbin/sysctl -w net.inet6.ip6.restrictrecvif=0"

        dut.getOS().Execute(removeFirewall4, block=True, runAsRoot=True)
        dut.getOS().Execute(removeFirewall6, block=True, runAsRoot=True)

    def PrintAWDLStatus(self, dut=False):
        '''
        print the output of apple80211 -awdl
        '''
        dut = self.__GetDut(dut)
        result = self.apple80211._run('-awdl')
        self.logger.Info('AWDL Status on %s: %s', self.dut.udid, result)

    def PrintAWDLPeerStatus(self, dut=False):
        '''
        print the output of awdl peers
        '''
        dut = self.__GetDut(dut)
        result = self.apple80211._run('-dbg=a_peer_cache')
        self.logger.Info("AWDL FW Peer Status: %s", result)

        result = self.apple80211._run('-awdl_peers')
        self.logger.Info("AWDL Peers: %s", result)

    def PrintAWDLServiceRecordStatus(self, dut=False):
        '''
        print the output of apple80211 awdl_sr
        '''
        dut = self.__GetDut(dut)
        result = self.apple80211._run('-awdl_sr')
        self.logger.Info("AWDL Service Record Status on %s: %s", self.dut.udid, result)

    def ToggleVerboseLogging(self, dut=False):
        '''
        this invokes some toggles that will enable or disable verbose AWDL logging
        in the syslog
        '''
        dut = self.__GetDut(dut)
        result = self.apple80211._run('-awdl_log=ev')
        result = self.apple80211._run('-awdl_log=mi')

    def GetAWDLInterface(self):
        '''
        return the awdl interface
        '''
        return self.awdl_if

    def GetAWDLInterfaceIndex(self):
        '''
        return the interface index
        '''
        if not self.awdl_if_info:
            self.logger.Warning('AWDL interface is not available')
            return -1
        return self.awdl_if_info.Index()

    def GetAWDLInfo(self):
        ''' Returns a dict containing AWDL interface info '''
        return self.awdl_if_info

    def GetMAC(self):
        '''
        return the AWDL mac address
        '''
        if not self.awdl_if_info:
            self.logger.Warning('AWDL interface is not available')
            return -1
        return self.awdl_if_info.MAC_Address()

    def BonjourDiscoverAWDL(self, dut=False):
        '''
        return true if AWDL successfully exchanges service records
        '''
        dut = self.__GetDut(dut)
        self.logger.Info("Attempting Native MDNS Airplay Discovery on %s", self.dut.udid)

        mdnsAirplayBrowse = "dns-sd -includeAWDL -B _airplay._tcp"
        mdnsAirplayBrowse = "dns-sd -includeAWDL -B _test local"
        airplayDiscover = dut.getOS().Execute(mdnsAirplayBrowse, block=False)
        Sleep(5, self.logger)
        dut.getOS().UpdateProcessOutput(airplayDiscover)

        bonjour = airplayDiscover.standardOutput.encode('utf-8').splitlines()
        self.logger.Info("Discovered the following Services: %s", airplayDiscover.standardOutput)
        for line in bonjour:
            if "Add" in line and "_airplay._tcp" in line:
                record = line.split(' ')
                index = record.index('local.')
                recordIndex = str(record[index - 1]).strip()
                if int(recordIndex) == int(self.GetAWDLInterfaceIndex()):
                    return True
        return False

    def BonjourRegisterAWDLSolo(self, dut=False):
        """
        Register test service to enable AWDL Solo
        """
        dut = self.__GetDut(dut)
        self.logger.Info("Attempting Native MDNS AirDropTest Service Register on %s", self.dut.udid)

        mdnsAirplayBrowse = "dns-sd -includeAWDL -R AirDropTest _test local 8888"
        airplayDiscover = dut.getOS().Execute(mdnsAirplayBrowse, block=False)
        Sleep(5, self.logger, "Waiting for the dns services to register")
        if not self.IsAWDLEnabled():
            self.logger.Fail("AWDL is not enabled after mDNS service register")
            self.PrintAWDLStatus()
            return False
        return True

    def BonjourRemoveAWDLSolo(self, dut=False):
        """
        Removes all dns-sd services including those enabling AWDL Solo
        """
        # TODO: Store PID of the dns-sd command so it can be killed specifically later, so other dns-sd commands can continue running
        dut = self.__GetDut(dut)
        self.logger.Info("Attempting removal of all dns-sd services on %s", self.dut.udid)

        mdnsAirplayBrowse = "killall dns-sd"
        airplayDiscover = dut.getOS().Execute(mdnsAirplayBrowse, block=True)

    def IsAWDLEnabled(self, dut=False):
        dut = self.__GetDut(dut)
        if not self.IsAWDLSupported(dut):
            return False
        result = self.apple80211._run('-awdl_enable')
        self.logger.Info("Getting AWDL status on %s", self.dut.udid)
        if result.find('awdl is enabled') >= 0:
            self.logger.Info("AWDL is enabled")
            return True
        elif result.find('awdl is disabled') >= 0:
            self.logger.Info("AWDL is disabled")
            return False
        else:
            self.logger.Info("Failed to get AWDL status, command returns: %s", result.strip())
            return False
        # TODO: wl cap |grep awdl

    def IsAWDLSupported(self, dut=False):
        if self.supported != None:
            return self.supported
        dut = self.__GetDut(dut)
        if not dut.getWiFi().GetPower():
            self.logger.Info("Turning WiFi ON to check AWDL")
            dut.getWiFi().On()
        try:
            return not self.dut.getOS().Execute('apple80211 -awdl > /dev/null 2>&1', True, True).returnCode
        except Exception as e:
            self.logger.Warning("Exception: %s", e)
            self.supported = False
            return False

    def __GetDut(self, dut=False):
        '''
        internal function to validate dut
        '''
        if not dut:
            dut = self.dut
        return dut

    def isUsingAWDLInterface(self, dut=False, waitTime=5):
        '''
        Use nettop to check if awdl0 interface is used for traffic
        '''
        result = False
        dut = self.__GetDut(dut)
        try:
            cmd = "TERM=vt100 /usr/bin/nettop"
            nettopResult = dut.getOS().Execute(cmd, block=False, runAsRoot=True)
            Sleep(waitTime, self.logger, msg='Wait for nettop to update')
            dut.getOS().UpdateProcessOutput(nettopResult)
            if nettopResult:
                if nettopResult.standardOutput.find(self.awdl_if) > 0:
                    self.logger.Info("Nettop reports using AWDL interface now")
                    result = True
                else:
                    self.logger.Info("did not find %s from nettop output", self.awdl_if)
                    self.IsAWDLEnabled(dut)
            else:
                self.logger.Info("Failed to get results from nettop")

            dut.getOS().Kill(nettopResult)

        except Exception as e:
            self.logger.Warning("Exception: %s", e)

        # self.logger.Info("nettop output is: %s, error is %s", nettopResult.standardOutput, nettopResult.standardError)
        return result

    def setMirrorModeWithUI(self, dut=False, enableMirrorMode=True, AppleTVName='Apple TV'):
        '''
        Set Mirror Mode with UI
        '''
        dut = self.__GetDut(dut)
        if dut.build >= '11A000':  # Innsbruck+
            self._setMirrorModeWithUI7(dut, enableMirrorMode, AppleTVName)
        else:  # All previous builds
            self._setMirrorModeWithUI6(dut, enableMirrorMode, AppleTVName)

    def _setMirrorModeWithUI7(self, dut=False, enableMirrorMode=True, AppleTVName='Apple TV'):
        ''' Returns True if successfully sets mirror mode w/ UI '''
        dut = self.__GetDut(dut)

        # Go to home screen
        ret = dut.ui.springboard.Home()
        if not ret:
            self.logger.Warning('Failed to set UI to home screen')
            return False

        # Swipe up for control center
        ret = dut.os.Execute('scripter -uiautomation YES -i /usr/local/etc/scripterUIA/libs/SpringBoard.js -c "springboard.dragUpForControlCenter()"')
        if ret.returnCode != 0:
            self.logger.Warning('Failed to swipe up for Control Center')
            return False

        # Select Airplay (cannot find element by name because it changes if connected)
        AIRPLAY_BTN_ID = 18  # UI button index
        cmd = 'scripter -uiautomation YES -i /usr/local/etc/scripterUIA/libs/UIAApp.js'
        cmd += ' -c "UIATarget.localTarget().frontMostApp().mainWindow().elements()[%s].tap()"' % AIRPLAY_BTN_ID
        ret = dut.os.Execute(cmd)
        if ret.returnCode != 0:
            self.logger.Warning('Failed to select Airplay to %s' % repr(AppleTVName))
            return False

        # Select AppleTV
        cmd = 'scripter -uiautomation YES -i /usr/local/etc/scripterUIA/libs/UIAApp.js'
        cmd += ' -c "UIATarget.localTarget().frontMostApp().mainWindow().popover().elements()[1].visibleElementsWithName(\\\"%s\\\")[0].tap()"' % AppleTVName
        ret = dut.os.Execute(cmd)
        if ret.returnCode != 0:
            self.logger.Warning('Failed to select Airplay to %s' % repr(AppleTVName))
            return False

        # Determine if mirroring is enabled
        cmd = 'scripter -uiautomation YES -i /usr/local/etc/scripterUIA/libs/UIAApp.js'
        cmd += ' -c "UIATarget.localTarget().frontMostApp().mainWindow().popover().elements()[1].visibleElementsWithName(\\\"Mirroring\\\")[0].value()"'
        ret = dut.os.Execute(cmd)
        if ret.returnCode != 0:
            self.logger.Warning('Failed to select Airplay to %s' % repr(AppleTVName))
            return False
        mirroringEnabled = bool(int(ret.standardOutput.strip()))

        # Return if already configured
        if enableMirrorMode == mirroringEnabled:
            return True

        # Select MirrorMode
        cmd = 'scripter -uiautomation YES -i /usr/local/etc/scripterUIA/libs/UIAApp.js'
        cmd += ' -c "UIATarget.localTarget().frontMostApp().mainWindow().popover().elements()[1].visibleElementsWithName(\\\"Mirroring\\\")[0].tap()"'
        ret = dut.os.Execute(cmd)
        if ret.returnCode != 0:
            self.logger.Warning('Failed to select Airplay to %s' % repr(AppleTVName))
            return False

        return True

    def _setMirrorModeWithUI6(self, dut=False, enableMirrorMode=True, AppleTVName='Apple TV'):
        ''' Returns True if successfully sets mirror mode w/ UI '''
        dut = self.__GetDut(dut)
        try:
            if enableMirrorMode:
                setLog = "Enabled"
                if issubclass(dut.__class__, iPadDevice):
                    cmd = 'scripter -i SpringBoard.js UIAAppAirPlayAdditions.js -c "springboard.getToElementWithNameInAppSwitcher(\'AirPlay\').tap(); springboard.selectAirPlayRoute(\'' + AppleTVName + '\', {turnOnMirroring:1})\"'
                else:
                    # Work around for <rdar> description
                    cmd = 'scripter -i SpringBoard.js UIAAppAirPlayAdditions.js -c "springboard.getToElementWithNameInAppSwitcher(\'Volume\'); springboard.switcherScrollView().buttons()[0].tap(); springboard.selectAirPlayRoute(\'' + AppleTVName + '\', {turnOnMirroring:1})\"'

            else:
                setLog = "Disabled"
                if issubclass(dut.__class__, iPadDevice):
                    cmd = 'scripter -i SpringBoard.js UIAAppAirPlayAdditions.js -c "springboard.getToElementWithNameInAppSwitcher(\'AirPlay\').tap(); springboard.selectAirPlayRoute(\'iPad\')"'
                else:
                    cmd = 'scripter -i SpringBoard.js UIAAppAirPlayAdditions.js -c "springboard.getToElementWithNameInAppSwitcher(\'AirPlay\').tap(); springboard.selectAirPlayRoute(\'iPhone\')"'

            result = dut.getOS().Execute(cmd, block=True, runAsRoot=True)
            if result and result.returnCode == 0 :
                self.logger.Info("Set Mirror Mode Pass: %s", setLog)
                return True
        except TestFailError as error:
                self.logger.Info('Failed to set Mirror Mode through UI, error %s', error)

        self.logger.Info("Failed to set Mirror Mode, result is %s, error is %s", result.standardOutput, result.standardError)
        self.logger.Info("Get a Screen Shot")
        dut.ui.Screenshot("Fail_SetMirrorMode_%s" % setLog)
        return False

    def AirplayUtil(self, cmd):
        '''
        Get the value returned with airplayutil tool using given cmd
        '''
        cmd = "airplayutil " + cmd
        try:
            result = self.dut.getOS().Execute(cmd, block=True, runAsRoot=True)
            if result and result.returnCode == 0:
                return result.standardOutput
            else:
                self.logger.Info("Could not get return value of '%s' using airplayutil", cmd)
        except TestFailError as error:
            self.logger.Info("Failed to use airplayutil tool for cmd '%s' with error: %s", cmd, error)
        return None

    def GetAWDLIPAddress(self, dut=False):
        dut = self.__GetDut(dut)
        return dut.getWiFi().GetTcpipSettings(self.awdl_if, True).ipAddress + "%" + self.awdl_if

    def GetAirplayUtiInfo(self):
        '''
        Get airplayutil information
        '''
        result = self.AirplayUtil('diagnose')
        if result:
            self.logger.Info("airplayutil diagnose output is:\n %s", result)
        result = self.AirplayUtil('avsys pickable')
        if result:
            self.logger.Info("airplayutil avsys pickable output is:\n %s", result)
        result = self.AirplayUtil('show')
        if result:
            self.logger.Info("airplayutil show output is:\n %s", result)
