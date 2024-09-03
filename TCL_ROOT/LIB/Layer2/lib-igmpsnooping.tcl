####################################################################################
#  File Name   : lib-igmpsnooping.tcl
#
#  Description :
#        This file includes functions used for igmp snooping configuration.
#
#  History     :
#        Date          Programmer         Description
#        11/15/2010    Tony Jing          Created
#
####################################################################################

#*******************************************************************************
#
#  Function Name  : _ntgrIgmpSnoopingEnable
#
#  Description    : This function is called to enable igmp snooping on specified
#                   switch.
#
#  Usage          : _ntgrIgmpSnoopingEnable <switch_name>
#
#*******************************************************************************
proc _ntgrIgmpSnoopingEnable {switch_name} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    exp_send -i $cnn_id "configure\r"
    exp_sleep 1
    exp_send -i $cnn_id "set igmp\r"
    exp_send -i $cnn_id "no set igmp header-validation\r"
    exp_sleep 1
    expect -i $cnn_id -re "#"
    exp_send -i $cnn_id "exit\r"
    exp_sleep 1
    
    Netgear_log_file "lib-igmpsnooping.tcl" "enable igmpsnooping on switcher"
    Netgear_disconnect_switch $switch_name
}

#*******************************************************************************
#
#  Function Name  : _ntgrIgmpSnoopingDisable
#
#  Description    : This function is called to disable igmp snooping on specified
#                   switch.
#
#  Usage          : _ntgrIgmpSnoopingDisable <switch_name>
#
#*******************************************************************************
proc _ntgrIgmpSnoopingDisable {switch_name} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    exp_send -i $cnn_id "configure\r"
    exp_sleep 1
    exp_send -i $cnn_id "no set igmp\r"
    exp_sleep 1
    expect -i $cnn_id -re "#"
    exp_send -i $cnn_id "exit\r"
    exp_sleep 1
    
    Netgear_log_file "lib-igmpsnooping.tcl" "disable igmpsnooping on switcher"
    Netgear_disconnect_switch $switch_name
}

#*******************************************************************************
#
#  Function Name  : _ntgrIgmpSnoopingUnknownMulticastFilterEnable
#
#  Description    : This function is called to enable igmp snooping unknown-multicast filter on specified
#                   switch.
#
#  Usage          : _ntgrIgmpSnoopingUnknownMulticastFilterEnable <switch_name>
#
#*******************************************************************************
proc _ntgrIgmpSnoopingUnknownMulticastFilterEnable {switch_name} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    exp_send -i $cnn_id "configure\r"
    exp_sleep 1
    exp_send -i $cnn_id "set igmp unknown-multicast filter\r"
    exp_sleep 1
    expect -i $cnn_id -re "#"
    exp_send -i $cnn_id "exit\r"
    exp_sleep 1
    
    Netgear_log_file "lib-igmpsnooping.tcl" "enable igmpsnooping unknown-multicast filter on switcher"
    Netgear_disconnect_switch $switch_name
}

#*******************************************************************************
#
#  Function Name  : _ntgrIgmpSnoopingUnknownMulticastFilterDisable
#
#  Description    : This function is called to disable igmp snooping unknown-multicast filter on specified
#                   switch.
#
#  Usage          : _ntgrIgmpSnoopingUnknownMulticastFilterDisable <switch_name>
#
#*******************************************************************************
proc _ntgrIgmpSnoopingUnknownMulticastFilterDisable {switch_name} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    exp_send -i $cnn_id "configure\r"
    exp_sleep 1
    exp_send -i $cnn_id "no set igmp unknown-multicast filter\r"
    exp_sleep 1
    expect -i $cnn_id -re "#"
    exp_send -i $cnn_id "exit\r"
    exp_sleep 1
    
    Netgear_log_file "lib-igmpsnooping.tcl" "disable igmpsnooping unknown-multicast filter on switcher"
    Netgear_disconnect_switch $switch_name
}

