################################################################################
#
#  File Name    : STK-015.tcl
#
#  Description  : This testcase used to test whether configuration lost while
#                 moving management to another unit without saving configuration.
#
#  Revision     :
#        Date        Programmer        Description
#        2007/03/21  Scott             Newly created    
#
################################################################################

#*************************  Start of Test case  ********************************
NtgrDumpLog  $NTGR_LOG_TERSE  "STK-015.tcl" "******************** Starting Test case STK-015.tcl ********************"

CALResetConfiguration $ntgrDUT
CALDisableConsoleTimeout $ntgrDUT

CALCreateAllPortChannels $ntgrDUT
CALSaveConfig $ntgrDUT

CALCreateVlan "VLAN200"

foreach {unit priority} [array get ntgrUnitPriority] {
    CALStkSetUnitPriority $ntgrDUT $unit $priority
}
set bRet 1
set bRet [expr $bRet & [CALCheckExpect $ntgrDUT $ntgrCommand $ntgrExpect1]]
set bRet [expr $bRet & [CALCheckExpect $ntgrDUT $ntgrCommand $ntgrExpect2]]

CALStackMoveMgrmt $ntgrDUT $ntgrMoveMasterTo
NtgrDumpLog  $NTGR_LOG_TERSE  "STK-015.tcl" "Please move the console cable to the current master unit's console port, press any key when you done."
gets stdin
sleep 90

set bRet [expr $bRet & [CALCheckExpect $ntgrDUT $ntgrCommand $ntgrExpect1]]
set bRet [expr $bRet & [CALCheckExpect $ntgrDUT $ntgrCommand $ntgrExpect2 0]]


if {$bRet == 1} {
    set NtgrTestResult(STK-015.tcl) "OK"
} else {
    set NtgrTestResult(STK-015.tcl) "NG"
}

NtgrDumpLog  $NTGR_LOG_TERSE  "STK-015.tcl" "******************** Complete Test case STK-015.tcl ********************"
#*************************  End of Test case  **********************************