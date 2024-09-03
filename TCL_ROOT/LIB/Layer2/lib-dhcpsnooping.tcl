####################################################################################
#  File Name   : lib-dhcpsnooping.tcl
#
#  Description :
#        This file includes functions used for dhcp snooping configuration.
#
#  History     :
#        Date          Programmer         Description
#        01/17/2014    Jim Xie             Created
#
####################################################################################

#*******************************************************************************
#
#  Function Name  : _ntgrDHCPSnoopingEnable
#
#  Description    : This function is called to enable dhcp snooping on specified
#                   switch.
#
#  Usage          : _ntgrDHCPSnoopingEnable <switch_name>
#
#*******************************************************************************
proc _ntgrDHCPSnoopingEnable {switch_name} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    after 200
    exp_send -i $cnn_id "configure\r"
    exp_sleep 1
    exp_send -i $cnn_id "ip dhcp snooping\r"
    exp_sleep 1
    expect -i $cnn_id -re "#"
    exp_send -i $cnn_id "exit\r"
    exp_sleep 1
    
    Netgear_log_file "lib-dhcpsnooping.tcl" "enable dhcpsnooping on switcher"
    Netgear_disconnect_switch $switch_name
}

#*******************************************************************************
#
#  Function Name  : _ntgrDHCPSnoopingDisable
#
#  Description    : This function is called to disable dhcp snooping on specified
#                   switch.
#
#  Usage          : _ntgrDHCPSnoopingDisable <switch_name>
#
#*******************************************************************************
proc _ntgrDHCPSnoopingDisable {switch_name} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    after 200
    exp_send -i $cnn_id "configure\r"
    exp_sleep 1
    exp_send -i $cnn_id "no ip dhcp snooping\r"
    exp_sleep 1
    expect -i $cnn_id -re "#"
    exp_send -i $cnn_id "exit\r"
    exp_sleep 1
    
    Netgear_log_file "lib-dhcpsnooping.tcl" "disable dhcpsnooping on switcher"
    Netgear_disconnect_switch $switch_name
}

#*******************************************************************************
#
#  Function Name  : _ntgrDHCPSnoopingMacValidationEnable
#
#  Description    : This function is called to enable dhcp snooping mac address validation on specified
#                   switch.
#
#  Usage          : _ntgrDHCPSnoopingMacValidationEnable <switch_name>
#
#*******************************************************************************
proc _ntgrDHCPSnoopingMacValidationEnable {switch_name} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    after 200
    exp_send -i $cnn_id "configure\r"
    exp_sleep 1
    exp_send -i $cnn_id "ip dhcp snooping verify mac-address\r"
    exp_sleep 1
    expect -i $cnn_id -re "#"
    exp_send -i $cnn_id "exit\r"
    exp_sleep 1
    
    Netgear_log_file "lib-dhcpsnooping.tcl" "enable dhcpsnooping mac address validation on switcher"
    Netgear_disconnect_switch $switch_name
}

#*******************************************************************************
#
#  Function Name  : _ntgrDHCPSnoopingMacValidationDisable
#
#  Description    : This function is called to disable dhcp snooping mac address validation on specified
#                   switch.
#
#  Usage          : _ntgrDHCPSnoopingMacValidationDisable <switch_name>
#
#*******************************************************************************
proc _ntgrDHCPSnoopingMacValidationDisable {switch_name} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    after 200
    exp_send -i $cnn_id "configure\r"
    exp_sleep 1
    exp_send -i $cnn_id "no ip dhcp snooping verify mac-address\r"
    exp_sleep 1
    expect -i $cnn_id -re "#"
    exp_send -i $cnn_id "exit\r"
    exp_sleep 1
    
    Netgear_log_file "lib-dhcpsnooping.tcl" "disable dhcpsnooping mac address validation on switcher"
    Netgear_disconnect_switch $switch_name
}

#*******************************************************************************
#
#  Function Name  : _ntgrDHCPSnoopingVlanEnable
#
#  Description    : This function is called to enable dhcp snooping vlan on specified
#                   switch.
#
#  Usage          : _ntgrDHCPSnoopingVlanEnable <switch_name> <vlan_id>
#
#*******************************************************************************
proc _ntgrDHCPSnoopingVlanEnable {switch_name vlan_id} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    after 200
    exp_send -i $cnn_id "configure\r"
    exp_sleep 1
    exp_send -i $cnn_id "ip dhcp snooping vlan $vlan_id\r"
    exp_sleep 1
    expect -i $cnn_id -re "#"
    exp_send -i $cnn_id "exit\r"
    exp_sleep 1
    
    Netgear_log_file "lib-dhcpsnooping.tcl" "enable dhcpsnooping vlan $vlan_id on switcher"
    Netgear_disconnect_switch $switch_name
}

