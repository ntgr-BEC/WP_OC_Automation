# !/usr/bin/env
#
#  EnterpriseSleepWakeStress.py
#  WiFi Enterprise WirelessAutomation
#
#  This test is a stress test for wifi on and off.
#
#  Created by Dani Gleser on 03/14/13.
#  Copyright (c) 2013 Apple. All rights reserved.
#
from common.WirelessTest import *
from common.OSCommon import ProfileInstallRequest
WiFiTest().AddPath()
from EnterpriseTestBase import EnterpriseTestBase



class EnterpriseSleepWakeStress(EnterpriseTestBase):


    def run(self):
        self.logger.Info('Starting Test: Enterprise On Off Stress')
        self.testname = "Enterprise On Off Stress Test: for %s " % self.dut.hardware
        self.logger.Info('%s: Removing any profiles we find on the device', self.TestName)
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

        self.dut.getOS().UninstallAllProfiles()
        self.dut.getWiFi().ForgetAll()
        self.testTittle = self.tests[self.Vendor]
        self.dut.getWiFi().ForgetAll()
        self.dut.getOS().UninstallAllProfiles()
        # start logging
        self.wifistress.logParser.EnableAllLogging()
        self.testTittle = self.TestName + " - " + self.Vendor
        self.logger.Info('%s: Install Dynamically created Profile: %s ' % (self.testTittle, self.tests[self.Vendor][self.TestName]['Profile']['path']))
        testprofile = self.tests[self.Vendor][self.TestName]['Profile']
        profile = ProfileInstallRequest(testprofile)
        logpath = self.logger.getTestLogsPath(self.dut)
        self.logger.Debug("Log Path: %s" % logpath)
        self.dut.getOS().InstallProfile(profile)
        self.logger.Info('%s: Validate WiFi profile installed', self.testTittle)
        self.logger.Debug ("Accesspoint: %s" % self.tests[self.Vendor][self.TestName]['accesspoint'])
        ap = WiFiNetwork(self.tests[self.Vendor][self.TestName]['accesspoint'])
        self.wifistress.dutControl.IsNetworkInAutoJoinList(ap)
        self.logger.Info("Sleeping for 10 seconds so that the network can Join")
        Sleep(10, self.logger)
        if self.wifistress.dutControl.VerifyConnectionAndPing(ap, self.web_destination):
            self.logger.Info("Verified Auto Join Passed")
            self.testResults.append('Test: %s-AutoJoin - Pass' % self.testTittle)
        else:
            self.logger.Fail('Did not connect to AP after Verify Auto Join: %s' % ap.ssid)
            self.testResults.append('Test: %s-AutoJoin - Failed' % self.testTittle)
            self.logger.Info("Since AutoJoin Failed - Forcing join")
            try:
                self.dut.getWiFi().Join(ap)
                self.logger.Info("Sleeping for 10 seconds so that the network can Join")
                Sleep(10, self.logger)
                if self.wifistress.dutControl.VerifyConnectionAndPing(ap, self.PingDestination):
                    self.logger.Info("Force Connection to AP Passed")
                    self.testResults.append('Test: %s-ForcedConnection - Passed' % self.testTittle)
                else:
                    self.logger.Fail('Did not connect to AP after Force Connection: %s' % ap.ssid)
                    self.testResults.append('Test: %s-ForcedConnection - Failed' % self.testTittle)
            except:
                self.logger.Fail("Failed to select ssid: %s " % ap.ssid)
                self.testResults.append('Test: %s-ForcedConnection - Failed' % self.testTittle)
                raise TestFailError, TestFailError('Forced Connection Failed')
        self.wifistress.logParser.SaveAllLogsWithTestName(self.testTittle + " - AutoJoin")
        for loopCount in range(1, self.Cycles):
            self.totalTests += 1
            testFail = True
            self.wifistress.logParser.CleanAllLogs()
            self.logger.Info("Log cleanup and sleep for 5 seconds")
            Sleep(5, self.logger)
            loopStart = self.TestName + " - Start - Loop # %i " % (loopCount)
            self.logger.Info("%s", loopStart.center(60, '='))
            self.wifistress.WiFiSleepWake(sleepTime=self.sleepTime, wakeTime=60, goToSpringBoardAfterWake=False, checkBB=True, unlock=False)
            # Check the network after a sleep wake has taken place
            self.logger.Info("Sleeping for 10 seconds so that the network can Join")
            Sleep(10, self.logger)
            if self.wifistress.dutControl.VerifyConnectionAndPing(ap, self.PingDestination):
                self.logger.Info('Test: %s-WiFi Sleep Wake Loop %i - Passed' % (self.testTittle, loopCount))
                testFail = False
                if self.wifistress.dutControl.VerifyEAPType(self.tests[self.Vendor][self.TestName]['TestOptions']['ExpectedEAPType']):
                    self.logger.Info("EAP Type Matches")
                    self.logger.Info('Test: %s-VerifyEAPType Loop %i - Passed' % (self.testTittle, loopCount))
                    testFail = False
                    if (self.wifistress.dutControl.traffic.HTTPDownloadTest(dest=self.web_destination)):
                        self.logger.Info('Test: %s-HTTPDownload Loop %i - Passed' % (self.testTittle, loopCount))
                        self.testPass += 1
                        testFail = True
                    else:
                        self.logger.Fail('HTTP Download Test - Could not download %s' % (self.web_destination))
                        self.testResults.append('Test: %s-HTTPDownload Loop %i- Failed' % (self.testTittle, loopCount))
                        testFail = False
                        self.testFail += 1
                else:
                    self.logger.Fail("Verify EAP Type Failed - Loop %i" % loopCount)
                    self.testResults.append('Test: %s-VerifyEAPType Loop %i - Failed' % (self.testTittle, loopCount))
                    testFail = True
                    self.testFail += 1
            else:
                self.testResults.append('Test: %s-Verify Connection and Ping Loop %i - Failed' % (self.testTittle, loopCount))
                self.logger.Fail('Verify Connection and Ping after WiFi Off-On: %s' % ap.ssid)
                testFail = True
                self.testFail += 1
            self.logger.Info("Saving test logs for Loop %i" % (loopCount))
            self.logger.Info("Saving Logs after sleeping for 5 seconds")
            Sleep(5, self.logger)
            testLable = self.testTittle + " - WiFi On Off Loop %i " % (loopCount)
            loopEnd = self.testTittle + " - End of Loop # %i " % (loopCount)
            self.wifistress.logParser.SaveAllLogsWithTestName(testLable)
            self.logger.Info("%s", loopEnd.center(60, '='))
        self.wifistress.logParser.SaveAllLogsWithTestName(self.testTittle)
        self.wifistress.logParser.DisableAllLogs()
        resulttext = "Results"
        self.testResults.append("\n\n%s \n" % resulttext.center(60, '='))
        self.testResults.append('%i tests Passed' % self.testPass)
        self.testResults.append('%i tests Failed' % self.testFail)
        self.testResults.append('%i total tests' % self.totalTests)
        percentPassed = "{0:.0f}% test Passed".format(float(self.testPass) / self.totalTests * 100)
        self.testResults.append(percentPassed)
        percentFailed = "{0:.0f}% test Failed".format(float(self.testFail) / self.totalTests * 100)
        self.testResults.append(percentFailed)
        self.logger.SetTestProperty("configurationtag", " %s - Pass: %s  - Fail: %s Percentages: %s %s" % (self.Vendor, self.testPass, self.testFail, percentPassed, percentFailed))
        self.testPassTotal += self.testPass
        self.testFailTotal += self.testFail
        self.testPass = 0
        self.testFail = 0

        self.testResults.append("\nLog files located on system: %s at %s" % (socket.gethostname() , self.logger.getTestLogsPath()))
        if self.sendmail:
            self.sendEmail()

if __name__ == '__main__':
    pass
