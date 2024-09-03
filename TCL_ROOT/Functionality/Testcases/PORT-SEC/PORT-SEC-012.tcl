################################################################################
#
#  File Name    : PORT-SEC-012.tcl
#
#  Description  : This testcase used to test moving all dynamic to static and it
#                 should not exceed the static limitation corresponding traffic
#                 will be allowed on the security port.
#
#  Revision     :
#        Date        Programmer        Description
#        2007/04/13  Scott             Newly created    
#
################################################################################

#*************************  Start of Test case  ********************************
NtgrDumpLog  $NTGR_LOG_TERSE  "PORT-SEC-012.tcl" "******************** Starting Test case PORT-SEC-012.tcl ********************"
## Initialize the DUTs
foreach ntgrDUT $ntgrDUTList {
    CALResetConfiguration $ntgrDUT
    CALDisableConsoleTimeout $ntgrDUT
}

## Shutdown additional links
CALAllPortsLinkDown $ntgrDUT1
CALPortLinkUp $ntgrDUT1 $ntgrPortList($ntgrDUT1)

## Configure port security
CALEnablePortSecurity $ntgrDUT1
CALEnablePortSecurity $ntgrDUT1 $ntgrSecurityPort

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
if {$rx4>0.95 && $rx4<1.05} {
    set bRet [expr $bRet & 1]
} else {
    set bRet [expr $bRet & 0]
}

CALSetMaxStatic $ntgrDUT1 $ntgrSecurityPort 15
CALConfigStaticAddr $ntgrDUT1 $ntgrSecurityPort "move"
CALSetMaxDynamic $ntgrDUT1 $ntgrSecurityPort 0

set TxRx4 [UALReportPortRate $ntgrTG $ntgrPort4]
set TxRx6 [UALReportPortRate $ntgrTG $ntgrPort6]
set rx4 [expr [lindex $TxRx4 1]/[lindex $TxRx6 0]]
if {$rx4>0.02 && $rx4<0.03} {
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
    set NtgrTestResult(PORT-SEC-012.tcl) "OK"
} else {
    set NtgrTestResult(PORT-SEC-012.tcl) "NG"
}

NtgrDumpLog  $NTGR_LOG_TERSE  "PORT-SEC-012.tcl" "******************** Complete Test case PORT-SEC-012.tcl ********************"
#*************************  End of Test case  **********************************