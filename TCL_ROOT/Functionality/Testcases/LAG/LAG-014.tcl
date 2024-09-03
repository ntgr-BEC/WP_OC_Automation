################################################################################
#
#  File Name    : LAG-014.tcl
#
#  Description  : This testcase used to test LAG specification: how many LAGs 
#                 could be used in a switch(8).
#
#  Revision     :
#        Date        Programmer        Description
#        2006/11/28  Scott             Newly created    
#
################################################################################

#*************************  Start of Test case  ********************************
NtgrDumpLog  $NTGR_LOG_TERSE  "LAG-014.tcl" "******************** Starting Test case LAG-014.tcl ********************"
foreach dut $ntgrDUTList {
    CALResetConfiguration $dut
    CALSetSwitchPrompt $dut
    CALSetSwitchMngrIPAddr $dut
    CALDisableConsoleTimeout $dut
    CALAllPortsLinkDown $dut
}

# Create LAG
foreach lag_index $ntgrLAGIndexList014  {
	CALCreatePortChannel $lag_index
}

# Create VLANs
foreach vlan_index $ntgrVlanIndexList014  {
	CALCreateVlan $vlan_index
}

# Loading traffic
foreach {chassis_id portlist} [array get ntgrTGPortList014] {
    UALConnectTester $chassis_id
    foreach pt $portlist {
        UALLoadPort $chassis_id $pt
    }
}

# Flowing traffic
foreach {chassis_id portlist} [array get ntgrTGPortList014] {
    foreach pt $portlist {
        UALStartTrafficPerPort $chassis_id $pt
    }
}

sleep 30
set bRet 1
# Define a simple way to check test result
proc ____CheckTestResult014 {} {
    uplevel 1 {
        set txRate  [lindex [UALReportPortRate $ntgrTG $ntgrTxPort]  0]
        set rxRate1 [lindex [UALReportPortRate $ntgrTG $ntgrRxPort1] 1]
        set bRet [expr $bRet & [expr abs([expr $txRate - $rxRate1])/$txRate]<0.01]
    }
}

____CheckTestResult014

# Shutdown the working LAG, and let STP select another LAG
foreach dut $ntgrDUTList {
    for {set i 1} {$i<$ntgrAmoutLAG} {incr i} {
        CALShutPortChannel $dut "POCH$i$i$i"
        sleep 30
        ____CheckTestResult014
    }
    for {set i [expr $ntgrAmoutLAG-1]} {$i>0} {incr i -1} {
        CALNoShutPortChannel $dut "POCH$i$i$i"
        sleep 30
        ____CheckTestResult014
    }
}

foreach dut $ntgrDUTList {
    CALRebootSwitch $dut
}

sleep $ntgrDUTRebootInterval

# Shutdown the working LAG, and let STP select another LAG
foreach dut $ntgrDUTList {
    for {set i 1} {$i<$ntgrAmoutLAG} {incr i} {
        CALShutPortChannel $dut "POCH$i$i$i"
        sleep 30
        ____CheckTestResult014
    }
    for {set i [expr $ntgrAmoutLAG-1]} {$i>0} {incr i -1} {
        CALNoShutPortChannel $dut "POCH$i$i$i"
        sleep 30
        ____CheckTestResult014
    }
}

if {$bRet == 1} {
    set NtgrTestResult(LAG-014.tcl) "OK"
} else {
    set NtgrTestResult(LAG-014.tcl) "NG"
}

NtgrDumpLog  $NTGR_LOG_TERSE  "LAG-014.tcl" "******************** Complete Test case LAG-014.tcl ********************"
#*************************  End of Test case  **********************************