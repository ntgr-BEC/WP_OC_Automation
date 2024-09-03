#!/bin/sh
################################################################################   
#
#  File Name		  : lib-protected-port.tcl
#
#  Description      :
#         This TCL contains functions to configure protected port
#
#  Revision History :
#        Date          Programmer        Description
#        -----------------------------------------------------------------------
#        19-Oct-06     Kenddy            Created
#
#
#
#
################################################################################


#*******************************************************************************
#  Function Name	: _ntgrAddProtectedGroup
#
#  Description    : This function is called to configure protected group name
#         
#  Usage          : _ntgrAddProtectedGroupName <switch_name> <group_id> <group_name>
#
#  Revision       : Create by kenddy xie 2010-10-19
#
#*******************************************************************************
proc _ntgrAddProtectedGroupName {switch_name group_id group_name} {
	
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
    expect -i $cnn_id "#" {}  
    exp_send -i $cnn_id "switchport protected $group_id name $group_name\r"
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
#
#  Function Name	: _ntgrEnableProtectedGroupOnInterface
#
#  Description    : This function is called to bind a protected group to specified interface
#         
#  Usage          : _ntgrEnableProtectedGroupOnInterface <switch_name> <group_id> <port_list> 
#                   
#
#  Note           : group_id: group id,such as 0|1|2
#
#*******************************************************************************

proc _ntgrEnableProtectedGroupOnInterface {switch_name group_id port_list} {
	
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
    expect -i $cnn_id "#" {}  	
  	foreach iport $port_list {
    	exp_send -i $cnn_id "interface $iport\r"
    	exp_sleep 1
    	expect -i $cnn_id -re "#"
      append ret $expect_out(buffer)
    	exp_send -i $cnn_id "switchport protected $group_id\r"
      exp_sleep 1
      expect -i $cnn_id -re "#" 
      append ret $expect_out(buffer) 
    	exp_send -i $cnn_id "exit\r"
  	  exp_sleep 1
      expect -i $cnn_id -re "#" 
      append ret $expect_out(buffer)  	  
    }
  	exp_send -i $cnn_id "exit\r"
  	exp_sleep 1
    expect -i $cnn_id -re "#" 
    append ret $expect_out(buffer)
    if {$bCnn == 0} {
        Netgear_disconnect_switch $switch_name
    }  	
    return $ret
}

#*******************************************************************************
#
#  Function Name	: _ntgrDisableProtectedGroupOnInterface
#
#  Description    : This function is called to unbind a protected group to specified interface
#         
#  Usage          : _ntgrDisableProtectedGroupOnInterface <switch_name> <group_id> <port_list> 
#                   
#
#  Note           : group_id: group id,such as 0|1|2
#
#*******************************************************************************

proc _ntgrDisableProtectedGroupOnInterface {switch_name group_id port_list} {

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
    expect -i $cnn_id "#" {}  
  	foreach iport $port_list {
    	exp_send -i $cnn_id "interface $iport\r"
    	exp_sleep 1
    	expect -i $cnn_id -re "#"    	
    	exp_send -i $cnn_id "no switchport protected $group_id\r"
      exp_sleep 1
      expect -i $cnn_id -re "#" 
      append ret $expect_out(buffer)       
    	exp_send -i $cnn_id "exit\r"
  	  exp_sleep 1
      expect -i $cnn_id -re "#" 
      append ret $expect_out(buffer)   	  
    }
  	exp_send -i $cnn_id "exit\r"
  	exp_sleep 1
    expect -i $cnn_id -re "#" 
    append ret $expect_out(buffer) 
    if {$bCnn == 0} {
        Netgear_disconnect_switch $switch_name
    }  	
    return $ret
}