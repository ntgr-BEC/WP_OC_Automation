################################################################################
#
#  File Name    : LAG-018.tcl
#
#  Description  : This testcase used to test interoperability with STP: 
#                 delete all configured LAGs and let STP select the best 
#                 path for forwarding.
#
#  Revision     :
#        Date        Programmer        Description
#        2006/11/28  Scott             Newly created    
#
################################################################################

#*************************  Start of Test case  ********************************
NtgrDumpLog  $NTGR_LOG_TERSE  "LAG-018.tcl" "******************** Starting Test case LAG-018.tcl ********************"
foreach dut $ntgrDUTList {
    CALResetConfiguration $dut
    CALSetSwitchPrompt $dut
    CALSetSwitchMngrIPAddr $dut
    CALDisableConsoleTimeout $dut
    CALAllPortsLinkDown $dut
}

# Create LAG
foreach lag_index $ntgrLAGIndexList018  {
	CALCreatePortChannel $lag_index
}

# Create VLANs
foreach vlan_index $ntgrVlanIndexList018  {
	CALCreateVlan $vlan_index
}

# Loading traffic
foreach {chassis_id portlist} [array get ntgrTGPortList018] {
    UALConnectTester $chassis_id
    foreach pt $portlist {
        UALLoadPort $chassis_id $pt
    }
}

# Flowing traffic
foreach {chassis_id portlist} [array get ntgrTGPortList018] {
    foreach pt $portlist {
        UALStartTrafficPerPort $chassis_id $pt
    }
}

sleep 30
set bRet 1
# Define a simple way to check test result
proc ____CheckTestResult018 {} {
    uplevel 1 {
        set txRate  [lindex [UALReportPortRate $ntgrTG $ntgrTxPort]  0]
        set rxRate1 [lindex [UALReportPortRate $ntgrTG $ntgrRxPort1] 1]
        set bRet [expr $bRet & [expr abs([expr $txRate - $rxRate1])/$txRate]<0.01]
    }
}

____CheckTestResult018

# Delete all configured LAGs
foreach dut $ntgrDUTList {
    CALDeleteAllPortChannels $dut
}

CALCreateVlan "VLAN10_1"

____CheckTestResult018

if {$bRet == 1} {
    set NtgrTestResult(LAG-018.tcl) "OK"
} else {
    set NtgrTestResult(LAG-018.tcl) "NG"
}

NtgrDumpLog  $NTGR_LOG_TERSE  "LAG-018.tcl" "******************** Complete Test case LAG-018.tcl ********************"
#*************************  End of Test case  **********************************