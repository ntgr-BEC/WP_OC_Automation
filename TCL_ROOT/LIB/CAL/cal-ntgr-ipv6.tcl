################################################################################   
#
#  File Name		  : cal-ntgr-ipv6.tcl
#
#  Description      :
#         This TCL contains functions to configure ipv6
#
#  Revision History :
#        Date          Programmer        Description
#        -----------------------------------------------------------------------
#        09-May-2011   Tony              Created
#
#
#
#

#*******************************************************************************
#
#  Function Name  : CALIpv6EnableUnicastRouting
#
#  Description    : This function is called to enable ipv6 unicast_routing
#
#  Usage          : CALIpv6EnableUnicastRouting <switch_name>
#
#*******************************************************************************
proc CALIpv6EnableUnicastRouting {switch_name} {
    set switch_vendor [ _get_switch_vendor_name $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrIpv6EnableUnicastRouting $switch_name
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
            Netgear_log_file "cal-ntgr-ipv6.tcl" "Switch not defined"
        }
    }
}

#*******************************************************************************
#
#  Function Name  : CALIpv6DisableUnicastRouting
#
#  Description    : This function is called to disable ipv6 unicast_routing
#
#  Usage          : CALIpv6DisableUnicastRouting <switch_name>
#
#*******************************************************************************
proc CALIpv6DisableUnicastRouting {switch_name} {
    set switch_vendor [ _get_switch_vendor_name $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrIpv6DisableUnicastRouting $switch_name
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
            Netgear_log_file "cal-ntgr-ipv6.tcl" "Switch not defined"
        }
    }
}


#*******************************************************************************
#
#  Function Name  : CALIpv6EnableOnPort
#
#  Description    : This function is called to enable ipv6 on port
#
#  Usage          : CALIpv6EnableOnPort <switch_name> <port>
#
#*******************************************************************************
proc CALIpv6EnableOnPort {switch_name port} {
    set switch_vendor [ _get_switch_vendor_name $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrIpv6EnableOnPort $switch_name $port
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
            Netgear_log_file "cal-ntgr-ipv6.tcl" "Switch not defined"
        }
    }
}

#*******************************************************************************
#
#  Function Name  : CALIpv6DisableOnPort
#
#  Description    : This function is called to disable ipv6 on port
#
#  Usage          : CALIpv6DisableOnPort <switch_name> <port>
#
#*******************************************************************************
proc CALIpv6DisableOnPort {switch_name port} {
    set switch_vendor [ _get_switch_vendor_name $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrIpv6DisableOnPort $switch_name $port
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
            Netgear_log_file "cal-ntgr-ipv6.tcl" "Switch not defined"
        }
    }
}


#*******************************************************************************
#
#  Function Name  : CALIpv6AddIpAddressOnPort
#
#  Description    : This function is called to add ipv6 address on port
#
#  Usage          : CALIpv6AddIpAddressOnPort <switch_name> <port> <addr>
#
#*******************************************************************************
proc CALIpv6AddIpAddressOnPort {switch_name port addr} {
    set switch_vendor [ _get_switch_vendor_name $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrIpv6AddIpAddressOnPort $switch_name $port $addr
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
            Netgear_log_file "cal-ntgr-ipv6.tcl" "Switch not defined"
        }
    }
}

#*******************************************************************************
#
#  Function Name  : CALIpv6DeleteIpAddressOnPort
#
#  Description    : This function is called to delete ipv6 address on port
#
#  Usage          : CALIpv6DeleteIpAddressOnPort <switch_name> <port> <addr>
#
#*******************************************************************************
proc CALIpv6DeleteIpAddressOnPort {switch_name port addr} {
    set switch_vendor [ _get_switch_vendor_name $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrIpv6DeleteIpAddressOnPort $switch_name $port $addr
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
            Netgear_log_file "cal-ntgr-ipv6.tcl" "Switch not defined"
        }
    }
}

#*******************************************************************************
#
#  Function Name  : CALIpv6EnableForwarding
#
#  Description    : This function is called to enable ipv6 forwarding.
#
#  Usage          : CALIpv6EnableForwarding <switch_name>
#
#*******************************************************************************
proc CALIpv6EnableForwarding {switch_name} {
    set switch_vendor [ _get_switch_vendor_name $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrIpv6EnableForwarding $switch_name
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
            Netgear_log_file "cal-ntgr-ipv6.tcl" "Switch not defined"
        }
    }
}

