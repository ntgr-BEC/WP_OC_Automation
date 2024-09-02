'''
WiFiModulesExceptions.py
WirelessAutomation

Created by Quint Friesen 2012-3-2
Copyright (c) 2012 Apple. All rights reserved.
'''
from LogInit import LogIt
from WirelessAutomation import WirelessError

class MissingModule(Exception):
    '''
    Exception class that tells user pxssh is not on the system
    '''
    def __init__(self, value):
        self.value = value

    def __str__(self):
        return repr(self.value)

class ConnectionError(Exception):
    '''
    Exception class that tells user the connection failed
    '''
    def __init__(self, value):
        self.value = value

    def __str__(self):
        return repr(self.value)


class WiFiSkipTestError(WirelessError):
    ''' Exception class to return TEST_SKIP '''

    # <rdar> description
    # TODO: replace WirelessError and remove below work-around

    def __init__(self, value):
        self.value = value

    def __str__(self):
        # Work-around to report TEST_SKIP to TESLA
        logger = LogIt()
        logger.Info('[TEST_SKIP]')
        return repr(self.value)
