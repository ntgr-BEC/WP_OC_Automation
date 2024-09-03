####################################################################################
#  File Name   : cal-ntgr-dhcpv6.tcl
#
#  Description :
#        This file contains CLI Abstraction layer for DHCPv6 configuration.
#
#  History     :
#        Date          Programmer         Description
#        April 14, 2014  Jason        Created
#
####################################################################################

#*******************************************************************************
#
#  Function Name  : CALEnableDHCP
#
#  Description    : This function is called to enable DHCP service.
#
#  Usage          : CALEnableDHCP <switch_name>
#
#*******************************************************************************
proc CALEnableDHCPv6 {switch_name} {
    set switch_vendor [ _get_switch_vendor_name $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrEnableDHCPv6 $switch_name
        }
        cisco {
            puts "TODO cisco\n"
        }
        hp {
            puts "TODO hp\n"
        }
        3com {
            puts "TODO 3com\n"
        }
        default {
            Netgear_log_file "cal-ntgr-dhcp.tcl" "Switch not defined"
        }
    }
}

#*******************************************************************************
#
#  Function Name  : CALDisableDHCP
#
#  Description    : This function is called to disable DHCP service.
#
#  Usage          : CALDisableDHCP <switch_name>
#
#*******************************************************************************
proc CALDisableDHCPv6 {switch_name} {
    set switch_vendor [ _get_switch_vendor_name $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrDisableDHCPv6 $switch_name
        }
        cisco {
            puts "TODO cisco\n"
        }
        hp {
            puts "TODO hp\n"
        }
        3com {
            puts "TODO 3com\n"
        }
        default {
            Netgear_log_file "cal-ntgr-dhcp.tcl" "Switch not defined"
        }
    }
} 

#*******************************************************************************
#
#  Function Name  : CALCreateDhcpv6AddrPool
#
#  Description    : This function is called to Create DHCPv6 Pool.
#
#  Usage          : CALCreateDhcpv6AddrPool <switch_name> <pool_name>
#
#*******************************************************************************
proc CALCreateDhcpv6AddrPool {switch_name {pool_name {}}} {
    set switch_vendor [ _get_switch_vendor_name $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrCreateDhcpv6AddrPool $switch_name  $pool_name
        }
        cisco {
            puts "TODO cisco\n"
        }
        hp {
            puts "TODO hp\n"
        }
        3com {
            puts "TODO 3com\n"
        }
        default {
            Netgear_log_file "cal-ntgr-dhcp.tcl" "Switch not defined"
        }
    }
} 

#*******************************************************************************
#
#  Function Name  : CALDelDhcpv6AddrPool
#
#  Description    : This function is called to Delete DHCPv6 Addr Pool.
#
#  Usage          : CALDelDhcpv6AddrPool <switch_name> <pool_name>
#
#*******************************************************************************
proc CALDelDhcpv6AddrPool {switch_name {pool_name {}}} {
    set switch_vendor [ _get_switch_vendor_name $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrDelDhcpv6AddrPool $switch_name $pool_name
        }
        cisco {
            puts "TODO cisco\n"
        }
        hp {
            puts "TODO hp\n"
        }
        3com {
            puts "TODO 3com\n"
        }
        default {
            Netgear_log_file "cal-ntgr-dhcp.tcl" "Switch not defined"
        }
    }
} 

#*******************************************************************************
#
#  Function Name  : CALEnableDhcpv6PoolOnPort
#
#  Description    : This function is called to bindings DHCPv6 Pool on Port.
#
#  Usage          : CALEnableDhcpv6PoolOnPort <switch_name> <port> <pool_name>
#
#*******************************************************************************
proc CALEnableDhcpv6PoolOnPort {switch_name port {pool_name {}}} {
    set switch_vendor [ _get_switch_vendor_name $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrEnableDhcpv6PoolOnPort $switch_name $port $pool_name
        }
        cisco {
            puts "TODO cisco\n"
        }
        hp {
            puts "TODO hp\n"
        }
        3com {
            puts "TODO 3com\n"
        }
        default {
            Netgear_log_file "cal-ntgr-dhcp.tcl" "Switch not defined"
        }
    }
} 

#*******************************************************************************
#
#  Function Name  : CALDisableDhcpv6PoolOnPort
#
#  Description    : This function is called to Delete DHCPv6 bindings on Port.
#
#  Usage          : CALDisableDhcpv6PoolOnPort <switch_name> <port> <pool_name>
#
#*******************************************************************************
proc CALDisableDhcpv6PoolOnPort {switch_name port {pool_name {}}} {
    set switch_vendor [ _get_switch_vendor_name $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrDisableDhcpv6PoolOnPort $switch_name $port $pool_name
        }
        cisco {
            puts "TODO cisco\n"
        }
        hp {
            puts "TODO hp\n"
        }
        3com {
            puts "TODO 3com\n"
        }
        default {
            Netgear_log_file "cal-ntgr-dhcp.tcl" "Switch not defined"
        }
    }
} 

#*******************************************************************************
#
#  Function Name  : CALCheckDHCPv6BindingEntry
#
#  Description    : This function is called to Check DHCPv6 Binding Engtry.
#
#  Usage          : CALCheckDHCPv6BindingEntry <switch_name> <duid_list> <notin>
#
#*******************************************************************************
proc CALCheckDHCPv6BindingEntry {switch_name duid_list {notin 0}} {
    set switch_vendor [ _get_switch_vendor_name $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrCheckDHCPv6BindingEntry $switch_name $duid_list $notin
        }
        cisco {
            puts "TODO cisco\n"
        }
        hp {
            puts "TODO hp\n"
        }
        3com {
            puts "TODO 3com\n"
        }
        default {
            Netgear_log_file "cal-ntgr-dhcp.tcl" "Switch not defined"
        }
    }
}

