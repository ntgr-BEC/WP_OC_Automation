#!/bin/sh
################################################################################   
#
#  File Name		: lib-ntgr-nwdisrupt.tcl
#
#  Desciption       	:
#         This TCL contains APIs to FLAP links
#
#  Revision History 	:
#         Date         Programmer        Desciption
#        -----------------------------------------------------------------------
#        24-may-06     Rajeswari Created    
#
#
#
################################################################################

#*******************************************************************************
#
#  Function Name	: APINetworkDisrupt
#
#  Desciption    : This function enables/disables link on the switches
#         
#  Usage          : APINetworkDisrupt (switch_list) 
# 
#*******************************************************************************
proc APINetworkDisrupt {switch_list} {
	set switch_vendor [_get_switch_vendor_name $switch]
	switch $switch_vendor {
		netgear {
			_ntgrNetworkDisrupt $switch_list	
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
		default		{
			Netgear_log_file "lib-ntgr-nwdisrupt.tcl" "Switch not defined"
		}
	} 
}