#*******************************************************************************
#
#  Function Name  : _ntgrIgmpSnoopingHeaderValidationEnable
#
#  Description    : This function is called to enable igmp snooping header-validation on specified
#                   switch.
#
#  Usage          : _ntgrIgmpSnoopingHeaderValidationEnable <switch_name>
#
#*******************************************************************************
proc _ntgrIgmpSnoopingHeaderValidationEnable {switch_name} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    exp_send -i $cnn_id "configure\r"
    exp_sleep 1
    exp_send -i $cnn_id "set igmp header-validation\r"
    exp_sleep 1
    expect -i $cnn_id -re "#"
    exp_send -i $cnn_id "exit\r"
    exp_sleep 1
    
    Netgear_log_file "lib-igmpsnooping.tcl" "enable igmpsnooping header-validation on switcher"
    Netgear_disconnect_switch $switch_name
}

#*******************************************************************************
#
#  Function Name  : _ntgrIgmpSnoopingHeaderValidationDisable
#
#  Description    : This function is called to disable igmp snooping header-validation on specified
#                   switch.
#
#  Usage          : _ntgrIgmpSnoopingHeaderValidationDisable <switch_name>
#
#*******************************************************************************
proc _ntgrIgmpSnoopingHeaderValidationDisable {switch_name} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    exp_send -i $cnn_id "configure\r"
    exp_sleep 1
    exp_send -i $cnn_id "no set igmp header-validation\r"
    exp_sleep 1
    expect -i $cnn_id -re "#"
    exp_send -i $cnn_id "exit\r"
    exp_sleep 1
    
    Netgear_log_file "lib-igmpsnooping.tcl" "disable igmpsnooping header-validation on switcher"
    Netgear_disconnect_switch $switch_name
}


#*******************************************************************************
#
#  Function Name  : _ntgrIgmpSnoopingIntfEnable
#
#  Description    : This function is called to enable igmp snooping on specified
#                   interface.
#
#  Usage          : _ntgrIgmpSnoopingIntfEnable <switch_name> <port_list>
#
#*******************************************************************************
proc _ntgrIgmpSnoopingIntfEnable {switch_name port_list} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    exp_send -i $cnn_id "configure\r"
    exp_sleep 1
    foreach port $port_list {
        exp_send -i $cnn_id "interface $port\r"
        exp_sleep 1
        exp_send -i $cnn_id "set igmp\r"
        exp_sleep 1
        expect -i $cnn_id -re "#"
        exp_send -i $cnn_id "exit\r"
        exp_sleep 1
    }
    exp_send -i $cnn_id "exit\r"
    exp_sleep 1
    
    Netgear_log_file "lib-igmpsnooping.tcl" "enable igmpsnooping no interface: $port_list"
    Netgear_disconnect_switch $switch_name
}

#*******************************************************************************
#
#  Function Name  : _ntgrIgmpSnoopingIntfDisable
#
#  Description    : This function is called to disable igmp snooping on specified
#                   interface.
#
#  Usage          : _ntgrIgmpSnoopingIntfDisable <switch_name> <port_list>
#
#*******************************************************************************
proc _ntgrIgmpSnoopingIntfDisable {switch_name port_list} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    exp_send -i $cnn_id "configure\r"
    exp_sleep 1
    foreach port $port_list {
        exp_send -i $cnn_id "interface $port\r"
        exp_sleep 1
        exp_send -i $cnn_id "no set igmp\r"
        exp_sleep 1
        expect -i $cnn_id -re "#"
        exp_send -i $cnn_id "exit\r"
        exp_sleep 1
    }
    exp_send -i $cnn_id "exit\r"
    exp_sleep 1
    
    Netgear_log_file "lib-igmpsnooping.tcl" "disable igmpsnooping on interface: $port_list"
    Netgear_disconnect_switch $switch_name
}

#*******************************************************************************
#
#  Function Name  : _ntgrIgmpSnoopingIntfFastleaveEnable
#
#  Description    : This function is called to enable igmp snooping fastleave on specified
#                   interface.
#
#  Usage          : _ntgrIgmpSnoopingIntfFastleaveEnable <switch_name> <port_list>
#
#*******************************************************************************
proc _ntgrIgmpSnoopingIntfFastleaveEnable {switch_name port_list} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    exp_send -i $cnn_id "configure\r"
    exp_sleep 1
    foreach port $port_list {
        exp_send -i $cnn_id "interface $port\r"
        exp_sleep 1
        exp_send -i $cnn_id "set igmp fast-leave\r"
        exp_sleep 1
        expect -i $cnn_id -re "#"
        exp_send -i $cnn_id "exit\r"
        exp_sleep 1
    }
    exp_send -i $cnn_id "exit\r"
    exp_sleep 1
    
    Netgear_log_file "lib-igmpsnooping.tcl" "enable igmpsnooping fastleave no interface: $port_list"
    Netgear_disconnect_switch $switch_name
}

