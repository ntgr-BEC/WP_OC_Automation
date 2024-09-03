#!/bin/sh
# \
exec tclsh "$0"  ${1+"$@"}
####################################################################################
#
#  File Name		: TC-Clear-Mac-016.tcl
#
#  Description       	: This file defines the test scripts for claring mac address from 
#				  the swithces
#
#  Test case Name 	: TC-Clear-Mac-016.tcl
# 
#  Revision History 	:
#         Date          Programmer         Description
#        ---------------------------------------------------------------------------
#
#
#
#
#####################################################################################



NtgrDumpLog  $NTGR_LOG_TERSE  "TC-Clear-Mac-016.tcl" "******************** Starting Test case TC-Clear-Mac-016.tcl ********************"
foreach switch [keylget ntgr_clearMacList SWITCH_LIST ] {
	NtgrDumpLog  $NTGR_LOG_TERSE  "TC-Clear-Mac-016.tcl" "Clearing MAC on the switch $switch "
	CALClearMacSwitch $switch
}
NtgrDumpLog  $NTGR_LOG_TERSE  "TC-Clear-Mac-016.tcl" "******************** Stopping Test case TC-Clear-Mac-016.tcl ********************"


