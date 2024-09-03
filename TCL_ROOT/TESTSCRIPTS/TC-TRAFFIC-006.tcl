################################################################################
#
#  File Name		: TC-TRAFFIC-006.tcl
#
#  Description       	:
#         This test configures 2 ports to run continous Test and run traffic
#	    on ports for a particular period
#		Port 1 - Seconds Mode
#			Stream 1 - Fixed frame size and load in p/s - l2 stream
#	 		Stream 2 - Fixed frame size and load in %   - l2 stream
#		Port 2 - Seconds Mode , fixed load in p/s
#	 		Stream 1 - Random frame size - l2 stream
#	 		Stream 2 - Random frame size - l2 stream
#	
#  Config File          : TC-TRAFFIC-006.cfg
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

Netgear_log_file "TC-TRAFFIC-006.tcl" "******************** Starting Test case TC-TRAFFIC-006.tcl ********************"

set packet_loss 0
for_array_keys chassis_id ntgr_trafficTestInfo {
	set duration [keylget ntgr_trafficTestInfo($chassis_id) TG_TRAFFIC_DURATION]
	Netgear_log_file "TC-TRAFFIC-006.tcl" "Connecting to Traffic Generator $chassis_id"
	UALConnectTester $chassis_id
	Netgear_log_file "TC-TRAFFIC-006.tcl" "Loading the configuration on ports"
	UALLoadTester $chassis_id
	Netgear_log_file "TC-TRAFFIC-006.tcl" "Started Traffic on chassis $chassis_id"
	UALStartTraffic $chassis_id
	sleep $duration
	Netgear_log_file "TC-TRAFFIC-006.tcl" "Stopped Traffic on chassis $chassis_id"
	UALStopTraffic $chassis_id
	Netgear_log_file "TC-TRAFFIC-006.tcl" "Collect Frame Loss Statistics on chassis $chassis_id"
	set temp 0
	set temp [UALGetTotalFrameLoss $chassis_id]
	set packet_loss [expr $packet_loss + $temp]
	Netgear_log_file "TC-TRAFFIC-006.tcl" "Disconnecting from chassis $chassis_id"
	UALDisconnectTester $chassis_id
}

if {$packet_loss == 0} {
	Netgear_log_file "TC-TRAFFIC-006.tcl" "******************** TEST CASE TC-TRAFFIC-006 PASSED ********************"
	set NTGR_TEST_RESULT TRUE
} else {
	Netgear_log_file "TC-TRAFFIC-006.tcl" "******************** TEST CASE TC-TRAFFIC-006 FAILED ********************"
	Netgear_log_file "TC-TRAFFIC-006.tcl" "         Failed Reason : Frame_loss = $packet_loss"
	Netgear_log_file "TC-TRAFFIC-006.tcl" "*********************************************************************"
	set NTGR_TEST_RESULT FALSE
}

Netgear_log_file "TC-TRAFFIC-006.tcl" "******************** Completed Test case TC-TRAFFIC-006.tcl ********************"

#*************************  End of Test case  ****************************************************************