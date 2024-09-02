#!/usr/bin/env python2.3
#
#  WiFi_Associate_Disassociate.py
#  WirelessAutomation
#
#  This test will verify it was able to associate and disassociate with a network.
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
import time

class WiFi_Associate_Disassociate(WiFiTest):

	def __init__(self):
		self.setRequirements(dut=WiFiDevice)

	def setup(self):
		WiFiTest.setup(self, unlockDevices=False, getToHome= False)
		
		# Make sure we have atleast one access point.
		if len(Config.accesspoints) > 0:
			self.ap = WiFiNetwork(Config.accesspoints[0])
		else:
			raise TestFailError, TestFailError("No access points were found in the configuration file. This test requires atleast one access point.")
			
		# Check if we need to power on WiFi.
		if not self.dut.getWiFi().GetPower():
			self.dut.getWiFi().On()
			
	def run(self):
		pingTarget = "www.apple.com"
		# Try associate and disassociate.
		self.logger.Info("Attempting to associate and disassociate with the WiFi network %s.", self.ap.ssid)
		self.dut.getWiFi().Associate(self.ap)
		
		# Wait for the WiFi interface to go active.
		self.logger.Info("Waiting for the WiFi interface to go active.")
		if self.dut.getWiFi().WaitActive():
			self.logger.Pass("The WiFi interface is now active.")
		else:
			self.logger.Fail("The WiFi interface is not active.")
			
		# Lets make sure we associated with the right network.
		self.logger.Info("Checking if we've successfully associated with the right WiFi network.")
		currentNetwork = self.dut.getWiFi().GetNetwork()
		if currentNetwork == self.ap:
			self.logger.Pass("We successfully associated with the WiFi network.")
		elif currentNetwork == None:
			self.logger.Fail("We failed to associate with the WiFi network.")
		else:
			self.logger.Fail("The device is associated with the wrong network %s.", currentNetwork)
			
		# Ping pingTarget to make sure we're connected.
		self.logger.Info("Checking if we're online by pinging %s." %pingTarget)
		if self.dut.getOS().Ping(pingTarget) == True:
			self.logger.Pass("We successfully pinged %s." %pingTarget)
		else:
			self.logger.Fail("We failed to ping %s." %pingTarget)
			
		# Now its time to disassociate.
		self.dut.getWiFi().Disassociate()
		
		# Lets make sure we're no longer connected.
		self.logger.Info("Checking if we've successfully disassociated with the WiFi network.")
		if self.dut.getWiFi().WaitForNetwork(networkExpected=False):
			self.logger.Pass("We successfully disassociated from the WiFi network.")
		else:
			self.logger.Fail("We failed to disassociate. We're still connected to the WiFi network.")
	
	def teardown(self):			
		WiFiTest.teardown(self, unlockDevices=False)
		
if __name__ == "__main__":
	pass
