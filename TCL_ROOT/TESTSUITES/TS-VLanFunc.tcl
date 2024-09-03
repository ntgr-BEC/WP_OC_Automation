#!/bin/sh
# \
exec tclsh "$0"  ${1+"$@"}
###########################################################################
#
#  File Name		: TS-VlanFunc.tcl
#
#  Description       	:
#         This TCL creates VLAN between 2 switches and passes traffic
#
#  Test Script List 	: TC-VLN-001.tcl
#				: TC-VTR-001.tcl
#
#  CFG file Name 		: A1-TC-VLN-001.cfg
#			  	  A2-TC-VLN-001.cfg
#
#  Usage                : TS-VlanFunc.tcl
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

#******************* Start of Global Variable Declaration Section **************

set NTGR_SCRIPT_NAME   	"TS-VlanFunc"

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

Netgear_log_file "$NTGR_SCRIPT_NAME.tcl" "Pinging the Switch A1"
set result [Netgear_check_switch_reachable A1]
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

#Netgear_log_file "$NTGR_SCRIPT_NAME.tcl" "Pinging the Switch A2"
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

if {$NTGR_TEST_RESULT == TRUE} {

	incr NTGR_SUITE_RESULT

	set NTGR_TEST_RESULT FALSE
} else {
	lappend NTGR_TEST_LIST "TC-VLN-001.tcl"
}

# Execute Test Case 2



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