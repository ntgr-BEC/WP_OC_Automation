#!/bin/sh
################################################################################   
#
#  File Name		  : lib-jframe.tcl
#
#  Description      :
#         This TCL contains functions to configure Jumbo frames
#
#  Revision History :
#        Date          Programmer        Description
#        -----------------------------------------------------------------------
#        15-Jun-06     Rajeswari        Created
#
#
#
#
################################################################################

#*******************************************************************************
#  Function Name	: _ntgrJumbFrameEnableL2Switch
#
#  Description    : This function is called to configure jumbo frame on L2 Switch
#         
#  Usage          : _ntgrJumbFrameEnableL2Switch <switch_name> <mtu>
#
#
#*******************************************************************************
proc _ntgrJumbFrameEnableL2Switch  {switch_name mtu} {
	set connection_id [_get_switch_handle $switch_name]
   	exp_send -i $connection_id "configure\r"
  	sleep 1
 	exp_send -i $connection_id "mtu $mtu\r"
 	sleep 1
 	exp_send -i $connection_id "exit\r"
 	sleep 1
}

#*******************************************************************************
#  Function Name	: _ntgrJumbFrameEnableL3Switch
#
#  Description    : This function is called to configure jumbo frame on L3 switch
#         
#  Usage          : _ntgrJumbFrameEnableL3Switch <switch_name> <port> <mtu>
#
#
#*******************************************************************************
proc _ntgrJumbFrameEnableL3Switch {switch_name port mtu} {
	set connection_id [_get_switch_handle $switch_name]
   	exp_send -i $connection_id "configure\r"
  	after 500
 	exp_send -i $connection_id "interface $port\r"
  	after 500
 	exp_send -i $connection_id "mtu $mtu\r"
  	after 500
 	exp_send -i $connection_id "exit\r"
  	after 500
 	exp_send -i $connection_id "exit\r"
  	after 500
}

#*******************************************************************************
#  Function Name	: _ntgrJumbFrameDisableL2Switch
#
#  Description    : This function is called to configure jumbo frame on L2 Switch
#         
#  Usage          : _ntgrJumbFrameDisableL2Switch <switch_name>
#
#
#*******************************************************************************
proc _ntgrJumbFrameDisableL2Switch  {switch_name} {
	set connection_id [_get_switch_handle $switch_name]
   	exp_send -i $connection_id "configure\r"
  	sleep 1
 	exp_send -i $connection_id "no mtu\r"
 	sleep 1
 	exp_send -i $connection_id "exit\r"
 	sleep 1
}

#*******************************************************************************
#  Function Name	: _ntgrJumbFrameDisableL3Switch
#
#  Description    : This function is called to configure jumbo frame on L3 switch
#         
#  Usage          : _ntgrJumbFrameDisableL3Switch <switch_name> <port>
#
#
#*******************************************************************************
proc _ntgrJumbFrameDisableL3Switch {switch_name port} {
	set connection_id [_get_switch_handle $switch_name]
   	exp_send -i $connection_id "configure\r"
  	sleep 1
 	exp_send -i $connection_id "interface $port\r"
 	sleep 1
 	exp_send -i $connection_id "no mtu\r"
 	sleep 1
 	exp_send -i $connection_id "exit\r"
 	sleep 1
 	exp_send -i $connection_id "exit\r"
 	sleep 1
}

#*******************************************************************************
#  Function Name	: _ntgrJumboFrameConfig
#
#  Description    : This function is called to configure Jumbo frame
#         
#  Usage          : _ntgrJumboFrameConfig <switch> 
#
#
#*******************************************************************************
proc _ntgrJumboFrameConfig {switch} {
	Netgear_connect_switch $switch
	set switch_type [_ntgrGetJFrameSwitchType $switch]
	set mtu_list   [_ntgrGetJFrameMtuList $switch]
	foreach mtu_info $mtu_list {
		set interface [lindex $mtu_info 0]
		set mtu       [lindex $mtu_info 1]
		if {$switch_type == "layer3"} {
			Netgear_log_file "lib-jframe.tcl" "Configuring Jumbo Frame on Layer3 Switch $switch"
			if {[string first "POCH" $interface] != -1} {
				set connection_id [_get_switch_handle $switch]
				set actual_if [_ntgrGetChannelLogicalIfName $connection_id $interface]
			} elseif {[string first "/" $interface] != -1}  {
				set actual_if $interface
			} else {
				Netgear_log_file "lib-jframe.tcl" "Error! Interface not defined"
			}
			if {$mtu == "default"} {
				Netgear_log_file "lib-jframe.tcl" "Disabling Jumbo frames on interface $actual_if"
				_ntgrJumbFrameDisableL3Switch $switch $actual_if
			} else {
				Netgear_log_file "lib-jframe.tcl" "Enabling Jumbo frames on interface $actual_if with mtu $mtu"
				_ntgrJumbFrameEnableL3Switch $switch $actual_if $mtu
			}
		} elseif {$switch_type == "layer2"} {
			Netgear_log_file "lib-jframe.tcl" "Configuring Jumbo Frame on Layer2 Switch $switch"
			if {$mtu == "default"} {
				Netgear_log_file "lib-jframe.tcl" "Disabled Jumbo frames"
				_ntgrJumbFrameDisableL2Switch $switch
			} else {
				Netgear_log_file "lib-jframe.tcl" "Enabled Jumbo frames "
				_ntgrJumbFrameEnableL2Switch $switch $mtu
			}
		} else {
			Netgear_log_file "lib-jframe.tcl" "Error! Switch Type can be <layer2> or <layer3>"
		}
	}
	Netgear_disconnect_switch $switch
}

