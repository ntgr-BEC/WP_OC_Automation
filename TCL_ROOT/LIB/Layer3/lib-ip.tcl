#!/bin/sh
################################################################################   
#
#  File Name		  : lib-ip.tcl
#
#  Description      :
#         This TCL contains functions to configure IP
#
#  Revision History :
#        Date          Programmer        Description
#        -----------------------------------------------------------------------
#        15-Jun-06     Nina	         Created
#
#
#
#
################################################################################

#*******************************************************************************
#  Function Name	: _ntgrIpRoutingEnable
#
#  Description    : This function is called to enable IP Routing on the Switch
#         
#  Usage          : _ntgrIpRoutingEnable <switch_name> 
#
#
#*******************************************************************************
proc _ntgrIpRoutingEnable {switch_name} {
  
    set connection_id [_get_switch_handle $switch_name]
    set bCnn 1
    if {[string first "exp" $connection_id] == -1} {
        Netgear_connect_switch $switch_name
        set connection_id [_get_switch_handle $switch_name]
        set bCnn 0
    }
   	exp_send -i $connection_id "configure\r"
  	sleep 1
   	exp_send -i $connection_id "ip routing\r"
   	sleep 1
   	exp_send -i $connection_id "exit\r"
    if {$bCnn == 0} {
        Netgear_disconnect_switch $switch_name
    }
}

#*******************************************************************************
#  Function Name	: _ntgrIpRoutingDisable
#
#  Description    : This function is called to disable IP routing on Switch
#         
#  Usage          : _ntgrIpRoutingDisable <switch_name> 
#
#
#*******************************************************************************
proc _ntgrIpRoutingDisable {switch_name} {

    set connection_id [_get_switch_handle $switch_name]
    set bCnn 1
    if {[string first "exp" $connection_id] == -1} {
        Netgear_connect_switch $switch_name
        set connection_id [_get_switch_handle $switch_name]
        set bCnn 0
    }
  	exp_send -i $connection_id "configure\r"
    sleep 1
   	exp_send -i $connection_id "no ip routing\r"
   	sleep 1
   	exp_send -i $connection_id "exit\r"
   	sleep 1
    if {$bCnn == 0} {
        Netgear_disconnect_switch $switch_name
    }
    
}

#*******************************************************************************
#  Function Name	: _ntgrIpRoutingEnablePerPort
#
#  Description    : This function is called to enable IP Routing Per port
#         
#  Usage          : _ntgrIpRoutingEnablePerPort <switch_name> <port>
#
#
#*******************************************************************************
proc _ntgrIpRoutingEnablePerPort {switch_name port} {

    set connection_id [_get_switch_handle $switch_name]
    set bCnn 1
    if {[string first "exp" $connection_id] == -1} {
        Netgear_connect_switch $switch_name
        set connection_id [_get_switch_handle $switch_name]
        set bCnn 0
    }
   	exp_send -i $connection_id "configure\r"
  	sleep 1
  	exp_send -i $connection_id "interface $port\r"
  	sleep 1
  	exp_send -i $connection_id "routing\r"
   	sleep 1
   	exp_send -i $connection_id "exit\r"
   	sleep 1
   	exp_send -i $connection_id "exit\r"
  	sleep 1
	  if {$bCnn == 0} {
        Netgear_disconnect_switch $switch_name
    }
}

#*******************************************************************************
#  Function Name	: _ntgrIpRoutingDisablePerPort
#
#  Description    : This function is called to disable IP Routing per port
#         
#  Usage          : _ntgrIpRoutingDisablePerPort <switch_name> <port>
#
#
#*******************************************************************************
proc _ntgrIpRoutingDisablePerPort {switch_name port} {

    set connection_id [_get_switch_handle $switch_name]
    set bCnn 1
    if {[string first "exp" $connection_id] == -1} {
        Netgear_connect_switch $switch_name
        set connection_id [_get_switch_handle $switch_name]
        set bCnn 0
    }
  	exp_send -i $connection_id "configure\r"
    sleep 1
  	exp_send -i $connection_id "interface $port\r"
    sleep 1
   	exp_send -i $connection_id "no routing\r"
   	sleep 1
   	exp_send -i $connection_id "exit\r"
  	sleep 1
   	exp_send -i $connection_id "exit\r"
   	sleep 1
	  if {$bCnn == 0} {
        Netgear_disconnect_switch $switch_name
    } 	
}

