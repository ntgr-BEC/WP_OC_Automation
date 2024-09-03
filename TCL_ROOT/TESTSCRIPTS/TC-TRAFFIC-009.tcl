################################################################################
#
#  File Name		: TC-TRAFFIC-009.tcl
#
#  Description       	:
#         This test configures 4 ports to run continous Test using IXIA. Start traffic
#	    on per port basis
#		Port 1 - Continuous Mode
#			Stream 1 - Fixed frame size and load in p/s - tcp stream
#		Port 2 - Continuous Mode 
#	 		Stream 1 - Random frame size and load in %- udp stream
#		Port 3 - Burst Mode
#	 		Stream 1 - Fixed frame size and load in p/s - vlan stream
#		Port 4 - Burst Mode
#	 		Stream 1 - Random frame size and load in %  - l2_stream
#	
#  Config File          : TC-TRAFFIC-009.cfg
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

Netgear_log_file "TC-TRAFFIC-009.tcl" "******************** Starting Test case TC-TRAFFIC-009.tcl ********************"

set packet_loss 0
for_array_keys chassis_id ntgr_trafficTestInfo {
	set duration [keylget ntgr_trafficTestInfo($chassis_id) TG_TRAFFIC_DURATION]
	Netgear_log_file "TC-TRAFFIC-009.tcl" "Connecting to Traffic Generator $chassis_id"
	UALConnectTester $chassis_id
	Netgear_log_file "TC-TRAFFIC-009.tcl" "Loading the configuration on ports"
	UALLoadTester $chassis_id

	set port_list [keylget ntgr_trafficTestInfo($chassis_id) TG_PORT_LIST]
	Netgear_log_file "TC-TRAFFIC-009.tcl" "Started Traffic on chassis $chassis_id, port = [lindex $port_list 0]"
	UALStartTrafficPerPort $chassis_id [lindex $port_list 0]
	Netgear_log_file "TC-TRAFFIC-009.tcl" "Started Traffic on chassis $chassis_id, port = [lindex $port_list 1]"
	UALStartTrafficPerPort $chassis_id [lindex $port_list 1]
	sleep $duration
	Netgear_log_file "TC-TRAFFIC-009.tcl" "Stopped Traffic on chassis $chassis_id, port = [lindex $port_list 0]"
	UALStopTrafficPerPort $chassis_id [lindex $port_list 0]
	Netgear_log_file "TC-TRAFFIC-009.tcl" "Stopped Traffic on chassis $chassis_id, port = [lindex $port_list 1]"
	UALStopTrafficPerPort $chassis_id [lindex $port_list 1]

	Netgear_log_file "TC-TRAFFIC-009.tcl" "Collect Frame Loss Statistics on chassis $chassis_id"
	set temp [UALGetFrameLossPerPort $chassis_id [lindex $port_list 0] [lindex $port_list 1]]
	set packet_loss [expr $packet_loss + $temp]
	set temp [UALGetFrameLossPerPort $chassis_id [lindex $port_list 1] [lindex $port_list 0]]
	set packet_loss [expr $packet_loss + $temp]
	Netgear_log_file "TC-TRAFFIC-009.tcl" "Total Packet Loss = $packet_loss"
	Netgear_log_file "TC-TRAFFIC-009.tcl" "Disconnecting from chassis $chassis_id"
	UALDisconnectTester $chassis_id
}

if {$packet_loss == 0} {
	Netgear_log_file "TC-TRAFFIC-009.tcl" "******************** TEST CASE TC-TRAFFIC-009 PASSED ********************"
	set NTGR_TEST_RESULT TRUE
} else {
	Netgear_log_file "TC-TRAFFIC-009.tcl" "******************** TEST CASE TC-TRAFFIC-009 FAILED ********************"
	Netgear_log_file "TC-TRAFFIC-009.tcl" "         Failed Reason : Frame_loss = $packet_loss"
	Netgear_log_file "TC-TRAFFIC-009.tcl" "*********************************************************************"
	set NTGR_TEST_RESULT FALSE
}

Netgear_log_file "TC-TRAFFIC-009.tcl" "******************** Completed Test case TC-TRAFFIC-009.tcl ********************"

#*************************  End of Test case  ****************************************************************