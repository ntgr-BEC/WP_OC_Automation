################################################################################
#
#  File Name    : LAG-031.tcl
#
#  Description  : This testcase used to test deleting a port from a LAG more than one time.
#
#  Revision     :
#        Date        Programmer        Description
#        2006/11/28  Scott             Newly created    
#
################################################################################

#*************************  Start of Test case  ********************************
NtgrDumpLog  $NTGR_LOG_TERSE  "LAG-031.tcl" "******************** Starting Test case LAG-031.tcl ********************"
set bRet 1
foreach dut $ntgrDutList031 {
    CALResetConfiguration $dut
    set bRet [expr $bRet & [CALCheckExpect $dut $ntgrCmdList031(1) $ntgrExpect031(1)]]
}

if {$bRet == 1} {
    set NtgrTestResult(LAG-031.tcl) "OK"
} else {
    set NtgrTestResult(LAG-031.tcl) "NG"
}

NtgrDumpLog  $NTGR_LOG_TERSE  "LAG-031.tcl" "******************** Complete Test case LAG-031.tcl ********************"
#*************************  End of Test case  **********************************