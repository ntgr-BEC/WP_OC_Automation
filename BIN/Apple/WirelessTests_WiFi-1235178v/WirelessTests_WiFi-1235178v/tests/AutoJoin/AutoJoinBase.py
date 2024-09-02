"""
AutoJoinBase

Created by aomoto on 2012-06-07
Copyright (c) 2012 Apple, Inc. All rights reserved.
"""

import WirelessAutomation as waf
from WiFiModulesExceptions import *
from APControl.APBase import APConfig, APBase
from LogInit import GetConfig
from WiFi.Control import Control
from WiFi.LogParse import WiFiLogs
from TestClass.ErrorCheckingWiFiTest import ErrorCheckingWiFiTest
from TestClass.WiFiTest import SetupSingleAp, GetDataDrivenConfigs

#----------------------------------------------------------------------
#
#----------------------------------------------------------------------

class AutoJoinBase(ErrorCheckingWiFiTest):
    """
    Verifies a DUT correctly autojoins across configs from "Tests".
    Usage:  WATestHarness -f AutoJoinTests.py -w n94 -j <basename w/o .list>
    """
    requirements = dict(dut=waf.WiFiDevice, ap=APBase)
    testData = GetDataDrivenConfigs('./Tests')

    def setup(self):
        super(AutoJoinBase, self).setup(unlockDevices=False, getToHome=False)
        self.ctrl = Control(self.dut)
        self.dut.wifi.On()
        self.dut.wifi.ForgetAll()
        self.logs = WiFiLogs(self.dut, testName=self.testName, enableStreamingFile=True)
        self.logs.EnableAllLogging()
        self.logs.StartSyslog()

    def teardown(self):
        super(AutoJoinBase, self).teardown(unlockDevices=False)

    def run(self):
        # Merge test w/ config data
        from Utilities.PlistUtils import PlistUtils
        cfg = GetConfig()

        # Skip if DUT doesn't support 5GHz
        cfg = APConfig(cfg.apconfigs[0])
        if (not self.ctrl.Has5Ghz) and (cfg['radios'][1]['apRadioPower'] == 'on'):
            raise WiFiSkipTestError("DUT doesn't support 5GHz")

        # Setup the AP
        self.ap.SetupAP(cfg)
        network = self.ap.GetNetwork()

        # Join network
        assert self.ctrl.VerifyJoin(network)

        # Verify WiFi off/on autojoin
        assert self.ctrl.VerifyAutoJoinWiFiOffOn(network)

        # Verify connection to internet
        assert self.ctrl.VerifyPing()

#----------------------------------------------------------------------
#  MAIN
#----------------------------------------------------------------------

if __name__ == '__main__':
    import WiFiUtil
    WiFiUtil.main()
