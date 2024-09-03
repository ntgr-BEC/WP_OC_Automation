#!/bin/sh
################################################################################   
#
#  File Name		  : lib-ipv6.tcl
#
#  Description      :
#         This TCL contains functions to config ipv6 address on switch
#
#  Revision History :
#        Date          Programmer        Description
#        -----------------------------------------------------------------------
#        09-May-2011   Tony              Created
#
#
#
#

#*******************************************************************************
#
#  Function Name  : _ntgrIpv6EnableUnicastRouting
#
#  Description    : This function is called to enable ipv6 unicast_routing
#
#  Usage          : _ntgrIpv6EnableOnPort <switch_name>
#
#*******************************************************************************
proc _ntgrIpv6EnableUnicastRouting {switch_name} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    exp_send -i $cnn_id "configure\r"
    exp_sleep 1
    exp_send -i $cnn_id "ipv6 unicast-routing\r"
    expect -i $cnn_id -re "#"
    exp_send -i $cnn_id "exit\r"
    exp_sleep 1
    
    Netgear_log_file "lib-ipv6.tcl" "ipv6 enable unicast_routing on switcher"
    Netgear_disconnect_switch $switch_name
}

#*******************************************************************************
#
#  Function Name  : _ntgrIpv6DisableUnicastRouting
#
#  Description    : This function is called to disable ipv6 unicast_routing
#
#  Usage          : _ntgrIpv6EnableOnPort <switch_name>
#
#*******************************************************************************
proc _ntgrIpv6DisableUnicastRouting {switch_name} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    exp_send -i $cnn_id "configure\r"
    exp_sleep 1
    exp_send -i $cnn_id "no ipv6 unicast-routing\r"
    expect -i $cnn_id -re "#"
    exp_send -i $cnn_id "exit\r"
    exp_sleep 1
    
    Netgear_log_file "lib-ipv6.tcl" "ipv6 disable unicast_routing on switcher"
    Netgear_disconnect_switch $switch_name
}


#*******************************************************************************
#
#  Function Name  : _ntgrIpv6EnableOnPort
#
#  Description    : This function is called to enable ipv6 on port
#
#  Usage          : _ntgrIpv6EnableOnPort <switch_name> <port>
#
#*******************************************************************************
proc _ntgrIpv6EnableOnPort {switch_name port} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    exp_send -i $cnn_id "configure\r"
    exp_sleep 1
    exp_send -i $cnn_id "interface $port\r"
    exp_sleep 1
    exp_send -i $cnn_id "ipv6 enable\r"
    expect -i $cnn_id -re "#"
    exp_send -i $cnn_id "exit\r"
    exp_sleep 1
    
    Netgear_log_file "lib-ipv6.tcl" "ipv6 enable on port on switcher"
    Netgear_disconnect_switch $switch_name
}


#*******************************************************************************
#
#  Function Name  : _ntgrIpv6EnableOnVlan
#
#  Description    : This function is called to enable ipv6  on vlan
#
#  Usage          : _ntgrIpv6EnableOnVlan <switch_name> <port> 
#
#*******************************************************************************
proc _ntgrIpv6EnableOnVlan {switch_name port} {
    
    set cnn_id [_get_switch_handle $switch_name]
    set bCnn 1
    if {[string first "exp" $cnn_id] == -1} {
        Netgear_connect_switch $switch_name
        set cnn_id [_get_switch_handle $switch_name]
        set bCnn 0
    }
    set port [lindex $port 0]
    exp_send -i $cnn_id "configure\r"
    exp_sleep 1
    exp_send -i $cnn_id "interface $port\r"
    exp_sleep 1
    exp_send -i $cnn_id "ipv6 enable\r"
    expect -i $cnn_id -re "#"
    exp_send -i $cnn_id "exit\r"
    exp_sleep 1
    expect -i $cnn_id -re "#"
    exp_send -i $cnn_id "exit\r"
    exp_sleep 1    
    Netgear_log_file "lib-ipv6.tcl" "enable ipv6  on port on switcher"
    if {$bCnn == 0} {
        Netgear_disconnect_switch $switch_name
    }    
    
}

#*******************************************************************************
#
#  Function Name  : _ntgrIpv6DisableOnPort
#
#  Description    : This function is called to disable ipv6 on port
#
#  Usage          : _ntgrIpv6DisableOnPort <switch_name> <port>
#
#*******************************************************************************
proc _ntgrIpv6DisableOnPort {switch_name port} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    exp_send -i $cnn_id "configure\r"
    exp_sleep 1
    exp_send -i $cnn_id "interface $port\r"
    exp_sleep 1
    exp_send -i $cnn_id "no ipv6 enable\r"
    expect -i $cnn_id -re "#"
    exp_send -i $cnn_id "exit\r"
    exp_sleep 1
    
    Netgear_log_file "lib-ipv6.tcl" "ipv6 disable on port on switcher"
    Netgear_disconnect_switch $switch_name
}


