####################################################################################
#  File Name   : cal-dhcpsnooping.tcl
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
#  Function Name  : CALDHCPSnoopingEnable
#
#  Description    : This function is called to enable dhcp snooping on specified
#                   switch.
#
#  Usage          : CALDHCPSnoopingEnable <switch_name>
#
#*******************************************************************************
proc CALDHCPSnoopingEnable {switch_name} {
    set switch_vendor [ _get_switch_vendor_name  $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrDHCPSnoopingEnable $switch_name
        }
        cisco {
            puts "TODO cisco\n"
        }
        hp {
            puts "TODO hp\n"
        }
        3com {
            puts "TODO hp\n"
        }
        default {
            Netgear_log_file "cal-dhcpsnooping.tcl" "Switch not defined"
        }
    }
}

#*******************************************************************************
#
#  Function Name  : CALDHCPSnoopingDisable
#
#  Description    : This function is called to disable dhcp snooping on specified
#                   switch.
#
#  Usage          : CALDHCPSnoopingDisable <switch_name>
#
#*******************************************************************************
proc CALDHCPSnoopingDisable {switch_name} {
    set switch_vendor [ _get_switch_vendor_name  $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrDHCPSnoopingDisable $switch_name
        }
        cisco {
            puts "TODO cisco\n"
        }
        hp {
            puts "TODO hp\n"
        }
        3com {
            puts "TODO hp\n"
        }
        default {
            Netgear_log_file "cal-dhcpsnooping.tcl" "Switch not defined"
        }
    }
}

#*******************************************************************************
#
#  Function Name  : CALDHCPSnoopingMacValidationEnable
#
#  Description    : This function is called to enable igmp snooping mac address validation on specified
#                   switch.
#
#  Usage          : CALDHCPSnoopingMacValidationEnable <switch_name>
#
#*******************************************************************************
proc CALDHCPSnoopingMacValidationEnable {switch_name} {
    set switch_vendor [ _get_switch_vendor_name  $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrDHCPSnoopingMacValidationEnable $switch_name
        }
        cisco {
            puts "TODO cisco\n"
        }
        hp {
            puts "TODO hp\n"
        }
        3com {
            puts "TODO hp\n"
        }
        default {
            Netgear_log_file "cal-dhcpsnooping.tcl" "Switch not defined"
        }
    }
}

#*******************************************************************************
#
#  Function Name  : CALDHCPSnoopingMacValidationDisable
#
#  Description    : This function is called to disable igmp snooping mac address validation on specified
#                   switch.
#
#  Usage          : CALDHCPSnoopingMacValidationDisable <switch_name>
#
#*******************************************************************************
proc CALDHCPSnoopingMacValidationDisable {switch_name} {
    set switch_vendor [ _get_switch_vendor_name  $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrDHCPSnoopingMacValidationDisable $switch_name
        }
        cisco {
            puts "TODO cisco\n"
        }
        hp {
            puts "TODO hp\n"
        }
        3com {
            puts "TODO hp\n"
        }
        default {
            Netgear_log_file "cal-dhcpsnooping.tcl" "Switch not defined"
        }
    }
}

#*******************************************************************************
#
#  Function Name  : CALDHCPSnoopingVlanEnable
#
#  Description    : This function is called to enable igmp snooping vlan on specified
#                   switch.
#
#  Usage          : CALDHCPSnoopingVlanEnable <switch_name> <vlan_id>
#
#*******************************************************************************
proc CALDHCPSnoopingVlanEnable {switch_name vlan_id} {
    set switch_vendor [ _get_switch_vendor_name  $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrDHCPSnoopingVlanEnable $switch_name $vlan_id
        }
        cisco {
            puts "TODO cisco\n"
        }
        hp {
            puts "TODO hp\n"
        }
        3com {
            puts "TODO hp\n"
        }
        default {
            Netgear_log_file "cal-dhcpsnooping.tcl" "Switch not defined"
        }
    }
}

#*******************************************************************************
#
#  Function Name  : CALDHCPSnoopingVlanDisable
#
#  Description    : This function is called to disable igmp snooping vlan on specified
#                   switch.
#
#  Usage          : CALDHCPSnoopingVlanDisable <switch_name> <vlan_id>
#
#*******************************************************************************
proc CALDHCPSnoopingVlanDisable {switch_name vlan_id} {
    set switch_vendor [ _get_switch_vendor_name  $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrDHCPSnoopingVlanDisable $switch_name $vlan_id
        }
        cisco {
            puts "TODO cisco\n"
        }
        hp {
            puts "TODO hp\n"
        }
        3com {
            puts "TODO hp\n"
        }
        default {
            Netgear_log_file "cal-dhcpsnooping.tcl" "Switch not defined"
        }
    }
}

