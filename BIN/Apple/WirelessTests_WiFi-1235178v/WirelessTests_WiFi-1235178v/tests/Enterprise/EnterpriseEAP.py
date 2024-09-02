#!/usr/bin/env
#
#  EnterpriseEAP.py
#  WiFi Enterprise WirelessAutomation
#
#  This test is the base for Enterprise WiFi testing.
#
#  Created by Quint Friesen on 11/29/11.
#  Copyright (c) 2011 Apple. All rights reserved.
#
#import pdb
import time, os, sys, plistlib, smtplib, socket
from email.mime.text import MIMEText
from common.WirelessException import *
from common.WirelessTest import *
from common.WirelessCommon import *
from common.ConfigurationInfo import Config
from common.OSCommon import ProfileInstallRequest
from common.WiFiCommon import WiFiNetwork
from common.CiscoCommon import CiscoCommon
from common.iPCUCommon import iPCUProfile
from common.Scripter import interact
from common.CiscoCommon import CiscoCommon
WiFiTest().AddPath()
from WiFi.LogParse import *
from LogInit import LogIt
from WiFi.Stress import WiFiStress
from plistlib import readPlist
from HostTools.WiFiControl import ProfileControl


class EnterpriseEAP(WiFiTest):

    def __init__(self):
        self.setRequirements(dut=WiFiDevice)
        self.testResults = []

    def setup(self):
        self.dutList = [self.dut]
        for dut in self.dutList:
            self.dut = dut
            WirelessTest.setup(self, unlockDevices=False)
            self.wifistress = WiFiStress(dut)
            self.wifistress.dutControl.traffic.SetDestination('http://www.google.com')
        self.tests = readPlist('%s/%s' % (Config.EnterpriseSetup['TestStorage'], 'EnterpriseEAP.plist'))
        self.policyStore = '%s/%s' % (os.getcwd(), Config.EnterpriseSetup['PolicyStorage'])
        self.web_destination = Config.EnterpriseSetup['WebDestination']
        #Sniffer settings:
        self.snifferCaptureTime = 300
        self.enableSniffer = False
        self.channel = 1
        #Email
        if Config.EnterpriseSetup["sendemail"]:
            self.emailserver = Config.EnterpriseSetup["email"]["server"]
            self.emailfrom = Config.EnterpriseSetup["email"]["emailfrom"]
            self.emailto = Config.EnterpriseSetup["email"]["to"]
            self.emailsubject = Config.EnterpriseSetup["email"]["subject"]
        self.hardwarelist = ""
        self.buildnumber = ""
        self.HTTPDownloadDest = Config.EnterpriseSetup["HTTPDownload"]
        self.PingDest = Config.EnterpriseSetup["PingDestination"]
        if len(self.tests) < 1:
                raise TestFailError, TestFailError('You need at least one test configured in PolicyTests in the configuration file.')
        #Setup Logging
        self.logger = LogIt()
        self.logger.Info("abs path: %s" % os.path.dirname(os.path.abspath(__file__)))
        self.logpathroot = self.logger.getTestLogsPath()
        self.logger.Info("================\nTest log path: %s\n=================\n" % self.logpathroot)
        self.testFail = 0
        self.testPass = 0
        self.testPassTotal = 0
        self.testFailTotal = 0
        self.Vendor_Array = Config.EnterpriseSetup['Vendor']
        self.logger.Debug("Vendor Array: %s " % self.Vendor_Array)
        self.vendorList = ""
        self.testResults = []

    def run(self):
        # Join the WiFi network
        for dut in self.dutList:
            #Setup logging
            self.dut = dut
            self.logger.Info('Starting Test: Enterprise QL')
            self.testname = "Enterprise QL Test: for %s " % dut.hardware
            self.logger.Info('%s: Removing any profiles we find on the device', self.testname)
            self.dut.getOS().UninstallAllProfiles()
            self.dut.getWiFi().ForgetAll()
            self.hardwarelist += self.dut.hardware
            self.buildnumber = self.dut.build
            self.testResults.append("\n------------------------------------------------------------\n")
            self.testResults.append("Device: %s" % self.dut.hardware)
            self.testResults.append("Build: %s" % self.dut.build)
            self.testResults.append("UDID: %s" % self.dut.udid)
            self.testResults.append("Serial Number: %s" % self.dut.serial_number)
            self.testResults.append("Device Name: %s" % self.dut.name)
            self.testResults.append("\n")
            for Vendor in self.Vendor_Array:
                self.dut.getOS().UninstallAllProfiles()
                self.dut.getWiFi().ForgetAll()
                self.testResults.append("\n%s \n\n" % Vendor.center(60, '-'))
                testlist = []
                if Config.EnterpriseSetup["SingleTest"]:
                    testlist.append(Config.EnterpriseSetup["SingleTestName"])
                else:
                    for testtmp in self.tests[Vendor]:
                        testlist.append(testtmp)
                for test in testlist:
                    self.passfail = True
                    self.testResults.append('Test Name: %s' % test)
                    #Clear logs, profiles and wifi networks
                    self.dut.getWiFi().ForgetAll()
                    self.dut.getOS().UninstallAllProfiles()
                    #start logging
                    self.wifistress.logParser.EnableAllLogging()
                    self.testcase = self.testname + " - " + Vendor + " - " + test
                    self.logger.Info('%s: Begin test', self.testcase)
                    self.logger.Info('%s: Install Dynamically created Profile: %s ' % (self.testcase, self.tests[Vendor][test]['Profile']['path']))
                    testprofile = self.tests[Vendor][test]['Profile']
                    profile = ProfileInstallRequest(testprofile)
                    logpath = self.logger.getTestLogsPath(self.dut)
                    print("Log Path: %s" % logpath)
                    self.dut.getOS().InstallProfile(profile)
                    self.logger.Info('%s: Validate WiFi profile installed', self.testcase)
                    print ("Accesspoint: %s" % self.tests[Vendor][test]['accesspoint'])
                    ap = WiFiNetwork(self.tests[Vendor][test]['accesspoint'])
                    self.wifistress.dutControl.IsNetworkInAutoJoinList(ap)
                    self.logger.Info("Sleeping for 10 seconds so that the network can Join")
                    Sleep(10, self.logger)
                    autojoin = False
                    forcejoin = False
                    if self.wifistress.dutControl.VerifyConnectionAndPing(ap, self.PingDest):
                        self.logger.Info("Verified Auto Join Passed")
                        self.testResults.append('Test: %s-AutoJoin - Pass' % self.testcase)
                        autojoin = True
                    else:
                        self.logger.Fail('Did not connect to AP after Verify Auto Join: %s' % ap.ssid)
                        self.testResults.append('Test: %s-AutoJoin - Failed' % self.testcase)
                        self.passfail = False
                        self.logger.Info("Since AutoJoin Failed - Forcing join")
                        try:
                            self.dut.getWiFi().Join(ap)
                            self.logger.Info("Sleeping for 10 seconds so that the network can Join")
                            Sleep(10, self.logger)
                            if self.wifistress.dutControl.VerifyConnectionAndPing(ap, self.PingDest):
                                self.logger.Info("Force Connection to AP Passed")
                                self.testResults.append('Test: %s-ForcedConnection - Passed' % self.testcase)
                                forcejoin = True
                            else:
                                self.logger.Fail('Did not connect to AP after Force Connection: %s' % ap.ssid)
                                self.testResults.append('Test: %s-ForcedConnection - Failed' % self.testcase)
                                self.passfail = False
                                forcejoin = False
                        except:
                            self.logger.Fail("Failed to select ssid: %s " % ap.ssid)
                            self.testResults.append('Test: %s-ForcedConnection - Failed' % self.testcase)
                            self.passfail = False
                            forcejoin = False
                    if autojoin or (not autojoin and forcejoin):
                        if self.tests[Vendor][test]['accesspoint']['isEap']:
                            if self.wifistress.dutControl.VerifyEAPType(self.tests[Vendor][test]['TestOptions']['ExpectedEAPType']):
                                self.logger.Info("EAP Type Matches")
                                self.testResults.append('Test: %s-VerifyEAPType - Passed' % self.testcase)
                            else:
                                self.logger.Fail('Test: %s-VerifyEAPType - Failed' % self.testcase)
                                self.testResults.append('Test: %s-VerifyEAPType - Failed' % self.testcase)
                                self.passfail = False
                        else:
                            self.logger.Info("Skipping EAP Verification")
                        if self.wifistress.dutControl.traffic.HTTPDownloadTest(dest=self.HTTPDownloadDest):
                            self.logger.Pass('Got the %s web page!' % self.HTTPDownloadDest)
                            self.testResults.append('Test: %s-HTTPDownload - Passed' % self.testcase)
                        else:
                            self.testResults.append('Test: %s-HTTPDownload - Failed' % self.testcase)
                            self.logger.Fail('Did not get the %s web page!' % self.HTTPDownloadDest)
                            self.passfail = False
                        #WiFi On Off
                        self.wifistress.WiFiOffOn()
                        #Check the network after WiFiOffOn
                        self.logger.Info("Sleeping for 10 seconds so that the network can Join")
                        Sleep(10, self.logger)
                        if self.wifistress.dutControl.VerifyConnectionAndPing(ap, self.PingDest):
                            self.logger.Info("WiFi Off On Passed")
                            self.testResults.append('Test: %s-WiFiOffOn - Passed' % self.testcase)
                        else:
                            self.testResults.append('Test: %s-WiFiOffOn - Failed' % self.testcase)
                            self.logger.Fail('Did not connect to AP after WiFi OFF On: %s' % ap.ssid)
                            self.passfail = False
                    if self.passfail:
                        self.testPass = self.testPass + 1
                        self.testResults.append('Overall Test: %s - Pass' % self.testcase)
                    else:
                        self.testFail = self.testFail + 1
                        self.testResults.append('Overall Test: %s - Fail' % self.testcase)
                    # Disable logging
                    self.wifistress.logParser.SaveAllLogsWithTestName(self.testcase)
                    self.wifistress.logParser.DisableAllLogs()
            resulttext = "Results"
            self.testResults.append("\n\n%s \n" % resulttext.center(60, '='))
            self.testResults.append('%s tests Passed' % self.testPass)
            self.testResults.append('%s tests Failed' % self.testFail)
            self.logger.SetTestProperty("configurationtag", " %s - Pass: %s  - Fail: %s" % (self.vendorList, self.testPass, self.testFail))
            self.testPassTotal += self.testPass
            self.testFailTotal += self.testFail
            self.testPass = 0
            self.testFail = 0
        self.testResults.append("\nLog files located on system: %s at %s" % (socket.gethostname() , self.logger.getTestLogsPath()))
        if Config.EnterpriseSetup["sendemail"]:
            self.sendEmail()

    def sendEmail(self):
        body = "\n"
        for results in self.testResults:
            self.logger.Info(results)
            body += results
            body += "\n"
        self.emailsubject += "-%s- Build -%s- Tests Passed %i - Tests Failed %i" % (self.hardwarelist, self.buildnumber, self.testPassTotal, self.testFailTotal)
        msg = MIMEText(body)
        msg['Subject'] = self.emailsubject
        msg['From'] = self.emailfrom
        msg['To'] = self.emailto
        server = smtplib.SMTP(self.emailserver)
        server.sendmail(self.emailfrom, self.emailto, msg.as_string())
        server.quit()

    def GetNamedDict(self, name=None, searchList=None):
        if name is not None and searchList is not None:
            print('GetNameDic name: %s' % name)
            print('GetNameDic searchList: %s' % searchList)
            for searchDict in searchList:
                if searchDict['name'] == name:
                    return searchDict
        return None

    def StartLogging(self, testlogdir):
        self.logger.Info("Starting logging")

    def StopLogging(self, testlogdir):
        self.logger.Info("Stopping logging")

    def teardown(self):
        matrixname = " Test Results Matrix "
        self.logger.Info("%s", matrixname.center(60, '='))
        for results in self.testResults:
            self.logger.Info(results)
        if self.testFail > 0:
            raise TestFailError, TestFailError('Failed at least one Enterprise WiFi test, please see logs.')
        # Disable logging
        WirelessTest.teardown(self, unlockDevices=False)
        #self.dut.getWiFi().DisableEapolclientLogging()
        #self.wifistress.logParser.DisableAllLogs()
        self.dut.getOS().UninstallAllProfiles()
        self.dut.getWiFi().ForgetAll()
        self.logger.Info("Disabling AP's")


if __name__ == '__main__':
    pass
