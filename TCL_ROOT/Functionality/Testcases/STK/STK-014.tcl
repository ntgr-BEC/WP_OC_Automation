################################################################################
#
#  File Name    : STK-014.tcl
#
#  Description  : This testcase used to test whether configuration lost while
#                 moving management to another unit with saving configuration.
#
#  Revision     :
#        Date        Programmer        Description
#        2007/03/21  Scott             Newly created    
#
################################################################################

#*************************  Start of Test case  ********************************
NtgrDumpLog  $NTGR_LOG_TERSE  "STK-014.tcl" "******************** Starting Test case STK-014.tcl ********************"

CALResetConfiguration $ntgrDUT
CALDisableConsoleTimeout $ntgrDUT

CALCreateAllPortChannels $ntgrDUT
CALCreateVlan "VLAN200"

foreach {unit priority} [array get ntgrUnitPriority] {
    CALStkSetUnitPriority $ntgrDUT $unit $priority
}
set bRet 1
set bRet [expr $bRet & [CALCheckExpect $ntgrDUT $ntgrCommand $ntgrExpect]]

CALSaveConfig $ntgrDUT

CALStackMoveMgrmt $ntgrDUT $ntgrMoveMasterTo
NtgrDumpLog  $NTGR_LOG_TERSE  "STK-014.tcl" "Please move the console cable to the current master unit's console port, press any key when you done."
gets stdin
sleep 90

set bRet [expr $bRet & [CALCheckExpect $ntgrDUT $ntgrCommand $ntgrExpect]]


if {$bRet == 1} {
    set NtgrTestResult(STK-014.tcl) "OK"
} else {
    set NtgrTestResult(STK-014.tcl) "NG"
}

NtgrDumpLog  $NTGR_LOG_TERSE  "STK-014.tcl" "******************** Complete Test case STK-014.tcl ********************"
#*************************  End of Test case  **********************************