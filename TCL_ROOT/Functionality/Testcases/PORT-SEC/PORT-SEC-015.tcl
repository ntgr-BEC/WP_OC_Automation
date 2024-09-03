################################################################################
#
#  File Name    : PORT-SEC-015.tcl
#
#  Description  : This testcase used to test moving dynamic to static port
#                 security over LAG.
#
#  Revision     :
#        Date        Programmer        Description
#        2007/04/13  Scott             Newly created    
#
################################################################################

#*************************  Start of Test case  ********************************
NtgrDumpLog  $NTGR_LOG_TERSE  "PORT-SEC-015.tcl" "******************** Starting Test case PORT-SEC-015.tcl ********************"
## Initialize the DUTs
foreach ntgrDUT $ntgrDUTList {
    CALResetConfiguration $ntgrDUT
    CALDisableConsoleTimeout $ntgrDUT
}
sleep 60
## Create LAG and VLAN
CALCreatePortChannel "POCH111"
CALCreateVlan "VLAN1000"
set lag_if [CALGetLAGLogicalIF $ntgrDUT1 "POCH111"]

## Configure port security
CALEnablePortSecurity $ntgrDUT1
CALEnablePortSecurity $ntgrDUT1 $lag_if

## Loading traffic
foreach {chassis_id portlist} [array get ntgrTGPortList] {
    UALConnectTester $chassis_id
    foreach pt $portlist {
        UALLoadPort $chassis_id $pt
    }
}

UALStartTrafficPerPort $ntgrTG $ntgrPort6
UALStartTrafficPerPort $ntgrTG $ntgrPort4
sleep 5

set bRet 1
set TxRx4 [UALReportPortRate $ntgrTG $ntgrPort4]
set TxRx6 [UALReportPortRate $ntgrTG $ntgrPort6]
set rx4 [expr [lindex $TxRx4 1]/[lindex $TxRx6 0]]
if {$rx4>0.57 && $rx4<0.63} {
    set bRet [expr $bRet & 1]
} else {
    set bRet [expr $bRet & 0]
}

CALConfigStaticAddr $ntgrDUT1 $lag_if "move"
set TxRx4 [UALReportPortRate $ntgrTG $ntgrPort4]
set TxRx6 [UALReportPortRate $ntgrTG $ntgrPort6]
set rx4 [expr [lindex $TxRx4 1]/[lindex $TxRx6 0]]
if {$rx4>0.58 && $rx4<0.65} {
    set bRet [expr $bRet & 1]
} else {
    set bRet [expr $bRet & 0]
}

CALSetMaxDynamic $ntgrDUT1 $lag_if 0
set TxRx4 [UALReportPortRate $ntgrTG $ntgrPort4]
set TxRx6 [UALReportPortRate $ntgrTG $ntgrPort6]
set rx4 [expr [lindex $TxRx4 1]/[lindex $TxRx6 0]]
if {$rx4>0.015 && $rx4<0.025} {
    set bRet [expr $bRet & 1]
} else {
    set bRet [expr $bRet & 0]
}

CALSetMaxStatic $ntgrDUT1 $lag_if 0
set TxRx4 [UALReportPortRate $ntgrTG $ntgrPort4]
set TxRx6 [UALReportPortRate $ntgrTG $ntgrPort6]
set rx4 [expr [lindex $TxRx4 1]/[lindex $TxRx6 0]]
if {$rx4<0.0001} {
    set bRet [expr $bRet & 1]
} else {
    set bRet [expr $bRet & 0]
    NtgrDumpLog  $NTGR_LOG_TERSE  "PORT-SEC-015.tcl" "Unexpected: [lindex $TxRx4 1]/[lindex $TxRx6 0]=$rx4 should be smaller than 0.0001"
}

CALResetMaxDynamic $ntgrDUT1 $lag_if
CALResetMaxStatic $ntgrDUT1 $lag_if
CALConfigStaticAddr $ntgrDUT1 $lag_if "move"
set TxRx4 [UALReportPortRate $ntgrTG $ntgrPort4]
set TxRx6 [UALReportPortRate $ntgrTG $ntgrPort6]
set rx4 [expr [lindex $TxRx4 1]/[lindex $TxRx6 0]]
if {$rx4>0.58 && $rx4<0.65} {
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
    set NtgrTestResult(PORT-SEC-015.tcl) "OK"
} else {
    set NtgrTestResult(PORT-SEC-015.tcl) "NG"
}

NtgrDumpLog  $NTGR_LOG_TERSE  "PORT-SEC-015.tcl" "******************** Complete Test case PORT-SEC-015.tcl ********************"
#*************************  End of Test case  **********************************