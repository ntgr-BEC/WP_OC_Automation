####################################################################################
#  File Name   : lib-ntgr-udp_relay.tcl                                            #
#                                                                                  #
#  Description :                                                                   #
#        This file includes functions used for udp relay configuration.            #
#                                                                                  #
#  History     :                                                                   #
#        Date          Programmer         Description                              #
#        Dec 19 2012   Tony Jing          Created                                  #
#                                                                                  #
####################################################################################


#*******************************************************************************
#
#  Function Name  : _ntgrIPHelperEnable
#
#  Description    : This function is called to enable ip helper.
#
#  Usage          : _ntgrIPHelperEnable <switch_name>
#
#*******************************************************************************
proc _ntgrIPHelperEnable {switch_name} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    exp_send -i $cnn_id "configure\r"
    exp_sleep 1
    exp_send -i $cnn_id "ip helper enable\r"
    exp_sleep 1
    exp_send -i $cnn_id "exit\r"
    exp_sleep 1

    Netgear_disconnect_switch $switch_name
}

#*******************************************************************************
#
#  Function Name  : _ntgrIPHelperDisable
#
#  Description    : This function is called to disable ip helper.
#
#  Usage          : _ntgrIPHelperDisable <switch_name>
#
#*******************************************************************************
proc _ntgrIPHelperDisable {switch_name} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    exp_send -i $cnn_id "configure\r"
    exp_sleep 1
    exp_send -i $cnn_id "no ip helper enable\r"
    exp_sleep 1
    exp_send -i $cnn_id "exit\r"
    exp_sleep 1

    Netgear_disconnect_switch $switch_name
}

#*******************************************************************************
#
#  Function Name  : _ntgrAddIPHelperAddr
#
#  Description    : This function is called to add ip helper address.
#
#  Usage          : _ntgrAddIPHelperAddr <switch_name>
#
#*******************************************************************************
proc _ntgrAddIPHelperAddr {switch_name} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    set serveraddr   [_getUdpRelayServerAddr $switch_name]
    set udpporttype  [_getUdpRelayUdpPortType $switch_name]
    set updportvalue [_getUdpRelayUdpPortValue $switch_name]
    
    set cmdstr "ip helper-address $serveraddr"
    if {$udpporttype != ""} {
        append cmdstr " $udpporttype"
    }
    if {$updportvalue != ""} {
        append cmdstr " $updportvalue"
    }
    
    exp_send -i $cnn_id "configure\r"
    exp_sleep 1
    exp_send -i $cnn_id "$cmdstr\r"
    exp_sleep 1
    exp_send -i $cnn_id "exit\r"
    exp_sleep 1

    Netgear_disconnect_switch $switch_name
}

#*******************************************************************************
#
#  Function Name  : _ntgrDelIPHelperAddr
#
#  Description    : This function is called to delete ip helper address.
#
#  Usage          : _ntgrDelIPHelperAddr <switch_name>
#
#*******************************************************************************
proc _ntgrDelIPHelperAddr {switch_name} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    set serveraddr   [_getUdpRelayServerAddr $switch_name]
    
    set cmdstr "no ip helper-address $serveraddr"
    
    exp_send -i $cnn_id "configure\r"
    exp_sleep 1
    exp_send -i $cnn_id "$cmdstr\r"
    exp_sleep 1
    exp_send -i $cnn_id "exit\r"
    exp_sleep 1

    Netgear_disconnect_switch $switch_name
} 

#*******************************************************************************
#
#  Function Name  : _ntgrDelAllIPHelperAddr
#
#  Description    : This function is called to delete all ip helper address.
#
#  Usage          : _ntgrDelAllIPHelperAddr <switch_name>
#
#*******************************************************************************
proc _ntgrDelAllIPHelperAddr {switch_name} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    set cmdstr "no ip helper-address"
    
    exp_send -i $cnn_id "configure\r"
    exp_sleep 1
    exp_send -i $cnn_id "$cmdstr\r"
    exp_sleep 1
    exp_send -i $cnn_id "exit\r"
    exp_sleep 1

    Netgear_disconnect_switch $switch_name
} 

#*******************************************************************************
#
#  Function Name  : _ntgrAddIPHelperInterfaceAddr
#
#  Description    : This function is called to add ip helper address on port.
#
#  Usage          : _ntgrAddIPHelperInterfaceAddr <switch_name>
#
#*******************************************************************************
proc _ntgrAddIPHelperInterfaceAddr {switch_name} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    set port   [_getUdpRelayInterface $switch_name]   
    set serveraddr   [_getUdpRelayServerAddrOnInterface $switch_name]
    set udpporttype  [_getUdpRelayUdpPortTypeOnInterface $switch_name]
    set updportvalue [_getUdpRelayUdpPortValueOnInterface $switch_name]
    
    set cmdstr "ip helper-address" 
    if {$serveraddr != "0.0.0.0"} {
        append cmdstr " $serveraddr"
    } else { 
		append cmdstr " discard"
	}
    if {$udpporttype != ""} {
        append cmdstr " $udpporttype"
    }
    if {$updportvalue != ""} {
        append cmdstr " $updportvalue"
    }
    
    exp_send -i $cnn_id "configure\r"
    exp_sleep 1 
    exp_send -i $cnn_id "interface $port\r"
    exp_sleep 1
    exp_send -i $cnn_id "$cmdstr\r"
    exp_sleep 1
    exp_send -i $cnn_id "exit\r"
    exp_sleep 1 
    exp_send -i $cnn_id "exit\r"
    exp_sleep 1

    Netgear_disconnect_switch $switch_name
} 

#*******************************************************************************
#
#  Function Name  : _ntgrDelIPHelperAddrOnInterface
#
#  Description    : This function is called to delete ip helper address on interface.
#
#  Usage          : _ntgrDelIPHelperAddrOnInterface <switch_name>
#
#*******************************************************************************
proc _ntgrDelIPHelperAddrOnInterface {switch_name} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    set port   [_getUdpRelayInterface $switch_name]   
     
    set cmdstr "no ip helper-address"
    
    exp_send -i $cnn_id "configure\r"
    exp_sleep 1 
	exp_send -i $cnn_id "interface $port\r"
    exp_sleep 1
    exp_send -i $cnn_id "$cmdstr\r"
    exp_sleep 1
    exp_send -i $cnn_id "exit\r"
    exp_sleep 1 
    exp_send -i $cnn_id "exit\r"
    exp_sleep 1

    Netgear_disconnect_switch $switch_name
} 