#!/usr/bin/env python
#-----------------------------------------------------------------------
# Copyright (c) 2012 Apple
# All Rights Reserved
#
# Proprietary and Confidential
#-----------------------------------------------------------------------

""" Provides interface / utility for apple80211 command-line utility """

# Python modules
import os
import sys
import re

# Internal modules
import WirelessAutomation as waf
import CoreAutomation as cam
from LogInit import LogIt
from common.OSCommon import OS

#----------------------------------------------------------------------
#
#----------------------------------------------------------------------

class Apple80211(object):
    """ Interface to apple80211 utility """

    def __init__(self, osIntf):
        super(Apple80211, self).__init__()
        self._logger = LogIt()
        self._os = osIntf
        assert isinstance(osIntf, OS)  # requires OS interface

        # Init
        self._versionStr = self._run('--driver').split(':\n')[-1]

    def _run(self, *args, **kwargs):
        """ Executes the command and returns the output """

        # Check for valid handle
        if not self._os:
            self._logger.Fail('Failed to execute command, invalid handle to OS')
            return

        # Construct the command to execute
        cmd = 'apple80211'
        for arg in args:
            cmd += ' ' + arg

        # Execute
        self._logger.Debug(cmd)
        res = self._os.Execute(cmd)
        if res.returnCode != 0:
            self._logger.Info('Command %s return code %d. StdOut: %s, StdErr: %s' % (cmd, res.returnCode, res.standardOutput.strip(), res.standardError.strip()))

        # Return the string output
        ret = res.standardOutput.strip()

        # WAF logger does strange % (), causing error:
        # *** TypeError: not enough arguments for format string
        # So let's replace any %%
        if '%' in ret:
            ret = ret.replace('%', '%%')

        self._logger.Debug(ret)
        return ret

    def HelpInfo(self):
        return self._run()

    def GetVersionStr(self):
        """ Returns full version string """
        return self._versionStr

    def GetDriverVersion(self):
        m = re.search('(AppleBCMWLANV2-\d+)', self.GetVersionStr(), re.DOTALL)
        assert m  # Version string not found!
        return m.group(1)

    def GetIoVersion(self):
        m = re.search('(IO80211-\d+)', self.GetVersionStr(), re.DOTALL)
        assert m  # Version string not found!
        return m.group(1)

    def GetBssid(self):
        return self._run('-bssid').strip('bssid = ')

    def GetDataRate(self):
        return self._run('-rate').strip('rate: ')

    def GetWowEnable(self):
        return bool(self._run('-wow') == 'wake on wireless: on')

    def SetWowEnable(self, enable):
        '''
        Using Apple80211 tool to enable WoW in driver. This function is for debugging purpose only, it won't be able to enable WoW in the system'
        '''
        self._run('-wow=%s' % (enable and 'on' or 'off'))

    def SetDriverScanSuppress(self, enable):
        ''' Enable scan suppression, for debugging only '''
        self._run('-dbg=a-refuse-scans=%s' % (enable and '1' or '0'))

    def ToggleSetDriverScanSuppress(self):
        ''' Toggles scan suppression, for debugging only '''
        self._run('-dbg=a-refuse-scans')

#----------------------------------------------------------------------
#
#----------------------------------------------------------------------

def main():
    dev = cam.CAMEmbeddedDevice.allDevices()[0] # @UndefinedVariable
    wifi = waf.WiFiDevice(dev, logger=LogIt())
    apl = Apple80211(wifi.getOS())
    #print apl.HelpInfo()
    print apl.GetDriverVersion()
    print apl.GetIoVersion()
    print apl.GetBssid()

if __name__ == '__main__':
    main()


