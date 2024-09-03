################################################################################
#
#  File Name    : VLAN-002.tcl
#
#  Description  : This testcase used to test VLAN configuration command to delete
#                 VLAN.
#
#  Revision     :
#        Date        Programmer        Description
#        2006/11/09  Scott             Newly created    
#
################################################################################

#*************************  Start of Test case  ********************************
NtgrDumpLog  $NTGR_LOG_TERSE  "VLAN-002.tcl" "******************** Starting Test case VLAN-002.tcl ********************"
set bRet 1
foreach switch_name $ntgrDutList002 {
    CALResetConfiguration $switch_name

    set index [lsort -dictionary [array names ntgrCmdList002 ] ]
    foreach idx $index {
        set bRet [expr $bRet & [CALCheckExpect $switch_name $ntgrCmdList002($idx) $ntgrExpect002($idx) 0]]
    }
}

if {$bRet == 1} {
    set NtgrTestResult(VLAN-002.tcl) "OK"
} else {
    set NtgrTestResult(VLAN-002.tcl) "NG"
}
NtgrDumpLog  $NTGR_LOG_TERSE  "VLAN-002.tcl" "******************** Complete Test case VLAN-002.tcl ********************"
#*************************  End of Test case  **********************************