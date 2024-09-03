####################################################################################
#  File Name   : lib-mldsnooping.tcl
#
#  Description :
#        This file includes functions used for mld snooping configuration.
#
#  History     :
#        Date          Programmer         Description
#        11/29/2010    Tony Jing          Created
#
####################################################################################

#*******************************************************************************
#
#  Function Name  : _ntgrMldSnoopingEnable
#
#  Description    : This function is called to enable mld snooping on specified
#                   switch.
#
#  Usage          : _ntgrMldSnoopingEnable <switch_name>
#
#*******************************************************************************
proc _ntgrMldSnoopingEnable {switch_name} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    exp_send -i $cnn_id "configure\r"
    exp_sleep 1
    exp_send -i $cnn_id "set mld\r"
    exp_sleep 1
    expect -i $cnn_id -re "#"
    exp_send -i $cnn_id "exit\r"
    exp_sleep 1
    
    Netgear_log_file "lib-mldsnooping.tcl" "enable mldsnooping on switcher"
    Netgear_disconnect_switch $switch_name
}

#*******************************************************************************
#
#  Function Name  : _ntgrMldSnoopingDisable
#
#  Description    : This function is called to disable mld snooping on specified
#                   switch.
#
#  Usage          : _ntgrMldSnoopingDisable <switch_name>
#
#*******************************************************************************
proc _ntgrMldSnoopingDisable {switch_name} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    exp_send -i $cnn_id "configure\r"
    exp_sleep 1
    exp_send -i $cnn_id "no set mld\r"
    exp_sleep 1
    expect -i $cnn_id -re "#"
    exp_send -i $cnn_id "exit\r"
    exp_sleep 1
    
    Netgear_log_file "lib-mldsnooping.tcl" "disable mldsnooping on switcher"
    Netgear_disconnect_switch $switch_name
}

#*******************************************************************************
#
#  Function Name  : _ntgrMldSnoopingUnknownMulticastFilterEnable
#
#  Description    : This function is called to enable mld snooping unknown-multicast filter on specified
#                   switch.
#
#  Usage          : _ntgrMldSnoopingUnknownMulticastFilterEnable <switch_name>
#
#*******************************************************************************
proc _ntgrMldSnoopingUnknownMulticastFilterEnable {switch_name} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    exp_send -i $cnn_id "configure\r"
    exp_sleep 1
    exp_send -i $cnn_id "set mld unknown-multicast filter\r"
    exp_sleep 1
    expect -i $cnn_id -re "#"
    exp_send -i $cnn_id "exit\r"
    exp_sleep 1
    
    Netgear_log_file "lib-mldsnooping.tcl" "enable mldsnooping unknown-multicast filter on switcher"
    Netgear_disconnect_switch $switch_name
}

#*******************************************************************************
#
#  Function Name  : _ntgrMldSnoopingUnknownMulticastFilterDisable
#
#  Description    : This function is called to disable mld snooping unknown-multicast filter on specified
#                   switch.
#
#  Usage          : _ntgrMldSnoopingUnknownMulticastFilterDisable <switch_name>
#
#*******************************************************************************
proc _ntgrMldSnoopingUnknownMulticastFilterDisable {switch_name} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    exp_send -i $cnn_id "configure\r"
    exp_sleep 1
    exp_send -i $cnn_id "no set mld unknown-multicast filter\r"
    exp_sleep 1
    expect -i $cnn_id -re "#"
    exp_send -i $cnn_id "exit\r"
    exp_sleep 1
    
    Netgear_log_file "lib-mldsnooping.tcl" "disable mldsnooping unknown-multicast filter on switcher"
    Netgear_disconnect_switch $switch_name
}

