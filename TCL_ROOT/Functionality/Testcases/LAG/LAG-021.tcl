################################################################################
#
#  File Name    : LAG-021.tcl
#
#  Description  : This testcase used to test interoperability with STP: 
#                 select the best path between 2 LAGs while add some 
#                 member ports to the second best LAG to make it the best.
#
#  Revision     :
#        Date        Programmer        Description
#        2006/11/28  Scott             Newly created    
#
################################################################################

#*************************  Start of Test case  ********************************
NtgrDumpLog  $NTGR_LOG_TERSE  "LAG-021.tcl" "******************** Starting Test case LAG-021.tcl ********************"
foreach dut $ntgrDUTList {
    CALResetConfiguration $dut
    CALSetSwitchPrompt $dut
    CALSetSwitchMngrIPAddr $dut
    CALDisableConsoleTimeout $dut
    CALAllPortsLinkDown $dut
}

# Create LAG
foreach lag_index $ntgrLAGIndexList021  {
	CALCreatePortChannel $lag_index
}

# Create VLANs
foreach vlan_index $ntgrVlanIndexList021  {
	CALCreateVlan $vlan_index
}

# Loading traffic
foreach {chassis_id portlist} [array get ntgrTGPortList021] {
    UALConnectTester $chassis_id
    foreach pt $portlist {
        UALLoadPort $chassis_id $pt
    }
}

# Flowing traffic
foreach {chassis_id portlist} [array get ntgrTGPortList021] {
    foreach pt $portlist {
        UALStartTrafficPerPort $chassis_id $pt
    }
}

sleep 30
set bRet 1
# Define a simple way to check test result
proc ____CheckTestResult021 {} {
    uplevel 1 {
        set txRate  [lindex [UALReportPortRate $ntgrTG $ntgrTxPort]  0]
        set rxRate1 [lindex [UALReportPortRate $ntgrTG $ntgrRxPort1] 1]
        set bRet [expr $bRet & [expr abs([expr $txRate - $rxRate1])/$txRate]<0.01]
    }
}

____CheckTestResult021

# Add some member ports from POCH222
foreach {dut portlist} [array get ntgrPortListAddToLAG] {
    CALAddPortToChannel $dut "POCH222" $portlist
}
sleep 90
____CheckTestResult021

if {$bRet == 1} {
    set NtgrTestResult(LAG-021.tcl) "OK"
} else {
    set NtgrTestResult(LAG-021.tcl) "NG"
}

NtgrDumpLog  $NTGR_LOG_TERSE  "LAG-021.tcl" "******************** Complete Test case LAG-021.tcl ********************"
#*************************  End of Test case  **********************************