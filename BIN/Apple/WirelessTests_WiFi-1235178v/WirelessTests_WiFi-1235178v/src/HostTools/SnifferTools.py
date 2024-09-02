'''
SnifferTools.py
WirelessAutomation

Updated by aomoto 2012-12-06
Created by Meng Wang on 2/16/10 as WiFi_StressCommon.py
Copyright (c) 2010 Apple. All rights reserved.
'''

from common.ToolWrappers import MacProcessCmd
from HostTools.WiFiControl import MACCommandExecuteWithBlock
from SSHControl import SSHControl
from time import sleep
import sys
import shutil
import errno, os
import re
import subprocess
import signal
import time
from LogInit import LogIt

class SnifferTools(object):
    '''
    Class to control various sniffing tools as they are available
    '''

    def __init__(self):
        self.logger = LogIt()
        self._wireshark = '/Applications/Wireshark/bin/wireshark'
        self._outDir = './Logs'
        self.cmd = False
        self.sniffer = False
        self.user = False
        self.passwd = False
        self.server = False
        self._Init()

    def _Init(self):
        # Init path to wireshark
        self._wireshark = self._which('wireshark')
        self.logger.Debug('Found wireshark path: %s' % repr(self._wireshark))

    def __del__(self):
        self.RemoteClose()

    def Configure(self, server=False, user=False, passwd=False):
        '''
        Configures the Wireshark options for now, as we bring in more tools, will configure all
        '''
        if server:
            self.server = server
        if user:
            self.user = user
        if passwd:
            self.passwd = passwd

    def GetOutputDir(self):
        return self._outDir

    def SetOutputDir(self, path):
        self._outDir = path

    def AirportDisassociate(self):
        """ Runs 'airport -z' to disassociate WiFi """
        self.logger.Debug('Mac OS airport: Disassociate')
        return (os.system('sudo airport -z') == 0)

    def AirportSetChannel(self, chan, secondary=None):
        """ Runs 'airport --channel=<channel>' to set WiFi channel """
        self.logger.Debug('Mac OS airport: Set channel %s,%s' % (chan, secondary))
        # Need to use apple80211 for 40MHz
        if not secondary:
            return (os.system('sudo airport --channel=%s' % chan) == 0)
        else:
            return (os.system('sudo apple80211 --channel=%s,%s' % (chan, secondary)) == 0)

    def WiresharkStart(self, captureFileName, captureHeader='IEEE802_11_RADIO',
                             optionalflags='', interface='en1', monitorMode=True):
        '''
        Method to capture using wireshark, this is the start function
        Takes captureFileName as required args, captureHeader and optional flags as optional args
        we can remove this function when WAF has those radars fixed:
        <rdar> description
        <rdar> description
        <rdar> description
        '''
        captureFile = os.path.join(self._outDir, captureFileName)
        cmdStr = '%s -i %s -y %s -w %s -k %s' % \
                (self._wireshark, interface, captureHeader, captureFile, optionalflags)
        if monitorMode:
            cmdStr += ' -I'
        self.cmd = MacProcessCmd(cmdStr)
        self.logger.Debug(cmdStr)
        self.cmd.run()
        self.logger.Info("sleep 10 seconds for sniffer to launch")
        sleep(10)
        if self.cmd.is_running():
            self.logger.Pass('wireshark sniffer is running')
        else:
            self.logger.Fail('wireshark is not running')
        return self.cmd

    def WiresharkStop(self):
        '''
        Method to capture using wireshark, this is the stop function
        Takes no args, requires that Configure() and WiresharkStart() has already been run
        '''
        # Skip if wireshark is not running
        if not self.cmd:
            self.logger.Debug('Skip stop, wireshark is not running')
            return
        self.cmd.kill()
        if self.cmd.is_running():
            self.logger.Fail('wireshark is still running')
        else:
            self.logger.Pass('wireshark sniffer is killed')
        sleep(3)
        return self.cmd

    def WiresharkTimeCapture(self, duration, captureFileName,
                                captureHeader='IEEE802_11_RADIO', interface='en1'):
        '''
        Method to capture using wireshark for a certain duration
        Takes duration as an integer for required args
        Optional args are a captureFileName and captureHeader
        '''
        captureFile = os.path.join(self._outDir, captureFileName)
        cmdStr = '%s -i %s -y %s -a duration:$s -w %s -k' % \
                    (self._wireshark, interface, captureHeader, str(duration), captureFile)
        cmd = MacProcessCmd(cmdStr)
        self.logger.Debug(cmdStr)
        cmd.run()
        if cmd.is_running():
            self.logger.Pass('wireshark sniffer is running')
        else:
            self.logger.Fail('wireshark is not running')
        return cmd

    def _CheckSSH(self, server, user='automation', passwd='test!123'):
        if not self.sniffer:
            if not self.server:
                self.server = server
            if not self.user:
                self.user = user
            if not self.passwd:
                self.passwd = passwd
            self.logger.Info('Connecting to %s (%s:%s)' % (server, user, passwd))
            self.sniffer = SSHControl(server, user=user, passwd=passwd)
            self.sniffer.Login()

    def RemoteClose(self):
        if self.sniffer:
            self.sniffer.Logout()
            self.sniffer = False

    def RemoteTSharkTimeCap(self, server, duration, captureFileName, interface='1', user='automation', passwd='test!123', captureHeader='IEEE802_11_RADIO'):
        '''
        Method to capture using wireshark for a certain duration on a remote host
        Takes server as a DNS name or IP address and duration as an integer for required args
        Optional args are a captureFileName and captureHeader and well as username and password for remote host
        NOTES: Remote hosts require tshark to be in the path
               Windows remote hosts require cygwin and cygwin SSH installed
        '''
        self._CheckSSH(server, user, passwd)
        self.logger.Info("Start capture")
        self.sniffer.Cmd("tshark -i %s -y %s -a duration:%s -w %s -q &" % (interface, captureHeader, str(duration), captureFileName))

    def RemoteTSharkStart(self, server, captureFileName, interface='1', user='automation', passwd='test!123', captureHeader='IEEE802_11_RADIO'):
        '''
        Method to start a capture using wireshark on a remote host
        Takes server as a DNS name or IP address and duration as an integer for required args
        Optional args are a captureFileName and captureHeader and well as username and password for remote host
        NOTES: Remote hosts require tshark to be in the path
               Windows remote hosts require cygwin and cygwin SSH installed
        '''
        self._CheckSSH(server, user, passwd)
        # TODO: Use Dmitry function to find interface
        command = 'tshark -i %s -y %s -w %s -q &' % (interface, captureHeader, captureFileName)
        self.logger.Info("Running: %s", command)
        snifferResult = self.sniffer.Cmd(command)

        if snifferResult.find("[1]") > 0:
            return True
        else:
            return False

    def RemoteTSharkStop(self, server, user='automation', passwd='test!123'):
        '''
        Method to stop a running capture using wireshark on a remote host
        Takes server as a DNS name or IP addressfor required args
        Optional args are username and password for remote host
        NOTES: Remote hosts require tshark to be in the path
               Windows remote hosts require cygwin and cygwin SSH installed
        '''
        self._CheckSSH(server, user, passwd)
        cygwinVersion = self.sniffer.Cmd('uname')
        if cygwinVersion.find('CYGWIN_NT-5.1') > 0:
            self.sniffer.Cmd('tskill tshark')
        else:
            self.sniffer.Cmd('killall -9 tshark')

        sharkDead = self.sniffer.Cmd('echo $?')
        if int(sharkDead.split('\\r\\n')[1]) == 0:
            return True
        else:
            self.logger.Warning('Failed to kill tshark, error %s', sharkDead)
            return False


    def _AirpcapChannelConfig(self, snifferCard):
        '''
        Method to config AirPcap Sniffer WiFi client card channel on a remote host
        Takes SnifferCard object as required args
        NOTES: Remote hosts require setChannel.exe to be in the path
               Windows remote hosts require cygwin and cygwin SSH installed
                Need have ssh connection before running this function
        '''
        if not snifferCard.isAirPcap:
            self.logger.Info("Can not config this card because it is not a AirPcap card")
            return False
        self.logger.Info("Try to config %s", snifferCard)
        changeChannelResult = self.sniffer.Cmd("setChannel.exe '%s' %s" % (snifferCard.deviceName, snifferCard.channel))
        self.logger.Info("setChannel. exe returned: %s", changeChannelResult)
        if changeChannelResult.find("Error opening the adapter") > 0:
            self.logger.Info("Failed to finded the device named: %s", snifferCard.deviceName)
            return False
        elif changeChannelResult.find("set succesfully") > 0:
            self.logger.Info("Set device %s to channel %s", snifferCard.deviceName, snifferCard.channel)
            return True
        else:
            self.logger.Warning("Failed to set device named: %s to channel %s", snifferCard.deviceName, snifferCard.channel)
            return False

    def RemoteAirpcapChannelConfig(self, server, snifferCard, user='automation', passwd='test!123'):
        '''
        Method to config AirPcap Sniffer WiFi client card channel on a remote host
        Takes server as a DNS name or IP address and a snifferCard object for required args
        Optional args are a deviceName and well as username and password for remote host
        NOTES: Remote hosts require setChannel.exe to be in the path
               Windows remote hosts require cygwin and cygwin SSH installed
        '''
        snifferCard = SnifferCard(snifferCard)

        self._CheckSSH(server, user, passwd)
        configResult = self._AirpcapChannelConfig(snifferCard)
        return configResult

    def RemoteAirpcapSingleDeviceChannelConfig(self, server, snifferCard, user='automation', passwd='test!123'):
        '''
        Method to config AirPcap Sniffer Multi Client cards channel on a remote host
        Takes server as a DNS name or IP address and channel, and an SnifferCard object or sniffer card configuration dict required args
        Optional args are a deviceName and well as username and password for remote host
        NOTES: Remote hosts require setChannel.exe to be in the path
               Windows remote hosts require cygwin and cygwin SSH installed
        '''
        snifferCard = SnifferCard(snifferCard)
        self._CheckSSH(server, user, passwd)
        configResult = self._AirpcapChannelConfig(snifferCard)
        return configResult

    def RemoteAirpcapMultiDevicesChannelConfig(self, server, devicesArray, user='automation', passwd='test!123'):
        '''
        Method to config AirPcap Sniffer Multi Client cards channel on a remote host
        Takes server as a DNS name or IP address and channel, and an array of SnifferCard object or sniffer card configuration dict required args
        Optional args are a deviceName and well as username and password for remote host
        NOTES: Remote hosts require setChannel.exe to be in the path
               Windows remote hosts require cygwin and cygwin SSH installed
        '''
        self._CheckSSH(server, user, passwd)
        for device in devicesArray:
            self.logger.Info("device is: %s", device)
            snifferCard = SnifferCard(device)
            configResult = self._AirpcapChannelConfig(snifferCard)
            if not configResult:
                break

        return configResult

    def GetRemoteTsharkDeviceInterfaceNumberByDeviceName(self, server, snifferCard, user='automation', passwd='test!123'):
        """
        Method to get tshark device interface number
        Takes server as a DNS name or IP address, pcapFile is the pcap file need to get converted
        snifferCard is a SnifferCard object
        optional args are username and password for the remote host
        NOTES: Remote hosts require tshark and setChannel.exe to be in the path
           Windows remote hosts require cygwin and cygwin SSH installed
        """
        self._CheckSSH(server, user, passwd)
        cmd = "tshark -D"
        returnValue = self.sniffer.Cmd(cmd)
        self.logger.Debug("result is %s", returnValue)
        deviceInfoArray = returnValue.split('\\r\\n')

        # Convert the device names if it has "\"
        deviceTmpName = snifferCard.deviceName.replace('\\', '\\\\')

        for deviceInfo in deviceInfoArray:
            if deviceInfo.find(snifferCard.deviceName) > 0 or deviceInfo.find(deviceTmpName) > 0:
                self.logger.Info("Find the device %s: %s", snifferCard.deviceName, deviceInfo)
                self.logger.Info("The interface number for device %s is %s", snifferCard.deviceName, deviceInfo[0])
                return deviceInfo[0]

        self.logger.Info("Could not find any device matches name %s", snifferCard.deviceName)
        self.logger.Info("The devices listed are: %s", returnValue)
        return None

    def RemoteTSharkMultiChannelAggregatorStart(self, server, captureFileName, interfaceName='AirPcap Multi-Channel Aggregator', user='automation', passwd='test!123', captureHeader='IEEE802_11_RADIO'):
        '''
        Method to start a capture using tshark on a remote host for multi channel aggregator sniffer capture
        Takes server as a DNS name or IP address and duration as an integer for required args
        captureFileName is the file name for the saved sniffer capture
        interfaceName is the interfaceName for multi channel capture.
        Optional args are a captureFileName and captureHeader and well as username and password for remote host
        NOTES: Remote hosts require tshark to be in the path
               Windows remote hosts require cygwin and cygwin SSH installed
        '''
        snifferCard = SnifferCard()
        snifferCard.deviceName = interfaceName
        interface = self.GetRemoteTsharkDeviceInterfaceNumberByDeviceName(server, snifferCard, user=user, passwd=passwd)
        if interface is not None:
            return self.RemoteTSharkStart(server, captureFileName, interface=interface, user=user, passwd=passwd, captureHeader=captureHeader)
        else:
            self.logger.Info("Could not find the device interface for %s", interfaceName)
            return False

    def MountRemotePCSharedFolder(self, server, remoteSharedFolder, localFolderForMount, domain='MSSNIFFER', user='automation', passwd='test!123'):
        """
        Method to mount a remote Windows PC shared folder from host
        Takes server as a DNS name or IP address, remoteSharedFolder is remote shared name of the folder
        and localFolderForMount as required args
        Optional args are: localFolder - domain - domain or workgroup name of the remote server
        user - remote server username, passwd - remote server password
        NOTES: Remote hosts require cygwin and cynwin SSH installed; remote hosts require have shared folder configured
        """
        # Skip if folder already exists
        if os.path.exists(localFolderForMount):
            self.logger.Debug('Skip mount remote server, mount folder already exists: %s' % localFolderForMount)
            return True

        # Remove local mount dir
        try:
            if os.path.exists(localFolderForMount):
                shutil.rmtree(localFolderForMount)
                self.logger.Info("Wait for 10 seconds before try to mount...")
                sleep(10)
        except OSError as dir_except:
            self.logger.Info("Failed to delete folder %s, error: %s", localFolderForMount, dir_except)

        if not os.path.exists(localFolderForMount):
            os.makedirs(localFolderForMount)
        """try:
            os.makedirs(localFolderForMount)
            self.logger.Info("Created folder %s for mount", localFolderForMount)
        except OSError as dir_except:
            if dir_except.errno != errno.EEXIST:
                self.logger.Info("Failed to create folder to mount: %s", localFolderForMount)
                return False"""

        cmd = "mount_smbfs \"//%s;%s:%s@%s/%s\" %s" % (domain, user, passwd, server, remoteSharedFolder, localFolderForMount)
        # self.logger.Info("The mount command is: %s", cmd)

        mountResult = MACCommandExecuteWithBlock(cmd)
        # self.logger.Info("result is %s", mountResult)
        if mountResult[1] == '':
            self.logger.Info("Mount Remote Server Passed")
            return True
        else:
            self.logger.Info('Mount failed')
            self.logger.Info("result is %s", mountResult[1])
            return False

    def UnmountRemotePCSharedFolder(self, mountFolder):
        """
        Unmount the remote PC shared folder on the MAC, take mountFolder as argument
        """
        # Skip if folder does not exist
        if not os.path.exists(mountFolder):
            self.logger.Debug('Skip unmount remote server, mount folder does not exist')
            return True

        cmd = 'umount -f ' + mountFolder
        mountResult = MACCommandExecuteWithBlock(cmd)
        # self.logger.Info("result is %s", mountResult)
        if mountResult[1] == '':
            self.logger.Info("Unmount Remote Server Passed")
            return True
        else:
            self.logger.Info('Unmount failed')
            self.logger.Info("result is %s", mountResult[1])
            return False

    def CopyFileFromRemote(self, server, remoteSharedFolder, sourceFile, localFolder, destFile=None, domain='MSSNIFFER', user='automation', passwd='test!123'):
        """
        Method to copy a file from remote host
        Takes server as a DNS name or IP address, remoteSharedFolder is remote shared name of the folder, sourceFile is the source file name
        localFolder where the destFile will be saved as required args
        Optional args are: destFile- destination file name,
        localFolder - local folder where you would like to save the file, domain - domain or workgroup name of the remote server
        user - remote server username, passwd - remote server password
        NOTES: Remote hosts require cygwin and cynwin SSH installed; remote hosts require have shared folder configured
        """
        self.localFolderForMount = "/Volumes/SnifferShared_remote"

        if not self.MountRemotePCSharedFolder(server, remoteSharedFolder, self.localFolderForMount, domain=domain, user=user, passwd=passwd):
            self.logger.Info("Failed to mount %s", remoteSharedFolder)
            return False

        sourceFileName = self.localFolderForMount + '/' + sourceFile

        if destFile is None:
            destFile = sourceFile

        localFileName = localFolder + '/' + destFile

        try:
            os.makedirs(localFolder)
        except OSError as dir_except:
            if dir_except.errno != errno.EEXIST:
                self.logger.Info("Failed to create folder to mount: %s", localFolder)
                return False

        try:
            shutil.copy(sourceFileName, localFileName)
        except:
            self.logger.Info("Failed to copy file, error = %s", sys.exc_info()[0])
            self.UnmountRemotePCSharedFolder(self.localFolderForMount)
            return False

        self.UnmountRemotePCSharedFolder(self.localFolderForMount)
        self.logger.Info("Copy file from remote passed")
        return True

    def _which(self, name):
        ''' Runs which to find an executable in system path and return string output '''
        pout = os.popen('which %s' % name)
        txt = pout.read()
        pout.close()
        return txt.strip()


