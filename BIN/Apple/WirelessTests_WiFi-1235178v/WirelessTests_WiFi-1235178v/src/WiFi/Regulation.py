'''
Regulation.py

WirelessAutomation

Created by Dani Gleser 2012-10-19
Copyright (c) 2012 Apple, Inc. All rights reserved.
'''

import os, sys, time, string
import common.WirelessLogging
from common.WirelessException import TestFailError
import subprocess

from PIL import Image
from LogInit import LogIt
from WiFi.Control import Control
from common.Scripter import screenshot
from WiFi.LogParse import WiFiLogs



class Regulation(object):
    '''
    Class to get the FCCID regulatory information from a device an compare it the known list of regulatory information.
    '''
    def __init__(self, dut):
        '''
        Normally init should not take args, but in this case is is expedient to do so
        '''
        self.dut = dut
        self.logger = LogIt()
        self.logpathroot = self._getTestLogsPathABS()
        self.deviceScreenshotPath = "/private/var/mobile/Media/DCIM/100APPLE/"
        self.TESSERACT_PATH = '/opt/local/bin/tesseract'
        out,err = subprocess.Popen(['/usr/bin/which', 'tesseract'], stdout=subprocess.PIPE, stderr=subprocess.PIPE).communicate()
        if err != "" or out == "":
            self.logger.Warning("tesseract not found, out: %s, err: %s" % (out, err))
        else:
            self.TESSERACT_PATH = out.strip()

    def RemoveScreenShots(self):
        '''
        Removes the old screenshots from the device
        '''
        self.logger.Info("Remove Old Screenshots")
        cmd = '/bin/rm ' + self.deviceScreenshotPath + '*.*'
        self.logger.Info("remove screenshot cmd: % s" % cmd)
        result = self.dut.getOS().Execute(cmd)
        self.logger.Info("remove screenshot result: % s" % result)
        output = str(result.standardOutput)

    def GetScreenShot(self):
        '''
        Navigates to the Settings > General > about > Legal > Regulation screen
        And takes a screehshot of the screen.
        '''
        self.logger.Info("Taking Screenshot")
        cmd1 = 'scripter -c "UIATarget.localTarget().setDeviceOrientation(UIA_DEVICE_ORIENTATION_PORTRAIT);"'
        result = self.dut.getOS().Execute(cmd1, block=True, runAsRoot=True)
        self.logger.Info("Set screen orientation to portrate: %s" % result)
        time.sleep(5)
        cmd2 = "scripter -i \"Settings.js\" -c \"settings.launchIfNotActive(); settings.navigateNavigationViews(['General', 'About', 'Legal','Regulatory'], {returnToTopLevel:true})\""
        result = self.dut.getOS().Execute(cmd2, block=True, runAsRoot=True)
        self.logger.Info("Opened the regulatory screen: %s" % result)
        screenshot_name = screenshot(self.dut, "RegScreenShot", "RegScreenShot")
        self.logger.Info("getScreenshot result: %s " % screenshot_name)
        self.logger.Info("Screenshot Name: %s" % screenshot_name)
        return screenshot_name

    def ScreenShotPath(self):
        '''
        Returns the path to the screen shot file
        '''
        self.logger.Info("Get the path of the Screenshot")
        logpath = os.path.abspath(common.WirelessLogging.SharedLogger.getTestLogsPath(self.dut))
        screenshotpath = os.path.join(logpath, "Screenshots")
        self.logger.Info("Screenshot Path: %s" % screenshotpath)
        return screenshotpath

    def ScreenShotFile(self):
        '''
        Locates and returns the Screehshot file name and path
        '''
        self.logger.Info("Getting the Screenshot File and path")
        screenshotpath = self.ScreenShotPath()
        dirList = os.listdir(screenshotpath)
        self.logger.Info("Directory Files List: %s" % dirList)
        for fname in dirList:
            self.logger.Info("fname: %s" % fname)
            if "RegScreenShot" in fname:
                self.logger.Info("ScreenShot file found: %s" % fname)
                screenshotfile = fname
            else:
                self.testFail = self.testFail + 1
                self.testResults.append('%s - Test: Failed to find the Screen shot file in %s - Fail' % (self.dut.hardware, screenshotpath))
                self.logger.Fail('%s: Failed to find Screen shot file in %s' % (self.testname, screenshotpath))
        result = screenshotpath + "/" + screenshotfile
        return result

    def RunOCR(self, screenshotfile):
        '''
        Uses tesseract on the image file to do an OCR and save the restuls in a text file
        command line example: tesseract IMG_0001.png txt2 -l eng.
        '''
        self.logger.Info("Run the OCR software on the Screenshot")
        screenshotPath = self.ScreenShotPath()
        self.logger.Info("RunOCR - Screen Shot Path:%s " % screenshotPath)
        if not os.path.exists(screenshotfile):
            raise TestFailError("Screen Shot File:%s does not exist" % (screenshotfile))
        destfile = screenshotPath + "/text"
        cmd = [self.TESSERACT_PATH , screenshotfile, destfile , "-l", "eng"]
        self.logger.Info("RunOCR - Tesseract Command:\n %s" % cmd)
        try:
            resultOut, resultErr = subprocess.Popen(cmd, stdout=subprocess.PIPE, stderr=subprocess.PIPE).communicate()
        except Exception as e:
            raise TestFailError, TestFailError("Run OCR Error %s" % e)
        self.logger.Info("RunOCR - OCR Results: %s / %s", (resultOut, resultErr))
        if not os.path.exists(destfile.strip() + ".txt"):
            raise TestFailError("Screen Shot File:%s does not exist, Error:%s" % (destfile + ".txt",e))
        self.logger.Info("RunOCR - Destination File:%s " % (destfile + ".txt"))
        return destfile + ".txt"

    def FindFCCIDinTextFile(self, ocrtextfile):
        '''
        Finds the FCC ID from the text file created by tesseract, otherewise None is returned.
        '''
        valueStripped = None
        self.logger.Info("Finding the FCC ID in the OCR Text File")
        ocrfile = open(ocrtextfile)
        for line in ocrfile:
            self.logger.Info("OCR File line: %s" % line)
            if ("FCC ID" in line) or ("FCCID" in line):
                key, value = line.split(':')
                self.logger.Info("Found:%s" % value.strip())
                # valueUnicode = unicode(value, errors='ignore')
                # self.logger.Info("Found Printable:%s" % repr(valueUnicode))
                # valueStripped = filter(lambda x: x in string.printable, valueUnicode)
                ocrfile.close()
                return string(value.strip())
        ocrfile.close()
        self.logger.Fail("Failed to find the FCCID in the OCR output file: %s" % ocrtextfile)
        return valueStripped

    def CompareFCCID(self, result, expected):
        '''
        compare the FCC ID from the expected plist and the FCC ID from the Regulation screen.
        '''
        self.logger.Info("Compare FCC ID's")
        if result in expected:
            self.logger.Info("FCC ID Matches")
            return True
        else:
            self.logger.Fail("Got FCC ID: %s Expected FCC ID: %s" % (result, expected))
            return False

    def CropScreenShot(self, screenshotfile):
        '''
        Need to install pip then install pil
        sudo easy_install pip
        sudo pip install pil
        '''
        self.logger.Info("Crop the Screenshot and remove the menu")
        modifyer = 2
        cropedfile = screenshotfile.strip(".png") + "-croped.png"
        ipad = Image.open(screenshotfile)
        width, height = ipad.size
        cropwidth = width / modifyer
        self.logger.Info("Crop width: %s " % cropwidth)
        box = (cropwidth, 0, width, height)
        self.logger.Info("Image size: %s Image Mode: %s Image Type: %s" % (ipad.size, ipad.mode, ipad.format))
        cropedimage = ipad.crop(box)
        cropedimage.save(cropedfile)
        return cropedfile

    def EnlargeScreenShot(self, screenshotfile):
        '''
        Need to increase the size of the screen to for better OCR
        '''
        self.logger.Info("Enlarge the Screenshot")
        largerImageFile = screenshotfile.strip(".png") + "-larger.png"

        img = Image.open(screenshotfile)
        width, height = img.size
        factor = 2.0
        newWidth = int(width * factor)
        newHeight = int(height * factor)
        imgNew = img.resize((newWidth, newHeight), Image.ANTIALIAS)
        self.logger.Info("New image size: width: %s height: %s " % (newWidth, newHeight))
        imgNew.save(largerImageFile, dpi=(600, 600))
        return largerImageFile

    def _getTestLogsPathABS(self):
        ''' returns the abolute path of the getTestLogsPath '''
        return os.path.abspath(self.logger.getTestLogsPath())

    def __GetDut(self, dut=False):
        '''
        internal function to validate dut
        '''
        if not dut:
            return self.dut
        return dut
    def __del__ (self):
        print "Regulation: Regulation object deleted"
