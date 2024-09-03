################################################################################
#
#  File Name    : VLAN-030.tcl
#
#  Description  : This testcase used to delete all created secondary IP.
#
#  Revision     :
#        Date        Programmer        Description
#        2006/11/14  Scott             Newly created    
#
################################################################################

#*************************  Start of Test case  ********************************
NtgrDumpLog  $NTGR_LOG_TERSE  "VLAN-030.tcl" "******************** Starting Test case VLAN-030.tcl ********************"
set bRet 1
foreach switch_name $ntgrDutList030 {
    CALResetConfiguration $switch_name
    set bRet [expr $bRet & [CALCheckExpect $switch_name $ntgrCmdList030(0) $ntgrExpect030(0)]]
    set bRet [expr $bRet & [CALCheckExpect $switch_name $ntgrCmdList030(1) $ntgrExpect030(1)]]
    set bRet [expr $bRet & [CALCheckExpect $switch_name $ntgrCmdList030(2) $ntgrExpect030(2) 0]]
    CALRebootSwitch $switch_name; sleep 180;
    set bRet [expr $bRet & [CALCheckExpect $switch_name $ntgrCmdList030(2) $ntgrExpect030(2) 0]]
}
if {$bRet == 1} {
    set NtgrTestResult(VLAN-030.tcl) "OK"
} else {
    set NtgrTestResult(VLAN-030.tcl) "NG"
}
NtgrDumpLog  $NTGR_LOG_TERSE  "VLAN-030.tcl" "******************** Complete Test case VLAN-030.tcl ********************"
#*************************  End of Test case  **********************************