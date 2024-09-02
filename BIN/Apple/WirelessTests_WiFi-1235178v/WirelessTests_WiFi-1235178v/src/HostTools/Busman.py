'''
Busman.py
WirelessAutomation

Created by Quint Friesen 2011-1-11
Leveraging the code from Dmitry Halavin that used to be in WiFi_Common.py
Copyright (c) 2011 Apple. All rights reserved.
'''

#astrisctl relay vbus and usb
#--host name     Specifies the Astris probe hostname or IP address, or 'usb' for a USB probe

from LogInit import LogIt
from subprocess import Popen, PIPE
from BusmanControl import BusmanControl
from time import sleep

class Busman(object):
    '''
    Class for controlling busman
    '''
    def __init__(self):
        self.logger = LogIt()

    def MapAllPortsToDevices(self):
        '''
        Method to create a dictionary of busman ports to devices
        returns a dictionary with keys of ports, each value is a dictionary of device attributes
        NOTE: This will disconnect all ports and reconnect them
              DO NOT USE IF YOU ARE RUNNING OTHER TESTS!
        '''
        duts = Popen(['mobdev list'], shell=True, stdout=PIPE).stdout
        dutList = []
        dutMap = {}
        for line in duts:
            if len(line.split()) > 10:
                dut = {'hardware' : line.split()[4],
                       'name' : line.split()[6].strip('"'),
                       'UDID' : line.split()[12].strip(','),
                       }
                dutList.append(dut)
        busman = BusmanControl()
        busman.DisconnectAll()
        for port in range(0, 9):
            busman.ConnectPort(port)
            sleep(1)
            duts = Popen(['mobdev list'], shell=True, stdout=PIPE).stdout
            for line in duts:
                if len(line.split()) < 10:
                    continue
                for dut in dutList:
                    if dut['UDID'] in line:
                        dutMap[port] = dut
                        dutList.remove(dut)
                        continue
        return dutMap

    def MapPortToDut(self, dutUDID):
        '''
        Method to return the port number of the dut passed in
        returns the busman port number
        NOTE: This will disconnect all ports and reconnect them
              DO NOT USE IF YOU ARE RUNNING OTHER TESTS!
        '''
        busman = BusmanControl()
        busman.DisconnectAll()
        for port in range(0, 9):
            busman.ConnectPort(port)
            sleep(1)
            duts = Popen(['mobdev list'], shell=True, stdout=PIPE).stdout
            for line in duts:
                if len(line.split()) < 10:
                    continue
                if dutUDID in line:
                    busman.ConnectAll()
                    return port
        self.logger.Info('Could not find the port for device ID: %s' % dutUDID)
        return False

