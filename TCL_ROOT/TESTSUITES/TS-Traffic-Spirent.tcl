#!/bin/sh
# \
exec tclsh "$0"  ${1+"$@"}
###########################################################################
#
#  File Name		: TS-Traffic-Spirent.tcl
#
#  Description       	:
#         This TCL sends traffic between 2 Spirent ports
#
#  Test Script List 	: TC-TRAFFIC-001.tcl
#				  TC-TRAFFIC-002.tcl
#				  TC-TRAFFIC-003.tcl
#				  TC-TRAFFIC-004.tcl
#				  TC-TRAFFIC-005.tcl
#				  TC-TRAFFIC-006.tcl
#				  TC-TRAFFIC-007.tcl
#
#  CFG file Name 		: TC-TRAFFIC-001.cfg
#				  TC-TRAFFIC-002.cfg
#				  TC-TRAFFIC-003.cfg
#				  TC-TRAFFIC-004.cfg
#				  TC-TRAFFIC-005.cfg
#				  TC-TRAFFIC-006.cfg
#				  TC-TRAFFIC-007.cfg
#
#  Usage                : TS-Traffic-Spirent.tcl
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
set NTGR_SCRIPT_NAME   	"TS-Traffic-Spirent"

#List the Switches and Tester Used in this Test Suite
keylset ntgr_productList			\
	SWITCH_LIST		{}			\
	TESTER_LIST		{S2}			\

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
source $NTGR_CONFIG_PATH/TC-TRAFFIC-001.cfg
source $NTGR_TEST_SCRIPT_PATH/TC-TRAFFIC-001.tcl
if {$NTGR_TEST_RESULT == TRUE} {
	set NTGR_TEST_RESULT FALSE
} else {
	incr NTGR_SUITE_RESULT
	lappend NTGR_TEST_LIST "TC-TRAFFIC-001.tcl"
}

# Execute Test Case 2
source $NTGR_CONFIG_PATH/TC-TRAFFIC-002.cfg
source $NTGR_TEST_SCRIPT_PATH/TC-TRAFFIC-002.tcl
if {$NTGR_TEST_RESULT == TRUE} {
	set NTGR_TEST_RESULT FALSE
} else {
	incr NTGR_SUITE_RESULT
	lappend NTGR_TEST_LIST "TC-TRAFFIC-002.tcl"
}

# Execute Test Case 3
source $NTGR_CONFIG_PATH/TC-TRAFFIC-003.cfg
source $NTGR_TEST_SCRIPT_PATH/TC-TRAFFIC-003.tcl
if {$NTGR_TEST_RESULT == TRUE} {
	set NTGR_TEST_RESULT FALSE
} else {
	incr NTGR_SUITE_RESULT
	lappend NTGR_TEST_LIST "TC-TRAFFIC-003.tcl"
}

# Execute Test Case 4
source $NTGR_CONFIG_PATH/TC-TRAFFIC-004.cfg
source $NTGR_TEST_SCRIPT_PATH/TC-TRAFFIC-004.tcl
if {$NTGR_TEST_RESULT == TRUE} {
	set NTGR_TEST_RESULT FALSE
} else {
	incr NTGR_SUITE_RESULT
	lappend NTGR_TEST_LIST "TC-TRAFFIC-004.tcl"
}

# Execute Test Case 5
source $NTGR_CONFIG_PATH/TC-TRAFFIC-005.cfg
source $NTGR_TEST_SCRIPT_PATH/TC-TRAFFIC-005.tcl
if {$NTGR_TEST_RESULT == TRUE} {
	set NTGR_TEST_RESULT FALSE
} else {
	incr NTGR_SUITE_RESULT
	lappend NTGR_TEST_LIST "TC-TRAFFIC-005.tcl"
}

# Execute Test Case 6
source $NTGR_CONFIG_PATH/TC-TRAFFIC-006.cfg
source $NTGR_TEST_SCRIPT_PATH/TC-TRAFFIC-006.tcl
if {$NTGR_TEST_RESULT == TRUE} {
	set NTGR_TEST_RESULT FALSE
} else {
	incr NTGR_SUITE_RESULT
	lappend NTGR_TEST_LIST "TC-TRAFFIC-006.tcl"
}

# Execute Test Case 7
source $NTGR_CONFIG_PATH/TC-TRAFFIC-007.cfg
source $NTGR_TEST_SCRIPT_PATH/TC-TRAFFIC-007.tcl
if {$NTGR_TEST_RESULT == TRUE} {
	set NTGR_TEST_RESULT FALSE
} else {
	incr NTGR_SUITE_RESULT
	lappend NTGR_TEST_LIST "TC-TRAFFIC-007.tcl"
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