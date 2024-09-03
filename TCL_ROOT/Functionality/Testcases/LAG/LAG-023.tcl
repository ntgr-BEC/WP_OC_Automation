################################################################################
#
#  File Name    : LAG-023.tcl
#
#  Description  : This testcase used to test adding/deleting a LAG repeat 100 times,
#                 with traffic flowing over.
#
#  Revision     :
#        Date        Programmer        Description
#        2006/11/28  Scott             Newly created    
#
################################################################################

#*************************  Start of Test case  ********************************
NtgrDumpLog  $NTGR_LOG_TERSE  "LAG-023.tcl" "******************** Starting Test case LAG-023.tcl ********************"
foreach dut $ntgrDUTList {
    CALResetConfiguration $dut
    CALSetSwitchPrompt $dut
    CALSetSwitchMngrIPAddr $dut
    CALDisableConsoleTimeout $dut
    CALAllPortsLinkDown $dut
}

# Create LAG
foreach lag_index $ntgrLAGIndexList023  {
	CALCreatePortChannel $lag_index
}

# Create VLANs
foreach vlan_index $ntgrVlanIndexList023  {
	CALCreateVlan $vlan_index
}

# Loading traffic
foreach {chassis_id portlist} [array get ntgrTGPortList023] {
    UALConnectTester $chassis_id
    foreach pt $portlist {
        UALLoadPort $chassis_id $pt
    }
}

# Flowing traffic
foreach {chassis_id portlist} [array get ntgrTGPortList023] {
    foreach pt $portlist {
        UALStartTrafficPerPort $chassis_id $pt
    }
}

set bRet 1
for {set t 0} {$t<$ntgrRepeatTimes} {incr t} {
    # Create LAG
    foreach lag_index $ntgrLAGIndexList023  {
    	CALCreatePortChannel $lag_index
    }
    # Create VLANs
    foreach vlan_index $ntgrVlanIndexList023  {
    	CALCreateVlan $vlan_index
    }

    sleep 60
    set txRate  [lindex [UALReportPortRate $ntgrTG $ntgrTxPort]  0]
    set rxRate1 [lindex [UALReportPortRate $ntgrTG $ntgrRxPort1] 1]
    set bRet [expr $bRet & [expr abs([expr $txRate - $rxRate1])/$txRate]<0.01]
    # Delete LAG
    foreach lag_index $ntgrLAGIndexList023  {
    	CALDeletePortChannel $lag_index
    }
}

if {$bRet == 1} {
    set NtgrTestResult(LAG-023.tcl) "OK"
} else {
    set NtgrTestResult(LAG-023.tcl) "NG"
}

NtgrDumpLog  $NTGR_LOG_TERSE  "LAG-023.tcl" "******************** Complete Test case LAG-023.tcl ********************"
#*************************  End of Test case  **********************************