################################################################################
#
#  File Name    : VLAN-008.tcl
#
#  Description  : This testcase used to name thousands of VLAN.
#
#  Revision     :
#        Date        Programmer        Description
#        2006/11/10  Scott             Newly created    
#
################################################################################

#*************************  Start of Test case  ********************************
NtgrDumpLog  $NTGR_LOG_TERSE  "VLAN-008.tcl" "******************** Starting Test case VLAN-008.tcl ********************"
set bRet 1
foreach switch_name $ntgrDutList008 {
    CALResetConfiguration $switch_name
    set bRet [expr $bRet & [CALCheckExpect $switch_name $ntgrCmdList008(1) $ntgrExpect008(1)]]
    CALRebootSwitch $switch_name; sleep 180;
    set bRet [expr $bRet & [CALCheckExpect $switch_name $ntgrCmdList008(2) $ntgrExpect008(2)]]
}
if {$bRet == 1} {
    set NtgrTestResult(VLAN-008.tcl) "OK"
} else {
    set NtgrTestResult(VLAN-008.tcl) "NG"
}
NtgrDumpLog  $NTGR_LOG_TERSE  "VLAN-008.tcl" "******************** Complete Test case VLAN-008.tcl ********************"
#*************************  End of Test case  **********************************