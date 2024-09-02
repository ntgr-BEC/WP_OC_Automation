'''
WiFiControl.py

WirelessAutomation

Updated by Quint Friesen 2011-1-11
Created by Dmitry Halavin on 2011-04-29
Copyright (c) 2011 Apple, Inc. All rights reserved.
'''

import os
from subprocess import Popen, PIPE
from LogInit import LogIt
from TrafficGen.Ping import Ping
# from HostTools.SnifferTools import SnifferTools
import subprocess
from time import sleep

class WiFiControl(object):
    '''
    Class to control the wifi settings on a Mac host
    '''
    def __init__(self):
        super(WiFiControl, self).__init__()
        self.logger = LogIt()

    def GetInterfaceName(self):
        ''' Returns the network interface name of AirPort HW, ie. en0 '''
        tmp = os.popen('system_profiler SPNetworkDataType | grep -A 1 "Hardware: AirPort" | cut -d ":" -f 2 | tail -1')
        ifname = tmp.readline().replace(' ', '').replace('\n', '')
        tmp.close()
        return ifname

    def GetIpAddress(self):
        ''' Returns the network ip address of Airport HW, ie. en0 '''
        tmp = os.popen('system_profiler SPNetworkDataType | grep -A 3 "Hardware: AirPort" | cut -d ":" -f 2 | tail -1')
        ifname = tmp.readline().replace(' ', '').replace('\n', '')
        tmp.close()
        return ifname

    def Associate(self, ap, adminPassword=None, eapType='None'):
        '''
        Method to connect Mac to specific AP defined in ap
        first arg is and AP dictionary that is used in all WAF
        second arg is optional sudo password for those that do not have sudoers file configured
        third arg is eap type if connecting to an EAP network
        '''
        ifname = self.GetInterfaceName()
        # command = ' setPower 1'
        # commandOut = self._corewlan(command)
        # if commandOut != '':
            # self.logger.Info('Turn on WiFi Failed, output: %s', commandOut)
            # return False
        if not self.SetAirportPower(on=True):
            return False

        command = '/usr/local/bin/corewlan ' + ifname + ' removeAllProfiles'

        if adminPassword:
            os.system('echo %s | sudo -S %s' % (adminPassword, command))
        else:
            os.system('sudo -S %s' % (command))
        security = 'None'
        if ap.isWep:
            security = '"WEP Password" --password ' + ap.password
        elif ap.isWpa or ap.isWpa2:
            security = '"WPA/WPA2 Personal" --password ' + ap.password
        elif ap.isEap:
            security = 'EAP --password ' + ap.password + " --username " + ap.username
        else:
            security = 'Open'
        if security == 'EAP':
            command = "associate --SSID '" + ap.ssid + "' --security 'WPA/WPA2 Enterprise' --keychain"
            commandEAP = 'eapolcfg createProfile --authType ' + eapType + " --SSID '" + ap.ssid + "' --securityType Any"
            os.system(commandEAP)
        else:
            command = "associate --SSID='" + ap.ssid + "' --security " + security
        # commented out for loop, not sure what it was for
