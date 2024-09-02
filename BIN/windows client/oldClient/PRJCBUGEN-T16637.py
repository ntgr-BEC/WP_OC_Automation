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
        while i <= 5:
            time.sleep(60)
            url_titleName = self.driver.title
            if url_titleName.find('Portal') != -1 or url_titleName.find(self.checkpoint) != -1:
                break
            i += 1
        print url_titleName
        if url_titleName.find('Portal') != -1:
            self.driver.find_element_by_id('registerName').send_keys('testWP')
            time.sleep(1)
            self.driver.find_element_by_id('registerEmail').send_keys('apwebportaltest1@mailinator.com')
            time.sleep(1)
            self.driver.find_element_by_id('registerContact').send_keys('12345')
            time.sleep(1)
            self.driver.find_element_by_id('tableRegisterButton').click()
            time.sleep(100)
            i = 0
            while i <= 15:
                driver.get(self.base_url)
                self.wait_page_ready()
                time.sleep(60)
                url_titleName = self.driver.title
                if url_titleName.find(self.checkpoint) != -1:
                    break
                i += 1
            url_titleName = self.driver.title
            print url_titleName
        else:
            url_titleName = ''
        print ("url_titleName=%s" % url_titleName)
        UnopenError = "Problem loading page"
        if self.expectresult == "enable":
            if url_titleName != self.checkpoint:
                test_result = 0
                print ("Expect can open the url:%s , And the Expect title was:%s . But real title was: %s ." % (
                    self.urlValue, self.checkpoint, url_titleName))
                self.capturescreen()
        else:
            if url_titleName.find(self.checkpoint) == -1:
                test_result = 0
                print ("Expect can't open the url:%s , And the Expect title was:%s . But real title was: %s ." % (
                    self.urlValue, UnopenError, url_titleName))
                self.capturescreen()

        print "finalresult:", test_result


if __name__ == "__main__":
    suite = unittest.TestLoader().loadTestsFromTestCase(Cpa)
    unittest.TextTestRunner().run(suite)
