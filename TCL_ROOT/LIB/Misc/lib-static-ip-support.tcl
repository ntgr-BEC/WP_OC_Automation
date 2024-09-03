#!/bin/sh
################################################################################   
#
#  File Name			: lib-static-ip-support.tcl
#
#  Description			:
#         This TCL contains functions commonly used for static IP.
#
#  Revision History 	:
#        Date          Programmer        Description
#        -----------------------------------------------------------------------
#        23-8-06       Nina Cheng        Created
#
#
#
#
#1*******************************************************************************
#
#  Function Name	: getAddressList
#
#  Description    : This function is used to get static ip list to be configured
#         
#  Usage          : getAddressList <switch_name>
# 
#*******************************************************************************
proc getAddressList {switch_name} {
	global ntgr_StaticIPList
	return [keylget ntgr_StaticIPList($switch_name) STATIC_ROUTE_ADDRESS_LIST]
}

#2*******************************************************************************
#
#  Function Name	: getDefaultRoute
#
#  Description    : This function is used to get default route information to be configured
#         
#  Usage          : getDefaultRoute <switch_name>
# 
#*******************************************************************************
proc getDefaultRoute {switch_name} {
	global ntgr_StaticIPList
	return [keylget ntgr_StaticIPList($switch_name) DEFAULT_ROUTE]
}

#3*******************************************************************************
#
#  Function Name	: getStaticRouteDistance
#
#  Description    : This function is used to get static route distance 
#         
#  Usage          : getStaticRouteDistance <switch_name>
# 
#*******************************************************************************
proc getStaticRouteDistance {switch_name} {
	global ntgr_StaticIPList
	return [keylget ntgr_StaticIPList($switch_name) STATIC_ROUTE_DISTANCE]
}