#*******************************************************************************
#  Function Name	: _ntgrIpDhcpEnablePerPort
#
#  Description    : This function is called to enable ip dhcp per port
#         
#  Usage          : _ntgrIpDhcpEnablePerPort <switch_name> <port>
#
#
#*******************************************************************************
proc _ntgrIpDhcpEnablePerPort {switch_name port} {

    set connection_id [_get_switch_handle $switch_name]
    set bCnn 1
    if {[string first "exp" $connection_id] == -1} {
        Netgear_connect_switch $switch_name
        set connection_id [_get_switch_handle $switch_name]
        set bCnn 0
    }
  	exp_send -i $connection_id "configure\r"
    sleep 1
  	exp_send -i $connection_id "interface $port\r"
    sleep 1
   	exp_send -i $connection_id "ip address dhcp\r"
   	sleep 1
   	exp_send -i $connection_id "exit\r"
  	sleep 1
   	exp_send -i $connection_id "exit\r"
   	sleep 1
	  if {$bCnn == 0} {
        Netgear_disconnect_switch $switch_name
    } 	
}

#*******************************************************************************
#  Function Name	: _ntgrIpDhcpDisablePerPort
#
#  Description    : This function is called to disable ip dhcp per port
#         
#  Usage          : _ntgrIpDhcpDisablePerPort <switch_name> <port>
#
#
#*******************************************************************************
proc _ntgrIpDhcpDisablePerPort {switch_name port} {

    set connection_id [_get_switch_handle $switch_name]
    set bCnn 1
    if {[string first "exp" $connection_id] == -1} {
        Netgear_connect_switch $switch_name
        set connection_id [_get_switch_handle $switch_name]
        set bCnn 0
    }
  	exp_send -i $connection_id "configure\r"
    sleep 1
  	exp_send -i $connection_id "interface $port\r"
    sleep 1
   	exp_send -i $connection_id "no ip address dhcp\r"
   	sleep 1
   	exp_send -i $connection_id "exit\r"
  	sleep 1
   	exp_send -i $connection_id "exit\r"
   	sleep 1
	  if {$bCnn == 0} {
        Netgear_disconnect_switch $switch_name
    } 	
}

#*******************************************************************************
#
#  Function Name  : _ntgrGetIpAddressOnPort
#
#  Description    : This function is get ip address on port.
#
#  Usage          : _ntgrGetIpAddressOnPort <switch_name> <port>
#
#*******************************************************************************
proc _ntgrGetIpAddressOnPort {switch_name port} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    exp_send -i $cnn_id "show ip interface brief\r"
    exp_sleep 1
    expect -i $cnn_id -re "show ip interface brief"
    expect -i $cnn_id -re "#"
    
    set output $expect_out(buffer)    
    set output_list [split $output "\n"]
    
    set index_start 0
    set index_end 0
    set expect_str ""
    foreach item_str $output_list {
        set item_str [string trim $item_str]
        if {[regexp {^-+} $item_str]} {
            set flag_str "[lindex $item_str 0] [lindex $item_str 1]"
            set index_start [expr [string length $flag_str] + 1]
            append flag_str " [lindex $item_str 2]"
            set index_end [expr [string length $flag_str] - 1]
        }
        
        if {[regexp $port $item_str]} {
            set expect_str $item_str
            break
        }
    }
    set result_str [string trim [string range $expect_str $index_start $index_end]]

    Netgear_log_file "lib-ip.tcl" "ip address on $switch_name: $result_str"
    Netgear_disconnect_switch $switch_name

    return $result_str
}