#*******************************************************************************
#
#  Function Name  : _ntgrIpv6AddIpAddressOnPort
#
#  Description    : This function is called to add ipv6 address on port
#
#  Usage          : _ntgrIpv6AddIpAddressOnPort <switch_name> <port> <addr>
#
#*******************************************************************************
proc _ntgrIpv6AddIpAddressOnPort {switch_name port addr} {
    
    set cnn_id [_get_switch_handle $switch_name]
    set bCnn 1
    if {[string first "exp" $cnn_id] == -1} {
        Netgear_connect_switch $switch_name
        set cnn_id [_get_switch_handle $switch_name]
        set bCnn 0
    }
    set port [lindex $port 0]
    exp_send -i $cnn_id "configure\r"
    exp_sleep 1
    exp_send -i $cnn_id "interface $port\r"
    exp_sleep 1
    exp_send -i $cnn_id "ipv6 address $addr\r"
    expect -i $cnn_id -re "#"
    exp_send -i $cnn_id "exit\r"
    exp_sleep 1
    expect -i $cnn_id -re "#"
    exp_send -i $cnn_id "exit\r"
    exp_sleep 1    
    Netgear_log_file "lib-ipv6.tcl" "add ipv6 address on port on switcher"
    if {$bCnn == 0} {
        Netgear_disconnect_switch $switch_name
    }    
    
}

#*******************************************************************************
#
#  Function Name  : _ntgrIpv6DeleteIpAddressOnPort
#
#  Description    : This function is called to add ipv6 address on port
#
#  Usage          : _ntgrIpv6DeleteIpAddressOnPort <switch_name> <port> <addr>
#
#*******************************************************************************
proc _ntgrIpv6DeleteIpAddressOnPort {switch_name port addr} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    exp_send -i $cnn_id "configure\r"
    exp_sleep 1
    exp_send -i $cnn_id "interface $port\r"
    exp_sleep 1
    exp_send -i $cnn_id "no ipv6 address $addr\r"
    expect -i $cnn_id -re "#"
    exp_send -i $cnn_id "exit\r"
    exp_sleep 1
    
    Netgear_log_file "lib-ipv6.tcl" "delete ipv6 address on port on switcher"
    Netgear_disconnect_switch $switch_name
}


#*******************************************************************************
#
#  Function Name  : _ntgrIpv6GetLinkLocakAddressOnPort
#
#  Description    : This function is called to get link-local address on port
#
#  Usage          : _ntgrIpv6GetLinkLocakAddressOnPort <switch_name> <port>
#
#*******************************************************************************
proc _ntgrIpv6GetLinkLocakAddressOnPort {switch_name port} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    set output ""
    
    exp_send -i $cnn_id "show ipv6 interface $port\r"
    exp_sleep 1
    expect -i $cnn_id -re "show ipv6"    
    exp_sleep 1
    expect {
        -i $cnn_id -re "#" { append output $expect_out(buffer) }
        -i $cnn_id -re "More--" {
            append output $expect_out(buffer)
            exp_send -i $cnn_id " "
            exp_sleep 1
            exp_continue
        }   
    }
    
    set result ""
    regexp -nocase {IPv6 Prefix is\s+\.+\s+([^\n]+)} $output dummy result
    set result [string trim $result]
    if {$result != ""} {
        set result [lindex [split $result "/"] 0]
    }
    
    Netgear_log_file "lib-ipv6.tcl" "get link-local address on port on switcher"
    Netgear_disconnect_switch $switch_name
    
    return $result
}

#*******************************************************************************
#
#  Function Name  : _ntgrIpv6EnableForwarding
#
#  Description    : This function is called to enable ipv6 forwarding
#
#  Usage          : _ntgrIpv6EnableForwarding <switch_name>
#
#  Note           : create by kenddy 
#*******************************************************************************
proc _ntgrIpv6EnableForwarding {switch_name} {
    
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
    exp_send -i $cnn_id "ipv6 forwarding\r"
    exp_sleep 1
    expect -i $cnn_id -re "#" 
    append ret $expect_out(buffer)       
  	exp_send -i $cnn_id "exit\r"
  	exp_sleep 1
    expect -i $cnn_id -re "#" 
    append ret $expect_out(buffer)   	
    Netgear_log_file "lib-ipv6.tcl" "enable global ipv6 forwarding"
    if {$bCnn == 0} {
        Netgear_disconnect_switch $switch_name
    }  	
    return $ret
}

