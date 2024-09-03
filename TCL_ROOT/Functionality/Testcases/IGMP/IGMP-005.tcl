################################################################################
#
#  File Name    : IGMP-005.tcl
#
#  Description  : This testcase used to test whether DUT can simulate multicast 
#                 router to send out IGMP query message.
#
#  Revision     :
#        Date        Programmer        Description
#        2007/01/15  Scott             Newly created    
#
################################################################################

#*************************  Start of Test case  ********************************
NtgrDumpLog  $NTGR_LOG_TERSE  "IGMP-005.tcl" "******************** Starting Test case IGMP-005.tcl ********************"
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

UALStartTrafficPerPort $ntgrTG $ntgrPort6
sleep 5
UALStopTrafficPerPort $ntgrTG $ntgrPort6

set key 0
while {$key != "y" && $key != "n"} {
    NtgrDumpLog  $NTGR_LOG_TERSE  "IGMP-005.tcl" "Please capture packet on port5 or port6."
    NtgrDumpLog  $NTGR_LOG_TERSE  "IGMP-005.tcl" "If you got IGMP query messages sent by the DUT, please press 'y'; otherwise press 'n'."
    set key [gets stdin]
}

## Stop traffic on ports
foreach {chassis_id portlist} [array get ntgrTGPortList] {
    foreach pt $portlist {
        UALStopTrafficPerPort $chassis_id $pt
    }
}


if {$key == "y"} {
    set NtgrTestResult(IGMP-005.tcl) "OK"
} else {
    set NtgrTestResult(IGMP-005.tcl) "NG"
}

NtgrDumpLog  $NTGR_LOG_TERSE  "IGMP-005.tcl" "******************** Complete Test case IGMP-005.tcl ********************"
#*************************  End of Test case  **********************************