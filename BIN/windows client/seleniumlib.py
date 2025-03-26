# -*- coding: utf-8 -*-

import unittest, time, re, os, sys
import xml.dom.minidom as minidom

from selenium import webdriver
from selenium.webdriver.common.by import By
from selenium.webdriver.common.keys import Keys
from selenium.webdriver.support.ui import Select
from selenium.common.exceptions import NoSuchElementException, NoAlertPresentException
from selenium.common.exceptions import WebDriverException, TimeoutException

def printst(msg):
    print time.asctime(), msg

class ClientTestCase(unittest.TestCase):
    def __init__(self, *args, **kwargs):
        unittest.TestCase.__init__(self, *args, **kwargs)
        openurl = self.testurl[0].childNodes[0].data
        openurl = "http://" + openurl
        printst ("openurl: %s" % openurl)
        # self.driver = webdriver.Firefox()
        self.driver = webdriver.Chrome()
        self.driver.implicitly_wait(10)
        self.base_url = openurl
        self.verificationErrors = []
        self.accept_next_alert = True

    def setUp(self):
        pass
    
    def tearDown(self):
        self.driver.quit()
        self.assertEqual([], self.verificationErrors)
        
    def wait_page_ready(self, timeout=300):
        start=time.time()
        printst ('wait_page_ready')
        pre_page = self.driver.page_source
        while (time.time()-start)<timeout:
            time.sleep(4)
            print '.',
            cur_page = self.driver.page_source
            if pre_page == cur_page: break
            pre_page = cur_page
        printst ('done')

    def refresh(self):
        printst ('refresh')
        for i in range(2):
            try:
                self.driver.refresh()
                self.wait_page_ready()
                break
            except WebDriverException, e:
                print e

    def navigate(self, url):
        printst ('navigate to: %s' % url)
        for i in range(2):
            try:
                self.driver.get(url)
                self.wait_page_ready()
                break
            except TimeoutException, e:
                print e
            except WebDriverException, e:
                print e
        
    def is_element_present(self, how, what):
        printst ('is_element_present: %s - %s' % (how, what))
        try:
            self.driver.find_element(by=how, value=what)
        except NoSuchElementException, e:
            return False
        return True

    def wait_element(self, how, what, timeout=30):
        start=time.time()
        printst ('wait_element: %s - %s - %s' % (how, what, timeout))
        while (time.time()-start)<timeout:
            time.sleep(4)
            print '.',
            if self.is_element_present(how, what): return
        self.capturescreen ('wait_element: failed')

    def is_linkelement_present(self, what):
        printst ('is_linkelement_present: %s' % what)
        try:
            self.driver.find_element_by_link_text(what)
        except NoSuchElementException, e:
            return False
        return True
    
    def is_alert_present(self):
        printst ('is_alert_present')
        try:
            self.driver.switch_to_alert()
        except NoAlertPresentException, e:
            return False
        return True
    
    def close_alert_and_get_its_text(self):
        printst ('close_alert_and_get_its_text')
        try:
            alert = self.driver.switch_to_alert()
            alert_text = alert.text
            if self.accept_next_alert:
                alert.accept()
            else:
                alert.dismiss()
            return alert_text
        finally: self.accept_next_alert = True
    
    def capturescreen(self, comment=''):
        fn = '%s.png' % time.strftime("%y%m%d-%H%M%S")
        printst ('capture screen for %s: %s' % (comment, fn))
        self.driver.get_screenshot_as_file(fn)

