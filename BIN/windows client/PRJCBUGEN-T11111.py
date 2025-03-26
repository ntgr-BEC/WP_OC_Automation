import self as self
from selenium import webdriver
import time
import os, sys, re
import xml.dom.minidom as minidom
from seleniumlib import ClientTestCase, unittest, By

from selenium.webdriver.common.by import By

driver=webdriver.Chrome()
driver.get("https://maint-beta-cpportal.insight.netgear.com/")

class Cpa(ClientTestCase):
    time.sleep(35)
    driver.refresh()
    time.sleep(35)
    self.wait_element(By.ID, 'fSubmit')
    self.driver.find_element_by_id('fSubmit').click()
    time.sleep(35)
    self.wait_element(By.ID, 'email')
    self.driver.find_element_by_id('email').send_keys('sumanta.span@gmail.com')
    self.driver.find_element_by_id('pass').send_keys('Borqs@1234')
    self.driver.find_element_by_id('loginbutton').click()
    time.sleep(15)
    if self.is_element_present(By.XPATH, '//span[text() ="Continue"]'):
        self.driver.find_element_by_xpath('//span[text() ="Continue"]').click()
        time.sleep(15)
        driver.refresh()
    if self.is_element_present(By.XPATH, '(/html/body/div[1]/div/div[1]/span)[1]'):
        test_result = 1
    else:
        test_result = 0


if __name__ == "__main__":
    suite = unittest.TestLoader().loadTestsFromTestCase(Cpa)
    unittest.TextTestRunner().run(suite)
