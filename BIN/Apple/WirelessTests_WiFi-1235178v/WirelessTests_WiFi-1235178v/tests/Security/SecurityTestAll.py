"""
SecurityTestBase class

Created by aomoto on 2012-11-09
Copyright (c) 2012 Apple, Inc. All rights reserved.
"""

import WirelessAutomation as waf
from LogInit import GetConfig
from APControl import CreateAP
from TestClass.WiFiTest import GetDataDrivenConfigs
from SecurityTestBase import SecurityTestBase

#----------------------------------------------------------------------
#
#----------------------------------------------------------------------

class SecurityTestAll(SecurityTestBase):
    """ Runs all Security/WLAN/Radio configurations in Tests folder """

    requirements = dict(dut=waf.WiFiDevice)
    testData = []

    def run(self):
        cfg = GetConfig()
        self.ap = CreateAP(cfg.apconfigs[0]['vendor'])

        for (testName, testData) in GetDataDrivenConfigs('./Tests').items():
            self.logger.Info('Running test: %s' % testName)
            self._apConfig = testData[testName].apconfigs[0]

            # Run test
            try:
                super(SecurityTestAll, self).run()
            except Exception, e:
                self.logger.Fail(str(e))

#----------------------------------------------------------------------
#  MAIN
#----------------------------------------------------------------------

if __name__ == '__main__':
    import WiFiUtil
    WiFiUtil.main()
