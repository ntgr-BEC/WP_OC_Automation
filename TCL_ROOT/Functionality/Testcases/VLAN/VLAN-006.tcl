################################################################################
#
#  File Name    : VLAN-006.tcl
#
#  Description  : This testcase used to configure thousands of VLAN.
#
#  Revision     :
#        Date        Programmer        Description
#        2006/11/09  Scott             Newly created    
#
################################################################################

#*************************  Start of Test case  ********************************
NtgrDumpLog  $NTGR_LOG_TERSE  "VLAN-006.tcl" "******************** Starting Test case VLAN-006.tcl ********************"
set bRet 1
foreach switch_name $ntgrDutList006 {
    CALResetConfiguration $switch_name
    set bRet [expr $bRet & [CALCheckExpect $switch_name $ntgrCmdList006(1) $ntgrExpect006(1)]]
    CALRebootSwitch $switch_name; sleep 180;
    set bRet [expr $bRet & [CALCheckExpect $switch_name $ntgrCmdList006(2) $ntgrExpect006(2)]]
}
if {$bRet == 1} {
    set NtgrTestResult(VLAN-006.tcl) "OK"
} else {
    set NtgrTestResult(VLAN-006.tcl) "NG"
}
NtgrDumpLog  $NTGR_LOG_TERSE  "VLAN-006.tcl" "******************** Complete Test case VLAN-006.tcl ********************"
#*************************  End of Test case  **********************************