#*******************************************************************************
#
#  Function Name  : _ntgrIpv6DisableForwarding
#
#  Description    : This function is called to disable ipv6 forwarding
#
#  Usage          : _ntgrIpv6DisableForwarding <switch_name>
#
#  Note           : create by kenddy 
#*******************************************************************************
proc _ntgrIpv6DisableForwarding {switch_name} {
    
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
    exp_send -i $cnn_id "no ipv6 forwarding\r"
    exp_sleep 1
    expect -i $cnn_id -re "#" 
    append ret $expect_out(buffer)       
  	exp_send -i $cnn_id "exit\r"
  	exp_sleep 1
    expect -i $cnn_id -re "#" 
    append ret $expect_out(buffer)   	
    Netgear_log_file "lib-ipv6.tcl" "disable global ipv6 forwarding"
    if {$bCnn == 0} {
        Netgear_disconnect_switch $switch_name
    }  	
    return $ret
}

proc _ntgrConfig6in4Tunnel {switch_name tunnel_num src_ip dst_ip } {
	
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
  	  	
    exp_send -i $cnn_id "interface tunnel $tunnel_num\r"
    exp_sleep 1
    expect -i $cnn_id -re "#" 
    append ret $expect_out(buffer)       
  	exp_send -i $cnn_id "tunnel mode ipv6ip\r"
  	exp_sleep 1
    expect -i $cnn_id -re "#"
    append ret $expect_out(buffer)
    exp_send -i $cnn_id "tunnel source $src_ip\r"     
  	exp_sleep 1
    expect -i $cnn_id -re "#"
    append ret $expect_out(buffer)
    exp_send -i $cnn_id "tunnel destination $dst_ip\r"     
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
    append ret $expect_out(buffer)     
    if {$bCnn == 0} {
        Netgear_disconnect_switch $switch_name
    }  	
    return $ret
}


proc _ntgrConfig6to4Tunnel {switch_name tunnel_num src_ip } {
	
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
  	  	
    exp_send -i $cnn_id "interface tunnel $tunnel_num\r"
    exp_sleep 1
    expect -i $cnn_id -re "#" 
    append ret $expect_out(buffer)       
  	exp_send -i $cnn_id "tunnel mode ipv6ip 6to4\r"
  	exp_sleep 1
    expect -i $cnn_id -re "#"
    append ret $expect_out(buffer)
    exp_send -i $cnn_id "tunnel source $src_ip\r"     
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
    append ret $expect_out(buffer)     
    if {$bCnn == 0} {
        Netgear_disconnect_switch $switch_name
    }  	
    return $ret
}

proc _ntgrDeleteTunnelConfig {switch_name tunnel_num } {
	
	  set cnn_id [_get_switch_handle $switch_name]
    set bCnn 1
    if {[string first "exp" $cnn_id] == -1} {
        Netgear_connect_switch $switch_name
        set cnn_id [_get_switch_handle $switch_name]
        set bCnn 0
    }
  	exp_send -i $cnn_id "configure\r"
  	exp_sleep 1  	  	
    exp_send -i $cnn_id "no interface tunnel $tunnel_num\r"
    exp_sleep 1
  	exp_send -i $cnn_id "exit\r"
  	exp_sleep 1

    if {$bCnn == 0} {
        Netgear_disconnect_switch $switch_name
    }  	
}

proc _ntgrIpv6ConfigHopLimit {switch_name limit_num} {
    
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
    exp_send -i $cnn_id "ipv6 hop-limit $limit_num\r"
    exp_sleep 1
    expect -i $cnn_id -re "#" 
    append ret $expect_out(buffer)       
  	exp_send -i $cnn_id "exit\r"
  	exp_sleep 1
    expect -i $cnn_id -re "#" 
    append ret $expect_out(buffer)   	
    Netgear_log_file "lib-ipv6.tcl" "config ipv6 hop-limit to $limit_num."
    if {$bCnn == 0} {
        Netgear_disconnect_switch $switch_name
    }  	
    return $ret
}

