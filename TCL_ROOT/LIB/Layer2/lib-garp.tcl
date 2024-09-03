####################################################################################
#  File Name   : lib-garp.tcl
#
#  Description :
#        This file includes functions used for garp configuration.
#
#  History     :
#        Date          Programmer         Description
#        09/18/2012    Tony Jing          Created
#
####################################################################################



#*******************************************************************************
#
#  Function Name  : _ntgrGvrpEnable
#
#  Description    : This function is called to enable gvrp on specified switch.
#
#  Usage          : _ntgrGvrpEnable <switch_name>
#
#*******************************************************************************
proc _ntgrGvrpEnable {switch_name} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    exp_send -i $cnn_id "set gvrp adminmode\r"
    exp_sleep 1
    expect -i $cnn_id -re "#"
    exp_sleep 1
    
    Netgear_log_file "lib-garp.tcl" "enable gvrp on switcher"
    Netgear_disconnect_switch $switch_name
}

#*******************************************************************************
#
#  Function Name  : _ntgrGvrpDisable
#
#  Description    : This function is called to disable gvrp on specified switch.
#
#  Usage          : _ntgrGvrpDisable <switch_name>
#
#*******************************************************************************
proc _ntgrGvrpDisable {switch_name} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    exp_send -i $cnn_id "no set gvrp adminmode\r"
    exp_sleep 1
    expect -i $cnn_id -re "#"
    exp_sleep 1
    
    Netgear_log_file "lib-garp.tcl" "disable gvrp on switcher"
    Netgear_disconnect_switch $switch_name
}

#*******************************************************************************
#
#  Function Name  : _ntgrGvrpIntfEnable
#
#  Description    : This function is called to enable gvrp on specified interface.
#
#  Usage          : _ntgrGvrpIntfEnable <switch_name> <port_list>
#
#*******************************************************************************
proc _ntgrGvrpIntfEnable {switch_name port_list} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    exp_send -i $cnn_id "configure\r"
    exp_sleep 1
    foreach port $port_list {
        exp_send -i $cnn_id "interface $port\r"
        exp_sleep 1
        exp_send -i $cnn_id "set gvrp interfacemode\r"
        exp_sleep 1
        expect -i $cnn_id -re "#"
        exp_send -i $cnn_id "exit\r"
        exp_sleep 1
    }
    exp_send -i $cnn_id "exit\r"
    exp_sleep 1
    
    Netgear_log_file "lib-garp.tcl" "enable gvrp on interface: $port_list"
    Netgear_disconnect_switch $switch_name
}

#*******************************************************************************
#
#  Function Name  : _ntgrGvrpIntfDisable
#
#  Description    : This function is called to disable gvrp on specified interface.
#
#  Usage          : _ntgrGvrpIntfDisable <switch_name> <port_list>
#
#*******************************************************************************
proc _ntgrGvrpIntfDisable {switch_name port_list} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    exp_send -i $cnn_id "configure\r"
    exp_sleep 1
    foreach port $port_list {
        exp_send -i $cnn_id "interface $port\r"
        exp_sleep 1
        exp_send -i $cnn_id "no set gvrp interfacemode\r"
        exp_sleep 1
        expect -i $cnn_id -re "#"
        exp_send -i $cnn_id "exit\r"
        exp_sleep 1
    }
    exp_send -i $cnn_id "exit\r"
    exp_sleep 1
    
    Netgear_log_file "lib-garp.tcl" "disable gvrp on interface: $port_list"
    Netgear_disconnect_switch $switch_name
}