#*******************************************************************************
#
#  Function Name  : CALIpv6DisableForwarding
#
#  Description    : This function is called to disable ipv6 forwarding.
#
#  Usage          : CALIpv6DisableForwarding <switch_name>
#
#*******************************************************************************
proc CALIpv6DisableForwarding {switch_name} {
    set switch_vendor [ _get_switch_vendor_name $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrIpv6DisableForwarding $switch_name
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
            Netgear_log_file "cal-ntgr-ipv6.tcl" "Switch not defined"
        }
    }
}

#*******************************************************************************
#
#  Function Name  : CALIpv6Config6in4Tunnel
#
#  Description    : This function is called to config tunnel.
#
#  Usage          : CALIpv6Config6in4Tunnel <switch_name> <tunnel_id> <src_ip> <dst_ip>
#
#*******************************************************************************
proc CALIpv6Config6in4Tunnel {switch_name tunnel_id src_ip dst_ip} {
    set switch_vendor [ _get_switch_vendor_name $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrConfig6in4Tunnel $switch_name $tunnel_id $src_ip $dst_ip
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
            Netgear_log_file "cal-ntgr-ipv6.tcl" "Switch not defined"
        }
    }
}

#*******************************************************************************
#
#  Function Name  : CALIpv6Config6to4Tunnel
#
#  Description    : This function is called to config tunnel.
#
#  Usage          : CALIpv6Config6to4Tunnel <switch_name> <tunnel_id> <src_ip>
#
#*******************************************************************************
proc CALIpv6Config6to4Tunnel {switch_name tunnel_id src_ip} {
    set switch_vendor [ _get_switch_vendor_name $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrConfig6to4Tunnel $switch_name $tunnel_id $src_ip
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
            Netgear_log_file "cal-ntgr-ipv6.tcl" "Switch not defined"
        }
    }
}

#*******************************************************************************
#
#  Function Name  : CALIpv6DeleteTunnelConfig
#
#  Description    : This function is called to delete tunnel config.
#
#  Usage          : CALIpv6DeleteTunnelConfig <switch_name> <tunnel_id>
#
#*******************************************************************************
proc CALIpv6DeleteTunnelConfig {switch_name tunnel_id} {
    set switch_vendor [ _get_switch_vendor_name $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrDeleteTunnelConfig $switch_name $tunnel_id
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
            Netgear_log_file "cal-ntgr-ipv6.tcl" "Switch not defined"
        }
    }
}

#*******************************************************************************
#
#  Function Name  : CALIpv6ConfigHopLimit
#
#  Description    : This function is called to config ipv6 Hop limit[0-255].
#
#  Usage          : CALIpv6ConfigHopLimit <switch_name> <limit_num>
#
#*******************************************************************************
proc CALIpv6ConfigHopLimit {switch_name limit_num} {
    set switch_vendor [ _get_switch_vendor_name $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrIpv6ConfigHopLimit $switch_name $limit_num
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
            Netgear_log_file "cal-ntgr-ipv6.tcl" "Switch not defined"
        }
    }
}

#*******************************************************************************
#
#  Function Name  : CALIpv6RestoreDefaultHopLimit
#
#  Description    : This function is called to restore ipv6 Hop limit to default.
#
#  Usage          : CALIpv6RestoreDefaultHopLimit <switch_name> <limit_num>
#
#*******************************************************************************
proc CALIpv6RestoreDefaultHopLimit {switch_name} {
    set switch_vendor [ _get_switch_vendor_name $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrIpv6RestoreDefaultHopLimit $switch_name
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
            Netgear_log_file "cal-ntgr-ipv6.tcl" "Switch not defined"
        }
    }
}

#*******************************************************************************
#
#  Function Name  : CALIpv6EnableAutoConfigMode
#
#  Description    : This function is called to enable ipv6 network autoconfig.
#
#  Usage          : CALIpv6EnableAutoConfigMode <switch_name>
#
#*******************************************************************************
proc CALIpv6EnableAutoConfigMode {switch_name} {
    set switch_vendor [ _get_switch_vendor_name $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrIpv6EnableAutoConfigMode $switch_name
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
            Netgear_log_file "cal-ntgr-ipv6.tcl" "Switch not defined"
        }
    }
}

