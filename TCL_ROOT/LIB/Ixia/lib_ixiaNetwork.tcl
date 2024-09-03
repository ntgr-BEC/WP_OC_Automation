#!/bin/sh
################################################################################   
#
#  File Name  : lib_ixiaNetwork.tcl
#
#  Description       :
#         This TCL contains utility functions to used by IxNetwork
#         IxNetwork version: 8.01.1029.6
#  Revision History  :
#         Date         Programmer        Description
#        -----------------------------------------------------------------------
#        2016-08-03    Jim       Created
#
################################################################################
catch {unset ixnHLT}
array set ixnHLT {}

#*******************************************************************************
#
#  Function Name  : ixnHLT_connectedIxNetwork
#
#  Description    : This function is called to connect IxNetwork
#
#  Usage          : ixnHLT_connectedIxNetwork <chassis_id> <port_input>
#
#*******************************************************************************
proc ixnHLT_connectedIxNetwork {chassis_id port_input} {
    global ixnHLT
    set chassis_ip_addr [_ntgrGetTGIPAddr $chassis_id]
    set port_L {}
    set vp_L {}
    set path_L {}
    foreach port_local $port_input {
        set chas [_ntgrGetTrafficPortChassisId $chassis_id $port_local]
        set card [_ntgrGetTrafficPortSlotId $chassis_id $port_local]
        set port [_ntgrGetTrafficPortId $chassis_id $port_local]
        lappend port_L "$card/$port"
        lappend path_L "//vport:<$port_local>"
        set vp_tmp "$card/$port--$port_local"
        set vp_tmp [list $vp_tmp]
        lappend vp_L $vp_tmp
    }
    set port_L [list $port_L]
    set vp_L [list $vp_L]
    set path_L [list $path_L]
    
    package require registry
    # start localhost ixnetwork tcl server
    set IxNet_versionKey    "HKEY_LOCAL_MACHINE\\SOFTWARE\\Ixia Communications\\IxNetwork\\InstallInfo"
    set IxNet_version [ registry keys $IxNet_versionKey ]
    set IxNet_productKey    "HKEY_LOCAL_MACHINE\\SOFTWARE\\Ixia Communications\\IxNetwork"
    set IxNet_installInfo    [ append IxNet_productKey \\ $IxNet_version \\ InstallInfo ]
    set IxNetwork_CMD "[ registry get $IxNet_installInfo  HomeDir ]/IxNetwork.exe"
    if {[catch {exec netstat -an | findstr "8009"} tcl_server_flag]} {
        exec $IxNetwork_CMD -tclPort 8009 -restPort 11009 -restOnAllInterfaces &
        after 60000
        for {set i 1} {$i < 10} {incr i} {
            if {[catch {exec netstat -an | findstr "8009"} tcl_server_flag]} {
                after 60000
            } else {
                Netgear_log_file "lib_ixiaNetwork.tcl" "LocalHost IxNetwork tcl server start finished."
                break
            }
            if {$i == 9} {
                Netgear_log_file "lib_ixiaNetwork.tcl" "ERROR: LocalHost IxNetwork tcl server start failed after 10 min." "YES"
                error "LocalHost IxNetwork tcl server start failed."
            }
        }
    } else {
        Netgear_log_file "lib_ixiaNetwork.tcl" "LocalHost IxNetwork tcl server already running, so no need to start."
    }
    set IxHLT_productKey    "HKEY_LOCAL_MACHINE\\SOFTWARE\\Ixia Communications\\HLTAPI"
    set IxHLT_versionKey     [ registry keys $IxHLT_productKey ]
    set IxHLT_latestKey      [ lindex $IxHLT_versionKey end ]
    set IxHLT_installInfo    [ append IxHLT_productKey \\ $IxHLT_latestKey \\ InstallInfo ]    
    set hlt_init "[ registry get $IxHLT_installInfo  HOMEDIR ]/TclScripts/bin/hlt_init.tcl"
    #source "C:/Program Files/Ixia/hltapi/8.01.125.2/TclScripts/bin/hlt_init.tcl"
    catch {source $hlt_init}
    # ixia hlt package
    package require Ixia
    
    # ----------------------------------------------------------------
    #  chassis, card, port configuration
    # 
    #  port_list needs to match up with path_list below
    # 
    set chassis [list $chassis_ip_addr]
    set tcl_server $chassis_ip_addr
    set port_list $port_L
    set vport_name_list $vp_L
    set guard_rail statistics
    # 
    #  this should match up w/ your port_list above
    # 
    set ixnHLT(path_list) $path_L
    # 
    # 
    set _result_ [::ixia::connect  \
        -reset 1 \
        -device $chassis \
        -port_list $port_list \
        -ixnetwork_tcl_server localhost \
        -tcl_server $tcl_server \
        -guard_rail $guard_rail \
        -return_detailed_handles 0 \
    ]
    
    # Check status
    if {[keylget _result_ status] != $::SUCCESS} {
        Netgear_log_file "lib_ixiaNetwork.tcl" "Connect IxNetwork Failed. Deatail Info:$_result_." "YES"
        #$::ixnHLT_errorHandler [info script] $_result_
    }
    foreach {port_list_elem} $port_list         \
        {name_list_elem} $vport_name_list   \
        {path_list_elem} $ixnHLT(path_list) \
        {chassis_elem}   $chassis {

        set ch_vport_list [list]
        foreach {port} $port_list_elem {path} $path_list_elem {
            if {[catch {keylget _result_ port_handle.$chassis_elem.$port} _port_handle]} {
                Netgear_log_file "lib_ixiaNetwork.tcl" "Connect IxNetwork Failed. Deatail Info:$_result_." "YES"
                error "connection status: $_result_: $_port_handle"
            }
            set ixnHLT(PORT-HANDLE,$path) $_port_handle
            lappend ch_vport_list $_port_handle
        }

        set vpinfo_rval [::ixia::vport_info     \
            -mode set_info                      \
            -port_list $ch_vport_list           \
            -port_name_list $name_list_elem     \
        ]
        if {[keylget vpinfo_rval status] != $::SUCCESS} {
            Netgear_log_file "lib_ixiaNetwork.tcl" "Connect IxNetwork Failed. Deatail Info:$vpinfo_rval." "YES"
            #$::ixnHLT_errorHandler [info script] $vpinfo_rval
        }
    }

    # 
    if {[llength [info commands obj_config_placeholder]]==0} {
        proc obj_config_placeholder {args} {}
    }
}


