#!/bin/sh
# \
exec tclsh "$0"  ${1+"$@"}
####################################################################################
#
#  File Name		: TC-Change-Bridge-Priority-036.tcl
#
#  Description       	: This file defines the test scripts for configuring bridge priority
#	                    so that another bridge becoems root bridge.
#				  the swithces
#
#  Test case Name 	: TC-Change-Bridge-Priority-036.tcl
# 
#  Revision History 	:
#         Date          Programmer         Description
#        ---------------------------------------------------------------------------
#
#
#
#
#####################################################################################

NtgrDumpLog  $NTGR_LOG_TERSE  "TC-Change-Bridge-Priority-036.tcl" "******************** Starting Test case TC-Change-Bridge-Priority-036.tcl ********************"
for_array_keys switch_name ntgr_stpList {
	set priority [getStpBridgePriority $switch_name]
	NtgrDumpLog $NTGR_LOG_TERSE  "TC-Change-Bridge-Priority-036.tcl" "Setting the bridge's priority $priority on switch $switch_name"
	CALStpConfigBridgePriority $switch_name $priority
}
NtgrDumpLog  $NTGR_LOG_TERSE  "TC-Change-Bridge-Priority-036.tcl" "******************** Stopping Test case TC-Change-Bridge-Priority-036.tcl ********************"
