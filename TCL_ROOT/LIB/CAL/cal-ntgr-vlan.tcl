#!/bin/sh
################################################################################   
#
#  File Name   : cal-ntgr-vlan.tcl
#
#  Description :
#         This TCL contains APIs to configure the switches. This is CLI 
#         abstraction Layer for VLAN configuration.
#
#  Revision History 	:
#        Date         Programmer        Description
#        -----------------------------------------------------------------------
#        22/5/06      Scott Zhang        Created
#
#
#
#
################################################################################

#*******************************************************************************
#
#  Function Name	: CALCreateVlan
#
#  Description    : This function is an API used to create vlan
#         
#  Usage          : CALCreateVlan <vlan_index>
# 
#*******************************************************************************
proc CALCreateVlan { vlan_index } {
	set vlan_members [_getVlanMembers $vlan_index]

	foreach switch_info $vlan_members {
		set switch_name [lindex $switch_info 0]
		set port_info   [lindex $switch_info 1]
		CALCreateVlanPerSwitch $switch_name $vlan_index $port_info
	} 
}

#*******************************************************************************
#
#  Function Name	: CALAddPrivateVlanGroup
#
#  Description    : This function is an API used to create private vlan group
#         
#  Usage          : CALAddPrivateVlanGroup <switch_name> <group_name> <group_mode>
# 
#*******************************************************************************
proc CALAddPrivateVlanGroup {switch_name group_name group_mode} {
  
    set switch_vendor [ _get_switch_vendor_name  $switch_name ]

    Netgear_log_file "cal-ntgr-vlan.tcl" "Add Private vlan on switch $switch_name"
    switch $switch_vendor {
        netgear {
            _ntgrAddPrivateVlanGroup $switch_name $group_name $group_mode
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
#  Function Name	: CALDeletePrivateVlanGroup
#
#  Description    : This function is an API used to del private vlan group
#         
#  Usage          : CALDeletePrivateVlanGroup <switch_name> <group_name>
# 
#*******************************************************************************
proc CALDeletePrivateVlanGroup {switch_name group_name} {
  
    set switch_vendor [ _get_switch_vendor_name  $switch_name ]

    Netgear_log_file "cal-ntgr-vlan.tcl" "Delete Private vlan on switch $switch_name"
    switch $switch_vendor {
        netgear {
            _ntgrDeletePrivateVlanGroup $switch_name $group_name
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
#  Function Name	: CALEnablePrivateVlanOnInterface
#
#  Description    : This function is an API used to enalbe private vlan group on interface
#         
#  Usage          : CALEnablePrivateVlanOnInterface <switch_name> <port_list> <name_id>
#
#  Note           : name_id: group name or group id
#
#*******************************************************************************
proc CALEnablePrivateVlanOnInterface {switch_name port_list name_id} {
  
    set switch_vendor [ _get_switch_vendor_name  $switch_name ]

    Netgear_log_file "cal-ntgr-vlan.tcl" "Enable Private vlan on switch $switch_name"
    switch $switch_vendor {
        netgear {
            _ntgrEnablePrivateVlanOnInterface $switch_name $port_list $name_id
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
#  Function Name	: CALDisablePrivateVlanOnInterface
#
#  Description    : This function is an API used to enalbe private vlan group on interface
#         
#  Usage          : CALDisablePrivateVlanOnInterface <switch_name> <port_list> <name_id>
#
#  Note           : name_id: group name or group id
#
#*******************************************************************************
proc CALDisablePrivateVlanOnInterface {switch_name port_list name_id} {
  
    set switch_vendor [ _get_switch_vendor_name  $switch_name ]

    Netgear_log_file "cal-ntgr-vlan.tcl" "Enable Private vlan on switch $switch_name"
    switch $switch_vendor {
        netgear {
            _ntgrDisablePrivateVlanOnInterface $switch_name $port_list $name_id
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
#  Function Name	: CALDeleteVlan
#
#  Description    : This function is an API used to delete a vlan.
#         
#  Usage          : CALDeleteVlan <vlan_index>
# 
#*******************************************************************************
proc CALDeleteVlan {vlan_index} {
	set vlan_members [_getVlanMembers $vlan_index]
	set vlan_id [_getVlanID $vlan_index]
	foreach switch_info $vlan_members {
		set switch_name [lindex $switch_info 0]
		CALDeleteVlanPerSwitch $switch_name $vlan_id $vlan_index
	}
}

#*******************************************************************************
#
#  Function Name  : CALCreateVlanPerSwitch
#
#  Description    : This function is called to configure a VLAN on the given switch.
#
#  Usage          : CALCreateVlanPerSwitch <switch_name vlan_index {port_info -1}>
#
#*******************************************************************************
proc CALCreateVlanPerSwitch { switch_name vlan_index {port_info -1} } {
    set switch_vendor [ _get_switch_vendor_name  $switch_name ]
    if {$port_info == -1} {
        set vlan_members [_getVlanMembers $vlan_index]
        foreach switch_info $vlan_members {
            if { $switch_name == [lindex $switch_info 0] } {
                set port_info   [lindex $switch_info 1]
                break;
            }
        }
        if {$port_info == -1} {
            Netgear_log_file "cal-ntgr-vlan.tcl" "Error: the switch $switch_name is not a member of $vlan_index."
            return 0
        }
    }

    Netgear_log_file "cal-ntgr-vlan.tcl" "Creating VLAN $vlan_index on switch $switch_name"
    switch $switch_vendor {
        netgear {
            _ntgrCreateVlan $switch_name $vlan_index $port_info
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
#  Function Name  : CALDeleteVlanPerSwitch
#
#  Description    : This function is called to delete a VLAN on the given switch.
#
#  Usage          : CALDeleteVlanPerSwitch <switch_name vlan_id>
#
#*******************************************************************************
proc CALDeleteVlanPerSwitch { switch_name vlan_id {vlan_index -1} } {
    set switch_vendor [ _get_switch_vendor_name  $switch_name ]

    Netgear_log_file "cal-ntgr-vlan.tcl" "Deleting VLAN $vlan_id on switch $switch_name"
    switch $switch_vendor {
        netgear {
            _ntgrDeleteVlan $switch_name $vlan_id $vlan_index
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
#  Function Name  : CALDeleteVlanAll
#
#  Description    : This function is called to delete all configured VLAN on a
#                   switch.
#
#  Usage          : CALDeleteVlanAll <switch_name>
#
#*******************************************************************************
proc CALDeleteVlanAll { switch_name } {
    set switch_vendor [ _get_switch_vendor_name  $switch_name ]

    Netgear_log_file "cal-ntgr-vlan.tcl" "Deleting all VLANs on switch $switch_name"
    switch $switch_vendor {
        netgear {
            _ntgrDeleteVlanAll $switch_name
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
#  Function Name  : CALCreateVlanAll
#
#  Description    : This function is called to create all vlan(s) according to
#                   the global variable(ntgr_vlanList) on the given switch.
#
#  Usage          : CALCreateVlanAll <switch_name>
#
#*******************************************************************************
proc CALCreateVlanAll { switch_name } {
    set switch_vendor [ _get_switch_vendor_name  $switch_name ]

    Netgear_log_file "cal-ntgr-vlan.tcl" "Creating all VLANs on switch $switch_name"
    switch $switch_vendor {
        netgear {
            _ntgrCreateVlanAll $switch_name
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
#  Function Name  : CALGetLFofVLAN
#
#  Description    : This function is called to get the logical interface of VLAN
#
#  Usage          : CALGetLFofVLAN <switch_name> <vlan_index_list>
#
#*******************************************************************************
proc CALGetLFofVLAN { switch_name vlan_index_list} {

    set bCnn 1
    set connection_id [_get_switch_handle $switch_name]
    if {[string first "exp" $connection_id] == -1} {
        Netgear_connect_switch $switch_name
        set connection_id [_get_switch_handle $switch_name]
        set bCnn 0
    }
    
    set switch_vendor [ _get_switch_vendor_name  $switch_name ]
    set vlanif ""

    Netgear_log_file "cal-ntgr-vlan.tcl" "Getting logical interface of VLAN $vlan_index_list on switch $switch_name"
    switch $switch_vendor {
        netgear {
            set vlanif [_ntgrGetL3LogicalIf $connection_id $vlan_index_list]
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
    if {$bCnn == 0} {
        Netgear_disconnect_switch $switch_name
    }
    Netgear_log_file "cal-ntgr-vlan.tcl" "Retrived VLAN logical interface: $vlanif"
    return $vlanif 
}

#*******************************************************************************
#
#  Function Name  : CALResetInterfacePVID
#
#  Description    : This function is called to reset PVID on interface.
#
#  Usage          : CALResetInterfacePVID <switch_name interface>
#
#*******************************************************************************
proc CALResetInterfacePVID { switch_name interface } {
    set switch_vendor [ _get_switch_vendor_name  $switch_name ]

    Netgear_log_file "cal-ntgr-vlan.tcl" "Reset interface $interface on switch $switch_name"
    switch $switch_vendor {
        netgear {
            _ntgrResetInterfacePVID $switch_name $interface
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
#  Function Name  : CALResetInterfaceVlanParticipation
#
#  Description    : This function is called to reset vlan participation on one port
#     
#
#  Usage          : CALResetInterfaceVlanParticipation <switch_name interface vlanid>
#
#*******************************************************************************
proc CALResetInterfaceVlanParticipation { switch_name interface vlanid } {
    set switch_vendor [ _get_switch_vendor_name  $switch_name ]
    
    switch $switch_vendor {
        netgear {
            _ntgrResetInterfaceVlanParticipation $switch_name $interface $vlanid
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
