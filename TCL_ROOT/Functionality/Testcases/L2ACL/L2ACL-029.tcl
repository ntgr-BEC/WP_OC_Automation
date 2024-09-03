################################################################################
#
#  File Name    : L2ACL-029.tcl
#
#  Description  : This testcase used to test filtering function of layer2 ACL.
#                 With rule to permit traffic with specified Cos value
#                 and destination MAC address.
#
#  Revision     :
#        Date        Programmer        Description
#        2006/12/14  Scott             Newly created    
#
################################################################################

#*************************  Start of Test case  ********************************
NtgrDumpLog  $NTGR_LOG_TERSE  "L2ACL-029.tcl" "******************** Starting Test case L2ACL-029.tcl ********************"
CALResetConfiguration $ntgrDUT

set mac_acl_name_list [getMACACLID $ntgrDUT]
set mac_acl_rule_list [getMACACLRule $ntgrDUT]
set mac_acl_interface_list [getMACACLInterface $ntgrDUT]
CALAddMacACL  $ntgrDUT $mac_acl_rule_list
CALInterfaceMACACLEnable $ntgrDUT $mac_acl_interface_list

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
sleep 5

# Collect rate info several times
set txRate 0
while {$txRate<1} {
    set txRate  [lindex [UALReportPortRate $ntgrTG $ntgrTxPort]  0]
    set rxRate1 [lindex [UALReportPortRate $ntgrTG $ntgrRxPort1] 1]
    sleep 5
}
set txrx [expr $rxRate1/$txRate]

if { $txrx>0.2 && $txrx<0.3} {
    set NtgrTestResult(L2ACL-029.tcl) "OK"
} else {
    set NtgrTestResult(L2ACL-029.tcl) "NG"
}

NtgrDumpLog  $NTGR_LOG_TERSE  "L2ACL-029.tcl" "******************** Complete Test case L2ACL-029.tcl ********************"
#*************************  End of Test case  **********************************