#*******************************************************************************
#
#  Description    : This functions was generate by IxNetwork
#
#*******************************************************************************
proc ixnHLT_connectedPathList {ixnHLT_N} {
    upvar $ixnHLT_N ixnHLT
    set path_list [ixnHLT_vportPathList ixnHLT "connected"]
    # consider the topology connected
    # if at least one of the vports for the topology is connected 
    set connected_vpaths $path_list
    foreach {t v} [ixnHLT_collectTopologyVports ixnHLT] {
        foreach v_item $v {
            if {[lsearch $connected_vpaths $v_item] != -1} {
                if {[lsearch $path_list $t] == -1} {lappend path_list $t}
            }
        }
    }
    return $path_list
}

#*******************************************************************************
#
#  Description    : This functions was generate by IxNetwork
#
#*******************************************************************************
proc ixnHLT_vportPathList {ixnHLT_N {type all}} {
    upvar $ixnHLT_N ixnHLT
    set path_list {}
    set source_data {}
    if {[info exists ixnHLT(path_list)]} { set source_data $ixnHLT(path_list) }
    foreach path_elem_0 $source_data {
        foreach path_elem_1 $path_elem_0 {
            if {$type == "all"} {
                lappend path_list $path_elem_1
            } elseif {$type == "connected"} {
                if {![info exists ixnHLT(unconnected_path_list)]} {
                    lappend path_list $path_elem_1
                } elseif {[info exists ixnHLT(unconnected_path_list)] &&  [lsearch $ixnHLT(unconnected_path_list) $path_elem_1] == -1} {
                    lappend path_list $path_elem_1
                }
            }
        }
    }
    return $path_list
}

#*******************************************************************************
#
#  Description    : This functions was generate by IxNetwork
#
#*******************************************************************************
proc ixnHLT_collectTopologyVports {ixnHLT_N} {
    upvar $ixnHLT_N ixnHLT
    # clear out exists topology-vports.* elements
    array set rval {}
    set r [ixNet getRoot]
    set failed [catch {
        set t_list [ixNet getList $r topology]
    }] 
    if {$failed} {
        # no topology present in this configuration
        return {}
    }
    foreach t $t_list {
        # convert to ixnet style path from internal format path
        # - remove the ::ixNet::OBJ- cruft
        # - convert :NN indexes to :<NN> indexes
        # - use leading double slash instead of single slash
        # eg .. convert ::ixNet::OBJ-/topology:1 to //topology:<1>
        set p_list [ixNet getList $t port]
        foreach p $p_list {
            set a [ixNet getAttribute $p -vport]
            set aa //[join [lrange [split $a "/"] 1 end] "/"]
            set tt //[join [lrange [split $t "/"] 1 end] "/"]
            set tt [regsub -all {:([0-9]+)} $tt {:<\1>}]
            set aa [regsub -all {:([0-9]+)} $aa {:<\1>}]
            lappend rval($tt) $aa
        }
    }
    array get rval
}

