#
#  WiFi_Eapolcleint_Logging.py
#  WirelessAutomation
#
#  This test will enable the eapolclient logging and get the eapolclient.eno.log file
#  Demonstrates using eapolclient logging with "WiFi_Join_Forget" test
#
#  Created by Anirban Maiti on 04/05/10.
#  Copyright (c) 2010 Apple Inc. All rights reserved.
#

from common.WirelessException import *
from common.WirelessLogging import *
from common.WirelessTest import *
from common.WirelessCommon import *
from common.ConfigurationInfo import Config
from common.WiFiCommon import WiFiNetwork


class WiFi_Eapolclient_Logging(WiFiTest):
	def __init__(self):
		WiFiTest.setRequirements(self, dut=WiFiDevice)

	def setup(self):
		WiFiTest.setup(self)
		
		self.logger.Info("Enabling and cleaning up the eapolclient logging.")
		# ********** Enable and Clean Eapolcleint Logging ********
		# As part of the setup process, enable eapolclient logging
		self.dut.getWiFi().EnableEapolclientLogging()
		# Clear the previous logs
		self.dut.getWiFi().EapolclientLogCleanUp()
		# ********************************************************
		
		# Setup for the Join-Forget Test - Copied from WiFi_Join_Forget
		# Make sure we have atleast one access point.
		if len(Config.accesspoints) > 0:
			self.ap = WiFiNetwork(Config.accesspoints[0])
		else:
			raise TestFailError, TestFailError("No access points were found in the configuration file. This test requires atleast one access point.")
			
		# Check if we need to power on WiFi.
		if not self.dut.getWiFi().GetPower():
			self.dut.getWiFi().On()
		
	def run(self):
		# Forget all networks.
		self.dut.getWiFi().ForgetAll()

		# Try join and forget the network.
		self.logger.Info("Attempting to join and forget the WiFi network %s.", self.ap.ssid)
		joinedNetwork = self.dut.getWiFi().Join(self.ap)
			
		# Wait for the WiFi interface to go active.
		self.logger.Info("Waiting for the WiFi interface to go active.")
		if self.dut.getWiFi().WaitActive():
			self.logger.Info("The WiFi interface is now active.")
		else:
			self.logger.Info("The WiFi interface is not active.")
			
		# Lets make sure we associated with the right network.
		self.logger.Info("Checking if we've successfully joined the right WiFi network.")
		currentNetwork = self.dut.getWiFi().GetNetwork()
		if currentNetwork == self.ap:
			self.logger.Info("We successfully joined the WiFi network.")
		elif currentNetwork == None:
			self.logger.Info("We failed to join the WiFi network.")
		else:
			self.logger.Info("The device joined the wrong network %s.", currentNetwork)
			
		# Ping www.google.com to make sure we're connected.
		self.logger.Info("Checking if we're online by pinging www.google.com.")
		if self.dut.getOS().Ping("www.google.com") == True:
			self.logger.Info("We successfully pinged www.google.com.")
		else:
			self.logger.Info("We failed to ping www.google.com.")
				
		# Now its time to disassociate.
		self.dut.getWiFi().Forget(joinedNetwork)

		# Lets make sure we're no longer connected.
		self.logger.Info("Checking if we've successfully forgot the WiFi network.")
		currentNetwork = self.dut.getWiFi().GetNetwork()
		if currentNetwork == None:
			self.logger.Info("We successfully forgot the WiFi network.")
		else:
			self.logger.Info("We failed to forget the WiFi network. We're still connected to the WiFi network %s.", currentNetwork.ssid)
		
	def teardown(self):
		self.logger.Info("Collect and disable eapolclient logging.")
		# ********** Disable and Collect Eapolcleint Log ********
		# save the log files via logger service
		WiFiTest.saveEapolclientLog(self)
		# Disable the eapolclient logging
		self.dut.getWiFi().DisableEapolclientLogging()
		# ********************************************************
		
		
		# teardown for the Join-Forget Test
		# Clean-up by turning WiFi off...
		if self.dut.getWiFi().GetPower():
			self.dut.getWiFi().Off()
			
		# And forget all networks.
		self.dut.getWiFi().ForgetAll()

		WirelessTest.teardown(self)

if __name__ == "__main__":
	pass
