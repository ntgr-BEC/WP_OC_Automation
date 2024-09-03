################################################################################
#
#  File Name		: TC-BurstTrafficDisconnect-024.tcl
#
#  Description       	: This test configures 4 ports to run broadcast streams for 
#                         10 minutes using Spirent Ports. 
#		Port 1 - Continuous Mode
#			Stream 1 - Fixed frame size and load in 100 %  - L2 VLAN Tagged Broadcast Stream
#			Stream 2 - Fixed frame size and load in 100 %  - L3 Broadcast Stream
#		Port 2 - Continuous Mode 
#			Stream 1 - Fixed frame size and load in 100 %  - L2 VLAN Tagged Broadcast Stream
#			Stream 2 - Fixed frame size and load in 100 %  - L3 Broadcast Stream
#		Port 3 - Burst Mode
#			Stream 1 - Fixed frame size and load in 100 %  - L2 VLAN Tagged Broadcast Stream
#			Stream 2 - Fixed frame size and load in 100 %  - L3 Broadcast Stream
#		Port 4 - Burst Mode
#			Stream 1 - Fixed frame size and load in 100 %  - L2 VLAN Tagged Broadcast Stream
#			Stream 2 - Fixed frame size and load in 100 %  - L3 Broadcast Stream
#	
#  Config File          : TC-BurstTrafficDisconnect-024.cfg
#
#  Global Variables	: ntgr_trafficTestInfo
#				  
#  Revision History 	:
#         Date         Programmer          Description
#        -----------------------------------------------------------------------
#
#
#
#
################################################################################

#*************************  Start of Test case  ********************************
for_array_keys chassis_id ntgr_trafficTestInfo { 
	NtgrDumpLog  $NTGR_LOG_TERSE "TC-BurstTrafficDisconnect-024.tcl" "Disconnecting from chassis $chassis_id"
	UALDisconnectTester $chassis_id
	NtgrDumpLog  $NTGR_LOG_TERSE "TC-BurstTrafficDisconnect-024.tcl" "******************** Completed Test case TC-BurstTrafficDisconnect-024.tcl ********************"
}
#*************************  End of Test case  ****************************************************************