proc _ntgrIpv6RestoreDefaultHopLimit {switch_name} {
    
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
    exp_send -i $cnn_id "no ipv6 hop-limit\r"
    exp_sleep 1
    expect -i $cnn_id -re "#" 
    append ret $expect_out(buffer)       
  	exp_send -i $cnn_id "exit\r"
  	exp_sleep 1
    expect -i $cnn_id -re "#" 
    append ret $expect_out(buffer)   	
    Netgear_log_file "lib-ipv6.tcl" "restore default ipv6 hop-limit."
    if {$bCnn == 0} {
        Netgear_disconnect_switch $switch_name
    }  	
    return $ret
}

proc _ntgrIpv6EnableAutoConfigMode {switch_name} {
    
	  set cnn_id [_get_switch_handle $switch_name]
    set bCnn 1
    if {[string first "exp" $cnn_id] == -1} {
        Netgear_connect_switch $switch_name
        set cnn_id [_get_switch_handle $switch_name]
        set bCnn 0
    }
    set ret ""
  	exp_send -i $cnn_id "network ipv6 address autoconfig\r"
  	exp_sleep 1
    expect -i $cnn_id -re "#" 
    append ret $expect_out(buffer)   	
    Netgear_log_file "lib-ipv6.tcl" "Enable ipv6 address autoconfig."
    if {$bCnn == 0} {
        Netgear_disconnect_switch $switch_name
    }  	
    return $ret
}

proc _ntgrIpv6DisableAutoConfigMode {switch_name} {
    
	  set cnn_id [_get_switch_handle $switch_name]
    set bCnn 1
    if {[string first "exp" $cnn_id] == -1} {
        Netgear_connect_switch $switch_name
        set cnn_id [_get_switch_handle $switch_name]
        set bCnn 0
    }
    set ret ""
  	exp_send -i $cnn_id "no network ipv6 address autoconfig\r"
  	exp_sleep 1
    expect -i $cnn_id -re "#" 
    append ret $expect_out(buffer)   	
    Netgear_log_file "lib-ipv6.tcl" "Disable ipv6 address autoconfig."
    if {$bCnn == 0} {
        Netgear_disconnect_switch $switch_name
    }  	
    return $ret
}

proc _ntgrIpv6EnableNetwokDhcp {switch_name} {
    
	  set cnn_id [_get_switch_handle $switch_name]
    set bCnn 1
    if {[string first "exp" $cnn_id] == -1} {
        Netgear_connect_switch $switch_name
        set cnn_id [_get_switch_handle $switch_name]
        set bCnn 0
    }
    set ret ""
  	exp_send -i $cnn_id "network ipv6 address dhcp\r"
  	exp_sleep 1
    expect -i $cnn_id -re "#" 
    append ret $expect_out(buffer)   	
    Netgear_log_file "lib-ipv6.tcl" "Enable network ipv6 address dhcp."
    if {$bCnn == 0} {
        Netgear_disconnect_switch $switch_name
    }  	
    return $ret
}

proc _ntgrIpv6DisableNetworkDhcp {switch_name} {
    
	  set cnn_id [_get_switch_handle $switch_name]
    set bCnn 1
    if {[string first "exp" $cnn_id] == -1} {
        Netgear_connect_switch $switch_name
        set cnn_id [_get_switch_handle $switch_name]
        set bCnn 0
    }
    set ret ""
  	exp_send -i $cnn_id "no network ipv6 address dhcp\r"
  	exp_sleep 1
    expect -i $cnn_id -re "#" 
    append ret $expect_out(buffer)   	
    Netgear_log_file "lib-ipv6.tcl" "Disable network ipv6 address dhcp."
    if {$bCnn == 0} {
        Netgear_disconnect_switch $switch_name
    }  	
    return $ret
}

proc _ntgrIpv6EnableNetwokMode {switch_name} {
    
	  set cnn_id [_get_switch_handle $switch_name]
    set bCnn 1
    if {[string first "exp" $cnn_id] == -1} {
        Netgear_connect_switch $switch_name
        set cnn_id [_get_switch_handle $switch_name]
        set bCnn 0
    }
    set ret ""
  	exp_send -i $cnn_id "network ipv6 enable\r"
  	exp_sleep 1
    expect -i $cnn_id -re "#" 
    append ret $expect_out(buffer)   	
    Netgear_log_file "lib-ipv6.tcl" "Enable network ipv6 mode."
    if {$bCnn == 0} {
        Netgear_disconnect_switch $switch_name
    }  	
    return $ret
}

