"""
Verifies a DUT can associate to an AP and has a live connection

Created by aomoto on 2012-11-09
Copyright (c) 2012 Apple, Inc. All rights reserved.
"""

# Python modules
import plistlib

# Internal modules
from SecurityTestBase import *

#----------------------------------------------------------------------
#
#----------------------------------------------------------------------

class A_Preference_Test(SecurityTestBase):
    """ Verifies DUT associates to AP A-band and autojoins w/ A Preference """

    def __init__(self):
        super(A_Preference_Test, self).__init__()
        self._radioIndex = 1
        self._autoJoin = 1
        self._autoJoinMethod = 'apOffOn'
        self._apConfig = plistlib.readPlistFromString(
        '''\
    <dict>
        <key>keyMgmtType</key>
        <string>wpa2-psk</string>
        <key>radios</key>
        <array>
            <dict>
                <key>ssid</key>
                <string>A_Preference_Test</string>
                <key>apRadioPower</key>
                <string>on</string>
                <key>radioMode</key>
                <string>BGN_HT20</string>
                <key>radioChannel</key>
                <string>2</string>
            </dict>
            <dict>
                <key>ssid</key>
                <string>A_Preference_Test</string>
                <key>apRadioPower</key>
                <string>on</string>
                <key>radioMode</key>
                <string>AN_HT20</string>
                <key>radioChannel</key>
                <string>36</string>
            </dict>
        </array>
    </dict>
''')

    def run(self):
        # Skip if 5G not supported
        if not self.ctrl.Has5Ghz:
            raise WiFiSkipTestError('DUT does not support 5GHz')

        # Setup the AP
        cfg = PlistUtils.MergePlist(Config.apconfigs[0], self._apConfig)
        (ap, network) = SetupSingleAp(cfg, radioIndex=1)

        # WiFi join
        assert self.ctrl.VerifyJoin(network)

        # Check associated channel is A band
        assert self.ctrl.VerifyNetwork(network)  # Should be on 5GHz

#----------------------------------------------------------------------
#  MAIN
#----------------------------------------------------------------------

if __name__ == '__main__':
    import WiFiUtil
    WiFiUtil.main()
