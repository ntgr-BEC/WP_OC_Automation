#!/bin/sh
################################################################################   
#
#  File Name		  : lib-poe.tcl
#
#  Description      :
#         This TCL contains functions to configure poe
#
#  Revision History :
#        Date          Programmer        Description
#        -----------------------------------------------------------------------
#        20-Feb-2013   Andy Xie          Created
#
#
#
#
################################################################################

#*******************************************************************************
#  Function Name	: _ntgrSetIntfPoeEnable
#
#  Description    : This function is called to set poe admin enable for interface.
#         
#  Usage          : _ntgrSetIntfPoeEnable <switch_name> <port_list>
#                   
#
#  Revision       : Create by andy xie 2013-02-20
#
#*******************************************************************************
proc _ntgrSetIntfPoeEnable {switch_name port_list} {
	
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
      	exp_send -i $cnn_id "poe\r"
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
          	exp_send -i $cnn_id "poe\r"
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
#  Function Name	: _ntgrSetIntfPoeDisable
#
#  Description    : This function is called to set poe admin disable for interface.
#         
#  Usage          : _ntgrSetIntfPoeDisable <switch_name> <port_list>
#                   
#
#  Revision       : Create by andy xie 2013-02-20
#
#*******************************************************************************
proc _ntgrSetIntfPoeDisable {switch_name port_list} {
	
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
      	exp_send -i $cnn_id "no poe\r"
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
          	exp_send -i $cnn_id "no poe\r"
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
#  Function Name	: _ntgrSetIntfPoePowerLimitType
#
#  Description    : This function is called to set poe power mode for interface.
#         
#  Usage          : _ntgrSetIntfPoePowerLimitType <switch_name> <port_list> <limit_mode>
#                   
#                   <limit_mode> [class-based | none | user-defined]
#
#  Revision       : Create by andy xie 2013-02-20
#
#*******************************************************************************
proc _ntgrSetIntfPoePowerLimitType {switch_name port_list limit_mode} {
	
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
      	exp_send -i $cnn_id "poe power limit $limit_mode\r"
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
          	exp_send -i $cnn_id "poe power limit $limit_mode\r"
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
#  Function Name	: _ntgrRestoreIntfPoePowerLimitType
#
#  Description    : This function is called to restore poe power mode to default for interface.
#         
#  Usage          : _ntgrRestoreIntfPoePowerLimitType <switch_name> <port_list>
#                   
#
#  Revision       : Create by andy xie 2013-02-20
#
#*******************************************************************************
proc _ntgrRestoreIntfPoePowerLimitType {switch_name port_list} {
	
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
      	exp_send -i $cnn_id "no poe power limit\r"
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
          	exp_send -i $cnn_id "no poe power limit\r"
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
#  Function Name	: _ntgrSetIntfPoeUserDefinedLimitWt
#
#  Description    : This function is called to set poe power mode for interface.
#         
#  Usage          : _ntgrSetIntfPoeUserDefinedLimitWt <switch_name> <port_list> <limit_wt>
#                   
#                   <limit_wt> [3000-32000] (mW)
#
#  Revision       : Create by andy xie 2013-02-20
#
#*******************************************************************************
proc _ntgrSetIntfPoeUserDefinedLimitWt {switch_name port_list limit_wt} {
	
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
      	exp_send -i $cnn_id "poe power limit user-defined $limit_wt\r"
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
          	exp_send -i $cnn_id "poe power limit user-defined $limit_wt\r"
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
#  Function Name	: _ntgrRestoreIntfPoeUserDefinedLimitWt
#
#  Description    : This function is called to set poe power mode for interface.
#         
#  Usage          : _ntgrSetIntfPoeUserDefinedLimitWt <switch_name> <port_list>
#                   
#
#  Revision       : Create by andy xie 2013-02-20
#
#*******************************************************************************
proc _ntgrRestoreIntfPoeUserDefinedLimitWt {switch_name port_list} {
	
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
      	exp_send -i $cnn_id "no poe power limit user-defined\r"
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
          	exp_send -i $cnn_id "no poe power limit user-defined\r"
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
#  Function Name	: _ntgrSetIntfPoeDetectionMode
#
#  Description    : This function is called to set poe detection mode for interface.
#         
#  Usage          : _ntgrSetIntfPoePowerMode <switch_name> <port_list> <detect_mode>
#                   
#                   <detect_mode>: [auto | ieee | pre-ieee]
#
#  Revision       : Create by andy xie 2013-02-20
#
#*******************************************************************************
proc _ntgrSetIntfPoeDetectionMode {switch_name port_list detect_mode} {
	
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
      	exp_send -i $cnn_id "poe detection $detect_mode\r"
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
          	exp_send -i $cnn_id "poe detection $detect_mode\r"
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
#  Function Name	: _ntgrRestoreIntfPoeDetectionMode
#
#  Description    : This function is called to restore poe detect mode to default for interface.
#         
#  Usage          : _ntgrRestoreIntfPoeDetectionMode <switch_name> <port_list>
#                   
#
#  Revision       : Create by andy xie 2013-02-20
#
#*******************************************************************************
proc _ntgrRestoreIntfPoeDetectionMode {switch_name port_list} {
	
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
      	exp_send -i $cnn_id "no poe detection\r"
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
          	exp_send -i $cnn_id "no poe detection\r"
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
#  Function Name	: _ntgrSetIntfPoePriority
#
#  Description    : This function is called to set poe priority for interface.
#         
#  Usage          : _ntgrSetIntfPoePriority <switch_name> <port_list> <prio>
#                   
#                   <prio>: [crit | high | low]
#
#  Revision       : Create by andy xie 2013-02-20
#
#*******************************************************************************
proc _ntgrSetIntfPoePriority {switch_name port_list prio} {
	
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
      	exp_send -i $cnn_id "poe priority $prio\r"
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
          	exp_send -i $cnn_id "poe priority $prio\r"
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
#  Function Name	: _ntgrEnablePoeTrap
#
#  Description    : This function is called to enable poe traps
#         
#  Usage          : _ntgrEnablePoeTrap <switch_name>
#
#  Revision       : Create by Andy xie 2013-02-20
#
#*******************************************************************************
proc _ntgrEnablePoeTrap {switch_name} {
	
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
    exp_send -i $cnn_id "poe traps\r"
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
#  Function Name	: _ntgrDisablePoeTrap
#
#  Description    : This function is called to disable poe traps
#         
#  Usage          : _ntgrDisablePoeTrap <switch_name>
#
#  Revision       : Create by Andy xie 2013-02-20
#
#*******************************************************************************
proc _ntgrDisablePoeTrap {switch_name} {
	
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
    exp_send -i $cnn_id "no poe traps\r"
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
#  Function Name	: _ntgrEnablePoeTrap
#
#  Description    : This function is called to config poe power management mode
#         
#  Usage          : _ntgrEnablePoeTrap <switch_name> <unit_id> <m_mode>
#                 
#                 <unit_id> : [all | 1-8]
#                 <m_mode>  : [dynamic | static]
#
#  Revision       : Create by Andy xie 2013-02-20
#
#*******************************************************************************
proc _ntgrPoePowerManagementMode {switch_name unit_id m_mode} {
	
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
    exp_send -i $cnn_id "poe power management $unit_id $m_mode\r"
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
#  Function Name	: _ntgrSetPoeUsageThreshold
#
#  Description    : This function is called to set poe usage threshold.
#         
#  Usage          : _ntgrSetPoeUsageThreshold <switch_name> <unit_id> <thrsh_value>
#
#  Revision       : Create by Andy xie 2013-02-20
#
#*******************************************************************************
proc _ntgrSetPoeUsageThreshold {switch_name unit_id thrsh_value} {
	
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
    exp_send -i $cnn_id "poe usagethreshold $unit_id $thrsh_value\r"
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
#  Function Name	: _ntgrRestorePoeUsageThreshold
#
#  Description    : This function is called to restore poe usage threshold to default.
#         
#  Usage          : _ntgrRestorePoeUsageThreshold <switch_name> <unit_id>
#
#  Revision       : Create by Andy xie 2013-02-20
#
#*******************************************************************************
proc _ntgrRestorePoeUsageThreshold {switch_name unit_id} {
	
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
    exp_send -i $cnn_id "no poe usagethreshold $unit_id\r"
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
#  Function Name	: _ntgrPoeCheckPortInfo
#
#  Description    : This function is called to check port info is crrect or not.
#         
#  Usage          : _ntgrPoeCheckPortInfo <switch_name> <port> <expect_class> <expect_power> <expect_status>
#
#  Revision       : Create by jim xie 2013-12-11
#
#*******************************************************************************
proc _ntgrPoeCheckPortInfo {switch_name port expect_class expect_power expect_status} {
	set real_class {}
	set real_power {}
	set real_status {}
	  sleep 20
	  Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
        
    set output ""
	  #exp_send -i $cnn_id "show poe port info $port\r"
    #exp_sleep 1
    exp_send -i $cnn_id "show poe port info $port \r"
    exp_sleep 1
    expect -i $cnn_id -re "show poe port info $port"
    expect {
        -i $cnn_id -re "#" { append output $expect_out(buffer) }
        -i $cnn_id -re "More--" {
            append output $expect_out(buffer)
            exp_send -i $cnn_id " "
            exp_sleep 1
            exp_continue
        }   
    }
	
	
    set output $expect_out(buffer)    
    set output_list [split $output "\n"]

    foreach item_str $output_list {
        set item_str [string trim $item_str]

        if {[regexp "^$port" $item_str]} {

			set real_class [lindex $item_str 3]
			set real_power [lindex $item_str 4]
      # modify by bo.shi begain 
       set special_model 0
       set intRelease {}; set intVersion {}; set intMaintenance {}; set intPatch {} ; set strModel {}
      _ntgrGetVersionDetailedbyConnectionID $cnn_id intRelease intVersion intMaintenance intPatch strModel
       if { $intRelease >= 11 } {
	        set special_model 1
	      } 
      
      if {$special_model} {
	        set real_status [lindex $item_str 7]
	    } else {
          set real_status [lindex $item_str 8]
	    }
	    # modify by bo.shi for end
			

			break
        }
    }
	Netgear_log_file "lib-poe.tcl" "Port:$port ,Expect class is: $expect_class ,Expect power is: $expect_power ,Expect status is: $expect_status ."
    Netgear_log_file "lib-poe.tcl" "Port:$port ,Real class is: $real_class ,Real power is: $real_power ,Real status is: $real_status ."
	
	set ret 1
	if {$real_class != $expect_class} {
	     set ret 0
		 Netgear_log_file "lib-poe.tcl" "Error:  PoE Class is not expect value."
	}
	if {[string tolower $expect_status] != [string tolower $real_status]} {
	     set ret 0
		 Netgear_log_file "lib-poe.tcl" "Error:  PoE port Status is not expect value."
	}
    # modify by bo.shi begain 
	if {[expr abs([expr $real_power-$expect_power])]>400} {
	     set ret 0
		 Netgear_log_file "lib-poe.tcl" "Error:  OutPut Power is not expect value."
	}
    # modify by bo.shi for end
    Netgear_disconnect_switch $switch_name
	return $ret
}



#*******************************************************************************
#  Function Name	: _ntgrPoeCheckPortIfSupportPOEPlus
#
#  Description    : This function is called to check port info support poe+/upoe or not.
#         
#  Usage          : _ntgrPoeCheckPortIfSupportPOEPlus <switch_name> <port> 
#
#  Revision       : Create by bo shi 2015-6-8
#
#*******************************************************************************
proc _ntgrPoeCheckPortIfSupportPOEPlus {switch_name port} {

	set max_power {}

	  sleep 20
	  Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
        
    set output ""
	  #exp_send -i $cnn_id "show poe port info $port\r"
    #exp_sleep 1
    exp_send -i $cnn_id "show poe port info $port \r"
    exp_sleep 1
    expect -i $cnn_id -re "show poe port info $port"
    expect {
        -i $cnn_id -re "#" { append output $expect_out(buffer) }
        -i $cnn_id -re "More--" {
            append output $expect_out(buffer)
            exp_send -i $cnn_id " "
            exp_sleep 1
            exp_continue
        }   
    }
	
	
    set output $expect_out(buffer)    
    set output_list [split $output "\n"]

    foreach item_str $output_list {
        set item_str [string trim $item_str]

        if {[regexp "^$port" $item_str]} {

			set max_power [lindex $item_str 2]
      
			break
        }
    }
	Netgear_log_file "lib-poe.tcl" "Port:$port ,maxpower=$max_power ."

	set ret 0
	if {$max_power >20000} {
	     set ret 1
		 Netgear_log_file "lib-poe.tcl" "POE+/UPOE supported."
	}

    Netgear_disconnect_switch $switch_name
	return $ret
}


#*******************************************************************************
#
#  Function Name  : _ntgrPoeGetPortListTotalPowerConsumed
#
#  Description    : This function is called to GET port Power Consumed(mw).
#
#  Usage          : _ntgrPoeGetPortListTotalPowerConsumed <switch_name if_list>
#
#*******************************************************************************
proc _ntgrPoeGetPortListTotalPowerConsumed {switch_name if_list} {

    set totalPowerConsumed 0
    set cnn_id [_get_switch_handle $switch_name]
    set bCnn 1
    if {[string first "exp" $cnn_id] == -1} {
        Netgear_connect_switch $switch_name
        set cnn_id [_get_switch_handle $switch_name]
        set bCnn 0
    }
    Netgear_log_file "lib-global-command.tcl" "Geting interface $if_list POE  consumed"
  
    foreach if_name $if_list {
          set real_power {}
          set output ""
        exp_send -i $cnn_id "show poe port info $if_name\r"
        exp_sleep 1
        expect -i $cnn_id -re "show poe port info $if_name"
        expect {
               -i $cnn_id -re "#" { append output $expect_out(buffer) }
               -i $cnn_id -re "More--" {
            append output $expect_out(buffer)
            exp_send -i $cnn_id " "
            exp_sleep 1
            exp_continue
         }   
      }
          
    set output $expect_out(buffer)    
    set output_list [split $output "\n"]

    foreach item_str $output_list {
        set item_str [string trim $item_str]

        if {[regexp "^$if_name" $item_str]} {
			set real_power [lindex $item_str 4]

			break
        }
    }
    
    set  totalPowerConsumed  [ expr $totalPowerConsumed + $real_power ]
    
    }
    
        Netgear_disconnect_switch $switch_name
    
    
    return $totalPowerConsumed
}



#*******************************************************************************
#
#  Function Name  : _ntgrPoeGetTotalPowerConsumed
#
#  Description    : This function is called to GET total Power Consumed(mw).
#
#  Usage          : _ntgrPoeGetTotalPowerConsumed <switch_name>
#
#*******************************************************************************
proc _ntgrPoeGetTotalPowerConsumed {switch_name} {

    set retStr ""
    set consume_power  0
    set cnn_id [_get_switch_handle $switch_name]
    set bCnn 1
    if {[string first "exp" $cnn_id] == -1} {
        Netgear_connect_switch $switch_name
        set cnn_id [_get_switch_handle $switch_name]
        set bCnn 0
    }
    Netgear_log_file "lib-global-command.tcl" "Geting Total POE  consumed"
    
        exp_send -i $cnn_id "show poe\r"
        exp_sleep 1
        expect -i $cnn_id -re "show poe"
        expect {
               -i $cnn_id -re "#" { append output $expect_out(buffer) }
               -i $cnn_id -re "More--" {
            append output $expect_out(buffer)
            exp_send -i $cnn_id " "
            exp_sleep 1
            exp_continue
         }   
      }
       set output $expect_out(buffer)
       set tmpList [split $output \n]
       foreach iList $tmpList {
        if {[regexp -all -nocase "consume" $iList]} {    
            #get unit is mW
            set modename [_get_switch_model $switch_name]
            if {[regexp -nocase {XCM.?|M4300|M4200} $modename]} {
                set consume_power [expr [lindex $iList 3]*1000]
            } else {
                set consume_power [lindex $iList 3]
            }
        } 
    }
    Netgear_disconnect_switch $switch_name
    return  $consume_power
}
#*******************************************************************************
#
#  Function Name  : _ntgrPoeGetTotalPowerConsumed
#
#  Description    : This function is called to GET total Power (Main AC).
#
#  Usage          : _ntgrPoeGetTotalPowerConsumed <switch_name>
#
#*******************************************************************************
proc _ntgrPoeGetTotalPower {switch_name} {

    set retStr ""
    set consume_power  0
    set cnn_id [_get_switch_handle $switch_name]
    set bCnn 1
    if {[string first "exp" $cnn_id] == -1} {
        Netgear_connect_switch $switch_name
        set cnn_id [_get_switch_handle $switch_name]
        set bCnn 0
    }
    Netgear_log_file "lib-global-command.tcl" "Geting Total POE  consumed"
    
        exp_send -i $cnn_id "show poe\r"
        exp_sleep 1
        expect -i $cnn_id -re "show poe"
        expect {
               -i $cnn_id -re "#" { append output $expect_out(buffer) }
               -i $cnn_id -re "More--" {
            append output $expect_out(buffer)
            exp_send -i $cnn_id " "
            exp_sleep 1
            exp_continue
         }   
      }
       set output $expect_out(buffer)
       set tmpList [split $output \n]
       foreach iList $tmpList {
        if {[regexp -all -nocase "(Main AC)..." $iList]} {
            #get unit is mW
            set modename [_get_switch_model $switch_name]
            if {[regexp -nocase {XCM.?|M4300|M4200} $modename]} {
                set consume_power [expr [lindex $iList 3]*1000]
            } else {
                set consume_power [lindex $iList 4]
            }
        } 
    }
    Netgear_disconnect_switch $switch_name
    return  $consume_power
}
#*******************************************************************************
#
#  Function Name  : _ntgrPoeGetTotalPowerConsumed
#
#  Description    : This function is called to GET total Power (Main AC).
#
#  Usage          : _ntgrPoeGetTotalPowerConsumed <switch_name>
#
#*******************************************************************************
proc _ntgrPoeGetThresholdPower {switch_name} {

    set retStr ""
    set consume_power  0
    set cnn_id [_get_switch_handle $switch_name]
    set bCnn 1
    if {[string first "exp" $cnn_id] == -1} {
        Netgear_connect_switch $switch_name
        set cnn_id [_get_switch_handle $switch_name]
        set bCnn 0
    }
    Netgear_log_file "lib-global-command.tcl" "Geting Total POE  consumed"
    
        exp_send -i $cnn_id "show poe\r"
        exp_sleep 1
        expect -i $cnn_id -re "show poe"
        expect {
               -i $cnn_id -re "#" { append output $expect_out(buffer) }
               -i $cnn_id -re "More--" {
            append output $expect_out(buffer)
            exp_send -i $cnn_id " "
            exp_sleep 1
            exp_continue
         }   
      }
       set output $expect_out(buffer)
       set tmpList [split $output \n]
       foreach iList $tmpList {
        if {[regexp -all -nocase "Threshold Power" $iList]} {
			 set flag [lindex $iList end]
			 if {$flag == "mw"} {
				set consume_power [expr [lindex $iList end-1]/1000]
			 } else {
			  set consume_power [lindex $iList end-1]
			 }
        } 
    }
    Netgear_disconnect_switch $switch_name
    return  $consume_power
}
