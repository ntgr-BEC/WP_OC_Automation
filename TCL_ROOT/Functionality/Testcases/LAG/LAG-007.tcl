################################################################################
#
#  File Name    : LAG-007.tcl
#
#  Description  : This testcase used to test shutdown/no shutdown one member port of LAG
#                 with traffic flowing.
#
#  Revision     :
#        Date        Programmer        Description
#        2006/11/28  Scott             Newly created    
#
################################################################################

#*************************  Start of Test case  ********************************
NtgrDumpLog  $NTGR_LOG_TERSE  "LAG-007.tcl" "******************** Starting Test case LAG-007.tcl ********************"
foreach dut $ntgrDUTList {
    CALResetConfiguration $dut
    CALAllPortsLinkDown $dut
}

# Create LAG
foreach lag_index $ntgrLAGIndexList007  {
	CALCreatePortChannel $lag_index
}

# Create VLANs
foreach vlan_index $ntgrVlanIndexList007  {
	CALCreateVlan $vlan_index
}

# Loading traffic
foreach {chassis_id portlist} [array get ntgrTGPortList007] {
    UALConnectTester $chassis_id
    foreach pt $portlist {
        UALLoadPort $chassis_id $pt
    }
}

# Flowing traffic
foreach {chassis_id portlist} [array get ntgrTGPortList007] {
    foreach pt $portlist {
        UALStartTrafficPerPort $chassis_id $pt
    }
}

sleep 30
set bRet 1
# Define a simple way to check test result
proc ____CheckTestResult007 {} {
    uplevel 1 {
        set txRate  [lindex [UALReportPortRate $ntgrTG $ntgrTxPort]  0]
        set rxRate1 [lindex [UALReportPortRate $ntgrTG $ntgrRxPort1] 1]
        set bRet [expr $bRet & [expr abs([expr $txRate - $rxRate1])/$txRate]<0.01]
    }
}
____CheckTestResult007

foreach {dut portlist} [array get ntgrShutdownPortList] {
    Netgear_connect_switch $dut
    foreach pt $portlist {
        CALPortLinkDown $dut $pt
        sleep 30
        ____CheckTestResult007
        CALPortLinkUp $dut $pt
        sleep 30
        ____CheckTestResult007
    }
    Netgear_disconnect_switch $dut
}

if {$bRet == 1} {
    set NtgrTestResult(LAG-007.tcl) "OK"
} else {
    set NtgrTestResult(LAG-007.tcl) "NG"
}

NtgrDumpLog  $NTGR_LOG_TERSE  "LAG-007.tcl" "******************** Complete Test case LAG-007.tcl ********************"
#*************************  End of Test case  **********************************