#*******************************************************************************
#
#  Function Name  : _ntgrGetIpMaskOnPort
#
#  Description    : This function is get ip mask on port.
#
#  Usage          : _ntgrGetIpMaskOnPort <switch_name> <port>
#
#*******************************************************************************
proc _ntgrGetIpMaskOnPort {switch_name port} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    exp_send -i $cnn_id "show ip interface brief\r"
    exp_sleep 1
    expect -i $cnn_id -re "show ip interface brief"
    expect -i $cnn_id -re "#"
    
    set output $expect_out(buffer)    
    set output_list [split $output "\n"]
    
    set index_start 0
    set index_end 0
    set expect_str ""
    foreach item_str $output_list {
        set item_str [string trim $item_str]
        if {[regexp {^-+} $item_str]} {
            set flag_str "[lindex $item_str 0] [lindex $item_str 1] [lindex $item_str 2]"
            set index_start [expr [string length $flag_str] + 2]
            append flag_str " [lindex $item_str 3]"
            set index_end [expr [string length $flag_str]]
        }
        
        if {[regexp $port $item_str]} {
            set expect_str $item_str
            break
        }
    }
	
    set result_str [string trim [string range $expect_str $index_start $index_end]]

    Netgear_log_file "lib-ip.tcl" "ip mask on $switch_name: $result_str"
    Netgear_disconnect_switch $switch_name
    
	 return $result_str
}
  
  
#*******************************************************************************
#  Function Name	: _ntgrAddIpAddressOnPort
#
#  Description    : This function is called to assign IP Address on the port
#         
#  Usage          : _ntgrAddIpAddressOnPort <switch_name> <port> <ip_addr> <ip_mask>
#
#
#*******************************************************************************
proc _ntgrAddIpAddressOnPort {switch_name port ip_addr ip_mask} {

    set connection_id [_get_switch_handle $switch_name]
    set bCnn 1
    if {[string first "exp" $connection_id] == -1} {
        Netgear_connect_switch $switch_name
        set connection_id [_get_switch_handle $switch_name]
        set bCnn 0
    }
   	exp_send -i $connection_id "configure\r"
  	sleep 1
  	exp_send -i $connection_id "interface $port\r"
  	sleep 1
  	exp_send -i $connection_id "ip address $ip_addr $ip_mask\r"
   	sleep 1
   	exp_send -i $connection_id "exit\r"
   	sleep 1
   	exp_send -i $connection_id "exit\r"
    sleep 1
	  if {$bCnn == 0} {
        Netgear_disconnect_switch $switch_name
    }
}

#*******************************************************************************
#  Function Name	: _ntgrAddIpAddressOnPort
#
#  Description    : This function is called to assign secondary IP Address on the port
#         
#  Usage          : _ntgrAddIpAddressOnPort <switch_name> <port> <ip_addr> <ip_mask>
#
#  Note           : Added by kenddy.xie
#*******************************************************************************
proc _ntgrAddSecondIpAddressOnPort {switch_name port ip_addr ip_mask} {

    set connection_id [_get_switch_handle $switch_name]
    set bCnn 1
    if {[string first "exp" $connection_id] == -1} {
        Netgear_connect_switch $switch_name
        set connection_id [_get_switch_handle $switch_name]
        set bCnn 0
    }
   	exp_send -i $connection_id "configure\r"
  	sleep 1
  	exp_send -i $connection_id "interface $port\r"
  	sleep 1
  	exp_send -i $connection_id "ip address $ip_addr $ip_mask secondary\r"
   	sleep 1
   	exp_send -i $connection_id "exit\r"
   	sleep 1
   	exp_send -i $connection_id "exit\r"
   	sleep 1
	  if {$bCnn == 0} {
        Netgear_disconnect_switch $switch_name
    }
    
}


#*******************************************************************************
#  Function Name	: _ntgrDeleteIpAddressOnPort
#
#  Description    : This function is called to delete IP Address on the port
#         
#  Usage          : _ntgrDeleteIpAddressOnPort <switch_name> <port> <ip_addr> <ip_mask>
#
#
#*******************************************************************************
proc _ntgrDeleteIpAddressOnPort {switch_name port ip_addr ip_mask} {

    set connection_id [_get_switch_handle $switch_name]
    set bCnn 1
    if {[string first "exp" $connection_id] == -1} {
        Netgear_connect_switch $switch_name
        set connection_id [_get_switch_handle $switch_name]
        set bCnn 0
    }
   	exp_send -i $connection_id "configure\r"
  	sleep 1
  	exp_send -i $connection_id "interface $port\r"
  	sleep 1
  	exp_send -i $connection_id "no ip address $ip_addr $ip_mask\r"
   	sleep 1
   	exp_send -i $connection_id "exit\r"
   	sleep 1
   	exp_send -i $connection_id "exit\r"
  	sleep 1
	  if {$bCnn == 0} {
        Netgear_disconnect_switch $switch_name
    }
}

