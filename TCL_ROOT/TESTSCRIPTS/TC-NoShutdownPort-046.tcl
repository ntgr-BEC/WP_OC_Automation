#!/bin/sh
# \
exec tclsh "$0"  ${1+"$@"}
####################################################################################
#
#  File Name		: TC-NoShutdownPort-046.tcl
#
#  Description       	: This file defines the test scripts for the ports in the 
#				  switches to be no shutdown.
#
#  Test case Name 	: TC-NoShutdownPort-046.tcl
# 
#  Revision History 	:
#         Date          Programmer         Description
#        ---------------------------------------------------------------------------
#
#
#
#
#####################################################################################


NtgrDumpLog  $NTGR_LOG_TERSE  "TC-NoShutdownPort-046.tcl" "******************** Starting Test case TC-NoShutdownPort-046.tcl********************"

set switchList [keylget ntgr_shutPortList SWITCH_LIST ]
set portList [keylget ntgr_shutPortList PORT_LIST ]

NtgrDumpLog  $NTGR_LOG_TERSE  "TC-NoShutdownPort-046.tcl" "No Shutting down the ports $portList in the switch $switchList"
foreach switch $switchList ports $portList {
	foreach port $ports {
		NtgrDumpLog  $NTGR_LOG_TERSE  "TC-NoShutdownPort-046.tcl" "Starting no shutting down the port $port in the switch $switch"
		CALNoShutPort $switch $port
	}
}
NtgrDumpLog  $NTGR_LOG_TERSE  "TC-NoShutdownPort-046.tcl"  "******************** Completed Test case TC-ShutPoch-017.tcl ********************"

