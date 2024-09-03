################################################################################
#
#  File Name    : LAG-032.tcl
#
#  Description  : This testcase used to test power off/on the DUT with traffic flowing
#
#  Revision     :
#        Date        Programmer        Description
#        2006/11/28  Scott             Newly created    
#
################################################################################

#*************************  Start of Test case  ********************************
NtgrDumpLog  $NTGR_LOG_TERSE  "LAG-032.tcl" "******************** Starting Test case LAG-032.tcl ********************"
foreach dut $ntgrDUTList {
    CALResetConfiguration $dut
    CALSetSwitchPrompt $dut
    CALSetSwitchMngrIPAddr $dut
    CALDisableConsoleTimeout $dut
    CALAllPortsLinkDown $dut
}

# Create LAG
foreach lag_index $ntgrLAGIndexList032  {
	CALCreatePortChannel $lag_index
}

# Create VLANs
foreach vlan_index $ntgrVlanIndexList032  {
	CALCreateVlan $vlan_index
}

# Loading traffic
foreach {chassis_id portlist} [array get ntgrTGPortList032] {
    UALConnectTester $chassis_id
    foreach pt $portlist {
        UALLoadPort $chassis_id $pt
    }
}

# Flowing traffic
foreach {chassis_id portlist} [array get ntgrTGPortList032] {
    foreach pt $portlist {
        UALStartTrafficPerPort $chassis_id $pt
    }
}

sleep 30
set bRet 1
set txRate  [lindex [UALReportPortRate $ntgrTG $ntgrTxPort]  0]
set rxRate1 [lindex [UALReportPortRate $ntgrTG $ntgrRxPort1] 1]
set bRet [expr $bRet & [expr abs([expr $txRate - $rxRate1])/$txRate]<0.01]

NtgrDumpLog  $NTGR_LOG_TERSE  "LAG-032.tcl" "Please power off the DUT and power on again, press any key when you finished"
gets stdin

sleep $ntgrDUTRebootTime

set txRate  [lindex [UALReportPortRate $ntgrTG $ntgrTxPort]  0]
set rxRate1 [lindex [UALReportPortRate $ntgrTG $ntgrRxPort1] 1]
set bRet [expr $bRet & [expr abs([expr $txRate - $rxRate1])/$txRate]<0.01]

if {$bRet == 1} {
    set NtgrTestResult(LAG-032.tcl) "OK"
} else {
    set NtgrTestResult(LAG-032.tcl) "NG"
}

NtgrDumpLog  $NTGR_LOG_TERSE  "LAG-032.tcl" "******************** Complete Test case LAG-032.tcl ********************"
#*************************  End of Test case  **********************************