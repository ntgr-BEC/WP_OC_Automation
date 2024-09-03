################################################################################
#
#  File Name    : IGMP-024.tcl
#
#  Description  : This testcase used to test multicast traffic forwarding
#                 with repeatedly sending IGMP join/leave messages.
#
#  Revision     :
#        Date        Programmer        Description
#        2007/01/15  Scott             Newly created    
#
################################################################################

#*************************  Start of Test case  ********************************
NtgrDumpLog  $NTGR_LOG_TERSE  "IGMP-024.tcl" "******************** Starting Test case IGMP-024.tcl ********************"
CALResetConfiguration $ntgrDUT
CALSetSwitchMngrIPAddr $ntgrDUT
CALDisableConsoleTimeout $ntgrDUT

sleep 90; # If there are more stack units, maybe we should changed longer.
## Configure IGMP Snooping
CALIgmpSnpEnable $ntgrDUT
CALIgmpSnpConfigInterfaceProperties $ntgrDUT

## Loading traffic
foreach {chassis_id portlist} [array get ntgrTGPortList] {
    UALConnectTester $chassis_id
    foreach pt $portlist {
        UALLoadPort $chassis_id $pt
    }
}

UALStartTrafficPerPort $ntgrTG $ntgrPort5
UALStartTrafficPerPort $ntgrTG $ntgrPort6
UALStartTrafficPerPort $ntgrTG $ntgrPort4
sleep 5

set bRet 1
set TxRx4 [UALReportPortRate $ntgrTG $ntgrPort4]
set TxRx5 [UALReportPortRate $ntgrTG $ntgrPort5]
set TxRx6 [UALReportPortRate $ntgrTG $ntgrPort6]
set rx5 [expr [lindex $TxRx5 1]/[lindex $TxRx4 0]]
set rx6 [expr [lindex $TxRx6 1]/[lindex $TxRx4 0]]
if {$rx5>0.55 && $rx5<0.65 && $rx6<0.45 && $rx6>0.35} {
    set bRet 1
} else {
    set bRet 0
}

for {set t 0} {$t<$ntgrRepeatTimes} {incr t} {
    ## Sending IGMP leaving message on port6
    UALSetTrafficPortStreamNameList $ntgrTG $ntgrPort5 "L2_STREAM_IGMP_LEAVE"
    UALLoadPort $ntgrTG $ntgrPort5
    UALStartTrafficPerPort $ntgrTG $ntgrPort5
    sleep 5
    
    set TxRx4 [UALReportPortRate $ntgrTG $ntgrPort4]
    set TxRx5 [UALReportPortRate $ntgrTG $ntgrPort5]
    set TxRx6 [UALReportPortRate $ntgrTG $ntgrPort6]
    set rx5 [expr [lindex $TxRx5 1]/[lindex $TxRx4 0]]
    set rx6 [expr [lindex $TxRx6 1]/[lindex $TxRx4 0]]
    
    if {$rx5>0.55 && $rx5<0.65 && $rx6<1.05 && $rx6>0.95} {
        set bRet [expr $bRet & 1]
    } else {
        set bRet [expr $bRet & 0]
    }

    ## Sending IGMP joining message on port6
    UALSetTrafficPortStreamNameList $ntgrTG $ntgrPort5 "L2_STREAM_IGMP_JOIN1"
    UALLoadPort $ntgrTG $ntgrPort5
    UALStartTrafficPerPort $ntgrTG $ntgrPort5
    sleep 10

    set TxRx4 [UALReportPortRate $ntgrTG $ntgrPort4]
    set TxRx5 [UALReportPortRate $ntgrTG $ntgrPort5]
    set TxRx6 [UALReportPortRate $ntgrTG $ntgrPort6]
    set rx5 [expr [lindex $TxRx5 1]/[lindex $TxRx4 0]]
    set rx6 [expr [lindex $TxRx6 1]/[lindex $TxRx4 0]]
    if {$rx5>0.55 && $rx5<0.65 && $rx6<0.45 && $rx6>0.35} {
        set bRet [expr $bRet & 1]
    } else {
        set bRet [expr $bRet & 0]
    }
}

## Stop traffic on ports
foreach {chassis_id portlist} [array get ntgrTGPortList] {
    foreach pt $portlist {
        UALStopTrafficPerPort $chassis_id $pt
    }
}


if {$bRet == 1} {
    set NtgrTestResult(IGMP-024.tcl) "OK"
} else {
    set NtgrTestResult(IGMP-024.tcl) "NG"
}

NtgrDumpLog  $NTGR_LOG_TERSE  "IGMP-024.tcl" "******************** Complete Test case IGMP-024.tcl ********************"
#*************************  End of Test case  **********************************