#*******************************************************************************
#
#  Function Name  : _ntgrIgmpSnoopingIntfFastleaveDisable
#
#  Description    : This function is called to disable igmp snooping fastleave on specified
#                   interface.
#
#  Usage          : _ntgrIgmpSnoopingIntfFastleaveDisable <switch_name> <port_list>
#
#*******************************************************************************
proc _ntgrIgmpSnoopingIntfFastleaveDisable {switch_name port_list} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    exp_send -i $cnn_id "configure\r"
    exp_sleep 1
    foreach port $port_list {
        exp_send -i $cnn_id "interface $port\r"
        exp_sleep 1
        exp_send -i $cnn_id "no set igmp fast-leave\r"
        exp_sleep 1
        expect -i $cnn_id -re "#"
        exp_send -i $cnn_id "exit\r"
        exp_sleep 1
    }
    exp_send -i $cnn_id "exit\r"
    exp_sleep 1
    
    Netgear_log_file "lib-igmpsnooping.tcl" "disable igmpsnooping fastleave no interface: $port_list"
    Netgear_disconnect_switch $switch_name
}


#*******************************************************************************
#
#  Function Name  : _ntgrIgmpSnoopingIntfSetMrouterPort
#
#  Description    : This function is called to set igmp snooping mrouter port on specified
#                   interface.
#
#  Usage          : _ntgrIgmpSnoopingIntfSetMrouterPort <switch_name> <port_list>
#
#*******************************************************************************
proc _ntgrIgmpSnoopingIntfSetMrouterPort {switch_name port_list} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    exp_send -i $cnn_id "configure\r"
    exp_sleep 1
    foreach port $port_list {
        exp_send -i $cnn_id "interface $port\r"
        exp_sleep 1
        exp_send -i $cnn_id "set igmp mrouter interface\r"
        exp_sleep 1
        expect -i $cnn_id -re "#"
        exp_send -i $cnn_id "exit\r"
        exp_sleep 1
    }
    exp_send -i $cnn_id "exit\r"
    exp_sleep 1
    
    Netgear_log_file "lib-igmpsnooping.tcl" "set igmp snooping mrouter port no interface: $port_list"
    Netgear_disconnect_switch $switch_name
}

#*******************************************************************************
#
#  Function Name  : _ntgrIgmpSnoopingIntfUnsetMrouterPort
#
#  Description    : This function is called to unset igmp snooping mrouter port on specified
#                   interface.
#
#  Usage          : _ntgrIgmpSnoopingIntfUnsetMrouterPort <switch_name> <port_list>
#
#*******************************************************************************
proc _ntgrIgmpSnoopingIntfUnsetMrouterPort {switch_name port_list} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    exp_send -i $cnn_id "configure\r"
    exp_sleep 1
    foreach port $port_list {
        exp_send -i $cnn_id "interface $port\r"
        exp_sleep 1
        exp_send -i $cnn_id "no set igmp mrouter interface\r"
        exp_sleep 1
        expect -i $cnn_id -re "#"
        exp_send -i $cnn_id "exit\r"
        exp_sleep 1
    }
    exp_send -i $cnn_id "exit\r"
    exp_sleep 1
    
    Netgear_log_file "lib-igmpsnooping.tcl" "unset igmp snooping mrouter port no interface: $port_list"
    Netgear_disconnect_switch $switch_name
}


