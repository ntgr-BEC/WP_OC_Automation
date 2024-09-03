################################################################################
#
#  File Name    : VLAN-057.tcl
#
#  Description  : This testcase used to test thousands of MAC for each VLAN.
#
#  Revision     :
#        Date        Programmer        Description
#        2006/11/22  Scott             Newly created    
#
################################################################################

#*************************  Start of Test case  ********************************
NtgrDumpLog  $NTGR_LOG_TERSE  "VLAN-057.tcl" "******************** Starting Test case VLAN-057.tcl ********************"
CALResetConfiguration $ntgrDUT

# Create VLANs
foreach vlan_index $ntgrVlanIndexList057  {
	CALCreateVlan $vlan_index
}

# Loading traffic
foreach {chassis_id portlist} [array get ntgrTGPortList057] {
    UALConnectTester $chassis_id
    foreach pt $portlist {
        UALLoadPort $chassis_id $pt
    }
}

# Flowing traffic
foreach {chassis_id portlist} [array get ntgrTGPortList057] {
    foreach pt $portlist {
        UALStartTrafficPerPort $chassis_id $pt
    }
}

sleep 120;## Sleep enough time to ensure finishing MAC learning
# Collect rate info several times
set txRate  [lindex [UALReportPortRate $ntgrTG $ntgrTxPort]  0]
set rxRate1 [lindex [UALReportPortRate $ntgrTG $ntgrRxPort1] 1]
set rxRate2 [lindex [UALReportPortRate $ntgrTG $ntgrRxPort2] 1]
if {[expr abs([expr $txRate - $rxRate1])/$txRate]<0.01 && [expr $rxRate2/$txRate]<0.05} {
    set NtgrTestResult(VLAN-057.tcl) "OK"
} else {
    set NtgrTestResult(VLAN-057.tcl) "NG"
}

NtgrDumpLog  $NTGR_LOG_TERSE  "VLAN-057.tcl" "******************** Complete Test case VLAN-057.tcl ********************"
#*************************  End of Test case  **********************************