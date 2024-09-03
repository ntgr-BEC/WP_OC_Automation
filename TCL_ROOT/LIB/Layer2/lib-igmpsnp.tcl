####################################################################################
#  File Name   : lib-igmpsnp.tcl
#
#  Description :
#        This file includes functions used for igmp snooping configuration.
#
#  History     :
#        Date          Programmer         Description
#        05/06/06      Scott Zhang        Created
#
####################################################################################

#*******************************************************************************
#
#  Function Name  : _ntgrIgmpSnpEnable
#
#  Description    : This function is called to enable igmp snooping on specified
#                   switch.
#
#  Usage          : _ntgrIgmpSnpEnable <switch_name>
#
#*******************************************************************************
proc _ntgrIgmpSnpEnable {switch_name} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    exp_send -i $cnn_id "configure\r"
    exp_sleep 1
    exp_send -i $cnn_id "ip igmp\r"
    exp_sleep 1
    
    Netgear_disconnect_switch $switch_name
}

#*******************************************************************************
#
#  Function Name  : _ntgrIgmpSnpDisable
#
#  Description    : This function is called to disable igmp snooping on specified
#                   switch.
#
#  Usage          : _ntgrIgmpSnpDisable <switch_name>
#
#*******************************************************************************
proc _ntgrIgmpSnpDisable {switch_name} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    exp_send -i $cnn_id "configure\r"
    exp_sleep 1
    exp_send -i $cnn_id "no ip igmp\r"
    exp_sleep 1
    
    Netgear_disconnect_switch $switch_name
}

#*******************************************************************************
#
#  Function Name  : _ntgrIgmpSnpMRouterEnable
#
#  Description    : This function is called to enable multicast router on specified
#                   ports.
#
#  Usage          : _ntgrIgmpSnpMRouterEnable <switch_name portlist vlan>
#  Note that vlan could be 'interface' or a real VLAN ID
#
#*******************************************************************************
proc _ntgrIgmpSnpMRouterEnable {switch_name portlist vlan} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    exp_send -i $cnn_id "configure\r"
    exp_sleep 1
    foreach pt $portlist {
        exp_send -i $cnn_id "interface $pt\r"
        exp_sleep 1
        exp_send -i $cnn_id "ip igmp mrouter $vlan\r"
        exp_sleep 1
        exp_send -i $cnn_id "exit\r"
        exp_sleep 1
    }
    
    Netgear_disconnect_switch $switch_name
}

#*******************************************************************************
#
#  Function Name  : _ntgrIgmpSnpMRouterDisable
#
#  Description    : This function is called to disable multicast router on specified
#                   ports.
#
#  Usage          : _ntgrIgmpSnpMRouterDisable <switch_name portlist vlan>
#  Note that vlan could be 'interface' or a real VLAN ID
#
#*******************************************************************************
proc _ntgrIgmpSnpMRouterDisable {switch_name portlist vlan} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    exp_send -i $cnn_id "configure\r"
    exp_sleep 1
    foreach pt $portlist {
        exp_send -i $cnn_id "interface $pt\r"
        exp_sleep 1
        exp_send -i $cnn_id "no ip igmp mrouter $vlan\r"
        exp_sleep 1
        exp_send -i $cnn_id "exit\r"
        exp_sleep 1
    }
    
    Netgear_disconnect_switch $switch_name
}

#*******************************************************************************
#
#  Function Name  : _ntgrIgmpSnpInterfaceModeEnable
#
#  Description    : This function is called to enable igmp snooping on all ports
#                   in the given switch.
#
#  Usage          : _ntgrIgmpSnpInterfaceModeEnable <switch_name>
#
#*******************************************************************************
proc _ntgrIgmpSnpInterfaceModeEnable {switch_name} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    exp_send -i $cnn_id "configure\r"
    exp_sleep 1
    exp_send -i $cnn_id "ip igmp interfacemode\r"
    exp_sleep 1
    
    Netgear_disconnect_switch $switch_name
}

#*******************************************************************************
#
#  Function Name  : _ntgrIgmpSnpInterfaceModeDisable
#
#  Description    : This function is called to disable igmp snooping on all ports
#                   in the given switch.
#
#  Usage          : _ntgrIgmpSnpInterfaceModeDisable <switch_name>
#
#*******************************************************************************
proc _ntgrIgmpSnpInterfaceModeDisable {switch_name} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    exp_send -i $cnn_id "configure\r"
    exp_sleep 1
    exp_send -i $cnn_id "no ip igmp interfacemode\r"
    exp_sleep 1
    
    Netgear_disconnect_switch $switch_name
}

