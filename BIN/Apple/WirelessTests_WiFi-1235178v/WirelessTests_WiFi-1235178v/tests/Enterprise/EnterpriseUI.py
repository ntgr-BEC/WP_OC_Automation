#!/usr/bin/env python2.3
#
from common.WirelessTest import *
WiFiTest().AddPath()
from common.ConfigurationInfo import Config
from TrafficGen.Basic import Basic
from HostTools.SnifferTools import SnifferTools
from WiFi.Control import Control
from WiFi.LogParse import WiFiLogs
from LogInit import LogIt
from WiFi.Stress import WiFiStress
#import pdb
class EnterpriseUI(WiFiTest):

    def __init__(self):
        #self.setRequirements(dut=WiFiDevice)
        self.setRequirements(dut1=WiFiDevice, dut2=WiFiDevice)
        #self.setRequirements(dut1=WiFiDevice)
        self.dutList = []
    def setup(self):
        #WirelessTest.setup(self)
        self.dutList = [self.dut1, self.dut2]
        #self.dutList = [self.dut1]
        for dut in self.dutList:
            self.dut = dut
            WirelessTest.setup(self)
            self.dut.getWiFi().ForgetAll()
        self.logger = LogIt()
        self.testFail = 0
        self.testPass = 0
        #self.ap = Config.accesspoints[0]
        
    def run(self):
        #pdb.set_trace()
        self.testResults = []

        for dut in self.dutList:
            for self.ap in Config.accesspoints:
                self.logger.Info('Starting Test')
                myTraffic = Basic()
                myTraffic.SetDutAndDest(self.dut, 'http://www.google.com')
                myDut = Control(dut)
                onoff = WiFiStress(self.dut)
                if myDut.TestAP(self.ap, 'www.google.com'):
                    if myTraffic.HTTPDownloadTest(dest='http:www.yahoo.com'):#(dut=self.dut, dest='http://www.google.com'):
                        self.logger.Pass('got google web page!')
                    else:
                        self.logger.Fail('didnt get google web page!')
                    for i in range (1, 2):
                        #jim=onoff.WiFiOnOff(i)
                        #print(jim)
                        try:
                            onoff.WiFiOnOff(i)
                        except:
                            self.logger.Fail('On Off Failed!')
                else:
                        self.logger.Fail('didnt connect to AP %s' % ap['ssid'])
    def teardown(self):
        matrixname = " Test Results Matrix "
        self.logger.Info("%s", matrixname.center(60,'='))
        for results in self.testResults:
            self.logger.Info(results)
        self.logger.Info('%s tests Passed', self.testPass)
        self.logger.Info('%s tests Failed', self.testFail)
        WirelessTest.teardown(self)

if __name__ == '__main__':
    test = EnterpriseUI()
    test.run()