################################################################################
#
#  File Name    : STK-016.tcl
#
#  Description  : This testcase used to test using the stack port as
#                 a ethernet port.
#
#  Revision     :
#        Date        Programmer        Description
#        2007/03/21  Scott             Newly created
#
################################################################################

#*************************  Start of Test case  ********************************
NtgrDumpLog  $NTGR_LOG_TERSE  "STK-016.tcl" "******************** Starting Test case STK-016.tcl ********************"

foreach dut $ntgrDUTList {
    CALResetConfiguration $dut
    CALDisableConsoleTimeout $dut
    ## Configure stack ports' mode
    foreach {if_name mode} [array get ntgrStackPortMode_$dut] {
        CALStkConfigStackPortMode $dut $if_name $mode
    }
    CALRoutingEnable $dut
    CALRebootSwitch $dut
}
sleep 60

CALCreateVlan "VLAN200"
sleep 5

set bRet 1
set bRet [expr $bRet & [CALCheckExpect $ntgrDUT1 $ntgrCommand1 $ntgrExpect1]]
set bRet [expr $bRet & [CALCheckExpect $ntgrDUT2 $ntgrCommand2 $ntgrExpect2]]


if {$bRet == 1} {
    set NtgrTestResult(STK-016.tcl) "OK"
} else {
    set NtgrTestResult(STK-016.tcl) "NG"
}

NtgrDumpLog  $NTGR_LOG_TERSE  "STK-016.tcl" "******************** Complete Test case STK-016.tcl ********************"
#*************************  End of Test case  **********************************