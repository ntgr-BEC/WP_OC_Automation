"""
StressTest: Verifies a DUT can associate to an AP and has a live connection

Created by aomoto on 2012-11-09
Copyright (c) 2012 Apple, Inc. All rights reserved.
"""

# Python modules
import plistlib

# Internal modules
from common.ConfigurationInfo import Config
from APControl.APDefines import *
from SecurityTestBase import SecurityTestBase
from TestClass.WiFiTest import SetupSingleAp
from WiFi.Control import Control
from Utilities.PlistUtils import PlistUtils

#----------------------------------------------------------------------
#
#----------------------------------------------------------------------

class BGN_NoSecurity_TwoApOffOn_Stress(SecurityTestBase):
    """
    Test case for AP in 2.4GHz, 802.11n/b/g compatible, open network/no security.

    This test will associate with the AP, turn AP associated radio off.
    Check device association status using apple80211 and wl command. Continue tests by
    turning the AP radio back on, and then WiFi off/on, check auto join.

    Pass condition: Able to communicate between STA and AP.
    Fail condition: Not able to communicate.
    """

    AP_CONFIG_1 = plistlib.readPlistFromString('''\
<dict>
    <key>keyMgmtType</key>
    <string>none</string>
    <key>radios</key>
    <array>
        <dict>
            <key>apRadioPower</key>
            <string>on</string>
            <key>radioMode</key>
            <string>BGN_HT20</string>
            <key>radioChannel</key>
            <string>1</string>
            <key>Bcast</key>
            <string>true</string>
        </dict>
        <dict>
            <key>apRadioPower</key>
            <string>off</string>
        </dict>
    </array>
</dict>
''')

    def setup(self):
        # Invoke parent
        super(BGN_NoSecurity_TwoApOffOn_Stress, self).setup():

        # Init
        self.ctrl = Control(self.dut)
        self.cfg1 = PlistUtils.MergePlist(Config.apconfigs[0], self.AP_CONFIG_1)
        self.cfg2 = PlistUtils.MergePlist(Config.apconfigs[0], self.AP_CONFIG_1)

        # Set SSIDs
        self.cfg1['radios'][0]['ssid'] = 'BGNStress'
        self.cfg2['radios'][0]['ssid'] = 'BGNStress'

        # Setup and Join AP1
        self.cfg1['radios'][0]['radioPower'] = 'on'
        self.cfg1['radios'][1]['radioPower'] = 'off'
        (self.ap1, self.network1) = SetupSingleAp(self.cfg1, interface=RADIO_BGN)
        assert not self.ctrl.VerifyJoin(self.network1)

        # Setup AP2 off
        self.cfg2['radios'][0]['radioPower'] = 'off'
        self.cfg2['radios'][1]['radioPower'] = 'off'
        (self.ap2, self.network2) = SetupSingleAp(self.cfg2, interface=RADIO_BGN)


    def runTest(self):
        # Cycle to AP2, back to AP1
        self.logger.Debug("Start AP cycle")
        self._SwitchAP(self.ap1, self.ap2, numCycles)

        self.logger.Debug("Now turn off AP1 to trigger device wake up")
        self.ap1.RadioPower('off', RADIO_BGN)
        self.ap1.RadioPower('off', RADIO_AN)
        self.ap1.Reboot()

        # Verify not associated
        assert not self.ctrl.VerifyNotAssociated()

        # Turn AP1 radio on
        self.ap1.RadioPower('on', RADIO_BGN)
        self.ap1.RadioPower('off', RADIO_AN)
        self.ap1.Reboot()

        # Verify AutoJoin
        assert not self.ctrl.VerifyAutoJoinWiFiOffOn(self.network1)
        assert not self.ctrl.VerifyPing(self.ap1.GetIpAddress())


    def run(self):
        # Set the number of iteration
        self._count = 50

        # Run stress test
        results = dict(PASS=0, FAIL=0) #ERROR
        err_msgs = {}

        for n in range(self._count):
            self.logger.Info('Starting cycle %d' % n)

            # Try to run the test
            res = 'PASS'
            try:
                self.runTest()
            except Exception, e:
                # Log error/fail
                self.logger.Fail(str(e))
                res = 'FAIL'
                err_msgs[str(n)] = str(e)
            finally:
                # Always run teardown
                test.teardown()

            # Process results
            for logger in self.logger._loggers:
                if logger.failEntryCount > 0:
                    logger.failEntryCount = 0
                    res = 'FAIL'

            self.logger.Info('Ending cycle %d: %s' % (n, res))
            results[res] += 1

        # Report results
        self.logger.Info('')
        self.logger.Info('Stress test results: %d cycles' % self._count)
        self.logger.Info('    PASS: %d' % results['PASS'])
        self.logger.Info('    FAIL: %d' % results['FAIL'])
        self.logger.Info('')
        if err_msgs:
            for (n, err_msg) in sorted(err_msgs.items()):
                self.logger.Fail('Test %d: %s' % (n, err_msg))
            self.logger.Info('')


    def _SwitchAP(self, numCycles=1):
        """ Cycles AP1 on/off, then AP2 on/off """
        for n in range(numCycles):
            self.logger.Debug('Turn AP2 radio on')
            self.ap2.RadioPower('on', RADIO_BGN)
            self.ap2.Reboot()

            self.logger.Debug('Turn AP1 radio off')
            self.ap1.RadioPower('off', RADIO_BGN)
            self.ap1.Reboot()

            self.logger.Debug('Turn AP1 radio on')
            self.ap1.RadioPower('on', RADIO_BGN)
            self.ap1.Reboot()

            self.logger.Debug('Turn AP2 radio off')
            self.ap2.RadioPower('off', RADIO_BGN)
            self.ap2.Reboot()

#----------------------------------------------------------------------
#  MAIN
#----------------------------------------------------------------------

if __name__ == '__main__':
    import WiFiUtil
    WiFiUtil.main()

