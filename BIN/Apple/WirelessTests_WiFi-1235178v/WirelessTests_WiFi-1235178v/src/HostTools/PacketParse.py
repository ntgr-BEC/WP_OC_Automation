#!/usr/bin/env python2.3
'''
PacketParse.py
WirelessAutomation

Created by Quint Friesen 2012-3-16
Updated by aomoto 2012-12-10
Copyright (c) 2012 Apple. All rights reserved.
'''

import re
import glob
import os, subprocess
from types import FileType, ListType
from xml.etree import ElementTree
from datetime import datetime
from time import time
from SnifferTools import CaptureFilter
from HostToolsExceptions import InvalidCaptureFile, NotValidInput, CaptureParseError
from ThirdPartyLibraries import sh
from pyparsing import Word, Group, SkipTo, restOfLine, Literal, nums
from LogInit import LogIt
import pprint

#----------------------------------------------------------------------
#
#----------------------------------------------------------------------

class TsharkFieldTreeNode(object):

    def __init__(self):
        super(TsharkFieldTreeNode, self).__init__()
        self._fields = []

    def GetFields(self):
        return [f for f in self.IterFields()]

    def GetFieldsDict(self):
        """ Returns all fields as a dict """
        return dict([(f.GetName(), f.GetValue()) for f in self.IterFields()])

    def GetFieldsByName(self, name):
        """ Returns list of TsharkFieldTreeNode by name """
        return [field for field in self.IterFields() if (field.GetName() == name)]

    def IterFields(self):
        for field in self._fields:
            yield field
            for childField in field.IterFields():
                yield childField

    def GetFieldByName(self, name):
        """ Returns TsharkFieldTreeNode by name, otherwise None if not found """
        for field in self._fields:
            if name == field.GetName():
                return field
            childField = field.GetFieldByName(name)
            if childField:
                return childField
        return None # not found in this node


class TsharkField(TsharkFieldTreeNode):
    """ Field data parsed from tshark """

    def __init__(self, fieldXml):
        super(TsharkField, self).__init__()
        self._xml = fieldXml
        self._ParseXml(fieldXml)

    def _ParseXml(self, fieldXml):
        # Parse field info
        self._name = fieldXml.get('name')
        self._showname = fieldXml.get('showname')
        self._size = self._EvalIntOrNone(fieldXml.get('size'))
        self._pos = self._EvalIntOrNone(fieldXml.get('pos'))
        self._show = fieldXml.get('show')
        self._value = fieldXml.get('show')
        try:
            # Evaluated to int or float
            self._value = eval(self._value)
        except:
            pass # otherwise, leave as string

        # Parse child nodes
        for childXml in fieldXml.getchildren():
            self._fields.append(TsharkField(childXml))

    def __eq__(self, field):
        """ Returns True if fields are matching """
        # Match name (not unique), showname (should be unique), size in Bytes, value
        return (
            (self._name == field._name) and \
            (self._showname == field._showname) and \
            (self._size == field._size) and \
            (self._value == field._value)
        )

    def __ne__(self, field):
        return not self.__eq__(field)

    def __str__(self):
        return '%s (%d Bytes) = %s at %d' % (self._name, self._size, repr(self._value), self._pos)

    def __contains__(self, field):
        for childField in self._fields:
            if (field == childField) or (field in childField):
                return True
        return False

    def _EvalIntOrNone(self, val):
        """ Evaluates int or -1 for None """
        if val is None:
            return -1
        return int(val)

    def GetName(self):
        return self._name

    def SetName(self, name):
        self._name = name

    def GetShowName(self):
        return self._showname

    def SetShowName(self, name):
        self._showname = name

    def GetSize(self):
        return self._size

    def SetSize(self, size):
        self._size = size

    def GetPosition(self):
        return self._pos

    def SetPosition(self, pos):
        self._pos = pos

    def GetShowValue(self):
        return self._show

    def SetShowValue(self, value):
        self._show = str(value)

    def GetValue(self):
        return self._value

    def SetValue(self, value):
        self._value = value


class TsharkPrototype(TsharkField):
    """ Prototype data parsed from tshark """

    def __init__(self, protoXml):
        super(TsharkPrototype, self).__init__(protoXml)


class TsharkWlanPacket(TsharkFieldTreeNode):
    """
    WLAN packet data parsed from tshark.
    This class provides interfaces to access captured packet and field data.
    """

    def __init__(self, pktNode):
        super(TsharkWlanPacket, self).__init__()
        self._node = pktNode
        self._ParseXml(pktNode)

    def _ParseXml(self, pktNode):
        """ Internal method to parse xml node to TSharkFields """
        for protoXml in pktNode.getchildren():
            self._fields.append(TsharkPrototype(protoXml))

    def __str__(self):
        """ Return a unique ID name for this packet """
        name = self.GetFieldByName('wlan.fc.type_subtype').GetShowName().replace('Type/Subtype: ', '')
        if self.GetFieldByName('frame.time'):
            return '%s at %d' % (name, self.GetPacketNumber())
        return name

    def __repr__(self):
        return self.__str__()

    def __contains__(self, field):
        """ Returns True if this packet contains a field """
        for childField in self._fields:
            if (field == childField) or (field in childField):
                return True
        return False

    def GetPacketNumber(self):
        """Return Packet Number as an int"""
        return int(self.GetFieldByName('num').GetShowValue())

    #----------------------------------------------------------------------
    #  Frame Protocol
    #----------------------------------------------------------------------

    def GetFrameTimeStr(self):
        """ Return frame time as a string """
        # Example format: Oct 25, 2012 17:57:21.311 (rounded 3 floating decimal places)
        (frameTime, frameTimeMsec) = self.GetFieldByName('frame.time').GetValue().split('.')
        return frameTime + '.' + ('%0.4f' % float('0.' + frameTimeMsec))[2:]

    def GetFrameTime(self):
        """ Returns datetime.datetime from 'frame.time' timestamp """
        return datetime.strptime(self.GetFrameTimeStr(), '%b %d, %Y %H:%M:%S.%f')

    def GetFrameLength(self):
        """ Returns integer from 'frame.len' (in Bytes) """
        val = self.GetFieldByName('frame.len').GetShowName()
        return int(re.search('Frame Length: (\d+) bytes', val).group(1))

    #----------------------------------------------------------------------
    #  RadioTap Protocol
    #----------------------------------------------------------------------

    # None needed now, skip
    pass

    #----------------------------------------------------------------------
    #  WLAN Protocol
    #----------------------------------------------------------------------

    def GetWlanType(self):
        return int(self.GetFieldByName('wlan.fc.type').GetValue())

    def GetWlanSubtype(self):
        return int(self.GetFieldByName('wlan.fc.type_subtype').GetValue())

    def GetPrototypes(self):
        return self._fields

    def GetFixedParameters(self):
        """ Returns a list of TSharkField for WLAN fixed parameters """
        field = self.GetFieldByName('wlan_mgt.fixed.all')
        if field is None:
            return []
        return field.GetFields()

    def GetTaggedParameters(self):
        """ Returns a list of TSharkField for WLAN tagged parameters """
        field = self.GetFieldByName('wlan_mgt.tagged.all')
        if field is None:
            return []
        return field.GetFields()

    def GetListenInterval(self):
        """ Return Listen Interval"""
        return self.GetFieldByName('wlan_mgt.fixed.listen_ival').GetValue()

    def VerifyProbReqSSID(self, ssid=''):
        """"Verify prob req SSID"""
        self.logger = LogIt()
        ssidProb = self.GetFieldByName('wlan_mgt.ssid').GetShowName()
        ssid = "SSID: %s" % (ssid)
        if ssidProb == ssid:
            return 1
        else:
            self.logger.Debug("prob request shows %s, not %s", ssidProb, ssid)
        return 0

    def IsBroadcastProbReq(self):
        return self.VerifyProbReqSSID(ssid='')

    def IsRetry(self):
        """ Return True if the packet is a retry """
        val = self.GetFieldByName('wlan.fc.retry').GetShowName()
        if str(val).find("Frame is not being retransmitted") < 0:
            return 1
        return 0

