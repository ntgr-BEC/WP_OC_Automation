################################################################################
#
#  File Name    : MAC-004.tcl
#
#  Description  : This testcase used to test clear all MAC address and traffic forwarding.
#
#  Revision     :
#        Date        Programmer        Description
#        2006/12/27  Scott             Newly created    
#
################################################################################

#*************************  Start of Test case  ********************************
NtgrDumpLog  $NTGR_LOG_TERSE  "MAC-004.tcl" "******************** Starting Test case MAC-004.tcl ********************"
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

set txRate1 [lindex [UALReportPortRate $ntgrTG $ntgrPort1] 0]
set rxRate2 [lindex [UALReportPortRate $ntgrTG $ntgrPort2] 1]
set rxRate3 [lindex [UALReportPortRate $ntgrTG $ntgrPort3] 1]
set rx2 [expr $rxRate2/$txRate1]
set rx3 [expr $rxRate3/$txRate1]

set bRet 1
if { $rx2<1.05 && $rx2>0.95 && $rx3<0.65 && $rx3>0.55} {
    set bRet 1
} else {
    set bRet 0
}

# Stop traffic on port4
UALStopTrafficPerPort $ntgrTG $ntgrPort2

CALClearMacSwitch $ntgrDUT

# After clearing MAC table, traffic should be flowed as unknown unicast
set txRate1 [lindex [UALReportPortRate $ntgrTG $ntgrPort1] 0]
set rxRate2 [lindex [UALReportPortRate $ntgrTG $ntgrPort2] 1]
set rxRate3 [lindex [UALReportPortRate $ntgrTG $ntgrPort3] 1]
set rx2 [expr $rxRate2/$txRate1]
set rx3 [expr $rxRate3/$txRate1]

if { $rx2<1.05 && $rx2>0.95 && $rx3<1.05 && $rx3>0.95 } {
    set bRet [expr $bRet & 1]
} else {
    set bRet [expr $bRet & 0]
}

if {$bRet == 1} {
    set NtgrTestResult(MAC-004.tcl) "OK"
} else {
    set NtgrTestResult(MAC-004.tcl) "NG"
}

NtgrDumpLog  $NTGR_LOG_TERSE  "MAC-004.tcl" "******************** Complete Test case MAC-004.tcl ********************"
#*************************  End of Test case  **********************************