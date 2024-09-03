####################################################################################
#  File Name   : cal-igmpsnooping.tcl
#
#  Description :
#        This file includes functions used for igmp snooping configuration.
#
#  History     :
#        Date          Programmer         Description
#        11/15/2010    Tony Jing          Created
####################################################################################

#*******************************************************************************
#
#  Function Name  : CALIgmpSnoopingEnable
#
#  Description    : This function is called to enable igmp snooping on specified
#                   switch.
#
#  Usage          : CALIgmpSnoopingEnable <switch_name>
#
#*******************************************************************************
proc CALIgmpSnoopingEnable {switch_name} {
    set switch_vendor [ _get_switch_vendor_name  $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrIgmpSnoopingEnable $switch_name
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
#  Function Name  : CALIgmpSnoopingDisable
#
#  Description    : This function is called to disable igmp snooping on specified
#                   switch.
#
#  Usage          : CALIgmpSnoopingDisable <switch_name>
#
#*******************************************************************************
proc CALIgmpSnoopingDisable {switch_name} {
    set switch_vendor [ _get_switch_vendor_name  $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrIgmpSnoopingDisable $switch_name
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
#  Function Name  : CALIgmpSnoopingUnknownMulticastFilterEnable
#
#  Description    : This function is called to enable igmp snooping unknown-multicast filter on specified
#                   switch.
#
#  Usage          : CALIgmpSnoopingUnknownMulticastFilterEnable <switch_name>
#
#*******************************************************************************
proc CALIgmpSnoopingUnknownMulticastFilterEnable {switch_name} {
    set switch_vendor [ _get_switch_vendor_name  $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrIgmpSnoopingUnknownMulticastFilterEnable $switch_name
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
#  Function Name  : CALIgmpSnoopingUnknownMulticastFilterDisable
#
#  Description    : This function is called to disable igmp snooping unknown-multicast filter on specified
#                   switch.
#
#  Usage          : CALIgmpSnoopingUnknownMulticastFilterDisable <switch_name>
#
#*******************************************************************************
proc CALIgmpSnoopingUnknownMulticastFilterDisable {switch_name} {
    set switch_vendor [ _get_switch_vendor_name  $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrIgmpSnoopingUnknownMulticastFilterDisable $switch_name
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
#  Function Name  : CALIgmpSnoopingHeaderValidationEnable
#
#  Description    : This function is called to enable igmp snooping header-validation on specified
#                   switch.
#
#  Usage          : CALIgmpSnoopingHeaderValidationEnable <switch_name>
#
#*******************************************************************************
proc CALIgmpSnoopingHeaderValidationEnable {switch_name} {
    set switch_vendor [ _get_switch_vendor_name  $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrIgmpSnoopingHeaderValidationEnable $switch_name
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
#  Function Name  : CALIgmpSnoopingHeaderValidationDisable
#
#  Description    : This function is called to disable igmp snooping header-validation on specified
#                   switch.
#
#  Usage          : CALIgmpSnoopingHeaderValidationDisable <switch_name>
#
#*******************************************************************************
proc CALIgmpSnoopingHeaderValidationDisable {switch_name} {
    set switch_vendor [ _get_switch_vendor_name  $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrIgmpSnoopingHeaderValidationDisable $switch_name
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
#  Function Name  : CALIgmpSnoopingIntfEnable
#
#  Description    : This function is called to enable igmp snooping on specified
#                   interface.
#
#  Usage          : CALIgmpSnoopingIntfEnable <switch_name> <port_list>
#
#*******************************************************************************
proc CALIgmpSnoopingIntfEnable {switch_name port_list} {
    set switch_vendor [ _get_switch_vendor_name  $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrIgmpSnoopingIntfEnable $switch_name $port_list
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
#  Function Name  : CALIgmpSnoopingIntfDisable
#
#  Description    : This function is called to disable igmp snooping on specified
#                   interface.
#
#  Usage          : CALIgmpSnoopingIntfDisable <switch_name> <port_list>
#
#*******************************************************************************
proc CALIgmpSnoopingIntfDisable {switch_name port_list} {
    set switch_vendor [ _get_switch_vendor_name  $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrIgmpSnoopingIntfDisable $switch_name $port_list
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
#  Function Name  : CALIgmpSnoopingIntfFastleaveEnable
#
#  Description    : This function is called to enable igmp snooping fastleave on specified
#                   interface.
#
#  Usage          : CALIgmpSnoopingIntfFastleaveEnable <switch_name> <port_list>
#
#*******************************************************************************
proc CALIgmpSnoopingIntfFastleaveEnable {switch_name port_list} {
    set switch_vendor [ _get_switch_vendor_name  $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrIgmpSnoopingIntfFastleaveEnable $switch_name $port_list
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
#  Function Name  : CALIgmpSnoopingIntfFastleaveDisable
#
#  Description    : This function is called to disable igmp snooping fastleave on specified
#                   interface.
#
#  Usage          : CALIgmpSnoopingIntfFastleaveDisable <switch_name> <port_list>
#
#*******************************************************************************
proc CALIgmpSnoopingIntfFastleaveDisable {switch_name port_list} {
    set switch_vendor [ _get_switch_vendor_name  $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrIgmpSnoopingIntfFastleaveDisable $switch_name $port_list
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
#  Function Name  : CALIgmpSnoopingIntfSetMrouterPort
#
#  Description    : This function is called to set igmp snooping mrouter port on specified
#                   interface.
#
#  Usage          : CALIgmpSnoopingIntfSetMrouterPort <switch_name> <port_list>
#
#*******************************************************************************
proc CALIgmpSnoopingIntfSetMrouterPort {switch_name port_list} {
    set switch_vendor [ _get_switch_vendor_name  $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrIgmpSnoopingIntfSetMrouterPort $switch_name $port_list
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
#  Function Name  : CALIgmpSnoopingIntfUnsetMrouterPort
#
#  Description    : This function is called to unset igmp snooping mrouter port on specified
#                   interface.
#
#  Usage          : CALIgmpSnoopingIntfUnsetMrouterPort <switch_name> <port_list>
#
#*******************************************************************************
proc CALIgmpSnoopingIntfUnsetMrouterPort {switch_name port_list} {
    set switch_vendor [ _get_switch_vendor_name  $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrIgmpSnoopingIntfUnsetMrouterPort $switch_name $port_list
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
#  Function Name  : CALIgmpSnoopingIntfSetMrouterVlan
#
#  Description    : This function is called to set igmp snooping mrouter vlan on specified
#                   interface.
#
#  Usage          : CALIgmpSnoopingIntfSetMrouterVlan <switch_name> <port_list> <vlanid>
#
#*******************************************************************************
proc CALIgmpSnoopingIntfSetMrouterVlan {switch_name port_list vlanid} {
    set switch_vendor [ _get_switch_vendor_name  $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrIgmpSnoopingIntfSetMrouterVlan $switch_name $port_list $vlanid
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
#  Function Name  : CALIgmpSnoopingIntfUnsetMrouterVlan
#
#  Description    : This function is called to unset igmp snooping mrouter vlan on specified
#                   interface.
#
#  Usage          : CALIgmpSnoopingIntfUnsetMrouterVlan <switch_name> <port_list> <vlanid>
#
#*******************************************************************************
proc CALIgmpSnoopingIntfUnsetMrouterVlan {switch_name port_list vlanid} {
    set switch_vendor [ _get_switch_vendor_name  $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrIgmpSnoopingIntfUnsetMrouterVlan $switch_name $port_list $vlanid
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
#  Function Name  : CALIgmpSnoopingIntfSetMcrtrexpiretime
#
#  Description    : This function is called to set igmp snooping mcrtrexpiretime on specified
#                   interface.
#
#  Usage          : CALIgmpSnoopingIntfSetMcrtrexpiretime <switch_name> <port_list> <time>
#
#*******************************************************************************
proc CALIgmpSnoopingIntfSetMcrtrexpiretime {switch_name port_list time} {
    set switch_vendor [ _get_switch_vendor_name  $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrIgmpSnoopingIntfSetMcrtrexpiretime $switch_name $port_list $time
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
#  Function Name  : CALIgmpSnoopingIntfUnsetMcrtrexpiretime
#
#  Description    : This function is called to set igmp snooping mcrtrexpiretime on specified
#                   interface.
#
#  Usage          : CALIgmpSnoopingIntfUnsetMcrtrexpiretime <switch_name> <port_list>
#
#*******************************************************************************
proc CALIgmpSnoopingIntfUnsetMcrtrexpiretime {switch_name port_list} {
    set switch_vendor [ _get_switch_vendor_name  $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrIgmpSnoopingIntfUnsetMcrtrexpiretime $switch_name $port_list
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
#  Function Name  : CALIgmpSnoopingVlanEnable
#
#  Description    : This function is called to enable igmp snooping on specified vlan.
#
#  Usage          : CALIgmpSnoopingVlanEnable <switch_name> <vlan_list>
#
#*******************************************************************************
proc CALIgmpSnoopingVlanEnable {switch_name vlan_list} {
    set switch_vendor [ _get_switch_vendor_name  $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrIgmpSnoopingVlanEnable $switch_name $vlan_list
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
#  Function Name  : CALIgmpSnoopingVlanDisable
#
#  Description    : This function is called to disable igmp snooping on specified vlan.
#
#  Usage          : CALIgmpSnoopingVlanDisable <switch_name> <vlan_list>
#
#*******************************************************************************
proc CALIgmpSnoopingVlanDisable {switch_name vlan_list} {
    set switch_vendor [ _get_switch_vendor_name  $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrIgmpSnoopingVlanDisable $switch_name $vlan_list
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
#  Function Name  : CALIgmpSnoopingInterfacemodeEnable
#
#  Description    : This function is called to enable igmp snooping interfacemode on specified
#                   switch.
#
#  Usage          : CALIgmpSnoopingInterfacemodeEnable <switch_name>
#
#*******************************************************************************
proc CALIgmpSnoopingInterfacemodeEnable {switch_name} {
    set switch_vendor [ _get_switch_vendor_name  $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrIgmpSnoopingInterfacemodeEnable $switch_name
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
#  Function Name  : CALIgmpSnoopingInterfacemodeDisable
#
#  Description    : This function is called to disable igmp snooping interfacemode on specified
#                   switch.
#
#  Usage          : CALIgmpSnoopingInterfacemodeDisable <switch_name>
#
#*******************************************************************************
proc CALIgmpSnoopingInterfacemodeDisable {switch_name} {
    set switch_vendor [ _get_switch_vendor_name  $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrIgmpSnoopingInterfacemodeDisable $switch_name
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
#  Function Name  : CALIgmpSnoopingClearEntry
#
#  Description    : This function is called to clear igmp snooping entry
#
#  Usage          : CALIgmpSnoopingClearEntry <switch_name>
#
#*******************************************************************************
proc CALIgmpSnoopingClearEntry {switch_name} {
    set switch_vendor [ _get_switch_vendor_name  $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrIgmpSnoopingClearEntry $switch_name
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
#  Function Name  : CALCheckIgmpSnoopingEntry
#
#  Description    : This function is used to check igmpsnooping entry exist or not.
#
#  Usage          : CALCheckIgmpSnoopingEntry <switch_name> <mac_list> <notin>
#
#*******************************************************************************
proc CALCheckIgmpSnoopingEntry {switch_name mac_list {notin 0}} {
    set switch_vendor [ _get_switch_vendor_name  $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrCheckIgmpSnoopingEntry $switch_name $mac_list $notin
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
#  Function Name  : CALCheckIgmpv3SnoopingEntry
#
#  Description    : This function is used to check igmpsnooping entry exist or not.
#
#  Usage          : CALCheckIgmpv3SnoopingEntry <switch_name> <ip_list> <notin>
#
#*******************************************************************************
proc CALCheckIgmpv3SnoopingEntry {switch_name ip_list {notin 0}} {
    set switch_vendor [ _get_switch_vendor_name  $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrCheckIgmpv3SnoopingEntry $switch_name $ip_list $notin
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
#  Function Name  : CALCheckIgmpSnoopingEntryByMac
#
#  Description    : This function is used to check igmpsnooping entry exist or not by multicast mac.
#
#  Usage          : CALCheckIgmpSnoopingEntryByMac <switch_name> <mac> <check_list>
#
#*******************************************************************************
proc CALCheckIgmpSnoopingEntryByMac {switch_name mac check_list} {
    set switch_vendor [ _get_switch_vendor_name  $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrCheckIgmpSnoopingEntryByMac $switch_name $mac $check_list
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
#  Function Name  : CALIgmpSnoopingQuerierEnable
#
#  Description    : This function is called to enable igmp snooping querier on specified
#                   switch.
#
#  Usage          : CALIgmpSnoopingQuerierEnable <switch_name>
#
#*******************************************************************************
proc CALIgmpSnoopingQuerierEnable {switch_name} {
    set switch_vendor [ _get_switch_vendor_name  $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrIgmpSnoopingQuerierEnable $switch_name
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
#  Function Name  : CALIgmpSnoopingQuerierDisable
#
#  Description    : This function is called to disable igmp snooping querier on specified
#                   switch.
#
#  Usage          : CALIgmpSnoopingQuerierDisable <switch_name>
#
#*******************************************************************************
proc CALIgmpSnoopingQuerierDisable {switch_name} {
    set switch_vendor [ _get_switch_vendor_name  $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrIgmpSnoopingQuerierDisable $switch_name
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
#  Function Name  : CALIgmpSnoopingQuerierSetConfig
#
#  Description    : This function is called to set igmp snooping querier configuration on specified
#                   switch.
#
#  Usage          : CALIgmpSnoopingQuerierSetConfig <switch_name>
#
#*******************************************************************************
proc CALIgmpSnoopingQuerierSetConfig {switch_name} {
    set switch_vendor [ _get_switch_vendor_name  $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrIgmpSnoopingQuerierSetConfig $switch_name
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
#  Function Name  : CALIgmpSnoopingQuerierUnsetConfig
#
#  Description    : This function is called to unset igmp snooping querier configuration on specified
#                   switch.
#
#  Usage          : CALIgmpSnoopingQuerierUnsetConfig <switch_name>
#
#*******************************************************************************
proc CALIgmpSnoopingQuerierUnsetConfig {switch_name} {
    set switch_vendor [ _get_switch_vendor_name  $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrIgmpSnoopingQuerierUnsetConfig $switch_name
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
#  Function Name  : CALIgmpSnoopingQuerierVlanEnable
#
#  Description    : This function is called to enable igmp snooping querier on specified vlan.
#
#  Usage          : CALIgmpSnoopingQuerierVlanEnable <switch_name> <vlanid>
#
#*******************************************************************************
proc CALIgmpSnoopingQuerierVlanEnable {switch_name vlanid} {
    set switch_vendor [ _get_switch_vendor_name  $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrIgmpSnoopingQuerierVlanEnable $switch_name $vlanid
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
#  Function Name  : CALIgmpSnoopingQuerierVlanDisable
#
#  Description    : This function is called to disable igmp snooping querier on specified vlan.
#
#  Usage          : CALIgmpSnoopingQuerierVlanDisable <switch_name> <vlanid>
#
#*******************************************************************************
proc CALIgmpSnoopingQuerierVlanDisable {switch_name vlanid} {
    set switch_vendor [ _get_switch_vendor_name  $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrIgmpSnoopingQuerierVlanDisable $switch_name $vlanid
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