#*******************************************************************************
#
#  Function Name  : _ntgrJumboFrameDisable
#
#  Description    : This function is called to disable all configured jumbo
#                   support for a switch.
#
#  Usage          : _ntgrJumboFrameDisable <switch_name>
#
#*******************************************************************************
proc _ntgrJumboFrameDisable {switch} {    
    Netgear_log_file "lib-jframe.tcl" "Disable jumbo frame support on switch $switch"
    Netgear_connect_switch $switch
    set switch_type [_ntgrGetJFrameSwitchType $switch]
    if { $switch_type == "layer2" } {
        _ntgrJumbFrameDisableL2Switch $switch
    } else {
        set mtu_list [_ntgrGetJFrameMtuList $switch]
        foreach mtu_info $mtu_list {
            set interface [lindex $mtu_info 0]
            if {[string first "POCH" $interface] != -1} {
                set connection_id [_get_switch_handle $switch]
                set interface [_ntgrGetChannelLogicalIfName $connection_id $interface]
            }
            _ntgrJumbFrameDisableL3Switch $switch $interface
        }
    }
    Netgear_disconnect_switch $switch
}

#*******************************************************************************
#
#  Function Name  : _ntgrJbFrameEnalbe
#
#  Description	  : This function configures Jumbo frame
#	  
#  Usage	        : _ntgrJbFrameEnalbe <switch_name> <mtu_interface_list>
#
#  Example	      : _ntgrJbFrameEnalbe $DUT $mtu_list
#
#  Revision       :
#			              Date        Programmer        Description
#			              2010/07/08  Kenddy            Newly created   
#
#*******************************************************************************
proc _ntgrJbFrameEnalbe {switch_name mtu_interface_list} {
	
	Netgear_connect_switch $switch_name
	set connection_id [_get_switch_handle $switch_name]
	set count [llength $mtu_interface_list]
	global JumboOnLayer2
	for {set i 0} {$i < $count} {incr i} {
      set interface [lindex [lindex $mtu_interface_list $i] 0]
      set frame_size [lindex [lindex $mtu_interface_list $i] 1]
      exp_send -i $connection_id "configure\r"
      sleep 1
      if { [info exists JumboOnLayer2] && ( $JumboOnLayer2 ==	1) } {
      
			    if { $frame_size != "default" }	{
             exp_send -i $connection_id "mtu $frame_size\r"
			    } else {
             exp_send -i $connection_id "no mtu\r"				
			    }
      } else {
			    if { $frame_size != "default" }	{
				      exp_send -i $connection_id "interface $interface\r"
				      sleep 1
              exp_send -i $connection_id "mtu $frame_size\r"
			    } else {
				      exp_send -i $connection_id "interface $interface\r"
				      sleep 1
              exp_send -i $connection_id "no mtu\r"				
			    }			
	    }
      exp_send -i $connection_id "exit\r"
      sleep 1
      exp_send -i $connection_id "exit\r"
      sleep 1
	}
	Netgear_disconnect_switch $switch_name
}

