################################################################################
#
#  File Name    : L2ACL-044.tcl
#
#  Description  : This testcase used to test filtering function of layer2 ACL.
#                 With max number of ACL and rules, to test each rule work correctly.
#
#  Revision     :
#        Date        Programmer        Description
#        2006/12/14  Scott             Newly created    
#
################################################################################

#*************************  Start of Test case  ********************************
NtgrDumpLog  $NTGR_LOG_TERSE  "L2ACL-044.tcl" "******************** Starting Test case L2ACL-044.tcl ********************"
CALResetConfiguration $ntgrDUT

set mac_acl_name_list [getMACACLID $ntgrDUT]
set mac_acl_rule_list [getMACACLRule $ntgrDUT]
set mac_acl_interface_list [getMACACLInterface $ntgrDUT]
CALAddMacACL  $ntgrDUT $mac_acl_rule_list

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
sleep 1

set bRet 1
set txRate 0
set rxRate1 0
set RetryTime 10
while {$txRate<5 || $rxRate1<5} {
    set txRate  [lindex [UALReportPortRate $ntgrTG $ntgrTxPort]  0]
    set rxRate1 [lindex [UALReportPortRate $ntgrTG $ntgrRxPort1] 1]
    sleep 5
    incr RetryTime -1
    if {$RetryTime<0} break;
}


# Collect rate for each ACL
foreach acl_if $mac_acl_interface_list {
    CALInterfaceMACACLEnable $ntgrDUT "\{$acl_if\}"
    sleep 5
    
    set txRate  [lindex [UALReportPortRate $ntgrTG $ntgrTxPort]  0]
    set rxRate1 [lindex [UALReportPortRate $ntgrTG $ntgrRxPort1] 1]
    set txrx [expr $rxRate1/$txRate]

    CALInterfaceMACACLDisable $ntgrDUT "\{$acl_if\}"

    if { $txrx<0.3 && $txrx>0.35} {
        set bRet 0
        break;
    }
}

if { $bRet == 1 } {
    set NtgrTestResult(L2ACL-044.tcl) "OK"
} else {
    set NtgrTestResult(L2ACL-044.tcl) "NG"
}

NtgrDumpLog  $NTGR_LOG_TERSE  "L2ACL-044.tcl" "******************** Complete Test case L2ACL-044.tcl ********************"
#*************************  End of Test case  **********************************