################################################################################
#
#  File Name		: TC-TRF-BG-Stop-064.tcl
#
#  Description       	: This Test Script stops the back ground traffic
#	
#  Config File          : TC-TRAFFIC-LOAD-059.cfg
#
#  Global Variables	: ntgr_BackGroundTrafficPortList
#				  
#  Revision History 	:
#         Date         Programmer          Description
#        -----------------------------------------------------------------------
#
################################################################################

#*************************  Start of Test case  ********************************
NtgrDumpLog $NTGR_LOG_TERSE   "TC-TRF-BG-Stop-064.tcl" "******************** Starting Test case TC-TRF-BG-Stop-064.tcl ********************"
foreach {chassis_id portlist} [array get ntgr_BackGroundTrafficPortList] {
    foreach pt $portlist {
        NtgrDumpLog $NTGR_LOG_TERSE   "TC-TRF-BG-Stop-064.tcl" "Stopped Traffic on chassis=$chassis_id, port=$pt"
        UALStopTrafficPerPort $chassis_id $pt
    }
}
NtgrDumpLog $NTGR_LOG_TERSE   "TC-TRF-BG-Stop-064.tcl" "******************** Completed Test case TC-TRF-BG-Stop-064.tcl ********************"
#*************************  End of Test case  ****************************************************************