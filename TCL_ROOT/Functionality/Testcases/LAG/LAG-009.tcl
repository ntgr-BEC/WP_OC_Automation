################################################################################
#
#  File Name    : LAG-009.tcl
#
#  Description  : This testcase used to test adding/deleting member ports with traffic flowing.
#
#  Revision     :
#        Date        Programmer        Description
#        2006/11/28  Scott             Newly created    
#
################################################################################

#*************************  Start of Test case  ********************************
NtgrDumpLog  $NTGR_LOG_TERSE  "LAG-009.tcl" "******************** Starting Test case LAG-009.tcl ********************"
foreach dut $ntgrDUTList {
    CALResetConfiguration $dut
    CALAllPortsLinkDown $dut
}

# Create LAG
CALCreatePortChannel $ntgrLAGIndex009

# Create VLANs
foreach vlan_index $ntgrVlanIndexList009  {
	CALCreateVlan $vlan_index
}

# Loading traffic
foreach {chassis_id portlist} [array get ntgrTGPortList009] {
    UALConnectTester $chassis_id
    foreach pt $portlist {
        UALLoadPort $chassis_id $pt
    }
}

# Flowing traffic
foreach {chassis_id portlist} [array get ntgrTGPortList009] {
    foreach pt $portlist {
        UALStartTrafficPerPort $chassis_id $pt
    }
}

sleep 30
set bRet 1
# Define a simple way to check test result
proc ____CheckTestResult009 {} {
    uplevel 1 {
        set txRate  [lindex [UALReportPortRate $ntgrTG $ntgrTxPort]  0]
        set rxRate1 [lindex [UALReportPortRate $ntgrTG $ntgrRxPort1] 1]
        set bRet [expr $bRet & [expr abs([expr $txRate - $rxRate1])/$txRate]<0.01]
    }
}
____CheckTestResult009

# adding/deleting some member ports with traffic flowing
foreach {dut portlist} [array get ntgrAddDelPortList] {
    CALDelPortFromChannel $dut $ntgrLAGIndex009 $portlist
    sleep 90
    ____CheckTestResult009

    CALAddPortToChannel $dut $ntgrLAGIndex009 $portlist
    sleep 30
    ____CheckTestResult009
}

if {$bRet == 1} {
    set NtgrTestResult(LAG-009.tcl) "OK"
} else {
    set NtgrTestResult(LAG-009.tcl) "NG"
}

NtgrDumpLog  $NTGR_LOG_TERSE  "LAG-009.tcl" "******************** Complete Test case LAG-009.tcl ********************"
#*************************  End of Test case  **********************************