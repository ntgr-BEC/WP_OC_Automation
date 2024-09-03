################################################################################
#
#  File Name    : MAC-013.tcl
#
#  Description  : This testcase used to test repeatedly generate traffic with
#                 the same source MAC address from different ports.
#
#  Revision     :
#        Date        Programmer        Description
#        2006/12/27  Scott             Newly created    
#
################################################################################

#*************************  Start of Test case  ********************************
NtgrDumpLog  $NTGR_LOG_TERSE  "MAC-013.tcl" "******************** Starting Test case MAC-013.tcl ********************"
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

set bRet 1
while {$ntgrRepeatTime !=0} {
    # Stop traffic on port6
    UALStopTrafficPerPort $ntgrTG $ntgrPort2
    UALStartTrafficPerPort $ntgrTG $ntgrPort3
    sleep 5

    set TxRx1 [UALReportPortRate $ntgrTG $ntgrPort1]
    set TxRx2 [UALReportPortRate $ntgrTG $ntgrPort2]
    set TxRx3 [UALReportPortRate $ntgrTG $ntgrPort3]
    set rx3 [expr [lindex $TxRx3 1]/[lindex $TxRx1 0]]

    if { $rx3<1.05 && $rx3>0.95 && [lindex $TxRx2 1]<5 } {
        set bRet [expr $bRet & 1]
    } else {
        set bRet [expr $bRet & 0]
    }

    UALStopTrafficPerPort $ntgrTG $ntgrPort3
    UALStartTrafficPerPort $ntgrTG $ntgrPort2
    sleep 5
    # After clearing MAC table, traffic should be flowed as unknown unicast
    set TxRx1 [UALReportPortRate $ntgrTG $ntgrPort1]
    set TxRx2 [UALReportPortRate $ntgrTG $ntgrPort2]
    set TxRx3 [UALReportPortRate $ntgrTG $ntgrPort3]
    set rx2 [expr [lindex $TxRx2 1]/[lindex $TxRx1 0]]
    
    if { $rx2<1.05 && $rx2>0.95 && [lindex $TxRx3 1]<5} {
        set bRet [expr $bRet & 1]
    } else {
        set bRet [expr $bRet & 0]
    }
    incr ntgrRepeatTime -1
}

if {$bRet == 1} {
    set NtgrTestResult(MAC-013.tcl) "OK"
} else {
    set NtgrTestResult(MAC-013.tcl) "NG"
}

NtgrDumpLog  $NTGR_LOG_TERSE  "MAC-013.tcl" "******************** Complete Test case MAC-013.tcl ********************"
#*************************  End of Test case  **********************************