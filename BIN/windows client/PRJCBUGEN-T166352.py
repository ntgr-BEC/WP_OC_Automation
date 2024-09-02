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
            self.navigate(self.base_url)
        except Exception as err:
            print ("connect remote PC err by {0}".format(err))
            test_result = 0
            print "finalresult:", test_result
            return
        url_titleName = ''
        self.wait_page_ready()
        self.capturescreen()
        i = 0
        while i <= 60:
            time.sleep(5)
            url_titleName = self.driver.title
            print(url_titleName)
            if url_titleName.find('Portal') != -1 or url_titleName.find(self.checkpoint) != -1:
                break
            i += 1
            print '.',
        self.capturescreen()
        if url_titleName.find('Portal') != -1:
            for i in range(5):
                #self.wait_element(By.ID, 'fSubmit')
                time.sleep(5)
                driver.refresh()
                time.sleep(2)
                driver.refresh()
                time.sleep(5)
                if self.is_element_present(By.XPATH, '//span[@class ="hmsprite logo"]'):
                    time.sleep(5)
                    test_result = 0
                    break
                element = driver.find_element_by_xpath("//*[@id='voucherForm']/div[1]/div[1]/input").send_keys("sumanta")
                # self.is_element_present(By.XPATH, '//*[@id="voucherForm"]/div[1]/div[1]/input').send_keys("test")
                time.sleep(1)
                element = driver.find_element_by_xpath("//*[@id='voucherForm']/div[1]/div[2]/input").send_keys("sumanta")
                # self.is_element_present(By.XPATH, '//*[@id="voucherForm"]/div[1]/div[2]/input').send_keys("test")
                time.sleep(1)
                if self.is_element_present(By.ID, 'submitVoucher'):
                    element = driver.find_element_by_xpath("//*[@id='submitVoucher']").click()
                    time.sleep(10)
                    test_result = 1
                    break

                test_result = 1


        else:
            test_result = 0

        print "finalresult:", test_result

if __name__ == "__main__":
    suite = unittest.TestLoader().loadTestsFromTestCase(Cpa)
    unittest.TextTestRunner().run(suite)
