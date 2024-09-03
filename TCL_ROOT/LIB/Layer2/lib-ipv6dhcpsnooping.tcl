####################################################################################
#  File Name   : lib-ipv6dhcpsnooping.tcl
#
#  Description :
#        This file includes functions used for ipv6 dhcp snooping configuration.
#
#  History     :
#        Date          Programmer         Description
#        11/20/2017    Jim Xie             Created
#
####################################################################################

#*******************************************************************************
#
#  Function Name  : CALIPv6DHCPSnoopingEnable
#
#  Description    : This function is called to enable ipv6 dhcp snooping on specified
#                   switch.
#
#  Usage          : CALIPv6DHCPSnoopingEnable <switch_name>
#
#*******************************************************************************
proc CALIPv6DHCPSnoopingEnable {switch_name} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    after 200
    exp_send -i $cnn_id "configure\r"
    exp_sleep 1
    exp_send -i $cnn_id "ipv6 dhcp snooping\r"
    exp_sleep 1
    expect -i $cnn_id -re "#"
    exp_send -i $cnn_id "exit\r"
    exp_sleep 1
    
    Netgear_log_file "lib-ipv6dhcpsnooping.tcl" "enable ipv6 dhcpsnooping on switcher"
    Netgear_disconnect_switch $switch_name
}

#*******************************************************************************
#
#  Function Name  : CALIPv6DHCPSnoopingDisable
#
#  Description    : This function is called to disable ipv6 dhcp snooping on specified
#                   switch.
#
#  Usage          : CALIPv6DHCPSnoopingDisable <switch_name>
#
#*******************************************************************************
proc CALIPv6DHCPSnoopingDisable {switch_name} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    after 200
    exp_send -i $cnn_id "configure\r"
    exp_sleep 1
    exp_send -i $cnn_id "no ipv6 dhcp snooping\r"
    exp_sleep 1
    expect -i $cnn_id -re "#"
    exp_send -i $cnn_id "exit\r"
    exp_sleep 1
    
    Netgear_log_file "lib-ipv6dhcpsnooping.tcl" "disable ipv6 dhcpsnooping on switcher"
    Netgear_disconnect_switch $switch_name
}

#*******************************************************************************
#
#  Function Name  : CALIPv6DHCPSnoopingVlanEnable
#
#  Description    : This function is called to enable ipv6 dhcp snooping vlan on specified
#                   switch.
#
#  Usage          : CALIPv6DHCPSnoopingVlanEnable <switch_name> <vlan_id>
#
#*******************************************************************************
proc CALIPv6DHCPSnoopingVlanEnable {switch_name vlan_id} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    after 200
    exp_send -i $cnn_id "configure\r"
    exp_sleep 1
    exp_send -i $cnn_id "ipv6 dhcp snooping vlan $vlan_id\r"
    exp_sleep 1
    expect -i $cnn_id -re "#"
    exp_send -i $cnn_id "exit\r"
    exp_sleep 1
    
    Netgear_log_file "lib-ipv6dhcpsnooping.tcl" "enable ipv6 dhcpsnooping vlan $vlan_id on switcher"
    Netgear_disconnect_switch $switch_name
}

#*******************************************************************************
#
#  Function Name  : CALIPv6DHCPSnoopingVlanDisable
#
#  Description    : This function is called to disable ipv6 dhcp snooping vlan on specified
#                   switch.
#
#  Usage          : CALIPv6DHCPSnoopingVlanDisable <switch_name> <vlan_id>
#
#*******************************************************************************
proc CALIPv6DHCPSnoopingVlanDisable {switch_name vlan_id} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    after 200
    exp_send -i $cnn_id "configure\r"
    exp_sleep 1
    exp_send -i $cnn_id "no ipv6 dhcp snooping vlan $vlan_id\r"
    exp_sleep 1
    expect -i $cnn_id -re "#"
    exp_send -i $cnn_id "exit\r"
    exp_sleep 1
    
    Netgear_log_file "lib-ipv6dhcpsnooping.tcl" "disable ipv6 dhcpsnooping vlan $vlan_id on switcher"
    Netgear_disconnect_switch $switch_name
}

