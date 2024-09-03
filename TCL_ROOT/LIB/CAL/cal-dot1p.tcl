#!/bin/sh
################################################################################   
#
#  File Name		: cal-dot1p.tcl
#
#  Description       	:
#         This file contain CLI Abstraction layer for 802.1P and traffic class mapping
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
#  Function Name	: CALGlobalDot1PClassMap
#
#  Description    : CAL for configuring Dot1P to class map
#		     	
#         
#  Usage          : CALGlobalDot1PClassMap <switch_name> <ntgrDot1P_array> 
# 
#*******************************************************************************

proc CALGlobalDot1PClassMap {switch_name ntgrDot1P_array} {
upvar $ntgrDot1P_array _ntgrDOT1PSwitchList 

# Now test whether there is an entry for the switch
# if not then return

	set p_to_class  [ntgrGetGlobalDot1PtoClassList $switch_name _ntgrDOT1PSwitchList]
	puts $p_to_class
	if {$p_to_class == -1} {
		Netgear_log_file "cal-dot1p.tcl" "unknown switch $switch_name"
		return -1
	}

# Now get the switch type
# 
	set switch_vendor [_get_switch_vendor_name $switch_name]

# Now connect to the switch and extract the handle
#

	Netgear_connect_switch $switch_name
	set switch_handle [_get_switch_handle $switch_name]
	puts "$switch_handle"

# go through each of the dot1p and class and configure in the switch

	foreach ik $p_to_class {
		switch $switch_vendor {
			netgear	{
				_ntgrGlobalDot1PClassMap $switch_handle [ntgrGetDot1PFromSwitchList $ik] [ntgrGetClassFromSwitchList $ik]	
			} 
			cisco		{
				puts "TODO\n"
			} 
			hp		{
				puts "TODO\n"
			} 
			3com		{
				puts "TODO\n"
			} 
			default		{
				Netgear_log_file "cal-dot1p.tcl" "Switch not defined"
			}
		} 
	}
	Netgear_disconnect_switch $switch_name

}
#*******************************************************************************
#
#  Function Name	: CALNOGlobalDot1PClassMap
#
#  Description    : CAL for configuring Dot1P to class map
#		     	
#         
#  Usage          : CALNOGlobalDot1PClassMap <switch_name> <ntgrDot1P_array > 
# 
#*******************************************************************************

proc CALNOGlobalDot1PClassMap {switch_name ntgrDot1P_array } {

upvar $ntgrDot1P_array _ntgrDOT1PSwitchList 

# Now test whether there is an entry for the switch
# if not then return

	set p_to_class  [ntgrGetGlobalDot1PtoClassList $switch_name _ntgrDOT1PSwitchList]
	puts $p_to_class
	if {$p_to_class == -1} {
		Netgear_log_file "cal-dot1p.tcl" "unknown switch $switch_name"
		return -1
	}

# Now get the switch type
# 
	set switch_vendor [_get_switch_vendor_name $switch_name]

# Now connect to the switch and extract the handle
#

	Netgear_connect_switch $switch_name
	set switch_handle [_get_switch_handle $switch_name]
	puts "$switch_handle"

# go through each of the dot1p and class and configure in the switch

	foreach ik $p_to_class {
		switch $switch_vendor {
			netgear	{
				_ntgrNOGlobalDot1PClassMap  $switch_handle [ntgrGetDot1PFromSwitchList $ik]	
			} 
			cisco		{
				puts "TODO\n"
			} 
			hp		{
				puts "TODO\n"
			} 
			3com		{
				puts "TODO\n"
			} 
			default		{
				Netgear_log_file "cal-dot1p.tcl" "Switch not defined"
			}
		} 
	}
	Netgear_disconnect_switch $switch_name


}
#*******************************************************************************
#
#  Function Name	: CALPortDot1PClassMap 
#
#  Description    : CAL for configuring Dot1P to class map at port level
#		     	
#         
#  Usage          :  CALPortDot1PClassMap <switch_name> <ntgrDot1P_array> 
# 
#*******************************************************************************
proc CALPortDot1PClassMap {switch_name ntgrDot1P_array} {

upvar $ntgrDot1P_array _ntgrDOT1PSwitchList 

# Now test whether there is an entry for the switch
# if not then return

	set p_to_class  [ntgrGetPortDot1PtoClassList $switch_name _ntgrDOT1PSwitchList]
	puts $p_to_class
	if {$p_to_class == -1} {
		Netgear_log_file "cal-dot1p.tcl" "unknown switch $switch_name"
		return -1
	}

# Now get the switch type
# 
	set switch_vendor [_get_switch_vendor_name $switch_name]

# Now connect to the switch and extract the handle
#

	Netgear_connect_switch $switch_name
	set switch_handle [_get_switch_handle $switch_name]
	puts "$switch_handle"

# go through each of the dot1p and class and configure in the switch

	foreach ik $p_to_class {
		switch $switch_vendor {
			netgear	{
			_ntgrPortDot1PClassMap $switch_handle [ntgrIFIndexFromPortList $ik] [ntgrGetDot1PFromPortList $ik] [ntgrGetClassFromPortList $ik]
	
			} 
			cisco		{
				puts "TODO\n"
			} 
			hp		{
				puts "TODO\n"
			} 
			3com		{
				puts "TODO\n"
			} 
			default		{
				Netgear_log_file "cal-dot1p.tcl" "Switch not defined"
			}
		} 
	}
	Netgear_disconnect_switch $switch_name


}

