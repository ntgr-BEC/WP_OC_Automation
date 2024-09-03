####################################################################################
#  File Name   : cal-mldsnooping.tcl
#
#  Description :
#        This file includes functions used for mld snooping configuration.
#
#  History     :
#        Date          Programmer         Description
#        11/29/2010    Tony Jing          Created
####################################################################################

#*******************************************************************************
#
#  Function Name  : CALMldSnoopingEnable
#
#  Description    : This function is called to enable mld snooping on specified
#                   switch.
#
#  Usage          : CALMldSnoopingEnable <switch_name>
#
#*******************************************************************************
proc CALMldSnoopingEnable {switch_name} {
    set switch_vendor [ _get_switch_vendor_name  $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrMldSnoopingEnable $switch_name
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
            Netgear_log_file "cal-mldsnooping.tcl" "Switch not defined"
        }
    }
}

#*******************************************************************************
#
#  Function Name  : CALMldSnoopingDisable
#
#  Description    : This function is called to disable mld snooping on specified
#                   switch.
#
#  Usage          : CALMldSnoopingDisable <switch_name>
#
#*******************************************************************************
proc CALMldSnoopingDisable {switch_name} {
    set switch_vendor [ _get_switch_vendor_name  $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrMldSnoopingDisable $switch_name
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
            Netgear_log_file "cal-mldsnooping.tcl" "Switch not defined"
        }
    }
}

#*******************************************************************************
#
#  Function Name  : CALMldSnoopingUnknownMulticastFilterEnable
#
#  Description    : This function is called to enable mld snooping unknown-multicast filter on specified
#                   switch.
#
#  Usage          : CALMldSnoopingUnknownMulticastFilterEnable <switch_name>
#
#*******************************************************************************
proc CALMldSnoopingUnknownMulticastFilterEnable {switch_name} {
    set switch_vendor [ _get_switch_vendor_name  $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrMldSnoopingUnknownMulticastFilterEnable $switch_name
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
            Netgear_log_file "cal-mldsnooping.tcl" "Switch not defined"
        }
    }
}

#*******************************************************************************
#
#  Function Name  : CALMldSnoopingUnknownMulticastFilterDisable
#
#  Description    : This function is called to disable mld snooping unknown-multicast filter on specified
#                   switch.
#
#  Usage          : CALMldSnoopingUnknownMulticastFilterDisable <switch_name>
#
#*******************************************************************************
proc CALMldSnoopingUnknownMulticastFilterDisable {switch_name} {
    set switch_vendor [ _get_switch_vendor_name  $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrMldSnoopingUnknownMulticastFilterDisable $switch_name
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
            Netgear_log_file "cal-mldsnooping.tcl" "Switch not defined"
        }
    }
}


#*******************************************************************************
#
#  Function Name  : CALMldSnoopingIntfEnable
#
#  Description    : This function is called to enable mld snooping on specified
#                   interface.
#
#  Usage          : CALMldSnoopingIntfEnable <switch_name> <port_list>
#
#*******************************************************************************
proc CALMldSnoopingIntfEnable {switch_name port_list} {
    set switch_vendor [ _get_switch_vendor_name  $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrMldSnoopingIntfEnable $switch_name $port_list
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
            Netgear_log_file "cal-mldsnooping.tcl" "Switch not defined"
        }
    }
}

#*******************************************************************************
#
#  Function Name  : CALMldSnoopingIntfDisable
#
#  Description    : This function is called to disable mld snooping on specified
#                   interface.
#
#  Usage          : CALMldSnoopingIntfDisable <switch_name> <port_list>
#
#*******************************************************************************
proc CALMldSnoopingIntfDisable {switch_name port_list} {
    set switch_vendor [ _get_switch_vendor_name  $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrMldSnoopingIntfDisable $switch_name $port_list
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
            Netgear_log_file "cal-mldsnooping.tcl" "Switch not defined"
        }
    }
}


#*******************************************************************************
#
#  Function Name  : CALMldSnoopingIntfFastleaveEnable
#
#  Description    : This function is called to enable mld snooping fastleave on specified
#                   interface.
#
#  Usage          : CALMldSnoopingIntfFastleaveEnable <switch_name> <port_list>
#
#*******************************************************************************
proc CALMldSnoopingIntfFastleaveEnable {switch_name port_list} {
    set switch_vendor [ _get_switch_vendor_name  $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrMldSnoopingIntfFastleaveEnable $switch_name $port_list
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
            Netgear_log_file "cal-mldsnooping.tcl" "Switch not defined"
        }
    }
}

