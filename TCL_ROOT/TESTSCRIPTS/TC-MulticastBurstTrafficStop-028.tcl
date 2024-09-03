################################################################################
#
#  File Name		: TC-MulticastBurstTrafficStop-028.tcl
#
#  Description       	: This test stops the Multicast Burst Traffic streams started.
#	
#  Config File          : None
#
#  Global Variables	: ntgr_Testcase027UsedPortList
#				  
#  Revision History 	:
#         Date         Programmer          Description
#        -----------------------------------------------------------------------
#
################################################################################

#*************************  Start of Test case  ********************************
NtgrDumpLog $NTGR_LOG_TERSE   "TC-MulticastBurstTrafficStop-028.tcl" "******************** Starting Test case TC-MulticastBurstTrafficStop-028.tcl ********************"
foreach {chassis_id portlist} [array get ntgr_Testcase027UsedPortList] {
    foreach pt $portlist {
        NtgrDumpLog $NTGR_LOG_TERSE   "TC-MulticastBurstTrafficStop-028.tcl" "Stopped Traffic on chassis=$chassis_id, port=$pt"
        UALStopTrafficPerPort $chassis_id $pt
    }
}
NtgrDumpLog $NTGR_LOG_TERSE   "TC-MulticastBurstTrafficStop-028.tcl" "******************** Completed Test case TC-MulticastBurstTrafficStop-028.tcl ********************"
#*************************  End of Test case  ****************************************************************