#*******************************************************************************
#
#  Function Name  : CALIpv6DisableAutoConfigMode
#
#  Description    : This function is called to disable ipv6 network autoconfig.
#
#  Usage          : CALIpv6DisableAutoConfigMode <switch_name>
#
#*******************************************************************************
proc CALIpv6DisableAutoConfigMode {switch_name} {
    set switch_vendor [ _get_switch_vendor_name $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrIpv6DisableAutoConfigMode $switch_name
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
            Netgear_log_file "cal-ntgr-ipv6.tcl" "Switch not defined"
        }
    }
}

#*******************************************************************************
#
#  Function Name  : CALIpv6EnableNetworkDhcp
#
#  Description    : This function is called to enable ipv6 network dhcp.
#
#  Usage          : CALIpv6EnableNetworkDhcp <switch_name>
#
#*******************************************************************************
proc CALIpv6EnableNetworkDhcp {switch_name} {
    set switch_vendor [ _get_switch_vendor_name $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrIpv6EnableNetwokDhcp $switch_name
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
            Netgear_log_file "cal-ntgr-ipv6.tcl" "Switch not defined"
        }
    }
}
#*******************************************************************************
#
#  Function Name  : CALIpv6DisableNetworkDhcp
#
#  Description    : This function is called to disable ipv6 network dhcp.
#
#  Usage          : CALIpv6DisableNetworkDhcp <switch_name>
#
#*******************************************************************************
proc CALIpv6DisableNetworkDhcp {switch_name} {
    set switch_vendor [ _get_switch_vendor_name $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrIpv6DisableNetworkDhcp $switch_name
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
            Netgear_log_file "cal-ntgr-ipv6.tcl" "Switch not defined"
        }
    }
}
#*******************************************************************************
#
#  Function Name  : CALIpv6EnableNetworkMode
#
#  Description    : This function is called to enable ipv6 network mode.
#
#  Usage          : CALIpv6EnableNetworkMode <switch_name>
#
#*******************************************************************************
proc CALIpv6EnableNetworkMode {switch_name} {
    set switch_vendor [ _get_switch_vendor_name $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrIpv6EnableNetwokMode $switch_name
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
            Netgear_log_file "cal-ntgr-ipv6.tcl" "Switch not defined"
        }
    }
}

#*******************************************************************************
#
#  Function Name  : CALIpv6DisableNetworkMode
#
#  Description    : This function is called to disable ipv6 network mode.
#
#  Usage          : CALIpv6DisableNetworkMode <switch_name>
#
#*******************************************************************************
proc CALIpv6DisableNetworkMode {switch_name} {
    set switch_vendor [ _get_switch_vendor_name $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrIpv6DisableNetwokMode $switch_name
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
            Netgear_log_file "cal-ntgr-ipv6.tcl" "Switch not defined"
        }
    }
}

#*******************************************************************************
#
#  Function Name  : CALIpv6AddStaticRoute
#
#  Description    : This function is called to add ipv6 static route.
#
#  Usage          : CALIpv6AddStaticRoute <switch_name> <prefix_len> <next_hop> <pref>
#
#*******************************************************************************
proc CALIpv6AddStaticRoute {switch_name prefix_len next_hop {pref 64}} {
    set switch_vendor [ _get_switch_vendor_name $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrIpv6AddStaticRoute $switch_name $prefix_len $next_hop $pref
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
            Netgear_log_file "cal-ntgr-ipv6.tcl" "Switch not defined"
        }
    }
}

#*******************************************************************************
#
#  Function Name  : CALIpv6DeleteStaticRoute
#
#  Description    : This function is called to delete ipv6 static route.
#
#  Usage          : CALIpv6DeleteStaticRoute <switch_name> <prefix_len>
#
#*******************************************************************************
proc CALIpv6DeleteStaticRoute {switch_name prefix_len } {
    set switch_vendor [ _get_switch_vendor_name $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrIpv6DeleteStaticRoute $switch_name $prefix_len
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
            Netgear_log_file "cal-ntgr-ipv6.tcl" "Switch not defined"
        }
    }
}

