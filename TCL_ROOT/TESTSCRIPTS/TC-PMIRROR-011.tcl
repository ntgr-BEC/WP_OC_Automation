################################################################################
#
#  File Name	    	: TC-PMIRROR-011.tcl
#
#  Description     	:This TCL tests port mirroring configurations
#         
#
#  Config file       : TC-PMIRROR-011.cfg 
#
#  Global Variables	:  ntgr_portMirrorList(defined in CFG file)
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

Netgear_log_file "TC-PMIRROR-011.tcl" "******************** Starting Test case TC-PMIRROR-011.tcl ********************"

Netgear_log_file "TC-PMIRROR-011.tcl" "Started Port Mirror Configuration on the Switches"

for_array_keys switch_name ntgr_portMirrorList {
	Netgear_log_file "TC-PMIRROR-011.tcl" "Configure Port Mirror on $switch_name"
	CALPortMirrorConfig $switch_name
	Netgear_log_file "TC-PMIRROR-011.tcl" "Completed Port Mirror configuration on $switch_name"
}

Netgear_log_file "TC-PMIRROR-011.tcl" "Completed Port Mirror Configuration on the Switches"
set NTGR_TEST_RESULT TRUE
Netgear_log_file "TC-TRAFFIC-001.tcl" "******************** TEST CASE TC-PMIRROR-011 PASSED ********************"
Netgear_log_file "TC-PMIRROR-011.tcl" "******************** Completed Test case TC-PMIRROR-011.tcl ********************"

#*************************  End of Test case  ********************************