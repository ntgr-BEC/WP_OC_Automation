################################################################################
#
#  File Name	    	: TC-SNTP-013.tcl
#
#  Description     	:This TCL tests SNTP configurations.
#         
#
#  Config file       : TC-SNTP-013.cfg 
#
#  Global Variables	:  ntgr_sntpList(defined in CFG file)
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

Netgear_log_file "TC-SNTP-013.tcl" "******************** Starting Test case TC-SNTP-013.tcl ********************"

Netgear_log_file "TC-SNTP-013.tcl" "Started SNTP Configuration on the Switches"

for_array_keys switch_name ntgr_sntpList {
	Netgear_log_file "TC-SNTP-013.tcl" "Configure SNTP client on switch $switch_name"
	CALSNTPClientConfig $switch_name
	Netgear_log_file "TC-SNTP-013.tcl" "Add SNTP server on switch $switch_name"
	CALSNTPAddServer $switch_name
	Netgear_log_file "TC-SNTP-013.tcl" "Delete SNTP server on switch $switch_name"
	CALSNTPDeleteServer $switch_name
	Netgear_log_file "TC-SNTP-013.tcl" "Completed SNTP configuration on $switch_name"
}

Netgear_log_file "TC-SNTP-013.tcl" "Completed SNTP Configuration on the Switches"
set NTGR_TEST_RESULT TRUE
Netgear_log_file "TC-TRAFFIC-001.tcl" "******************** TEST CASE TC-SNTP-013 PASSED ********************"
Netgear_log_file "TC-SNTP-013.tcl" "******************** Completed Test case TC-SNTP-013.tcl ********************"

#*************************  End of Test case  ********************************