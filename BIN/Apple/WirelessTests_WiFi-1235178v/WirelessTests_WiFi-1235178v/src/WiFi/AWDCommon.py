#!/usr/bin/env python
#-----------------------------------------------------------------------
# Copyright (c) 2013 Apple
# All Rights Reserved
# Jim Collum
# Proprietary and Confidential
#-----------------------------------------------------------------------

import os

from common.WirelessTest import *
WiFiTest().AddPath()
from common.WirelessException import *
from common.WirelessCommon import PurpleDevice, WiFiDevice
from LogInit import LogIt, PrivateLogger

class AWDCommon(object):

    def __init__(self,dut):
        self._dut = dut
        self.Debug = True
        if self.Debug:
            self._logger = LogIt(DEBUG = True)
        else:
            self._logger = LogIt(DEBUG = False)

    def CleanAWDMetricFileDir(self, fileLocation):
        cmd = 'rm -fvr ' + fileLocation + '/*'
        result = self._dut.getOS().Execute(cmd, block = True, runAsRoot = True)
        if self.Debug:
            self._logger.Debug('CleanAWDMetricFileDir result.standardOutput:\n%s',result.standardOutput)

    def InstallAWDConfig(self, configFileName):
        # copy file to dut  .CopyFileFromHost
        self._dut.getOS().CopyFileFromHost(configFileName, '/var/root')
        configFile = os.path.basename(configFileName)

        cmd = '/usr/local/bin/AWDConfigurer /var/root/' + configFile + ' 1'
        result = self._dut.getOS().Execute(cmd, block = True, runAsRoot = True)
        if self.Debug:
            self._logger.Debug('InstallAWDConfig installed:%s   result:\n%s', configFile, result)

    def Trigger_24HourMetric(self):
        cmd = '/usr/local/bin/AWDTestingClient -c 12 -t 520207 -x'
        result = self._dut.getOS().Execute(cmd, block = True, runAsRoot = True)
        if self.Debug:
            self._logger.Debug('Trigger_24HourMetric result:\n%s', result.standardOutput)

    def ListMetricFiles(self, remoteDir):
        dirContents = self._dut.getOS().ContentsOfDirectoryAtPath(remoteDir)
        if self.Debug:
            self._logger.Debug('dirContents:\n%s', dirContents)
        return dirContents

    def DecodeMetricFromFile(self, fileName):
        cmd = '/usr/local/bin/AWDDisplay ' + fileName
        result = self._dut.getOS().Execute(cmd, block = True, runAsRoot = True)
        if self.Debug:
            self._logger.Debug('DecodeMetricFromFile: result:\n%s', result.standardOutput)
        return result.standardOutput


def main():
    print('main')

if __name__ == '__main__':
    main()

