#!/usr/bin/env python2.3
#
#  WiFi_Join_Forget.py
#  WirelessAutomation
#
#  This test will verify it was able to join and forget a network.
#
#  Created by Shannon Ma on 8/4/09.
#  Copyright (c) 2009 Apple. All rights reserved.
#

from common.WirelessException import *
from common.WirelessLogging import *
from common.WirelessTest import *
from common.WirelessCommon import *
from common.ConfigurationInfo import Config
from common.WiFiCommon import WiFiNetwork

class WiFi_Join_Forget(WiFiTest):

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
			
		# Ping www.google.com to make sure we're connected.
		self.logger.Info("Checking if we're online by pinging www.google.com.")
		if self.dut.getOS().Ping("www.google.com") == True:
			self.logger.Pass("We successfully pinged www.google.com.")
		else:
			self.logger.Fail("We failed to ping www.google.com.")
				
		# Now its time to disassociate.
		self.dut.getWiFi().Forget(joinedNetwork)

		# Lets make sure we're no longer connected.
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
