"""
StressTest: Verifies a DUT can associate to an AP and has a live connection

Created by aomoto on 2012-11-09
Copyright (c) 2012 Apple, Inc. All rights reserved.
"""

# Python modules
import plistlib

# Internal modules
from SecurityTestBase import *
from TestClass.WiFi import StressTest
from BGN_NoSecurity_ApOffOn import BGN_NoSecurity_ApOffOn

#----------------------------------------------------------------------
#
#----------------------------------------------------------------------

class BGN_NoSecurity_ApOffOn_Stress(StressTest):

    def __init__(self):
        super(BGN_NoSecurity_ApOffOn_Stress, self).__init__(
            BGN_NoSecurity_ApOffOn,
            requirements = dict(dut=WiFiDevice),
            count = 100,
        )

#----------------------------------------------------------------------
#  MAIN
#----------------------------------------------------------------------

if __name__ == '__main__':
    import WiFiUtil
    WiFiUtil.main()
