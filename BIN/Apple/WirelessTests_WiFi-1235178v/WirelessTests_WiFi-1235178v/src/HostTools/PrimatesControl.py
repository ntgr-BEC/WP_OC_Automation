#!/usr/bin/env python2.3
#
#    Primates tools control module
#    Copyright (c) 2012 Apple. All rights reserved.
#    Created by Dmitry Halavin on 2/2/12.
#
from subprocess import check_output,Popen,PIPE
def DisconnectDevice(astrisDevice):
    astrisDevice.setRelay_withValue_('vbus', 0)
    astrisDevice.setRelay_withValue_('usb', 0)

def ConnectDevice(astrisDevice):
    astrisDevice.setRelay_withValue_('vbus', 1)
    astrisDevice.setRelay_withValue_('usb', 1)

def ReconnectDevice(astrisDevice):
    DisconnectDevice(astrisDevice)
    ConnectDevice(astrisDevice)

def AstrisFromDevice(dut):
    return dut._cam_device.astris()

class PrimatesControl():

    def __init__(self):

        try:
            self._gorilla = check_output(["/bin/bash", "-c", "ls /dev/cu.gorilla* | sed 's/\/dev\/cu.//g'"]).replace('gorilla','GorillaSWD')
            self.gorillas = self._gorilla.splitlines()

        except Exception as e:
            print e
            self.gorillas = []
        try:
            self._kong = check_output(["/bin/bash", "-c", "ls /dev/cu.kong* | sed 's/\/dev\/cu.//g'"]).replace('kong','KongSWD')
            self.kongs = self._kong.splitlines()
        except:
            self.kongs = []
        try:
            self._skittle = check_output(["/bin/bash", "-c", "ls /dev/cu.skittle* | sed 's/\/dev\/cu.//g'"]).replace('skittle','SkittleSWD')
            self.skittles = self._skittle.splitlines()
        except:
            self.skittles = []

        self.allPrimateDevices = self.gorillas + self.kongs + self.skittles

    def ConnectPort(self, port="", name=""):
        device = self.__GetPrimate(port, name)
        check_output(["/bin/bash", "-c", "/usr/local/bin/astrisctl --host " + device + " relay vbus 1"])
        check_output(["/bin/bash", "-c", "/usr/local/bin/astrisctl --host " + device + " relay usb 1"])

    def DisconnectPort(self, port="", name=""):
        device = self.__GetPrimate(port, name)
        check_output(["/bin/bash", "-c", "/usr/local/bin/astrisctl --host " + device + " relay vbus 0"])
        check_output(["/bin/bash", "-c", "/usr/local/bin/astrisctl --host " + device + " relay usb 0"])

    def ReconnectPort(self, port="", name=""):
        self.disconnectPort(port)
        self.connectPort(port)

    def ChargePort(self, port="", name=""):
        device = self.__GetPrimate(port, name)
        check_output(["/bin/bash", "-c", "/usr/local/bin/astrisctl --host " + device + " relay usb 0"])
        check_output(["/bin/bash", "-c", "/usr/local/bin/astrisctl --host " + device + " relay vbus 1"])

        return False

    def RelayInfo(self, port="", name=""):
        '''
        returnDictiony = RelayInfo(port=<port device string>, name=<port name>
                         can use either port or name, but not both or neither
        values returned:
                         Gorilla : {'accid', 'usb', 'vtarget', 'vbus', 'tgtpower', 'charge', 'accdet', 'ptarget', 'sel0', 'sel1', 'redled'}
                         Kong    : {'usb', 'vbus', 'uart', 'nobrick', 'utarget', 'connected', 'ptarget'}
        '''
        retDict = {}
        device = self.__GetPrimate(port, name)
        statusProc = Popen(["/bin/bash", "-c", "/usr/local/bin/astrisctl --host " + device + " relays"],stdout=PIPE)
        oput = statusProc.communicate()[0].strip().split('\n')[1:]
        for each in oput:
            retDict[each.split()[0]] = each.split()[1]
        return retDict

    def __GetPrimate(self, port="", name=""):
        if (name == "" and port == "") or (not name == "" and not port == ""):
            raise Exception("Either the name or port must be set, not both or neither.")
        elif not name == "":
            if not str(name) in self.allPrimateDevices:
                raise Exception ("Device is not in the list of allPrimateDevices")
            device = name
        else:
            device = self.allPrimateDevices[port]
        return device
