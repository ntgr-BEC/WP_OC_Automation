################################################################################
#
#  File Name    : VLAN-044.tcl
#
#  Description  : This testcase used to test shutdown operation of layer3 VLAN.
#
#  Revision     :
#        Date        Programmer        Description
#        2006/11/20  Scott             Newly created    
#
################################################################################

#*************************  Start of Test case  ********************************
NtgrDumpLog  $NTGR_LOG_TERSE  "VLAN-044.tcl" "******************** Starting Test case VLAN-044.tcl ********************"
CALResetConfiguration $ntgrDut044
Netgear_connect_switch $ntgrDut044
CALRoutingEnable $ntgrDut044
Netgear_disconnect_switch $ntgrDut044

# Create VLANs
foreach vlan_index $ntgrVlanIndexList044  {
	CALCreateVlan $vlan_index
}

Netgear_connect_switch $ntgrDut044
CALPortLinkDown $ntgrDut044 $ntgrPort044_1
Netgear_disconnect_switch $ntgrDut044

set bRet 1
set bRet [expr $bRet & [CALCheckExpect $ntgrDut044 $ntgrCmdList044(0) $ntgrExpect044(0)]]
set bRet [expr $bRet & [CALCheckExpect $ntgrDut044 $ntgrCmdList044(1) $ntgrExpect044(1)]]

Netgear_connect_switch $ntgrDut044
CALPortLinkDown $ntgrDut044 $ntgrPort044_2
CALPortLinkDown $ntgrDut044 $ntgrPort044_3
Netgear_disconnect_switch $ntgrDut044

set bRet [expr $bRet & [CALCheckExpect $ntgrDut044 $ntgrCmdList044(0) $ntgrExpect044(0) 0]]
set bRet [expr $bRet & [CALCheckExpect $ntgrDut044 $ntgrCmdList044(1) $ntgrExpect044(1) 0]]

if {$bRet == 1} {
    set NtgrTestResult(VLAN-044.tcl) "OK"
} else {
    set NtgrTestResult(VLAN-044.tcl) "NG"
}
NtgrDumpLog  $NTGR_LOG_TERSE  "VLAN-044.tcl" "******************** Complete Test case VLAN-044.tcl ********************"
#*************************  End of Test case  **********************************