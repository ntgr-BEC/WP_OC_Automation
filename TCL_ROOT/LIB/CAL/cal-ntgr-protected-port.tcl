#!/bin/sh
################################################################################   
#
#  File Name		: cal-ntgr-protected-port.tcl
#
#  Desciption       	:
#         This TCL contains APIs to configure protected port. This is CLI 
#	    abstraction Layer for protected group Configuration
#
#  Revision History 	:
#         Date         Programmer        Desciption
#        -----------------------------------------------------------------------
#        19-Oct-2010   kenddy            Created    
#
#
#
################################################################################

#*******************************************************************************
#
#  Function Name	: CALAddProtectedPortGroupName
#
#  Desciption     : This function add protected port gorup name for a group id
#         
#  Usage          : CALAddProtectedPortGroupName <switch_name> <group_id> <group_name>
# 
#*******************************************************************************
proc CALAddProtectedPortGroupName {switch_name group_id group_name} {
	set switch_vendor [_get_switch_vendor_name $switch_name]
	switch $switch_vendor {
		netgear {
			_ntgrAddProtectedGroupName $switch_name $group_id $group_name	
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
			Netgear_log_file "cal-ntgr-protected-port.tcl" "Switch not defined"
		}
	} 
}


#*******************************************************************************
#
#  Function Name	: CALEnableProtectedGroupOnInterface
#
#  Desciption     : This function is called to bind a protected group to specified interface
#         
#  Usage          : CALEnableProtectedGroupOnInterface <switch_name> <group_id> <port_list>
# 
#*******************************************************************************
proc CALEnableProtectedGroupOnInterface {switch_name group_id port_list} {
  
	set switch_vendor [_get_switch_vendor_name $switch_name]
	switch $switch_vendor {
		netgear {
			_ntgrEnableProtectedGroupOnInterface $switch_name $group_id $port_list	
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
			Netgear_log_file "cal-ntgr-protected-port.tcl" "Switch not defined"
		}
	} 
}

#*******************************************************************************
#
#  Function Name	: CALDisableProtectedGroupOnInterface
#
#  Desciption     : This function is called to unbind a protected group to specified interface
#         
#  Usage          : CALDisableProtectedGroupOnInterface <switch_name> <group_id> <port_list>
# 
#*******************************************************************************
proc CALDisableProtectedGroupOnInterface {switch_name group_id port_list} {
  
	set switch_vendor [_get_switch_vendor_name $switch_name]
	switch $switch_vendor {
		netgear {
			_ntgrDisableProtectedGroupOnInterface $switch_name $group_id $port_list	
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
			Netgear_log_file "cal-ntgr-protected-port.tcl" "Switch not defined"
		}
	} 
}