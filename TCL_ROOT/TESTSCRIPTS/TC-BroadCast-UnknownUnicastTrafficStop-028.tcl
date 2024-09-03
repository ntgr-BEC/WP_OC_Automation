################################################################################
#
#  File Name		: TC-BroadCast-UnknownUnicastTrafficStop-028.tcl
#
#  Description       	: This test stops the Broadcast and Unknown Unicast Traffic 
#				  streams started.
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

#*************************  Start of Test case  ********************************
for_array_keys chassis_id ntgr_trafficTestInfo { 
	NtgrDumpLog  $NTGR_LOG_TERSE "TC-BroadCast-UnknownUnicastTrafficStop-028.tcl" "Stopped Broadcast and Unknown Unicast Traffic on chassis $chassis_id"
	UALStopTraffic $chassis_id
	NtgrDumpLog  $NTGR_LOG_TERSE "TC-BroadCast-UnknownUnicastTrafficStop-028.tcl" "Disconnecting from chassis $chassis_id"
	UALDisconnectTester $chassis_id
	NtgrDumpLog  $NTGR_LOG_TERSE "TC-BroadCast-UnknownUnicastTrafficStop-028.tcl" "******************** Completed Test case TC-BroadCast-UnknownUnicastTrafficStop-028.tcl ********************"
}
#*************************  End of Test case  ****************************************************************