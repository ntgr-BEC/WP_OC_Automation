#!/usr/bin/env python2.3
'''
WirelessAutomation
Copyright (c) 2012 Apple. All rights reserved.
'''
from LogInit import LogIt
from common.WirelessException import TestFailError
from common.Scripter import scripter, interact, command
from time import strftime

class UI_Control(object):
    def __init__(self, dut):
        self.logger = LogIt()
        self.dut = dut

    def RunScripterTest(self, test, testDescription='UI Test'):
        '''
        Execute one UIAutomation test
        '''
        test = ' %s' % (test)
        try:
            result = scripter(self.dut, testDescription, test, block=True)
        except TestFailError:
            self.logger.Info('%s Failed', testDescription)
            result = False
        return result

    def RunInteractCmd(self, cmd, testDescription='Interact UI Test'):
        '''
        Execute one interact command
        '''
        cmd = "\"%s\"" % cmd
        result = interact(self.dut, testDescription, cmd, block=True)
        try:
            result = interact(self.dut, testDescription, cmd, block=True)
        except TestFailError:
            self.logger.Info('%s Failed', testDescription)
            result = False
        return result

    def PersonalHotspotOnUI(self, password):
        '''
        Enable Personal Hotspot
        '''
        self.dut.getOS().DisableUSBEthernetSharing()
        test = " settingsTest_setPersonalHotspot 1 %s " % (password)
        testDescription = "Enable Personal Hotspot"
        result = self.RunScripterTest(test, testDescription)
        if result:
            self.logger.Info("%s on %s Pass.", testDescription, self.dut.hardware)
        else:
            self.logger.Info("%s on %s Fail.", testDescription, self.dut.hardware)
        return result

    def PersonalHotspotOffUI(self):
        '''
        Disable Personal Hotspot
        '''
        self.dut.getOS().DisableUSBEthernetSharing()
        test = " settingsTest_setPersonalHotspot 0 "
        testDescription = "Disable Personal Hotspot"
        result = self.RunScripterTest(test, testDescription)
        if result:
            self.logger.Info("%s on %s Pass.", testDescription, self.dut.hardware)
        else:
            self.logger.Info("%s on %s Fail.", testDescription, self.dut.hardware)
        return result

    def TurnOffMailPushUI(self):
        '''
        Turn of Mail Push
        '''
        test = " settingsTest_changeSingleSwitch 'Mail, Contacts, Calendars' 'Fetch New Data' Push 0"
        testDescription = "Turn off Mail Push"
        result = self.RunScripterTest(test, testDescription)
        if result:
            self.logger.Info("%s on %s Pass.", testDescription, self.dut.hardware)
        else:
            self.logger.Info("%s on %s Fail.", testDescription, self.dut.hardware)
        return result

    def SpringBoardQuitAppUI(self, appBundleName):
        '''
        Kill the AP using appBundleName. 
        To get the app bundle name: launch the app and then run: interact 'app.bundleID()' 
        '''
        test = " springboardTest_quitApp '%s'" % (appBundleName)
        testDescription = "Quit App %s" % (appBundleName)
        result = self.RunScripterTest(test, testDescription)
        if result:
            self.logger.Info("%s on %s Pass.", testDescription, self.dut.hardware)
        else:
            self.logger.Info("%s on %s Fail.", testDescription, self.dut.hardware)
        return result

    def SetFaceTimeUI(self, enable=True):
        '''
        Enable or Disable FaceTime through Settings - > FaceTime
        '''
        if enable:
            test = " settingsTest_changeSingleSwitch FaceTime FaceTime 1"
            testDescription = "Enable FaceTime"
        else:
            test = " settingsTest_changeSingleSwitch FaceTime FaceTime 0"
            testDescription = "Disable FaceTime"
        result = self.RunScripterTest(test, testDescription)
        if result:
            self.logger.Info("%s on %s Pass.", testDescription, self.dut.hardware)
        else:
            self.logger.Info("%s on %s Fail.", testDescription, self.dut.hardware)
        return result

    def SetWiFiUI(self, on=True, logFail=True):
        '''
        Turn on WiFi through UI and leave device at WiFi Preferences
        '''
        if on:
            test = ' settingsTest_setWifi 1'
            testDescription = "Turn WiFi On"
        else:
            test = ' settingsTest_setWifi 0'
            testDescription = "Turn WiFi Off"
        result = self.RunScripterTest(test, testDescription)
        if result:
            self.logger.Info("%s on %s Pass.", testDescription, self.dut.hardware)
        else:
            if logFail:
                self.logger.Fail("%s on %s Fail.", testDescription, self.dut.hardware)
            else:
                self.logger.Info("%s on %s Fail.", testDescription, self.dut.hardware)
        return result

    def CheckWapiUI(self, logFail=True):
        '''
        Check China device UI for WLAN string
        '''
        if not self.dut.isChinaDevice():
            self.logger.Warning("%s is not a China device, skip this test", self.dut.hardware)
            return True
        test = 'settingsTest_checkWAPIUI'
        testDescription = 'Check WAPI UI'
        result = self.RunScripterTest(test, testDescription)
        if result:
            self.logger.Info("%s on %s Pass.", testDescription, self.dut.hardware)
        else:
            if logFail:
                self.logger.Fail("%s on %s Fail.", testDescription, self.dut.hardware)
            else:
                self.logger.Info("%s on %s Fail.", testDescription, self.dut.hardware)
        return result

    def JoinWapiEnterpriseUI(self, ap, logFail=True):
        '''
        Join WAPI Enterprise network through UI
        '''
        if not self.dut.isChinaDevice():
            self.logger.Warning("%s is not a China device, skip this test", self.dut.hardware)
            return True
        try:
            self.dut.ui.settings.wifi.Join(ap.ssid, '', 'WAPI enterprise', '', '')
        except TestFailError:
            if logFail:
                self.logger.Fail("Join WAPI network %s failed", ap.ssid)
            else:
                self.logger.Info("Join WAPI network %s failed", ap.ssid)
            return False
        return True

    def InstallProfileUI(self, password='', timeout=10, logFail=True):
        '''
        Install profile through UI. Profile window need to be promt already
        '''
        test = ' settingsTest_installProfile %s %s' % (password, timeout)
        testDescription = "Install profile"
        result = self.RunScripterTest(test, testDescription)
        if result:
            self.logger.Info("%s on %s Pass.", testDescription, self.dut.hardware)
        else:
            if logFail:
                self.logger.Fail("%s on %s Fail.", testDescription, self.dut.hardware)
            else:
                self.logger.Info("%s on %s Fail.", testDescription, self.dut.hardware)
        return result

    def DeleteProfileUI(self, profileName='', logFail=True):
        '''
        Delete profile through UI, delete all profiles if profileName is ''
        '''
        if profileName == '':
            test = ' settingsTest_deleteProfile'
            testDescription = "Delete all profile"
        else:
            test = ' settingsTest_deleteProfile %s' % (profileName)
            testDescription = "Delete profile %s" % (profileName)
        result = self.RunScripterTest(test, testDescription)
        if result:
            self.logger.Info("%s on %s Pass.", testDescription, self.dut.hardware)
        else:
            if logFail:
                self.logger.Fail("%s on %s Fail.", testDescription, self.dut.hardware)
            else:
                self.logger.Info("%s on %s Fail.", testDescription, self.dut.hardware)
        return result

    def DownloadProfileFromEmail(self, emailTitle, profileName, emailAccount, emailFolder, timeout=30, waitForMailTimeout=30, logFail=True):
        '''
        Download Mail attachment
        '''
        test = ' mailTest_downloadMailAttachment "%s" "%s" "account:%s" "folder:%s" "timeout:%s" "noVerification:true" "waitForMailTimeout:%s"' % (emailTitle, profileName, emailAccount, emailFolder, timeout, waitForMailTimeout)
        print test
        testDescription = "download profile from email"
        result = self.RunScripterTest(test, testDescription)
        if result:
            self.logger.Info("%s on %s Pass.", testDescription, self.dut.hardware)
        else:
            if logFail:
                self.logger.Fail("%s on %s Fail.", testDescription, self.dut.hardware)
            else:
                self.logger.Info("%s on %s Fail.", testDescription, self.dut.hardware)
        return result

    def CreateEmailAccount(self, typeName, accountName, emailAccount, password, logFail=True):
        '''
        Create Email Account
        typeName: Gmail; accountName: "WiFi ABD", emailAccount:xxx@gmail.com, password: password
        '''
        test = ' settingsTest_createEmailAccount "%s" "%s" "%s" "%s"' % (typeName, accountName, emailAccount, password)
        print test
        testDescription = "Create email account"
        result = self.RunScripterTest(test, testDescription)
        if result:
            self.logger.Info("%s on %s Pass.", testDescription, self.dut.hardware)
        else:
            if logFail:
                self.logger.Fail("%s on %s Fail.", testDescription, self.dut.hardware)
            else:
                self.logger.Info("%s on %s Fail.", testDescription, self.dut.hardware)
        return result


    def EnablePushLoggingUI(self, logFail=True):
        test = " settingsTest_changeSingleSwitch 'Internal Settings' Messages 'Push Logging' 1"
        testDescription = "Turn on Push Notification Logging"
        result = self.RunScripterTest(test, testDescription)
        if result:
            self.logger.Info("%s on %s Pass.", testDescription, self.dut.hardware)
        else:
            if logFail:
                self.logger.Fail("%s on %s Fail.", testDescription, self.dut.hardware)
            else:
                self.logger.Info("%s on %s Fail.", testDescription, self.dut.hardware)
        return result


    def GetWiFiBarUI(self, logFail=True):
        '''
        Return number of WiFi Bars showed on UI
        '''
        command = "/usr/local/bin/scripter -c  \"UIATarget.localTarget().frontMostApp().statusBar().elements().firstWithPredicate(\\\"name contains 'Wi-Fi'\\\").name()\""
        name = "Get Number of WiFi Bars"
        self.logger.Info(name)

        try:
            result = self.dut.os.Execute(command, True, timeout=10)
            if result is None:
                self.logger.Info('Unable to run %s on the device with %s.' % (name, command))
                return 0

            standardOutput = result.standardOutput
            self.logger.Info(standardOutput)
            logFilename = "scripter_" + name + "_" + strftime('%Y-%m-%d_%H-%M-%S') + ".log"
            self.logger.LogFromStandardOutput(standardOutput, logFilename, hostKey='UIAutomation_log', device=self.dut)

            if standardOutput:
                if standardOutput.find("3 of 3") >= 0:
                    result = 3
                elif standardOutput.find("2 of 3") >= 0:
                    result = 2
                elif standardOutput.find("1 of 3") >= 0:
                    result = 1
            else:
                result = 0
                if logFail:
                    self.logger.Fail("%s on %s Fail.", name, self.dut.hardware)
                else:
                    self.logger.Info("%s on %s Fail.", name, self.dut.hardware)

        except Exception, ex:
            self.logger.Info('There was a problem executing %s with %s: %s' % (name, command, ex))
            result = 0

        return result


    def VerifySSIDUI(self, ssid, logFail=True):
        '''
        Verify if UI shows connected with correct SSID
        '''
        test = " settingsTest_checkSSID '%s'" % ssid
        testDescription = "Check WiFi SSID on UI for :%s" % ssid
        result = self.RunScripterTest(test, testDescription)
        if result:
            self.logger.Info("%s on %s Pass.", testDescription, self.dut.hardware)
        else:
            if logFail:
                self.logger.Fail("%s on %s Fail.", testDescription, self.dut.hardware)
            else:
                self.logger.Info("%s on %s Fail.", testDescription, self.dut.hardware)
        return result

    def VerifyNotConnectedStatusUI(self, status='Not Connected', logFail=True):
        '''
        Check if Settings WiFi status shows as 'Not Connected'
        '''
        cmd = "scripter -i Settings.js -c 'settings = UIAApp.application(\"com.apple.Preferences\"); settings.launchIfNotActive(); settings.getWiFiTableCell().withValueForKey(\"%s\", \"value\").tap(); settings.returnToTopLevel()'" % status
        testDescription = "Check UI shows %s" % status
        try:
            result = command(self.dut, testDescription, cmd, block=True)
        except TestFailError:
            if logFail:
                self.logger.Fail('%s Failed', testDescription)
            else:
                self.logger.Warning('%s Failed', testDescription)
            return False
        if not result:
            if logFail:
                self.logger.Fail('%s Failed', testDescription)
            else:
                self.logger.Warning('%s Failed', testDescription)
        return result

    def ForgetNetworkUI(self, ssid, logFail=True):
        '''
        Forget Network from UI
        '''
        test = " settingsTest_forgetWifi '%s'" % ssid
        testDescription = "Forget Network :%s" % ssid
        result = self.RunScripterTest(test, testDescription)
        if result:
            self.logger.Info("%s on %s Pass.", testDescription, self.dut.hardware)
        else:
            if logFail:
                self.logger.Fail("%s on %s Fail.", testDescription, self.dut.hardware)
            else:
                self.logger.Info("%s on %s Fail.", testDescription, self.dut.hardware)
        return result

    def SetAirplaneModeUI(self, on=True, logFail=True):
        '''
        Turn on/off Airplane mode through UI
        '''
        if on:
            test = ' settingsTest_changeSingleSwitch \"Airplane Mode\" 1'
            testDescription = "Turn Airplane Mode On"
        else:
            test = ' settingsTest_changeSingleSwitch \"Airplane Mode\" 0'
            testDescription = "Turn Airplane Mode Off"
        result = self.RunScripterTest(test, testDescription)
        if result:
            self.logger.Info("%s on %s Pass.", testDescription, self.dut.hardware)
        else:
            if logFail:
                self.logger.Fail("%s on %s Fail.", testDescription, self.dut.hardware)
            else:
                self.logger.Info("%s on %s Fail.", testDescription, self.dut.hardware)
        return result

    def GetIPAddressUI(self, ssid):
        """
        Get the IP address from UI
        """
        cmd = "scripter -i Settings.js -c 'settings = UIAApp.application(\"com.apple.Preferences\"); settings.launchIfNotActive(); settings.getToWiFiNetworkDetailedView(\"%s\"); UIATarget.localTarget().delay(2); if (UIATarget.localTarget().model() == \"iPad\") {settingsTable = settings.mainWindow().tableViews()[1];} else {settingsTable = settings.mainWindow().tableViews()[0];}; value=settingsTable.cells()[2].value()'" % ssid
        result = self.dut.getOS().Execute(cmd, True, True)
        outputResults = result.standardOutput.split('\n')
        self.logger.Info("ip is %s", str(outputResults[len(outputResults) - 2]))
        return str(outputResults[len(outputResults) - 2])

    def GetSubnetMaskUI(self, ssid):
        """
        Get the Subnet Mask address from UI
        """
        cmd = "scripter -i Settings.js -c 'settings = UIAApp.application(\"com.apple.Preferences\"); settings.launchIfNotActive(); settings.getToWiFiNetworkDetailedView(\"%s\"); UIATarget.localTarget().delay(2); if (UIATarget.localTarget().model() == \"iPad\") {settingsTable = settings.mainWindow().tableViews()[1];} else {settingsTable = settings.mainWindow().tableViews()[0];}; settingsTable.cells().firstWithPredicate(\"name BEGINSWITH \\\"Subnet Mask\\\"\").value()'" % ssid
        result = self.dut.getOS().Execute(cmd, True, True)
        outputResults = result.standardOutput.split('\n')
        self.logger.Info("subnet mask is %s", str(outputResults[len(outputResults) - 2]))
        return str(outputResults[len(outputResults) - 2])

    def GetRouterUI(self, ssid):
        """
        Get the Router Mask address from UI
        """
        cmd = "scripter -i Settings.js -c 'settings = UIAApp.application(\"com.apple.Preferences\"); settings.launchIfNotActive(); settings.getToWiFiNetworkDetailedView(\"%s\"); UIATarget.localTarget().delay(2); if (UIATarget.localTarget().model() == \"iPad\") {settingsTable = settings.mainWindow().tableViews()[1];} else {settingsTable = settings.mainWindow().tableViews()[0];}; settingsTable.cells().firstWithPredicate(\"name BEGINSWITH \\\"Router\\\"\").value()'" % ssid
        result = self.dut.getOS().Execute(cmd, True, True)
        outputResults = result.standardOutput.split('\n')
        self.logger.Info("Router is %s", str(outputResults[len(outputResults) - 2]))
        return str(outputResults[len(outputResults) - 2])

    def GetMACAddressUI(self):
        '''
        Get Mac Address from UI
        '''
        from common.MacUtils import IsMACAddressEqual
        if self.dut.isChinaDevice():
            cmd = "scripter -i Settings.js -c 'settings = UIAApp.application(\"com.apple.Preferences\"); settings.launchIfNotActive(); settings.navigateNavigationViews([\"General\", \"About\"], {returnToTopLevel:true}); UIATarget.localTarget().delay(2); if (UIATarget.localTarget().model() == \"iPad\") {settingsTable = settings.mainWindow().tableViews()[1];} else {settingsTable = settings.mainWindow().tableViews()[0];}; settingsTable.cells()[\"WLAN Address\"].value()'"
        else:
            cmd = "scripter -i Settings.js -c 'settings = UIAApp.application(\"com.apple.Preferences\"); settings.launchIfNotActive(); settings.navigateNavigationViews([\"General\", \"About\"], {returnToTopLevel:true}); UIATarget.localTarget().delay(2); if (UIATarget.localTarget().model() == \"iPad\") {settingsTable = settings.mainWindow().tableViews()[1];} else {settingsTable = settings.mainWindow().tableViews()[0];}; settingsTable.cells()[\"Wi-Fi Address\"].value()'"
        result = self.dut.getOS().Execute(cmd, True, True)
        outputResults = result.standardOutput.split('\n')
        mac = str(outputResults[len(outputResults) - 2])
        self.logger.Info("Router is %s", mac)
        if not IsMACAddressEqual(mac, self.dut.wifi_address):
            self.logger.Fail("UI shows WiFi Mac address is %s, system shows it is: %s", mac, self.dut.wifi_address)
            return ""
        return mac

    def VerifyAPInPrefs(self, ssid):
        """
        Verify is the AP showing in Prefs
        """
        cmd = "scripter -i Settings.js -c 'settings = UIAApp.application(\"com.apple.Preferences\"); settings.launchIfNotActive(); settings.returnToTopLevel(); try{settings.getToWiFiNetworkDetailedView(\"%s\")}catch(e) {throw new Error(\"Can not find the WiFi network\")}'" % ssid
        result = self.dut.getOS().Execute(cmd, True, True)
        if "Can not find the WiFi network" in result.standardError:
            self.logger.Info("Did not find the network %s in WiFi Prefs", ssid)
            return False
        else:
            self.logger.Info("Found the network %s in WiFi Prefs", ssid)
            return True
