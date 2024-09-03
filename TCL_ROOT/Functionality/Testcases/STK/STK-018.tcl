################################################################################
#
#  File Name    : STK-018.tcl
#
#  Description  : This testcase used to test the quality of service between
#                 stack units with qos mode enabled.
#
#  Revision     :
#        Date        Programmer        Description
#        2007/03/21  Scott             Newly created
#
################################################################################

#*************************  Start of Test case  ********************************
NtgrDumpLog  $NTGR_LOG_TERSE  "STK-018.tcl" "******************** Starting Test case STK-018.tcl ********************"

CALResetConfiguration $ntgrDUT
CALDisableConsoleTimeout $ntgrDUT
CALStkQosModeEnable $ntgrDUT

## Note that only FSM series have Qos mode, so the stack speed is 1000M.
## So please use 2 Gigabit port to make the traffic overflow
NtgrDumpLog  $NTGR_LOG_TERSE  "STK-018.tcl" "Pls create traffic between the 2 units, 1000M for low priority, 1000M with higher priority."
NtgrDumpLog  $NTGR_LOG_TERSE  "STK-018.tcl" "Press 'y' if only higher priority traffic go thru, otherwise press any key to continue."

set bRet 1
set key [gets stdin]
if {$key == "y" || $key == "Y"} {
    set bRet [expr $bRet & 1]
} else {
    set bRet [expr $bRet & 0]
}


if {$bRet == 1} {
    set NtgrTestResult(STK-018.tcl) "OK"
} else {
    set NtgrTestResult(STK-018.tcl) "NG"
}

NtgrDumpLog  $NTGR_LOG_TERSE  "STK-018.tcl" "******************** Complete Test case STK-018.tcl ********************"
#*************************  End of Test case  **********************************