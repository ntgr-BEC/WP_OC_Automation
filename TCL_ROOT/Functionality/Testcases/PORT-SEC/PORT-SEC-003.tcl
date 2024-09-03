################################################################################
#
#  File Name    : PORT-SEC-003.tcl
#
#  Description  : This testcase used to test dynamic port security and fill
#                 addresses to reach the max limitation.
#
#  Revision     :
#        Date        Programmer        Description
#        2007/04/13  Scott             Newly created    
#
################################################################################

#*************************  Start of Test case  ********************************
NtgrDumpLog  $NTGR_LOG_TERSE  "PORT-SEC-003.tcl" "******************** Starting Test case PORT-SEC-003.tcl ********************"
## Initialize the DUTs
foreach ntgrDUT $ntgrDUTList {
    CALResetConfiguration $ntgrDUT
    CALDisableConsoleTimeout $ntgrDUT
}

## Shutdown additional links
CALAllPortsLinkDown $ntgrDUT1
CALCreateVlan "VLAN1000"

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
if {$rx4>0.55 && $rx4<0.65} {
    set bRet [expr $bRet & 1]
} else {
    set bRet [expr $bRet & 0]
}

set bRet [expr $bRet & [CALCheckExpect $ntgrDUT1 $ntgrDisplayDynamicCmd $ntgrDynamicAddrExpect]]

## Stop traffic on ports
foreach {chassis_id portlist} [array get ntgrTGPortList] {
    foreach pt $portlist {
        UALStopTrafficPerPort $chassis_id $pt
    }
}


if {$bRet == 1} {
    set NtgrTestResult(PORT-SEC-003.tcl) "OK"
} else {
    set NtgrTestResult(PORT-SEC-003.tcl) "NG"
}

NtgrDumpLog  $NTGR_LOG_TERSE  "PORT-SEC-003.tcl" "******************** Complete Test case PORT-SEC-003.tcl ********************"
#*************************  End of Test case  **********************************