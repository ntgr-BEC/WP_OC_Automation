################################################################################
#
#  File Name    : LAG-016.tcl
#
#  Description  : This testcase used to test interoperability with STP: 
#                 shutdown the best LAG(Max bandwidth) and let STP select next
#                 best LAG for forwarding.
#
#  Revision     :
#        Date        Programmer        Description
#        2006/11/28  Scott             Newly created    
#
################################################################################

#*************************  Start of Test case  ********************************
NtgrDumpLog  $NTGR_LOG_TERSE  "LAG-016.tcl" "******************** Starting Test case LAG-016.tcl ********************"
foreach dut $ntgrDUTList {
    CALResetConfiguration $dut
    CALSetSwitchPrompt $dut
    CALSetSwitchMngrIPAddr $dut
    CALDisableConsoleTimeout $dut
    CALAllPortsLinkDown $dut
}

# Create LAG
foreach lag_index $ntgrLAGIndexList016  {
	CALCreatePortChannel $lag_index
}

# Create VLANs
foreach vlan_index $ntgrVlanIndexList016  {
	CALCreateVlan $vlan_index
}

# Loading traffic
foreach {chassis_id portlist} [array get ntgrTGPortList016] {
    UALConnectTester $chassis_id
    foreach pt $portlist {
        UALLoadPort $chassis_id $pt
    }
}

# Flowing traffic
foreach {chassis_id portlist} [array get ntgrTGPortList016] {
    foreach pt $portlist {
        UALStartTrafficPerPort $chassis_id $pt
    }
}

sleep 30
set bRet 1
# Define a simple way to check test result
proc ____CheckTestResult016 {} {
    uplevel 1 {
        set txRate  [lindex [UALReportPortRate $ntgrTG $ntgrTxPort]  0]
        set rxRate1 [lindex [UALReportPortRate $ntgrTG $ntgrRxPort1] 1]
        set bRet [expr $bRet & [expr abs([expr $txRate - $rxRate1])/$txRate]<0.01]
    }
}

____CheckTestResult016

# Shutdown the working LAG, and let STP select another LAG
foreach dut $ntgrDUTList {
    CALShutPortChannel $dut "POCH111"; # Here assume POCH111 is the best
    sleep 30
    ____CheckTestResult016
}

if {$bRet == 1} {
    set NtgrTestResult(LAG-016.tcl) "OK"
} else {
    set NtgrTestResult(LAG-016.tcl) "NG"
}

NtgrDumpLog  $NTGR_LOG_TERSE  "LAG-016.tcl" "******************** Complete Test case LAG-016.tcl ********************"
#*************************  End of Test case  **********************************