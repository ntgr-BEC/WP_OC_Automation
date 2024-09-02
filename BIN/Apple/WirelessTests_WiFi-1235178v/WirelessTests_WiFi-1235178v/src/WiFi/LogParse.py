'''
LogParse.py
WirelessAutomation

Updated by aomoto 2012-12-12
Created by Meng Wang on 2/16/10 as WiFi_StressCommon.py
Copyright (c) 2010 Apple. All rights reserved.
'''
from common import WirelessLogging, OSCommon
import os
import logging
from common.ConfigurationInfo import Config
from common.WirelessException import TestFailError
from common.MacUtils import wirelessautomation_version
from time import sleep
import sys
import re
from common.WirelessLogging import Sleep
from common import WirelessCommon
from datetime import datetime, timedelta
from LogErrors import *
from LogInit import LogIt

#----------------------------------------------------------------------
#
#----------------------------------------------------------------------

CrashReporterDirs = ['/Library/Logs/CrashReporter/', '/var/mobile/Library/Logs/CrashReporter/']
CoreCaptureFolderNames = ['com.apple.driver.AppleBCMWLANCore', 'com.apple.driver.AppleBCMWLANCore0', 'com.apple.driver.AppleBCMWLANCore1', ]
CoreCapturePaths = [os.path.join(x, y) for x in CrashReporterDirs for y in CoreCaptureFolderNames]

class LogRecord(object):
    ''' LogRecord is used to navigate parsed log entries from iOS device logs '''

    def __init__(self, msg, name='', level='', logTime=None):
        super(LogRecord, self).__init__()
        self._msg = msg
        self._name = name
        self._level = level
        self._logTime = logTime

    def GetLoggerName(self):
        return self._name

    def SetLoggerName(self, name):
        self._name = name

    def GetLevelStr(self):
        ''' Returns log level string, ie. Notice '''
        return self._level

    def SetLevelStr(self, level):
        ''' Set log level str, ie. Notice '''
        self._level = level

    def GetLogTime(self):
        ''' Returns time as datetime '''
        return self._logTime

    def SetLogTime(self, logTime):
        ''' Set log time as datetime '''
        self._logTime = logTime

    def GetMessage(self):
        return self._msg

    def SetMessage(self, msg):
        self._msg = msg


