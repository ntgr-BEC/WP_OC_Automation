################################################################################
#
#  File Name		: TC-TCP-Burst-High-TrafficStop-040.tcl
#
#  Description       	: This test stops the TCP Burts during peak hours.
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
	NtgrDumpLog  $NTGR_LOG_TERSE "TC-TCP-Burst-High-TrafficStop-040.tcl" "Stopped TCP Burst High Traffic on chassis $chassis_id"
	UALStopTraffic $chassis_id
	NtgrDumpLog  $NTGR_LOG_TERSE "TC-TCP-Burst-High-TrafficStop-040.tcl" "Disconnecting from chassis $chassis_id"
	UALDisconnectTester $chassis_id
	NtgrDumpLog  $NTGR_LOG_TERSE "TC-TCP-Burst-High-TrafficStop-040.tcl" "******************** Completed Test case TC-UnknownUnicastTrafficStop-026.tcl ********************"
	
	

}
#*************************  End of Test case  ****************************************************************