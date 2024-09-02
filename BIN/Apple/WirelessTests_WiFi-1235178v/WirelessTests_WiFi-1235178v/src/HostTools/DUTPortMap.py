#!/usr/bin/python

#    DUTPortMap tools  module
#    Copyright (c) 2012 Apple. All rights reserved.
#    Created by Jim Collum 1 May 2012
#
#TODO: add filename checking.. make sure you can write to a file before going thru all of the data gathering

import os, sys
from CoreAutomation import CAMEmbeddedDevice
from HostTools.PrimatesControl import PrimatesControl
from LogInit import LogIt
from plistlib import readPlist
import time


try:
    import pydevd
except:
    pass


class DUTPortMap(object):
    '''

    collects port and additional info about devices attached via primate cables

    portmap = DUTPortMap()
    portDict = portmap.CreatePortMap()
    status = portmap.WritePortMap(fileName,portMap)
    portDict = portmap.ReadPortMap(fileName)


        dict  'udid' : {' hardware':         'K95AP',
'                         udid':             'f009c52b3282202a57e1ad11aaa9eb7257fab64f',
                         'serialNum':        'DLXFG06PDMG9',
                         'name':             'J2aAP-41',
                         'port':             '/dev/cu.gorilla-000843',
                         'onSWD':            True
    Note:  can be run as a standalone Utility
    
        python DUTPortMap <filename>
               <filename>    path to a file that will contain the plist describing the connected Primates
                            if left blank, then it defaults to ~/Desktop/myports.plist
    '''

    def _parseDevice(self,device):
        udid = device.udid()
        serialNum = device.serialNumber()
        hardware = device.hardwareModel()
        deviceName = device.deviceName()
        build = str(device.build())
        ecid = str(device.ecid())

        dutMap={}
        dutMap.update({'hardware':hardware})
        dutMap.update({'deviceName':deviceName})
        dutMap.update({'serialNum':serialNum})
        dutMap.update({'onSWD': True})
        dutMap.update({'udid':udid})
        dutMap.update({'build': build })
        dutMap.update({'ecid': ecid })

        return dutMap


    def __init__(self):
        self.allDuts = {}
        self.logger = LogIt()
        self.allDutsCreated = False
        return


    def CreatePortMap(self):
        self.logger.Info('Turning all primates on')
        myPorts = PrimatesControl()
        numAttachedPrimates = myPorts.allPrimateDevices.__len__()
        self.logger.Info('%s primates attached' %str(numAttachedPrimates))
        for each in myPorts.allPrimateDevices:
            self.logger.Info('Turning on port :%s' %str(each))
            try:
                myPorts.ConnectPort(name=each)
            except:
                time.sleep(2)
                myPorts.ConnectPort(name=each)

            try:
                myPorts.ConnectPort(name=each)
            except:
                time.sleep(3)
                myPorts.ConnectPort(name=each)

        time.sleep(8)
        self.allDuts = {}
        devices = CAMEmbeddedDevice.allDevices()
        self.logger.Info('Parsing attached devices')
        for line in devices:
            self.logger.Info('device:%s' % str(line.deviceName()))
            entry = self._parseDevice(line)
            self.allDuts.update({entry['udid']:entry})
        self.logger.Info('disconnecting primates')
        for each in myPorts.allPrimateDevices:
            self.logger.Info('Turning %s OFF' %str( each))
            myPorts.DisconnectPort(name=each)
            time.sleep(2)
        # now mark those that are not on the SWD devices
        time.sleep(12)
        self.logger.Info('Marking all duts not on primate')
        devices = CAMEmbeddedDevice.allDevices()
        for line in devices:
            entry = self._parseDevice(line)
            self.logger.Info('Device %s is not on a primate' %str(line.deviceName()))
            self.allDuts[entry['udid']]['onSWD'] = False
        time.sleep(5)
        self.logger.Info('Assigning ports to duts')
        for each in myPorts.allPrimateDevices:
            self.logger.Info('***Connecting %s' %str( each))
            myPorts.ConnectPort(name=each)
            time.sleep(2)
            myPorts.ConnectPort(name=each)
            time.sleep(8)
            self.logger.Info('Checking attached devices to see which belongs to port')
            devices = CAMEmbeddedDevice.allDevices()
            for line in devices:
                self.logger.Info('attached %s' %str(line.deviceName()))
            for line in devices:
                entry = self._parseDevice(line)
                if (self.allDuts[entry['udid']]['onSWD']) :
                    self.allDuts[entry['udid']]['primate'] = each
                    self.allDuts[entry['udid']]['port'] = '/dev/cu.' + each.replace('SWD','').replace('Kong','kong').replace('Gorilla','gorilla')
                    self.logger.Info('Tagged %s with primate port %s'
                         % (str( self.allDuts[entry['udid']]['deviceName']), str(self.allDuts[entry['udid']]['port'])))
                else:
                    self.allDuts[entry['udid']]['primate'] = ''
            myPorts.DisconnectPort(name=each)
        self.logger.Info('Turning on Primates')
        for each in myPorts.allPrimateDevices:
            self.logger.Info('Turning on %s' %str(each))
            myPorts.ConnectPort(name=each)
        self.allDutsCreated = True
        return self.allDuts

    def WritePortMap(self,outFileName, portMap=""):

        if (portMap == ""  and (self.allDutsCreated)):
            self.logger.Info('Using the internal dut map to write')
            portMap = self.allDuts
        elif (portMap == "" and not(self.allDutsCreated)):
            self.logger.Info('No port map provided to output')
            return False
        # portMap now set to write to a file
        self.logger.Info('Output file is %s' %str(outFileName))
        try:
            outfile = open(  outFileName , 'w')
        except:
            self.logger.Info('Unable to open file %s for write' %str(outFileName))
            return False
        outfile.write('<array>\n')
        for each in portMap:
            if portMap[each]['onSWD']:
                self.logger.Info('writing self.allDuts[each] : %s' %str(portMap[each]))
                outfile.write('    <dict>\n')

                outfile.write('        <key>name</key>\n')
                outfile.write('        <string>%s</string>\n' %str( portMap[each]['deviceName']))
                outfile.write('        <key>hardware</key>\n')
                outfile.write('        <string>%s</string>\n' %str( portMap[each]['hardware']))
                outfile.write('        <key>port</key>\n')
                outfile.write('        <string>%s</string>\n' %str( portMap[each]['port']))
                outfile.write('        <key>serialNum</key>\n')
                outfile.write('        <string>%s</string>\n' %str( portMap[each]['serialNum']))
                outfile.write('        <key>udid</key>\n')
                outfile.write('        <string>%s</string>\n' %str( portMap[each]['udid']))
                outfile.write('        <key>onSWD</key>\n')
                outfile.write('        <true/>\n')
                outfile.write('        <key>build</key>\n')
                outfile.write('        <string>%s</string>\n' %str(portMap[each]['build']))
                outfile.write('        <key>ecid</key>\n')
                outfile.write('        <string>%s</string>\n' %str(portMap[each]['ecid']))

                outfile.write('    </dict>\n')

        outfile.write('</array>\n')
        outfile.close()
        return True

    def ReadPortMap(self,inFileName):
        portMap = {}
        if (inFileName != ''):
            userPath = os.path.expanduser('~')
            inFileName = inFileName.replace('~',userPath)
            if (not os.path.exists(inFileName)):
                raise Exception('file %s not found' %str(inFileName))
        try:
            tmpMap = readPlist(inFileName)
        except:
            self.logger.Info('Unable to open plist %s for input' %str(inFileName))
            return portMap

        for each in tmpMap:
            portMap[each['udid']] = {}
            portMap[each['udid']].update({'hardware':each['hardware']})
            portMap[each['udid']].update({'name':each['name']})
            portMap[each['udid']].update({'port':each['port']})
            portMap[each['udid']].update({'serialNum':each['serialNum']})
            portMap[each['udid']].update({'udid':each['udid']})
            portMap[each['udid']].update({'onSWD':each['onSWD']})
            portMap[each['udid']].update({'build':each['build']})
            portMap[each['udid']].update({'ecid':each['ecid']})

        return portMap


def main():

    try:
        pydevd.settrace()
    except:
        pass
    
if __name__ == '__main__':
    userPath = os.path.expanduser('~')
    pmapFile = ''
    if len(sys.argv) < 2:
        pmapFile = '~/Desktop/myports.plist'
    else:
        pmapFile = sys.argv[1]

    fileName = pmapFile.replace('~',userPath)
    if (os.path.exists(fileName)):
        print('Filename %s exists, overwriting' %str(fileName))
    
    print('starting test routine')
    pmap = DUTPortMap()
    dutMap = pmap.CreatePortMap()
    retVal = pmap.WritePortMap(outFileName=fileName, portMap=dutMap)
    print('%s written' %str(fileName))



