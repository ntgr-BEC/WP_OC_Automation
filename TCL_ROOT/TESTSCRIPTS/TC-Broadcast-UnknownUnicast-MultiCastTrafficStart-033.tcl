################################################################################
#
#  File Name		: TC-Broadcast-UnknownUnicast-MultiCastTrafficStart-033.tcl
#
#  Description       	: This test Starts Broadcast and Unknown Unicast Traffic 
#				  as defined in configuration file.
#	
#  Config File          : TC-Broadcast-UnknownUnicast-MultiCastTrafficStart-033.cfg
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
NtgrDumpLog  $NTGR_LOG_TERSE  "TC-Broadcast-UnknownUnicast-MultiCastTrafficStart-033.tcl" "******************** Starting Test case TC-Broadcast-UnknownUnicast-MultiCastTrafficStart-033.tcl ********************"
package require SpirentTestCenter
for_array_keys chassis_id ntgr_trafficTestInfo {
	set duration [keylget ntgr_trafficTestInfo($chassis_id) TG_TRAFFIC_DURATION]
	NtgrDumpLog  $NTGR_LOG_TERSE "TC-Broadcast-UnknownUnicast-MultiCastTrafficStart-033.tcl" "Connecting to Traffic Generator $chassis_id"
	UALConnectTester $chassis_id
	NtgrDumpLog  $NTGR_LOG_TERSE "TC-Broadcast-UnknownUnicast-MultiCastTrafficStart-033.tcl" "Loading the configuration on ports"
	UALLoadTester $chassis_id
	NtgrDumpLog  $NTGR_LOG_TERSE "TC-Broadcast-UnknownUnicast-MultiCastTrafficStart-033.tcl" "Cycle Started Traffic on chassis $chassis_id"
	UALStartTraffic $chassis_id
}
#*************************  End of Test case  ****************************************************************