#!/bin/sh
################################################################################   
#
#  File Name   : TC-TRAFFIC-UNLOAD-060.tcl
#
#  Description :
#         This testcase is used to unload and disconnect from Testers.
#
#  Config File : TC-TRAFFIC-LOAD-059.cfg
#
#  History     :
#        Date          Programmer        Description
#        -----------------------------------------------------------------------
#        Sep 17, 2006  Scott Zhang       Created
#
################################################################################

#*************************  Start of Test case  ********************************
NtgrDumpLog  $NTGR_LOG_TERSE  "TC-TRAFFIC-UNLOAD-060.tcl" "******************** Starting Test case TC-TRAFFIC-UNLOAD-060.tcl ********************"
for_array_keys chassis_id ntgr_trafficTestInfo {
	NtgrDumpLog $NTGR_LOG_TERSE   "TC-TRAFFIC-UNLOAD-060.tcl" "Disconnecting from chassis $chassis_id"
	UALDisconnectTester $chassis_id
}
NtgrDumpLog  $NTGR_LOG_TERSE  "TC-TRAFFIC-UNLOAD-060.tcl" "******************** Completed Test case TC-TRAFFIC-UNLOAD-060.tcl ********************"
#*************************  End of Test case  ****************************************************************