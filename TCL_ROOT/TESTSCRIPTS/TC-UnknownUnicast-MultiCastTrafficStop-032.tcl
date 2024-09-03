################################################################################
#
#  File Name		: TC-UnknownUnicast-MultiCastTrafficStop-032.tcl
#
#  Description       	: This test stops the Unknown Unicast and Multi Cast 
#				  Traffic streams started.
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
	NtgrDumpLog  $NTGR_LOG_TERSE "TC-UnknownUnicast-MultiCastTrafficStop-032.tcl" "Stopped Unknown Unicast Traffic on chassis $chassis_id"
	UALStopTraffic $chassis_id
	NtgrDumpLog  $NTGR_LOG_TERSE "TC-UnknownUnicast-MultiCastTrafficStop-032.tcl" "Disconnecting from chassis $chassis_id"
	UALDisconnectTester $chassis_id
	NtgrDumpLog  $NTGR_LOG_TERSE "TC-UnknownUnicast-MultiCastTrafficStop-032.tcl" "******************** Completed Test case TC-UnknownUnicast-MultiCastTrafficStop-032.tcl ********************"
}
#*************************  End of Test case  ****************************************************************