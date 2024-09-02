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
            self.driver.refresh()
        print url_titleName
        result = True
        if url_titleName.find('Portal') != -1:
            if result and not self.is_element_present(By.ID, 'fSubmit'):
                result = False
                print "Facebook button not existed."
            if result and not self.is_element_present(By.ID, 'lSubmit'):
                result = False
                print "LinkedIn button not existed."
            if result and not self.is_element_present(By.ID, 'tSubmit'):
                result = False
                print "Twitter button not existed."
            if result and not self.is_element_present(By.ID, 'registerName'):
                result = False
                print "Register input box not existed."
        else:
            url_titleName = ''
            result = False
        print ("url_titleName=%s" % url_titleName)
        UnopenError = "Problem loading page"
        if self.expectresult == "enable":
            if url_titleName != self.checkpoint:
                test_result = 0
                print ("Expect can open the url:%s , And the Expect title was:%s . But real title was: %s ." % (
                    self.urlValue, self.checkpoint, url_titleName))
                self.capturescreen()
        else:
            if not result:
                test_result = 0
                print ("Expect can't open the url:%s , And the Expect title was:%s . But real title was: %s ." % (
                    self.urlValue, UnopenError, url_titleName))
                self.capturescreen()

        print "finalresult:", test_result


if __name__ == "__main__":
    suite = unittest.TestLoader().loadTestsFromTestCase(Cpa)
    unittest.TextTestRunner().run(suite)