#*******************************************************************************
#
#  Function Name  : _ntgrIgmpSnoopingIntfSetMrouterVlan
#
#  Description    : This function is called to set igmp snooping mrouter vlan on specified
#                   interface.
#
#  Usage          : _ntgrIgmpSnoopingIntfSetMrouterVlan <switch_name> <port_list> <vlanid>
#
#*******************************************************************************
proc _ntgrIgmpSnoopingIntfSetMrouterVlan {switch_name port_list vlanid} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    exp_send -i $cnn_id "configure\r"
    exp_sleep 1
    foreach port $port_list {
        exp_send -i $cnn_id "interface $port\r"
        exp_sleep 1
        exp_send -i $cnn_id "set igmp mrouter $vlanid\r"
        exp_sleep 1
        expect -i $cnn_id -re "#"
        exp_send -i $cnn_id "exit\r"
        exp_sleep 1
    }
    exp_send -i $cnn_id "exit\r"
    exp_sleep 1
    
    Netgear_log_file "lib-igmpsnooping.tcl" "set igmp snooping mrouter vlan no interface: $port_list"
    Netgear_disconnect_switch $switch_name
}

#*******************************************************************************
#
#  Function Name  : _ntgrIgmpSnoopingIntfUnsetMrouterVlan
#
#  Description    : This function is called to unset igmp snooping mrouter vlan on specified
#                   interface.
#
#  Usage          : _ntgrIgmpSnoopingIntfUnsetMrouterVlan <switch_name> <port_list> <vlanid>
#
#*******************************************************************************
proc _ntgrIgmpSnoopingIntfUnsetMrouterVlan {switch_name port_list vlanid} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    exp_send -i $cnn_id "configure\r"
    exp_sleep 1
    foreach port $port_list {
        exp_send -i $cnn_id "interface $port\r"
        exp_sleep 1
        exp_send -i $cnn_id "no set igmp mrouter $vlanid\r"
        exp_sleep 1
        expect -i $cnn_id -re "#"
        exp_send -i $cnn_id "exit\r"
        exp_sleep 1
    }
    exp_send -i $cnn_id "exit\r"
    exp_sleep 1
    
    Netgear_log_file "lib-igmpsnooping.tcl" "set igmp snooping mrouter vlan no interface: $port_list"
    Netgear_disconnect_switch $switch_name
}


#*******************************************************************************
#
#  Function Name  : _ntgrIgmpSnoopingIntfSetMcrtrexpiretime
#
#  Description    : This function is called to set igmp snooping mcrtrexpiretime on specified
#                   interface.
#
#  Usage          : _ntgrIgmpSnoopingIntfSetMcrtrexpiretime <switch_name> <port_list> <time>
#
#*******************************************************************************
proc _ntgrIgmpSnoopingIntfSetMcrtrexpiretime {switch_name port_list time} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    exp_send -i $cnn_id "configure\r"
    exp_sleep 1
    foreach port $port_list {
        exp_send -i $cnn_id "interface $port\r"
        exp_sleep 1
        exp_send -i $cnn_id "set igmp mcrtrexpiretime $time\r"
        exp_sleep 1
        expect -i $cnn_id -re "#"
        exp_send -i $cnn_id "exit\r"
        exp_sleep 1
    }
    exp_send -i $cnn_id "exit\r"
    exp_sleep 1
    
    Netgear_log_file "lib-igmpsnooping.tcl" "set igmp snooping mcrtrexpiretime no interface: $port_list"
    Netgear_disconnect_switch $switch_name
}

#*******************************************************************************
#
#  Function Name  : _ntgrIgmpSnoopingIntfUnsetMcrtrexpiretime
#
#  Description    : This function is called to unset igmp snooping mcrtrexpiretime on specified
#                   interface.
#
#  Usage          : _ntgrIgmpSnoopingIntfUnsetMcrtrexpiretime <switch_name> <port_list>
#
#*******************************************************************************
proc _ntgrIgmpSnoopingIntfUnsetMcrtrexpiretime {switch_name port_list} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    exp_send -i $cnn_id "configure\r"
    exp_sleep 1
    foreach port $port_list {
        exp_send -i $cnn_id "interface $port\r"
        exp_sleep 1
        exp_send -i $cnn_id "no set igmp mcrtrexpiretime\r"
        exp_sleep 1
        expect -i $cnn_id -re "#"
        exp_send -i $cnn_id "exit\r"
        exp_sleep 1
    }
    exp_send -i $cnn_id "exit\r"
    exp_sleep 1
    
    Netgear_log_file "lib-igmpsnooping.tcl" "unset igmp snooping mcrtrexpiretime no interface: $port_list"
    Netgear_disconnect_switch $switch_name
}



