################################################################################
#
#  File Name		: TC-StopRip-070.tcl
#
#  
#  Config File          : TC-ROUTE-RIP-070.cfg
#
#  Global Variables	: ntgr_trafficTestInfo
#				  
#  Revision History 	:
#         Date         Programmer          Description
#        -----------------------------------------------------------------------
#        29-9-06       Nina Cheng          Create  
#
#
#
################################################################################

#*************************  Start of Test case  ********************************

Netgear_log_file "TC-StopRip-070.tcl" "******************** Starting Test case TC-StopRip-070.tcl ********************"

set packet_loss 0
foreach {chassis_id port} [array get ntgr_RipPortList] {

	UALStopRIPRouter $chassis_id $port

}



Netgear_log_file "TC-StopRip-070.tcl" "******************** Completed Test case TC-StopRip-070.tcl ********************"

#*************************  End of Test case  ****************************************************************