#*******************************************************************************
#  Function Name	: _ntgrAddIpRoute
#
#  Description    : This function is called to add IP route
#         
#  Usage          : _ntgrAddIpRoute <switch_name> <ip_addr> <ip_mask> <next_hop>
#
#
#*******************************************************************************
proc _ntgrAddIpRoute {switch_name ip_addr ip_mask next_hop} {

    set connection_id [_get_switch_handle $switch_name]
    set bCnn 1
    if {[string first "exp" $connection_id] == -1} {
        Netgear_connect_switch $switch_name
        set connection_id [_get_switch_handle $switch_name]
        set bCnn 0
    }
   	exp_send -i $connection_id "configure\r"
  	sleep 1
  	exp_send -i $connection_id "ip route $ip_addr $ip_mask $next_hop\r"
   	sleep 1
   	exp_send -i $connection_id "exit\r"
   	sleep 1
	  if {$bCnn == 0} {
        Netgear_disconnect_switch $switch_name
    }
}
#*******************************************************************************
#  Function Name	: _ntgrAddDefaultIpRoute
#
#  Description    : This function is called to add default IP route
#         
#  Usage          : _ntgrAddDefaultIpRoute <switch_name> <next_hop>
#
#
#*******************************************************************************
proc _ntgrAddDefaultIpRoute {switch_name next_hop} {

    set connection_id [_get_switch_handle $switch_name]
    set bCnn 1
    if {[string first "exp" $connection_id] == -1} {
        Netgear_connect_switch $switch_name
        set connection_id [_get_switch_handle $switch_name]
        set bCnn 0
    }
   	exp_send -i $connection_id "configure\r"
  	sleep 1
  	set next_hop_ip = lindex [next_hop 1]
  	if {$next_hop_ip != "default"} {
      	exp_send -i $connection_id "ip route default $next_hop\r"
       	sleep 1
   	}
   	exp_send -i $connection_id "exit\r"
    sleep 1
	  if {$bCnn == 0} {
        Netgear_disconnect_switch $switch_name
    }
}

#*******************************************************************************
#  Function Name	: _ntgrDeleteIpRoute
#
#  Description    : This function is called to delete IP Route 
#         
#  Usage          : _ntgrDeleteIpRoute <switch_name> <ip_addr> <ip_mask> <next_hop>
#
#
#*******************************************************************************
proc _ntgrDeleteIpRoute {switch_name ip_addr ip_mask next_hop} {

    set connection_id [_get_switch_handle $switch_name]
    set bCnn 1
    if {[string first "exp" $connection_id] == -1} {
        Netgear_connect_switch $switch_name
        set connection_id [_get_switch_handle $switch_name]
        set bCnn 0
    }
   	exp_send -i $connection_id "configure\r"
  	sleep 1
  	exp_send -i $connection_id "no ip route $ip_addr $ip_mask $next_hop\r"
   	sleep 1
   	exp_send -i $connection_id "exit\r"
  	sleep 1
	  if {$bCnn == 0} {
        Netgear_disconnect_switch $switch_name
    }
}

#*******************************************************************************
#  Function Name	: _ntgrIpConfigRouting 
#
#  Description    : This function is called to configure IP paramaters
#         
#  Usage          : _ntgrIpConfigRouting <switch>
#
#
#*******************************************************************************
proc _ntgrIpConfigRouting {switch} {
#	Netgear_connect_switch $switch
	set ip_status [_ntgrGetIpGlobalStatus $switch]
	if {$ip_status == "enable"} {
		Netgear_log_file "lib-ip.tcl" "Enabling IP Routing on Switch $switch"
		_ntgrIpRoutingEnable $switch
	} elseif {$ip_status =="disable"} {
		Netgear_log_file "lib-ip.tcl" "Disabling IP Routing on Switch $switch"
		_ntgrIpRoutingDisable $switch
	} else {
		Netgear_log_file "lib-ip.tcl" "Error!Status should be either <enable> or <disable>"
	}
#	Netgear_disconnect_switch $switch
}

