################################################################################
#
#  File Name    : LAG-010.tcl
#
#  Description  : This testcase used to test plugging/unplugging GBIC module
#                 with traffic flowing.
#
#  Revision     :
#        Date        Programmer        Description
#        2006/11/28  Scott             Newly created    
#
################################################################################

#*************************  Start of Test case  ********************************
NtgrDumpLog  $NTGR_LOG_TERSE  "LAG-010.tcl" "******************** Starting Test case LAG-010.tcl ********************"
foreach dut $ntgrDUTList {
    CALResetConfiguration $dut
    CALAllPortsLinkDown $dut
}

# Create LAG
foreach lag_index $ntgrLAGIndexList010  {
	CALCreatePortChannel $lag_index
}

# Create VLANs
foreach vlan_index $ntgrVlanIndexList010  {
	CALCreateVlan $vlan_index
}

# Loading traffic
foreach {chassis_id portlist} [array get ntgrTGPortList010] {
    UALConnectTester $chassis_id
    foreach pt $portlist {
        UALLoadPort $chassis_id $pt
    }
}

# Flowing traffic
foreach {chassis_id portlist} [array get ntgrTGPortList010] {
    foreach pt $portlist {
        UALStartTrafficPerPort $chassis_id $pt
    }
}

set bRet 1
foreach dut $ntgrDUTList {
    set bRet [expr $bRet & [CALCheckExpect $dut $ntgrCmdList010(1) $ntgrExpect010(1)]]
    set bRet [expr $bRet & [CALCheckExpect $dut $ntgrCmdList010(1) $ntgrExpect010(2) 0]]
}
# Define a simple way to check test result
proc ____CheckTestResult010 {} {
    uplevel 1 {
        set txRate  [lindex [UALReportPortRate $ntgrTG $ntgrTxPort]  0]
        set rxRate1 [lindex [UALReportPortRate $ntgrTG $ntgrRxPort1] 1]
        set bRet [expr $bRet & [expr abs([expr $txRate - $rxRate1])/$txRate]<0.01]
    }
}

____CheckTestResult010

NtgrDumpLog  $NTGR_LOG_TERSE  "LAG-010.tcl" "Please unplug the GBIC module, press any key when you finished."
gets stdin
foreach dut $ntgrDUTList {
    set bRet [expr $bRet & [CALCheckExpect $dut $ntgrCmdList010(1) $ntgrExpect010(1) 0]]
}
____CheckTestResult010

NtgrDumpLog  $NTGR_LOG_TERSE  "LAG-010.tcl" "Please plug the GBIC module again, press any key when you finished."
gets stdin
sleep 30
foreach dut $ntgrDUTList {
    set bRet [expr $bRet & [CALCheckExpect $dut $ntgrCmdList010(1) $ntgrExpect010(1)]]
}
____CheckTestResult010

if {$bRet == 1} {
    set NtgrTestResult(LAG-010.tcl) "OK"
} else {
    set NtgrTestResult(LAG-010.tcl) "NG"
}

NtgrDumpLog  $NTGR_LOG_TERSE  "LAG-010.tcl" "******************** Complete Test case LAG-010.tcl ********************"
#*************************  End of Test case  **********************************