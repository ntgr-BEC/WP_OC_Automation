#!/usr/bin/env
#
#  EnterpriseSample.py
#  WiFi Enterprise WirelessAutomation
#
#  This test is the base for Enterprise WiFi testing.
#
#  Created by Quint Friesen on 11/29/11.
#  Copyright (c) 2011 Apple. All rights reserved.
#

from common.WirelessException import *
from common.WirelessTest import WiFiTest
from common.WirelessCommon import *
from common.ConfigurationInfo import Config
from common.OSCommon import ProfileInstallRequest
from common.WiFiCommon import WiFiNetwork
from common.CiscoCommon import CiscoCommon
from common.iPCUCommon import iPCUProfile
from common.Scripter import interact
from plistlib import readPlist
import time, os, sys

sys.path.append("WiFi/CommonFunctions/")

from WiFi_Common import WiFi_Common


class EnterpriseSample(WiFiTest):

    def __init__(self):
        self.setRequirements(dut=WiFiDevice)

    def setup(self):
        WiFiTest.setup(self)
        try:
            self.tests = readPlist('%s/%s' % (Config.EnterpriseSetup['TestStorage'], 'EnterpriseSample.plist'))
            self.policyStore = '%s/%s' % (os.getcwd(), Config.EnterpriseSetup['PolicyStorage'])
            self.profiles = Config.profiles
            self.web_destination = Config.EnterpriseSetup['WebDestination']
        except:
            raise TestFailError, TestFailError('No PolicyTests was found in the configuration file. This test requires it')

        if len(self.tests) < 1:
                raise TestFailError, TestFailError('You need at least one test configured in PolicyTests in the configuration file.')

    def run(self):
        # Join the WiFi network
        testFail = 0
        testPass = 0
        self.testResults = []
        for test in self.tests:
#            try:
                self.logger.Info('%s: Removing any profiles we find on the device', test['name'])
                self.dut.getOS().UninstallAllProfiles()
                self.logger.Info('%s: Begin test', test['name'])
                #need to ipcu build profiles here
                enterpriseProfile = iPCUProfile()
                #pull in the plist and edit it here
                dynamicProfile = readPlist('%s/%s' % (self.policyStore, test['ProfileSource']))
                #test options edit?
                for k, v in test['ProfileOptions'].items():
                    dynamicProfile['WiFi'][k] = v
                enterpriseProfile.CreateProfile(dynamicProfile, plistobj=True)
                enterpriseProfile.ExportProfile('%s/%s' % (self.policyStore, 'CurrentTest-%s.mobileconfig' % self.dut.name))
                # Grab the profile we created.
                self.logger.Info('%s: Install Dynamically created Profile' % test['name'])
                dynaProfile = self.GetNamedDict(test['name'], self.profiles)
                #need conditional here to see if prompt answers are needed or not
                dynaProfile['promptAnswers'][dynamicProfile['WiFi']['username']] = dynamicProfile['WiFi']['password']
                dynaProfile['path'] = '%s/CurrentTest-%s.mobileconfig' % (self.policyStore, self.dut.name)
                profile = ProfileInstallRequest(dynaProfile)
                installedProfile = self.dut.getOS().InstallProfile(profile)
                self.logger.Info('%s: Validate WiFi profile installed', test['name'])
                # Connect to the AP and disconnect.
                if test['negative']:
                    #need to wrap the negatives in a try block and pass them if the exception hits
                    try:
                        pass
                    except WAError as info:
                        self.logger.Pass('%s: WiFi failed to connect, passed test!', test['name'])
                else:
                    self.logger.Info('%s: connect to AP', test['name'])
                    self.dut.getWiFi().Join(WiFiNetwork(Config.accesspoints[1]), useUI=True)
                    WiFi = WiFi_Common()
                    eapType = WiFi.GetEAPState(self.dut)
                    if eapType['EAPType'] != 17 and eapType['EAPTypeName'] != 'LEAP':
                        interact(self.dut, 'Certificate Accept', ' \'UIATarget.localTarget().frontMostApp().mainWindow().tableViews()[2].cells()["apple.com, Not Trusted,"].buttons()["Accept"].tap();\'')
                    if eapType['EAPType'] == test['TestOptions']['ExpectedEAPType']:
                        self.logger.Pass('%s: EAP Type %s matches expected' % (test['name'], eapType['EAPTypeName']))
                    else:
                        self.logger.Fail('%s: EAP Type %s expected, got %s instead' % (test['name'], test['TestOptions']['ExpectedEAPType'], eapType['EAPType']))
                    time.sleep(5)
                    self.logger.Info('%s: httpDownloadTest traffic test', test['name'])
                    httpRes = self.dut.getOS().Execute('httpDownloadTest %s' % self.web_destination)
                    if 'connection failed:' in httpRes.standardError:
                        self.logger.Fail('%s: httpDownloadTest failed' % test['name'])
                        raise Exception('Failed HTTP')
                    else:
                        self.logger.Pass('%s: httpDownloadTest succeeded' % test['name'])
                    self.logger.Info('%s: Disassociate from AP', test['name'])
                    self.dut.getWiFi().Disassociate()

                # Remove the profile
                self.logger.Info('%s: Remove profile', test['name'])
                self.dut.getOS().UninstallProfile(installedProfile)
                #Remove profile for filesystem
                enterpriseProfile.RemoveProfile()
                os.remove('%s/%s' % (self.policyStore, 'CurrentTest-%s.mobileconfig' %self.dut.name))
                self.dut.getWiFi().ForgetAll()
                testPass = testPass + 1
                self.testResults.append('Test: %s - Pass' % test['name'])
                #reset wifi logging
#            except:
#                self.logger.Fail('%s: Test hit an exception', test['name'])
#                testFail = testFail + 1
#                self.testResults.append('Test: %s - Fail' % test['name'])
#                #Need to get wifi logs from device
#                enterpriseProfile.RemoveProfile()
#                self.dut.getWiFi().ForgetAll()
        self.logger.Info("====Test Results====")
        self.logger.Info('Test Results Matrix:')
        for results in self.testResults:
            self.logger.Info(results)
        self.logger.Info('%s tests Passed', testPass)
        self.logger.Info('%s tests Failed', testFail)
        self.dut.getOS().UninstallAllProfiles()
        self.dut.getWiFi().ForgetAll()
        if testFail > 0:
            raise TestFailError, TestFailError('Failed at least one Enterprise WiFi test, please see logs.')

    def GetNamedDict(self, name=None, searchList=None):
        if name is not None and searchList is not None:
            for searchDict in searchList:
                if searchDict['name'] == name:
                    return searchDict
        return None

if __name__ == '__main__':
    pass
