#!/bin/sh
################################################################################   
#
#  File Name		: cal-ntgr-sntp.tcl
#
#  Desciption       	:
#         This TCL contains APIs to configure SNTP. This is CLI 
#	    abstraction Layer for SNTP Configuration
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
#  Function Name	: CALSNTPClientConfig
#
#  Desciption    : This function configures SNTP client mode on the switch
#         
#  Usage          : CALSNTPClientConfig (switch) 
# 
#*******************************************************************************
proc CALSNTPClientConfig {switch} {
	set switch_vendor [_get_switch_vendor_name $switch]
	switch $switch_vendor {
		netgear {
			_ntgrSntpClientConfig $switch	
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
			Netgear_log_file "cal-ntgr-sntp.tcl" "Switch not defined"
		}
	} 
}

#*******************************************************************************
#
#  Function Name	: CALSNTPAddServer
#
#  Desciption    : This function adds SNTP server IP to the switch
#         
#  Usage          : CALSNTPAddServer (switch) 
# 
#*******************************************************************************
proc CALSNTPAddServer {switch} {
	set switch_vendor [_get_switch_vendor_name $switch]
	switch $switch_vendor {
		netgear {
			_ntgrSntpAddServer $switch	
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
			Netgear_log_file "cal-ntgr-sntp.tcl" "Switch not defined"
		}
	} 
}

#*******************************************************************************
#
#  Function Name	: CALSNTPDeleteServer
#
#  Desciption    : This function deletes SNTP server IP to the switch
#         
#  Usage          : CALSNTPAddServer (switch) 
# 
#*******************************************************************************
proc CALSNTPDeleteServer {switch} {
	set switch_vendor [_get_switch_vendor_name $switch]
	switch $switch_vendor {
		netgear {
			_ntgrSntpDeleteServer $switch	
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
			Netgear_log_file "cal-ntgr-sntp.tcl" "Switch not defined"
		}
	} 
}

