'''
ConsoleCapture.py
WirelessAutomation

Created by Jim Collum on 03/29/12
Copyright (c) 2012 Apple. All rights reserved.
'''

from LogInit import LogIt
import subprocess, os
from plistlib import readPlist
from time import *
import codecs


class ConsoleCapture(object):

    '''
            consoleConfig dict entry (an array of these)
            created by DUTPortMap module

           dict  'udid' : {' hardware':         'K95AP',
'                         udid':             'f009c52b3282202a57e1ad11aaa9eb7257fab64f',
                         'serialNum':        'DLXFG06PDMG9',
                         'name':             'J2aAP-41',
                         'port':             '/dev/cu.gorilla-000843',
                         'onSWD':            True
    NOTE:  if  you have another nanokdp session open to the device being captured, then the output will be split between the two,
           resulting in garbage being passed
           
    TODO: add core automation functionality   
        device = CAMEmbeddedDevice.alloc().initWithLocationID_(self.arguments['location']) port = device.serialPort()
        if port:
            port.open()
            port.setHostPath_(os.path.join(self.arguments['logsDir'], "restore-serial.log"))
    '''

    def __init__(self, filename='',consoleDict={}):

        self.logger =LogIt()
        self.logger.Info('in ConsoleCapture init')
        self.ConsoleDict = {}
        self.ConsoleConfig = []
        self.haveConsoleConfig = False
        if (filename!= ''):
            userPath = os.path.expanduser('~')
            consoleConfigFile = filename.replace('~',userPath)
            if (not os.path.exists(consoleConfigFile)):
                raise Exception('file %s not found' %str(consoleConfigFile))

            try:
                self.ConsoleConfig = readPlist(consoleConfigFile)
                self.haveConsoleConfig = True
            except:
                self.logger.Info('ConsoleCapture: Error reading ConsoleConfig plist %s' %str(consoleConfigFile))
                return
            return


        if (consoleDict != {}):
            self.ConsoleConfig = consoleDict
            return
        return

    def __getPortFromName(self,name):
        #get port from ConsoleConfig
        if (name != ''):
            if (self.haveConsoleConfig):
                for eachPort in self.ConsoleConfig:
                    if (eachPort['name'] == name):
                        return str(eachPort['port'])
                return ''
        else:
            return ''

    def IsPortValid(self, port):
        #self.logger.Info('in IsPortValid')
        try:
            if os.path.exists(port):
                exists = True
            else:
                exists = False
        except:
            self.logger.Warn('IsPortValid: port not valid device name:%s'%str(port))
            return False
        return exists

    def StartConsoleCapture(self, port='', name=''):
        #self.logger.Info('in StartConsoleCapture port:%s' % str(port))
        # you must have either a port or a name for this to work
        if ((port == '') and (name == '')):
            return False

        if (port ==''):
            port = self.__getPortFromName(name)
            if (port == ''):
                return False

        #does port already have a session attached to it?
        for eachProc in self.ConsoleDict:
            try:
                if ((self.ConsoleDict[eachProc]['port'].find(port) >= 0 ) and (self.ConsoleDict[eachProc]['active'] )):
                    self.logger.Warn('StartConsoleCapture Falied to start Console Capture: port  %s in use' %str(port))
                    return False
            except:
                self.logger.Warn('StartConsoleCapture: Error in starting Console Capture')
                return False

        args = ['/usr/local/bin/nanokdp', '-d', port]
        retVal = subprocess.Popen(args,shell=False,stderr=subprocess.PIPE, stdout=subprocess.PIPE)

        if (retVal._child_created):
            pid = retVal.pid
            self.logger.Info('StartConsoleCapture: PID = %s' % str(pid))
            self.ConsoleDict[pid] = {}
            self.ConsoleDict[pid].update({'Popen':retVal})
            self.ConsoleDict[pid].update({'port':port})
            self.ConsoleDict[pid].update({'active': True})
            return True
        else:
            self.logger.Warn('StartConsoleCapture: Could not create subprocess')
            return False

    def StopConsoleCapture(self, port='',name=''):
        #self.logger.Info('in stopConsoleCapture')
        #get port from ConsoleConfig
        if ((port == '') and (name == '')):
            return False

        if (port ==''):
            port = self.__getPortFromName(name)
            if (port == ''):
                return False

        for eachProc in self.ConsoleDict:
            if (port == self.ConsoleDict[eachProc]['port'] and (self.ConsoleDict[eachProc]['active'])):
                self.logger.Info('StopConsoleCapture: Killing process PID:%s' % str(eachProc))
                self.ConsoleDict[eachProc]['Popen'].kill()
                self.ConsoleDict[eachProc].update({'active': False})
                return True
        return False

    def GetConsoleCapture(self, port='',name=''):
        #self.logger.Info('in GetConsoleCapture')
        if ((port == '') and (name == '')):
            raise Exception('GetConsoleCapture:Invalid arguments, No capture done on port:%s' %str(port))
            return

        if (port ==''):
            port = self.__getPortFromName(name)
            if (port == ''):
                return ''

        for eachProc in self.ConsoleDict:
            try:
                if ((port == self.ConsoleDict[eachProc]['port']) and (self.ConsoleDict[eachProc]['active'])):
                    self.StopConsoleCapture( self.ConsoleDict[eachProc]['port'] )
                #TODO there's a random codec decode error that happens here.. trying to repro and figure it out
                consoleOutput = self.ConsoleDict[eachProc]['Popen'].communicate()[0].decode('ascii', 'ignore')
                #once you read it, delete the dict entry (since you've already closed it in Stop)
                del self.ConsoleDict[eachProc]
                return consoleOutput.replace('\r\r', '')
            except UnicodeDecodeError as e:
                raise Exception('GetConsoleCapture: Decode Error :%s  No capture done on port:%s' %(str(e),str(port)))
            except Exception as e:
                raise Exception('GetConsoleCapture: Unknown Error: %s No capture done on port:%s' %(str(e),str(port)))

        raise Exception('GetConsoleCapture: No capture done on port:%s' %str(port))
        return ''

def main():
    try:
        pydevd.settrace()
    except:
        pass

if __name__ == '__main__':
    print('testing ConsoleConfig')
    testName='J2aAP-41'
    testPort='/dev/cu.gorilla-000518'
    testFilename='~/Desktop/myports.plist'

    try:
        myCapture = ConsoleCapture(filename='~/blah/myports.plist')
    except Exception, err:
        print('Exception:%s' %str(err))
        pass

    myCapture = ConsoleCapture(filename=testFilename)
    if ( myCapture.IsPortValid(testPort)):
        print('port valid')
    else:
        print('port not valid')

    if ( myCapture.IsPortValid('blah')):
        print('port valid')
    else:
        print('port not valid')

    try:
        status = myCapture.StartConsoleCapture(name='blah')
    except Exception, err:
        print('Exception:%s' %str(err))
        pass

    status = myCapture.StartConsoleCapture(name=testName)
    sleep(30)
    status = myCapture.StopConsoleCapture(name=testName)
    capOutput = myCapture.GetConsoleCapture(name=testName)

    print('test done')

