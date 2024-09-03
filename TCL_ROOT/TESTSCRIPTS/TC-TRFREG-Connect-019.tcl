################################################################################
#
#  File Name		: TC-TRFREG-Connect-019.tcl
#
#  Description       	: This Test Script Connect to the traffic generators and 
#				  load the streams 
#	
#  Config File          : TC-TRFREG-Start-019.cfg
#
#  Global Variables	: ntgr_trafficTestInfo
#				  
#  Revision History 	:
#         Date         Programmer          Description
#        -----------------------------------------------------------------------
#
################################################################################

#*************************  Start of Test case  ********************************

NtgrDumpLog $NTGR_LOG_TERSE   "TC-TRFREG-Connect-019.tcl" "******************** Starting Test case TC-TRFREG-Connect-019.tcl ********************"
set packet_loss 0
#IXIA throws error when configuring Chassis-2 after configuring Chassis-1.  
#This bug has to be fixed. Work around is configure Chassis-2 and configure Chassis-1
for_array_keys chassis_id ntgr_trafficTestInfo {
	set duration [keylget ntgr_trafficTestInfo($chassis_id) TG_TRAFFIC_DURATION]
	NtgrDumpLog $NTGR_LOG_TERSE   "TC-TRFREG-Connect-019.tcl" "Connecting to Traffic Generator $chassis_id"
	UALConnectTester $chassis_id
	NtgrDumpLog $NTGR_LOG_TERSE   "TC-TRFREG-Connect-019.tcl" "Loading the configuration on ports"
	UALLoadTester $chassis_id
}
#*************************  End of Test case  ****************************************************************