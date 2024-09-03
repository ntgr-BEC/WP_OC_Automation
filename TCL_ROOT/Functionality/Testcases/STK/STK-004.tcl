################################################################################
#
#  File Name    : STK-004.tcl
#
#  Description  : This testcase used to test adding new members to a stack with
#                 its supported switch type.
#
#  Revision     :
#        Date        Programmer        Description
#        2007/03/21  Scott             Newly created    
#
################################################################################

#*************************  Start of Test case  ********************************
NtgrDumpLog  $NTGR_LOG_TERSE  "STK-004.tcl" "******************** Starting Test case STK-004.tcl ********************"

foreach dut $ntgrDUTList {
    CALResetConfiguration $dut
    CALDisableConsoleTimeout $dut
}

## Add members to each stack with supported switch type
foreach dut $ntgrDUTList {
    foreach {unitID type} [array get ntgrDUT_$dut] {
        CALStkAddMember $dut $unitID $type
    }
}

set bRet 1
foreach dut $ntgrDUTList {
    set bRet [expr $bRet & [CALCheckExpect $dut $ntgrCommand $ntgrExpect($dut)]]
}

if {$bRet == 1} {
    set NtgrTestResult(STK-004.tcl) "OK"
} else {
    set NtgrTestResult(STK-004.tcl) "NG"
}

NtgrDumpLog  $NTGR_LOG_TERSE  "STK-004.tcl" "******************** Complete Test case STK-004.tcl ********************"
#*************************  End of Test case  **********************************