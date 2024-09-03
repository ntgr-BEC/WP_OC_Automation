################################################################################
#
#  File Name    : VLAN-005.tcl
#
#  Description  : This testcase used to test VLAN configuration command to
#                 modify a VLAN name.
#
#  Revision     :
#        Date        Programmer        Description
#        2006/11/09  Scott             Newly created    
#
################################################################################

#*************************  Start of Test case  ********************************
NtgrDumpLog  $NTGR_LOG_TERSE  "VLAN-005.tcl" "******************** Starting Test case VLAN-005.tcl ********************"
set bRet 1
foreach switch_name $ntgrDutList005 {
    CALResetConfiguration $switch_name
    set bRet [expr $bRet & [CALCheckExpect $switch_name $ntgrCmdList005(1) $ntgrExpect005(1)]]
    CALRebootSwitch $switch_name; sleep 180;
    set bRet [expr $bRet & [CALCheckExpect $switch_name $ntgrCmdList005(2) $ntgrExpect005(2)]]
}
if {$bRet == 1} {
    set NtgrTestResult(VLAN-005.tcl) "OK"
} else {
    set NtgrTestResult(VLAN-005.tcl) "NG"
}
NtgrDumpLog  $NTGR_LOG_TERSE  "VLAN-005.tcl" "******************** Complete Test case VLAN-005.tcl ********************"
#*************************  End of Test case  **********************************