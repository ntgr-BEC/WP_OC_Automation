"""
Created on May 11, 2012

@author: dhalavin
"""
import subprocess
import re
from IPy import IP

class InterfaceInfo(object):
    """
    Retrieve settings from an interface
    """
    def __init__(self, interfaceName, dut=False):
        """
        Returns dictionary of network details
        {   mac_address = "ba:da:dd:01:23:45" 
            IPv4 = {
                        Addresses = ("13.37.13.37");
                        BroadcastAddresses = ("13.37.13.255");
                        SubnetMasks = ("255.255.255.0");
                    };
            IPv6 =  {
                        Addresses = ("fe80::ce08:e0ff:fe05:ea3a","2620::1b00:1f08:ce08:e0ff:fe05:ea3a");
                        Flags = (0,64);
                        PrefixLength = (64,64);
                    };
            Link =  {
                        Active = 1;
                    };
        };
        """
        self.interfaceName = interfaceName
        ifconfigCommand = "/sbin/ifconfig " + interfaceName
        if dut == False:
            executeCommand = subprocess.Popen([ifconfigCommand], stdout=subprocess.PIPE, stderr=subprocess.PIPE, shell=True)
            self.output, __error = executeCommand.communicate()
        else:
            self.output = dut.getOS().Execute(ifconfigCommand).standardOutput
        self.ifSettings = dict()
        try:
            self.ifSettings["mac_address"] = str(re.findall("ether ([a-fA-F0-9\:]*) ", self.output)[0])
        except:
            self.ifSettings["mac_address"] = ""
        try:
            self.ifSettings["flags"] = re.findall('flags=(.*)<', self.output)[0]
        except:
            self.ifSettings["flags"] = ""
        try:
            self.ifSettings["index"] = re.findall('index (\d*)', self.output)[0]
        except:
            self.ifSettings["index"] = ""
        try:
            self.ifSettings["mtu"] = re.findall('mtu (\d*)', self.output)[0]
        except:
            self.ifSettings["mtu"] = ""
        try:
            self.ifSettings["media"] = re.findall('media: (.*)', self.output)[0]
        except:
            self.ifSettings["media"] = ""
        try:
            self.ifSettings["link quality"] = re.findall('link quality: (.*)', self.output)[0]
        except:
            self.ifSettings["link quality"] = ""
        try:
            self.ifSettings["scheduler"] = re.findall('scheduler: (.*)', self.output)[0]
        except:
            self.ifSettings["scheduler"] = ""
        try:
            self.ifSettings["link rate"] = re.findall('link rate: (.*)', self.output)[0]
        except:
            self.ifSettings["link rate"] = ""

        v4_addresses = re.findall("inet (.*)", self.output)
        self.ifSettings["IPv4"] = dict()
        self.ifSettings["IPv4"]["Addresses"] = list()
        self.ifSettings["IPv4"]["BroadcastAddresses"] = list()
        self.ifSettings["IPv4"]["SubnetMasks"] = list()
        for i in range(0, len(v4_addresses)):
            self.ifSettings["IPv4"]["Addresses"].append(str(v4_addresses[i].split(" ")[0]))
            self.ifSettings["IPv4"]["SubnetMasks"].append(str(IP(v4_addresses[i].split(" ")[2])))
            self.ifSettings["IPv4"]["BroadcastAddresses"].append(str(v4_addresses[i].split(" ")[4]))

        v6_addresses = re.findall("inet6 (.*) ", self.output)
        self.ifSettings["IPv6"] = dict()
        self.ifSettings["IPv6"]["Addresses"] = list()
        self.ifSettings["IPv6"]["PrefixLength"] = list()
        self.ifSettings["IPv6"]["scopeid"] = list()
        for i in range(0, len(v6_addresses)):
            self.ifSettings["IPv6"]["Addresses"].append(str(v6_addresses[i].split(" ")[0]))
            try:
                self.ifSettings["IPv6"]["PrefixLength"].append(str(v6_addresses[i].split(" ")[2]))
            except:
                self.ifSettings["IPv6"]["PrefixLength"].append("")
            try:
                scopeid = v6_addresses[i].split(" ")[4]
                if scopeid == "autoconf":
                    self.ifSettings["IPv6"]["scopeid"].append("")
                else:
                    self.ifSettings["IPv6"]["scopeid"].append(str(v6_addresses[i].split(" ")[4]))
            except:
                self.ifSettings["IPv6"]["scopeid"].append("")

    def MAC_Address(self):
        return self.ifSettings["mac_address"]

    def V6_Addresses(self):
        return self.ifSettings["IPv6"]["Addresses"]

    def V4_Addresses(self):
        return self.ifSettings["IPv4"]["Addresses"]

    def Interface_Settings_Dictionary(self):
        return self.ifSettings

    def Index(self):
        return self.ifSettings["index"]