#*******************************************************************************
#
#  Function Name  : _ntgrIgmpSnoopingVlanEnable
#
#  Description    : This function is called to enable igmp snooping on specified vlan.
#
#  Usage          : _ntgrIgmpSnoopingVlanEnable <switch_name> <vlan_list>
#
#*******************************************************************************
proc _ntgrIgmpSnoopingVlanEnable {switch_name vlan_list} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    exp_send -i $cnn_id "vlan database\r"
    exp_sleep 1
    foreach vlanid $vlan_list {
        exp_send -i $cnn_id "set igmp $vlanid\r"
        exp_sleep 1
        expect -i $cnn_id -re "#"
        exp_sleep 1
    }
    exp_send -i $cnn_id "exit\r"
    exp_sleep 1
    
    Netgear_log_file "lib-igmpsnooping.tcl" "enable igmpsnooping no vlan: $vlan_list"
    Netgear_disconnect_switch $switch_name
}

#*******************************************************************************
#
#  Function Name  : _ntgrIgmpSnoopingVlanDisable
#
#  Description    : This function is called to disable igmp snooping on specified vlan.
#
#  Usage          : _ntgrIgmpSnoopingVlanDisable <switch_name> <vlan_list>
#
#*******************************************************************************
proc _ntgrIgmpSnoopingVlanDisable {switch_name vlan_list} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    exp_send -i $cnn_id "vlan database\r"
    exp_sleep 1
    foreach vlanid $vlan_list {
        exp_send -i $cnn_id "no set igmp $vlanid\r"
        exp_sleep 1
        expect -i $cnn_id -re "#"
        exp_sleep 1
    }
    exp_send -i $cnn_id "exit\r"
    exp_sleep 1
    
    Netgear_log_file "lib-igmpsnooping.tcl" "disable igmpsnooping no vlan: $vlan_list"
    Netgear_disconnect_switch $switch_name
}



#*******************************************************************************
#
#  Function Name  : _ntgrIgmpSnoopingInterfacemodeEnable
#
#  Description    : This function is called to enable igmp snooping interfacemode on specified
#                   switch.
#
#  Usage          : _ntgrIgmpSnoopingInterfacemodeEnable <switch_name>
#
#*******************************************************************************
proc _ntgrIgmpSnoopingInterfacemodeEnable {switch_name} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    exp_send -i $cnn_id "configure\r"
    exp_sleep 1
    exp_send -i $cnn_id "set igmp interfacemode\r"
    exp_sleep 1
    expect -i $cnn_id -re "#"
    exp_send -i $cnn_id "exit\r"
    exp_sleep 1
    
    Netgear_log_file "lib-igmpsnooping.tcl" "enable igmpsnooping interfacemode on switcher"
    Netgear_disconnect_switch $switch_name
}

#*******************************************************************************
#
#  Function Name  : _ntgrIgmpSnoopingInterfacemodeDisable
#
#  Description    : This function is called to disable igmp snooping interfacemode on specified
#                   switch.
#
#  Usage          : _ntgrIgmpSnoopingInterfacemodeDisable <switch_name>
#
#*******************************************************************************
proc _ntgrIgmpSnoopingInterfacemodeDisable {switch_name} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    exp_send -i $cnn_id "configure\r"
    exp_sleep 1
    exp_send -i $cnn_id "no set igmp interfacemode\r"
    exp_sleep 1
    expect -i $cnn_id -re "#"
    exp_send -i $cnn_id "exit\r"
    exp_sleep 1
    
    Netgear_log_file "lib-igmpsnooping.tcl" "disable igmpsnooping interfacemode on switcher"
    Netgear_disconnect_switch $switch_name
}



#*******************************************************************************
#
#  Function Name  : _ntgrIgmpSnoopingClearEntry
#
#  Description    : This function is called to clear igmp snooping entry
#
#  Usage          : _ntgrIgmpSnoopingClearEntry <switch_name>
#
#*******************************************************************************
proc _ntgrIgmpSnoopingClearEntry {switch_name} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    exp_send -i $cnn_id "clear igmpsnooping\r"
    sleep 1
		expect -i $cnn_id -re "\(y/n\)" {
  			exp_send -i $cnn_id "y"
		}
		sleep 1
    expect -i $cnn_id -re "#"
    
    Netgear_log_file "lib-igmpsnooping.tcl" "clear igmpsnooping entry on switcher"
    Netgear_disconnect_switch $switch_name
}