#*******************************************************************************
#
#  Function Name  : CALCheckDHCPv6BindingPreferLifetime
#
#  Description    : This function is called to Check Prefer Lifetime in DHCPv6 Bindings Table.
#
#  Usage          : CALCheckDHCPv6BindingPreferLifetime <switch_name> <pretime> <notin>
#
#*******************************************************************************
proc CALCheckDHCPv6BindingPreferLifetime {switch_name pretime {notin 0}} {
    set switch_vendor [ _get_switch_vendor_name $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrCheckDHCPv6BindingPreferLifetime $switch_name $pretime $notin
        }
        cisco {
            puts "TODO cisco\n"
        }
        hp {
            puts "TODO hp\n"
        }
        3com {
            puts "TODO 3com\n"
        }
        default {
            Netgear_log_file "cal-ntgr-dhcp.tcl" "Switch not defined"
        }
    }
} 

#*******************************************************************************
#
#  Function Name  : CALCheckDHCPv6BindingValidLifetime
#
#  Description    : This function is called to Check Valid Time in DHCPv6 Bindings Table.
#
#  Usage          : CALCheckDHCPv6BindingValidLifetime <switch_name> <validtime> <notin>
#
#*******************************************************************************
proc CALCheckDHCPv6BindingValidLifetime {switch_name validtime {notin 0}} {
    set switch_vendor [ _get_switch_vendor_name $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrCheckDHCPv6BindingValidLifetime $switch_name $validtime $notin
        }
        cisco {
            puts "TODO cisco\n"
        }
        hp {
            puts "TODO hp\n"
        }
        3com {
            puts "TODO 3com\n"
        }
        default {
            Netgear_log_file "cal-ntgr-dhcp.tcl" "Switch not defined"
        }
    }
} 

#*******************************************************************************
#
#  Function Name  : CALCheckDHCPv6BindingPrefix
#
#  Description    : This function is called to Check prefix on DHCPv6 Bindings Table.
#
#  Usage          : CALCheckDHCPv6BindingPrefix <switch_name> <prefix> <notin>
#
#*******************************************************************************
proc CALCheckDHCPv6BindingPrefix {switch_name prefix {notin 0}} {
    set switch_vendor [ _get_switch_vendor_name $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrCheckDHCPv6BindingPrefix $switch_name $prefix $notin
        }
        cisco {
            puts "TODO cisco\n"
        }
        hp {
            puts "TODO hp\n"
        }
        3com {
            puts "TODO 3com\n"
        }
        default {
            Netgear_log_file "cal-ntgr-dhcp.tcl" "Switch not defined"
        }
    }
} 

#*******************************************************************************
#
#  Function Name  : CALClearDHCPv6ServerPacketStatistic
#
#  Description    : This function is called to clear dhcpv6 server packets statistic.
#
#  Usage          : CALClearDHCPv6ServerPacketStatistic <switch_name>
#
#*******************************************************************************
proc CALClearDHCPv6ServerPacketStatistic {switch_name} {
    set switch_vendor [ _get_switch_vendor_name $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrClearDHCPv6ServerPacketStatistic $switch_name
        }
        cisco {
            puts "TODO cisco\n"
        }
        hp {
            puts "TODO hp\n"
        }
        3com {
            puts "TODO 3com\n"
        }
        default {
            Netgear_log_file "cal-ntgr-dhcp.tcl" "Switch not defined"
        }
    }
} 

#*******************************************************************************
#
#  Function Name  : CALGetDHCPv6ServerPacketStatistic
#
#  Description    : This function is called to Get DHCPv6 Server Packets Statistic.
#
#  Usage          : CALGetDHCPv6ServerPacketStatistic <switch_name> <strtext>
#
#*******************************************************************************
proc CALGetDHCPv6ServerPacketStatistic {switch_name strtext} {
    set switch_vendor [ _get_switch_vendor_name $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrGetDHCPv6ServerPacketStatistic $switch_name $strtext
        }
        cisco {
            puts "TODO cisco\n"
        }
        hp {
            puts "TODO hp\n"
        }
        3com {
            puts "TODO 3com\n"
        }
        default {
            Netgear_log_file "cal-ntgr-dhcp.tcl" "Switch not defined"
        }
    }
}

#*******************************************************************************
#
#  Function Name  : CALCreateDhcpv6AddrPrefix
#
#  Description    : This function is called to Create DHCPv6 Addr Prefix.
#
#  Usage          : CALCreateDhcpv6AddrPrefix <switch_name> <prefix_name>
#
#*******************************************************************************
proc CALCreateDhcpv6AddrPrefixOnPool {switch_name pool_name prefix} {
    set switch_vendor [ _get_switch_vendor_name $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrCreateDhcpv6AddrPrefixOnPool $switch_name  $pool_name $prefix
        }
        cisco {
            puts "TODO cisco\n"
        }
        hp {
            puts "TODO hp\n"
        }
        3com {
            puts "TODO 3com\n"
        }
        default {
            Netgear_log_file "cal-ntgr-dhcp.tcl" "Switch not defined"
        }
    }
} 



