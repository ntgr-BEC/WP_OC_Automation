#*******************************************************************************
#
#  Function Name  : CALVLanTrunkAddVlanPort
#
#  Description    : This function is called to add vlan id on interface.
#
#  Usage          : CALVLanTrunkAddVlanPort <switch_name interface mode vlanid>
#
#*******************************************************************************
proc CALVLanTrunkAddVlanPort { switch_name interface mode vlanid} {
    set switch_vendor [ _get_switch_vendor_name  $switch_name ]

    Netgear_log_file "cal-ntgr-vlan.tcl" "add vlan $vlanid  to interface $interface on switch $switch_name with $mode mode"
    switch $switch_vendor {
        netgear {
            _switch_vlan_trunk_add_vlans_to_port $switch_name $interface $mode $vlanid
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
            Netgear_log_file "cal-ntgr-vlan.tcl" "Switch not defined"
        }
    }
}

#*******************************************************************************
#
#  Function Name  : CALVLanTrunkAllowTrunkVlanPort
#
#  Description    : This function is called to allow trunk vlan list on interface.
#
#  Usage          : CALVLanTrunkAllowTrunkVlanPort <switch_name interface vlanlist>
#
#*******************************************************************************
proc CALVLanTrunkAllowTrunkVlanPort { switch_name interface vlanlist} {
    set switch_vendor [ _get_switch_vendor_name  $switch_name ]

    Netgear_log_file "cal-ntgr-vlan.tcl" "allow trunk vlan $vlanlist  to interface $interface on switch $switch_name "
    switch $switch_vendor {
        netgear {
            _switch_vlan_trunk_allow_vlan $switch_name $interface $vlanlist
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
            Netgear_log_file "cal-ntgr-vlan.tcl" "Switch not defined"
        }
    }
}


#*******************************************************************************
#
#  Function Name  : CALVLanTrunkAddTrunkVlanPort
#
#  Description    : This function is called to add trunk vlan id on interface.
#
#  Usage          : CALVLanTrunkAddTrunkVlanPort <switch_name interface vlanid>
#
#*******************************************************************************
proc CALVLanTrunkAddTrunkVlanPort { switch_name interface vlanid} {
    set switch_vendor [ _get_switch_vendor_name  $switch_name ]

    Netgear_log_file "cal-ntgr-vlan.tcl" "add trunk vlan $vlanid  to interface $interface on switch $switch_name "
    switch $switch_vendor {
        netgear {
            _switch_vlan_trunk_add_vlan $switch_name $interface $vlanid
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
            Netgear_log_file "cal-ntgr-vlan.tcl" "Switch not defined"
        }
    }
}

#*******************************************************************************
#
#  Function Name  : CALVLanTrunkRemoveTrunkVlanPort
#
#  Description    : This function is called to remove trunk vlan id on interface.
#
#  Usage          : CALVLanTrunkRemoveTrunkVlanPort <switch_name interface vlanid>
#
#*******************************************************************************
proc CALVLanTrunkRemoveTrunkVlanPort { switch_name interface vlanid} {
    set switch_vendor [ _get_switch_vendor_name  $switch_name ]

    Netgear_log_file "cal-ntgr-vlan.tcl" "remove trunk vlan $vlanid  to interface $interface on switch $switch_name "
    switch $switch_vendor {
        netgear {
            _switch_vlan_trunk_remove_vlan $switch_name $interface $vlanid
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
            Netgear_log_file "cal-ntgr-vlan.tcl" "Switch not defined"
        }
    }
}

#*******************************************************************************
#
#  Function Name  : CALVLanTrunkExceptTrunkVlanPort
#
#  Description    : This function is called to except trunk vlan id on interface.
#
#  Usage          : CALVLanTrunkExceptTrunkVlanPort <switch_name interface vlanid>
#
#*******************************************************************************
proc CALVLanTrunkExceptTrunkVlanPort { switch_name interface vlanid} {
    set switch_vendor [ _get_switch_vendor_name  $switch_name ]

    Netgear_log_file "cal-ntgr-vlan.tcl" "expect trunk vlan $vlanid  to interface $interface on switch $switch_name "
    switch $switch_vendor {
        netgear {
            _switch_vlan_trunk_except_vlan $switch_name $interface $vlanid
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
            Netgear_log_file "cal-ntgr-vlan.tcl" "Switch not defined"
        }
    }
}


#*******************************************************************************
#
#  Function Name  : CALVLanTrunkCFGNativeVlan
#
#  Description    : This function is called to configure native vlan id on interface.
#
#  Usage          : CALVLanTrunkCFGNativeVlan <switch_name interface vlanid>
#
#*******************************************************************************
proc CALVLanTrunkCFGNativeVlan { switch_name interface vlanid} {
    set switch_vendor [ _get_switch_vendor_name  $switch_name ]

    Netgear_log_file "cal-ntgr-vlan.tcl" "configre native vlan $vlanid  to interface $interface on switch $switch_name "
    switch $switch_vendor {
        netgear {
            _switch_vlan_trunk_native_vlan $switch_name $interface $vlanid
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
            Netgear_log_file "cal-ntgr-vlan.tcl" "Switch not defined"
        }
    }
}

