################################################################################
#
#  File Name    : VLAN-023.tcl
#
#  Description  : This testcase used to enable a VLAN as routing VLAN.
#
#  Revision     :
#        Date        Programmer        Description
#        2006/11/13  Scott             Newly created    
#
################################################################################

#*************************  Start of Test case  ********************************
NtgrDumpLog  $NTGR_LOG_TERSE  "VLAN-023.tcl" "******************** Starting Test case VLAN-023.tcl ********************"
set bRet 1
foreach switch_name $ntgrDutList023 {
    CALResetConfiguration $switch_name
    set bRet [expr $bRet & [CALCheckExpect $switch_name $ntgrCmdList023(0) $ntgrExpect023(0)]]
    CALRebootSwitch $switch_name; sleep 180;
    set bRet [expr $bRet & [CALCheckExpect $switch_name $ntgrCmdList023(1) $ntgrExpect023(1)]]
}
if {$bRet == 1} {
    set NtgrTestResult(VLAN-023.tcl) "OK"
} else {
    set NtgrTestResult(VLAN-023.tcl) "NG"
}
NtgrDumpLog  $NTGR_LOG_TERSE  "VLAN-023.tcl" "******************** Complete Test case VLAN-023.tcl ********************"
#*************************  End of Test case  **********************************