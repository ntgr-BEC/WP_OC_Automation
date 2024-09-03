################################################################################
#
#  File Name    : VLAN-014.tcl
#
#  Description  : This testcase used to add all ports as VLAN exclude member.
#
#  Revision     :
#        Date        Programmer        Description
#        2006/11/10  Scott             Newly created    
#
################################################################################

#*************************  Start of Test case  ********************************
NtgrDumpLog  $NTGR_LOG_TERSE  "VLAN-014.tcl" "******************** Starting Test case VLAN-014.tcl ********************"
set bRet 1
foreach switch_name $ntgrDutList014 {
    CALResetConfiguration $switch_name
    set bRet [expr $bRet & [CALCheckExpect $switch_name $ntgrCmdList014(1) $ntgrExpect014(1) 0]]
    set bRet [expr $bRet & [CALCheckExpect $switch_name $ntgrCmdList014(2) $ntgrExpect014(2) 0]]
    CALRebootSwitch $switch_name; sleep 180;
    set bRet [expr $bRet & [CALCheckExpect $switch_name $ntgrCmdList014(3) $ntgrExpect014(3) 0]]
}
if {$bRet == 1} {
    set NtgrTestResult(VLAN-014.tcl) "OK"
} else {
    set NtgrTestResult(VLAN-014.tcl) "NG"
}
NtgrDumpLog  $NTGR_LOG_TERSE  "VLAN-014.tcl" "******************** Complete Test case VLAN-014.tcl ********************"
#*************************  End of Test case  **********************************