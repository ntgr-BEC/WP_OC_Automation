"""
A_Preference_RoamFromBG

Created by aomoto on 2012-11-09
Copyright (c) 2012 Apple, Inc. All rights reserved.
"""

# Python modules
import plistlib
import copy
import time

# Add path to WIFI modules
from WirelessAutomation import *
WiFiTest().AddPath()

# Internal modules
from common.ConfigurationInfo import Config
from Utilities.PlistUtils import PlistUtils
from TestClass.WiFiTest import SetupSingleAp, CreateWiFiNetwork
from SecurityTestBase import *
from APControl.APDefines import *

#----------------------------------------------------------------------
#
#----------------------------------------------------------------------

class A_Preference_RoamFromBG(SecurityTestBase):
    """ Verifies DUT associates to AP A-band and autojoins w/ A Preference """

    def __init__(self):
        super(A_Preference_RoamFromBG, self).__init__()
        self._apConfig = plistlib.readPlistFromString(
        '''\
    <dict>
        <key>keyMgmtType</key>
        <string>wpa2-psk</string>
        <key>passphrase</key>
        <string>wireless</string>
        <key>radios</key>
        <array>
            <dict>
                <key>ssid</key>
                <string>AGSameSSID</string>
                <key>apRadioPower</key>
                <string>on</string>
                <key>radioMode</key>
                <string>BGN_HT20</string>
                <key>radioChannel</key>
                <string>2</string>
            </dict>
            <dict>
                <key>ssid</key>
                <string>AGSameSSID</string>
                <key>apRadioPower</key>
                <string>off</string>
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
        (self.ap, self.network) = SetupSingleAp(cfg, radioIndex=0)

        # Join 2.4G network
        self.dut.wifi.ForgetAll()
        assert self.ctrl.VerifyJoin(self.network)

        # Send ping to AP
        assert self.ctrl.VerifyPing(self.ap.GetIpAddress())

        # Turn WiFi Off
        self.dut.wifi.Off()

        # Turn on AP 5GHz
        cfg['radios'][0]['apRadioPower'] = 'on'
        cfg['radios'][1]['apRadioPower'] = 'on'
        (self.ap, self.network2) = SetupSingleAp(cfg, interface=RADIO_AN)

        # Turn WiFi On
        self.dut.wifi.On()

        # Verify 5GHz network
        time.sleep(10)  # DUT should autojoin to 5G within some time
        assert self.ctrl.VerifyNetwork(self.network2)

#----------------------------------------------------------------------
#  MAIN
#----------------------------------------------------------------------

if __name__ == '__main__':
    import WiFiUtil
    WiFiUtil.main()
