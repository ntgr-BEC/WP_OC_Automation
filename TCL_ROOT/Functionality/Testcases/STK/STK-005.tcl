################################################################################
#
#  File Name    : STK-005.tcl
#
#  Description  : This testcase used to test deleting added members from a stack.
#
#  Revision     :
#        Date        Programmer        Description
#        2007/03/21  Scott             Newly created    
#
################################################################################

#*************************  Start of Test case  ********************************
NtgrDumpLog  $NTGR_LOG_TERSE  "STK-005.tcl" "******************** Starting Test case STK-005.tcl ********************"

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


## Deleting added members from each stack
foreach dut $ntgrDUTList {
    foreach {unitID type} [array get ntgrDUT_$dut] {
        CALStkDelMember $dut $unitID
    }
}

foreach dut $ntgrDUTList {
    set bRet [expr $bRet & [CALCheckExpect $dut $ntgrCommand $ntgrExpect($dut) 0]]
}


if {$bRet == 1} {
    set NtgrTestResult(STK-005.tcl) "OK"
} else {
    set NtgrTestResult(STK-005.tcl) "NG"
}

NtgrDumpLog  $NTGR_LOG_TERSE  "STK-005.tcl" "******************** Complete Test case STK-005.tcl ********************"
#*************************  End of Test case  **********************************