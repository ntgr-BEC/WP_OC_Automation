################################################################################
#
#  File Name    : MAC-005.tcl
#
#  Description  : This testcase used to test clear MAC address of a given interface
#                 and traffic forwarding.
#
#  Revision     :
#        Date        Programmer        Description
#        2006/12/27  Scott             Newly created    
#
################################################################################

#*************************  Start of Test case  ********************************
NtgrDumpLog  $NTGR_LOG_TERSE  "MAC-005.tcl" "******************** Starting Test case MAC-005.tcl ********************"
CALResetConfiguration $ntgrDUT

# Loading traffic
foreach {chassis_id portlist} [array get ntgrTGPortList] {
    UALConnectTester $chassis_id
    foreach pt $portlist {
        UALLoadPort $chassis_id $pt
    }
}

# Flowing traffic
foreach {chassis_id portlist} [array get ntgrTGPortList] {
    foreach pt $portlist {
        UALStartTrafficPerPort $chassis_id $pt
    }
}

sleep 10

set TxRx1 [UALReportPortRate $ntgrTG $ntgrPort1]
set TxRx2 [UALReportPortRate $ntgrTG $ntgrPort2]
set TxRx3 [UALReportPortRate $ntgrTG $ntgrPort3]
set rx1 [expr [lindex $TxRx1 1]/[expr [lindex $TxRx2 0] + [lindex $TxRx3 0]]]
set rx2 [expr [lindex $TxRx2 1]/[lindex $TxRx1 0]]
set rx3 [expr [lindex $TxRx3 1]/[lindex $TxRx1 0]]

set bRet 1
if { $rx1<1.05 && $rx1>0.95 && $rx2<0.45 && $rx2>0.35 && $rx3<0.65 && $rx3>0.55} {
    set bRet 1
} else {
    set bRet 0
}

# Stop traffic on port4
UALStopTrafficPerPort $ntgrTG $ntgrPort2

CALClearMacSwitch $ntgrDUT 0 $ntgrDUTPort

# After clearing MAC table, traffic should be flowed as unknown unicast
set txRate1 [lindex [UALReportPortRate $ntgrTG $ntgrPort1] 0]
set rxRate2 [lindex [UALReportPortRate $ntgrTG $ntgrPort2] 1]
set rxRate3 [lindex [UALReportPortRate $ntgrTG $ntgrPort3] 1]
set rx2 [expr $rxRate2/$txRate1]
set rx3 [expr $rxRate3/$txRate1]

if { $rx2<0.45 && $rx2>0.35 && $rx3<1.05 && $rx3>0.95 } {
    set bRet [expr $bRet & 1]
} else {
    set bRet [expr $bRet & 0]
}

if {$bRet == 1} {
    set NtgrTestResult(MAC-005.tcl) "OK"
} else {
    set NtgrTestResult(MAC-005.tcl) "NG"
}

NtgrDumpLog  $NTGR_LOG_TERSE  "MAC-005.tcl" "******************** Complete Test case MAC-005.tcl ********************"
#*************************  End of Test case  **********************************