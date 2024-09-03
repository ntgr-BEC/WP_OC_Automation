################################################################################
#
#  File Name		: TC-TRAFFIC-004.tcl
#
#  Description       	:
#         This test configures 2 ports to run Fixed Test on ports.  
#		Port 1 - Continuous Mode
#			Stream 1 - Fixed frame size and load in p/s 
#	 		Stream 2 - Fixed frame size and load in %   
#		Port 2 - Burst Mode, fixed load in %
#	 		Stream 1 - Random frame size 
#	 		Stream 2 - Random frame size
#	
#  Config File          : TC-TRAFFIC-004.cfg
#
#  Global Variables	: ntgr_trafficTestInfo
#				  
#  Revision History 	:
#         Date         Programmer          Description
#        -----------------------------------------------------------------------
#        02-May-06     Rajeswari V         Created
#
#
#
#
################################################################################

#*************************  Start of Test case  ********************************

Netgear_log_file "TC-TRAFFIC-004.tcl" "******************** Starting Test case TC-TRAFFIC-004.tcl ********************"

set packet_loss 0
for_array_keys chassis_id ntgr_trafficTestInfo {
	Netgear_log_file "TC-TRAFFIC-004.tcl" "Connecting to Traffic Generator $chassis_id"
	UALConnectTester $chassis_id
	Netgear_log_file "TC-TRAFFIC-004.tcl" "Loading the configuration on ports"
	UALLoadTester $chassis_id
	Netgear_log_file "TC-TRAFFIC-004.tcl" "Started Traffic on chassis $chassis_id"
	UALStartTraffic $chassis_id
	Netgear_log_file "TC-TRAFFIC-004.tcl" "Collect Frame Loss Statistics on chassis $chassis_id"
	set temp 0
	set temp [UALGetTotalFrameLoss $chassis_id]
	set packet_loss [expr $packet_loss + $temp]
	Netgear_log_file "TC-TRAFFIC-004.tcl" "Disconnecting from chassis $chassis_id"
	UALDisconnectTester $chassis_id
}

if {$packet_loss == 0} {
	Netgear_log_file "TC-TRAFFIC-004.tcl" "******************** TEST CASE TC-TRAFFIC-004 PASSED ********************"
	set NTGR_TEST_RESULT TRUE
} else {
	Netgear_log_file "TC-TRAFFIC-004.tcl" "******************** TEST CASE TC-TRAFFIC-004 FAILED ********************"
	Netgear_log_file "TC-TRAFFIC-004.tcl" "         Failed Reason : Frame_loss = $packet_loss"
	Netgear_log_file "TC-TRAFFIC-004.tcl" "*********************************************************************"
	set NTGR_TEST_RESULT FALSE
}

Netgear_log_file "TC-TRAFFIC-004.tcl" "******************** Completed Test case TC-TRAFFIC-004.tcl ********************"

#*************************  End of Test case  ****************************************************************