#!/bin/sh
################################################################################   
#
#  File Name		  : lib-static-ip.tcl
#
#  Description      :
#         This TCL contains functions to configure Static IP
#
#  Revision History :
#        Date          Programmer        Description
#        -----------------------------------------------------------------------
#        17-Aug-06     Nina Cheng        Created
#
#
#
#
################################################################################
#1*******************************************************************************
#  Function Name	: _ntgrAddStaticRoute
#
#  Description    	: This function is called to add static routes 
#         
#  Usage          	: _ntgrAddStaticRoute <switch_name> <address_list> 
#
#
#*******************************************************************************
proc _ntgrAddStaticRoute {switch_name address_list} {

	set cnn_id [_get_switch_handle $switch_name]
    	set bCnn 1
    	if {[string first "exp" $cnn_id] == -1} {
        	Netgear_connect_switch $switch_name
        	set cnn_id [_get_switch_handle $switch_name]
        	set bCnn 0
    	}
    
	expect -i $cnn_id -re "#"
   	exp_send -i $cnn_id "configure\r"
  	sleep 1
  	
	foreach static_route $address_list {
	        set ip_address [lindex $static_route 0]
	        set subnet_mask [lindex $static_route 1]
	        set next_hop [lindex $static_route 2]
	        set prefrence [lindex $static_route 3]
	       
	        if { $prefrence == "default"} {
	               exp_send -i $cnn_id "ip route $ip_address $subnet_mask $next_hop\r"
	               sleep 1
	        } elseif {$prefrence<1 || $prefrence >255} {
	               Netgear_log_file "lib-static-ip.tcl" "Error:the prefrence must be 1~255!"
	               return
	        } else {
	 	             exp_send -i $cnn_id "ip route $ip_address $subnet_mask $next_hop $prefrence\r"
	 	             sleep 1
	  	}
	}
 	exp_send -i $cnn_id "exit\r"
 	
 	if {$bCnn == 0} {
        	Netgear_disconnect_switch $switch_name
    	}
}
#2*******************************************************************************
#  Function Name	: _ntgrDeleteStaticRoute
#
#  Description    	: This function is called to delete static routes on switch
#         
#  Usage          	: _ntgrDeleteStaticRoute <switch_name> <address_list> 
#
#
#*******************************************************************************
proc _ntgrDeleteStaticRoute {switch_name address_list} {

	set cnn_id [_get_switch_handle $switch_name]
    	set bCnn 1
    	if {[string first "exp" $cnn_id] == -1} {
        	Netgear_connect_switch $switch_name
        	set cnn_id [_get_switch_handle $switch_name]
        	set bCnn 0
    	}

	expect -i $cnn_id -re "#"
   	exp_send -i $cnn_id "configure\r"
  	sleep 1
	foreach static_route $address_list {
		set ip_address [lindex $static_route 0]
		set subnet_mask [lindex $static_route 1]
		exp_send -i $cnn_id "no ip route $ip_address $subnet_mask\r"
		sleep 1
	}
	exp_send -i $cnn_id "exit\r"
 	
 	if {$bCnn == 0} {
        	Netgear_disconnect_switch $switch_name
    	}
}

