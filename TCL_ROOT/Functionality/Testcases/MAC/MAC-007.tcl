################################################################################
#
#  File Name    : MAC-007.tcl
#
#  Description  : This testcase used to test clear MAC address in a VLAN
#                 and traffic forwarding.
#
#  Revision     :
#        Date        Programmer        Description
#        2006/12/27  Scott             Newly created    
#
################################################################################

#*************************  Start of Test case  ********************************
NtgrDumpLog  $NTGR_LOG_TERSE  "MAC-007.tcl" "******************** Starting Test case MAC-007.tcl ********************"
CALResetConfiguration $ntgrDUT

# Create VLANs
foreach vlan_index $ntgrVlanList  {
	CALCreateVlan $vlan_index
}

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

# Stop traffic on port6
UALStopTrafficPerPort $ntgrTG $ntgrPort1
sleep 2

set TxRx1 [UALReportPortRate $ntgrTG $ntgrPort1]
set TxRx2 [UALReportPortRate $ntgrTG $ntgrPort2]
set TxRx3 [UALReportPortRate $ntgrTG $ntgrPort3]
set rx1 [expr [lindex $TxRx1 1]/([lindex $TxRx2 0]+[lindex $TxRx3 0])]

set bRet 1
if { $rx1<1.05 && $rx1>0.95 && [lindex $TxRx2 1]<5 && [lindex $TxRx3 1]<5 } {
    set bRet 1
} else {
    set bRet 0
}

CALClearMacSwitch $ntgrDUT 0 0 $ntgrVlanMacClear

# After clearing MAC table, traffic should be flowed as unknown unicast
set TxRx1 [UALReportPortRate $ntgrTG $ntgrPort1]
set TxRx2 [UALReportPortRate $ntgrTG $ntgrPort2]
set TxRx3 [UALReportPortRate $ntgrTG $ntgrPort3]
set rx2 [expr [lindex $TxRx2 1]/[lindex $TxRx3 0]]
set rx3 [expr [lindex $TxRx3 1]/[lindex $TxRx2 0]]

if { $rx2<0.55 && $rx2>0.45 && $rx3<0.55 && $rx3>0.45} {
    set bRet [expr $bRet & 1]
} else {
    set bRet [expr $bRet & 0]
}

if {$bRet == 1} {
    set NtgrTestResult(MAC-007.tcl) "OK"
} else {
    set NtgrTestResult(MAC-007.tcl) "NG"
}

NtgrDumpLog  $NTGR_LOG_TERSE  "MAC-007.tcl" "******************** Complete Test case MAC-007.tcl ********************"
#*************************  End of Test case  **********************************