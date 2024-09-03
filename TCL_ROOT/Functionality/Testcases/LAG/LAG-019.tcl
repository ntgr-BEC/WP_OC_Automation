################################################################################
#
#  File Name    : LAG-019.tcl
#
#  Description  : This testcase used to test interoperability with STP: 
#                 select the best path between LAG and usual cable link.
#
#  Revision     :
#        Date        Programmer        Description
#        2006/11/28  Scott             Newly created    
#
################################################################################

#*************************  Start of Test case  ********************************
NtgrDumpLog  $NTGR_LOG_TERSE  "LAG-019.tcl" "******************** Starting Test case LAG-019.tcl ********************"
foreach dut $ntgrDUTList {
    CALResetConfiguration $dut
    CALSetSwitchPrompt $dut
    CALSetSwitchMngrIPAddr $dut
    CALDisableConsoleTimeout $dut
    CALAllPortsLinkDown $dut
}

# Set usual cable port to up
foreach {dut port} [array get ntgrCableLinkPort019] {
    Netgear_connect_switch $dut
    CALPortLinkUp $dut $port
    Netgear_disconnect_switch $dut
}

# Create LAG
foreach lag_index $ntgrLAGIndexList019  {
	CALCreatePortChannel $lag_index
}

# Create VLANs
foreach vlan_index $ntgrVlanIndexList019  {
	CALCreateVlan $vlan_index
}

# Loading traffic
foreach {chassis_id portlist} [array get ntgrTGPortList019] {
    UALConnectTester $chassis_id
    foreach pt $portlist {
        UALLoadPort $chassis_id $pt
    }
}

# Flowing traffic
foreach {chassis_id portlist} [array get ntgrTGPortList019] {
    foreach pt $portlist {
        UALStartTrafficPerPort $chassis_id $pt
    }
}

sleep 30
set bRet 1
# Define a simple way to check test result
proc ____CheckTestResult019 {} {
    uplevel 1 {
        set txRate  [lindex [UALReportPortRate $ntgrTG $ntgrTxPort]  0]
        set rxRate1 [lindex [UALReportPortRate $ntgrTG $ntgrRxPort1] 1]
        set bRet [expr $bRet & [expr abs([expr $txRate - $rxRate1])/$txRate]<0.01]
    }
}

____CheckTestResult019

# Shutdown the working LAG, and let STP select the usual cable link
foreach dut $ntgrDUTList {
    CALShutPortChannel $dut "POCH111"
    sleep 300
    ____CheckTestResult019
    CALNoShutPortChannel $dut "POCH111"
    sleep 300
    ____CheckTestResult019
}


if {$bRet == 1} {
    set NtgrTestResult(LAG-019.tcl) "OK"
} else {
    set NtgrTestResult(LAG-019.tcl) "NG"
}

NtgrDumpLog  $NTGR_LOG_TERSE  "LAG-019.tcl" "******************** Complete Test case LAG-019.tcl ********************"
#*************************  End of Test case  **********************************