#*******************************************************************************
#
#  Function Name  : _ntgrMldSnoopingIntfEnable
#
#  Description    : This function is called to enable mld snooping on specified
#                   interface.
#
#  Usage          : _ntgrMldSnoopingIntfEnable <switch_name> <port_list>
#
#*******************************************************************************
proc _ntgrMldSnoopingIntfEnable {switch_name port_list} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    exp_send -i $cnn_id "configure\r"
    exp_sleep 1
    foreach port $port_list {
        exp_send -i $cnn_id "interface $port\r"
        exp_sleep 1
        exp_send -i $cnn_id "set mld\r"
        exp_sleep 1
        expect -i $cnn_id -re "#"
        exp_send -i $cnn_id "exit\r"
        exp_sleep 1
    }
    exp_send -i $cnn_id "exit\r"
    exp_sleep 1
    
    Netgear_log_file "lib-mldsnooping.tcl" "enable mldsnooping no interface: $port_list"
    Netgear_disconnect_switch $switch_name
}

#*******************************************************************************
#
#  Function Name  : _ntgrMldSnoopingIntfDisable
#
#  Description    : This function is called to disable mld snooping on specified
#                   interface.
#
#  Usage          : _ntgrMldSnoopingIntfDisable <switch_name> <port_list>
#
#*******************************************************************************
proc _ntgrMldSnoopingIntfDisable {switch_name port_list} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    exp_send -i $cnn_id "configure\r"
    exp_sleep 1
    foreach port $port_list {
        exp_send -i $cnn_id "interface $port\r"
        exp_sleep 1
        exp_send -i $cnn_id "no set mld\r"
        exp_sleep 1
        expect -i $cnn_id -re "#"
        exp_send -i $cnn_id "exit\r"
        exp_sleep 1
    }
    exp_send -i $cnn_id "exit\r"
    exp_sleep 1
    
    Netgear_log_file "lib-mldsnooping.tcl" "disable mldsnooping on interface: $port_list"
    Netgear_disconnect_switch $switch_name
}

#*******************************************************************************
#
#  Function Name  : _ntgrMldSnoopingIntfFastleaveEnable
#
#  Description    : This function is called to enable mld snooping fastleave on specified
#                   interface.
#
#  Usage          : _ntgrMldSnoopingIntfFastleaveEnable <switch_name> <port_list>
#
#*******************************************************************************
proc _ntgrMldSnoopingIntfFastleaveEnable {switch_name port_list} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    exp_send -i $cnn_id "configure\r"
    exp_sleep 1
    foreach port $port_list {
        exp_send -i $cnn_id "interface $port\r"
        exp_sleep 1
        exp_send -i $cnn_id "set mld fast-leave\r"
        exp_sleep 1
        expect -i $cnn_id -re "#"
        exp_send -i $cnn_id "exit\r"
        exp_sleep 1
    }
    exp_send -i $cnn_id "exit\r"
    exp_sleep 1
    
    Netgear_log_file "lib-mldsnooping.tcl" "enable mldsnooping fastleave no interface: $port_list"
    Netgear_disconnect_switch $switch_name
}

#*******************************************************************************
#
#  Function Name  : _ntgrMldSnoopingIntfFastleaveDisable
#
#  Description    : This function is called to disable mld snooping fastleave on specified
#                   interface.
#
#  Usage          : _ntgrMldSnoopingIntfFastleaveDisable <switch_name> <port_list>
#
#*******************************************************************************
proc _ntgrMldSnoopingIntfFastleaveDisable {switch_name port_list} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    exp_send -i $cnn_id "configure\r"
    exp_sleep 1
    foreach port $port_list {
        exp_send -i $cnn_id "interface $port\r"
        exp_sleep 1
        exp_send -i $cnn_id "no set mld fast-leave\r"
        exp_sleep 1
        expect -i $cnn_id -re "#"
        exp_send -i $cnn_id "exit\r"
        exp_sleep 1
    }
    exp_send -i $cnn_id "exit\r"
    exp_sleep 1
    
    Netgear_log_file "lib-mldsnooping.tcl" "disable mldsnooping fastleave no interface: $port_list"
    Netgear_disconnect_switch $switch_name
}