#*******************************************************************************
#
#  Function Name  : CALVLanTrunkDeleteMode
#
#  Description    : This function is called to delete mode on interface.
#
#  Usage          : CALVLanTrunkDeleteMode <switch_name interface>
#
#*******************************************************************************
proc CALVLanTrunkDeleteMode { switch_name interface} {
    set switch_vendor [ _get_switch_vendor_name  $switch_name ]

    Netgear_log_file "cal-ntgr-vlan.tcl" "delete mode for interface $interface on switch $switch_name "
    switch $switch_vendor {
        netgear {
            _switch_vlan_trunk_delete_mode $switch_name $interface
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
            Netgear_log_file "cal-ntgr-vlan.tcl" "Switch not defined"
        }
    }
}

#*******************************************************************************
#
#  Function Name  : CALVLanTrunkSwitchMode
#
#  Description    : This function is called to switch mode on interface.
#
#  Usage          : CALVLanTrunkSwitchMode <switch_name interface mode>
#
#*******************************************************************************
proc CALVLanTrunkSwitchMode { switch_name interface mode} {
    set switch_vendor [ _get_switch_vendor_name  $switch_name ]

    Netgear_log_file "cal-ntgr-vlan.tcl" "switch mode $mode for interface $interface on switch $switch_name "
    switch $switch_vendor {
        netgear {
            _switch_vlan_trunk_switch_mode $switch_name $interface $mode
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
            Netgear_log_file "cal-ntgr-vlan.tcl" "Switch not defined"
        }
    }
}

#*******************************************************************************
#
#  Function Name  : CALVLanTrunkDeleteAccess
#
#  Description    : This function is called to delete access vlan on interface.
#
#  Usage          : CALVLanTrunkDeleteAccess <switch_name interface>
#
#*******************************************************************************
proc CALVLanTrunkDeleteAccess { switch_name interface} {
    set switch_vendor [ _get_switch_vendor_name  $switch_name ]

    Netgear_log_file "cal-ntgr-vlan.tcl" "delete access vlan interface $interface on switch $switch_name "
    switch $switch_vendor {
        netgear {
            _switch_vlan_trunk_delete_access $switch_name $interface
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
            Netgear_log_file "cal-ntgr-vlan.tcl" "Switch not defined"
        }
    }
}


#*******************************************************************************
#
#  Function Name  : CALVLanTrunkGetAccessCFG
#
#  Description    : This function is called to get access vlan cfg on interface.
#
#  Usage          : CALVLanTrunkGetAccessCFG <switch_name interface expected_mode expected_vlanid>
#
#*******************************************************************************
proc CALVLanTrunkGetAccessCFG { switch_name interface expected_mode expected_vlanid} {
    set switch_vendor [ _get_switch_vendor_name  $switch_name ]

    Netgear_log_file "cal-ntgr-vlan.tcl" "get access vlan cfg of interface $interface on switch $switch_name "
    switch $switch_vendor {
        netgear {
           set ret [ _switch_vlan_trunk_Get_accessvlan_CFG $switch_name $interface $expected_mode $expected_vlanid ]
           return $ret
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
            Netgear_log_file "cal-ntgr-vlan.tcl" "Switch not defined"
        }
    }
}

#*******************************************************************************
#
#  Function Name  : CALVLanTrunkModifyAccess
#
#  Description    : This function is called to modify access vlan on interface.
#
#  Usage          : CALVLanTrunkModifyAccess <switch_name interface vlanid>
#
#*******************************************************************************
proc CALVLanTrunkModifyAccess { switch_name interface vlanid} {
    set switch_vendor [ _get_switch_vendor_name  $switch_name ]

    Netgear_log_file "cal-ntgr-vlan.tcl" "modify access vlan to $vlanid for interface $interface on switch $switch_name "
    switch $switch_vendor {
        netgear {
            _switch_vlan_trunk_modify_access $switch_name $interface $vlanid
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
            Netgear_log_file "cal-ntgr-vlan.tcl" "Switch not defined"
        }
    }
}