#----------------------------------------------------------------------
#
#----------------------------------------------------------------------

class PacketParse(object):
    '''
    Class to parse out xml packets generated by tshark
    Requires a filepath or a file object, or a XML string
    '''
    def __init__(self, captureFile=''):
        '''
        setup holding dictionaries and pull in the file for initial parse
        '''
        self._errorMsgs = []
        self.headerCommon = ['frame.number', 'frame.time_relative', 'frame.time',
                             'radiotap.xchannel.type.2ghz', 'radiotap.xchannel.type.2ghz',
                             'radiotap.xchannel', 'wlan.fc.type_subtype', 'wlan.da',
                             'wlan.sa', 'wlan.bssid', 'frame.time_epoch', 'wlan.fc.type']
        self.wlanMgt = ['wlan_mgt.ssid', 'wlan_mgt.supported_rates',
                        'wlan_mgt.extented_supported_rates']
        self.logger = LogIt()
        self.packetDoneData = {}
        self.packetRawData = ElementTree.ElementTree()
        self.captureFile = ''
        if captureFile:
            self.Parse(captureFile)

    def Parse(self, captureFile):
        ''' Parses .pcap to .xml, returns True if file parsed with packet data '''
        try:
            # Parse from handle or filename or xml string
            if type(captureFile) is FileType:
                try:
                    self.packetRawData = ElementTree.parse(captureFile)
                except Exception, e:
                    self._AddError('Failed to parse capture file, got %s' % repr(e))
            elif isinstance(captureFile, basestring) and (os.stat(captureFile).st_size == 0):
                self.logger.Warning('Capture file is empty: %s' % repr(captureFile))
            elif isinstance(captureFile, basestring) and os.path.isfile(captureFile):
                fh = open(captureFile)
                try:
                    self.packetRawData = ElementTree.parse(fh)
                except Exception, e:
                    self._AddError('Failed to parse capture file, got %s' % repr(e))
                finally:
                    fh.close()
            elif isinstance(captureFile, basestring):
                try:
                    self.packetRawData = ElementTree.XML(captureFile)
                except Exception, e:
                    self._AddError('Failed to parse capture file, got %s' % repr(e))

        except Exception, e:
            self._AddError(e)

        # Return True parse succeeded
        return (len(self.GetErrors()) == 0)

    def _AddError(self, errMsg):
        self.logger.Warning(errMsg)
        self._errorMsgs.append(errMsg)

    def GetErrors(self):
        ''' Returns list of error messages '''
        return self._errorMsgs

    def ClearErrors(self):
        self._errorMsgs = []

    def ParseFields(self, customFields=False, last=False):
        '''
        Parse out the additional fields and add to the dictionary
        Return the current parsed data. Will return a dictionary of dictionaries
            with the initial key being the packet number
        ALL fields are STRINGS or LISTS OF STRINGS
            including the fields that may not exist, they are null string (None)
        '''
        if customFields:
            if not type(customFields) is ListType and len(customFields) < 1:
                raise NotValidInput('Must supply a valid list of fields you want to pull from the packets')
            fieldsToParse = self.headerCommon
            for field in customFields:
                fieldsToParse.append(field)
            self.__ParseData(filter=fieldsToParse, header=True, last=last)
        else:
            self.__ParseData(header=True, last=last)
        return self.packetDoneData

    def ParseLastPacket(self, customFields=None):
        '''
        Special function to parse just last packet in filtered packets.
        '''
        if customFields:
            self.ParseCustomFields(customFields, last=True)
        else:
            self.__ParseData(last=True)
        return self.packetDoneData

    def __ParseData(self, filter=None, header=False, last=False):
        '''
        Main Parse function. Runs through the requested fields and populates
            the return dictionary. ALL fields are STRINGS or LISTS OF STRINGS
            including the fields that may not exist, they are null string (None)
        '''
        if not filter:
            filter = self.headerCommon
        packets = self.packetRawData.findall('.//packet')
        currentPacket = 0
        for packet in packets:
            if last:
                packet = packets[-1]
            packetNum = packet.findall('.//field[@name="frame.number"]')[0].attrib.get('show')
            if packetNum not in self.packetDoneData:
                self.packetDoneData[packetNum] = {}
            for field in filter:
                data = packet.findall('.//field[@name="%s"]' % field)
                if len(data) > 1:
                    # handle multiple here
                    self.packetDoneData[packetNum][field] = []
                    for item in data:
                        self.packetDoneData[packetNum][field].append(item.attrib.get('show'))
                elif len(data) == 0:
                    self.packetDoneData[packetNum][field] = None
                else:
                    self.packetDoneData[packetNum][field] = data[0].attrib.get('show')
            if header and packet.findall('.//proto[@name="wlan_mgt"]') > 0:
                self.__ParseData(self.wlanMgt, header=False, last=last)
            if last:
                break

    def ParseAll(self):
        ''' Returns a list of Packet instances '''
        # Return [] if parse is invalid
        if self.packetRawData is None:
            return []
        return [TsharkWlanPacket(pktNode) for pktNode in self.packetRawData.findall('.//packet')]


class WiFiPacketParseBase(object):
    '''
    Hold the basic and common function for Packet filtering and parse
    '''

    def __init__(self, snifferCapture, remote=False, logdir=None):
        '''
        snifferCapture: snifferCapture pcap file.
        optional remote arg should contain a dictionary:
        {'server' : '192.168.x.x',
         'user' : 'automation',
         'password' : 'test!123',
         }
        '''
        self.logger = LogIt()
        if remote:
            myFilter = CaptureFilter(server=remote['server'], user=remote['user'], password=remote['password'])
            self.capFilter = getattr(myFilter, 'RemotePcapToXml')
        else:
            myFilter = CaptureFilter()
            self.capFilter = getattr(myFilter, 'PcapToXml')

        self.SetLogDir(logdir)
        self.SetOutputFile('capture.xml')
        self.mainCapture = snifferCapture

    def Config(self, snifferCapture, logdir=None, outputFile="capture.xml"):
        '''
        Set mainCapture, logdir and outFile
        '''
        self.SetLogDir(logdir)
        self.SetMainCaptureFile(snifferCapture)
        self.SetOutputFile(outputFile)

    def SetLogDir(self, logdir=None):
        '''
        Set the log folder name
        '''
        if not logdir:
            self.logdir = './Logs/Capture-%s' % (datetime.now().isoformat('_').replace(':', '-'))
        else:
            self.logdir = '%s/Capture-%s' % (logdir, datetime.now().isoformat('_').replace(':', '-'))
        if not os.path.isdir(self.logdir):
            os.makedirs(self.logdir)

    def GetLogDir(self):
        '''
        Return the log folder name
        '''
        return self.logdir

    def SetOutputFile(self, outputFile):
        '''
        set the output file name
        '''
        self.outputFile = outputFile
        self.xmlOutputFile = os.path.join(self.logdir, self.outputFile)

    def GetOutputFile(self):
        '''
        return full directory of the output file
        '''
        return self.xmlOutputFile

    def SetMainCaptureFile(self, snifferCapture):
        '''
        Set the main capture file
        '''
        self.mainCapture = snifferCapture

    def GetMainCaptureFile(self):
        '''
        Get the main capture file
        '''
        return self.mainCapture

    def GetFilteredPackets(self, filterString, packetCount):
        '''
        Generate XML file with given filter.
        filter: WiFi sniffer filter
        packetCount: filtered packet num
        Returns a list of Packet instances: TsharkWlanPacket
        '''

        self.capFilter(self.mainCapture, filterString, self.xmlOutputFile, packetCount=packetCount)

        parse = PacketParse(self.xmlOutputFile)
        if parse.GetErrors():
            e = '\n'.join(parse.GetErrors())
            self.logger.Info('Failed to parse %s with error %s', self.xmlOutputFile, e)
            return False
        tempDict = parse.ParseAll()
        if tempDict is None:
            self.logger.Info("Failed to get output dictionary")
        return tempDict


