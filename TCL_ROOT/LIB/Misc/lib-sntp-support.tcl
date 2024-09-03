#!/bin/sh
################################################################################   
#
#  File Name		: lib-sntp-support.tcl
#
#  Description       	:
#         This TCL contains functions commonly used by Port Mirroring
#
#  Revision History 	:
#         Date         Programmer        Description
#        -----------------------------------------------------------------------
#        02-May-06     Rajeswari V       Created
#
#
#
#
################################################################################

#*******************************************************************************
#
#  Function Name	: _ntgrGetSntpClientMode 
#
#  Description    : This function is called to get the client mode from
#			  the global ntgr_sntpList
#         
#  Usage          : _ntgrGetSntpClientMode <switch>
# 
#*******************************************************************************
proc _ntgrGetSntpClientMode {switch} {
	global ntgr_sntpList
	return [keylget ntgr_sntpList($switch) SNTP_CLIENT_MODE]
}

#*******************************************************************************
#
#  Function Name	: _ntgrGetSntpClientPort 
#
#  Description    : This function is called to get the client port from
#			  the global ntgr_sntpList
#         
#  Usage          : _ntgrGetSntpClientPort <switch>
# 
#*******************************************************************************
proc _ntgrGetSntpClientPort {switch} {
	global ntgr_sntpList
	return [keylget ntgr_sntpList($switch) SNTP_CLIENT_PORT]
}

#*******************************************************************************
#
#  Function Name	: _ntgrGetSntpUnicastPollInterval 
#
#  Description    : This function is called to get the unicast poll interval from
#			  the global ntgr_sntpList
#         
#  Usage          : _ntgrGetSntpUnicastPollInterval <switch>
# 
#*******************************************************************************
proc _ntgrGetSntpUnicastPollInterval {switch} {
	global ntgr_sntpList
	return [keylget ntgr_sntpList($switch) SNTP_UNICAST_CLIENT_POLL_INTERVAL]
}

#*******************************************************************************
#
#  Function Name	: _ntgrGetSntpUnicastPollRetry 
#
#  Description    : This function is called to get the unicast poll retry from
#			  the global ntgr_sntpList
#         
#  Usage          : _ntgrGetSntpUnicastPollRetry <switch>
# 
#*******************************************************************************
proc _ntgrGetSntpUnicastPollRetry {switch} {
	global ntgr_sntpList
	return [keylget ntgr_sntpList($switch) SNTP_UNICAST_CLIENT_POLL_RETRY]
}

#*******************************************************************************
#
#  Function Name	: _ntgrGetSntpUnicastPollTimeout 
#
#  Description    : This function is called to get the unicast poll timeout from
#			  the global ntgr_sntpList
#         
#  Usage          : _ntgrGetSntpUnicastPollTimeout <switch>
# 
#*******************************************************************************
proc _ntgrGetSntpUnicastPollTimeout {switch} {
	global ntgr_sntpList
	return [keylget ntgr_sntpList($switch) SNTP_UNICAST_CLIENT_POLL_TIMEOUT]
}

#*******************************************************************************
#
#  Function Name	: _ntgrGetSntpBroadcastPollInterval 
#
#  Description    : This function is called to get the broadcast poll interval from
#			  the global ntgr_sntpList
#         
#  Usage          : _ntgrGetSntpBroadcastPollInterval <switch>
# 
#*******************************************************************************
proc _ntgrGetSntpBroadcastPollInterval {switch} {
	global ntgr_sntpList
	return [keylget ntgr_sntpList($switch) SNTP_BROADCAST_CLIENT_POLL_INTERVAL]
}

#*******************************************************************************
#
#  Function Name	: _ntgrGetSntpAddServerList 
#
#  Description    : This function is called to get the add server ip list from
#			  the global ntgr_sntpList
#         
#  Usage          : _ntgrGetSntpAddServerList <switch>
# 
#*******************************************************************************
proc _ntgrGetSntpAddServerList {switch} {
	global ntgr_sntpList
	return [keylget ntgr_sntpList($switch) SNTP_SERVER_ADD_LIST]
}

#*******************************************************************************
#
#  Function Name	: _ntgrGetSntpDelServerList 
#
#  Description    : This function is called to get the delete server ip list from
#			  the global ntgr_sntpList
#         
#  Usage          : _ntgrGetSntpDelServerList <switch>
# 
#*******************************************************************************
proc _ntgrGetSntpDelServerList {switch} {
	global ntgr_sntpList
	return [keylget ntgr_sntpList($switch) SNTP_SERVER_DEL_LIST]
}

#*******************************************************************************
#
#  Function Name	: _ntgrGetTimeZone 
#
#  Description    : This function is called to get timezone name and offset
#         
#  Usage          : _ntgrGetTimeZone <switch>
# 
#*******************************************************************************
proc _ntgrGetTimeZone {switch} {
	global ntgr_sntpList
	return [keylget ntgr_sntpList($switch) CLOCK_TIMEZONE]
}

