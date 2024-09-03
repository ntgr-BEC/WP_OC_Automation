################################################################################
#
#  File Name		: TC-TRFREG-Stop-020.tcl
#
#  Description       	: This Test Script stops traffic generators generating traffic.
#	
#  Config File          : TC-TRFREG-Stop-020.cfg
#
#  Global Variables	: ntgr_trafficTestInfo
#				  
#  Revision History 	:
#         Date         Programmer          Description
#        -----------------------------------------------------------------------
#
################################################################################

#*************************  Start of Test case  ********************************
NtgrDumpLog $NTGR_LOG_TERSE   "TC-TRFREG-Stop-020.tcl" "******************** Starting Test case TC-TRFREG-Stop-020.tcl ********************"
foreach {chassis_id portlist} [array get ntgr_Testcase019UsedPortList] {
    foreach pt $portlist {
        NtgrDumpLog $NTGR_LOG_TERSE   "TC-TRFREG-Stop-020.tcl" "Stopped Traffic on chassis=$chassis_id, port=$pt"
        UALStopTrafficPerPort $chassis_id $pt
    }
}
NtgrDumpLog $NTGR_LOG_TERSE   "TC-TRFREG-Stop-020.tcl" "******************** Completed Test case TC-TRFREG-Stop-020.tcl ********************"
#*************************  End of Test case  ****************************************************************