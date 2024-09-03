#!/bin/sh
################################################################################   
#
#  File Name   : lib-access-control.tcl
#
#  Description :
#         This TCL contains APIs to configure access control on netgear switches. 
#
#  Revision History 	:
#        Date         Programmer        Description
#        -----------------------------------------------------------------------
#        22/5/15      Bo Shi           Created
#
#
#
#
################################################################################

#*******************************************************************************
#
#  Function Name	: _switch_create_AccessProfileName
#
#  Description    : This function is called to create a access profile on netgear switch.
#
#  Usage          : _switch_create_AccessProfileName <switch_name> <profile_name>
#
#  Note           : mode could be "layer2" or "layer3"
# 
#*******************************************************************************
proc _switch_create_AccessProfileName {switch_name profile_name} {

	Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
     expect -i $cnn_id -re "#"
    exp_send -i $cnn_id "configure\r"
    exp_sleep 1
    expect -i $cnn_id -re "#"
    exp_send -i $cnn_id "management access-list $profile_name\r"
    exp_sleep 1
    exp_send -i $cnn_id "exit\r"
	Netgear_disconnect_switch $switch_name
}

#*******************************************************************************
#
#  Function Name	: _switch_get_AccessProfileName
#
#  Description    : This function is called to get a access profile name on netgear switch.
#
#  Usage          : _switch_get_AccessProfileName <switch_name> <profile_name>
#
#  Note           : mode could be "layer2" or "layer3"
# 
#*******************************************************************************
proc _switch_get_AccessProfileName {switch_name profile_name} {

	Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    exp_send -i $cnn_id "show management access-list $profile_name\r"
    expect -i $cnn_id -re "show management access-list $profile_name"
    expect -i $cnn_id -re "#"
    
    set output $expect_out(buffer)   
    set result ""
    regexp -line -nocase {^List\s+Name[^\w]*([\w]+)$} $output match result
    set result [string trim $result]

    Netgear_log_file "lib-access-control.tcl" "get a access profile name on $switch_name"
    Netgear_disconnect_switch $switch_name

    return $result
}


#*******************************************************************************
#
#  Function Name	: _switch_delete_AccessProfileName
#
#  Description    : This function is called to delete a access profile on netgear switch.
#
#  Usage          : _switch_delete_AccessProfileName <switch_name> <profile_name>
#
#  Note           : mode could be "layer2" or "layer3"
# 
#*******************************************************************************
proc _switch_delete_AccessProfileName {switch_name profile_name} {

	Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
     expect -i $cnn_id -re "#"
    exp_send -i $cnn_id "configure\r"
    exp_sleep 1
    expect -i $cnn_id -re "#"
    exp_send -i $cnn_id "no management access-list $profile_name\r"
    exp_sleep 1
    exp_send -i $cnn_id "exit\r"
	Netgear_disconnect_switch $switch_name
}


#*******************************************************************************
#
#  Function Name	: _switch_enable_AccessProfileName
#
#  Description    : This function is called to enable a access profile on netgear switch.
#
#  Usage          : _switch_enable_AccessProfileName <switch_name> <profile_name>
#
#  Note           : mode could be "layer2" or "layer3"
# 
#*******************************************************************************
proc _switch_enable_AccessProfileName {switch_name profile_name} {

	Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
     expect -i $cnn_id -re "#"
    exp_send -i $cnn_id "configure\r"
    exp_sleep 1
    expect -i $cnn_id -re "#"
    exp_send -i $cnn_id "management access-class $profile_name\r"
    exp_sleep 1
    exp_send -i $cnn_id "exit\r"
	Netgear_disconnect_switch $switch_name
}


#*******************************************************************************
#
#  Function Name	: _switch_disable_AccessControl
#
#  Description    : This function is called to disable  access control on netgear switch.
#
#  Usage          : _switch_disable_AccessControl <switch_name>
#
#  Note           : mode could be "layer2" or "layer3"
# 
#*******************************************************************************
proc _switch_disable_AccessControl {switch_name} {

	Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
     expect -i $cnn_id -re "#"
    exp_send -i $cnn_id "configure\r"
    exp_sleep 1
    expect -i $cnn_id -re "#"
    exp_send -i $cnn_id "no management access-class\r"
    exp_sleep 1
    exp_send -i $cnn_id "exit\r"
	Netgear_disconnect_switch $switch_name
}

#*******************************************************************************
#
#  Function Name	: _switch_add_AccessControlrule
#
#  Description    : This function is called to add  access control rule on netgear switch.
#
#  Usage          : _switch_add_AccessControlrule <switch_name> <profile_name> <rule>
#
#  Note           : mode could be "layer2" or "layer3"
# 
#*******************************************************************************
proc _switch_add_AccessControlrule {switch_name profile_name rule} {

	Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
     expect -i $cnn_id -re "#"
    exp_send -i $cnn_id "configure\r"
    exp_sleep 1
    expect -i $cnn_id -re "#"
    exp_send -i $cnn_id "management access-list $profile_name\r"
    exp_send -i $cnn_id "$rule\r"
    exp_sleep 1
    exp_send -i $cnn_id "exit\r"
	Netgear_disconnect_switch $switch_name
}

#*******************************************************************************
#
#  Function Name	: _switch_create_AccessControlrule
#
#  Description    : This function is called to create  access control rule on netgear switch.
#
#  Usage          : _switch_create_AccessControlrule <action> <sourceip> <mask> <service> <priority>
#
#  Note           : mode could be "layer2" or "layer3"
# 
#*******************************************************************************
proc _switch_create_AccessControlrule {action sourceip mask service priority} {

 set rule ""
  set rule  [concat $rule $action]
if {$sourceip !=""} {
 set rule  [concat $rule "ip-source" $sourceip "mask" $mask]
}
if {$service !=""} {
 set rule  [concat $rule "service" $service]
}

if {$priority !=""} {
 set rule  [concat $rule "priority" $priority]
}
return $rule

}

#*******************************************************************************
#
#  Function Name	: _switch_AccessProfileSpecifiedRule_IfExisting
#
#  Description    : This function is called to tell a specified access profile rule whether existing on netgear switch.
#
#  Usage          : _switch_get_AccessProfileName <switch_name> <profile_name> <action> <sourceip> <mask> <service> <priority>
#
#  Note           : mode could be "layer2" or "layer3"
# 
#*******************************************************************************
proc _switch_AccessProfileSpecifiedRule_IfExisting {switch_name profile_name action sourceip mask service priority} {

	Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    exp_send -i $cnn_id "show management access-list $profile_name\r"
    expect -i $cnn_id -re "show management access-list $profile_name"
    expect -i $cnn_id -re "#"
    
    set output $expect_out(buffer)   
    set result 0
    if {$sourceip !="" && $service !=""} {
       set result [regexp -line -nocase -- "^$action\\sip-source\\s$sourceip\\smask\\s$mask\\sservice\\s$service\\spriority\\s$priority$" $output]
    }
    if {$sourceip =="" && $service !=""} {
       set result [regexp -line -nocase -- "^$action\\sservice\\s$service\\spriority\\s$priority$" $output]
    }
    if {$sourceip !="" && $service ==""} {
      set result [regexp -line -nocase -- "^$action\\sip-source\\s$sourceip\\smask\\s$mask\\spriority\\s$priority$" $output]
    }
    Netgear_log_file "lib-access-control.tcl" "_switch_AccessProfileSpecifiedRule_IfExisting on $switch_name"
    Netgear_disconnect_switch $switch_name

    return $result
}