#*******************************************************************************
#
#  Function Name  : _ntgrMldSnoopingIntfSetMrouterPort
#
#  Description    : This function is called to set mld snooping mrouter port on specified
#                   interface.
#
#  Usage          : _ntgrMldSnoopingIntfSetMrouterPort <switch_name> <port_list>
#
#*******************************************************************************
proc _ntgrMldSnoopingIntfSetMrouterPort {switch_name port_list} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    exp_send -i $cnn_id "configure\r"
    exp_sleep 1
    foreach port $port_list {
        exp_send -i $cnn_id "interface $port\r"
        exp_sleep 1
        exp_send -i $cnn_id "set mld mrouter interface\r"
        exp_sleep 1
        expect -i $cnn_id -re "#"
        exp_send -i $cnn_id "exit\r"
        exp_sleep 1
    }
    exp_send -i $cnn_id "exit\r"
    exp_sleep 1
    
    Netgear_log_file "lib-mldsnooping.tcl" "set mldsnooping mrouter port no interface: $port_list"
    Netgear_disconnect_switch $switch_name
}

#*******************************************************************************
#
#  Function Name  : _ntgrMldSnoopingIntfUnsetMrouterPort
#
#  Description    : This function is called to unset mld snooping mrouter port on specified
#                   interface.
#
#  Usage          : _ntgrMldSnoopingIntfUnsetMrouterPort <switch_name> <port_list>
#
#*******************************************************************************
proc _ntgrMldSnoopingIntfUnsetMrouterPort {switch_name port_list} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    exp_send -i $cnn_id "configure\r"
    exp_sleep 1
    foreach port $port_list {
        exp_send -i $cnn_id "interface $port\r"
        exp_sleep 1
        exp_send -i $cnn_id "no set mld mrouter interface\r"
        exp_sleep 1
        expect -i $cnn_id -re "#"
        exp_send -i $cnn_id "exit\r"
        exp_sleep 1
    }
    exp_send -i $cnn_id "exit\r"
    exp_sleep 1
    
    Netgear_log_file "lib-mldsnooping.tcl" "unset mldsnooping mrouter port no interface: $port_list"
    Netgear_disconnect_switch $switch_name
}


#*******************************************************************************
#
#  Function Name  : _ntgrMldSnoopingIntfSetMrouterVlan
#
#  Description    : This function is called to set mld snooping mrouter vlan on specified
#                   interface.
#
#  Usage          : _ntgrMldSnoopingIntfSetMrouterVlan <switch_name> <port_list> <vlanid>
#
#*******************************************************************************
proc _ntgrMldSnoopingIntfSetMrouterVlan {switch_name port_list vlanid} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    exp_send -i $cnn_id "configure\r"
    exp_sleep 1
    foreach port $port_list {
        exp_send -i $cnn_id "interface $port\r"
        exp_sleep 1
        exp_send -i $cnn_id "set mld mrouter $vlanid\r"
        exp_sleep 1
        expect -i $cnn_id -re "#"
        exp_send -i $cnn_id "exit\r"
        exp_sleep 1
    }
    exp_send -i $cnn_id "exit\r"
    exp_sleep 1
    
    Netgear_log_file "lib-mldsnooping.tcl" "set mldsnooping mrouter vlan no interface: $port_list"
    Netgear_disconnect_switch $switch_name
}

#*******************************************************************************
#
#  Function Name  : _ntgrMldSnoopingIntfUnsetMrouterVlan
#
#  Description    : This function is called to unset mld snooping mrouter vlan on specified
#                   interface.
#
#  Usage          : _ntgrMldSnoopingIntfUnsetMrouterVlan <switch_name> <port_list> <vlanid>
#
#*******************************************************************************
proc _ntgrMldSnoopingIntfUnsetMrouterVlan {switch_name port_list vlanid} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    exp_send -i $cnn_id "configure\r"
    exp_sleep 1
    foreach port $port_list {
        exp_send -i $cnn_id "interface $port\r"
        exp_sleep 1
        exp_send -i $cnn_id "no set mld mrouter $vlanid\r"
        exp_sleep 1
        expect -i $cnn_id -re "#"
        exp_send -i $cnn_id "exit\r"
        exp_sleep 1
    }
    exp_send -i $cnn_id "exit\r"
    exp_sleep 1
    
    Netgear_log_file "lib-mldsnooping.tcl" "set mldsnooping mrouter vlan no interface: $port_list"
    Netgear_disconnect_switch $switch_name
}


