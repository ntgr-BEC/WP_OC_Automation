################################################################################
#
#  File Name    : MAC-001.tcl
#
#  Description  : This testcase used to test basic switch MAC address learning
#                 and traffic forwarding.
#
#  Revision     :
#        Date        Programmer        Description
#        2006/12/27  Scott             Newly created    
#
################################################################################

#*************************  Start of Test case  ********************************
NtgrDumpLog  $NTGR_LOG_TERSE  "MAC-001.tcl" "******************** Starting Test case MAC-001.tcl ********************"
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
# Collect rate info several times
set txRate1 [lindex [UALReportPortRate $ntgrTG $ntgrPort1] 0]
set rxRate2 [lindex [UALReportPortRate $ntgrTG $ntgrPort2] 1]
set rxRate3 [lindex [UALReportPortRate $ntgrTG $ntgrPort3] 1]
set rx2 [expr $rxRate2/$txRate1]
set rx3 [expr $rxRate3/$txRate1]

if { $rx2<1.05 && $rx2>0.95 && $rx3<0.65 && $rx3>0.55} {
    set NtgrTestResult(MAC-001.tcl) "OK"
} else {
    set NtgrTestResult(MAC-001.tcl) "NG"
}

NtgrDumpLog  $NTGR_LOG_TERSE  "MAC-001.tcl" "******************** Complete Test case MAC-001.tcl ********************"
#*************************  End of Test case  **********************************