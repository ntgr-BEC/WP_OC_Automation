################################################################################
#
#  File Name    : STK-013.tcl
#
#  Description  : This testcase used to test whether all configurations were
#                 reset while renumbering the master unit ID.
#
#  Revision     :
#        Date        Programmer        Description
#        2007/03/21  Scott             Newly created    
#
################################################################################

#*************************  Start of Test case  ********************************
NtgrDumpLog  $NTGR_LOG_TERSE  "STK-013.tcl" "******************** Starting Test case STK-013.tcl ********************"

CALResetConfiguration $ntgrDUT
CALDisableConsoleTimeout $ntgrDUT

CALCreateAllPortChannels $ntgrDUT
CALCreateVlan "VLAN200"

set bRet 1
set bRet [expr $bRet & [CALCheckExpect $ntgrDUT $ntgrCommand $ntgrExpect]]

set master [CALStkGetMasterID $ntgrDUT]
CALStkRenumberUnit $ntgrDUT $master $ntgrUnitRenumTo
sleep 120
set bRet [expr $bRet & [CALCheckExpect $ntgrDUT $ntgrCommand $ntgrExpect 0]]

## Renumber it back
CALStkRenumberUnit $ntgrDUT [CALStkGetMasterID $ntgrDUT] $master
sleep 120
set bRet [expr $bRet & [CALCheckExpect $ntgrDUT $ntgrCommand $ntgrExpect 0]]


if {$bRet == 1} {
    set NtgrTestResult(STK-013.tcl) "OK"
} else {
    set NtgrTestResult(STK-013.tcl) "NG"
}

NtgrDumpLog  $NTGR_LOG_TERSE  "STK-013.tcl" "******************** Complete Test case STK-013.tcl ********************"
#*************************  End of Test case  **********************************