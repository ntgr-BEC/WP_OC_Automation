#!/usr/bin/env python
"""
Provides WiFiManager, interface/utility for mobilewifitool

Created by aomoto on 2/16/13
Copyright (c) 2013 Apple. All rights reserved.
"""

import os
import sys
import WirelessAutomation as waf
import CoreAutomation as cam
from LogInit import LogIt
from common.OSCommon import OS

#----------------------------------------------------------------------
#
#----------------------------------------------------------------------

class WiFiManager(object):
    """ WiFiManager interface, using mobilewifitool utility """

    def __init__(self, osIntf):
        super(WiFiManager, self).__init__()
        self._logger = LogIt()
        self._os = osIntf
        self._awdlIf = 'awdl0'
        assert isinstance(osIntf, OS)  # requires OS interface

    def _run(self, *args, **kwargs):
        """ Executes the command and returns the output """

        # Check for valid handle
        if not self._os:
            self._logger.Fail('Failed to execute command, invalid handle to OS')
            return

        # Construct the command to execute
        cmd = 'mobilewifitool --'
        for arg in args:
            cmd += ' ' + arg

        # Execute
        self._logger.Debug(cmd)
        res = self._os.Execute(cmd)
        if res.returnCode != 0:
            self._logger.Fail('Error %d: Failed to execute command %s' % (res.returnCode, cmd))

        # Return the string output
        ret = res.standardOutput.strip()

        # WAF logger does strange % (), causing error:
        # *** TypeError: not enough arguments for format string
        # So let's replace any %%
        if '%' in ret:
            ret = ret.replace('%', '%%')

        self._logger.Debug(ret)
        return ret

    def Help(self, name):
        return self._run('help %s' % name)

    def Info(self):
        return self._run('tree')

    #----------------------------------------------------------------------
    #  AutoJoin
    #----------------------------------------------------------------------

    def GetMultiStageEnable(self):
        return ('Enabled' in self._run('autojoin --multistage'))

    def SetMultiStageEnable(self, enable):
        self._run('autojoin --multistage=%d' % int(enable))

    def SetAskToJoinEnable(self, enable):
        self._run('asktojoin %d' % int(enable))

    def IsLpasEnabled(self):
        return ('Enabled' in self._run('lpas'))
#----------------------------------------------------------------------
#
#----------------------------------------------------------------------

def main():
    from DUT.DUT_Utils import GetWiFiDevices
    dut = GetWiFiDevices()[0]

    mgr = WiFiManager(dut.os)
    print '    AutoJoin:        %d' % mgr.GetAutoJoinEnable()
    print '    MultiStage:      %d' % mgr.GetMultiStageEnable()

if __name__ == '__main__':
    main()