#*******************************************************************************
#
#  Function Name  : CALMldSnoopingIntfFastleaveDisable
#
#  Description    : This function is called to disable mld snooping fastleave on specified
#                   interface.
#
#  Usage          : CALMldSnoopingIntfFastleaveDisable <switch_name> <port_list>
#
#*******************************************************************************
proc CALMldSnoopingIntfFastleaveDisable {switch_name port_list} {
    set switch_vendor [ _get_switch_vendor_name  $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrMldSnoopingIntfFastleaveDisable $switch_name $port_list
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
            Netgear_log_file "cal-mldsnooping.tcl" "Switch not defined"
        }
    }
}

#*******************************************************************************
#
#  Function Name  : CALMldSnoopingIntfSetMrouterPort
#
#  Description    : This function is called to set mld snooping mrouter port on specified
#                   interface.
#
#  Usage          : CALMldSnoopingIntfSetMrouterPort <switch_name> <port_list>
#
#*******************************************************************************
proc CALMldSnoopingIntfSetMrouterPort {switch_name port_list} {
    set switch_vendor [ _get_switch_vendor_name  $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrMldSnoopingIntfSetMrouterPort $switch_name $port_list
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
            Netgear_log_file "cal-mldsnooping.tcl" "Switch not defined"
        }
    }
}

#*******************************************************************************
#
#  Function Name  : CALMldSnoopingIntfUnsetMrouterPort
#
#  Description    : This function is called to unset mld snooping mrouter port on specified
#                   interface.
#
#  Usage          : CALMldSnoopingIntfUnsetMrouterPort <switch_name> <port_list>
#
#*******************************************************************************
proc CALMldSnoopingIntfUnsetMrouterPort {switch_name port_list} {
    set switch_vendor [ _get_switch_vendor_name  $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrMldSnoopingIntfUnsetMrouterPort $switch_name $port_list
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
            Netgear_log_file "cal-mldsnooping.tcl" "Switch not defined"
        }
    }
}


#*******************************************************************************
#
#  Function Name  : CALMldSnoopingIntfSetMrouterVlan
#
#  Description    : This function is called to set mld snooping mrouter vlan on specified
#                   interface.
#
#  Usage          : CALMldSnoopingIntfSetMrouterVlan <switch_name> <port_list> <vlanid>
#
#*******************************************************************************
proc CALMldSnoopingIntfSetMrouterVlan {switch_name port_list vlanid} {
    set switch_vendor [ _get_switch_vendor_name  $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrMldSnoopingIntfSetMrouterVlan $switch_name $port_list $vlanid
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
            Netgear_log_file "cal-mldsnooping.tcl" "Switch not defined"
        }
    }
}

#*******************************************************************************
#
#  Function Name  : CALMldSnoopingIntfUnsetMrouterVlan
#
#  Description    : This function is called to unset mld snooping mrouter vlan on specified
#                   interface.
#
#  Usage          : CALMldSnoopingIntfUnsetMrouterVlan <switch_name> <port_list> <vlanid>
#
#*******************************************************************************
proc CALMldSnoopingIntfUnsetMrouterVlan {switch_name port_list vlanid} {
    set switch_vendor [ _get_switch_vendor_name  $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrMldSnoopingIntfUnsetMrouterVlan $switch_name $port_list $vlanid
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
            Netgear_log_file "cal-mldsnooping.tcl" "Switch not defined"
        }
    }
}

#*******************************************************************************
#
#  Function Name  : CALMldSnoopingIntfSetMcrtrexpiretime
#
#  Description    : This function is called to set mld snooping mcrtrexpiretime on specified
#                   interface.
#
#  Usage          : CALMldSnoopingIntfSetMcrtrexpiretime <switch_name> <port_list> <time>
#
#*******************************************************************************
proc CALMldSnoopingIntfSetMcrtrexpiretime {switch_name port_list time} {
    set switch_vendor [ _get_switch_vendor_name  $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrMldSnoopingIntfSetMcrtrexpiretime $switch_name $port_list $time
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
            Netgear_log_file "cal-mldsnooping.tcl" "Switch not defined"
        }
    }
}

#*******************************************************************************
#
#  Function Name  : CALMldSnoopingIntfUnsetMcrtrexpiretime
#
#  Description    : This function is called to set mld snooping mcrtrexpiretime on specified
#                   interface.
#
#  Usage          : CALMldSnoopingIntfUnsetMcrtrexpiretime <switch_name> <port_list>
#
#*******************************************************************************
proc CALMldSnoopingIntfUnsetMcrtrexpiretime {switch_name port_list} {
    set switch_vendor [ _get_switch_vendor_name  $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrMldSnoopingIntfUnsetMcrtrexpiretime $switch_name $port_list
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
            Netgear_log_file "cal-mldsnooping.tcl" "Switch not defined"
        }
    }
}






