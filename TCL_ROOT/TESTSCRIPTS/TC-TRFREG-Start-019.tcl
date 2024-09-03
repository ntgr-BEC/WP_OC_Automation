################################################################################
#
#  File Name		: TC-TRFREG-Start-019.tcl
#
#  Description       	: This Test Script starts traffic from the traffic generators 
#
#  Config File          : TC-TRFREG-Start-019.cfg
#
#  Global Variables	: ntgr_trafficTestInfo
#				  
#  Revision History 	:
#         Date         Programmer          Description
#        -----------------------------------------------------------------------
#
################################################################################

#*************************  Start of Test case  ********************************

NtgrDumpLog $NTGR_LOG_TERSE   "TC-TRFREG-Start-019.tcl" "******************** Starting Test case TC-TRFREG-Start-019.tcl ********************"
#IXIA throws error when configuring Chassis-2 after configuring Chassis-1.  
#This bug has to be fixed. Work around is configure Chassis-2 and configure Chassis-1
foreach {chassis_id portlist} [array get ntgr_Testcase019UsedPortList] {
    foreach pt $portlist {
        NtgrDumpLog $NTGR_LOG_TERSE   "TC-TRFREG-Start-019.tcl" "Started Traffic on chassis=$chassis_id, port=$pt"
        UALStartTrafficPerPort $chassis_id $pt
    }
}
NtgrDumpLog $NTGR_LOG_TERSE "TC-TRFREG-Start-019.tcl" "******************** Completed Test case TC-TRFREG-Start-019.tcl ********************"
#*************************  End of Test case  ****************************************************************