################################################################################
#
#  File Name		: TC-UnknownUnicastTrafficStart-025.tcl
#
#  Description       	: This test Starts Unknown Unicast Traffic as defined in 
#                         configuration file.
#	
#  Config File          : TC-UnknownUnicastTrafficStart-025.cfg
#
#  Global Variables	: ntgr_Testcase025UsedPortList
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
NtgrDumpLog  $NTGR_LOG_TERSE  "TC-UnknownUnicastTrafficStart-025.tcl" "******************** Starting Test case TC-UnknownUnicastTrafficStart-025.tcl ********************"
foreach {chassis_id portlist} [array get ntgr_Testcase025UsedPortList] {
    foreach pt $portlist {
        NtgrDumpLog $NTGR_LOG_TERSE   "TC-UnknownUnicastTrafficStart-025.tcl" "Started Traffic on chassis=$chassis_id, port=$pt"
        UALStartTrafficPerPort $chassis_id $pt
    }
}
NtgrDumpLog $NTGR_LOG_TERSE "TC-UnknownUnicastTrafficStart-025.tcl" "******************** Completed Test case TC-UnknownUnicastTrafficStart-025.tcl ********************"
#*************************  End of Test case  ****************************************************************