#        for i in range(3):
        commandOut = self._corewlan(command)
        if commandOut != '':
            self.logger.Info('Associate failure, output: %s', commandOut)
        else:
            return True
        return False

    def StartIBSS(self, ssid, security='Open', channel='11', password=''):
        '''
        Method to config MAC to create an IBSS (Ad-Hoc) network
        first arg is the SSID
        second arg is optional for security settings: Open', 'WEP40 Open System', or 'WEP104 Open System'
        third arg is optional for channel: is an integer 1-11, 36, 40, 44, or 48
        '''
        if not self.SetAirportPower(on=True):
            return False

        command = 'removeAllProfiles'
        commandOut = self._corewlan(command)
        if commandOut != '':
            self.logger.Warning('Remove all profiles failed, output: %s', commandOut)

        if security == 'Open':
            requirePassword = False
            command = 'startIBSSMode --SSID "%s" --security "%s" --channel %s' % (ssid, security, channel)
        else:
            requirePassword = True
            command = 'startIBSSMode --SSID "%s" --security "%s" --channel %s --password "%s"' % (ssid, security, channel, password)
        commandOut = self._corewlan(command)
        if commandOut != '':
            self.logger.Info('Create IBSS network failure, output: %s', commandOut)
        else:
            adHocConfig = {'uuid':'', 'ssid':ssid, 'userName':'', 'password':password, 'requiresPassword':requirePassword, 'isHidden':False, 'isWep':requirePassword, 'isWpa':False, 'isEap':False}
            return adHocConfig
        return False

    def Disassociate(self):
        ''' Disassociates from ap on interface '''
        self._corewlan('disassociate')

    def SetAirportPower(self, on=True):
        if on:
            command = ' setPower 1'
        else:
            command = ' setPower 0'
        commandOut = self._corewlan(command)
        if commandOut != '':
            self.logger.Info('Set WiFi Failed, output: %s', commandOut)
            return False
        return True

    def SetAirportChannel(self, channel, adminPassword=False):
        '''
        Sets channel host wifi
        first arg is the channel you want to set
        second arg is optional sudo password for those that do not have sudoers file configured
        '''
        disaCommand = '/System/Library/PrivateFrameworks/Apple80211.framework/Versions/A/Resources/airport -z'
        channelCommand = '/System/Library/PrivateFrameworks/Apple80211.framework/Versions/A/Resources/airport --channel=' + str(channel)
        if adminPassword:
            os.system('echo %s|sudo -S %s' % (adminPassword, disaCommand))
            _channel_result = os.system('echo %s|sudo -S %s' % (adminPassword, channelCommand))
        else:
            os.system('sudo -S %s' % (disaCommand))
            channelResult = os.system('sudo -S %s' % (channelCommand))
        if channelResult == 0:
            self.logger.Info('Set Airport to Channel %d', channel)
            return True
        else:
            self.logger.Info('Can not set Airport to Channel %d', channel)
            return False

    def _which(self, name):
        ''' Runs which to find an executable in system path and return string output '''
        pout = os.popen('which %s' % name)
        txt = pout.read()
        pout.close()
        return txt.strip()

    def GetAirportSSID(self):
        '''
        Get SSID from Airport
        '''
        ssid = self.Apple80211Mac('--ssid')
        if not ssid:
            self.logger.Info("Failed to get a SSID")
        else:
            ssid = ssid.replace('ssid = ', '').replace('\n', '')
        return ssid

    def Apple80211Mac(self, *args):
        ''' Run apple80211 tool on Mac and return the system output '''

        # Find corewlan and check for error
        cmd = self._which('apple80211')
        if cmd == '':
            self.logger.Info('apple80211 on host no found!')
            return ''

        for arg in args:
            cmd += ' ' + arg

        # Run the command
        ssidResult = MACCommandExecuteWithBlock(cmd)
        if ssidResult[1] == '':
            self.logger.Info("%s returns with: %s", cmd, ssidResult[0].replace('\n', ''))
        else:
            self.logger.Info("Failed to run %s with error: %s", cmd, ssidResult[1])
        return ssidResult[0]

    def _corewlan(self, *args):
        ''' Run corewlan utility and return the system output '''

        # Get the Airport interface name
        ifname = self.GetInterfaceName()

        # Find corewlan and check for error
        cmd = self._which('corewlan')
        if cmd == '':
            self.logger.Warning('Failed to config WiFiControl, corewlan no found!')
            return ''

        # Build the commmand
        cmd += ' ' + ifname
        for arg in args:
            cmd += ' ' + arg

        # Run the command
        self.logger.Info(cmd)
        tmp = os.popen(cmd)
        ret = tmp.readline().replace(' ', '').replace('\n', '')
        tmp.close()

        # Log any errors and return the output
        if ret != '':
            self.logger.Warning('Failed to run command, returned output: %s', ret)
        return ret

    def CleanArpEntry(self):
        '''
        Clear arp entery on MAC
        '''
        testResult = False
        cmd = 'sudo arp -da'
        configResult = MACCommandExecuteWithBlock(cmd)
        if configResult[1] == '':
            self.logger.Info("Clean arp entry Passed")
            testResult = True
        else:
            if configResult[1].rstrip() == "dyld: DYLD_ environment variables being ignored because main executable (/usr/bin/sudo) is setuid or setgid":
                self.logger.Info("Clean arp entry Passed")
                self.logger.Warning("result has warning: %s", configResult[1])
                testResult = True
            else:
                self.logger.Info("Clean arp entry Failed")
                self.logger.Info("result is: %s", configResult[1])
                testResult = False

        return testResult

    def MacWiFiClientAssociate(self, ap, pingDestination=False, extendedArpTimeout=150, count=10, timeout=10, retry=2, verbose=False):
        '''
        Config Mac Airport WiFi client to join ap, ap needs to be a WiFiNetwork object
        if pingDestination is provided, it will ping after association
        extendedArpTimeout is used to extend ARP timeout from default 30 to 150
        This function will use sudo, so please make sure you have sudoers configured
        '''
        if self.GetAirportSSID() == ap.ssid:
            self.logger.Info("Host already connected with the WiFi network %s", ap.ssid)
        else:
            for i in range(1, retry + 1):
                if self.Associate(ap):
                    testResult = True
                    break
                else:
                    testResult = False
                    self.logger.Debug("MAC failed to join %s at %d try", ap.ssid, i)
                    sleep(3)
            if not testResult:
                return False

        if pingDestination:
            hostPing = Ping(dut='local')
            if not hostPing.Ping(dest=pingDestination, count=count, timeout=timeout, verbose=verbose):
                return False

        # Extend ARP timeout for verifying disconnected hosts
        if extendedArpTimeout > 30:
            cmd = 'sudo sysctl -w net.link.ether.inet.arp_llreach_base=%d' % (extendedArpTimeout)
            # mySnifferTools = SnifferTools()
            # configResult = mySnifferTools.MACCommandExecuteWithBlock(cmd)
            configResult = MACCommandExecuteWithBlock(cmd)
            if configResult[1] == '':
                self.logger.Info("Set extended ARP timeout to %d on Host Passed", extendedArpTimeout)
            else:
                if configResult[1].rstrip() == "dyld: DYLD_ environment variables being ignored because main executable (/usr/bin/sudo) is setuid or setgid":
                    self.logger.Info("Set extended ARP timeout to %d on Host Passed", extendedArpTimeout)
                    self.logger.Warning("result has warning: %s", configResult[1])
                else:
                    self.logger.Info("Set extended ARP timeout to %d on Host Fail", extendedArpTimeout)
                    self.logger.Info("result is: %s", configResult[1])
                    return False

        return True

    def SendWakeUpPacket(self, ip):
        '''
        Sends a wow wake up packet using apple80211 from host Mac OS
        Note: Host need to be on the same subnet with dut
        '''
        self.logger.Info('Sending UDP magic packet over WIFI interface')

        # Sends magic packet over UDP if arp entry exists, otherwise as Ethernet packet
        # Entry should still be in arp table, otherwise this will fail
        testResult = False
        cmd = 'apple80211 -wake=%s' % (ip)
        # mySnifferTools = SnifferTools()
        # magicPacketResult = mySnifferTools.MACCommandExecuteWithBlock(cmd)
        magicPacketResult = MACCommandExecuteWithBlock(cmd)
        if magicPacketResult[1] == '':
            self.logger.Info("WoW magic packet sent to %s", ip)
            testResult = True
        else:
            self.logger.Info("Failed to sent WoW magic packet to %s", ip)
            self.logger.Info("result is %s", magicPacketResult[1])
        return testResult

    def VerifyPing(self, dest=None, count=10, maxFailurePercent=90, **kwargs):
        ''' Returns True if host can ping url destination '''
        self.logger.Info('Verify host can ping %s' % repr(dest))

        # Randomize some url, if not provided
        if dest is None:
            from random import randint
            dest = ['www.yahoo.com', 'www.espn.com', 'www.google.com'][randint(0, 2)]

        # Use ping utility to send from DUT OS to dest
        from TrafficGen.Ping import Ping
        p = Ping()
        return p.Ping(
                dut='local',
                dest=dest,
                count=count,
                maxFailurePercent=maxFailurePercent,
                **kwargs
            )


