#!/usr/bin/env python2.3
#
#  WiFi_JoinDisconnectedReason.py
#  WirelessAutomation
#
#  This test will verify it was able to join and forget a network.
#
#  Created by Wayne Finlay on 5/9/2013.
#  Copyright (c) 2013 Apple. All rights reserved.
#

from common.WirelessException import *
from common.WirelessLogging import *
from common.WirelessTest import *
from common.WirelessCommon import *
from common.ConfigurationInfo import Config
from common.WiFiCommon import WiFiNetwork
from datetime import datetime
import time

class WiFi_JoinDisconnectedReason(WiFiTest):

	def OutputLastDisconnectedReason(self, reasonDict):
		if self.initialDisconnectedTime - reasonDict['TIMESTAMP'] <= 0:
			self.logger.Info("Last disconnected reason occured before this test began.\nTest Start: {0}\nDisconnected Reason Time: {1}".format(self.epocheTimeStart, reasonDict['TIMESTAMP']))
		else:
			self.logger.Pass("New last disconnected reason found!")
		self.logger.Info("Last DisconnectedReason occured at: {0} (epoche time)\nReasonCode: {1} - {2}\nSubReasonCode: {3} - {4}\nWas Involuntarily: {5}.".format( \
			reasonDict['TIMESTAMP'], \
			reasonDict['LINKDOWN_REASON_CODE'], \
			self.dut.getWiFi().LastDisconnectedReasonCodeDescription(reasonDict['LINKDOWN_REASON_CODE']), \
			reasonDict['LINKDOWN_SUBREASON_CODE'], \
			self.dut.getWiFi().LastDisconnectedSubReasonCodeDescription(reasonDict['LINKDOWN_SUBREASON_CODE']), \
			reasonDict['LINKDOWN_IS_INVOL']))

	def __init__(self):
		super(WiFi_JoinDisconnectedReason, self).__init__()
		self.setRequirements(dut=WiFiDevice)

	def setup(self):
		WiFiTest.setup(self)
		self.initialDisconnectedTime = 0
		self.initalReasonDict = None
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
		#  Guards around LastDisconnected reason code
		if ( int(self.dut._cam_device.productVersion().split(".")[0]) < 7 ):
			self.logger.Info("Device has an older than Innsbruck build. Test requires Innsbruck or greater to test LastDisconncected Reason.")	
		else:
			# Get the current time so we know if the last disconnected reason is
			# associated with the network we're about to join.
			self.epocheTimeStart = time.mktime(time.localtime())
			self.initalReasonDict = self.dut.getWiFi().GetLastDisconnectedReason()
			if self.initalReasonDict is None:
				self.logger.Info("No previous LastDisconnectedReason found.")
			else:
				self.initialDisconnectedTime = self.initalReasonDict['TIMESTAMP']
		
		# Try join and forget the network.
		self.logger.Info("Attempting to join and get disconnected from the WiFi network %s.", self.ap.ssid)
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
			
		# Ping pingTarget to make sure we're connected.
		self.logger.Info("Checking if we're online by pinging %s." %pingTarget)
		if self.dut.getOS().Ping(pingTarget) == True:
			self.logger.Pass("We successfully pinged %s." %pingTarget)
		else:
			self.logger.Fail("We failed to ping %s." %pingTarget)

		#  Guards around LastDisconnected reason code
		if ( int(self.dut._cam_device.productVersion().split(".")[0]) < 7 ):
			self.logger.Info("Device has an older than Innsbruck build. Test requires Innsbruck or greater to test LastDisconncected Reason.")
		else:	
			self.logger.Info("Do something to cause a disconnected reason - powering radio off then on...")
			self.logger.Info("radio off...")
			self.dut.getWiFi().Off()
			sleep(10)
			self.logger.Info("radio on...")
			self.dut.getWiFi().On()
			sleep(20)
			self.logger.Info("Looking for the last disconnected reason.")
			reasonDict = self.dut.getWiFi().GetLastDisconnectedReason()
			if reasonDict is None:
				self.logger.Info("There is no last disconnected reason available.")
			else:
				self.OutputLastDisconnectedReason(reasonDict)
	
			self.logger.Info("Retreaving last disconnected reason history since monitorig started.")
			reasonArray = self.dut.getWiFi().GetLastDisconnectedReasonHistory()
			self.logger.Info("Last Disconnected Reason monitoring started {0} seconds ago.".format(self.epocheTimeStart - reasonArray[0]))
			self.logger.Info("Reason Array returned {0} elements.".format(len(reasonArray)))
			if len(reasonArray) > 1:
				for index in range(1, len(reasonArray)):
					self.OutputLastDisconnectedReason(reasonArray[index])
			else:
				self.logger.Info("There is no last disconnected reason available.")
		
				
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
