################################################################################
#
#  File Name    : MAC-010.tcl
#
#  Description  : This testcase used to test learning MAC address and forwarding traffic
#                 while disconnect/reconnect the cable with traffic.
#
#  Revision     :
#        Date        Programmer        Description
#        2006/12/27  Scott             Newly created    
#
################################################################################

#*************************  Start of Test case  ********************************
NtgrDumpLog  $NTGR_LOG_TERSE  "MAC-010.tcl" "******************** Starting Test case MAC-010.tcl ********************"
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

# define a simple function to show Tx, Rx info
set bRet 1
proc __GetTxRxResult {} {
    uplevel {
        set txRate1 [lindex [UALReportPortRate $ntgrTG $ntgrPort1] 0]
        set rxRate2 [lindex [UALReportPortRate $ntgrTG $ntgrPort2] 1]
        set rxRate3 [lindex [UALReportPortRate $ntgrTG $ntgrPort3] 1]
        set rx2 [expr $rxRate2/$txRate1]
        set rx3 [expr $rxRate3/$txRate1]
        
        if { $rx2<1.05 && $rx2>0.95 && $rx3<0.65 && $rx3>0.55} {
            set bRet [expr $bRet & 1]
        } else {
            set bRet [expr $bRet & 0]
        }
    }
}

__GetTxRxResult

# Simulate cable disconnect by down the Tester's port
UALPortLinkDown $ntgrTG $ntgrPort2
sleep 5
set txRate1 [lindex [UALReportPortRate $ntgrTG $ntgrPort1] 0]
set rxRate2 [lindex [UALReportPortRate $ntgrTG $ntgrPort2] 1]
set rxRate3 [lindex [UALReportPortRate $ntgrTG $ntgrPort3] 1]
set rx3 [expr $rxRate3/$txRate1]
if { $rx3<1.05 && $rx3>0.95 } {
    set bRet [expr $bRet & 1]
} else {
    set bRet [expr $bRet & 0]
}
UALPortLinkUP $ntgrTG $ntgrPort2
sleep 10
__GetTxRxResult

if {$bRet == 1} {
    set NtgrTestResult(MAC-010.tcl) "OK"
} else {
    set NtgrTestResult(MAC-010.tcl) "NG"
}

NtgrDumpLog  $NTGR_LOG_TERSE  "MAC-010.tcl" "******************** Complete Test case MAC-010.tcl ********************"
#*************************  End of Test case  **********************************