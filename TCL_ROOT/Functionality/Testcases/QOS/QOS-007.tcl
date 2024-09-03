################################################################################
#
#  File Name    : QOS-007.tcl
#
#  Description  : This testcase used to test the match condition of VLAN ID.
#
#  Revision     :
#        Date        Programmer        Description
#        2007/01/29  Scott             Newly created    
#
################################################################################

#*************************  Start of Test case  ********************************
NtgrDumpLog  $NTGR_LOG_TERSE  "QOS-007.tcl" "******************** Starting Test case QOS-007.tcl ********************"
foreach ntgrDUT $ntgrDUTList {
    CALResetConfiguration $ntgrDUT
    CALSetSwitchMngrIPAddr $ntgrDUT
    CALDisableConsoleTimeout $ntgrDUT
}

CALAllPortsLinkDown $ntgrDUT1
CALCreateVlan "VLAN100"
CALCreateVlan "VLAN200"

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
    set NtgrTestResult(QOS-007.tcl) "OK"
} else {
    set NtgrTestResult(QOS-007.tcl) "NG"
}

NtgrDumpLog  $NTGR_LOG_TERSE  "QOS-007.tcl" "******************** Complete Test case QOS-007.tcl ********************"
#*************************  End of Test case  **********************************