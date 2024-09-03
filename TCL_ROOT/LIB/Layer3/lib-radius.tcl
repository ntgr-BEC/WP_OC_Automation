####################################################################################
#  File Name   : lib-radius.tcl                                                    #
#                                                                                  #
#  Description :                                                                   #
#        This file includes functions used for RADIUS configuration.               #
#                                                                                  #
#  History     :                                                                   #
#        Date          Programmer         Description                              #
#        May 11, 2007  Scott Zhang        Created                                  #
#                                                                                  #
####################################################################################

#*******************************************************************************
#
#  Function Name  : _ntgrAddRadiusServer
#
#  Description    : This function is called to add a radius server to switch.
#
#  Usage          : _ntgrAddRadiusServer <switch_name mode ip>
#
#*******************************************************************************
proc _ntgrAddRadiusServer {switch_name mode ip} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    exp_send -i $cnn_id "configure\r"
    exp_sleep 1
    exp_send -i $cnn_id "radius server host $mode $ip\r"
    exp_sleep 1

    Netgear_disconnect_switch $switch_name
}

#*******************************************************************************
#
#  Function Name  : _ntgrDelRadiusServer
#
#  Description    : This function is called to delete a radius server from switch.
#
#  Usage          : _ntgrDelRadiusServer <switch_name mode ip>
#
#*******************************************************************************
proc _ntgrDelRadiusServer {switch_name mode ip} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    exp_send -i $cnn_id "configure\r"
    exp_sleep 1
    exp_send -i $cnn_id "no radius server host $mode $ip\r"
    exp_sleep 1

    Netgear_disconnect_switch $switch_name
}

#*******************************************************************************
#
#  Function Name  : _ntgrSetRadiusServerKey
#
#  Description    : This function is called to add a radius server to switch.
#
#  Usage          : _ntgrSetRadiusServerKey <switch_name mode ip key re_enter>
#
#*******************************************************************************
proc _ntgrSetRadiusServerKey {switch_name mode ip key re_enter} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    exp_send -i $cnn_id "configure\r"
    exp_sleep 1
    exp_send -i $cnn_id "radius server key $mode $ip\r"
    exp_sleep 1
    exp_send -i $cnn_id "$key\r"
    exp_sleep 1
    if {$re_enter == {}} {
        exp_send -i $cnn_id "$key\r"
    } else {
        exp_send -i $cnn_id "$re_enter\r"
    }
    exp_sleep 1

    Netgear_disconnect_switch $switch_name
}

#*******************************************************************************
#
#  Function Name  : _ntgrSetPrimaryRadiusServer
#
#  Description    : This function is called to configure the primary radius server.
#
#  Usage          : _ntgrSetPrimaryRadiusServer <switch_name mode ip>
#
#*******************************************************************************
proc _ntgrSetPrimaryRadiusServer {switch_name ip} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    exp_send -i $cnn_id "configure\r"
    exp_sleep 1
    exp_send -i $cnn_id "radius server primary $ip\r"
    exp_sleep 1

    Netgear_disconnect_switch $switch_name
}

#*******************************************************************************
#
#  Function Name  : _ntgrSetRadiusServerPort
#
#  Description    : This function is called to configure radius server's port.
#
#  Usage          : _ntgrSetRadiusServerPort <switch_name mode ip port>
#
#*******************************************************************************
proc _ntgrSetRadiusServerPort {switch_name mode ip port} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    exp_send -i $cnn_id "configure\r"
    exp_sleep 1
    exp_send -i $cnn_id "radius server host $mode $ip $port\r"
    exp_sleep 1

    Netgear_disconnect_switch $switch_name
}

#*******************************************************************************
#
#  Function Name  : _ntgrSetAuthServerType
#
#  Description    : This function is called to configure authentication server's type.
#
#  Usage          : _ntgrSetAuthServerType <switch_name mode ip type>
#
#*******************************************************************************
proc _ntgrSetAuthServerType {switch_name ip type} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    exp_send -i $cnn_id "configure\r"
    exp_sleep 1
    exp_send -i $cnn_id "radius server host auth $ip type $type\r"
    exp_sleep 1

    Netgear_disconnect_switch $switch_name
}

