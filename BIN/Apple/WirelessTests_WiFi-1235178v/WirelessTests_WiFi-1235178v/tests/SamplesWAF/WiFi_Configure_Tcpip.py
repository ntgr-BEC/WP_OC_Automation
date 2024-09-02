#!/usr/bin/env python2.3
#
#  WiFi_Configure_Tcpip.py
#  WirelessAutomation
#
#  This test will attempt to join a network and set its WiFi adapter to use some static TCP/IP settings.
#  It will then configure the WiFi adapter to use DHCP and verifies this succeeded.
#
#  Created by Shannon Ma on 9/8/09.
#  Copyright (c) 2009 Apple. All rights reserved.
#

from common.WirelessException import *
from common.WirelessLogging import *
from common.WirelessTest import *
from common.WirelessCommon import *
from common.ConfigurationInfo import Config
from common.WiFiCommon import TcpipSettings
from common.WiFiCommon import WiFiNetwork

import time

class WiFi_Configure_Tcpip(WiFiTest):

	def __init__(self):
		# Make sure we have atleast one set of TCP/IP settings.
		if len(Config.tcpipsettings) > 0:
			self.tcpip = TcpipSettings(Config.tcpipsettings[0])
		else:
			raise TestFailError, TestFailError("No TCP/IP settings were found in the configuration file. This test requires at least one set of TCP/IP settings.")
			
		self.setRequirements(dut=WiFiDevice, ip_lock=GenericResource.classForResource(self.tcpip.ipAddress))

	def setup(self):
		WiFiTest.setup(self)
		# Make sure we have atleast one access point.
		if len(Config.accesspoints) > 0:
			self.ap = WiFiNetwork(Config.accesspoints[1]) # hidden-k10a has 10.0.1.210 - 213 set aside for this kind of testing.
		else:
			raise TestFailError, TestFailError("No access points were found in the configuration file. This test requires at least one access point.")
		
		# Check if we need to power on WiFi.
		if not self.dut.getWiFi().GetPower():
			self.dut.getWiFi().On()
			
	def run(self):
		# Try join and forget the network.
		self.logger.Info("Attempting to join and forget the WiFi network %s.", self.ap.ssid)
		joinedNetwork = self.dut.getWiFi().Join(self.ap)
			
		# Wait for the WiFi interface to go active.
		self.logger.Info("Waiting for the WiFi interface to go active.")
		if self.dut.getWiFi().WaitActive():
			self.logger.Pass("The WiFi interface is now active.")
		else:
			self.logger.Fail("The WiFi interface is not active.")
			
		# Lets make sure we associated with the right network.
		self.logger.Info("Checking if we've successfully joined the right WiFi network.")
		currentNetwork = self.dut.getWiFi().GetNetwork()
		if currentNetwork == self.ap:
			self.logger.Pass("We successfully joined the WiFi network.")
		elif currentNetwork == None:
			self.logger.Fail("We failed to join the WiFi network.")
		else:
			self.logger.Fail("The device joined the wrong network %s.", currentNetwork)

		# First let's try give the phone a static IP.
		self.dut.getWiFi().SetManual(self.ap, self.tcpip)
		# Wait for potentially a really long time (3 minutes). Since this is a sample test, there's probably bogus IP information in the config.
		# With a lack of internet connection on an open WiFi network, WiFi won't become the primary interface until captive decides it is good and ready to give up the fight.
		for i in range(3*60):
			currentIP = self.dut.getWiFi().GetTcpipSettings()
			if currentIP and currentIP == self.tcpip:
				self.logger.Pass("The static TCP/IP setting assignment was successful.")
				break
			time.sleep(1)
		else:
			self.logger.Fail("The static TCP/IP setting assignment failed. The device's IP address is %s.", currentIP)

		# Ping www.apple.com to make sure we're connected.
		self.logger.Info("Checking if we're online by pinging www.apple.com.")
		if self.dut.getOS().Ping("www.apple.com") == True:
			self.logger.Pass("We successfully pinged www.apple.com.")
		else:
			self.logger.Fail("We failed to ping www.apple.com.")

		# Then let's try set it back to DHCP.
		self.dut.getWiFi().SetDhcp(self.ap)

		# Check if the device got its IP from DHCP.
		self.logger.Info("Waiting for the device to get its WiFi TCP/IP settings from DHCP.")
		# Wait up to a minute for the settings to become active
		for i in range(60):
			currentIP = self.dut.getWiFi().GetTcpipSettings()
			# The settings be different than the manually set settings
			if currentIP and currentIP != self.tcpip:
				self.logger.Pass("The device successfully got its WiFi TCP/IP settings from DHCP.")
				break
			time.sleep(1)
		else:
			self.logger.Fail("The device failed to get its WiFi TCP/IP settings from DHCP. They still match the static settings we assigned previously.")

		# Ping www.apple.com to make sure we're connected.
		self.logger.Info("Checking if we're online by pinging www.apple.com.")
		if self.dut.getOS().Ping("www.apple.com") == True:
			self.logger.Pass("We successfully pinged www.apple.com.")
		else:
			self.logger.Fail("We failed to ping www.apple.com.")
		
		# Now it's time to disassociate.
		self.dut.getWiFi().Forget(joinedNetwork)

		# Let's make sure we're no longer connected.
		self.logger.Info("Checking if we've successfully forgot the WiFi network.")
		currentNetwork = self.dut.getWiFi().GetNetwork()
		if currentNetwork == None:
			self.logger.Pass("We successfully forgot the WiFi network.")
		else:
			self.logger.Fail("We failed to forget the WiFi network. We're still connected to the WiFi network %s.", currentNetwork.ssid)
			
	def teardown(self):
		# Clean-up by turning WiFi off...
		if self.dut.getWiFi().GetPower():
			self.dut.getWiFi().Off()
			
		# And forget all networks.
		self.dut.getWiFi().ForgetAll()
		WiFiTest.teardown(self)
			
if __name__ == "__main__":
	pass
