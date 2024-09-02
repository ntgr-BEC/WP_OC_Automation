'''
Basic.py
WirelessAutomation

Created by Quint Friesen 2011-1-11
Copyright (c) 2011 Apple. All rights reserved.
'''
from LogInit import LogIt
from time import sleep
from TrafficGen.Ping import Ping
import re
import subprocess

class Basic(object):
    '''
    Class for using common network traffic tools like ping, http, etc
    '''
    def __init__(self, dut=False, dest=None):
        self.logger = LogIt()
        self.destination = dest
        self.dut = dut
        self._http_dl_res = self._defaultHttpDownloadTestResults()
        self.pinger = Ping(self.dut)

    def _defaultHttpDownloadTestResults(self):
        ''' Returns default result values for httpDownloadTest util '''
        return dict(
            dlTotalBytes=0,
            dlTimeSec=0.0,
            dlThruMbps=0.0,
        )

    def __GetDutAndDest(self, dut, dest):
        '''
        internal function to validate dut and dest for traffic tests
        '''
        if not self.destination and not dest:
            self.logger.Info('Must provide a destination using the SetDestination API or as an arg')
            return False
        elif dest:
            self.SetDestination(dest)
        if not self.dut and not dut:
            self.logger.Info('Must provide a dut using the SetDut API or as an arg')
            return False
        elif dut:
            self.SetDut(dut)
        return True

    def SetDestination(self, destination):
        '''
        sets destination address or URL to use with any test
        '''
        self.destination = destination
        self.pinger.SetDestination(destination)

    def SetDut(self, dut):
        '''
        sets device to use with any test for single device tests
        '''
        self.dut = dut
        self.pinger.SetDut(dut)

    def SetDutAndDest(self, dut, dest):
        '''
        sets device to use with any test for single device tests
        '''
        self.SetDut(dut)
        self.SetDestination(dest)

    def HTTPDownloadTest(self, dut=False, dest=None, expectedThroughput=False):
        '''
        Method for sending and validating http traffic without the UI
        first arg is dut to run test on
        Takes optional arg for destination host
        Returns True if successful or False if a failure is detected
        '''
        # Reset results
        self._http_dl_res = self._defaultHttpDownloadTestResults()

        # Run httpDownloadTest util on DUT
        if self.__GetDutAndDest(dut, dest):
            self.logger.Info('httpDownloadTest %s', self.destination)
            httpRes = self.dut.getOS().Execute("httpDownloadTest '%s'" % self.destination)
            if 'connection failed:' in httpRes.standardError:
                self.logger.Warning('httpDownloadTest Failed')
                self.logger.Info('httpDownTest reports: %s', httpRes.standardError)
                return False

            # Parse stdout and populate results dictionary
            self.logger.Info('httpDownTest reports: %s', httpRes.standardOutput)
            self.logger.Pass('httpDownloadTest Passed')

            # Parse DL Total Bytes
            dlTotalBytes = int(re.search('Downloaded (\d+) bytes', httpRes.standardOutput, re.DOTALL | re.MULTILINE).group(1))

            # Parse DL Time
            dlTimeSec = float(re.search('bytes in (.*?) seconds', httpRes.standardOutput, re.DOTALL | re.MULTILINE).group(1))

            # Parse DL Throughput to Mbps
            dlThruMbps = 0.0
            for (n, bps) in enumerate(('Kbps', 'Mbps', 'Gbps')):
                m = re.search('B/s\) \((.*?)%s\)' % bps, httpRes.standardOutput, re.DOTALL | re.MULTILINE)
                if m:
                    dlThruMbps = float(m.group(1)) * pow(10, (n - 1) * 3)
                    break
            else:
                self.logger.Warning('Failed to parse DL throughput from httpDownloadTest')

            self._http_dl_res = dict(
                dlTotalBytes=dlTotalBytes,
                dlTimeSec=dlTimeSec,
                dlThruMbps=dlThruMbps,
            )
            if expectedThroughput:
                if self._http_dl_res['dlThruMbps'] < expectedThroughput:
                    self.logger.Fail("httpDownload results are lower than expected: %s, expected %s", dlThruMbps, expectedThroughput)
                else:
                    self.logger.Pass("httpDownload results are higher than expected: %s, expected %s", dlThruMbps, expectedThroughput)
            return True
        # No device found, return False
        self.logger.Warning('Failed to run httpDownloadTest, no device found')
        return False

    def HTTPDownloadTestResults(self):
        '''
        Returns a dictionary containing results from HTTPDownloadTest util
        Results include dlTotalBytes, dlTimeSec, dlThruMbps
        '''
        return self._http_dl_res

    def SafariTest(self, dut=False, dest=None):
        '''
        Method for sending and validating http traffic with the UI
        first arg is dut to run test on
        Takes optional arg for destination host
        Returns True if successful or False if a failure is detected
        '''
        if self.__GetDutAndDest(dut, dest):
            self.dut.ui.safari.RemoveWebsiteData()
            self.dut.getOS().Execute('killall MobileSafari')
            # result = self.dut.getOS().Execute('scripter -f "ExecuteTest.js" "safariTest_loadSafariPage" "%s"' % self.destination)
            result = self.dut.ui.safari.LoadPage(self.destination)
            sleep(10)
            self.dut.getOS().Execute('eventer h')
            return result

    def PingTest(self, dut=False, dest=None, count=5, verbose=False):
        '''
        Method for pinging from dut to destination
        first arg is dut to run test on
        Takes optional arg for destination host and optional arg for how many pings to send
        Returns True if pings are partially successful or False if all pings fail
        '''
        if self.__GetDutAndDest(dut, dest):
            result = self.pinger.Ping(dut=self.dut, dest=self.destination, count=count, verbose=verbose)
            if result and result['received'] == count:
                self.logger.Pass('Ping %s Pass', self.destination)
                return True
            elif result and result['received'] > 0:
                self.logger.Warning('Ping %s partial fail', self.destination)
                self.logger.Info("Ping results: %s", str(result))
                return True
            else:
                self.logger.Warning('Ping %s complete fail', self.destination)
                self.logger.Info("Ping results: %s", str(result))
        return False

