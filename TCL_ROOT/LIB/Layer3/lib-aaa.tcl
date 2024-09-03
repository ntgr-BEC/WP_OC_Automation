#!/bin/sh
################################################################################   
#
#  File Name		  : lib-aaa.tcl
#
#  Description      :
#         This TCL contains functions to configure aaa
#
#  Revision History :
#        Date          Programmer        Description
#        -----------------------------------------------------------------------
#        06-Dec-11     Tony              Created
#
#
#
#

#*******************************************************************************
#
#  Function Name  : _ntgrAaaAddIasUser
#
#  Description    : This function is called to add aaa ias user
#
#  Usage          : _ntgrAaaAddIasUser <switch_name> <username>
#
#*******************************************************************************
proc _ntgrAaaAddIasUser {switch_name username} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    exp_send -i $cnn_id "configure\r"
    exp_sleep 1
    exp_send -i $cnn_id "aaa ias-user username $username\r"
    exp_sleep 1
    expect -i $cnn_id -re "#"
    exp_send -i $cnn_id "exit\r"
    exp_sleep 1
    
    Netgear_log_file "lib-aaa.tcl" "add aaa ias user on switcher"
    Netgear_disconnect_switch $switch_name
}

#*******************************************************************************
#
#  Function Name  : _ntgrAaaDeleteIasUser
#
#  Description    : This function is called to delete aaa ias user
#
#  Usage          : _ntgrAaaDeleteIasUser <switch_name> <username>
#
#*******************************************************************************
proc _ntgrAaaDeleteIasUser {switch_name username} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    exp_send -i $cnn_id "configure\r"
    exp_sleep 1
    exp_send -i $cnn_id "no aaa ias-user username $username\r"
    exp_sleep 1
    expect -i $cnn_id -re "#"
    exp_send -i $cnn_id "exit\r"
    exp_sleep 1
    
    Netgear_log_file "lib-aaa.tcl" "delete aaa ias user on switcher"
    Netgear_disconnect_switch $switch_name
}


#*******************************************************************************
#
#  Function Name  : _ntgrCheckAaaIasUser
#
#  Description    : This function is used to check aaa ias user exist or not.
#
#  Usage          : _ntgrCheckAaaIasUser <switch_name> <user_list> <notin>
#
#*******************************************************************************
proc _ntgrCheckAaaIasUser {switch_name user_list {notin 0}} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    set output ""
    
    exp_send -i $cnn_id "show aaa ias-users\r"
    exp_sleep 1
    expect -i $cnn_id -re "show aaa ias-users"
    expect {
        -i $cnn_id -re "#" { append output $expect_out(buffer) }
        -i $cnn_id -re "More--" {
            append output $expect_out(buffer)
            exp_send -i $cnn_id " "
            exp_sleep 1
            exp_continue
        }   
    }
    
    set result 1
    foreach user $user_list {
        if {$notin == 1} {
            if {[regexp -nocase $user $output]} {
                set result 0
                break
            }
        } else {
            if {![regexp -nocase $user $output]} {
                set result 0
                break
            }
        }
    }

    Netgear_log_file "lib-aaa.tcl" "getting aaa ias user on switcher"
    Netgear_disconnect_switch $switch_name

    return $result
}


#*******************************************************************************
#  Function Name	: _ntgrAddAaaEnableAuthList
#
#  Description    : This function is called to add aaa enable authentication list
#         
#  Usage          : _ntgrAddAaaEnableAuthList <switch_name> <list_name> <auth_mode>
#
#  Revision       : Create by kenddy xie 2011-08-22
#
#*******************************************************************************
proc _ntgrAddAaaEnableAuthList {switch_name list_name auth_mode} {
	
	  set cnn_id [_get_switch_handle $switch_name]
    set bCnn 1
    if {[string first "exp" $cnn_id] == -1} {
        Netgear_connect_switch $switch_name
        set cnn_id [_get_switch_handle $switch_name]
        set bCnn 0
    }
    set ret ""
  	exp_send -i $cnn_id "configure\r"
  	exp_sleep 1
    expect -i $cnn_id -re "#"
    exp_send -i $cnn_id "aaa authentication enable $list_name $auth_mode\r"
    exp_sleep 1
    expect -i $cnn_id -re "#" 
    append ret $expect_out(buffer)       
  	exp_send -i $cnn_id "exit\r"
  	exp_sleep 1
    if {$bCnn == 0} {
        Netgear_disconnect_switch $switch_name
    }  	
    return $ret
}

