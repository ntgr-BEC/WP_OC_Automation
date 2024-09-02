#
#  WiFi_Reset_Network.py
#  WirelessAutomation
#
#  This test will reset network to factory settings.
#
#  Created by Anirban Maiti on 03/30/10.
#  Copyright (c) 2010 Apple Inc. All rights reserved.
#

from common.WirelessLogging import *
from common.WirelessTest import WirelessTest
from common.WirelessCommon import *
from common.WiFiCommon import WiFiNetwork


class WiFi_Reset_Network(WirelessTest):
	def __init__(self):
		WirelessTest.setRequirements(self, dut=WiFiDevice)

	def setup(self):
		WirelessTest.setup(self)
		
	def run(self):
		self.dut.getOS().SettingsResetNetwork()

	def teardown(self):
		WirelessTest.teardown(self)

if __name__ == "__main__":
	pass
