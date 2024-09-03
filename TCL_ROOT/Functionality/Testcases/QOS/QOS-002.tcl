################################################################################
#
#  File Name    : QOS-002.tcl
#
#  Description  : This testcase used to test modifying IP DSCP mapping and
#                 check whether it take effect after modification.
#
#  Revision     :
#        Date        Programmer        Description
#        2007/01/29  Scott             Newly created    
#
################################################################################

#*************************  Start of Test case  ********************************
NtgrDumpLog  $NTGR_LOG_TERSE  "QOS-002.tcl" "******************** Starting Test case QOS-002.tcl ********************"
foreach ntgrDUT $ntgrDUTList {
    CALResetConfiguration $ntgrDUT
    CALSetSwitchMngrIPAddr $ntgrDUT
    CALDisableConsoleTimeout $ntgrDUT
}

CALAllPortsLinkDown $ntgrDUT1
Netgear_connect_switch $ntgrDUT1
CALRoutingEnable $ntgrDUT1
Netgear_disconnect_switch $ntgrDUT1

foreach vlan $ntgrVlanList {
    CALCreateVlan "$vlan"
}
CALQosSetTrustMode $ntgrDUT1 $ntgrTrustMode


## Loading traffic
set rx6 0
while {$rx6<0.45} {
    foreach {chassis_id portlist} [array get ntgrTGPortList] {
        UALConnectTester $chassis_id
        foreach pt $portlist {
            UALLoadPort $chassis_id $pt
        }
    }
    UALStartTrafficPerPort $ntgrTG $ntgrPort4
    sleep 5

    set TxRx4 [UALReportPortRate $ntgrTG $ntgrPort4]
    set TxRx6 [UALReportPortRate $ntgrTG $ntgrPort6]
    set rx6 [expr [lindex $TxRx6 1]/[lindex $TxRx4 0]]
    sleep 5
}

set bRet 1
## Configure ACL filter on DUT2
set ip_acl_id_list [getIPACLID $ntgrDUT2]
set ip_acl_rule_list [ getIPACLRule $ntgrDUT2]
set ip_acl_interface_list [getIPACLInterface $ntgrDUT2]
CALAddIPACL $ntgrDUT2 $ip_acl_rule_list
CALEnableIPACLOninterface $ntgrDUT2 $ip_acl_interface_list

set TxRx4 [UALReportPortRate $ntgrTG $ntgrPort4]
set TxRx6 [UALReportPortRate $ntgrTG $ntgrPort6]
set rx6 [expr [lindex $TxRx6 1]/[lindex $TxRx4 0]]
if {$rx6>0.2 && $rx6<0.3} {
    set bRet [expr $bRet & 1]
} else {
    set bRet [expr $bRet & 0]
}

CALQosEnableQueueStrict $ntgrDUT1 "1" $ntgrQosQueueStrictPort

set TxRx4 [UALReportPortRate $ntgrTG $ntgrPort4]
set TxRx6 [UALReportPortRate $ntgrTG $ntgrPort6]
set rx6 [expr [lindex $TxRx6 1]/[lindex $TxRx4 0]]
if {$rx6>0.45 && $rx6<0.55} {
    set bRet [expr $bRet & 1]
} else {
    set bRet [expr $bRet & 0]
}

CALQosSetDSCPMap $ntgrDUT1 "28" "3"
sleep 5

set TxRx4 [UALReportPortRate $ntgrTG $ntgrPort4]
set TxRx6 [UALReportPortRate $ntgrTG $ntgrPort6]
set rx6 [expr [lindex $TxRx6 1]/[lindex $TxRx4 0]]
if {$rx6>0.2 && $rx6<0.3} {
    set bRet [expr $bRet & 1]
} else {
    set bRet [expr $bRet & 0]
}

CALQosDisableQueueStrict $ntgrDUT1 "1" $ntgrQosQueueStrictPort
CALQosEnableQueueStrict $ntgrDUT1 "3" $ntgrQosQueueStrictPort

set TxRx4 [UALReportPortRate $ntgrTG $ntgrPort4]
set TxRx6 [UALReportPortRate $ntgrTG $ntgrPort6]
set rx6 [expr [lindex $TxRx6 1]/[lindex $TxRx4 0]]
if {$rx6>0.45 && $rx6<0.55} {
    set bRet [expr $bRet & 1]
} else {
    set bRet [expr $bRet & 0]
}

## Stop traffic on ports
foreach {chassis_id portlist} [array get ntgrTGPortList] {
    foreach pt $portlist {
        UALStopTrafficPerPort $chassis_id $pt
    }
}


if {$bRet == 1} {
    set NtgrTestResult(QOS-002.tcl) "OK"
} else {
    set NtgrTestResult(QOS-002.tcl) "NG"
}

NtgrDumpLog  $NTGR_LOG_TERSE  "QOS-002.tcl" "******************** Complete Test case QOS-002.tcl ********************"
#*************************  End of Test case  **********************************