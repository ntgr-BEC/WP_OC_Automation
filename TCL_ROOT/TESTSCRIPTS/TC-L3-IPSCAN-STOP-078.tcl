################################################################################
#
#  File Name		: TC-L3-IPSCAN-STOP-078.tcl
#
#  Description       	: This test stops ip scanning traffic over layer3 routing testbed.
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
NtgrDumpLog  $NTGR_LOG_TERSE  "TC-L3-IPSCAN-STOP-078.tcl" "******************** Starting Test case TC-L3-IPSCAN-STOP-078.tcl ********************"
foreach {chassis_id portlist} [array get ntgr_Testcase076UsedPortList] {
    foreach pt $portlist {
        UALStopTrafficPerPort $chassis_id $pt
    }
}
NtgrDumpLog  $NTGR_LOG_TERSE  "TC-L3-IPSCAN-STOP-078.tcl" "******************** Complete Test case TC-L3-IPSCAN-STOP-078.tcl ********************"
#*************************  End of Test case  ****************************************************************