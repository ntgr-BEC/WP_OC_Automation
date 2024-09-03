#!/bin/sh
################################################################################   
#
#  File Name			: lib-vlan-support.tcl
#
#  Description			:
#         This TCL contains functions commonly used for vlan.
#
#  Revision History 	:
#        Date          Programmer        Description
#        -----------------------------------------------------------------------
#        29-May-06     Scott Zhang       Created
#
#
#
#
################################################################################

#*******************************************************************************
#
#  Function Name	: _getVlanName
#
#  Description    : This function is called to get the vlan name from 
#	                 the global ntgr_vlanList
#         
#  Usage          : _getVlanName <vlan_index>
# 
#*******************************************************************************
proc _getVlanName {vlan_index} {
	global ntgr_vlanList
	return [keylget ntgr_vlanList($vlan_index) SWITCH_VLAN_NAME]
}

#*******************************************************************************
#
#  Function Name	: _getVlanID
#
#  Description    : This function is called to get the vlan id from 
#	                 the global ntgr_vlanList
#         
#  Usage          : _getVlanID <vlan_index>
# 
#*******************************************************************************
proc _getVlanID {vlan_index} {
	global ntgr_vlanList
	return [keylget ntgr_vlanList($vlan_index) SWITCH_VLAN_ID]
}

#*******************************************************************************
#
#  Function Name	: _getVlanIndexByName
#
#  Description    : This function is called to get vlan index according to 
#	                 the specified vlan name from the global ntgr_vlanList.
#         
#  Usage          : _getVlanIndexByName <vlan_name>
# 
#*******************************************************************************
proc _getVlanIndexByName {vlan_name} {
    global ntgr_vlanList
    for_array_keys index ntgr_vlanList {
        if {$vlan_name == [_getVlanName $index]} {
            return $index
        }
    }
    return ""
}

#*******************************************************************************
#
#  Function Name	: _getVlanIndexByID
#
#  Description    : This function is called to get vlan index according to 
#	                 the specified vlan id from the global ntgr_vlanList.
#         
#  Usage          : _getVlanIndexByID <vlan_id>
# 
#*******************************************************************************
proc _getVlanIndexByID {vlan_id} {
    global ntgr_vlanList
    for_array_keys index ntgr_vlanList {
        if {$vlan_id == [_getVlanID $index]} {
            return $index
        }
    }
    return ""
}

#*******************************************************************************
#
#  Function Name	: _getVlanMode
#
#  Description    : This function is called to get the vlan mode from 
#			           the global ntgr_vlanList
#         
#  Usage          : _getVlanMode <vlan_index> <switch_name>
# 
#*******************************************************************************
proc _getVlanMode {vlan_index switch_name} {
	global ntgr_vlanList

	set properties [keylget ntgr_vlanList($vlan_index) SWITCH_VLAN_SWITCH_PROPERTIES]
	foreach property $properties {
		if {$switch_name == [lindex $property 0]} {
			return [lindex $property 1]
		}
	}
	return ""
}

#*******************************************************************************
#
#  Function Name	: _getVlanIPAddr
#
#  Description    : This function is called to get the vlan IP address from 
#			  the global ntgr_vlanList
#         
#  Usage          : _getVlanIPAddr <vlan_index> <switch_name>
# 
#*******************************************************************************
proc _getVlanIPAddr {vlan_index switch_name} {
	global ntgr_vlanList
	set properties [keylget ntgr_vlanList($vlan_index) SWITCH_VLAN_SWITCH_PROPERTIES]
	foreach property $properties {
		if {$switch_name == [lindex $property 0]} {
			return [lindex $property 2]
		}
	}
	return ""
}