class WiFiPacketParse(WiFiPacketParseBase):
    '''
    Class that contains all the logic to parse out the wifi data/management from
        capture files.
    '''

    def ParseProbReq(self, sourceAddress=None, destAddress=None, packetCount=1, xmlFileName='ProbReq.xml'):
        '''
        self.capFilter(pcapFile, tsharkFilter, xmlFile, packetCount=10)
        'wlan.sa == %s && wlan.fc.type_subtype == 4'
        Returns a list of Packet instances: TsharkWlanPacket
        '''
        self.SetOutputFile(xmlFileName)
        if sourceAddress and destAddress:
            filterString = 'wlan.sa == %s && wlan.da == %s && wlan.fc.type_subtype == 4' % (sourceAddress, destAddress)
        elif sourceAddress:
            filterString = 'wlan.sa == %s && wlan.fc.type_subtype == 4' % (sourceAddress)
        elif destAddress:
            filterString = 'wlan.da == %s && wlan.fc.type_subtype == 4' % (destAddress)
        else:
            filterString = 'wlan.fc.type_subtype == 4'
        return self.GetFilteredPackets(filterString, packetCount)


    def ParseAssocReq(self, sourceAddress=None, destAddress=None, packetCount=1, xmlFileName='AssocReq.xml'):
        '''
        self.capFilter(pcapFile, tsharkFilter, xmlFile, packetCount=10)
        filterString = 'wlan.sa == %s && wlan.da == %s && (wlan.fc.type_subtype == 0 || wlan.fc.type_subtype == 1 || wlan.fc.type_subtype == 2 || wlan.fc.type_subtype == 3)' % (self.dutMAC, self.ap2MAC)
        Returns a list of Packet instances: TsharkWlanPacket
        '''
        self.SetOutputFile(xmlFileName)
        if sourceAddress and destAddress:
            filterString = 'wlan.sa == %s && wlan.da == %s && (wlan.fc.type_subtype == 0 || wlan.fc.type_subtype == 1 || wlan.fc.type_subtype == 2 || wlan.fc.type_subtype == 3)' % (sourceAddress, destAddress)
        elif sourceAddress:
            filterString = 'wlan.sa == %s && (wlan.fc.type_subtype == 0 || wlan.fc.type_subtype == 1 || wlan.fc.type_subtype == 2 || wlan.fc.type_subtype == 3)' % (sourceAddress)
        elif destAddress:
            filterString = 'wlan.da == %s && (wlan.fc.type_subtype == 0 || wlan.fc.type_subtype == 1 || wlan.fc.type_subtype == 2 || wlan.fc.type_subtype == 3)' % (destAddress)
        else:
            filterString = '(wlan.fc.type_subtype == 0 || wlan.fc.type_subtype == 1 || wlan.fc.type_subtype == 2 || wlan.fc.type_subtype == 3)'
        return self.GetFilteredPackets(filterString, packetCount)


    def ParseArpResponse(self, sourceAddress=None, destAddress=None, packetCount=1, xmlFileName='ArpResp.xml'):
        '''
        self.capFilter(pcapFile, tsharkFilter, xmlFile, packetCount=10)
        filterString = 'wlan.sa == %s && wlan.da == %s && arp.opcode == 2' % (self.dutMAC, self.ap2MAC)
        Returns a list of Packet instances: TsharkWlanPacket
        '''
        self.SetOutputFile(xmlFileName)
        if sourceAddress and destAddress:
            filterString = 'wlan.sa == %s && wlan.da == %s && arp.opcode == 2' % (sourceAddress, destAddress)
        elif sourceAddress:
            filterString = 'wlan.sa == %s && arp.opcode == 2' % (sourceAddress)
        elif destAddress:
            filterString = 'wlan.da == %s && arp.opcode == 2' % (destAddress)
        else:
            filterString = 'arp.opcode == 2'
        return self.GetFilteredPackets(filterString, packetCount)


