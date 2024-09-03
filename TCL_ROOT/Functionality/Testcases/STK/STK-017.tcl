################################################################################
#
#  File Name    : STK-017.tcl
#
#  Description  : This testcase used to test whether the stack could be formed
#                 while configure stack port to stack mode.
#
#  Revision     :
#        Date        Programmer        Description
#        2007/03/21  Scott             Newly created
#
################################################################################

#*************************  Start of Test case  ********************************
NtgrDumpLog  $NTGR_LOG_TERSE  "STK-017.tcl" "******************** Starting Test case STK-017.tcl ********************"

foreach dut $ntgrDUTList {
    CALResetConfiguration $dut
    CALDisableConsoleTimeout $dut
    ## Configure stack ports' mode
    foreach {if_name mode} [array get ntgrStackPortMode_$dut] {
        CALStkConfigStackPortMode $dut $if_name $mode
    }
}
CALRebootSwitch $ntgrDUT1
CALRebootSwitch $ntgrDUT2
sleep 120

set bRet 1
set bRet [expr $bRet & [CALCheckExpect $ntgrDUT1 $ntgrCommand $ntgrExpect]]


if {$bRet == 1} {
    set NtgrTestResult(STK-017.tcl) "OK"
} else {
    set NtgrTestResult(STK-017.tcl) "NG"
}

NtgrDumpLog  $NTGR_LOG_TERSE  "STK-017.tcl" "******************** Complete Test case STK-017.tcl ********************"
#*************************  End of Test case  **********************************