class CaptureFilter(object):
    '''
    Seperate class to filter .pcap files and export the results to XML
    '''
    def __init__(self, server=None, user=None, password=None):
        '''
        optional remote args:
        'server' : '192.168.x.x'
        'user' : 'automation'
        'password' : 'test!123'
        '''
        self.logger = LogIt()
        self.server = server
        self.user = user
        self.password = password

    def RemotePcapToXml(self, pcapFile, tsharkFilter, xmlFile, packetCount=5):
        """
        Method to convert the PCAP file into XML format on a remote host
        Takes server as a DNS name or IP address, pcapFile is the pcap file need to get converted
        tsharkFilter is the filter for Tshark, xmlFile is the file name that is going to converted to
        optional args are packetCount: how many packets are going to get converted
        Update by Quint: if packetCount is 0, don't use it. Needed for certain filtering.
        username and password for the remote host
        NOTES: Remote hosts require tshark and setChannel.exe to be in the path
           Windows remote hosts require cygwin and cygwin SSH installed
        """
        self.logger.Info('Connecting and logging into %s' % self.server)
        self.sniffer = SSHControl(self.server, user=self.user, passwd=self.password)
        self.sniffer.Login()
        if packetCount == 0:
            cmd = "tshark -r " + pcapFile + " -T pdml -R '" + tsharkFilter + "' > " + xmlFile
        else:
            cmd = "tshark -r " + pcapFile + " -T pdml -c " + str(packetCount) + " -R '" + tsharkFilter + "' > " + xmlFile
        self.logger.Info("Try to conver pcap file to xml using command: %s", cmd)
        self.sniffer.Cmd(cmd)
        convertResult = self.sniffer.Cmd('echo $?')
        if int(convertResult.split('\\r\\n')[1]) == 0:
            self.sniffer.Logout()
            return True
        else:
            self.logger.Info('Warning: convert to xml failed')
            self.logger.Info("result is %s", convertResult)
            self.sniffer.Logout()
            return False

    def PcapToXml(self, pcapFile, tsharkFilter, xmlFile, packetCount=5):
        """
        Method to convert the PCAP file into XML format
        Takes pcapFile is the pcap file need to get converted
        tsharkFilter is the filter for Tshark, xmlFile is the file name that is going to converted to
        optional args are packetCount: how many packets are going to get converted
        Update by Quint: if packetCount is 0, don't use it. Needed for certain filtering.
        """
        createFile = False
        for _ in range(2):
            try:
                f = open(xmlFile, 'w')
                if f:
                    createFile = True
                    break;
            except:
                self.logger.Info("Failed to open file, error = %s", sys.exc_info()[0])
                sleep(1)
                continue;

        if not createFile:
            self.logger.Info("Failed to create xml file: %s", xmlFile)
            return False
        if packetCount == 0:
            cmd = "tshark -r " + pcapFile + " -T pdml -R '" + tsharkFilter + "' > " + xmlFile
        else:
            cmd = "tshark -r " + pcapFile + " -T pdml -c " + str(packetCount) + " -R '" + tsharkFilter + "' > " + xmlFile

        [out, error] = self._run(cmd)

        # Work-around for 'cut short in the middle of a packet' error.
        errLines = error.splitlines()
        for n in range(len(errLines) - 1, -1, -1):
            line = errLines[n]
            # Fix the xml file and remove the error
            if 'cut short in the middle of a packet' in line:
                self._FixPacketCutShortError(xmlFile)
                errLines.pop(n)
        error = '\n'.join(errLines)

        return not error

    def PcapMerge(self, infile1, infile2, outfile):
        createFile = False
        for _ in range(2):
            try:
                f = open(outfile, 'w')
                if f:
                    createFile = True
                    break;
            except:
                self.logger.Info("Failed to open file, error = %s", sys.exc_info()[0])
                sleep(1)
                continue;
        if not createFile:
            self.logger.Info("Failed to create xml file: %s", outfile)
            return False
        if os.path.isfile(infile1) and os.path.isfile(infile2):
            cmd = 'mergecap -w %s %s %s' % (outfile, infile1, infile2)
        else:
            self.logger.Info('Could not find %s or %s. Make sure the paths are correct' % (infile1, infile2))
            return False
        [out, error] = self._run(cmd)
        return not error

    def _run(self, command):
        """ Returns [stdout, stderr] from executed system command """
        self.logger.Info("Running: %s" % command)
        execute_command = subprocess.Popen(
                            [command],
                            stdout=subprocess.PIPE,
                            stderr=subprocess.PIPE,
                            shell=True
                        )
        # Create blocking call that waits till command is finished and returns it
        (out, error) = execute_command.communicate()

        # Known issue, parse should still be OK
        error = error.replace('OOPS: dissector table "sctp.ppi" doesn\'t exist\nProtocol being registered is "Datagram Transport Layer Security"\n', '')

        # Warn if parse failed
        if error:
            self.logger.Warning(error)
        return (out, error)

    def _FixPacketCutShortError(self, xmlFile):
        """
        Work-around for issue when 'kill -9 tshark' causes 'cut short in the middle of
        a packet' error (kill with interrupt does not seem to fix either,
        needs investigation).
        """
        # Fix is to add </pdml> to end of output .xml file
        fh = open(xmlFile, 'a')
        fh.write('\n</pdml>')
        fh.close()


