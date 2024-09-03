################################################################################
#
#  File Name    : VLAN-016.tcl
#
#  Description  : This testcase used to delete/exclude a port from hundred of VLANs.
#
#  Revision     :
#        Date        Programmer        Description
#        2006/11/10  Scott             Newly created    
#
################################################################################

#*************************  Start of Test case  ********************************
NtgrDumpLog  $NTGR_LOG_TERSE  "VLAN-016.tcl" "******************** Starting Test case VLAN-016.tcl ********************"
set bRet 1
foreach switch_name $ntgrDutList016 {
    CALResetConfiguration $switch_name
    set bRet [expr $bRet & [CALCheckExpect $switch_name $ntgrCmdList016(0) $ntgrExpect016(0)]]
    set bRet [expr $bRet & [CALCheckExpect $switch_name $ntgrCmdList016(1) $ntgrExpect016(1)]]

    for {set i 2} {$i<=$ntgrVlanDisplay} {incr i} {
        set bRet [expr $bRet & [CALCheckExpect $switch_name $ntgrCmdList016($i) $ntgrExpect016($i)]]
    }

    CALRebootSwitch $switch_name; sleep 180;
    sleep 200

    for {set i 2} {$i<=$ntgrVlanDisplay} {incr i} {
        set bRet [expr $bRet & [CALCheckExpect $switch_name $ntgrCmdList016($i) $ntgrExpect016($i)]]
    }
}
if {$bRet == 1} {
    set NtgrTestResult(VLAN-016.tcl) "OK"
} else {
    set NtgrTestResult(VLAN-016.tcl) "NG"
}
NtgrDumpLog  $NTGR_LOG_TERSE  "VLAN-016.tcl" "******************** Complete Test case VLAN-016.tcl ********************"
#*************************  End of Test case  **********************************