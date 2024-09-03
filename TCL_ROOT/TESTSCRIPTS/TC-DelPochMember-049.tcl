#!/bin/sh
# \
exec tclsh "$0"  ${1+"$@"}
####################################################################################
#
#  File Name		: TC-DelPochMember-049.tcl
#
#  Description       	: This file defines the test scripts for Removing Port Channel
#			        members 
#
#  Test case Name 	: TC-DelPochMember-049.tcl
# 
#  Revision History 	:
#         Date          Programmer         Description
#        ---------------------------------------------------------------------------
#
#
#
#
#####################################################################################

NtgrDumpLog  $NTGR_LOG_TERSE  "TC-DelPochMember-049.tcl" "******************** Starting Test case TC-DelPochMember-049.tcl ********************"
for_array_keys channel_name ntgr_addDelPochMember {
	set members [getAddDelPortChannelMember $channel_name]
	foreach member $members {
		set switch_name [lindex $member 0]
		set portlist [lindex $member 1]
		NtgrDumpLog $NTGR_LOG_TERSE  "TC-DelPochMember-049.tcl" "Deleting ports $portlist from port channel($channel_name) on switch $switch_name."
		CALDelPortFromChannel $switch_name $channel_name $portlist
	}
}
NtgrDumpLog  $NTGR_LOG_TERSE  "TC-DelPochMember-049.tcl" "******************** Stopping Test case TC-DelPochMember-049.tcl ********************"