class RoamPacketParse(WiFiPacketParse):
    '''
    Class that contains all the logic to parse out the roaming time data from
        capture files.
    '''
    def __init__(self, roamCapture, roamNum, dutMAC, srvMAC, ap1MAC, ap2MAC,
                        roamCaptureTwo=False, remote=False, logdir='./Logs'):
        '''
        Assumes this is for ONE roam only. Each roam should have its own capture
        Pulls in at LEAST one file, can pull in two if the sniffs are split.
            order of the files doesn't matter.
        optional remote arg should contain a dictionary:
        {'server' : '192.168.x.x',
         'user' : 'automation',
         'password' : 'test!123',
         }
        '''
        super(RoamPacketParse, self).__init__(roamCapture, remote, logdir)
        self.dutMAC = dutMAC
        self.srvMAC = srvMAC
        self.ap1MAC = ap1MAC
        self.ap2MAC = ap2MAC
        self.roamNum = roamNum
        self.lastData = {} # None
        self.firstProbe = None
        self.firstBeacon = None
        self.authReq = None
        self.authResp = None
        self.reassocReq = None
        self.reassocResp = None
        self.firstData = None
        self.eapPackets = None
        self.actionPackets = []
        self.disassoc = None
        self.deauth = None
        self.allPackets = []
        if remote:
            myFilter = CaptureFilter(server=remote['server'], user=remote['user'], password=remote['password'])
            self.capFilter = getattr(myFilter, 'RemotePcapToXml')
        else:
            myFilter = CaptureFilter()
            self.capFilter = getattr(myFilter, 'PcapToXml')
        if not logdir:
            self.logdir = './Logs/Capture-%s-%s' % (roamNum, datetime.now().isoformat('_').replace(':', '-'))
        else:
            self.logdir = '%s/Capture-%s-%s' % (logdir, roamNum, datetime.now().isoformat('_').replace(':', '-'))
        if not os.path.isdir(self.logdir):
            os.mkdir(self.logdir)
        if roamCaptureTwo:
            self.mainCapture = '%s/MergedCapture.pcap' % self.logdir
            myFilter.PcapMerge(roamCapture, roamCaptureTwo, self.mainCapture)
        else:
            self.mainCapture = roamCapture

    def _ParseReassociation(self):
        '''
        self.capFilter(pcapFile, tsharkFilter, xmlFile, packetCount=10)
        'wlan.fc.type_subtype == 11 || wlan.fc.type_subtype == 13 || wlan.fc.type_subtype == 2 || wlan.fc.type_subtype == 3'
        '''
        outFile = os.path.join(self.logdir, 'ReassocReq.xml')
        filterString = 'wlan.sa == %s && wlan.da == %s && (wlan.fc.type_subtype == 2)' % (self.dutMAC, self.ap2MAC)
        self.capFilter(self.mainCapture, filterString, outFile, packetCount=1)
        parse = PacketParse(outFile)
        if parse.GetErrors():
            self.reassocReq = False
            return False
        tempDict = parse.ParseFields()
        if tempDict:
            _, self.reassocReq = tempDict.popitem()
        else:
            self.reassocReq = False
            return False
        outFile = os.path.join(self.logdir, 'ReassocResp.xml')
        filterString = 'wlan.sa == %s && wlan.da == %s && (wlan.fc.type_subtype == 3)' % (self.ap2MAC, self.dutMAC)
        filterString += ' && wlan.fcs_good == 1'
        self.capFilter(self.mainCapture, filterString, outFile, packetCount=1)

        parse = PacketParse(outFile)
        if parse.GetErrors():
            self.reassocResp = False
            return False
        tempDict = parse.ParseFields()
        if tempDict:
            _, self.reassocResp = tempDict.popitem()
            return True
        else:
            self.reassocResp = False

    def _ParseAuthentication(self):
        '''
        self.capFilter(pcapFile, tsharkFilter, xmlFile, packetCount=10)
        'wlan.fc.type_subtype == 11 || wlan.fc.type_subtype == 13 || wlan.fc.type_subtype == 2 || wlan.fc.type_subtype == 3'
        '''
        outFile = os.path.join(self.logdir, 'AuthReq.xml')
        filterString = 'wlan.sa == %s && wlan.da == %s && wlan.fc.type_subtype == 11' % (self.dutMAC, self.ap2MAC)
        filterString += ' && wlan.fcs_good == 1'
        self.capFilter(self.mainCapture, filterString, outFile, packetCount=1)

        parse = PacketParse(outFile)
        if parse.GetErrors():
            self.authReq = False
            return False
        tempDict = parse.ParseFields()
        if tempDict:
            _, self.authReq = tempDict.popitem()
        else:
            self.authReq = False
            return False
        outFile = os.path.join(self.logdir, 'AuthResp.xml')
        filterString = 'wlan.sa == %s && wlan.da == %s && wlan.fc.type_subtype == 11' % (self.ap2MAC, self.dutMAC)
        filterString += ' && wlan.fcs_good == 1'
        self.capFilter(self.mainCapture, filterString, outFile, packetCount=1)

        parse = PacketParse(outFile)
        if parse.GetErrors():
            self.authResp = False
            return False
        tempDict = parse.ParseFields()
        if tempDict:
            _, self.authResp = tempDict.popitem()
            return True
        else:
            self.authResp = False
            return False

    def _ParseFirstData(self):
        '''
        add after time to the following
        self.capFilter(pcapFile, tsharkFilter, xmlFile, packetCount=1)
        'frame.number > %s && wlan.fc.type == 2'
        '''
        outFile = os.path.join(self.logdir, 'FirstData.xml')
        filterString = 'wlan.sa == %s && wlan.da == %s && frame.number > %s && wlan.fc.type == 2' % (self.dutMAC, self.srvMAC, int(self.reassocResp['frame.number']))
        filterString += ' && wlan.fcs_good == 1'
        self.capFilter(self.mainCapture, filterString, outFile, packetCount=1)

        parse = PacketParse(outFile)
        if parse.GetErrors():
            self.firstData = False
            return False
        tempDict = parse.ParseFields()
        if tempDict:
            _, self.firstData = tempDict.popitem()
        else:
            self.firstData = False

    def _ParseLastData(self):
        '''
        self.capFilter(pcapFile, tsharkFilter, xmlFile, packetCount=0)
        'wlan.fc.type == 2 && frame.time_epoch > %s'
        '''
        outFile = os.path.join(self.logdir, 'LastData.xml')
        filterString = 'wlan.sa == %s && wlan.da == %s && frame.number > %s && frame.number < %s && wlan.fc.type == 2' % (self.dutMAC, self.srvMAC, int(self.reassocReq['frame.number']) - 1500, int(self.reassocReq['frame.number']))
        filterString += ' && wlan.fcs_good == 1'
        self.capFilter(self.mainCapture, filterString, outFile, packetCount=0)

        parse = PacketParse(outFile)
        if parse.GetErrors():
            self.lastData = False
            return False
        tempDict = parse.ParseFields(last=True)
        if tempDict:
            _, self.lastData = tempDict.popitem()
        else:
            self.lastData = False

    def _ParseFirstProbe(self):
        '''
        self.capFilter(pcapFile, tsharkFilter, xmlFile, packetCount=1)
        'frame.number > %s && wlan.fc.type_subtype == 4' %
        '''
        outFile = os.path.join(self.logdir, 'FirstProbe.xml')
        filterString = 'wlan.sa == %s && frame.time_epoch > %.9f && frame.time_epoch < %.9f && (wlan.fc.type_subtype == 4)' % \
                (self.dutMAC, float(self.lastData['frame.time_epoch']), float(self.reassocReq['frame.time_epoch']))
        filterString += ' && wlan.fcs_good == 1'
        self.capFilter(self.mainCapture, filterString, outFile, packetCount=1)
        parse = PacketParse(outFile)
        if parse.GetErrors():
            self.firstProbe = False
            return False
        tempDict = parse.ParseFields()
        if tempDict:
            junk, self.firstProbe = tempDict.popitem()
        else:
            self.firstProbe = False

    def _ParseEAP(self):
        '''
        eapol.keydes.type
        '''
        outFile = os.path.join(self.logdir, 'EAPOL.xml')
        filterString = '((wlan.sa == %s && wlan.da == %s) || (wlan.sa == %s && wlan.da == %s)) && frame.time_epoch > %.9f && frame.time_epoch < %.9f && eapol' % \
                  (self.dutMAC, self.ap2MAC, self.ap2MAC, self.dutMAC, float(self.lastData['frame.time_epoch']), float(self.firstData['frame.time_epoch']))
        filterString += ' && wlan.fcs_good == 1'
        self.capFilter(self.mainCapture, filterString, outFile, packetCount=0)

        parse = PacketParse(outFile)
        if parse.GetErrors():
            self.eapPackets = False
            return False
        self.eapPackets = parse.ParseFields(customFields=['eapol.keydes.type', 'eapol.type', 'eapol.version', 'eap.code'])

    def _ParseFirstBeacon(self):
        '''
        self.capFilter(pcapFile, tsharkFilter, xmlFile, packetCount=1)
        'frame.number > %s && '
        '''
        outFile = os.path.join(self.logdir, 'FirstBeacon.xml')
        filterString = 'wlan.sa == %s && frame.time_epoch > %.9f && wlan.fc.type_subtype == 8' % \
                    (self.ap2MAC, float(self.lastData['frame.time_epoch']))
        filterString += ' && wlan.fcs_good == 1'
        self.capFilter(self.mainCapture, filterString, outFile, packetCount=1)
        parse = PacketParse(outFile)
        junk, self.firstBeacon = parse.ParseFields().popitem()

    def _ParseFirstActionFrames(self):
        outFile = os.path.join(self.logdir, 'FirstActionFrames.xml')
        filterString = '(wlan.fc.type_subtype == 13 || wlan.fc.type_subtype == 14) && frame.time_epoch > %s' % \
                            self.reassocReq['frame.time_epoch']
        filterString += ' && wlan.fcs_good == 1'
        self.capFilter(self.mainCapture, filterString, outFile, packetCount=2)

        parse = PacketParse(outFile)
        if parse.GetErrors():
            self.actionPackets = False
            return False
        self.actionPackets = parse.ParseFields()

    def _ParseDisassociation(self):
        outFile = os.path.join(self.logdir, 'Disassociation.xml')
        filterString = 'wlan.addr == %s && wlan.fc.type_subtype == 10' % self.dutMAC
        filterString += ' && wlan.fcs_good == 1'
        self.capFilter(self.mainCapture, filterString, outFile, packetCount=1)

    def _ParseDeauthentication(self):
        outFile = os.path.join(self.logdir, 'Deauthentication.xml')
        filterString = 'wlan.sa == %s && wlan.fc.type_subtype == 12' % self.dutMAC
        filterString += ' && wlan.fcs_good == 1'
        self.capFilter(self.mainCapture, filterString, outFile, packetCount=1)

    def _ParsePackets(self, globStr='*.xml'):
        ''' Returns sorted list of all packets (.xml format) from log directory '''
        pkts = []
        for fname in glob.glob(os.path.join(self.logdir, globStr)):
            pkts.extend(PacketParse(fname).ParseAll())

        # Remove duplciates and sort
        pktsDict = {}
        for pkt in pkts:
            pktsDict[pkt.GetPacketNumber()] = pkt

        # Return sorted by time
        #return sorted(pkts, cmp=lambda x,y: x.GetPacketNumber() - y.GetPacketNumber())
        return [pkt for (key, pkt) in sorted(pktsDict.items())]

    def _ParsePacketOrNone(self, globStr='*.xml', index=0):
        ''' Returns single packet at index, otherwise None '''
        pkts = self._ParsePackets(globStr)
        if len(pkts) > index:
            return pkts[index]

    def _ParseRoam(self):
        '''
        Parses a single roam event, returns None.
        Check internals for discovered packets.
        '''
        # Parse to file
        self._ParseAuthentication()
        self._ParseReassociation()
        self._ParseLastData()
        self._ParseFirstProbe()
        self._ParseFirstBeacon()
        self._ParseFirstActionFrames()
        self._ParseFirstData()
        self._ParseEAP()
        self._ParseDisassociation()
        self._ParseDeauthentication()

        # Load file to Packet instances or list
        self.lastData = self._ParsePacketOrNone('LastData.xml')
        self.firstProbe = self._ParsePacketOrNone('FirstProbe.xml')
        self.firstBeacon = self._ParsePacketOrNone('FirstBeacon.xml')
        self.authReq = self._ParsePacketOrNone('AuthReq.xml')
        self.authResp = self._ParsePacketOrNone('AuthResp.xml')
        self.reassocReq = self._ParsePacketOrNone('ReassocReq.xml')
        self.reassocResp = self._ParsePacketOrNone('ReassocResp.xml')
        self.firstData = self._ParsePacketOrNone('FirstData.xml')
        self.actionPackets = self._ParsePackets('FirstActionFrames.xml')
        self.eapPackets = self._ParsePackets('EAPOL.xml')
        self.disassoc = self._ParsePacketOrNone('Disassociation.xml')
        self.deauth = self._ParsePacketOrNone('Deauthentication.xml')
        self.allPackets = self._ParsePackets('*.xml')

    def VerifyRoam(self):
        ''' Return True if parsed packets detects a successful roam '''
        self._ParseRoam()
        # TODO: return False if roam sequence is incomplete
        if self.disassoc:
            self.logger.Warning('Roam should not have disassoc')
            return False
        elif self.deauth:
            self.logger.Warning('Roam should not have deauth')
            return False
        return True

    def RetFail(self):
        return {'RoamNumber' : self.roamNum,
                'DeltaOverall' : 0,
                'DeltaLastDataToAuthReq' : 0,
                'DeltaAuthReqToAuthResp' : 0,
                'DeltaAuthRespToAssocReq' : 0,
                'DeltaAssocReqToAssocResp' : 0,
                'DeltaAssocRespToFirstData' : 0,
                'DeltaLastDataToFirstProbe' : 0,
                }

    def GenerateResults(self, rk=False):
        '''
        Here is the logic and the formatting of results to return
        '''
        if not self.ap2MAC:
            return self.RetFail()
        if not self._ParseReassociation():
            return self.RetFail()
        authPass = self._ParseAuthentication()
