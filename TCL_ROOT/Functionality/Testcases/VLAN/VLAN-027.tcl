################################################################################
#
#  File Name    : VLAN-027.tcl
#
#  Description  : This testcase used to configure secondary IP address of a layer3 VLAN.
#
#  Revision     :
#        Date        Programmer        Description
#        2006/11/13  Scott             Newly created    
#
################################################################################

#*************************  Start of Test case  ********************************
NtgrDumpLog  $NTGR_LOG_TERSE  "VLAN-027.tcl" "******************** Starting Test case VLAN-027.tcl ********************"
set bRet 1
foreach switch_name $ntgrDutList027 {
    CALResetConfiguration $switch_name
    set bRet [expr $bRet & [CALCheckExpect $switch_name $ntgrCmdList027(0) $ntgrExpect027(0)]]
    CALRebootSwitch $switch_name; sleep 180;
    set bRet [expr $bRet & [CALCheckExpect $switch_name $ntgrCmdList027(1) $ntgrExpect027(1)]]
}
if {$bRet == 1} {
    set NtgrTestResult(VLAN-027.tcl) "OK"
} else {
    set NtgrTestResult(VLAN-027.tcl) "NG"
}
NtgrDumpLog  $NTGR_LOG_TERSE  "VLAN-027.tcl" "******************** Complete Test case VLAN-027.tcl ********************"
#*************************  End of Test case  **********************************