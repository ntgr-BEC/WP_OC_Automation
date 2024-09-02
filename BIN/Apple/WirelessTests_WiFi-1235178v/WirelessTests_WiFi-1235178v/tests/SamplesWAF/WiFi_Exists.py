#!/usr/bin/env python2.3
#
#  WiFi_Exists.py
#  WirelessAutomation
#
#  This test verifies WiFi exists on the device.
#
#  Created by Shannon Ma on 09/21/10.
#  Copyright (c) 2010 Apple. All rights reserved.
#

from common.WirelessException import *
from common.WirelessTest import *
from common.WirelessCommon import *

class WiFi_Exists(WiFiTest):
	def __init__(self):
		self.setRequirements(dut=WiFiDevice)

	def setup(self):
		WiFiTest.setup(self)
	
	def run(self):
		if self.dut.getWiFi().HasWiFi():
			self.logger.Pass("The device has WiFi.")
		else:
			raise TestFailError, TestFailError("The device does not have WiFi.")
	
	def teardown(self):
		WiFiTest.teardown(self)

if __name__ == "__main__":
	pass