#*******************************************************************************
#
#  Function Name  : _ntgrDHCPSnoopingVlanDisable
#
#  Description    : This function is called to disable dhcp snooping vlan on specified
#                   switch.
#
#  Usage          : _ntgrDHCPSnoopingVlanDisable <switch_name> <vlan_id>
#
#*******************************************************************************
proc _ntgrDHCPSnoopingVlanDisable {switch_name vlan_id} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    after 200
    exp_send -i $cnn_id "configure\r"
    exp_sleep 1
    exp_send -i $cnn_id "no ip dhcp snooping vlan $vlan_id\r"
    exp_sleep 1
    expect -i $cnn_id -re "#"
    exp_send -i $cnn_id "exit\r"
    exp_sleep 1
    
    Netgear_log_file "lib-dhcpsnooping.tcl" "disable dhcpsnooping vlan $vlan_id on switcher"
    Netgear_disconnect_switch $switch_name
}

#*******************************************************************************
#
#  Function Name  : _ntgrDHCPSnoopingIntfTrustEnable
#
#  Description    : This function is called to enable dhcp snooping interface on specified
#                   switch.
#
#  Usage          : _ntgrDHCPSnoopingIntfTrustEnable <switch_name> <if_list>
#
#*******************************************************************************
proc _ntgrDHCPSnoopingIntfTrustEnable {switch_name if_list} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
	after 200
    Netgear_log_file "lib-dhcpsnooping.tcl" "Enable dhcp snooping trust on interface $if_list ."
  
    foreach if_name $if_list {
        if {[string first "POCH" $if_name] != -1} {
            set if_name [_ntgrGetChannelLogicalIfName $cnn_id $if_name]
        }
        exp_send -i $cnn_id "configure\r"
        after 200
        exp_send -i $cnn_id "interface $if_name\r"
        after 200
        exp_send -i $cnn_id "ip dhcp snooping trust\r"
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
#  Function Name  : _ntgrDHCPSnoopingIntfTrustDisable
#
#  Description    : This function is called to disable dhcp snooping interface on specified
#                   switch.
#
#  Usage          : _ntgrDHCPSnoopingIntfTrustDisable <switch_name> <if_list>
#
#*******************************************************************************
proc _ntgrDHCPSnoopingIntfTrustDisable {switch_name if_list} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
	after 200
    Netgear_log_file "lib-dhcpsnooping.tcl" "Disable dhcp snooping trust on interface $if_list ."
  
    foreach if_name $if_list {
        if {[string first "POCH" $if_name] != -1} {
            set if_name [_ntgrGetChannelLogicalIfName $cnn_id $if_name]
        }
        exp_send -i $cnn_id "configure\r"
        after 200
        exp_send -i $cnn_id "interface $if_name\r"
        after 200
        exp_send -i $cnn_id "no ip dhcp snooping trust\r"
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
#  Function Name  : _ntgrDHCPSnoopingIntfLoggingInvalidEnable
#
#  Description    : This function is called to enable dhcp snooping logging invalid interface on specified
#                   switch.
#
#  Usage          : _ntgrDHCPSnoopingIntfLoggingInvalidEnable <switch_name> <if_list>
#
#*******************************************************************************
proc _ntgrDHCPSnoopingIntfLoggingInvalidEnable {switch_name if_list} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
	after 200
    Netgear_log_file "lib-dhcpsnooping.tcl" "Enable dhcp snooping logging invalid on interface $if_list ."
  
    foreach if_name $if_list {
        if {[string first "POCH" $if_name] != -1} {
            set if_name [_ntgrGetChannelLogicalIfName $cnn_id $if_name]
        }
        exp_send -i $cnn_id "configure\r"
        after 200
        exp_send -i $cnn_id "interface $if_name\r"
        after 200
        exp_send -i $cnn_id "ip dhcp snooping log-invalid\r"
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
#  Function Name  : _ntgrDHCPSnoopingIntfLoggingInvalidDisable
#
#  Description    : This function is called to disable dhcp snooping logging invalid interface on specified
#                   switch.
#
#  Usage          : _ntgrDHCPSnoopingIntfLoggingInvalidDisable <switch_name> <if_list>
#
#*******************************************************************************
proc _ntgrDHCPSnoopingIntfLoggingInvalidDisable {switch_name if_list} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
	after 200
    Netgear_log_file "lib-dhcpsnooping.tcl" "Disable dhcp snooping logging invalid on interface $if_list ."
  
    foreach if_name $if_list {
        if {[string first "POCH" $if_name] != -1} {
            set if_name [_ntgrGetChannelLogicalIfName $cnn_id $if_name]
        }
        exp_send -i $cnn_id "configure\r"
        after 200
        exp_send -i $cnn_id "interface $if_name\r"
        after 200
        exp_send -i $cnn_id "no ip dhcp snooping log-invalid\r"
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
#  Function Name  : _ntgrDHCPSnoopingIntfSetRateLimitBurstInterval
#
#  Description    : This function is called to set dhcp snooping rate limit and burst interval on interface on specified
#                   switch.
#
#  Usage          : _ntgrDHCPSnoopingIntfSetRateLimitBurstInterval <switch_name> <if_list> <rate> <sec>
#
#*******************************************************************************
proc _ntgrDHCPSnoopingIntfSetRateLimitBurstInterval {switch_name if_list rate sec} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
	after 200
    Netgear_log_file "lib-dhcpsnooping.tcl" "set dhcp snooping rate limit $rate pps burst interval $sec seconds on interface $if_list ."
  
    foreach if_name $if_list {
        if {[string first "POCH" $if_name] != -1} {
            set if_name [_ntgrGetChannelLogicalIfName $cnn_id $if_name]
        }
        exp_send -i $cnn_id "configure\r"
        after 200
        exp_send -i $cnn_id "interface $if_name\r"
        after 200
		if { $sec > 0 } {
            exp_send -i $cnn_id "ip dhcp snooping limit rate $rate burst interval $sec\r"
		} else {
		    exp_send -i $cnn_id "ip dhcp snooping limit rate $rate\r"
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
#  Function Name  : _ntgrDHCPSnoopingIntfUnSetRateLimitBurstInterval
#
#  Description    : This function is called to reset dhcp snooping rate limit and burst interval on interface on specified
#                   switch.
#
#  Usage          : _ntgrDHCPSnoopingIntfUnSetRateLimitBurstInterval <switch_name> <if_list>
#
#*******************************************************************************
proc _ntgrDHCPSnoopingIntfUnSetRateLimitBurstInterval {switch_name if_list} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
	after 200
    Netgear_log_file "lib-dhcpsnooping.tcl" "reset dhcp snooping rate limit on interface $if_list ."
  
    foreach if_name $if_list {
        if {[string first "POCH" $if_name] != -1} {
            set if_name [_ntgrGetChannelLogicalIfName $cnn_id $if_name]
        }
        exp_send -i $cnn_id "configure\r"
        after 200
        exp_send -i $cnn_id "interface $if_name\r"
        after 200
        exp_send -i $cnn_id "no ip dhcp snooping limit\r"
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
#  Function Name  : _ntgrDHCPSnoopingAddBindingConfiguration
#
#  Description    : This function is called to add dhcp snooping blinding configuration on specified
#                   switch.
#
#  Usage          : _ntgrDHCPSnoopingAddBindingConfiguration <switch_name> <port> <mac_addr> <vlan_id> <ip_addr>
#
#*******************************************************************************
proc _ntgrDHCPSnoopingAddBindingConfiguration {switch_name port mac_addr vlan_id ip_addr} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    after 200
    exp_send -i $cnn_id "configure\r"
    exp_sleep 1
    exp_send -i $cnn_id "ip dhcp snooping binding $mac_addr vlan $vlan_id $ip_addr interface $port\r"
    exp_sleep 1
    expect -i $cnn_id -re "#"
    exp_send -i $cnn_id "exit\r"
    exp_sleep 1
    
    Netgear_log_file "lib-dhcpsnooping.tcl" "add dhcpsnooping blinding $mac_addr on vlan $vlan_id ip address $ip_addr on port $port on switcher"
    Netgear_disconnect_switch $switch_name
}

#*******************************************************************************
#
#  Function Name  : _ntgrDHCPSnoopingDeleteBindingConfiguration
#
#  Description    : This function is called to delete dhcp snooping blinding configuration on specified
#                   switch.
#
#  Usage          : _ntgrDHCPSnoopingDeleteBindingConfiguration <switch_name> <mac_addr>
#
#*******************************************************************************
proc _ntgrDHCPSnoopingDeleteBindingConfiguration {switch_name mac_addr} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    after 200
    exp_send -i $cnn_id "configure\r"
    exp_sleep 1
    exp_send -i $cnn_id "no ip dhcp snooping binding $mac_addr\r"
    exp_sleep 1
    expect -i $cnn_id -re "#"
    exp_send -i $cnn_id "exit\r"
    exp_sleep 1
    
    Netgear_log_file "lib-dhcpsnooping.tcl" "delete dhcpsnooping blinding $mac_addr on switcher"
    Netgear_disconnect_switch $switch_name
}

#*******************************************************************************
#
#  Function Name  : _ntgrDHCPSnoopingClearBindingConfiguration
#
#  Description    : This function is called to clear dhcp snooping blinding configuration on specified
#                   switch.
#
#  Usage          : _ntgrDHCPSnoopingClearBindingConfiguration <switch_name>
#
#*******************************************************************************
proc _ntgrDHCPSnoopingClearBindingConfiguration {switch_name} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    after 200
    exp_send -i $cnn_id "clear ip dhcp snooping binding\r"
    exp_sleep 1
    expect -i $cnn_id -re "#"
    exp_send -i $cnn_id "exit\r"
    exp_sleep 1
    
    Netgear_log_file "lib-dhcpsnooping.tcl" "clear dhcpsnooping blinding on switcher"
    Netgear_disconnect_switch $switch_name
}

#*******************************************************************************
#
#  Function Name  : _ntgrDHCPSnoopingClearStatistics
#
#  Description    : This function is called to clear dhcp snooping statistics configuration on specified
#                   switch.
#
#  Usage          : _ntgrDHCPSnoopingClearStatistics <switch_name>
#
#*******************************************************************************
proc _ntgrDHCPSnoopingClearStatistics {switch_name} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    after 200
    exp_send -i $cnn_id "clear ip dhcp snooping statistics\r"
    exp_sleep 1
    expect -i $cnn_id -re "#"
    exp_send -i $cnn_id "exit\r"
    exp_sleep 1
    
    Netgear_log_file "lib-dhcpsnooping.tcl" "clear dhcpsnooping statistics on switcher"
    Netgear_disconnect_switch $switch_name
}

#*******************************************************************************
#
#  Function Name  : _ntgrCheckDHCPSnoopingEntry
#
#  Description    : This function is used to check dhcpsnooping entry exist or not.
#
#  Usage          : _ntgrCheckDHCPSnoopingEntry <switch_name> <mac_list> <notin>
#
#*******************************************************************************
proc _ntgrCheckDHCPSnoopingEntry {switch_name mac_list {notin 0}} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    after 200
    set output ""
    
    exp_send -i $cnn_id "show ip dhcp snooping binding\r"
    exp_sleep 1
    expect -i $cnn_id -re "show ip dhcp snooping binding"
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

    Netgear_log_file "lib-dhcpsnooping.tcl" "getting dhcpsnooping entry on switcher"
    Netgear_disconnect_switch $switch_name

    return $result
}

#*******************************************************************************
#
#  Function Name  : _ntgrCheckDHCPSnoopingStatistics
#
#  Description    : This function is used to check dhcpsnooping Statistics exist or not.
#
#  Usage          : _ntgrCheckDHCPSnoopingStatistics <switch_name> <port> <expect_text>
#
#*******************************************************************************
proc _ntgrCheckDHCPSnoopingStatistics {switch_name port expect_text} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    after 200
    set output ""
    set str1 {}
    set str2 {}
	set str3 {}
	set match {}
	
    exp_send -i $cnn_id "show ip dhcp snooping statistics\r"
    exp_sleep 1
    expect -i $cnn_id -re "show ip dhcp snooping statistics"
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
	    Netgear_log_file "lib-dhcpsnooping.tcl" "Error: Invalid DHCP Snooping Statistics Type."
	}
	
	return $result
	
}

