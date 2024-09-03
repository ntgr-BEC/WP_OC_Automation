#!/bin/sh
# \
exec tclsh "$0"  ${1+"$@"}
####################################################################################
#
#  File Name		: TC-AddPochMember-050.tcl
#
#  Description       	: This file defines the test scripts for Removing Port Channel
#			        members 
#
#  Test case Name 	: TC-AddPochMember-050.tcl
# 
#  Revision History 	:
#         Date          Programmer         Description
#        ---------------------------------------------------------------------------
#
#
#
#
#####################################################################################

NtgrDumpLog  $NTGR_LOG_TERSE  "TC-AddPochMember-050.tcl" "******************** Starting Test case TC-AddPochMember-050.tcl ********************"
for_array_keys channel_name ntgr_addDelPochMember {
	set members [getAddDelPortChannelMember $channel_name]
	foreach member $members {
		set switch_name [lindex $member 0]
		set portlist [lindex $member 1]
		NtgrDumpLog $NTGR_LOG_TERSE  "TC-AddPochMember-050.tcl" "Adding ports $portlist from port channel($channel_name) on switch $switch_name."
		CALAddPortToChannel $switch_name $channel_name $portlist
	}
}
NtgrDumpLog  $NTGR_LOG_TERSE  "TC-AddPochMember-050.tcl" "******************** Stopping Test case TC-AddPochMember-050.tcl ********************"
