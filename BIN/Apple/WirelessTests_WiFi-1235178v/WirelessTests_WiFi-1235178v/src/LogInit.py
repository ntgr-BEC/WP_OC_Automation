"""
LogInit - Common Logger

Created by qfriesen
Last modified by aomoto on 2013-04-26
Copyright (c) 2012 Apple, Inc. All rights reserved.
"""

import logging
import sys
import os
import time
from datetime import datetime
import plistlib

# Define exports here
__all__ = [
    'LogIt',
    'GetConfig',
]

def GetConfig(defaultPlist='default.plist'):
    """ Returns WAF ConfigurationInfo.Config if initialized, otherwise defaultPlist """

    if sys.argv[0] == '/usr/local/bin/WATestHarness' or sys.argv[0] == '/usr/local/bin/WATestManager':
        from common.ConfigurationInfo import ConfigurationInfo, Config
        if not Config:
            # Verify config path
            if not os.path.exists(defaultPlist):
                LogIt().Warning('Config file does not exist: %s' % repr(defaultPlist))
                return {}
            Config = ConfigurationInfo(defaultPlist)

        # Fix ConfigurationInfo should be Plist subclass
        if not isinstance(Config, plistlib._InternalDict):
            temp = plistlib.Plist()
            temp.update(Config.__dict__)
            Config = temp

    else:
        # Search all parent dirs
        for i in range(os.path.abspath('.').count('/')):
            if os.path.exists(defaultPlist):
                break
            defaultPlist = os.path.join('..', defaultPlist)

        # Otherwise, return default plist config
        try:
            Config = plistlib.readPlist(defaultPlist)
        except Exception, e:
            LogIt().Warning(str(e))
            Config = plistlib.Plist()

    return Config


def LogIt(DEBUG=False):
    """ Returns WAF SharedLogger if initialized, otherwise PrivateLogger """
    if sys.argv[0] == '/usr/local/bin/WATestHarness' or sys.argv[0] == '/usr/local/bin/WATestManager':
        import common.WirelessLogging as WirelessLogging
        logger = WirelessLogging.SharedLogger
    else:
        logger = PrivateLogger(DEBUG)
    return logger


class PrivateLogger(object):
    """ Internal Logger, if WAF is not initialized """

    def __init__(self, DEBUG=False):
        super(PrivateLogger, self).__init__()
        self.DEBUG = DEBUG
        from pprint import PrettyPrinter
        ppp = PrettyPrinter()
        self.logger = getattr(ppp, 'pprint')
        self._loggers = []
        self._test = None
        self.testLogFolder = os.path.join('./Logs', 'PrivateLogger' + "_" + time.strftime('%Y-%m-%d_%H-%M') + "_" + str(os.getpid()))

    def SetCurrentTest(self, test):
        self._test = test

    def StartTest(self, test):
        pass

    def EndTest(self, test):
        pass

    def LoggerComplete(self):
        self._test = None

    def Fail(self, message, *args):
        self._Logger('FAIL', message, *args)

    def Pass(self, message, *args):
        self._Logger('PASS', message, *args)

    def Info(self, message, *args):
        self._Logger('INFO', message, *args)

    def Warning(self, message, *args):
        self._Logger('WARN', message, *args)

    def Debug(self, message, *args):
        self._Logger('DEBUG', message, *args)

    def TestName(self):
        if self._test:
            return self._test.name
        return ''

    def DeviceInfo(self, device):
        pass

    def DidCurrentTestFail(self):
        return False

    def LogFileFromDevice(self, device, devicePath, hostKey=''):
        pass

    def LogFileSourceFromDevice(self, device, fileSource, hostKey=''):
        pass

    def LogFileFromFileSource(self, device, fileSource, devicePath, hostKey=''):
        pass

    def LogFileFromHost(self, hostPath, hostKey='', localCopy=True):
        pass

    def LogFromStandardOutput(self, standardOutput, fileName, hostKey='', device=None):
        pass

    def SetPerfCategory(self, performanceCategory, testName, train, model, name, build):
        pass

    def PerfResult(self, metricID, value, unit):
        pass

    def LogPerfMeasurement(self, description, measurement, units, largerIsBetter, value, append=True):
        pass

    def SetTestProperty(self, key, value):
        pass

    def setDeviceProperty(self, prop, value):
        pass

    def GetTestLogFolderName(self):
        return self.testLogFolder

    def StartMuninLogging(self, test, testVersion=None, testState=None):
        pass

    def EndMuninLogging(self, test):
        pass

    def SyncResultsToMunin(self, test):
        pass

    def SetMuninStatus(self, status):
        pass

    def getTestLogsPath(self, device=None, hostKey=''):
        if device is None:
            logDirSubPath = os.path.join(self.testLogFolder, hostKey, '')
        else:
            logDirSubPath = os.path.join(self.testLogFolder, device.hardware+ '_' + device.udid, hostKey, '')
        if not os.path.exists(logDirSubPath):
            os.makedirs(logDirSubPath)
        return logDirSubPath

    def AddLogger(self, logger):
        self._loggers.append(logger)

    def SetTestProperty(self, key, val):
        pass

    def _Logger(self, logType, message, *args):
        if self.DEBUG:
            import inspect
            message = '%s - called from %s line %s' % (message % args, inspect.stack()[2][1], inspect.stack()[2][2])
        #self.logger('%s - %s - %s' % (datetime.now().isoformat(' '), logType, message % args))
        else:
            message = message % args

        print '%s - %s - %s' % (datetime.now().isoformat(' '), logType, message)
