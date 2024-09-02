'''
PersonalHotspot.py
WirelessAutomation

Created by Quint Friesen 2011-1-11
Copyright (c) 2011 Apple. All rights reserved.
'''
import common.WirelessLogging as WirelessLogging
import time
import re
from common.Scripter import scripter
from common.WirelessException import TestFailError

class PersonalHotspot(object):
    '''
    Class to control and test Personal Hotspot feature
    '''
    def __init__(self, dut, password='12345678'):
        self.logger = WirelessLogging.SharedLogger
        self.dut = dut
        self.password = password

    def Configure(self, dut, password='12345678'):
        '''
        Method to reuse the object with a new device
        '''
        self.dut = dut
        self.password = password

    def Start(self):
        '''
        Starts the hotspot
        '''
        self.logger.Info('Try unload the plist before Enable/Diable Tethering')
        self.dut.getOS().Execute('launchctl unload /System/Library/LaunchDaemons/com.apple.usbethernetsharing.plist', runAsRoot=True)
        test = ' settingsTest_setPersonalHotspot 1 %s ' % (self.password)
        try:
            result = scripter(self.dut, 'Turn on WiFi Personal Hotspot', test, block=True)
        except TestFailError:
            self.logger.Fail('Failed to turn on Personal Hotspot through UI')
        if result:
            self.logger.Pass('Enable Personal Hotspot on %s Pass.', self.dut.hardware)
        else:
            self.logger.Fail('Enable Personal Hotspot on %s Fail.', self.dut.hardware)
        return result

    def Stop(self):
        '''
        Stops the hotspot
        '''
        self.logger.Info('Try unload the plist before Enable/Diable Tethering')
        self.dut.getOS().Execute('launchctl unload /System/Library/LaunchDaemons/com.apple.usbethernetsharing.plist', runAsRoot=True)
        try:
            result = scripter(self.dut, 'Turn off WiFi Personal Hotspot', ' settingsTest_setPersonalHotspot 0', block=True)
        except TestFailError:
            self.logger.Fail('Failed to turn off Personal Hotspot through UI')
        if result:
            self.logger.Pass('Disable Personal Hotspot on %s Pass.', self.dut.hardware)
        else:
            self.logger.Fail('Disable Personal Hotspot on %s Fail.', self.dut.hardware)
        return result

    def OnTest(self):
        '''
        Enable Personal Hotspot and WiFi should not join any WiFi Network
        '''
        result = self.Start()
        if result:
            if self.dut.getWiFi().WaitForNetwork(timeout=5):
                actualNetwork = self.dut.getWiFi().GetNetwork()
                raise TestFailError, TestFailError('WiFi still connected with %s after enable Personal Hotspot', actualNetwork.ssid)
            else:
                self.logger.Pass('WiFi does not join any network after Personal Hotspot is enabled')

    def OffTest(self, network):
        '''
        Disable Personal Hotspot and WiFi should join previous joined WiFi network
        '''
        result = self.Stop()
        # Set device to SpringBoard
        self.dut.ui.springboard.DismissAll()
        self.dut.ui.springboard.Home()
        if result:
            if self.verify_connection(self.dut, network):
                self.ping_site(self.dut, self.ping_destination)
            else:
                self.logger.Fail('WiFi does not join the network %s after Personal Hotspot is disabled', network.ssid)

    def Timer(self, ap, pingDestination):
        '''
        Tests the time it takes to turn off hotspot and connect to network
        Takes the AP it should connect to, and a ping destination
        '''
        test = ' settingsTest_personalHotspotTimer %s %s' % (ap.ssid, self.password)
        try:
            result = scripter(self.dut, 'Personal Hotspot Timer', test, block=True)
        except TestFailError:
            self.logger.Fail('Failed the Personal Hotspot timer test through UI')
        if result:
            self.logger.Pass('WiFi Personal Hotspot 90 seconds timer test Pass')
            if self.VerifyConnection(self.dut, ap):
                self.PingSite(self.dut, pingDestination)
        else:
            self.logger.Fail('WiFi Personal Hotspot 90 seconds timer test Fail')
            if self.VerifyConnection(self.dut, ap):
                self.logger.Info('Did not join the network %s right after the 90 seconds timer, but joined the network later', ap.ssid)
            else:
                self.logger.Fail('Did not join the network %s right after the 90 seconds timer, and did not join the network later', ap.ssid)
        return result

    def DisconnectClients(self, ap, pingDestination):
        '''
        Tests disconnecting clients
        Takes the AP it should connect to, and a ping destination
        '''
        self.logger.Info('Try to disconnect Personal Hotspot Clients from WiFi Preferences')
        test = ' settingsTest_disconnectPersonalHotspotClients'
        try:
            result = scripter(self.dut, 'Disconnect Personal Hotspot Clients', test, block=True)
        except TestFailError:
                self.logger.Fail('Failed to disconnect Personal Hotspot clients through UI')
        if result:
            self.logger.Pass('Disconnect Personal Hotspot Clients Test Pass')
            self.dut.ui.springboard.DismissAll()
            self.dut.ui.springboard.Home()
            if self.VerifyConnection(self.dut, ap):
                self.PingSite(self.dut, pingDestination)
        else:
            self.logger.Fail('Disconnect Personal Hotspot Clients Test Fail')
        return result

    def ClientNumber(self):
        '''
        returns the number of clients connected to the Personal Hotspot
        '''
        self.logger.Info('Check how many personal hotspot clients joined use apple80211 tool')
        output = self.dut.getOS().Execute('apple80211 --stalist')
        if output is not None:
            stationResult = output.standardOutput
            if output.standardError != '':
                self.logger.Fail('Check personal hotspot clients failed, apple80211 reports error: %s', output.standardError)
                return -1
        else:
            self.logger.Fail('Check personal hotspot clients failed, could not get any result from apple80211 --stalist')
            return -1
        if stationResult == 'Stations:\nNo stations associated\n':
            self.logger.Info('Personal Hotspot has no client joined, the apple80211 --stalist reports: \n%s', stationResult)
            return 0
        else:
            result = re.split('Station', stationResult)
            if len(result) >= 3:
                i = len(result) - 2
            else:
                i = 0
            self.logger.Info('Personal Hotspot has %s client joined, the apple80211 --stalist reports: \n%s', i, stationResult)
            return i

    def ClientNumberCheck(self, number):
        '''
        Verifies the number of clients connected is the expected number
        Takes a expected number of clients as an arg
        '''
        currentNumber = self.ClientNumber()
        if number == currentNumber:
            self.logger.Pass('There are %s clients joined with the Personal Hotspot', number)
        else:
            self.logger.Fail('There are %s clients joined with Personal Hotspot, it should be %s', currentNumber, number)

    def DisplayOffTimer(self):
        '''
        Tests timer off function
        '''
        if self.Start(self.password):
            self.logger.Info('Turn off %s display', self.dut.hardware)
            self.dut.getOS().Execute('interact -uiautomation YES -i System.js \'UIATarget.localTarget().lock()\'')
            syslogResult = ''
            timerString = 'Starting MIS idle timer'
            expireString = 'MIS idle timer expired. MIS is Enabled, associated clients = 0, discovery is Disabled'
            stopString = 'Stopping MIS session'
            #check the timer string from syslog
            cmd = 'syslog -k Time ge -10'
            resultDict = self.dut.getOS().Execute(cmd)
            if resultDict is not None:
                syslogResult = resultDict.standardOutput
                if syslogResult.find(timerString) >= 0:
                    self.logger.Pass('Find "%s" in syslog, timer stated', timerString)
                else:
                    self.logger.Fail('Did not find "%s" in syslog, timer did not fire', timerString)
            else:
                self.logger.Fail('Get Personal Hotspot Timer information from syslog failed')
            self.logger.Info('Waiting for 90 seconds for the timer to expire')
            time.sleep(90)
            resultDict = self.dut.getOS().Execute(cmd)
            if resultDict is not None:
                syslogResult = resultDict.standardOutput
                if syslogResult.find(expireString) >= 0:
                    self.logger.Pass('Find "%s" in syslog, timer expired', expireString)
                    if syslogResult.find(stopString) >= 0:
                        self.logger.Pass('Find "%s" in syslog, Personal Hotspot stopped', stopString)
                    else:
                        self.logger.Fail('Did not find "%s" in syslog', stopString)
                else:
                    self.logger.Fail('Did not find "%s" in syslog', expireString)
            else:
                self.logger.Fail('Get Personal Hotspot Timer information from syslog failed')
        else:
            self.logger.Fail('Could not enable Personal Hotspot, skip the Personal Hotspot test by turning off display')
        self.dut.ui.springboard.DismissAll()
        self.dut.ui.springboard.Home()
