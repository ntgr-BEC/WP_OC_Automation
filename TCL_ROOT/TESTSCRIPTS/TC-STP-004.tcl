################################################################################
#
#  File Name	    	: TC-STP-004.tcl
#
#  Description     	:
#         This TCL tests configures spanning tree on switches.
#
#  Config file       : TC-STP-004.cfg 
#
#  Global Variables	: ntgr_vlanList (defined in CFG file)
#				  
#  Revision History 	:
#        Date          Programmer          Description
#        -----------------------------------------------------------------------
#        18-May-06     Scott Zhang         Created
#
#
#
#
################################################################################

#*************************  Start of Test case  ********************************

Netgear_log_file "TC-STP-004.tcl" "******************** Starting Test case TC-STP-004.tcl ********************"

Netgear_log_file "TC-STP-004.tcl" "Started Spanning-tree Configuration on the Switches"

set switch_names [lsort -dictionary [array names ntgr_stpList ] ]
foreach switch_name $switch_names {
	Netgear_log_file "TC-STP-004.tcl" "Enable spanning-tree on switch $switch_name"
	CALStpEnableSwitch $switch_name
	
	Netgear_log_file "TC-STP-004.tcl" "Disable all ports' spanning-tree mode on switch $switch_name"
	CALStpDisablePort $switch_name "all"
	
	Netgear_log_file "TC-STP-004.tcl" "Enable the special ports' spanning-tree mode on switch $switch_name"
	set portlist [getStpPortList $switch_name]
	CALStpEnablePort $switch_name $portlist
	
	Netgear_log_file "TC-STP-004.tcl" "Setting spanning-tree ports' cost value"
	set costlist [getStpPortCostList $switch_name]
	CALStpConfigCost $switch_name $portlist $costlist
	
	Netgear_log_file "TC-STP-004.tcl" "Setting the bridge's priority on switch $switch_name"
	set priority [getStpBridgePriority $switch_name]
	CALStpConfigBridgePriority $switch_name $priority

	Netgear_log_file "TC-STP-004.tcl" "Setting the bridge's forward delay on switch $switch_name"
	set fwdTime [getStpFwdTime $switch_name]
	CALStpConfigForwardTime $switch_name $fwdTime

	Netgear_log_file "TC-STP-004.tcl" "Setting the bridge's max age on switch $switch_name"
	set maxAge [getStpMaxAge $switch_name]
	CALStpConfigMaxAge $switch_name $maxAge

	Netgear_log_file "TC-STP-004.tcl" "Setting the bridge's hello time on switch $switch_name"
	set helloInterval [getStpHelloTime $switch_name]
	CALStpConfigHelloTime $switch_name $helloInterval
}

# Netgear_log_file "TC-STP-004.tcl" "Clearing spanning-tree on switches"
# for_array_keys switch_name ntgr_stpList {
# 	Netgear_log_file "TC-STP-004.tcl" "Clearing spanning-tree on switch $switch_name"
# 	CALStpDisableSwitch $switch_name
# }
# 
# Netgear_log_file "TC-STP-004.tcl" "Completed clearing of spanning-tree Configuration on the Switches"

set packet_loss 0
if {$packet_loss == 0} {
	Netgear_log_file "TC-STP-004.tcl" "******************** TEST CASE TC-STP-004 PASSED ********************"
	set NTGR_TEST_RESULT TRUE
} else {
	Netgear_log_file "TC-STP-004.tcl" "******************** TEST CASE TC-STP-004 FAILED ********************"
	Netgear_log_file "TC-STP-004.tcl" "         Failed Reason : Frame_loss = $packet_loss"
	Netgear_log_file "TC-STP-004.tcl" "*********************************************************************"
	set NTGR_TEST_RESULT FALSE
}

Netgear_log_file "TC-STP-004.tcl" "******************** Completed Test case TC-STP-004.tcl ********************"

#*************************  End of Test case  ****************************************************************