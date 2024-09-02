"""
StressTest: Verifies a DUT can associate to an AP and has a live connection

Created by aomoto on 2012-11-09
Copyright (c) 2012 Apple, Inc. All rights reserved.
"""

# Python modules

# Internal modules
from BGN_NoSecurity_TwoApOffOn_Stress import *

#----------------------------------------------------------------------
#
#----------------------------------------------------------------------

class BGN_NoSecurity_TwoApOffOn_SleepWake_Stress(BGN_NoSecurity_TwoApOffOn_Stress):
    """
    Test case for AP in 2.4GHz, 802.11n/b/g compatible, open network/no security.

    This test will associate with the AP, turn AP associated radio off.
    (Verify Wow is enabled before testing). Sleeps test device for 90 seconds.
    Check device association status using apple80211 and wl command. Continue tests by
    turning the AP radio back on, and then WiFi off/on, check auto join.

    Pass condition: Able to communicate between STA and AP.
    Fail condition: Not able to communicate.
    """

    def runTest(self):
        # Sleep DUT
        self.dut.os.SleepCycle(sleep=90)

        # Invoke parent
        super(BGN_NoSecurity_TwoApOffOn_SleepWake_Stress, self).runTest()

#----------------------------------------------------------------------
#  MAIN
#----------------------------------------------------------------------

if __name__ == '__main__':
    import WiFiUtil
    WiFiUtil.main()

