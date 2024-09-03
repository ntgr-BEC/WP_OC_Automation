################################################################################
#
#  File Name    : LAG-001.tcl
#
#  Description  : This testcase used to test forwarding layer2 traffic over LAG
#                 with only 1 member port.
#
#  Revision     :
#        Date        Programmer        Description
#        2006/11/28  Scott             Newly created    
#
################################################################################

#*************************  Start of Test case  ********************************
NtgrDumpLog  $NTGR_LOG_TERSE  "LAG-001.tcl" "******************** Starting Test case LAG-001.tcl ********************"
foreach dut $ntgrDUTList {
    CALResetConfiguration $dut
    CALSetSwitchPrompt $dut
    CALSetSwitchMngrIPAddr $dut
    CALDisableConsoleTimeout $dut
    CALAllPortsLinkDown $dut
}

# Create LAG
foreach lag_index $ntgrLAGIndexList001  {
	CALCreatePortChannel $lag_index
}

# Create VLANs
foreach vlan_index $ntgrVlanIndexList001  {
	CALCreateVlan $vlan_index
}

# Loading traffic
foreach {chassis_id portlist} [array get ntgrTGPortList001] {
    UALConnectTester $chassis_id
    foreach pt $portlist {
        UALLoadPort $chassis_id $pt
    }
}

# Flowing traffic
foreach {chassis_id portlist} [array get ntgrTGPortList001] {
    foreach pt $portlist {
        UALStartTrafficPerPort $chassis_id $pt
    }
}

sleep 30
set bRet 1
set txRate  [lindex [UALReportPortRate $ntgrTG $ntgrTxPort]  0]
set rxRate1 [lindex [UALReportPortRate $ntgrTG $ntgrRxPort1] 1]
set bRet [expr $bRet & [expr abs([expr $txRate - $rxRate1])/$txRate]<0.01]

foreach dut $ntgrDUTList {
    CALRebootSwitch $dut
}

sleep $ntgrDUTRebootTime

set txRate  [lindex [UALReportPortRate $ntgrTG $ntgrTxPort]  0]
set rxRate1 [lindex [UALReportPortRate $ntgrTG $ntgrRxPort1] 1]
set bRet [expr $bRet & [expr abs([expr $txRate - $rxRate1])/$txRate]<0.01]

if {$bRet == 1} {
    set NtgrTestResult(LAG-001.tcl) "OK"
} else {
    set NtgrTestResult(LAG-001.tcl) "NG"
}

NtgrDumpLog  $NTGR_LOG_TERSE  "LAG-001.tcl" "******************** Complete Test case LAG-001.tcl ********************"
#*************************  End of Test case  **********************************