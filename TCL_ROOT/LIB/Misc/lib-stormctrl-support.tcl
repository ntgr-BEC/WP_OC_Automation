#!/bin/sh
################################################################################   
#
#  File Name		: lib-stormctrl-support.tcl
#
#  Description       	:
#         This TCL contains functions commonly used by Storm Control
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
#  Function Name	: _ntgrGetStormControlFlowCtrl 
#
#  Description    : This function is called to get the flow control status from
#			  the global ntgr_stormControlList
#         
#  Usage          : _ntgrGetStormControlFlowCtrl <switch>
# 
#*******************************************************************************
proc _ntgrGetStormControlFlowCtrl {switch} {
	global ntgr_stormControlList
	return [keylget ntgr_stormControlList($switch) STORM_CTRL_FLOWCTRL]
}

#*******************************************************************************
#
#  Function Name	: _ntgrGetStormControlBcastList 
#
#  Description    : This function is called to get the broadcast interface list from
#			  the global ntgr_stormControlList
#         
#  Usage          : _ntgrGetStormControlBcastList <switch>
# 
#*******************************************************************************
proc _ntgrGetStormControlBcastList {switch} {
	global ntgr_stormControlList
	return [keylget ntgr_stormControlList($switch) STORM_CTRL_BROADCAST_IF_LIST]
}

#*******************************************************************************
#
#  Function Name	: _ntgrGetStormControlMcastList 
#
#  Description    : This function is called to get the Multicast interface list from
#			  the global ntgr_stormControlList
#         
#  Usage          : _ntgrGetStormControlMcastList <switch>
# 
#*******************************************************************************
proc _ntgrGetStormControlMcastList {switch} {
	global ntgr_stormControlList
	return [keylget ntgr_stormControlList($switch) STORM_CTRL_MULTICAST_IF_LIST]
}

#*******************************************************************************
#
#  Function Name	: _ntgrGetStormControlUcastList 
#
#  Description    : This function is called to get the Unicast interface list from
#			  the global ntgr_stormControlList
#         
#  Usage          : _ntgrGetStormControlUcastList <switch>
# 
#*******************************************************************************
proc _ntgrGetStormControlUcastList {switch} {
	global ntgr_stormControlList
	return [keylget ntgr_stormControlList($switch) STORM_CTRL_UNICAST_IF_LIST]
}