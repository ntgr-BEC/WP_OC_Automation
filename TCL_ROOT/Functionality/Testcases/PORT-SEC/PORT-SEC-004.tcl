################################################################################
#
#  File Name    : PORT-SEC-004.tcl
#
#  Description  : This testcase used to test dynamic port security and setting
#                 max dynamic to 0 to discarded all traffic.
#
#  Revision     :
#        Date        Programmer        Description
#        2007/04/13  Scott             Newly created    
#
################################################################################

#*************************  Start of Test case  ********************************
NtgrDumpLog  $NTGR_LOG_TERSE  "PORT-SEC-004.tcl" "******************** Starting Test case PORT-SEC-004.tcl ********************"
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
CALSetMaxDynamic $ntgrDUT1 $ntgrSecurityPort $ntgrDynamicLimit

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
if {$rx4<0.05} {
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
    set NtgrTestResult(PORT-SEC-004.tcl) "OK"
} else {
    set NtgrTestResult(PORT-SEC-004.tcl) "NG"
}

NtgrDumpLog  $NTGR_LOG_TERSE  "PORT-SEC-004.tcl" "******************** Complete Test case PORT-SEC-004.tcl ********************"
#*************************  End of Test case  **********************************