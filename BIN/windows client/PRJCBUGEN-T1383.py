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
        if not self.is_element_present(By.XPATH, '//*[contains(text(), "test1")]'):
            test_result = 0
            self.capturescreen ()
            print "not able to see Title changed"
        if not self.is_element_present(By.XPATH, '//*[contains(text(), "test2")]'):
            test_result = 0
            self.capturescreen ()
            print "not able to see Message changed"
        if not self.is_element_present(By.XPATH, '//*[contains(text(), "test3")]'):
            test_result = 0
            self.capturescreen ()
            print "not able to see Eula changed"
            
        print "finalresult:", test_result
        

if __name__ == "__main__":
    suite = unittest.TestLoader().loadTestsFromTestCase(Cpa)
    unittest.TextTestRunner().run(suite)
