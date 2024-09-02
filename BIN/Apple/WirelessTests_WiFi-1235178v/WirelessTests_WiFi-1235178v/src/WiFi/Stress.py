#!/usr/bin/env python2.3
'''
Stress.py
WirelessAutomation

Updated by Quint Friesen 2011-1-11
Created by Meng Wang on 2/16/10 as WiFi_StressCommon.py
Copyright (c) 2010 Apple. All rights reserved.
'''

from WiFi.Control import Control
from LogInit import LogIt
from DeviceTools.DeviceConnector import DeviceConnector
from common.WirelessLogging import Sleep
from common.WirelessException import TestFailError, PurpleDeviceError
from WiFi.LogParse import WiFiLogs, CheckForCoreCaptureLogs

class WiFiStress(object):  # WiFiTest, WiFiLogs, Busman):
    '''
    Class for stress related test and functional methods
    '''
    def __init__(self, dut, noUI=False, testName=""):
        self.logger = LogIt()
        self.dut = dut
        self.BBDumpCount = 0
        self.testName = self.logger.GetTestLogFolderName() + testName
        self.dutControl = Control(self.dut)
        self.logParser = WiFiLogs(self.dut, self.testName, interface=self.dutControl.WiFiManagerDevice())
        self.deviceConnector = DeviceConnector(self.dut)
        self.HSICDevice = self.dutControl.GetIsHSICDevice()
        self.noUI = noUI or self.dut.isAppleTV()
        self.logParser.GetErrors(self.HSICDevice)

        self.PlatformErrorCount = 0
        self.watchdogResetCount = 0
        self.criticalErrorCount = 0

        self.ExceptionsContinueCount = 0
        self.PlatformErrorContinueCount = 0
        self.criticalErrorContinueCount = 0
        self.watchdogResetContinueCount = 0
        self._pmset_boot = False

    def Configure(self, noUI=False, forgetAll=True, autoLockToNever=True, enableWifidLeaks=False, enableConfigdLeaks=False, enableBluetooth=False, airplaneMode=False):
        self.dutControl.Configure(noUI, forgetAll, autoLockToNever, enableWifidLeaks, enableConfigdLeaks, enableBluetooth, airplaneMode)
        self.countersStart = self.logParser.ParseDBGM()
        if self.HSICDevice:
            self.countersStart['eof2violations'] = self.logParser.EOF2ViolationCounter()
        for entry in self.countersStart:
            self.logger.Info("Counter %s: %s", entry, self.countersStart[entry])
            self.logger.SetTestProperty(entry, str(self.countersStart[entry]))

    def CheckForErrors(self, syslog_result, cycle):
        '''
        Check Errors from giving string
        '''
        logSaved = False
        try:
            if self.logParser.FindSyslogWatchdogResetAndSaveLogs(syslog_result, cycle):
                logSaved = True
                self.watchdogResetCount = self.watchdogResetCount + 1
                self.watchdogResetContinueCount = self.watchdogResetContinueCount + 1
            else:
                self.watchdogResetContinueCount = 0

            if (not logSaved and self.logParser.FindSyslogCriticalErrorAndSaveLogs(syslog_result, cycle, stopTest=False)):
                self.criticalErrorContinueCount = self.criticalErrorContinueCount + 1
                self.criticalErrorCount = self.criticalErrorCount + 1
                logSaved = True
            else:
                self.criticalErrorContinueCount = 0

            if (not logSaved and self.logParser.FindPlatformErrors(syslog_result, cycle, HSICDevice=self.HSICDevice)):
                self.PlatformErrorCount = self.PlatformErrorCount + 1
                self.PlatformErrorContinueCount = self.PlatformErrorContinueCount + 1
            else:
                self.PlatformErrorContinueCount = 0
        except Exception as e:
            self.logger.Warning("Failed to check errors with error %s", e)

    def CheckErrorsSaveLogs(self, cycle=0):
        '''
        Check Errors from syslog, save all the logs
        '''
        try:
            syslog_result = self.logParser.SaveSyslogAndAddToLog(cycle=cycle)
            self.CheckForErrors(syslog_result, cycle)
            self.logParser.SaveWiFiLogAndAddToLog(cycle=cycle)
            self.logParser.CleanoutSyslogFile()
            self.dut.getWiFi().WiFiLogCleanUp()
        except Exception as e:
            self.logger.Warning("Failed to check errors and save logs with error %s", e)

    def WiFiOffOn(self, sleepTime=5, gotoSpringBoard=True, resetDHCP=False):
        '''
        Turn WiFi Off, wait for sleepTime, then turn WiFi On
        '''
        self.logger.Info('Turning WiFi OFF')
        try:
            self.dutControl.WiFiOff()
            if resetDHCP:
                self.dutControl.ResetDHCPState()
            Sleep(sleepTime, self.logger, 'Wait for %s seconds before turn WiFi On' % sleepTime)
        except Exception as e:
            self.logger.Fail("Caught exception: %s, continuing for stress.", e)
        try:
            self.dutControl.WiFiOn()
        except Exception as e:
            self.logger.Fail("Caught exception: %s, continuing for stress.", e)
        if gotoSpringBoard:
            self.dutControl.Unlock(gotoSpringBoard)


    def WiFiOffOnStress(self, cycles, sleepTime=5, gotoSpringBoard=True):
        '''
        WiFi Off On function for stress
        '''
        for cycle in range(1, cycles + 1):
            self.logger.Info('Cycle %d: WiFi Off On Starting', cycle)
            self.WiFiOffOn(sleepTime=sleepTime, gotoSpringBoard=gotoSpringBoard)
            self.logger.Info('Cycle %d: WiFi Off On Ended successfully', cycle)

    def FWResetStress(self, cycles, delay=10):
        for _cycle in range(1, cycles + 1):
            if not self.dutControl.FWReset():
                return False
            if delay:
                Sleep(delay, self.logger)
        return True

    def AirplaneModeOnOff(self, sleepTime=5, gotoSpringBoard=True):
        '''
        Airplane Mode On Off function
        '''
        self.dut.TurnOnAirplaneMode()
        self.logger.Info('Wait for %s seconds before turn airplane mode off', sleepTime)
        Sleep(sleepTime, self.logger)
        self.dut.TurnOffAirplaneMode()
        if gotoSpringBoard and not self.noUI:
            # self.dut.ui.springboard.DismissAll()
            self.dut.ui.springboard.Home()

    def AirplaneModeOnOffStress(self, cycles, sleepTime=5, gotoSpringBoard=True):
        '''
        AirplaneMode On Off function for stress
        '''
        for cycle in range(1, cycles + 1):
            self.logger.Info('Cycle %d: Airplane Mode On Off Starting', cycle)
            self.AirplaneModeOnOff(sleepTime=sleepTime, gotoSpringBoard=gotoSpringBoard)
            self.logger.Info('Cycle %d: Airplane Mode On Off Ended successfully', cycle)

    def WiFiSleepWake(self, sleepTime=10, wakeTime=60, goToSpringBoardAfterWake=True, checkBB=True, unlock=True, block=True, failIfWowChange=True):
        '''
        Method for sleeping and waking WiFi and validating the operations were successful.
        '''
        testResult = False
        try:
            wowSupportBefore = self.dut.getWiFi().IsWoWSupported()
            wowEnableBefore = self.dut.getWiFi().IsWoWEnabled()
            awdlEnableBefore = self.dutControl.awdl.IsAWDLEnabled()
        except Exception as _error:
            raise TestFailError('Could not get status from wifi driver, something is wrong with WiFi. Error: %s', _error)

        self.logger.Info('Start sleep/wake, device is going to sleep for %d seconds', sleepTime)
        # make sure the screen is unlocked, then turn it off
        if not self.noUI:
            self.dut.getOS().Execute('scripter -i SpringBoard.js -c "UIATarget.localTarget().lock()"', True, True)
        try:
            if checkBB:
                resultDict = self.dut.getOS().Execute('CTGetResetState', runAsRoot=True)
                if resultDict is not None:
                    bbState = str(resultDict.standardOutput)
                    bbStateErr = str(resultDict.standardError)
                    if not ('CommCenter not in reset state' in bbState or 'Unable to create connection to CTServer - may be CommCenter is unloaded' in bbStateErr or "Unable to get reset state of CommCenter" in bbStateErr):
                        self.logger.Warning('Baseband is in reset state, waiting up to 5 minutes to sleep. CTGetResetState returns: %s,%s', bbState, bbStateErr)
                        self.BBDumpCount = self.BBDumpCount + 1
                        try:
                            if not self.dut.baseband.WaitForAllDumps(timeout=5 * 60):
                                self.logger.Warning('Baseband was still dumping after 5 minutes, try to sleep anyway')
                        except Exception as error:
                            self.logger.Warning('Caught error %s, continuing', error)
            if self.dut.isAppleTV() and not self._pmset_boot:
                # Add workaround for <rdar> description
                self.logger.Info("Do a pmset boot for AppleTV before sleep...")
                self.dut.getOS().Execute("/usr/bin/pmset boot", True, True)
                self._pmset_boot = True
            self.dut.getOS().SleepCycler(sleep=sleepTime, wake=wakeTime, unlockDevice=False, block=block)
            # Unlock Springboard again
            if block:
                if not self.noUI and unlock:
                    self.logger.Info('Device wake up, unlock Springboard now')
                    self.dutControl.Unlock(goToSpringBoard=goToSpringBoardAfterWake)
        except PurpleDeviceError as sleepwakeError:
            self.failReason = str(sleepwakeError)
            raise TestFailError, TestFailError('Failed at the device after sleep/wake, error: %s', sleepwakeError)

        # check WoW status for each sleep/wake cycle
        if block:
            try:
                wowSupportAfter = self.dut.getWiFi().IsWoWSupported()
                wowEnableAfter = self.dut.getWiFi().IsWoWEnabled()
                awdlEnableAfter = self.dutControl.awdl.IsAWDLEnabled()
            except Exception as error:
                raise TestFailError('Could not get status from wifi driver, something is wrong with WiFi. Error: %s', error)

            if wowSupportAfter == wowSupportBefore and wowEnableAfter == wowEnableBefore:
                self.logger.Pass('WoW status remains the same after sleep/wake')
                testResult = True
            else:
                if failIfWowChange:
                    raise TestFailError('WoW status changed after sleep/wake')
                else:
                    self.logger.Warning('WoW status changed after sleep/wake')  # See <rdar> description
                    testResult = True

            if awdlEnableAfter == awdlEnableBefore and (awdlEnableBefore == False or self.dut.isAppleTV()):  # <rdar> description
                self.logger.Pass('AWDL status remains the same after sleep/wake')
                testResult = True
            elif awdlEnableBefore == True and awdlEnableAfter == False:
                self.logger.Pass('AWDL is off after sleep/wake, this is expected')  # See rdar://11357768
                testResult = True
            else:
                # raise TestFailError('AWDL status after sleep/wake is unexpected')
                self.logger.Warning("AWDL status is enabled before and after sleep.")  # Investigation in progress, let's not fail for now
            self.dut.getWiFi().LastWakeInfo()
        else:
            testResult = True
        return testResult

    def WiFiSleepWakeStress(self, cycles, sleepTime=10, wakeTime=60):
        for cycle in range(1, cycles + 1):
            self.logger.Info('Cycle %d: Sleep/Wake Starting', cycle)
            self.WiFiSleepWake(sleepTime, wakeTime)
            self.logger.Info('Cycle %d: Sleep/Wake Ended successfully', cycle)

    def LockUnlock(self, goToSpringBoard=False, timeBetweenLockUnlock=5):
        '''
        Lock and Unlock display
        '''
        self.dutControl.Lock()
        Sleep(timeBetweenLockUnlock, self.logger, 'Wait to unlock display')
        self.dutControl.Unlock(goToSpringBoard=goToSpringBoard)

    def BBPowerCycle(self):
        self.dut.getOS().Execute('/usr/local/bin/bbctl powercycle', runAsRoot=True)


    def CheckMemUseCycle(self, name=False):
        if self.dutControl.CheckMemUse():
            for i in xrange(1, 6):
                if self.dutControl.CheckMemUse():
                    self.logParser.SaveSocRamFile(cycle="memuse{0}-".format(i) if not name else "memuse{0}-".format(i) + name)
                    Sleep(10, self.logger)
                else:
                    self.logger.Info("Mem use recovered")
                    break
            if self.dutControl.CheckMemUse():
                self.logger.Warning("Mem use is low for a long time. Bouncing the interface and checking again.")
                self.dutControl.WiFiOff()
                Sleep(10, self.logger)
                self.dutControl.WiFiOn()
                Sleep(10, self.logger)
                for i in xrange(1, 6):
                    if self.dutControl.CheckMemUse():
                        self.logParser.SaveSocRamFile(cycle="memuse{0}-".format(i) if not name else "memuse_afterbounce_{0}-".format(i) + name)
                        Sleep(10, self.logger)
                    else:
                        self.logger.Info("Mem use recovered")
                        break
                return True
        else:
            return False

    def CheckForAllErrors(self, logFailOnLeaks=True, logFailOnErrors=True, name=False):
        '''
        Catch-all. Please add any error-checking functions to this one, and then run this one at the end of all tests.
        '''
        failuresFound = ""
        if self.dutControl.CheckForLeaks(logFailOnLeaks):
            failuresFound += "Leaks; "
        if self.watchdogResetCount > 0:
            failuresFound += "watchdogs; "
        if self.PlatformErrorCount > 0:
            failuresFound += "platform errors; "
        if self.criticalErrorCount > 0:
            failuresFound += "critical errors; "
        if self.CheckMemUseCycle(name):
            failuresFound += "memuse"
        if CheckForCoreCaptureLogs(self.dut, self.logger, warn=True):
            failuresFound += "CoreCapture logs; "
        self.countersEnd = self.logParser.ParseDBGM()
        if self.HSICDevice:
            self.countersEnd['eof2violations'] = self.logParser.EOF2ViolationCounter()
        for entry in self.countersEnd:
            self.logger.Info("Counter %s: %s", entry, self.countersEnd[entry])
            self.logger.SetTestProperty(entry, str(self.countersEnd[entry]))
            try:
                if entry in self.countersStart and (int(self.countersEnd[entry]) - int(self.countersStart[entry])) > 0:
                    if logFailOnErrors:
                        self.logger.Fail("Counter %s has increased by %d", entry, (self.countersEnd[entry] - self.countersStart[entry]))
                    else:
                        self.logger.Warning("Counter %s has increased by %d", entry, (self.countersEnd[entry] - self.countersStart[entry]))
                    failuresFound = failuresFound + entry + "; "
                else:
                    self.logger.Pass("Counter %s has not increased", entry)
            except:
                pass
        return failuresFound
