#!/usr/bin/env python
#-----------------------------------------------------------------------
# Copyright (c) 2012 Apple
# All Rights Reserved
#
# Proprietary and Confidential
#-----------------------------------------------------------------------

""" Provides HS2.0 functions """
# Internal modules
from WiFi.Control import Control
from LogInit import LogIt

class HS20(object):
    '''
    Class for performing common functions on HS2.0 devices
    '''
    def __init__(self, dut):
        self.logger = LogIt()
        self.dut = dut
        self.dutControl = Control(self.dut)
        if not self.dutControl.wl.SupportHS20():
            self.logger.Info("HS2.0 is not supported on this hardware.")

    def SetDut(self, dut):
        self.dut = dut

    def VerifyDomainName(self, domainNameList, bssid, channel):
        '''
        Verify if the DomainName responsed from the AP is included in the DomainNameList
        '''
        self.logger.Info("Initiating GAS scan for NetworkParameterDomainName")
        result = self.dut.getWiFi().GetNetworkParameterDomainName(bssid, channel)
        if result:
            self.logger.Pass(u"GAS Scan on {0} using channel {1} for NetworkParameterDomainName: {2}:\n".format(bssid, channel, result))
            for element in domainNameList:
                if element in result:
                    self.logger.Pass(u"Found expected element {} in GAS query result.".format(element))
                else:
                    self.logger.Fail(u"Could not find expected element {} in GAS query result.".format(element))
        else:
            self.logger.Fail(u"GAS Scan on {0} using channel {1} for NetworkParameterDomainName failed.".format(bssid, channel))

    def VerifyMCCMNC(self, MCCMNCList, bssid, channel):
        '''
        Verify if the MCCMNC responsed from the AP is included in the MCCMNC
        '''
        self.logger.Info("Initiating GAS scan for MCCMNC")
        result = self.dut.getWiFi().GetMCCMNC(bssid, channel)
        if result:
            self.logger.Pass(u"GAS Scan on {0} using channel {1} for MCCMNC: {2}:\n".format(bssid, channel, result))
            for element in MCCMNCList:
                if element in result:
                    self.logger.Pass(u"Found expected element {} in GAS query result.".format(element))
                else:
                    self.logger.Fail(u"Could not find expected element {} in GAS query result.".format(element))
        else:
            self.logger.Fail(u"GAS Scan on {0} using channel {1} for MCCMNC failed.".format(bssid, channel))

    def VerifyNAIRealm(self, NAIRealmList, bssid, channel):
        '''
        Verify if the NAIRealm responsed from the AP is included in the NAIRealm
        '''
        self.logger.Info("Initiating GAS scan for NAIRealm")
        result = self.dut.getWiFi().GetNAIRealm(bssid, channel)
        if result:
            self.logger.Pass(u"GAS Scan on {0} using channel {1} for NAIRealm: {2}:\n".format(bssid, channel, result))
            for element in NAIRealmList:
                if element in result:
                    self.logger.Pass(u"Found expected element {} in GAS query result.".format(element))
                else:
                    self.logger.Fail(u"Could not find expected element {} in GAS query result.".format(element))
        else:
            self.logger.Fail(u"GAS Scan on {0} using channel {1} for NAIRealm failed.".format(bssid, channel))

    def VerifyRoamingConsortium(self, roamingConsortiumList, bssid, channel):
        '''
        Verify if the RoamingConsortium responsed from the AP is included in the RoamingConsortium
        '''
        self.logger.Info("Initiating GAS scan for RoamingConsortium")
        result = self.dut.getWiFi().GetRoamingConsortium(bssid, channel)
        if result:
            self.logger.Pass(u"GAS Scan on {0} using channel {1} for RoamingConsortium: {2}:\n".format(bssid, channel, result))
            for element in roamingConsortiumList:
                if element in result:
                    self.logger.Pass(u"Found expected element {} in GAS query result.".format(element))
                else:
                    self.logger.Fail(u"Could not find expected element {} in GAS query result.".format(element))
        else:
            self.logger.Fail(u"GAS Scan on {0} using channel {1} for RoamingConsortium failed.".format(bssid, channel))

