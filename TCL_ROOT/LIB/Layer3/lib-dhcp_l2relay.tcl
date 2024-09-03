####################################################################################
#  File Name   : lib-ntgr-dhcp_l2relay.tcl                                         #
#                                                                                  #
#  Description :                                                                   #
#        This file includes functions used for dhcp l2relay configuration.         #
#                                                                                  #
#  History     :                                                                   #
#        Date          Programmer         Description                              #
#        Jan 09 2013   Tony Jing          Created                                  #
#                                                                                  #
####################################################################################


#*******************************************************************************
#
#  Function Name  : _ntgrDHCPL2RelayEnable
#
#  Description    : This function is called to enable dhcp l2relay.
#
#  Usage          : _ntgrDHCPL2RelayEnable <switch_name>
#
#*******************************************************************************
proc _ntgrDHCPL2RelayEnable {switch_name} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    exp_send -i $cnn_id "configure\r"
    exp_sleep 1
    exp_send -i $cnn_id "dhcp l2relay\r"
    exp_sleep 1
    exp_send -i $cnn_id "exit\r"
    exp_sleep 1

    Netgear_disconnect_switch $switch_name
}

#*******************************************************************************
#
#  Function Name  : _ntgrDHCPL2RelayDisable
#
#  Description    : This function is called to disable dhcp l2relay.
#
#  Usage          : _ntgrDHCPL2RelayDisable <switch_name>
#
#*******************************************************************************
proc _ntgrDHCPL2RelayDisable {switch_name} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    exp_send -i $cnn_id "configure\r"
    exp_sleep 1
    exp_send -i $cnn_id "no dhcp l2relay\r"
    exp_sleep 1
    exp_send -i $cnn_id "exit\r"
    exp_sleep 1

    Netgear_disconnect_switch $switch_name
}

#*******************************************************************************
#
#  Function Name  : _ntgrDHCPL2RelayEnableOnVlan
#
#  Description    : This function is called to enable dhcp l2relay on vlan.
#
#  Usage          : _ntgrDHCPL2RelayEnableOnVlan <switch_name> <vlan_list>
#
#*******************************************************************************
proc _ntgrDHCPL2RelayEnableOnVlan {switch_name vlan_list} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    exp_send -i $cnn_id "configure\r"
    exp_sleep 1
    exp_send -i $cnn_id "dhcp l2relay vlan $vlan_list\r"
    exp_sleep 1
    exp_send -i $cnn_id "exit\r"
    exp_sleep 1

    Netgear_disconnect_switch $switch_name
}

#*******************************************************************************
#
#  Function Name  : _ntgrDHCPL2RelayDisableOnVlan
#
#  Description    : This function is called to disable dhcp l2relay.
#
#  Usage          : _ntgrDHCPL2RelayDisableOnVlan <switch_name> <vlan_list>
#
#*******************************************************************************
proc _ntgrDHCPL2RelayDisableOnVlan {switch_name vlan_list} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    exp_send -i $cnn_id "configure\r"
    exp_sleep 1
    exp_send -i $cnn_id "no dhcp l2relay vlan $vlan_list\r"
    exp_sleep 1
    exp_send -i $cnn_id "exit\r"
    exp_sleep 1

    Netgear_disconnect_switch $switch_name
}

#*******************************************************************************
#
#  Function Name  : _ntgrDHCPL2RelayCircuitIDEnableOnVlan
#
#  Description    : This function is called to enable dhcp l2relay circuit-id on vlan.
#
#  Usage          : _ntgrDHCPL2RelayCircuitIDEnableOnVlan <switch_name> <vlan_list>
#
#*******************************************************************************
proc _ntgrDHCPL2RelayCircuitIDEnableOnVlan {switch_name vlan_list} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    exp_send -i $cnn_id "configure\r"
    exp_sleep 1
    exp_send -i $cnn_id "dhcp l2relay circuit-id vlan $vlan_list\r"
    exp_sleep 1
    exp_send -i $cnn_id "exit\r"
    exp_sleep 1

    Netgear_disconnect_switch $switch_name
}

#*******************************************************************************
#
#  Function Name  : _ntgrDHCPL2RelayCircuitIDDisableOnVlan
#
#  Description    : This function is called to disable dhcp l2relay circuit-id on vlan.
#
#  Usage          : _ntgrDHCPL2RelayCircuitIDDisableOnVlan <switch_name> <vlan_list>
#
#*******************************************************************************
proc _ntgrDHCPL2RelayCircuitIDDisableOnVlan {switch_name vlan_list} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    exp_send -i $cnn_id "configure\r"
    exp_sleep 1
    exp_send -i $cnn_id "no dhcp l2relay circuit-id vlan $vlan_list\r"
    exp_sleep 1
    exp_send -i $cnn_id "exit\r"
    exp_sleep 1

    Netgear_disconnect_switch $switch_name
}

