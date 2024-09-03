################################################################################
#
#  File Name		: TC-L3-IPSCAN-LOAD-076.tcl
#
#  Description       	: This test loads ip scanning traffic configuration.
#	
#  Config File          : TC-L3-IPSCAN-LOAD-076.cfg
#
#  Global Variables	: ntgr_Testcase076UsedPortList
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
NtgrDumpLog  $NTGR_LOG_TERSE  "TC-L3-IPSCAN-LOAD-076.tcl" "******************** Starting Test case TC-L3-IPSCAN-LOAD-076.tcl ********************"
foreach {chassis_id portlist} [array get ntgr_Testcase076UsedPortList] {
    UALConnectTester $chassis_id
    foreach pt $portlist {
        UALLoadPort $chassis_id $pt
    }
}
NtgrDumpLog  $NTGR_LOG_TERSE  "TC-L3-IPSCAN-LOAD-076.tcl" "******************** Complete Test case TC-L3-IPSCAN-LOAD-076.tcl ********************"
#*************************  End of Test case  ****************************************************************