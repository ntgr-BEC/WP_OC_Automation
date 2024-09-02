# encoding utf-8
'''
iPerf.py
WirelessAutomation

Provides iPerf utility for running on a MacOS host or iOS device.

Created by Dmitry Halavin on 2012-04-17
Modified by Alex Omoto on 2012-10-26: Added iPerfManager
Copyright (c) 2011 Apple, Inc. All rights reserved.
'''

import re
import subprocess
import threading
from common.ToolWrappers import MacProcessCmd
from LogInit import LogIt
import netifaces
from common.MacUtils import CommandExecuteWithBlock

#------------------------------------------------------------------------
#  iPerf
#------------------------------------------------------------------------

class iPerf(object):

    def __init__(self, dut=None, interface='en0', output_file=None, macIP=None):
        self.dut = dut
        self.logger = LogIt()
        self.clientResults = ""
        self.output_file = output_file
        self.interface = interface
        self._client = None
        self.iperfResultNumber = 0
        self.resultsInKbits = False
        if not dut:
            if macIP:
                self.macIP = macIP
            else:
                self.macIP = self._findMacIpAddress(interface)
            self.logger.Info('MAC IP is %s.', self.macIP)

    def StartServer(self, udp=False, IPv6=False, mcastAddress=False):
        self.logger.Info("Starting iPerf server")
        serverCommand = '/usr/local/bin/iperf -s'
        if udp:
            serverCommand = serverCommand + ' -u'
        if mcastAddress:
            serverCommand += '-B' + mcastAddress
        if IPv6 or self.interface.find('awdl') >= 0:
            serverCommand = serverCommand + ' -V'
        self.logger.Info('Running command: %s' % serverCommand)
        if self.dut:
            self.serverResult = self.dut.getOS().Execute(serverCommand, block=False)
            self.logger.Info("iPerf server running on %s", self.dut.name)
        else:
            self.process = MacProcessCmd(serverCommand, self.output_file)
            self.process.run()
            if self.process.is_running():
                self.logger.Info('iPerf server is running on MAC now')
            else:
                self.logger.Warning('Could not start iPerf server on MAC')

    def StopServer(self):
        self.logger.Debug('Stopping server')
        if self.dut == None:
            if self.process.is_running():
                self.process.kill()
                self.logger.Pass('Killed process')
            else:
                self.logger.Info('No server running')
        else:
            try:
                self.logger.Info("Stopping iPerf server on: %s", self.dut.name)
                self.dut.getOS().UpdateProcessOutput(self.serverResult)
                self.dut.getOS().Kill(self.serverResult)
            except Exception as e:
                self.logger.Warning("Can not stop iPerf server: %s", e)


    def _estimatedTimeToFinish(self, bytesToSend):
        K = 1024
        M = K * K
        G = K * M
        _bytesToSend = str(bytesToSend.lower())
        if _bytesToSend[-1] == 'k':
            _bytes = K * int(_bytesToSend[:-1])
        elif _bytesToSend[-1] == 'm':
            _bytes = M * int(_bytesToSend[:-1])
        elif _bytesToSend[-1] == 'g':
            _bytes = G * int(_bytesToSend[:-1])
        else:
            _bytes = int(bytesToSend)
        return _bytes / (100 * K) # If tests get less than 100Kbps, we're in trouble...


    def RunClient(self, destination, udp=False, time=10, interval=35, bytesToSend=False,
            threads=False, IPv6=False, bandwidth=False, noDelay=False, block=True, port=False, ac_class=False):
        '''
        Runs iperf client
        takes run time in seconds, and interval in seconds,
        udp as an option, and bytesToSend as an option instead of time.
        '''
        if bandwidth:
            udp = True
        if not bytesToSend and not time:
            self.logger.Fail("Must specify time or bytes to send")
        elif not bytesToSend:
            command = "iperf -c %s -t %f -i %f" % (destination, time, interval)
        else:
            command = "iperf -c %s -n %s -i %f" % (destination, bytesToSend, interval)
            time = self._estimatedTimeToFinish(bytesToSend)
        if port:
            command += ' -p ' + port
        if udp:
            command += ' -u'
            if bandwidth:
                command += ' -b ' + bandwidth
        if threads:
            command += ' -P ' + str(threads)
        if IPv6 or destination.find('awdl') >= 0:
            command += ' -V'
        if noDelay:
            command += ' -N'
        if ac_class:
            command += ' -S ' + str(ac_class)
        self.logger.Info("Running command: %s (timeout: %ss)" % (command, time + 30))
        err = False
        if self.dut == None:
            self._client = subprocess.Popen([command], stdout=subprocess.PIPE,
                                stderr=subprocess.PIPE, shell=True)
        else:
            self._client = self.dut.getOS().Execute(command, block=block,
                                                    runAsRoot=True, timeout=time + 30)
        if err:
            self.logger.Warning("result error: %s", err)

    def IsClientRunning(self):
        ''' Returns True if client is running '''
        # Validate client was started
        if not self._client:
            self.logger.Warning('iPerf client has not been started')
            return False
        if type(self._client) is subprocess.Popen:
            # Mac OS
            return (self._client.poll() != 0)
        else:
            # iOS
            self.dut.os.UpdateProcessOutput(self._client)
            return (self._client.returnCode == -1)

    def KillClient(self):
        ''' Returns None. Kills running iPerf client '''
        # Validate client was started
        if not self._client:
            self.logger.Warning('iPerf client has not been started')
            return

        self.logger.Debug('Kill iPerf client')
        if type(self._client) is subprocess.Popen:
            # Mac OS
            self._client.kill()
        else:
            # iOS
            self.dut.os.Kill(self._client)

        # Clean up client
        self._client = None

    def WaitClient(self):
        ''' Returns None. Waits (blocking) for client to finish '''
        # Validate client was started
        if not self._client:
            self.logger.Warning('iPerf client has not been started')
            return

        self.logger.Debug('Wait for iPerf client')
        self.clientResults = ''
        errMsg = ''
        if type(self._client) is subprocess.Popen:
            # Mac OS
            (self.clientResults, errMsg) = self._client.communicate()
            self._client = None  # clean up client
        else:
            # iOS
            while self._client.returnCode < 0:
                self.dut.os.UpdateProcessOutput(self._client)
            self.clientResults = self._client.standardOutput
            errMsg = self._client.standardError
            self._client = None  # clean up client

        # Warn user of any errors
        if errMsg:
            self.logger.Warning(errMsg)

    def ProcessIPerfTestResult(self, passNumber=5):
        '''
        Process the iperf client test results
        '''
        threadString = '[SUM]'
        indexString = 'Mbits/sec'
        kbitString = 'Kbits/sec'
        searchPattern = ""
        resultLine = []
        self.resultsInKbits = False
        self.WaitClient()
        self.logger.Debug("Results: %s", self.clientResults)
        if indexString in self.clientResults:
            searchPattern = indexString
        elif kbitString in self.clientResults:
            searchPattern = kbitString
            self.logger.Warning('iPerf Test did not find Mbit/s in the results, found Kbits/s')
            indexString = kbitString
            self.resultsInKbits = True
        if threadString in self.clientResults:
            searchPattern = threadString
        resultList = self.clientResults.split('\n')
        for line in resultList:
            if searchPattern in line:
                resultLine = line.split()
        if not resultLine:
            self.logger.Fail('iPerf Test fail, could not process the results')
            self.logger.Info("Actual results: %s", self.clientResults)
            return False
        resultIndex = resultLine.index(indexString) - 1
        self.iperfResultNumber = float(resultLine[resultIndex]) / (1000 if self.resultsInKbits else 1)

        if self.iperfResultNumber >= passNumber:
            self.logger.Pass('iPerf Test pass, the test result is %.2f Mbits/sec, higher than pass number: %.2f Mbits/sec',
                             self.iperfResultNumber, passNumber)
            return True
        else:
            self.logger.Fail('iPerf Test fail, the test result is %.2f Mbits/sec, lower than pass number: %.2f Mbits/sec',
                             self.iperfResultNumber, passNumber)
            return False

    def ProcessClient(self):
        '''
        Returns a dict of results processed from iperf stdout
        Result keys include tcpWinSizeKB, transferMbps, bandwidthMbps.
        '''
        self.WaitClient()
        txt = self.clientResults
        return dict(
            tcpWinSizeKB=self._parseTcpWinSize(txt),
            transferMbps=self._parseTransfer(txt),
            bandwidthMbps=self._parseBandwidth(txt),
        )

    def _findMacIpAddress(self, ifname):
        ''' Returns MAC/IP found on interface info, otherwise empty string '''
        ifaddr_info = netifaces.ifaddresses(ifname)
        try:
            return ifaddr_info[2][0]['addr'] # @UndefinedVariable
        except KeyError, e:
            self.logger.Warning('Could not get address from interface: %s' % ifname)
        except Exception, e:
            self.logger.Warning('Could not get address from interface: %s, unknown error: %s' % (ifname, str(e)))
        return ''

    def _parseTcpWinSize(self, txt):
        ''' Returns TCP window size (KB) parsed from iperf output '''
        m = re.search('TCP window size:\s+(\d+)\s+', txt, re.DOTALL | re.MULTILINE)
        if not m:
            self.logger.Warning('Could not parse TCP window size')
            return 0.0
        return float(m.group(1))

    def _parseTransfer(self, txt):
        ''' Returns transfer (MB) parsed from iperf output '''
        for (n, key) in enumerate(('KBytes', 'MBytes', 'GBytes')):
            m = re.search('\s+(\S+)\s+%s' % key, txt, re.DOTALL | re.MULTILINE)
            if m:
                return float(m.group(1)) * (1000 ** (n - 1))
        self.logger.Warning('Could not parse iperf transfer')
        return 0.0

    def _parseBandwidth(self, txt):
        ''' Returns bandwidth (Mbps) parsed from iperf output '''
        for (n, key) in enumerate(('Kbits/sec', 'Mbits/sec', 'Gbits/sec')):
            m = re.search('\s+(\S+)\s+%s' % key, txt, re.DOTALL | re.MULTILINE)
            if m:
                return float(m.group(1)) * (1000 ** (n - 1))
        self.logger.Warning('Could not parse iperf bandwidth')
        return 0.0

