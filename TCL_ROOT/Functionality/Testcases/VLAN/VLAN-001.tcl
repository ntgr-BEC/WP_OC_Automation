################################################################################
#
#  File Name    : VLAN-001.tcl
#
#  Description  : This testcase used to test VLAN configuration command to create
#                 VLAN.
#
#  Revision     :
#        Date        Programmer        Description
#        2006/11/08  Scott             Newly created    
#
################################################################################

#*************************  Start of Test case  ********************************
NtgrDumpLog  $NTGR_LOG_TERSE  "VLAN-001.tcl" "******************** Starting Test case VLAN-001.tcl ********************"
set bRet 1
foreach switch_name $ntgrDutList001 {
    CALResetConfiguration $switch_name
    set bRet [expr $bRet & [CALCheckExpect $switch_name $ntgrCmdList001(1) $ntgrExpect001(1)]]
    CALRebootSwitch $switch_name; sleep 180;
    set bRet [expr $bRet & [CALCheckExpect $switch_name $ntgrCmdList001(2) $ntgrExpect001(2)]]
}

if {$bRet == 1} {
    set NtgrTestResult(VLAN-001.tcl) "OK"
} else {
    set NtgrTestResult(VLAN-001.tcl) "NG"
}
NtgrDumpLog  $NTGR_LOG_TERSE  "VLAN-001.tcl" "******************** Complete Test case VLAN-001.tcl ********************"
#*************************  End of Test case  **********************************