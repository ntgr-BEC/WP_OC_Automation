# -*- coding: utf-8 -*-

import os, sys, time, re
import xml.dom.minidom as minidom
from seleniumlib import ClientTestCase, unittest, By
from selenium.webdriver.support.ui import WebDriverWait
from selenium.webdriver.support import expected_conditions as EC


class Cpa(ClientTestCase):
    def getText(nodelist):
        rc = []
        for node in nodelist:
            if node.nodeType == node.TEXT_NODE:
                rc.append(node.data)
        return ''.join(rc)

    # get the value from xml file
    DOMTree = minidom.parse("captive.xml")
    testurl = DOMTree.getElementsByTagName("testurl")
    urlNode = DOMTree.getElementsByTagName("testurl")[0]
    urlValue = getText(urlNode.childNodes)
    expectNode = DOMTree.getElementsByTagName("username")[0]
    expectresult = getText(expectNode.childNodes)
    checkNode = DOMTree.getElementsByTagName("userpassword")[0]
    checkpoint = getText(checkNode.childNodes)
    checkpoint = "Rediff.com"

    def test_cpa(self):
        test_result = 1

        driver = self.driver
        wait = WebDriverWait(driver, 30)


        try:
            driver.get(self.base_url)
        except Exception as err:
            print ("connect remote PC err by {0}".format(err))
            test_result = 0
            print "finalresult:", test_result
            return
        # url_titleName = ''
        # self.wait_page_ready()
        # self.capturescreen()
        # i = 0
        # while i <= 60:
        #     time.sleep(5)
        #     url_titleName = self.driver.title
        #     print url_titleName
        #     if url_titleName.find('Portal') != -1 or url_titleName.find(self.checkpoint) != -1:
        #         break
        #     i += 1
        #     print '.',
        # self.capturescreen()
        # if url_titleName.find('Portal') != -1:
        for i in range(5):
            element = wait.until(EC.presence_of_element_located((By.ID, "lSubmit")))
            # self.wait_element(By.ID, '')
            element.click()
            # self.driver.find_element_by_id('lSubmit').click()
            # self.wait_element(By.ID, 'username')
            wait.until(EC.presence_of_element_located((By.ID, "username")))
            self.driver.find_element_by_id('username').send_keys('sumanta.span@gmail.com')
            self.driver.find_element_by_id('password').send_keys('Netgear@123')
            self.driver.find_element_by_xpath('//button[@aria-label="Sign in"]').click()
            time.sleep(10)
            liveUrl = self.driver.current_url
            print("Current Url = ", liveUrl)
            if "rediff" in liveUrl:
                test_result = 1
                break
            #if self.is_element_present(By.ID, 'input__email_verification_pin'):
                #test_result = 1
                #break
            else:
                test_result = 0


        print "finalresult:", test_result


if __name__ == "__main__":
    suite = unittest.TestLoader().loadTestsFromTestCase(Cpa)
    unittest.TextTestRunner().run(suite)
