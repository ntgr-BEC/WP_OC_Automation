#!/usr/bin/python
#
#  Attenuator.py
#  WirelessAutomation
#  Created by Tien H Nguyen 02/13/12
#  Update by aomoto 01/10/13
#  Copyright (c) 2011 Apple. All rights reserved.
#

import time
import serial
import telnetlib
import commands
from LogInit import LogIt

def Attenuator(address=None, port=None, baudrate=9600, bytesize=8, parity='N',
                 stopbits=1, timeout=None, xonxoff=False, rtscts=False, dsrdtr=False):
    '''
        This class control combine both Serial and Ethernet
    '''
    if address:
        if port:
            return EthernetAttenuator(address, port)
        else:
            return EthernetAttenuator(address)
    else:
        return SerialAttenuator(port, baudrate, bytesize, parity,
                                stopbits, timeout, xonxoff, rtscts, dsrdtr)


class AttenuatorBase(object):
    ''' AttenuatorBase class '''

    MIN_ATTN = 0
    MAX_ATTN = 103
    CHAN_LIST = range(1, 5)

    # These methods are aliases, keep for compatibility

    def GetChannel(self):
        return self.GetAttenuatorChannel()

    def SetChannel(self, chan):
        self.SetAttenuatorChannel(chan)

    def GetValue(self):
        return self.GetAttenuatorValue()

    def SetValue(self, value):
        self.SetAttenuatorValue(value)

    def GetAttn(self, chan):
        self.SetChannel(chan)
        return self.GetValue()

    def SetAttn(self, chan, value):
        self.SetChannel(chan)
        self.SetValue(value)


class EthernetAttenuator(AttenuatorBase):
    '''
        This class control the Attenuator via Ethernet interface from Aeroflex (8310 Series)
    '''
    def __init__(self, ip, port=10001):
        super(EthernetAttenuator, self).__init__()
        self.ip = ip
        self.port = port
        self.logger = LogIt()
        self._connected = False

    def __del__(self):
        if self._connected:
            self.ClosePort()

    def _TelnetCMD(self, cmd):
        tn = telnetlib.Telnet(self.ip, self.port)
        time.sleep(1)
        tn.write('%s\r' % cmd)
        time.sleep(1)
        tn.close()

    def OpenPort(self):
        '''
            Open port
        '''
        self._connected = True

    def ClosePort(self):
        '''
            Close port
        '''
        self._connected = False

    def IsConnected(self):
        return self._connected

    def GetAttenuatorChannel(self):
        ''' Returns current channel '''
        raise Exception('Not implemented yet')

    def SetAttenuatorChannel(self, chan):
        '''
            Select channel on Attenuator to work with
            Only work with maximum 4 channel Attenuator Switch
        '''
        # Verify connection
        if not self._connected:
            raise Exception('Attenuator is not connected')

        if chan in self.CHAN_LIST:
            self._TelnetCMD('CHAN %s' % chan)
            self.logger.Debug('Channel %s' % chan)
            #return chan
        else:
            raise IOError('Invalid attenuation channel %d' % chan)

    def GetAttenuatorValue(self):
        ''' Returns attn value (dB) on current channel '''
        raise Exception('Not implemented yet')

    def SetAttenuatorValue(self, value):
        '''
            Set Attenuation value for the selected channel
        '''
        # Verify connection
        if not self._connected:
            raise Exception('Attenuator is not connected')

        if (value >= self.MIN_ATTN and value <= self.MAX_ATTN):
            self._TelnetCMD('ATTN %s' % value)
            self.logger.Debug('Value %s' % value)
            #return value
        else:
            raise IOError('Invalid attenuation value %d (min=%d, max=%d)' % \
                                (value, self.MIN_ATTN, self.MAX_ATTN))