class SyslogParse(object):
    ''' Class for WiFi-parsing methods for a log file or text '''

    def __init__(self, fname=None):
        super(SyslogParse, self).__init__()
        self._logger = LogIt()
        self._fname = ''
        self._txt = ''
        if fname is not None:
            self.Open(fname)

    def Open(self, fname):
        self._logger.Info('Opening file: %s' % fname)

        # Validate log file
        if not os.path.exists(fname):
            self._logger.Fail('Cannot open file, file does not exist: %s' % fname)
            return

        # Save text for parsing
        fh = open(fname, 'r')
        self._txt = fh.read()
        fh.close()

        # File open succeeded
        self._fname = fname

    def GetFilename(self):
        return self._fname

    def GetText(self):
        return self._txt

    def SetText(self, txt):
        self._txt = txt

    #----------------------------------------------------------------------
    #  PARSE
    #----------------------------------------------------------------------

    AUTOJOIN_START = 'Attempting multi-stage auto join mStageAutoJoinAttempts=0'
    AUTOJOIN_START += '|Fallback to existing auto-join mStageAutoJoinAttempts=0'
    AUTOJOIN_STOP = 'Auto join association completed.*?$'
    AUTOJOIN_STOP += '|Async association request completed.*?$'
    AUTOJOIN_STOP += '|Aborted current auto-join session.*?$'
    # AUTOJOIN_STOP += '|Fallback to existing auto-join.*?$'

    def ParseAutoJoinEvents(self):
        ''' Returns a list of dict for parsed AutoJoin events '''
        return [self._ParseAutoJoinInfo(block) for block in self._ParseAutoJoinBlocks()]

    def _ParseAutoJoinBlocks(self):
        ''' Returns list of text blocks containing autojoin events '''
        self._logger.Debug('Parsing AutoJoin Stage events')
        txt = self._txt

        blocks = self._ParseLogBlocks(txt, self.AUTOJOIN_START, self.AUTOJOIN_STOP)
        self._logger.Info('Found %d autojoin event(s)' % len(blocks))

        # Warn user, could not find autojoin
        if len(blocks) == 0:
            self._logger.Warning('Could not find any autojoin events')
            self._logger.Warning('   Starting with: %s' % repr(self.AUTOJOIN_START))
            self._logger.Warning('   Ending with:   %s' % repr(self.AUTOJOIN_STOP))

        return blocks

    def _ParseAutoJoinInfo(self, block):
        '''
        Returns a dict of autojoin info, parsed from AutoJoin log text block
        The following data is parsed for each event:
            rfChannel, dwellTimeMsec, autoJoinTimeSec
        '''
        ret = dict(
            prevNetwork=self._ParseAutoJoinPreviousNetwork(block),
            rfChannel=self._ParseAutoJoinChannel(block),
            dwellTimeMsec=self._ParseAutoJoinDwellTime(block),
            autoJoinTimeSec=self._ParseAutoJoinCompletionTime(block),
            result=self._ParseAutoJoinResult(block),
            stage=self._ParseAutoJoinStage(block),
        )
        self._logger.Info('AutoJoin event:')
        self._logger.Info('    prevNetwork     = %s' % ret['prevNetwork'])
        self._logger.Info('    rfChannel       = %s' % ret['rfChannel'])
        self._logger.Info('    dwellTimeMsec   = %d' % ret['dwellTimeMsec'])
        self._logger.Info('    autoJoinTimeSec = %0.3f' % ret['autoJoinTimeSec'])
        self._logger.Info('    result          = %s' % ret['result'])
        self._logger.Info('    stage           = %d' % ret['stage'])
        return ret

    def _HasAutoJoinStage1(self, block):
        ''' Returns True if MultiStage 1 event is found in text '''
        self._logger.Debug('Searching for MultiStage AutoJoin, stage 1')
        ret = bool(
            self._ParseLogRecordsFromText((
                'multi-stage auto-join: Scanning for previous network .*? on channel \d+',
                ),
                block
            )
        )
        if ret:
            self._logger.Debug('Found MultiStage AutoJoin, stage 1')
        return ret

    def _HasAutoJoinStage2(self, block):
        ''' Returns True if MultiStage 2 event is found in text '''
        self._logger.Debug('Searching for MultiStage AutoJoin, stage 2')
        ret = bool(
            self._ParseLogRecordsFromText((
                'multi-stage auto-join: Attempting to scan MRU network channels',
                ),
                block
            )
        )
        if ret:
            self._logger.Debug('Found MultiStage AutoJoin, stage 2')
        return ret

    def _HasAutoJoinStage3(self, block):
        ''' Returns True if MultiStage 3 event is found in text '''
        self._logger.Debug('Searching for MultiStage AutoJoin, stage 3')
        ret = bool(
            self._ParseLogRecordsFromText((
                'multi-stage auto-join: Attempting to scan remaining channels',
                ),
                block
            )
        )
        if ret:
            self._logger.Debug('Found MultiStage AutoJoin, stage 3')
        return ret

    def _HasAutoJoinMultiStageFallback(self, block):
        ''' Returns True if MultiStage AutoJoin failure is found in text '''
        self._logger.Debug('Searching for MultStage AutoJoin fail')
        ret = bool(
            self._ParseLogRecordsFromText((
                'Fallback to existing auto-join',
                ),
                block
            )
        )
        if ret:
            self._logger.Debug('Found MultiStage fallback')
        return ret

    def _ParseAutoJoinPreviousNetwork(self, block):
        ''' Returns SSID of previous network, or "" (default) if not found '''
        m = re.search('Scanning for previous network (.*?) on channel', block, re.DOTALL)
        if m:
            return m.group(1)
        m = re.search('No previous network', block, re.DOTALL)
        if m:
            return ''
        # May not find if not multistage
        return ''  # default, not found

    def _ParseAutoJoinChannel(self, block):
        ''' Returns RF channel (int), or -1 (default) if not found '''
        m = re.search('dwell time of \S+ on channels: (\d+)', block, re.DOTALL)
        if m:
            return int(m.group(1))
        m = re.search('No previous network', block, re.DOTALL)
        if m:
            return -1
        # May not find if not multistage
        return -1  # default, not found

    def _ParseAutoJoinDwellTime(self, block):
        ''' Returns dwell time (int) in msec, or 0 (default) if not found '''
        m = re.search('dwell time of (\S+) on channels: \d+', block, re.DOTALL)
        if m:
            return int(m.group(1).replace('ms', ''))
        m = re.search('No previous network', block, re.DOTALL)
        if m:
            return 0
        # May not find if not multistage
        return 0  # default, not found

    def _ParseAutoJoinCompletionTime(self, block):
        ''' Returns timedelta for autojoin event to complete (sec), or 0.0 if not found '''

        # Get start/stop log entries
        startRec = self._ParseLogRecord(self.AUTOJOIN_START, block)
        stopRec = self._ParseLogRecord(self.AUTOJOIN_STOP, block)
        if not startRec:
            self._logger.Debug('Did not find log record for autojoin event to start')
            return 0.0
        if not stopRec:
            self._logger.Debug('Did not find log record for autojoin event to stop')
            return 0.0

        return (stopRec.GetLogTime() - startRec.GetLogTime()).total_seconds()

    def _ParseAutoJoinResult(self, block):
        """ Returns the autojoin result, ie. 'Joined: ...', otherwise 'Autojoin Fail' """
        m = re.search('(Joined: .*?$)', block, re.MULTILINE | re.DOTALL)
        if m:
            return m.group(1)
        self._logger.Debug('Failed to parse autojoin result')
        return 'Autojoin Fail'

    def _ParseAutoJoinStage(self, block):
        """ Returns parsed stage (int) 1, 2, or 3. Otherwise, -1 for regular autojoin """
        stage = -1
        if self._HasAutoJoinStage1(block):
            stage = 1
        if (stage == 1) and self._HasAutoJoinStage2(block):
            stage = 2
        if (stage == 2) and self._HasAutoJoinStage3(block):
            stage = 3
        if (stage == 3) and self._HasAutoJoinMultiStageFallback(block):
            stage = -1
        return stage

    #----------------------------------------------------------------------
    #  PARSE - Roam
    #----------------------------------------------------------------------

    def ParseRoamEvents(self):
        ''' Returns a list of dict for parsed Roam events '''
        return [self._ParseRoamInfo(block) for block in self._ParseRoamBlocks()]

    def _ParseRoamBlocks(self):
        ''' Returns list of text blocks containing roam events '''
        self._logger.Debug('Parsing Roam events')
        txt = self._txt

        # Parse log into join events
        regexStart = 'AppleBCMWLANCore::startRoamScan'
        regexStop = 'AppleBCMWLAN Roamed to BSS.*?$'
        regexStop += '|AppleBCMWLAN Left BSS(up).*?$'

        blocks = self._ParseLogBlocks(txt, regexStart, regexStop)
        self._logger.Info('Found %d roam event(s)' % len(blocks))

        # Warn user, could not find roam
        if len(blocks) == 0:
            self._logger.Warning('Could not find any roam events')
            self._logger.Warning('   Starting with: %s' % repr(regexStart))
            self._logger.Warning('   Ending with:   %s' % repr(regexStop))

        return blocks

    def _ParseRoamInfo(self, block):
        '''
        Returns a dict of autojoin info, parsed from Roam log text block
        '''
        ret = dict(
            multiApEnv=False,
            isDualBand=False,
            isOn5G=False,
            fromBss=self._ParseRoamFromBss(block),
            toBss=self._ParseRoamToBss(block),
        )

        # Parse scan info
        m = re.search('starting RoamScan; MultiAPEnv:(\d+) isdualBand:(\d+) isOn5G:(\d+)', block, re.MULTILINE | re.DOTALL)
        if not m:
            self._logger.Warning('Failed to parse roam scan')
            return ret
        ret['multiApEnv'] = bool(int(m.group(1)))
        ret['isDualBand'] = bool(int(m.group(2)))
        ret['isOn5G'] = bool(int(m.group(3)))

        # Log for debug
        self._logger.Info('Roam event:')
        self._logger.Info('    isDualBand   = %s' % ret['isDualBand'])
        self._logger.Info('    multiApEnv   = %s' % ret['multiApEnv'])
        self._logger.Info('    isOn5G       = %s' % ret['isOn5G'])
        self._logger.Info('    fromBss:')
        self._logger.Info('        ssid         = %s' % ret['fromBss']['ssid'])
        self._logger.Info('        bssid        = %s' % ret['fromBss']['bssid'])
        self._logger.Info('        channel      = %s' % ret['fromBss']['channel'])
        self._logger.Info('        rssi         = %s' % ret['fromBss']['rssi'])
        self._logger.Info('        snr          = %s' % ret['fromBss']['snr'])
        self._logger.Info('        rate         = %s' % ret['fromBss']['rate'])
        self._logger.Info('        ap           = %s' % ret['fromBss']['ap'])
        # self._logger.Info('        cca          = %s' % ret['fromBss']['cca'])
        # self._logger.Info('        age          = %s' % ret['fromBss']['age'])
        # self._logger.Info('        encryption   = %s' % ret['fromBss']['encryption'])
        # self._logger.Info('        failures     = %s' % ret['fromBss']['failures'])
        self._logger.Info('    toBss:')
        self._logger.Info('        ssid         = %s' % ret['toBss']['ssid'])
        self._logger.Info('        bssid        = %s' % ret['toBss']['bssid'])
        self._logger.Info('        channel      = %s' % ret['toBss']['channel'])
        self._logger.Info('        rssi         = %s' % ret['toBss']['rssi'])
        self._logger.Info('        snr          = %s' % ret['toBss']['snr'])
        self._logger.Info('        rate         = %s' % ret['toBss']['rate'])
        self._logger.Info('        ap           = %s' % ret['toBss']['ap'])
        # self._logger.Info('        cca          = %s' % ret['toBss']['cca'])
        # self._logger.Info('        age          = %s' % ret['toBss']['age'])
        # self._logger.Info('        encryption   = %s' % ret['toBss']['encryption'])
        # self._logger.Info('        failures     = %s' % ret['toBss']['failures'])
        return ret

    def _ParseRoamFromBss(self, block):
        '''
        Return a dict containing debug info of left BSS
            bssid, rssi, snr, cca, rate, channel, encryption, failures, age, ssid
        '''
        m = re.search('AppleBCMWLAN Left BSS\(up\):\s+\@\S+\s+(.*?)$', block, re.MULTILINE | re.DOTALL)
        if not m:
            self._logger.Warning('Failed to parse left BSS')
            return self._ParseRoamBss('')  # should return default dict

        return self._ParseRoamBss(m.group(1))

    def _ParseRoamToBss(self, block):
        '''
        Return a dict containing debug info of left BSS
            bssid, rssi, snr, cca, rate, channel, encryption, failures, age, ssid
        '''
        m = re.search('AppleBCMWLAN Roamed to BSS:\s+\@\S+\s+(.*?)$', block, re.MULTILINE | re.DOTALL)
        if not m:
            self._logger.Warning('Failed to parse left BSS')
            return self._ParseRoamBss('')  # should return default dict

        return self._ParseRoamBss(m.group(1))

    def _ParseRoamBss(self, line):
        '''
        Returns a dict of BSS params from line:
        BSSID=d4:d7:48:80:00:d0 rssi=-72 noise=-94 snr= 23 cca= 21 rate=65 (100%)
        channel= 6 encryption=0x8 ap=1 failures=  0 age=174 ssid[11]="RoamDefault"
        '''
        ret = {}
        for key in ('BSSID', 'rssi', 'noise', 'snr', 'cca', 'rate', 'channel',
                'encryption', 'ap', 'failures', 'age', 'ssid'):
            ret[key.lower()] = ''
            m = re.search('\s*%s\S*\=\s*(\S+)' % key, line)
            if m:
                # Try to convert to int/float, otherwise leave as str
                try:
                    ret[key.lower()] = eval(m.group(1))
                except:
                    ret[key.lower()] = m.group(1)
        return ret


    #----------------------------------------------------------------------
    #  PARSE - Airplay
    #----------------------------------------------------------------------

    def ParseAirplayEvents(self):
        ''' Returns a list of dict for parsed airplay events '''
        return [self._ParseAirplayInfo(block) for block in self._ParseAirplayBlocks()]

    def _ParseAirplayBlocks(self):
        ''' Returns list of text blocks containing airplay events '''
        self._logger.Debug('Parsing Airplay events')
        txt = self._txt

        # Parse log into events
        regexStart = 'Started mirror presentation'
        regexStop = 'Stopped mirror presentation'

        blocks = self._ParseLogBlocks(txt, regexStart, regexStop)
        self._logger.Info('Found %d airplay event(s)' % len(blocks))

        # Warn user, could not find airplay
        if len(blocks) == 0:
            self._logger.Warning('Could not find any airplay events')
            self._logger.Warning('   Starting with: %s' % repr(regexStart))
            self._logger.Warning('   Ending with:   %s' % repr(regexStop))

        return blocks

    def _ParseAirplayInfo(self, block):
        '''
        Returns a dict of airplay info, parsed from airplay log text block
        '''
        ret = dict(
            session= -1,
            device='',
        )

        # Parse scan info
        m = re.search('Started mirror presentation session (\d+), device (\S+)',
                        block, re.MULTILINE | re.DOTALL)
        if not m:
            self._logger.Warning('Failed to parse roam scan')
            return ret
        ret['session'] = int(m.group(1))
        ret['device'] = m.group(2)

        # Log for debug
        self._logger.Info('Roam event:')
        self._logger.Info('    session   = %s' % ret['session'])
        self._logger.Info('    device   = %s' % ret['device'])
        return ret

    #----------------------------------------------------------------------
    #
    #----------------------------------------------------------------------

    def _ParseLogBlocks(self, txt, regexStart, regexStop):
        ''' Returns a list of text blocks matching from regexStart to regexStop '''
        blocks = []
        block = ''
        for line in txt.splitlines():
            # Find start
            if re.search(regexStart, line) and not block:
                block += line + '\n'
            elif block:
                # Append between start/stop
                block += line + '\n'
                # Found stop
                if re.search(regexStop, line):
                    blocks.append(block)
                    block = ''  # reset
        return blocks

    def _ParseLogLine(self, line):
        ''' Returns a LogRecord parsed from a log line '''

        # Use the following format to search WIFI debug logging
        format = '^.*?wifid\[\d+\] <(\w+)>: WiFi:\[(.*?)\]: (.*?)$'
        m = re.search(format, line)
        if m:
            (levelStr, timeStr, msg) = m.groups()
            timeOffset = timedelta(11323)  # WIFI timestamp returns 1981, not 2012
            logTime = datetime.fromtimestamp(float(timeStr)) + timeOffset
            return LogRecord(msg, 'wifid', levelStr, logTime)

        # Default, if regex fails
        self._logger.Fail('Failed to parse wifid log line: %s' % line)
        return LogRecord(line)

    def _ParseLogRecord(self, regex, txt):
        ''' Returns first instance of LogRecord where regex is found, or None if not found '''
        for line in txt.splitlines():
            if re.search(regex, line):
                return self._ParseLogLine(line)
        return None  # not found

    def _ParseLogRecordsFromText(self, regexList, txt):
        '''
        Returns a list of LogRecord instances matching a regex seqeuence (list). Otherwise,
        returns [] (if not all regex are found).
        '''
        lineNum = 0
        records = []
        lines = txt.splitlines()
        for regex in regexList:
            self._logger.Debug('Searching for "%s"' % regex)
            for lineNum in range(lineNum, len(lines)):
                line = lines[lineNum]
                if re.search(regex, line):
                    # Found regex, continue with next regex and line
                    self._logger.Debug('Found log %s' % repr(regex))
                    records.append(self._ParseLogLine(line))
                    lineNum += 1
                    break
            else:
                # Reached end of log, not all regex found
                self._logger.Debug('Did not find %s' % repr(regex))
                return []
        return records


