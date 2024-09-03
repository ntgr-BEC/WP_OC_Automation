################################################################################
#
#  File Name    : LAG-005.tcl
#
#  Description  : This testcase used to test forwarding traffic over LAG
#                 with copper and fiber link.
#
#  Revision     :
#        Date        Programmer        Description
#        2006/11/28  Scott             Newly created    
#
################################################################################

#*************************  Start of Test case  ********************************
NtgrDumpLog  $NTGR_LOG_TERSE  "LAG-005.tcl" "******************** Starting Test case LAG-005.tcl ********************"
foreach dut $ntgrDUTList {
    CALResetConfiguration $dut
    CALSetSwitchPrompt $dut
    CALSetSwitchMngrIPAddr $dut
    CALDisableConsoleTimeout $dut
    CALAllPortsLinkDown $dut
}

# Create LAG
foreach lag_index $ntgrLAGIndexList005  {
	CALCreatePortChannel $lag_index
}

# Create VLANs
foreach vlan_index $ntgrVlanIndexList005  {
	CALCreateVlan $vlan_index
}

# Loading traffic
foreach {chassis_id portlist} [array get ntgrTGPortList005] {
    UALConnectTester $chassis_id
    foreach pt $portlist {
        UALLoadPort $chassis_id $pt
    }
}

# Flowing traffic
foreach {chassis_id portlist} [array get ntgrTGPortList005] {
    foreach pt $portlist {
        UALStartTrafficPerPort $chassis_id $pt
    }
}

set bRet 1
foreach dut $ntgrDUTList {
    set bRet [expr $bRet & [CALCheckExpect $dut $ntgrCmdList005(1) $ntgrExpect005(1)]]
    set bRet [expr $bRet & [CALCheckExpect $dut $ntgrCmdList005(1) $ntgrExpect005(2) 0]]
}

set txRate  [lindex [UALReportPortRate $ntgrTG $ntgrTxPort]  0]
set rxRate1 [lindex [UALReportPortRate $ntgrTG $ntgrRxPort1] 1]
set bRet [expr $bRet & [expr abs([expr $txRate - $rxRate1])/$txRate]<0.01]

foreach dut $ntgrDUTList {
    CALRebootSwitch $dut
}

sleep $ntgrDUTRebootTime

foreach dut $ntgrDUTList {
    set bRet [expr $bRet & [CALCheckExpect $dut $ntgrCmdList005(1) $ntgrExpect005(1)]]
}

set txRate  [lindex [UALReportPortRate $ntgrTG $ntgrTxPort]  0]
set rxRate1 [lindex [UALReportPortRate $ntgrTG $ntgrRxPort1] 1]
set bRet [expr $bRet & [expr abs([expr $txRate - $rxRate1])/$txRate]<0.01]

if {$bRet == 1} {
    set NtgrTestResult(LAG-005.tcl) "OK"
} else {
    set NtgrTestResult(LAG-005.tcl) "NG"
}

NtgrDumpLog  $NTGR_LOG_TERSE  "LAG-005.tcl" "******************** Complete Test case LAG-005.tcl ********************"
#*************************  End of Test case  **********************************