#!/bin/sh
################################################################################   
#
#  File Name   : TC-TRAFFIC-LOAD-059.tcl
#
#  Description :
#         This testcase is used to load traffic relative definition.
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
# Connect and Load the stream in the first cylce of Time Wheel
NtgrDumpLog  $NTGR_LOG_TERSE  "TC-TRAFFIC-LOAD-059.tcl" "******************** Starting Test case TC-TRAFFIC-LOAD-059.tcl ********************"
for_array_keys chassis_id ntgr_trafficTestInfo {
    NtgrDumpLog  $NTGR_LOG_TERSE "TC-TRAFFIC-LOAD-059.tcl" "Connecting to Traffic Generator $chassis_id"
    UALConnectTester $chassis_id
    NtgrDumpLog  $NTGR_LOG_TERSE "TC-TRAFFIC-LOAD-059.tcl" "Loading all ports' and streams' definition"
    UALLoadTester $chassis_id
}
NtgrDumpLog  $NTGR_LOG_TERSE  "TC-TRAFFIC-LOAD-059.tcl" "******************** Completed Test case TC-TRAFFIC-LOAD-059.tcl ********************"
#*************************  End of Test case  ****************************************************************