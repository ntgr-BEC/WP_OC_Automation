# -*- coding: utf-8 -*-

import os, sys, time, re
import xml.dom.minidom as minidom
from seleniumlib import ClientTestCase, unittest, By

class Cpa(ClientTestCase):
    DOMTree = minidom.parse("captive.xml")
    testurl = DOMTree.getElementsByTagName("testurl")
    
    def test_cpa(self):
        test_result = 0
        
        driver = self.driver
        driver.get(self.base_url)
        self.wait_page_ready()
        # if not self.is_element_present(By.ID, "previewTitle"):
        #     test_result = 0
        #     print "not able to see Title"
        #     self.capturescreen ()
        # if not self.is_element_present(By.ID, "previewMessage"):
        #     test_result = 0
        #     print "not able to see Message"
        #     self.capturescreen ()
        # if not self.is_element_present(By.ID, "previewDesc"):
        #     test_result = 0
        #     print "not able to see Eula"
        #     self.capturescreen ()
        el = self.is_element_present(By.XPATH,"//button[text()='Agree']")
        if not el:
            test_result = 0
            print "not able to see Agree"
            self.capturescreen ()
        else:
            self.driver.find_element_by_xpath('//button[contains(@onclick,"navigateFn_access")]').click()
            time.sleep(30)
            driver.refresh()
            self.driver.find_element_by_xpath('//button[contains(@onclick,"navigateFn_access")]').click()
            driver.get(self.base_url)
            if self.is_element_present(By.XPATH, '(/html/body/div[1]/div/div[1]/span)[1]'):
                test_result = 1
            
        print "finalresult:", test_result
        

if __name__ == "__main__":
    suite = unittest.TestLoader().loadTestsFromTestCase(Cpa)
    unittest.TextTestRunner().run(suite)
