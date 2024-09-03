#!/bin/sh
################################################################################   
#
#  File Name		: lib-ntgr-connect.tcl
#
#  Description       	:
#         This library defines the APIs to connect and disconnect to all Netgear
#	    switches and third party products like Spirent, IXIA and other 
#	    vendor switches like CISCO, 3COM, HP, etc.
#
#  Revision History 	:
#         Date         Programmer        Description
#        -----------------------------------------------------------------------
#        02-May-06     Rajeswari V       Created
#
#
################################################################################

#*******************************************************************************
#
#  Function Name	: Netgear_connect_switch
#
#  Description    : This function initialise and connects to the traffic
#			  generator chassis (Ixia/Spirent) or Netgear Switches or
#		        non-netgear switches
#         
#  Usage          : Netgear_connect_switch <product_id>
# 
#*******************************************************************************
proc Netgear_connect_switch {product_id} {
  global NTGR_DUT_LIST
	Netgear_log_file "lib-ntgr-connect.tcl" "Connecting to $product_id ..."
	set switch_vendor  [_get_switch_vendor_name $product_id]
	set switch_ip_addr [_get_switch_ip_addr $product_id]
	set switch_ts_port [_ntgrGetTSPort $product_id]
	set switch_model   [_get_switch_model $product_id]

	switch $switch_vendor {
		netgear	{
		  set retId [_switch_connect_telnet $switch_ip_addr $switch_ts_port]
		  if { $retId == -1 } {return -1}
			_set_switch_handle $product_id $retId
			if {[string first $product_id $NTGR_DUT_LIST] == -1} {
			  set NTGR_DUT_LIST "$NTGR_DUT_LIST $product_id"
			}
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
#  Function Name	: Netgear_connect_switch_serial
#
#  Description    : This function connects to Netgear/Non-netgear switches using
#			  COM port         
#  Usage          : Netgear_connect_switch_serial <product_id> <com_port>
# 
#*******************************************************************************
proc Netgear_connect_switch_serial {product_id com_port} {
	set switch_vendor [_get_switch_vendor_name $product_id]
	switch $switch_vendor {
		case netgear	{
			_set_switch_handle [_switch_connect_serial $com_port]
		}
		case cisco		{
			puts "TODO\n"
		}
		case hp		{
			puts "TODO\n"
		}
		case 3com		{
			puts "TODO\n"
		}
		default		{
			Netgear_log_file "LIB-lib-ual.tcl" "Switch not defined"
		}
	}
}

#*******************************************************************************
#
#  Function Name	: Netgear_disconnect_switch
#
#  Description    : This function disconnects the traffic generator (Ixia/Spirent)
#			  or Netgear switch or non-netgear switch
#         
#  Usage          : Netgear_disconnect_switch <product_id>
# 
#*******************************************************************************
proc Netgear_disconnect_switch {product_id} {
	set switch_vendor  [_get_switch_vendor_name $product_id]
	set switch_handle [_get_switch_handle $product_id]
	set switch_ts_port [_ntgrGetTSPort $product_id]
	if {$switch_handle == 0} {
		Netgear_log_file "LIB-lib-ual.tcl" "Switch is not connected."
		Netgear_log_file "LIB-lib-ual.tcl" "Switch cannot be disconnected"
	} else {
		switch $switch_vendor {
			netgear	{
			if {$switch_ts_port == 60000} {
				_switch_disconnect_60000 $switch_handle
				_set_switch_handle $product_id 0
			} else {
				_switch_disconnect $switch_handle
				_set_switch_handle $product_id 0
					}
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

#*******************************************************************************
#
#  Function Name	: Netgear_check_switch_reachable
#
#  Description    : This function checks whether the switch is reachable
#         
#  Usage          : Netgear_check_switch_reachable <product_id>
# 
#*******************************************************************************
proc Netgear_check_switch_reachable {product_id} {
	global ntgr_swList
	set switch_ip_addr  [_get_switch_ip_addr $product_id]
	return [_switch_ping $switch_ip_addr]
}
#*******************************************************************************
#
#  Function Name	: APICheckProductReachable
#
#  Description    : This function checks whether the switch/ Tester is reachable
#         
#  Usage          : APICheckProductReachable
# 
#*******************************************************************************
proc APICheckProductReachable {} {
	global NTGR_SCRIPT_NAME

	set switch_list [_ntgrGetProductSwitchList]
	set tester_list [_ntgrGetProductTesterList]
	if {[llength $switch_list] != 0} {
		foreach product $switch_list {
			Netgear_log_file "$NTGR_SCRIPT_NAME.tcl" "Pinging the Switch $product"
			set result [Netgear_check_switch_reachable $product]
			if {$result == FALSE}  {
				Netgear_log_file "$NTGR_SCRIPT_NAME.tcl" "-------------------------------------------------------------------------"
				Netgear_log_file "$NTGR_SCRIPT_NAME.tcl" "                   Exited Test Suite $NTGR_SCRIPT_NAME"
				Netgear_log_file "$NTGR_SCRIPT_NAME.tcl" "          Exit Reason : Switch $product is not reachable"
				Netgear_log_file "$NTGR_SCRIPT_NAME.tcl" "-------------------------------------------------------------------------"
				Netgear_stop_log
				exit 0
			} else {
				Netgear_log_file "$NTGR_SCRIPT_NAME.tcl" "Ping  Successful to switch $product"
			}
		}
	}

	if {[llength $tester_list] != 0} {
		foreach product $tester_list {
			Netgear_log_file "$NTGR_SCRIPT_NAME.tcl" "Pinging the Tester $product"
			set result [UALCheckTesterReachable $product]
			if {$result == FALSE}  {
				Netgear_log_file "$NTGR_SCRIPT_NAME.tcl" "-------------------------------------------------------------------------"
				Netgear_log_file "$NTGR_SCRIPT_NAME.tcl" "                   Exited Test Suite $NTGR_SCRIPT_NAME"
				Netgear_log_file "$NTGR_SCRIPT_NAME.tcl" "          Exit Reason : Tester $product is not reachable"
				Netgear_log_file "$NTGR_SCRIPT_NAME.tcl" "-------------------------------------------------------------------------"
				Netgear_stop_log
				exit 0
			} else {
				Netgear_log_file "$NTGR_SCRIPT_NAME.tcl" "Ping  Successful to Tester $product"
			}
		}
	}
}
