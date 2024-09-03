#!/bin/sh
################################################################################   
#
#  File Name		: cal-ntgr-pmirror.tcl
#
#  Desciption       	:
#         This TCL contains APIs to configure Port Mirroring. This is CLI 
#	    abstraction Layer for Port Mirror Configuration
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
#  Function Name	: CALPortMirrorConfig
#
#  Desciption    : This function configures Port Mirroring on the switch
#         
#  Usage          : CALPortMirrorConfig (switch) 
# 
#*******************************************************************************
proc CALPortMirrorConfig {switch} {
	set switch_vendor [_get_switch_vendor_name $switch]
	switch $switch_vendor {
		netgear {
			_ntgrConfigurePortMirror $switch	
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
			Netgear_log_file "cal-ntgr-pmirror.tcl" "Switch not defined"
		}
	} 
}

#*******************************************************************************
#
#  Function Name	: CALPortMirrorConfig
#
#  Desciption    : This function configures Port Mirroring on the switch
#         
#  Usage          : CALPortMirrorConfig (switch) 
#
#  Revision       : Create by kenddy xie 2010-09-08
# 
#*******************************************************************************
proc CALAddPortMirror {switch srcInt dstInt {direct both}} {
	set switch_vendor [_get_switch_vendor_name $switch]
	switch $switch_vendor {
		netgear {
			_ntgrAddPortMirror $switch $srcInt $dstInt $direct 	
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
			Netgear_log_file "cal-ntgr-pmirror.tcl" "Switch not defined"
		}
	} 
}

#*******************************************************************************
#
#  Function Name	: CALDeletePortMirror
#
#  Desciption    : This function configures Port Mirroring on the switch
#         
#  Usage          : CALDeletePortMirror <switch> <srcInt> <dstInt>) 
#
#  Revision       : Create by kenddy xie 2010-09-08
#
#*******************************************************************************
proc CALDeletePortMirror {switch srcInt dstInt} {
	set switch_vendor [_get_switch_vendor_name $switch]
	switch $switch_vendor {
		netgear {
			_ntgrDeletePortMirror $switch $srcInt $dstInt	
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
			Netgear_log_file "cal-ntgr-pmirror.tcl" "Switch not defined"
		}
	} 
}


#*******************************************************************************
#
#  Function Name	: CALVlanMirrorConfig
#
#  Desciption    : This function configures vlan Mirroring on the switch
#         
#  Usage          : CALVlanMirrorConfig (switch) 
#
#  Revision       : Create by boshi
# 
#*******************************************************************************
proc CALVlanMirrorConfig {switch srcInt dstInt} {
	set switch_vendor [_get_switch_vendor_name $switch]
	switch $switch_vendor {
		netgear {
			_ntgrAddVlanMirror $switch $srcInt $dstInt
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
			Netgear_log_file "cal-ntgr-pmirror.tcl" "Switch not defined"
		}
	} 
}

#*******************************************************************************
#
#  Function Name	: CALDeleteVlanMirror
#
#  Desciption    : This function configures Port Mirroring on the switch
#         
#  Usage          : CALDeletePortMirror <switch> 
#
#  Revision       : Create by boshi
#
#*******************************************************************************
proc CALDeleteVlanMirror {switch} {
	set switch_vendor [_get_switch_vendor_name $switch]
	switch $switch_vendor {
		netgear {
			_ntgrRemoveVlanMirror $switch 
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
			Netgear_log_file "cal-ntgr-pmirror.tcl" "Switch not defined"
		}
	} 
}