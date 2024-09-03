################################################################################
#
#  File Name		: TC-L3-UDP-BROADCAST-STOP-075.tcl
#
#  Description       	: This test stops udp broadcast traffic.
#	
#  Config File          : None
#
#  Global Variables	: ntgr_Testcase073UsedPortList
#				  
#  Revision History 	:
#         Date         Programmer          Description
#        -----------------------------------------------------------------------
#
################################################################################

#*************************  Start of Test case  ********************************
NtgrDumpLog  $NTGR_LOG_TERSE  "TC-L3-UDP-BROADCAST-STOP-075.tcl" "******************** Starting Test case TC-L3-UDP-BROADCAST-STOP-075.tcl ********************"
foreach {chassis_id portlist} [array get ntgr_Testcase073UsedPortList] {
    foreach pt $portlist {
        UALStopTrafficPerPort $chassis_id $pt
    }
}
NtgrDumpLog  $NTGR_LOG_TERSE  "TC-L3-UDP-BROADCAST-STOP-075.tcl" "******************** Complete Test case TC-L3-UDP-BROADCAST-STOP-075.tcl ********************"
#*************************  End of Test case  ****************************************************************