#*******************************************************************************
#
#  Function Name	: CALNOPortDot1PClassMap 
#
#  Description    : CAL for removing configuring Dot1P to class map at port level
#		     	
#         
#  Usage          :  CALNOPortDot1PClassMap <switch_name> <ntgrDot1P_array > 
# 
#*******************************************************************************
proc CALNOPortDot1PClassMap {switch_name ntgrDot1P_array} {
upvar $ntgrDot1P_array _ntgrDOT1PSwitchList 

# Now test whether there is an entry for the switch
# if not then return

	set p_to_class  [ntgrGetPortDot1PtoClassList $switch_name _ntgrDOT1PSwitchList]
	puts $p_to_class
	if {$p_to_class == -1} {
		Netgear_log_file "cal-dot1p.tcl" "unknown switch $switch_name"
		return -1
	}

# Now get the switch type
# 
	set switch_vendor [_get_switch_vendor_name $switch_name]

# Now connect to the switch and extract the handle
#

	Netgear_connect_switch $switch_name
	set switch_handle [_get_switch_handle $switch_name]
	puts "$switch_handle"

# go through each of the dot1p and class and configure in the switch

	foreach ik $p_to_class {
		switch $switch_vendor {
			netgear	{
			_ntgrNOPortDot1PClassMap $switch_handle [ntgrIFIndexFromPortList $ik] [ntgrGetDot1PFromPortList $ik]
	
			} 
			cisco		{
				puts "TODO\n"
			} 
			hp		{
				puts "TODO\n"
			} 
			3com		{
				puts "TODO\n"
			} 
			default		{
				Netgear_log_file "cal-dot1p.tcl" "Switch not defined"
			}
		} 
	}
	Netgear_disconnect_switch $switch_name





}

#*******************************************************************************
#
#  Function Name	: CALDot1PGlobalUntagPriority 
#
#  Description    : CAL for configuring Dot1P priority for untag packets
#		     	
#         
#  Usage          :   CALDot1PGlobalUntagPriority <switch_name> <ntgrDot1P_array> 
# 
#*******************************************************************************
proc CALDot1PGlobalUntagPriority {switch_name ntgrDot1P_array} {
upvar $ntgrDot1P_array _ntgrDOT1PSwitchList 

# Now test whether there is an entry for the switch
# if not then return

	set global_untag  [ntgrGetGlobalDot1PUntagPriority  $switch_name _ntgrDOT1PSwitchList]

	puts $global_untag
	if {$global_untag == -1} {
		Netgear_log_file "cal-dot1p.tcl" "unknown switch $switch_name"
		return -1
	}

# Now get the switch type
# 
	set switch_vendor [_get_switch_vendor_name $switch_name]

# Now connect to the switch and extract the handle
#

	Netgear_connect_switch $switch_name
	set switch_handle [_get_switch_handle $switch_name]
	puts "$switch_handle"

# go through each of the dot1p and class and configure in the switch

		switch $switch_vendor {
			netgear	{
			_ntgDot1PGlobalUntagPriority $switch_handle $global_untag
	
			} 
			cisco		{
				puts "TODO\n"
			} 
			hp		{
				puts "TODO\n"
			} 
			3com		{
				puts "TODO\n"
			} 
			default		{
				Netgear_log_file "cal-dot1p.tcl" "Switch not defined"
			}
		} 
	Netgear_disconnect_switch $switch_name

}
#*******************************************************************************
#
#  Function Name	: CALNODot1PGlobalUntagPriority 
#
#  Description    : CAL for removing Dot1P priority for untag packets
#		     	
#         
#  Usage          :   CALNODot1PGlobalUntagPriority <switch_name>
# 
#*******************************************************************************

