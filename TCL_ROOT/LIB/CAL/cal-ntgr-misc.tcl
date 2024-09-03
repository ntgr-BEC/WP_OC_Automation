#!/bin/sh
################################################################################   
#
#  File Name   : cal-ntgr-misc.tcl
#
#  Description : This TCL contains APIs to configure the switches. This is CLI 
#         abstraction Layer for misclaneous commands.
#
#  Revision History 	:
#        Date         Programmer        Description
#        -----------------------------------------------------------------------
#
#
#
#
################################################################################

#*******************************************************************************
#
#  Function Name	: CALConfigureTstSw
#
#  Description    : This CAL function is used to configure Test Switch.
#         
#  Usage          : CALConfigureTstSw 
# 
#*******************************************************************************
proc CALConfigureTstSw {switch} {
	set switch_vendor [ _get_switch_vendor_name  $switch ]
	switch $switch_vendor {
		netgear 	{
			_ntgrConfigureTstSw $switch
		}
		cisco	{
			puts "TODO\n"
		}
		hp	{
			puts "TODO\n"
		}
		3com	{
			puts "TODO\n"
		}
		default {
			Netgear_log_file "cal-global-cmd.tcl" "Switch not defined"
		}
	}
}

