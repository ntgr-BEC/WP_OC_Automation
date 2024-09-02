"""
AutoJoin

Created by aomoto on 2013-06-14
Copyright (c) 2013 Apple. All rights reserved.
"""
import WirelessAutomation as waf
from LogInit import GetConfig
from APControl import *
from TestClass.WiFiTest import SetupSingleAp
from WiFi.Control import Control
from TestClass.ErrorCheckingWiFiTest import ErrorCheckingWiFiTest

#----------------------------------------------------------------------
#
#----------------------------------------------------------------------

class AutoJoinFailChangeWpaPassword(ErrorCheckingWiFiTest):
    """
    <rdar> description
    wifi off at same time wifid crashes in loop and never recovers

    1. Setup WPA network.
    2. Connect device to that network.
    3. Turn off WiFi on the device.
    4 Change WPA password on the AP.
    5. Enable WiFi on the device.
    6. Verify device cannot join the network and wifid doesn't crash.
    """

    requirements = dict(dut=waf.WiFiDevice)

    def run(self):
        ctrl = Control(self.dut)

        # Setup WPA network
        cfg1 = APConfig()
        cfg1.LoadConfig(GetConfig().apconfigs[0])
        cfg1.SetSecurityMode('wpa-psk')
        cfg1.SetPassPhrase('wireless')
        (ap1, network1) = SetupSingleAp(cfg1)

        # Connect to network
        assert ctrl.VerifyJoin(network1)

        # Turn off WIFI
        self.dut.wifi.Off()

        # Change WPA password on AP
        cfg2 = APConfig()
        cfg2.LoadConfig(cfg1)
        cfg2.SetPassPhrase('password')
        (ap2, network2) = SetupSingleAp(cfg2)

        # Enable WIFI
        self.dut.wifi.On()

        # Verify device cannot join the network and wifi doesn't crash
        self.logger.Info('Verify DUT fails to join network w/ new passphrase')
        try:
            self.dut.wifi.Join(network1)
            self.logger.Fail('DUT joined the network successfully')
        except waf.WiFiAssociationError, e:
            pass

#----------------------------------------------------------------------
#  MAIN
#----------------------------------------------------------------------

if __name__ == '__main__':
    import WiFiUtil
    WiFiUtil.main()