#        if not rk and not authPass:
            # 802.11r/k can have missing auth packets
#            raise CaptureParseError('Authentication: Did not find packets, did you forget to set the rk flag?')
        self._ParseLastData()
        # below line just used for testing
#        self.lastData['frame.time_epoch'] = '1331680532.505303000'
#        if not self.lastData:
#            raise CaptureParseError('Last Data: Did not find packet within 1 second of Auth Request')
        self._ParseFirstProbe()
#        self._ParseFirstBeacon()
#        if not self.firstBeacon:
#            raise CaptureParseError('Beacon: Did not find packet after Last Data')
        self._ParseFirstData()
        # below line just used for testing
#        self.firstData['frame.time_epoch'] = '1331680544.537805000'
#        if not self.firstData:
#            raise CaptureParseError('First Data: Did not find packet after Association')
        # ok, now make sure we have all the packets we need
        if not rk:
            if not self.reassocReq or not self.reassocResp or not self.authReq or not self.authResp or not self.firstData or not self.lastData:
                print self.reassocReq, self.reassocResp, self.authReq, self.authResp, self.firstData, self.lastData
                # format failure data and return
                return self.RetFail()
        else:
            if not self.reassocReq or not self.reassocResp or not self.firstData or not self.lastData:
                # format failure data and return
                return self.RetFail()
        if rk:
            # time to get the EAP and handshake packets
            self._ParseEAP()
        # Ok, have all packets, math time and format return
        retVal = {'RoamNumber' : self.roamNum,
                  'DeltaOverall' : float(self.firstData['frame.time_epoch']) - float(self.lastData['frame.time_epoch']),
                  # 'DeltaLastDataToFirstBeacon' : float(self.firstBeacon['frame.time_epoch']) - float(self.lastData['frame.time_epoch']),
                  'DeltaLastDataToAuthReq' : float(self.authReq['frame.time_epoch']) - float(self.lastData['frame.time_epoch']),
                  'DeltaAuthReqToAuthResp' : float(self.authResp['frame.time_epoch']) - float(self.authReq['frame.time_epoch']),
                  'DeltaAuthRespToAssocReq' : float(self.reassocReq['frame.time_epoch']) - float(self.authResp['frame.time_epoch']),
                  'DeltaAssocReqToAssocResp' : float(self.reassocResp['frame.time_epoch']) - float(self.reassocReq['frame.time_epoch']),
                  'DeltaAssocRespToFirstData' : float(self.firstData['frame.time_epoch']) - float(self.reassocResp['frame.time_epoch']),
                  'LastDataPacket' : self.lastData,
                  # 'FirstBeaconPacket' : self.firstBeacon,
                  'AssocReqPacket' : self.reassocReq,
                  'AssocRespPacket' : self.reassocResp,
                  'AuthReqPacket' : self.authReq,
                  'AuthRespPacket' : self.authResp,
                  'FirstDataPacket': self.firstData,
                  }
        if self.firstProbe == 0:
            retVal['DeltaLastDataToFirstProbe'] = 0
            retVal['FirstProbePacket'] = False
        else:
            retVal['DeltaLastDataToFirstProbe'] = float(self.firstProbe['frame.time_epoch']) - float(self.lastData['frame.time_epoch'])
            retVal['FirstProbePacket'] = self.firstProbe
        if self.eapPackets:
            retVal['EAPPackets'] = self.eapPackets

        # Option to save raw result for debug
        outFile = os.path.join(self.logdir, 'RawResult.txt')
        fh = open(outFile, 'w')
        pprint.pprint(retVal, fh)
        fh.close()

        return retVal

    def Verify11kRoam(self):
        '''
        Returns True if .11k roam event is detected,
        - RRMIE 70 in beacons
        - neighbor report request (action frame) after (re)assoc,response
        - neighbor report response (action frame) after report request
        '''
        # Parse roam if not done
        if not self.allPackets:
            self._ParseRoam()

        # Require these packets
        if not self.firstBeacon:
            self.logger.Warning('Failed 11k, did not parse first beacon')
            return False
        if len(self.actionPackets) != 2:
            self.logger.Warning('Failed 11k, got %d action packets, expect 2' % len(self.actionPackets))
            return False

        # Verify RRMIE 70 in beacons. IE70 shows as Reserved (70)
        for param in self.firstBeacon.GetTaggedParameters():
            if 'Reserved (70)' in param.GetShowName():
                break
        else:
            self.logger.Warning('Failed 11k, did not find RRMIE 70 in first beacon')
            return False

        # Verify neighbor request
        self.actionPackets  # TODO

        # Otherwise, successful
        return True

    def Verify11rRoam(self, ftOverDs=False):
        '''
        Returns True if .11r roam event is detected,
        - MDIE54 in beacons/assoc
        - "FT over IEEE 802.1X" or "FT using PSK" under RSNIE48 AKM Suite
        - Look for FTIE55 in reassoc
        - No EAP exchange after reassoc
        - No 4-way HS after reassoc

        FToverDS
        - No auth request before reassoc request
        - FToverDS support in MD IE 54 (beacons, re(assoc) frames)
        '''
        # Parse roam if not done
        if not self.allPackets:
            self._ParseRoam()

        # Require these packets
        if not self.firstBeacon:
            self.logger.Warning('Failed 11r, did not parse first beacon')
            return False
        if not self.reassocReq:
            self.logger.Warning('Failed 11r, did not parse assoc req')
            return False

        ret = True

        # Verify MDIE54 in beacon, assoc
        field = self.firstBeacon.GetFieldByName('wlan_mgt.mobility_domain.mdid')
        if field is None:
            self.logger.Warning('Failed 11r, did not find MDIE54 in beacon')
            ret = False
        field = self.reassocReq.GetFieldByName('wlan_mgt.mobility_domain.mdid')
        if field is None:
            self.logger.Warning('Failed 11r, did not find MDIE54 in assoc req')
            ret = False
        field = self.reassocResp.GetFieldByName('wlan_mgt.mobility_domain.mdid')
        if field is None:
            self.logger.Warning('Failed 11r, did not find MDIE54 in assoc resp')
            ret = False

        # Verify FTIE55
        field = self.reassocReq.GetFieldByName('wlan_mgt.ft.mic')
        if field is None:
            self.logger.Warning('Failed 11r, did not find FTIE55 in assoc req')
            ret = False

        # Verify no eap 4-way handshake after reassoc
        if self.eapPackets:
            self.logger.Warning('Failed 11r, should not have eap exchange after reassoc')
            ret = False

        if ftOverDs:
            # Verify no auth request after reassoc request
            if self.authReq or self.authResp:
                self.logger.Warning('Failed FToverDS, should not have auth')
                ret = False

            # Verify FToverDS support in MDIE 54 in beacon, assoc
            field = self.firstBeacon.GetFieldByName('wlan_mgt.mobility_domain.ft_capab.ft_over_ds')
            if field.GetValue() != 1:
                self.logger.Warning('Failed FToverDS, Beacon reports FToverDS %d, expected 1' % field.GetValue())
                ret = False
            field = self.reassocReq.GetFieldByName('wlan_mgt.mobility_domain.ft_capab.ft_over_ds')
            if field.GetValue() != 1:
                self.logger.Warning('Failed FToverDS, Assoc Req reports FToverDS %d, expected 1' % field.GetValue())
                ret = False

        # Otherwise, successful
        return ret