#*******************************************************************************
#
#  Function Name  : _ntgrCheckIgmpv3SnoopingEntry
#
#  Description    : This function is used to check igmpsnooping entry exist or not.
#
#  Usage          : _ntgrCheckIgmpv3SnoopingEntry <switch_name> <ip_list> <notin>
#
#*******************************************************************************
proc _ntgrCheckIgmpv3SnoopingEntry {switch_name ip_list {notin 0}} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    set output ""
    
    exp_send -i $cnn_id "show igmpsnooping ssm entries\r"
    exp_sleep 1
    expect -i $cnn_id -re "show igmpsnooping ssm entries"
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
    foreach ip $ip_list {
        if {$notin == 1} {
            if {[regexp -nocase $ip $output]} {
                set result 0
                break
            }
        } else {
            if {![regexp -nocase $ip $output]} {
                set result 0
                break
            }
        }
    }

    Netgear_log_file "lib-igmpsnooping.tcl" "getting igmpsnooping entry on switcher"
    Netgear_disconnect_switch $switch_name

    return $result
}

#*******************************************************************************
#
#  Function Name  : _ntgrCheckIgmpSnoopingEntry
#
#  Description    : This function is used to check igmpsnooping entry exist or not.
#
#  Usage          : _ntgrCheckIgmpSnoopingEntry <switch_name> <mac_list> <notin>
#
#*******************************************************************************
proc _ntgrCheckIgmpSnoopingEntry {switch_name mac_list {notin 0}} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    set output ""
    
    exp_send -i $cnn_id "show mac-address-table igmpsnooping\r"
    exp_sleep 1
    expect -i $cnn_id -re "show mac-address-table igmpsnooping"
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
    foreach mac $mac_list {
        if {$notin == 1} {
            if {[regexp -nocase $mac $output]} {
                set result 0
                break
            }
        } else {
            if {![regexp -nocase $mac $output]} {
                set result 0
                break
            }
        }
    }

    Netgear_log_file "lib-igmpsnooping.tcl" "getting igmpsnooping entry on switcher"
    Netgear_disconnect_switch $switch_name

    return $result
}

#*******************************************************************************
#
#  Function Name  : _ntgrCheckIgmpSnoopingEntryByMac
#
#  Description    : This function is used to check igmpsnooping entry exist or not by multicast mac.
#
#  Usage          : _ntgrCheckIgmpSnoopingEntryByMac <switch_name> <mac> <check_list>
#
#*******************************************************************************
proc _ntgrCheckIgmpSnoopingEntryByMac {switch_name mac check_list} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    set output ""
    
    exp_send -i $cnn_id "show mac-address-table igmpsnooping\r"
    exp_sleep 1
    expect -i $cnn_id -re "show mac-address-table igmpsnooping"
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
    if {[regexp -nocase $mac $output]} {
        set output [split [string tolower $output] "\n"]
        set mac [string tolower $mac]
        set str [lsearch -regexp -inline $output $mac]
        
        foreach check_item $check_list {
            if {![regexp -nocase $check_item $str]} {
                set result 0
                break
            }
        }
    } else {
        set result 0
    }

    Netgear_log_file "lib-igmpsnooping.tcl" "getting igmpsnooping entry on switcher"
    Netgear_disconnect_switch $switch_name

    return $result
}



#*******************************************************************************
#
#  Function Name  : _ntgrIgmpSnoopingQuerierEnable
#
#  Description    : This function is called to enable igmp snooping querier on specified
#                   switch.
#
#  Usage          : _ntgrIgmpSnoopingQuerierEnable <switch_name>
#
#*******************************************************************************
proc _ntgrIgmpSnoopingQuerierEnable {switch_name} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    exp_send -i $cnn_id "configure\r"
    exp_sleep 1
    exp_send -i $cnn_id "set igmp querier\r"
    exp_sleep 1
    expect -i $cnn_id -re "#"
    exp_send -i $cnn_id "exit\r"
    exp_sleep 1
    
    Netgear_log_file "lib-igmpsnooping.tcl" "enable igmpsnooping querier on switcher"
    Netgear_disconnect_switch $switch_name
}