class WiFiLogs(object):
    ''' WiFi logs control module '''

    NETWORK_INTERFACES_PLIST = '/Library/Preferences/SystemConfiguration/NetworkInterfaces.plist'
    COM_WIFI_APPLE_PLIST = '/Library/Preferences/SystemConfiguration/com.apple.wifi.plist'
    PREFERENCES_PLIST = '/Library/Preferences/SystemConfiguration/preferences.plist'
    DEVICE_WIFI_LOG = "/var/logs/wifi.log"
    DEVICE_WIFI_DRIVER_LOG = "/var/logs/wifi_driver.log"
    DEVICE_SYSLOG = "/var/logs/syslogCurrent.log"
    DEVICE_CORECAPTURED_LOG = "/var/logs/corecaptured.log"
    DEVICE_WIFIFIRMWARELOADER_LOG = "/Library/Logs/wifiFirmwareLoader.log"

    def __init__(self, dut, testName='', enableStreamingFile=False, logName="", interface="en0"):
        self.logger = LogIt()
        self.dut = dut
        self._GetName(logName)
        self.CurrentFile = os.path.join(self.logger.getTestLogsPath(), "syslog-" + self.logName + "-Current.log")
        self.CurrentWiFiFile = self.logger.getTestLogsPath() + "wifilog-" + self.logName + "-Current.log"
        if enableStreamingFile:
            self.EnableStreamingFile()
        else:
            self.DisableStreamingFile()
        self.FullSyslogFileName = os.path.join(self.logger.getTestLogsPath(), "syslog-" + self.logName + "-full.log")
        self.logFileDirectory = "/var/logs/"
        self.fullWiFiLogDir = os.path.join(self.logger.getTestLogsPath(), "wifilog-" + self.logName + "-full.log")

        self.wifiFirmwareLoaderLogMac = os.path.join(self.logger.getTestLogsPath(device=self.dut), "wifiFirmwareLoader-" + self.logName + ".log")
        self.coreCaptureLogMac = os.path.join(self.logger.getTestLogsPath(device=self.dut), "corecaptured-" + self.logName + ".log")
        self.eapolLogFileName = "/var/log/eapolclient.%s.log" % interface
        self.eapolLogFileNameMac = os.path.join(self.logger.getTestLogsPath(), self.logName + "-eapolclient.%s.log" % interface)

    def GetStreamingFilename(self):
        return self.StreamingFile

    def SetStreamingFilename(self, fname):
        self.StreamingFile = fname

    def EnableStreamingFile(self):
        self.StreamingFile = os.path.join(self.logger.getTestLogsPath(), "syslog-" + self.logName + "-Streaming.log")
        self.logger.Info("Enabled syslog streaming to file %s", self.StreamingFile)

    def DisableStreamingFile(self):
        self.StreamingFile = None

    def EnableAllLogging(self, enableBBLogging=True, enableBTLogging=True):
        '''
        Configures all testing logging for WiFi Device: Wifi driver logging, wifi manager logging, eapolclient log, syslog.
        all logging is set to default log locations and file names:
        syslog: <logFileDir>/syslogCurrent.log
        wifi: /Library/Logs/wifi.log
        wifi driver: /Library/Logs/wifi_driver.log
        eapolclient log: /var/log/eapolclient.enX.log
        @arguments:
        @return: true if completed, false if failed to create logs.
        '''
        # Backwards compatibility
        if self._IsWAFVersionAtLeast(7.0):
            return self._EnableAllLoggingWa7(enableBBLogging, enableBTLogging)
        return self._EnableAllLoggingWa6(enableBBLogging, enableBTLogging)

    def EnableSyslogToFile(self):
        '''
        Configures asl.conf to write syslog to <logFileDir>/syslogCurrent.log
        '''
        # Skip if already enabled
        if self.IsSyslogToFileEnabled():
            self.logger.Debug('SyslogToFile already enabled, skipping')
            return True
        self.dut.getOS().Execute("/sbin/mount -uw /", True, True)
        syslogFileEnable = "? [ <= Level debug] file " + self.DEVICE_SYSLOG
        cmd = "echo \"" + syslogFileEnable + "\" >> /etc/asl.conf"
        self.dut.getOS().Execute(cmd, True, True)
        self.dut.getOS().Execute('killall -SIGHUP syslogd', True, True)
        for i in xrange(0, 4):
            Sleep(3, self.logger, 'Sleeping to let syslogd restart')
            if self.IsSyslogFileAvailable():
                return True
        self.logger.Warning("Enabled file logging, but the file was not created")
        return False

    def IsSyslogFileAvailable(self):
        return self.dut.getOS().Execute('[[ -s %s ]]' % self.DEVICE_SYSLOG, True, True).returnCode == 0

    def IsSyslogToFileEnabled(self):
        ''' Returns True if syslog-to-file is enabled '''
        cmd = 'grep syslogCurrent.log /etc/asl.conf'
        return (self.DEVICE_SYSLOG in self.dut.getOS().Execute(cmd, True, True).standardOutput) and self.IsSyslogFileAvailable()

    def StartSyslog(self):
        '''
        Changes the path of the syslog output for the device from under WAF directory to local streaming file.
        '''

        self.dut._syslog_path = self.StreamingFile
        self.CleanoutSyslogFile()
        self.dut.os._save_syslog_to_host_file(self.dut._syslog_path)


    def CleanoutSyslogFile(self):
        '''
        Cleans out <logFileDir>/syslogCurrent.log
        '''
        self.logger.Info('Clean log: %s' % self.DEVICE_SYSLOG)
        self.dut.getOS().Execute("> %s" % self.DEVICE_SYSLOG, True, True)

    def CleanAllLogs(self):
        '''
        Disables, removes and restarts all test logging for WiFi Device: Wifi driver logging, wifi manager logging, eapolclient log, syslog.
        all logging is set to default log locations and file names:
        syslog: <logFileDir>/syslogCurrent.log
        wifi: /Library/Logs/wifi.log
        wifidriver: /Library/Logs/wifi_driver.log
        eapolclient log: /var/log/eapolclient.enX.log
        @arguments:
        @return: true if completed, false if failed to create logs.
        '''
        self.CleanoutSyslogFile()
        self.CleanLogDumps()
        self.dut.getWiFi().WiFiLogCleanUp()
        self.dut.getWiFi().WiFiDriverLogCleanUp()
        self.dut.getWiFi().EapolclientLogCleanUp()
        return True

    def DisableSyslogToFile(self):
        '''
        Disables writing syslog to <logFileDir>/syslogCurrent.log
        '''
        cmd = "sed -i -e 's/\? \[<= Level debug\] file.*//' /etc/asl.conf"
        self.dut.getOS().Execute(cmd, True, True)
        self.dut.getOS().Execute('killall -HUP syslogd', True, True)

    def DisableAllLogs(self, disableBBLogging=True, disableBTLogging=True):
        '''
        Disables all test logging for WiFi Device: Wifi driver logging, wifi manager logging, eapolclient log, syslog.
        all logging is set to default log locations and file names:
        syslog: <logFileDir>/syslogCurrent.log
        wifi: /Library/Logs/wifi.log
        wifidriver: /Library/Logs/wifi_driver.log
        eapolclient log: /var/log/eapolclient.enX.log
        @arguments:
        @return: true if completed, false if failed to create logs.
        '''
        # Backwards compatibility
        if self._GetWaVersionNum() < 7.0:
            return self._DisableAllLoggingWa6(disableBBLogging, disableBTLogging)
        else:
            return self._DisableAllLoggingWa7(disableBBLogging, disableBTLogging)

    def SaveAllLogsWithTestName(self, testname):
        '''
        Copies all log files to the log directory with the test name from the device for WiFi Device: Wifi driver logging, wifi manager logging, eapolclient log, syslog.
        all logging is set to default log locations and file names:
        syslog: <logFileDir>/syslogCurrent.log
        wifi: /Library/Logs/wifi.log
        wifidriver: /Library/Logs/wifi_driver.log
        eapolclient log: /var/log/eapolclient.enX.log
        @arguments: testname # the name of the test to be pre-pended to the log file name
        @return: true if completed, false if failed to create logs.
        '''
        rootfilename = os.path.join(self.logger.getTestLogsPath(), testname)
        eapolclientlogdest = rootfilename + "-eapolclient.en0.log"
        wifilogdest = rootfilename + "-wifi.log"
        wifidriverlogdest = rootfilename + "-wifi_driver.log"
        sysloglogdest = rootfilename + "-syslog.log"
        # eapolclientlog = self.dut.getOS().Execute("> /var/log/eapolclient.en0.log", True, True)
        # wifilog = self.dut.getOS().Execute("> /Library/Logs/wifi.log", True, True)
        # wifidriverlog = self.dut.getOS().Execute("> /Library/Logs/wifi_driver.log", True, True)
        # syslog = self.dut.getOS().Execute("> %s" % os.path.join(self.logFileDirectory, "syslogCurrent.log"), True, True)

        self.SaveEapolClientLog(eapolclientlogdest)
        self.SaveWiFiLog(wifilogdest)
        self.SaveWiFiDriverLog(wifidriverlogdest)
        self.SaveSyslogAndAddToLog(fileName=sysloglogdest)
        self.SaveCoreCaptureLog()
        return True

    def SaveAllPlistsWithTestName(self, testname):
        '''
        Copies all WiFi system preferences from the device to the log directory with a test name pre-pended to the plist name.
        The files are:
        /Library/Preferences/SystemConfiguration/NetworkInterfaces.plist
        /Library/Preferences/SystemConfiguration/com.apple.wifi.plist
        /Library/Preferences/SystemConfiguration/preferences.plist
        @arguments: testname # the name of the test to be pre-pended to the plist file name
        @return: true if completed, false if failed to create the plists.
        '''
        rootfilename = os.path.join(self.logger.getTestLogsPath(), testname)
        preferencesPlistDest = os.path.join(rootfilename, "Preferences.plist")
        comAppleWifiPlistDest = os.path.join(rootfilename, "com.apple.wifi.plist")
        networkInterfacesPlistDest = os.path.join(rootfilename, "NetworkInterfaces.plist")
        self.SavePreferencePlist(preferencesPlistDest)
        self.SaveComAppleWifiPlist(comAppleWifiPlistDest)
        self.SaveNetworkInterfacesPlist(networkInterfacesPlistDest)
        return True

    def SaveDataPathLogs(self):
        '''
        Triggers the datapath log dump
        '''
        self.dut.getOS().Execute('apple80211 --dbg=captureDatapathInfo', True, True)

    def SaveSyslog(self, time=600):
        '''
        Grabs the result from dut
        '''
        syslogResult = ""

        # This will throw an exception if the file doesn't exist, so use the old method
        try:
            self.dut.getOS().CopyFileToHost(self.DEVICE_SYSLOG, self.CurrentFile)
            if os.path.getsize(self.CurrentFile) > 1:
                f = open(self.CurrentFile)
                syslogResult = f.read().decode('ascii', 'ignore')
                f.close()
            else:
                self.logger.Info("Syslog file on the device is blank, calling syslog on the device")
                syslogResult = self.GetSyslog(time)
        except Exception:
            self.logger.Info("Could not retrieve syslog file from the device, reverting to streaming method")
            try:
                f = open(self.StreamingFile)
                syslogResult = f.read().decode('ascii', 'ignore')
                f.close()
            except Exception:
                self.logger.Info("Could not retrieve syslog from logs folder, reverting to syslog function")
                syslogResult = self.GetSyslog(time)

        return syslogResult

    def SaveSyslogAndAddToLog(self, cycle=0, fileName=False):
        '''
        Gets the syslog from the device and merges it into the big log
        '''
        syslog = self.SaveSyslog()
        if fileName:
            self._AddLogToFile(syslog, fileName)
        else:
            self._AddLogToFile(syslog, self.FullSyslogFileName, cycle)
        return syslog

    def SaveWiFiLog(self, fileName="wifi.log"):
        '''
        Saves the wifi.log from the device to the host
        '''
        self.dut.getOS().CopyFileToHost(self.DEVICE_WIFI_LOG, fileName)

    def SaveWiFiDriverLog(self, fileName="wifi_driver.log"):
        '''
        Saves the wifi_driver.log from the device to the host
        '''
        self.dut.getOS().CopyFileToHost(self.DEVICE_WIFI_DRIVER_LOG, fileName)

    def SaveCoreCaptureLog(self):
        '''
        Saves the corecaptured.log from the device to the host
        '''
        self.dut.getOS().CopyFileToHost(self.DEVICE_CORECAPTURED_LOG, self.coreCaptureLogMac)

    def SavePreferencePlist(self, fileName="preferences.plist"):
        '''
        Saves the Preferences.plist from the device to the host
        '''
        self.dut.getOS().CopyFileToHost(self.PREFERENCES_PLIST, fileName)

    def SaveComAppleWifiPlist(self, fileName="com.apple.wifi.plist"):
        '''
        Saves the com.apple.wifi.plist from the device to the host
        '''
        self.dut.getOS().CopyFileToHost(self.COM_WIFI_APPLE_PLIST, fileName)

    def SaveNetworkInterfacesPlist(self, fileName="NetworkInterfaces.plist"):
        '''
        Saves the NetworkInterfaces.plist from the device to the host
        '''
        self.dut.getOS().CopyFileToHost(self.NETWORK_INTERFACES_PLIST, fileName)

    def SaveWiFiLogAndAddToLog(self, cycle=0, fileName=""):
        '''
        Gets the wifi.log from the device for the current cycle and merges it into the big log
        '''
        fileName = self._GetWiFiFileName(fileName)
        self.SaveWiFiLog(fileName)
        self._AddLogFileToOneFile(fileName, self.fullWiFiLogDir, cycle)

    def SaveWiFiFirmwareLoaderLog(self, cleanLog=True):
        '''
        Gets the wifiFirmwareLoader.log from the device for the current cycle and merges it into the big log
        '''
        try:
            self.dut.getOS().CopyFileToHost(self.DEVICE_WIFIFIRMWARELOADER_LOG, self.wifiFirmwareLoaderLogMac)
        except:
            self.logger.Warning("Could not copy wifi firmware loader file")
        if cleanLog:
            self.dut.getOS().Execute("cat /dev/null > " + self.DEVICE_WIFIFIRMWARELOADER_LOG)

    def SaveEapolClientLog(self, fileName=""):
        '''
        Saves the eapolclient log from the device
        '''
        if fileName == "":
            fileName = self.eapolLogFileNameMac
        try:
            self.dut.getOS().CopyFileToHost(self.eapolLogFileName, fileName)
        except:
            self.logger.Warning("Could not copy eapol file")

    def GetSyslog(self, syslogTime=600):
        '''
        Gets the syslog from the device
        '''
        syslogResult = ""
        cmd = "syslog -k Time ge -" + str(syslogTime)
        resultDict = self.dut.getOS().Execute(cmd)
        if resultDict is not None:
            syslogResult = resultDict.standardOutput
        return syslogResult

    def GetErrors(self, HSICDevice=False):
        '''
        Gets errors from the plist config file
        '''
        if HSICDevice:
            self.HSICErrors = [LogErrors().GetWiFi_HSICErrors_IOUSBFamily(), LogErrors().GetWiFi_HSICErrors_IOUSBFamilyHardware()]
            self.logger.Debug("This test will check WiFi HSIC Errors: %s", self.HSICErrors)

        else:
            self.SDIOErrors = LogErrors().GetSDIOErrors()
            self.logger.Debug("This test will check WiFi SDIO Errors: %s", self.SDIOErrors)

        self.SyslogCriticalErrors = LogErrors().GetCriticalErrors()
        self.logger.Debug("This test will check WiFi Syslog Critical Errors: %s", self.SyslogCriticalErrors)

        self.SyslogWatchdogResets = LogErrors().GetWatchdogStrings()
        self.logger.Debug("This test will check WiFi Watchdog Resets: %s", self.SyslogWatchdogResets)

        self.IgnoreErrors = LogErrors().GetKnownErrorStrings()
        self.logger.Info("Ignoring known error strings: %s", self.IgnoreErrors)

    def FindMatchingStringInLog(self, stringListToSearchFor, log, logFailIfFound=True, ignoreStrings=""):
        '''
        Generic string find function
        '''
        for searchString in stringListToSearchFor:
            index = log.find(searchString)
            if index >= 0:
                newLineIndex = log[:log.find(searchString)].rfind('\n') + 1
                foundLine = log[newLineIndex:log.find('\n', index)]
                if any(ignoreItem in foundLine for ignoreItem in ignoreStrings):
                    self.logger.Info("Found ignored error string in line %s", foundLine)
                    continue
                if logFailIfFound:
                    self.logger.Fail("Found: %s; type %s" % (foundLine, searchString))
                    return True
                else:
                    self.logger.Info("Found: %s; type %s" % (foundLine, searchString))
                    return True
        return False

    def FindSDIOError(self, logString, cycle, saveFileName="", ErrorList="all"):
        '''
        Search SOC errors, save the copy of syslog if error found, and do not delete ioreg file dump
        return the string of ErrroList name if the error found
        '''
        hasError = False
        syslogAppendString = ""
        self.logger.Info("Cycle %s: Search for SDIO errors", str(cycle))
        if ErrorList == "all":
            for searchErrorList in self.SDIOErrors:
                searchResult = False
                searchResult = self.FindMatchingStringInLog(self.SDIOErrors[searchErrorList], logString, logFailIfFound=True)
                if searchResult:
                    self.logger.Warning("Cycle %s: Found %s type error: %s", str(cycle), searchErrorList, self.SDIOErrors[searchErrorList])
                    syslogAppendString = syslogAppendString + "_" + str(searchErrorList)
                    hasError = True
        else:
            if self.FindMatchingStringInLog(ErrorList, logString, logFailIfFound=True):
                syslogAppendString = syslogAppendString + "_" + str(ErrorList)
                hasError = True
        # if error found, save a copy of syslog
        if hasError:
            if saveFileName == "":
                saveSOCFileName = os.path.join(self.logger.getTestLogsPath(), "syslog" + syslogAppendString + self.logName + '_' + str(cycle) + ".log")
            else:
                saveSOCFileName = os.path.join(self.logger.getTestLogsPath(), saveFileName)

            self.logger.Warning("Cycle %s: Fail. Found SDIO errors, save the log: %s", str(cycle), saveSOCFileName)
            self._SaveFile(logString, saveSOCFileName)
            return True
        else:
            self.logger.Pass("Cycle %s: pass, and did not find SDIO error in syslog", str(cycle))
            return False

    def FindHSICError(self, logString, cycle, saveFileName=""):
        '''
        return the string of ErrroList name if the error found
        '''
        hasError = False
        self.logger.Info("Cycle %s: Search for HSIC errors", str(cycle))
        syslogAppendString = "_HSICError_"
        searchResult = False
        for searchErrorList in self.HSICErrors:
            searchResult = self.FindMatchingStringInLog(searchErrorList, logString, logFailIfFound=True)
            if searchResult:
                self.logger.Fail("Cycle %s: found HSIC errors", cycle)
                syslogAppendString = syslogAppendString
                hasError = True
                break
        # if error found, save a copy of syslog
        if hasError:
            if saveFileName == "":
                saveSOCFileName = os.path.join(self.logger.getTestLogsPath(), "syslog_" + syslogAppendString + '_' + self.logName + '_' + str(cycle) + ".log")
            else:
                saveSOCFileName = os.path.join(self.logger.getTestLogsPath(), saveFileName)

            self._SaveFile(logString, saveSOCFileName)
            return True
        else:
            self.logger.Pass("Cycle %s: pass, did not find HSIC error in syslog", str(cycle))
            return False

    def FindPlatformErrors(self, logString, cycle, HSICDevice, saveFileName=""):
        '''
        Finds errors for the current platform in the log
        '''
        if HSICDevice:
            # HSIC Error Checking
            hasErrors = self.FindHSICError(logString, cycle)
        else:
            hasErrors = self.FindSDIOError(logString, cycle)

        if hasErrors:
            self.SaveSocRamFile(cycle)
            self.SaveIORegFile(cycle)
            return True

        return False

    def SaveIORegFile(self, cycle=0, regFileNameDevice=""):
        '''
        Save the IOReg dump file
        '''
        if regFileNameDevice == "":
            regFileNameDevice = os.path.join(self.logFileDirectory, "ioreg-" + self.logName + '-' + str(cycle) + ".txt")
        else:
            regFileNameDevice = os.path.join(self.logFileDirectory, regFileNameDevice)
        for _ in range(0, 2):
            try:
                cmd = "/usr/sbin/ioreg -w0 -l > " + regFileNameDevice
                self.dut.getOS().Execute(cmd, True, True)
                self.dut.getOS().CopyFileToHost(regFileNameDevice, self.logger.getTestLogsPath())
                self.logger.Info("Saved ioreg files: %s", regFileNameDevice)
                break;
            except:
                self.logger.Info("Failed to save ioreg files, error = %s", sys.exc_info()[0])
                sleep(1)
            continue;
        return regFileNameDevice

    def SaveSocRamFile(self, cycle=0, regFileNameDevice=""):
        '''
        Save the SocRAM dump file
        '''
        if regFileNameDevice == "":
            regFileNameDevice = os.path.join(self.logFileDirectory, "SocRam-" + self.logName + '-' + str(cycle) + ".bin")
        else:
            regFileNameDevice = os.path.join(self.logFileDirectory, regFileNameDevice)
        for _ in range(0, 2):
            try:
                cmd = "wifitool --socram -F " + regFileNameDevice
                self.dut.getOS().Execute(cmd, True, True)
                self.dut.getOS().CopyFileToHost(regFileNameDevice, self.logger.getTestLogsPath())
                self.logger.Info("Saved Soc Ram files: %s", regFileNameDevice)
                break;
            except:
                self.logger.Info("Failed to save Soc Ram files, error = %s", sys.exc_info()[0])
                sleep(1)
            continue;
        return regFileNameDevice

    def FindSyslogCriticalError(self, syslogString, cycle, regFileNameDevice="", stopTest=False):
        '''
        Finds critical errors in the syslog
        '''
        self.logger.Info("Cycle %s: Search for critical errors", str(cycle))

        criticalErrors = self.FindMatchingStringInLog(self.SyslogCriticalErrors, syslogString, logFailIfFound=True, ignoreStrings=self.IgnoreErrors)
        if criticalErrors:
            self.logger.Fail("Cycle %s: test fail, found critical errors in syslog", str(cycle))
            if stopTest:
                raise TestFailError, TestFailError("Found critical error in syslog, stop the tests")
            else:
                return True
        else:
            self.logger.Pass("Cycle %s: pass, did not find critical errors", str(cycle))
            return False

    def FindSyslogCriticalErrorAndSaveLogs(self, syslogString, cycle, regFileNameDevice="", stopTest=False):
        '''
        Find the critical errors in syslog, stop the tests if find it, and save the ioreg dump file
        '''
        if self.FindSyslogCriticalError(syslogString, cycle, regFileNameDevice, stopTest):
            saveSyslogName = os.path.join(self.logger.getTestLogsPath(), "syslog_CriticalError_" + self.logName + '_' + str(cycle) + ".log")
            self._SaveFile(syslogString, saveSyslogName)
            self.SaveSocRamFile(cycle)
            self.SaveDataPathLogs()
            self.SaveIORegFile(cycle)
            return True
        return False

    def FindSyslogWatchdogReset(self, syslogString, cycle):
        '''
        Finds watchdog resets in the log
        '''
        watchdogResets = self.FindMatchingStringInLog(self.SyslogWatchdogResets, syslogString, logFailIfFound=True)
        if watchdogResets:
            saveSyslogName = os.path.join(self.logger.getTestLogsPath(), "syslog_watchdog_" + self.logName + '_' + str(cycle) + ".log")
            self._SaveFile(syslogString, saveSyslogName)
            return True
        else:
            return False

    def FindSyslogWatchdogResetAndSaveLogs(self, syslogString, cycle):
        '''
        Finds watchdog resets in the logs and saves logs
        '''
        if self.FindSyslogWatchdogReset(syslogString, cycle):
            self.SaveIORegFile(cycle)
            self.logger.Info("Cycle %s: fail, found watchdog errors", str(cycle))
            return True
        self.logger.Info("Cycle %s: pass, did not find watchdogs errors", str(cycle))

        return False

    def ProcessIoregFile(self, cycle, deleteRegFile, regFileNameDevice="", stopTest=True):
        '''
        #Generate register dump file, rsync to the Host, then search for errors, delete the ioreg file is nothing wrong
        '''
        # The file will be saved under the coreect log folder
        if regFileNameDevice == "":
            regFileNameDevice = os.path.join(self.logger.getTestLogsPath(), "ioreg_" + self.logName + '_' + str(cycle) + ".txt")
            regFileNameDeviceName = "ioreg" + str(cycle) + ".txt"
        else:
            regFileNameDeviceName = regFileNameDevice
            regFileNameDevice = os.path.join(self.logger.getTestLogsPath(), regFileNameDevice)
        regFileName = self.SaveIORegFile(cycle, regFileNameDeviceName)
        for _ in range(0, 2):
            try:
                f = open(regFileName, "r")
                if f:
                    output = f.read().decode('ascii', 'ignore')
                    tmp = self.FindMatchingStringInLog(self.ioregFileCriticalErrors, output)
                f.close()
                break;
            except:
                self.logger.Info("Failed to open file, error = %s", sys.exc_info()[0])
                tmp = False
                sleep(1)
            continue;
        if tmp:
            self.logger.Fail("Cycle %s: Fail. Found at least one error type %s in ioreg file", str(cycle), self.ioregFileCriticalErrors)
            if stopTest:
                raise TestFailError, TestFailError("Found critical errors in the ioreg file, stop the tests, please open %s for ioreg dump", regFileName)
            else:
                return False
        else:
            self.logger.Pass("Cycle %s: Pass, nothing found in ioreg file", str(cycle))
        if deleteRegFile:
            cmd = "rm " + regFileNameDevice
            self.dut.getOS().Execute(cmd, True, True)
            self.logger.Info("device ioreg file %s and %s removed.", regFileNameDevice, regFileName)
            try:
                os.remove(regFileName)
            except os.error:
                self.logger.Info("Failed to delete file: %s", regFileName)
        return True

    def CollectSOCandIORegLogs(self, cycle, errorMessage, regFileNameDevice="", stopTest=True):
        '''
        Get syslog, and save the file into another syslog
        '''
        self.SaveSyslogAndAddToLog(cycle)
        self.SaveIORegFile(cycle, regFileNameDevice=regFileNameDevice)
        self.SaveSocRamFile(cycle, regFileNameDevice=regFileNameDevice)
        self.failReason = str(errorMessage)
        if stopTest:
            raise TestFailError, TestFailError("Stop test because some critical error: %s", errorMessage)
        else:
            self.logger.Info("Gather logs finished for error: %s", errorMessage)

    def CasePercentage(self, passCaseName, cycles, passNumber, percentRequiredToExceed=90, logFailIfExceeds=False):
        casePercentage = 0
        try:
            casePercentage = round((1.0 * float(passNumber) / int(cycles)) * 100, 2)
            if casePercentage > 100:
                casePercentage = 100
        except ZeroDivisionError:
            casePercentage = 100
        if casePercentage >= percentRequiredToExceed:
            if logFailIfExceeds:
                self.logger.Fail("%d cycles, %s count is: %d, percentage:%.2f", cycles, passCaseName, passNumber, casePercentage)
            else:
                self.logger.Pass("%d cycles, %s count is: %d, percentage:%.2f", cycles, passCaseName, passNumber, casePercentage)
        else:
            if logFailIfExceeds:
                self.logger.Pass("%d cycles, %s count is: %d, percentage:%.2f", cycles, passCaseName, passNumber, casePercentage)
            else:
                self.logger.Fail("%d cycles, %s count is: %d, percentage:%.2f", cycles, passCaseName, passNumber, casePercentage)
        return casePercentage

    def FindLogInFile(self, logFile, searchLine, searchStartString=""):
        '''
        Finds a log string in a file, starting at searchString if defined.
        Otherwise returns "" if searchLine and/or searchStartString are not found.
        '''
        # self.logger.Info("Searching for %s in file %s, starting at %s", searchLine, logFile, searchStartString)
        foundStartString = False
        # self.logger.Info("searchStartString is %s", searchStartString)

        # Read file to lines list
        try:
            fh = open(logFile, 'r')
            lines = fh.readlines()
            fh.close()
        except:
            self.logger.Fail("Failed to open file %s, error = %s", file, sys.exc_info()[0])
            return ""

        # Verify input
        if not lines:
            self.logger.Warning("Did not find line in file, no lines read from file")
            return ""

        # Do string search on each line
        for line in lines:
            # Starting string option, filter lines before searchStartString is found
            if searchStartString and not foundStartString:
                if line.find(searchStartString) >= 0:
                    self.logger.Info("Found search start string '%s', %s", searchStartString, repr(line))
                    foundStartString = True
                    # searchLine can be same line as searchStartString, so start search here
                else:
                    continue

            # Search for string in this line
            if line.find(searchLine) >= 0:
                self.logger.Info("Found search string '%s'", searchLine)
                return line

        # Log info not warning. The intention may be to NOT find a string. Calling function should fail/warn/pass as needed.
        if searchStartString != "" and not foundStartString:
            self.logger.Info("Did not find the search start sting in file: %s", repr(searchStartString))
        elif foundStartString:
            self.logger.Info("Did not find %s in file after %s", repr(searchLine), repr(searchStartString))
        else:
            self.logger.Info("Did not find %s in file", repr(searchLine))

        # Return empty string if not found
        return ""

    def AutoJoinTime(self, autoJoinLine, autoJoinStartLine, beginSearchString="Device display is ON", fileName=""):
        autoJoinTime = self.WiFiLogTimeDelta(fileName, autoJoinStartLine, autoJoinLine, beginSearchString)
        return autoJoinTime

    def PrimaryInterfaceTime(self, autoJoinLine, primaryLine, beginSearchString="Device display is ON", fileName=""):
        primaryTime = self.WiFiLogTimeDelta(fileName, autoJoinLine, primaryLine, beginSearchString)
        return primaryTime

    def WiFiLogTimeDelta(self, fileName, searchStart, searchEnd, beginSearchString):
        '''
        Finds two strings in a file and returns the time delta
        '''
        fileName = self._GetWiFiFileName(fileName)
        searchStartLine = self.FindLogInFile(fileName, searchStart, beginSearchString)

        searchEndLine = self.FindLogInFile(fileName, searchEnd, beginSearchString)


        if searchStartLine != "" and searchEndLine != "":