#3*******************************************************************************
#  Function Name	: _ntgrModifyStaticRoutePrefrence
#
#  Description    	: This function is called to modify prefrence for static routes on switch
#         
#  Usage          	: _ntgrModifyStaticRoutePrefrence <switch_name> <address_list> 
#
#
#*******************************************************************************
proc _ntgrModifyStaticRoutePrefrence {switch_name address_list} {

  set num [llength $address_list]
	Netgear_connect_switch $switch_name
	set connection_id [_get_switch_handle $switch_name]
	
	expect -i $connection_id -re "#"
   	exp_send -i $connection_id "configure\r"
  	sleep 1
  for {set i 1} {$i<=$num} {incr i} {
  set j [expr $i - 1]
  set static_route [lindex $address_list $j]
  set ip_address [lindex $static_route 0]
  set subnet_mask [lindex $static_route 1]
  set next_hop [lindex $static_route 2]
  set prefrence [lindex $static_route 3]
 	exp_send -i $connection_id "ip route $ip_address $subnet_mask $next_hop $prefrence\r"
 	sleep 1
 	}
 	exp_send -i $connection_id "exit\r"
	Netgear_disconnect_switch $switch_name
}
#4*******************************************************************************
#  Function Name	: _ntgrSetStaticRoutePrefrenceToDefault
#
#  Description    	: This function is called to set prefrence for static routes to default on switch
#         
#  Usage          	: _ntgrSetStaticRoutePrefrenceToDefault <switch_name> <address_list> 
#
#
#*******************************************************************************
proc _ntgrSetStaticRoutePrefrenceToDefault {switch_name address_list} {

   set num [llength $address_list]
	 Netgear_connect_switch $switch_name
	 set connection_id [_get_switch_handle $switch_name]
	
	expect -i $connection_id -re "#"
   	exp_send -i $connection_id "configure\r"
  	sleep 1
	for {set i 1} {$i<=$num} {incr i} {
	set j [expr $i - 1]
	set static_route [lindex $address_list $j]
	set ip_address [lindex $static_route 0]
	set subnet_mask [lindex $static_route 1]
	set next_hop [lindex $static_route 2]
	set prefrence [lindex $static_route 3]
 	exp_send -i $connection_id "no ip route $ip_address $subnet_mask $prefrence\r"
 	sleep 1
 	}
 	exp_send -i $connection_id "exit\r"
	Netgear_disconnect_switch $switch_name
}

#5*******************************************************************************
#  Function Name	: _ntgrAddDefaultRoute
#
#  Description    	: This function is called to add default routes 
#         
#  Usage          	: _ntgrAddDefaultRoute <switch_name> <default_route> 
#
#
#*******************************************************************************
proc _ntgrAddDefaultRoute {switch_name default_route_list} {

	Netgear_connect_switch $switch_name
	set connection_id [_get_switch_handle $switch_name]	
	expect -i $connection_id -re "#"
   	exp_send -i $connection_id "configure\r"
  	sleep 1 	
	foreach default_route $default_route_list {
		set next_hop [lindex $default_route 0]
		if {$next_hop != "default"} {
	  		set prefrence [lindex $default_route 1]
	  		if {$prefrence == "default"} {set prefrence " "}
	  		exp_send -i $connection_id "ip route default $next_hop $prefrence\r"
	 		sleep 1
 		}
	}	
 	exp_send -i $connection_id "exit\r"
	Netgear_disconnect_switch $switch_name
}

#********************************************************************************
#  Function Name	: _ntgrAddDefaultStaticRoute
#
#  Description    	: This function is called to add default routes 
#         
#  Usage          	: _ntgrAddDefaultStaticRoute <switch_name> <default_route> 
#
#
#*******************************************************************************
proc _ntgrAddDefaultStaticRoute {switch_name default_route_list} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    exp_send -i $cnn_id "configure\r"
    exp_sleep 1
    foreach defaultroute $default_route_list {
        set nexthop [lindex $defaultroute 0]
        set prefrence [lindex $defaultroute 1]
        if {[string equal $prefrence "default"]} {
            set prefrence ""
        }
        exp_send -i $cnn_id "ip route default $nexthop $prefrence\r"
        exp_sleep 1
        expect -i $cnn_id -re "#"
    }
    exp_send -i $cnn_id "exit\r"
    exp_sleep 1
    
    Netgear_log_file "lib-static-ip.tcl" "add default static route on switcher"
    Netgear_disconnect_switch $switch_name
}

#6*******************************************************************************
#  Function Name	: _ntgrDeleteDefaultRoute
#
#  Description    	: This function is called to delete static routes on switch
#         
#  Usage          	: _ntgrDeleteDefaultRoute <switch_name>
#
#
#*******************************************************************************
proc _ntgrDeleteDefaultRoute {switch_name} {
 
	Netgear_connect_switch $switch_name
	set connection_id [_get_switch_handle $switch_name]
	
	expect -i $connection_id -re "#"
   	exp_send -i $connection_id "configure\r"
  	sleep 1
 	exp_send -i $connection_id "no ip route default\r"
 	sleep 1
 	exp_send -i $connection_id "exit\r"
	Netgear_disconnect_switch $switch_name
}

