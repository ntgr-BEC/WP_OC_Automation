################################################################################
#
#  File Name    : LAG-026.tcl
#
#  Description  : This testcase used to test interoperability with STP: 
#                 Repeatly shutdown the LAGs one by one and let STP select the
#                 best LAG for forwarding.
#
#  Revision     :
#        Date        Programmer        Description
#        2006/11/28  Scott             Newly created    
#
################################################################################

#*************************  Start of Test case  ********************************
NtgrDumpLog  $NTGR_LOG_TERSE  "LAG-026.tcl" "******************** Starting Test case LAG-026.tcl ********************"
foreach dut $ntgrDUTList {
    CALResetConfiguration $dut
    CALSetSwitchPrompt $dut
    CALSetSwitchMngrIPAddr $dut
    CALDisableConsoleTimeout $dut
    CALAllPortsLinkDown $dut
}

# Create LAG
foreach lag_index $ntgrLAGIndexList026  {
	CALCreatePortChannel $lag_index
}

# Create VLANs
foreach vlan_index $ntgrVlanIndexList026  {
	CALCreateVlan $vlan_index
}

# Loading traffic
foreach {chassis_id portlist} [array get ntgrTGPortList026] {
    UALConnectTester $chassis_id
    foreach pt $portlist {
        UALLoadPort $chassis_id $pt
    }
}

# Flowing traffic
foreach {chassis_id portlist} [array get ntgrTGPortList026] {
    foreach pt $portlist {
        UALStartTrafficPerPort $chassis_id $pt
    }
}

sleep 30
set bRet 1
# Define a simple way to check test result
proc ____CheckTestResult026 {} {
    uplevel 1 {
        set txRate  [lindex [UALReportPortRate $ntgrTG $ntgrTxPort]  0]
        set rxRate1 [lindex [UALReportPortRate $ntgrTG $ntgrRxPort1] 1]
        set bRet [expr $bRet & [expr abs([expr $txRate - $rxRate1])/$txRate]<0.01]
    }
}

____CheckTestResult026

for {set i 0} {$i<$ntgrRepeatTimes} {incr i} {
    # Shutdown the LAGs one by one, and let STP select the best LAG for forwarding
    foreach lag $ntgrLAGIndexList026 {
        if {$lag == "POCH666"} break;
        foreach dut $ntgrDUTList {
            CALShutPortChannel $dut $lag;
            sleep 300
            ____CheckTestResult026
        }
    }

    # No shutdown the LAGs one by one, and let STP select the best LAG for forwarding
    set laglist [lsort -decreasing $ntgrLAGIndexList026 ]
    foreach lag $laglist {
        if {$lag == "POCH666"} continue;
        foreach dut $ntgrDUTList {
            CALNoShutPortChannel $dut $lag;
            sleep 300
            ____CheckTestResult026
        }
    }
}

if {$bRet == 1} {
    set NtgrTestResult(LAG-026.tcl) "OK"
} else {
    set NtgrTestResult(LAG-026.tcl) "NG"
}

NtgrDumpLog  $NTGR_LOG_TERSE  "LAG-026.tcl" "******************** Complete Test case LAG-026.tcl ********************"
#*************************  End of Test case  **********************************