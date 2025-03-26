# -*- coding: utf-8 -*-

import os
import sys
import time
import re
import xml.dom.minidom as minidom
import unittest
from selenium.webdriver.common.by import By
from selenium.webdriver.support.ui import WebDriverWait
from selenium.webdriver.support import expected_conditions as EC
from seleniumlib import ClientTestCase

class Cpa(ClientTestCase):
    def getText(self, nodelist):
        rc = []
        for node in nodelist:
            if node.nodeType == node.TEXT_NODE:
                rc.append(node.data)
        return ''.join(rc)

    # get the value from xml file
    DOMTree = minidom.parse("captive.xml")
    testurl = DOMTree.getElementsByTagName("testurl")
    urlNode = DOMTree.getElementsByTagName("testurl")[0]
    urlValue = urlNode.childNodes[0].data if urlNode.childNodes else ""
    expectNode = DOMTree.getElementsByTagName("username")[0]
    expectresult = expectNode.childNodes[0].data if expectNode.childNodes else ""
    checkNode = DOMTree.getElementsByTagName("userpassword")[0]
    checkpoint = checkNode.childNodes[0].data if checkNode.childNodes else ""
    checkpoint = "Rediff.com"

    def test_cpa(self):
        test_result = 1

        driver = self.driver
        wait = WebDriverWait(driver, 120)
        try:
            driver.get(self.base_url)
        except Exception as err:
            print("connect remote PC err by {0}".format(err))
            test_result = 0
            print("finalresult:", test_result)
            return

        self.wait_page_ready()

        wait.until(EC.presence_of_element_located((By.ID, "registerName")))
        driver.find_element(By.ID, 'registerName').send_keys('testWP')
        time.sleep(1)
        driver.find_element(By.ID, 'registerEmail').send_keys('apwebportaltest1@mailinator.com')
        time.sleep(1)
        driver.find_element(By.ID, 'registerContact').send_keys('12345')
        time.sleep(1)
        driver.find_element(By.ID, 'tableRegisterButton').click()
        time.sleep(10)
        
        liveUrl = driver.current_url
        print("Current Url =", liveUrl)
        if "rediff" in liveUrl:
            test_result = 1
        else:
            test_result = 0  

        print("finalresult:", test_result)

if __name__ == "__main__":
    suite = unittest.TestLoader().loadTestsFromTestCase(Cpa)
    unittest.TextTestRunner().run(suite)