class SnifferCard(object):
    """
    Holds data for Sniffer capture card
    """
    def __init__(self, snifferCardDict=None):
        super(SnifferCard, self).__init__()
        self.channel = '1'
        self.deviceName = '\\\.\\airpcap00'
        self.isAirPcap = True
        if snifferCardDict is not None:
            self.LoadConfig(snifferCardDict)

    def __str__(self):
        return '[Channel to config: %s, Device Name: %s, Is a AirPcap Card: %s]' % \
            (self.channel, self.deviceName, self.isAirPcap)

    def LoadConfig(self, cfg):
        """ Loads config from plist data """
        self.channel = cfg.get('channel', self.channel)
        self.deviceName = cfg.get('deviceName', self.deviceName)
        self.isAirPcap = cfg.get('isAirPcap', self.isAirPcap)

    def GetChannel(self):
        return self.channel

    def SetChannel(self, channel):
        self.channel = channel

    def GetDeviceName(self):
        return self.deviceName

    def SetDeviceName(self, name):
        self.deviceName = name

    def IsAirPcap(self):
        return self.isAirPcap


class SnifferBase(object):
    """ Base class for all Sniffers """

    def __init__(self, cfg={}):
        super(SnifferBase, self).__init__()
        self._logger = LogIt()
        self._ifname = 'en1'
        self._captureFile = 'capture.pcap'
        self._captureDir = 'Captures'
        self._snifferCards = []
        self._errors = []
        if cfg:
            self.LoadConfig(cfg)

    def __enter__(self):
        """ Support context (with statement) to clean sniffer """
        self.StartCapture()
        return self

    def __exit__(self, type, value, traceback):
        self.StopCapture()

    def LoadConfig(self, cfg):
        """
        Loads config from plist data:
<dict>
    <key>captureDir</key>
    <string>Captures</string>
    <key>snifferCardInfo</key>
    <array>
        <dict>
            <key>channel</key>
            <string>1</string>
            <key>deviceName</key>
            <string>\\.\airpcap01</string>
        </dict>
    </array>
</dict>
        """
        self._captureDir = cfg.get('captureDir', self._captureDir)
        for cardInfo in cfg.get('snifferCardInfo', []):
            self.AddSnifferCard(SnifferCard(cardInfo))

        # Log for debug
        self._logger.Debug('Sniffer:')
        self._logger.Debug('    captureDir       = %s' % self._captureDir)

    def GetInterface(self):
        return self._ifname

    def SetInterface(self, ifname):
        self._ifname = ifname

    def GetCaptureDir(self):
        return self._captureDir

    def SetCaptureDir(self, path):
        self._captureDir = path

    def GetCaptureFile(self):
        return self._captureFile

    def SetCaptureFile(self, fname):
        self._captureFile = fname

    def GetCaptureFullName(self):
        return os.path.join(self._captureDir, self._captureFile)

    def SetCaptureFullName(self, path):
        self._captureFile = os.path.basename(path)
        self._captureDir = os.path.dirname(path)

    def GetErrors(self):
        return self._errors

    def ClearErrors(self):
        self._errors = []

    def StartCapture(self):
        """ Starts packet capture until StopCapture """
        raise Exception('Override method not implemented')

    def StopCapture(self):
        """ Stops packet capture from StartCapture """
        raise Exception('Override method not implemented')

    def TimeCapture(self, durationSec):
        """ Run capture for given duration (in sec) """
        raise Exception('Override method not implemented')

    def AddSnifferCard(self, snifferCard):
        self._snifferCards.append(snifferCard)

    def GetSnifferCards(self):
        return self._snifferCards

    def ClearSnifferCards(self):
        self._snifferCards = []

    def _AddError(self, errMsg):
        self._logger.Warning(errMsg)
        self._errors.append(errMsg)

    def _GetSnifferCardInfoArray(self):
        """ Returns sniffer card info as array of dict, needed for SnifferTools """
        cardInfoList = []
        for card in self._snifferCards:
            cardInfoList.append(dict(
                deviceName=card.GetDeviceName(),
                channel=card.GetChannel(),
            ))
        return cardInfoList

    def SetSnifferCardChannel(self, channel, cardIndex=0):
        '''
        Set channel info in cardInfoList for given card index
        '''
        self._snifferCards[cardIndex].SetChannel(str(channel))


