################################################################################
#
#  File Name		: TC-L3-IPJUMBO-LOAD-079.tcl
#
#  Description       	: This test loads ip jumbo traffic configuration.
#	
#  Config File          : TC-L3-IPJUMBO-LOAD-079.cfg
#
#  Global Variables	: ntgr_Testcase079UsedPortList
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
NtgrDumpLog  $NTGR_LOG_TERSE  "TC-L3-IPJUMBO-LOAD-079.tcl" "******************** Starting Test case TC-L3-IPJUMBO-LOAD-079.tcl ********************"
foreach {chassis_id portlist} [array get ntgr_Testcase079UsedPortList] {
    UALConnectTester $chassis_id
    foreach pt $portlist {
        UALLoadPort $chassis_id $pt
    }
}
NtgrDumpLog  $NTGR_LOG_TERSE  "TC-L3-IPJUMBO-LOAD-079.tcl" "******************** Complete Test case TC-L3-IPJUMBO-LOAD-079.tcl ********************"
#*************************  End of Test case  ****************************************************************