proc _ntgrIpv6DisableNetwokMode {switch_name} {
    
	  set cnn_id [_get_switch_handle $switch_name]
    set bCnn 1
    if {[string first "exp" $cnn_id] == -1} {
        Netgear_connect_switch $switch_name
        set cnn_id [_get_switch_handle $switch_name]
        set bCnn 0
    }
    set ret ""
  	exp_send -i $cnn_id "no network ipv6 enable\r"
  	exp_sleep 1
    expect -i $cnn_id -re "#" 
    append ret $expect_out(buffer)   	
    Netgear_log_file "lib-ipv6.tcl" "Disable network ipv6 mode."
    if {$bCnn == 0} {
        Netgear_disconnect_switch $switch_name
    }  	
    return $ret
}

proc _ntgrIpv6AddStaticRoute {switch_name net_work next_hop {pref 1}} {
    
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
    exp_send -i $cnn_id "ipv6 route $net_work $next_hop $pref\r"
    exp_sleep 1
    expect -i $cnn_id -re "#" 
    append ret $expect_out(buffer)       
  	exp_send -i $cnn_id "exit\r"
  	exp_sleep 1
    expect -i $cnn_id -re "#" 
    append ret $expect_out(buffer)   	
    Netgear_log_file "lib-ipv6.tcl" "config ipv6 static route."
    if {$bCnn == 0} {
        Netgear_disconnect_switch $switch_name
    }  	
    return $ret
}

proc _ntgrIpv6DeleteStaticRoute {switch_name net_work } {
    
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
    exp_send -i $cnn_id "no ipv6 route $net_work\r"
    exp_sleep 1
    expect -i $cnn_id -re "#" 
    append ret $expect_out(buffer)       
  	exp_send -i $cnn_id "exit\r"
  	exp_sleep 1
    expect -i $cnn_id -re "#" 
    append ret $expect_out(buffer)   	
    Netgear_log_file "lib-ipv6.tcl" "delete ipv6 static route."
    if {$bCnn == 0} {
        Netgear_disconnect_switch $switch_name
    }  	
    return $ret
}

proc _ntgrConfigInterfaceMtu {switch_name intf mtu_size } {
	
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
    exp_send -i $cnn_id "interface $intf\r" 
  	exp_sleep 1
    expect -i $cnn_id -re "#"
    append ret $expect_out(buffer)
    exp_send -i $cnn_id "ipv6 mtu $mtu_size\r"
  	exp_sleep 1
    expect -i $cnn_id -re "#"
    append ret $expect_out(buffer) 
  	exp_send -i $cnn_id "exit\r"
  	exp_sleep 1
  	exp_send -i $cnn_id "exit\r"
  	exp_sleep 1
    if {$bCnn == 0} {
        Netgear_disconnect_switch $switch_name
    }  	
    return $ret
}

proc _ntgrRestoreInterfaceMtu {switch_name intf} {
	
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
    exp_send -i $cnn_id "interface $intf\r" 
  	exp_sleep 1
    expect -i $cnn_id -re "#"
    append ret $expect_out(buffer)
    exp_send -i $cnn_id "no ipv6 mtu\r"
  	exp_sleep 1
    expect -i $cnn_id -re "#"
    append ret $expect_out(buffer) 
  	exp_send -i $cnn_id "exit\r"
  	exp_sleep 1
  	exp_send -i $cnn_id "exit\r"
  	exp_sleep 1
    if {$bCnn == 0} {
        Netgear_disconnect_switch $switch_name
    }  	
    return $ret
}

proc _ntgrIpv6ConfigStaticRoutePreference {switch_name  pref_value} {
    
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
    exp_send -i $cnn_id "ipv6 route distance $pref_value\r"
    exp_sleep 1
    expect -i $cnn_id -re "#" 
    append ret $expect_out(buffer)       
  	exp_send -i $cnn_id "exit\r"
  	exp_sleep 1
    expect -i $cnn_id -re "#" 
    append ret $expect_out(buffer)   	
    Netgear_log_file "lib-ipv6.tcl" "config distance value for ipv6 static route."
    if {$bCnn == 0} {
        Netgear_disconnect_switch $switch_name
    }  	
    return $ret
}

proc _ntgrIpv6RestoreStaticRoutePreference {switch_name} {
    
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
    exp_send -i $cnn_id "no ipv6 route distance\r"
    exp_sleep 1
    expect -i $cnn_id -re "#" 
    append ret $expect_out(buffer)       
  	exp_send -i $cnn_id "exit\r"
  	exp_sleep 1
    expect -i $cnn_id -re "#" 
    append ret $expect_out(buffer)   	
    Netgear_log_file "lib-ipv6.tcl" "restore distance value to default for ipv6 static route."
    if {$bCnn == 0} {
        Netgear_disconnect_switch $switch_name
    }  	
    return $ret
}