#*******************************************************************************
#
#  Function Name  : _ntgrEnableAccounting
#
#  Description    : This function is called to enable accounting function on switch.
#
#  Usage          : _ntgrEnableAccounting <switch_name>
#
#*******************************************************************************
proc _ntgrEnableAccounting {switch_name} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    exp_send -i $cnn_id "configure\r"
    exp_sleep 1
    exp_send -i $cnn_id "radius accounting mode\r"
    exp_sleep 1

    Netgear_disconnect_switch $switch_name
}

#*******************************************************************************
#
#  Function Name  : _ntgrDisableAccounting
#
#  Description    : This function is called to disable accounting function on switch.
#
#  Usage          : _ntgrDisableAccounting <switch_name>
#
#*******************************************************************************
proc _ntgrDisableAccounting {switch_name} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    exp_send -i $cnn_id "configure\r"
    exp_sleep 1
    exp_send -i $cnn_id "no radius accounting mode\r"
    exp_sleep 1

    Netgear_disconnect_switch $switch_name
}

#*******************************************************************************
#
#  Function Name  : _ntgrEnableRadiusMsgAuthAttr
#
#  Description    : This function is called to enable message authenticator attribute.
#
#  Usage          : _ntgrEnableRadiusMsgAuthAttr <switch_name ip>
#
#*******************************************************************************
proc _ntgrEnableRadiusMsgAuthAttr {switch_name ip} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    exp_send -i $cnn_id "configure\r"
    exp_sleep 1
    exp_send -i $cnn_id "radius server msgauth $ip\r"
    exp_sleep 1

    Netgear_disconnect_switch $switch_name
}

#*******************************************************************************
#
#  Function Name  : _ntgrDisableRadiusMsgAuthAttr
#
#  Description    : This function is called to disable message authenticator attribute.
#
#  Usage          : _ntgrDisableRadiusMsgAuthAttr <switch_name ip>
#
#*******************************************************************************
proc _ntgrDisableRadiusMsgAuthAttr {switch_name ip} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    exp_send -i $cnn_id "configure\r"
    exp_sleep 1
    exp_send -i $cnn_id "no radius server msgauth $ip\r"
    exp_sleep 1

    Netgear_disconnect_switch $switch_name
}

#*******************************************************************************
#
#  Function Name  : _ntgrSetRadiusTimeout
#
#  Description    : This function is called to set radius timeout value.
#
#  Usage          : _ntgrSetRadiusTimeout <switch_name timeout>
#
#*******************************************************************************
proc _ntgrSetRadiusTimeout {switch_name timeout} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    exp_send -i $cnn_id "configure\r"
    exp_sleep 1
    exp_send -i $cnn_id "radius server timeout $timeout\r"
    exp_sleep 1

    Netgear_disconnect_switch $switch_name
}

#*******************************************************************************
#
#  Function Name  : _ntgrResetRadiusTimeout
#
#  Description    : This function is called to reset radius timeout to default.
#
#  Usage          : _ntgrResetRadiusTimeout <switch_name>
#
#*******************************************************************************
proc _ntgrResetRadiusTimeout {switch_name} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    exp_send -i $cnn_id "configure\r"
    exp_sleep 1
    exp_send -i $cnn_id "no radius server timeout\r"
    exp_sleep 1

    Netgear_disconnect_switch $switch_name
}

#*******************************************************************************
#
#  Function Name  : _ntgrSetRadiusRetransmitTime
#
#  Description    : This function is called to set radius retransmit times.
#
#  Usage          : _ntgrSetRadiusRetransmitTime <switch_name time>
#
#*******************************************************************************
proc _ntgrSetRadiusRetransmitTime {switch_name time} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    exp_send -i $cnn_id "configure\r"
    exp_sleep 1
    exp_send -i $cnn_id "radius server retransmit $time\r"
    exp_sleep 1

    Netgear_disconnect_switch $switch_name
}

#*******************************************************************************
#
#  Function Name  : _ntgrResetRadiusRetransmitTime
#
#  Description    : This function is called to set radius retransmit times to default.
#
#  Usage          : _ntgrResetRadiusRetransmitTime <switch_name>
#
#*******************************************************************************
proc _ntgrResetRadiusRetransmitTime {switch_name} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    exp_send -i $cnn_id "configure\r"
    exp_sleep 1
    exp_send -i $cnn_id "no radius server retransmit\r"
    exp_sleep 1

    Netgear_disconnect_switch $switch_name
}

