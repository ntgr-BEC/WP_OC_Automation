################################################################################
#
#  File Name		: TC-L3-TRAFFIC-ROUTER-START-071.tcl
#
#  Description       	: This test start layer3 traffic across layer3 routing testbed.
#	
#  Config File          : TC-L3-TRAFFIC-ROUTER-LOAD-070.cfg
#
#  Global Variables	: ntgr_Testcase070UsedPortList
#				  
#  Revision History 	:
#         Date         Programmer          Description
#        -----------------------------------------------------------------------
#
################################################################################

#*************************  Start of Test case  ********************************
NtgrDumpLog  $NTGR_LOG_TERSE  "TC-L3-TRAFFIC-ROUTER-START-071.tcl" "******************** Starting Test case TC-L3-TRAFFIC-ROUTER-START-071.tcl ********************"
foreach {chassis_id portlist} [array get ntgr_Testcase070UsedPortList] {
    foreach pt $portlist {
        UALStartTrafficPerPort $chassis_id $pt
    }
}
NtgrDumpLog  $NTGR_LOG_TERSE  "TC-L3-TRAFFIC-ROUTER-START-071.tcl" "******************** Complete Test case TC-L3-TRAFFIC-ROUTER-START-071.tcl ********************"
#*************************  End of Test case  ****************************************************************