#*******************************************************************************
#
#  Function Name  : CALIPv6DHCPSnoopingIntfTrustEnable
#
#  Description    : This function is called to enable ipv6 dhcp snooping interface on specified
#                   switch.
#
#  Usage          : CALIPv6DHCPSnoopingIntfTrustEnable <switch_name> <if_list>
#
#*******************************************************************************
proc CALIPv6DHCPSnoopingIntfTrustEnable {switch_name if_list} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    after 200
    Netgear_log_file "lib-ipv6dhcpsnooping.tcl" "Enable ipv6 dhcp snooping trust on interface $if_list ."
  
    foreach if_name $if_list {
        if {[string first "POCH" $if_name] != -1} {
            set if_name [_ntgrGetChannelLogicalIfName $cnn_id $if_name]
        }
        exp_send -i $cnn_id "configure\r"
        after 200
        exp_send -i $cnn_id "interface $if_name\r"
        after 200
        exp_send -i $cnn_id "ipv6 dhcp snooping trust\r"
        after 200
        exp_send -i $cnn_id "exit\r"
        after 200
        exp_send -i $cnn_id "exit\r"
        after 200
    }
    Netgear_disconnect_switch $switch_name
}

#*******************************************************************************
#
#  Function Name  : CALIPv6DHCPSnoopingIntfTrustDisable
#
#  Description    : This function is called to disable ipv6 dhcp snooping interface on specified
#                   switch.
#
#  Usage          : CALIPv6DHCPSnoopingIntfTrustDisable <switch_name> <if_list>
#
#*******************************************************************************
proc CALIPv6DHCPSnoopingIntfTrustDisable {switch_name if_list} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    after 200
    Netgear_log_file "lib-ipv6dhcpsnooping.tcl" "Disable ipv6 dhcp snooping trust on interface $if_list ."
  
    foreach if_name $if_list {
        if {[string first "POCH" $if_name] != -1} {
            set if_name [_ntgrGetChannelLogicalIfName $cnn_id $if_name]
        }
        exp_send -i $cnn_id "configure\r"
        after 200
        exp_send -i $cnn_id "interface $if_name\r"
        after 200
        exp_send -i $cnn_id "no ipv6 dhcp snooping trust\r"
        after 200
        exp_send -i $cnn_id "exit\r"
        after 200
        exp_send -i $cnn_id "exit\r"
        after 200
    }
    Netgear_disconnect_switch $switch_name
}

#*******************************************************************************
#
#  Function Name  : CALIPv6DHCPSnoopingClearBindingConfiguration
#
#  Description    : This function is called to clear ipv6 dhcp snooping blinding configuration on specified
#                   switch.
#
#  Usage          : CALIPv6DHCPSnoopingClearBindingConfiguration <switch_name>
#
#*******************************************************************************
proc CALIPv6DHCPSnoopingClearBindingConfiguration {switch_name} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    after 200
    exp_send -i $cnn_id "clear ipv6 dhcp snooping binding\r"
    exp_sleep 1
    expect -i $cnn_id -re "#"
    exp_send -i $cnn_id "exit\r"
    exp_sleep 1
    
    Netgear_log_file "lib-ipv6dhcpsnooping.tcl" "clear ipv6 dhcpsnooping blinding on switcher"
    Netgear_disconnect_switch $switch_name
}

#*******************************************************************************
#
#  Function Name  : CALIPv6CheckDHCPSnoopingEntry
#
#  Description    : This function is used to check ipv6 dhcpsnooping entry exist or not.
#
#  Usage          : CALIPv6CheckDHCPSnoopingEntry <switch_name> <prefix_list> <notin>
#
#*******************************************************************************
proc CALIPv6CheckDHCPSnoopingEntry {switch_name prefix_list {notin 0}} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    after 200
    set output ""
    
    exp_send -i $cnn_id "show ipv6 dhcp snooping binding\r"
    exp_sleep 1
    expect -i $cnn_id -re "show ipv6 dhcp snooping binding"
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
    foreach prefix $prefix_list {
        if {$notin == 1} {
            if {[regexp -nocase $prefix $output]} {
                set result 0
                break
            }
        } else {
            if {![regexp -nocase $prefix $output]} {
                set result 0
                break
            }
        }
    }

    Netgear_log_file "lib-ipv6dhcpsnooping.tcl" "getting ipv6 dhcpsnooping entry on switcher"
    Netgear_disconnect_switch $switch_name

    return $result
}