#*******************************************************************************
#
#  Function Name  : _ntgrMldSnoopingIntfSetMcrtrexpiretime
#
#  Description    : This function is called to set mld snooping mcrtrexpiretime on specified
#                   interface.
#
#  Usage          : _ntgrMldSnoopingIntfSetMcrtrexpiretime <switch_name> <port_list> <time>
#
#*******************************************************************************
proc _ntgrMldSnoopingIntfSetMcrtrexpiretime {switch_name port_list time} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    exp_send -i $cnn_id "configure\r"
    exp_sleep 1
    foreach port $port_list {
        exp_send -i $cnn_id "interface $port\r"
        exp_sleep 1
        exp_send -i $cnn_id "set mld mcrtrexpiretime $time\r"
        exp_sleep 1
        expect -i $cnn_id -re "#"
        exp_send -i $cnn_id "exit\r"
        exp_sleep 1
    }
    exp_send -i $cnn_id "exit\r"
    exp_sleep 1
    
    Netgear_log_file "lib-mldsnooping.tcl" "set mldsnooping mcrtrexpiretime no interface: $port_list"
    Netgear_disconnect_switch $switch_name
}

#*******************************************************************************
#
#  Function Name  : _ntgrMldSnoopingIntfUnsetMcrtrexpiretime
#
#  Description    : This function is called to unset mld snooping mcrtrexpiretime on specified
#                   interface.
#
#  Usage          : _ntgrMldSnoopingIntfUnsetMcrtrexpiretime <switch_name> <port_list>
#
#*******************************************************************************
proc _ntgrMldSnoopingIntfUnsetMcrtrexpiretime {switch_name port_list} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    exp_send -i $cnn_id "configure\r"
    exp_sleep 1
    foreach port $port_list {
        exp_send -i $cnn_id "interface $port\r"
        exp_sleep 1
        exp_send -i $cnn_id "no set mld mcrtrexpiretime\r"
        exp_sleep 1
        expect -i $cnn_id -re "#"
        exp_send -i $cnn_id "exit\r"
        exp_sleep 1
    }
    exp_send -i $cnn_id "exit\r"
    exp_sleep 1
    
    Netgear_log_file "lib-mldsnooping.tcl" "unset mldsnooping mcrtrexpiretime no interface: $port_list"
    Netgear_disconnect_switch $switch_name
}




#*******************************************************************************
#
#  Function Name  : _ntgrMldSnoopingInterfacemodeEnable
#
#  Description    : This function is called to enable mld snooping interfacemode on specified
#                   switch.
#
#  Usage          : _ntgrMldSnoopingInterfacemodeEnable <switch_name>
#
#*******************************************************************************
proc _ntgrMldSnoopingInterfacemodeEnable {switch_name} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    exp_send -i $cnn_id "configure\r"
    exp_sleep 1
    exp_send -i $cnn_id "set mld interfacemode\r"
    exp_sleep 1
    expect -i $cnn_id -re "#"
    exp_send -i $cnn_id "exit\r"
    exp_sleep 1
    
    Netgear_log_file "lib-mldsnooping.tcl" "enable mldsnooping interfacemode on switcher"
    Netgear_disconnect_switch $switch_name
}

