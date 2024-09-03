################################################################################
#
#  File Name		: TC-StartOspf-080.tcl
#
#  
#  Config File          : TC-StartOspf-080.cfg
#
#  Global Variables	: ntgr_trafficTestInfo
#				  
#  Revision History 	:
#         Date         Programmer          Description
#        -----------------------------------------------------------------------
#        29-01-06       Nina Cheng          Create  
#
#
#
################################################################################

#*************************  Start of Test case  ********************************

Netgear_log_file "TC-StartOspf-080.tcl" "****** Starting Test case TC-StartOspf-080.tcl *******"

set packet_loss 0
foreach {chassis_id port} [array get ntgr_OspfPortList] {

	UALConnectTester $chassis_id
	
	UALTakeOwnership $chassis_id $port

	UALLoadPort $chassis_id $port

	UALStartOSPFRouter $chassis_id $port

}
Netgear_log_file "TC-StartOspf-080.tcl" "****** Completed Test case TC-StartOspf-080.tcl ******"

#*************************  End of Test case  ****************************************************************
