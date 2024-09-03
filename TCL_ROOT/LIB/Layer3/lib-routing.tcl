#!/bin/sh
################################################################################   
#
#  File Name		  : lib-routing.tcl
#
#  Description      :
#         This TCL contains functions to enable routing on switch
#
#  Revision History :
#        Date          Programmer        Description
#        -----------------------------------------------------------------------
#        15-09-06      Nina Cheng        Created
#
#
#
#
################################################################################
#1*******************************************************************************
#  Function Name	: _ntgrRoutingEnable
#
#  Description    	: This function is called to enable routing on switch
#         
#  Usage          	: _ntgrRoutingEnable <switch_name> 
#
#
#*******************************************************************************
proc _ntgrRoutingEnable {switch_name} {
	set connection_id [_get_switch_handle $switch_name]	
    set bCnn 1
    if {[string first "exp" $connection_id] == -1} {
        Netgear_connect_switch $switch_name
        set connection_id [_get_switch_handle $switch_name]
        set bCnn 0
    }
	
	expect -i $connection_id -re "#"
   exp_send -i $connection_id "configure\r"
  	sleep 1
 	exp_send -i $connection_id "ip routing\r"
 	sleep 1
 	exp_send -i $connection_id "exit\r"
	
    if {$bCnn == 0} {
        Netgear_disconnect_switch $switch_name
    }
}

################################################################################
#2*******************************************************************************
#  Function Name	: _ntgrRoutingDisable
#
#  Description    	: This function is called to disable routing on switch
#         
#  Usage          	: _ntgrRoutingDisable <switch_name> 
#
#
#*******************************************************************************
proc _ntgrRoutingDisable {switch_name} {
	
	set connection_id [_get_switch_handle $switch_name]	
	expect -i $connection_id -re "#"
   exp_send -i $connection_id "configure\r"
  	sleep 1
 	exp_send -i $connection_id "no ip routing\r"
 	sleep 1
 	exp_send -i $connection_id "exit\r"
}

################################################################################
#3*******************************************************************************
#  Function Name	: _ntgrRoutingPortEnable
#
#  Description    	: This function is called to enable routing on port
#         
#  Usage          	: _ntgrRoutingPortEnable <switch_name> <port> 
#
#
#*******************************************************************************
proc _ntgrRoutingPortEnable {switch_name port} {
	
	set connection_id [_get_switch_handle $switch_name]	

    set bCnn 1
    if {[string first "exp" $connection_id] == -1} {
        Netgear_connect_switch $switch_name
        set connection_id [_get_switch_handle $switch_name]
        set bCnn 0
    }
    	
	expect -i $connection_id -re "#"
  exp_send -i $connection_id "configure\r"
  sleep 1
  exp_send -i $connection_id "interface $port\r"
  sleep 1
 	exp_send -i $connection_id "routing\r"
 	sleep 1
 	exp_send -i $connection_id "exit\r"
	sleep 1
 	exp_send -i $connection_id "exit\r"

    if {$bCnn == 0} {
        Netgear_disconnect_switch $switch_name
    }  	
}

################################################################################
#4*******************************************************************************
#  Function Name	: _ntgrRoutingPortDisable
#
#  Description    	: This function is called to disable routing on port
#         
#  Usage          	: _ntgrRoutingPortDisable <switch_name> <port> 
#
#
#*******************************************************************************
proc _ntgrRoutingPortDisable {switch_name port} {

	set connection_id [_get_switch_handle $switch_name]	

    set bCnn 1
    if {[string first "exp" $connection_id] == -1} {
        Netgear_connect_switch $switch_name
        set connection_id [_get_switch_handle $switch_name]
        set bCnn 0
    }
    	
	expect -i $connection_id -re "#"
   exp_send -i $connection_id "configure\r"
  	sleep 1
  exp_send -i $connection_id "interface $port\r"
  sleep 1
 	exp_send -i $connection_id "no routing\r"
 	sleep 1
 	exp_send -i $connection_id "exit\r"
	sleep 1
 	exp_send -i $connection_id "exit\r"

    if {$bCnn == 0} {
        Netgear_disconnect_switch $switch_name
    } 
     	
}