class SnifferRemoteBase(SnifferBase):
    """ Sniffer executed on a remote host """

    def __init__(self, cfg={}):
        super(SnifferRemoteBase, self).__init__()
        self._tools = SnifferTools()
        self._server = ''
        self._user = 'root'
        self._password = 'password'
        self._domain = 'DefaultDomain'
        self._remoteCaptureDir = 'Shared'
        self._remoteCaptureDirWin = 'C:/Users/%s/%s' % (self._user, self._remoteCaptureDir)
        self._mountDir = '/Volumes/DefaultMount'
        if cfg:
            self.LoadConfig(cfg)

    def __del__(self):
        pass #self._tools.UnmountRemotePCSharedFolder(self._mountDir)

    def LoadConfig(self, cfg):
        """
        Loads config from plist data:
<dict>
    <key>server</key>
    <string>172.16.11.4</string>
    <key>interface</key>
    <string>en2</string>
    <key>user</key>
    <string>wifiqa</string>
    <key>password</key>
    <string>wifiqa</string>
    <key>domain</key>
    <string>MSSNIFFER</string>
    <key>captureDir</key>
    <string>Captures</string>
    <key>remoteCaptureDir</key>
    <string>SnifferFile</string>
    <key>mountDir</key>
    <string>/Volumes/SnifferDest</string>
    <key>snifferCardInfo</key>
    <array>
        <dict>
            <key>channel</key>
            <string>1</string>
            <key>deviceName</key>
            <string>\\.\airpcap01</string>
        </dict>
    </array>
</dict>
        """
        self._ifname = cfg.get('interface', self._ifname)
        self._mountDir = cfg.get('captureDir', self._mountDir)
        self._server = cfg.get('server', self._server)
        self._user = cfg.get('user', self._user)
        self._password = cfg.get('password', self._password)
        self._domain = cfg.get('domain', 'DefaultDomain')
        self._remoteCaptureDir = cfg.get('remoteCaptureDir', self._remoteCaptureDir)
        self._remoteCaptureDirWin = 'C:/Users/%s/%s' % (self._user, self._remoteCaptureDir)
        self._mountDir = cfg.get('mountDir', self._mountDir)

        for cardInfo in cfg.get('snifferCardInfo', []):
            self.AddSnifferCard(SnifferCard(cardInfo))

        # Log input params for debug
        self._logger.Debug('RemoteSniffer:')
        self._logger.Debug('    server           = %s' % self._server)
        self._logger.Debug('    user             = %s' % self._user)
        self._logger.Debug('    password         = %s' % self._password)
        self._logger.Debug('    domain           = %s' % self._domain)
        self._logger.Debug('    captureDir       = %s' % self._captureDir)
        self._logger.Debug('    remoteCaptureDir = %s' % self._remoteCaptureDir)
        self._logger.Debug('    mountDir         = %s' % self._mountDir)

        # Log output directory on Win host
        self._logger.Debug('Remote capture dir (Win) = %s' % self._remoteCaptureDirWin)

    def GetServer(self):
        return self._server

    def SetServer(self, ipAddr):
        self._server = ipAddr

    def GetUser(self):
        return self._user

    def SetUser(self, name):
        self._user = name

    def GetPassword(self):
        return self._password

    def SetPassword(self, password):
        self._password = password

    def GetDomain(self):
        return self._domain

    def SetDomain(self, domain):
        self._domain = domain

    def GetRemoteCaptureDir(self):
        return self._remoteCaptureDir

    def SetRemoteCaptureDir(self, path):
        self._remoteCaptureDir = path

    def GetMountDir(self):
        return self._mountDir

    def SetMountDir(self, path):
        self._mountDir = path

    def _CreateMountDir(self):
        """ Create mount directory if needed """
        if not os.path.exists(self._captureDir):
            os.makedirs(self._captureDir)

        # Wait for dir to be created
        for i in range(10):
            if os.path.exists(self._captureDir):
                break
            time.sleep(1)

        # Create mount
        ret = self._tools.MountRemotePCSharedFolder(
            server=self._server,
            remoteSharedFolder=self._remoteCaptureDir,
            localFolderForMount=self._mountDir,
            domain=self._domain,
            user=self._user,
            passwd=self._password
        )
        if not ret:
            self._AddError('Failed to mount drive')

    def _CopyMountToLocal(self):
        """ Copies files in mount to local """
        mountCapFile = os.path.join(self._mountDir, self._captureFile)
        localCapFile = os.path.join(self._captureDir, self._captureFile)
        try:
            shutil.copy(mountCapFile, localCapFile)
        except Exception, e:
            self._AddError('Failed to copy file from %s to %s' % (mountCapFile, localCapFile))


