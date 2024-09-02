'''
HostToolsExceptions.py
WirelessAutomation

Created by Quint Friesen 2012-3-2
Copyright (c) 2012 Apple. All rights reserved.
'''

class NotValidInput(Exception):
    '''
    Exception class that tells user AP Config values are invalid
    '''
    def __init__(self, value):
        self.value = value

    def __str__(self):
        return repr(self.value)

class InvalidCaptureFile(Exception):
    '''
    Exception class that tells user AP Config values are invalid
    '''
    def __init__(self, value):
        self.value = value

    def __str__(self):
        return repr(self.value)

class CaptureParseError(Exception):
    '''
    Exception class that tells user AP Config values are invalid
    '''
    def __init__(self, value):
        self.value = value

    def __str__(self):
        return repr(self.value)