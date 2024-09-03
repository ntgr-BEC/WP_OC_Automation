################################################################################
#
#  File Name    : VLAN-029.tcl
#
#  Description  : This testcase used to configure secondary IP as many as possible.
#
#  Revision     :
#        Date        Programmer        Description
#        2006/11/14  Scott             Newly created    
#
################################################################################

#*************************  Start of Test case  ********************************
NtgrDumpLog  $NTGR_LOG_TERSE  "VLAN-029.tcl" "******************** Starting Test case VLAN-029.tcl ********************"
set bRet 1
foreach switch_name $ntgrDutList029 {
    CALResetConfiguration $switch_name
    set bRet [expr $bRet & [CALCheckExpect $switch_name $ntgrCmdList029(0) $ntgrExpect029(0)]]
    set bRet [expr $bRet & [CALCheckExpect $switch_name $ntgrCmdList029(1) $ntgrExpect029(1)]]
    CALRebootSwitch $switch_name; sleep 180;
    set bRet [expr $bRet & [CALCheckExpect $switch_name $ntgrCmdList029(1) $ntgrExpect029(1)]]
}
if {$bRet == 1} {
    set NtgrTestResult(VLAN-029.tcl) "OK"
} else {
    set NtgrTestResult(VLAN-029.tcl) "NG"
}
NtgrDumpLog  $NTGR_LOG_TERSE  "VLAN-029.tcl" "******************** Complete Test case VLAN-029.tcl ********************"
#*************************  End of Test case  **********************************