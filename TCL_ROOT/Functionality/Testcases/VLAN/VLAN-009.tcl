################################################################################
#
#  File Name    : VLAN-009.tcl
#
#  Description  : This testcase used to modify thousands of VLAN names.
#
#  Revision     :
#        Date        Programmer        Description
#        2006/11/10  Scott             Newly created    
#
################################################################################

#*************************  Start of Test case  ********************************
NtgrDumpLog  $NTGR_LOG_TERSE  "VLAN-009.tcl" "******************** Starting Test case VLAN-009.tcl ********************"
set bRet 1
foreach switch_name $ntgrDutList009 {
    CALResetConfiguration $switch_name
    set bRet [expr $bRet & [CALCheckExpect $switch_name $ntgrCmdList009(1) $ntgrExpect009(1)]]
    CALRebootSwitch $switch_name; sleep 180;
    set bRet [expr $bRet & [CALCheckExpect $switch_name $ntgrCmdList009(2) $ntgrExpect009(2)]]
}
if {$bRet == 1} {
    set NtgrTestResult(VLAN-009.tcl) "OK"
} else {
    set NtgrTestResult(VLAN-009.tcl) "NG"
}
NtgrDumpLog  $NTGR_LOG_TERSE  "VLAN-009.tcl" "******************** Complete Test case VLAN-009.tcl ********************"
#*************************  End of Test case  **********************************