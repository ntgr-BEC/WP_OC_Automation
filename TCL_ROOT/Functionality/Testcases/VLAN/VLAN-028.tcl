################################################################################
#
#  File Name    : VLAN-028.tcl
#
#  Description  : This testcase used to delete secondary IP address of a layer3 VLAN.
#
#  Revision     :
#        Date        Programmer        Description
#        2006/11/13  Scott             Newly created    
#
################################################################################

#*************************  Start of Test case  ********************************
NtgrDumpLog  $NTGR_LOG_TERSE  "VLAN-028.tcl" "******************** Starting Test case VLAN-028.tcl ********************"
set bRet 1
foreach switch_name $ntgrDutList028 {
    CALResetConfiguration $switch_name
    set bRet [expr $bRet & [CALCheckExpect $switch_name $ntgrCmdList028(0) $ntgrExpect028(0) 0]]
    CALRebootSwitch $switch_name; sleep 180;
    set bRet [expr $bRet & [CALCheckExpect $switch_name $ntgrCmdList028(1) $ntgrExpect028(1) 0]]
}
if {$bRet == 1} {
    set NtgrTestResult(VLAN-028.tcl) "OK"
} else {
    set NtgrTestResult(VLAN-028.tcl) "NG"
}
NtgrDumpLog  $NTGR_LOG_TERSE  "VLAN-028.tcl" "******************** Complete Test case VLAN-028.tcl ********************"
#*************************  End of Test case  **********************************