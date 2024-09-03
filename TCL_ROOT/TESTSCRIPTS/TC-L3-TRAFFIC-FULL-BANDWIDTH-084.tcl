################################################################################
#
#  File Name		: TC-L3-TRAFFIC-FULL-BANDWIDTH-084.tcl
#
#  Description       	: This testcase generate enough traffic to fill full bandwidth
#                         to check whether protocol frames could be delivered as usual.
#	
#  Config File          : TC-L3-TRAFFIC-FULL-BANDWIDTH-084.cfg
#
#  Global Variables	: ntgr_Testcase084UsedPortList
#				  
#  Revision History 	:
#         Date         Programmer          Description
#        -----------------------------------------------------------------------
#
################################################################################

NtgrDumpLog  $NTGR_LOG_TERSE  "TC-L3-TRAFFIC-FULL-BANDWIDTH-084.tcl" "******************** Starting Test case TC-L3-TRAFFIC-FULL-BANDWIDTH-084.tcl ********************"
foreach {chassis_id portlist} [array get ntgr_Testcase084UsedPortList] {
    UALConnectTester $chassis_id
    foreach pt $portlist {
        UALLoadPort $chassis_id $pt
    }
    foreach pt $portlist {
        UALStartTrafficPerPort $chassis_id $pt
    }
    puts " \nPlease go to check routes on DUTs to see whether pathes are changed.\n"
    puts " \nWhen you finished, please input the result and press Enter key to continue.\n"
    set result [gets stdin]
    foreach pt $portlist {
        UALStopTrafficPerPort $chassis_id $pt
    }
    NtgrDumpLog  $NTGR_LOG_TERSE  "TC-L3-TRAFFIC-FULL-BANDWIDTH-084.tcl" "Test result: $result"
}
NtgrDumpLog  $NTGR_LOG_TERSE  "TC-L3-TRAFFIC-FULL-BANDWIDTH-084.tcl" "******************** Complete Test case TC-L3-TRAFFIC-FULL-BANDWIDTH-084.tcl ********************"
