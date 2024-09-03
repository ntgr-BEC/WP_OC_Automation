################################################################################
#
#  File Name    : LAG-012.tcl
#
#  Description  : This testcase used to test rebooting switch for 100 times
#                 with traffic flowing over the LAG.
#
#  Revision     :
#        Date        Programmer        Description
#        2006/11/28  Scott             Newly created    
#
################################################################################

#*************************  Start of Test case  ********************************
NtgrDumpLog  $NTGR_LOG_TERSE  "LAG-012.tcl" "******************** Starting Test case LAG-012.tcl ********************"
foreach dut $ntgrDUTList {
    CALResetConfiguration $dut
    CALSetSwitchPrompt $dut
    CALSetSwitchMngrIPAddr $dut
    CALDisableConsoleTimeout $dut
    CALAllPortsLinkDown $dut
}

# Create LAG
foreach lag_index $ntgrLAGIndexList012  {
	CALCreatePortChannel $lag_index
}

# Create VLANs
foreach vlan_index $ntgrVlanIndexList012  {
	CALCreateVlan $vlan_index
}

# Loading traffic
foreach {chassis_id portlist} [array get ntgrTGPortList012] {
    UALConnectTester $chassis_id
    foreach pt $portlist {
        UALLoadPort $chassis_id $pt
    }
}

# Flowing traffic
foreach {chassis_id portlist} [array get ntgrTGPortList012] {
    foreach pt $portlist {
        UALStartTrafficPerPort $chassis_id $pt
    }
}

sleep 30
set bRet 1
set txRate  [lindex [UALReportPortRate $ntgrTG $ntgrTxPort]  0]
set rxRate1 [lindex [UALReportPortRate $ntgrTG $ntgrRxPort1] 1]
set bRet [expr $bRet & [expr abs([expr $txRate - $rxRate1])/$txRate]<0.01]

for {set i 1} {$i<=$ntgrRebootTimes} {incr i} {
    foreach dut $ntgrDUTList {
        CALRebootSwitch $dut
    }
    sleep $ntgrDUTRebootInterval
    
    set txRate  [lindex [UALReportPortRate $ntgrTG $ntgrTxPort]  0]
    set rxRate1 [lindex [UALReportPortRate $ntgrTG $ntgrRxPort1] 1]
    set bRet [expr $bRet & [expr abs([expr $txRate - $rxRate1])/$txRate]<0.01]
}

if {$bRet == 1} {
    set NtgrTestResult(LAG-012.tcl) "OK"
} else {
    set NtgrTestResult(LAG-012.tcl) "NG"
}

NtgrDumpLog  $NTGR_LOG_TERSE  "LAG-012.tcl" "******************** Complete Test case LAG-012.tcl ********************"
#*************************  End of Test case  **********************************