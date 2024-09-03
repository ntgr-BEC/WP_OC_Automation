################################################################################
#
#  File Name	    	: TC-NWDISRUPT-002.tcl
#
#  Description     	:This TCL does network disruption in the 48hr regression testing
#         
#
#  Config file       	: TC-NWDISRUPT-002.cfg 
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

Netgear_log_file "TC-NWDISRUPT-002.tcl" "******************** Starting Test case TC-NWDISRUPT-002.tcl ********************"
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
Netgear_log_file "TC-TRAFFIC-001.tcl" "******************** TEST CASE TC-NWDISRUPT-002 PASSED ********************"
Netgear_log_file "TC-NWDISRUPT-002.tcl" "******************** Completed Test case TC-NWDISRUPT-002.tcl ********************"

#*************************  End of Test case  ********************************