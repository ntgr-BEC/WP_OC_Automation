#!/bin/sh
# \
exec tclsh "$0"  ${1+"$@"}
###########################################################################
#
#  File Name		: TS-RIPFunc.tcl
#
#  Description       	:
#         This TCL configure RIP between 2 switches  
#
#  Test Script List 	: TC-RIP-001.tcl
#			: TC-RIP-002.tcl
#
#  CFG file Name 		: TC-RIP-001.cfg
#			  	  
#  Usage                : TS-RIPFunc.tcl
# 
#  Revision History 	:
#         Date        Programmer        Description
#        ----------------------------------------------------------------------
#        28-May-06    Nina Cheng        Created
#
#
#
#
################################################################################

#******************* Start of Global Variable Declaration Section **************

set NTGR_SCRIPT_NAME   	"TS-RIPFunc"

#*******************  End of Global Variable Declaration Section ***************

#*******************  Start of Header File Include Section *********************

source common_env.tcl

#*******************  End of Header File Include Section ***********************

#***********************  Start of LOG Definition Section **********************

#######  define Log Options - log flag has the follwing values
# 1 - CONSOLE
# 2 - FILE
# 4 - REMOTE FILE - host, login name, password and log file path to be specified
# 8 - TEST DIRECTOR - host, login name, password and log file path to be specified
#######

array set ntgr_logOptions {
	LOG_FLAG		1
	LOG_HOST      	""
	LOG_HOST_PATH	""
	LOG_USER_NAME 	""
	LOG_USER_PASSWD 	""
	LOG_FD		0
}

#Start the log
Netgear_start_log

#*******************  End of Log Definition Section ***********************

#*******************  Start of Topology Reachability Section *******************

Netgear_log_file "$NTGR_SCRIPT_NAME.tcl" "-------------------------------------------------------------------------"
Netgear_log_file "$NTGR_SCRIPT_NAME.tcl" "                Started Test Suite $NTGR_SCRIPT_NAME"
Netgear_log_file "$NTGR_SCRIPT_NAME.tcl" "-------------------------------------------------------------------------"

Netgear_log_file "$NTGR_SCRIPT_NAME.tcl" "Pinging the Switch A3"
set result [Netgear_check_switch_reachable A3]
if {$result == FALSE}  {
	Netgear_log_file "$NTGR_SCRIPT_NAME.tcl" "-------------------------------------------------------------------------"
	Netgear_log_file "$NTGR_SCRIPT_NAME.tcl" "                   Exited Test Suite $NTGR_SCRIPT_NAME"
	Netgear_log_file "$NTGR_SCRIPT_NAME.tcl" "          Exit Reason : Switch A1 is not reachable"
	Netgear_log_file "$NTGR_SCRIPT_NAME.tcl" "-------------------------------------------------------------------------"
	Netgear_stop_log
	exit 0
} else {
	Netgear_log_file "$NTGR_SCRIPT_NAME.tcl" "Ping  Successful to switch A1"
}

Netgear_log_file "$NTGR_SCRIPT_NAME.tcl" "Pinging the Switch A2"
#set result [Netgear_check_switch_reachable A2]
#if {$result == FALSE}  {
#	Netgear_log_file "$NTGR_SCRIPT_NAME.tcl" #"-------------------------------------------------------------------------"
#	Netgear_log_file "$NTGR_SCRIPT_NAME.tcl" "                   Exited Test Suite $NTGR_SCRIPT_NAME"
#	Netgear_log_file "$NTGR_SCRIPT_NAME.tcl" "          Exit Reason : Switch A2 is not reachable"
#	Netgear_log_file "$NTGR_SCRIPT_NAME.tcl" #"-------------------------------------------------------------------------"
#	Netgear_stop_log
#	exit 0
#} else {
#	Netgear_log_file "$NTGR_SCRIPT_NAME.tcl" "Ping  Successful to switch A2"
#}

#******************  End of Topology Reachability Section ************************

#******************  Start of Test Suite *****************************************

# Execute Test Case 1
source $NTGR_CONFIG_PATH/TC-VLN-001.cfg
source $NTGR_TEST_SCRIPT_PATH/TC-VLN-001.tcl

# Execute Test Case 2
source $NTGR_CONFIG_PATH/TC-RIP-007.cfg
source $NTGR_TEST_SCRIPT_PATH/TC-RIP-007.tcl
source $NTGR_TEST_SCRIPT_PATH/TC-RIP-008.tcl



# Execute Test Case 3



#Compare the results
if {$NTGR_SUITE_RESULT == 1} {
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