class ProfileControl(object):
    '''Class to install delete and manage profiles'''
    def __init__(self, profile=None):
        super(ProfileControl, self).__init__()
        self.logger = LogIt()
        self.cmd = 'sudo profiles'
        self.profile = profile
        self.error = None
        self.out = None

    def Install(self, profile=None):
        '''
        based on the profiles command
            profiles -I -F /testfile.mobileconfig
        '''
        if not self._ValidateProfile(profile):
            return False
        cmd = '%s -I -F %s' % (self.cmd, self.profile)
        self._Execute(cmd)

    def Remove(self, profile=None):
        '''
        based on the profiles command
            profiles -R -F /testfile.mobileconfig
        '''
        if not self._ValidateProfile(profile):
            return False
        cmd = '%s -R -F %s' % (self.cmd, self.profile)
        self._Execute(cmd)

    def RemoveAll(self):
        '''
        based on the profiles command
            profiles -D
        '''
        cmd = '%s -D -f' % self.cmd
        self._Execute(cmd)

    def GetInstalled(self):
        '''
        based on the profiles command
            profiles -P
        '''
        cmd = '%s -L' % self.cmd
        self._Execute(cmd)
        return self.out

    def GetLastError(self):
        return self.error

    def ClearError(self):
        self.error = None

    def _Execute(self, cmd):
        results = Popen([cmd],
                        stdout=PIPE,
                        stderr=PIPE,
                        shell=True)
        self.out, error = results.communicate()
        if results.returncode != 0:
            self.logger.Warn('"%s" returned non-zero return code of %s' % (cmd, results.returncode))
            self.error = 'STDOUT: %s\nSTDERR: %s' % (self.out, error)

    def _ValidateProfile(self, profile):
        if not profile and not self.profile:
            self.logger.Warn('No profile specified. you must provide a path to the profile')
            return False
        if profile:
            self.profile = profile
            self.ClearError()
        if not os.path.isfile(self.profile):
            self.logger.Warn('Profile specified is not a valid path to a file, you must provide a path to the profile')
            return False
        return True


def MACCommandExecuteWithBlock(command):
    """
    Function that executes command on mac and returns the output and error of command.
    """
    logger = LogIt()
    logger.Info("Received command %s, executing it on mac", command)
    execute_command = subprocess.Popen([command],
                    stdout=subprocess.PIPE,
                    stderr=subprocess.PIPE,
                    shell=True)
    # Create blocking call that waits till command is finished and returns it
    out, error = execute_command.communicate()
    results = [out, error]
    return results
