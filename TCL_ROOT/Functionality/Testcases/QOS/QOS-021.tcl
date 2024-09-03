################################################################################
#
#  File Name    : QOS-021.tcl
#
#  Description  : This testcase used to test mark the queue ID of the matched.
#
#  Revision     :
#        Date        Programmer        Description
#        2007/01/29  Scott             Newly created    
#
################################################################################

#*************************  Start of Test case  ********************************
NtgrDumpLog  $NTGR_LOG_TERSE  "QOS-021.tcl" "******************** Starting Test case QOS-021.tcl ********************"
foreach ntgrDUT $ntgrDUTList {
    CALResetConfiguration $ntgrDUT
    CALSetSwitchMngrIPAddr $ntgrDUT
    CALDisableConsoleTimeout $ntgrDUT
}

CALAllPortsLinkDown $ntgrDUT1
Netgear_connect_switch $ntgrDUT1
CALPortLinkUp $ntgrDUT1 $ntgrPortList($ntgrDUT1)
Netgear_disconnect_switch $ntgrDUT1

## Loading traffic
foreach {chassis_id portlist} [array get ntgrTGPortList] {
    UALConnectTester $chassis_id
    foreach pt $portlist {
        UALLoadPort $chassis_id $pt
    }
}

UALStartTrafficPerPort $ntgrTG $ntgrPort4
sleep 5

set bRet 1
set TxRx4 [UALReportPortRate $ntgrTG $ntgrPort4]
set TxRx6 [UALReportPortRate $ntgrTG $ntgrPort6]
set rx6 [expr [lindex $TxRx6 1]/[lindex $TxRx4 0]]
if {$rx6>0.45 && $rx6<0.55} {
    set bRet [expr $bRet & 1]
} else {
    set bRet [expr $bRet & 0]
}

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

## Configure policy-map
foreach {dut cls} [array get ntgrClassMapList] {
    CALQosCreateClassMap $dut $cls
}
foreach {dut plc} [array get ntgrPolicyMapList] {
    CALQosCreatePolicyMap $dut $plc
    set portlist [_ntgrQosGetPolicyApplyPorts $dut $plc]
    CALQosEnableServicePolicy $dut $plc $portlist
}
CALQosEnableQueueStrict $ntgrDUT1 "1" $ntgrQosQueueStrictPort

set TxRx4 [UALReportPortRate $ntgrTG $ntgrPort4]
set TxRx6 [UALReportPortRate $ntgrTG $ntgrPort6]
set rx6 [expr [lindex $TxRx6 1]/[lindex $TxRx4 0]]
if {$rx6>0.26 && $rx6<0.55} {
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
    set NtgrTestResult(QOS-021.tcl) "OK"
} else {
    set NtgrTestResult(QOS-021.tcl) "NG"
}

NtgrDumpLog  $NTGR_LOG_TERSE  "QOS-021.tcl" "******************** Complete Test case QOS-021.tcl ********************"
#*************************  End of Test case  **********************************