#*******************************************************************************
#
#  Function Name  : CALMldSnoopingInterfacemodeEnable
#
#  Description    : This function is called to enable mld snooping interfacemode on specified
#                   switch.
#
#  Usage          : CALMldSnoopingInterfacemodeEnable <switch_name>
#
#*******************************************************************************
proc CALMldSnoopingInterfacemodeEnable {switch_name} {
    set switch_vendor [ _get_switch_vendor_name  $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrMldSnoopingInterfacemodeEnable $switch_name
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
            Netgear_log_file "cal-mldsnooping.tcl" "Switch not defined"
        }
    }
}

#*******************************************************************************
#
#  Function Name  : CALMldSnoopingInterfacemodeDisable
#
#  Description    : This function is called to disable mld snooping interfacemode on specified
#                   switch.
#
#  Usage          : CALMldSnoopingInterfacemodeDisable <switch_name>
#
#*******************************************************************************
proc CALMldSnoopingInterfacemodeDisable {switch_name} {
    set switch_vendor [ _get_switch_vendor_name  $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrMldSnoopingInterfacemodeDisable $switch_name
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
            Netgear_log_file "cal-mldsnooping.tcl" "Switch not defined"
        }
    }
}



#*******************************************************************************
#
#  Function Name  : CALMldSnoopingVlanEnable
#
#  Description    : This function is called to enable mld snooping on vlan on specified
#                   switch.
#
#  Usage          : CALMldSnoopingVlanEnable <switch_name> <vlan_list>
#
#*******************************************************************************
proc CALMldSnoopingVlanEnable {switch_name vlan_list} {
    set switch_vendor [ _get_switch_vendor_name  $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrMldSnoopingVlanEnable $switch_name $vlan_list
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
            Netgear_log_file "cal-mldsnooping.tcl" "Switch not defined"
        }
    }
}

#*******************************************************************************
#
#  Function Name  : CALMldSnoopingVlanDisable
#
#  Description    : This function is called to disable mld snooping on vlan on specified
#                   switch.
#
#  Usage          : CALMldSnoopingVlanDisable <switch_name> <vlan_list>
#
#*******************************************************************************
proc CALMldSnoopingVlanDisable {switch_name vlan_list} {
    set switch_vendor [ _get_switch_vendor_name  $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrMldSnoopingVlanDisable $switch_name $vlan_list
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
            Netgear_log_file "cal-mldsnooping.tcl" "Switch not defined"
        }
    }
}





#*******************************************************************************
#
#  Function Name  : CALMldSnoopingClearEntry
#
#  Description    : This function is called to clear mld snooping entry
#
#  Usage          : CALMldSnoopingClearEntry <switch_name>
#
#*******************************************************************************
proc CALMldSnoopingClearEntry {switch_name} {
    set switch_vendor [ _get_switch_vendor_name  $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrMldSnoopingClearEntry $switch_name
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
            Netgear_log_file "cal-mldsnooping.tcl" "Switch not defined"
        }
    }
}


#*******************************************************************************
#
#  Function Name  : CALCheckMldSnoopingEntry
#
#  Description    : This function is used to check mldsnooping entry exist or not.
#
#  Usage          : CALCheckMldSnoopingEntry <switch_name> <mac_list> <notin>
#
#*******************************************************************************
proc CALCheckMldSnoopingEntry {switch_name mac_list {notin 0}} {
    set switch_vendor [ _get_switch_vendor_name  $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrCheckMldSnoopingEntry $switch_name $mac_list $notin
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
            Netgear_log_file "cal-mldsnooping.tcl" "Switch not defined"
        }
    }
}


#*******************************************************************************
#
#  Function Name  : CALCheckMldSnoopingV2Entry
#
#  Description    : This function is used to check mldsnooping entry (V2) exist or not.
#                   From version 11.0, V2 can't found in the GUI, and the command change to <show mldsnooping ssm groups>
#
#  Usage          : CALCheckMldSnoopingV2Entry <switch_name> <ip_list> <notin>
#
#  Author         : jim.xie
#
#*******************************************************************************
proc CALCheckMldSnoopingV2Entry {switch_name ip_list {notin 0}} {
    set switch_vendor [ _get_switch_vendor_name  $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrCheckMldSnoopingV2Entry $switch_name $ip_list $notin
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
            Netgear_log_file "cal-mldsnooping.tcl" "Switch not defined"
        }
    }
}


