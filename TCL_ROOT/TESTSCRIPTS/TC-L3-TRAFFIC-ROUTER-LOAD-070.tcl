################################################################################
#
#  File Name		: TC-L3-TRAFFIC-ROUTER-LOAD-070.tcl
#
#  Description       	: This test loads layer3 packets will across layer3 routing testbed.
#	
#  Config File          : TC-L3-TRAFFIC-ROUTER-LOAD-070.cfg
#
#  Global Variables	: ntgr_Testcase070UsedPortList
#				  
#  Revision History 	:
#         Date         Programmer          Description
#        -----------------------------------------------------------------------
#
#
#
#
################################################################################

#*************************  Start of Test case  ********************************


# Connect and Load the stream in the first cylce of Time Wheel
NtgrDumpLog  $NTGR_LOG_TERSE  "TC-L3-TRAFFIC-ROUTER-LOAD-070.tcl" "******************** Starting Test case TC-L3-TRAFFIC-ROUTER-LOAD-070.tcl ********************"
foreach {chassis_id portlist} [array get ntgr_Testcase070UsedPortList] {
    UALConnectTester $chassis_id
    foreach pt $portlist {
        UALLoadPort $chassis_id $pt
    }
}
NtgrDumpLog  $NTGR_LOG_TERSE  "TC-L3-TRAFFIC-ROUTER-LOAD-070.tcl" "******************** Complete Test case TC-L3-TRAFFIC-ROUTER-LOAD-070.tcl ********************"
#*************************  End of Test case  ****************************************************************