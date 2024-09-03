################################################################################
#
#  File Name	    	: TC-DOS-003.tcl
#
#  Description     	:This TCL does Denial of service test in the 48hr regression testing
#         
#
#  Config file       	: TC-DOS-003.cfg 
#
#  Global Variables	: None
#				  
#  Revision History 	:
#        Date          Programmer          Description
#        -----------------------------------------------------------------------
#        15-Jun-06	Rajeswari          Created
#
#
#
#
################################################################################

#*************************  Start of Test case  ********************************

Netgear_log_file "TC-DOS-003.tcl" "******************** Starting Test case TC-DOS-003.tcl ********************"

for {set count 1} {$count <= 172800} {incr count 1}
for {set byte 64} {$byte <= 65500} {incr byte 10} {
	foreach switch $NTGR_DOS_SWITCH_LIST {
		APIDosPing $switch $byte
	}
}

set switch_ip_addr [_get_switch_ip_addr A1]
set pid [spawn ping $switch_ip_addr]
set id $spawn_id


set count 1
foreach time $NW_DISRUPTION_INTERVAL {
	sleep $time
	
	if {[expr $count % 2] == 0} {
		APINetworkDisrupt $NW_DISRUPTION_ACCESS_EVEN_SWITCH_LIST
		APINetworkDisrupt $NW_DISRUPTION_ENT_EVEN_SWITCH_LIST
	} else {
		APINetworkDisrupt $NW_DISRUPTION_ACCESS_ODD_SWITCH_LIST
		APINetworkDisrupt $NW_DISRUPTION_ENT_ODD_SWITCH_LIST
	}

	incr count
}

set NTGR_TEST_RESULT TRUE
Netgear_log_file "TC-TRAFFIC-001.tcl" "******************** TEST CASE TC-DOS-003 PASSED ********************"
Netgear_log_file "TC-DOS-003.tcl" "******************** Completed Test case TC-DOS-003.tcl ********************"

#*************************  End of Test case  ********************************