#********************************************************************************
#  Function Name	: _ntgrDeleteDefaultStaticRoute
#
#  Description    	: This function is called to add default routes 
#         
#  Usage          	: _ntgrDeleteDefaultStaticRoute <switch_name> 
#
#
#*******************************************************************************
proc _ntgrDeleteDefaultStaticRoute {switch_name} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    exp_send -i $cnn_id "configure\r"
    exp_sleep 1
    exp_send -i $cnn_id "no ip route default\r"
    exp_sleep 1
    expect -i $cnn_id -re "#"
    exp_send -i $cnn_id "exit\r"
    exp_sleep 1
    
    Netgear_log_file "lib-static-ip.tcl" "delete default static route on switcher"
    Netgear_disconnect_switch $switch_name
}

#7*******************************************************************************
#  Function Name	: _ntgrModifyDefaultRoutePrefrence
#
#  Description    	: This function is called to modify prefrence for default routes on switch
#         
#  Usage          	: _ntgrModifyDefaultRoutePrefrence <switch_name> <default_route> 
#
#
#*******************************************************************************
proc _ntgrModifyDefaultRoutePrefrence {switch_name default_route} {
  set prefrence [lindex $default_route 1]
	Netgear_connect_switch $switch_name
	set connection_id [_get_switch_handle $switch_name]
	
	expect -i $connection_id -re "#"
   	exp_send -i $connection_id "configure\r"
  	sleep 1
 	exp_send -i $connection_id "ip route default $prefrence\r"
 	sleep 1
 	exp_send -i $connection_id "exit\r"
	Netgear_disconnect_switch $switch_name
}
#8*******************************************************************************
#  Function Name	: _ntgrSetDefaultRoutePrefrenceToDefault
#
#  Description    	: This function is called to set prefrence for static routes to default on switch
#         
#  Usage          	: _ntgrSetDefaultRoutePrefrenceToDefault <switch_name> <default_route>
#
#
#*******************************************************************************
proc _ntgrSetDefaultRoutePrefrenceToDefault {switch_name default_route} {
  
	Netgear_connect_switch $switch_name
	set connection_id [_get_switch_handle $switch_name]
	
	expect -i $connection_id -re "#"
   	exp_send -i $connection_id "configure\r"
  	sleep 1
  set prefrence [lindex $default_route 1]
 	exp_send -i $connection_id "no ip route default $prefrence\r"
 	sleep 1
 	exp_send -i $connection_id "exit\r"
	Netgear_disconnect_switch $switch_name
}

#9*******************************************************************************
#  Function Name	: _ntgrModifyStaticRouteDistance
#
#  Description    	: This function is called to change distance for static routes on switch
#         
#  Usage          	: _ntgrModifyStaticRouteDistance <switch_name> <distance>
#
#
#*******************************************************************************
proc _ntgrModifyStaticRouteDistance {switch_name distance} {
  
	Netgear_connect_switch $switch_name
	set connection_id [_get_switch_handle $switch_name]
	
	expect -i $connection_id -re "#"
   	exp_send -i $connection_id "configure\r"
  	sleep 1
 	exp_send -i $connection_id "ip route distance $distance\r"
 	sleep 1
 	exp_send -i $connection_id "exit\r"
	Netgear_disconnect_switch $switch_name
}
#10*******************************************************************************
#  Function Name	: _ntgrSetStaticRouteDistanceToDefault
#
#  Description    	: This function is called to set distance for static routes to default on switch
#         
#  Usage          	: _ntgrSetStaticRouteDistanceToDefault <switch_name>
#
#
#*******************************************************************************
proc _ntgrSetStaticRouteDistanceToDefault {switch_name} {
  
	Netgear_connect_switch $switch_name
	set connection_id [_get_switch_handle $switch_name]
	
	expect -i $connection_id -re "#"
   	exp_send -i $connection_id "configure\r"
  	sleep 1
 	exp_send -i $connection_id "no ip route distance\r"
 	sleep 1
 	exp_send -i $connection_id "exit\r"
	Netgear_disconnect_switch $switch_name
}