#*******************************************************************************
#
#  Function Name  : _ntgrIgmpSnpConfigIfProperties
#
#  Description    : This function is called to configure igmp snooping parameters
#                   on an interface.
#
#  Usage          : _ntgrIgmpSnpConfigIfProperties <cnn_id> <if_name> <if_template>
#
#*******************************************************************************
proc _ntgrIgmpSnpConfigIfProperties { cnn_id if_name if_template} {
    exp_send -i $cnn_id "configure\r"
    exp_sleep 1
    exp_send -i $cnn_id "interface $if_name\r"
    exp_sleep 1
    
    set if_status [_getIgmpSnpInterfaceStatus $if_template]
    set if_fastleave [_getIgmpSnpFastLeaveStatus $if_template]
    set if_interval [_getIgmpSnpGrpInterval $if_template]
    set if_response [_getIgmpSnpMaxReponseTime $if_template]
    set if_expire [_getIgmpSnpMRouterExpireTime $if_template]
    set if_MRouterVlanMember [_getIgmpSnpMRouterVlanMember $if_template]
    if { $if_status == "enable" } {
        exp_send -i $cnn_id "ip igmp\r"
    } elseif { $if_status == "disable" } {
        exp_send -i $cnn_id "no ip igmp\r"
    }
    exp_sleep 1
    if { $if_fastleave == "enable" } {
        exp_send -i $cnn_id "ip igmp fast-leave\r"
    } elseif { $if_fastleave == "disable" } {
        exp_send -i $cnn_id "no ip igmp fast-leave\r"
    }
    exp_sleep 1
    if { $if_interval != "default" } {
        exp_send -i $cnn_id "ip igmp groupmembership-interval $if_interval\r"
    }
    exp_sleep 1
    if { $if_response != "default" } {
        exp_send -i $cnn_id "ip igmp maxresponse $if_response\r"
    }
    exp_sleep 1
    if { $if_expire != "default" } {
        exp_send -i $cnn_id "ip igmp mcrtrexpiretime $if_expire\r"
    }
    exp_sleep 1
    
    foreach vlan $if_MRouterVlanMember {
        if {$vlan == "all"} {
            exp_send -i $cnn_id "ip igmp mrouter interface\r"
            exp_sleep 1
            break;
        } else {
            exp_send -i $cnn_id "ip igmp mrouter $vlan\r"
        }
        exp_sleep 1
    }
    exp_send -i $cnn_id "exit\r"
    exp_sleep 1
    exp_send -i $cnn_id "exit\r"
    exp_sleep 1
}

#*******************************************************************************
#
#  Function Name  : _ntgrIgmpSnpConfigVlanProperties
#
#  Description    : This function is called to configure igmp snooping parameters
#                   on an vlan.
#
#  Usage          : _ntgrIgmpSnpConfigVlanProperties <cnn_id> <if_name> <if_template>
#
#*******************************************************************************
proc _ntgrIgmpSnpConfigVlanProperties { cnn_id vlan_index template } {
    exp_send -i $cnn_id "vlan database\r"
    exp_sleep 1
    
    set vlan_id [_getVlanID $vlan_index]
    set vlan_status [_getIgmpSnpInterfaceStatus $template]
    set vlan_fastleave [_getIgmpSnpFastLeaveStatus $template]
    set vlan_interval [_getIgmpSnpGrpInterval $template]
    set vlan_response [_getIgmpSnpMaxReponseTime $template]
    set vlan_expire   [_getIgmpSnpMRouterExpireTime $template]
    set vlan_querier  [_ntgrGetIgmpSnpQuerierStatus $template]
    set vlan_querier_interval  [_ntgrGetIgmpSnpQuerierInterval $template]
    
    if { $vlan_status == "enable" } {
        exp_send -i $cnn_id "ip igmp $vlan_id\r"
        exp_sleep 1
    } elseif { $vlan_status == "disable" } {
        exp_send -i $cnn_id "no ip igmp $vlan_id\r"
        exp_sleep 1
    }
    if { $vlan_fastleave == "enable" } {
        exp_send -i $cnn_id "ip igmp fast-leave $vlan_id\r"
        exp_sleep 1
    } elseif { $vlan_fastleave == "disable" } {
        exp_send -i $cnn_id "no ip igmp fast-leave $vlan_id\r"
        exp_sleep 1
    }
    if { $vlan_interval != "default" } {
        exp_send -i $cnn_id "ip igmp groupmembership-interval $vlan_id $vlan_interval\r"
        exp_sleep 1
    }
    if { $vlan_response != "default" } {
        exp_send -i $cnn_id "ip igmp maxresponse $vlan_id $vlan_response\r"
        exp_sleep 1
    }
    if { $vlan_expire != "default" } {
        exp_send -i $cnn_id "ip igmp mcrtrexpiretime $vlan_id $vlan_expire\r"
        exp_sleep 1
    }
    if { $vlan_querier == "enable" } {
        exp_send -i $cnn_id "ip igmpsnooping querier $vlan_id\r"
        exp_sleep 1
    } elseif { $vlan_querier == "disable" } {
        exp_send -i $cnn_id "no ip igmpsnooping querier $vlan_id\r"
        exp_sleep 1
    }
    if { $vlan_querier_interval != "default" } {
        exp_send -i $cnn_id "ip igmpsnooping querier query-interval $vlan_id $vlan_querier_interval\r"
        exp_sleep 1
    }
    
    exp_send -i $cnn_id "exit\r"
    exp_sleep 1
}

