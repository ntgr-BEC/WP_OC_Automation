################################################################################
#
#  File Name    : STK-006.tcl
#
#  Description  : This testcase used to test reloading one unit of a stack.
#
#  Revision     :
#        Date        Programmer        Description
#        2007/03/21  Scott             Newly created    
#
################################################################################

#*************************  Start of Test case  ********************************
NtgrDumpLog  $NTGR_LOG_TERSE  "STK-006.tcl" "******************** Starting Test case STK-006.tcl ********************"

CALResetConfiguration $ntgrDUT
CALDisableConsoleTimeout $ntgrDUT

CALCreateAllPortChannels $ntgrDUT
CALCreateVlan "VLAN200"

set bRet 1
set bRet [expr $bRet & [CALCheckExpect $ntgrDUT $ntgrCommand $ntgrExpect]]

CALRebootSwitch $ntgrDUT $ntgrReloadUnitID
sleep 30;# Wait for the guy to be reloaded
set bRet [expr $bRet & [CALCheckExpect $ntgrDUT $ntgrCommand $ntgrExpect]]
sleep 120;# Wait for the reloaded unit to come up

set bRet [expr $bRet & [CALCheckExpect $ntgrDUT $ntgrCommand $ntgrExpect]]

if {$bRet == 1} {
    set NtgrTestResult(STK-006.tcl) "OK"
} else {
    set NtgrTestResult(STK-006.tcl) "NG"
}

NtgrDumpLog  $NTGR_LOG_TERSE  "STK-006.tcl" "******************** Complete Test case STK-006.tcl ********************"
#*************************  End of Test case  **********************************