class EthernetPacketParse(object):

    def __init__(self):
        self.__captureFilter = CaptureFilter()
        # ist: ['eth', 'ip', 'udp', 'udpencap', 'esp']
        self.frameCommon = ['frame.time', 'frame.time_relativbe', 'frame.number', 'frame.len',
                            'frame.cap_len', 'frame.protocols']
        self.packetFields = { # eth
                             'eth':['eth.dst', 'eth.src', 'eth.type'],
                             # ip
                             'ip': ['ip.version', 'ip.hdr_len', 'ip.dsfield', 'ip.len', 'ip.id', 'ip.flags',
                             'ip.frag_offset', 'ip.ttl', 'ip.proto', 'ip.src', 'ip.src_host',
                             'ip.dst', 'ip.dst_host'],
                             # udp
                             'udp':['udp.srcport', 'udp.dstport', 'udp.length'],
                             # tcp
                            'tcp':['tcp.srcport', 'tcp.dstport', 'tcp.stream', 'tcp.len', 'tcp.seq', 'tcp.hdr_len'
                                    'tcp.flags', 'tcp.window_size', 'tcp.options'],
                             # ipv6
                             'ipv6':['ipv6.addr', 'ipv6.class', 'ipv6.dst', 'ipv6.dst_host', 'ipv6.flow',
                                     'ipv6.host', 'ipv6.opt', 'ipv6.sa_mac', 'ipv6.src', 'ipv6.src_host',
                                     'ipv6.version']
                             }
        self.packetDoneData = {}
        return


    def TextToBinPcap(self, packetString, captureFile='', formatted=True):
        '''
        formatted: True   already parsed to be

        0000  88 cb 87 80 36 49 04 0c ce 1d e4 18 08 00 45 00 00 82 7a 1c 00 00 40 11 62 5b c0 a8 0e 38 c0 a8
        0020  0e 6b df 6f 00 09 00 6e 18 51 ff ff ff ff ff ff 88 cb 87 80 36 49 88 cb 87 80 36 49 88 cb 87 80
        0040  36 49 88 cb 87 80 36 49 88 cb 87 80 36 49 88 cb 87 80 36 49 88 cb 87 80 36 49 88 cb 87 80 36 49
        0060  88 cb 87 80 36 49 88 cb 87 80 36 49 88 cb 87 80 36 49 88 cb 87 80 36 49 88 cb 87 80 36 49 88 cb
        0080  87 80 36 49 88 cb 87 80 36 49 88 cb 87 80 36 49

        formatted: False

        88cb87803649040cce1de4180800450000827a

        '''
        fileName = 'tmp_' + str(int(time()))
        txtFile = '/tmp/' + fileName + '.txt'
        if (captureFile == ''):
            return False
        else:
            userPath = os.path.expanduser('~')
            outCaptureFile = captureFile.replace('~', userPath)
            if (not os.access(os.path.dirname(outCaptureFile), os.W_OK | os.X_OK)):
                raise Exception('file %s not writeable' % str(outCaptureFile))
        if not formatted:
            pkt = self.__splitPair(packetString)
            pkt = '0: ' + pkt
        else:
            pkt = packetString
        echoSh = sh.Command('/bin/echo')
        cmdEcho = echoSh(pkt, _out=txtFile)
        txt2pcapCmd = sh.which('text2pcap')
        if (txt2pcapCmd == None):
            raise Exception('text2pcap was not found, please add it to your path')
        text2pcapSh = sh.Command(txt2pcapCmd)

        cmdPcap = text2pcapSh(txtFile, outCaptureFile)
        cmdLiteral = Literal('potential packet, wrote ')
        cmdNum = Word(nums)
        p = Group(SkipTo(cmdLiteral).suppress() + cmdLiteral.suppress() + cmdNum + restOfLine.suppress())
        try:
            numRecordsWritten = int(p.parseString(cmdPcap.stderr).asList()[0][0])
        except:
            numRecordsWritten = 0

        if (numRecordsWritten > 0):
            return True
        else:
            return False


    def TextToXmlPcap(self, packetString, captureFile='', numPackets=0, formatted=True):
        '''
        formatted: True   already parsed to be

        0000  88 cb 87 80 36 49 04 0c ce 1d e4 18 08 00 45 00 00 82 7a 1c 00 00 40 11 62 5b c0 a8 0e 38 c0 a8
        0020  0e 6b df 6f 00 09 00 6e 18 51 ff ff ff ff ff ff 88 cb 87 80 36 49 88 cb 87 80 36 49 88 cb 87 80
        0040  36 49 88 cb 87 80 36 49 88 cb 87 80 36 49 88 cb 87 80 36 49 88 cb 87 80 36 49 88 cb 87 80 36 49
        0060  88 cb 87 80 36 49 88 cb 87 80 36 49 88 cb 87 80 36 49 88 cb 87 80 36 49 88 cb 87 80 36 49 88 cb
        0080  87 80 36 49 88 cb 87 80 36 49 88 cb 87 80 36 49

        formatted: False

        88cb87803649040cce1de4180800450000827a

        '''

        fileName = 'tmp_' + str(int(time()))
        binTempPcap = '/tmp/' + fileName + '.cap'
        if (captureFile == ''):
            return False
        else:
            userPath = os.path.expanduser('~')
            outCaptureFile = captureFile.replace('~', userPath)
            if (not os.access(os.path.dirname(outCaptureFile), os.W_OK | os.X_OK)):
                raise Exception('file %s not writeable' % str(outCaptureFile))
        # create temp bin pcap file
        if (not self.TextToBinPcap(packetString, binTempPcap, formatted=formatted)):
            return False
        tFilter = ''
        if (not self.__captureFilter.PcapToXml(binTempPcap, tFilter , outCaptureFile, packetCount=numPackets)):
            return False
        return True


    def BintoXmlPcap(self, binCaptureFile, xmlCaptureFile, numPackets=0):
        if ((binCaptureFile == '') or (xmlCaptureFile == '')):
            return False
        else:
            userPath = os.path.expanduser('~')
            outXmlCaptureFile = xmlCaptureFile.replace('~', userPath)
            binCaptureFile = binCaptureFile.replace('~', userPath)
            if (not os.access(os.path.dirname(outXmlCaptureFile), os.W_OK | os.X_OK)):
                raise Exception('file %s not writeable' % str(xmlCaptureFile))
        tFilter = ''
        if (not self.__captureFilter.PcapToXml(binCaptureFile, tFilter , outXmlCaptureFile, packetCount=numPackets)):
            return False
        return True


    def ParsePcapFile(self, captureFile, packetFilters=None):
        userPath = os.path.expanduser('~')
        captureFile = captureFile.replace('~', userPath)
        self.packetDoneData = {}
        packetFiltersToProcess = self.packetFields.copy()

        if (packetFilters != None):
            packetFiltersToProcess.update(packetFilters)
        if type(captureFile) is FileType:
            try:
                self.packetRawData = ElementTree.parse(captureFile)
            except Exception, e:
                self._AddError('Failed to parse capture file, got %s' % repr(e))
        elif isinstance(captureFile, basestring) and os.path.isfile(captureFile):
            try:
                fh = open(captureFile)
                self.packetRawData = ElementTree.parse(fh)
            except:
                self.packetDoneData = {}
                return self.packetDoneData
        elif isinstance(captureFile, basestring):
            try:
                self.packetRawData = ElementTree.XML(captureFile)
            except Exception, e:
                self._AddError('Failed to parse capture file, got %s' % repr(e))


        else:
            raise NotValidInput('Must supply a valid path, file handle, or XML string')
        # get the header info
        self.__ParseData(packetFilters=packetFiltersToProcess)
        fh.close()
        return self.packetDoneData


    def __ParseData(self, packetFilters=None):
        '''

                self.packetFields = { #eth
                             'eth':['eth.dst','eth.src', 'eth.type'],
                             #ip
                             'ip': ['ip.version','ip.hdr_len','ip.dsfield','ip.len','ip.id','ip.flags',
                             'ip.frag_offset', 'ip.ttl','ip.proto','ip.src','ip.src_host',
                             'ip.dst','ip.dst_host'],
                             #udp
                             'udp':['udp.srcport','udp.dstport','udp.length']
                             }
        '''

        if (packetFilters == ''):
            packetFields = self.packetFields.copy()
        else:
            packetFields = packetFilters

        packets = self.packetRawData.findall('.//packet')
        # currentPacket = 0
        for packet in packets:
