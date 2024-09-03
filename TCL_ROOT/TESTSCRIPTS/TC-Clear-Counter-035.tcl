#!/bin/sh
# \
exec tclsh "$0"  ${1+"$@"}
####################################################################################
#
#  File Name		: TC-Clear-Counter-035.tcl
#
#  Description       	: This file defines the test scripts for claring mac address from 
#				  the swithces
#
#  Test case Name 	: TC-Clear-Counter-035.tcl
# 
#  Revision History 	:
#         Date          Programmer         Description
#        ---------------------------------------------------------------------------
#
#
#
#
#####################################################################################



NtgrDumpLog  $NTGR_LOG_TERSE  "TC-Clear-Counter-035.tcl" "******************** Starting Test case TC-Clear-Counter-035.tcl ********************"
foreach switch [keylget ntgr_clearCounterList SWITCH_LIST ] {
	NtgrDumpLog  $NTGR_LOG_TERSE  "TC-Clear-Counter-035.tcl" "Clearing Counters on the switch $switch "
	CALClearCounterSwitch $switch
}
NtgrDumpLog  $NTGR_LOG_TERSE  "TC-Clear-Counter-035.tcl" "******************** Stopping Test case TC-Clear-Counter-035.tcl ********************"


