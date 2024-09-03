################################################################################
#
#  File Name    : QOS-028.tcl
#
#  Description  : This testcase used to test stability of repeatedly rename class-map.
#
#  Revision     :
#        Date        Programmer        Description
#        2007/01/29  Scott             Newly created    
#
################################################################################

#*************************  Start of Test case  ********************************
NtgrDumpLog  $NTGR_LOG_TERSE  "QOS-028.tcl" "******************** Starting Test case QOS-028.tcl ********************"
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
if {$rx6>0.95 && $rx6<1.05} {
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

for {set t 1} {$t<=$ntgrRepeatTimes} {incr t} {
    set TxRx4 [UALReportPortRate $ntgrTG $ntgrPort4]
    set TxRx6 [UALReportPortRate $ntgrTG $ntgrPort6]
    set rx6 [expr [lindex $TxRx6 1]/[lindex $TxRx4 0]]
    if {$rx6>0.45 && $rx6<0.55} {
        set bRet [expr $bRet & 1]
    } else {
        set bRet [expr $bRet & 0]
    }
    
    CALQosRenameClassMap $ntgrDUT1 "cls_$t" "cls_[expr $t+1]"
    sleep 5
}

## Stop traffic on ports
foreach {chassis_id portlist} [array get ntgrTGPortList] {
    foreach pt $portlist {
        UALStopTrafficPerPort $chassis_id $pt
    }
}


if {$bRet == 1} {
    set NtgrTestResult(QOS-028.tcl) "OK"
} else {
    set NtgrTestResult(QOS-028.tcl) "NG"
}

NtgrDumpLog  $NTGR_LOG_TERSE  "QOS-028.tcl" "******************** Complete Test case QOS-028.tcl ********************"
#*************************  End of Test case  **********************************