#*******************************************************************************
#
#  Function Name  : _ntgrMldSnoopingInterfacemodeDisable
#
#  Description    : This function is called to disable mld snooping interfacemode on specified
#                   switch.
#
#  Usage          : _ntgrMldSnoopingInterfacemodeDisable <switch_name>
#
#*******************************************************************************
proc _ntgrMldSnoopingInterfacemodeDisable {switch_name} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    exp_send -i $cnn_id "configure\r"
    exp_sleep 1
    exp_send -i $cnn_id "no set mld interfacemode\r"
    exp_sleep 1
    expect -i $cnn_id -re "#"
    exp_send -i $cnn_id "exit\r"
    exp_sleep 1
    
    Netgear_log_file "lib-mldsnooping.tcl" "disable mldsnooping interfacemode on switcher"
    Netgear_disconnect_switch $switch_name
}


#*******************************************************************************
#
#  Function Name  : _ntgrMldSnoopingVlanEnable
#
#  Description    : This function is called to enable mld snooping on vlan on specified
#                   switch.
#
#  Usage          : _ntgrMldSnoopingVlanEnable <switch_name> <vlan_list>
#
#*******************************************************************************
proc _ntgrMldSnoopingVlanEnable {switch_name vlan_list} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    exp_send -i $cnn_id "vlan database\r"
    exp_sleep 1
    foreach vlanid $vlan_list {
        exp_send -i $cnn_id "set mld $vlanid\r"
        exp_sleep 1
        expect -i $cnn_id -re "#"
        exp_sleep 1
    }
    exp_send -i $cnn_id "exit\r"
    exp_sleep 1
    
    Netgear_log_file "lib-mldsnooping.tcl" "enable mldsnooping on vlan: $vlan_list"
    Netgear_disconnect_switch $switch_name
}

#*******************************************************************************
#
#  Function Name  : _ntgrMldSnoopingVlanDisable
#
#  Description    : This function is called to disable mld snooping on vlan on specified
#                   switch.
#
#  Usage          : _ntgrMldSnoopingVlanDisable <switch_name> <vlan_list>
#
#*******************************************************************************
proc _ntgrMldSnoopingVlanDisable {switch_name vlan_list} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    exp_send -i $cnn_id "vlan database\r"
    exp_sleep 1
    foreach vlanid $vlan_list {
        exp_send -i $cnn_id "no set mld $vlanid\r"
        exp_sleep 1
        expect -i $cnn_id -re "#"
        exp_sleep 1
    }
    exp_send -i $cnn_id "exit\r"
    exp_sleep 1
    
    Netgear_log_file "lib-mldsnooping.tcl" "disable mldsnooping on vlan: $vlan_list"
    Netgear_disconnect_switch $switch_name
}




#*******************************************************************************
#
#  Function Name  : _ntgrMldSnoopingClearEntry
#
#  Description    : This function is called to clear mld snooping entry
#
#  Usage          : _ntgrMldSnoopingClearEntry <switch_name>
#
#*******************************************************************************
proc _ntgrMldSnoopingClearEntry {switch_name} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    exp_send -i $cnn_id "clear mldsnooping\r"
    sleep 1
		expect -i $cnn_id -re "\(y/n\)" {
  			exp_send -i $cnn_id "y"
		}
		sleep 1
    expect -i $cnn_id -re "#"
    
    Netgear_log_file "lib-mldsnooping.tcl" "clear mldsnooping entry on switcher"
    Netgear_disconnect_switch $switch_name
}




#*******************************************************************************
#
#  Function Name  : _ntgrCheckMldSnoopingEntry
#
#  Description    : This function is used to check mldsnooping entry exist or not.
#
#  Usage          : _ntgrCheckMldSnoopingEntry <switch_name> <mac_list> <notin>
#
#*******************************************************************************
proc _ntgrCheckMldSnoopingEntry {switch_name mac_list {notin 0}} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    set output ""
    
    exp_send -i $cnn_id "show mac-address-table mldsnooping\r"
    exp_sleep 1
    expect -i $cnn_id -re "show mac-address-table mldsnooping"
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

    Netgear_log_file "lib-mldsnooping.tcl" "getting mldsnooping entry on switcher"
    Netgear_disconnect_switch $switch_name

    return $result
}

