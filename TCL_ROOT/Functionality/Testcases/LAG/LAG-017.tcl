################################################################################
#
#  File Name    : LAG-017.tcl
#
#  Description  : This testcase used to test interoperability with STP: 
#                 no shutdown the best LAG(Max bandwidth, previous has been shutdown)
#                 and let STP select it again for forwarding.
#
#  Revision     :
#        Date        Programmer        Description
#        2006/11/28  Scott             Newly created    
#
################################################################################

#*************************  Start of Test case  ********************************
NtgrDumpLog  $NTGR_LOG_TERSE  "LAG-017.tcl" "******************** Starting Test case LAG-017.tcl ********************"
foreach dut $ntgrDUTList {
    CALResetConfiguration $dut
    CALSetSwitchPrompt $dut
    CALSetSwitchMngrIPAddr $dut
    CALDisableConsoleTimeout $dut
    CALAllPortsLinkDown $dut
}

# Create LAG
foreach lag_index $ntgrLAGIndexList017  {
	CALCreatePortChannel $lag_index
}

# Create VLANs
foreach vlan_index $ntgrVlanIndexList017  {
	CALCreateVlan $vlan_index
}

# Loading traffic
foreach {chassis_id portlist} [array get ntgrTGPortList017] {
    UALConnectTester $chassis_id
    foreach pt $portlist {
        UALLoadPort $chassis_id $pt
    }
}

# Flowing traffic
foreach {chassis_id portlist} [array get ntgrTGPortList017] {
    foreach pt $portlist {
        UALStartTrafficPerPort $chassis_id $pt
    }
}

sleep 30
set bRet 1
# Define a simple way to check test result
proc ____CheckTestResult017 {} {
    uplevel 1 {
        set txRate  [lindex [UALReportPortRate $ntgrTG $ntgrTxPort]  0]
        set rxRate1 [lindex [UALReportPortRate $ntgrTG $ntgrRxPort1] 1]
        set bRet [expr $bRet & [expr abs([expr $txRate - $rxRate1])/$txRate]<0.01]
    }
}

____CheckTestResult017

# Shutdown the working LAG, and let STP select another LAG
foreach dut $ntgrDUTList {
    CALShutPortChannel $dut "POCH111"
    sleep 300
    ____CheckTestResult017
    CALNoShutPortChannel $dut "POCH111"
    sleep 300
    ____CheckTestResult017
}

if {$bRet == 1} {
    set NtgrTestResult(LAG-017.tcl) "OK"
} else {
    set NtgrTestResult(LAG-017.tcl) "NG"
}

NtgrDumpLog  $NTGR_LOG_TERSE  "LAG-017.tcl" "******************** Complete Test case LAG-017.tcl ********************"
#*************************  End of Test case  **********************************