#*******************************************************************************
#
#  Function Name  : _ntgrCheckDHCPSnoopingEntryByMAC
#
#  Description    : This function is used to check dhcpsnooping entry exist by port or not.
#
#  Usage          : _ntgrCheckDHCPSnoopingEntryByMAC <switch_name> <mac_list> <port> <notin>
#
#*******************************************************************************
proc _ntgrCheckDHCPSnoopingEntryByMAC {switch_name mac_list port {notin 0}} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    after 200
    set output ""
    
    exp_send -i $cnn_id "show ip dhcp snooping binding\r"
    exp_sleep 1
    expect -i $cnn_id -re "show ip dhcp snooping binding"
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
            if {[regexp -nocase $mac $output] && [regexp -nocase $port $output]} {
                set result 0
                break
            }
        } else {
            if {![regexp -nocase $mac $output] && ![regexp -nocase $port $output]} {
                set result 0
                break
            }
        }
    }

    Netgear_log_file "lib-dhcpsnooping.tcl" "getting dhcpsnooping entry on switcher"
    Netgear_disconnect_switch $switch_name

    return $result
}

#*******************************************************************************
#
#  Function Name  : _ntgrCheckDHCPSnoopingBindingLeaseTime
#
#  Description    : This function is used to get dhcpsnooping binding lease time .
#
#  Usage          : _ntgrCheckDHCPSnoopingBindingLeaseTime <switch_name> <mac> <port> <vlan_id> <expect_text>
#
#*******************************************************************************
proc _ntgrCheckDHCPSnoopingBindingLeaseTime {switch_name mac port vlan_id expect_text} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    after 200
    set output ""
    set str1 {}
    set str2 {}
	set match {}
	
    exp_send -i $cnn_id "show ip dhcp snooping binding interface $port vlan $vlan_id\r"
    exp_sleep 1
    expect -i $cnn_id -re "show ip dhcp snooping binding interface $port vlan $vlan_id"
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
	set regSWMAC "$mac\[ ]+(\[0-9\]\{1,3\}.\[0-9\]\{1,3\}.\[0-9\]\{1,3\}.\[0-9\]\{1,3\})\[ ]+$vlan_id\[ ]+$port\[ ]+DYNAMIC\[ ]+(\[0-9\]+)"
    regexp $regSWMAC $output match str1 str2
	
	if { $expect_text == "ip" } {
	    if {$str1 == ""} {
	        set result 0
	    } else {
	        set result $str1
	    }
	} else {
	    if {$str2 == ""} {
	        set result 0
	    } else {
	        set result $str2
	    }
	}
	
	return $result
	
}

