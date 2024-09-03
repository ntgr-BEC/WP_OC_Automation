################################################################################
#
#  File Name    : VLAN-004.tcl
#
#  Description  : This testcase used to test VLAN configuration command to delete
#                 VLAN name.
#
#  Revision     :
#        Date        Programmer        Description
#        2006/11/09  Scott             Newly created    
#
################################################################################

#*************************  Start of Test case  ********************************
NtgrDumpLog  $NTGR_LOG_TERSE  "VLAN-004.tcl" "******************** Starting Test case VLAN-004.tcl ********************"
set bRet 1
foreach switch_name $ntgrDutList004 {
    CALResetConfiguration $switch_name

    set index [lsort -dictionary [array names ntgrCmdList004 ] ]
    foreach idx $index {
        set bRet [expr $bRet & [CALCheckExpect $switch_name $ntgrCmdList004($idx) $ntgrExpect004($idx) 0]]
    }
}

if {$bRet == 1} {
    set NtgrTestResult(VLAN-004.tcl) "OK"
} else {
    set NtgrTestResult(VLAN-004.tcl) "NG"
}
NtgrDumpLog  $NTGR_LOG_TERSE  "VLAN-004.tcl" "******************** Complete Test case VLAN-004.tcl ********************"
#*************************  End of Test case  **********************************