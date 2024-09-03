################################################################################
#
#  File Name		: TC-L3-IPJUMBO-START-080.tcl
#
#  Description       	: This test start ip jumbo traffic.
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
NtgrDumpLog  $NTGR_LOG_TERSE  "TC-L3-IPJUMBO-START-080.tcl" "******************** Starting Test case TC-L3-IPJUMBO-START-080.tcl ********************"
foreach {chassis_id portlist} [array get ntgr_Testcase079UsedPortList] {
    foreach pt $portlist {
        UALStartTrafficPerPort $chassis_id $pt
    }
}
NtgrDumpLog  $NTGR_LOG_TERSE  "TC-L3-IPJUMBO-START-080.tcl" "******************** Complete Test case TC-L3-IPJUMBO-START-080.tcl ********************"
#*************************  End of Test case  ****************************************************************