#            if last:
#                packet = packets[-1]
            packetNum = packet.findall('.//field[@name="frame.number"]')[0].attrib.get('show')
            if packetNum not in self.packetDoneData:
                self.packetDoneData[packetNum] = {}
            for field in self.frameCommon:
                data = packet.findall('.//field[@name="%s"]' % field)
                if len(data) > 1:
                    # handle multiple here
                    self.packetDoneData[packetNum][field] = []
                    for item in data:
                        self.packetDoneData[packetNum][field].append(item.attrib.get('show'))
                elif len(data) == 0:
                    self.packetDoneData[packetNum][field] = None
                else:
                    self.packetDoneData[packetNum][field] = data[0].attrib.get('show')

            if packet.findall('.//proto[@name="frame.protocols"]') > 0:
                try:
                    pktProtocols = self.packetDoneData[packetNum]['frame.protocols'].split(':')
                    for each in pktProtocols:
                        for pktField in packetFields[each]:
                            data = packet.findall('.//field[@name="%s"]' % pktField)
                            if len(data) > 1:
                                # handle multiple here
                                self.packetDoneData[packetNum][pktField] = []
                                for item in data:
                                    self.packetDoneData[packetNum][pktField].append(item.attrib.get('show'))
                            elif len(data) == 0:
                                self.packetDoneData[packetNum][pktField] = None
                            else:
                                self.packetDoneData[packetNum][pktField] = data[0].attrib.get('show')
                except:
                    pass
        return


    def __splitPair(self, inputString):

        a = ''.join([ char if not ind or ind % 2 else ' ' + char
                    for ind, char in enumerate(inputString)
                    ])
        return a

#----------------------------------------------------------------------
#
#----------------------------------------------------------------------

