#!/usr/bin/env python2.3
#
#  WiFi_ScanForChannel.py
#  WirelessAutomation
#
#  This test scans for all WiFi networks and verifies an expected network exists.
#
#  Created by Shannon Ma on 8/4/09.
#  Copyright (c) 2009 Apple. All rights reserved.
#

from common.WirelessException import *
from common.WirelessTest import *
from common.WirelessCommon import *
from common.ConfigurationInfo import Config
from common.WiFiCommon import WiFiNetwork

class WiFi_ScanForChannel(WiFiTest):

	def __init__(self):
		self.setRequirements(dut=WiFiDevice)

	def setup(self):
		WiFiTest.setup(self)
		# Make sure we have atleast one access point.
		if len(Config.accesspoints) > 0:
			self.ap = WiFiNetwork(Config.accesspoints[0])
		else:
			raise TestFailError, TestFailError("No access points were found in the configuration file. This test requires atleast one access point.")
			
		# Check if we need to power on WiFi.
		if not self.dut.getWiFi().GetPower():
			self.dut.getWiFi().On()
			
	def run(self):
		if self.dut.build_train == "Sundance" or self.dut.build_train == "Brighton":
			self.logger.Pass("Not a Brighton or Sundance supported test.")
			return
		
		# Scan for all visible networks.
		# To find a specific hidden network, you can pass its SSID to Scan.
		self.logger.Info("Scanning for all WiFi networks.")
		networks = self.dut.getWiFi().Scan()
		
		# Check how many we found.
		networkCount = len(networks)
		if networkCount <= 0:
			raise TestFailError, TestFailError("We were unable to find any WiFi networks.")
		else:
			self.logger.Pass("Found %d WiFi networks.", networkCount)
		
		# Check if we found the network we're looking for.
		self.logger.Info("Attempting to search the discovered networks for %s.", self.ap.ssid);
		foundNetwork = None
		for network in networks:
			# We can't compare the entire network object since network might not have its username and password set.
			if network.ssid == self.ap.ssid:
				self.logger.Pass("Found the %s network!", self.ap.ssid)
				self.logger.Info(u"{0}".format(network))
				foundNetwork = network
				break
		
		if foundNetwork == None:
			raise TestFailError, TestFailError("The %s network was not found.", self.ap.ssid)
			
		self.logger.Info(u"Scanning for all networks on channel {0} using default dwell time".format(foundNetwork.channel))
		
		channelNetworks = self.dut.getWiFi().ScanChannel(foundNetwork.channel)
		channelNetworkCount = len(channelNetworks)
		if channelNetworkCount <=0:
			self.logger.Fail(u"Failed to find any networks on channel {0}".format(foundNetwork.channel))
		else:
			self.logger.Pass("Found networks in channel scan:")
			for network in channelNetworks:
				self.logger.Info(u"Found {0} on channel {1}".format(network.ssid, network.channel))
				
		
		dwell = 1000 # 1 second		
		self.logger.Info(u"Scanning for all networks on channel {0} using default dwell time of {1}".format(foundNetwork.channel, dwell))
		channelNetworks = self.dut.getWiFi().ScanChannel(foundNetwork.channel, dwellTime=dwell, timeout=2000)
		channelNetworkCount = len(channelNetworks)
		if channelNetworkCount <=0:
			self.logger.Fail(u"Failed to find any networks on channel {0}".format(foundNetwork.channel))
		else:
			self.logger.Pass("Found networks in channel scan:")
			for network in channelNetworks:
				self.logger.Info(u"Found {0} on channel {1}".format(network.ssid, network.channel))				
				
		dwell = 110
		channels = "1,6"	
		self.logger.Info(u"Scanning for all networks on channels: ({0}) using default dwell time of {1}".format(channels, dwell))
		channelNetworks = self.dut.getWiFi().ScanChannel(channels, dwellTime=dwell)
		channelNetworkCount = len(channelNetworks)
		if channelNetworkCount <=0:
			self.logger.Fail(u"Failed to find any networks on channels: ({0})".format(channels))
		else:
			self.logger.Pass("Found networks in channel scan:")
			for network in channelNetworks:
				self.logger.Info(u"Found {0} on channel {1}".format(network.ssid, network.channel))	

		dwell = 1000 # 1 second		
		self.logger.Info(u"Scanning for all networks on channel {0} using default dwell time of {1}".format(foundNetwork.channel, dwell))
		channelNetworks = self.dut.getWiFi().ScanChannel(foundNetwork.channel, dwellTime=dwell, timeout=2000)
		channelNetworkCount = len(channelNetworks)
		if channelNetworkCount <=0:
			self.logger.Fail(u"Failed to find any networks on channel {0}".format(foundNetwork.channel))
		else:
			self.logger.Pass("Found networks in channel scan:")
			for network in channelNetworks:
				self.logger.Info(u"Found {0} on channel {1}".format(network.ssid, network.channel))				
				
		dwell = 1000
		timeout = 500
		channels = "1,6"	
		self.logger.Info(u"Scanning for all networks on channels: ({0}) using default dwell time of {1} and timeout of {2}".format(channels, dwell, timeout))
		channelNetworks = self.dut.getWiFi().ScanChannel(channels, dwellTime=dwell, timeout=timeout)
		channelNetworkCount = len(channelNetworks)
		if channelNetworkCount <=0:
			self.logger.Fail(u"Failed to find any networks on channels: ({0})".format(channels))
		else:
			self.logger.Pass("Found networks in channel scan:")
			for network in channelNetworks:
				self.logger.Info(u"Found {0} on channel {1}".format(network.ssid, network.channel))	



	def teardown(self):
		# Clean-up by turning off WiFi.
		if self.dut.getWiFi().GetPower():
			self.dut.getWiFi().Off()
		WiFiTest.teardown(self)

if __name__ == "__main__":
	pass