#*******************************************************************************
#
#  Function Name	: _getVlanIPMask
#
#  Description    : This function is called to get the vlan IP address mask from 
#			  the global ntgr_vlanList
#         
#  Usage          : _getVlanIPMask <vlan_index> <switch_name>
# 
#*******************************************************************************
proc _getVlanIPMask {vlan_index switch_name} {
	global ntgr_vlanList
	set properties [keylget ntgr_vlanList($vlan_index) SWITCH_VLAN_SWITCH_PROPERTIES]
	foreach property $properties {
		if {$switch_name == [lindex $property 0]} {
			return [lindex $property 3]
		}
	}
	return ""
}

#*******************************************************************************
#
#  Function Name	: _getVlanLogicalIfName
#
#  Description    : This function is called to retrieve the logical interface
#                   name of a layer3 vlan from ntgr_vlanList,which should be
#                   initialze after creating the vlan.
#         
#  Usage          : _getVlanLogicalIfName <vlan_index> <switch_name>
# 
#*******************************************************************************
proc _getVlanLogicalIfName {vlan_index switch_name} {
	global ntgr_vlanList
	set properties [keylget ntgr_vlanList($vlan_index) SWITCH_VLAN_SWITCH_PROPERTIES]
	foreach property $properties {
		if {$switch_name == [lindex $property 0]} {
			return [lindex $property 4]
		}
	}
	return ""
}

#*******************************************************************************
#
#  Function Name	: _setVlanLogicalIfName
#
#  Description    : This function is used to save the given VLAN's
#                   logical interface name in the global variable
#                   'ntgr_vlanList'.
#         
#  Usage          : _setVlanLogicalIfName <switch_name> <channel_name> <logical_if_name>
# 
#*******************************************************************************
proc _setVlanLogicalIfName {switch_name vlan_index logical_if_name} {
	global ntgr_vlanList
	set properties [keylget ntgr_vlanList($vlan_index) SWITCH_VLAN_SWITCH_PROPERTIES]
	set new_properties {}
	foreach property $properties {
		if {$switch_name == [lindex $property 0]} {
			lappend new_properties [lreplace $property 4 4 $logical_if_name]
		} else {
			lappend new_properties $property
		}
	}
	
	keylset ntgr_vlanList($vlan_index) SWITCH_VLAN_SWITCH_PROPERTIES $new_properties	
}


#*******************************************************************************
#
#  Function Name	: _getVlanIfEnableIPv6
#
#  Description    : This function is called to retrieve if enable IPv6
#                   of a layer3 vlan from ntgr_vlanList,which should be
#                   initialze after creating the vlan.
#         
#  Usage          : _getVlanLogicalIfName <vlan_index> <switch_name>
# 
#*******************************************************************************
proc _getVlanIfEnableIPv6 {vlan_index switch_name} {
	global ntgr_vlanList
	set properties [keylget ntgr_vlanList($vlan_index) SWITCH_VLAN_SWITCH_PROPERTIES]
	foreach property $properties {
		if {$switch_name == [lindex $property 0]} {
           if { [llength $property]>=6 } {
			return [lindex $property 5]
          } else {
            return ""
          }
		}
	}
	return ""
}


#*******************************************************************************
#
#  Function Name	: _getVlanMembers
#
#  Description    : This function is called to get the vlan members from 
#			  the global ntgr_vlanList
#         
#  Usage          : _getVlanMembers <vlan_index>
# 
#*******************************************************************************
proc _getVlanMembers {vlan_index} {
	global ntgr_vlanList
	return [keylget ntgr_vlanList($vlan_index) SWITCH_VLAN_MEMBER]
}

#*******************************************************************************
#
#  Function Name  : _IsVlanExist
#
#  Description    : This function is called to check if the given vlan index  
#                   is a valid vlan index in the vlan configuration file.
#
#  Usage          : _IsVlanExist <index>
#
#  Return value   : 0:invalid,1:valid
#
#*******************************************************************************
proc _IsVlanExist {vlan_index} {
    global ntgr_vlanList
    if { [array exists ntgr_vlanList] == 0 || [lsearch [array names ntgr_vlanList] $vlan_index]==-1 } {
        return 0
    } else {
        return 1
    }
}


