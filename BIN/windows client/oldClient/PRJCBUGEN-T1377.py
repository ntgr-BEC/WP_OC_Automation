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
        if not self.is_element_present(By.ID, "previewTitle"):
            test_result = 0
            print "not able to see Title"
            self.capturescreen ()
        if not self.is_element_present(By.ID, "previewMessage"):
            test_result = 0
            self.capturescreen ()
            print "not able to see Message"
        if not self.is_element_present(By.ID, "previewDesc"):
            test_result = 0
            self.capturescreen ()
            print "not able to see Eula"
        if not self.is_element_present(By.XPATH, '//*[contains(text(), "Agree")]'):
            test_result = 0
            self.capturescreen ()
            print "not able to see Agree"
            
        print "finalresult:", test_result

if __name__ == "__main__":
    suite = unittest.TestLoader().loadTestsFromTestCase(Cpa)
    unittest.TextTestRunner().run(suite)