################################################################################
#5*******************************************************************************
#  Function Name	: _ntgrAddIPtoPort
#
#  Description    	: This function is called to add IP address on port
#         
#  Usage          	: _ntgrAddIPtoPort <switch_name> <port> <ip_addr> 
#
#
#*******************************************************************************
proc _ntgrAddIPtoPort {switch_name port ip_addr} {
	
	set connection_id [_get_switch_handle $switch_name]	

    set bCnn 1
    if {[string first "exp" $connection_id] == -1} {
        Netgear_connect_switch $switch_name
        set connection_id [_get_switch_handle $switch_name]
        set bCnn 0
    }
    	
	expect -i $connection_id -re "#"
   exp_send -i $connection_id "configure\r"
  	sleep 1
  exp_send -i $connection_id "interface $port\r"
  sleep 1
 	exp_send -i $connection_id "ip address $ip_addr\r"
 	sleep 1
 	exp_send -i $connection_id "exit\r"	
 	sleep 1
 	exp_send -i $connection_id "exit\r"
    if {$bCnn == 0} {
        Netgear_disconnect_switch $switch_name
    }  	
}

################################################################################
#*******************************************************************************
#  Function Name	: _ntgrAddIPv6toPort
#
#  Description    	: This function is called to add IPv6 address on port
#         
#  Usage          	: _ntgrAddIPv6toPort <switch_name> <port> <ip_addr> 
#
#
#*******************************************************************************
proc _ntgrAddIPv6toPort {switch_name port ip_addr} {
	
	set connection_id [_get_switch_handle $switch_name]	

    set bCnn 1
    if {[string first "exp" $connection_id] == -1} {
        Netgear_connect_switch $switch_name
        set connection_id [_get_switch_handle $switch_name]
        set bCnn 0
    }
    	
	expect -i $connection_id -re "#"
   exp_send -i $connection_id "configure\r"
  	sleep 1
  exp_send -i $connection_id "interface $port\r"
  sleep 1
 	exp_send -i $connection_id "ipv6 address $ip_addr\r"
 	sleep 1
 	exp_send -i $connection_id "exit\r"	
 	sleep 1
 	exp_send -i $connection_id "exit\r"
    if {$bCnn == 0} {
        Netgear_disconnect_switch $switch_name
    }  	
}
################################################################################
#6*******************************************************************************
#  Function Name	: _ntgrDeleteIPtoPort
#
#  Description    	: This function is called to delete IP address on port
#         
#  Usage          	: _ntgrDeleteIPtoPort <switch_name> <port> <ip_addr> 
#
#
#*******************************************************************************
proc _ntgrDeleteIPtoPort {switch_name port ip_addr} {

	set connection_id [_get_switch_handle $switch_name]

    set bCnn 1
    if {[string first "exp" $connection_id] == -1} {
        Netgear_connect_switch $switch_name
        set connection_id [_get_switch_handle $switch_name]
        set bCnn 0
    }
    	
	expect -i $connection_id -re "#"
   exp_send -i $connection_id "configure\r"
  	sleep 1
  exp_send -i $connection_id "interface $port\r"
  sleep 1
 	exp_send -i $connection_id "no ip address $ip_addr\r"
 	sleep 1
 	exp_send -i $connection_id "exit\r"
 	sleep 1
 	exp_send -i $connection_id "exit\r"	

    if {$bCnn == 0} {
        Netgear_disconnect_switch $switch_name
    }   	
}

################################################################################
#6*******************************************************************************
#  Function Name	: _ntgrDeleteIPv6toPort
#
#  Description    	: This function is called to delete IPv6 address on port
#         
#  Usage          	: _ntgrDeleteIPv6toPort <switch_name> <port> <ip_addr> 
#
#
#*******************************************************************************
proc _ntgrDeleteIPv6toPort {switch_name port ip_addr} {

	set connection_id [_get_switch_handle $switch_name]

    set bCnn 1
    if {[string first "exp" $connection_id] == -1} {
        Netgear_connect_switch $switch_name
        set connection_id [_get_switch_handle $switch_name]
        set bCnn 0
    }
    	
	expect -i $connection_id -re "#"
   exp_send -i $connection_id "configure\r"
  	sleep 1
  exp_send -i $connection_id "interface $port\r"
  sleep 1
 	exp_send -i $connection_id "no ipv6 address $ip_addr\r"
 	sleep 1
 	exp_send -i $connection_id "exit\r"
 	sleep 1
 	exp_send -i $connection_id "exit\r"	

    if {$bCnn == 0} {
        Netgear_disconnect_switch $switch_name
    }   	
}


