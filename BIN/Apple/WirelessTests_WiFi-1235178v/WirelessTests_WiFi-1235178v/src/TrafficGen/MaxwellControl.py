#!/usr/bin/python
# encode as UTF-8

'''
 This example opens a connection to Maxwell, issues some simple query and
 sets up some simple impairment and then exits. Note that Maxwell will
 continue to perform the impairments even after the script disconnects.
 Add the location of Maxwell Python interface module to the Python search
 path so the "import servercomm" succeeds. This is the location of that
 library script on Maxwell; when controlling Maxwell with this script
 from another machine, you would change the path to whereever you copied
 our module file.

Updated by Quint Friesen Jan-23-2012
Written by Dmitry Halavin
'''

from ThirdPartyLibraries import maxwellcomm
from plistlib import readPlistFromString
from LogInit import LogIt

class MaxwellControl(object):
    '''
    Class to control the Maxwell traffic shaper
    '''
    def __init__(self):
        self.srv = False
        self.logger = LogIt()
        #we can load the plist here too since it is not huge
        self.confSet = readPlistFromString(self.__GetConfList())


    def Connect(self, host, port=7021):
        self.srv = maxwellcomm.ServerControl(host, port)
        self.srv.ServerConnect()
        self.logger.Info('Connected to Maxwell')

    def Configure(self, trafficType, configDict=False):
        '''
        Create connection to the server and load up the config requested
        Takes a ip address for server arg
        takes a string for traffic type
        if custom configDict is desired, it must have a unique name and be passed
            in as a dictionary matching the ImpairSet format. this gets merged
            with the current list of dicts and then chosen by the name supplied
            in trafficType
        '''

        self.srv.SetImpair(0, 0, "", 0)
        self.srv.SetImpair(0, 1, "", 0)
        self.srv.ClearAllFlows()
        self.logger.Info('Impair sets reset, and flows cleared')
        if configDict:
            config = configDict
        else:
            config = self.confSet[trafficType]
        self.LoadImpairment(config)

    def LoadImpairment(self, config):
        '''
        loads the impairment desired onto the box. See dict below for types
        and samples
        '''
        self.impairSet = maxwellcomm.ImpairSet() # Create the object.
        self.impairSet.rate.rate = config["rate"]
        self.impairSet.rate.qlen = 1000000
        if config["rate"] > 0:
            self.impairSet.rate.enabled = True
        self.impairSet.drop.pct = config["drop"] / 2
        if config["drop"] > 0:
            self.impairSet.drop.enabled = True
        self.impairSet.delay.constDelay = config["delay"] / 2
        if config["delay"] > 0:
            self.impairSet.delay.enabled = True
        self.impairSet.jitter.pct = config["jitter"]["pct"] / 2
        self.impairSet.jitter.jitterType = config["jitter"]["jitterType"]
        self.impairSet.jitter.mean = config["jitter"]["mean"] / 2
        self.impairSet.jitter.stdDev = config["jitter"]["stdDev"] / 2
        self.impairSet.jitter.enabled = True
        self.logger.Info('Impair set configured. Rate: %s, Drop: %s, Delay: %s' % (config["rate"], config["drop"], config["delay"]))

    def Start(self):
        '''
        Start the impairment
        '''
        self.srv.SetImpair(0, 0, self.impairSet, 0)
        self.srv.SetImpair(0, 1, self.impairSet, 0)
        self.logger.Info("Impair started")

    def Reset(self):
        '''
        reset the box to clean slate
        '''
        self.srv.SetImpair(0, 0, "", 0)
        self.srv.SetImpair(0, 1, "", 0)
        self.srv.ClearAllFlows()
        self.logger.Info('Impair sets reset, and flows cleared')

    def Disconnect(self):
        '''
        Disconnect
        '''
        self.srv.ServerDisconnect()
        self.logger.Info('Disconnected from Maxwell')

    def GetCurrentImpairment(self):
        return self.srv.GetImpair(0, 1)

    def __GetConfList(self):
        return '''<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE plist PUBLIC "-//Apple//DTD PLIST 1.0//EN" "http://www.apple.com/DTDs/PropertyList-1.0.dtd">
<plist version="1.0">
<dict>
    <key>dsl</key>
    <dict>
        <key>rate</key>
        <integer>1500000</integer>
        <key>drop</key>
        <integer>1</integer>
        <key>delay</key>
        <integer>50000</integer>
        <key>jitter</key>
        <dict>
            <key>pct</key>
            <integer>25</integer>
            <key>jitterType</key>
            <string>gaussian</string>
            <key>mean</key>
            <integer>5000</integer>
            <key>stdDev</key>
            <integer>5000</integer>
        </dict>
    </dict>
    <key>cablefast</key>
    <dict>
        <key>rate</key>
        <integer>15000000</integer>
        <key>drop</key>
        <integer>1</integer>
        <key>delay</key>
        <integer>20000</integer>
        <key>jitter</key>
        <dict>
            <key>pct</key>
            <integer>25</integer>
            <key>jitterType</key>
            <string>gaussian</string>
            <key>mean</key>
            <integer>2000</integer>
            <key>stdDev</key>
            <integer>1000</integer>
        </dict>
    </dict>
    <key>cableslow</key>
    <dict>
        <key>rate</key>
        <integer>5000000</integer>
        <key>drop</key>
        <integer>1</integer>
        <key>delay</key>
        <integer>23000</integer>
        <key>jitter</key>
        <dict>
            <key>pct</key>
            <integer>25</integer>
            <key>jitterType</key>
            <string>gaussian</string>
            <key>mean</key>
            <integer>3000</integer>
            <key>stdDev</key>
            <integer>1000</integer>
        </dict>
    </dict>
    <key>satellite</key>
    <dict>
        <key>rate</key>
        <integer>1000000</integer>
        <key>drop</key>
        <integer>2</integer>
        <key>delay</key>
        <integer>500000</integer>
        <key>jitter</key>
        <dict>
            <key>pct</key>
            <integer>25</integer>
            <key>jitterType</key>
            <string>gaussian</string>
            <key>mean</key>
            <integer>100000</integer>
            <key>stdDev</key>
            <integer>5000</integer>
        </dict>
    </dict>
    <key>schlc11</key>
    <dict>
        <key>rate</key>
        <integer>4500000</integer>
        <key>drop</key>
        <integer>3</integer>
        <key>delay</key>
        <integer>100000</integer>
        <key>jitter</key>
        <dict>
            <key>pct</key>
            <integer>25</integer>
            <key>jitterType</key>
            <string>gaussian</string>
            <key>mean</key>
            <integer>50000</integer>
            <key>stdDev</key>
            <integer>25000</integer>
        </dict>
    </dict>
</dict>
</plist>'''

