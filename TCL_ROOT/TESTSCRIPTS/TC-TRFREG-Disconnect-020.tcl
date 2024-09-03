################################################################################
#
#  File Name		: TC-TRFREG-Disconnect-020.tcl
#
#  Description       	: This Test Script Disconnects from traffic generators
#	
#  Config File          : TC-TRFREG-Disconnect-020.tcl
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
	NtgrDumpLog $NTGR_LOG_TERSE   "TC-TRFREG-Disconnect-020.tcl" "Disconnecting from chassis $chassis_id"
	UALDisconnectTester $chassis_id
}
NtgrDumpLog $NTGR_LOG_TERSE   "TC-TRFREG-Disconnect-020.tcl" "******************** Completed Test case TC-TRFREG-Disconnect-020.tcl ********************"

#*************************  End of Test case  ****************************************************************