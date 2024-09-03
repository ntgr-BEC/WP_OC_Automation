####################################################################################
#  File Name   : lib-ntgr-dhcp.tcl                                                 #
#                                                                                  #
#  Description :                                                                   #
#        This file includes functions used for DHCP configuration.                 #
#                                                                                  #
#  History     :                                                                   #
#        Date          Programmer         Description                              #
#        Feb 26, 2007  Scott Zhang        Created                                  #
#                                                                                  #
####################################################################################

#*******************************************************************************
#
#  Function Name  : _ntgrEnableDHCP
#
#  Description    : This function is called to enable DHCP service.
#
#  Usage          : _ntgrEnableDHCP <switch_name>
#
#*******************************************************************************
proc _ntgrEnableDHCP {switch_name} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    exp_send -i $cnn_id "configure\r"
    exp_sleep 1
    exp_send -i $cnn_id "service dhcp\r"
    exp_sleep 1

    Netgear_disconnect_switch $switch_name
}

#*******************************************************************************
#
#  Function Name  : _ntgrDisableDHCP
#
#  Description    : This function is called to disable DHCP service.
#
#  Usage          : _ntgrDisableDHCP <switch_name>
#
#*******************************************************************************
proc _ntgrDisableDHCP {switch_name} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    exp_send -i $cnn_id "configure\r"
    exp_sleep 1
    exp_send -i $cnn_id "no service dhcp\r"
    exp_sleep 1

    Netgear_disconnect_switch $switch_name
}

#*******************************************************************************
#
#  Function Name  : _ntgrCreateDhcpAddrPool
#
#  Description    : This function is called to create DHCP address pool.
#
#  Usage          : _ntgrCreateDhcpAddrPool <switch_name pool_name>
#
#*******************************************************************************
proc _ntgrCreateDhcpAddrPool {switch_name pool_name} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    exp_send -i $cnn_id "configure\r"
    exp_sleep 1

    if {$pool_name == {}} {
        set pool_name [array names ntgr_DHCP_AddrPool_$switch_name]; # All
    }
    foreach pool $pool_name {
        set bootfile        [_ntgrGetDhcpPoolBootfile $switch_name $pool]
        set clientID        [_ntgrGetDhcpPoolClientID $switch_name $pool]
        set clientName      [_ntgrGetDhcpPoolClientName $switch_name $pool]
        set router          [_ntgrGetDhcpPoolDefaultRouter $switch_name $pool]
        set dns             [_ntgrGetDhcpPoolDnsServer $switch_name $pool]
        set domain          [_ntgrGetDhcpPoolDomainName $switch_name $pool]
        set hwAddr          [_ntgrGetDhcpPoolHwAddr $switch_name $pool]
        set host            [_ntgrGetDhcpPoolHostIP $switch_name $pool]
        set lease           [_ntgrGetDhcpPoolLeaseTime $switch_name $pool]
        set nns             [_ntgrGetDhcpPoolNetbiosNameServer $switch_name $pool]
        set nnt             [_ntgrGetDhcpPoolNetbiosNodeType $switch_name $pool]
        set ns              [_ntgrGetDhcpPoolNextServer $switch_name $pool]
        set network         [_ntgrGetDhcpPoolNetwork $switch_name $pool]
        set op_list         [_ntgrGetDhcpPoolOptionList $switch_name $pool]

        exp_send -i $cnn_id "ip dhcp pool $pool\r"
        exp_sleep 1

        if {$bootfile != {}} {
            exp_send -i $cnn_id "bootfile $bootfile\r"
            exp_sleep 1
        }

        if {$clientID != {}} {
            exp_send -i $cnn_id "client-identifier $clientID\r"
            exp_sleep 1
        }

        if {$clientName != {}} {
            exp_send -i $cnn_id "client-name $clientName\r"
            exp_sleep 1
        }

        if {$router != {}} {
            exp_send -i $cnn_id "default-router $router\r"
            exp_sleep 1
        }

        if {$dns != {}} {
            exp_send -i $cnn_id "dns-server $dns\r"
            exp_sleep 1
        }

        if {$domain != {}} {
            exp_send -i $cnn_id "domain-name $domain\r"
            exp_sleep 1
        }

        if {$hwAddr != {}} {
            exp_send -i $cnn_id "hardware-address $hwAddr\r"
            exp_sleep 1
        }

        if {$host != {}} {
            exp_send -i $cnn_id "host $host\r"
            exp_sleep 1
        }

        if {$lease != {}} {
            exp_send -i $cnn_id "lease $lease\r"
            exp_sleep 1
        }

        if {$nns != {}} {
            exp_send -i $cnn_id "netbios-name-server $nns\r"
            exp_sleep 1
        }

        if {$nnt != {}} {
            exp_send -i $cnn_id "netbios-node-type $nnt\r"
            exp_sleep 1
        }

        if {$ns != {}} {
            exp_send -i $cnn_id "next-server $ns\r"
            exp_sleep 1
        }

        if {$network != {}} {
            exp_send -i $cnn_id "network $network\r"
            exp_sleep 1
        }

        foreach op $op_list {
            if {$op != {}} {
                exp_send -i $cnn_id "option $op\r"
                exp_sleep 1
            }
        }

        exp_send -i $cnn_id "exit\r"
        exp_sleep 1
    }

    Netgear_disconnect_switch $switch_name
}