#*******************************************************************************
#
#  Function Name  : CALIpv6ConfigInterfaceMtu
#
#  Description    : This function is called to config ipv6 interface mtu size.
#
#  Usage          : CALIpv6ConfigInterfaceMtu <switch_name> <intf> <mtu_size>
#
#*******************************************************************************
proc CALIpv6ConfigInterfaceMtu {switch_name intf mtu_size} {
    set switch_vendor [ _get_switch_vendor_name $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrConfigInterfaceMtu $switch_name $intf $mtu_size
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
            Netgear_log_file "cal-ntgr-ipv6.tcl" "Switch not defined"
        }
    }
}

#*******************************************************************************
#
#  Function Name  : CALIpv6RestoreInterfaceMtu
#
#  Description    : This function is called to restore ipv6 interface mtu size to default.
#
#  Usage          : CALIpv6RestoreInterfaceMtu <switch_name> <intf> 
#
#*******************************************************************************
proc CALIpv6RestoreInterfaceMtu {switch_name intf} {
    set switch_vendor [ _get_switch_vendor_name $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrRestoreInterfaceMtu $switch_name $intf
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
            Netgear_log_file "cal-ntgr-ipv6.tcl" "Switch not defined"
        }
    }
}

#*******************************************************************************
#
#  Function Name  : CALIpv6ConfigStaticRoutePreference
#
#  Description    : This function is called to config distance value for ipv6 static route.
#
#  Usage          : CALIpv6ConfigStaticRoutePreference <switch_name> <pref_value> 
#
#*******************************************************************************
proc CALIpv6ConfigStaticRoutePreference {switch_name pref_value} {
    set switch_vendor [ _get_switch_vendor_name $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrIpv6ConfigStaticRoutePreference $switch_name $pref_value
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
            Netgear_log_file "cal-ntgr-ipv6.tcl" "Switch not defined"
        }
    }
}

#*******************************************************************************
#
#  Function Name  : CALIpv6RestoreStaticRoutePreference
#
#  Description    : This function is called to restore distance value to default for ipv6 static route.
#
#  Usage          : CALIpv6RestoreStaticRoutePreference <switch_name> 
#
#*******************************************************************************
proc CALIpv6RestoreStaticRoutePreference {switch_name} {
    set switch_vendor [ _get_switch_vendor_name $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrIpv6RestoreStaticRoutePreference $switch_name
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
            Netgear_log_file "cal-ntgr-ipv6.tcl" "Switch not defined"
        }
    }
}

#*******************************************************************************
#
#  Function Name  : CALIpv6ConfigInterfaceRoutePreferenceLevel
#
#  Description    : This function is called to config interface nd route preference.
#
#  Usage          : CALIpv6ConfigInterfaceRoutePreferenceLevel <switch_name> <intf> <pref_level>
#
#  Note           : <pref_level> must be one of them option :[high | low | medium]
#
#**********************************************************************************
proc CALIpv6ConfigInterfaceRoutePreferenceLevel {switch_name intf pref_level} {
  
    set switch_vendor [ _get_switch_vendor_name $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrIPv6ConfigInterfaceRoutePreferenceLevel $switch_name $intf $pref_level
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
            Netgear_log_file "cal-ntgr-ipv6.tcl" "Switch not defined"
        }
    }
}

#*******************************************************************************
#
#  Function Name  : CALIpv6RestoreInterfaceRoutePreferenceLevel
#
#  Description    : This function is called to restore interface nd route preference.
#
#  Usage          : CALIpv6RestoreInterfaceRoutePreferenceLevel <switch_name> <intf>
#
#  Note           : kenddy xie create
#
#**********************************************************************************
proc CALIpv6RestoreInterfaceRoutePreferenceLevel {switch_name intf} {
  
    set switch_vendor [ _get_switch_vendor_name $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrIPv6RestoreInterfaceRoutePreferenceLevel $switch_name $intf
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
            Netgear_log_file "cal-ntgr-ipv6.tcl" "Switch not defined"
        }
    }
}

