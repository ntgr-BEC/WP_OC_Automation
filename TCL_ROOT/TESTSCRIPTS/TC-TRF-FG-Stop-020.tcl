################################################################################
#
#  File Name		: TC-TRF-FG-Stop-020.tcl
#
#  Description       	: This Test Script stops the fore ground traffic
#	
#  Config File          : TC-TRAFFIC-LOAD-059.cfg
#
#  Global Variables	: ntgr_ForeGroundTrafficPortList
#				  
#  Revision History 	:
#         Date         Programmer          Description
#        -----------------------------------------------------------------------
#
################################################################################

#*************************  Start of Test case  ********************************
NtgrDumpLog $NTGR_LOG_TERSE   "TC-TRF-FG-Stop-020.tcl" "******************** Starting Test case TC-TRF-FG-Stop-020.tcl ********************"
foreach {chassis_id portlist} [array get ntgr_ForeGroundTrafficPortList] {
    foreach pt $portlist {
        NtgrDumpLog $NTGR_LOG_TERSE   "TC-TRF-FG-Stop-020.tcl" "Stopped Traffic on chassis=$chassis_id, port=$pt"
        UALStopTrafficPerPort $chassis_id $pt
    }
}
NtgrDumpLog $NTGR_LOG_TERSE   "TC-TRF-FG-Stop-020.tcl" "******************** Completed Test case TC-TRF-FG-Stop-020.tcl ********************"
#*************************  End of Test case  ****************************************************************