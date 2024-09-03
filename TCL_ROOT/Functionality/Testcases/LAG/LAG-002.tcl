################################################################################
#
#  File Name    : LAG-002.tcl
#
#  Description  : This testcase used to test forwarding layer3 traffic over LAG
#                 with only 1 member port.
#
#  Revision     :
#        Date        Programmer        Description
#        2006/11/28  Scott             Newly created    
#
################################################################################

#*************************  Start of Test case  ********************************
NtgrDumpLog  $NTGR_LOG_TERSE  "LAG-002.tcl" "******************** Starting Test case LAG-002.tcl ********************"
foreach dut $ntgrDUTList {
    CALResetConfiguration $dut
    CALSetSwitchPrompt $dut
    CALSetSwitchMngrIPAddr $dut
    CALDisableConsoleTimeout $dut
    CALAllPortsLinkDown $dut
    Netgear_connect_switch $dut
    CALRoutingEnable $dut
    Netgear_disconnect_switch $dut
}

# Create LAG
foreach lag_index $ntgrLAGIndexList002  {
	CALCreatePortChannel $lag_index
}

# Create VLANs
foreach vlan_index $ntgrVlanIndexList002  {
	CALCreateVlan $vlan_index
}

# Create default static route
foreach {dut ip} [array get ntgrDefaultRoute002] {
    CALAddDefaultRoute $dut $ip
}

# Loading traffic
foreach {chassis_id portlist} [array get ntgrTGPortList002] {
    UALConnectTester $chassis_id
    foreach pt $portlist {
        UALLoadPort $chassis_id $pt
    }
}

# Flowing traffic
foreach {chassis_id portlist} [array get ntgrTGPortList002] {
    foreach pt $portlist {
        UALStartTrafficPerPort $chassis_id $pt
    }
}

sleep 30
# Collect rate info several times
set bRet 1
set txRate  [lindex [UALReportPortRate $ntgrTG $ntgrTxPort]  0]
set rxRate1 [lindex [UALReportPortRate $ntgrTG $ntgrRxPort1] 1]
set bRet [expr $bRet & [expr abs([expr $txRate - $rxRate1])/$txRate]<0.1]

foreach dut $ntgrDUTList {
    CALRebootSwitch $dut
}

sleep $ntgrDUTRebootTime

set txRate  [lindex [UALReportPortRate $ntgrTG $ntgrTxPort]  0]
set rxRate1 [lindex [UALReportPortRate $ntgrTG $ntgrRxPort1] 1]
set bRet [expr $bRet & [expr abs([expr $txRate - $rxRate1])/$txRate]<0.1]

if {$bRet == 1} {
    set NtgrTestResult(LAG-002.tcl) "OK"
} else {
    set NtgrTestResult(LAG-002.tcl) "NG"
}

NtgrDumpLog  $NTGR_LOG_TERSE  "LAG-002.tcl" "******************** Complete Test case LAG-002.tcl ********************"
#*************************  End of Test case  **********************************