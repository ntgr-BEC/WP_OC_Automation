"""
SecurityTestBase class

Created by aomoto on 2012-11-09
Copyright (c) 2012 Apple, Inc. All rights reserved.
"""

import plistlib
import copy
import time

# Add path to WIFI modules
from WirelessAutomation import *
WiFiTest().AddPath()

from LogInit import GetConfig
from APControl.APDefines import *
from APControl.APBase import APConfig, APBase
from common.ConfigurationInfo import Config
from Utilities.PlistUtils import PlistUtils
from WiFi.Control import Control
from TestClass.WiFiTest import SetupSingleAp, GetDataDrivenConfigs

#----------------------------------------------------------------------
#
#----------------------------------------------------------------------

class SecurityTestBase(WiFiTest):
    """ Verifies a DUT associates to an AP and has a live connection """

    requirements = dict(dut=WiFiDevice, ap=APBase)
    testData = GetDataDrivenConfigs('./Tests')

    def __init__(self):
        super(SecurityTestBase, self).__init__()
        self.ctrl = None
        self._apConfig = dict()
        self._radioIndex = 0  # option to set ap radio
        self._browse   = 0    # option to test browse w/ safari
        self._autoJoin = 0    # option to test autojoin w/ autoJoinMethod
        self._autoJoinMethod = 'sleepWake'  # sleepWake, wifiOffOn, apOffOn

    def setup(self):
        super(SecurityTestBase, self).setup(unlockDevices=False, getToHome=False)
        self.ctrl = Control(self.dut)
        self.dut.wifi.ForgetAll()

    def teardown(self):
        super(SecurityTestBase, self).teardown(unlockDevices=False)

    def run(self):
        # Merge test w/ config data
        from Utilities.PlistUtils import PlistUtils
        cfg = copy.deepcopy(GetConfig())

        # Setup the AP
        apCfg = PlistUtils.MergePlist(cfg.apconfigs[0], self._apConfig)

        # DUT should prefer A if both enabled
        interface = RADIO_BGN
        if apCfg['radios'][1]['apRadioPower'] == 'on':
            interface = RADIO_AN
        (self.ap, self.network) = SetupSingleAp(apCfg, interface=interface)

        # Time buffer to ensure setup complete
        time.sleep(4)

        # Join network
        assert self.ctrl.VerifyJoin(self.network)

        # Send ping to AP
        assert self.ctrl.VerifyPing(self.ap.GetIpAddress())

        # Optional: VerifyBrowse
        if self._browse:
            assert self.ctrl.VerifyBrowse()

        # Optional: VerifyAutoJoin
        if self._autoJoin:
            self._VerifyAutoJoinSelector()
            assert self.ctrl.VerifyPing(self.ap.GetIpAddress())
            assert self.ctrl.VerifyBrowse()

    def _VerifyAutoJoinSelector(self):
        """ AutoJoin test, selected on method param """

        self.logger.Info('Testing autojoin method: %s' % self._autoJoinMethod)
        meth = self._autoJoinMethod.lower()

        if meth == 'wifioffon':
            assert self.ctrl.VerifyAutoJoinWiFiOffOn(self.network)
        elif meth == 'apoffon':
            assert self.ctrl.VerifyAutoJoinApOffOn(self.ap, self.network)
        else:
            # Default
            assert self.ctrl.VerifyAutoJoinSleepWake(self.network)

#----------------------------------------------------------------------
#  MAIN
#----------------------------------------------------------------------

if __name__ == '__main__':
    import WiFiUtil
    WiFiUtil.main()
