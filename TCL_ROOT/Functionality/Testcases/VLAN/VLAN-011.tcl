################################################################################
#
#  File Name    : VLAN-011.tcl
#
#  Description  : This testcase used to add port as VLAN member.
#
#  Revision     :
#        Date        Programmer        Description
#        2006/11/10  Scott             Newly created    
#
################################################################################

#*************************  Start of Test case  ********************************
NtgrDumpLog  $NTGR_LOG_TERSE  "VLAN-011.tcl" "******************** Starting Test case VLAN-011.tcl ********************"
set bRet 1
foreach switch_name $ntgrDutList011 {
    CALResetConfiguration $switch_name
    set bRet [expr $bRet & [CALCheckExpect $switch_name $ntgrCmdList011(1) $ntgrExpect011(1)]]
    set bRet [expr $bRet & [CALCheckExpect $switch_name $ntgrCmdList011(2) $ntgrExpect011(2)]]
    CALRebootSwitch $switch_name; sleep 180;
    set bRet [expr $bRet & [CALCheckExpect $switch_name $ntgrCmdList011(3) $ntgrExpect011(3)]]
}
if {$bRet == 1} {
    set NtgrTestResult(VLAN-011.tcl) "OK"
} else {
    set NtgrTestResult(VLAN-011.tcl) "NG"
}
NtgrDumpLog  $NTGR_LOG_TERSE  "VLAN-011.tcl" "******************** Complete Test case VLAN-011.tcl ********************"
#*************************  End of Test case  **********************************