proc CALNODot1PGlobalUntagPriority switch_name {
upvar $ntgrDot1P_array _ntgrDOT1PSwitchList 

# Now get the switch type
# 
	set switch_vendor [_get_switch_vendor_name $switch_name]

# Now connect to the switch and extract the handle
#

	Netgear_connect_switch $switch_name
	set switch_handle [_get_switch_handle $switch_name]
	puts "$switch_handle"

# go through each of the dot1p and class and configure in the switch

		switch $switch_vendor {
			netgear	{
				_ntgNODot1PGlobalUntagPriority $switch_handle
	
			} 
			cisco		{
				puts "TODO\n"
			} 
			hp		{
				puts "TODO\n"
			} 
			3com		{
				puts "TODO\n"
			} 
			default		{
				Netgear_log_file "cal-dot1p.tcl" "Switch not defined"
			}
		} 
	Netgear_disconnect_switch $switch_name


}
#*******************************************************************************
#
#  Function Name	: CALPortDot1PUntagPriority 
#
#  Description    : CAL for configuring Dot1P priority for untag packets
#			at each interface
#		     	
#         
#  Usage          :   CALPortDot1PUntagPriority <switch_name> <ntgrDot1P_array> 
# 
#*******************************************************************************


proc CALPortDot1PUntagPriority {switch_name ntgrDot1P_array} {
upvar $ntgrDot1P_array _ntgrDOT1PSwitchList 

# Now test whether there is an entry for the switch
# if not then return

	set port_untag  [ntgrPortDot1PUntagPriorityList   $switch_name _ntgrDOT1PSwitchList]
	
	if {$port_untag == -1} {
		Netgear_log_file "cal-dot1p.tcl"  "unknown switch $switch_name"
		return -1
	}

# Now get the switch type
# 
	set switch_vendor [_get_switch_vendor_name $switch_name]

# Now connect to the switch and extract the handle
#

	Netgear_connect_switch $switch_name
	set switch_handle [_get_switch_handle $switch_name]
	puts "$switch_handle"

# go through each of port and configure the untag priority

	foreach ik $port_untag {
		switch $switch_vendor {
			netgear	{
				_ntgrPortDot1PUntagPriority $switch_handle [ntgrIFIndexFromPortList $ik] [ntgrGetPriorityFromPortList $ik]

			} 
			cisco		{
				puts "TODO\n"
			} 
			hp		{
				puts "TODO\n"
			} 
			3com		{
				puts "TODO\n"
			} 
			default		{
				Netgear_log_file "cal-dot1p.tcl" "Switch not defined"
			}
		} 
	}
	Netgear_disconnect_switch $switch_name

}

#*******************************************************************************
#
#  Function Name	: CALPortDot1PNOUntagPriority 
#
#  Description    : CAL for removing Dot1P priority for untag packets
#			at each interface
#		     	
#         
#  Usage          :   CALPortDot1PNOUntagPriority <switch_name> 
# 
#*******************************************************************************


proc CALPortDot1PNOUntagPriority {switch_name IfName} {
upvar $ntgrDot1P_array _ntgrDOT1PSwitchList 

# Now test whether there is an entry for the switch
# if not then return

	set port_untag  [ntgrPortDot1PUntagPriorityList   $switch_name _ntgrDOT1PSwitchList]
	
	if {$port_untag == -1} {
		Netgear_log_file "cal-dot1p.tcl"  "unknown switch $switch_name"
		return -1
	}

# Now get the switch type
# 
	set switch_vendor [_get_switch_vendor_name $switch_name]

# Now connect to the switch and extract the handle
#

	Netgear_connect_switch $switch_name
	set switch_handle [_get_switch_handle $switch_name]
	puts "$switch_handle"

# go through each of port and remove the untag priority

	foreach ik $port_untag {
		switch $switch_vendor {
			netgear	{
				_ntgrPortDot1PNOUntagPriority $switch_handle [ntgrIFIndexFromPortList $ik]

			} 
			cisco		{
				puts "TODO\n"
			} 
			hp		{
				puts "TODO\n"
			} 
			3com		{
				puts "TODO\n"
			} 
			default		{
				Netgear_log_file "cal-dot1p.tcl" "Switch not defined"
			}
		} 
	}
	Netgear_disconnect_switch $switch_name


}