#*******************************************************************************
#
#  Function Name  : _ntgrDHCPSnoopingSetPersistentConfiguration
#
#  Description    : This function is called to set Persistent configuration on specified
#                   switch.
#
#  Usage          : _ntgrDHCPSnoopingSetPersistentConfiguration <switch_name> <storetype> <remote_ip> <remote_file> <delay_time>
#
#*******************************************************************************
proc _ntgrDHCPSnoopingSetPersistentConfiguration {switch_name storetype remote_ip remote_file delay_time} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    after 200
    exp_send -i $cnn_id "configure\r"
    exp_sleep 1
	if { $storetype == "local" } {
       exp_send -i $cnn_id "ip dhcp snooping database local\r"
	   exp_sleep 1
	} elseif { $storetype == "remote" } {
	   exp_send -i $cnn_id "ip dhcp snooping database tftp://$remote_ip/$remote_file\r"
	   exp_sleep 1
	}

	if { $delay_time == "" } {
	   exp_sleep 1
	} else {
	   exp_send -i $cnn_id "ip dhcp snooping database write-delay $delay_time\r"
	   exp_sleep 1
	}
	
    expect -i $cnn_id -re "#"
    exp_send -i $cnn_id "exit\r"
    exp_sleep 1
    
    Netgear_log_file "lib-dhcpsnooping.tcl" "set Persistent configuration Store Type is $storetype Delay Time is $delay_time"
    Netgear_disconnect_switch $switch_name
}

#*******************************************************************************
#
#  Function Name  : _ntgrDHCPSnoopingReSetPersistentConfiguration
#
#  Description    : This function is called to reset Persistent configuration on specified
#                   switch.
#
#  Usage          : _ntgrDHCPSnoopingReSetPersistentConfiguration <switch_name>
#
#*******************************************************************************
proc _ntgrDHCPSnoopingReSetPersistentConfiguration {switch_name} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    after 200
    exp_send -i $cnn_id "configure\r"
    exp_sleep 1
    exp_send -i $cnn_id "ip dhcp snooping database local\r"
	exp_sleep 1
	exp_send -i $cnn_id "ip dhcp snooping database write-delay 300\r"
	exp_sleep 1
    expect -i $cnn_id -re "#"
    exp_send -i $cnn_id "exit\r"
    exp_sleep 1
    
    Netgear_log_file "lib-dhcpsnooping.tcl" "Reset Persistent configuration Store Type to local Delay Time to 300 ."
    Netgear_disconnect_switch $switch_name
}