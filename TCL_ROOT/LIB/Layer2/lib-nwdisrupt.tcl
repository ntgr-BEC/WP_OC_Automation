#!/bin/sh
################################################################################   
#
#  File Name		: lib-nwdisrupt.tcl
#
#  Desciption       	:
#         This TCL contains APIs to shutdown all the links
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
#  Function Name	: _ntgrNetworkDisrupt 
#
#  Desciption    : This function enables/disables all the link on the switches
#                  This is run using the com port.
#         
#  Usage          : _ntgrNetworkDisrupt (switch_list) 
# 
#*******************************************************************************
proc _ntgrNetworkDisrupt {switch_list} {
	foreach switch_info $switch_list {
		set switch   [lindex $switch_info 0]
		set com_port [lindex $switch_info 1]
		Netgear_log_file "lib-nwdisrupt.tcl" "Disabling all ports on switch $switch connected on $com_port"
		Netgear_connect_switch_serial $switch $com_port
		set connection_id [_get_switch_handle $switch]
	    	exp_send -i $connection_id "configure\r"
    		exp_sleep 1
    		exp_send -i $connection_id "shutdown all\r"
		exp_sleep 1
	    	exp_send -i $connection_id "exit\r"
	    	exp_sleep 1
	}
	sleep 100
	foreach switch_info $switch_list {
		set switch   [lindex $switch_info 0]
		set connection_id [_get_switch_handle $switch]
		Netgear_log_file "lib-nwdisrupt.tcl" "Enabling all ports on switch $switch"
	    	exp_send -i $connection_id "configure\r"
   		exp_sleep 1
    		exp_send -i $connection_id "no shutdown all\r"
		exp_sleep 1
	    	exp_send -i $connection_id "exit\r"
	    	exp_sleep 1
		Netgear_disconnect_switch $switch
	}
}