#*******************************************************************************
#
#  Function Name  : _ntgrDelDhcpAddrPool
#
#  Description    : This function is called to delete DHCP address pool.
#
#  Usage          : _ntgrDelDhcpAddrPool <switch_name pool_name>
#
#*******************************************************************************
proc _ntgrDelDhcpAddrPool {switch_name pool_name} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    exp_send -i $cnn_id "configure\r"
    exp_sleep 1

    if {$pool_name == {}} {
        set pool_name [array names ntgr_DHCP_AddrPool_$switch_name]; # All
    }
    foreach pool $pool_name {
        exp_send -i $cnn_id "no ip dhcp pool $pool\r"
        exp_sleep 1
        exp_send -i $cnn_id "exit\r"
        exp_sleep 1
    }

    Netgear_disconnect_switch $switch_name
}

#*******************************************************************************
#
#  Function Name  : _ntgrResetDhcpLeaseTime
#
#  Description    : This function is called to reset DHCP lease time to default.
#
#  Usage          : _ntgrResetDhcpLeaseTime <switch_name> <pool_name>
#
#*******************************************************************************
proc _ntgrResetDhcpLeaseTime {switch_name pool_name} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    exp_send -i $cnn_id "configure\r"
    exp_sleep 1
    exp_send -i $cnn_id "ip dhcp pool $pool_name\r"
    exp_sleep 1
    exp_send -i $cnn_id "no lease\r"
    exp_sleep 1

    Netgear_disconnect_switch $switch_name
}

#*******************************************************************************
#
#  Function Name  : _ntgrResetDhcpGateway
#
#  Description    : This function is called to reset DHCP gateway to default.
#
#  Usage          : _ntgrResetDhcpGateway <switch_name> <pool_name>
#
#*******************************************************************************
proc _ntgrResetDhcpGateway {switch_name pool_name} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    exp_send -i $cnn_id "configure\r"
    exp_sleep 1
    exp_send -i $cnn_id "ip dhcp pool $pool_name\r"
    exp_sleep 1
    exp_send -i $cnn_id "no default-router\r"
    exp_sleep 1

    Netgear_disconnect_switch $switch_name
}

#*******************************************************************************
#
#  Function Name  : _ntgrResetDhcpDNS
#
#  Description    : This function is called to reset DHCP DNS server to default.
#
#  Usage          : _ntgrResetDhcpDNS <switch_name> <pool_name>
#
#*******************************************************************************
proc _ntgrResetDhcpDNS {switch_name pool_name} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    exp_send -i $cnn_id "configure\r"
    exp_sleep 1
    exp_send -i $cnn_id "ip dhcp pool $pool_name\r"
    exp_sleep 1
    exp_send -i $cnn_id "no dns-server\r"
    exp_sleep 1

    Netgear_disconnect_switch $switch_name
}

