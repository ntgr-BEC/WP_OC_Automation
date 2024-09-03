#!/bin/sh
# \
exec tclsh "$0"  ${1+"$@"}
###########################################################################
#
#  File Name		: TS-MstpFunc.tcl
#
#  Description       	: This TCL configure MSTP for testing.
#
#  Test Script List 	: TC-MSTP-001.tcl
#
#  CFG file Name 	: TC-MSTP-001.cfg
#
#  Usage                : Goto the current dir in the console,  and exec 'tclsh TS-MSTPFunc.tcl'
# 
#  Revision History 	:
#  Revision   Date         Programmer        Description
#  --------------------------------------------------------------------------------------------------------------
#    1.0     27-June-06  Selva Kumar         Created
#
#
#
#
################################################################################

#******************* Start of Global Variable Declaration Section **************

set NTGR_SCRIPT_NAME   	"TS-MstpFunc"

#*******************  End of Global Variable Declaration Section ***************

#*******************  Start of Header File Include Section *********************

source common_env.tcl

#*******************  End of Header File Include Section ***********************

#***********************  Start of LOG Definition Section **********************

#######  define Log Options - log flag has one or more of the follwing values
#   1 - CONSOLE
#   2 - FILE
#   4 - REMOTE FILE - host, login name, password and log file path to be specified
#   8 - TEST DIRECTOR - host, login name, password and log file path to be specified
#  16 - LOG_ERROR       
#  32 - LOG_TERSE
#  64 - LOG_INFORM
# 128 - LOG_DEBUG
#   
#  NTGR_LOG_DEFAULT dumps LOG_ERROR on the CONSOLE 
#######

array set ntgr_logOptions {
    LOG_HOST      	""
    LOG_HOST_PATH	""
    LOG_USER_NAME 	""
    LOG_USER_PASSWD 	""
    LOG_FD		0
    LOG_LEVEL           1
    LOG_FLAG            17 
}

NtgrSetLogDestination $NTGR_LOCAL_CONSOLE $NTGR_LOCAL_FILE 

NtgrSetLogLevel $NTGR_LOG_ERROR $NTGR_LOG_TERSE


#Start the log
Netgear_start_log

#*******************  End of Log Definition Section ***********************

#*******************  Start of Topology Reachability Section *******************

NtgrDumpLog  $NTGR_LOG_TERSE  "$NTGR_SCRIPT_NAME.tcl" "                Started Test Suite $NTGR_SCRIPT_NAME"
NtgrDumpLog  $NTGR_LOG_TERSE "$NTGR_SCRIPT_NAME.tcl" "-------------------------------------------------------------------------"

NtgrDumpLog  $NTGR_LOG_TERSE  "$NTGR_SCRIPT_NAME.tcl" "Pinging the Switch A1"
set result [Netgear_check_switch_reachable A1]
if {$result == FALSE}  {
    NtgrDumpLog  $NTGR_LOG_TERSE  "$NTGR_SCRIPT_NAME.tcl" "-------------------------------------------------------------------------"
    NtgrDumpLog  $NTGR_LOG_TERSE  "$NTGR_SCRIPT_NAME.tcl" "                   Exited Test Suite $NTGR_SCRIPT_NAME"
    NtgrDumpLog  $NTGR_LOG_TERSE  "$NTGR_SCRIPT_NAME.tcl" "          Exit Reason : Switch A1 is not reachable"
    NtgrDumpLog  $NTGR_LOG_TERSE  "$NTGR_SCRIPT_NAME.tcl" "-------------------------------------------------------------------------"
    Netgear_stop_log
    exit 0
} else {
    NtgrDumpLog  $NTGR_LOG_TERSE  "$NTGR_SCRIPT_NAME.tcl" "Ping  Successful to switch A1"
}

#******************  Start of Test Suite *****************************************
# Execute Test Case 1
source $NTGR_CONFIG_PATH/TC-MSTP-001.cfg
source $NTGR_TEST_SCRIPT_PATH/TC-MSTP-001.tcl


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

