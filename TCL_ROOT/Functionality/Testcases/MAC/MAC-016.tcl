################################################################################
#
#  File Name    : MAC-016.tcl
#
#  Description  : This testcase used to test basic switch MAC address learning
#                 and traffic forwarding while switch power off/on.
#
#  Revision     :
#        Date        Programmer        Description
#        2006/12/27  Scott             Newly created    
#
################################################################################

#*************************  Start of Test case  ********************************
NtgrDumpLog  $NTGR_LOG_TERSE  "MAC-016.tcl" "******************** Starting Test case MAC-016.tcl ********************"
CALResetConfiguration $ntgrDUT
CALSaveConfig $ntgrDUT

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

# Define a simple function to retrieve Tx Rx info
set bRet 1
proc __GetTxRxResult {} {
    uplevel {
        # Collect rate info several times
        set txRate1 [lindex [UALReportPortRate $ntgrTG $ntgrPort1] 0]
        set rxRate2 [lindex [UALReportPortRate $ntgrTG $ntgrPort2] 1]
        set rxRate3 [lindex [UALReportPortRate $ntgrTG $ntgrPort3] 1]
        set rx2 [expr $rxRate2/$txRate1]
        set rx3 [expr $rxRate3/$txRate1]

        if { $rx2<1.05 && $rx2>0.95 && $rx3<0.8 && $rx3>0.7} {
            set bRet [expr $bRet & 1]
        } else {
            set bRet [expr $bRet & 0]
        }
    }
}

__GetTxRxResult

NtgrDumpLog  $NTGR_LOG_TERSE  "MAC-016.tcl" "Please power off the DUT and power on again, press any key when you done."
gets stdin
sleep $ntgrRebootInterval

# Print result again
__GetTxRxResult

if { $bRet == 1 } {
    set NtgrTestResult(MAC-016.tcl) "OK"
} else {
    set NtgrTestResult(MAC-016.tcl) "NG"
}

NtgrDumpLog  $NTGR_LOG_TERSE  "MAC-016.tcl" "******************** Complete Test case MAC-016.tcl ********************"
#*************************  End of Test case  **********************************