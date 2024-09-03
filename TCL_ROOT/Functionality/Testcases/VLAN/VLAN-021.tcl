################################################################################
#
#  File Name    : VLAN-021.tcl
#
#  Description  : This testcase used to change all ports' PVID.
#
#  Revision     :
#        Date        Programmer        Description
#        2006/11/13  Scott             Newly created    
#
################################################################################

#*************************  Start of Test case  ********************************
NtgrDumpLog  $NTGR_LOG_TERSE  "VLAN-021.tcl" "******************** Starting Test case VLAN-021.tcl ********************"
set bRet 1
foreach switch_name $ntgrDutList021 {
    CALResetConfiguration $switch_name
    set bRet [expr $bRet & [CALCheckExpect $switch_name $ntgrCmdList021(0) $ntgrExpect021(0)]]
    set bRet [expr $bRet & [CALCheckExpect $switch_name $ntgrCmdList021(1) $ntgrExpect021(1) 0]]
    CALRebootSwitch $switch_name; sleep 180;
    set bRet [expr $bRet & [CALCheckExpect $switch_name $ntgrCmdList021(2) $ntgrExpect021(2) 0]]
}
if {$bRet == 1} {
    set NtgrTestResult(VLAN-021.tcl) "OK"
} else {
    set NtgrTestResult(VLAN-021.tcl) "NG"
}
NtgrDumpLog  $NTGR_LOG_TERSE  "VLAN-021.tcl" "******************** Complete Test case VLAN-021.tcl ********************"
#*************************  End of Test case  **********************************