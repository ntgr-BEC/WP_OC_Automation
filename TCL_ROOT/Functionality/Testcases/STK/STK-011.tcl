################################################################################
#
#  File Name    : STK-011.tcl
#
#  Description  : This testcase used to test whether master changed while higher
#                 priority unit join the stack.
#
#  Revision     :
#        Date        Programmer        Description
#        2007/03/21  Scott             Newly created
#
################################################################################

#*************************  Start of Test case  ********************************
NtgrDumpLog  $NTGR_LOG_TERSE  "STK-011.tcl" "******************** Starting Test case STK-011.tcl ********************"

CALResetConfiguration $ntgrDUT
CALDisableConsoleTimeout $ntgrDUT

foreach {unit priority} [array get ntgrUnitPriority] {
    CALStkSetUnitPriority $ntgrDUT $unit $priority
}

set master [CALStkGetMasterID $ntgrDUT]
if { $master == $ntgrNextMaster} {
    NtgrDumpLog  $NTGR_LOG_TERSE  "STK-011.tcl" "Warning: the currect master is the same unit configured as next master."
    NtgrDumpLog  $NTGR_LOG_TERSE  "STK-011.tcl" "Press any key to continue this testing."
    gets stdin
}

CALRebootSwitch $ntgrDUT $master
sleep 120

NtgrDumpLog  $NTGR_LOG_TERSE  "STK-011.tcl" "Pls reconnect the console cable to the master unit's console port, press any key when you done."
gets stdin
set master [CALStkGetMasterID $ntgrDUT]

set bRet 1
if { $master == $ntgrNextMaster} {
    set bRet [expr $bRet & 1]
} else {
    set bRet [expr $bRet & 0]
}


CALRebootSwitch $ntgrDUT $master
sleep 120
NtgrDumpLog  $NTGR_LOG_TERSE  "STK-011.tcl" "Pls reconnect the console cable to the master unit's console port, press any key when you done."
gets stdin
set master [CALStkGetMasterID $ntgrDUT]

set bRet 1
if { $master == $ntgrMaster} {
    set bRet [expr $bRet & 1]
} else {
    set bRet [expr $bRet & 0]
}

if {$bRet == 1} {
    set NtgrTestResult(STK-011.tcl) "OK"
} else {
    set NtgrTestResult(STK-011.tcl) "NG"
}

NtgrDumpLog  $NTGR_LOG_TERSE  "STK-011.tcl" "******************** Complete Test case STK-011.tcl ********************"
#*************************  End of Test case  **********************************