################################################################################
#
#  File Name    : IGMP-001.tcl
#
#  Description  : This testcase used to test processing join message and
#                 multicast traffic forwarding.
#
#  Revision     :
#        Date        Programmer        Description
#        2007/01/15  Scott             Newly created    
#
################################################################################

#*************************  Start of Test case  ********************************
NtgrDumpLog  $NTGR_LOG_TERSE  "IGMP-001.tcl" "******************** Starting Test case IGMP-001.tcl ********************"
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

UALStartTrafficPerPort $ntgrTG $ntgrPort6
UALStartTrafficPerPort $ntgrTG $ntgrPort4
sleep 5

set TxRx4 [UALReportPortRate $ntgrTG $ntgrPort4]
set TxRx5 [UALReportPortRate $ntgrTG $ntgrPort5]
set TxRx6 [UALReportPortRate $ntgrTG $ntgrPort6]
set rx5 [expr [lindex $TxRx5 1]/[lindex $TxRx4 0]]
set rx6 [expr [lindex $TxRx6 1]/[lindex $TxRx4 0]]

## Stop traffic on ports
foreach {chassis_id portlist} [array get ntgrTGPortList] {
    foreach pt $portlist {
        UALStopTrafficPerPort $chassis_id $pt
    }
}


if {$rx5>0.35 && $rx5<0.45 && $rx6<1.05 && $rx6>0.95} {
    set NtgrTestResult(IGMP-001.tcl) "OK"
} else {
    set NtgrTestResult(IGMP-001.tcl) "NG"
}

NtgrDumpLog  $NTGR_LOG_TERSE  "IGMP-001.tcl" "******************** Complete Test case IGMP-001.tcl ********************"
#*************************  End of Test case  **********************************