#*******************************************************************************
#
#  Function Name  : _ntgrDHCPL2RelaySetRemoteIDOnVlan
#
#  Description    : This function is called to set dhcp l2relay remote-id on vlan.
#
#  Usage          : _ntgrDHCPL2RelaySetRemoteIDOnVlan <switch_name> <vlan_list> <remoteid>
#
#*******************************************************************************
proc _ntgrDHCPL2RelaySetRemoteIDOnVlan {switch_name vlan_list remoteid} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    exp_send -i $cnn_id "configure\r"
    exp_sleep 1
    exp_send -i $cnn_id "dhcp l2relay remote-id $remoteid vlan $vlan_list\r"
    exp_sleep 1
    exp_send -i $cnn_id "exit\r"
    exp_sleep 1

    Netgear_disconnect_switch $switch_name
}

#*******************************************************************************
#
#  Function Name  : _ntgrDHCPL2RelayUnsetRemoteIDOnVlan
#
#  Description    : This function is called to unset dhcp l2relay remote-id on vlan.
#
#  Usage          : _ntgrDHCPL2RelayUnsetRemoteIDOnVlan <switch_name> <vlan_list>
#
#*******************************************************************************
proc _ntgrDHCPL2RelayUnsetRemoteIDOnVlan {switch_name vlan_list} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    exp_send -i $cnn_id "configure\r"
    exp_sleep 1
    exp_send -i $cnn_id "no dhcp l2relay remote-id vlan $vlan_list\r"
    exp_sleep 1
    exp_send -i $cnn_id "exit\r"
    exp_sleep 1

    Netgear_disconnect_switch $switch_name
}


#*******************************************************************************
#
#  Function Name  : _ntgrDHCPL2RelayEnableOnPort
#
#  Description    : This function is called to enable dhcp l2relay on port.
#
#  Usage          : _ntgrDHCPL2RelayEnableOnPort <switch_name> <port_list>
#
#*******************************************************************************
proc _ntgrDHCPL2RelayEnableOnPort {switch_name port_list} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    exp_send -i $cnn_id "configure\r"
    exp_sleep 1
    foreach port $port_list {
        exp_send -i $cnn_id "interface $port\r"
        exp_sleep 1
        exp_send -i $cnn_id "dhcp l2relay\r"
        exp_sleep 1
        exp_send -i $cnn_id "exit\r"
    }
    exp_send -i $cnn_id "exit\r"
    exp_sleep 1

    Netgear_disconnect_switch $switch_name
}

#*******************************************************************************
#
#  Function Name  : _ntgrDHCPL2RelayDisableOnPort
#
#  Description    : This function is called to disable dhcp l2relay on port.
#
#  Usage          : _ntgrDHCPL2RelayDisableOnPort <switch_name> <port_list>
#
#*******************************************************************************
proc _ntgrDHCPL2RelayDisableOnPort {switch_name port_list} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    exp_send -i $cnn_id "configure\r"
    exp_sleep 1
    foreach port $port_list {
        exp_send -i $cnn_id "interface $port\r"
        exp_sleep 1
        exp_send -i $cnn_id "no dhcp l2relay\r"
        exp_sleep 1
        exp_send -i $cnn_id "exit\r"
    }
    exp_send -i $cnn_id "exit\r"
    exp_sleep 1

    Netgear_disconnect_switch $switch_name
}

#*******************************************************************************
#
#  Function Name  : _ntgrDHCPL2RelayTrustEnableOnPort
#
#  Description    : This function is called to enable dhcp l2relay trust on port.
#
#  Usage          : _ntgrDHCPL2RelayTrustEnableOnPort <switch_name> <port_list>
#
#*******************************************************************************
proc _ntgrDHCPL2RelayTrustEnableOnPort {switch_name port_list} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    exp_send -i $cnn_id "configure\r"
    exp_sleep 1
    foreach port $port_list {
        exp_send -i $cnn_id "interface $port\r"
        exp_sleep 1
        exp_send -i $cnn_id "dhcp l2relay trust\r"
        exp_sleep 1
        exp_send -i $cnn_id "exit\r"
    }
    exp_send -i $cnn_id "exit\r"
    exp_sleep 1

    Netgear_disconnect_switch $switch_name
}

#*******************************************************************************
#
#  Function Name  : _ntgrDHCPL2RelayTrustDisableOnPort
#
#  Description    : This function is called to disable dhcp l2relay on port.
#
#  Usage          : _ntgrDHCPL2RelayTrustDisableOnPort <switch_name> <port_list>
#
#*******************************************************************************
proc _ntgrDHCPL2RelayTrustDisableOnPort {switch_name port_list} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    exp_send -i $cnn_id "configure\r"
    exp_sleep 1
    foreach port $port_list {
        exp_send -i $cnn_id "interface $port\r"
        exp_sleep 1
        exp_send -i $cnn_id "no dhcp l2relay trust\r"
        exp_sleep 1
        exp_send -i $cnn_id "exit\r"
    }
    exp_send -i $cnn_id "exit\r"
    exp_sleep 1

    Netgear_disconnect_switch $switch_name
}

#*******************************************************************************
#
#  Function Name  : _ntgrDHCPL2RelayClearAllStats
#
#  Description    : This function is called to clear dhcp l2 relay statistics.
#
#  Usage          : _ntgrDHCPL2RelayClearAllStats <switch_name>
#
#*******************************************************************************
proc _ntgrDHCPL2RelayClearAllStats {switch_name} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    exp_send -i $cnn_id "clear dhcp l2relay statistics interface all\r"
    exp_sleep 1

    Netgear_disconnect_switch $switch_name
}
