################################################################################
#
#  File Name    : STK-009.tcl
#
#  Description  : This testcase used to test whether the configured unit 
#                 priority changed while renumber the unit ID.
#
#  Revision     :
#        Date        Programmer        Description
#        2007/03/21  Scott             Newly created    
#
################################################################################

#*************************  Start of Test case  ********************************
NtgrDumpLog  $NTGR_LOG_TERSE  "STK-009.tcl" "******************** Starting Test case STK-009.tcl ********************"

CALResetConfiguration $ntgrDUT
CALDisableConsoleTimeout $ntgrDUT

CALCreateAllPortChannels $ntgrDUT
CALCreateVlan "VLAN200"

foreach {unit priority} [array get ntgrUnitPriority] {
    CALStkSetUnitPriority $ntgrDUT $unit $priority
}
set bRet 1
set bRet [expr $bRet & [CALCheckExpect $ntgrDUT $ntgrCommand $ntgrExpect1]]

CALStkRenumberUnit $ntgrDUT $ntgrUnitRenumFrom $ntgrUnitRenumTo
sleep 90

set bRet [expr $bRet & [CALCheckExpect $ntgrDUT $ntgrCommand $ntgrExpect2]]


if {$bRet == 1} {
    set NtgrTestResult(STK-009.tcl) "OK"
} else {
    set NtgrTestResult(STK-009.tcl) "NG"
}

NtgrDumpLog  $NTGR_LOG_TERSE  "STK-009.tcl" "******************** Complete Test case STK-009.tcl ********************"
#*************************  End of Test case  **********************************