#*******************************************************************************
#
#  Function Name  : _ntgrCheckIpRouteEntry
#
#  Description    : This function is used to check ip route entry exist or not.
#
#  Usage          : _ntgrCheckIpRouteEntry <switch_name> <iproute_list> <notin>
#
#*******************************************************************************
proc _ntgrCheckIpRouteEntry {switch_name iproute_list {notin 0}} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    set output ""
    
    exp_send -i $cnn_id "show ip route all\r"
    exp_sleep 1
    expect -i $cnn_id -re "show ip route all"
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
    foreach iproute $iproute_list {
        if {$notin == 1} {
            if {[regexp -nocase " $iproute" $output]} {
                set result 0
                break
            }
        } else {
            if {![regexp -nocase " $iproute" $output]} {
                set result 0
                break
            }
        }
    }

    Netgear_log_file "lib-routing.tcl" "getting ip route entry on switcher"
    Netgear_disconnect_switch $switch_name

    return $result
}

#*******************************************************************************
#
#  Function Name  : _ntgrCheckIpRouteEntryByRoute
#
#  Description    : This function is used to check ip route entry exist or not by route.
#
#  Usage          : _ntgrCheckIpRouteEntryByRoute <switch_name> <iproute> <check_list>
#
#*******************************************************************************
proc _ntgrCheckIpRouteEntryByRoute {switch_name iproute check_list} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    set output ""
    
    exp_send -i $cnn_id "show ip route all\r"
    exp_sleep 1
    expect -i $cnn_id -re "show ip route all"
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
    if {[regexp -nocase " $iproute" $output]} {
        set output [split $output "\n"]        
        set str [lsearch -regexp -inline $output " $iproute"]
        
        foreach check_item $check_list {
            if {![regexp -nocase $check_item $str]} {
                set result 0
                break
            }
        }
    } else {
        set result 0
    }

    Netgear_log_file "lib-routing.tcl" "getting ip route entry on switcher"
    Netgear_disconnect_switch $switch_name

    return $result
}


#*******************************************************************************
#
#  Function Name  : _ntgrCheckDelStaticRouteInfo
#
#  Description    : This function is used to check delete static route error info.
#
#  Usage          : _ntgrCheckDelStaticRouteInfo <switch_name> <iproute>
#
#*******************************************************************************
proc _ntgrCheckDelStaticRouteInfo {switch_name iproute} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    set network [lindex $iproute 0]
    set netmask [lindex $iproute 1]
    set nexthop [lindex $iproute 2]
    
    set output ""
    
    exp_send -i $cnn_id "config\r"
    exp_sleep 1
    exp_send -i $cnn_id "no ip route $network $netmask $nexthop\r"
    exp_sleep 1
    expect -i $cnn_id -re "no ip route $network $netmask $nexthop"
    expect -i $cnn_id -re "#"
    
    set output $expect_out(buffer)
    
    set result [regexp -nocase {Failed to delete} $output]

    Netgear_log_file "lib-routing.tcl" "get info from deleting static route on switcher"
    Netgear_disconnect_switch $switch_name

    return $result
}

#*******************************************************************************
#  Function Name	: CALntgrRoutingConfigureICMPEcho
#
#  Description    	: This function is called to enable or disable routing icmp echo reply on switch
#         
#  Usage          	: CALntgrRoutingConfigureICMPEcho <switch_name> <enableflag>
#
#
#*******************************************************************************
proc CALntgrRoutingConfigureICMPEcho {switch_name enableflag} {
    set connection_id [_get_switch_handle $switch_name]	
    set bCnn 1
    if {[string first "exp" $connection_id] == -1} {
        Netgear_connect_switch $switch_name
        set connection_id [_get_switch_handle $switch_name]
        set bCnn 0
    }
	
	expect -i $connection_id -re "#"
    exp_send -i $connection_id "configure\r"
  	sleep 1
	if {$enableflag == "enable"} {
 	    exp_send -i $connection_id "ip icmp echo-reply\r"
	} else {
	    exp_send -i $connection_id "no ip icmp echo-reply\r"
	}
 	sleep 1
 	exp_send -i $connection_id "exit\r"
	
    if {$bCnn == 0} {
        Netgear_disconnect_switch $switch_name
    }
}

