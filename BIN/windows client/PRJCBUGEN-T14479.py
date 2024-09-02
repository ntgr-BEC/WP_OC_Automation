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
        self.capturescreen()
        if self.is_element_present(By.XPATH,"//h2[text()='Latency Test Error']"):
            driver.refresh()
            self.wait_page_ready()
            self.capturescreen()
        driver.find_element_by_xpath("//span[text()='Go']/..").click()
        time.sleep(60)
		
	if not self.is_element_present(By.XPATH,"//span[@class='result-data-large number result-data-value download-speed']") and not self.is_element_present(By.XPATH,"//span[@class='result-data-large number result-data-value upload-speed']"):
	    test_result = 0
        else:
	    download_speed = driver.find_element_by_xpath("//span[@class='result-data-large number result-data-value download-speed']").text
	    upload_speed = driver.find_element_by_xpath("//span[@class='result-data-large number result-data-value upload-speed']").text
         
	if test_result == 1:
	    print "download-speed:", download_speed
	    print "upload-speed:", upload_speed
        print "finalresult:", test_result
        

if __name__ == "__main__":
    suite = unittest.TestLoader().loadTestsFromTestCase(Cpa)
    unittest.TextTestRunner().run(suite)
