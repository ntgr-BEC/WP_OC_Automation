################################################################################
#
#  File Name    : STK-010.tcl
#
#  Description  : This testcase used to test whether the configured unit 
#                 priority still exist while clearing current configuration.
#
#  Revision     :
#        Date        Programmer        Description
#        2007/03/21  Scott             Newly created
#
################################################################################

#*************************  Start of Test case  ********************************
NtgrDumpLog  $NTGR_LOG_TERSE  "STK-010.tcl" "******************** Starting Test case STK-010.tcl ********************"

CALResetConfiguration $ntgrDUT
CALDisableConsoleTimeout $ntgrDUT

foreach {unit priority} [array get ntgrUnitPriority] {
    CALStkSetUnitPriority $ntgrDUT $unit $priority
}
set bRet 1
set bRet [expr $bRet & [CALCheckExpect $ntgrDUT $ntgrCommand $ntgrExpect]]

CALResetConfiguration $ntgrDUT
sleep 30
set bRet [expr $bRet & [CALCheckExpect $ntgrDUT $ntgrCommand $ntgrExpect]]


CALRebootSwitch $ntgrDUT
sleep 120
set bRet [expr $bRet & [CALCheckExpect $ntgrDUT $ntgrCommand $ntgrExpect]]

if {$bRet == 1} {
    set NtgrTestResult(STK-010.tcl) "OK"
} else {
    set NtgrTestResult(STK-010.tcl) "NG"
}

NtgrDumpLog  $NTGR_LOG_TERSE  "STK-010.tcl" "******************** Complete Test case STK-010.tcl ********************"
#*************************  End of Test case  **********************************