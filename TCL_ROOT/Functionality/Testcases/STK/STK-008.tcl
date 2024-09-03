################################################################################
#
#  File Name    : STK-008.tcl
#
#  Description  : This testcase used to test the selection of master between
#                 prioritized units while reloading the previous master.
#
#  Revision     :
#        Date        Programmer        Description
#        2007/03/21  Scott             Newly created    
#
################################################################################

#*************************  Start of Test case  ********************************
NtgrDumpLog  $NTGR_LOG_TERSE  "STK-008.tcl" "******************** Starting Test case STK-008.tcl ********************"

CALResetConfiguration $ntgrDUT
CALDisableConsoleTimeout $ntgrDUT

CALCreateAllPortChannels $ntgrDUT
CALCreateVlan "VLAN200"

set bRet 1
set bRet [expr $bRet & [CALCheckExpect $ntgrDUT $ntgrCommand $ntgrExpect]]

foreach {unit priority} [array get ntgrUnitPriority] {
    CALStkSetUnitPriority $ntgrDUT $unit $priority
}
CALSaveConfig $ntgrDUT
CALRebootSwitch $ntgrDUT [CALStkGetMasterID $ntgrDUT]

NtgrDumpLog  $NTGR_LOG_TERSE  "STK-008.tcl" "Please reconnect the console cable to the master's console port, press any key when you done"
gets stdin
sleep 120

## The master should be the highest priority unit
if {[CALStkGetMasterID $ntgrDUT] == $ntgrUnitHighest} {
    set bRet [expr $bRet & 1]
} else {
    set bRet [expr $bRet & 0]
}

if {$bRet == 1} {
    set NtgrTestResult(STK-008.tcl) "OK"
} else {
    set NtgrTestResult(STK-008.tcl) "NG"
}

NtgrDumpLog  $NTGR_LOG_TERSE  "STK-008.tcl" "******************** Complete Test case STK-008.tcl ********************"
#*************************  End of Test case  **********************************