#*******************************************************************************
#
#  Function Name  : _ntgrIgmpSnpConfigInterfaceProperties
#
#  Description    : This function is called to configure igmp snooping parameters
#                   on an interface/vlan.
#
#  Usage          : _ntgrIgmpSnpConfigInterfaceProperties <cnn_id> <if_name> 
#                   <if_template>
#
#*******************************************************************************
proc _ntgrIgmpSnpConfigInterfaceProperties { cnn_id if_name if_template} {
    if { [_IsVlanExist $if_name] == 1 } {
        #Configure igmp snooping on vlan database mode
        _ntgrIgmpSnpConfigVlanProperties $cnn_id $if_name $if_template
    } else {
        _ntgrIgmpSnpConfigIfProperties $cnn_id $if_name $if_template
    }
}

#*******************************************************************************
#
#  Function Name  : _ntgrIgmpSnpConfigGlobalProperties
#
#  Description    : This function is called to configure igmp properties for 
#                   a global level on the given switch.
#
#  Usage          : _ntgrIgmpSnpConfigGlobalProperties <switch_name>
#
#*******************************************************************************
proc _ntgrIgmpSnpConfigGlobalProperties {switch_name} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    set if_interval [_getIgmpSnpGrpInterval $switch_name]
    set if_response [_getIgmpSnpMaxReponseTime $switch_name]
    set if_expire [_getIgmpSnpMRouterExpireTime $switch_name]
    set querier   [_ntgrGetIgmpSnpQuerierIP $switch_name]
    
    exp_send -i $cnn_id "configure\r"
    exp_sleep 1
    if { $if_interval != "default" } {
        exp_send -i $cnn_id "ip igmp groupmembership-interval $if_interval\r"
        exp_sleep 1
    }
    if { $if_response != "default" } {
        exp_send -i $cnn_id "ip igmp maxresponse $if_response\r"
        exp_sleep 1
    }
    if { $if_expire != "default" } {
        exp_send -i $cnn_id "ip igmp mcrtrexpiretime $if_expire\r"
        exp_sleep 1
    }
    if { $querier != "default" } {
        exp_send -i $cnn_id "ip igmpsnooping querier ip-address $querier\r"
        exp_sleep 1
    }

    Netgear_disconnect_switch $switch_name
}

#*******************************************************************************
#
#  Function Name  : _ntgrIgmpSnpClearEntry
#
#  Description    : This function is called to clear igmp forwarding entry.
#
#  Usage          : _ntgrIgmpSnpClearEntry <switch_name {entry {}}>
#
#*******************************************************************************
proc _ntgrIgmpSnpClearEntry {switch_name {entry {}}} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    set cmd "clear ip igmp"
    if {$entry != {}} {
        set cmd "$cmd $entry"
    }
    exp_send -i $cnn_id "$cmd\r"
    exp_sleep 1
    exp_send -i $cnn_id "y\r"
    exp_sleep 1
    
    Netgear_disconnect_switch $switch_name
}

#*******************************************************************************
#
#  Function Name  : _ntgrIgmpSnpPortEnable
#
#  Description    : This function is called to enable igmp snooping on ports.
#
#  Usage          : _ntgrIgmpSnpPortEnable <switch_name portlist>
#
#*******************************************************************************
proc _ntgrIgmpSnpPortEnable {switch_name portlist} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    exp_send -i $cnn_id "configure\r"
    exp_sleep 1
    foreach pt $portlist {
        exp_send -i $cnn_id "interface $pt\r"
        exp_sleep 1
        exp_send -i $cnn_id "ip igmp\r"
        exp_sleep 1
        exp_send -i $cnn_id "exit\r"
        exp_sleep 1
    }
    
    Netgear_disconnect_switch $switch_name
}

#*******************************************************************************
#
#  Function Name  : _ntgrIgmpSnpPortDisable
#
#  Description    : This function is called to disable igmp snooping on ports.
#
#  Usage          : _ntgrIgmpSnpPortDisable <switch_name portlist>
#
#*******************************************************************************
proc _ntgrIgmpSnpPortDisable {switch_name portlist} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    exp_send -i $cnn_id "configure\r"
    exp_sleep 1
    foreach pt $portlist {
        exp_send -i $cnn_id "interface $pt\r"
        exp_sleep 1
        exp_send -i $cnn_id "no ip igmp\r"
        exp_sleep 1
        exp_send -i $cnn_id "exit\r"
        exp_sleep 1
    }
    
    Netgear_disconnect_switch $switch_name
}
