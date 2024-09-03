################################################################################
#
#  File Name	    	: TC-JFRAME-010.tcl
#
#  Description     	: This TCL tests clear RIP on switches.
#         
#
#  Config file       	: TC-JFRAME-010.cfg 
#
#  Global Variables	: ntgr_jumboFrameList(defined in CFG file)
#                         ntgr_poChanList
#				  ntgr_vlanList   
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

Netgear_log_file "TC-JFRAME-010.tcl" "******************** Starting Test case TC-JFRAME-010.tcl ********************"

Netgear_log_file "TC-JFRAME-010.tcl" "Started Jumbo Frame Configuration on the Switches"

for_array_keys switch_name ntgr_jumboFrameList {
	Netgear_log_file "TC-JFRAME-010.tcl" "Configure Jumbo Frame on $switch_name"
	CALJumboFrameConfig $switch_name
	Netgear_log_file "TC-JFRAME-010.tcl" "Completed Jumbo Frame configuration on $switch_name"
}

Netgear_log_file "TC-JFRAME-010.tcl" "Completed Jumho Frame Configuration on the Switches"
set NTGR_TEST_RESULT TRUE
Netgear_log_file "TC-TRAFFIC-001.tcl" "******************** TEST CASE TC-JFRAME-010 PASSED ********************"
Netgear_log_file "TC-JFRAME-010.tcl" "******************** Completed Test case TC-JFRAME-010.tcl ********************"

#*************************  End of Test case  ********************************