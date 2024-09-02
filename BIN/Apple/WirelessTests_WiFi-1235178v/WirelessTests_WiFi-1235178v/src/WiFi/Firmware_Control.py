#!/usr/bin/env python2.3
'''
WirelessAutomation
Copyright (c) 2012 Apple. All rights reserved.
'''
from LogInit import LogIt
from common.WirelessException import TestFailError
import re # @UnresolvedImport
from common.WirelessCommon import iPadDevice
from wl import wl

class Firmware_Control(object):
    def __init__(self, dut):
        self.logger = LogIt()
        self.dut = dut
        self.wl = wl(dut.os)
        self.maxSupportedChannel = 0
        self.interface = self.dut.getWiFi().GetInterfaceName()

    def Counters(self):
        return self.wl.GetCountersStr()

    # Deprecated, should use wl.py
    def BCMWlTool(self, cmd, interface=None):
        '''
        Get the value returned with wl tool using given cmd
        '''
        if interface == None:
            interface = self.interface
        try:
            ret = self.wl._run("-i {0} {1}".format(interface, cmd))
            if self.wl.GetErrors():
                raise Exception('\n'.join(self.wl.GetErrors()))
            return ret
        except TestFailError as error:
            self.logger.Info("Failed to use wl tool for cmd '%s' with error: %s", cmd, error)
        return ""  # wl command failed

    def CheckBCMPSMode(self, expected=2):
        '''
        Get WiFi Power Save Mode using BCM wl tool
        2 for associated, after getting IP / timing out
        0 for unassociated or PH enabled
        '''
        if self.dut.isAppleTV():
            expected = 0

        ps = self.wl.GetPsMode()
        if ps == expected:
            self.logger.Pass("PS Mode check pass: wl returns {0} for wl PM".format(expected))
            return ps
        else:
            self.logger.Fail("PS Mode check fail: wl returns {0} for wl PM, expecting {1}".format(ps, expected))
            self.logger.Info("Status: %s", self.wl.GetStatus())
            return False

    def CheckFRTS(self):
        '''
        Using wl tool tp get BCM fast return to sleep (FRTS) time in ms to return to power save
        '''
        try:
            result = self.wl.GetFrtsTime()
            self.logger.Info("FRTS returns %s", result)
            if issubclass(self.dut.__class__, iPadDevice):
                frts = 200
            else:
                frts = 60
            if result == frts:
                self.logger.Pass("%s FRTS is %d ", self.dut.hardware, frts)
            else:
                self.logger.Fail("%s FRTS should be %d, but wl returns %d", self.dut.hardware, frts, result)
            return result
        except Exception as error:
            self.logger.Info("Failed to get the FRTS value with error: %s", error)
        self.logger.Fail("Could not check for FRTS")
        return False

    def CheckRoamTrigger(self, multiAP=False):
        '''
        Use wl command to check roam trigger values
        '''
        testResult = True
        if multiAP:
            passNumberB = -70
            passNumberA = -70
        else:
            passNumberB = -75
            passNumberA = -75

        if self.dut.hardware == 'N81' or self.dut.hardware == 'N88' or self.dut.hardware == "K66" or self.dut.hardware.find("K9") >= 0 or self.dut.hardware == 'N90' or self.dut.hardware == 'N92':
            self.logger.Warning("%s does not support CheckRoamTrigger feature", self.dut.hardware)
            return True
        resultB = self.wl.GetRoamTrigger('b')
        if resultB == passNumberB:
            self.logger.Pass("Pass with wl roam_trigger test: %s", resultB)
        else:
            self.logger.Fail("wl roam_trigger should return with value: %s, but it returns: %s", passNumberB, resultB)
            testResult = False

        if self.Has5Ghz():
            resultA = self.wl.GetRoamTrigger('a')
            if resultA == passNumberA:
                self.logger.Pass("Pass with wl roam_trigger test: %s", resultA)
            else:
                self.logger.Fail("wl roam_trigger should return with value: %s, but it returns: %s", passNumberA, resultA)
                testResult = False

        return testResult

    def CheckRoamDelta(self, multiAP=False):
        '''
        Use wl command to check roam delta values
        '''
        testResult = True
        if self.dut.hardware == 'N81' or self.dut.hardware == 'N88' or self.dut.hardware == "K66" or self.dut.hardware.find("K9") >= 0 or self.dut.hardware == 'N90' or self.dut.hardware == 'N92':
            self.logger.Warning("%s does not support CheckRoamDelta feature", self.dut.hardware)
            return True
        if multiAP:
            passNumberB = 12
            passNumberA = 12
        else:
            passNumberB = 20
            passNumberA = 10

        resultB = self.wl.GetRoamDelta('b')
        if resultB == passNumberB:
            self.logger.Pass("Pass with wl roam_delta test: %s", resultB)
        else:
            self.logger.Fail("wl roam_delta should return with value: %s, but it returns: %s", passNumberB, resultB)
            testResult = False

        if self.Has5Ghz():
            resultA = self.wl.GetRoamDelta('a')
            if resultA == passNumberA:
                self.logger.Pass("Pass with wl roam_delta test: %s", resultA)
            else:
                self.logger.Fail("wl roam_delta should return with value: %s, but it returns: %s", passNumberA, resultA)
                testResult = False

        return testResult

    def Has5Ghz(self):
        """
        Returns whether the device supports 5ghz channels.
        """
        if self.maxSupportedChannel == 0:
            try:
                result = self.wl.GetSupportedChannels()
                self.maxSupportedChannel = result[-1]
            except Exception as error:
                self.logger.Debug("Caught exception %s, returning false", error)
                return False
            self.logger.Info("Max supported channel: %d", self.maxSupportedChannel)
        if self.maxSupportedChannel > 14:
            return True
        else:
            return False
