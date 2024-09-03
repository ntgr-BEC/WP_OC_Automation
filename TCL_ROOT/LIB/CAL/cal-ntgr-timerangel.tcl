#!/bin/sh
################################################################################   
#
#  File Name   : cal-ntgr-timerange.tcl
#
#  Description :
#         This file contains CLI Abstraction layer for time-range configuration.
#
#  Revision History 	:
#        Date         Programmer        Description
#        -----------------------------------------------------------------------
#        22/09/11     Tony Jing         Created
#
#
#
#
################################################################################

#*******************************************************************************
#
#  Function Name	: CALAddTimeRange
#
#  Description    	: This function is called to add time-range on switch
#         
#  Usage          	: CALAddTimeRange <switch_name> <timerange_rule_list>
# 
#*******************************************************************************
proc CALAddTimeRange {switch_name timerange_rule_list} {
	   
		set switch_vendor [_get_switch_vendor_name $switch_name]
		switch $switch_vendor {
			netgear {
		        _ntgrAddTimeRange $switch_name $timerange_rule_list
			}
			cisco {
				puts "TODO\n"
			} 
			hp {
				puts "TODO\n"
			}
			3com {
				puts "TODO\n"
			} 
			default {
				Netgear_log_file "cal-ntgr-timerange.tcl" "Switch not defined"
			}
		} 
}

#*******************************************************************************
#
#  Function Name	: CALDeleteTimeRange
#
#  Description    	: This function is called to delete time-range on switch
#         
#  Usage          	: CALDeleteTimeRange <switch_name> <timerange_name_list>
# 
#*******************************************************************************
proc CALDeleteTimeRange { switch_name timerange_name_list} {
	   
		set switch_vendor [_get_switch_vendor_name $switch_name]
		switch $switch_vendor {
			netgear {
			    _ntgrDeleteTimeRange $switch_name $timerange_name_list
			}
			cisco {
				puts "TODO\n"
			} 
			hp {
				puts "TODO\n"
			}
			3com {
				puts "TODO\n"
			} 
			default {
				Netgear_log_file "cal-ntgr-timerange.tcl" "Switch not defined"
			}
		} 
}






#*******************************************************************************
#
#  Function Name  : CALGetTimeRangeStatus
#
#  Description    : This function is used to get time range status of a switch.
#
#  Usage          : CALGetTimeRangeStatus <switch_name> <timeRangeName>
# 
#*******************************************************************************
proc CALGetTimeRangeStatus { switch_name timeRangeName} {
	   
		set switch_vendor [_get_switch_vendor_name $switch_name]
		switch $switch_vendor {
			netgear {
			    _ntgrGetTimeRangeStatus $switch_name $timeRangeName
			}
			cisco {
				puts "TODO\n"
			} 
			hp {
				puts "TODO\n"
			}
			3com {
				puts "TODO\n"
			} 
			default {
				Netgear_log_file "cal-ntgr-timerange.tcl" "Switch not defined"
			}
		} 
}