class AirPcapSniffer(SnifferBase):
    """ Sniffer (wireshark) run on local host """

    def __init__(self, cfg={}):
        super(AirPcapSniffer, self).__init__()
        self._tools = SnifferTools()

    def StartCapture(self):
        """ Starts packet capture until StopCapture """
        self._logger.Info('Starting packet capture')
        if not os.path.exists(self._captureDir):
            os.makedirs(self._captureDir)

        # Uses 1 RF
        channel = None  # Any
        for card in self._snifferCards:
            channel = card.GetChannel()
            break
        self._tools.AirportSetChannel(channel)

        self._tools.SetOutputDir(self._captureDir)
        self._tools.WiresharkStart(self._captureFile, interface=self._ifname)

    def StopCapture(self):
        """ Stops packet capture from StartCapture """
        self._logger.Info('Stopping packet capture')
        self._tools.WiresharkStop()

    def TimeCapture(self, durationSec):
        """ Run capture for given duration (in sec) """
        if not os.path.exists(self._captureDir):
            os.makedirs(self._captureDir)
        self._tools.SetOutputDir(self._captureDir)
        self._tools.WiresharkTimeCapture(durationSec, self._captureFile,
                                            interface=self._ifname)


class AirPcapRemoteSniffer(SnifferRemoteBase):
    """
    AirPcapSniffer executed on a remote host

    Example config (load w/ plistlib):
<dict>
    <key>server</key>
    <string>172.16.11.4</string>
    <key>user</key>
    <string>wifiqa</string>
    <key>password</key>
    <string>wifiqa</string>
    <key>domain</key>
    <string>MSSNIFFER</string>
    <key>captureDir</key>
    <string>Captures</string>
    <key>remoteCaptureDir</key>
    <string>SnifferFile</string>
    <key>mountDir</key>
    <string>/Volumes/SnifferDest</string>
    <key>snifferCardInfo</key>
    <array>
        <dict>
            <key>channel</key>
            <string>1</string>
            <key>deviceName</key>
            <string>\\.\airpcap01</string>
        </dict>
    </array>
</dict>
	"""

    def StartCapture(self):
        self._logger.Info('Starting packet capture')

        # Setup mount dir
        self._CreateMountDir()

        # Determine if single- or multi-capture
        channels = []
        for card in self._snifferCards:
            if card.GetChannel() not in channels:
                channels.append(card.GetChannel())

        if len(channels) == 1:
            # Single-card capture
            self._tools.RemoteAirpcapSingleDeviceChannelConfig(
                server=self._server,
                snifferCard=self._GetSnifferCardInfoArray()[0],
                user=self._user,
                passwd=self._password
            )
            self._tools.RemoteTSharkStart(
                server=self._server,
                captureFileName=os.path.join(self._remoteCaptureDirWin, self._captureFile),
                interface='1',
                user=self._user,
                passwd=self._password
            )

        else:
            # Multi-card capture
            self._tools.RemoteAirpcapMultiDevicesChannelConfig(
                server=self._server,
                devicesArray=self._GetSnifferCardInfoArray(),
                user=self._user,
                passwd=self._password
            )
            ret = self._tools.RemoteTSharkMultiChannelAggregatorStart(
                server=self._server,
                captureFileName=os.path.join(self._remoteCaptureDirWin, self._captureFile),
                user=self._user,
                passwd=self._password,
                captureHeader='IEEE802_11_RADIO'
            )
            if not ret:
                self._AddError('Remote packet capture failed')

    def StopCapture(self):
        self._logger.Info('Stopping packet capture')
        self._tools.RemoteTSharkStop(self._server, user=self._user, passwd=self._password)

        # Copy capture to local mount dir
        self._CopyMountToLocal()

    def SetChannel(self, channel=0, snifferCardIndex=0):
        '''
        Set Channel of the Sniffer Card, if channel =0, it wont change the channel in the original config file
        '''
        if snifferCardIndex < 0 or snifferCardIndex >= len(self._GetSnifferCardInfoArray()):
            raise Exception("You want to config sniffer card num %d, number need to be between 0 and %d", snifferCardIndex, len(self._GetSnifferCardInfoArray()) - 1)
        if channel > 0:
            self.SetSnifferCardChannel(channel, cardIndex=snifferCardIndex)
        if not self._tools.RemoteAirpcapChannelConfig(self._server, self._GetSnifferCardInfoArray()[snifferCardIndex], user=self._user, passwd=self._password):
            self.logger.Warning("Failed to set sniffer card channel")
            return False
        return True

    def TimeCapture(self, durationSec, channel=0):
        # Setup mount dir
        self._CreateMountDir()

        # Start tshark
        if not self.SetChannel(channel=channel, snifferCardIndex=0):
            self._AddError("Failed to config Sniffer Card channel")
            raise Exception('Failed to configure Sniffer card channel!')
        self._tools.RemoteTSharkTimeCap(
            server=self._server,
            duration=durationSec,
            captureFileName=os.path.join(self._remoteCaptureDirWin, self._captureFile),
            interface='1',
            user=self._user,
            passwd=self._password
        )

        # Copy capture to local mount dir
        self._CopyMountToLocal()


    def StartSingleCardCapture(self, channel=0):
        # Setup mount dir
        self._CreateMountDir()

        # Start tshark
        if not self.SetChannel(channel=channel, snifferCardIndex=0):
            self._AddError("Failed to config Sniffer Card channel")
            raise Exception('Failed to configure Sniffer card channel!')
        self._tools.RemoteTSharkStart(
            server=self._server,
            captureFileName=os.path.join(self._remoteCaptureDirWin, self._captureFile),
            interface='1',
            user=self._user,
            passwd=self._password
        )

        # Copy capture to local mount dir
        self._CopyMountToLocal()

