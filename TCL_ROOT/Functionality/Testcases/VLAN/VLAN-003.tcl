################################################################################
#
#  File Name    : VLAN-003.tcl
#
#  Description  : This testcase used to test VLAN configuration command to
#                 configure VLAN name.
#
#  Revision     :
#        Date        Programmer        Description
#        2006/11/09  Scott             Newly created    
#
################################################################################

#*************************  Start of Test case  ********************************
NtgrDumpLog  $NTGR_LOG_TERSE  "VLAN-003.tcl" "******************** Starting Test case VLAN-003.tcl ********************"
set bRet 1
foreach switch_name $ntgrDutList003 {
    CALResetConfiguration $switch_name
    set bRet [expr $bRet & [CALCheckExpect $switch_name $ntgrCmdList003(1) $ntgrExpect003(1)]]
    CALRebootSwitch $switch_name; sleep 180;
    set bRet [expr $bRet & [CALCheckExpect $switch_name $ntgrCmdList003(2) $ntgrExpect003(2)]]
}
if {$bRet == 1} {
    set NtgrTestResult(VLAN-003.tcl) "OK"
} else {
    set NtgrTestResult(VLAN-003.tcl) "NG"
}
NtgrDumpLog  $NTGR_LOG_TERSE  "VLAN-003.tcl" "******************** Complete Test case VLAN-003.tcl ********************"
#*************************  End of Test case  **********************************