################################################################################
#
#  File Name		: TC-BroadcastTrafficStart-022.tcl
#
#  Description       	: This test Starts Multicast Burst Traffic as defined in 
#                         configuration file.
#	
#  Config File          : TC-BroadcastTrafficStart-022.cfg
#
#  Global Variables	: ntgr_Testcase022UsedPortList
#				  
#  Revision History 	:
#         Date         Programmer          Description
#        -----------------------------------------------------------------------
#
#
#
#
################################################################################

#*************************  Start of Test case  ********************************


# Connect and Load the stream in the first cylce of Time Wheel
NtgrDumpLog  $NTGR_LOG_TERSE  "TC-BroadcastTrafficStart-022.tcl" "******************** Starting Test case TC-BroadcastTrafficStart-022.tcl ********************"
foreach {chassis_id portlist} [array get ntgr_Testcase022UsedPortList] {
    foreach pt $portlist {
        NtgrDumpLog $NTGR_LOG_TERSE   "TC-BroadcastTrafficStart-022.tcl" "Started Traffic on chassis=$chassis_id, port=$pt"
        UALStartTrafficPerPort $chassis_id $pt
    }
}
NtgrDumpLog $NTGR_LOG_TERSE "TC-BroadcastTrafficStart-022.tcl" "******************** Completed Test case TC-BroadcastTrafficStart-022.tcl ********************"
#*************************  End of Test case  ****************************************************************