#*******************************************************************************
#
#  Function Name  : CALDHCPSnoopingIntfTrustEnable
#
#  Description    : This function is called to enable igmp snooping interface trust on specified
#                   switch.
#
#  Usage          : CALDHCPSnoopingIntfTrustEnable <switch_name> <port>
#
#*******************************************************************************
proc CALDHCPSnoopingIntfTrustEnable {switch_name port} {
    set switch_vendor [ _get_switch_vendor_name  $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrDHCPSnoopingIntfTrustEnable $switch_name $port
        }
        cisco {
            puts "TODO cisco\n"
        }
        hp {
            puts "TODO hp\n"
        }
        3com {
            puts "TODO hp\n"
        }
        default {
            Netgear_log_file "cal-dhcpsnooping.tcl" "Switch not defined"
        }
    }
}

#*******************************************************************************
#
#  Function Name  : CALDHCPSnoopingIntfTrustDisable
#
#  Description    : This function is called to disable igmp snooping interface trust on specified
#                   switch.
#
#  Usage          : CALDHCPSnoopingIntfTrustDisable <switch_name> <port>
#
#*******************************************************************************
proc CALDHCPSnoopingIntfTrustDisable {switch_name port} {
    set switch_vendor [ _get_switch_vendor_name  $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrDHCPSnoopingIntfTrustDisable $switch_name $port
        }
        cisco {
            puts "TODO cisco\n"
        }
        hp {
            puts "TODO hp\n"
        }
        3com {
            puts "TODO hp\n"
        }
        default {
            Netgear_log_file "cal-dhcpsnooping.tcl" "Switch not defined"
        }
    }
}

#*******************************************************************************
#
#  Function Name  : CALDHCPSnoopingIntfLoggingInvalidEnable
#
#  Description    : This function is called to enable dhcp snooping logging invalid interface on specified
#                   switch.
#
#  Usage          : CALDHCPSnoopingIntfLoggingInvalidEnable <switch_name> <port>
#
#*******************************************************************************
proc CALDHCPSnoopingIntfLoggingInvalidEnable {switch_name port} {
    set switch_vendor [ _get_switch_vendor_name  $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrDHCPSnoopingIntfLoggingInvalidEnable $switch_name $port
        }
        cisco {
            puts "TODO cisco\n"
        }
        hp {
            puts "TODO hp\n"
        }
        3com {
            puts "TODO hp\n"
        }
        default {
            Netgear_log_file "cal-dhcpsnooping.tcl" "Switch not defined"
        }
    }
}

#*******************************************************************************
#
#  Function Name  : CALDHCPSnoopingIntfLoggingInvalidDisable
#
#  Description    : This function is called to disable dhcp snooping logging invalid interface on specified
#                   switch.
#
#  Usage          : CALDHCPSnoopingIntfLoggingInvalidDisable <switch_name> <port>
#
#*******************************************************************************
proc CALDHCPSnoopingIntfLoggingInvalidDisable {switch_name port} {
    set switch_vendor [ _get_switch_vendor_name  $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrDHCPSnoopingIntfLoggingInvalidDisable $switch_name $port
        }
        cisco {
            puts "TODO cisco\n"
        }
        hp {
            puts "TODO hp\n"
        }
        3com {
            puts "TODO hp\n"
        }
        default {
            Netgear_log_file "cal-dhcpsnooping.tcl" "Switch not defined"
        }
    }
}

#*******************************************************************************
#
#  Function Name  : CALDHCPSnoopingIntfSetRateLimitBurstInterval
#
#  Description    : This function is called to set dhcp snooping rate limit and burst interval on interface on specified
#                   switch.
#
#  Usage          : CALDHCPSnoopingIntfSetRateLimitBurstInterval <switch_name> <port> <rate> <sec>
#
#*******************************************************************************
proc CALDHCPSnoopingIntfSetRateLimitBurstInterval {switch_name port rate sec} {
    set switch_vendor [ _get_switch_vendor_name  $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrDHCPSnoopingIntfSetRateLimitBurstInterval $switch_name $port $rate $sec
        }
        cisco {
            puts "TODO cisco\n"
        }
        hp {
            puts "TODO hp\n"
        }
        3com {
            puts "TODO hp\n"
        }
        default {
            Netgear_log_file "cal-dhcpsnooping.tcl" "Switch not defined"
        }
    }
}

