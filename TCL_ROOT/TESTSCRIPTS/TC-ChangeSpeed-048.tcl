#!/bin/sh
# \
exec tclsh "$0"  ${1+"$@"}
####################################################################################
#
#  File Name		: TC-ChangeSpeed-048.tcl
#
#  Description       	: This file defines the test scripts for setting Spped of the 
#				  switch ports.
#
#  Test case Name 	: TC-ChangeSpeed-048.tcl
# 
#  Revision History 	:
#         Date          Programmer         Description
#        ---------------------------------------------------------------------------
#
#
#
#
#####################################################################################

NtgrDumpLog  $NTGR_LOG_TERSE  "TC-ChangeSpeed-048.tcl" "******************** Starting Test case TC-ChangeSpeed-048.tcl ********************"

for_array_keys switch ntgr_switchPortSpeed {
	set portList [keylget ntgr_switchPortSpeed($switch) PORT_LIST ]
	set speedDuplexList [keylget ntgr_switchPortSpeed($switch) SPEED_DUPLEX_LIST ]
	NtgrDumpLog  $NTGR_LOG_TERSE  "TC-ChangeSpeed-048.tcl" "Changing Speed of the switch $switch port $portList "
	foreach port $portList speedDuplex $speedDuplexList {
		set speed [lindex $speedDuplex 0 ]
		set duplex [lindex $speedDuplex 1 ]
		NtgrDumpLog  $NTGR_LOG_TERSE  "TC-ChangeSpeed-048.tcl" "Setting Speed of the switch $switch port $port as $speed and duplex as $duplex"
		CALSetSpeed $switch $port $speed $duplex
	}
}
NtgrDumpLog  $NTGR_LOG_TERSE  "TC-ChangeSpeed-048.tcl" "******************** Stopping Test case TC-ChangeSpeed-048.tcl ********************"