#            self.logger.Info("Found %s: %s", searchStart, searchStartLine)
#            self.logger.Info("Found %s: %s", searchEnd, searchEndLine)

            timeDiff = self._TimeDeltaFromWiFiLogStrings(searchStartLine, searchEndLine)
            self.logger.Info("Time delta: %f", timeDiff)

        else:
            self.logger.Info("Could not get enough information from wifi.log for delta")
            timeDiff = ""

        return timeDiff

    def VerifyFirstStageAutoJoinSleepwake(self, ap_ssid, syslogMac="", failString="scan MRU network channels", searchStartString="AppleBCMWLANCore::powerOnSystem() : Powering On"):
        '''
        Check whether passed the first stage auto join for SleepWake
        '''
        firstStageAutoJoin = True
        searchStringFound = False
        if syslogMac == "":
            if os.path.isfile(self.CurrentFile):
                syslogMac = self.CurrentFile
            elif os.path.isfile(self.StreamingFile):
                syslogMac = self.StreamingFile
            else:
                raise TestFailError, TestFailError("No syslog file exists")
        try:
            # search for first auto join fail strings in syslog
            for line in open(syslogMac, 'r').readlines():
                if line.find(searchStartString) >= 0:
                    self.logger.Info("Verify First Stage Auto Join: Found '%s' in syslog, start to search for '%s' now", searchStartString, failString)
                    # self.logger.Info("Current line is: %s", line)
                    self.logger.Info("start to search for '%s' now", failString)
                    searchStringFound = True
                else:
                    continue
                if searchStringFound:
                    if line.find(failString) >= 0:
                        self.logger.Warning("Found %s in syslog, first stage auto join fail", failString)
                        firstStageAutoJoin = False
                        break;
            if firstStageAutoJoin:
                self.firstStageAutojoinPass = self.firstStageAutojoinPass + 1
                self.logger.Pass("First Stage Auto Join Pass")
            if not searchStringFound:
                self.logger.Info("Could not get enough information from syslog for first stage auto join test, did not find '%s'", searchStartString)
        except Exception, e:
            self.logger.Info("Failed to open file %s, error = %s", syslogMac, sys.exc_info()[0])
            self.logger.Info(str(e))
            Sleep(1, self.logger)
        return firstStageAutoJoin

    def VerifyFirstStageAutoJoin(self, fileName="", failString="scan MRU network channels"):
        ''' Check whether passed the first stage auto join '''
        try:
            fileName = self._GetWiFiFileName(fileName)

            # search for first auto join fail strings in WiFi Manager log
            hasMultiStage = False
            for line in open(fileName, 'r').readlines():
                if line.find("Starting multi-stage auto join sequence") >= 0:
                    hasMultiStage = True
                if hasMultiStage and (line.find(failString) >= 0):
                    self.logger.Warning("First Stage Auto Join Fail, found %s in WiFi Manager log", failString)
                    return False
            self.logger.Pass("First Stage Auto Join Pass")
            return True
        except Exception as e:
            self.logger.Fail("Caught exception (%s) while trying to parse wifi logs.", e)
            return False


    def VerifyLogAutoJoinStage1(self, rfChannel=None, dwellTimeMsec=None,
                                                      autoJoinTimeSec=None, result=''):
        """ Helper function to verify 1 autojoin event, stage 1 """
        return self.VerifyLogAutoJoinEvents(
            [
                dict(
                    stage=1,
                    rfChannel=rfChannel,
                    dwellTimeMsec=dwellTimeMsec,
                    autoJoinTimeSec=autoJoinTimeSec,
                    result=result,
                )
            ]
        )

    def VerifyLogAutoJoinStage2(self, rfChannel=None, dwellTimeMsec=None,
                                                      autoJoinTimeSec=None, result=''):
        """ Helper function to verify 1 autojoin event, stage 2 """
        return self.VerifyLogAutoJoinEvents(
            [
                dict(
                    stage=2,
                    rfChannel=rfChannel,
                    dwellTimeMsec=dwellTimeMsec,
                    autoJoinTimeSec=autoJoinTimeSec,
                    result=result,
                )
            ]
        )

    def VerifyLogAutoJoinStage3(self, rfChannel=None, dwellTimeMsec=None,
                                                      autoJoinTimeSec=None, result=''):
        """ Helper function to verify 1 autojoin event, stage 3 """
        return self.VerifyLogAutoJoinEvents(
            [
                dict(
                    stage=3,
                    rfChannel=rfChannel,
                    dwellTimeMsec=dwellTimeMsec,
                    autoJoinTimeSec=autoJoinTimeSec,
                    result=result,
                )
            ]
        )

    def VerifyLogAutoJoinEvents(self, templates):
        """
        Verifies syslog for AutoJoin events. Each template is a dict to verify expected
        result:
            stage = expected stage, 1, 2, 3, or -1 (regular autojoin)
            rfChannel = scan channel for stage 1
            dwellTimeMsec = dwell time used to scan for stage 1
            autoJoinTimeSec = total time from autojoin start to join
            result = reg expression 'Joined' or 'Fail'
        """
        # Save syslog to local, if needed
        syslogMac = self.CurrentFile or self.StreamingFile
        if not os.path.exists(syslogMac):
            self.SaveSyslog()

        wifiParse = SyslogParse(syslogMac)
        events = wifiParse.ParseAutoJoinEvents()
        for template in templates:
            for n, event in enumerate(events):
                ret = self._VerifyLogAutoJoinEvent(event, template=template)
                if ret:
                    events.pop(n)
                    break
            else:
                self.logger.Warning('Did not find autojoin event for template: %s' % template)
                return False

        # Found all autojoin events
        return True

    def _VerifyLogAutoJoinEvent(self, event, template=dict(stage=None, rfChannel=None,
                                      dwellTimeMsec=None, autoJoinTimeSec=None, result='')):
        """ Returns True if autojoin event matches template """

        rfChannel = template.get('rfChannel', None)
        dwellTimeMsec = template.get('dwellTimeMsec', None)
        autoJoinTimeSec = template.get('autoJoinTimeSec', None)
        stage = template.get('stage', None)
        result = template.get('result', '')

        isMatch = True
        if stage and (stage > 0) and (stage != event['stage']):
            isMatch = False
        if rfChannel and (rfChannel > 0) and (rfChannel != event['rfChannel']):
            isMatch = False
        if dwellTimeMsec and (dwellTimeMsec > 0) and (dwellTimeMsec != event['dwellTimeMsec']):
            isMatch = False
        if autoJoinTimeSec and (autoJoinTimeSec > 0) and (autoJoinTimeSec <= event['autoJoinTimeSec']):
            isMatch = False
        if result and not re.search(result, event['result'], re.IGNORECASE):
            isMatch = False

        return isMatch

    def VerifyLogRoamEvents(self, templates):
        """
        Verifies syslog for roam events. Each template is a dict to verify expected
        result:
            TODO
        """
        # Save syslog to local, if needed
        syslogMac = self.CurrentFile or self.StreamingFile
        if not os.path.exists(syslogMac):
            self.SaveSyslog()

        wifiParse = SyslogParse(syslogMac)
        events = wifiParse.ParseRoamEvents()
        for template in templates:
            for event in events:
                ret = self._VerifyLogRoamEvent(event, template=template)
                if ret:
                    break
            else:
                self.logger.Warning('Did not find roam event for template: %s' % template)
                return False

        # Found all roam events
        return True

    def _VerifyLogRoamEvent(self, event, template=dict()):
        """ Returns True if roam event matches template """
        return True  # TODO

    def VerifyLogAirplayEvents(self, templates):
        """
        Verifies syslog for airplay events. Each template is a dict to verify expected
        result:
            TODO
        """
        # Save syslog to local, if needed
        syslogMac = self.CurrentFile or self.StreamingFile
        if not os.path.exists(syslogMac):
            self.SaveSyslog()

        wifiParse = SyslogParse(syslogMac)
        events = wifiParse.ParseAirplayEvents()
        for template in templates:
            for event in events:
                ret = self._VerifyLogAirplayEvent(event, template=template)
                if ret:
                    break
            else:
                self.logger.Warning('Did not find airplay event for template: %s' % template)
                return False

        # Found all airplay events
        return True

    def _VerifyLogAirplayEvent(self, event, template=dict()):
        """ Returns True if airplay event matches template """
        return True  # TODO

    def EOF2ViolationCounter(self):
        result = self.dut.getOS().Execute("/usr/sbin/ioreg -w0 -rd1 -n wlan | grep port-statistics", True, True).standardOutput
        try:
            eofIndex = result.index('kPortStatEOF2ViolationCount')
            # To get the counter, get the value of the key starting at the index and up to the delimiter
            eof2ViolationCounter = int(result[eofIndex:result.index(',', eofIndex)].split('=')[-1])
        except:
            self.logger.Warning("Warning: could not get EOF2 counter information from ioreg value: %s", result)
            eof2ViolationCounter = 0
        self.logger.Info("EOF2ViolationCounter: %d", eof2ViolationCounter)
        return eof2ViolationCounter

    def ParseDBGM(self):
        iosOut = self.dut.getOS().Execute("apple80211 -dbg=m", True, True).standardOutput.split('\n')
        self.counters = {}
        for entry in iosOut:
            try:
                key, val = entry.split('%:')
                self.counters[str(key.strip('%'))] = val
            except:
                pass
        return self.counters

    def CountCrashes(self, processName):
        '''
        Count the number of crashes of a certain process
        '''
        crash_count = 0
        crashes = self.dut.getOS().CrashLogs()
        if type(crashes) is bool or None == crashes:
            pass
        elif crashes:
            for crash in crashes:
                if os.path.basename(crash).startswith(processName):
                    crash_count += 1
        self.logger.Info("Found %d %s crashes.", crash_count, processName)
        return crash_count

    def CleanLogDumps(self):
        self.dut.getOS().Execute("rm %s" % os.path.join(self.logFileDirectory, "SocRam*"), True, True)
        self.dut.getOS().Execute("rm %s" % os.path.join(self.logFileDirectory, "ioreg*"), True, True)

    def EnablePushLogging(self):
        '''
        Enable Push Logging, this function will reboot the device
        '''
        self.dut.getOS().Execute("defaults write com.apple.persistentconnection APSFullNetworkLogging -bool yes", block=True, runAsRoot=False)
        self.dut.getOS().Execute("defaults write com.apple.persistentconnection Log -bool yes", block=True, runAsRoot=False)
        self.dut.getOS().Execute("defaults write com.apple.persistentconnection PCWriteLogs -bool yes", block=True, runAsRoot=False)
        self.dut.getOS().reboot()

    def SavePushLog(self):
        self.dut.getOS().CopyFileToHost("/var/mobile/Library/Logs/PersistentConnection", self.logger.getTestLogsPath(self.dut))

    def _getTestLogsPathABS(self):
        ''' returns the abolute path of the getTestLogsPath '''
        return os.path.abspath(self.logger.getTestLogsPath())

    def _GetName(self, logName):
        if logName == "":
            logName = self.dut.udid
        self.logName = logName

    def _GetWiFiFileName(self, fileName):
        if fileName == "":
            return self.CurrentWiFiFile
        return fileName

    def _SaveFile(self, fileContent, logFile):
        ''' Saves content into a file '''
        tmpFile = open(logFile, 'w')
        if tmpFile:
            tmpFile.write(fileContent.encode('utf8'))
            tmpFile.close()

    def _TimeDeltaFromWiFiLogStrings(self, startLine, endLine):
        '''
        pass the lines got from the wifi.log, and caculate the time between those two events using the wifi.log timestamp
        wifi.log does not have date information in its timestamp, this function need current time as part of information
        time
        '''
        startTimeStringTmp = re.split(" <", startLine);
        startTimeString = startTimeStringTmp[0]
        timeStart = datetime.strptime(startTimeString, "%m/%d/%y %H:%M:%S.%f")
        endTimeStringTmp = re.split(" <", endLine);
        endTimeString = endTimeStringTmp[0]
        timeEnd = datetime.strptime(endTimeString, "%m/%d/%y %H:%M:%S.%f")

        timeDiff = (timeEnd - timeStart).total_seconds()
        return float(timeDiff)

    def _AddLogFileToOneFile(self, fileNameOneCycle, fileNameFull, cycle):
        '''
        Merge log from each cycle into one log file
        '''
        output = ''
        f = open(fileNameOneCycle, 'r')
        if f:
            output = f.read().decode('ascii', 'ignore')
            f.close()

            logFile = open(fileNameFull, 'a')
            if logFile:
                logFile.write('Cycle ' + str(cycle) + ': log\n\n')
                logFile.write(output)
                logFile.write('\n')
                logFile.write('************************************')
                logFile.write('\n')
                logFile.close()

    def _AddLogToFile(self, log, logFile, cycle=""):
        '''
        Merge log contents into one log file
        '''
        tmpFile = open(logFile, 'a')
        if tmpFile:
            if not (cycle == ""):
                tmpFile.write('Cycle ' + str(cycle) + ': Log\n\n')
            tmpFile.write(log.encode('utf8'))
            tmpFile.write('\n')
            tmpFile.write('************************************')
            tmpFile.write('\n')
            tmpFile.close()

    def _DutFileExists(self, fname):
        ''' Returns True if file exists on DUT OS '''
        return bool(self.dut.os.Execute('ls %s' % fname, True, True).standardOutput)

    def _GetWaVersionNum(self):
        ''' Return WAF version number as float, ie. 6.1 '''
        # Version string should look like '6.1E (1174786)'
        versionStr = wirelessautomation_version()
        if versionStr == '<unknown version>':
            return 999.99  # Using dev build, return dummy for latest version
        return float(versionStr[:3])

    def _IsWAFVersionAtLeast(self, version):
        ''' Return whether WAF version is greater than passed in arg '''
        return self._GetWaVersionNum() >= version

    def _EnableAllLoggingWa6(self, enableBBLogging=True, enableBTLogging=True):
        ''' EnableAllLogging for backward compatibility, WA version pre-7.0 '''
        self.CleanAllLogs()
        if not self.EnableSyslogToFile():
            self.EnableStreamingFile()
        self.dut.getWiFi().EnableEapolclientLogging()
        self.dut.getOS().EnableFullWiFiLogging()
        if issubclass(self.dut.__class__, WirelessCommon.DataDevice) and enableBBLogging:
            self.dut.getOS().EnableFullBasebandLogging()
        if issubclass(self.dut.__class__, WirelessCommon.BTDevice) and enableBTLogging:
            self.dut.getOS().EnableFullBluetoothLogging()

    def _EnableAllLoggingWa7(self, enableBBLogging=True, enableBTLogging=True):
        ''' EnableAllLogging for backward compatibility, WA version 7.0+ '''
        self.CleanAllLogs()
        if not self.EnableSyslogToFile():
            self.EnableStreamingFile()
        self.dut.getWiFi().EnableEapolclientLogging()
        self.dut.getWiFi().EnableLogging()
        if issubclass(self.dut.__class__, WirelessCommon.DataDevice) and enableBBLogging:
            self.dut.getData().EnableLogging()
        if issubclass(self.dut.__class__, WirelessCommon.BTDevice) and enableBTLogging:
            self.dut.getBT().EnableLogging()

    def _DisableAllLoggingWa6(self, disableBBLogging=True, disableBTLogging=True):
        ''' DisbleAllLogging for backward compatibility, WA version pre-7.0 '''
        self.DisableSyslogToFile()
        self.dut.getWiFi().DisableEapolclientLogging()
        self.dut.getWiFi().WiFiDriverLogSetup(False)
        self.dut.getWiFi().WiFiLogSetup(False)
        self.dut.getWiFi().WiFiLogToFileSetup(False)
        if issubclass(self.dut.__class__, WirelessCommon.DataDevice) and disableBBLogging:
            self.dut.getOS().DisableFullBasebandLogging()
        if issubclass(self.dut.__class__, WirelessCommon.BTDevice) and disableBTLogging:
            self.dut.getOS().DisableFullBasebandLogging()

    def _DisableAllLoggingWa7(self, disableBBLogging=True, disableBTLogging=True):
        ''' DisbleAllLogging for backward compatibility, WA version 7.0+ '''
        self.DisableSyslogToFile()
        self.dut.getWiFi().DisableEapolclientLogging()
        self.dut.getWiFi().WiFiDriverLogSetup(False)
        self.dut.getWiFi().WiFiLogSetup(False)
        self.dut.getWiFi().WiFiLogToFileSetup(False)
        if issubclass(self.dut.__class__, WirelessCommon.DataDevice) and disableBBLogging:
            self.dut.getData().DisableLogging()
        if issubclass(self.dut.__class__, WirelessCommon.BTDevice) and disableBTLogging:
            self.dut.getBT().DisableLogging()
        return True