#*******************************************************************************
#
#  Function Name  : CALDHCPSnoopingIntfUnSetRateLimitBurstInterval
#
#  Description    : This function is called to reset dhcp snooping rate limit and burst interval on interface on specified
#                   switch.
#
#  Usage          : CALDHCPSnoopingIntfUnSetRateLimitBurstInterval <switch_name> <port>
#
#*******************************************************************************
proc CALDHCPSnoopingIntfUnSetRateLimitBurstInterval {switch_name port} {
    set switch_vendor [ _get_switch_vendor_name  $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrDHCPSnoopingIntfUnSetRateLimitBurstInterval $switch_name $port
        }
        cisco {
            puts "TODO cisco\n"
        }
        hp {
            puts "TODO hp\n"
        }
        3com {
            puts "TODO hp\n"
        }
        default {
            Netgear_log_file "cal-dhcpsnooping.tcl" "Switch not defined"
        }
    }
}

#*******************************************************************************
#
#  Function Name  : CALDHCPSnoopingAddBindingConfiguration
#
#  Description    : This function is called to add dhcp snooping blinding configuration on interface on specified
#                   switch.
#
#  Usage          : CALDHCPSnoopingAddBindingConfiguration <switch_name> <port> <mac_addr> <vlan_id> <ip_addr>
#
#*******************************************************************************
proc CALDHCPSnoopingAddBindingConfiguration {switch_name port mac_addr vlan_id ip_addr} {
    set switch_vendor [ _get_switch_vendor_name  $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrDHCPSnoopingAddBindingConfiguration $switch_name $port $mac_addr $vlan_id $ip_addr
        }
        cisco {
            puts "TODO cisco\n"
        }
        hp {
            puts "TODO hp\n"
        }
        3com {
            puts "TODO hp\n"
        }
        default {
            Netgear_log_file "cal-dhcpsnooping.tcl" "Switch not defined"
        }
    }
}

#*******************************************************************************
#
#  Function Name  : CALDHCPSnoopingDeteleBindingConfiguration
#
#  Description    : This function is called to delete dhcp snooping blinding configuration on interface on specified
#                   switch.
#
#  Usage          : CALDHCPSnoopingDeteleBindingConfiguration <switch_name> <mac_addr>
#
#*******************************************************************************
proc CALDHCPSnoopingDeteleBindingConfiguration {switch_name mac_addr} {
    set switch_vendor [ _get_switch_vendor_name  $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrDHCPSnoopingDeleteBindingConfiguration $switch_name $mac_addr
        }
        cisco {
            puts "TODO cisco\n"
        }
        hp {
            puts "TODO hp\n"
        }
        3com {
            puts "TODO hp\n"
        }
        default {
            Netgear_log_file "cal-dhcpsnooping.tcl" "Switch not defined"
        }
    }
}

#*******************************************************************************
#
#  Function Name  : CALDHCPSnoopingClearBindingConfiguration
#
#  Description    : This function is called to clear dhcp snooping blinding configuration on specified
#                   switch.
#
#  Usage          : CALDHCPSnoopingClearBindingConfiguration <switch_name>
#
#*******************************************************************************
proc CALDHCPSnoopingClearBindingConfiguration {switch_name} {
    set switch_vendor [ _get_switch_vendor_name  $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrDHCPSnoopingClearBindingConfiguration $switch_name
        }
        cisco {
            puts "TODO cisco\n"
        }
        hp {
            puts "TODO hp\n"
        }
        3com {
            puts "TODO hp\n"
        }
        default {
            Netgear_log_file "cal-dhcpsnooping.tcl" "Switch not defined"
        }
    }
}

#*******************************************************************************
#
#  Function Name  : CALDHCPSnoopingClearStatistics
#
#  Description    : This function is called to clear dhcp snooping statistics on specified
#                   switch.
#
#  Usage          : CALDHCPSnoopingClearStatistics <switch_name>
#
#*******************************************************************************
proc CALDHCPSnoopingClearStatistics {switch_name} {
    set switch_vendor [ _get_switch_vendor_name  $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrDHCPSnoopingClearStatistics $switch_name
        }
        cisco {
            puts "TODO cisco\n"
        }
        hp {
            puts "TODO hp\n"
        }
        3com {
            puts "TODO hp\n"
        }
        default {
            Netgear_log_file "cal-dhcpsnooping.tcl" "Switch not defined"
        }
    }
}

#*******************************************************************************
#
#  Function Name  : CALCheckDHCPSnoopingEntry
#
#  Description    : This function is used to check dhcpsnooping entry exist or not.
#
#  Usage          : CALCheckDHCPSnoopingEntry <switch_name> <mac_list> <notin>
#
#*******************************************************************************
proc CALCheckDHCPSnoopingEntry {switch_name mac_list {notin 0}} {
    set switch_vendor [ _get_switch_vendor_name  $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrCheckDHCPSnoopingEntry $switch_name $mac_list $notin
        }
        cisco {
            puts "TODO cisco\n"
        }
        hp {
            puts "TODO hp\n"
        }
        3com {
            puts "TODO hp\n"
        }
        default {
            Netgear_log_file "cal-igmpsnooping.tcl" "Switch not defined"
        }
    }
}