class SerialAttenuator(AttenuatorBase):
    '''
        This class control the Attenuator via Serial interface from Aeroflex (8310 Series)
    '''
    def __init__(self, port=None, baudrate=9600, bytesize=8, parity='N',
                 stopbits=1, timeout=None, xonxoff=False, rtscts=False, dsrdtr=False):
        '''
            Available parameters are filled with default values.
            The most common ones are port and baudrate
        '''
        super(SerialAttenuator, self).__init__()
        self.logger = LogIt()
        self._connected = False
        self.ser = serial.Serial()
        self.ser.port = port
        self.ser.baudrate = baudrate
        self.ser.bytesize = bytesize
        self.ser.parity = parity
        self.ser.stopbit = stopbits
        self.ser.timeout = timeout
        self.ser.xonxoff = xonxoff
        self.ser.rtscts = rtscts
        self.ser.dsrdtr = dsrdtr
        #if user does not put in a Serial Port name, auto find
        if (port == None):
            try:
                self.ser.port = self.SelectPort()
            except:
                self.logger.Info('Cannot create Serial object. Please check to see if Serial port is connected or if has more than 1, specify directly')

    def __del__(self):
        if self._connected:
            self.ClosePort()

    def SelectPort(self):
        '''
            Auto select by searching Serial port from /dev
            Can only work with 1 Serial Port per machine, if found more than 1, throw error
        '''
        name = commands.getoutput('ls /dev/tty.PL*')
        notfound = name.count('ls:')
        manyfound = name.count('/dev')
        self.logger.Info(name)
        if (notfound == 1):
            #print ('should get here')
            raise IOError('None found')
        elif (manyfound > 1):
            #print ('should get there')
            raise IOError('Multiple found')
        else:
            return name

    def OpenPort(self):
        '''
            Open Serial port
        '''
        try:
            self.logger.Info('Open Serial Connection to Attenuator')
            self.ser.open()
            #time.sleep(1)
            self._connected = True
        except:
            print 'Cannot open Serial Connection'

    def ClosePort(self):
        '''
            Close Serial port
        '''
        try:
            self.logger.Info('Close Serial Connection')
            self.ser.close()
            #time.sleep(1)
            self._connected = False
        except:
            self.logger.Info('Cannot close Serial Connection')

    def IsConnected(self):
        return self._connected

    def GetAttenuatorChannel(self):
        ''' Returns current channel '''
        # Verify connection
        if not self._connected:
            raise Exception('Attenuator is not connected')
        self.ser.write('CHAN?\r')
        return int(self.ser.readline())

    def SetAttenuatorChannel(self, chan):
        '''
            Select channel on Attenuator to work with
            Only work with maximum 4 channel Attenuator Switch
        '''
        # Verify connection
        if not self._connected:
            raise Exception('Attenuator is not connected')

        if chan in self.CHAN_LIST:
            self.ser.write('CHAN %s \r' % chan)
            time.sleep(0.2)
            self.logger.Debug('Channel %s' % chan)
            #return chan
        else:
            raise IOError('Invalid attenuation channel %d' % chan)

    def GetAttenuatorValue(self):
        ''' Returns attn value (dB) on current channel '''
        # Verify connection
        if not self._connected:
            raise Exception('Attenuator is not connected')
        self.ser.write('ATTN?\r')
        return float(self.ser.readline())

    def SetAttenuatorValue(self, value):
        '''
            Set Attenuation value for the selected channel
        '''
        # Verify connection
        if not self._connected:
            raise Exception('Attenuator is not connected')

        if (value >= self.MIN_ATTN and value <= self.MAX_ATTN):
            self.ser.write('ATTN %s \r' % value)
            time.sleep(0.2)
            self.logger.Debug('Value %s \r' % value)
            #return value
        else:
            raise IOError('Invalid attenuation value %d (min=%d, max=%d)' % \
                                (value, self.MIN_ATTN, self.MAX_ATTN))


class Aeroflex8310(SerialAttenuator):
    ''' Aeroflex8310 '''

#----------------------------------------------------------------------
#  MAIN
#----------------------------------------------------------------------

if __name__ == "__main__":
    attobj = Attenuator()
    print type(attobj)
    attobj.OpenPort()
    attobj.SetChannel(1)
    attobj.SetValue(6)
    print 'Channel =', attobj.GetChannel()
    print 'Value =', attobj.GetValue()
    attobj.ClosePort()

