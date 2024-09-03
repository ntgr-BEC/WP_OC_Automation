################################################################################
#
#  File Name    : VLAN-025.tcl
#
#  Description  : This testcase used to configure IP address of layer3 VLAN.
#
#  Revision     :
#        Date        Programmer        Description
#        2006/11/13  Scott             Newly created    
#
################################################################################

#*************************  Start of Test case  ********************************
NtgrDumpLog  $NTGR_LOG_TERSE  "VLAN-025.tcl" "******************** Starting Test case VLAN-025.tcl ********************"
set bRet 1
foreach switch_name $ntgrDutList025 {
    CALResetConfiguration $switch_name
    set bRet [expr $bRet & [CALCheckExpect $switch_name $ntgrCmdList025(0) $ntgrExpect025(0)]]
    CALRebootSwitch $switch_name; sleep 180;
    set bRet [expr $bRet & [CALCheckExpect $switch_name $ntgrCmdList025(1) $ntgrExpect025(1)]]
}
if {$bRet == 1} {
    set NtgrTestResult(VLAN-025.tcl) "OK"
} else {
    set NtgrTestResult(VLAN-025.tcl) "NG"
}
NtgrDumpLog  $NTGR_LOG_TERSE  "VLAN-025.tcl" "******************** Complete Test case VLAN-025.tcl ********************"
#*************************  End of Test case  **********************************