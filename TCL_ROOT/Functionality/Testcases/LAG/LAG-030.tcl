################################################################################
#
#  File Name    : LAG-030.tcl
#
#  Description  : This testcase used to test adding a port to a LAG more than one time.
#
#  Revision     :
#        Date        Programmer        Description
#        2006/11/28  Scott             Newly created    
#
################################################################################

#*************************  Start of Test case  ********************************
NtgrDumpLog  $NTGR_LOG_TERSE  "LAG-030.tcl" "******************** Starting Test case LAG-030.tcl ********************"
set bRet 1
foreach dut $ntgrDutList030 {
    CALResetConfiguration $dut
    set bRet [expr $bRet & [CALCheckExpect $dut $ntgrCmdList030(1) $ntgrExpect030(1)]]
}

if {$bRet == 1} {
    set NtgrTestResult(LAG-030.tcl) "OK"
} else {
    set NtgrTestResult(LAG-030.tcl) "NG"
}

NtgrDumpLog  $NTGR_LOG_TERSE  "LAG-030.tcl" "******************** Complete Test case LAG-030.tcl ********************"
#*************************  End of Test case  **********************************