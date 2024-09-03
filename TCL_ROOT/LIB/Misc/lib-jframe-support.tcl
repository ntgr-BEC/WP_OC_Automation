#!/bin/sh
################################################################################   
#
#  File Name		: lib-jframe-support.tcl
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
#  Function Name	: _ntgrGetJFrameSwitchType
#
#  Description    : This function is called to get the switch type from
#			  the global ntgr_jumboFrameList
#         
#  Usage          : _ntgrGetJFrameSwitchType <switch>
# 
#*******************************************************************************
proc _ntgrGetJFrameSwitchType {switch} {
	global ntgr_jumboFrameList
	return [keylget ntgr_jumboFrameList($switch) JFRAME_SWITCH_TYPE]
}

#*******************************************************************************
#
#  Function Name	: _ntgrGetJFrameMtuList
#
#  Description    : This function is called to get the MTU list from
#			  the global ntgr_jumboFrameList
#         
#  Usage          : _ntgrGetJFrameMtuList <switch>
# 
#*******************************************************************************
proc _ntgrGetJFrameMtuList {switch} {
	global ntgr_jumboFrameList
	return [keylget ntgr_jumboFrameList($switch) JFRAME_MTU_LIST]
}