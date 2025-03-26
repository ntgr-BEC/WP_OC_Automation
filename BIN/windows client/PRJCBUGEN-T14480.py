# -*- coding: utf-8 -*-

import os, sys, time, re
import xml.dom.minidom as minidom
from seleniumlib import ClientTestCase, unittest, By


class Cpa(ClientTestCase):
    DOMTree = minidom.parse("captive.xml")
    testurl = DOMTree.getElementsByTagName("testurl")

    def test_cpa(self):
        test_result = 1

        driver = self.driver
        driver.get(self.base_url)
        self.wait_page_ready()
        if self.is_element_present(By.ID, "advancedButton"):
            print "Advance Page visible"
            driver.find_element_by_xpath("//*[@id='advancedButton']").click()
            time.sleep(10)

        if self.is_element_present(By.ID, "exceptionDialogButton"):
            print "Advance Page visible"
            driver.find_element_by_xpath("//*[@id='exceptionDialogButton']").click()
            time.sleep(10)

        if self.is_element_present(By.ID, "wt-header"):
            print "Page cannot be open"
            time.sleep(10)

        else:
            self.is_element_present(By.XPATH, '/html/body/div[1]/div[1]/div/a/img')
            test_result = 0
            print " able to see redirect URL"
            self.capturescreen()

        print "finalresult:", test_result


if __name__ == "__main__":
    suite = unittest.TestLoader().loadTestsFromTestCase(Cpa)
    unittest.TextTestRunner().run(suite)