#*******************************************************************************
#
#  Description    : This functions was generate by IxNetwork
#
#*******************************************************************************
proc ixnHLT_endpointMatch {ixnHLT_N ixnpattern_list {handle_type "HANDLE"}} {
    upvar $ixnHLT_N ixnHLT
    
    set traffic_ep_ignore_list {
        {^::ixNet::OBJ-/vport:\d+/protocols/mld/host:\d+$}
        {^::ixNet::OBJ-/vport:\d+/protocolStack/ethernet:[^/]+/ipEndpoint:[^/]+/range:[^/]+/ptpRangeOverIp:1$}
    }
    
    set rval [list]
    foreach {cpat} $ixnpattern_list {
        set pat $cpat
        if {[string range $pat 0 0] != "^"}     { set pat "^$pat" }
        if {[string range $pat end end] != "$"} { set pat "$pat\$" }
        foreach {n} [lsort -dictionary [array names ixnHLT "$handle_type,*"]] {
            # note there could be stuff after the path so use 1, not end
            set ixn_path        [lindex [split $n ","] 1]
            set parent_ixn_path [join [lrange  [split $ixn_path /] 0 end-1] /]
            if {[info exists ixnHLT($handle_type,$parent_ixn_path)] && [llength $rval] > 0} {
                set rval_list $rval
                if {[llength $rval] == 1} {
                    set rval_list [list $rval]
                }
                set parent_ixn_index_list [lsearch -regexp -all $rval_list \
                    "^[set ixnHLT($handle_type,$parent_ixn_path)]\$"]
            } else {
                set parent_ixn_index_list ""
            }
            if {[llength $parent_ixn_index_list] == 0 && \
                [regexp $pat $ixn_path] && [string length $ixnHLT($n)] > 0 \
            } {
                if {![regexp $traffic_ep_ignore_list $ixnHLT($n)]} {
                    lappend rval $ixnHLT($n)
                }
            }
        }
    }
    return $rval
}