proc _ntgrIPv6ConfigInterfaceRoutePreferenceLevel {switch_name int_f pref_level } {
	
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
    exp_send -i $cnn_id "interface $int_f\r" 
  	exp_sleep 1
    expect -i $cnn_id -re "#"
    append ret $expect_out(buffer)
    exp_send -i $cnn_id "ipv6 nd router-preference $pref_level\r"
  	exp_sleep 1
    expect -i $cnn_id -re "#"
    append ret $expect_out(buffer) 
  	exp_send -i $cnn_id "exit\r"
  	exp_sleep 1
  	exp_send -i $cnn_id "exit\r"
  	exp_sleep 1
    if {$bCnn == 0} {
        Netgear_disconnect_switch $switch_name
    }  	
    return $ret
}

proc _ntgrIPv6RestoreInterfaceRoutePreferenceLevel {switch_name int_f } {
	
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
    exp_send -i $cnn_id "interface $int_f\r" 
  	exp_sleep 1
    expect -i $cnn_id -re "#"
    append ret $expect_out(buffer)
    exp_send -i $cnn_id "no ipv6 nd router-preference\r"
  	exp_sleep 1
    expect -i $cnn_id -re "#"
    append ret $expect_out(buffer) 
  	exp_send -i $cnn_id "exit\r"
  	exp_sleep 1
  	exp_send -i $cnn_id "exit\r"
  	exp_sleep 1
    if {$bCnn == 0} {
        Netgear_disconnect_switch $switch_name
    }  	
    return $ret
}

#*******************************************************************************
#
#  Function Name  : _ntgrCheckIPv6Route
#
#  Description    : This function is used to get the ipv6 route table info
#
#  Usage          : _ntgrCheckIPv6Route <switch_id> <protocol_type> <ipv6_adress> <next_inter> <next_address> <perference> <matric_value>
#
#  Author         :  jim.xie
#
#*******************************************************************************
proc _ntgrCheckIPv6Route {switch_id protocol_type ipv6_adress next_inter next_address perference matric_value} {

    set cnn_id [_get_switch_handle $switch_id]
    set bCnn 1
    if {[string first "exp" $cnn_id] == -1} {
        Netgear_connect_switch $switch_id
        set cnn_id [_get_switch_handle $switch_id]
        set bCnn 0
    }
    set str1 {}
    set str2 {}
	set str3 {}
	set str4 {}
	set match {}
	
	set result ""
   	exp_send -i $cnn_id "show ipv6 route $ipv6_adress \r"
    expect -i $cnn_id "show ipv6 route"
    expect {
        -i $cnn_id -re "#" { set result $result$expect_out(buffer) }
        -i $cnn_id -re "More" {
            set result $result$expect_out(buffer)
                exp_send -i $cnn_id " "
                exp_sleep 1
            	exp_continue
        }
        timeout { exp_send -i $cnn_id " "; exp_continue }
    }

	if {$bCnn == 0} {
        Netgear_disconnect_switch $switch_id
    }

	set regSWMAC "$protocol_type\[ ]+$ipv6_adress\[ ]+\\\[(\[0-9\]+)/(\[0-9\]+)\\\]"
	regexp $regSWMAC $result match str1 str2
	set regSWMAC "\[ ]+via\[ ]+($next_address),\[ ]+(\[0-9\]+h:\[0-9\]+m:\[0-9\]+s,)?\[ ]+$next_inter"
	regexp $regSWMAC $result match str3 str4
	
	Netgear_log_file "Function: _ntgrCheckIPv6Route " "str1=$str1 str2=$str2 str3=$str3 str4=$str4 ."

	set return_value 1
	if {$str1 == "" || $str2 == ""} {
	    set return_value 0
		Netgear_log_file "Function: _ntgrCheckIPv6Route " "Error: IPv6 Address: $ipv6_adress , Protocol Type:$protocol_type is not exist on IPv6 route table."
	}
    
	if {$next_address != "" && $next_address != $str3} {
	    set return_value 0
		Netgear_log_file "Function: _ntgrCheckIPv6Route " "Error: Next Hop Interface: $next_inter , Next Hop IP Address:$next_address is not exist on IPv6 route table."
	}
	
	if {$matric_value != "" && $matric_value != $str2} {
	    set return_value 0
		Netgear_log_file "Function: _ntgrCheckIPv6Route " "Error: IPv6 Address: $ipv6_adress , Protocol Type:$protocol_type Matric value is Error."
	}
	
	if {$perference != "" && $perference != $str1} {
	    set return_value 0
		Netgear_log_file "Function: _ntgrCheckIPv6Route " "Error: IPv6 Address: $ipv6_adress , Protocol Type:$protocol_type Perference value is Error."
	}
	
	return $return_value
}

