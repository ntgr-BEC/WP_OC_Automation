################################################################################
#
#  File Name		: TC-BroadcastTrafficStop-023.tcl
#
#  Description       	: This test stops the Multicast Burst Traffic streams started.
#	
#  Config File          : None
#
#  Global Variables	: ntgr_Testcase022UsedPortList
#				  
#  Revision History 	:
#         Date         Programmer          Description
#        -----------------------------------------------------------------------
#
################################################################################

#*************************  Start of Test case  ********************************
NtgrDumpLog $NTGR_LOG_TERSE   "TC-BroadcastTrafficStop-023.tcl" "******************** Starting Test case TC-BroadcastTrafficStop-023.tcl ********************"
foreach {chassis_id portlist} [array get ntgr_Testcase022UsedPortList] {
    foreach pt $portlist {
        NtgrDumpLog $NTGR_LOG_TERSE   "TC-BroadcastTrafficStop-023.tcl" "Stopped Traffic on chassis=$chassis_id, port=$pt"
        UALStopTrafficPerPort $chassis_id $pt
    }
}
NtgrDumpLog $NTGR_LOG_TERSE   "TC-BroadcastTrafficStop-023.tcl" "******************** Completed Test case TC-BroadcastTrafficStop-023.tcl ********************"
#*************************  End of Test case  ****************************************************************