#*******************************************************************************
#
#  Function Name  : CALCheckMldSnoopingEntryByMac
#
#  Description    : This function is used to check mldsnooping entry exist or not by multicast mac.
#
#  Usage          : CALCheckMldSnoopingEntryByMac <switch_name> <mac> <check_list>
#
#*******************************************************************************
proc CALCheckMldSnoopingEntryByMac {switch_name mac check_list} {
    set switch_vendor [ _get_switch_vendor_name  $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrCheckMldSnoopingEntryByMac $switch_name $mac $check_list
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
            Netgear_log_file "cal-mldsnooping.tcl" "Switch not defined"
        }
    }
}





#*******************************************************************************
#
#  Function Name  : CALMldSnoopingQuerierEnable
#
#  Description    : This function is called to enable mld snooping querier on specified
#                   switch.
#
#  Usage          : CALMldSnoopingQuerierEnable <switch_name>
#
#*******************************************************************************
proc CALMldSnoopingQuerierEnable {switch_name} {
    set switch_vendor [ _get_switch_vendor_name  $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrMldSnoopingQuerierEnable $switch_name
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
            Netgear_log_file "cal-mldsnooping.tcl" "Switch not defined"
        }
    }
}

#*******************************************************************************
#
#  Function Name  : CALMldSnoopingQuerierDisable
#
#  Description    : This function is called to disable mld snooping querier on specified
#                   switch.
#
#  Usage          : CALMldSnoopingQuerierDisable <switch_name>
#
#*******************************************************************************
proc CALMldSnoopingQuerierDisable {switch_name} {
    set switch_vendor [ _get_switch_vendor_name  $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrMldSnoopingQuerierDisable $switch_name
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
            Netgear_log_file "cal-mldsnooping.tcl" "Switch not defined"
        }
    }
}

#*******************************************************************************
#
#  Function Name  : CALMldSnoopingQuerierSetConfig
#
#  Description    : This function is called to set mld snooping querier configuration on specified
#                   switch.
#
#  Usage          : CALMldSnoopingQuerierSetConfig <switch_name>
#
#*******************************************************************************
proc CALMldSnoopingQuerierSetConfig {switch_name} {
    set switch_vendor [ _get_switch_vendor_name  $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrMldSnoopingQuerierSetConfig $switch_name
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
            Netgear_log_file "cal-mldsnooping.tcl" "Switch not defined"
        }
    }
}

#*******************************************************************************
#
#  Function Name  : CALMldSnoopingQuerierUnsetConfig
#
#  Description    : This function is called to unset mld snooping querier configuration on specified
#                   switch.
#
#  Usage          : CALMldSnoopingQuerierUnsetConfig <switch_name>
#
#*******************************************************************************
proc CALMldSnoopingQuerierUnsetConfig {switch_name} {
    set switch_vendor [ _get_switch_vendor_name  $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrMldSnoopingQuerierUnsetConfig $switch_name
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
            Netgear_log_file "cal-mldsnooping.tcl" "Switch not defined"
        }
    }
}


#*******************************************************************************
#
#  Function Name  : CALMldSnoopingQuerierVlanEnable
#
#  Description    : This function is called to enable mld snooping querier no vlan on specified
#                   switch.
#
#  Usage          : CALMldSnoopingQuerierVlanEnable <switch_name> <vlanid>
#
#*******************************************************************************
proc CALMldSnoopingQuerierVlanEnable {switch_name vlanid} {
    set switch_vendor [ _get_switch_vendor_name  $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrMldSnoopingQuerierVlanEnable $switch_name $vlanid
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
            Netgear_log_file "cal-mldsnooping.tcl" "Switch not defined"
        }
    }
}

#*******************************************************************************
#
#  Function Name  : CALMldSnoopingQuerierVlanDisable
#
#  Description    : This function is called to disable mld snooping querier no vlan on specified
#                   switch.
#
#  Usage          : CALMldSnoopingQuerierVlanDisable <switch_name> <vlanid>
#
#*******************************************************************************
proc CALMldSnoopingQuerierVlanDisable {switch_name vlanid} {
    set switch_vendor [ _get_switch_vendor_name  $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrMldSnoopingQuerierVlanDisable $switch_name $vlanid
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
            Netgear_log_file "cal-mldsnooping.tcl" "Switch not defined"
        }
    }
}