#*******************************************************************************
#
#  Function Name  : _ntgrIgmpSnoopingQuerierDisable
#
#  Description    : This function is called to disable igmp snooping querier on specified
#                   switch.
#
#  Usage          : _ntgrIgmpSnoopingQuerierDisable <switch_name>
#
#*******************************************************************************
proc _ntgrIgmpSnoopingQuerierDisable {switch_name} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    exp_send -i $cnn_id "configure\r"
    exp_sleep 1
    exp_send -i $cnn_id "no set igmp querier\r"
    exp_sleep 1
    expect -i $cnn_id -re "#"
    exp_send -i $cnn_id "exit\r"
    exp_sleep 1
    
    Netgear_log_file "lib-igmpsnooping.tcl" "disable igmpsnooping querier on switcher"
    Netgear_disconnect_switch $switch_name
}

#*******************************************************************************
#
#  Function Name  : _ntgrIgmpSnoopingQuerierSetConfig
#
#  Description    : This function is called to set igmp snooping querier configuration on specified
#                   switch.
#
#  Usage          : _ntgrIgmpSnoopingQuerierSetConfig <switch_name>
#
#*******************************************************************************
proc _ntgrIgmpSnoopingQuerierSetConfig {switch_name} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    set ipstr    [_ntgrGetIgmpSnpQuerierAddress $switch_name]
    set ver      [_ntgrGetIgmpSnpQuerierVersion $switch_name]
    set interval [_ntgrGetIgmpSnpQuerierInterval $switch_name]
    set expiry   [_ntgrGetIgmpSnpQuerierExpiry $switch_name]
    
    exp_send -i $cnn_id "configure\r"
    exp_sleep 1
    if {$ipstr != "default"} {
        exp_send -i $cnn_id "set igmp querier address $ipstr\r"
        exp_sleep 1
        expect -i $cnn_id -re "#"
    }
    if {$ver != "default"} {
        exp_send -i $cnn_id "set igmp querier version $ver\r"
        exp_sleep 1
        expect -i $cnn_id -re "#"
    }
    if {$interval != "default"} {
        exp_send -i $cnn_id "set igmp querier query-interval $interval\r"
        exp_sleep 1
        expect -i $cnn_id -re "#"
    }
    if {$expiry != "default"} {
        exp_send -i $cnn_id "set igmp querier timer expiry $expiry\r"
        exp_sleep 1
        expect -i $cnn_id -re "#"
    }
    exp_send -i $cnn_id "exit\r"
    exp_sleep 1
    
    Netgear_log_file "lib-igmpsnooping.tcl" "set config of igmpsnooping querier on switcher"
    Netgear_disconnect_switch $switch_name
}

#*******************************************************************************
#
#  Function Name  : _ntgrIgmpSnoopingQuerierUnsetConfig
#
#  Description    : This function is called to unset igmp snooping querier configuration on specified
#                   switch.
#
#  Usage          : _ntgrIgmpSnoopingQuerierUnsetConfig <switch_name>
#
#*******************************************************************************
proc _ntgrIgmpSnoopingQuerierUnsetConfig {switch_name} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    set ipstr    [_ntgrGetIgmpSnpQuerierAddress $switch_name]
    set ver      [_ntgrGetIgmpSnpQuerierVersion $switch_name]
    set interval [_ntgrGetIgmpSnpQuerierInterval $switch_name]
    set expiry   [_ntgrGetIgmpSnpQuerierExpiry $switch_name]
    
    exp_send -i $cnn_id "configure\r"
    exp_sleep 1
    if {$ipstr != "default"} {
        exp_send -i $cnn_id "no set igmp querier address\r"
        exp_sleep 1
        expect -i $cnn_id -re "#"
    }
    if {$ver != "default"} {
        exp_send -i $cnn_id "no set igmp querier version\r"
        exp_sleep 1
        expect -i $cnn_id -re "#"
    }
    if {$interval != "default"} {
        exp_send -i $cnn_id "no set igmp querier query-interval\r"
        exp_sleep 1
        expect -i $cnn_id -re "#"
    }
    if {$expiry != "default"} {
        exp_send -i $cnn_id "no set igmp querier timer expiry\r"
        exp_sleep 1
        expect -i $cnn_id -re "#"
    }
    exp_send -i $cnn_id "exit\r"
    exp_sleep 1
    
    Netgear_log_file "lib-igmpsnooping.tcl" "reset config of igmpsnooping querier on switcher"
    Netgear_disconnect_switch $switch_name
}

