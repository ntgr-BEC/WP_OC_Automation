#!/bin/sh
# \
exec tclsh "$0"  ${1+"$@"}
###########################################################################
#
#  File Name		: TS-SntpFunc.tcl
#
#  Description       	:
#         This TCL tests SNTP functionality
#
#  Test Script List 	: TC-SNTP-013.tcl
#
#  CFG file Name 		: TC-SNTP-013.cfg
#
#  Usage                : TS-SntpFunc.tcl
# 
#  Revision History 	:
#         Date        Programmer        Description
#        ----------------------------------------------------------------------
#        02-May-06    Rajeswari V       Created
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
set NTGR_SCRIPT_NAME   	"TS-SntpFunc"

#List the Switches and Tester Used in this Test Suite
keylset ntgr_productList			\
	SWITCH_LIST		{A1}			\
	TESTER_LIST		{}

#*******************  End of Global Variable Declaration Section ***************

#***********************  Start of LOG Definition Section **********************

#######  define Log Options - log flag has the follwing values
# 1 - CONSOLE
# 2 - FILE
# 4 - REMOTE FILE - host, login name, password and log file path to be specified
# 8 - TEST DIRECTOR - host, login name, password and log file path to be specified
#######

array set ntgr_logOptions {
	LOG_FLAG		2
	LOG_HOST      	""
	LOG_HOST_PATH	""
	LOG_USER_NAME 	""
	LOG_USER_PASSWD 	""
	LOG_FD		0
}

#*******************  End of Log Definition Section ***********************

#Start the log
Netgear_start_log

Netgear_log_file "$NTGR_SCRIPT_NAME.tcl" "-------------------------------------------------------------------------"
Netgear_log_file "$NTGR_SCRIPT_NAME.tcl" "                Started Test Suite $NTGR_SCRIPT_NAME"
Netgear_log_file "$NTGR_SCRIPT_NAME.tcl" "-------------------------------------------------------------------------"

#***********************  Start of Topology Reachability Section **********************

APICheckProductReachable

#******************  End of Topology Reachability Section ************************

#******************  Start of Test Suite *****************************************

# Execute Test Case 1
source $NTGR_CONFIG_PATH/TC-SNTP-013.cfg
source $NTGR_TEST_SCRIPT_PATH/TC-SNTP-013.tcl
if {$NTGR_TEST_RESULT == TRUE} {
	set NTGR_TEST_RESULT FALSE
} else {
	incr NTGR_SUITE_RESULT
	lappend NTGR_TEST_LIST "TC-SNTP-013.tcl"
}

#Compare the results
if {$NTGR_SUITE_RESULT == 0} {
	Netgear_log_file "$NTGR_SCRIPT_NAME.tcl" "-------------------------------------------------------------------------"
	Netgear_log_file "$NTGR_SCRIPT_NAME.tcl" "                   Test Suite $NTGR_SCRIPT_NAME PASSED"
	Netgear_log_file "$NTGR_SCRIPT_NAME.tcl" "-------------------------------------------------------------------------"
} else {
	Netgear_log_file "$NTGR_SCRIPT_NAME.tcl" "-------------------------------------------------------------------------"
	Netgear_log_file "$NTGR_SCRIPT_NAME.tcl" "                   Test Suite $NTGR_SCRIPT_NAME FAILED"
	Netgear_log_file "$NTGR_SCRIPT_NAME.tcl" "          Failed Reason : TEST CASES $NTGR_TEST_LIST Failed"
	Netgear_log_file "$NTGR_SCRIPT_NAME.tcl" "-------------------------------------------------------------------------"
}

Netgear_log_file "$NTGR_SCRIPT_NAME.tcl" "-------------------------------------------------------------------------"
Netgear_log_file "$NTGR_SCRIPT_NAME.tcl" "                Completed Test Suite $NTGR_SCRIPT_NAME"
Netgear_log_file "$NTGR_SCRIPT_NAME.tcl" "-------------------------------------------------------------------------"

#stop the log
Netgear_stop_log

#******************  End of Test Suite ************************************************************************