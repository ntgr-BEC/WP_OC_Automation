################################################################################
#
#  File Name    : MAC-009.tcl
#
#  Description  : This testcase tests learning MAC address and forwarding traffic
#                 while pluging/unplugging GBIC module.
#
#  Revision     :
#        Date        Programmer        Description
#        2007/01/04  Scott             Newly created    
#
################################################################################

#*************************  Start of Test case  ********************************
NtgrDumpLog  $NTGR_LOG_TERSE  "MAC-009.tcl" "******************** Starting Test case MAC-009.tcl ********************"
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

# Loading traffic
foreach {chassis_id portlist} [array get ntgrTGPortList] {
    UALConnectTester $chassis_id
    foreach pt $portlist {
        UALLoadPort $chassis_id $pt
    }
}

foreach {chassis_id portlist} [array get ntgrTGPortList] {
    foreach pt $portlist {
        UALStartTrafficPerPort $ntgrTG $pt
    }
}
sleep 20

set bRet 1
set TxRx1 [UALReportPortRate $ntgrTG $ntgrPort1]
set TxRx2 [UALReportPortRate $ntgrTG $ntgrPort2]
set TxRx3 [UALReportPortRate $ntgrTG $ntgrPort3]

set rx2 [expr [lindex $TxRx2 1]/[lindex $TxRx1 0]]
set rx3 [expr [lindex $TxRx3 1]/[lindex $TxRx1 0]]

if { $rx2<0.65 && $rx2>0.55 && $rx3<0.45 && $rx3>0.35 } {
    set bRet [expr $bRet & 1]
} else {
    set bRet [expr $bRet & 0]
}


NtgrDumpLog  $NTGR_LOG_TERSE  "MAC-009.tcl" "Please unplug the GBIC module, press any key when you finished."
gets stdin
set TxRx1 [UALReportPortRate $ntgrTG $ntgrPort1]
set TxRx2 [UALReportPortRate $ntgrTG $ntgrPort2]
set TxRx3 [UALReportPortRate $ntgrTG $ntgrPort3]

set rx2 [expr [lindex $TxRx2 1]/[lindex $TxRx1 0]]
set rx3 [expr [lindex $TxRx3 1]/[lindex $TxRx1 0]]

if { $rx2<1.05 && $rx2>0.95 && $rx3<0.01 } {
    set bRet [expr $bRet & 1]
} else {
    set bRet [expr $bRet & 0]
}


NtgrDumpLog  $NTGR_LOG_TERSE  "MAC-009.tcl" "Please plug the GBIC module again, press any key when you finished."
gets stdin
set TxRx1 [UALReportPortRate $ntgrTG $ntgrPort1]
set TxRx2 [UALReportPortRate $ntgrTG $ntgrPort2]
set TxRx3 [UALReportPortRate $ntgrTG $ntgrPort3]

set rx2 [expr [lindex $TxRx2 1]/[lindex $TxRx1 0]]
set rx3 [expr [lindex $TxRx3 1]/[lindex $TxRx1 0]]

if { $rx2<0.65 && $rx2>0.55 && $rx3<0.45 && $rx3>0.35 } {
    set bRet [expr $bRet & 1]
} else {
    set bRet [expr $bRet & 0]
}


if {$bRet == 1} {
    set NtgrTestResult(MAC-009.tcl) "OK"
} else {
    set NtgrTestResult(MAC-009.tcl) "NG"
}

NtgrDumpLog  $NTGR_LOG_TERSE  "MAC-009.tcl" "******************** Complete Test case MAC-009.tcl ********************"
#*************************  End of Test case  **********************************