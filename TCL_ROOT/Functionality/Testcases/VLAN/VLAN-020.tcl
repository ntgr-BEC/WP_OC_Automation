################################################################################
#
#  File Name    : VLAN-020.tcl
#
#  Description  : This testcase used to change a port's PVID to its default value.
#
#  Revision     :
#        Date        Programmer        Description
#        2006/11/13  Scott             Newly created    
#
################################################################################

#*************************  Start of Test case  ********************************
NtgrDumpLog  $NTGR_LOG_TERSE  "VLAN-020.tcl" "******************** Starting Test case VLAN-020.tcl ********************"
set bRet 1
foreach switch_name $ntgrDutList020 {
    CALResetConfiguration $switch_name
    set bRet [expr $bRet & [CALCheckExpect $switch_name $ntgrCmdList020(0) $ntgrExpect020(0)]]
    set bRet [expr $bRet & [CALCheckExpect $switch_name $ntgrCmdList020(1) $ntgrExpect020(1)]]
    CALRebootSwitch $switch_name; sleep 180;
    set bRet [expr $bRet & [CALCheckExpect $switch_name $ntgrCmdList020(2) $ntgrExpect020(2)]]
}
if {$bRet == 1} {
    set NtgrTestResult(VLAN-020.tcl) "OK"
} else {
    set NtgrTestResult(VLAN-020.tcl) "NG"
}
NtgrDumpLog  $NTGR_LOG_TERSE  "VLAN-020.tcl" "******************** Complete Test case VLAN-020.tcl ********************"
#*************************  End of Test case  **********************************