################################################################################
#
#  File Name    : LAG-028.tcl
#
#  Description  : This testcase used to test changing a LAG's name with exist LAG's name.
#
#  Revision     :
#        Date        Programmer        Description
#        2006/11/28  Scott             Newly created    
#
################################################################################

#*************************  Start of Test case  ********************************
NtgrDumpLog  $NTGR_LOG_TERSE  "LAG-028.tcl" "******************** Starting Test case LAG-028.tcl ********************"
set bRet 1
foreach dut $ntgrDutList028 {
    CALResetConfiguration $dut
    set bRet [expr $bRet & [CALCheckExpect $dut $ntgrCmdList028(1) $ntgrExpect028(1) 0]]
}

if {$bRet == 1} {
    set NtgrTestResult(LAG-028.tcl) "OK"
} else {
    set NtgrTestResult(LAG-028.tcl) "NG"
}

NtgrDumpLog  $NTGR_LOG_TERSE  "LAG-028.tcl" "******************** Complete Test case LAG-028.tcl ********************"
#*************************  End of Test case  **********************************