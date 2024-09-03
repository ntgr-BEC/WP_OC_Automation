################################################################################
#
#  File Name		: TC-Vlan-Jumbo-TrafficStop-052.tcl
#
#  Description       	: This test stops the VLAN Tagged Jumbo frames streams started.
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
	NtgrDumpLog  $NTGR_LOG_TERSE "TC-Vlan-Jumbo-TrafficStop-052.tcl" "Stopped Unknown Unicast Traffic on chassis $chassis_id"
	UALStopTraffic $chassis_id
	NtgrDumpLog  $NTGR_LOG_TERSE "TC-Vlan-Jumbo-TrafficStop-052.tcl" "Disconnecting from chassis $chassis_id"
	UALDisconnectTester $chassis_id
	NtgrDumpLog  $NTGR_LOG_TERSE "TC-Vlan-Jumbo-TrafficStop-052.tcl" "******************** Completed Test case TC-Vlan-Jumbo-TrafficStop-052.tcl ********************"
}
#*************************  End of Test case  ****************************************************************