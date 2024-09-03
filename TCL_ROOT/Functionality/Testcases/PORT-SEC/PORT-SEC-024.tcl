################################################################################
#
#  File Name    : PORT-SEC-024.tcl
#
#  Description  : This testcase used to test traffic should be discarded while
#                 the allowed security port is shutdown.
#
#  Revision     :
#        Date        Programmer        Description
#        2007/04/13  Scott             Newly created    
#
################################################################################

#*************************  Start of Test case  ********************************
NtgrDumpLog  $NTGR_LOG_TERSE  "PORT-SEC-024.tcl" "******************** Starting Test case PORT-SEC-024.tcl ********************"
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

## Shutdown the security port
CALPortLinkDown $ntgrDUT1 $ntgrSecurityPort
CALPortLinkUp $ntgrDUT1 $ntgrBackupSecurityPort
UALStartTrafficPerPort $ntgrTG $ntgrPort6
sleep 5

## Both ports should discard the static address, because the allowed port is down
set TxRx4 [UALReportPortRate $ntgrTG $ntgrPort4]
set TxRx6 [UALReportPortRate $ntgrTG $ntgrPort6]
set rx4 [expr [lindex $TxRx4 1]/[lindex $TxRx6 0]]
set rx6 [expr [lindex $TxRx6 1]/[lindex $TxRx4 0]]
if {$rx6>0.97 && $rx6<0.99} {
    set bRet [expr $bRet & 1]
} else {
    set bRet [expr $bRet & 0]
}
if {$rx4>0.97 && $rx4<0.99} {
    set bRet [expr $bRet & 1]
} else {
    set bRet [expr $bRet & 0]
}
puts "-------------$bRet---------------"
gets stdin

## Now no shutdown the allowed port
CALPortLinkDown $ntgrDUT1 $ntgrBackupSecurityPort
CALPortLinkUp $ntgrDUT1 $ntgrSecurityPort
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

## After dynamic learning, shutdown the allowed port again, 
## The result should be as before
CALPortLinkDown $ntgrDUT1 $ntgrSecurityPort
CALPortLinkUp $ntgrDUT1 $ntgrBackupSecurityPort
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
    set NtgrTestResult(PORT-SEC-024.tcl) "OK"
} else {
    set NtgrTestResult(PORT-SEC-024.tcl) "NG"
}

NtgrDumpLog  $NTGR_LOG_TERSE  "PORT-SEC-024.tcl" "******************** Complete Test case PORT-SEC-024.tcl ********************"
#*************************  End of Test case  **********************************