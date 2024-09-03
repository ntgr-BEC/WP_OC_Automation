################################################################################
#
#  File Name		: TC-TRAFFIC-001.tcl
#
#  Description       	:
#         This test configures 4 ports to run continous Test and run continous traffic
#	    on ports.  
#		Port 1 - Continuous Mode
#			Stream 1 - Fixed frame size and load in p/s - tcp stream
#	 		Stream 2 - Fixed frame size and load in %   - udp stream
#		Port 2 - Continuous Mode 
#	 		Stream 1 - Fixed frame size and load in p/s - tcp stream
#	 		Stream 2 - Fixed frame size and load in %   - udp stream
#		Port 3 - Continuous Mode, random load in p/s
#	 		Stream 1 - Fixed frame size - vlan stream 1
#	 		Stream 2 - Fixed frame size - vlan stream 2
#		Port 4 - Continuous Mode, random load in %
#	 		Stream 1 - Fixed frame size - vlan stream 3
#	 		Stream 2 - Fixed frame size - vlan stream 4
#	
#  Config File          : TC-TRAFFIC-001.cfg
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

Netgear_log_file "TC-TRAFFIC-001.tcl" "******************** Starting Test case TC-TRAFFIC-001.tcl ********************"

set packet_loss 0
for_array_keys chassis_id ntgr_trafficTestInfo {
	set duration [keylget ntgr_trafficTestInfo($chassis_id) TG_TRAFFIC_DURATION]
	Netgear_log_file "TC-TRAFFIC-001.tcl" "Connecting to Traffic Generator $chassis_id"
	UALConnectTester $chassis_id
	Netgear_log_file "TC-TRAFFIC-001.tcl" "Loading the configuration on ports"
	UALLoadTester $chassis_id
	Netgear_log_file "TC-TRAFFIC-001.tcl" "Started Traffic on chassis $chassis_id"
	UALStartTraffic $chassis_id
	sleep $duration
	Netgear_log_file "TC-TRAFFIC-001.tcl" "Stopped Traffic on chassis $chassis_id"
	UALStopTraffic $chassis_id
	Netgear_log_file "TC-TRAFFIC-001.tcl" "Collect Frame Loss Statistics on chassis $chassis_id"
	set temp 0
	set temp [UALGetTotalFrameLoss $chassis_id]
	set packet_loss [expr $packet_loss + $temp]
	Netgear_log_file "TC-TRAFFIC-001.tcl" "Disconnecting from chassis $chassis_id"
	UALDisconnectTester $chassis_id
}

if {$packet_loss == 0} {
	Netgear_log_file "TC-TRAFFIC-001.tcl" "******************** TEST CASE TC-TRAFFIC-001 PASSED ********************"
	set NTGR_TEST_RESULT TRUE
} else {
	Netgear_log_file "TC-TRAFFIC-001.tcl" "******************** TEST CASE TC-TRAFFIC-001 FAILED ********************"
	Netgear_log_file "TC-TRAFFIC-001.tcl" "         Failed Reason : Frame_loss = $packet_loss"
	Netgear_log_file "TC-TRAFFIC-001.tcl" "*********************************************************************"
	set NTGR_TEST_RESULT FALSE
}

Netgear_log_file "TC-TRAFFIC-001.tcl" "******************** Completed Test case TC-TRAFFIC-001.tcl ********************"

#*************************  End of Test case  ****************************************************************