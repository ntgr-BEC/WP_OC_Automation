################################################################################
#
#  File Name    : VLAN-018.tcl
#
#  Description  : This testcase used to delete/exclude all ports from hundred of VLANs.
#
#  Revision     :
#        Date        Programmer        Description
#        2006/11/10  Scott             Newly created    
#
################################################################################

#*************************  Start of Test case  ********************************
NtgrDumpLog  $NTGR_LOG_TERSE  "VLAN-018.tcl" "******************** Starting Test case VLAN-018.tcl ********************"
set bRet 1
foreach switch_name $ntgrDutList018 {
    CALResetConfiguration $switch_name
    set bRet [expr $bRet & [CALCheckExpect $switch_name $ntgrCmdList018(0) $ntgrExpect018(0)]]
    set bRet [expr $bRet & [CALCheckExpect $switch_name $ntgrCmdList018(1) $ntgrExpect018(1)]]

    for {set i 2} {$i<=$ntgrVlanDisplay} {incr i} {
        set bRet [expr $bRet & [CALCheckExpect $switch_name $ntgrCmdList018($i) $ntgrExpect018($i) 0]]
    }

    CALRebootSwitch $switch_name; sleep 180;
    sleep 200

    for {set i 2} {$i<=$ntgrVlanDisplay} {incr i} {
        set bRet [expr $bRet & [CALCheckExpect $switch_name $ntgrCmdList018($i) $ntgrExpect018($i) 0]]
    }
}
if {$bRet == 1} {
    set NtgrTestResult(VLAN-018.tcl) "OK"
} else {
    set NtgrTestResult(VLAN-018.tcl) "NG"
}
NtgrDumpLog  $NTGR_LOG_TERSE  "VLAN-018.tcl" "******************** Complete Test case VLAN-018.tcl ********************"
#*************************  End of Test case  **********************************