#*******************************************************************************
#  Function Name	: _ntgrIpv6InterfaceReachableEnable
#
#  Description    	: This function is called to enable unreachables mode on interface
#         
#  Usage          	: _ntgrIpv6InterfaceReachableEnable <switch_name> <if_list>
#
#  Author           : jim.xie
#
#*******************************************************************************
proc _ntgrIpv6InterfaceReachableEnable {switch_name if_list} {
      
    set cnn_id [_get_switch_handle $switch_name]
    set bCnn 1
    if {[string first "exp" $cnn_id] == -1} {
        Netgear_connect_switch $switch_name
        set cnn_id [_get_switch_handle $switch_name]
        set bCnn 0
    }
	
    foreach if_name $if_list {
        if {[string first "POCH" $if_name] != -1} {
            set if_name [_ntgrGetChannelLogicalIfName $cnn_id $if_name]
        }
        exp_send -i $cnn_id "configure\r"
        after 200
        exp_send -i $cnn_id "interface $if_name\r"
        after 200
        exp_send -i $cnn_id "ipv6 unreachables\r"
        after 200
        exp_send -i $cnn_id "exit\r"
        after 200
        exp_send -i $cnn_id "exit\r"
        after 200
    }
	
    if {$bCnn == 0} {
        Netgear_disconnect_switch $switch_name
    }
}

#*******************************************************************************
#  Function Name	: _ntgrIpv6InterfaceReachableDisable
#
#  Description    	: This function is called to disable unreachables mode on interface
#         
#  Usage          	: _ntgrIpv6InterfaceReachableDisable <switch_name> <if_list>
#
#  Author           : jim.xie
#
#*******************************************************************************
proc _ntgrIpv6InterfaceReachableDisable {switch_name if_list} {
      
    set cnn_id [_get_switch_handle $switch_name]
    set bCnn 1
    if {[string first "exp" $cnn_id] == -1} {
        Netgear_connect_switch $switch_name
        set cnn_id [_get_switch_handle $switch_name]
        set bCnn 0
    }
	
    foreach if_name $if_list {
        if {[string first "POCH" $if_name] != -1} {
            set if_name [_ntgrGetChannelLogicalIfName $cnn_id $if_name]
        }
        exp_send -i $cnn_id "configure\r"
        after 200
        exp_send -i $cnn_id "interface $if_name\r"
        after 200
        exp_send -i $cnn_id "no ipv6 unreachables\r"
        after 200
        exp_send -i $cnn_id "exit\r"
        after 200
        exp_send -i $cnn_id "exit\r"
        after 200
    }
	
    if {$bCnn == 0} {
        Netgear_disconnect_switch $switch_name
    }
}

#*******************************************************************************
#  Function Name	: _ntgrIpv6ClearNeighborTable
#
#  Description    	: This function is called to clear neighbor table
#         
#  Usage          	: _ntgrIpv6ClearNeighborTable <switch_name>
#
#  Author           : jim.xie
#
#*******************************************************************************
proc _ntgrIpv6ClearNeighborTable {switch_name} {
      
    set cnn_id [_get_switch_handle $switch_name]
    set bCnn 1
    if {[string first "exp" $cnn_id] == -1} {
        Netgear_connect_switch $switch_name
        set cnn_id [_get_switch_handle $switch_name]
        set bCnn 0
    }
	
	expect -i $cnn_id -re "#"
    exp_send -i $cnn_id "clear ipv6 neighbors\r"
    sleep 1
    exp_send -i $cnn_id "exit\r"
   
    if {$bCnn == 0} {
        Netgear_disconnect_switch $switch_name
    }
}

#*******************************************************************************
#
#  Function Name  : _ntgrIpv6AddNDRAIntervalOnPort
#
#  Description    : This function is called to enable ipv6 on port
#
#  Usage          : _ntgrIpv6AddNDRAIntervalOnPort <switch_name> <port> <maxtime> <mintime>
#
#*******************************************************************************
proc _ntgrIpv6AddNDRAIntervalOnPort {switch_name port maxtime mintime} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    exp_send -i $cnn_id "configure\r"
    exp_sleep 1
    exp_send -i $cnn_id "interface $port\r"
    exp_sleep 1
    exp_send -i $cnn_id "ipv6 nd ra-interval $maxtime $mintime\r"
    expect -i $cnn_id -re "#"
    exp_send -i $cnn_id "exit\r"
    exp_sleep 1
    
    Netgear_log_file "lib-ipv6.tcl" "ipv6 add nd ra interval on port on switcher"
    Netgear_disconnect_switch $switch_name
}

