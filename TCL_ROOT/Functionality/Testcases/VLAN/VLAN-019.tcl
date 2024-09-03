################################################################################
#
#  File Name    : VLAN-019.tcl
#
#  Description  : This testcase used to change a port's PVID.
#
#  Revision     :
#        Date        Programmer        Description
#        2006/11/13  Scott             Newly created    
#
################################################################################

#*************************  Start of Test case  ********************************
NtgrDumpLog  $NTGR_LOG_TERSE  "VLAN-019.tcl" "******************** Starting Test case VLAN-019.tcl ********************"
set bRet 1
foreach switch_name $ntgrDutList019 {
    CALResetConfiguration $switch_name
    set bRet [expr $bRet & [CALCheckExpect $switch_name $ntgrCmdList019(0) $ntgrExpect019(0)]]
    set bRet [expr $bRet & [CALCheckExpect $switch_name $ntgrCmdList019(1) $ntgrExpect019(1)]]
    CALRebootSwitch $switch_name; sleep 180;
    set bRet [expr $bRet & [CALCheckExpect $switch_name $ntgrCmdList019(2) $ntgrExpect019(2)]]
}
if {$bRet == 1} {
    set NtgrTestResult(VLAN-019.tcl) "OK"
} else {
    set NtgrTestResult(VLAN-019.tcl) "NG"
}
NtgrDumpLog  $NTGR_LOG_TERSE  "VLAN-019.tcl" "******************** Complete Test case VLAN-019.tcl ********************"
#*************************  End of Test case  **********************************