################################################################################
#
#  File Name		: TC-TRF-BG-Start-063.tcl
#
#  Description       	: This Test Script starts the back ground traffic
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

NtgrDumpLog $NTGR_LOG_TERSE   "TC-TRF-BG-Start-063.tcl" "******************** Starting Test case TC-TRF-BG-Start-063.tcl ********************"
foreach {chassis_id portlist} [array get ntgr_BackGroundTrafficPortList] {
    foreach pt $portlist {
        NtgrDumpLog $NTGR_LOG_TERSE   "TC-TRF-BG-Start-063.tcl" "Started Traffic on chassis=$chassis_id, port=$pt"
        UALStartTrafficPerPort $chassis_id $pt
    }
}
NtgrDumpLog $NTGR_LOG_TERSE "TC-TRF-BG-Start-063.tcl" "******************** Completed Test case TC-TRF-BG-Start-063.tcl ********************"
#*************************  End of Test case  ****************************************************************
