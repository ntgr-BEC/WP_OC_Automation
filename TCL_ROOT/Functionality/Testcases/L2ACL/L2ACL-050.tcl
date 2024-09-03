################################################################################
#
#  File Name    : L2ACL-050.tcl
#
#  Description  : This testcase used to test filtering function of layer2 ACL.
#                 Using MAC ACL on LAG member ports.
#
#  Revision     :
#        Date        Programmer        Description
#        2006/12/14  Scott             Newly created    
#
################################################################################

#*************************  Start of Test case  ********************************
NtgrDumpLog  $NTGR_LOG_TERSE  "L2ACL-050.tcl" "******************** Starting Test case L2ACL-050.tcl ********************"
CALResetConfiguration $ntgrDUT1
CALResetConfiguration $ntgrDUT2

CALCreatePortChannel $ntgrLAGList

set mac_acl_name_list [getMACACLID $ntgrDUT2]
set mac_acl_rule_list [getMACACLRule $ntgrDUT2]
set mac_acl_interface_list [getMACACLInterface $ntgrDUT2]
CALAddMacACL  $ntgrDUT2 $mac_acl_rule_list
CALInterfaceMACACLEnable $ntgrDUT2 $mac_acl_interface_list

# Loading traffic
foreach {chassis_id portlist} [array get ntgrTGPortList] {
    UALConnectTester $chassis_id
    foreach pt $portlist {
        UALLoadPort $chassis_id $pt
    }
}

# Flowing traffic
foreach {chassis_id portlist} [array get ntgrTGPortList] {
    foreach pt $portlist {
        UALStartTrafficPerPort $chassis_id $pt
    }
}
sleep 10

# Collect rate info several times
set txRate 0
while { $txRate < 5 } {
    set txRate  [lindex [UALReportPortRate $ntgrTG $ntgrTxPort]  0]
    set rxRate1 [lindex [UALReportPortRate $ntgrTG $ntgrRxPort1] 1]
    sleep 5
}
set txrx [expr $rxRate1/$txRate]

if { $txrx>0.35 && $txrx<0.4} {
    set NtgrTestResult(L2ACL-050.tcl) "OK"
} else {
    set NtgrTestResult(L2ACL-050.tcl) "NG"
}

NtgrDumpLog  $NTGR_LOG_TERSE  "L2ACL-050.tcl" "******************** Complete Test case L2ACL-050.tcl ********************"
#*************************  End of Test case  **********************************