#*******************************************************************************
#  Function Name	: _ntgrSetIpMtuOnVlanInterface
#
#  Description    : This function is called to set interface vlan mtu.
#         
#  Usage          : _ntgrSetIpMtuOnVlanInterface <switch_name> <mtu_interface_list>
#                   eg: set mtu_interface_list "100 9000 200 4000"
#
#  History        : Create by kenddy xie 2011-09-21
# 
#*******************************************************************************
proc _ntgrSetIpMtuOnVlanInterface {switch_name mtu_interface_list} {
	
	  set cnn_id [_get_switch_handle $switch_name]
    set bCnn 1
    if {[string first "exp" $cnn_id] == -1} {
        Netgear_connect_switch $switch_name
        set cnn_id [_get_switch_handle $switch_name]
        set bCnn 0
    }
    set ret ""
    foreach {vlanId mtu_size} $mtu_interface_list {
      
      	exp_send -i $cnn_id "configure\r"
      	exp_sleep 1
        expect -i $cnn_id -re "#"
        expect -i $cnn_id "#" {}  
        append ret $expect_out(buffer) 
        exp_send -i $cnn_id "interface vlan $vlanId\r"
        exp_sleep 1
        expect -i $cnn_id -re "#" 
        append ret $expect_out(buffer) 
        exp_send -i $cnn_id "ip mtu $mtu_size\r"
        exp_sleep 1
        expect -i $cnn_id -re "#" 
        append ret $expect_out(buffer)
      	exp_send -i $cnn_id "exit\r"
      	exp_sleep 1
        expect -i $cnn_id -re "#" 
        append ret $expect_out(buffer)       
      	exp_send -i $cnn_id "exit\r"
      	exp_sleep 1
        expect -i $cnn_id -re "#"
    }
    if {$bCnn == 0} {
        Netgear_disconnect_switch $switch_name
    }  	
    return $ret
}

#*******************************************************************************
#  Function Name	: _ntgrRestoreIpMtuOnVlanInterface
#
#  Description    : This function is called to restore interface vlan mtu.
#         
#  Usage          : _ntgrRestoreIpMtuOnVlanInterface <switch_name> <vlan_interface_list>
#
#  History        : Create by kenddy xie 2011-09-21
# 
#*******************************************************************************
proc _ntgrRestoreIpMtuOnVlanInterface {switch_name vlan_interface_list} {
	
	  set cnn_id [_get_switch_handle $switch_name]
    set bCnn 1
    if {[string first "exp" $cnn_id] == -1} {
        Netgear_connect_switch $switch_name
        set cnn_id [_get_switch_handle $switch_name]
        set bCnn 0
    }
    set ret ""
    foreach {vlanId} $vlan_interface_list {
      
      	exp_send -i $cnn_id "configure\r"
      	exp_sleep 1
        expect -i $cnn_id -re "#"
        expect -i $cnn_id "#" {}  
        append ret $expect_out(buffer) 
        exp_send -i $cnn_id "interface vlan $vlanId\r"
        exp_sleep 1
        expect -i $cnn_id -re "#" 
        append ret $expect_out(buffer) 
        exp_send -i $cnn_id "no ip mtu\r"
        exp_sleep 1
        expect -i $cnn_id -re "#" 
        append ret $expect_out(buffer)
      	exp_send -i $cnn_id "exit\r"
      	exp_sleep 1
        expect -i $cnn_id -re "#" 
        append ret $expect_out(buffer)       
      	exp_send -i $cnn_id "exit\r"
      	exp_sleep 1
        expect -i $cnn_id -re "#"
    }
    if {$bCnn == 0} {
        Netgear_disconnect_switch $switch_name
    }  	
    return $ret
}
#*******************************************************************************
#
#  Function Name  :	_ntgrJbFrameDisable
#
#  Description	  :	This function disable Jumbo frame
#	  
#  Usage	        :	_ntgrJbFrameDisable <switch_name> <interface_list>
#	
#  Example	      :	_ntgrJbFrameDisable $DUT $interface_list
#
#  Revision       :
#			              Date        Programmer        Description
#			              2010/07/08  Kenddy            Newly created   
#
#*******************************************************************************
proc _ntgrJbFrameDisable {switch_name interface_list} {
	
	Netgear_connect_switch $switch_name
	set connection_id [_get_switch_handle $switch_name]
	set count [llength $interface_list]
	global JumboOnLayer2
  for {set i 0} {$i < $count} {incr i} {
		set interface [lindex [lindex $interface_list $i] 0]
    exp_send -i $connection_id "configure\r"
    sleep 1
		if { [info exists JumboOnLayer2] && ( $JumboOnLayer2 ==	1) } {
        exp_send -i $connection_id "no mtu\r"				
		} else {
        exp_send -i $connection_id "interface $interface\r"
			  sleep 1
        exp_send -i $connection_id "no mtu\r"							
		}
    exp_send -i $connection_id "exit\r"
    sleep 1
    exp_send -i $connection_id "exit\r"
    sleep 1
	}
	Netgear_disconnect_switch $switch_name
}
