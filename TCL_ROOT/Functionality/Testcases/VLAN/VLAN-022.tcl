################################################################################
#
#  File Name    : VLAN-022.tcl
#
#  Description  : This testcase used to change all ports' PVID to its default value.
#
#  Revision     :
#        Date        Programmer        Description
#        2006/11/13  Scott             Newly created    
#
################################################################################

#*************************  Start of Test case  ********************************
NtgrDumpLog  $NTGR_LOG_TERSE  "VLAN-022.tcl" "******************** Starting Test case VLAN-022.tcl ********************"
set bRet 1
foreach switch_name $ntgrDutList022 {
    CALResetConfiguration $switch_name
    set bRet [expr $bRet & [CALCheckExpect $switch_name $ntgrCmdList022(0) $ntgrExpect022(0)]]
    set bRet [expr $bRet & [CALCheckExpect $switch_name $ntgrCmdList022(1) $ntgrExpect022(1) 0]]
    CALRebootSwitch $switch_name; sleep 180;
    set bRet [expr $bRet & [CALCheckExpect $switch_name $ntgrCmdList022(2) $ntgrExpect022(2) 0]]
}
if {$bRet == 1} {
    set NtgrTestResult(VLAN-022.tcl) "OK"
} else {
    set NtgrTestResult(VLAN-022.tcl) "NG"
}
NtgrDumpLog  $NTGR_LOG_TERSE  "VLAN-022.tcl" "******************** Complete Test case VLAN-022.tcl ********************"
#*************************  End of Test case  **********************************