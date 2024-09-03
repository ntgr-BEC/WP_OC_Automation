#!/bin/sh
################################################################################   
#
#  File Name			: lib-timerange-support.tcl
#
#  Description			:
#         This TCL contains functions commonly used for time-range.
#
#  Revision History 	:
#        Date          Programmer        Description
#        -----------------------------------------------------------------------
#        21-09-11       Tony Jing         Created
#
#
#
#

#*******************************************************************************
#
#  Function Name  : getTimeRangeName
#
#  Description    : This function is used to get time-range name list
#         
#  Usage          : getTimeRangeName <switch_name>
# 
#*******************************************************************************
proc getTimeRangeName {switch_name} {
	global ntgr_TimeRange_List
	return [keylget ntgr_TimeRange_List($switch_name) TIMERANGE_NAME_LIST]
}

#*******************************************************************************
#
#  Function Name  : getTimeRangeRule
#
#  Description    : This function is used to get time-range rule list
#         
#  Usage          : getTimeRangeRule <switch_name>
# 
#*******************************************************************************
proc getTimeRangeRule {switch_name} {
	global ntgr_TimeRange_List
	return [keylget ntgr_TimeRange_List($switch_name) TIMERANGE_RULE_LIST]
}
