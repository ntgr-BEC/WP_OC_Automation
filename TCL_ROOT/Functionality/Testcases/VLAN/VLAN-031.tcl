################################################################################
#
#  File Name    : VLAN-031.tcl
#
#  Description  : This testcase used to change the IP address of layer3 VLAN.
#
#  Revision     :
#        Date        Programmer        Description
#        2006/11/14  Scott             Newly created    
#
################################################################################

#*************************  Start of Test case  ********************************
NtgrDumpLog  $NTGR_LOG_TERSE  "VLAN-031.tcl" "******************** Starting Test case VLAN-031.tcl ********************"
set bRet 1
foreach switch_name $ntgrDutList031 {
    CALResetConfiguration $switch_name
    set bRet [expr $bRet & [CALCheckExpect $switch_name $ntgrCmdList031(0) $ntgrExpect031(0)]]
    set bRet [expr $bRet & [CALCheckExpect $switch_name $ntgrCmdList031(1) $ntgrExpect031(1)]]
    CALRebootSwitch $switch_name; sleep 180;
    set bRet [expr $bRet & [CALCheckExpect $switch_name $ntgrCmdList031(2) $ntgrExpect031(2)]]
}
if {$bRet == 1} {
    set NtgrTestResult(VLAN-031.tcl) "OK"
} else {
    set NtgrTestResult(VLAN-031.tcl) "NG"
}
NtgrDumpLog  $NTGR_LOG_TERSE  "VLAN-031.tcl" "******************** Complete Test case VLAN-031.tcl ********************"
#*************************  End of Test case  **********************************