#*******************************************************************************
#
#  Function Name  : _ntgrCheckMldSnoopingV2Entry
#
#  Description    : This function is used to check mldsnooping entry (V2) exist or not.
#                   From version 11.0, V2 can't found in the GUI, and the command change to <show mldsnooping ssm groups>
#
#  Usage          : _ntgrCheckMldSnoopingV2Entry <switch_name> <ip_list> <notin>
#
#  Author         : jim.xie
#
#*******************************************************************************
proc _ntgrCheckMldSnoopingV2Entry {switch_name ip_list {notin 0}} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    set output ""
    
    exp_send -i $cnn_id "show mldsnooping ssm groups\r"
    exp_sleep 1
    expect -i $cnn_id -re "show mldsnooping ssm groups"
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

    Netgear_disconnect_switch $switch_name

    return $result
}

#*******************************************************************************
#
#  Function Name  : _ntgrCheckMldSnoopingEntryByMac
#
#  Description    : This function is used to check mldsnooping entry exist or not by multicast mac.
#
#  Usage          : _ntgrCheckMldSnoopingEntryByMac <switch_name> <mac> <check_list>
#
#*******************************************************************************
proc _ntgrCheckMldSnoopingEntryByMac {switch_name mac check_list} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    set output ""
    
    exp_send -i $cnn_id "show mac-address-table mldsnooping\r"
    exp_sleep 1
    expect -i $cnn_id -re "show mac-address-table mldsnooping"
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

    Netgear_log_file "lib-mldsnooping.tcl" "getting mldsnooping entry on switcher"
    Netgear_disconnect_switch $switch_name

    return $result
}





#*******************************************************************************
#
#  Function Name  : _ntgrMldSnoopingQuerierEnable
#
#  Description    : This function is called to enable mld snooping querier on specified
#                   switch.
#
#  Usage          : _ntgrMldSnoopingQuerierEnable <switch_name>
#
#*******************************************************************************
proc _ntgrMldSnoopingQuerierEnable {switch_name} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    exp_send -i $cnn_id "configure\r"
    exp_sleep 1
    exp_send -i $cnn_id "set mld querier\r"
    exp_sleep 1
    expect -i $cnn_id -re "#"
    exp_send -i $cnn_id "exit\r"
    exp_sleep 1
    
    Netgear_log_file "lib-mldsnooping.tcl" "enable mldsnooping querier on switcher"
    Netgear_disconnect_switch $switch_name
}

#*******************************************************************************
#
#  Function Name  : _ntgrMldSnoopingQuerierDisable
#
#  Description    : This function is called to disable mld snooping querier on specified
#                   switch.
#
#  Usage          : _ntgrMldSnoopingQuerierDisable <switch_name>
#
#*******************************************************************************
proc _ntgrMldSnoopingQuerierDisable {switch_name} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    exp_send -i $cnn_id "configure\r"
    exp_sleep 1
    exp_send -i $cnn_id "no set mld querier\r"
    exp_sleep 1
    expect -i $cnn_id -re "#"
    exp_send -i $cnn_id "exit\r"
    exp_sleep 1
    
    Netgear_log_file "lib-mldsnooping.tcl" "disable mldsnooping querier on switcher"
    Netgear_disconnect_switch $switch_name
}

#*******************************************************************************
#
#  Function Name  : _ntgrMldSnoopingQuerierSetConfig
#
#  Description    : This function is called to set mld snooping querier configuration on specified
#                   switch.
#
#  Usage          : _ntgrMldSnoopingQuerierSetConfig <switch_name>
#
#*******************************************************************************
proc _ntgrMldSnoopingQuerierSetConfig {switch_name} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    set ipstr    [_ntgrGetMldSnpQuerierAddress $switch_name]
    set interval [_ntgrGetMldSnpQuerierInterval $switch_name]
    set expiry   [_ntgrGetMldSnpQuerierExpiry $switch_name]
    
    exp_send -i $cnn_id "configure\r"
    exp_sleep 1
    if {$ipstr != "default"} {
        exp_send -i $cnn_id "set mld querier address $ipstr\r"
        exp_sleep 1
        expect -i $cnn_id -re "#"
    }
    if {$interval != "default"} {
        exp_send -i $cnn_id "set mld querier query-interval $interval\r"
        exp_sleep 1
        expect -i $cnn_id -re "#"
    }
    if {$expiry != "default"} {
        exp_send -i $cnn_id "set mld querier timer expiry $expiry\r"
        exp_sleep 1
        expect -i $cnn_id -re "#"
    }
    exp_send -i $cnn_id "exit\r"
    exp_sleep 1
    
    Netgear_log_file "lib-mldsnooping.tcl" "set config of mldsnooping querier on switcher"
    Netgear_disconnect_switch $switch_name
}

