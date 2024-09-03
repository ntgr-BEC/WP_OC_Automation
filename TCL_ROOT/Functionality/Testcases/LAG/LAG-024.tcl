################################################################################
#
#  File Name    : LAG-024.tcl
#
#  Description  : This testcase used to test LAG stability:
#                 shutdown/no shutdown a LAG while traffic flowing over it.
#
#  Revision     :
#        Date        Programmer        Description
#        2006/11/28  Scott             Newly created    
#
################################################################################

#*************************  Start of Test case  ********************************
NtgrDumpLog  $NTGR_LOG_TERSE  "LAG-024.tcl" "******************** Starting Test case LAG-024.tcl ********************"
foreach dut $ntgrDUTList {
    CALResetConfiguration $dut
    CALSetSwitchPrompt $dut
    CALSetSwitchMngrIPAddr $dut
    CALDisableConsoleTimeout $dut
    CALAllPortsLinkDown $dut
}

# Create LAG
foreach lag_index $ntgrLAGIndexList024  {
	CALCreatePortChannel $lag_index
}

# Create VLANs
foreach vlan_index $ntgrVlanIndexList024  {
	CALCreateVlan $vlan_index
}

# Loading traffic
foreach {chassis_id portlist} [array get ntgrTGPortList024] {
    UALConnectTester $chassis_id
    foreach pt $portlist {
        UALLoadPort $chassis_id $pt
    }
}

# Flowing traffic
foreach {chassis_id portlist} [array get ntgrTGPortList024] {
    foreach pt $portlist {
        UALStartTrafficPerPort $chassis_id $pt
    }
}

sleep 30
set bRet 1
set txRate  [lindex [UALReportPortRate $ntgrTG $ntgrTxPort]  0]
set rxRate1 [lindex [UALReportPortRate $ntgrTG $ntgrRxPort1] 1]
set bRet [expr $bRet & [expr abs([expr $txRate - $rxRate1])/$txRate]<0.01]

# Repeatly shutdown/no shutdown the LAG
for {set t 0} {$t<$ntgrRepeatTimes} {incr t} {
    # Shutdown the LAG
    foreach dut $ntgrDUTList {
        CALShutPortChannel $dut "POCH111";
    }

    set txRate  [lindex [UALReportPortRate $ntgrTG $ntgrTxPort]  0]
    set rxRate1 [lindex [UALReportPortRate $ntgrTG $ntgrRxPort1] 1]
    set bRet [expr $bRet & [expr abs([expr $txRate - $rxRate1])/$txRate]>0.5]

    # No shutdown the LAG
    foreach dut $ntgrDUTList {
        CALNoShutPortChannel $dut "POCH111";
    }
    
    sleep 30

    set txRate  [lindex [UALReportPortRate $ntgrTG $ntgrTxPort]  0]
    set rxRate1 [lindex [UALReportPortRate $ntgrTG $ntgrRxPort1] 1]
    set bRet [expr $bRet & [expr abs([expr $txRate - $rxRate1])/$txRate]<0.01]
}

if {$bRet == 1} {
    set NtgrTestResult(LAG-024.tcl) "OK"
} else {
    set NtgrTestResult(LAG-024.tcl) "NG"
}

NtgrDumpLog  $NTGR_LOG_TERSE  "LAG-024.tcl" "******************** Complete Test case LAG-024.tcl ********************"
#*************************  End of Test case  **********************************