#*******************************************************************************
#
#  Function Name  : _ntgrResetDhcpDomain
#
#  Description    : This function is called to reset domain name to default.
#
#  Usage          : _ntgrResetDhcpDomain <switch_name> <pool_name>
#
#*******************************************************************************
proc _ntgrResetDhcpDomain {switch_name pool_name} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    exp_send -i $cnn_id "configure\r"
    exp_sleep 1
    exp_send -i $cnn_id "ip dhcp pool $pool_name\r"
    exp_sleep 1
    exp_send -i $cnn_id "no domain-name\r"
    exp_sleep 1

    Netgear_disconnect_switch $switch_name
}

#*******************************************************************************
#
#  Function Name  : _ntgrResetDhcpNNS
#
#  Description    : This function is called to reset netbios name server to default.
#
#  Usage          : _ntgrResetDhcpNNS <switch_name> <pool_name>
#
#*******************************************************************************
proc _ntgrResetDhcpNNS {switch_name pool_name} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    exp_send -i $cnn_id "configure\r"
    exp_sleep 1
    exp_send -i $cnn_id "ip dhcp pool $pool_name\r"
    exp_sleep 1
    exp_send -i $cnn_id "no netbios-name-server\r"
    exp_sleep 1

    Netgear_disconnect_switch $switch_name
}

#*******************************************************************************
#
#  Function Name  : _ntgrConfigureDhcpNNT
#
#  Description    : This function is called to configure netbios node type to default.
#
#  Usage          : _ntgrConfigureDhcpNNT <switch_name> <pool_name>
#
#*******************************************************************************
proc _ntgrConfigureDhcpNNT {switch_name pool_name type} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    exp_send -i $cnn_id "configure\r"
    exp_sleep 1
    exp_send -i $cnn_id "ip dhcp pool $pool_name\r"
    exp_sleep 1
    if {$type != {}} {
        exp_send -i $cnn_id "netbios-node-type $type\r"
        exp_sleep 1
    } else {
        exp_send -i $cnn_id "no netbios-node-type\r"
        exp_sleep 1
    }

    Netgear_disconnect_switch $switch_name
}

#*******************************************************************************
#
#  Function Name  : _ntgrResetDhcpBootfile
#
#  Description    : This function is called to reset bootfile name to default.
#
#  Usage          : _ntgrResetDhcpBootfile <switch_name> <pool_name>
#
#*******************************************************************************
proc _ntgrResetDhcpBootfile {switch_name pool_name} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    exp_send -i $cnn_id "configure\r"
    exp_sleep 1
    exp_send -i $cnn_id "ip dhcp pool $pool_name\r"
    exp_sleep 1
    exp_send -i $cnn_id "no bootfile\r"
    exp_sleep 1

    Netgear_disconnect_switch $switch_name
}

#*******************************************************************************
#
#  Function Name  : _ntgrResetDhcpNextServer
#
#  Description    : This function is called to reset next server address to default.
#
#  Usage          : _ntgrResetDhcpNextServer <switch_name> <pool_name>
#
#*******************************************************************************
proc _ntgrResetDhcpNextServer {switch_name pool_name} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    exp_send -i $cnn_id "configure\r"
    exp_sleep 1
    exp_send -i $cnn_id "ip dhcp pool $pool_name\r"
    exp_sleep 1
    exp_send -i $cnn_id "no next-server\r"
    exp_sleep 1

    Netgear_disconnect_switch $switch_name
}

#*******************************************************************************
#
#  Function Name  : _ntgrAddDhcpOption
#
#  Description    : This function is called to add option to address pool.
#
#  Usage          : _ntgrAddDhcpOption <switch_name> <pool_name> <op_string>
#
#*******************************************************************************
proc _ntgrAddDhcpOption {switch_name pool_name op_string} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    exp_send -i $cnn_id "configure\r"
    exp_sleep 1
    exp_send -i $cnn_id "ip dhcp pool $pool_name\r"
    exp_sleep 1
    exp_send -i $cnn_id "option $op_string\r"
    exp_sleep 1

    Netgear_disconnect_switch $switch_name
}

