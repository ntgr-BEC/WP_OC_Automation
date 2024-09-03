################################################################################
#
#  File Name    : IGMP-008.tcl
#
#  Description  : This testcase used to test whether DUT could delete
#                 an empty multicast group when all member leave.
#
#  Revision     :
#        Date        Programmer        Description
#        2007/01/15  Scott             Newly created    
#
################################################################################

#*************************  Start of Test case  ********************************
NtgrDumpLog  $NTGR_LOG_TERSE  "IGMP-008.tcl" "******************** Starting Test case IGMP-008.tcl ********************"
CALResetConfiguration $ntgrDUT
CALSetSwitchMngrIPAddr $ntgrDUT
CALDisableConsoleTimeout $ntgrDUT

sleep 90; # If there are more stack units, maybe we should changed longer.
## Configure IGMP Snooping
CALIgmpSnpEnable $ntgrDUT
CALIgmpSnpConfigGlobalProperties $ntgrDUT
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
if {$rx5>0.95 && $rx5<1.05 && $rx6<1.05 && $rx6>0.95} {
    set bRet 1
} else {
    set bRet 0
}

UALStopTrafficPerPort $ntgrTG $ntgrPort5
UALStopTrafficPerPort $ntgrTG $ntgrPort6
sleep 240; # Wait for max response to let multicast table age out
set TxRx4 [UALReportPortRate $ntgrTG $ntgrPort4]
set TxRx5 [UALReportPortRate $ntgrTG $ntgrPort5]
set TxRx6 [UALReportPortRate $ntgrTG $ntgrPort6]
set rx5 [expr [lindex $TxRx5 1]/[lindex $TxRx4 0]]
set rx6 [expr [lindex $TxRx6 1]/[lindex $TxRx4 0]]
if {$rx5<0.05 && $rx6<0.05} {
    set bRet [expr $bRet & 1]
} else {
    set bRet [expr $bRet & 0]
}

sleep 300; # Wait again for DUT to delete the empty groups, 
set TxRx4 [UALReportPortRate $ntgrTG $ntgrPort4]
set TxRx5 [UALReportPortRate $ntgrTG $ntgrPort5]
set TxRx6 [UALReportPortRate $ntgrTG $ntgrPort6]
set rx5 [expr [lindex $TxRx5 1]/[lindex $TxRx4 0]]
set rx6 [expr [lindex $TxRx6 1]/[lindex $TxRx4 0]]
if {$rx5>0.95 && $rx5<1.05 && $rx6<1.05 && $rx6>0.95} {
    set bRet [expr $bRet & 1]
} else {
    set bRet [expr $bRet & 0]
}

## Stop traffic on ports
foreach {chassis_id portlist} [array get ntgrTGPortList] {
    foreach pt $portlist {
        UALStopTrafficPerPort $chassis_id $pt
    }
}


if {$bRet == 1} {
    set NtgrTestResult(IGMP-008.tcl) "OK"
} else {
    set NtgrTestResult(IGMP-008.tcl) "NG"
}

NtgrDumpLog  $NTGR_LOG_TERSE  "IGMP-008.tcl" "******************** Complete Test case IGMP-008.tcl ********************"
#*************************  End of Test case  **********************************