from LogInit import LogIt
import subprocess
import re
import socket

TOS_QUEUES = ['BE', 'BK', 'VI', 'VO']

TC_CLASSES = [0, 1, 2, 3]

TOS_PRIORITIES = ['0x00', '0x20', '0x80', '0xC0']

class Ping(object):
    '''
    Class for using common network traffic tools like ping, http, etc
    '''
    def __init__(self, dut=False, dest=False):
        self.logger = LogIt()
        self.dut = dut
        self.destination = dest
        self.pingResults = dict(host="", sent=0, received=0, minping=0, avgping=0, maxping=0, jitter=0)
        self.rawPingOutput = ""
        self.rawPingError = ""
        self.v4pps = False
        self.v6pps = False

    def Ping(self, dut=False, dest=None, count=False, iface=None, wait=False, ttl=False,
             pattern=None, srcaddr=None, size=False, flood=False, numonly=False, timeout=False,
             tos=False, df=False, v6=False, verbose=False, maxFailurePercent=0, sweepminsize=False, sweepincrsize=False, sweepmaxsize=False):
        '''
        method to ping with options and then return parsed output. dut can be 'local' to ping from osx
            ping [-AaDdfLnoQqRrv] [-c count] [-I iface] [-i wait] [-l preload]
            [-M mask | time] [-m ttl] [-p pattern] [-S src_addr]
            [-s packetsize] [-T ttl] [-t timeout] [-W waittime]
        '''
        if self._GetDutAndDest(dut, dest):
            if v6:
                cmd = '/sbin/ping6'
            else:
                cmd = '/sbin/ping'
            if count:
                cmd = '%s -c %s' % (cmd, count)
            if iface:
                cmd = '%s -I %s' % (cmd, iface)
            if wait:
                cmd = '%s -i %s' % (cmd, wait)
            if ttl:
                cmd = '%s -m %s' % (cmd, ttl)
            if pattern:
                cmd = '%s -p %s' % (cmd, pattern)
            if srcaddr:
                cmd = '%s -S %s' % (cmd, srcaddr)
            if size:
                cmd = '%s -s %s' % (cmd, size)
            if flood:
                cmd = '%s -f' % (cmd)
            if df:
                cmd = '%s -D' % (cmd)
            if numonly:
                cmd = '%s -n' % (cmd)
            if timeout:
                cmd = '%s -t %s' % (cmd, timeout)
            if tos:
                cmd = '%s -z %s' % (cmd, tos)
            if sweepminsize:
                cmd = '%s -g %s' % (cmd, sweepminsize)
            if sweepmaxsize:
                cmd = '%s -G %s' % (cmd, sweepmaxsize)
            if sweepincrsize:
                cmd = '%s -h %s' % (cmd, sweepincrsize)

            cmd = '%s %s' % (cmd, self.destination)
            if sweepmaxsize and not timeout:
                    timeout = ((sweepmaxsize - sweepminsize) / sweepincrsize * (wait if wait else 1)) + 5
        self.logger.Info("Ping command is: %s", cmd)
        if self.dut == 'local':
            cmd = 'sudo %s' % cmd
            executeCommand = subprocess.Popen([cmd], stdout=subprocess.PIPE, stderr=subprocess.PIPE, shell=True)
            if count or timeout:
                out, error = executeCommand.communicate()
            else:
                return True
        else:
            if v6 and wait and wait < 0.002:
                if not self.v6pps:
                    self.v6pps = int(self.dut.getOS().Execute('/usr/sbin/sysctl net.inet6.icmp6.errppslimit | cut -d" " -f2').standardOutput)
                if self.v6pps <= 500:
                    self.logger.Warning("Warning: DUT will limit v6 pps to %i", self.v6pps)
            elif not v6 and wait and wait < .02:
                if not self.v4pps:
                    self.v4pps = int(self.dut.getOS().Execute('/usr/sbin/sysctl net.inet.icmp.icmplim | cut -d" " -f2').standardOutput)
                if self.v4pps <= 50:
                    self.logger.Warning("Warning: DUT will limit v4 pps to %i", self.v4pps)

            if count or timeout:
                myTimeout = count * 5 if (count and not timeout) else timeout  # Let's assume expected worst case = 5 sec timeout per ping
                pingResult = self.dut.getOS().Execute(cmd, runAsRoot=True, block=True, timeout=myTimeout)
            else:
                return self.dut.getOS().Execute(cmd, runAsRoot=True, block=False)
            out, error = pingResult.standardOutput, pingResult.standardError
        if verbose:
            self.logger.Debug("Ping Results: \n%s", out)
            self.logger.Debug("Ping Results: \n%s", error)
        try:
            result = self.Parse(out, v6)
        except:
            self.logger.Info("Ping failed, Ping Output: \n%s \nPing Error: \n%s" % (out, error))
            return False
        for value in result:
            if result[value] == 'NaN':
                self.logger.Warning("Results contain non-integer values for %s: %s. Results:%s;%s", value, result[value], result, error)
                return False

        if maxFailurePercent > 0:
            failPercent = (float((result['sent']) - result['received']) / float(result['sent'])) * 100
            return self._DidTestPass("Ping test", failPercent, maxFailurePercent)

        return result


    def _GetMatchGroups(self, pingOutput, regex):
        match = regex.search(pingOutput)
        if not match:
            raise Exception('Invalid PING output:\n' + pingOutput)
        return match.groups()

    def Parse(self, pingOutput, v6=False):
        '''
        Parses the `pingOutput` string into a dictionary containing the following
        fields:

            `host`: *string*; the target hostname that was pinged
            `sent`: *int*; the number of ping request packets sent
            `received`: *int*; the number of ping reply packets received
            `minping`: *float*; the minimum (fastest) round trip ping request/reply
                        time in milliseconds
            `avgping`: *float*; the average round trip ping time in milliseconds
            `maxping`: *float*; the maximum (slowest) round trip ping time in
                        milliseconds
            `jitter`: *float*; the standard deviation between round trip ping times
                        in milliseconds
        '''
        if v6:
            matcher = re.compile(r'PING6\(([0-9=\+]+) bytes\) ([a-f0-9:]+[%[^0-9a-z]*]?) \-\-> ([a-f0-9:]+[%[^0-9a-z]*]?)')
            host = self._GetMatchGroups(pingOutput, matcher)[2]
        else:
            matcher = re.compile(r'PING ([a-zA-Z0-9.\-]+) \(')
            host = self._GetMatchGroups(pingOutput, matcher)[0]

        try:
            matcher = re.compile(r'(\d+) packets transmitted, (\d+) packets received')
            sent, received = self._GetMatchGroups(pingOutput, matcher)
        except:
            sent, received = ['NaN'] * 2

        try:
            matcher = re.compile(r'(\d+.\d+)/(\d+.\d+)/(\d+.\d+)/(\d+.\d+)')
            minping, avgping, maxping, jitter = self._GetMatchGroups(pingOutput, matcher)
        except:
            minping, avgping, maxping, jitter = ['NaN'] * 4

        return {'host': str(host), 'sent': int(sent), 'received': int(received),
                'minping': float(minping), 'avgping': float(avgping), 'maxping': float(maxping),
                'jitter': float(jitter)}

    def _GetDutAndDest(self, dut, dest):
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

    def _DidTestPass(self, testDescription, testcasePercentage, allowedPassPercentage):
        if testcasePercentage > allowedPassPercentage:
            self.logger.Fail("Test %s failed. Loss percentage: %f, max allowed fail percentage: %f", testDescription, testcasePercentage, allowedPassPercentage)
            return False
        else:
            self.logger.Pass("Test %s passed. Loss percentage: %f, max allowed fail percentage: %f", testDescription, testcasePercentage, allowedPassPercentage)
            return True

    def SetDestination(self, destination):
        '''
        sets destination address
        '''
        self.destination = destination

    def SetDut(self, dut):
        '''
        sets device
        '''
        self.dut = dut

#----------------------------------------------------------------------
#
#----------------------------------------------------------------------

if __name__ == '__main__':
    from DUT.DUT_Utils import GetWiFiDevices
    from APControl.SohoAP import GetAppleWiFiNetwork
    dut = GetWiFiDevices()[0]
    dut.wifi.Join(GetAppleWiFiNetwork())
    p = Ping()
    ret = p.Ping(dut, 'www.google.com', count=3)
    print ret


