################################################################################
#
#  File Name    : VLAN-041.tcl
#
#  Description  : This testcase used to test layer3 VLAN routing and forwarding.
#
#  Revision     :
#        Date        Programmer        Description
#        2006/11/15  Scott             Newly created    
#
################################################################################

#*************************  Start of Test case  ********************************
NtgrDumpLog  $NTGR_LOG_TERSE  "VLAN-041.tcl" "******************** Starting Test case VLAN-041.tcl ********************"
CALResetConfiguration $ntgrDUT

Netgear_connect_switch $ntgrDUT
CALRoutingEnable $ntgrDUT
Netgear_disconnect_switch $ntgrDUT

# Create VLANs
foreach vlan_index $ntgrVlanIndexList041  {
	CALCreateVlan $vlan_index
}

# Loading traffic
foreach {chassis_id portlist} [array get ntgrTGPortList041] {
    UALConnectTester $chassis_id
    foreach pt $portlist {
        UALLoadPort $chassis_id $pt
    }
}

# Flowing traffic
foreach {chassis_id portlist} [array get ntgrTGPortList041] {
    foreach pt $portlist {
        UALStartTrafficPerPort $chassis_id $pt
    }
}

# Collect rate info several times
for {set i 1} {$i<=30} {incr i} {
    set txRate  [lindex [UALReportPortRate $ntgrTG $ntgrTxPort]  0]
    set rxRate1 [lindex [UALReportPortRate $ntgrTG $ntgrRxPort1] 1]
    if {[expr abs([expr $txRate - $rxRate1])/$txRate]<0.05} {
        set NtgrTestResult(VLAN-041.tcl) "OK"
        break
    } else {
        set NtgrTestResult(VLAN-041.tcl) "NG"
    }
    sleep 10
}

NtgrDumpLog  $NTGR_LOG_TERSE  "VLAN-041.tcl" "******************** Complete Test case VLAN-041.tcl ********************"
#*************************  End of Test case  **********************************