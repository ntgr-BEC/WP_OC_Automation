################################################################################
#
#  File Name    : LAG-008.tcl
#
#  Description  : This testcase used to test shutdown/no shutdown all member ports
#                 of LAG with traffic flowing.
#
#  Revision     :
#        Date        Programmer        Description
#        2006/11/28  Scott             Newly created    
#
################################################################################

#*************************  Start of Test case  ********************************
NtgrDumpLog  $NTGR_LOG_TERSE  "LAG-008.tcl" "******************** Starting Test case LAG-008.tcl ********************"
foreach dut $ntgrDUTList {
    CALResetConfiguration $dut
    CALAllPortsLinkDown $dut
}

# Create LAG
foreach lag_index $ntgrLAGIndexList008  {
	CALCreatePortChannel $lag_index
}

# Create VLANs
foreach vlan_index $ntgrVlanIndexList008  {
	CALCreateVlan $vlan_index
}

# Loading traffic
foreach {chassis_id portlist} [array get ntgrTGPortList008] {
    UALConnectTester $chassis_id
    foreach pt $portlist {
        UALLoadPort $chassis_id $pt
    }
}

# Flowing traffic
foreach {chassis_id portlist} [array get ntgrTGPortList008] {
    foreach pt $portlist {
        UALStartTrafficPerPort $chassis_id $pt
    }
}

sleep 30
set bRet 1
set txRate  [lindex [UALReportPortRate $ntgrTG $ntgrTxPort]  0]
set rxRate1 [lindex [UALReportPortRate $ntgrTG $ntgrRxPort1] 1]
set bRet [expr $bRet & [expr abs([expr $txRate - $rxRate1])/$txRate]<0.01]

foreach {dut portlist} [array get ntgrShutdownPortList] {
    Netgear_connect_switch $dut
    foreach pt $portlist {
        CALPortLinkDown $dut $pt
    }
    Netgear_disconnect_switch $dut
}

set txRate  [lindex [UALReportPortRate $ntgrTG $ntgrTxPort]  0]
set rxRate1 [lindex [UALReportPortRate $ntgrTG $ntgrRxPort1] 1]
set bRet [expr $bRet & [expr abs([expr $txRate - $rxRate1])/$txRate]>0.5]

foreach {dut portlist} [array get ntgrShutdownPortList] {
    Netgear_connect_switch $dut
    foreach pt $portlist {
        CALPortLinkUp $dut $pt
    }
    Netgear_disconnect_switch $dut
}

sleep 30
set txRate  [lindex [UALReportPortRate $ntgrTG $ntgrTxPort]  0]
set rxRate1 [lindex [UALReportPortRate $ntgrTG $ntgrRxPort1] 1]
set bRet [expr $bRet & [expr abs([expr $txRate - $rxRate1])/$txRate]<0.01]

if {$bRet == 1} {
    set NtgrTestResult(LAG-008.tcl) "OK"
} else {
    set NtgrTestResult(LAG-008.tcl) "NG"
}

NtgrDumpLog  $NTGR_LOG_TERSE  "LAG-008.tcl" "******************** Complete Test case LAG-008.tcl ********************"
#*************************  End of Test case  **********************************