#*******************************************************************************
#
#  Function Name  : CALCheckIPv6Route
#
#  Description    : This function is used to get the ipv6 route table info.
#
#  Usage          : CALCheckIPv6Route <switch_id> <protocol_type> <ipv6_adress> <next_inter> <next_address> <perference> <matric_value>
#
#  Note           : jim.xie create
#
#**********************************************************************************
proc CALCheckIPv6Route {switch_name protocol_type ipv6_adress next_inter next_address perference matric_value} {
  
    set switch_vendor [ _get_switch_vendor_name $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrCheckIPv6Route $switch_name $protocol_type $ipv6_adress $next_inter $next_address $perference $matric_value
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
            Netgear_log_file "cal-ntgr-ipv6.tcl" "Switch not defined"
        }
    }
}

#*******************************************************************************
#  Function Name	: CALIpv6InterfaceReachableEnable
#
#  Description    	: This function is called to enable unreachables mode on interface
#         
#  Usage          	: CALIpv6InterfaceReachableEnable <switch_name> <if_list>
#
#  Author           : jim.xie
#
#*******************************************************************************
proc CALIpv6InterfaceReachableEnable {switch_name if_list} {
  
    set switch_vendor [ _get_switch_vendor_name $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrIpv6InterfaceReachableEnable $switch_name $if_list
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
            Netgear_log_file "cal-ntgr-ipv6.tcl" "Switch not defined"
        }
    }
}

#*******************************************************************************
#  Function Name	: CALIpv6InterfaceReachableDisable
#
#  Description    	: This function is called to disable unreachables mode on interface
#         
#  Usage          	: CALIpv6InterfaceReachableDisable <switch_name> <if_list>
#
#  Author           : jim.xie
#
#*******************************************************************************
proc CALIpv6InterfaceReachableDisable {switch_name if_list} {
  
    set switch_vendor [ _get_switch_vendor_name $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrIpv6InterfaceReachableDisable $switch_name $if_list
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
            Netgear_log_file "cal-ntgr-ipv6.tcl" "Switch not defined"
        }
    }
}

#*******************************************************************************
#  Function Name	: CALIpv6ClearNeighborTable
#
#  Description    	: This function is called to clear neighbor table
#         
#  Usage          	: CALIpv6ClearNeighborTable <switch_name>
#
#  Author           : jim.xie
#
#*******************************************************************************
proc CALIpv6ClearNeighborTable {switch_name} {
  
    set switch_vendor [ _get_switch_vendor_name $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrIpv6ClearNeighborTable $switch_name
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
            Netgear_log_file "cal-ntgr-ipv6.tcl" "Switch not defined"
        }
    }
}

#*******************************************************************************
#
#  Function Name  : CALIpv6AddNDIntervalOnPort
#
#  Description    : This function is called to Add ipv6 ND Interval on port
#
#  Usage          : CALIpv6AddNDIntervalOnPort <switch_name> <port> <maxtime> <mintime>
#
#*******************************************************************************
proc CALIpv6AddNDRAIntervalOnPort {switch_name port maxtime mintime} {
    set switch_vendor [ _get_switch_vendor_name $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrIpv6AddNDRAIntervalOnPort $switch_name $port $maxtime $mintime
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
            Netgear_log_file "cal-ntgr-ipv6.tcl" "Switch not defined"
        }
    }
}

#*******************************************************************************
#
#  Function Name  : CALIpv6DelNDRAIntervalOnPort
#
#  Description    : This function is called to delete ipv6 nd ra-interval  on port
#
#  Usage          : CALIpv6DelNDRAIntervalOnPort <switch_name> <port>
#
#*******************************************************************************
proc CALIpv6DelNDRAIntervalOnPort {switch_name port} {
    set switch_vendor [ _get_switch_vendor_name $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrIpv6DelNDRAIntervalOnPort $switch_name $port
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
            Netgear_log_file "cal-ntgr-ipv6.tcl" "Switch not defined"
        }
    }
}

#*******************************************************************************
#
#  Function Name  : CALIpv6GetRouteSize
#
#  Description    : This function is called to get ipv6 static route table size.
#
#  Usage          : CALIpv6GetRouteSize <switch_name> <prefix> 
#
#*******************************************************************************
proc CALIpv6GetRouteSize {switch_name {prefix ""}} {
    set switch_vendor [ _get_switch_vendor_name $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrIpv6GetRoutesNum $switch_name $prefix
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
            Netgear_log_file "cal-ntgr-ipv6.tcl" "Switch not defined"
        }
    }
}