#*******************************************************************************
#
#  Function Name  : _ntgrDelDhcpOption
#
#  Description    : This function is called to delete option from address pool.
#
#  Usage          : _ntgrDelDhcpOption <switch_name> <pool_name> <op_code>
#
#*******************************************************************************
proc _ntgrDelDhcpOption {switch_name pool_name op_code} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    exp_send -i $cnn_id "configure\r"
    exp_sleep 1
    exp_send -i $cnn_id "ip dhcp pool $pool_name\r"
    exp_sleep 1
    exp_send -i $cnn_id "no option $op_code\r"
    exp_sleep 1

    Netgear_disconnect_switch $switch_name
}

#*******************************************************************************
#
#  Function Name  : _ntgrAddDhcpExcludeAddr
#
#  Description    : This function is called to configure excluded address.
#
#  Usage          : _ntgrAddDhcpExcludeAddr <switch_name> <ex_addr>
#
#*******************************************************************************
proc _ntgrAddDhcpExcludeAddr {switch_name ex_addr} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]

    exp_send -i $cnn_id "configure\r"
    exp_sleep 1
    exp_send -i $cnn_id "ip dhcp excluded-address $ex_addr\r"
    exp_sleep 1

    Netgear_disconnect_switch $switch_name
}

#*******************************************************************************
#
#  Function Name  : _ntgrDelDhcpExcludeAddr
#
#  Description    : This function is called to delete excluded address.
#
#  Usage          : _ntgrDelDhcpExcludeAddr <switch_name> <ex_addr>
#
#*******************************************************************************
proc _ntgrDelDhcpExcludeAddr {switch_name ex_addr} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]

    exp_send -i $cnn_id "configure\r"
    exp_sleep 1
    exp_send -i $cnn_id "no ip dhcp excluded-address $ex_addr\r"
    exp_sleep 1

    Netgear_disconnect_switch $switch_name
}

#*******************************************************************************
#
#  Function Name  : _ntgrResetDhcpClientName
#
#  Description    : This function is called to reset the client name to default.
#
#  Usage          : _ntgrResetDhcpClientName <switch_name> <pool_name>
#
#*******************************************************************************
proc _ntgrResetDhcpClientName {switch_name pool_name} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    exp_send -i $cnn_id "configure\r"
    exp_sleep 1
    exp_send -i $cnn_id "ip dhcp pool $pool_name\r"
    exp_sleep 1
    exp_send -i $cnn_id "no client-name\r"
    exp_sleep 1

    Netgear_disconnect_switch $switch_name
}

#*******************************************************************************
#
#  Function Name  : _ntgrConfigDhcpPingNumber
#
#  Description    : This function is called to configure ping packets number.
#
#  Usage          : _ntgrConfigDhcpPingNumber <switch_name> <num>
#
#*******************************************************************************
proc _ntgrConfigDhcpPingNumber {switch_name num} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]

    exp_send -i $cnn_id "configure\r"
    exp_sleep 1
    exp_send -i $cnn_id "ip dhcp ping packets $num\r"
    exp_sleep 1

    Netgear_disconnect_switch $switch_name
}

#*******************************************************************************
#
#  Function Name  : _ntgrResetDhcpPingNumber
#
#  Description    : This function is called to reset ping packets number.
#
#  Usage          : _ntgrResetDhcpPingNumber <switch_name>
#
#*******************************************************************************
proc _ntgrResetDhcpPingNumber {switch_name} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]

    exp_send -i $cnn_id "configure\r"
    exp_sleep 1
    exp_send -i $cnn_id "no ip dhcp ping packets\r"
    exp_sleep 1

    Netgear_disconnect_switch $switch_name
}

