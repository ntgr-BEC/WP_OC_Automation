################################################################################
#
#  File Name		: TC-TRAFFIC-007.tcl
#
#  Description       	:
#         This test configures streams per port and starts and stops traffic per port
#	    and collect statistics per port
#	
#  Config File          : TC-TRAFFIC-007.cfg
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

Netgear_log_file "TC-TRAFFIC-007.tcl" "******************** Starting Test case TC-TRAFFIC-007.tcl ********************"

set packet_loss 0
set total_loss 0
for_array_keys chassis_id ntgr_trafficTestInfo {
	set duration [keylget ntgr_trafficTestInfo($chassis_id) TG_TRAFFIC_DURATION]
	Netgear_log_file "TC-TRAFFIC-007.tcl" "Connecting to Traffic Generator $chassis_id"
	UALConnectTester $chassis_id
	Netgear_log_file "TC-TRAFFIC-007.tcl" "Loading the configuration on ports"
	UALLoadTester $chassis_id

	set port_list [keylget ntgr_trafficTestInfo($chassis_id) TG_PORT_LIST]

	Netgear_log_file "TC-TRAFFIC-007.tcl" "Started Traffic on chassis $chassis_id port = [lindex $port_list 0]"
	UALStartTrafficPerPort $chassis_id [lindex $port_list 0]
	Netgear_log_file "TC-TRAFFIC-007.tcl" "Started Traffic on chassis $chassis_id port = [lindex $port_list 1]"
	UALStartTrafficPerPort $chassis_id [lindex $port_list 1]
	sleep $duration
	Netgear_log_file "TC-TRAFFIC-007.tcl" "Stopped Traffic on chassis $chassis_id port = [lindex $port_list 0]"
	UALStopTrafficPerPort $chassis_id [lindex $port_list 0]
	Netgear_log_file "TC-TRAFFIC-007.tcl" "Stopped Traffic on chassis $chassis_id port = [lindex $port_list 1]"
	UALStopTrafficPerPort $chassis_id [lindex $port_list 1]

	Netgear_log_file "TC-TRAFFIC-007.tcl" "Collect Frame Loss Statistics on chassis $chassis_id"
	set packet_loss [UALGetFrameLossPerPort $chassis_id [lindex $port_list 0] [lindex $port_list 1]]
	Netgear_log_file "TC-TRAFFIC-007.tcl" "PACKET LOSS for Stream 1 = $packet_loss"
	if {$packet_loss != 0} {
		set total_loss [expr $total_loss + $packet_loss]
	}
	
	set packet_loss [UALGetFrameLossPerPort $chassis_id [lindex $port_list 1] [lindex $port_list 0]]
	Netgear_log_file "TC-TRAFFIC-007.tcl" "PACKET LOSS for Stream 2 = $packet_loss"
	if {$packet_loss != 0} {
		set total_loss [expr $total_loss + $packet_loss]
	}

	Netgear_log_file "TC-TRAFFIC-007.tcl" "TOTAL PACKET LOSS = $total_loss"
	Netgear_log_file "TC-TRAFFIC-007.tcl" "Disconnecting from chassis $chassis_id"
	UALDisconnectTester $chassis_id
}

if {$total_loss == 0} {
	Netgear_log_file "TC-TRAFFIC-007.tcl" "******************** TEST CASE TC-TRAFFIC-007 PASSED ********************"
	set NTGR_TEST_RESULT TRUE
} else {
	Netgear_log_file "TC-TRAFFIC-007.tcl" "******************** TEST CASE TC-TRAFFIC-007 FAILED ********************"
	Netgear_log_file "TC-TRAFFIC-007.tcl" "         Failed Reason : Total Frame loss = $total_loss"
	Netgear_log_file "TC-TRAFFIC-007.tcl" "*********************************************************************"
	set NTGR_TEST_RESULT FALSE
}

Netgear_log_file "TC-TRAFFIC-007.tcl" "******************** Completed Test case TC-TRAFFIC-007.tcl ********************"

#*************************  End of Test case  ****************************************************************