#!/bin/sh
################################################################################   
#
#  File Name   : cal-ntgr-routing.tcl
#
#  Description :
#         This TCL contains APIs to configure the switches. This is CLI 
#         abstraction Layer for enable routing.
#
#  Revision History 	:
#        Date         Programmer        Description
#        -----------------------------------------------------------------------
#        15/9/06      Nina Cheng        Created
#
#
#
#
################################################################################

#*******************************************************************************
#
#  Function Name	: Netgear_connect_switch_1
#
#  Description    : This function initialise and connects to the traffic
#			  generator chassis (Ixia/Spirent) or Netgear Switches or
#		        non-netgear switches
#         
#  Usage          : Netgear_connect_switch_1 <product_id> <username> <password> <enable_pwd>
# 
#*******************************************************************************
proc Netgear_connect_switch_1 {product_id {user_name admin} {pass_word ""} {enable_pwd ""}} {

	set switch_vendor  [_get_switch_vendor_name $product_id]
	set switch_ip_addr [_get_switch_ip_addr $product_id]
	set switch_ts_port [_ntgrGetTSPort $product_id]

	switch $switch_vendor {
		netgear	{
		  
		  set ret_id [_switch_connect_telnet_1 $switch_ip_addr $switch_ts_port $user_name $pass_word $enable_pwd]
			_set_switch_handle $product_id $ret_id
			return $ret_id
		}
		ixia		{
			_set_switch_handle $product_id [_ixia_connect_init $switch_ip_addr]
		}
		spirent 	{
			# Spirent returns 2 handle. The connection id and the project id
			set handle 0
			set handle [_spirent_connect_init $switch_ip_addr]
			_set_switch_handle [lindex $handle 0]
			_set_switch_project_handle [lindex $handle 1]
		}
		cisco		{
			puts "TODO\n"
		}
		hp		{
			puts "TODO\n"
		}
		3com		{
			puts "TODO\n"
		}
		default		{
			Netgear_log_file "LIB-lib-ual.tcl" "Switch $switch_vendor not defined"
		}
	}
}

#*******************************************************************************
#
#  Function Name	: Netgear_disconnect_switch_1
#
#  Description    : This function disconnects the traffic generator (Ixia/Spirent)
#			  or Netgear switch or non-netgear switch
#         
#  Usage          : Netgear_disconnect_switch_1 <product_id>
# 
#*******************************************************************************
proc Netgear_disconnect_switch_1 {product_id} {
	set switch_vendor  [_get_switch_vendor_name $product_id]
	set switch_handle [_get_switch_handle $product_id]

	if {$switch_handle == 0} {
		Netgear_log_file "LIB-lib-ual.tcl" "Switch is not connected."
		Netgear_log_file "LIB-lib-ual.tcl" "Switch cannot be disconnected"
	} else {
		switch $switch_vendor {
			netgear	{
				_switch_disconnect_1 $switch_handle
				_set_switch_handle $product_id 0                
			}

			ixia		{
				_ixia_disconnect $switch_handle
				_set_switch_handle $product_id 0
			}

			spirent 	{
				_spirent_disconnect $switch_handle
				_set_switch_handle $product_id 0
				_set_switch_project_handle $product_id 0
			}
	
			cisco		{
				puts "TODO\n"
			}

			hp		{
				puts "TODO\n"
			}

			3com		{
				puts "TODO\n"
			}
		
			default		{
				Netgear_log_file "LIB-lib-ual.tcl" "Switch not defined"
			}
		}
	}
}