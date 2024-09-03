################################################################################
#
#  File Name    : MAC-018.tcl
#
#  Description  : This testcase used to test forwarding layer2 traffic over LAG
#                 with smallest aging time and repeatedly shutdown/no shutdown the LAG.
#
#  Revision     :
#        Date        Programmer        Description
#        2007/01/04  Scott             Newly created    
#
################################################################################

#*************************  Start of Test case  ********************************
NtgrDumpLog  $NTGR_LOG_TERSE  "MAC-018.tcl" "******************** Starting Test case MAC-018.tcl ********************"
foreach dut $ntgrDUTList {
    CALResetConfiguration $dut
    CALSetSwitchPrompt $dut
    CALSetSwitchMngrIPAddr $dut
    CALDisableConsoleTimeout $dut
    CALAllPortsLinkDown $dut
    CALSetMacAgeTime $dut $ntgrMacAgeTime

    Netgear_connect_switch $dut
    CALPortLinkUp $dut $ntgrPortList($dut)
    Netgear_disconnect_switch $dut
}

CALCreatePortChannel $ntgrLAGIndex

# Loading traffic
foreach {chassis_id portlist} [array get ntgrTGPortList] {
    UALConnectTester $chassis_id
    foreach pt $portlist {
        UALLoadPort $chassis_id $pt
    }
}

set bRet 1
# Flowing traffic on port24 and port28
UALStartTrafficPerPort $ntgrTG $ntgrPort2
UALStartTrafficPerPort $ntgrTG $ntgrPort3
sleep 90

for {set tm 0} {$tm<$ntgrRepeatTimes} {incr tm} {
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

    Netgear_connect_switch $ntgrDUT1
    CALPortLinkDown $ntgrDUT1 $ntgrLAGIndex
    sleep 5
    CALPortLinkUp $ntgrDUT1 $ntgrLAGIndex
    Netgear_disconnect_switch $ntgrDUT1
    sleep 5
}

if {$bRet == 1} {
    set NtgrTestResult(MAC-018.tcl) "OK"
} else {
    set NtgrTestResult(MAC-018.tcl) "NG"
}

NtgrDumpLog  $NTGR_LOG_TERSE  "MAC-018.tcl" "******************** Complete Test case MAC-018.tcl ********************"
#*************************  End of Test case  **********************************