#!/bin/sh
# \
exec tclsh "$0"  ${1+"$@"}
###########################################################################
#
#  File Name		: TS-Regression.tcl
#
#  Description       	: This TCL configures Port Channel, Spanning Tree, VLAN and 
#                         Static Routes and generates traffic.  
#
#  Test Script List 	: TC-Regression-001.tcl
#
#  CFG file Name 		: TC-Regression-001.cfg
#
#  Usage                : TS-Regression.tcl
# 
#  Revision History 	:
#         Date        Programmer        Description
#        ----------------------------------------------------------------------
#
#
#
#
################################################################################

#*******************  Start of Header File Include Section *********************

source common_env.tcl

#*******************  End of Header File Include Section ***********************

#******************* Start of Global Variable Declaration Section **************

# set the script name
set NTGR_SCRIPT_NAME   	"TS-Regression"

#List the Switches and Tester Used in this Test Suite
keylset ntgr_productList			\
	SWITCH_LIST		{A1 A2 A3 A4 B1 B2 B3 B4 C1 C2 C3 C4 S1 S2}\
	TESTER_LIST		{S2 I1 I2}

#*******************  End of Global Variable Declaration Section ***************

#***********************  Start of LOG Definition Section **********************

#######  define Log Options - log flag has the follwing values
# 1 	- CONSOLE
# 2 	- FILE
# 4 	- REMOTE FILE - host, login name, password and log file path to be specified
# 8 	- TEST DIRECTOR - host, login name, password and log file path to be specified
#16 	- LOG Error Messages
#32 	- LOG TERSE Messages
#64 	- Log Information  Messages
#128	- Log Debug Messages
########################################################################################

array set ntgr_logOptions {
	LOG_FLAG		51
	LOG_HOST      	""
	LOG_HOST_PATH	""
	LOG_USER_NAME 	""
	LOG_USER_PASSWD 	""
	LOG_FD		0
}

#*******************  End of Log Definition Section ***********************

#Start the log
Netgear_start_log

NtgrDumpLog $NTGR_LOG_TERSE "$NTGR_SCRIPT_NAME.tcl" "-------------------------------------------------------------------------"
NtgrDumpLog $NTGR_LOG_TERSE "$NTGR_SCRIPT_NAME.tcl" "                Started Test Suite $NTGR_SCRIPT_NAME"
NtgrDumpLog $NTGR_LOG_TERSE "$NTGR_SCRIPT_NAME.tcl" "-------------------------------------------------------------------------"

#***********************  Start of Topology Reachability Section **********************

APICheckProductReachable

#******************  End of Topology Reachability Section ************************

#******************  Start of Test Suite *****************************************

# Execute Test Case 1
# Configure Switches as required in Regression
source $NTGR_CONFIG_PATH/TC-CFGREG-014.cfg
source $NTGR_TEST_SCRIPT_PATH/TC-CFGREG-014.tcl

if {$NTGR_TEST_RESULT == TRUE} {
	set NTGR_TEST_RESULT FALSE
} else {
	incr NTGR_SUITE_RESULT
	lappend NTGR_TEST_LIST "TC-CFGREG-014.tcl"
}

# Execute Test Case 2
# Generate Traffic as required in Regression
source $NTGR_CONFIG_PATH/TC-TRFREG-020.cfg
source $NTGR_TEST_SCRIPT_PATH/TC-TRFREG-020.tcl

if {$NTGR_TEST_RESULT == TRUE} {
	set NTGR_TEST_RESULT FALSE
} else {
	incr NTGR_SUITE_RESULT
	lappend NTGR_TEST_LIST "TC-TRFREG-020.tcl"
}

#Compare the results
if {$NTGR_SUITE_RESULT == 0} {
	NtgrDumpLog $NTGR_LOG_TERSE "$NTGR_SCRIPT_NAME.tcl" "-------------------------------------------------------------------------"
	NtgrDumpLog $NTGR_LOG_TERSE "$NTGR_SCRIPT_NAME.tcl" "                   Test Suite $NTGR_SCRIPT_NAME PASSED"
	NtgrDumpLog $NTGR_LOG_TERSE "$NTGR_SCRIPT_NAME.tcl" "-------------------------------------------------------------------------"
} else {
	NtgrDumpLog $NTGR_LOG_TERSE "$NTGR_SCRIPT_NAME.tcl" "-------------------------------------------------------------------------"
	NtgrDumpLog $NTGR_LOG_TERSE "$NTGR_SCRIPT_NAME.tcl" "                   Test Suite $NTGR_SCRIPT_NAME FAILED"
	NtgrDumpLog $NTGR_LOG_TERSE "$NTGR_SCRIPT_NAME.tcl" "          Failed Reason : TEST CASES $NTGR_TEST_LIST Failed"
	NtgrDumpLog $NTGR_LOG_TERSE "$NTGR_SCRIPT_NAME.tcl" "-------------------------------------------------------------------------"
}

NtgrDumpLog $NTGR_LOG_TERSE "$NTGR_SCRIPT_NAME.tcl" "-------------------------------------------------------------------------"
NtgrDumpLog $NTGR_LOG_TERSE "$NTGR_SCRIPT_NAME.tcl" "                Completed Test Suite $NTGR_SCRIPT_NAME"
NtgrDumpLog $NTGR_LOG_TERSE "$NTGR_SCRIPT_NAME.tcl" "-------------------------------------------------------------------------"

#stop the log
Netgear_stop_log

#******************  End of Test Suite ************************************************************************