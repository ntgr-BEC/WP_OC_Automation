#!/usr/bin/env python
#-----------------------------------------------------------------------
# Copyright (c) 2012 Apple
# All Rights Reserved
#
# Proprietary and Confidential
#-----------------------------------------------------------------------

""" Provides interface / utility for wl command-line utility """

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

class wl(object):
    """ Interface to apple80211 utility """

    def __init__(self, osIntf):
        super(wl, self).__init__()
        self._logger = LogIt()
        self._os = osIntf
        self._errors = []
        assert isinstance(osIntf, OS)  # requires OS interface

    def _run(self, *args, **kwargs):
        """ Executes the command and returns the output """

        # Check for valid handle
        if not self._os:
            self._logger.Fail('Failed to execute command, invalid handle to OS')
            return

        # Construct the command to execute
        cmd = 'wl'
        for arg in args:
            cmd += ' ' + arg

        # Execute
        self._logger.Debug(cmd)
        res = self._os.Execute(cmd)
        if res.returnCode != 0:
            self._AddError('Command %s return code %d. StdOut: %s, StdErr: %s' % \
                (cmd, res.returnCode, res.standardOutput.strip(), res.standardError.strip()))

        # Return the string output
        ret = res.standardOutput.strip()

        # WAF logger does strange % (), causing error:
        # *** TypeError: not enough arguments for format string
        # So let's replace any %%
        if '%' in ret:
            ret = ret.replace('%', '%%')

        self._logger.Debug(ret)
        return ret

    def _AddError(self, msg):
        self._logger.Warning(msg)
        self._errors.append(msg)

    def ClearErrors(self):
        self._errors = []

    def GetErrors(self):
        return self._errors

    def HelpInfo(self):
        return self._run()

    def GetVersion(self):
        return self._run('ver')

    def GetStatus(self):
        """ Returns a status string """
        return self._run('status')

    def GetCapabilities(self):
        """ Returns a list of WiFi capabilities """
        return self._run('cap').split(' ')

    def GetRevisionInfo(self):
        """ Returns hardware revision info as a dict """
        ret = self._run('revinfo')
        info = {}
        for line in ret.splitlines():
            key, val = line.split(' ')
            info[key] = val
        return info

    def GetSsid(self):
        ret = self._run('ssid')
        m = re.search('Current SSID\: \"(.*?)\"', ret)
        if m:
            return m.group(1)
        self._logger.Warning('Failed to parse ssid from %s' % ret)
        return ''

    def GetBssid(self):
        return self._run('bssid').lower()

    def GetRssi(self):
        """ Returns RSSI (float) in dBm """
        return float(self._run('rssi'))

    def GetNoise(self):
        """ Returns noise (float) in dBm """
        try:
            return float(self._run('noise'))
        except Exception, e:
            self._logger.Warning('Failed to parse noise from %s' % e)
            return '-174'

    def GetBand(self):
        """ Returns current band, 'a' or 'b' """
        return (self.GetChannel() <= 14) and 'b' or 'a'

    def GetChannel(self):
        """ Returns WLAN channel number (int) """
        ret = self._run('channel')
        m = re.search('current mac channel\s+(\d+)', ret, re.DOTALL)
        if m:
            return int(m.group(1))
        self._logger.Warning('Failed to parse channel from %s' % ret)
        return 1

    def GetSupportedChannels(self):
        """ Returns list of supported channels """
        return map(int, self._run('channels').split(' '))

    def GetDataRate(self):
        """ Returns data rate (float) in Mbps """
        ret = self._run('rate')
        m = re.search('(\S+)\s+Mbps', ret)
        if m:
            return float(m.group(1))
        self._logger.Warning('Failed to parse data rate from %s' % ret)
        return 0.0

    def MemUse(self):
        return self._run('memuse')

    #----------------------------------------------------------------------
    #  Roaming
    #----------------------------------------------------------------------

    def GetRoamTrigger(self, band):
        """ Returns roam trigger (float) for band 'a' or 'b' """
        ret = self._run('roam_trigger %s' % band)
        m = re.search('roam_trigger is \S+\((\S+)\)', ret)
        if m:
            return float(m.group(1))
        self._logger.Warning('Failed to parse roam_trigger from %s' % ret)
        return 0.0

    def GetRoamDelta(self, band):
        """ Returns roam delta for band 'a' or 'b' """
        ret = self._run('roam_delta %s' % band)
        m = re.search('roam_delta is \S+\((\S+)\)', ret)
        if m:
            return float(m.group(1))
        self._logger.Warning('Failed to parse roam_delta from %s' % ret)
        return 0.0

    def GetRoamMultiApEnv(self):
        """ Returns True if WiFi detects a Multi-AP environment """
        try:
            return bool(int(self._run('roam_multi_ap_env')))
        except Exception, e:
            # Command may fail, bug?
            self._logger.Debug(str(e))
            return False

    def GetPmkIdInfo(self):
        ret = self._run('pmkid_info')
        return re.findall('PMKID\[\d+\]\:\s+(\S+)\s+\=(.*?)$', ret, re.DOTALL | re.MULTILINE)

    #----------------------------------------------------------------------
    #  Power
    #----------------------------------------------------------------------

    def GetPsMode(self):
        """
        Returns power mode enum
        0 = unassociated or PH enabled
        2 = associated, after getting IP / timing out
        """
        return int(self._run('PM'))

    def GetFrtsTime(self):
        """ Returns the fast-return-to-sleep (FRTS) time to power save in msec """
        return int(self._run('pm2_sleep_ret').split()[0])

    #----------------------------------------------------------------------
    # Wow LPAS
    #----------------------------------------------------------------------
    def GetPktFilterList(self, enable):
        '''
        Return Filters number that is enable or disabled
        enable need to be '1' or '0'
        '''
        ret = self._run('pkt_filter_list %s' % enable)
        m = re.search('Num filters:\s+(\d+)', ret)
        if m:
            return int(m.group(1))
        self._logger.Warning('Failed to get Pkt Filter')
        return ''

    def GetPktFilterMax(self):
        """ Returns the fPkt Filter Max """
        return int(self._run('pkt_filter_max').split()[0])

    def GetArpoe(self):
        """ Return arpoe """
        return int(self._run('arpoe'))

    def GetPktFilterIcmp(self):
        """ Return pkt_filter_icmp """
        return int(self._run('pkt_filter_icmp'))

    def GetNdoe(self):
        """ Return ndoe """
        return int(self._run('ndoe'))

    def GetBcnLiDtim(self):
        """ Return bcn_li_dtim """
        return int(self._run('bcn_li_dtim'))

    def GetBcnLiBcn(self):
        """ Return bcn_li_bcn """
        return int(self._run('bcn_li_bcn'))

    def GetPm2BcnSleepRet(self):
        """ Returns the pm2_bcn_sleep_ret """
        return int(self._run('pm2_bcn_sleep_ret').split()[0])

    def GetExcessPmPeriod(self):
        """ Return excess_pm_period """
        return int(self._run('excess_pm_period'))

    def GetExcessPmPercent(self):
        """ Returns the excess_pm_percent """
        return int(self._run('excess_pm_percent').split()[0])

    def GetPfnRoamAlertThresh(self):
        """ Returns the pfn_roam_alert_thresh """
        ret = self._run('pfn_roam_alert_thresh').split('\n')
        pfnAlertThresh = int(ret[0].split()[1])
        roamAlertThresh = int(ret[1].split()[1])
        return int(pfnAlertThresh or roamAlertThresh)

    def GetPmBcmcWait(self):
        """ Returns the pm_bcmc_wait """
        return int(self._run('pm_bcmc_wait').split()[0])

    def GetNolinkup(self):
        """ Return nolinkup """
        return int(self._run('nolinkup'))

    def GetEventMsgs(self):
        """ Return event_msgs """
        return str(self._run('event_msgs'))

    def GetPspollPrd(self):
        """ Return pspoll_prd """
        return str(self._run('pspoll_prd'))

    def GetPm2SleepRet(self):
        """ Returns the pm2_sleep_ret """
        return int(self._run('pm2_sleep_ret').split()[0])

    def GetPm2SleepRetExt(self):
        """ Returns the pm2_sleep_ret_ext """
        ret = self._run('pm2_sleep_ret_ext').split('\n')
        low = int(ret[1].split()[1])
        high = int(ret[2].split()[1])
        txrxPkts = int(ret[5].split()[1])
        return [low, high, txrxPkts]

    def GetTlv(self):
        """ Returns the tlv """
        return int(self._run('tlv').split()[0])

    def GetPktFilterPorts(self):
        """ Returns the pkt_filter_ports """
        return str(self._run('pkt_filter_ports'))
    #----------------------------------------------------------------------
    #
    #----------------------------------------------------------------------

    def GetCountersStr(self):
        """ Returns counters struct as a string """
        return self._run('counters')

    def SupportLpas(self):
        """ Returns whether device support LPAS"""
        return ('lpas' in self._run('cap'))

    def SupportHS20(self):
        """ Returns whether device support HS 2.0"""
        return ('anqpo' in self._run('cap'))

    def ChangeCountry(self, country):
        '''
        Sets the country code
        '''
        self._logger.Info("Setting country code on %s to %s", self._os.purpleDevice.udid, country)
        self._run("country " + country)

    def ChangeCountryXZ(self):
        self.ChangeCountry("XZ")

    def ChangeCountryUM(self):
        self.ChangeCountry("UM")

#----------------------------------------------------------------------
#
#----------------------------------------------------------------------

def main():
    dev = cam.CAMEmbeddedDevice.allDevices()[0]  # @UndefinedVariable
    wifi = waf.WiFiDevice(dev, logger=LogIt())
    myWl = wl(wifi.getOS())
    print myWl.GetVersion()

if __name__ == '__main__':
    main()


