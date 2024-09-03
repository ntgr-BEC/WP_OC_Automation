################################################################################
#
#  File Name		: TC-L3-IPSCAN-START-077.tcl
#
#  Description       	: This test starts ip scanning traffic over layer3 routing testbed.
#	
#  Config File          : None
#
#  Global Variables	: ntgr_Testcase076UsedPortList
#				  
#  Revision History 	:
#         Date         Programmer          Description
#        -----------------------------------------------------------------------
#
################################################################################

#*************************  Start of Test case  ********************************
NtgrDumpLog  $NTGR_LOG_TERSE  "TC-L3-IPSCAN-START-077.tcl" "******************** Starting Test case TC-L3-IPSCAN-START-077.tcl ********************"
foreach {chassis_id portlist} [array get ntgr_Testcase076UsedPortList] {
    foreach pt $portlist {
        UALStartTrafficPerPort $chassis_id $pt
    }
}
NtgrDumpLog  $NTGR_LOG_TERSE  "TC-L3-IPSCAN-START-077.tcl" "******************** Complete Test case TC-L3-IPSCAN-START-077.tcl ********************"
#*************************  End of Test case  ****************************************************************