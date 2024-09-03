################################################################################
#
#  File Name    : VLAN-007.tcl
#
#  Description  : This testcase used to delete thousands of VLAN.
#
#  Revision     :
#        Date        Programmer        Description
#        2006/11/09  Scott             Newly created    
#
################################################################################

#*************************  Start of Test case  ********************************
NtgrDumpLog  $NTGR_LOG_TERSE  "VLAN-007.tcl" "******************** Starting Test case VLAN-007.tcl ********************"
set bRet 1
foreach switch_name $ntgrDutList007 {
    CALResetConfiguration $switch_name
    set bRet [expr $bRet & [CALCheckExpect $switch_name $ntgrCmdList007(1) $ntgrExpect007(1) 0]]
    CALRebootSwitch $switch_name; sleep 180;
    set bRet [expr $bRet & [CALCheckExpect $switch_name $ntgrCmdList007(2) $ntgrExpect007(2) 0]]
}
if {$bRet == 1} {
    set NtgrTestResult(VLAN-007.tcl) "OK"
} else {
    set NtgrTestResult(VLAN-007.tcl) "NG"
}
NtgrDumpLog  $NTGR_LOG_TERSE  "VLAN-007.tcl" "******************** Complete Test case VLAN-007.tcl ********************"
#*************************  End of Test case  **********************************