################################################################################
#
#  File Name    : PORT-SEC-023.tcl
#
#  Description  : This testcase used to test traffic could come in only the allowed
#                 port, from other ports should be discarded.
#
#  Revision     :
#        Date        Programmer        Description
#        2007/04/13  Scott             Newly created    
#
################################################################################

#*************************  Start of Test case  ********************************
NtgrDumpLog  $NTGR_LOG_TERSE  "PORT-SEC-023.tcl" "******************** Starting Test case PORT-SEC-023.tcl ********************"
## Initialize the DUTs
foreach ntgrDUT $ntgrDUTList {
    CALResetConfiguration $ntgrDUT
    CALDisableConsoleTimeout $ntgrDUT
}

## Shutdown additional links
CALAllPortsLinkDown $ntgrDUT1
CALPortLinkUp $ntgrDUT1 $ntgrPortList($ntgrDUT1)

sleep 30
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
if {$rx6>0.99 && $rx6<1.01} {
    set bRet [expr $bRet & 1]
} else {
    set bRet [expr $bRet & 0]
}

## Configure port security
CALEnablePortSecurity $ntgrDUT1
CALEnablePortSecurity $ntgrDUT1 $ntgrSecurityPort
CALConfigStaticAddr $ntgrDUT1 $ntgrSecurityPort $ntgrStaticAddr

set TxRx4 [UALReportPortRate $ntgrTG $ntgrPort4]
set TxRx6 [UALReportPortRate $ntgrTG $ntgrPort6]
set rx6 [expr [lindex $TxRx6 1]/[lindex $TxRx4 0]]
if {$rx6>0.97 && $rx6<0.99} {
    set bRet [expr $bRet & 1]
} else {
    set bRet [expr $bRet & 0]
}
puts "-------------$bRet---------------"
gets stdin

UALStartTrafficPerPort $ntgrTG $ntgrPort6
sleep 5

set TxRx4 [UALReportPortRate $ntgrTG $ntgrPort4]
set TxRx6 [UALReportPortRate $ntgrTG $ntgrPort6]
set rx6 [expr [lindex $TxRx6 1]/[lindex $TxRx4 0]]
if {$rx6<0.39 && $rx6>0.37} {
    set bRet [expr $bRet & 1]
} else {
    set bRet [expr $bRet & 0]
}
puts "-------------$bRet---------------"
gets stdin

set rx4 [expr [lindex $TxRx4 1]/[lindex $TxRx6 0]]
if {$rx4>0.61 && $rx4<0.63} {
    set bRet [expr $bRet & 1]
} else {
    set bRet [expr $bRet & 0]
}
puts "-------------$bRet---------------"
gets stdin

## Stop traffic on ports
foreach {chassis_id portlist} [array get ntgrTGPortList] {
    foreach pt $portlist {
        UALStopTrafficPerPort $chassis_id $pt
    }
}


if {$bRet == 1} {
    set NtgrTestResult(PORT-SEC-023.tcl) "OK"
} else {
    set NtgrTestResult(PORT-SEC-023.tcl) "NG"
}

NtgrDumpLog  $NTGR_LOG_TERSE  "PORT-SEC-023.tcl" "******************** Complete Test case PORT-SEC-023.tcl ********************"
#*************************  End of Test case  **********************************