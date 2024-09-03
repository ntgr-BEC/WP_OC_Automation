#!/bin/sh
################################################################################   
#
#  File Name			: lib-poch-support.tcl
#
#  Description			:
#         This TCL contains functions commonly used for port channel.
#
#  Revision History 	:
#        Date          Programmer        Description
#        -----------------------------------------------------------------------
#        22-May-06     Scott Zhang       Created
#
#
#
#
################################################################################

#*******************************************************************************
#
#  Function Name	: getPortChannelID
#
#  Description    : This function is used to get the given port channel's
#                   id information.
#         
#  Usage          : getPortChannelID <channel_name>
# 
#*******************************************************************************
proc getPortChannelID {channel_name} {
	global ntgr_poChanList
	set id 1
	keylget ntgr_poChanList($channel_name) SWITCH_POCHAN_ID id
	return $id
}

#*******************************************************************************
#
#  Function Name	: getPortChannelMember
#
#  Description    : This function is used to get the given port channel's
#                   member(port list) information.
#         
#  Usage          : getPortChannelMember <channel_name>
# 
#*******************************************************************************
proc getPortChannelMember {channel_name} {
	global ntgr_poChanList
	return [keylget ntgr_poChanList($channel_name) SWITCH_POCHAN_MEMBER]
}


#*******************************************************************************
#
#  Function Name	: getAddDelPortChannelMember
#
#  Description    : This function is used to get the port list added or removed 
#                   from the given port channel
#         
#  Usage          : getAddDelPortChannelMember <channel_name>
# 
#*******************************************************************************
proc getAddDelPortChannelMember {channel_name} {
	global ntgr_addDelPochMember
	return [keylget ntgr_addDelPochMember($channel_name) SWITCH_POCHAN_MEMBER]
}

#*******************************************************************************
#
#  Function Name	: getChannelLogicalIfName
#
#  Description    : This function is used to get the given port channel's
#                   logical interface name from the global variable 
#                   'ntgr_poChanList'.
#         
#  Usage          : getChannelLogicalIfName <switch_name> <channel_name>
# 
#*******************************************************************************
proc getChannelLogicalIfName {switch_name channel_name} {
	global ntgr_poChanList
	set channel_switches [getPortChannelMember $channel_name]
	
	foreach sw_info $channel_switches {
		set sw_name [lindex $sw_info 0]
		if { $sw_name == $switch_name } {
			return [lindex $sw_info 2]
		}
	}
	
	return ""
}

#*******************************************************************************
#
#  Function Name	: _setChannelLogicalIfName
#
#  Description    : This function is used to save the given port channel's
#                   logical interface name in the global variable
#                   'ntgr_poChanList'.
#         
#  Usage          : _setChannelLogicalIfName <switch_name> <channel_name> <logical_if_name>
# 
#*******************************************************************************
proc _setChannelLogicalIfName {switch_name channel_name logical_if_name} {
	global ntgr_poChanList
	set channel_switches [getPortChannelMember $channel_name]
	
	set new_members {}
	foreach sw_info $channel_switches {
		set sw_name [lindex $sw_info 0]
		if { $sw_name == $switch_name } {
			lappend new_members [lreplace $sw_info 2 2 $logical_if_name]
		} else {
			lappend new_members $sw_info
		}
	}

	keylset ntgr_poChanList($channel_name) SWITCH_POCHAN_MEMBER $new_members	
}

#*******************************************************************************
#
#  Function Name  : _IsPortChannelExist
#
#  Description    : This function is called to check if the given channel index  
#                   is a valid channel index in the port channel configuration file.
#
#  Usage          : _IsPortChannelExist <index>
#
#  Return value   : 0:invalid,1:valid
#
#*******************************************************************************
proc _IsPortChannelExist {index} {
    global ntgr_poChanList
    if { [array exists ntgr_poChanList] == 0 || [lsearch [array names ntgr_poChanList] $index]==-1 } {
        return 0
    } else {
        return 1
    }
}