#*******************************************************************************
#
#  Function Name  : ixnHLT_LoadPort
#
#  Description    : This function is called to load port
#
#  Usage          : ixnHLT_LoadPort <chassis_id> <port_list>
#
#*******************************************************************************
proc ixnHLT_LoadPort {chassis_id port_list} {
    # add by jim.xie 
    # when load this function the port can't link up
    return 1
    #upvar 1 $ixnHLTVarName ixnHLT
    global ixnHLT
    foreach port_local $port_list {
        set chas [_ntgrGetTrafficPortChassisId $chassis_id $port_local]
        set card [_ntgrGetTrafficPortSlotId $chassis_id $port_local]
        set port [_ntgrGetTrafficPortId $chassis_id $port_local]
        set speed [_ntgrGetTrafficPortSpeed $chassis_id $port_local]
        switch -exact -- $speed {
        10GLAN  {
                     set port_speed "ether10000lan"
                }
        10GWAN  {
                     set port_speed "ether10000lan"
                }
        default {
                     set port_speed "ether100"
                }
        }
        set pL [list [list $chas $card $port]]
        Netgear_log_file "lib_ixiaNetwork.tcl" "Start using <interface_config> configure vport:<$port_local>: $pL ..."
        set _result_ [::ixia::interface_config  \
            -mode config \
            -port_handle $ixnHLT(PORT-HANDLE,//vport:<$port_local>) \
            -transmit_clock_source external \
            -transmit_mode advanced \
            -tx_gap_control_mode average \
            -internal_ppm_adjust 0 \
            -additional_fcoe_stat_2 fcoe_invalid_frames \
            -ignore_link 0 \
            -enable_data_center_shared_stats 0 \
            -additional_fcoe_stat_1 fcoe_invalid_delimiter \
            -data_integrity 1 \
            -arp_refresh_interval 60 \
            -intf_mode ethernet \
            -speed $port_speed \
            -auto_detect_instrumentation_type floating \
        ]
        # Check status
        if {[keylget _result_ status] != $::SUCCESS} {
            Netgear_log_file "lib_ixiaNetwork.tcl" "Configure vport:<$port_local>: $p1 Failed. Deatail Info:$_result_." "YES"
           # $::ixnHLT_errorHandler [info script] $_result_
        }

        catch { 
            set ixnHLT(HANDLE,//vport:<$port_local>) [keylget _result_ interface_handle] 
            lappend ixnHLT(VPORT-CONFIG-HANDLES,//vport:<$port_local>,interface_config) \
            $ixnHLT(HANDLE,//vport:<$port_local>)
        }
        Netgear_log_file "lib_ixiaNetwork.tcl" "Using <interface_config> configure vport:<$port_local>: $pL finished."
  }
}

#*******************************************************************************
#
#  Function Name  : ixnHLT_configurePort
#
#  Description    : This function is called to configure port
#
#  Usage          : ixnHLT_configurePort <chassis_id> <port_list>
#
#*******************************************************************************
proc ixnHLT_configurePort {chassis_id port_local interface_info} {
    global ixnHLT
    set num_sub [llength $interface_info]
    for {set i 0} {$i < $num_sub} {incr i} { 
        set sub_int [lindex $interface_info $i]
        set vlan_id [lindex $sub_int 0]
        if {$vlan_id == 0} {
            set vlan_flag 0
        } else {
            set vlan_flag 1
        }
        set ixia_ip [lindex $sub_int 1]
        set gw_ip [lindex $sub_int 2]
        set mac_address [lindex $sub_int 3]
        set description [lindex $sub_int 4]           
        Netgear_log_file "lib_ixiaNetwork.tcl" "Start configure vport:<$port_local>/interface:<[expr $i+1]> ..."
        Netgear_log_file "lib_ixiaNetwork.tcl" "mac_address=$mac_address"
        #$::ixnHLT_log interface_config://vport:<1>/interface:<1>...
        set _result_ [::ixia::interface_config  \
            -mode modify \
            -port_handle $ixnHLT(PORT-HANDLE,//vport:<$port_local>) \
            -gateway $gw_ip \
            -intf_ip_addr $ixia_ip \
            -netmask 255.255.255.0 \
            -check_opposite_ip_version 0 \
            -src_mac_addr $mac_address \
            -arp_on_linkup 1 \
            -ns_on_linkup 1 \
            -single_arp_per_gateway 1 \
            -single_ns_per_gateway 1 \
            -mtu 1500 \
            -vlan $vlan_flag \
            -vlan_id $vlan_id \
            -vlan_user_priority 0 \
            -vlan_tpid 0x8100 \
            -l23_config_type protocol_interface \
        ]
        # Check status
        if {[keylget _result_ status] != $::SUCCESS} {
            Netgear_log_file "lib_ixiaNetwork.tcl" "Configure vport:<$port_local>/interface:<[expr $i+1]> Failed. Deatail Info:$_result_." "YES"
            #$::ixnHLT_errorHandler [info script] $_result_
        }
        # The last configure command did not scriptgen the following attributes:
        # [//vport:<1>/interface:<1>]
        # n kString -description {1/6/1 - 00 00 01 8d 5d b1 - 1}
        # n kBlob -eui64Id {02 00 01 FF FE 8D 5D B1 }
        # n kInteger -mtu 1500

        catch { 
            set ixnHLT(HANDLE,//vport:<$port_local>/interface:<[expr $i+1]>) [keylget _result_ interface_handle] 
            lappend ixnHLT(VPORT-CONFIG-HANDLES,//vport:<$port_local>,interface_config) \
            $ixnHLT(HANDLE,//vport:<$port_local>/interface:<[expr $i+1]>)
        }
        Netgear_log_file "lib_ixiaNetwork.tcl" "Configure vport:<$port_local>/interface:<[expr $i+1]>  Finished."
        #$::ixnHLT_log {COMPLETED: interface_config}
    }
}


#*******************************************************************************
#
#  Function Name  : ixnHLT_configureOspf
#
#  Description    : This function is called to configure ospf
#
#  Usage          : ixnHLT_configureOspf <chassis_id> <port_list>
#
#*******************************************************************************
proc ixnHLT_configureOspf {chassis_id port_local router_ospf_list interface_info} {
    global ixnHLT
    set num_ospf [llength $router_ospf_list]
    for {set i 0} {$i < $num_ospf} {incr i} {
        set ospf_int [lindex $router_ospf_list $i]
        set router_id [_ntgrGetOSPFRouterID $ospf_int]
        set area_id [_ntgrGetOSPFAreaID $ospf_int]
        set network_type [string tolower [_ntgrGetOSPFInterfaceNetworkType $ospf_int]]
        set routeRanges_list [_ntgrGetOSPFRouteRange $ospf_int]
        
        set sub_int [lindex $interface_info $i]
        set vlan_id [lindex $sub_int 0]
        set ixia_ip [lindex $sub_int 1]
        set gw_ip [lindex $sub_int 2]
        set mac_address [lindex $sub_int 3]
        set prefix_length [lindex $sub_int 5]
        if {$prefix_length == ""} {
            set prefix_length "24"
        }
        # //vport/protocols/ospf/router
        Netgear_log_file "lib_ixiaNetwork.tcl" "Start configure ospf://vport:<$port_local>/protocols/ospf/router:<[expr $i+1]>..."
        #$::ixnHLT_log emulation_ospf_config://vport:<1>/protocols/ospf/router:<1>...
        set _result_ [::ixia::emulation_ospf_config  \
            -mode create \
            -port_handle $ixnHLT(PORT-HANDLE,//vport:<$port_local>) \
            -lsa_discard_mode 1 \
            -router_id $router_id \
            -graceful_restart_enable 0 \
            -session_type ospfv2 \
            -area_id $area_id \
            -area_type external-capable \
            -dead_interval 40 \
            -hello_interval 10 \
            -interface_cost 10 \
            -authentication_mode null \
            -mtu 1500 \
            -neighbor_router_id 0.0.0.0 \
            -network_type $network_type \
            -option_bits 2 \
            -router_priority 2 \
            -te_enable 0 \
            -bfd_registration 0 \
            -intf_ip_addr $ixia_ip \
            -intf_prefix_length $prefix_length \
            -neighbor_intf_ip_addr $gw_ip \
            -vlan 1 \
            -vlan_id $vlan_id \
            -vlan_user_priority 0 \
            -mac_address_init $mac_address \
        ]
        # Check status
        if {[keylget _result_ status] != $::SUCCESS} {
            Netgear_log_file "lib_ixiaNetwork.tcl" "Configure ospf://vport:<$port_local>/protocols/ospf/router:<[expr $i+1]> Failed. Detaill Info:$_result_ ." "YES"
            #$::ixnHLT_errorHandler [info script] $_result_
        }
    
        foreach _handle_ [keylget _result_ handle] {
            set ixnHLT(HANDLE,//vport:<$port_local>/protocols/ospf/router:<[expr $i+1]>) $_handle_
            lappend ixnHLT(VPORT-CONFIG-HANDLES,//vport:<$port_local>,emulation_ospf_config) \
                $ixnHLT(HANDLE,//vport:<$port_local>/protocols/ospf/router:<[expr $i+1]>)
        }
        #$::ixnHLT_log {COMPLETED: emulation_ospf_config}
        Netgear_log_file "lib_ixiaNetwork.tcl" "Configure ospf://vport:<$port_local>/protocols/ospf/router:<[expr $i+1]> Finished."
        
        # //vport/protocols/ospf/router/routeRange
        set num_ospf_range [llength $routeRanges_list]
        for {set j 0} {$j < $num_ospf_range} {incr j} {
            Netgear_log_file "lib_ixiaNetwork.tcl" "Start configure ospf route range://vport:<$port_local>/protocols/ospf/router:<[expr $i+1]>/routeRange:<[expr $j+1]>..."
            #$::ixnHLT_log emulation_ospf_topology_route_config://vport:<1>/protocols/ospf/router:<1>/routeRange:<1>...
            set routerange [lindex $routeRanges_list $j]
            set first_ip [_ntgrGetOSPFFirstRouteINRouteRange $routerange]
            set route_count [_ntgrGetOSPFRouteCount $routerange]
            set route_metric [_ntgrGetOSPFRouteMetric $routerange]
            set route_origin [_ntgrGetOSPFRouteOrigin $routerange]
            set route_prefix [_ntgrGetOSPFRouteMask $routerange]
            
            if {$route_origin == "default"} {
                set _result_ [::ixia::emulation_ospf_topology_route_config  \
                    -mode create \
                    -handle $ixnHLT(HANDLE,//vport:<$port_local>/protocols/ospf/router:<[expr $i+1]>) \
                    -count 1 \
                    -summary_prefix_length $route_prefix \
                    -summary_prefix_metric $route_metric \
                    -summary_prefix_start $first_ip \
                    -summary_number_of_prefix $route_count \
                    -type summary_routes \
                    -summary_route_type another_area \
                ]
            } elseif {$route_origin == "external 2"} {
                set _result_ [::ixia::emulation_ospf_topology_route_config  \
                    -mode create \
                    -handle $ixnHLT(HANDLE,//vport:<$port_local>/protocols/ospf/router:<[expr $i+1]>) \
                    -count 1 \
                    -external_prefix_length $route_prefix \
                    -external_prefix_metric $route_metric \
                    -external_prefix_start $first_ip \
                    -external_number_of_prefix $route_count \
                    -type ext_routes \
                    -external_prefix_type 2 \
                ]
            } elseif {$route_origin == "external 1"} {
                set _result_ [::ixia::emulation_ospf_topology_route_config  \
                    -mode create \
                    -handle $ixnHLT(HANDLE,//vport:<$port_local>/protocols/ospf/router:<[expr $i+1]>) \
                    -count 1 \
                    -external_prefix_length $route_prefix \
                    -external_prefix_metric $route_metric \
                    -external_prefix_start $first_ip \
                    -external_number_of_prefix $route_count \
                    -type ext_routes \
                    -external_prefix_type 1 \
                ]
            }
            # Check status
            if {[keylget _result_ status] != $::SUCCESS} {
                Netgear_log_file "lib_ixiaNetwork.tcl" "Configure ospf route range://vport:<$port_local>/protocols/ospf/router:<[expr $i+1]>/routeRange:<[expr $j+1]> failed. Detail info: $_result_." "YES"
                #$::ixnHLT_errorHandler [info script] $_result_
            }
            # The last configure command did not scriptgen the following attributes:
            # [//vport:<1>/protocols/ospf/router:<1>/routeRange:<1>]
            # n kBool -propagate False
            Netgear_log_file "lib_ixiaNetwork.tcl" "Configure ospf route range://vport:<$port_local>/protocols/ospf/router:<[expr $i+1]>/routeRange:<[expr $j+1]> Finished."
            #$::ixnHLT_log {COMPLETED: emulation_ospf_topology_route_config}
        }
    }
}

#*******************************************************************************
#
#  Function Name  : ixnHLT_configureIpv6Port
#
#  Description    : This function is called to configure port ipv6 address
#
#  Usage          : ixnHLT_configureIpv6Port <chassis_id> <port_local><interface_info>
#
#*******************************************************************************
proc ixnHLT_configureIpv6Port {chassis_id port_local interface_info} {
    global ixnHLT
    set num_sub [llength $interface_info]
    if {$num_sub == 0} { 
        Netgear_log_file "lib_ixiaNetwork.tcl" "Start configure ipv6 interface vport:<$port_local>/interface:<1> ..."
        #$::ixnHLT_log interface_config://vport:<1>/interface:<1>...
        set _result_ [::ixia::interface_config  \
            -mode modify \
            -port_handle $ixnHLT(PORT-HANDLE,//vport:<$port_local>) \
            -ipv6_intf_addr 0:0:0:0:0:0:0:0 \
            -ipv6_prefix_length 64 \
            -ipv6_gateway 0:0:0:0:0:0:0:0 \
            -target_link_layer_address 0 \
            -check_opposite_ip_version 0 \
            -src_mac_addr 0000.0518.b17d \
            -arp_on_linkup 1 \
            -ns_on_linkup 1 \
            -single_arp_per_gateway 1 \
            -single_ns_per_gateway 1 \
            -mtu 1500 \
            -vlan 0 \
            -l23_config_type protocol_interface \
        ]
        # Check status
        if {[keylget _result_ status] != $::SUCCESS} {
            Netgear_log_file "lib_ixiaNetwork.tcl" "Configure ipv6 interface vport:<$port_local>/interface:<1> Failed. Deatail Info:$_result_." "YES"
            #$::ixnHLT_errorHandler [info script] $_result_
        }
        # The last configure command did not scriptgen the following attributes:
        # [//vport:<1>/interface:<1>]
        # n kString -description {1/6/1 - 00 00 01 8d 5d b1 - 1}
        # n kBlob -eui64Id {02 00 01 FF FE 8D 5D B1 }
        # n kInteger -mtu 1500

        catch { 
            set ixnHLT(HANDLE,//vport:<$port_local>/interface:<1>) [keylget _result_ interface_handle] 
            lappend ixnHLT(VPORT-CONFIG-HANDLES,//vport:<$port_local>,interface_config) \
            $ixnHLT(HANDLE,//vport:<$port_local>/interface:<1>)
        }
        Netgear_log_file "lib_ixiaNetwork.tcl" "Configure ipv6 interface vport:<$port_local>/interface:<1>  Finished."
        #$::ixnHLT_log {COMPLETED: interface_config}
    }
}

#*******************************************************************************
#
#  Function Name  : ixnHLT_configureMLDGROUP
#
#  Description    : This function is called to configure mld
#
#  Usage          : ixnHLT_configureMLDGROUP <chassis_id> <port_local><mld_route_list>
#
#*******************************************************************************
proc ixnHLT_configureMLDGROUP {chassis_id port_local mld_route_list} {
    global ixnHLT
    
    set mld_group_list [lindex $mld_route_list 0]
    set group_count [_ntgrGetMLDGROUPCOUNT "$mld_group_list"]
    set add_start [_ntgrGetMLDADDRSTART "$mld_group_list"]
    set add_step  [_ntgrGetMLDADDRSTEP "$mld_group_list"]
    
    Netgear_log_file "lib_ixiaNetwork.tcl" "Start configure emulation_mld_config://vport:<$port_local>/protocols/mld/host:<1>..."
    # //vport/protocols/mld/host
    set _result_ [::ixia::emulation_mld_config  \
        -mode create \
        -port_handle $ixnHLT(PORT-HANDLE,//vport:<$port_local>) \
        -count 1 \
        -robustness 1 \
        -interface_handle $ixnHLT(HANDLE,//vport:<$port_local>/interface:<1>) \
        -suppress_report 0 \
        -group_query 1 \
        -ip_router_alert 1 \
        -general_query 1 \
        -max_response_control 0 \
        -max_response_time 0 \
        -mld_version v2 \
        -msg_interval 0 \
        -msg_count_per_interval 0 \
        -mldv2_report_type 143 \
    ]
    if {[keylget _result_ status] != $::SUCCESS} {
        Netgear_log_file "lib_ixiaNetwork.tcl" "Configure vport:<$port_local>/interface:<1> Failed. Deatail Info:$_result_." "YES"
        #$::ixnHLT_errorHandler [info script] $_result_
    }
    # The last configure command did not scriptgen the following attributes:
    # [//vport:<1>/protocols/mld/host:<1>]
    # n kInteger -reportFreq 120
    # n kObjref -trafficGroupId ::ixNet::OBJ-null
    
    foreach _handle_ [keylget _result_ handle] {
        set ixnHLT(HANDLE,//vport:<$port_local>/protocols/mld/host:<1>) $_handle_
        lappend ixnHLT(VPORT-CONFIG-HANDLES,//vport:<$port_local>,emulation_mld_config) \
            $ixnHLT(HANDLE,//vport:<$port_local>/protocols/mld/host:<1>)
    }
    Netgear_log_file "lib_ixiaNetwork.tcl" "Configure emulation_mld_config://vport:<$port_local>/protocols/mld/host:<1> finish."
    #$::ixnHLT_log {COMPLETED: emulation_mld_config}
    
    # //vport/protocols/mld/host/groupRange
    Netgear_log_file "lib_ixiaNetwork.tcl" "Start configure emulation_multicast_group_config://vport:<$port_local>/protocols/mld/host:<1>/groupRange:<1>..."
    #$::ixnHLT_log emulation_multicast_group_config://vport:<1>/protocols/mld/host:<1>/groupRange:<1>...
    set _result_ [::ixia::emulation_multicast_group_config  \
        -mode create \
        -num_groups $group_count \
        -ip_addr_start $add_start \
        -ip_addr_step $add_step \
    ]
    Netgear_log_file "lib_ixiaNetwork.tcl" "Configure Group Count:$group_count , IP Address Start:$add_start , IP Address Step:$add_step ."
    # Check status
    if {[keylget _result_ status] != $::SUCCESS} {
        Netgear_log_file "lib_ixiaNetwork.tcl" "Configure vport:<$port_local>/interface:<1> Failed. Deatail Info:$_result_." "YES"
        #$::ixnHLT_errorHandler [info script] $_result_
    }
    # The last configure command did not scriptgen the following attributes:
    # [//vport:<1>/protocols/mld/host:<1>/groupRange:<1>]
    # n kBool -enablePacking False
    # n kBool -enableUpdateRequired False
    # n kInteger -recordsPerFrame 0
    # n kEnumValue -sourceMode exclude
    # n kInteger -sourcesPerRecord 0
    
    foreach _handle_ [keylget _result_ handle] {
        set ixnHLT(MULTICAST-HANDLE-GROUP,//vport:<$port_local>/protocols/mld/host:<1>/groupRange:<1>) $_handle_
        lappend ixnHLT(VPORT-CONFIG-HANDLES,//vport:<$port_local>,emulation_multicast_group_config) \
            $ixnHLT(MULTICAST-HANDLE-GROUP,//vport:<$port_local>/protocols/mld/host:<1>/groupRange:<1>)
    }
    Netgear_log_file "lib_ixiaNetwork.tcl" "Configure emulation_multicast_group_config://vport:<$port_local>/protocols/mld/host:<1>/groupRange:<1> finish."
    #$::ixnHLT_log {COMPLETED: emulation_multicast_group_config}
    
    # //vport/protocols/mld/host/groupRange
    #$::ixnHLT_log emulation_mld_group_config://vport:<1>/protocols/mld/host:<1>/groupRange:<1>...
    set _result_ [::ixia::emulation_mld_group_config  \
        -mode create \
        -session_handle $ixnHLT(HANDLE,//vport:<$port_local>/protocols/mld/host:<1>) \
        -g_enable_packing 0 \
        -g_filter_mode exclude \
        -g_max_groups_per_pkts 0 \
        -g_max_sources_per_group 0 \
        -group_pool_handle $ixnHLT(MULTICAST-HANDLE-GROUP,//vport:<$port_local>/protocols/mld/host:<1>/groupRange:<1>) \
    ]
    # Check status
    if {[keylget _result_ status] != $::SUCCESS} {
        Netgear_log_file "lib_ixiaNetwork.tcl" "Configure emulation_mld_group_config vport:<$port_local>/interface:<1> Failed. Deatail Info:$_result_." "YES"
        #$::ixnHLT_errorHandler [info script] $_result_
    }

}

#*******************************************************************************
#
#  Function Name  : ixnHLT_RunTest
#
#  Description    : This function is called to run IxNetwork
#
#  Usage          : ixnHLT_RunTest <chassis_id> 
#
#*******************************************************************************
proc ixnHLT_RunTest {chassis_id } {
    global ixnHLT
    # #######################
    # start phase of the test
    # #######################
    Netgear_log_file "lib_ixiaNetwork.tcl" "Waiting 60 seconds before starting protocol(s) ..."
    #$::ixnHLT_log {Waiting 60 seconds before starting protocol(s) ...}
    after 60000
    
    Netgear_log_file "lib_ixiaNetwork.tcl" "Starting all protocol(s) ..."
    #$::ixnHLT_log {Starting all protocol(s) ...}
    set r [::ixia::test_control -action start_all_protocols]
    if {[keylget r status] != $::SUCCESS} {
        Netgear_log_file "lib_ixiaNetwork.tcl" "Starting all protocol(s) failed. Detail Info: $r." "YES"
        #$::ixnHLT_errorHandler [info script] $r
    }
    #@ MARKER: hlapi_framework:HLApiStatsConfig
    Netgear_log_file "lib_ixiaNetwork.tcl" "Starting all protocol(s) Finished."
}


#*******************************************************************************
#
#  Function Name  : ixnHLT_StopTest
#
#  Description    : This function is called to stop IxNetwork
#
#  Usage          : ixnHLT_StopTest <chassis_id> 
#
#*******************************************************************************
proc ixnHLT_StopTest {chassis_id } {
    global ixnHLT
    
    # ######################
    # stop phase of the test
    # ######################
    Netgear_log_file "lib_ixiaNetwork.tcl" "Stopping all protocol(s) ..."
    #$::ixnHLT_log {Stopping all protocol(s) ...}
    set r [::ixia::test_control -action stop_all_protocols]
    if {[keylget r status] != $::SUCCESS} {
        #$::ixnHLT_errorHandler [info script] $r
        Netgear_log_file "lib_ixiaNetwork.tcl" "Stopping all protocol(s) failed. Detail Info: $r." "YES"
    }
    Netgear_log_file "lib_ixiaNetwork.tcl" "Stopping all protocol(s) Finished."
}

#*******************************************************************************
#
#  Function Name  : Start_IxNetwork
#
#  Description    : This function is called to connect IxNetwork
#
#  Usage          : Start_IxNetwork
#
#*******************************************************************************
proc Start_IxNetwork {} {
    global ixnHLT
    package require registry
    # start localhost ixnetwork tcl server
    set IxNet_versionKey    "HKEY_LOCAL_MACHINE\\SOFTWARE\\Ixia Communications\\IxNetwork\\InstallInfo"
    set IxNet_version [ registry keys $IxNet_versionKey ]
    set IxNet_productKey    "HKEY_LOCAL_MACHINE\\SOFTWARE\\Ixia Communications\\IxNetwork"
    set IxNet_installInfo    [ append IxNet_productKey \\ $IxNet_version \\ InstallInfo ]
    set IxNetwork_CMD "[ registry get $IxNet_installInfo  HomeDir ]/IxNetwork.exe"
    if {[catch {exec netstat -an | findstr "8009"} tcl_server_flag]} {
        exec $IxNetwork_CMD -tclPort 8009 -restPort 11009 -restOnAllInterfaces &
        after 60000
        for {set i 1} {$i < 10} {incr i} {
            if {[catch {exec netstat -an | findstr "8009"} tcl_server_flag]} {
                after 60000
            } else {
                Netgear_log_file "lib_ixiaNetwork.tcl" "LocalHost IxNetwork tcl server start finished."
                break
            }
            if {$i == 9} {
                Netgear_log_file "lib_ixiaNetwork.tcl" "ERROR: LocalHost IxNetwork tcl server start failed after 10 min." "YES"
                error "LocalHost IxNetwork tcl server start failed."
            }
        }
    } else {
        Netgear_log_file "lib_ixiaNetwork.tcl" "LocalHost IxNetwork tcl server already running, so no need to start."
    }
    set IxHLT_productKey    "HKEY_LOCAL_MACHINE\\SOFTWARE\\Ixia Communications\\HLTAPI"
    set IxHLT_versionKey     [ registry keys $IxHLT_productKey ]
    set IxHLT_latestKey      [ lindex $IxHLT_versionKey end ]
    set IxHLT_installInfo    [ append IxHLT_productKey \\ $IxHLT_latestKey \\ InstallInfo ]    
    set hlt_init "[ registry get $IxHLT_installInfo  HOMEDIR ]/TclScripts/bin/hlt_init.tcl"
    #source "C:/Program Files/Ixia/hltapi/8.01.125.2/TclScripts/bin/hlt_init.tcl"
    catch {source $hlt_init}
    # ixia hlt package
    package require Ixia
    
}

#*******************************************************************************
#
#  Function Name  : ixnHLT_ReleasePort
#
#  Description    : This function is called to release port
#
#  Usage          : ixnHLT_ReleasePort <chassis_id> <port_list>
#
#*******************************************************************************
proc ixnHLT_ReleasePort {chassis_id port_list} {
    #upvar 1 $ixnHLTVarName ixnHLT
    global ixnHLT
    foreach port_local $port_list {
        set chas [_ntgrGetTrafficPortChassisId $chassis_id $port_local]
        set card [_ntgrGetTrafficPortSlotId $chassis_id $port_local]
        set port [_ntgrGetTrafficPortId $chassis_id $port_local]
        set speed [_ntgrGetTrafficPortSpeed $chassis_id $port_local]
        set pL [list [list $chas $card $port]]
        Netgear_log_file "lib_ixiaNetwork.tcl" "Start using <cleanup_session> release vport:<$port_local>: $pL ..."
        set cleanup_info [::ixia::cleanup_session  \
            -port_handle $ixnHLT(PORT-HANDLE,//vport:<$port_local>) \
            -reset]
        # Check status
        if {[keylget cleanup_info status] != $::SUCCESS} {
            Netgear_log_file "lib_ixiaNetwork.tcl" "Clean up vport:<$port_local>: $p1 Failed. Deatail Info:[keylget cleanup_info log]." "YES"
        }
  }
}