#*******************************************************************************
#
#  Function Name  : _ntgrIgmpSnoopingQuerierVlanEnable
#
#  Description    : This function is called to enable igmp snooping querier on specified vlan.
#
#  Usage          : _ntgrIgmpSnoopingQuerierVlanEnable <switch_name> <vlanid>
#
#*******************************************************************************
proc _ntgrIgmpSnoopingQuerierVlanEnable {switch_name vlanid} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    exp_send -i $cnn_id "vlan database\r"
    exp_sleep 1
    exp_send -i $cnn_id "set igmp querier $vlanid\r"
    exp_sleep 1
    expect -i $cnn_id -re "#"
    exp_sleep 1
    exp_send -i $cnn_id "exit\r"
    exp_sleep 1
    
    Netgear_log_file "lib-igmpsnooping.tcl" "enable igmpsnooping querier no vlan: $vlanid"
    Netgear_disconnect_switch $switch_name
}

#*******************************************************************************
#
#  Function Name  : _ntgrIgmpSnoopingQuerierVlanDisable
#
#  Description    : This function is called to disable igmp snooping querier on specified vlan.
#
#  Usage          : _ntgrIgmpSnoopingQuerierVlanDisable <switch_name> <vlanid>
#
#*******************************************************************************
proc _ntgrIgmpSnoopingQuerierVlanDisable {switch_name vlanid} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    exp_send -i $cnn_id "vlan database\r"
    exp_sleep 1
    exp_send -i $cnn_id "no set igmp querier $vlanid\r"
    exp_sleep 1
    expect -i $cnn_id -re "#"
    exp_sleep 1
    exp_send -i $cnn_id "exit\r"
    exp_sleep 1
    
    Netgear_log_file "lib-igmpsnooping.tcl" "disable igmpsnooping querier no vlan: $vlanid"
    Netgear_disconnect_switch $switch_name
}


#*******************************************************************************
#
#  Function Name  : _ntgrIgmpSnoopingGetMFDBCurrentCount
#
#  Description    : This function is called to get current entries of MFDB on specified
#                   switch.
#
#  Usage          : _ntgrIgmpSnoopingGetMFDBCurrentCount <switch_name> <expect_text>
#
#  Author         : jim.xie
#*******************************************************************************
proc _ntgrIgmpSnoopingGetMFDBCurrentCount {switch_name expect_text} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]

    set str1 {}
    set str2 {}
	set max_table {}
	set current_count {}
	
	set output ""
	exp_send -i $cnn_id "show mac-address-table stats\r"
    exp_sleep 1
    expect -i $cnn_id -re "show mac-address-table stats"
    expect {
        -i $cnn_id -re "#" { append output $expect_out(buffer) }
        -i $cnn_id -re "More--" {
            append output $expect_out(buffer)
            exp_send -i $cnn_id " "
            exp_sleep 1
            exp_continue
        }
    }
    Netgear_disconnect_switch $switch_name

	set regSWMAC "(Max MFDB Table Entries\[.\]+)(\[ ]+)(\[0-9\]+)"
    regexp $regSWMAC $output match str1 str2 max_table
	set regSWMAC "(Current Entries\[.\]+)(\[ ]+)(\[0-9\]+)"
    regexp $regSWMAC $output match str1 str2 current_count
	
	set result 0
	if {$expect_text == "Max MFDB Table Entries"} {
	    set result $max_table
	} elseif {$expect_text == "Current Entries"} {
	    set result $current_count
	} else {
	    Netgear_log_file "lib-igmpsnooping.tcl" "Error: Invalid MFDB Configure Type."
	}
	
	return $result
}