#*******************************************************************************
#
#  Function Name  : CALIPv6DHCPSnoopingIntfSetRateLimitBurstInterval
#
#  Description    : This function is called to set ipv6 dhcp snooping rate limit and burst interval on interface on specified
#                   switch.
#
#  Usage          : CALIPv6DHCPSnoopingIntfSetRateLimitBurstInterval <switch_name> <if_list> <rate> <sec>
#
#*******************************************************************************
proc CALIPv6DHCPSnoopingIntfSetRateLimitBurstInterval {switch_name if_list rate sec} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    after 200
    Netgear_log_file "lib-ipv6dhcpsnooping.tcl" "set ipv6 dhcp snooping rate limit $rate pps burst interval $sec seconds on interface $if_list ."
  
    foreach if_name $if_list {
        if {[string first "POCH" $if_name] != -1} {
            set if_name [_ntgrGetChannelLogicalIfName $cnn_id $if_name]
        }
        exp_send -i $cnn_id "configure\r"
        after 200
        exp_send -i $cnn_id "interface $if_name\r"
        after 200
        if { $sec > 0 } {
            exp_send -i $cnn_id "ipv6 dhcp snooping limit rate $rate burst interval $sec\r"
        } else {
            exp_send -i $cnn_id "ipv6 dhcp snooping limit rate $rate\r"
        }
        after 200
        exp_send -i $cnn_id "exit\r"
        after 200
        exp_send -i $cnn_id "exit\r"
        after 200
    }
    Netgear_disconnect_switch $switch_name
}

#*******************************************************************************
#
#  Function Name  : CALIPv6DHCPSnoopingIntfUnSetRateLimitBurstInterval
#
#  Description    : This function is called to reset ipv6 dhcp snooping rate limit and burst interval on interface on specified
#                   switch.
#
#  Usage          : CALIPv6DHCPSnoopingIntfUnSetRateLimitBurstInterval <switch_name> <if_list>
#
#*******************************************************************************
proc CALIPv6DHCPSnoopingIntfUnSetRateLimitBurstInterval {switch_name if_list} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    after 200
    Netgear_log_file "lib-ipv6dhcpsnooping.tcl" "reset ipv6 dhcp snooping rate limit on interface $if_list ."
  
    foreach if_name $if_list {
        if {[string first "POCH" $if_name] != -1} {
            set if_name [_ntgrGetChannelLogicalIfName $cnn_id $if_name]
        }
        exp_send -i $cnn_id "configure\r"
        after 200
        exp_send -i $cnn_id "interface $if_name\r"
        after 200
        exp_send -i $cnn_id "no ipv6 dhcp snooping limit\r"
        after 200
        exp_send -i $cnn_id "exit\r"
        after 200
        exp_send -i $cnn_id "exit\r"
        after 200
    }
    Netgear_disconnect_switch $switch_name
}

#*******************************************************************************
#
#  Function Name  : CALIPv6DHCPSnoopingSetPersistentConfiguration
#
#  Description    : This function is called to set Persistent configuration on specified
#                   switch.
#
#  Usage          : CALIPv6DHCPSnoopingSetPersistentConfiguration <switch_name> <storetype> <remote_ip> <remote_file> <delay_time>
#
#*******************************************************************************
proc CALIPv6DHCPSnoopingSetPersistentConfiguration {switch_name storetype remote_ip remote_file delay_time} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    after 200
    exp_send -i $cnn_id "configure\r"
    exp_sleep 1
    if { $storetype == "local" } {
       exp_send -i $cnn_id "ipv6 dhcp snooping database local\r"
       exp_sleep 1
    } elseif { $storetype == "remote" } {
       exp_send -i $cnn_id "ipv6 dhcp snooping database tftp://$remote_ip/$remote_file\r"
       exp_sleep 1
    }

    if { $delay_time == "" } {
       exp_sleep 1
    } else {
       exp_send -i $cnn_id "ipv6 dhcp snooping database write-delay $delay_time\r"
       exp_sleep 1
    }

    expect -i $cnn_id -re "#"
    exp_send -i $cnn_id "exit\r"
    exp_sleep 1
    
    Netgear_log_file "lib-ipv6dhcpsnooping.tcl" "set Persistent configuration Store Type is $storetype Delay Time is $delay_time"
    Netgear_disconnect_switch $switch_name
}

