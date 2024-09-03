################################################################################
#
#  File Name    : LAG-029.tcl
#
#  Description  : This testcase used to test changing a LAG's name.
#
#  Revision     :
#        Date        Programmer        Description
#        2006/11/28  Scott             Newly created    
#
################################################################################

#*************************  Start of Test case  ********************************
NtgrDumpLog  $NTGR_LOG_TERSE  "LAG-029.tcl" "******************** Starting Test case LAG-029.tcl ********************"
set bRet 1
foreach dut $ntgrDutList029 {
    CALResetConfiguration $dut
    set bRet [expr $bRet & [CALCheckExpect $dut $ntgrCmdList029(1) $ntgrExpect029(1)]]
}

if {$bRet == 1} {
    set NtgrTestResult(LAG-029.tcl) "OK"
} else {
    set NtgrTestResult(LAG-029.tcl) "NG"
}

NtgrDumpLog  $NTGR_LOG_TERSE  "LAG-029.tcl" "******************** Complete Test case LAG-029.tcl ********************"
#*************************  End of Test case  **********************************