class AirportSniffer(SnifferBase):
    """ Sniffer (airport) run on local host """

    def __init__(self, cfg={}):
        super(AirportSniffer, self).__init__()
        self._procs = []

    def StartCapture(self):
        """ Starts packet capture until StopCapture """
        self._logger.Debug('Starting packet capture')

        # Create output dir
        if not os.path.exists(self._captureDir):
            os.makedirs(self._captureDir)

        # Create command
        for card in self._snifferCards:
            cmd = 'airport sniff %d' % card.GetChannel()
            self._logger.Debug('Running: %s' % cmd)

            # Start the process
            proc = subprocess.Popen(cmd, shell=True, stdout=subprocess.PIPE)
            self._procs.append(proc)

    def StopCapture(self):
        """ Stops packet capture from StartCapture """
        self._logger.Debug('Stopping packet capture')

        # Check if processes exist
        if not self._procs:
            self._logger.Warning('Did not kill airport, processes not running')
            return

        # Create outfile
        outfile = os.path.join(self._captureDir, self._captureFile)
        os.system('touch %s' % outfile)

        # Kill each process (may take some time)
        for n in range(len(self._procs) - 1, -1, -1):
            proc = self._procs[n]
            # self._proc.kill()  # this doesn't work too well
            os.kill(proc.pid, signal.SIGINT)

        # Merge all cap files
        capfiles = []
        for n in range(len(self._procs) - 1, -1, -1):
            proc = self._procs.pop(n)

            # Parse capture file, printed to stdout
            txt = proc.stdout.read()
            m = re.search('Session saved to (\S+)\.', txt, re.DOTALL)
            if not m:
                self._logger.Warning('Failed to parse for capture file, airport output is: %s' % repr(txt))
                break

            # Move tmp capture to capture dir
            tmpfile = m.group(1)
            capfiles.append(tmpfile)
            self._logger.Debug('Temp capture file: %s' % tmpfile)

        if not capfiles:
            self._logger.Warning('Failed to output capture file')
            return

        os.system('mergecap -w %s %s' % (outfile, ' '.join(capfiles)))
        self._logger.Debug('Output capture to %s' % outfile)

        # Remove tmp files
        for fname in capfiles:
            os.remove(fname)


    # def TimeCapture(self, durationSec):
    #    """ Run capture for given duration (in sec) """
    # TODO: to be implemented


