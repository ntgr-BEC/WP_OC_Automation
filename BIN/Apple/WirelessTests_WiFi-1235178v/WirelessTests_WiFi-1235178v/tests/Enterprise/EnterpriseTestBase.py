'''
"""
Enterprise

Created by Dani Gleser on 2013-04-03
Copyright (c) 2013 Apple. All rights reserved.
"""
import WirelessAutomation as waf
from WiFi.Control import Control
from WiFi.LogParse import WiFiLogs
from common.ConfigurationInfo import Config, ConfigurationInfo



#----------------------------------------------------------------------
#
#----------------------------------------------------------------------

class EnterpriseTestBase(waf.WiFiTest):
    """ Base class for Enterprise Tests """

    def __init__(self):
        super(EnterpriseTestBase, self).__init__()
        self.setRequirements(dut=waf.WiFiDevice)
        self.ctrl = None
        self.apconfigs = []
        self.testName = self.__class__.__name__

    def setup(self):
        super(EnterpriseTestBase, self).setup(unlockDevices=False, getToHome=False)
        self.SetConfig(Config.__dict__)
        self.dut.wifi.ForgetAll()
        self.dut.getOS().UninstallAllProfiles()
        self.ctrl = Control(self.dut)
        self.logs = WiFiLogs(self.dut, self.name)
        self.logs.EnableAllLogging()
        self.logs.StartSyslog()

    def teardown(self):
        super(EnterpriseTestBase, self).teardown(unlockDevices=False)
        self.dut.wifi.ForgetAll()
        self.dut.getOS().UninstallAllProfiles()

    def SetConfig(self, cfg):
        """ Set config input as dictionary or ConfigurationInfo """
        if isinstance(cfg, ConfigurationInfo):
            cfg = cfg.__dict__
        print "CFG: %s" % cfg
        self.testName = cfg.get('TestName', self.testName)

#----------------------------------------------------------------------
#  MAIN
#----------------------------------------------------------------------

if __name__ == '__main__':
    pass  # TODO

'''

# !/usr/bin/env
#
#  EnterpriseOnOffStress.py
#  WiFi Enterprise WirelessAutomation
#
#  This test is a stress test for wifi on and off.
#
#  Created by Dani Gleser on 03/14/13.
#  Copyright (c) 2013 Apple. All rights reserved.
#
# import pdb
import os, smtplib

from common.WirelessTest import *

from common.ConfigurationInfo import Config
from common.OSCommon import ProfileInstallRequest
WiFiTest().AddPath()
from WiFi.Stress import WiFiStress
from WiFi.LogParse import *
from LogInit import LogIt
from WiFi.Stress import WiFiStress
from plistlib import readPlist
from HostTools.Mailer import Mailer


# from WiFi_Common import WiFi_Common


class EnterpriseTestBase(WiFiTest):

    def __init__(self):
        self.setRequirements(dut=WiFiDevice)
    def setup(self):
        WirelessTest.setup(self, unlockDevices=False)
        self.wifistress = WiFiStress(self.dut)
        self.wifistress.dutControl.traffic.SetDestination('http://www.gooogle.com')
        self.rootpath = os.path.dirname(os.path.abspath(__file__))
        self.logger = LogIt()
        self.tests = readPlist('%s/%s' % (Config.EnterpriseSetup['TestStorage'], 'EnterpriseOnOffStress.plist'))
        self.policyStore = '%s/%s' % (os.getcwd(), Config.EnterpriseSetup['PolicyStorage'])
        self.web_destination = Config.EnterpriseSetup['WebDestination']
        # Email
        if Config.EnterpriseSetup["sendemail"]:
            self.emailserver = Config.EnterpriseSetup["email"]["server"]
            self.emailsubject = Config.EnterpriseSetup["email"]["subject"]
            self.Email = Mailer(sender=Config.EnterpriseSetup["email"]["emailfrom"], destination=Config.EnterpriseSetup["email"]["to"], server=Config.EnterpriseSetup["email"]["server"])
            self.sendmail = Config.EnterpriseSetup["sendemail"]
        self.testResults = []
        self.hardwarelist = ""
        self.buildnumber = ""
        self.Cycles = Config.EnterpriseSetup['StressCycles'] + 1
        try:
            self.sleepTime = Config.EnterpriseSetup['SleepWakeStressSleepTime']
        except:
            self.sleepTime = 10
            self.logger.Warning("Sleep Wake Stress Sleep Timer is missing from config plist. Setting to default of 10 seconds")
        self.totalTests = 0
        if len(self.tests) < 1:
            raise TestFailError, TestFailError('You need at least one test configured in PolicyTests in the configuration file.')
        # Setup Login
        self.logpathroot = self.logger.getTestLogsPath()
        self.testFail = 0
        self.testPass = 0
        self.testPassTotal = 0
        self.testFailTotal = 0
        self.Vendor = Config.EnterpriseSetup['Vendor']
        self.TestName = Config.EnterpriseSetup['TestName']
        self.PingDestination = Config.EnterpriseSetup['PingDestination']
        self.WebDestination = Config.EnterpriseSetup['WebDestination']


    def sendEmail(self):
        emailbody = "\n"
        for results in self.testResults:
            self.logger.Info(results)
            emailbody += results
            emailbody += "\n"
        self.emailsubject += "-%s- Build -%s- Tests Passed %i - Tests Failed %i" % (self.hardwarelist, self.buildnumber, self.testPassTotal, self.testFailTotal)
        self.Email.Send(subject=self.emailsubject, body=emailbody)

    def verifyEAPType(self, myDut, expectedEAPType, TestName):
        try:
            eapType = myDut.GetEAPState(self.dut)
            self.logger.Info("verifyEAPType - Got EAP type: %s" % eapType)
        except:
            self.logger.Fail("verifyEAPType - Failed to get EAP Type from Dut")
            self.testResults.append("%s - Test: Failed to get EAP Type from devcie" % TestName)
        if eapType['EAPType'] == expectedEAPType:
            self.logger.Info("verifyEAPType - EAP Types Matched for type: %s" % eapType['EAPType'])
            return True
        else:
            self.logger.Fail('verifyEAPType - %s - Test: Failed to match EAP Type - Expected: %s and Got: %s' % (TestName, expectedEAPType, eapType['EAPType']))
            self.testResults.append('%s - Test: Failed to match EAP Type - Expected: %s and Got: %s' % (TestName, expectedEAPType, eapType['EAPType']))
        return False

    def teardown(self):
        matrixname = " Test Results Matrix "
        self.logger.Info("%s", matrixname.center(60, '='))
        for results in self.testResults:
            self.logger.Info(results)
        if self.testFail > 0:
            raise TestFailError, TestFailError('Failed at least one Enterprise WiFi test, please see logs.')
        # Disable eapolclient logging
        WiFiTest.teardown(self, unlockDevices=False)
        self.dut.getWiFi().DisableEapolclientLogging()
        self.wifistress.logParser.DisableAllLogs()
        self.dut.getOS().UninstallAllProfiles()
        self.dut.getWiFi().ForgetAll()

if __name__ == '__main__':
    pass