#*******************************************************************************
#
#  Function Name  : CALIPv6DHCPSnoopingReSetPersistentConfiguration
#
#  Description    : This function is called to reset Persistent configuration on specified
#                   switch.
#
#  Usage          : CALIPv6DHCPSnoopingReSetPersistentConfiguration <switch_name>
#
#*******************************************************************************
proc CALIPv6DHCPSnoopingReSetPersistentConfiguration {switch_name} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    after 200
    exp_send -i $cnn_id "configure\r"
    exp_sleep 1
    exp_send -i $cnn_id "ipv6 dhcp snooping database local\r"
    exp_sleep 1
    exp_send -i $cnn_id "ipv6 dhcp snooping database write-delay 300\r"
    exp_sleep 1
    expect -i $cnn_id -re "#"
    exp_send -i $cnn_id "exit\r"
    exp_sleep 1
    
    Netgear_log_file "lib-ipv6dhcpsnooping.tcl" "Reset Persistent configuration Store Type to local Delay Time to 300 ."
    Netgear_disconnect_switch $switch_name
}

#*******************************************************************************
#
#  Function Name  : CALIPv6DHCPSnoopingClearStatistics
#
#  Description    : This function is called to clear ipv6 dhcp snooping statistics configuration on specified
#                   switch.
#
#  Usage          : CALIPv6DHCPSnoopingClearStatistics <switch_name>
#
#*******************************************************************************
proc CALIPv6DHCPSnoopingClearStatistics {switch_name} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    after 200
    exp_send -i $cnn_id "clear ipv6 dhcp snooping statistics\r"
    exp_sleep 1
    expect -i $cnn_id -re "#"
    exp_send -i $cnn_id "exit\r"
    exp_sleep 1
    
    Netgear_log_file "lib-ipv6dhcpsnooping.tcl" "clear ipv6 dhcpsnooping statistics on switcher"
    Netgear_disconnect_switch $switch_name
}


#*******************************************************************************
#
#  Function Name  : CALIPv6CheckDHCPSnoopingStatistics
#
#  Description    : This function is used to check ipv6 dhcpsnooping Statistics exist or not.
#
#  Usage          : CALIPv6CheckDHCPSnoopingStatistics <switch_name> <port> <expect_text>
#
#*******************************************************************************
proc CALIPv6CheckDHCPSnoopingStatistics {switch_name port expect_text} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    after 200
    set output ""
    set str1 {}
    set str2 {}
    set str3 {}
    set match {}

    exp_send -i $cnn_id "show ipv6 dhcp snooping statistics\r"
    exp_sleep 1
    expect -i $cnn_id -re "show ipv6 dhcp snooping statistics"
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
    
    set regSWMAC "$port\[ ]+(\[0-9\]+)\[ ]+(\[0-9\]+)\[ ]+(\[0-9\]+)"
    regexp $regSWMAC $output match str1 str2 str3

    set result 0
    if {$expect_text == "MAC Verify Failures"} {
        if { $str1 == "" } {
            set str1 0
        }
        set result $str1
    } elseif {$expect_text == "Client Ifc Mismatch"} {
        if { $str2 == "" } {
            set str2 0
        }
        set result $str2
    } elseif {$expect_text == "DHCP Server Msgs Received"} {
        if { $str3 == "" } {
            set str3 0
        }
        set result $str3
    } else {
        Netgear_log_file "lib-ipv6dhcpsnooping.tcl" "Error: Invalid ipv6 DHCP Snooping Statistics Type."
    }

    return $result
}