#*******************************************************************************
#
#  Function Name  : _ntgrMldSnoopingQuerierUnsetConfig
#
#  Description    : This function is called to unset mld snooping querier configuration on specified
#                   switch.
#
#  Usage          : _ntgrMldSnoopingQuerierUnsetConfig <switch_name>
#
#*******************************************************************************
proc _ntgrMldSnoopingQuerierUnsetConfig {switch_name} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    set ipstr    [_ntgrGetMldSnpQuerierAddress $switch_name]
    set interval [_ntgrGetMldSnpQuerierInterval $switch_name]
    set expiry   [_ntgrGetMldSnpQuerierExpiry $switch_name]
    
    exp_send -i $cnn_id "configure\r"
    exp_sleep 1
    if {$ipstr != "default"} {
        exp_send -i $cnn_id "no set mld querier address\r"
        exp_sleep 1
        expect -i $cnn_id -re "#"
    }
    if {$interval != "default"} {
        exp_send -i $cnn_id "no set mld querier query-interval\r"
        exp_sleep 1
        expect -i $cnn_id -re "#"
    }
    if {$expiry != "default"} {
        exp_send -i $cnn_id "no set mld querier timer expiry\r"
        exp_sleep 1
        expect -i $cnn_id -re "#"
    }
    exp_send -i $cnn_id "exit\r"
    exp_sleep 1
    
    Netgear_log_file "lib-mldsnooping.tcl" "reset config of mldsnooping querier on switcher"
    Netgear_disconnect_switch $switch_name
}



#*******************************************************************************
#
#  Function Name  : _ntgrMldSnoopingQuerierVlanEnable
#
#  Description    : This function is called to enable mld snooping querier no vlan on specified
#                   switch.
#
#  Usage          : _ntgrMldSnoopingQuerierVlanEnable <switch_name> <vlanid>
#
#*******************************************************************************
proc _ntgrMldSnoopingQuerierVlanEnable {switch_name vlanid} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    exp_send -i $cnn_id "vlan database\r"
    exp_sleep 1
    exp_send -i $cnn_id "set mld querier $vlanid\r"
    exp_sleep 1
    expect -i $cnn_id -re "#"
    exp_send -i $cnn_id "exit\r"
    exp_sleep 1
    
    Netgear_log_file "lib-mldsnooping.tcl" "enable mldsnooping querier on vlan: $vlanid"
    Netgear_disconnect_switch $switch_name
}

#*******************************************************************************
#
#  Function Name  : _ntgrMldSnoopingQuerierVlanDisable
#
#  Description    : This function is called to disable mld snooping querier no vlan on specified
#                   switch.
#
#  Usage          : _ntgrMldSnoopingQuerierVlanDisable <switch_name> <vlanid>
#
#*******************************************************************************
proc _ntgrMldSnoopingQuerierVlanDisable {switch_name vlanid} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    exp_send -i $cnn_id "vlan database\r"
    exp_sleep 1
    exp_send -i $cnn_id "no set mld querier $vlanid\r"
    exp_sleep 1
    expect -i $cnn_id -re "#"
    exp_send -i $cnn_id "exit\r"
    exp_sleep 1
    
    Netgear_log_file "lib-mldsnooping.tcl" "disable mldsnooping querier on vlan: $vlanid"
    Netgear_disconnect_switch $switch_name
}