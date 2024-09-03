################################################################################
#
#  File Name		: TC-BurstTrafficLoad-021.tcl
#
#  Description       	: This test loads the burst traffic streams in the sprirent 
#				  4 ports 
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
#  Config File          : TC-BurstTrafficLoad-021.cfg
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


# Connect and Load the stream in the first cylce of Time Wheel
NtgrDumpLog  $NTGR_LOG_TERSE  "TC-BurstTrafficLoad-021.tcl" "******************** Starting Test case TC-BurstTrafficLoad-021.tcl ********************"
for_array_keys chassis_id ntgr_trafficTestInfo {
	set duration [keylget ntgr_trafficTestInfo($chassis_id) TG_TRAFFIC_DURATION]
	NtgrDumpLog  $NTGR_LOG_TERSE "TC-BurstTrafficLoad-021.tcl" "Connecting to Traffic Generator $chassis_id"
	UALConnectTester $chassis_id
	NtgrDumpLog  $NTGR_LOG_TERSE "TC-BurstTrafficLoad-021.tcl" "Loading the configuration on ports"
	UALLoadTester $chassis_id
}
#*************************  End of Test case  ****************************************************************