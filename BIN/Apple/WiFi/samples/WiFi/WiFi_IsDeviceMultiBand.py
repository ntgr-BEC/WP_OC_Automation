#!/usr/bin/env python2.3
#
#  WiFi_IsDeviceMultiBand.py
#  WirelessAutomation
#
#  This test verifies WiFi exists on the device.
#
#  Created by Wayne Finlay on 05/16/2013.
#  Copyright (c) 2013 Apple. All rights reserved.
#

from common.WirelessException import *
from common.WirelessTest import *
from common.WirelessCommon import *

class WiFi_IsDeviceMultiBand(WiFiTest):
	def __init__(self):
		super(WiFi_IsDeviceMultiBand, self).__init__()
		self.setRequirements(dut=WiFiDevice)

	def setup(self):
		WiFiTest.setup(self)
	
	def run(self):
		if ( int(self.dut._cam_device.productVersion().split(".")[0]) < 7 ):
			self.logger.Info("Device has an older than Innsbruck build. Test requires Innsbruck or greater.")
			return		
	
		if self.dut.wifi.HasWiFi():
			self.logger.Pass("The device has WiFi.")
			self.logger.Pass("Device supports multi-band: {0}.".format(["No","Yes"][self.dut.wifi.IsDeviceMultiBand()]))
		else:
			raise TestFailError, TestFailError("The device does not have WiFi.")
	
	def teardown(self):
		WiFiTest.teardown(self)

if __name__ == "__main__":
	pass