#*******************************************************************************
#
#  Function Name  : CALVLanTrunkGetTrunkCFG
#
#  Description    : This function is called to get trunk vlan cfg on interface.
#
#  Usage          : CALVLanTrunkGetTrunkCFG <switch_name interface expected_mode expected_vlanlist expected_nativevlan>
#
#*******************************************************************************
proc CALVLanTrunkGetTrunkCFG { switch_name interface expected_mode expected_vlanid expected_nativevlan} {
    set switch_vendor [ _get_switch_vendor_name  $switch_name ]

    Netgear_log_file "cal-ntgr-vlan.tcl" "get trunk vlan cfg of interface $interface on switch $switch_name "
    switch $switch_vendor {
        netgear {
           set ret [ _switch_vlan_trunk_Get_trunkvlan_CFG $switch_name $interface $expected_mode $expected_vlanid $expected_nativevlan]
           return $ret
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
            Netgear_log_file "cal-ntgr-vlan.tcl" "Switch not defined"
        }
    }
}

#*******************************************************************************
#
#  Function Name  : CALVLanTrunkResetTrunkvlan
#
#  Description    : This function is called to reset trunk vlan on interface.
#
#  Usage          : CALVLanTrunkResetTrunkvlan <switch_name interface>
#
#*******************************************************************************
proc CALVLanTrunkResetTrunkvlan { switch_name interface} {
    set switch_vendor [ _get_switch_vendor_name  $switch_name ]

    Netgear_log_file "cal-ntgr-vlan.tcl" "reset trunk vlan for interface $interface on switch $switch_name "
    switch $switch_vendor {
        netgear {
            _switch_vlan_trunk_reset_trunkvlan $switch_name $interface
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
            Netgear_log_file "cal-ntgr-vlan.tcl" "Switch not defined"
        }
    }
}

#*******************************************************************************
#
#  Function Name  : CALVLanTrunkIncludeGeneral
#
#  Description    : This function is called to include general vlans on interface.
#
#  Usage          : CALVLanTrunkIncludeGeneral <switch_name interface vlanid>
#
#*******************************************************************************
proc CALVLanTrunkIncludeGeneral { switch_name interface vlanid} {
    set switch_vendor [ _get_switch_vendor_name  $switch_name ]

    Netgear_log_file "cal-ntgr-vlan.tcl" "include vlans  $vlanid for interface $interface on switch $switch_name "
    switch $switch_vendor {
        netgear {
            _switch_vlan_trunk_include_general $switch_name $interface $vlanid
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
            Netgear_log_file "cal-ntgr-vlan.tcl" "Switch not defined"
        }
    }
}

#*******************************************************************************
#
#  Function Name  : CALVLanTrunkDelIncludeGeneral
#
#  Description    : This function is called to delete include general vlans on interface.
#
#  Usage          : CALVLanTrunkDelIncludeGeneral <switch_name interface vlanid>
#
#*******************************************************************************
proc CALVLanTrunkDelIncludeGeneral { switch_name interface vlanid} {
    set switch_vendor [ _get_switch_vendor_name  $switch_name ]

    Netgear_log_file "cal-ntgr-vlan.tcl" "include vlans  $vlanid for interface $interface on switch $switch_name "
    switch $switch_vendor {
        netgear {
            _switch_vlan_trunk_delete_include_general $switch_name $interface $vlanid
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
            Netgear_log_file "cal-ntgr-vlan.tcl" "Switch not defined"
        }
    }
}

#*******************************************************************************
#
#  Function Name  : CALVLanTrunkTagGeneral
#
#  Description    : This function is called to add tagging vlan for general vlans on interface.
#
#  Usage          : CALVLanTrunkTagGeneral <switch_name interface vlanid>
#
#*******************************************************************************
proc CALVLanTrunkTagGeneral { switch_name interface vlanid} {
    set switch_vendor [ _get_switch_vendor_name  $switch_name ]

    Netgear_log_file "cal-ntgr-vlan.tcl" "add tagging vlans  $vlanid for interface $interface on switch $switch_name "
    switch $switch_vendor {
        netgear {
            _switch_vlan_trunk_tagvlan_general $switch_name $interface $vlanid
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
            Netgear_log_file "cal-ntgr-vlan.tcl" "Switch not defined"
        }
    }
}

#*******************************************************************************
#
#  Function Name  : CALVLanTrunkDelTagGeneral
#
#  Description    : This function is called to delete tagging vlan for general vlans on interface.
#
#  Usage          : CALVLanTrunkDelTagGeneral <switch_name interface vlanid>
#
#*******************************************************************************
proc CALVLanTrunkDelTagGeneral { switch_name interface vlanid} {
    set switch_vendor [ _get_switch_vendor_name  $switch_name ]

    Netgear_log_file "cal-ntgr-vlan.tcl" "deleting tagging vlans  $vlanid for interface $interface on switch $switch_name "
    switch $switch_vendor {
        netgear {
            _switch_vlan_trunk_deleting_tag_general $switch_name $interface $vlanid
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
            Netgear_log_file "cal-ntgr-vlan.tcl" "Switch not defined"
        }
    }
}