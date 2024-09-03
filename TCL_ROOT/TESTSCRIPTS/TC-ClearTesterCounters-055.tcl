################################################################################
#
#  File Name		: TC-ClearTesterCounters-055.tcl
#
#  Description       	: This test clears the counters of traffic Generators.
#	
#  Config File          : None
#
#  Global Variables	: ntgr_trafficTestInfo
#				  
#  Revision History 	:
#         Date         Programmer          Description
#        -----------------------------------------------------------------------
#
################################################################################

NtgrDumpLog  $NTGR_LOG_TERSE "TC-BroadcastTrafficStart-022.tcl" "Clearing Counters from the traffic generator"

#*************************  Start of Test case  ********************************
for_array_keys chassis_id ntgr_trafficTestInfo { 
	UALClearCounter $chassis_id
}

#*************************  End of Test case  ****************************************************************

