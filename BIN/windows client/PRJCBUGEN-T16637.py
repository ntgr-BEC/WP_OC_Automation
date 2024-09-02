# -*- coding: utf-8 -*-

import os, sys, time, re
import xml.dom.minidom as minidom
from seleniumlib import ClientTestCase, unittest, By


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
        try:
            driver.get(self.base_url)
        except Exception as err:
            print ("connect remote PC err by {0}".format(err))
            test_result = 0
            print "finalresult:", test_result
            return
        url_titleName = ''
        self.wait_page_ready()
        i = 0
        # while i <= 5:
        #     time.sleep(60)
        #     url_titleName = self.driver.title
        #     if url_titleName.find('Portal') != -1 or url_titleName.find(self.checkpoint) != -1:
        #         break
        #     i += 1
        # print url_titleName
        # if url_titleName.find('Portal') != -1:
        time.sleep(10)
        self.driver.find_element_by_id('registerName').send_keys('testWP')
        time.sleep(1)
        self.driver.find_element_by_id('registerEmail').send_keys('apwebportaltest1@mailinator.com')
        time.sleep(1)
        self.driver.find_element_by_id('registerContact').send_keys('12345')
        time.sleep(1)
        self.driver.find_element_by_id('tableRegisterButton').click()
        time.sleep(30)
        i = 0
        driver.refresh()
        if self.is_element_present(By.XPATH, '(/html/body/div[1]/div/div[1]/span)[1]'):
            test_result = 1

        print "finalresult:", test_result


if __name__ == "__main__":
    suite = unittest.TestLoader().loadTestsFromTestCase(Cpa)
    unittest.TextTestRunner().run(suite)
