#!/bin/sh
################################################################################   
#
#  File Name		  : lib-pmirror.tcl
#
#  Description      :
#         This TCL contains functions to configure Port Mirroring
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
#  Function Name	: _ntgrPortMirrorEnable
#
#  Description    : This function is called to enable Port Mirroring on the Switch
#         
#  Usage          : _ntgrPortMirrorEnable <switch_name> 
#
#
#*******************************************************************************
proc _ntgrPortMirrorEnable {switch_name} {
	set connection_id [_get_switch_handle $switch_name]
   	exp_send -i $connection_id "configure\r"
  	sleep 1
 	exp_send -i $connection_id "monitor session 1 mode\r"
 	sleep 1
 	exp_send -i $connection_id "exit\r"
}

#*******************************************************************************
#  Function Name	: _ntgrPortMirrorDisable
#
#  Description    : This function is called to disable Port Mirroring on Switch
#         
#  Usage          : _ntgrPortMirrorDisable <switch_name> 
#
#
#*******************************************************************************
proc _ntgrPortMirrorDisable {switch_name} {
	set connection_id [_get_switch_handle $switch_name]
	exp_send -i $connection_id "configure\r"
  	sleep 1
 	exp_send -i $connection_id "no monitor session 1 mode\r"
 	sleep 1
 	exp_send -i $connection_id "exit\r"
}

#*******************************************************************************
#  Function Name	: _ntgrAddProbePort
#
#  Description    : This function is called to add the probe port for port mirroring
#         
#  Usage          : _ntgrAddProbePort <switch_name> <port>
#
#
#*******************************************************************************
proc _ntgrAddProbePort {switch_name port} {
	set connection_id [_get_switch_handle $switch_name]
   	exp_send -i $connection_id "configure\r"
  	sleep 1
  	exp_send -i $connection_id "monitor session 1 destination interface $port\r"
  	sleep 1
 	exp_send -i $connection_id "exit\r"
}

#*******************************************************************************
#  Function Name	: _ntgrDeleteProbePort
#
#  Description    : This function is called to delete the probe port for port mirroring
#         
#  Usage          : _ntgrDeleteProbePort <switch_name>
#
#
#*******************************************************************************
proc _ntgrDeleteProbePort {switch_name} {
	set connection_id [_get_switch_handle $switch_name]
   	exp_send -i $connection_id "configure\r"
  	sleep 1
  	exp_send -i $connection_id "no monitor session 1 destination interface \r"
 	sleep 1
 	exp_send -i $connection_id "exit\r"
}

#*******************************************************************************
#  Function Name	: _ntgrAddMirrorPort
#
#  Description    : This function is called to add the mirror port for port mirroring
#         
#  Usage          : _ntgrAddMirrorPort <switch_name> <port_list>
#
#
#*******************************************************************************
proc _ntgrAddMirrorPort {switch_name port_list} {
	set connection_id [_get_switch_handle $switch_name]
   	exp_send -i $connection_id "configure\r"
  	sleep 1
	foreach port $port_list {
	  	exp_send -i $connection_id "monitor session 1 source interface $port\r"
	  	sleep 1
	}
 	exp_send -i $connection_id "exit\r"
}

#*******************************************************************************
#  Function Name	: _ntgrDeleteMirrorPort
#
#  Description    : This function is called to delete the mirror port for port mirroring
#         
#  Usage          : _ntgrDeleteMirrorPort <switch_name> <port_list>
#
#
#*******************************************************************************
proc _ntgrDeleteMirrorPort {switch_name port_list} {
	set connection_id [_get_switch_handle $switch_name]
   	exp_send -i $connection_id "configure\r"
  	sleep 1
	foreach port $port_list {
	  	exp_send -i $connection_id "no monitor session 1 source interface $port\r"
 		sleep 1
	}
 	exp_send -i $connection_id "exit\r"
}


