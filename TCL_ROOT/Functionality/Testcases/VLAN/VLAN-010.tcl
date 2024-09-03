################################################################################
#
#  File Name    : VLAN-010.tcl
#
#  Description  : This testcase used to delete thousands of VLAN names.
#
#  Revision     :
#        Date        Programmer        Description
#        2006/11/10  Scott             Newly created    
#
################################################################################

#*************************  Start of Test case  ********************************
NtgrDumpLog  $NTGR_LOG_TERSE  "VLAN-010.tcl" "******************** Starting Test case VLAN-010.tcl ********************"
set bRet 1
foreach switch_name $ntgrDutList010 {
    CALResetConfiguration $switch_name
    set bRet [expr $bRet & [CALCheckExpect $switch_name $ntgrCmdList010(1) $ntgrExpect010(1) 0]]
    CALRebootSwitch $switch_name; sleep 180;
    set bRet [expr $bRet & [CALCheckExpect $switch_name $ntgrCmdList010(2) $ntgrExpect010(2) 0]]
}
if {$bRet == 1} {
    set NtgrTestResult(VLAN-010.tcl) "OK"
} else {
    set NtgrTestResult(VLAN-010.tcl) "NG"
}
NtgrDumpLog  $NTGR_LOG_TERSE  "VLAN-010.tcl" "******************** Complete Test case VLAN-010.tcl ********************"
#*************************  End of Test case  **********************************