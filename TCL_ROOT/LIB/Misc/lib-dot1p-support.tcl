#!/bin/sh
################################################################################   
#
#  File Name		: lib-dot1p-support.tcl
#
#  Description       	:
#         This file contain support procedures for 802.1P and traffic class mapping
#
#  Revision History 	:
#         Date         Programmer        Description
#        -----------------------------------------------------------------------
#        05/21/06		Tissa			Initial version
#
#
#
#
################################################################################


#*******************************************************************************
#
#  Function Name	: 
#
#  Description    :
#		     	
#         
#  Usage          : 
# 
#*******************************************************************************

#--------- Library Support Functions -----------------------------
# These functions allow to extract various different list elements
# from the key array that contain various dot1p parameters
#---------------------------------------------------------------

proc _ntgrGetGlobal1PtoClassList {switch_id dot1p_array} {
upvar $dot1p_array _array
   return [keylget _array($switch_id) DOT1P_GLOBAL_1P_TO_CLASS ]  
}

proc _ntgrGetPort1PtoClassList {switch_id dot1p_array} {
upvar $dot1p_array _array
   return [keylget _array($switch_id) DOT1P_PORT_1P_TO_CLASS ]  

}

proc _ntgrGetSwitchNameDot1P {switch_id dot1p_array} {
upvar $dot1p_array _array
   return [keylget _array($switch_id)  DOT1P_SWITCH_NAME]  
}

proc _ntgrGetGlobal1PUntagPriority {switch_id dot1p_array} {
upvar $dot1p_array _array
   return [keylget _array($switch_id) DOT1P_GLOBAL_UNTAG_VLAN_PRIORITY]  

}

proc _ntgrGetPort1PUntagPriorityList {switch_id dot1p_array} {
upvar $dot1p_array _array
   return [keylget _array($switch_id) DOT1P_PORT_UNTAG_VLAN_PRIORITY]  

}

#----------------------List extraction elements--------------------
# Following functions will extract Dot1P, class and Port_id
# from variou lists
#-----------------------------------------------------------------

proc ntgrGetDot1PFromPortList {portList} {

	return [lindex $portList 1]
}

proc ntgrGetClassFromPortList {portList} {

	return [lindex $portList 2]

}

proc ntgrIFIndexFromPortList {portList} {

return [lindex $portList 0]

}

proc ntgrGetDot1PFromSwitchList {swList} {

	return [lindex $swList 0]
}

proc ntgrGetClassFromSwitchList {swList} {

	return [lindex $swList 1]

}

proc ntgrGetPriorityFromPortList {swList} {

	return [lindex $swList 1]

}


#------------------------------------------------------------
# This routine take name of the switch and go through the list
# ntgrDOT1PSwitchList. return -1 if no information found for the switch 
# name. If switch name is found it will return the entire list for
# 1P to Class mapping. Each element in the list as format 
# { p-value class}
#------------------------------------------------------------
proc ntgrGetGlobalDot1PtoClassList {switch_name dot1p_array} {

upvar $dot1p_array _ntgrDOT1PSwitchList 
	for_array_keys switch_entry _ntgrDOT1PSwitchList {

		if {$switch_name == \
				[_ntgrGetSwitchNameDot1P $switch_entry _ntgrDOT1PSwitchList ] } {
			return [ _ntgrGetGlobal1PtoClassList  $switch_entry _ntgrDOT1PSwitchList ]
		}
		
	}
	catch {return -1}
	return -1

}

#
# This routine take name of the switch and go through the list
# ntgrDOT1PSwitchList. return -1 if no information found for the switch 
# name. If switch name is found it will return the entire list,
# that will contain series of elements. Each element in the list
# has the format {port P-value class}
#
proc ntgrGetPortDot1PtoClassList {switch_name dot1p_array} {
upvar $dot1p_array _ntgrDOT1PSwitchList 
	for_array_keys switch_entry _ntgrDOT1PSwitchList {

		if {$switch_name == \
				[_ntgrGetSwitchNameDot1P $switch_entry _ntgrDOT1PSwitchList ] } {
			return [ _ntgrGetPort1PtoClassList  $switch_entry _ntgrDOT1PSwitchList ]
		}
		
	}
	catch {return -1}
	return -1
}

#*******************************************************************************
#
#  Function Name	: ntgrGetGlobalDot1PUntagPriority
#
#  Description    : This return Untag Packet priority for the switch. -1
#		if switch name does not exist
#		     	
#         
#  Usage          : ntgrGetGlobalDot1PUntagPriority <switch_name> <dot1p_array>
# 
#*******************************************************************************

proc ntgrGetGlobalDot1PUntagPriority {switch_name dot1p_array} {
upvar $dot1p_array _ntgrDOT1PSwitchList 
	for_array_keys switch_entry _ntgrDOT1PSwitchList {

		if {$switch_name == \
				[_ntgrGetSwitchNameDot1P $switch_entry _ntgrDOT1PSwitchList ] } {
			return [\
			_ntgrGetGlobal1PUntagPriority   $switch_entry _ntgrDOT1PSwitchList ]
		}
		
	}
	catch {return -1}
	return -1
}

#*******************************************************************************
#
#  Function Name	: ntgrPortDot1PUntagPriorityList
#
#  Description    : Returns the Untag Packet priority list for each port
#			if swicth_name is not present return -1
#		     	
#         
#  Usage          : ntgrPortDot1PUntagPriorityList <switch_name> <dot1p_array>
# 
#*******************************************************************************

proc ntgrPortDot1PUntagPriorityList {switch_name dot1p_array} {
upvar $dot1p_array _ntgrDOT1PSwitchList 
	for_array_keys switch_entry _ntgrDOT1PSwitchList {

		if {$switch_name == \
				[_ntgrGetSwitchNameDot1P $switch_entry _ntgrDOT1PSwitchList ] } {
			return [ _ntgrGetPort1PUntagPriorityList $switch_entry _ntgrDOT1PSwitchList ]
		}
		
	}
	catch {return -1}
	return -1
}
