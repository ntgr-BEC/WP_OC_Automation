################################################################################
#
#  File Name	    	: TC-STORMCTRL-012.tcl
#
#  Description     	:This TCL tests storm control configurations.
#         
#
#  Config file       : TC-STORMCTRL-012.cfg 
#
#  Global Variables	:  ntgr_stormCtrlList(defined in CFG file)
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

Netgear_log_file "TC-STORMCTRL-012.tcl" "******************** Starting Test case TC-STORMCTRL-012.tcl ********************"

Netgear_log_file "TC-STORMCTRL-012.tcl" "Started Storm Control Configuration on the Switches"

for_array_keys switch_name ntgr_stormControlList {
	Netgear_log_file "TC-STORMCTRL-012.tcl" "Configure  Storm Control on $switch_name"
# 	CALStormControlConfig $switch_name
	CALConfigFlowCtrl $switch_name
	CALConfigBCastStormCtrl $switch_name
	CALConfigMCastStormCtrl $switch_name
	CALConfigUCastStormCtrl $switch_name
	Netgear_log_file "TC-STORMCTRL-012.tcl" "Completed  Storm Control configuration on $switch_name"
}

Netgear_log_file "TC-STORMCTRL-012.tcl" "Completed Storm Control Configuration on the Switches"
set NTGR_TEST_RESULT TRUE
Netgear_log_file "TC-TRAFFIC-001.tcl" "******************** TEST CASE TC-STORMCTRL-012 PASSED ********************"
Netgear_log_file "TC-STORMCTRL-012.tcl" "******************** Completed Test case TC-STORMCTRL-012.tcl ********************"

#*************************  End of Test case  ********************************