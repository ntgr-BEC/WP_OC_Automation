################################################################################
#
#  File Name		: TC-TRF-FG-Start-019.tcl
#
#  Description       	: This Test Script starts the fore ground traffic
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

NtgrDumpLog $NTGR_LOG_TERSE   "TC-TRF-FG-Start-019.tcl" "******************** Starting Test case TC-TRF-FG-Start-019.tcl ********************"
foreach {chassis_id portlist} [array get ntgr_ForeGroundTrafficPortList] {
    foreach pt $portlist {
        NtgrDumpLog $NTGR_LOG_TERSE   "TC-TRF-FG-Start-019.tcl" "Started Traffic on chassis=$chassis_id, port=$pt"
        UALStartTrafficPerPort $chassis_id $pt
    }
}
NtgrDumpLog $NTGR_LOG_TERSE "TC-TRF-FG-Start-019.tcl" "******************** Completed Test case TC-TRF-FG-Start-019.tcl ********************"
#*************************  End of Test case  ****************************************************************
