#!/bin/sh
################################################################################   
#
#  File Name			: lib-udp_relay-support.tcl
#
#  Description			:
#         This TCL contains functions commonly used for udp relay.
#
#  Revision History 	:
#        Date          Programmer        Description
#        -----------------------------------------------------------------------
#        Dec 19 2012   Tony              Created
#
#
#
#
################################################################################


#*******************************************************************************
#
#  Function Name	: _getUdpRelayServerAddr
#
#  Description    : This function is called to get the udp relay's server address from 
#	                the global ntgr_UdpRelayList
#         
#  Usage          : _getUdpRelayServerAddr <switch>
# 
#*******************************************************************************
proc _getUdpRelayServerAddr {switch} {
	global ntgr_UdpRelayList
  set result ""
	keylget ntgr_UdpRelayList($switch) SERVER_ADDR result
	return $result
}

#*******************************************************************************
#
#  Function Name	: _getUdpRelayUdpPortType
#
#  Description    : This function is called to get the udp relay's udp port type from 
#	                the global ntgr_UdpRelayList
#         
#  Usage          : _getUdpRelayUdpPortType <switch>
# 
#*******************************************************************************
proc _getUdpRelayUdpPortType {switch} {
	global ntgr_UdpRelayList
  set result ""
	keylget ntgr_UdpRelayList($switch) UDPPORT_TYPE result
	return $result
}

#*******************************************************************************
#
#  Function Name	: _getUdpRelayUdpPortValue
#
#  Description    : This function is called to get the udp relay's udp port value from 
#	                the global ntgr_UdpRelayList
#         
#  Usage          : _getUdpRelayUdpPortValue <switch>
# 
#*******************************************************************************
proc _getUdpRelayUdpPortValue {switch} {
	global ntgr_UdpRelayList
  set result ""
	keylget ntgr_UdpRelayList($switch) UDPPORT_VALUE result
	return $result
} 

#*******************************************************************************
#
#  Function Name	: _getUdpRelayInterface
#
#  Description    : This function is called to get interface from 
#					the global ntgr_UdpRelayListOnInterface
#         
#  Usage          : _getUdpRelayInterface <switch>
# 
#*******************************************************************************
proc _getUdpRelayInterface {switch} {
	global ntgr_UdpRelayListOnInterface
  	set result ""
	keylget ntgr_UdpRelayListOnInterface($switch) INTERFACE result
	return $result
} 

#*******************************************************************************
#
#  Function Name	: _getUdpRelayServerAddrOnInterface
#
#  Description    : This function is called to get interface from 
#					the global _getUdpRelayServerAddrOnInterface
#         
#  Usage          : _getUdpRelayServerAddrOnInterface <switch>
# 
#*******************************************************************************
proc _getUdpRelayServerAddrOnInterface {switch} {
	global ntgr_UdpRelayListOnInterface
  	set result ""
	keylget ntgr_UdpRelayListOnInterface($switch) SERVER_ADDR result
	return $result
} 

#*******************************************************************************
#
#  Function Name	: _getUdpRelayInterface
#
#  Description    : This function is called to get interface from 
#					the global ntgr_UdpRelayListByInterface
#         
#  Usage          : ntgr_UdpRelayListByInterface <switch>
# 
#*******************************************************************************
proc _getUdpRelayUdpPortTypeOnInterface {switch} {
	global ntgr_UdpRelayListOnInterface
  	set result ""
	keylget ntgr_UdpRelayListOnInterface($switch) UDPPORT_TYPE result
	return $result
} 

#*******************************************************************************
#
#  Function Name	: _getUdpRelayUdpPortValueOnInterface
#
#  Description    : This function is called to get interface from 
#					the global ntgr_UdpRelayListOnInterface
#         
#  Usage          : _getUdpRelayUdpPortValueOnInterface <switch>
# 
#*******************************************************************************
proc _getUdpRelayUdpPortValueOnInterface {switch} {
	global ntgr_UdpRelayListOnInterface
  	set result ""
	keylget ntgr_UdpRelayListOnInterface($switch) UDPPORT_VALUE result
	return $result
}

