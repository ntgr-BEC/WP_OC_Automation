####################################################################################
#  File Name   : cal-igmpsnp.tcl
#
#  Description :
#        This file contains CLI Abstraction layer for IGMP Snooping.
#
#  History     :
#        Date          Programmer         Description
#        07/06/06      Scott Zhang        Created
#
####################################################################################

#*******************************************************************************
#
#  Function Name  : CALIgmpSnpEnable
#
#  Description    : This function is called to enable igmp snooping on specified
#                   switch.
#
#  Usage          : CALIgmpSnpEnable <switch_name>
#
#*******************************************************************************
proc CALIgmpSnpEnable {switch_name} {
    set switch_vendor [ _get_switch_vendor_name  $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrIgmpSnpEnable $switch_name
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
            Netgear_log_file "cal-igmpsnp.tcl" "Switch not defined"
        }
    }
}

#*******************************************************************************
#
#  Function Name  : CALIgmpSnpDisable
#
#  Description    : This function is called to disable igmp snooping on specified
#                   switch.
#
#  Usage          : CALIgmpSnpDisable <switch_name>
#
#*******************************************************************************
proc CALIgmpSnpDisable {switch_name} {
    set switch_vendor [ _get_switch_vendor_name  $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrIgmpSnpDisable $switch_name
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
            Netgear_log_file "cal-igmpsnp.tcl" "Switch not defined"
        }
    }
}

#*******************************************************************************
#
#  Function Name  : CALIgmpSnpMRouterDisable
#
#  Description    : This function is called to disable multicast router on specified
#                   ports.
#
#  Usage          : CALIgmpSnpMRouterDisable <switch_name portlist vlan>
#
#*******************************************************************************
proc CALIgmpSnpMRouterDisable {switch_name portlist vlan} {
    set switch_vendor [ _get_switch_vendor_name  $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrIgmpSnpMRouterDisable $switch_name $portlist $vlan
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
            Netgear_log_file "cal-igmpsnp.tcl" "Switch not defined"
        }
    }
}

#*******************************************************************************
#
#  Function Name  : CALIgmpSnpInterfaceModeEnable
#
#  Description    : This function is called to enable igmp snooping on all ports
#                   in the given switch.
#
#  Usage          : CALIgmpSnpInterfaceModeEnable <switch_name>
#
#*******************************************************************************
proc CALIgmpSnpInterfaceModeEnable {switch_name} {
    set switch_vendor [ _get_switch_vendor_name  $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrIgmpSnpInterfaceModeEnable $switch_name
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
            Netgear_log_file "cal-igmpsnp.tcl" "Switch not defined"
        }
    }
}

#*******************************************************************************
#
#  Function Name  : CALIgmpSnpInterfaceModeDisable
#
#  Description    : This function is called to disable igmp snooping on all ports
#                   in the given switch.
#
#  Usage          : CALIgmpSnpInterfaceModeDisable <switch_name>
#
#*******************************************************************************
proc CALIgmpSnpInterfaceModeDisable {switch_name} {
    set switch_vendor [ _get_switch_vendor_name  $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrIgmpSnpInterfaceModeDisable $switch_name
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
            Netgear_log_file "cal-igmpsnp.tcl" "Switch not defined"
        }
    }
}

#*******************************************************************************
#
#  Function Name  : CALIgmpSnpConfigInterfaceProperties
#
#  Description    : This function is called to configure igmp snooping parameters
#                   on an interface/vlan.
#
#  Usage          : CALIgmpSnpConfigInterfaceProperties <switch_name>
#
#*******************************************************************************
proc CALIgmpSnpConfigInterfaceProperties {switch_name} {
    set switch_vendor [ _get_switch_vendor_name  $switch_name ]
    Netgear_connect_switch $switch_name
    set cnn_id [_get_switch_handle $switch_name]

    set interfaces [_getIgmpSnpInterfaceList $switch_name]
    foreach if_info $interfaces {
        set if_name [lindex $if_info 0]
        set if_template [lindex $if_info 1]
        if { [_IsPortChannelExist $if_name] == 1 } {
            set if_name [getChannelLogicalIfName $switch_name $if_name];#port channel interface
        }
        
        switch $switch_vendor {
            netgear {
                _ntgrIgmpSnpConfigInterfaceProperties $cnn_id $if_name $if_template
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
                Netgear_log_file "cal-igmpsnp.tcl" "Switch not defined"
            }
        }
    }

    Netgear_disconnect_switch $switch_name
}

#*******************************************************************************
#
#  Function Name  : CALIgmpSnpConfigGlobalProperties
#
#  Description    : This function is called to configure igmp properties for 
#                   a global level on the given switch.
#
#  Usage          : CALIgmpSnpConfigGlobalProperties <switch_name>
#
#*******************************************************************************
proc CALIgmpSnpConfigGlobalProperties {switch_name} {
    set switch_vendor [ _get_switch_vendor_name  $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrIgmpSnpConfigGlobalProperties $switch_name
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
            Netgear_log_file "cal-igmpsnp.tcl" "Switch not defined"
        }
    }
}

#*******************************************************************************
#
#  Function Name  : CALIgmpSnpClearEntry
#
#  Description    : This function is called to clear igmp forwarding entry.
#
#  Usage          : CALIgmpSnpClearEntry <switch_name {entry {}}>
#
#*******************************************************************************
proc CALIgmpSnpClearEntry {switch_name {entry {}}} {
    set switch_vendor [ _get_switch_vendor_name  $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrIgmpSnpClearEntry $switch_name $entry
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
            Netgear_log_file "cal-igmpsnp.tcl" "Switch not defined"
        }
    }
}

#*******************************************************************************
#
#  Function Name  : CALIgmpSnpPortEnable
#
#  Description    : This function is called to enable igmp snooping on ports.
#
#  Usage          : CALIgmpSnpPortEnable <switch_name portlist>
#
#*******************************************************************************
proc CALIgmpSnpPortEnable {switch_name portlist} {
    set switch_vendor [ _get_switch_vendor_name  $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrIgmpSnpPortEnable $switch_name $portlist
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
            Netgear_log_file "cal-igmpsnp.tcl" "Switch not defined"
        }
    }
}

#*******************************************************************************
#
#  Function Name  : CALIgmpSnpPortDisable
#
#  Description    : This function is called to disable igmp snooping on ports.
#
#  Usage          : CALIgmpSnpPortDisable <switch_name portlist>
#
#*******************************************************************************
proc CALIgmpSnpPortDisable {switch_name portlist} {
    set switch_vendor [ _get_switch_vendor_name  $switch_name ]

    switch $switch_vendor {
        netgear {
            _ntgrIgmpSnpPortDisable $switch_name $portlist
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
            Netgear_log_file "cal-igmpsnp.tcl" "Switch not defined"
        }
    }
}