#*******************************************************************************
#
#  Function Name  : _ntgrEnableConflictLogging
#
#  Description    : This function is called to enable conflict infor logging.
#
#  Usage          : _ntgrEnableConflictLogging <switch_name>
#
#*******************************************************************************
proc _ntgrEnableConflictLogging {switch_name} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]

    exp_send -i $cnn_id "configure\r"
    exp_sleep 1
    exp_send -i $cnn_id "ip dhcp conflict logging\r"
    exp_sleep 1

    Netgear_disconnect_switch $switch_name
}

#*******************************************************************************
#
#  Function Name  : _ntgrDisableConflictLogging
#
#  Description    : This function is called to disable conflict infor logging.
#
#  Usage          : _ntgrDisableConflictLogging <switch_name>
#
#*******************************************************************************
proc _ntgrDisableConflictLogging {switch_name} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]

    exp_send -i $cnn_id "configure\r"
    exp_sleep 1
    exp_send -i $cnn_id "no ip dhcp conflict logging\r"
    exp_sleep 1

    Netgear_disconnect_switch $switch_name
}

#*******************************************************************************
#
#  Function Name  : _ntgrEnableBootp
#
#  Description    : This function is called to enable automatic address
#                   allocation for bootp clients.
#
#  Usage          : _ntgrEnableBootp <switch_name>
#
#*******************************************************************************
proc _ntgrEnableBootp {switch_name} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]

    exp_send -i $cnn_id "configure\r"
    exp_sleep 1
    exp_send -i $cnn_id "ip dhcp bootp automatic\r"
    exp_sleep 1

    Netgear_disconnect_switch $switch_name
}

#*******************************************************************************
#
#  Function Name  : _ntgrDisableBootp
#
#  Description    : This function is called to disable automatic address
#                   allocation for bootp clients.
#
#  Usage          : _ntgrDisableBootp <switch_name>
#
#*******************************************************************************
proc _ntgrDisableBootp {switch_name} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]

    exp_send -i $cnn_id "configure\r"
    exp_sleep 1
    exp_send -i $cnn_id "no ip dhcp bootp automatic\r"
    exp_sleep 1

    Netgear_disconnect_switch $switch_name
}

#*******************************************************************************
#
#  Function Name  : _ntgrClearDhcpStatistics
#
#  Description    : This function is called to clear statistics of DHCP.
#
#  Usage          : _ntgrClearDhcpStatistics <switch_name>
#
#*******************************************************************************
proc _ntgrClearDhcpStatistics {switch_name} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]

    exp_send -i $cnn_id "clear ip dhcp server statistics\r"
    exp_sleep 1

    Netgear_disconnect_switch $switch_name
}

#*******************************************************************************
#
#  Function Name  : _ntgrClearDhcpConflict
#
#  Description    : This function is called to clear DHCP conflict logging.
#
#  Usage          : _ntgrClearDhcpConflict <switch_name> <ip>
#
#*******************************************************************************
proc _ntgrClearDhcpConflict {switch_name ip} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]

    if {$ip == {}} {
        set ip "*"
    }

    exp_send -i $cnn_id "clear ip dhcp conflict $ip\r"
    exp_sleep 1

    Netgear_disconnect_switch $switch_name
}

#*******************************************************************************
#
#  Function Name  : _ntgrClearDhcpBind
#
#  Description    : This function is called to clear DHCP binding.
#
#  Usage          : _ntgrClearDhcpBind <switch_name> <ip>
#
#*******************************************************************************
proc _ntgrClearDhcpBind {switch_name ip} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]

    if {$ip == {}} {
        set ip "*"
    }

    exp_send -i $cnn_id "clear ip dhcp binding $ip\r"
    exp_sleep 1

    Netgear_disconnect_switch $switch_name
}

#*******************************************************************************
#
#  Function Name  : _ntgrEnableDhcpRelay
#
#  Description    : This function is called to enable DHCP relay.
#
#  Usage          : _ntgrEnableDhcpRelay <switch_name>
#
#*******************************************************************************
proc _ntgrEnableDhcpRelay {switch_name} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]

    exp_send -i $cnn_id "configure\r"
    exp_sleep 1
    exp_send -i $cnn_id "bootpdhcprelay\r"
    exp_sleep 1

    Netgear_disconnect_switch $switch_name
}

