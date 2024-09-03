#!/bin/sh
################################################################################   
#
#  File Name		  : lib-captiveportal.tcl
#
#  Description      :
#         This TCL contains functions to configure Captive Portal
#
#  Revision History :
#        Date          Programmer        Description
#        -----------------------------------------------------------------------
#       2014-03-06     jim.xie	         Created
#
#
#
#
################################################################################

#*******************************************************************************
#
#  Function Name  : _ntgrCaptivePortalEnable
#
#  Description    : This function is called to enable Captive Portal on specified
#                   switch.
#
#  Usage          : _ntgrCaptivePortalEnable <switch_name>
#
#*******************************************************************************
proc _ntgrCaptivePortalEnable {switch_name} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    exp_send -i $cnn_id "configure\r"
    exp_sleep 1
    exp_send -i $cnn_id "captive-portal\r"
    exp_sleep 1
	exp_send -i $cnn_id "enable\r"
    exp_sleep 1
    expect -i $cnn_id -re "#"
    exp_send -i $cnn_id "exit\r"
	exp_sleep 1
    expect -i $cnn_id -re "#"
    exp_send -i $cnn_id "exit\r"
    exp_sleep 1
    
    Netgear_log_file "lib-captiveportal.tcl" "Enable CP on switcher"
    Netgear_disconnect_switch $switch_name
}

#*******************************************************************************
#
#  Function Name  : _ntgrCaptivePortalDisable
#
#  Description    : This function is called to disable Captive Portal on specified
#                   switch.
#
#  Usage          : _ntgrCaptivePortalDisable <switch_name>
#
#*******************************************************************************
proc _ntgrCaptivePortalDisable {switch_name} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    exp_send -i $cnn_id "configure\r"
    exp_sleep 1
    exp_send -i $cnn_id "captive-portal\r"
    exp_sleep 1
	exp_send -i $cnn_id "no enable\r"
    exp_sleep 1
    expect -i $cnn_id -re "#"
    exp_send -i $cnn_id "exit\r"
	exp_sleep 1
    expect -i $cnn_id -re "#"
    exp_send -i $cnn_id "exit\r"
    exp_sleep 1
    
    Netgear_log_file "lib-captiveportal.tcl" "Disable CP on switcher"
    Netgear_disconnect_switch $switch_name
}

#*******************************************************************************
#
#  Function Name  : _ntgrCaptivePortalAddCP
#
#  Description    : This function is called to add Captive Portal on specified
#                   switch.
#
#  Pagrams        :
#                   cp_name
#                   protocol_type : http/https
#                   ver_type : guest/local/radius
#                   interface: A00
#
#  Usage          : _ntgrCaptivePortalAddCP <switch_name>
#
#*******************************************************************************
proc _ntgrCaptivePortalAddCP {switch_name cp_name protocol_type ver_type interface} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    exp_send -i $cnn_id "configure\r"
    exp_sleep 1
    exp_send -i $cnn_id "captive-portal\r"
    exp_sleep 1
	exp_send -i $cnn_id "configuration 2\r"
    exp_sleep 1
	exp_send -i $cnn_id "name $cp_name\r"
    exp_sleep 1
    exp_send -i $cnn_id "protocol $protocol_type\r"
    exp_sleep 1
	exp_send -i $cnn_id "verification $ver_type\r"
    exp_sleep 1
	exp_send -i $cnn_id "group 1 \r"
    exp_sleep 1
	exp_send -i $cnn_id "interface $interface \r"
    exp_sleep 1
    expect -i $cnn_id -re "#"
    exp_send -i $cnn_id "exit\r"
	exp_sleep 1
    expect -i $cnn_id -re "#"
    exp_send -i $cnn_id "exit\r"
    exp_sleep 1
    
    Netgear_log_file "lib-captiveportal.tcl" "Add CP 2 on switcher"
    Netgear_disconnect_switch $switch_name
}

#*******************************************************************************
#
#  Function Name  : _ntgrCaptivePortalDeleteCP
#
#  Description    : This function is called to delete Captive Portal on specified
#                   switch.
#
#  Pagrams        :
#
#  Usage          : _ntgrCaptivePortalDeleteCP <switch_name>
#
#*******************************************************************************
proc _ntgrCaptivePortalDeleteCP {switch_name} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    exp_send -i $cnn_id "configure\r"
    exp_sleep 1
    exp_send -i $cnn_id "captive-portal\r"
    exp_sleep 1
	exp_send -i $cnn_id "no configuration 2\r"
    exp_sleep 1
    expect -i $cnn_id -re "#"
    exp_send -i $cnn_id "exit\r"
	exp_sleep 1
    expect -i $cnn_id -re "#"
    exp_send -i $cnn_id "exit\r"
    exp_sleep 1
    
    Netgear_log_file "lib-captiveportal.tcl" "Delete CP 2 on switcher"
    Netgear_disconnect_switch $switch_name
}