#*******************************************************************************
#  Function Name	: _ntgrConfigurePortMirror
#
#  Description    : This function is called to configure port mirroring
#         
#  Usage          : _ntgrConfigurePortMirror <switch>
#
#
#*******************************************************************************
proc _ntgrConfigurePortMirror {switch} {
	Netgear_connect_switch $switch

	set port_mirror_status [_ntgrGetPortMirrorStatus $switch]
	if {$port_mirror_status == "enable"} {
		Netgear_log_file "lib-pmirror.tcl" "Enabling Port Mirroring on Switch $switch"
		_ntgrPortMirrorEnable $switch
	} elseif {$port_mirror_status =="disable"} {
		Netgear_log_file "lib-pmirror.tcl" "Disabling Port Mirroring on Switch $switch"
		_ntgrPortMirrorDisable $switch
	} else {
		Netgear_log_file "lib-pmirror.tcl" "Error!Status should be either <enable> or <disable>"
	}

	set probe_port [_ntgrGetPortMirrorProbePort $switch]
	if {[llength $probe_port]} {
		Netgear_log_file "lib-pmirror.tcl" "Configuring Probe port - $probe_port"
		_ntgrAddProbePort $switch $probe_port
	} else {
		Netgear_log_file "lib-pmirror.tcl" "Removing Probe port - $probe_port"
		_ntgrDeleteProbePort $switch $probe_port
	}

	set mirror_add_list [_ntgrGetPortMirrorAddList $switch]
	set mirror_del_list [_ntgrGetPortMirrorDelList $switch]

	if {[llength $mirror_add_list] != 0}	{
		Netgear_log_file "lib-pmirror.tcl" "Adding Mirror port - $mirror_add_list"
		_ntgrAddMirrorPort $switch $mirror_add_list
	} 
	if {[llength $mirror_del_list] != 0} {
		Netgear_log_file "lib-pmirror.tcl" "Removing Mirror port - $mirror_del_list"
		_ntgrDeleteMirrorPort $switch $mirror_del_list
	}

	Netgear_disconnect_switch $switch
}

