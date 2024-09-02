'''
SSHControl.py

WirelessAutomation

Created by Quint Friesen 2011-1-31
Copyright (c) 2011 Apple, Inc. All rights reserved.
'''

from WiFiModulesExceptions import MissingModule, ConnectionError

class SSHControl(object):
    '''
    Class to ssh into hosts and execute commands
    '''
    def __init__(self, machIP, user='automation', passwd='test!123', timeout=5):
        '''
        Normally init should not take args, but in this case is is expedient to do so
        Requires server IP or DNS name to connet to
        optional username and passwords
        '''
        try:
            temp = __import__('pxssh')
            pxssh = temp.pxssh
            self.ExceptionPxssh = temp.ExceptionPxssh
            self.EOF = temp.EOF
        except:
            raise MissingModule('Did not find pxssh module on the machine, please do "sudo easy_install pexpect" from the shell command line.')
        self.server = machIP
        self.user = user
        self.passwd = passwd
        self.timeout = timeout
        self.log = open('sshlog.txt', 'w')
        self.ssh = pxssh(timeout=timeout, logfile=self.log)

    def __del__(self):
        if self.log:
            self.log.close()
            self.log = None
        if self.ssh:
            self.ssh.close()
            self.ssh = None

    def Login(self):
        '''
        Login to a normal host
        '''
        '''
        try:
            self.ssh.login(self.server, self.user, self.passwd, self.timeout)
            self.ssh.prompt()  # match the prompt
        except (self.ExceptionPxssh, self.EOF), e:
            raise ConnectionError('pxssh failed on login.\n%s' % str(e))
        '''
        # Add work around for windows XP
        try:
            self.ssh.login(self.server, self.user, self.passwd, self.timeout, auto_prompt_reset=False)
            if not self.ssh.prompt():  # match the prompt
                self.ssh.sendline(self.passwd)
                self.ssh.prompt()
            if not self.ssh.set_unique_prompt():
                self.ssh.close()
                raise ConnectionError ('could not set shell prompt\n' + self.before)
            self.ssh.prompt()
        except (self.ExceptionPxssh, self.EOF), e:
            raise ConnectionError('pxssh failed on login.\n%s' % str(e))

    def Cmd(self, command):
        '''
        Sends a single command to the cisco device and returns the output
        '''
        try:
            self.ssh.sendline(command)
            self.ssh.prompt(self.timeout)
        except self.ExceptionPxssh, e:
            print 'pxssh failed on command %s.' % command
            print str(e)
        return repr(self.ssh.before)

    def Cmds(self, commands):
        '''
        arg should be a list, loops the list of commands and runs Cmd on them
        '''
        retList = []
        for command in commands:
            retList.append(self.Cmd(command))
        return retList

    def Logout(self):
        '''Wrapper for the pxssh logout'''
        self.ssh.logout()