class PacketDiff(object):
    """ PacketDiff class for diff'ing between capture file (.xml) and template (.xml) """

    def __init__(self, captureFile='', templateFile=''):
        super(PacketDiff, self).__init__()
        self._logger = LogIt()
        self._captureFile = captureFile
        self._templateFile = templateFile
        self._capturePkts = []
        self._templatePkts = []

        # Read the files if provided
        if self._captureFile:
            self.SetCaptureFile(captureFile)
        if self._templateFile:
            self.SetTemplateFile(templateFile)

    def GetCaptureFile(self):
        return self._captureFile

    def SetCaptureFile(self, fname):
        parse = PacketParse(fname)
        if parse.GetErrors():
            return # Parse failed
        self._capturePkts = parse.ParseAll()
        self._captureFile = fname

    def GetCapturePackets(self):
        return self._capturePkts

    def SetCapturePackets(self, pkts):
        self._capturePkts = pkts

    def ClearCapturePackets(self):
        self._capturePkts = []

    def GetTemplateFile(self):
        return self._templateFile

    def SetTemplateFile(self, fname):
        try:
            self._templatePkts = PacketParse(fname).ParseAll()
            self._templateFile = fname
        except Exception, e:
            self._logger.Warning(str(e))

    def GetTemplatePackets(self):
        return self._templatePkts

    def SetTemplatePackets(self, pkts):
        self._templatePkts = pkts

    def ClearTemplatePackets(self):
        self._templatePkts = []

    def RunSimpleDiff(self, matchFields=True):
        """
        Runs a simple diff algorithm comparing the capture file with a template file.
        Returns a list of leftover Packet instances not matched.

        The algorithm iterates the template packets to find a match in the capture packets.
        The match first checks packet type and then checks all fields exist and match.

        Note: Simple diff function, may want something more advanced later =].
        """
        # Validate capture/template packets
        if not self._capturePkts:
            raise Exception('No capture packets are found!')
        elif not self._templatePkts:
            raise Exception('No template packets are found!')

        # Iterate all packets in capture and template
        captIdx = 0
        for (tempIdx, tempPkt) in enumerate(self._templatePkts):
            for captIdx in range(captIdx, len(self._capturePkts)):
                captPkt = self._capturePkts[captIdx]
                # self._logger.Debug('Found packet type: %d' % captPkt.GetWlanSubtype())

                # Packet types do not match, continue searching for a match
                if captPkt.GetWlanType() != tempPkt.GetWlanType() or \
                        (captPkt.GetWlanType() == tempPkt.GetWlanType() and \
                         captPkt.GetWlanSubtype() != tempPkt.GetWlanSubtype()):
                    continue

                # We've found a match
                self._logger.Info('Packet match: %s' % str(captPkt))
                if not matchFields:
                    # Option to skip field matching
                    break

                # Now see if fields are matching
                fieldMismatch = False
                for tempField in tempPkt.IterFields():

                    # Use packet override to determine if field is in __contains__
                    if tempField not in captPkt:

                        # Warn user, field mismatch can be missing field, value mismatch, other (ie. size)
                        name = tempField.GetName()
                        captField = captPkt.GetFieldByName(tempField.GetName())
                        if not captField:
                            self._logger.Debug('Field missing in capture packet: %s' % tempField)
                        elif tempField.GetValue() != captField.GetValue():
                            self._logger.Debug('Field mismatch %s: %s, capture returned %s' % (name, tempField, captField))
                        else:
                            self._logger.Debug('Field mismatch %s, capture returned %s' % (tempField, captField))

                        fieldMismatch = True
                        break # No need to check further

                    # Field match
                    self._logger.Debug('Field match: %s' % tempField)

                if fieldMismatch:
                    continue

                # Packet and fields match, move to next template packet
                break
            else:
                # Reached the end of captured packets and did not find the template packet
                self._logger.Warning('Failed to find packet: %s' % str(self._templatePkts[tempIdx]))
                return self._templatePkts[tempIdx:]

        # Found all template packets, capt_idx < len(capt_pkts)
        return []

#----------------------------------------------------------------------
#  MAIN
#----------------------------------------------------------------------

def main1():
    '''
    Test on my class
    '''
    # from pprint import PrettyPrinter
    # ppp = PrettyPrinter()
    # pp = getattr(ppp, 'pprint')
    pkt = 'b8f6b13e0bb2040cce1de418080045000082c4c3000040111916c0a80d6fc0a80dd2e1710009006e228effffffffffffb8f6b13e0bb2b8f6b13e0bb2b8f6b13e0bb2b8f6b13e0bb2b8f6b13e0bb2b8f6b13e0bb2b8f6b13e0bb2b8f6b13e0bb2b8f6b13e0bb2b8f6b13e0bb2b8'
    myParse = EthernetPacketParse()

    # myParse.TextToPcap(pkt,captureFile='/bin/blahblah.cap')
    capFile = '~/Desktop/mycapture.cap'
    binCapFile = '~/Desktop/caps/mybincapture.cap'
    xmlCaptureFile = '~/Desktop/caps/myXmlCapture.cap'
    myPacketFields = {
                      'esp':['esp.spi', 'esp.sequence'],
                      'afp':['afp.command', 'afp.pad']
                             }
    stat = myParse.TextToXmlPcap(pkt, captureFile=capFile)
    stat = myParse.BintoXmlPcap(binCapFile, xmlCaptureFile, numPackets=100)
    # stat = myParse.TextToXmlPcap(pkt,captureFile=capFile,packetFilters=myPacketFields)
    userPath = os.path.expanduser('~')
    xmlCaptureFile = xmlCaptureFile.replace('~', userPath)
    parsed = myParse.ParsePcapFile(captureFile=xmlCaptureFile, packetFilters=myPacketFields)
    parsed2 = myParse.ParsePcapFile(captureFile=xmlCaptureFile)

    userPath = os.path.expanduser('~')
    outCaptureFile = capFile.replace('~', userPath)
    myParse.ParsePcapFile(outCaptureFile)

    if (myParse.TextToBinPcap(pkt, captureFile=outCaptureFile)):
        myParse.ParsePcapFile(captureFile=outCaptureFile)

#    test = PacketParse('/Users/friesen/Work/WiFi/trunk/WiFi/Sundance/Tests/Samples/MyOutputFile.xml')
#    test = PacketParse('/Users/friesen/Work/WiFi/trunk/WiFi/Sundance/Tests/Samples/test3.xml')
#    test = PacketParse('/Users/friesen/Work/WiFi/trunk/WiFi/Sundance/Tests/Samples/LastData.xml')
#    pp(test.ParseCommonFields())
#    pp(test.ParseLastPacket(customFields=['frame.time_epoch', 'radiotap.flags']))
#    pp(test.ParseCustomFields(['frame.time_epoch', 'radiotap.flags']))
    # test = RoamPacketParse('AdeptCapture1_03-06-2012_12-08-42-dot11.pcap', '1', roamCaptureTwo='AdeptCapture2_03-06-2012_12-08-43-dot11.pcap')
    # pp(test.GenerateResults())


def main2():
    # Inputs
    captureFile = '../../Tools/CaptureDiffExample/J1-capture.pcap'
    captureXml = 'temp.xml'
    templateXml = '../../Tools/CaptureDiffExample/template.xml'
    filterStr = '(wlan.sa == %(dutMacAddr)s || wlan.sa == %(apMacAddr)s) \
&& (wlan.da == %(dutMacAddr)s || wlan.da == %(apMacAddr)s) \
&& (wlan.fc.type_subtype >= 0 && wlan.fc.type_subtype <= 15)' % dict(
        dutMacAddr='b8:17:c2:ad:87:9a',
        apMacAddr='F0:25:72:3C:42:4B', # '58:BC:27:92:54:51', #'F0:25:72:3C:42:4B', #
    )

    # Run .pcap to .xml
    from SnifferTools import CaptureFilter
    f = CaptureFilter()
    f.PcapToXml(captureFile, filterStr, captureXml, packetCount=0)

    # Run parser
    p = PacketParse(captureXml)
    pkts = p.ParseAll()
    print 'WLAN Packets in %s:' % repr(captureXml)
    for pkt in pkts:
        print '    ', str(pkt)

    # Diff w/ template
    diff = PacketDiff(captureXml, templateXml)
    ret = diff.RunSimpleDiff()
    if ret:
        print 'Failed to find packets:'
        for pkt in ret:
            print '    ', str(pkt)


if __name__ == '__main__':
    main2()
