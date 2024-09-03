#!/bin/sh
# \
exec tclsh "$0"  ${1+"$@"}

set NTGR_SCRIPT_NAME   	"TS-TimeWheel-FUNC-PORT-SEC.tcl"
source common_env.tcl


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


#Start the log
Netgear_start_log

NtgrDumpLog $NTGR_LOG_TERSE  "$NTGR_SCRIPT_NAME.tcl" "-------------------------------------------------------------------------"
NtgrDumpLog $NTGR_LOG_TERSE "$NTGR_SCRIPT_NAME.tcl" "                Started Test Suite $NTGR_SCRIPT_NAME"
NtgrDumpLog $NTGR_LOG_TERSE "$NTGR_SCRIPT_NAME.tcl" "-------------------------------------------------------------------------"

source $NTGR_FUNC_CFG/$NTGR_TEST_ENVIRONMENT/TC-TIMEWHEEL-FUNC-PORT-SEC.cfg
source $NTGR_FUNC_TC/TC-TIMEWHEEL-FUNC-PORT-SEC.tcl

NtgrDumpLog $NTGR_LOG_TERSE  "$NTGR_SCRIPT_NAME.tcl" "-------------------------------------------------------------------------"
NtgrDumpLog $NTGR_LOG_TERSE  "$NTGR_SCRIPT_NAME.tcl" "                Completed Test Suite $NTGR_SCRIPT_NAME"
NtgrDumpLog $NTGR_LOG_TERSE  "$NTGR_SCRIPT_NAME.tcl" "-------------------------------------------------------------------------"

#stop the log
Netgear_stop_log

#******************  End of Test Suite ************************************************************************