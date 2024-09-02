"""
PingTestBase class

Created by aomoto on 2012-06-07
Copyright (c) 2012 Apple, Inc. All rights reserved.
"""

import WirelessAutomation as waf
from APControl.APBase import APConfig, APBase
from LogInit import GetConfig
from WiFi.Control import Control
from WiFi.LogParse import WiFiLogs
from TestClass.ErrorCheckingWiFiTest import ErrorCheckingWiFiTest
from TestClass.WiFiTest import SetupSingleAp, GetDataDrivenConfigs
from HostTools.WiFiControl import WiFiControl

#----------------------------------------------------------------------
#
#----------------------------------------------------------------------

class PingTestBase(ErrorCheckingWiFiTest):
    """ Verifies a DUT can ping successfully w/ various ping settings """

    requirements = dict(dut=waf.WiFiDevice, ap=APBase)
    testData = GetDataDrivenConfigs('./Tests')

    def setup(self):
        super(PingTestBase, self).setup(unlockDevices=False, getToHome=False)
        self.ctrl = Control(self.dut)
        self.dut.wifi.On()
        self.dut.wifi.ForgetAll()
        self.logs = WiFiLogs(self.dut, testName=self.testName, enableStreamingFile=True)
        self.logs.EnableAllLogging()
        self.logs.StartSyslog()

    def teardown(self):
        super(PingTestBase, self).teardown(unlockDevices=False)

    def run(self):
        # Merge test w/ config data
        from Utilities.PlistUtils import PlistUtils
        cfg = GetConfig()

        # Setup AP
        self.ap.SetupAP(cfg.apconfigs[0])
        network = self.ap.GetNetwork()

        # Should be connected
        assert self.ctrl.VerifyJoin(network)

        # Send ping to AP
        macCtrl = WiFiControl()
        macCtrl.Associate(network)
        pingFunc = macCtrl.VerifyPing
        ipAddr = self.dut.wifi.GetTcpipSettings().ipAddress
        direction = cfg.get('ping_direction', 'rx')

        if direction == 'tx':
            pingFunc = self.ctrl.VerifyPing
            ipAddr = macCtrl.GetIpAddress()

        assert pingFunc(
            dest = ipAddr,
            count = int(cfg.get('ping_count', 10)),
            wait = float(cfg.get('ping_interval', 0.5)),
            size = int(cfg.get('ping_packet_size', 64)),
            maxFailurePercent = float(cfg.get('ping_fail_percent', 90.0))
        )

#----------------------------------------------------------------------
#  MAIN
#----------------------------------------------------------------------

if __name__ == '__main__':
    import WiFiUtil
    WiFiUtil.main()
