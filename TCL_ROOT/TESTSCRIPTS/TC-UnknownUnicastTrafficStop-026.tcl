################################################################################
#
#  File Name		: TC-UnknownUnicastTrafficStop-026.tcl
#
#  Description       	: This test stops the Unknown Unicast Traffic streams started.
#	
#  Config File          : None
#
#  Global Variables	: ntgr_Testcase025UsedPortList
#				  
#  Revision History 	:
#         Date         Programmer          Description
#        -----------------------------------------------------------------------
#
################################################################################

#*************************  Start of Test case  ********************************
NtgrDumpLog $NTGR_LOG_TERSE   "TC-UnknownUnicastTrafficStop-026.tcl" "******************** Starting Test case TC-UnknownUnicastTrafficStop-026.tcl ********************"
foreach {chassis_id portlist} [array get ntgr_Testcase025UsedPortList] {
    foreach pt $portlist {
        NtgrDumpLog $NTGR_LOG_TERSE   "TC-UnknownUnicastTrafficStop-026.tcl" "Stopped Traffic on chassis=$chassis_id, port=$pt"
        UALStopTrafficPerPort $chassis_id $pt
    }
}
NtgrDumpLog $NTGR_LOG_TERSE   "TC-UnknownUnicastTrafficStop-026.tcl" "******************** Completed Test case TC-UnknownUnicastTrafficStop-026.tcl ********************"
#*************************  End of Test case  ****************************************************************