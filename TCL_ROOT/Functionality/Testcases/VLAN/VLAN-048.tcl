################################################################################
#
#  File Name    : VLAN-048.tcl
#
#  Description  : This testcase used to test forwarding layer3 traffic in hundreds of VLANs.
#
#  Revision     :
#        Date        Programmer        Description
#        2006/11/20  Scott             Newly created    
#
################################################################################

#*************************  Start of Test case  ********************************
NtgrDumpLog  $NTGR_LOG_TERSE  "VLAN-048.tcl" "******************** Starting Test case VLAN-048.tcl ********************"
CALResetConfiguration $ntgrDUT

Netgear_connect_switch $ntgrDUT
CALRoutingEnable $ntgrDUT
Netgear_disconnect_switch $ntgrDUT

# Create VLANs
foreach vlan_index $ntgrVlanIndexList048  {
	CALCreateVlan $vlan_index
}

# Loading traffic
foreach {chassis_id portlist} [array get ntgrTGPortList048] {
    UALConnectTester $chassis_id
    foreach pt $portlist {
        UALLoadPort $chassis_id $pt
    }
}

# Flowing traffic
foreach {chassis_id portlist} [array get ntgrTGPortList048] {
    foreach pt $portlist {
        UALStartTrafficPerPort $chassis_id $pt
    }
}
sleep 20

# Collect rate info several times
set txRate  [lindex [UALReportPortRate $ntgrTG $ntgrTxPort]  0]
set rxRate1 [lindex [UALReportPortRate $ntgrTG $ntgrRxPort1] 1]
if {[expr abs([expr $txRate - $rxRate1])/$txRate]<0.05} {
    set NtgrTestResult(VLAN-048.tcl) "OK"
} else {
    set NtgrTestResult(VLAN-048.tcl) "NG"
}

NtgrDumpLog  $NTGR_LOG_TERSE  "VLAN-048.tcl" "******************** Complete Test case VLAN-048.tcl ********************"
#*************************  End of Test case  **********************************