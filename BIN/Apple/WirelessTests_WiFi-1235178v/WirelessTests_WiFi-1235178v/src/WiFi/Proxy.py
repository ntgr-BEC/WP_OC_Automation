'''
Proxy.py

WirelessAutomation

Created by Dani Gleser 2012-10-13
Copyright (c) 2012 Apple, Inc. All rights reserved.
'''

from WiFiModulesExceptions import MissingModule, ConnectionError
from SSHControl import SSHControl
from LogInit import LogIt

class Proxy(object):
    '''
    Class to get proxy information from device and proxy server logs.
    '''
    def __init__(self, dut, serverIP, syslogfile="/var/log/squid/access.log", user='automation', passwd='test!123', timeout=5):
        '''
        Normally init should not take args, but in this case is is expedient to do so
        Requires server IP or DNS name to connet to
        optional username and passwords, and optional time out
        '''
        self.dut = dut
        self.logger = LogIt()
        self.server = serverIP
        self.user = user
        self.passwd = passwd
        self.timeout = timeout
        self.SyslogFile = syslogfile
        self.logpathroot = self.logger.getTestLogsPath()

    def getProxyState(self):
        '''
        This function gets the proxy state from the device using the scutil --proxy command
        '''
        self.logger.Info("Starting Get Proxy State")
        cmd1 = "/usr/sbin/scutil --proxy"
        proxyState = self.dut.getOS().Execute(cmd1, True, True)
        return proxyState

    def clearProxyLog(self):
        ''' 
        This function clears the proxy logs on the server by using cat /dev/NULL > <file name>
        It gets the file name and server information from the initialization of the Proxy object
        '''
        syslogcmd = "cat /dev/NULL > %s" % (self.SyslogFile)
        sshSession = SSHControl(self.server, user=self.user, passwd=self.passwd, timeout=self.timeout)
        sshSession.Login()
        self.logger.Info("SSH options: Server: %s User: %s PWD: %s CMD: %s" % (self.server, self.user, self.passwd, syslogcmd))
        sshSession.Cmd(syslogcmd)
        sshSession.Logout()

    def getProxyLog(self):
        ''' 
        This function gets the proxy logs on the server and returns the logs in a string
        It gets the file name and server information from the initialization of the Proxy object
        '''
        syslogcmd = "cat %s" % (self.SyslogFile)
        sshSession = SSHControl(self.server, user=self.user, passwd=self.passwd, timeout=self.timeout)
        sshSession.Login()
        self.logger.Info("SSH options: Server: %s User: %s PWD: %s CMD: %s" % (self.server, self.user, self.passwd, syslogcmd))
        proxyResult = sshSession.Cmd(syslogcmd)
        sshSession.Logout()
        self.logger.Info("getProxyLog Result:\n%s" % proxyResult)
        return proxyResult

    def findStringinProxyLog(self, proxyLog, findString):
        '''
        This function parses a the proxy log for a string and returns True if the string is found in the proxy log and returns false if not
        '''
        if findString in proxyLog :
            self.logger.Info("Found String: %s" % findString)
            return True
        else:
            self.logger.Fail("Failed to find String: %s" % findString)
            return False

    def saveProxyLog(self, proxyLog, LogFileDest):
        ''' 
        This function saves the proxy log string to a file on the host Log directory
        It gets the file name and server information from the initialization of the Proxy object
        '''
        proxyLogFile = "%s%s-%s" % (self.logpathroot, LogFileDest, "proxy.log")
        self.logger.Info("Writing log to file: %s" % (proxyLogFile))
        f = open(proxyLogFile, "w")
        f.write(proxyLog)
        f.close()

    def __GetDut(self, dut=False):
        '''
        internal function to validate dut
        '''
        if not dut:
            return self.dut
        return dut
    def __del__ (self):
        print "Proxy: Proxy object deleted"