#*******************************************************************************
#  Function Name	: _ntgrDelAaaEnableAuthList
#
#  Description    : This function is called to create aaa enable authentication list
#         
#  Usage          : _ntgrDelAaaEnableAuthList <switch_name> <list_name> <auth_mode>
#
#  Revision       : Create by kenddy xie 2011-08-22
#
#*******************************************************************************
proc _ntgrDelAaaEnableAuthList {switch_name list_name} {
	
	  set cnn_id [_get_switch_handle $switch_name]
    set bCnn 1
    if {[string first "exp" $cnn_id] == -1} {
        Netgear_connect_switch $switch_name
        set cnn_id [_get_switch_handle $switch_name]
        set bCnn 0
    }
    set ret ""
  	exp_send -i $cnn_id "configure\r"
  	exp_sleep 1
    expect -i $cnn_id -re "#"
    exp_send -i $cnn_id "no aaa authentication enable $list_name\r"
    exp_sleep 1
    expect -i $cnn_id -re "#" 
    append ret $expect_out(buffer)       
  	exp_send -i $cnn_id "exit\r"
  	exp_sleep 1
    if {$bCnn == 0} {
        Netgear_disconnect_switch $switch_name
    }  	
    return $ret
}

#*******************************************************************************
#  Function Name	: _ntgrAddAaaLoginAuthList
#
#  Description    : This function is called to add aaa login authentication list
#         
#  Usage          : _ntgrAddAaaLoginAuthList <switch_name> <list_name> <auth_mode>
#
#  Revision       : Create by kenddy xie 2011-08-22
#
#*******************************************************************************
proc _ntgrAddAaaLoginAuthList {switch_name list_name auth_mode} {
	
	  set cnn_id [_get_switch_handle $switch_name]
    set bCnn 1
    if {[string first "exp" $cnn_id] == -1} {
        Netgear_connect_switch $switch_name
        set cnn_id [_get_switch_handle $switch_name]
        set bCnn 0
    }
    set ret ""
  	exp_send -i $cnn_id "configure\r"
  	exp_sleep 1
    expect -i $cnn_id -re "#"
    exp_send -i $cnn_id "aaa authentication login $list_name $auth_mode\r"
    exp_sleep 1
    expect -i $cnn_id -re "#" 
    append ret $expect_out(buffer)       
  	exp_send -i $cnn_id "exit\r"
  	exp_sleep 1
    if {$bCnn == 0} {
        Netgear_disconnect_switch $switch_name
    }  	
    return $ret
}

#*******************************************************************************
#  Function Name	: _ntgrDelAaaLoginAuthList
#
#  Description    : This function is called to create aaa login authentication list
#         
#  Usage          : _ntgrDelAaaLoginAuthList <switch_name> <list_name>
#
#  Revision       : Create by kenddy xie 2011-08-22
#
#*******************************************************************************
proc _ntgrDelAaaLoginAuthList {switch_name list_name} {
	
	  set cnn_id [_get_switch_handle $switch_name]
    set bCnn 1
    if {[string first "exp" $cnn_id] == -1} {
        Netgear_connect_switch $switch_name
        set cnn_id [_get_switch_handle $switch_name]
        set bCnn 0
    }
    set ret ""
  	exp_send -i $cnn_id "configure\r"
  	exp_sleep 1
    expect -i $cnn_id -re "#"
    exp_send -i $cnn_id "no aaa authentication login $list_name\r"
    exp_sleep 1
    expect -i $cnn_id -re "#" 
    append ret $expect_out(buffer)       
  	exp_send -i $cnn_id "exit\r"
  	exp_sleep 1
    if {$bCnn == 0} {
        Netgear_disconnect_switch $switch_name
    }  	
    return $ret
}