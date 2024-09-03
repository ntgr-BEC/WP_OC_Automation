################################################################################
#
#  File Name    : VLAN-012.tcl
#
#  Description  : This testcase used to add port as VLAN exclude member.
#
#  Revision     :
#        Date        Programmer        Description
#        2006/11/10  Scott             Newly created    
#
################################################################################

#*************************  Start of Test case  ********************************
NtgrDumpLog  $NTGR_LOG_TERSE  "VLAN-012.tcl" "******************** Starting Test case VLAN-012.tcl ********************"
set bRet 1
foreach switch_name $ntgrDutList012 {
    CALResetConfiguration $switch_name
    set bRet [expr $bRet & [CALCheckExpect $switch_name $ntgrCmdList012(1) $ntgrExpect012(1)]]
    set bRet [expr $bRet & [CALCheckExpect $switch_name $ntgrCmdList012(2) $ntgrExpect012(2)]]
    CALRebootSwitch $switch_name; sleep 180;
    set bRet [expr $bRet & [CALCheckExpect $switch_name $ntgrCmdList012(3) $ntgrExpect012(3)]]
}
if {$bRet == 1} {
    set NtgrTestResult(VLAN-012.tcl) "OK"
} else {
    set NtgrTestResult(VLAN-012.tcl) "NG"
}
NtgrDumpLog  $NTGR_LOG_TERSE  "VLAN-012.tcl" "******************** Complete Test case VLAN-012.tcl ********************"
#*************************  End of Test case  **********************************