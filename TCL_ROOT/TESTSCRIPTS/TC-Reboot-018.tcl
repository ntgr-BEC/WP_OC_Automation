#!/bin/sh
# \
exec tclsh "$0"  ${1+"$@"}
####################################################################################
#
#  File Name		: TC-Reboot-018.tcl
#
#  Description       	: This file defines the test scripts for reboot swithces
#
#  Test case Name 	: TC-Reboot-018.tcl
# 
#  Revision History 	:
#         Date          Programmer         Description
#        ---------------------------------------------------------------------------
#
#
#
#
#####################################################################################

NtgrDumpLog  $NTGR_LOG_TERSE  "$NTGR_SCRIPT_NAME.tcl" "******************** Starting Test case TC-Reboot-018.tcl ********************"

foreach switch [keylget ntgr_rebootList SWITCH_LIST ] {
	NtgrDumpLog  $NTGR_LOG_TERSE  "$NTGR_SCRIPT_NAME.tcl" "Rebooting the switch $switch" 
	CALRebootSwitch $switch
}

NtgrDumpLog  $NTGR_LOG_TERSE  "$NTGR_SCRIPT_NAME.tcl" "Completed Test case TC-Reboot-018.tcl ********************"


