################################################################################
#
#  File Name    : LAG-025.tcl
#
#  Description  : This testcase used to test LAG stability:
#                 shutdown/no shutdown a LAG's member ports one by one
#                 with traffic flowing over the LAG.
#
#  Revision     :
#        Date        Programmer        Description
#        2006/11/28  Scott             Newly created    
#
################################################################################

#*************************  Start of Test case  ********************************
NtgrDumpLog  $NTGR_LOG_TERSE  "LAG-025.tcl" "******************** Starting Test case LAG-025.tcl ********************"
foreach dut $ntgrDUTList {
    CALResetConfiguration $dut
    CALAllPortsLinkDown $dut
}

# Create LAG
foreach lag_index $ntgrLAGIndexList025  {
	CALCreatePortChannel $lag_index
}

# Create VLANs
foreach vlan_index $ntgrVlanIndexList025  {
	CALCreateVlan $vlan_index
}

# Loading traffic
foreach {chassis_id portlist} [array get ntgrTGPortList025] {
    UALConnectTester $chassis_id
    foreach pt $portlist {
        UALLoadPort $chassis_id $pt
    }
}

# Flowing traffic
foreach {chassis_id portlist} [array get ntgrTGPortList025] {
    foreach pt $portlist {
        UALStartTrafficPerPort $chassis_id $pt
    }
}

sleep 30
set bRet 1
# Define a simple way to check test result
proc ____CheckTestResult025 {} {
    uplevel 1 {
        set txRate  [lindex [UALReportPortRate $ntgrTG $ntgrTxPort]  0]
        set rxRate1 [lindex [UALReportPortRate $ntgrTG $ntgrRxPort1] 1]
        set bRet [expr $bRet & [expr abs([expr $txRate - $rxRate1])/$txRate]<0.01]
    }
}
____CheckTestResult025

# Repeatly shutdown/no shutdown the LAG's member ports, with traffic flowing
for {set i 0} {$i<$ntgrRepeatTimes} {incr i} {
    foreach {dut portlist} [array get ntgrShutdownPortList] {
        Netgear_connect_switch $dut
        foreach pt $portlist {
            CALPortLinkDown $dut $pt
            sleep 30
            ____CheckTestResult025
            CALPortLinkUp $dut $pt
            sleep 30
            ____CheckTestResult025
        }
        Netgear_disconnect_switch $dut
    }
}

if {$bRet == 1} {
    set NtgrTestResult(LAG-025.tcl) "OK"
} else {
    set NtgrTestResult(LAG-025.tcl) "NG"
}

NtgrDumpLog  $NTGR_LOG_TERSE  "LAG-025.tcl" "******************** Complete Test case LAG-025.tcl ********************"
#*************************  End of Test case  **********************************