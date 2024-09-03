################################################################################
#
#  File Name	    	: TC-IP-009.tcl
#
#  Description     	:This TCL tests clear RIP on switches.
#         
#
#  Config file       : TC-IP-009.cfg 
#
#  Global Variables	:  ntgr_IPList(defined in CFG file)
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

Netgear_log_file "TC-IP-009.tcl" "******************** Starting Test case TC-IP-009.tcl ********************"

Netgear_log_file "TC-IP-009.tcl" "Started IP Configuration on the Switches"

for_array_keys switch_name ntgr_IPList {
	Netgear_log_file "TC-IP-009.tcl" "Configure IP Routing on $switch_name"
	CALIpRoutingConfig $switch_name
	Netgear_log_file "TC-IP-009.tcl" "Completed IP Routing configuration on $switch_name"
}

Netgear_log_file "TC-IP-009.tcl" "Completed IP Configuration on the Switches"
set NTGR_TEST_RESULT TRUE
Netgear_log_file "TC-TRAFFIC-001.tcl" "******************** TEST CASE TC-IP-009 PASSED ********************"
Netgear_log_file "TC-IP-009.tcl" "******************** Completed Test case TC-IP-009.tcl ********************"

#*************************  End of Test case  ********************************