#*******************************************************************************
#
#  Function Name  : _ntgrDhcpRelayServer
#
#  Description    : This function is called to configure DHCP sever IP.
#
#  Usage          : _ntgrDhcpRelayServer <switch_name> <server> <backup {}>
#
#*******************************************************************************
proc _ntgrDhcpRelayServer {switch_name server {backup {}}} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]

    exp_send -i $cnn_id "configure\r"
    exp_sleep 1
    exp_send -i $cnn_id "bootpdhcprelay serverip $server\r"
    exp_sleep 1
    if {$backup != {}} {
        exp_send -i $cnn_id "bootpdhcprelay backup-serverip $backup\r"
        exp_sleep 1
    }

    Netgear_disconnect_switch $switch_name
}




#*******************************************************************************
#
#  Function Name  : _ntgrDhcpGetBindingIP
#
#  Description    : This function is used to get dhcp binding ip by mac.
#
#  Usage          : _ntgrDhcpGetBindingIP <switch_name> <mac>
#
#*******************************************************************************
proc _ntgrDhcpGetBindingIP {switch_name mac} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    set output ""
    exp_send -i $cnn_id "show ip dhcp binding\r"
    exp_sleep 1
    expect -i $cnn_id -re "show ip dhcp binding"
    expect {
        -i $cnn_id -re "#" { append output $expect_out(buffer) }
        -i $cnn_id -re "More--" {
            append output $expect_out(buffer)
            exp_send -i $cnn_id " "
            exp_sleep 1
            exp_continue
        }
    }
    
    set result ""
    regsub -all {\r\n} $output {\n} output
    set output_list [split $output "\n"]
    set indexStr [lsearch -regexp $output_list $mac]
    if {$indexStr != -1} {
        set itemStr [lindex $output_list $indexStr]
        set result [lindex [string trim $itemStr] 0]
    }

    Netgear_log_file "lib-dhcp.tcl" "get dhcp binding ip on switcher"
    Netgear_disconnect_switch $switch_name

    return $result
}

#*******************************************************************************
#
#  Function Name  : _ntgrCheckDhcpBindingEntry
#
#  Description    : This function is used to check dhcp binding entry exist or not.
#
#  Usage          : _ntgrCheckDhcpBindingEntry <switch_name> <mac_list> <notin>
#
#*******************************************************************************
proc _ntgrCheckDhcpBindingEntry {switch_name mac_list {notin 0}} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    set output ""
    
    exp_send -i $cnn_id "show ip dhcp binding\r"
    exp_sleep 1
    expect -i $cnn_id -re "show ip dhcp binding"
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

    Netgear_log_file "lib-dhcp.tcl" "check dhcp binding entry on switcher"
    Netgear_disconnect_switch $switch_name

    return $result
}


#*******************************************************************************
#
#  Function Name  : _ntgrCheckDhcpConflictEntry
#
#  Description    : This function is used to check dhcp conflict entry exist or not.
#
#  Usage          : _ntgrCheckDhcpConflictEntry <switch_name> <ip_list> <notin>
#
#*******************************************************************************
proc _ntgrCheckDhcpConflictEntry {switch_name ip_list {notin 0}} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    set output ""
    
    exp_send -i $cnn_id "show ip dhcp conflict\r"
    exp_sleep 1
    expect -i $cnn_id -re "show ip dhcp conflict"
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
    foreach ipStr $ip_list {
        if {$notin == 1} {
            if {[regexp -nocase $ipStr $output]} {
                set result 0
                break
            }
        } else {
            if {![regexp -nocase $ipStr $output]} {
                set result 0
                break
            }
        }
    }

    Netgear_log_file "lib-dhcp.tcl" "check dhcp conflict entry on switcher"
    Netgear_disconnect_switch $switch_name

    return $result
}


