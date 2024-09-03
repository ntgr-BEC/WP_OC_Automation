################################################################################
#
#  File Name    : LAG-011.tcl
#
#  Description  : This testcase used to test disconnect/connect the cable
#                 with traffic flowing.
#
#  Revision     :
#        Date        Programmer        Description
#        2006/11/28  Scott             Newly created    
#
################################################################################

#*************************  Start of Test case  ********************************
NtgrDumpLog  $NTGR_LOG_TERSE  "LAG-011.tcl" "******************** Starting Test case LAG-011.tcl ********************"
foreach dut $ntgrDUTList {
    CALResetConfiguration $dut
    CALAllPortsLinkDown $dut
}

# Create LAG
foreach lag_index $ntgrLAGIndexList011  {
	CALCreatePortChannel $lag_index
}

# Create VLANs
foreach vlan_index $ntgrVlanIndexList011  {
	CALCreateVlan $vlan_index
}

# Loading traffic
foreach {chassis_id portlist} [array get ntgrTGPortList011] {
    UALConnectTester $chassis_id
    foreach pt $portlist {
        UALLoadPort $chassis_id $pt
    }
}

# Flowing traffic
foreach {chassis_id portlist} [array get ntgrTGPortList011] {
    foreach pt $portlist {
        UALStartTrafficPerPort $chassis_id $pt
    }
}

set bRet 1
foreach dut $ntgrDUTList {
    set bRet [expr $bRet & [CALCheckExpect $dut $ntgrCmdList011(1) $ntgrExpect011(1)]]
    set bRet [expr $bRet & [CALCheckExpect $dut $ntgrCmdList011(1) $ntgrExpect011(2) 0]]
}
# Define a simple way to check test result
proc ____CheckTestResult011 {} {
    uplevel 1 {
        set txRate  [lindex [UALReportPortRate $ntgrTG $ntgrTxPort]  0]
        set rxRate1 [lindex [UALReportPortRate $ntgrTG $ntgrRxPort1] 1]
        set bRet [expr $bRet & [expr abs([expr $txRate - $rxRate1])/$txRate]<0.01]
    }
}

____CheckTestResult011

# Disconnect/reconnect the copper cable
NtgrDumpLog  $NTGR_LOG_TERSE  "LAG-011.tcl" "Please disconnect the copper cable, press any key when you finished."
gets stdin
foreach dut $ntgrDUTList {
    set bRet [expr $bRet & [CALCheckExpect $dut $ntgrCmdList011(1) $ntgrExpect011(1) 0]]
}
____CheckTestResult011

NtgrDumpLog  $NTGR_LOG_TERSE  "LAG-011.tcl" "Please reconnect the cable again, press any key when you finished."
gets stdin
sleep 30
foreach dut $ntgrDUTList {
    set bRet [expr $bRet & [CALCheckExpect $dut $ntgrCmdList011(1) $ntgrExpect011(1)]]
}
____CheckTestResult011

# Disconnect/reconnect the fiber cable
NtgrDumpLog  $NTGR_LOG_TERSE  "LAG-011.tcl" "Please disconnect the fiber cable, press any key when you finished."
gets stdin
foreach dut $ntgrDUTList {
    set bRet [expr $bRet & [CALCheckExpect $dut $ntgrCmdList011(2) $ntgrExpect011(2) 0]]
}
____CheckTestResult011

NtgrDumpLog  $NTGR_LOG_TERSE  "LAG-011.tcl" "Please reconnect the cable again, press any key when you finished."
gets stdin
sleep 30
foreach dut $ntgrDUTList {
    set bRet [expr $bRet & [CALCheckExpect $dut $ntgrCmdList011(2) $ntgrExpect011(2)]]
}
____CheckTestResult011

if {$bRet == 1} {
    set NtgrTestResult(LAG-011.tcl) "OK"
} else {
    set NtgrTestResult(LAG-011.tcl) "NG"
}

NtgrDumpLog  $NTGR_LOG_TERSE  "LAG-011.tcl" "******************** Complete Test case LAG-011.tcl ********************"
#*************************  End of Test case  **********************************