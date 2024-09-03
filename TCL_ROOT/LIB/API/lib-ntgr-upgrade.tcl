#!/bin/sh
################################################################################   
#
#  File Name		: lib-ntgr-upgrade.tcl
#
#  Description       	:
#         This TCL contains APIs to upgrade the versions on Netgear Switches
#
#  Revision History 	:
#         Date         Programmer        Description
#        -----------------------------------------------------------------------
#        02-May-06     Rajeswari V       Created
#
#
#
#
################################################################################

#*******************************************************************************
#
#  Function Name	: Netgear_switch_upgrade
#
#  Description    : This function is an API to upgrade the image. This script assumes
#			  the TFTP server is running on the host where this TCL script is 
#		 	  executed and the TFTP path is configured properly.
#         
#  Usage          : Netgear_switch_upgrade <product_id>
# 
#*******************************************************************************

proc Netgear_switch_upgrade {product_id} {

	set connect_type [_get_tftp_connect_type  $product_id]

	if {$connect_type == "telnet"} {
		Netgear_connect_switch $product_id
	} else {
		Netgear_connect_switch_serial $product_id $connect_type
	}

	set switch_handle [_get_switch_handle $product_id]
	set tftp_server [_get_tftp_server_ip_addr $product_id]
	set image_name [_get_tftp_image_name $product_id]

	set version1 [_switch_show_hardware $switch_handle]
	Netgear_log_file "lib-ntgr-upgrade.tcl" "Existing version : $version1"
	_switch_copy_and_reload $switch_handle $tftp_server $image_name "image"

	sleep 3
	set reachable FALSE
	while {$reachable == FALSE} {
	    set reachable [Netgear_check_switch_reachable $product_id]
	}

	Netgear_connect_switch $product_id
	set switch_handle [_get_switch_handle $product_id]
	
	set version2 [_switch_show_hardware $switch_handle]
	Netgear_log_file "lib-ntgr-upgrade.tcl" "Upgraded Version : $version2"
	
	Netgear_disconnect_switch $product_id
	
	if {$version1 == $version2} {
		return FALSE
	} else {
		#_set_switch_image_version $product_id $version2
		return TRUE
	}
}

#*******************************************************************************
#
#  Function Name	: Netgear_switch_upload_config
#
#  Description    : This function is an API to upload the config. This script assumes
#			  the TFTP server is running on the host where this TCL script is 
#		 	  executed and the TFTP path is configured properly.
#         
#  Usage          : Netgear_switch_upload_config <product_id> <tftp_server> <config_name>
# 
#*******************************************************************************

proc Netgear_switch_upload_config {product_id tftp_server config_name} {
	global _ntgr_swList
	
	Netgear_connect_switch $product_id
	set switch_handle [_get_switch_handle $product_id]

	_switch_copy_command $switch_handle $tftp_server $image_name "config"

	sleep 100
	
	Netgear_disconnect_switch $product_id
}

