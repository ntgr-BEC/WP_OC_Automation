#!/usr/bin/env python2.3
#
#  WiFi_RoamingConsortiumQuery.py
#  WirelessAutomation
#
#  This test verifies GAS query for RoamingConsortiums.
#
#  Created by Wayne Finlay on 04/23/2013.
#  Copyright (c) 2013 Apple. All rights reserved.
#

from common.WirelessException import *
from common.WirelessTest import *
from common.WirelessCommon import *
from common.ConfigurationInfo import Config

class WiFi_RoamingConsortiumQuery(WiFiTest):
	def __init__(self):
		super(WiFi_RoamingConsortiumQuery, self).__init__()
		self.setRequirements(dut=WiFiDevice)

	def setup(self):
		WiFiTest.setup(self)
		
		if len(Config.GAS) > 0:
			self.hs = Config.GAS[0]
			if 'RoamingConsortiumList' in self.hs and 'bssid' in self.hs and 'channel' in self.hs:
				self.logger.Info("Verified config file has needed test data.")
			else:
				self.logger.Fail("Passed-in config file does not contain the needed entries for this test.")
		else:
			self.logger.Fail("No GAS configuration found in provided config file.")	
					
		# Check if we need to power on WiFi.
		if not self.dut.getWiFi().GetPower():
			self.dut.getWiFi().On()
	
	def run(self):
		if ( int(self.dut._cam_device.productVersion().split(".")[0]) < 7 ):
			self.logger.Info("Device has an older than Innsbruck build. Test requires Innsbruck or greater.")
			return	

		if self.dut.getWiFi().HasWiFi():
			self.logger.Pass("The device has WiFi.")
		else:
			raise TestFailError, TestFailError("The device does not have WiFi.")
	
		self.logger.Info("Initiating GAS scan for RoamingConsortium")
		result = self.dut.getWiFi().GetRoamingConsortium(self.hs.bssid, self.hs.channel)
		if result:
			self.logger.Pass(u"GAS Scan on {0} using channel {1} for RoamingConsortium: {2}:\n".format(self.hs.bssid, self.hs.channel, result))
			# Validate against config
			for element in self.hs.RoamingConsortiumList:
				if element in result:
					self.logger.Pass(u"Found expected element {} in GAS query result.".format(element))
				else:
					self.logger.Fail(u"Could not find expected element {} in GAS query result.".format(element))
		else:
			self.logger.Fail(u"GAS Scan on {0} using channel {1} for RoamingConsortium failed.".format(self.hs.bssid, self.hs.channel))
				
	def teardown(self):
		WiFiTest.teardown(self)

if __name__ == "__main__":
	pass
