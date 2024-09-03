#!/bin/sh
# \
exec tclsh "$0"  ${1+"$@"}
####################################################################################
#
#  File Name		: TC-ShutdownPort-045.tcl
#
#  Description       	: This file defines the test scripts for the ports in the 
#				  switches to be shutdown.
#
#  Test case Name 	: TC-ShutdownPort-045.tcl
# 
#  Revision History 	:
#         Date          Programmer         Description
#        ---------------------------------------------------------------------------
#
#
#
#
#####################################################################################


NtgrDumpLog  $NTGR_LOG_TERSE  "TC-ShutdownPort-045.tcl" "******************** Starting Test case TC-ShutdownPort-045.tcl********************"

set switchList [keylget ntgr_shutPortList SWITCH_LIST ]
set portList [keylget ntgr_shutPortList PORT_LIST ]

NtgrDumpLog  $NTGR_LOG_TERSE  "TC-ShutdownPort-045.tcl" "Shutting down the ports $portList in the switch $switchList"
foreach switch $switchList ports $portList {
	foreach port $ports {
		NtgrDumpLog  $NTGR_LOG_TERSE  "TC-ShutdownPort-045.tcl" "Starting  shutting down the port $port in the switch $switch"
		CALShutPort $switch $port
	}
}
NtgrDumpLog  $NTGR_LOG_TERSE  "TC-ShutdownPort-045.tcl"  "******************** Completed Test case TC-ShutPoch-017.tcl ********************"

