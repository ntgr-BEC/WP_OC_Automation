#!/bin/sh
# \
exec tclsh "$0"  ${1+"$@"}
###########################################################################
#
#  File Name		: TS-TimeWheel-Func-RIP.tcl
#
#  Description       	: This TCL configure time wheel to run multiple scripts
#                         at their respective time slots.
#
#  Test Script List 	: TC-TIMEWHEEL-015.tcl
#
#  CFG file Name 		: TC-TIMEWHEEL-015.cfg
#
#  Usage             : Goto the current dir in the console, 
#							  and exec 'TS-TimeWheel.tcl'
# 
#  Revision History 	:
#        Date        Programmer        Description
#        ----------------------------------------------------------------------
#        30-Jan-07   Nina Cheng        Create
#
#
#
################################################################################

#******************* Start of Global Variable Declaration Section **************

set NTGR_SCRIPT_NAME   	"TS-TimeWheel.tcl"

#*******************  End of Global Variable Declaration Section ***************

#*******************  Start of Header File Include Section *********************

source common_env.tcl

#*******************  End of Header File Include Section ***********************

#***********************  Start of LOG Definition Section **********************

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
	LOG_FLAG		163
	LOG_HOST      	""
	LOG_HOST_PATH	""
	LOG_USER_NAME 	""
	LOG_USER_PASSWD 	""
	LOG_FD		0
}


#*******************  End of Log Definition Section ***********************

#Start the log
Netgear_start_log

NtgrDumpLog $NTGR_LOG_TERSE  "$NTGR_SCRIPT_NAME.tcl" "-------------------------------------------------------------------------"
NtgrDumpLog $NTGR_LOG_TERSE "$NTGR_SCRIPT_NAME.tcl" "                Started Test Suite $NTGR_SCRIPT_NAME"
NtgrDumpLog $NTGR_LOG_TERSE "$NTGR_SCRIPT_NAME.tcl" "-------------------------------------------------------------------------"

source $NTGR_FUNC_CFG/$NTGR_TEST_ENVIRONMENT/TC-TIMEWHEEL-FUNC-RIP.cfg
source $NTGR_FUNC_TC/TC-TIMEWHEEL-FUNC-RIP.tcl

NtgrDumpLog $NTGR_LOG_TERSE  "$NTGR_SCRIPT_NAME.tcl" "-------------------------------------------------------------------------"
NtgrDumpLog $NTGR_LOG_TERSE  "$NTGR_SCRIPT_NAME.tcl" "                Completed Test Suite $NTGR_SCRIPT_NAME"
NtgrDumpLog $NTGR_LOG_TERSE  "$NTGR_SCRIPT_NAME.tcl" "-------------------------------------------------------------------------"

#stop the log
Netgear_stop_log

#******************  End of Test Suite ************************************************************************
