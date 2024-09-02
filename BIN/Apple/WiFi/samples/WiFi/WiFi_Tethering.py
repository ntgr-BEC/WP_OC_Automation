#!/usr/bin/env python2.3
#
#  WiFi_Tethering.py
#  WirelessAutomation
#
#  This test enables WiFi Tethering on one device and associates the softAP from another device
#
#  Created by Anirban Maiti on 3/24/11.
#  Copyright (c) 2011 Apple. All rights reserved.
#

from common.WirelessException import *
from common.WirelessTest import *
from common.WirelessCommon import *
from common.ConfigurationInfo import Config
from common.WiFiCommon import WiFiNetwork

class WiFi_Tethering(WiFiTest):
	def __init__(self):
		self.setRequirements(dut=DataDevice, dut2=WiFiDevice)

	def setup(self):
		WiFiTest.setup(self, unlockDevices=False, getToHome=False)

	def run(self):
		if self.dut.hardware == 'N88':
			self.logger.Pass("WiFi tethering is not supported on n88.")
			return

		# Authenticate with carrier.
		self.dut.getOS().AuthenticateTethering()

		# Turn on the Airplane Mode
		self.dut2.TurnOnAirplaneMode()

		# Disable USB internet sharing
		self.dut.getOS().DisableUSBEthernetSharing()

		# Enable Internet tethering with WiFi only
		self.dut.getOS().TurnTetheringOn(enableBT=False, enableWiFi=True)

		misInterfaceName = self.dut.getWiFi().GetWiFiTetheringInterfaceName()
		# Wait for the WiFi tethering interface to go active.
		self.logger.Info("Waiting for the WiFi tethering interface to go active.")
		if self.dut.getWiFi().WaitActive(interfaceName=misInterfaceName):
			self.logger.Pass("The WiFi tethering interface is now active.")
		else:
			self.logger.Fail("The WiFi tethering interface is not active.")

		self.logger.Info("IF name is %s", misInterfaceName)

		# Get SoftAP SSID
		softapSSID = self.dut.getWiFi().GetWiFiTetheringSSID()
		self.logger.Info("WiFi tethering SSID is %s.", softapSSID)

		# Scan for all visible networks from DUT2.
		self.logger.Info("Scanning for all WiFi networks.")
		self.dut2.getWiFi().On()
		networks = self.dut2.getWiFi().Scan()

		# Check if we found the network we're looking for.
		foundNetwork = None
		for network in networks:
			# We can't compare the entire network object since network might not have its username and password set.
			if network.ssid == softapSSID:
				foundNetwork = network
				self.logger.Pass("Found. %s", foundNetwork)
				break

		if foundNetwork == None:
			raise TestFailError, TestFailError("The %s network was not found.", softapSSID)

		# Get the Tethering WiFi password
		foundNetwork.requiresPassword = True
		foundNetwork.password = self.dut.getWiFi().GetWiFiTetheringPassword()
		
		joinedNetwork = self.dut2.getWiFi().Join(foundNetwork)

		# Wait for the WiFi interface to go active.
		self.logger.Info("Waiting for the WiFi interface to go active.")
		if self.dut2.getWiFi().WaitActive():
			self.logger.Pass("The WiFi interface is now active.")
		else:
			self.logger.Fail("The WiFi interface is not active.")
			
		# Lets make sure we associated with the right network.
		self.logger.Info("Checking if we've successfully joined the right WiFi network.")
		currentNetwork = self.dut2.getWiFi().GetNetwork()
		if currentNetwork == foundNetwork:
			self.logger.Pass("We successfully joined the WiFi network.")
		elif currentNetwork == None:
			self.logger.Fail("We failed to join the WiFi network.")
		else:
			self.logger.Fail("The device joined the wrong network %s.", currentNetwork)
			
		# Ping pingTarget to make sure we're connected.
		self.logger.Info("Checking if we're online by pinging %s." %pingTarget)
		if self.dut2.getOS().Ping(pingTarget) == True:
			self.logger.Pass("We successfully pinged %s." %pingTarget)
		else:
			self.logger.Fail("We failed to ping %s." %pingTarget)
		
		self.logger.Info("Tethered host count: %d.",self.dut.getOS().GetTetheredHostCount())
		self.dut.getOS().TurnTetheringOff()
		
		
	def teardown(self):
		# Clean-up by turning off tethering
		if self.dut.getOS().IsTetheringOn():
			self.dut.getOS().TurnTetheringOff()
			
		# Clean-up by turning off WiFi.
		if self.dut.getWiFi().GetPower():
			self.dut.getWiFi().Off()
		WiFiTest.teardown(self, unlockDevices=False)

if __name__ == "__main__":
	pass
