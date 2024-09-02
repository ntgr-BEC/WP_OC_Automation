# -*- coding: utf-8 -*-

import os, sys, time, re

try:
    from seleniumlib import ClientTestCase, unittest, By
except:
    print 'please unzip selenium.zip to c:\Python27\Lib\site-packages'
    sys.exit(1)

class A: data = 'about:blank'
class B: childNodes = [A]
C = [A]
class CheckEnv(ClientTestCase):
    testurl = [B]
    def test_env(self):
        testresult = True
        try:
            self.driver.get("https://www.google.com")
        except Exception as err:
            testresult = False
            print "Driver is failed to start: {0}".format(err)
            return

        self.wait_page_ready()
        try:
            print self.driver.title
        except Exception as err:
            testresult = False
            print "check version of geckodriver & firefox: {0}".format(err)
            return
        assert testresult, "check log for details"

if __name__ == "__main__":
    suite = unittest.TestLoader().loadTestsFromTestCase(CheckEnv)
    unittest.TextTestRunner().run(suite)
