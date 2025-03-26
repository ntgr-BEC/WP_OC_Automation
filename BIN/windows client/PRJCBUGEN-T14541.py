# -*- coding: utf-8 -*-

import os, sys, time, re
import xml.dom.minidom as minidom

from selenium.common.exceptions import NoSuchElementException

from seleniumlib import ClientTestCase, unittest, By


class Cpa(ClientTestCase):
    def getText(nodelist):
        rc = []
        for node in nodelist:
            if node.nodeType == node.TEXT_NODE:
                rc.append(node.data)
        return ''.join(rc)

    def is_element_present(self, how, what):
        try:
            self.driver.find_element(by=how, value=what)
        except NoSuchElementException, e:
            return False
        return True

    # get the value from xml file
    DOMTree = minidom.parse("captive.xml")
    testurl = DOMTree.getElementsByTagName("testurl")
    urlNode = DOMTree.getElementsByTagName("testurl")[0]
    urlValue = getText(urlNode.childNodes)
    expectNode = DOMTree.getElementsByTagName("username")[0]
    expectresult = getText(expectNode.childNodes)
    checkNode = DOMTree.getElementsByTagName("userpassword")[0]
    checkpoint = getText(checkNode.childNodes)


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
        url_titleName = None
        self.wait_page_ready()
        i = 0
        print("expectresult =====>>>", self.expectresult)
        while i <= 5:
            time.sleep(60)
            url_titleName = self.driver.title
            if url_titleName.find('Portal') != -1 or url_titleName.find(self.checkpoint) != -1:
                break
            i += 1
        print("url_titleName ====>>>", url_titleName)
        if self.is_element_present(By.CLASS_NAME, 'copy'):
            title = self.driver.find_element_by_class_name('copy').text
            print("title>>>> = ",title )
            if title.find(self.expectresult) == -1:
                test_result = 0
        else:
            test_result = 0
        print ("url_titleName=%s" % url_titleName)

        print "finalresult:", test_result


if __name__ == "__main__":
    suite = unittest.TestLoader().loadTestsFromTestCase(Cpa)
    unittest.TextTestRunner().run(suite)
