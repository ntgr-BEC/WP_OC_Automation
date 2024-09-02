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

class WiFi_Disassociate(WiFiTest):

	def __init__(self):
		self.setRequirements(dut=WiFiDevice)
		
	def setup(self):
		WiFiTest.setup(self, unlockDevices=False, getToHome= False)
	
	def run(self):
		self.dut.getWiFi().Disassociate()
		
		# Lets make sure we're no longer connected.
		self.logger.Info("Checking if we've successfully disassociated with the WiFi network.")
		if self.dut.getWiFi().WaitForNetwork(networkExpected=False):
			self.logger.Pass("We successfully disassociated from the WiFi network.")
		else:
			self.logger.Fail("We failed to disassociate. We're still connected to the WiFi network.")
	
	def teardown(self):
		# Clean-up by turning WiFi off...
		if self.dut.getWiFi().GetPower():
			self.dut.getWiFi().Off()
			
		# And forget all networks.
		self.dut.getWiFi().ForgetAll()
		WiFiTest.teardown(self, unlockDevices=False)
		
if __name__ == "__main__":
	pass