#------------------------------------------------------------------------
#  iPerfManager
#------------------------------------------------------------------------

class iPerfManager(object):
    ''' Manages a iPerf worker queue '''

    def __init__(self):
        super(iPerfManager, self).__init__()
        self._threads = []
        self._logger = LogIt()
        self._queue = []

    def __del__(self):
        self._wait()  # Cleans up any remaining threads

    def _start(self, meth, *args, **kwargs):
        ''' Starts a thread for given method(*args, **kwargs) '''
        self._logger.Debug('Start thread %d' % len(self._threads))
        th = threading.Thread(target=meth, args=args, kwargs=kwargs)
        self._threads.append(th)
        th.start()

    def _wait(self, timeout=300):
        ''' Waits for all threads to finish '''
        for th in self._threads:
            th.join(timeout=timeout)
        self._threads = []

    def AddClient(self, iperf_obj, *args, **kwargs):
        ''' Adds client to a worker queue '''
        self._queue.append((iperf_obj, args, kwargs))

    def ClearClients(self):
        ''' Clears worker queue of client runs '''
        self._queue = []

    def RunClients(self):
        ''' Runs all client runs in the queue '''
        self._wait()  # clients must finish before running again
        for (iperf_obj, args, kwargs) in self._queue:
            meth = getattr(iperf_obj, 'RunClient')
            self._start(meth, *args, **kwargs)

    def WaitClients(self):
        ''' Returns None. Waits for clients to finish '''
        self._wait()

    def IsReady(self):
        ''' Returns True if manager has finished tasks '''
        for th in self._threads:
            if th.isAlive():
                return False
        return True

    def ProcessClients(self):
        ''' Processes output of completed client runs and returns a list of dict results '''
        self._wait()  # clients must finish before processing
        res = []
        for (iperf_obj, args, kwargs) in self._queue:
            meth = getattr(iperf_obj, 'ProcessClient')
            res.append(meth())
        self._queue = []
        return res

