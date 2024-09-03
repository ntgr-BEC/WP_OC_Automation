#!/bin/sh
################################################################################   
#
#  File Name		  : lib-mac-filter.tcl
#
#  Description      :
#         This TCL contains functions to configure mac filter
#
#  Revision History :
#        Date          Programmer        Description
#        -----------------------------------------------------------------------
#        01-Nov-10     Kenddy            Created
#
#
#
#
################################################################################


#*******************************************************************************
#  Function Name	: _ntgrAddMacFilterInstance
#
#  Description    : This function is called to add mac filter instance.
#         
#  Usage          : _ntgrAddProtectedGroupName <switch_name> <mac_addr> <vlan_id>
#
#  Revision       : Create by kenddy xie 2010-11-01
#
#*******************************************************************************
proc _ntgrAddMacFilterInstance {switch_name mac_addr vlan_id} {
	
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
    append ret $expect_out(buffer) 
    exp_send -i $cnn_id "macfilter $mac_addr $vlan_id\r"
    exp_sleep 1
    expect -i $cnn_id -re "#" 
    append ret $expect_out(buffer)       
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
#  Function Name	: _ntgrAddMacFilterInstance
#
#  Description    : This function is called to delete mac filter instance.
#         
#  Usage          : _ntgrDeleteMacFilterInstance <switch_name> <mac_addr> <vlan_id>
#
#  Revision       : Create by kenddy xie 2010-11-01
#
#*******************************************************************************
proc _ntgrDeleteMacFilterInstance {switch_name mac_addr vlan_id} {
	
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
    exp_send -i $cnn_id "no macfilter $mac_addr $vlan_id\r"
    exp_sleep 1
    expect -i $cnn_id -re "#" 
    append ret $expect_out(buffer)       
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
#  Function Name	: _ntgrEnableMacFilterOnInterface
#
#  Description    : This function is called to bind a protected group to specified interface
#         
#  Usage          : _ntgrEnableMacFilterOnInterface <switch_name> <port_list> <direction> <mac_addr> <vlan_id>
#                   
#
#  Note           : direction: addsrc | adddest
#                    (A) (Interface 1/0/12)#macfilter ?
#                    
#                    adddest                  Add a destination port to the MAC filter.
#                    addsrc                   Add a source port to the MAC filter.
#                    
#                    (A) (Interface 1/0/12)#macfilter adddest ?
#                   
#                   direction : all
#                    (A) #configure 
#                    
#                    (A) (Config)#no macfilter addsrc all 00:00:00:00:00:01 200 
#                    
#                    (A) (Config)#ex
#*******************************************************************************

proc _ntgrEnableMacFilterOnInterface {switch_name port_list direction mac_addr vlan_id} {
	
    set cnn_id [_get_switch_handle $switch_name]
    set bCnn 1
    if {[string first "exp" $cnn_id] == -1} {
        Netgear_connect_switch $switch_name
        set cnn_id [_get_switch_handle $switch_name]
        set bCnn 0
    }
    set reg {^[0-9a-zA-Z]+$}
	  set ret ""
  	exp_send -i $cnn_id "configure\r"
  	exp_sleep 1
  	expect -i $cnn_id -re "#"
  	if { $port_list == "all" } {
      	exp_send -i $cnn_id "macfilter $direction all $mac_addr $vlan_id\r"
        exp_sleep 1  	  
      	expect -i $cnn_id -re "#"
        append ret $expect_out(buffer)  	  
      	exp_send -i $cnn_id "exit\r"
    	  exp_sleep 1
        expect -i $cnn_id -re "#" 
        append ret $expect_out(buffer)       
  	} else {
      	foreach iport $port_list {
            set mflag  [regexp $reg $iport]
            if {$mflag} {set iport [_ntgrGetChannelLogicalIfName $cnn_id $iport]}
          	exp_send -i $cnn_id "interface $iport\r"
          	exp_sleep 1
          	expect -i $cnn_id -re "#"
            append ret $expect_out(buffer)
          	exp_send -i $cnn_id "macfilter $direction $mac_addr $vlan_id\r"
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
    }
    if {$bCnn == 0} {
        Netgear_disconnect_switch $switch_name
    }  	
    return $ret
}

#*******************************************************************************
#
#  Function Name	: _ntgrDisableMacFilterOnInterface
#
#  Description    : This function is called to unbind mac filter instance to specified interface
#         
#  Usage          : _ntgrEnableMacFilterOnInterface <switch_name> <port_list> <direction> <mac_addr> <vlan_id>
#                   
#
#  Note           : direction: addsrc | adddest
#                    (A) (Interface 1/0/12)#no macfilter ?
#                    
#                    adddest                  Add a destination port to the MAC filter.
#                    addsrc                   Add a source port to the MAC filter.
#                    
#                    (A) (Interface 1/0/12)#macfilter adddest ?
#                   
#                   direction : all
#                    (A) #configure 
#                    
#                    (A) (Config)#no macfilter addsrc all 00:00:00:00:00:01 200 
#                    
#                    (A) (Config)#ex
#*******************************************************************************

proc _ntgrDisableMacFilterOnInterface {switch_name port_list direction mac_addr vlan_id} {
	
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
  	if { $port_list == "all" } {
      	exp_send -i $cnn_id "no macfilter $direction all $mac_addr $vlan_id\r"
        exp_sleep 1  	  
      	expect -i $cnn_id -re "#"
        append ret $expect_out(buffer)  	  
      	exp_send -i $cnn_id "exit\r"
    	  exp_sleep 1
        expect -i $cnn_id -re "#" 
        append ret $expect_out(buffer)       
  	} else {
      	foreach iport $port_list {
          	exp_send -i $cnn_id "interface $iport\r"
          	exp_sleep 1
          	expect -i $cnn_id -re "#"
            append ret $expect_out(buffer)
          	exp_send -i $cnn_id "no macfilter $direction $mac_addr $vlan_id\r"
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
    }
    if {$bCnn == 0} {
        Netgear_disconnect_switch $switch_name
    }  	
    return $ret
}