#*******************************************************************************
#
#  Function Name  : _ntgrIpv6DelNDRAIntervalOnPort
#
#  Description    : This function is called to delete ipv6 nd ra-interval  on port
#
#  Usage          : _ntgrIpv6DelNDRAIntervalOnPort <switch_name> <port> 
#
#*******************************************************************************
proc _ntgrIpv6DelNDRAIntervalOnPort {switch_name port} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    exp_send -i $cnn_id "configure\r"
    exp_sleep 1
    exp_send -i $cnn_id "interface $port\r"
    exp_sleep 1
    exp_send -i $cnn_id "no ipv6 nd ra-interval\r"
    expect -i $cnn_id -re "#"
    exp_send -i $cnn_id "exit\r"
    exp_sleep 1
    
    Netgear_log_file "lib-ipv6.tcl" "ipv6 delete nd ra interval on port on switcher"
    Netgear_disconnect_switch $switch_name
}

#*******************************************************************************
#
#  Function Name  : _ntgrIpv6GetRoutesNum
#
#  Description    : This function is called to get ipv6 route table size
#
#  Usage          : _ntgrIpv6GetRoutesNum <switch_name> <prefix> 
#
#*******************************************************************************
proc _ntgrIpv6GetRoutesNum {switch_id {prefix ""}} {
 set cnn_id [_get_switch_handle $switch_id]
    set bCnn 1
    if {[string first "exp" $cnn_id] == -1} {
        Netgear_connect_switch $switch_id
        set cnn_id [_get_switch_handle $switch_id]
        set bCnn 0
    }
    set num {}

	set match {}
	set result ""
   	exp_send -i $cnn_id "show ipv6 route $prefix \r"
    expect -i $cnn_id "show ipv6 route"
    expect {
        -i $cnn_id -re "#" { set result $result$expect_out(buffer) }
        -i $cnn_id -re "More" {
            set result $result$expect_out(buffer)
                exp_send -i $cnn_id " "
                exp_sleep 1
            	exp_continue
        }
        timeout { exp_send -i $cnn_id " "; exp_continue }
    }

	if {$bCnn == 0} {
        Netgear_disconnect_switch $switch_id
    }
    set reg "IPv6\[ ]+Routing\[ ]+Table\[ ]+-\[ ]+(\\d+)\[ ]entries$"
	regexp -nocase -line $reg $result match num

	Netgear_log_file "Function: _ntgrIpv6GetRoutesNum " "num=$num"

	return $num
}
#*******************************************************************************
#
#  Function Name  : CALIpv6EnableRAGuard
#
#  Description    : This function is called to enable ipv6 nd raguard on port
#
#  Usage          : CALIpv6EnableRAGuard <switch_name> <port> 
#
#  Author         : zhenwei.li
#
#*******************************************************************************
proc CALIpv6EnableRAGuard {switch_name port} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    exp_send -i $cnn_id "configure\r"
    exp_sleep 1
    exp_send -i $cnn_id "interface $port\r"
    exp_sleep 1
    exp_send -i $cnn_id "ipv6 nd raguard attach-policy\r"
    expect -i $cnn_id -re "#"
    exp_send -i $cnn_id "exit\r"
    exp_sleep 1
    
    Netgear_log_file "lib-ipv6.tcl" "ipv6 enable nd ra guard on port on switcher"
    Netgear_disconnect_switch $switch_name
}
#*******************************************************************************
#
#  Function Name  : CALIpv6DisableRAGuard
#
#  Description    : This function is called to disable ipv6 nd raguard on port
#
#  Usage          : CALIpv6DisableRAGuard <switch_name> <port> 
#
#  Author         : zhenwei.li
#
#*******************************************************************************
proc CALIpv6DisableRAGuard {switch_name port} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    exp_send -i $cnn_id "configure\r"
    exp_sleep 1
    exp_send -i $cnn_id "interface $port\r"
    exp_sleep 1
    exp_send -i $cnn_id "no ipv6 nd raguard attach-policy\r"
    expect -i $cnn_id -re "#"
    exp_send -i $cnn_id "exit\r"
    exp_sleep 1
    
    Netgear_log_file "lib-ipv6.tcl" "ipv6 disable nd ra guard on port on switcher"
    Netgear_disconnect_switch $switch_name
}