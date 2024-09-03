####################################################################################
#  File Name   : cal-ntgr-dhcp.tcl
#
#  Description :
#        This file contains CLI Abstraction layer for DHCP configuration.
#
#  History     :
#        Date          Programmer         Description
#        Feb 26, 2007  Scott Zhang        Created
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
proc CALEnableDHCP {switch_name} {
    set switch_vendor [ _get_switch_vendor_name $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrEnableDHCP $switch_name
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
proc CALDisableDHCP {switch_name} {
    set switch_vendor [ _get_switch_vendor_name $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrDisableDHCP $switch_name
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
#  Function Name  : CALCreateDhcpAddrPool
#
#  Description    : This function is called to create DHCP address pool.
#
#  Usage          : CALCreateDhcpAddrPool <switch_name> <pool_name {}>
#
#*******************************************************************************
proc CALCreateDhcpAddrPool {switch_name {pool_name {}}} {
    set switch_vendor [ _get_switch_vendor_name $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrCreateDhcpAddrPool $switch_name $pool_name
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
#  Function Name  : CALDelDhcpAddrPool
#
#  Description    : This function is called to delete DHCP address pool.
#
#  Usage          : CALDelDhcpAddrPool <switch_name> <pool_name {}>
#
#*******************************************************************************
proc CALDelDhcpAddrPool {switch_name {pool_name {}}} {
    set switch_vendor [ _get_switch_vendor_name $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrDelDhcpAddrPool $switch_name $pool_name
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
#  Function Name  : CALResetDhcpLeaseTime
#
#  Description    : This function is called to reset DHCP lease time to default.
#
#  Usage          : CALResetDhcpLeaseTime <switch_name> <pool_name>
#
#*******************************************************************************
proc CALResetDhcpLeaseTime {switch_name pool_name} {
    set switch_vendor [ _get_switch_vendor_name $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrResetDhcpLeaseTime $switch_name $pool_name
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
#  Function Name  : CALResetDhcpDNS
#
#  Description    : This function is called to reset DHCP DNS server to default.
#
#  Usage          : CALResetDhcpDNS <switch_name> <pool_name>
#
#*******************************************************************************
proc CALResetDhcpDNS {switch_name pool_name} {
    set switch_vendor [ _get_switch_vendor_name $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrResetDhcpDNS $switch_name $pool_name
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
#  Function Name  : CALResetDhcpDomain
#
#  Description    : This function is called to reset domain name to default.
#
#  Usage          : CALResetDhcpDomain <switch_name> <pool_name>
#
#*******************************************************************************
proc CALResetDhcpDomain {switch_name pool_name} {
    set switch_vendor [ _get_switch_vendor_name $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrResetDhcpDomain $switch_name $pool_name
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
#  Function Name  : CALResetDhcpNNS
#
#  Description    : This function is called to reset netbios name server to default.
#
#  Usage          : CALResetDhcpNNS <switch_name> <pool_name>
#
#*******************************************************************************
proc CALResetDhcpNNS {switch_name pool_name} {
    set switch_vendor [ _get_switch_vendor_name $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrResetDhcpNNS $switch_name $pool_name
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
#  Function Name  : CALConfigureDhcpNNT
#
#  Description    : This function is called to configure netbios node type to default.
#
#  Usage          : CALConfigureDhcpNNT <switch_name> <pool_name> <type {}>
#
#*******************************************************************************
proc CALConfigureDhcpNNT {switch_name pool_name {type {}}} {
    set switch_vendor [ _get_switch_vendor_name $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrConfigureDhcpNNT $switch_name $pool_name $type
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
#  Function Name  : CALResetDhcpBootfile
#
#  Description    : This function is called to reset bootfile name to default.
#
#  Usage          : CALResetDhcpBootfile <switch_name> <pool_name>
#
#*******************************************************************************
proc CALResetDhcpBootfile {switch_name pool_name} {
    set switch_vendor [ _get_switch_vendor_name $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrResetDhcpBootfile $switch_name $pool_name
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
#  Function Name  : CALResetDhcpNextServer
#
#  Description    : This function is called to reset next server address to default.
#
#  Usage          : CALResetDhcpNextServer <switch_name> <pool_name>
#
#*******************************************************************************
proc CALResetDhcpNextServer {switch_name pool_name} {
    set switch_vendor [ _get_switch_vendor_name $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrResetDhcpNextServer $switch_name $pool_name
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
#  Function Name  : CALAddDhcpOption
#
#  Description    : This function is called to add option to address pool.
#
#  Usage          : CALAddDhcpOption <switch_name> <pool_name> <op_string>
#
#*******************************************************************************
proc CALAddDhcpOption {switch_name pool_name op_string} {
    set switch_vendor [ _get_switch_vendor_name $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrAddDhcpOption $switch_name $pool_name $op_string
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
#  Function Name  : CALDelDhcpOption
#
#  Description    : This function is called to delete option from address pool.
#
#  Usage          : CALDelDhcpOption <switch_name> <pool_name> <op_code>
#
#*******************************************************************************
proc CALDelDhcpOption {switch_name pool_name op_code} {
    set switch_vendor [ _get_switch_vendor_name $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrDelDhcpOption $switch_name $pool_name $op_code
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
#  Function Name  : CALAddDhcpExcludeAddr
#
#  Description    : This function is called to configure the excluded address.
#
#  Usage          : CALAddDhcpExcludeAddr <switch_name> <ex_addr>
#
#*******************************************************************************
proc CALAddDhcpExcludeAddr {switch_name ex_addr} {
    set switch_vendor [ _get_switch_vendor_name $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrAddDhcpExcludeAddr $switch_name $ex_addr
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
#  Function Name  : CALDelDhcpExcludeAddr
#
#  Description    : This function is called to delete the excluded address.
#
#  Usage          : CALDelDhcpExcludeAddr <switch_name> <ex_addr>
#
#*******************************************************************************
proc CALDelDhcpExcludeAddr {switch_name ex_addr} {
    set switch_vendor [ _get_switch_vendor_name $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrDelDhcpExcludeAddr $switch_name $ex_addr
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
#  Function Name  : CALResetDhcpClientName
#
#  Description    : This function is called to reset the client name to default.
#
#  Usage          : CALResetDhcpClientName <switch_name> <pool_name>
#
#*******************************************************************************
proc CALResetDhcpClientName {switch_name pool_name} {
    set switch_vendor [ _get_switch_vendor_name $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrResetDhcpClientName $switch_name $pool_name
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
#  Function Name  : CALConfigDhcpPingNumber
#
#  Description    : This function is called to configure ping packets number.
#
#  Usage          : CALConfigDhcpPingNumber <switch_name> <num>
#
#*******************************************************************************
proc CALConfigDhcpPingNumber {switch_name num} {
    set switch_vendor [ _get_switch_vendor_name $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrConfigDhcpPingNumber $switch_name $num
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
#  Function Name  : CALResetDhcpPingNumber
#
#  Description    : This function is called to reset ping packets number.
#
#  Usage          : CALResetDhcpPingNumber <switch_name>
#
#*******************************************************************************
proc CALResetDhcpPingNumber {switch_name} {
    set switch_vendor [ _get_switch_vendor_name $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrResetDhcpPingNumber $switch_name
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
#  Function Name  : CALEnableConflictLogging
#
#  Description    : This function is called to enable conflict infor logging.
#
#  Usage          : CALEnableConflictLogging <switch_name>
#
#*******************************************************************************
proc CALEnableConflictLogging {switch_name} {
    set switch_vendor [ _get_switch_vendor_name $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrEnableConflictLogging $switch_name
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
#  Function Name  : CALDisableConflictLogging
#
#  Description    : This function is called to disable conflict infor logging.
#
#  Usage          : CALDisableConflictLogging <switch_name>
#
#*******************************************************************************
proc CALDisableConflictLogging {switch_name} {
    set switch_vendor [ _get_switch_vendor_name $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrDisableConflictLogging $switch_name
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
#  Function Name  : CALEnableBootp
#
#  Description    : This function is called to enable automatic address
#                   allocation for bootp clients.
#
#  Usage          : CALEnableBootp <switch_name>
#
#*******************************************************************************
proc CALEnableBootp {switch_name} {
    set switch_vendor [ _get_switch_vendor_name $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrEnableBootp $switch_name
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
#  Function Name  : CALDisableBootp
#
#  Description    : This function is called to disable automatic address
#                   allocation for bootp clients.
#
#  Usage          : CALDisableBootp <switch_name>
#
#*******************************************************************************
proc CALDisableBootp {switch_name} {
    set switch_vendor [ _get_switch_vendor_name $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrDisableBootp $switch_name
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
#  Function Name  : CALClearDhcpStatistics
#
#  Description    : This function is called to clear statistics of DHCP.
#
#  Usage          : CALClearDhcpStatistics <switch_name>
#
#*******************************************************************************
proc CALClearDhcpStatistics {switch_name} {
    set switch_vendor [ _get_switch_vendor_name $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrClearDhcpStatistics $switch_name
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
#  Function Name  : CALClearDhcpConflict
#
#  Description    : This function is called to DHCP conflict logging.
#
#  Usage          : CALClearDhcpConflict <switch_name> <ip {}>
#
#*******************************************************************************
proc CALClearDhcpConflict {switch_name {ip {}}} {
    set switch_vendor [ _get_switch_vendor_name $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrClearDhcpConflict $switch_name $ip
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
#  Function Name  : CALClearDhcpBind
#
#  Description    : This function is called to clear DHCP binding.
#
#  Usage          : CALClearDhcpBind <switch_name> <ip {}>
#
#*******************************************************************************
proc CALClearDhcpBind {switch_name {ip {}}} {
    set switch_vendor [ _get_switch_vendor_name $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrClearDhcpBind $switch_name $ip
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
#  Function Name  : CALEnableDhcpRelay
#
#  Description    : This function is called to enable DHCP relay.
#
#  Usage          : CALEnableDhcpRelay <switch_name>
#
#*******************************************************************************
proc CALEnableDhcpRelay {switch_name} {
    set switch_vendor [ _get_switch_vendor_name $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrEnableDhcpRelay $switch_name
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
#  Function Name  : CALDhcpRelayServer
#
#  Description    : This function is called to configure DHCP server.
#
#  Usage          : CALDhcpRelayServer <switch_name> <server> <backup {}>
#
#*******************************************************************************
proc CALDhcpRelayServer {switch_name server {backup {}}} {
    set switch_vendor [ _get_switch_vendor_name $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrDhcpRelayServer $switch_name $server $backup
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
#  Function Name  : CALDhcpGetBindingIP
#
#  Description    : This function is used to get dhcp binding ip by mac.
#
#  Usage          : CALDhcpGetBindingIP <switch_name> <mac>
#
#*******************************************************************************
proc CALDhcpGetBindingIP {switch_name mac} {
    set switch_vendor [ _get_switch_vendor_name $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrDhcpGetBindingIP $switch_name $mac
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
#  Function Name  : CALCheckDhcpBindingEntry
#
#  Description    : This function is used to check dhcp binding entry exist or not.
#
#  Usage          : CALCheckDhcpBindingEntry <switch_name> <mac_list> <notin>
#
#*******************************************************************************
proc CALCheckDhcpBindingEntry {switch_name mac_list {notin 0}} {
    set switch_vendor [ _get_switch_vendor_name $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrCheckDhcpBindingEntry $switch_name $mac_list $notin
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
#  Function Name  : CALCheckDhcpConflictEntry
#
#  Description    : This function is used to check dhcp conflict entry exist or not.
#
#  Usage          : CALCheckDhcpConflictEntry <switch_name> <ip_list> <notin>
#
#*******************************************************************************
proc CALCheckDhcpConflictEntry {switch_name ip_list {notin 0}} {
    set switch_vendor [ _get_switch_vendor_name $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrCheckDhcpConflictEntry $switch_name $ip_list $notin
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
#  Function Name  : CALGetDhcpPingNumber
#
#  Description    : This function is used to get dhcp ping number.
#
#  Usage          : CALGetDhcpPingNumber <switch_name>
#
#*******************************************************************************
proc CALGetDhcpPingNumber {switch_name} {
    set switch_vendor [ _get_switch_vendor_name $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrGetDhcpPingNumber $switch_name
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
