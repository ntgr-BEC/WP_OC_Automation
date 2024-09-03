################################################################################
#
#  File Name    : LAG-020.tcl
#
#  Description  : This testcase used to test interoperability with STP: 
#                 select the best path between 2 LAGs while delete 
#                 some member ports of the best LAG.
#
#  Revision     :
#        Date        Programmer        Description
#        2006/11/28  Scott             Newly created    
#
################################################################################

#*************************  Start of Test case  ********************************
NtgrDumpLog  $NTGR_LOG_TERSE  "LAG-020.tcl" "******************** Starting Test case LAG-020.tcl ********************"
foreach dut $ntgrDUTList {
    CALResetConfiguration $dut
    CALSetSwitchPrompt $dut
    CALSetSwitchMngrIPAddr $dut
    CALDisableConsoleTimeout $dut
    CALAllPortsLinkDown $dut
}

# Create LAG
foreach lag_index $ntgrLAGIndexList020  {
	CALCreatePortChannel $lag_index
}

# Create VLANs
foreach vlan_index $ntgrVlanIndexList020  {
	CALCreateVlan $vlan_index
}

# Loading traffic
foreach {chassis_id portlist} [array get ntgrTGPortList020] {
    UALConnectTester $chassis_id
    foreach pt $portlist {
        UALLoadPort $chassis_id $pt
    }
}

# Flowing traffic
foreach {chassis_id portlist} [array get ntgrTGPortList020] {
    foreach pt $portlist {
        UALStartTrafficPerPort $chassis_id $pt
    }
}

sleep 30
set bRet 1
# Define a simple way to check test result
proc ____CheckTestResult020 {} {
    uplevel 1 {
        set txRate  [lindex [UALReportPortRate $ntgrTG $ntgrTxPort]  0]
        set rxRate1 [lindex [UALReportPortRate $ntgrTG $ntgrRxPort1] 1]
        set bRet [expr $bRet & [expr abs([expr $txRate - $rxRate1])/$txRate]<0.01]
    }
}

____CheckTestResult020

# Delete some member ports from POCH111
foreach {dut portlist} [array get ntgrPortListDeleteFromLAG] {
    CALDelPortFromChannel $dut "POCH111" $portlist
}
sleep 90
____CheckTestResult020

if {$bRet == 1} {
    set NtgrTestResult(LAG-020.tcl) "OK"
} else {
    set NtgrTestResult(LAG-020.tcl) "NG"
}

NtgrDumpLog  $NTGR_LOG_TERSE  "LAG-020.tcl" "******************** Complete Test case LAG-020.tcl ********************"
#*************************  End of Test case  **********************************