#------------------------------------------------------------------------
#  MAIN
#------------------------------------------------------------------------

def main():
    import pprint
    import time
    from DUT.DUT_Utils import GetWiFiDevices

    server1 = iPerf()
    server1.StartServer()
    hostIp = '192.168.0.7'

    # Non-blocking iperf (Mac OS)
    client1 = iPerf(interface='lo0')
    client1.RunClient('127.0.0.1', time=5, block=False)
    for i in range(10):
        print 'Wait %d sec' % i
        time.sleep(1)
        if not client1.IsClientRunning():
            break
    pprint.pprint(client1.ProcessClient())

    # Non-blocking iperf (device)
    dut = GetWiFiDevices()[0]
    client1 = iPerf(dut)
    client1.RunClient(hostIp, time=5, block=False)

    for i in range(10):
        print 'Wait %d sec' % i
        time.sleep(1)
        if not client1.IsClientRunning():
            break
    if client1.IsClientRunning():  # kill if still running
        client1.KillClient()
    pprint.pprint(client1.ProcessClient())

    # Sequential clients
    # Client1
    client1 = iPerf(interface='lo0')
    client1.RunClient('127.0.0.1', time=5)
    print client1.ProcessClient()

    # Client2
    client2 = iPerf(interface='lo0')
    client2.RunClient('127.0.0.1', time=5)
    print client2.ProcessClient()

    # Client3 (DUT)
    dut = GetWiFiDevices()[0]
    client3 = iPerf(dut)
    client3.RunClient(hostIp, time=5)
    print client3.ProcessClient()

    # Parallel clients
    client1 = iPerf(interface='lo0')
    client2 = iPerf(interface='lo0')
    mgr = iPerfManager()
    mgr.AddClient(client1, '127.0.0.1', time=5)
    mgr.AddClient(client2, '127.0.0.1', time=5)
    mgr.RunClients()
    pprint.pprint(mgr.ProcessClients())
    mgr.ClearClients()

    # Stop iperf server
    server1.StopServer()

if __name__ == '__main__':
    main()