#*******************************************************************************
#  Function Name	: _ntgrIpConfigStaticRoute 
#
#  Description    : This function is called to configure IP Route
#         
#  Usage          : _ntgrIpConfigStaticRoute <switch>
#
#
#*******************************************************************************
proc _ntgrIpConfigStaticRoute {switch} {
	#Netgear_connect_switch $switch
	set route_list [_ntgrGetIpRouteList $switch]
	foreach route $route_list {
		if {[lindex $route 0] == "add"} {
			Netgear_log_file "lib-ip.tcl" "Adding IP Route on Switch $switch"
			Netgear_log_file "lib-ip.tcl" "IP Address = [lindex $route 1]"
			Netgear_log_file "lib-ip.tcl" "IP Mask = [lindex $route 2]"
			Netgear_log_file "lib-ip.tcl" "Next Hop = [lindex $route 3]"
			_ntgrAddIpRoute $switch [lindex $route 1] [lindex $route 2] [lindex $route 3]
		} elseif {[lindex $route 0] == "delete"} {
			Netgear_log_file "lib-ip.tcl" "Deleting IP Route on Switch $switch"
			Netgear_log_file "lib-ip.tcl" "IP Address = [lindex $route 1]"
			Netgear_log_file "lib-ip.tcl" "IP Mask = [lindex $route 2]"
			Netgear_log_file "lib-ip.tcl" "Next Hop = [lindex $route 3]"
			_ntgrDeleteIpRoute $switch [lindex $route 1] [lindex $route 2] [lindex $route 3]
		} elseif {[lindex $route 0] == "default"} {
			Netgear_log_file "lib-ip.tcl" "Addeing Default Route on Switch $switch"
			Netgear_log_file "lib-ip.tcl" "Next Hop = [lindex $route 1]"
			_ntgrAddDefaultIpRoute $switch [lindex $route 1] 
		} else {
			Netgear_log_file "lib-ip.tcl" "Error!Route Action should be either <add> or <delete>"
		}
	}
	#Netgear_disconnect_switch $switch
}

#*******************************************************************************
#  Function Name	: _ntgrIpConfigRoutingPerPort 
#
#  Description    : This function is called to configure IP per port
#         
#  Usage          : _ntgrIpConfigRoutingPerPort <switch>
#
#
#*******************************************************************************
proc _ntgrIpConfigRoutingPerPort {switch} {
#	Netgear_connect_switch $switch
	set interface_list [_ntgrGetIpInterfaceList $switch]
	foreach interface $interface_list {
		if {[lindex $interface 1] == "enable"} {
			Netgear_log_file "lib-ip.tcl" "Enabling IP Routing on Port = [lindex $interface 0] on Switch $switch"
			_ntgrIpRoutingEnablePerPort $switch [lindex $interface 0]
		} elseif {[lindex $interface 1] =="disable"} {
			Netgear_log_file "lib-ip.tcl" "Disabling IP Routing on Port = [lindex $interface 0] on Switch $switch"
			_ntgrIpRoutingDisablePerPort $switch [lindex $interface 0]
		} else {
			Netgear_log_file "lib-ip.tcl" "Error!Status should be either <enable> or <disable>"
		}
		set ip_info [lindex $interface 2]
		if {[lindex $ip_info 0] == "add"} {
			Netgear_log_file "lib-ip.tcl" "Adding IP Address on Switch $switch"
			Netgear_log_file "lib-ip.tcl" "Interface = [lindex $interface 0]"
			Netgear_log_file "lib-ip.tcl" "IP Address = [lindex $ip_info 1]"
			Netgear_log_file "lib-ip.tcl" "IP Mask = [lindex $ip_info 2]"
			_ntgrAddIpAddressOnPort $switch [lindex $interface 0] [lindex $ip_info 1] [lindex $ip_info 2]
		} elseif {[lindex $ip_info 0] == "delete"} {
			Netgear_log_file "lib-ip.tcl" "Deleting IP Address on Switch $switch"
			Netgear_log_file "lib-ip.tcl" "Interface = [lindex $interface 0]"
			Netgear_log_file "lib-ip.tcl" "IP Address = [lindex $ip_info 1]"
			Netgear_log_file "lib-ip.tcl" "IP Mask = [lindex $ip_info 2]"
			_ntgrDeleteIpAddressOnPort $switch [lindex $interface 0] [lindex $ip_info 1] [lindex $ip_info 2]
		} else {
			Netgear_log_file "lib-ip.tcl" "Error!Action should be either <add> or <delete>"
		}
	}
#	Netgear_disconnect_switch $switch
}

#*******************************************************************************
#  Function Name	: _ntgrGetLocalMACfromInterface 
#
#  Description    : This function is to get Mac address from IP port
#         
#  Usage          : _ntgrGetLocalMACfromInterface <switch> <interface>
#
#
#*******************************************************************************

