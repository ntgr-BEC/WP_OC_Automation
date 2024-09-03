################################################################################
#
#  File Name    : VLAN-043.tcl
#
#  Description  : This testcase used to test layer3 VLAN delete operation.
#
#  Revision     :
#        Date        Programmer        Description
#        2006/11/20  Scott             Newly created    
#
################################################################################

#*************************  Start of Test case  ********************************
NtgrDumpLog  $NTGR_LOG_TERSE  "VLAN-043.tcl" "******************** Starting Test case VLAN-043.tcl ********************"
foreach switch_name $ntgrDutList043 {
    CALResetConfiguration $switch_name
    Netgear_connect_switch $switch_name
    CALRoutingEnable $switch_name
    Netgear_disconnect_switch $switch_name
}

# Create VLANs
foreach vlan_index $ntgrVlanIndexList043  {
	CALCreateVlan $vlan_index
}

# Delete VLANs
foreach vlan_index $ntgrVlanIndexList043  {
	CALDeleteVlan $vlan_index
}

set bRet 1
foreach switch_name $ntgrDutList043 {
    set bRet [expr $bRet & [CALCheckExpect $switch_name $ntgrCmdList043(0) $ntgrExpect043(0) 0]]
    set bRet [expr $bRet & [CALCheckExpect $switch_name $ntgrCmdList043(1) $ntgrExpect043(1) 0]]
    set bRet [expr $bRet & [CALCheckExpect $switch_name $ntgrCmdList043(2) $ntgrExpect043(2) 0]]
    CALRebootSwitch $switch_name; sleep 180;
    set bRet [expr $bRet & [CALCheckExpect $switch_name $ntgrCmdList043(0) $ntgrExpect043(0) 0]]
    set bRet [expr $bRet & [CALCheckExpect $switch_name $ntgrCmdList043(1) $ntgrExpect043(1) 0]]
    set bRet [expr $bRet & [CALCheckExpect $switch_name $ntgrCmdList043(2) $ntgrExpect043(2) 0]]
}
if {$bRet == 1} {
    set NtgrTestResult(VLAN-043.tcl) "OK"
} else {
    set NtgrTestResult(VLAN-043.tcl) "NG"
}
NtgrDumpLog  $NTGR_LOG_TERSE  "VLAN-043.tcl" "******************** Complete Test case VLAN-043.tcl ********************"
#*************************  End of Test case  **********************************