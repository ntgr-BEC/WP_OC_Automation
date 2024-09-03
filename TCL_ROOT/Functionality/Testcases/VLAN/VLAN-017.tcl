################################################################################
#
#  File Name    : VLAN-017.tcl
#
#  Description  : This testcase used to add all ports to hundreds of VLAN.
#
#  Revision     :
#        Date        Programmer        Description
#        2006/11/10  Scott             Newly created    
#
################################################################################

#*************************  Start of Test case  ********************************
NtgrDumpLog  $NTGR_LOG_TERSE  "VLAN-017.tcl" "******************** Starting Test case VLAN-017.tcl ********************"
set bRet 1
foreach switch_name $ntgrDutList017 {
    CALResetConfiguration $switch_name
    set bRet [expr $bRet & [CALCheckExpect $switch_name $ntgrCmdList017(0) $ntgrExpect017(0)]]
    set bRet [expr $bRet & [CALCheckExpect $switch_name $ntgrCmdList017(1) $ntgrExpect017(1)]]

    for {set i 2} {$i<=$ntgrVlanDisplay} {incr i} {
        set bRet [expr $bRet & [CALCheckExpect $switch_name $ntgrCmdList017($i) $ntgrExpect017($i) 0]]
    }

    CALRebootSwitch $switch_name; sleep 180;
    sleep 200

    for {set i 2} {$i<=$ntgrVlanDisplay} {incr i} {
        set bRet [expr $bRet & [CALCheckExpect $switch_name $ntgrCmdList017($i) $ntgrExpect017($i) 0]]
    }
}
if {$bRet == 1} {
    set NtgrTestResult(VLAN-017.tcl) "OK"
} else {
    set NtgrTestResult(VLAN-017.tcl) "NG"
}
NtgrDumpLog  $NTGR_LOG_TERSE  "VLAN-017.tcl" "******************** Complete Test case VLAN-017.tcl ********************"
#*************************  End of Test case  **********************************