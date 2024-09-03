################################################################################
#
#  File Name    : VLAN-013.tcl
#
#  Description  : This testcase used to add all ports as VLAN member.
#
#  Revision     :
#        Date        Programmer        Description
#        2006/11/10  Scott             Newly created    
#
################################################################################

#*************************  Start of Test case  ********************************
NtgrDumpLog  $NTGR_LOG_TERSE  "VLAN-013.tcl" "******************** Starting Test case VLAN-013.tcl ********************"
set bRet 1
foreach switch_name $ntgrDutList013 {
    CALResetConfiguration $switch_name
    set bRet [expr $bRet & [CALCheckExpect $switch_name $ntgrCmdList013(1) $ntgrExpect013(1) 0]]
    set bRet [expr $bRet & [CALCheckExpect $switch_name $ntgrCmdList013(2) $ntgrExpect013(2) 0]]
    CALRebootSwitch $switch_name; sleep 180;
    set bRet [expr $bRet & [CALCheckExpect $switch_name $ntgrCmdList013(3) $ntgrExpect013(3) 0]]
}
if {$bRet == 1} {
    set NtgrTestResult(VLAN-013.tcl) "OK"
} else {
    set NtgrTestResult(VLAN-013.tcl) "NG"
}
NtgrDumpLog  $NTGR_LOG_TERSE  "VLAN-013.tcl" "******************** Complete Test case VLAN-013.tcl ********************"
#*************************  End of Test case  **********************************