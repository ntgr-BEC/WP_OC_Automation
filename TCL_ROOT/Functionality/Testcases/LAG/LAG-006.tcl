################################################################################
#
#  File Name    : LAG-006.tcl
#
#  Description  : This testcase used to test changing MTU value of LAG with traffic flowing.
#
#  Revision     :
#        Date        Programmer        Description
#        2006/11/28  Scott             Newly created    
#
################################################################################

#*************************  Start of Test case  ********************************
NtgrDumpLog  $NTGR_LOG_TERSE  "LAG-006.tcl" "******************** Starting Test case LAG-006.tcl ********************"
foreach dut $ntgrDUTList {
    CALResetConfiguration $dut
    CALAllPortsLinkDown $dut
}

# Create LAG
foreach lag_index $ntgrLAGIndexList006  {
	CALCreatePortChannel $lag_index
}

# Create VLANs
foreach vlan_index $ntgrVlanIndexList006  {
	CALCreateVlan $vlan_index
}

# Loading traffic
foreach {chassis_id portlist} [array get ntgrTGPortList006] {
    UALConnectTester $chassis_id
    foreach pt $portlist {
        UALLoadPort $chassis_id $pt
    }
}

# Flowing traffic
foreach {chassis_id portlist} [array get ntgrTGPortList006] {
    foreach pt $portlist {
        UALStartTrafficPerPort $chassis_id $pt
    }
}

# Modify the MTU value to support jumbo frame
foreach dut $ntgrDUTList {
    CALJumboFrameConfig $dut
}

set bRet 1
set txRate  [lindex [UALReportPortRate $ntgrTG $ntgrTxPort]  0]
set rxRate1 [lindex [UALReportPortRate $ntgrTG $ntgrRxPort1] 1]
set bRet [expr $bRet & [expr abs([expr $txRate - $rxRate1])/$txRate]<0.01]

# Disable jumbo frame support
foreach dut $ntgrDUTList {
    CALJumboFrameDisable $dut
}
sleep 30

set txRate  [lindex [UALReportPortRate $ntgrTG $ntgrTxPort]  0]
set rxRate1 [lindex [UALReportPortRate $ntgrTG $ntgrRxPort1] 1]
set bRet [expr $bRet & [expr abs([expr $txRate - $rxRate1])/$txRate]>0.9]

# Modify the MTU value to support jumbo frame
foreach dut $ntgrDUTList {
    CALJumboFrameConfig $dut
}
sleep 30
set txRate  [lindex [UALReportPortRate $ntgrTG $ntgrTxPort]  0]
set rxRate1 [lindex [UALReportPortRate $ntgrTG $ntgrRxPort1] 1]
set bRet [expr $bRet & [expr abs([expr $txRate - $rxRate1])/$txRate]<0.01]

if {$bRet == 1} {
    set NtgrTestResult(LAG-006.tcl) "OK"
} else {
    set NtgrTestResult(LAG-006.tcl) "NG"
}

NtgrDumpLog  $NTGR_LOG_TERSE  "LAG-006.tcl" "******************** Complete Test case LAG-006.tcl ********************"
#*************************  End of Test case  **********************************