#!/bin/sh
# \
exec tclsh "$0"  ${1+"$@"}
####################################################################################
#
#  File Name		: TC-ChangeMTU-047.tcl
#
#  Description       	: This file defines the test scripts for setting mtu of the 
#				  switch ports.
#
#  Test case Name 	: TC-ChangeMTU-047.tcl
# 
#  Revision History 	:
#         Date          Programmer         Description
#        ---------------------------------------------------------------------------
#
#
#
#
#####################################################################################

NtgrDumpLog  $NTGR_LOG_TERSE  "TC-ChangeMTU-047.tcl" "******************** Starting Test case TC-ChangeMTU-047.tcl ********************"

for_array_keys switch ntgr_switchPortMtu {
	set portList [keylget ntgr_switchPortMtu($switch) PORT_LIST ]
	set mtuList [keylget ntgr_switchPortMtu($switch) MTU_LIST ]
	NtgrDumpLog  $NTGR_LOG_TERSE  "TC-ChangeMTU-047.tcl" "Changing MTU of the switch $switch port $portList "
	foreach port $portList mtu $mtuList {
		NtgrDumpLog  $NTGR_LOG_TERSE  "TC-ChangeMTU-047.tcl" "Settomg MTU of the switch $switch port $port as $mtu"
		CALSetMTU $switch $port $mtu
	}
}
NtgrDumpLog  $NTGR_LOG_TERSE  "TC-ChangeMTU-047.tcl" "******************** Stopping Test case TC-ChangeMTU-047.tcl ********************"


