#!/bin/sh
# \
exec tclsh "$0"  ${1+"$@"}
####################################################################################
#
#  File Name		: TC-REBOOT-014.tcl
#
#  Description       	: This file defines the test scripts for reboot swithces
#
#  Test case Name 	: TC-REBOOT-014.tcl
# 
#  Revision History 	:
#         Date          Programmer         Description
#        ---------------------------------------------------------------------------
#
#
#
#
#####################################################################################


NtgrDumpLog  $NTGR_LOG_TERSE  "$NTGR_SCRIPT_NAME.tcl" "******************** Starting Test case TC-ShutPoch-017.tcl ********************"

set switchList [keylget ntgr_shutPochList SWITCH_LIST ]
set pochList [keylget ntgr_shutPochList PORT_CHANNEL_LIST ]

NtgrDumpLog  $NTGR_LOG_TERSE  "$NTGR_SCRIPT_NAME.tcl" "Port Channel Shutdown and No Shutdown in the ports of $pochList in the switch $switchList"
foreach switch $switchList pochs $pochList {
	foreach poch $pochs {
		NtgrDumpLog  $NTGR_LOG_TERSE  "$NTGR_SCRIPT_NAME.tcl" "Starting  Port Channel Shutdown in the port $poch of the switch $switch"
		CALShutPort $switch $poch
		NtgrDumpLog  $NTGR_LOG_TERSE  "$NTGR_SCRIPT_NAME.tcl" "Starting  Port Channel No Shutdown in the port $poch of the switch $switch"
		CALNoShutPort $switch $poch
	}
}
NtgrDumpLog  $NTGR_LOG_TERSE  "$NTGR_SCRIPT_NAME.tcl"  "******************** Completed Test case TC-ShutPoch-017.tcl ********************"