#*******************************************************************************
#
#  Function Name  : CALCheckDHCPSnoopingStatistics
#
#  Description    : This function is used to check dhcpsnooping Statistics exist or not.
#
#  Usage          : CALCheckDHCPSnoopingStatistics <switch_name> <port> <expect_text>
#
#*******************************************************************************
proc CALCheckDHCPSnoopingStatistics {switch_name port expect_text} {
    set switch_vendor [ _get_switch_vendor_name  $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrCheckDHCPSnoopingStatistics $switch_name $port $expect_text
        }
        cisco {
            puts "TODO cisco\n"
        }
        hp {
            puts "TODO hp\n"
        }
        3com {
            puts "TODO hp\n"
        }
        default {
            Netgear_log_file "cal-igmpsnooping.tcl" "Switch not defined"
        }
    }
}

#*******************************************************************************
#
#  Function Name  : CALCheckDHCPSnoopingEntryByMAC
#
#  Description    : This function is used to check dhcpsnooping entry exist or not.
#
#  Usage          : CALCheckDHCPSnoopingEntryByMAC <switch_name> <mac_list> <port> <notin>
#
#*******************************************************************************
proc CALCheckDHCPSnoopingEntryByMAC {switch_name mac_list port {notin 0}} {
    set switch_vendor [ _get_switch_vendor_name  $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrCheckDHCPSnoopingEntryByMAC $switch_name $mac_list $port $notin
        }
        cisco {
            puts "TODO cisco\n"
        }
        hp {
            puts "TODO hp\n"
        }
        3com {
            puts "TODO hp\n"
        }
        default {
            Netgear_log_file "cal-igmpsnooping.tcl" "Switch not defined"
        }
    }
}

#*******************************************************************************
#
#  Function Name  : CALCheckDHCPSnoopingBindingLeaseTime
#
#  Description    : This function is used to get dhcpsnooping binding lease time .
#
#  Usage          : CALCheckDHCPSnoopingBindingLeaseTime <switch_name> <mac> <port> <vlan_id> <expect_text>
#
#*******************************************************************************
proc CALCheckDHCPSnoopingBindingLeaseTime {switch_name mac port vlan_id expect_text} {
    set switch_vendor [ _get_switch_vendor_name  $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrCheckDHCPSnoopingBindingLeaseTime $switch_name $mac $port $vlan_id $expect_text
        }
        cisco {
            puts "TODO cisco\n"
        }
        hp {
            puts "TODO hp\n"
        }
        3com {
            puts "TODO hp\n"
        }
        default {
            Netgear_log_file "cal-igmpsnooping.tcl" "Switch not defined"
        }
    }
}

#*******************************************************************************
#
#  Function Name  : CALCheckDHCPSnoopingSetPersistentConfiguration
#
#  Description    : This function is called to set Persistent configuration on specified.
#
#  Usage          : CALCheckDHCPSnoopingSetPersistentConfiguration <switch_name> <storetype> <remote_ip> <remote_file> <delay_time>
#
#*******************************************************************************
proc CALCheckDHCPSnoopingSetPersistentConfiguration {switch_name storetype remote_ip remote_file delay_time} {
    set switch_vendor [ _get_switch_vendor_name  $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrDHCPSnoopingSetPersistentConfiguration $switch_name $storetype $remote_ip $remote_file $delay_time
        }
        cisco {
            puts "TODO cisco\n"
        }
        hp {
            puts "TODO hp\n"
        }
        3com {
            puts "TODO hp\n"
        }
        default {
            Netgear_log_file "cal-igmpsnooping.tcl" "Switch not defined"
        }
    }
}

#*******************************************************************************
#
#  Function Name  : CALCheckDHCPSnoopingReSetPersistentConfiguration
#
#  Description    : This function is called to set Persistent configuration on specified.
#
#  Usage          : CALCheckDHCPSnoopingReSetPersistentConfiguration <switch_name>
#
#*******************************************************************************
proc CALCheckDHCPSnoopingReSetPersistentConfiguration {switch_name} {
    set switch_vendor [ _get_switch_vendor_name  $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrDHCPSnoopingReSetPersistentConfiguration $switch_name
        }
        cisco {
            puts "TODO cisco\n"
        }
        hp {
            puts "TODO hp\n"
        }
        3com {
            puts "TODO hp\n"
        }
        default {
            Netgear_log_file "cal-igmpsnooping.tcl" "Switch not defined"
        }
    }
}
