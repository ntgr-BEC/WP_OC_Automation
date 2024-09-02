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

class BGN_WEP128_32Char(SecurityTestBase):

    def __init__(self):
        super(BGN_WEP128_32Char, self).__init__()
        self._apConfig = plistlib.readPlistFromString(
        '''\
    <dict>
        <key>keyMgmtType</key>
        <string>wep128</string>
        <key>passphrase</key>
        <string>123456789ABCD</string>
        <key>radios</key>
        <array>
            <dict>
                <key>ssid</key>
                <string>abcdefghijklmnopqrstuvwxyzabcdef</string>
                <key>apRadioPower</key>
                <string>on</string>
                <key>radioMode</key>
                <string>BGN_HT20</string>
            </dict>
            <dict>
                <key>apRadioPower</key>
                <string>off</string>
            </dict>
        </array>
    </dict>
''')

#----------------------------------------------------------------------
#  MAIN
#----------------------------------------------------------------------

if __name__ == '__main__':
    import WiFiUtil
    WiFiUtil.main()
