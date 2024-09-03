#!/bin/sh
################################################################################   
#
#  File Name		: lib-ip-support.tcl
#
#  Description       	:
#         This TCL contains functions commonly used by IP routing.
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
#  Function Name	: _ntgrGetIpGlobalStatus
#
#  Description    : This function is called to get the global status from
#			  the global ntgr_ipList
#         
#  Usage          : _ntgrGetIpGlobalStatus <switch>
# 
#*******************************************************************************
proc _ntgrGetIpGlobalStatus {switch} {
	global ntgr_IPList
	return [keylget ntgr_IPList($switch) IP_GLOBAL_STATUS]
}

#*******************************************************************************
#
#  Function Name	: _ntgrGetIpInterfaceList
#
#  Description    : This function is called to get the interface list from
#			  the global ntgr_ipList
#         
#  Usage          : _ntgrGetIpInterfaceList <switch>
# 
#*******************************************************************************
proc _ntgrGetIpInterfaceList {switch} {
	global ntgr_IPList
	return [keylget ntgr_IPList($switch) IP_INTERFACE_LIST]
}

#*******************************************************************************
#
#  Function Name	: _ntgrGetIpRouteList
#
#  Description    : This function is called to get the Route list from
#			  the global ntgr_ipList
#         
#  Usage          : _ntgrGetIpRouteList <switch>
# 
#*******************************************************************************
proc _ntgrGetIpRouteList {switch} {
	global ntgr_IPList
	return [keylget ntgr_IPList($switch) IP_ROUTE_LIST]
}