#*******************************************************************************
#
#  Function Name  : CALIPv6DHCPSnoopingMacValidationEnable
#
#  Description    : This function is called to enable ipv6 dhcp snooping mac address validation on specified
#                   switch.
#
#  Usage          : _ntgrDHCPSnoopingMacValidationEnable <switch_name>
#
#*******************************************************************************
proc CALIPv6DHCPSnoopingMacValidationEnable {switch_name} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    after 200
    exp_send -i $cnn_id "configure\r"
    exp_sleep 1
    exp_send -i $cnn_id "ipv6 dhcp snooping verify mac-address\r"
    exp_sleep 1
    expect -i $cnn_id -re "#"
    exp_send -i $cnn_id "exit\r"
    exp_sleep 1
    
    Netgear_log_file "lib-ipv6dhcpsnooping.tcl" "enable ipv6 dhcpsnooping mac address validation on switcher"
    Netgear_disconnect_switch $switch_name
}

#*******************************************************************************
#
#  Function Name  : CALIPv6DHCPSnoopingMacValidationDisable
#
#  Description    : This function is called to disable ipv6 dhcp snooping mac address validation on specified
#                   switch.
#
#  Usage          : CALIPv6DHCPSnoopingMacValidationDisable <switch_name>
#
#*******************************************************************************
proc CALIPv6DHCPSnoopingMacValidationDisable {switch_name} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    after 200
    exp_send -i $cnn_id "configure\r"
    exp_sleep 1
    exp_send -i $cnn_id "no ipv6 dhcp snooping verify mac-address\r"
    exp_sleep 1
    expect -i $cnn_id -re "#"
    exp_send -i $cnn_id "exit\r"
    exp_sleep 1
    
    Netgear_log_file "lib-ipv6dhcpsnooping.tcl" "disable ipv6 dhcpsnooping mac address validation on switcher"
    Netgear_disconnect_switch $switch_name
}

#*******************************************************************************
#
#  Function Name  : CALIPv6DHCPSnoopingIntfLoggingInvalidEnable
#
#  Description    : This function is called to enable ipv6 dhcp snooping logging invalid interface on specified
#                   switch.
#
#  Usage          : CALIPv6DHCPSnoopingIntfLoggingInvalidEnable <switch_name> <if_list>
#
#*******************************************************************************
proc CALIPv6DHCPSnoopingIntfLoggingInvalidEnable {switch_name if_list} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    after 200
    Netgear_log_file "lib-ipv6dhcpsnooping.tcl" "Enable ipv6 dhcp snooping logging invalid on interface $if_list ."
  
    foreach if_name $if_list {
        if {[string first "POCH" $if_name] != -1} {
            set if_name [_ntgrGetChannelLogicalIfName $cnn_id $if_name]
        }
        exp_send -i $cnn_id "configure\r"
        after 200
        exp_send -i $cnn_id "interface $if_name\r"
        after 200
        exp_send -i $cnn_id "ipv6 dhcp snooping log-invalid\r"
        after 200
        exp_send -i $cnn_id "exit\r"
        after 200
        exp_send -i $cnn_id "exit\r"
        after 200
    }
    Netgear_disconnect_switch $switch_name
}

#*******************************************************************************
#
#  Function Name  : CALIPv6DHCPSnoopingIntfLoggingInvalidDisable
#
#  Description    : This function is called to disable ipv6 dhcp snooping logging invalid interface on specified
#                   switch.
#
#  Usage          : CALIPv6DHCPSnoopingIntfLoggingInvalidDisable <switch_name> <if_list>
#
#*******************************************************************************
proc CALIPv6DHCPSnoopingIntfLoggingInvalidDisable {switch_name if_list} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    after 200
    Netgear_log_file "lib-ipv6dhcpsnooping.tcl" "Disable dhcp snooping logging invalid on interface $if_list ."
  
    foreach if_name $if_list {
        if {[string first "POCH" $if_name] != -1} {
            set if_name [_ntgrGetChannelLogicalIfName $cnn_id $if_name]
        }
        exp_send -i $cnn_id "configure\r"
        after 200
        exp_send -i $cnn_id "interface $if_name\r"
        after 200
        exp_send -i $cnn_id "no ipv6 dhcp snooping log-invalid\r"
        after 200
        exp_send -i $cnn_id "exit\r"
        after 200
        exp_send -i $cnn_id "exit\r"
        after 200
    }
    Netgear_disconnect_switch $switch_name
}


