################################################################################
#
#  File Name    : IGMP-025.tcl
#
#  Description  : This testcase used to test adding/deleting a snooping port
#                 to/from a VLAN with multicast traffic traffic flowing.
#
#  Revision     :
#        Date        Programmer        Description
#        2007/01/15  Scott             Newly created    
#
################################################################################

#*************************  Start of Test case  ********************************
NtgrDumpLog  $NTGR_LOG_TERSE  "IGMP-025.tcl" "******************** Starting Test case IGMP-025.tcl ********************"
# CALResetConfiguration $ntgrDUT
# CALSetSwitchMngrIPAddr $ntgrDUT
# CALDisableConsoleTimeout $ntgrDUT
# 
# sleep 90; # If there are more stack units, maybe we should changed longer.
# CALCreateVlan "VLAN10"
# ## Configure IGMP Snooping
# CALIgmpSnpEnable $ntgrDUT
# CALIgmpSnpConfigInterfaceProperties $ntgrDUT

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

set bRet 1
proc __ReportTestResult {} {
    uplevel 1 {
        set TxRx4 [UALReportPortRate $ntgrTG $ntgrPort4]
        set TxRx5 [UALReportPortRate $ntgrTG $ntgrPort5]
        set TxRx6 [UALReportPortRate $ntgrTG $ntgrPort6]
        set rx5 [expr [lindex $TxRx5 1]/[lindex $TxRx4 0]]
        set rx6 [expr [lindex $TxRx6 1]/[lindex $TxRx4 0]]
        if {$rx5>0.35 && $rx5<0.45 && $rx6<1.05 && $rx6>0.95} {
            set bRet [expr $bRet & 1]
        } else {
            set bRet [expr $bRet & 0]
        }
    }
}

__ReportTestResult

for {set t 0} {$t<$ntgrRepeatTimes} {incr t} {
    CALDeleteVlan "VLAN10"
    CALCreateVlan "VLAN1"; # Set ports' PVID to 1
    sleep 10
    __ReportTestResult

    CALCreateVlan "VLAN10"
    sleep 10
    __ReportTestResult
}

## Stop traffic on ports
foreach {chassis_id portlist} [array get ntgrTGPortList] {
    foreach pt $portlist {
        UALStopTrafficPerPort $chassis_id $pt
    }
}


if {$bRet == 1} {
    set NtgrTestResult(IGMP-025.tcl) "OK"
} else {
    set NtgrTestResult(IGMP-025.tcl) "NG"
}

NtgrDumpLog  $NTGR_LOG_TERSE  "IGMP-025.tcl" "******************** Complete Test case IGMP-025.tcl ********************"
#*************************  End of Test case  **********************************