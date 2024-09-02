"""
Verifies a DUT can associate to an AP and has a live connection

Created by aomoto on 2012-11-09
Copyright (c) 2012 Apple, Inc. All rights reserved.
"""

# Python modules

# Internal modules
from SecurityTestBase import *
from APControl.SohoAP import GetAppleWiFiNetwork

#----------------------------------------------------------------------
#
#----------------------------------------------------------------------

class Broadcast_AppleWiFi(SecurityTestBase):

    def run(self):
        # Setup the AP
        network = GetAppleWiFiNetwork()

        # Join network
        assert self.ctrl.VerifyJoin(network)

        # Send ping to AP
        assert self.ctrl.VerifyPing('http://www.yahoo.com')

#----------------------------------------------------------------------
#  MAIN
#----------------------------------------------------------------------

if __name__ == '__main__':
    import WiFiUtil
    WiFiUtil.main()
