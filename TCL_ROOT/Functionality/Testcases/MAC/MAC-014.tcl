################################################################################
#
#  File Name    : MAC-014.tcl
#
#  Description  : This testcase used to test repeatedly let the MAC table aging
#                 and learning the new MAC address for traffic forwarding.
#
#  Revision     :
#        Date        Programmer        Description
#        2006/12/27  Scott             Newly created    
#
################################################################################

#*************************  Start of Test case  ********************************
NtgrDumpLog  $NTGR_LOG_TERSE  "MAC-014.tcl" "******************** Starting Test case MAC-014.tcl ********************"
CALResetConfiguration $ntgrDUT
CALSetMacAgeTime $ntgrDUT $ntgrMacAgeTime

# Loading traffic
foreach {chassis_id portlist} [array get ntgrTGPortList] {
    UALConnectTester $chassis_id
    foreach pt $portlist {
        UALLoadPort $chassis_id $pt
    }
}

# Flowing traffic on port4
UALStartTrafficPerPort $ntgrTG $ntgrPort1

sleep $ntgrTrafficFlowingTime

set bRet 1
# Stop traffic on port4
UALStopTrafficPerPort $ntgrTG $ntgrPort1
CALClearMacSwitch $ntgrDUT

UALStartTrafficPerPort $ntgrTG $ntgrPort2
UALStartTrafficPerPort $ntgrTG $ntgrPort3
sleep 5

set TxRx1 [UALReportPortRate $ntgrTG $ntgrPort1]
set TxRx2 [UALReportPortRate $ntgrTG $ntgrPort2]
set TxRx3 [UALReportPortRate $ntgrTG $ntgrPort3]
set rx2 [expr [lindex $TxRx2 1]/[lindex $TxRx3 0]]
set rx3 [expr [lindex $TxRx3 1]/[lindex $TxRx2 0]]

if { $rx2<1.05 && $rx2>0.95 && $rx3<1.05 && $rx3>0.95 && [lindex $TxRx1 1]<5 } {
    set bRet [expr $bRet & 1]
} else {
    set bRet [expr $bRet & 0]
}

if {$bRet == 1} {
    set NtgrTestResult(MAC-014.tcl) "OK"
} else {
    set NtgrTestResult(MAC-014.tcl) "NG"
}

NtgrDumpLog  $NTGR_LOG_TERSE  "MAC-014.tcl" "******************** Complete Test case MAC-014.tcl ********************"
#*************************  End of Test case  **********************************