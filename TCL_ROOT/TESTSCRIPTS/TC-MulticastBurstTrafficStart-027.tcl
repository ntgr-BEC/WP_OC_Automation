################################################################################
#
#  File Name		: TC-MulticastBurstTrafficStart-027.tcl
#
#  Description       	: This test Starts Multicast Burst Traffic as defined in 
#                         configuration file.
#	
#  Config File          : TC-MulticastBurstTrafficStart-027.cfg
#
#  Global Variables	: ntgr_Testcase027UsedPortList
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
NtgrDumpLog  $NTGR_LOG_TERSE  "TC-MulticastBurstTrafficStart-027.tcl" "******************** Starting Test case TC-MulticastBurstTrafficStart-027.tcl ********************"
foreach {chassis_id portlist} [array get ntgr_Testcase027UsedPortList] {
    foreach pt $portlist {
        NtgrDumpLog $NTGR_LOG_TERSE   "TC-MulticastBurstTrafficStart-027.tcl" "Started Traffic on chassis=$chassis_id, port=$pt"
        UALStartTrafficPerPort $chassis_id $pt
    }
}
NtgrDumpLog $NTGR_LOG_TERSE "TC-MulticastBurstTrafficStart-027.tcl" "******************** Completed Test case TC-MulticastBurstTrafficStart-027.tcl ********************"
#*************************  End of Test case  ****************************************************************