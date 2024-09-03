#!/bin/sh
# \
exec tclsh "$0"  ${1+"$@"}
####################################################################################
#
#  File Name		: TC-Clear-IGMP-Group-Members-053.tcl
#
#  Description       	: This file defines the test scripts for claring IGMP Group 
#			        members from the swithces
#
#  Test case Name 	: TC-Clear-IGMP-Group-Members-053.tcl
# 
#  Revision History 	:
#         Date          Programmer         Description
#        ---------------------------------------------------------------------------
#
#
#####################################################################################

NtgrDumpLog  $NTGR_LOG_TERSE  "TC-Clear-IGMP-Group-Members-053.tcl" "******************** Starting Test case TC-Clear-IGMP-Group-Members-053.tcl ********************"
foreach switch [keylget ntgr_clearIGMPGroupMemberList SWITCH_LIST ] {
	NtgrDumpLog  $NTGR_LOG_TERSE  "TC-Clear-IGMP-Group-Members-053.tcl" "Clearing IGMP Group Members on the switch $switch "
	CALClearMacSwitch $switch
}
NtgrDumpLog  $NTGR_LOG_TERSE  "TC-Clear-IGMP-Group-Members-053.tcl" "******************** Stopping Test case TC-Clear-IGMP-Group-Members-053.tcl ********************"


