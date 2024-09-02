#!/usr/bin/env python
#-----------------------------------------------------------------------
# Copyright (c) 2012 Apple
# All Rights Reserved
#
# Proprietary and Confidential
#-----------------------------------------------------------------------

""" Provides Wow/Pno functions """
import copy

from plistlib import readPlist
# Internal modules
from WiFi.Control import Control, ModifyBootArgs
from LogInit import LogIt
from common.WirelessException import TestFailError, WiFiWoWError
from common.WirelessLogging import Sleep
from TrafficGen.Ping import Ping
# from HostTools.SnifferTools import SnifferTools

class WowPno(object):
    '''
    Class for performing common functions on WoW/PNO devices
    '''
    def __init__(self, dut):
        self.logger = LogIt()
        self.dut = dut
        # self.uiControl = UI_Control(self.dut)
        self.dutControl = Control(self.dut)
        if not self.dut.getWiFi().IsWoWSupported():
            self.logger.Info("WoW is not supported on this hardware.")

    def DisableChargeWake(self, dut=False):
        dut = self.__GetDut(dut)
        return ModifyBootArgs(dut, "charger-sleep-interval=0", addIntoBootArgs=True, useDefaultBootArgs=False)

    def EnableWow(self, dut=False, disableChargeWake=True, forceSetupAPSTester=True):
        '''
        Enable WoW
        '''
        result = False
        dut = self.__GetDut(dut)
        if disableChargeWake:
            if not self.DisableChargeWake(dut=dut):
                self.logger.Warning('Failed to disable charge wake by adding boot-args')
        try:
            if self.dutControl.VerifyWoWEnabled():
                # Still enable APSTester to let it running at the background
                if forceSetupAPSTester:
                    self.SetupAPSTester(dut, forceSetup=False)
                result = True
            else:
                dut.logger.Info("Check for boot-args first...")
                ModifyBootArgs(dut, "bcom.feature.wow=0", False, useDefaultBootArgs=False)
                dut.TurnOnAirplaneMode()
                dut.getWiFi().On()
                Sleep(5, self.logger, msg='Wait for 5 seconds for Wow get enabled after Airplane mode enabled')
                if self.SetupAPSTester(dut, forceSetup=True):
                    if self.dutControl.VerifyWoWEnabled():
                        result = True
                else:
                    if self.dutControl.VerifyWoWEnabled():
                        result = True
                        dut.logger.Info("WoW is enabled on the device")
                    else:
                        dut.logger.Info("WoW is not enabled because failed to setup APSTester")
                if not result:
                    Sleep(30, self.logger, msg='Wait for 30 seconds for Wow get enabled after turn on APSTester')
                    if self.dutControl.VerifyWoWEnabled():
                        result = True
                    else:
                        Sleep(30, self.logger, msg='Wait for 30 seconds for Wow get enabled after turn on APSTester')
                        if self.dutControl.VerifyWoWEnabled():
                            result = True
        except TestFailError as e:
            dut.logger.Fail("Failed to enable Wow because: %s", e)

        return result

    def DisableWow(self, disableWowUseBootArgs=False, unloadPushPlist=True, dut=False, logFail=True):
        '''
        Disable WoW on the device, if disableWowUseBootArgs set as True, it will use boot-args 
        to disable WoW and reboot device
        will Unload Push Plist if unloadPushPlist set as True
        '''
        try:
            dut = self.__GetDut(dut)
            if not self.dutControl.VerifyWoWEnabled():
                return True

            if disableWowUseBootArgs:
                if ModifyBootArgs(dut, "bcom.feature.wow=0", True, useDefaultBootArgs=False):
                    if not self.dutControl.VerifyWoWEnabled():
                        self.logger.Info("Disable WoW Pass")
                        return True
                    else:
                        self.logger.Info("Failed to use boot-args to disable Wow")
                else:
                    self.logger.Info("Failed to set boot-args to disable Wow")

                return False

            if unloadPushPlist:
                if self.dutControl.utils.UnloadPushPlist(logFail=logFail):
                    if not self.dutControl.VerifyWoWEnabled():
                        self.logger.Info("Disable WoW Pass")
                        return True
                    else:
                        if logFail:
                            self.logger.Fail("Failed to disable Wow  after unload push plist")
                        else:
                            self.logger.Warning("Failed to disable Wow  after unload push plist")
                        return False

            self.logger.Info("WoW is enabled, try to disable it by turning off Airplane mode and killing APSTester...")
            dut.getOS().Execute('killall APSTester', True)

            if dut.GetAirplaneMode():
                dut.TurnOffAirplaneMode()

            self.logger.Info("Turn WiFi Off to force data passing through Cellular ")
            dut.getWiFi().Off()
            Sleep(10, self.logger, msg='Wait for 10 seconds for cellular data becomes active')
            dutPing = Ping(dut)
            dutPing.Ping(dut=dut, dest="www.google.com", count=10, timeout=10, verbose=False)
            dut.getWiFi().On()
            Sleep(60, self.logger, msg='Wait for 60 seconds for WoW get disabled after Airplane mode off')

            if not self.dutControl.VerifyWoWEnabled():
                return True
            else:
                self.logger.Info("WoW is enabled, try to turn off Mail Push...")
                if self.dutControl.uiControl.TurnOffMailPushUI():
                    Sleep(60, self.logger, msg='Wait for 60 seconds for WoW get disabled after turn off Mail Push')
                    if not self.dutControl.VerifyWoWEnabled():
                        return True

            self.logger.Info("WoW is enabled, try to disable it by killing FaceTime and Mail...")
            if self.dutControl.uiControl.SpringBoardQuitAppUI("com.apple.mobilephone") or self.dutControl.uiControl.SpringBoardQuitAppUI("com.apple.mobilemail") or self.dutControl.uiControl.SetFaceTimeUI(enable=False):
                Sleep(60, self.logger, msg='Wait for 60 seconds for WoW get disabled after kill Mail or Facetime')
                if not self.dutControl.VerifyWoWEnabled():
                    return True

            Sleep(60, self.logger, msg='Wait for another 60 seconds for WoW get disabled')
            if not self.dutControl.VerifyWoWEnabled():
                return True

            self.logger.Info("Failed to disable WoW")
        except TestFailError as e:
            if logFail:
                self.logger.Fail("Failed to disable Wow because: %s", e)
            else:
                self.logger.Warning("Failed to disable Wow because: %s", e)

        return False

    def SetupAPSTester(self, dut=False, forceSetup=False):
        '''
        Launch APSTester
        '''
        dut = self.__GetDut(dut)
        if forceSetup == False:
            result = dut.getOS().Execute('ps -A |grep APSTester')
            if result is not None:
                if result.standardOutput.find("/AppleInternal/Applications/APSTester.app/APSTester") > 0:
                    dut.logger.Pass("APSTester is already running")
                    return True
                else:
                    dut.logger.Info("Setup APSTester through UI...")
        try:
            dut.getWiFi().SetupAPSTester()
        except Exception as e:
            dut.logger.Info("Failed to set up APSTester, error: %s", e)
            return False

        return True

    def PnoProbReqScanTime(self, packetList, ssid='', passCount=2, passInterval=45, allowOtherSSID=False, logFail=True):
        '''
        Calculate PNO scan interval.
        packetList: List returned by WiFiPacketParse.ParseProbReq()
        ssid: ssid showed in the prob req, default is broadcast: ''
        passCount: Count to find match prob interval
        passInterval: scan interval
        allowOtherSSID: ignor other ssid (not given ssid) showed up in the prob req
        logFail: log fail if fail
        '''
        probPassResult = False
        if packetList is None:
            self.logger.Fail("Failed to get output dictionary")
            return False
        pkts = list(enumerate(packetList))
        # print pkts
        probPass = 0
        currentProb = None
        nextProb = None
        for i in range (0, len(pkts) - 1):
            pktNumber = pkts[i][1].GetPacketNumber()
            if pkts[i][1].VerifyProbReqSSID(ssid=ssid):
                self.logger.Pass("Prob Req (number %d) shows SSID as %s", pktNumber, ssid)
                if currentProb is None:
                    currentProb = pkts[i]
                else:
                    nextProb = pkts[i]
            else:
                if not allowOtherSSID and logFail:
                    self.logger.Fail("Prob Req (number %d) does not show SSID as %s, skip checking this prob", pktNumber, ssid)
                elif allowOtherSSID:
                    self.logger.Debug("Prob Req (number %d) does not show SSID as %s, skip checking this prob", pktNumber, ssid)
                else:
                    self.logger.Warning("Prob Req (number %d) does not show SSID as %s, skip checking this prob", pktNumber, ssid)
                continue
            if currentProb is None or nextProb is None:
                continue
            pktNumber = currentProb[1].GetPacketNumber()
            pktTime = currentProb[1].GetFrameTime()
            nextPktTime = nextProb[1].GetFrameTime()
            nextPktNumber = nextProb[1].GetPacketNumber()
            timeDiff = nextPktTime - pktTime
            if timeDiff.seconds >= passInterval and timeDiff.seconds <= passInterval + 1:
                self.logger.Pass("Probe Requests (number %d and %d) happened every 45 seconds: %s", pktNumber, nextPktNumber, timeDiff)
                currentProb = nextProb
                probPass = probPass + 1
            elif timeDiff.seconds >= passInterval - 1 and timeDiff.seconds < passInterval:
                self.logger.Pass("Probe Requests (number %d and %d) happened every 45 seconds: %s", pktNumber, nextPktNumber, timeDiff)
                currentProb = nextProb
                probPass = probPass + 1
            elif timeDiff.seconds < 1:
                if nextProb[1].IsRetry():
                    self.logger.Info("Probe Requests (number %d and %d) happened within 1 second: %s, it is a retry", pktNumber, nextPktNumber, timeDiff)
                else:
                    self.logger.Warning("Probe Requests (number %d and %d) happened within 1 second: %s, it is not a retry", pktNumber, nextPktNumber, timeDiff)
            else:
                if nextProb[1].IsRetry():
                    self.logger.Warning("Probe Requests (number %d and %d) happened with %s second, it is a retry", pktNumber, nextPktNumber, timeDiff)
                else:
                    if logFail:
                        self.logger.Fail("Probe Requests (number %d and %d) happened with %s second, and it is not a retry", pktNumber, nextPktNumber, timeDiff)
                    else:
                        self.logger.Warning("Probe Requests (number %d and %d) happened with %s second, and it is not a retry", pktNumber, nextPktNumber, timeDiff)
            if probPass == passCount:
                self.logger.Pass("45 sec apart prob request happened %d", passCount)
                probPassResult = True
                break
        if not probPassResult:
            self.logger.Fail("Failed to find %d 45 sec apart prob request", passCount)
            # print pkts[i]

    def VerifyPnoProbReqSSID(self, packetList, ssid='', allowOtherSSID=False, logFail=True):
        '''
        Verify Pno ProbeReqSSID.
        packetList: List returned by WiFiPacketParse.ParseProbReq()
        ssid: ssid showed in the prob req, default is broadcast: ''
        logFail: log fail if fail
        '''
        probPassResult = False
        if packetList is None:
            self.logger.Fail("Failed to get output dictionary")
            return False
        pkts = list(enumerate(packetList))
        if len(pkts) == 0:
            if logFail:
                self.logger.Fail("no required prob packet saved in this xml")
            else:
                self.logger.Warning("no required prob packet saved in this xml")
            return False
        # print pkts
        for i in range (0, len(pkts) - 1):
            pktNumber = pkts[i][1].GetPacketNumber()
            if pkts[i][1].VerifyProbReqSSID(ssid=ssid):
                self.logger.Pass("Prob Req (number %d) shows SSID as %s", pktNumber, ssid)
                probPassResult = True
            else:
                if not allowOtherSSID and logFail:
                    self.logger.Fail("Prob Req (number %d) does not show SSID as %s", pktNumber, ssid)
                elif allowOtherSSID:
                    self.logger.Debug("Prob Req (number %d) does not show SSID as %s", pktNumber, ssid)
                else:
                    self.logger.Warning("Prob Req (number %d) does not show SSID as %s", pktNumber, ssid)
        return probPassResult


    def VerifyListenInterval(self, packetList, interval=20, logFail=True):
        '''
        Verify Listen Interval.
        packetList: List returned by WiFiPacketParse.ParseAssocReq()
        interval: Listen Interval
        logFail: log fail if fail
        '''
        passResult = True
        if packetList is None:
            self.logger.Fail("Failed to get output dictionary")
            return False
        pkts = list(enumerate(packetList))
        if len(pkts) == 0:
            if logFail:
                self.logger.Fail("no required packet saved in this xml")
            else:
                self.logger.Warning("no required packet saved in this xml")
            return False
        # print pkts
        for i in range (0, len(pkts)):
            pktNumber = pkts[i][1].GetPacketNumber()
            print pkts[i][1].GetListenInterval()
            currentInterval = pkts[i][1].GetListenInterval()
            if currentInterval == interval:
                self.logger.Pass("packet (number %d) Listen Interval is %s", pktNumber, interval)
            else:
                if logFail:
                    self.logger.Fail("packet (number %d) Listen Interval is not %s, it is %s", pktNumber, interval, currentInterval)
                else:
                    self.logger.Warning("packet (number %d) Listen Interval is not %s, it is %s", pktNumber, interval, currentInterval)
                passResult = False
        return passResult


    def VerifyArpResp(self, packetList, logFail=True):
        '''
        Verify Arp Response showed up.
        packetList: List returned by WiFiPacketParse.ParseArpResp()
        logFail: log fail if fail
        '''
        if packetList is None:
            self.logger.Fail("Failed to get output dictionary")
            return False
        pkts = list(enumerate(packetList))
        if len(pkts) == 0:
            if logFail:
                self.logger.Fail("no required packet saved in this xml")
            else:
                self.logger.Warning("no required packet saved in this xml")
            return False
        # print pkts
        for i in range (0, len(pkts)):
            pktNumber = pkts[i][1].GetPacketNumber()
            self.logger.Pass("packet (number %d) is Arp Response", pktNumber)
            return True

        if logFail:
            self.logger.Fail("Did not find any Arp Response")
        else:
            self.logger.Warning("Did not find any Arp Response")


    def __GetDut(self, dut=False):
        '''
        internal function to validate dut
        '''
        if not dut:
            dut = self.dut
        return dut

