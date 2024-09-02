#
#  WiFi_Power_Toggle.py
#  WirelessAutomation
#
#  This test will verify it was able to turn on and off WiFi 20 times.
#
#  Created by Nicolas Melo on 6/18/09.
#  Copyright (c) 2009 Apple. All rights reserved.
#

from common.WirelessLogging import *
from common.WirelessTest import *
from common.WirelessCommon import *

class WiFi_Power_Toggle(WiFiTest):

	def __init__(self):
		self.setRequirements(dut=WiFiDevice)

	def setup(self):
		WiFiTest.setup(self)

	def run(self):
		# Turn WiFi on and off numerous times.
		self.logger.Info("Attempting to toggle WiFi power on and off 20 times.")
		for i in range(20):
			self.dut.getWiFi().On()
			self.dut.getWiFi().Off()

	def teardown(self):
		# Clean-up by making sure WiFi is turned off.
		if self.dut.getWiFi().GetPower():
			self.dut.getWiFi().Off()
		WiFiTest.teardown(self)

if __name__ == "__main__":
	pass