proc _ntgrGetLocalMACfromInterface  {switch_name interface} {
  
  set ret 0
  set connection_id [_get_switch_handle $switch_name]
  set bCnn 1
  if {[string first "exp" $connection_id] == -1} {
      Netgear_connect_switch $switch_name
      set connection_id [_get_switch_handle $switch_name]
      set bCnn 0
  }
	
	set interface [lindex $interface 0]
	
	exp_send -i $connection_id "show ip interface $interface\r"
  exp_sleep 1
  expect -i $connection_id -re "show ip interface $interface"
  expect -i $connection_id -re "#"   

  set token0 "MAC address"
  set token1 "Encapsulation Type"
  set start [string first $token0 $expect_out(buffer)] 
  set end [string first $token1 $expect_out(buffer)] 
  set tmpbuff [string range $expect_out(buffer) $start [expr $end-2]]
  #Add regsub by kenddy, for subst the newline.
  regsub -all {\n} $tmpbuff "" tmpbuff  
  set token0 "."
  set start [string last $token0 $tmpbuff] 
  set ret [string range $tmpbuff [expr $start+2] end]
 	
  if {$bCnn == 0} {
      Netgear_disconnect_switch $switch_name
  }
  return [string trim $ret " "]
}

#*******************************************************************************
#  Function Name	: _ntgrSetStaticARP
#
#  Description    : This function is to set switch static arp
#         
#  Usage          :  _ntgrSetStaticARP <switch> <ipaddress> <macaddr>
#
#
#*******************************************************************************
proc _ntgrSetStaticARP {switch_name ipaddress macaddr} {

  set connection_id [_get_switch_handle $switch_name]
  set bCnn 1
  if {[string first "exp" $connection_id] == -1} {
      Netgear_connect_switch $switch_name
      set connection_id [_get_switch_handle $switch_name]
      set bCnn 0
  }
	exp_send -i $connection_id "configure\r"
	sleep 1
  expect -i $connection_id -re "#"
  exp_send -i $connection_id "arp $ipaddress $macaddr\r"
  sleep 1
  exp_send -i $connection_id "exit\r"
  sleep 1
  if {$bCnn == 0} {
      Netgear_disconnect_switch $switch_name
  }
}

#*******************************************************************************
#  Function Name	: _ntgrDisableStaticARP
#
#  Description    : This function is to disable switch static arp
#         
#  Usage          :  _ntgrDisableStaticARP <switch> <ipaddress> 
#
#
#*******************************************************************************
proc _ntgrDisableStaticARP {switch_name ipaddress} {

  set connection_id [_get_switch_handle $switch_name]
  set bCnn 1
  if {[string first "exp" $connection_id] == -1} {
      Netgear_connect_switch $switch_name
      set connection_id [_get_switch_handle $switch_name]
      set bCnn 0
  }
	exp_send -i $connection_id "configure\r"
	sleep 1
  expect -i $connection_id -re "#"
  exp_send -i $connection_id "no arp $ipaddress \r"
  sleep 1
  exp_send -i $connection_id "exit\r"
  sleep 1
  if {$bCnn == 0} {
      Netgear_disconnect_switch $switch_name
  }
  
}

