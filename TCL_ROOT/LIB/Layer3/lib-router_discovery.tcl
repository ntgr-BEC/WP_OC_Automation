#!/bin/sh
################################################################################   
#
#  File Name		  : lib-router_discovery.tcl
#
#  Description      :
#         This TCL contains functions to configure Router Discovery
#
#  Revision History :
#        Date          Programmer        Description
#        -----------------------------------------------------------------------
#        27-Jan-14     Jason Li        Created
#
#
#
#
################################################################################
#1*******************************************************************************
#  Function Name	: _ntgrRouterDiscoveryEnable
#
#  Description    	: This function is called to enable router discovery on port
#         
#  Usage          	: _ntgrRouterDiscoveryEnable <switch_name> <port>
#
#
#*******************************************************************************
proc _ntgrRouterDiscoveryEnable {switch_name port} { 

    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    exp_send -i $cnn_id "configure\r" 
    exp_sleep 1
    exp_send -i $cnn_id "interface $port\r"
    exp_sleep 1
    exp_send -i $cnn_id "ip irdp\r"
    exp_sleep 1
    expect -i $cnn_id -re "#"
    exp_send -i $cnn_id "exit\r"
    exp_sleep 1
    
    Netgear_log_file "lib-router_discovery.tcl" "enable Router discovery on $port"
    Netgear_disconnect_switch $switch_name

} 

#1*******************************************************************************
#  Function Name	: _ntgrRouterDiscoveryDisable
#
#  Description    	: This function is called to disable router discovery on port
#         
#  Usage          	: _ntgrRouterDiscoveryDisable <switch_name> <port>
#
#
#*******************************************************************************
proc _ntgrRouterDiscoveryDisable {switch_name port} { 

    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    exp_send -i $cnn_id "configure\r" 
    exp_sleep 1
    exp_send -i $cnn_id "interface $port\r"
    exp_sleep 1
    exp_send -i $cnn_id "no ip irdp\r"
    exp_sleep 1
    expect -i $cnn_id -re "#"
    exp_send -i $cnn_id "exit\r"
    exp_sleep 1
    
    Netgear_log_file "lib-router_discovery.tcl" "disable Router discovery on $port"
    Netgear_disconnect_switch $switch_name

} 

#1*******************************************************************************
#  Function Name	: _ntgrRouterDiscoverySetMaxAdvert
#
#  Description    	: This function is called to set max advertise interval on port
#         
#  Usage          	: _ntgrRouterDiscoverySetMaxAdvert <switch_name> <port> <maxadv>
#
#
#*******************************************************************************
proc _ntgrRouterDiscoverySetMaxAdvert {switch_name port maxadv} { 

    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    exp_send -i $cnn_id "configure\r" 
    exp_sleep 1
    exp_send -i $cnn_id "interface $port\r"
    exp_sleep 1
    exp_send -i $cnn_id "ip irdp maxadvertinterval $maxadv\r"
    exp_sleep 1
    expect -i $cnn_id -re "#"
    exp_send -i $cnn_id "exit\r"
    exp_sleep 1
    
    Netgear_log_file "lib-router_discovery.tcl" " Set router discovery Max Advertise interval on $port"
    Netgear_disconnect_switch $switch_name

}

#1*******************************************************************************
#  Function Name	: _ntgrRouterDiscoveryDeleteMaxAdvert
#
#  Description    	: This function is called to delete max advertise interval on port
#         
#  Usage          	: _ntgrRouterDiscoveryDeleteMaxAdvert <switch_name> <port> 
#
#
#*******************************************************************************
proc _ntgrRouterDiscoveryDeleteMaxAdvert {switch_name port} { 

    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    exp_send -i $cnn_id "configure\r" 
    exp_sleep 1
    exp_send -i $cnn_id "interface $port\r"
    exp_sleep 1
    exp_send -i $cnn_id "no ip irdp maxadvertinterval\r"
    exp_sleep 1
    expect -i $cnn_id -re "#"
    exp_send -i $cnn_id "exit\r"
    exp_sleep 1
    
    Netgear_log_file "lib-router_discovery.tcl" "Delete router discovery Max Advertise interval on $port"
    Netgear_disconnect_switch $switch_name

} 

#1*******************************************************************************
#  Function Name	: _ntgrRouterDiscoverySetMinAdvert
#
#  Description    	: This function is called to set min advertise interval on port
#         
#  Usage          	: _ntgrRouterDiscoverySetMinAdvert <switch_name> <port> <minadv>
#
#
#*******************************************************************************
proc _ntgrRouterDiscoverySetMinAdvert {switch_name port minadv} { 

    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    exp_send -i $cnn_id "configure\r" 
    exp_sleep 1
    exp_send -i $cnn_id "interface $port\r"
    exp_sleep 1
    exp_send -i $cnn_id "ip irdp minadvertinterval $minadv\r"
    exp_sleep 1
    expect -i $cnn_id -re "#"
    exp_send -i $cnn_id "exit\r"
    exp_sleep 1
    
    Netgear_log_file "lib-router_discovery.tcl" " Set router discovery Min Advertise interval on $port"
    Netgear_disconnect_switch $switch_name

}

