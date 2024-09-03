################################################################################
#
#  File Name		: TC-L3-IPJUMBO-STOP-081.tcl
#
#  Description       	: This test stops ip jumbo traffic.
#	
#  Config File          : None
#
#  Global Variables	: ntgr_Testcase079UsedPortList
#				  
#  Revision History 	:
#         Date         Programmer          Description
#        -----------------------------------------------------------------------
#
################################################################################

#*************************  Start of Test case  ********************************
NtgrDumpLog  $NTGR_LOG_TERSE  "TC-L3-IPJUMBO-STOP-081.tcl" "******************** Starting Test case TC-L3-IPJUMBO-STOP-081.tcl ********************"
foreach {chassis_id portlist} [array get ntgr_Testcase079UsedPortList] {
    foreach pt $portlist {
        UALStopTrafficPerPort $chassis_id $pt
    }
}
NtgrDumpLog  $NTGR_LOG_TERSE  "TC-L3-IPJUMBO-STOP-081.tcl" "******************** Complete Test case TC-L3-IPJUMBO-STOP-081.tcl ********************"
#*************************  End of Test case  ****************************************************************