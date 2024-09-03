################################################################################
#
#  File Name    : L2ACL-046.tcl
#
#  Description  : This testcase used to test filtering function of layer2 ACL.
#                 Create/Delete ACL repeatedly, test ACL work correctly.
#
#  Revision     :
#        Date        Programmer        Description
#        2006/12/14  Scott             Newly created    
#
################################################################################

#*************************  Start of Test case  ********************************
NtgrDumpLog  $NTGR_LOG_TERSE  "L2ACL-046.tcl" "******************** Starting Test case L2ACL-046.tcl ********************"
CALResetConfiguration $ntgrDUT

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

set bRet 1
NtgrDumpLog  $NTGR_LOG_TERSE  "L2ACL-046.tcl" "Repeat $ntgrRepeatTimes times to do create/delete ACL operation"
for {set times 0} {$times<$ntgrRepeatTimes} {incr times} {
    set mac_acl_name_list [getMACACLID $ntgrDUT]
    set mac_acl_rule_list [getMACACLRule $ntgrDUT]
    set mac_acl_interface_list [getMACACLInterface $ntgrDUT]
    CALAddMacACL $ntgrDUT $mac_acl_rule_list
    CALInterfaceMACACLEnable $ntgrDUT $mac_acl_interface_list
    sleep 5

    set txRate  [lindex [UALReportPortRate $ntgrTG $ntgrTxPort]  0]
    set rxRate1 [lindex [UALReportPortRate $ntgrTG $ntgrRxPort1] 1]
    set txrx [expr $rxRate1/$txRate]
    if { $txrx>0.4 || $txrx<0.35} {
        set bRet 0;
        break;
    }
    CALDeleteMacACL $ntgrDUT $mac_acl_name_list
}

if { $bRet == 1 } {
    set NtgrTestResult(L2ACL-046.tcl) "OK"
} else {
    set NtgrTestResult(L2ACL-046.tcl) "NG"
}

NtgrDumpLog  $NTGR_LOG_TERSE  "L2ACL-046.tcl" "******************** Complete Test case L2ACL-046.tcl ********************"
#*************************  End of Test case  **********************************