#*******************************************************************************
#  Function Name	: _ntgrAddPortMirror
#
#  Description    : This function is called to configure port mirroring
#         
#  Usage          : _ntgrAddPortMirror <switch_name> <srcInterface> <dstInterface> <direction>
#
#  Revision       : Create by kenddy xie 2010-09-08
#
#*******************************************************************************
proc _ntgrAddPortMirror {switch_name srcInterface dstInterface direction} {
	
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
  	  	
    exp_send -i $cnn_id "monitor session 1 mode\r"
    exp_sleep 1
    expect -i $cnn_id -re "#" 
    append ret $expect_out(buffer)       
    
    if {$direction == "both"} {
        exp_send -i $cnn_id "monitor session 1 source interface $srcInterface\r"
      	exp_sleep 1
        expect -i $cnn_id -re "#" 
        append ret $expect_out(buffer)
    } elseif { $direction == "rx" } {
        exp_send -i $cnn_id "monitor session 1 source interface $srcInterface rx\r"
      	exp_sleep 1
        expect -i $cnn_id -re "#" 
        append ret $expect_out(buffer)      
    } elseif { $direction == "tx" } {
        exp_send -i $cnn_id "monitor session 1 source interface $srcInterface tx\r"
      	exp_sleep 1
        expect -i $cnn_id -re "#" 
        append ret $expect_out(buffer)      
    } else {
        puts "direction parameter is Error: $direction"
    }  

    exp_send -i $cnn_id "monitor session 1 destination interface $dstInterface\r"
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
#  Function Name	: _ntgrDeletePortMirror
#
#  Description    : This function is called to configure port mirroring
#         
#  Usage          : _ntgrDeletePortMirror <switch_name> <srcInterface> <dstInterface>
#
#  Revision       : Create by kenddy xie 2010-09-08
#
#*******************************************************************************
proc _ntgrDeletePortMirror {switch_name srcInterface dstInterface} {

    set dut_software_version [_ntgrGetSoftwareVersion $switch_name]
#	  puts "$dut_software_version"
    set ver [string trim $dut_software_version]
    regexp {^\d+} $ver ver_first

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
  	  	
    exp_send -i $cnn_id "no monitor session 1 mode\r"
    exp_sleep 1
    expect -i $cnn_id -re "#" 
    append ret $expect_out(buffer)       

    exp_send -i $cnn_id "no monitor session 1 source interface $srcInterface\r"
    exp_sleep 1
    expect -i $cnn_id -re "#" 
    append ret $expect_out(buffer)
    
    if {$ver_first >= 9} {
        #---------- for version 9.x -----------#
        exp_send -i $cnn_id "no monitor session 1 destination interface\r"          
    } else {
        #---------- for version 8.x -----------#
        exp_send -i $cnn_id "no monitor session 1 destination interface $dstInterface\r"
    }
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
#  Function Name	: _ntgrAddVlanMirror
#
#  Description    : This function is called to configure port mirroring with soure vlan
#         
#  Usage          : _ntgrAddPortMirror <switch_name> <srcVlanid> <dstInterface> 
#
#  Revision       : Create by boshi 2015-09-11
#
#*******************************************************************************
proc _ntgrAddVlanMirror {switch_name srcVlanid dstInterface} {
	
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
  	  	
    exp_send -i $cnn_id "monitor session 1 mode\r"
    exp_sleep 1
    expect -i $cnn_id -re "#" 
    append ret $expect_out(buffer)       
    
        exp_send -i $cnn_id "monitor session 1 source vlan $srcVlanid\r"
      	exp_sleep 1
        expect -i $cnn_id -re "#" 
        append ret $expect_out(buffer)
    

    exp_send -i $cnn_id "monitor session 1 destination interface $dstInterface\r"
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
#  Function Name	: _ntgrRemoveVlanMirror
#
#  Description    : This function is called to remove port mirroring with soure vlan
#         
#  Usage          : _ntgrAddPortMirror <switch_name> 
#
#  Revision       : Create by boshi  2015-09-11
#
#*******************************************************************************
proc _ntgrRemoveVlanMirror {switch_name} {

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
  	  	  
    exp_send -i $cnn_id "no monitor session 1 \r"
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
#  Function Name   : CALntgrEnableRemoteSpan
#
#  Description     : This function is called to enable remote_span on vlan
#         
#  Usage           : CALntgrEnableRemoteSpan <switch_name> <vlan_id>
#
#*******************************************************************************
proc CALntgrEnableRemoteSpan {switch_name vlan_id} {
    ntgrCLIExecute $switch_name "configure"
    ntgrCLIExecute $switch_name "vlan $vlan_id"
    ntgrCLIExecute $switch_name "remote-span"
    ntgrCLIExecute $switch_name "exit"
    ntgrCLIExecute $switch_name "exit"
}

#*******************************************************************************
#  Function Name   : CALntgrDisableRemoteSpan
#
#  Description     : This function is called to disable remote_span on vlan
#         
#  Usage           : CALntgrDisableRemoteSpan <switch_name> <vlan_id>
#
#*******************************************************************************
proc CALntgrDisableRemoteSpan {switch_name vlan_id} {
    ntgrCLIExecute $switch_name "configure"
    ntgrCLIExecute $switch_name "vlan $vlan_id"
    ntgrCLIExecute $switch_name "no remote-span"
    ntgrCLIExecute $switch_name "exit"
    ntgrCLIExecute $switch_name "exit"
}

#*******************************************************************************
#  Function Name   : CALAddRemoteVlanMirror
#
#  Description     : This function is called to add remote-span on vlan
#         
#  Usage           : CALAddRemoteVlanMirror <switch_name> <srcFlag> <srcInterface> <dstFlag> <dstInterface> <reflectorPort>
#
#*******************************************************************************
proc CALAddRemoteVlanMirror {switch_name srcFlag srcInterface dstFlag dstInterface reflectorPort} {
    ntgrCLIExecute $switch_name "configure"
    ntgrCLIExecute $switch_name "monitor session 1 mode"
    if {$srcFlag == "port"} {
        ntgrCLIExecute $switch_name "monitor session 1 source interface $srcInterface"
    } elseif {$srcFlag == "vlan"} {
        ntgrCLIExecute $switch_name "monitor session 1 source vlan $srcInterface"
    } else {
        ntgrCLIExecute $switch_name "monitor session 1 source remote vlan $srcInterface"
    }
    
    if {$dstFlag == "port"} {
        ntgrCLIExecute $switch_name "monitor session 1 destination interface $dstInterface"
    } else {
        if {$reflectorPort != ""} {
            ntgrCLIExecute $switch_name "monitor session 1 destination remote vlan $dstInterface reflector-port $reflectorPort"
        } else {
            ntgrCLIExecute $switch_name "monitor session 1 destination remote vlan $dstInterface"
        }
    }
    ntgrCLIExecute $switch_name "exit"
}

#*******************************************************************************
#  Function Name   : CALDeleteRemoteVlanMirror
#
#  Description     : This function is called to delete remote-span on vlan
#         
#  Usage           : CALDeleteRemoteVlanMirror <switch_name> <srcFlag> <srcInterface> <dstFlag> <dstInterface> <reflectorPort>
#
#*******************************************************************************
proc CALDeleteRemoteVlanMirror {switch_name srcFlag srcInterface dstFlag dstInterface reflectorPort} {
    ntgrCLIExecute $switch_name "configure"
    ntgrCLIExecute $switch_name "no monitor session 1 mode"
    if {$srcFlag == "port"} {
        ntgrCLIExecute $switch_name "no monitor session 1 source interface $srcInterface"
    } elseif {$srcFlag == "vlan"} {
        ntgrCLIExecute $switch_name "no monitor session 1 source vlan $srcInterface"
    } else {
        ntgrCLIExecute $switch_name "no monitor session 1 source remote vlan"
    }
    
    if {$dstFlag == "port"} {
        ntgrCLIExecute $switch_name "no monitor session 1 destination interface"
    } else {
        ntgrCLIExecute $switch_name "no monitor session 1 destination remote vlan"
    }
    ntgrCLIExecute $switch_name "exit"
}

#*******************************************************************************
#  Function Name   : CALntgrEnableMonitorFilter
#
#  Description     : This function is called to enable monitor filter
#         
#  Usage           : CALntgrEnableMonitorFilter <switch_name> <acl_type> <acl_name>
#
#*******************************************************************************
proc CALntgrEnableMonitorFilter {switch_name acl_type acl_name} {
    ntgrCLIExecute $switch_name "configure"
    if {$acl_type == "mac"} {
        ntgrCLIExecute $switch_name "monitor session 1 filter mac access-group $acl_name"
    } elseif {$acl_type == "ip"} {
        ntgrCLIExecute $switch_name "monitor session 1 filter ip access-group $acl_name"
    }
    ntgrCLIExecute $switch_name "exit"
}

#*******************************************************************************
#  Function Name   : CALntgrDisableMonitorFilter
#
#  Description     : This function is called to disable monitor filter
#         
#  Usage           : CALntgrDisableMonitorFilter <switch_name> <acl_type> <acl_name>
#
#*******************************************************************************
proc CALntgrDisableMonitorFilter {switch_name acl_type acl_name} {
    ntgrCLIExecute $switch_name "configure"
    if {$acl_type == "mac"} {
        ntgrCLIExecute $switch_name "no monitor session 1 filter mac access-group"
    } elseif {$acl_type == "ip"} {
        ntgrCLIExecute $switch_name "no monitor session 1 filter ip access-group"
    }
    ntgrCLIExecute $switch_name "exit"
}

