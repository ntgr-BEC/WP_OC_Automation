################################################################################
#
#  File Name    : PORT-SEC-020.tcl
#
#  Description  : This testcase used to test whether MAC security LAG
#                 port could work correctly while clearing the MAC address table.
#
#  Revision     :
#        Date        Programmer        Description
#        2007/04/13  Scott             Newly created    
#
################################################################################

#*************************  Start of Test case  ********************************
NtgrDumpLog  $NTGR_LOG_TERSE  "PORT-SEC-020.tcl" "******************** Starting Test case PORT-SEC-020.tcl ********************"
## Initialize the DUTs
foreach ntgrDUT $ntgrDUTList {
    CALResetConfiguration $ntgrDUT
    CALDisableConsoleTimeout $ntgrDUT
    CALAllPortsLinkDown $ntgrDUT
}
sleep 60
## Shutdown additional links
CALCreatePortChannel "POCH111"
CALCreateVlan "VLAN1000"
set lag_if [CALGetLAGLogicalIF $ntgrDUT1 "POCH111"]

## Configure port security
CALEnablePortSecurity $ntgrDUT1
CALEnablePortSecurity $ntgrDUT1 $lag_if
CALSetMaxDynamic $ntgrDUT1 $lag_if 90
CALSetMaxStatic $ntgrDUT1 $lag_if 10

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

CALConfigStaticAddr $ntgrDUT1 $lag_if "move"

set bRet 1
set TxRx4 [UALReportPortRate $ntgrTG $ntgrPort4]
set TxRx6 [UALReportPortRate $ntgrTG $ntgrPort6]
set rx4 [expr [lindex $TxRx4 1]/[lindex $TxRx6 0]]
if {$rx4>0.45 && $rx4<0.55} {
    set bRet [expr $bRet & 1]
} else {
    set bRet [expr $bRet & 0]
    NtgrDumpLog  $NTGR_LOG_TERSE  "PORT-SEC-020.tcl" "Unexpected: [lindex $TxRx4 1]/[lindex $TxRx6 0]=$rx4 should between 0.45 and 0.55"
}

CALClearMacSwitch $ntgrDUT1
sleep 5

set TxRx4 [UALReportPortRate $ntgrTG $ntgrPort4]
set TxRx6 [UALReportPortRate $ntgrTG $ntgrPort6]
set rx4 [expr [lindex $TxRx4 1]/[lindex $TxRx6 0]]
if {$rx4>0.45 && $rx4<0.55} {
    set bRet [expr $bRet & 1]
} else {
    set bRet [expr $bRet & 0]
    NtgrDumpLog  $NTGR_LOG_TERSE  "PORT-SEC-020.tcl" "Unexpected: [lindex $TxRx4 1]/[lindex $TxRx6 0]=$rx4 should between 0.45 and 0.55"
}


## Stop traffic on ports
foreach {chassis_id portlist} [array get ntgrTGPortList] {
    foreach pt $portlist {
        UALStopTrafficPerPort $chassis_id $pt
    }
}


if {$bRet == 1} {
    set NtgrTestResult(PORT-SEC-020.tcl) "OK"
} else {
    set NtgrTestResult(PORT-SEC-020.tcl) "NG"
}

NtgrDumpLog  $NTGR_LOG_TERSE  "PORT-SEC-020.tcl" "******************** Complete Test case PORT-SEC-020.tcl ********************"
#*************************  End of Test case  **********************************