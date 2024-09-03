#!/bin/sh
################################################################################   
#
#  File Name		: cal-ntgr-mac-filter.tcl
#
#  Desciption       	:
#         This TCL contains APIs to configure mac filter. This is CLI 
#	    abstraction Layer for mac filter Configuration
#
#  Revision History 	:
#         Date         Programmer        Desciption
#        -----------------------------------------------------------------------
#        01-Nov-2010   kenddy            Created    
#
#
#
################################################################################

#*******************************************************************************
#
#  Function Name	: CALAddMacFilterInstance
#
#  Desciption     : This function add macfilter for device
#         
#  Usage          : CALAddMacFilterInstance <switch_name> <mac_addr> <vlan_id>
# 
#*******************************************************************************
proc CALAddMacFilterInstance {switch_name mac_addr vlan_id} {
	set switch_vendor [_get_switch_vendor_name $switch_name]
	switch $switch_vendor {
		netgear {
			_ntgrAddMacFilterInstance $switch_name $mac_addr $vlan_id
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
			Netgear_log_file "cal-ntgr-mac-filter.tcl" "Switch not defined"
		}
	} 
}

#*******************************************************************************
#
#  Function Name	: CALDeleteMacFilterInstance
#
#  Desciption     : This function del macfilter for device
#         
#  Usage          : CALDeleteMacFilterInstance <switch_name> <mac_addr> <vlan_id>
# 
#*******************************************************************************
proc CALDeleteMacFilterInstance {switch_name mac_addr vlan_id} {
	set switch_vendor [_get_switch_vendor_name $switch_name]
	switch $switch_vendor {
		netgear {
			_ntgrDeleteMacFilterInstance $switch_name $mac_addr $vlan_id
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
			Netgear_log_file "cal-ntgr-mac-filter.tcl" "Switch not defined"
		}
	} 
}

#*******************************************************************************
#
#  Function Name	: CALEnableMacFilterOnInterface
#
#  Desciption     : This function binding macfilter on interface.
#         
#  Usage          : CALEnableMacFilterOnInterface <switch_name> <port_list> <direction> <mac_addr> <vlan_id>
# 
#*******************************************************************************
proc CALEnableMacFilterOnInterface {switch_name port_list direction mac_addr vlan_id} {
	set switch_vendor [_get_switch_vendor_name $switch_name]
	switch $switch_vendor {
		netgear {
			_ntgrEnableMacFilterOnInterface $switch_name $port_list $direction $mac_addr $vlan_id
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
			Netgear_log_file "cal-ntgr-mac-filter.tcl" "Switch not defined"
		}
	} 
}

#*******************************************************************************
#
#  Function Name	: CALDisableMacFilterOnInterface
#
#  Desciption     : This function unbinding macfilter on interface.
#         
#  Usage          : CALDisableMacFilterOnInterface <switch_name> <port_list> <direction> <mac_addr> <vlan_id>
# 
#*******************************************************************************
proc CALDisableMacFilterOnInterface {switch_name port_list direction mac_addr vlan_id} {
	set switch_vendor [_get_switch_vendor_name $switch_name]
	switch $switch_vendor {
		netgear {
			_ntgrDisableMacFilterOnInterface $switch_name $port_list $direction $mac_addr $vlan_id
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
			Netgear_log_file "cal-ntgr-mac-filter.tcl" "Switch not defined"
		}
	} 
}