def RemoveCoreCaptureLogs(dut, logger):
    """
    Removes the all logs on the device.
    """
    logger.Info("Removing CoreCapture logs for dut %s", dut.udid)
    for CoreCapturePath in CoreCapturePaths:
        if dut.getOS().DoesDirectoryExist(CoreCapturePath):
            dut.getOS().Delete(CoreCapturePath, asRoot=True)


def CheckForCoreCaptureLogs(dut, logger, delete=True, warn=True, fail=False):
    """
    Copies CoreCapture logs if they exist
    Then it deletes them
    """
    found = False
    contents = []
    for CoreCapturePath in CoreCapturePaths:
        if dut.getOS().DoesDirectoryExist(CoreCapturePath):
            dut.getOS().CopyFileToHost(CoreCapturePath, os.path.join(logger.getTestLogsPath(dut), "CrashReporter", CoreCapturePath[1:])) # Strip out the leading /
            for dir in dut.getOS().ContentsOfDirectoryAtPath(CoreCapturePath):
                contents.append(os.path.join(CoreCapturePath, dir))
    if contents:
        found = True
        if all('User Induced' in path for path in contents):
            logger.Info("Found user-induced CoreCapture logs")
            found = False
        elif fail:
            logger.Fail("Found CoreCapture logs. Please submit a rdar to Purple WiFi with the contents of the com.apple.driver.AppleBCMWLANCore directory")
        elif warn:
            logger.Warning("Found CoreCapture logs. Please submit a rdar to Purple WiFi with the contents of the com.apple.driver.AppleBCMWLANCore directory")
        else:
            logger.Info("Found CoreCapture logs. Please submit a rdar to Purple WiFi with the contents of the com.apple.driver.AppleBCMWLANCore directory")
    else:
        logger.Info("No CoreCapture logs found")
    if delete and found:
        RemoveCoreCaptureLogs(dut, logger)
    return found

#----------------------------------------------------------------------
#  MAIN
#----------------------------------------------------------------------

def main():
    _TestWiFiLogs()
    # _TestSyslogParse()

def _TestWiFiLogs():
    # Get DUT
    from DUT.DUT_Utils import GetWiFiDevices
    dut = GetWiFiDevices()[0]

    logs = WiFiLogs(dut)
    print logs.GetStreamingFilename()
    print logs.EnableAllLogging()

    logs.CleanAllLogs()
    logs.StartSyslog()

    dut.wifi.On()

    logs.SaveAllLogsWithTestName('test')
    logs.DisableAllLogs()
    print logs.GetErrors()


def _TestSyslogParse():
    import CoreAutomation as cam
    import WirelessAutomation as waf
    from LogInit import LogIt

    logFile = 'temp.log'

    # Init
    # devs = cam.CAMEmbeddedDevice.allDevices()
    # dut = waf.WiFiDevice(devs[0], logger=LogIt())
    # logs = WiFiLogs(dut, 'test', enableStreamingFile=True)

    # Parse logs using SyslogParse
    wifiParse = SyslogParse(logFile)
    print wifiParse.ParseAutoJoinStage1()

if __name__ == '__main__':
    main()