#*******************************************************************************
#  Function Name	: _ntgrConfigArpCacheSize
#
#  Description    : This function is called to config arp cache size.
#         
#  Usage          : _ntgrConfigArpCacheSize <switch_name> <cache_size>
#
#  Revision       : Create by kenddy xie 2011-05-30
#
#*******************************************************************************
proc _ntgrConfigArpCacheSize {switch_name cache_size} {
	
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
    exp_send -i $cnn_id "arp cachesize $cache_size\r"
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
#  Function Name	: _ntgrRestoreArpCacheSize
#
#  Description    : This function is called to restore arp cache size to default.
#         
#  Usage          : _ntgrRestoreArpCacheSize <switch_name>
#
#  Revision       : Create by kenddy xie 2011-05-30
#
#*******************************************************************************
proc _ntgrRestoreArpCacheSize {switch_name} {
	
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
    exp_send -i $cnn_id "no arp cachesize\r"
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
#  Function Name	: _ntgrEnableArpDynamicRenew
#
#  Description    : This function is called to enable arp dynamic renew.
#         
#  Usage          : _ntgrEnableArpDynamicRenew <switch_name>
#
#  Revision       : Create by kenddy xie 2011-05-30
#
#*******************************************************************************
proc _ntgrEnableArpDynamicRenew {switch_name} {
	
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
    exp_send -i $cnn_id "arp dynamicrenew\r"
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
#  Function Name	: _ntgrDisableArpDynamicRenew
#
#  Description    : This function is called to disable arp dynamic renew.
#         
#  Usage          : _ntgrDisableArpDynamicRenew <switch_name>
#
#  Revision       : Create by kenddy xie 2011-05-30
#
#*******************************************************************************
proc _ntgrDisableArpDynamicRenew {switch_name} {
	
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
    exp_send -i $cnn_id "no arp dynamicrenew\r"
    exp_sleep 1
    expect -i $cnn_id -re "\(y/n\)" {
  			exp_send -i $cnn_id "y"
		}
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
#  Function Name	: _ntgrConfigArpResponseTime
#
#  Description    : This function is called to config arp response time.
#         
#  Usage          : _ntgrConfigArpResponseTime <switch_name> <resp_time>
#
#  Revision       : Create by kenddy xie 2011-05-30
#
#*******************************************************************************
proc _ntgrConfigArpResponseTime {switch_name resp_time} {
	
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
    exp_send -i $cnn_id "arp resptime $resp_time\r"
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
#  Function Name	: _ntgrConfigArpResponseTime
#
#  Description    : This function is called to config arp response time to default.
#         
#  Usage          : _ntgrConfigArpResponseTime <switch_name>
#
#  Revision       : Create by kenddy xie 2011-05-30
#
#*******************************************************************************
proc _ntgrRestoreArpResponseTime {switch_name} {
	
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
    exp_send -i $cnn_id "no arp resptime\r"
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
#  Function Name	: _ntgrConfigArpRetriesCount
#
#  Description    : This function is called to config the counter of maximum requests for retries.
#         
#  Usage          : _ntgrConfigArpRetriesCount <switch_name> <retr_time>
#
#  Revision       : Create by kenddy xie 2011-05-30
#
#*******************************************************************************
proc _ntgrConfigArpRetriesCount {switch_name retr_time} {
	
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
    exp_send -i $cnn_id "arp retries $retr_time\r"
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
#  Function Name	: _ntgrRestoreArpRetriesCount
#
#  Description    : This function is called to restore arp retries counter to default.
#         
#  Usage          : _ntgrRestoreArpRetriesCount <switch_name>
#
#  Revision       : Create by kenddy xie 2011-05-30
#
#*******************************************************************************
proc _ntgrRestoreArpRetriesCount {switch_name} {
	
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
    exp_send -i $cnn_id "no arp retries\r"
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
#  Function Name	: _ntgrConfigArpAgeout
#
#  Description    : This function is called to config arp entry ageout time.
#         
#  Usage          : _ntgrConfigArpAgeout <switch_name> <ageout_time>
#
#  Revision       : Create by kenddy xie 2011-05-30
#
#*******************************************************************************
proc _ntgrConfigArpAgeout {switch_name ageout_time} {
	
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
    exp_send -i $cnn_id "arp timeout $ageout_time\r"
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
#  Function Name	: _ntgrRestoreArpAgeout
#
#  Description    : This function is called to restroe arp entry ageout time.
#         
#  Usage          : _ntgrRestoreArpAgeout <switch_name>
#
#  Revision       : Create by kenddy xie 2011-05-30
#
#*******************************************************************************
proc _ntgrRestoreArpAgeout {switch_name} {
	
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
    exp_send -i $cnn_id "no arp timeout\r"
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
#  Function Name	: _ntgrAddStaticArp
#
#  Description    : This function is called to add arp entry .
#         
#  Usage          : _ntgrAddStaticArp <switch_name> <ip_add> <mac_add>
#
#  Revision       : Create by kenddy xie 2011-05-30
#
#*******************************************************************************
proc _ntgrAddStaticArp {switch_name ip_add mac_add} {
	
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
    exp_send -i $cnn_id "arp $ip_add $mac_add\r"
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
#  Function Name	: _ntgrDeleteStaticArp
#
#  Description    : This function is called to delete arp entry.
#         
#  Usage          : _ntgrDeleteStaticArp <switch_name>
#
#  Revision       : Create by kenddy xie 2011-05-30
#
#*******************************************************************************
proc _ntgrDeleteStaticArp {switch_name ip_add} {
	
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
    exp_send -i $cnn_id "no arp $ip_add\r"
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
#  Function Name	: CALntgrBatchSetSecondaryIpAddressOnPort
#
#  Description    : This function is called to bacth assign or delete secondary IP Address on the port
#         
#  Usage          : CALntgrBatchSetSecondaryIpAddressOnPort <switch_name> <port> <ip_addr> <ip_mask>
#
#  Note           : Added by jim.xie
#*******************************************************************************
proc CALntgrBatchSetSecondaryIpAddressOnPort {switch_name port init_ip_addr ip_mask ClassMode Count DeleteFlag} {

    set connection_id [_get_switch_handle $switch_name]
    set bCnn 1
    if {[string first "exp" $connection_id] == -1} {
        Netgear_connect_switch $switch_name
        set connection_id [_get_switch_handle $switch_name]
        set bCnn 0
    }
   	exp_send -i $connection_id "configure\r"
  	sleep 1
  	exp_send -i $connection_id "interface $port\r"
  	sleep 1
	set ip_list [split $init_ip_addr "."]
	if {[llength $ip_list] == 4} {
	    set ip_Class1 [lindex $ip_list 0]
		set ip_Class2 [lindex $ip_list 1]
		set ip_Class3 [lindex $ip_list 2]
		set ip_Class4 [lindex $ip_list 3]
		set init_ip_Class1 $ip_Class1
		set init_ip_Class2 $ip_Class2
		set init_ip_Class3 $ip_Class3
		set init_ip_Class4 $ip_Class4
		for {set i 0} {$i < $Count} {incr i} {
		    switch $ClassMode {
			    classA {
					set ip_Class1 [expr $init_ip_Class1 + $i]
				}
				classB {
					set ip_Class2 [expr $init_ip_Class2 + $i]
				}
				classC {
					set ip_Class3 [expr $init_ip_Class3 + $i]
				}
				classD {
					set ip_Class4 [expr $init_ip_Class4 + $i]
				}
				default {
					set ip_Class4 [expr $init_ip_Class4 + $i]
				}
			}
			if {$ip_Class4 >= 255 || $ip_Class3 >= 255 || $ip_Class2 >= 255 || $ip_Class1 >= 255} {
			    break;
            }
			set new_ip "$ip_Class1.$ip_Class2.$ip_Class3.$ip_Class4"
			if {$DeleteFlag} {
			    exp_send -i $connection_id "no ip address $new_ip $ip_mask secondary\r"
			} else {
                exp_send -i $connection_id "ip address $new_ip $ip_mask secondary\r"
			}
   	        sleep 1
        }		
	}
   	exp_send -i $connection_id "exit\r"
   	sleep 1
   	exp_send -i $connection_id "exit\r"
   	sleep 1
	  if {$bCnn == 0} {
        Netgear_disconnect_switch $switch_name
    }
    
}

#*******************************************************************************
#
#  Function Name  : CALGetIPManagementport
#
#  Description    : This function is used to get IP Management port
#
#  Usage          : CALGetIPManagementport <switch_name> <remark> <expect_rule>
#
#  return         :  management
#
#  Author           : zhenwei.li
#
#*******************************************************************************
proc CALGetIPManagementport  {switch_name A00} {
    set output {}
	set ip [_get_switch_ip_addr $switch_name]
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    exp_send -i $cnn_id "show running-config | include $ip\r"
    exp_sleep 1
    expect -i $cnn_id -re "show running-config"
    expect {
        -i $cnn_id -re "#" { append output $expect_out(buffer) }
        -i $cnn_id -re "More--" {
            append output $expect_out(buffer)
            exp_send -i $cnn_id " "
            exp_sleep 1
            exp_continue
        }   
    }  
	#set result [regexp -all "$remark" $output]
    Netgear_disconnect_switch $switch_name
	set vlanresult [string first "ip management vlan " $output ]
	set serviceport [string first "serviceport ip" $output ]
	set id [expr $vlanresult+19]
	set result ""
	if {$vlanresult > 1} {
		set vlanID [ string index $output $id]
		append result "vlan "
     	append result $vlanID
      } elseif {$serviceport>1} {
	    append result "Service Port"
	  } else {
	    append result $A00
	  }
	return $result
} 