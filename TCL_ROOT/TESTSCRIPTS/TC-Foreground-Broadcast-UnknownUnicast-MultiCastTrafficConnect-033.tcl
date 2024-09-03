################################################################################
#
#  File Name		: TC-Foreground-Broadcast-UnknownUnicast-MultiCastTrafficConnect-033.tcl
#
#  Description       	: This test script loads the streams required for foreground traffic and 
#				  background traffic such as Brodacast, Unknown Unicast and Multicast 
#				  as defined in configuration file.
#	
#  Config File          : TC-Foreground-Broadcast-UnknownUnicast-MultiCastTrafficConnect-033.cfg
#
#  Global Variables	: ntgr_trafficTestInfo
#				  
#  Revision History 	:
#         Date         Programmer          Description
#        -----------------------------------------------------------------------
#
#
################################################################################

#*************************  Start of Test case  ********************************


# Connect and Load the stream in the first cylce of Time Wheel
NtgrDumpLog  $NTGR_LOG_TERSE  "TC-Broadcast-UnknownUnicast-MultiCastTrafficStart-033.tcl" "******************** Starting Test case TC-Broadcast-UnknownUnicast-MultiCastTrafficStart-033.tcl ********************"
#package require SpirentTestCenter
for_array_keys chassis_id ntgr_trafficTestInfo {
	set duration [keylget ntgr_trafficTestInfo($chassis_id) TG_TRAFFIC_DURATION]
	NtgrDumpLog  $NTGR_LOG_TERSE "TC-Broadcast-UnknownUnicast-MultiCastTrafficStart-033.tcl" "Connecting to Traffic Generator $chassis_id"
	UALConnectTester $chassis_id
	NtgrDumpLog  $NTGR_LOG_TERSE "TC-Broadcast-UnknownUnicast-MultiCastTrafficStart-033.tcl" "Loading the configuration on ports"
	UALLoadTester $chassis_id
}
#*************************  End of Test case  ****************************************************************