def CreateSniffer(snifferType, remote=False):
    """
    Returns Sniffer instance from snifferType and isRemote option.
    Supported snifferType: airport, airpcap
    """
    logger = LogIt()
    snifferType = snifferType.lower()

    if (snifferType == 'airport') and (not remote):
        return AirportSniffer()
    elif (snifferType == 'airpcap') and (not remote):
        return AirPcapSniffer()
    elif (snifferType == 'airpcap') and remote:
        return AirPcapRemoteSniffer()

    # Sniffer not created
    logger.Debug('Sniffer not created snifferType=%s, remote=%s' % (snifferType, remote))
    return None


def CreateSnifferFromConfig(cfg):
    """ Returns Sniffer instance from plist config, otherwise None """
    sniffer = CreateSniffer(cfg.get('snifferType', '').lower(), bool('server' in cfg))
    if sniffer:
        sniffer.LoadConfig(cfg)
    return sniffer

#----------------------------------------------------------------------
#  MAIN
#----------------------------------------------------------------------

def _TestParser():
    # Inputs
    captureFile = '../../Tools/CaptureDiffExample/J1-capture.pcap'
    dutMacAddr = 'b8:17:c2:ad:87:9a'
    apMacAddr = '58:BC:27:92:54:51'
    filterStr = '(wlan.sa == b8:17:c2:ad:87:9a || wlan.sa == 58:BC:27:92:54:51) \
&& (wlan.da == b8:17:c2:ad:87:9a || wlan.da == 58:BC:27:92:54:51) \
&& (wlan.fc.type_subtype >= 0 && wlan.fc.type_subtype <= 15)'

    # Run .pcap to .xml
    parser = CaptureFilter()
    parser.PcapToXml(captureFile, filterStr, 'temp.xml', packetCount=0)


def _TestSniffer():
    """ Sanity test for Sniffer from config """

    # Use default.plist
    import common.ConfigurationInfo as info
    # cfg = info.ConfigurationInfo('../../Tests/Roaming/default.plist').TsharkServerConfig

    # Create sniffer from config
    # sniffer = CreateSnifferFromConfig(cfg)
    sniffer = AirPcapSniffer()
    sniffer.AddSnifferCard(SnifferCard(dict(channel=2)))
    sniffer.AddSnifferCard(SnifferCard(dict(channel=11)))
    if not sniffer:
        raise Exception('Failed to create sniffer!')

    sniffer.StartCapture()
    time.sleep(2)
    sniffer.StopCapture()

    # Same as above
    with sniffer:
        time.sleep(2)


def main():
    # Run all tests
    # _TestParser()
    _TestSniffer()


if __name__ == '__main__':
    main()

