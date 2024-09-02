"""
PingTestBase class

Created by aomoto on 2012-06-07
Copyright (c) 2012 Apple, Inc. All rights reserved.
"""

import copy
import WirelessAutomation as waf
from LogInit import GetConfig
from APControl import CreateAP
from common.ConfigurationInfo import Config
from TestClass.WiFiTest import GetDataDrivenConfigs
from Utilities.PlistUtils import PlistUtils
from PingTestBase import PingTestBase

#----------------------------------------------------------------------
#
#----------------------------------------------------------------------

class PingTestAll(PingTestBase):
    """ Runs all Ping configurations in Tests folder """

    requirements = dict(dut=waf.WiFiDevice)
    testData = []

    def run(self):
        cfg = copy.deepcopy(GetConfig())  # save original
        self.ap = CreateAP(cfg.apconfigs[0]['vendor'])

        for (testName, testData) in GetDataDrivenConfigs('./Tests').items():
            self.logger.Info('Running test: %s' % testName)

            # Update Config w/ testData
            PlistUtils.UpdatePlist(Config.__dict__, cfg)  # restore original
            PlistUtils.UpdatePlist(Config.__dict__, testData[testName])

            # Run test
            try:
                super(PingTestAll, self).run()
            except Exception, e:
                self.logger.Fail(str(e))

#----------------------------------------------------------------------
#  MAIN
#----------------------------------------------------------------------

if __name__ == '__main__':
    import WiFiUtil
    WiFiUtil.main()
