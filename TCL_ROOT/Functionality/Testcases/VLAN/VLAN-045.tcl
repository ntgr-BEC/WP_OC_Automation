################################################################################
#
#  File Name    : VLAN-045.tcl
#
#  Description  : This testcase used to test no shutdown operation of layer3 VLAN.
#
#  Revision     :
#        Date        Programmer        Description
#        2006/11/20  Scott             Newly created    
#
################################################################################

#*************************  Start of Test case  ********************************
NtgrDumpLog  $NTGR_LOG_TERSE  "VLAN-045.tcl" "******************** Starting Test case VLAN-045.tcl ********************"
CALResetConfiguration $ntgrDut045
Netgear_connect_switch $ntgrDut045
CALRoutingEnable $ntgrDut045
Netgear_disconnect_switch $ntgrDut045

# Create VLANs
foreach vlan_index $ntgrVlanIndexList045  {
	CALCreateVlan $vlan_index
}

Netgear_connect_switch $ntgrDut045
CALPortLinkDown $ntgrDut045 $ntgrPort045_1
CALPortLinkDown $ntgrDut045 $ntgrPort045_2
CALPortLinkDown $ntgrDut045 $ntgrPort045_3
Netgear_disconnect_switch $ntgrDut045

set bRet 1
set bRet [expr $bRet & [CALCheckExpect $ntgrDut045 $ntgrCmdList045(0) $ntgrExpect045(0) 0]]
set bRet [expr $bRet & [CALCheckExpect $ntgrDut045 $ntgrCmdList045(1) $ntgrExpect045(1) 0]]

Netgear_connect_switch $ntgrDut045
CALPortLinkUp $ntgrDut045 $ntgrPort045_1
Netgear_disconnect_switch $ntgrDut045

set bRet [expr $bRet & [CALCheckExpect $ntgrDut045 $ntgrCmdList045(0) $ntgrExpect045(0)]]
set bRet [expr $bRet & [CALCheckExpect $ntgrDut045 $ntgrCmdList045(1) $ntgrExpect045(1)]]

if {$bRet == 1} {
    set NtgrTestResult(VLAN-045.tcl) "OK"
} else {
    set NtgrTestResult(VLAN-045.tcl) "NG"
}
NtgrDumpLog  $NTGR_LOG_TERSE  "VLAN-045.tcl" "******************** Complete Test case VLAN-045.tcl ********************"
#*************************  End of Test case  **********************************