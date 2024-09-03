################################################################################
#
#  File Name    : VLAN-024.tcl
#
#  Description  : This testcase used to disable a VLAN as routing VLAN.
#
#  Revision     :
#        Date        Programmer        Description
#        2006/11/13  Scott             Newly created    
#
################################################################################

#*************************  Start of Test case  ********************************
NtgrDumpLog  $NTGR_LOG_TERSE  "VLAN-024.tcl" "******************** Starting Test case VLAN-024.tcl ********************"
set bRet 1
foreach switch_name $ntgrDutList024 {
    CALResetConfiguration $switch_name
    set bRet [expr $bRet & [CALCheckExpect $switch_name $ntgrCmdList024(0) $ntgrExpect024(0) 0]]
    CALRebootSwitch $switch_name; sleep 180;
    set bRet [expr $bRet & [CALCheckExpect $switch_name $ntgrCmdList024(1) $ntgrExpect024(1) 0]]
}
if {$bRet == 1} {
    set NtgrTestResult(VLAN-024.tcl) "OK"
} else {
    set NtgrTestResult(VLAN-024.tcl) "NG"
}
NtgrDumpLog  $NTGR_LOG_TERSE  "VLAN-024.tcl" "******************** Complete Test case VLAN-024.tcl ********************"
#*************************  End of Test case  **********************************