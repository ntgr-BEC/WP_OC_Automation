#!/usr/bin/env python2.3
'''
DUT_Utils.py
WirelessAutomation

Created by Dmitry Halavin
Copyright (c) 2010 Apple. All rights reserved.
'''
from LogInit import LogIt
import plistlib, os, sys
from common.WirelessCommon import iPadDevice, BTDevice
import CoreAutomation as cam
import WirelessAutomation as waf
from common.WirelessException import WAError

class DUT_Utils(object):
    '''
    Class for controlling and querying wifi related methods
    '''
    def __init__(self, dut):
        self.dut = dut
        self.logger = LogIt()

    def Battery_Level(self, dut=False):
        if self._GetDut(dut):
            try:
                if self.dut.hardware == 'K66' or 'J33' in self.dut.hardware.upper():
                    return 100
                batteryInfo = self.dut.getOS().Execute("powerlog -Bq", timeout=30).standardOutput
                batteryLevel = float(batteryInfo[batteryInfo.find("level=") + 6:batteryInfo.find("%")])
                self.logger.Info("Battery level is %f", batteryLevel)
            except:
                self.logger.Info("Unable to get battery level")
                batteryLevel = 100
            return batteryLevel
        else:
            return 0

    def DefaultsReadFromPlist(self, plistName, variable=""):
        '''
        Will return a dictionary containing values from the plist specified
        Arguments: plistName to read; variable being the key to read if only partial read is required
        Throws IOError if said plist does not exist
        '''
        plist = self.ReadPlist(plistName)
        if not variable:
            return plist
        if variable in plist:
            return plist[variable]
        else:
            self.logger.Info("Variable %s is not in the plist", variable)
            return False

    def ReadPlist(self, plistName):
        self.dut.getOS().Execute("/sbin/mount -uw /", True, True)
        self.dut.getOS().Execute("/usr/bin/plutil -convert xml1 " + plistName, True, True)
        return plistlib.readPlistFromString(str(self.dut.getOS().Execute("/bin/cat " + plistName, True, True).standardOutput))

    def _GetDut(self, dut):
        '''
        internal function to validate dut
        '''
        if not self.dut and not dut:
            self.logger.Info('Must provide a dut using the SetDut API or as an arg')
            return False
        elif dut:
            self.dut = dut
        return True

    def StopPowerCharge(self, dut=False, stopPowerPercent=0.0):
        '''
        Stop power charge if power is higher than give power percent, if it is None, just stop charge without checking power
        '''
        if not self._GetDut(dut):
            self.logger.Info("Failed to get test device")
            return False

        powerLevel = self.Battery_Level(dut)
        if powerLevel > stopPowerPercent:
            self.logger.Info("Power is %f, higher than %f, stop power charge", powerLevel, stopPowerPercent)
            self.dut.getOS().Execute("screen -dmS battdrain /usr/local/bin/setbatt drain", block=True, runAsRoot=True)
        else:
            self.logger.Info("Power is %f, lower than %f, skip stop power charge", powerLevel, stopPowerPercent)

        return True

    def StartPowerCharge(self, dut=False, startPowerPercent=100.0):
        '''
        Start power charge if power is lower than give power percent, if it is None, just start charge without checking power
        '''
        if not self._GetDut(dut):
            self.logger.Info("Failed to get test device")
            return False

        powerLevel = self.Battery_Level(dut)
        if powerLevel < startPowerPercent:
            self.logger.Info("Power is %f, lower than %f, start power charge", powerLevel, startPowerPercent)
            self.dut.getOS().Execute("/usr/bin/killall setbatt", block=True, runAsRoot=True)
            self.dut.getOS().Execute("/usr/local/bin/setbatt on", block=True, runAsRoot=True)
        else:
            self.logger.Info("Power is %f, higher than %f, skip start power charge", powerLevel, startPowerPercent)

        return True

    def InstallCert(self, profilePath, timeout=30):
        '''
        Install cert into dut using profilectl
        '''
        cmd = "install! %s" % (profilePath)
        return self.ProfilectlTool(cmd, timeout=timeout)

    def ProfilectlTool(self, cmd, timeout=30):
        '''
        Run profilectl command
        '''
        cmd = "/usr/local/bin/profilectl %s" % (cmd)
        cmdResult = self.dut.getOS().Execute(cmd, timeout=timeout)
        if cmdResult.standardError != '':
            if cmdResult.standardOutput.find("Received profile list changed notification") >= 0:
                self.logger.Info("Profile cmd %s succeed", cmd)
                return True
            self.logger.Info("Failed to run profilectl cmd %s with error: %s", cmd, cmdResult.standardError)
            self.logger.Info("Failed to run profilectl cmd %s with output: %s", cmd, cmdResult.standardOutput)
            return False
        else:
            self.logger.Info("Profile cmd %s succeed", cmd)
            return True

    def UnloadPlist(self, plist, logFail):
        '''
        Unload plist in the device
        '''
        plistFile = "/System/Library/LaunchDaemons/" + plist
        self.dut.logger.Info("Checking if the plist %s exists." % plistFile)
        if self.dut.getOS().DoesFileExist(plistFile):

            # Force file-system to be writable. This might already be done, but avoids a common source of failures here.
            self.dut.getOS().Execute("/sbin/mount -uw /", runAsRoot=True)
            self.logger.Info("Unloading plist for %s.", plistFile)
            result = self.dut.getOS().Execute("/bin/launchctl unload {0}".format(plistFile), runAsRoot=True)
            if 0 == result.returnCode:
                self.logger.Info("Succeeded unloading plist for %s.", plistFile)
                return True
            else:
                if logFail:
                    self.logger.Fail("Failed to unload plist for %s.", plistFile)
                else:
                    self.logger.Warning("Failed to unload plist for %s.", plistFile)
        else:
            if logFail:
                self.logger.Fail("Failed to find the plist %s.", plistFile)
            else:
                self.logger.Warning("Failed fine the plist %s.", plistFile)
        return False

    def LoadPlist(self, plist, logFail=True, needReboot=False):
        '''
        load plist, reboot if needed
        '''
        plistLabel = plist.replace('.plist', '')
        plistFile = "/System/Library/LaunchDaemons/" + plist
        self.logger.Info("Checking if plist %s exists.", plistFile)
        if self.dut.getOS().DoesFileExist(plistFile):
            # Force file-system to be writable. This might already be done, but avoids a common source of failures here.
            self.dut.getOS().Execute("/sbin/mount -uw /", runAsRoot=True)
            self.logger.Info("Loading the plist %s.", plistFile)
            result = self.dut.getOS().Execute("/bin/launchctl load {0}".format(plistFile), runAsRoot=True)
            if 0 == result.returnCode:
                self.logger.Info("Succeeded Loading plist for %s.", plistFile)
            else:
                if logFail:
                    self.logger.Fail("Failed to load  %s.", plistFile)
                else:
                    self.logger.Warning("Failed to load  %s.", plistFile)
                return False
            # Give the daemon a chance to initialize.
            result = self.dut.getOS().Execute("for i in {{1..20}}; do /bin/launchctl list {0} | grep PID && exit 0; sleep 0.5; done; exit 1".format(plistLabel), runAsRoot=True)
            if result.returnCode != 0:
                if logFail:
                    self.dut.logger.Fail("%s never re-launched.error %s", plist, result)
                else:
                    self.dut.logger.Warning("%s never re-launched.", plist)
                    return False
            if needReboot:
                self.logger.Info("Reboot device...")
                self.dut.getOS().Reboot()
            return True
        else:
            if logFail:
                self.logger.Fail("Can not find plist %s", plistFile)
            else:
                self.logger.Warning("Can not find plist %s", plistFile)
                return False


    def InstallCertOnDut(self, macCertPath, profileName, timeout=30, removeAllProfiles=True, dutCertPath='/tmp/', logFail=True):
        '''
        Copy over the cert and install using profilectl tool. remove all profiles if requested
        macCertPath: path of the profile
        profileName: name of the profile
        '''

        result = False
        macCert = os.path.join(macCertPath, profileName)
        detCert = os.path.join(dutCertPath, profileName)
        if removeAllProfiles:
            try:
                self.dut.getOS().UninstallAllProfiles()
            except WAError as e:
                if logFail:
                    self.logger.Fail("Failed to remove all the profiles with error: %s", e)
                else:
                    self.logger.Warning("Failed to remove all the profiles with error: %s", e)
                return result

        try:
            self.dut.getOS().CopyFileFromHost(macCert, dutCertPath)
        except Exception as e:
            if logFail:
                self.logger.Fail("Failed to transfer cert on host %s to the device, error: %s", macCert, e)
            else:
                self.logger.Warning("Failed to transfer cert on host %s to the device, error: %s", macCert, e)
            return result
        if not self.InstallCert(detCert, timeout=timeout):
            if logFail:
                self.logger.Fail("Failed to install cert")
            else:
                self.logger.Warning("Failed to install cert")
        else:
            self.logger.Info("Cert installed")
            result = True
        return result


    def ResetWifid(self, logFail=True, needReboot=False):
        """
        Unload and reload wifid plist.
        @ return: True if the configuration was successful, otherwise False.

        """
        plist = "com.apple.wifid.plist"
        daemon = 'wifid'

        if self.UnloadPlist(plist, logFail=logFail):
            if self.LoadPlist(plist, logFail=logFail, needReboot=needReboot):
                self.dut.getOS().ReattachToDaemon(daemon)
                return True
        if logFail:
            self.dut.logger.Fail("Failed to reset wifid")
        else:
            self.dut.logger.Warning("Failed to reset wifid")
        return False

    def UnloadPushPlist(self, logFail=True):
        '''
        Unload Push Plist to disable Push services
        '''
        plist = "com.apple.apsd.plist"

        return self.UnloadPlist(plist, logFail=logFail)

    def LoadPushPlist(self, logFail=True, needReboot=False):
        '''
        Load the Push Plist and Reboot the device to re-enable Push services
        '''
        plist = "com.apple.apsd.plist"
        return self.LoadPlist(plist, logFail=logFail, needReboot=needReboot)

    def GetKernWakeReason(self):
        '''
        Get device wake reason from kernel
        '''
        checkWakeupReasonCommand = "/usr/sbin/sysctl -n kern.wakereason"
        wakeupReason = self.dut.getOS().Execute(checkWakeupReasonCommand, block=True)
        return wakeupReason.standardOutput

def GetWiFiDevices():
    """ Returns all WiFiDevice instances attached to the local system """
    logger = LogIt()
    return [waf.WiFiDevice(dev, logger=logger) for dev in cam.CAMEmbeddedDevice.allDevices()]

def StartLoggingConsole(dut, consoleLogFile):
    '''
    If the dut has a serial port that we can open (primate cables), start logging it
    @param dut: device in question
    @param consoleLogFile: file name to be logged to
    @return:file handle to the log file in question
    '''
    logger = LogIt()
    if dut.serialPort():
        try:
            dut.serialPort().open()
            fileHandler = open(consoleLogFile, 'w')
            dut.serialPort().setHostPath_(consoleLogFile)
        except Exception as e:
            logger.Fail('Unable to open file %s for write' % str(consoleLogFile))
            return False
        return fileHandler

def StopLoggingConsole(dut, fileHandle):
    '''
    If the dut has a serial port that is being used for logging, close it
    @param dut: device in question
    @param fileHandle: a file handle to the console log file so it can be closed
    @return:file handle to the log file in question
    '''
    logger = LogIt()
    if dut.serialPort():
        dut.serialPort().close()
        fileHandle.close()
