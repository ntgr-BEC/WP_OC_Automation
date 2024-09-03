################################################################################
#
#  File Name		: TC-L3-UDP-BROADCAST-LOAD-073.tcl
#
#  Description       	: This test loads udp broadcast streams from configuration file.
#	
#  Config File          : TC-L3-UDP-BROADCAST-LOAD-073.cfg
#
#  Global Variables	: ntgr_Testcase073UsedPortList
#				  
#  Revision History 	:
#         Date         Programmer          Description
#        -----------------------------------------------------------------------
#
#
#
#
################################################################################

# Connect and Load the stream in the first cylce of Time Wheel
NtgrDumpLog  $NTGR_LOG_TERSE  "TC-L3-UDP-BROADCAST-LOAD-073.tcl" "******************** Starting Test case TC-L3-UDP-BROADCAST-LOAD-073.tcl ********************"
foreach {chassis_id portlist} [array get ntgr_Testcase073UsedPortList] {
    UALConnectTester $chassis_id
    foreach pt $portlist {
        UALLoadPort $chassis_id $pt
    }
}
NtgrDumpLog  $NTGR_LOG_TERSE  "TC-L3-UDP-BROADCAST-LOAD-073.tcl" "******************** Complete Test case TC-L3-UDP-BROADCAST-LOAD-073.tcl ********************"
#*************************  End of Test case  ****************************************************************