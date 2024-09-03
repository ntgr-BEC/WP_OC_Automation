################################################################################
#
#  File Name    : VLAN-015.tcl
#
#  Description  : This testcase used to add one port to hundreds of VLAN.
#
#  Revision     :
#        Date        Programmer        Description
#        2006/11/10  Scott             Newly created    
#
################################################################################

#*************************  Start of Test case  ********************************
NtgrDumpLog  $NTGR_LOG_TERSE  "VLAN-015.tcl" "******************** Starting Test case VLAN-015.tcl ********************"
set bRet 1
foreach switch_name $ntgrDutList015 {
    CALResetConfiguration $switch_name
    set bRet [expr $bRet & [CALCheckExpect $switch_name $ntgrCmdList015(0) $ntgrExpect015(0)]]
    set bRet [expr $bRet & [CALCheckExpect $switch_name $ntgrCmdList015(1) $ntgrExpect015(1)]]

    for {set i 2} {$i<=$ntgrVlanDisplay} {incr i} {
        set bRet [expr $bRet & [CALCheckExpect $switch_name $ntgrCmdList015($i) $ntgrExpect015($i)]]
    }

    CALRebootSwitch $switch_name; sleep 180;
    sleep 200

    for {set i 2} {$i<=$ntgrVlanDisplay} {incr i} {
        set bRet [expr $bRet & [CALCheckExpect $switch_name $ntgrCmdList015($i) $ntgrExpect015($i)]]
    }
}
if {$bRet == 1} {
    set NtgrTestResult(VLAN-015.tcl) "OK"
} else {
    set NtgrTestResult(VLAN-015.tcl) "NG"
}
NtgrDumpLog  $NTGR_LOG_TERSE  "VLAN-015.tcl" "******************** Complete Test case VLAN-015.tcl ********************"
#*************************  End of Test case  **********************************