################################################################################
#
#  File Name    : LAG-027.tcl
#
#  Description  : This testcase used to test creating LAG with the same name.
#
#  Revision     :
#        Date        Programmer        Description
#        2006/11/28  Scott             Newly created    
#
################################################################################

#*************************  Start of Test case  ********************************
NtgrDumpLog  $NTGR_LOG_TERSE  "LAG-027.tcl" "******************** Starting Test case LAG-027.tcl ********************"
set bRet 1
foreach dut $ntgrDutList027 {
    CALResetConfiguration $dut
    set bRet [expr $bRet & [CALCheckExpect $dut $ntgrCmdList027(1) $ntgrExpect027(1)]]
}

if {$bRet == 1} {
    set NtgrTestResult(LAG-027.tcl) "OK"
} else {
    set NtgrTestResult(LAG-027.tcl) "NG"
}

NtgrDumpLog  $NTGR_LOG_TERSE  "LAG-027.tcl" "******************** Complete Test case LAG-027.tcl ********************"
#*************************  End of Test case  **********************************