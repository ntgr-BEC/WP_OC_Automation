################################################################################
#
#  File Name    : PORT-SEC-030.tcl
#
#  Description  : This testcase used to enable/disable port security repeatedly
#                 on a LAG to test stability.
#
#  Revision     :
#        Date        Programmer        Description
#        2007/04/13  Scott             Newly created    
#
################################################################################

#*************************  Start of Test case  ********************************
NtgrDumpLog  $NTGR_LOG_TERSE  "PORT-SEC-030.tcl" "******************** Starting Test case PORT-SEC-030.tcl ********************"
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
if {$rx4>0.59 && $rx4<0.61} {
    set bRet [expr $bRet & 1]
} else {
    set bRet [expr $bRet & 0]
    NtgrDumpLog  $NTGR_LOG_TERSE  "PORT-SEC-030.tcl" "Unexpected: [lindex $TxRx4 1]/[lindex $TxRx6 0]=$rx4"
}

CALConfigStaticAddr $ntgrDUT1 $lag_if "move"
for {set i 0} {$i<$ntgrRepeatTimes} {incr i} {
    CALDisablePortSecurity $ntgrDUT1 $lag_if
    set TxRx4 [UALReportPortRate $ntgrTG $ntgrPort4]
    set TxRx6 [UALReportPortRate $ntgrTG $ntgrPort6]
    set rx4 [expr [lindex $TxRx4 1]/[lindex $TxRx6 0]]
    if {$rx4>0.99 && $rx4<1.01} {
        set bRet [expr $bRet & 1]
    } else {
        set bRet [expr $bRet & 0]
        NtgrDumpLog  $NTGR_LOG_TERSE  "PORT-SEC-030.tcl" "Unexpected: [lindex $TxRx4 1]/[lindex $TxRx6 0]=$rx4"
    }

    CALEnablePortSecurity $ntgrDUT1 $lag_if
    set TxRx4 [UALReportPortRate $ntgrTG $ntgrPort4]
    set TxRx6 [UALReportPortRate $ntgrTG $ntgrPort6]
    set rx4 [expr [lindex $TxRx4 1]/[lindex $TxRx6 0]]
    if {$rx4>0.61 && $rx4<0.63} {
        set bRet [expr $bRet & 1]
    } else {
        set bRet [expr $bRet & 0]
        NtgrDumpLog  $NTGR_LOG_TERSE  "PORT-SEC-030.tcl" "Unexpected: [lindex $TxRx4 1]/[lindex $TxRx6 0]=$rx4"
    }
}

## Stop traffic on ports
foreach {chassis_id portlist} [array get ntgrTGPortList] {
    foreach pt $portlist {
        UALStopTrafficPerPort $chassis_id $pt
    }
}


if {$bRet == 1} {
    set NtgrTestResult(PORT-SEC-030.tcl) "OK"
} else {
    set NtgrTestResult(PORT-SEC-030.tcl) "NG"
}

NtgrDumpLog  $NTGR_LOG_TERSE  "PORT-SEC-030.tcl" "******************** Complete Test case PORT-SEC-030.tcl ********************"
#*************************  End of Test case  **********************************