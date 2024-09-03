################################################################################
#
#  File Name    : VLAN-026.tcl
#
#  Description  : This testcase used to delete IP address of layer3 VLAN.
#
#  Revision     :
#        Date        Programmer        Description
#        2006/11/13  Scott             Newly created    
#
################################################################################

#*************************  Start of Test case  ********************************
NtgrDumpLog  $NTGR_LOG_TERSE  "VLAN-026.tcl" "******************** Starting Test case VLAN-026.tcl ********************"
set bRet 1
foreach switch_name $ntgrDutList026 {
    CALResetConfiguration $switch_name
    set bRet [expr $bRet & [CALCheckExpect $switch_name $ntgrCmdList026(0) $ntgrExpect026(0) 0]]
    CALRebootSwitch $switch_name; sleep 180;
    set bRet [expr $bRet & [CALCheckExpect $switch_name $ntgrCmdList026(1) $ntgrExpect026(1) 0]]
}
if {$bRet == 1} {
    set NtgrTestResult(VLAN-026.tcl) "OK"
} else {
    set NtgrTestResult(VLAN-026.tcl) "NG"
}
NtgrDumpLog  $NTGR_LOG_TERSE  "VLAN-026.tcl" "******************** Complete Test case VLAN-026.tcl ********************"
#*************************  End of Test case  **********************************