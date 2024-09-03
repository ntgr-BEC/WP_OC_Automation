################################################################################
#
#  File Name    : VLAN-042.tcl
#
#  Description  : This testcase used to test VLAN delete operation.
#
#  Revision     :
#        Date        Programmer        Description
#        2006/11/20  Scott             Newly created    
#
################################################################################

#*************************  Start of Test case  ********************************
NtgrDumpLog  $NTGR_LOG_TERSE  "VLAN-042.tcl" "******************** Starting Test case VLAN-042.tcl ********************"
foreach switch_name $ntgrDutList042 {
    CALResetConfiguration $switch_name
}
# Create VLANs
foreach vlan_index $ntgrVlanIndexList042  {
	CALCreateVlan $vlan_index
}

# Delete VLANs
foreach vlan_index $ntgrVlanIndexList042  {
	CALDeleteVlan $vlan_index
}

set bRet 1
foreach switch_name $ntgrDutList042 {
    set bRet [expr $bRet & [CALCheckExpect $switch_name $ntgrCmdList042(0) $ntgrExpect042(0) 0]]
    set bRet [expr $bRet & [CALCheckExpect $switch_name $ntgrCmdList042(1) $ntgrExpect042(1) 1]]
    CALRebootSwitch $switch_name; sleep 180;
    set bRet [expr $bRet & [CALCheckExpect $switch_name $ntgrCmdList042(0) $ntgrExpect042(0) 0]]
    set bRet [expr $bRet & [CALCheckExpect $switch_name $ntgrCmdList042(1) $ntgrExpect042(1) 1]]
}
if {$bRet == 1} {
    set NtgrTestResult(VLAN-042.tcl) "OK"
} else {
    set NtgrTestResult(VLAN-042.tcl) "NG"
}
NtgrDumpLog  $NTGR_LOG_TERSE  "VLAN-042.tcl" "******************** Complete Test case VLAN-042.tcl ********************"
#*************************  End of Test case  **********************************