#*******************************************************************************
#
#  Function Name  : _ntgrGetDhcpStatistics
#
#  Description    : This function is used to get dhcp server statistics.
#
#  Usage          : _ntgrGetDhcpStatistics <switch_name> <dhcp_Info>
#
#*******************************************************************************
proc _ntgrGetDhcpStatistics {switch_name dhcp_Info} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    set output ""
    
    exp_send -i $cnn_id "show ip dhcp server statistics\r"
    exp_sleep 1
    expect -i $cnn_id -re "show ip dhcp server statistics"
    expect {
        -i $cnn_id -re "#" { append output $expect_out(buffer) }
        -i $cnn_id -re "More--" {
            append output $expect_out(buffer)
            exp_send -i $cnn_id " "
            exp_sleep 1
            exp_continue
        }
    }
    
    set dhcp_discover -1
    set dhcp_request -1
    set dhcp_decline -1
    set dhcp_release -1
    set dhcp_inform -1
    set dhcp_offer -1
    set dhcp_ack -1
    set dhcp_nack -1
    set automatic_binding -1
	set expired_binding -1
	
	regexp -nocase {Automatic Bindings\s*\.+\s*(\d+)} $output dumpStr automatic_binding
	regexp -nocase {Expired Bindings\s*\.+\s*(\d+)} $output dumpStr expired_binding	
    regexp -nocase {DHCP DISCOVER\s*\.+\s*(\d+)} $output dumpStr dhcp_discover
    regexp -nocase {DHCP REQUEST\s*\.+\s*(\d+)} $output dumpStr dhcp_request
    regexp -nocase {DHCP DECLINE\s*\.+\s*(\d+)} $output dumpStr dhcp_decline
    regexp -nocase {DHCP RELEASE\s*\.+\s*(\d+)} $output dumpStr dhcp_release
    regexp -nocase {DHCP INFORM\s*\.+\s*(\d+)} $output dumpStr dhcp_inform
    regexp -nocase {DHCP OFFER\s*\.+\s*(\d+)} $output dumpStr dhcp_offer
    regexp -nocase {DHCP ACK\s*\.+\s*(\d+)} $output dumpStr dhcp_ack
    regexp -nocase {DHCP NACK\s*\.+\s*(\d+)} $output dumpStr dhcp_nack
    
    upvar $dhcp_Info info_aray
    array set info_aray "automatic_binding $automatic_binding expired_binding $expired_binding dhcp_discover $dhcp_discover dhcp_request $dhcp_request dhcp_decline $dhcp_decline dhcp_release $dhcp_release dhcp_inform $dhcp_inform dhcp_offer $dhcp_offer dhcp_ack $dhcp_ack dhcp_nack $dhcp_nack"

    Netgear_log_file "lib-dhcp.tcl" "get dhcp server statistics on switcher"
    Netgear_disconnect_switch $switch_name
}

#*******************************************************************************
#
#  Function Name  : _ntgrGetDhcpPingNumber
#
#  Description    : This function is used to get dhcp ping number.
#
#  Usage          : _ntgrGetDhcpPingNumber <switch_name>
#
#*******************************************************************************
proc _ntgrGetDhcpPingNumber {switch_name} {
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]
    
    set output ""
    
    exp_send -i $cnn_id "show ip dhcp global configuration\r"
    exp_sleep 1
    expect -i $cnn_id -re "show ip dhcp global configuration"
    expect {
        -i $cnn_id -re "#" { append output $expect_out(buffer) }
        -i $cnn_id -re "More--" {
            append output $expect_out(buffer)
            exp_send -i $cnn_id " "
            exp_sleep 1
            exp_continue
        }
    }
    
    set ping_num ""
    regexp -nocase {Number of Ping Packets\s*\.+\s*(\d+)} $output dumpStr ping_num

    Netgear_log_file "lib-dhcp.tcl" "get dhcp ping number on switcher"
    Netgear_disconnect_switch $switch_name
    
    return $ping_num
}