#1*******************************************************************************
#  Function Name	: _ntgrRouterDiscoveryDeleteMinAdvert
#
#  Description    	: This function is called to delete min advertise interval on port
#         
#  Usage          	: _ntgrRouterDiscoveryDeleteMinAdvert <switch_name> <port> 
#
#
#*******************************************************************************
proc _ntgrRouterDiscoveryDeleteMinAdvert {switch_name port} { 

    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    exp_send -i $cnn_id "configure\r" 
    exp_sleep 1
    exp_send -i $cnn_id "interface $port\r"
    exp_sleep 1
    exp_send -i $cnn_id "no ip irdp minadvertinterval\r"
    exp_sleep 1
    expect -i $cnn_id -re "#"
    exp_send -i $cnn_id "exit\r"
    exp_sleep 1
    
    Netgear_log_file "lib-router_discovery.tcl" "Delete router discovery Min Advertise interval on $port"
    Netgear_disconnect_switch $switch_name

} 

#1*******************************************************************************
#  Function Name	: _ntgrRouterDiscoverySetHoldTime
#
#  Description    	: This function is called to set hold time on port
#         
#  Usage          	: _ntgrRouterDiscoverySetHoldTime <switch_name> <port> <holdtime> 
#
#
#*******************************************************************************
proc _ntgrRouterDiscoverySetHoldTime {switch_name port holdtime} { 

    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    exp_send -i $cnn_id "configure\r" 
    exp_sleep 1
    exp_send -i $cnn_id "interface $port\r"
    exp_sleep 1
    exp_send -i $cnn_id "ip irdp holdtime $holdtime\r"
    exp_sleep 1
    expect -i $cnn_id -re "#"
    exp_send -i $cnn_id "exit\r"
    exp_sleep 1
    
    Netgear_log_file "lib-router_discovery.tcl" "set router discovery hold time on $port"
    Netgear_disconnect_switch $switch_name

} 

#1*******************************************************************************
#  Function Name	: _ntgrRouterDiscoveryDeleteHoldTime
#
#  Description    	: This function is called to delete hold time on port
#         
#  Usage          	: _ntgrRouterDiscoveryDeleteHoldTime <switch_name> <port> 
#
#
#*******************************************************************************
proc _ntgrRouterDiscoveryDeleteHoldTime {switch_name port} { 

    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    exp_send -i $cnn_id "configure\r" 
    exp_sleep 1
    exp_send -i $cnn_id "interface $port\r"
    exp_sleep 1
    exp_send -i $cnn_id "no ip irdp holdtime\r"
    exp_sleep 1
    expect -i $cnn_id -re "#"
    exp_send -i $cnn_id "exit\r"
    exp_sleep 1
    
    Netgear_log_file "lib-router_discovery.tcl" "delete router discovery hold time on $port"
    Netgear_disconnect_switch $switch_name

}  

#1*******************************************************************************
#  Function Name	: _ntgrRouterDiscoverySetPreference
#
#  Description    	: This function is called to set preference on port
#         
#  Usage          	: _ntgrRouterDiscoverySetPreference <switch_name> <port> <preference> 
#
#
#*******************************************************************************
proc _ntgrRouterDiscoverySetPreference {switch_name port preference} { 

    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    exp_send -i $cnn_id "configure\r" 
    exp_sleep 1
    exp_send -i $cnn_id "interface $port\r"
    exp_sleep 1
    exp_send -i $cnn_id "ip irdp preference $preference\r"
    exp_sleep 1
    expect -i $cnn_id -re "#"
    exp_send -i $cnn_id "exit\r"
    exp_sleep 1
    
    Netgear_log_file "lib-router_discovery.tcl" "set router discovery preference on $port"
    Netgear_disconnect_switch $switch_name

}  

#1*******************************************************************************
#  Function Name	: _ntgrRouterDiscoveryDeletePreference
#
#  Description    	: This function is called to delete preference on port
#         
#  Usage          	: _ntgrRouterDiscoveryDeletePreference <switch_name> <port> <preference> 
#
#
#*******************************************************************************
proc _ntgrRouterDiscoveryDeletePreference {switch_name port} { 

    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    exp_send -i $cnn_id "configure\r" 
    exp_sleep 1
    exp_send -i $cnn_id "interface $port\r"
    exp_sleep 1
    exp_send -i $cnn_id "no ip irdp preference\r"
    exp_sleep 1
    expect -i $cnn_id -re "#"
    exp_send -i $cnn_id "exit\r"
    exp_sleep 1
    
    Netgear_log_file "lib-router_discovery.tcl" "delete router discovery preference on $port"
    Netgear_disconnect_switch $switch_name

} 



