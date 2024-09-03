################################################################################
#
#  File Name    : STK-012.tcl
#
#  Description  : This testcase used to test whether the configuration such as 
#                 VLAN and LAG could be moved to new unit while renumbering the
#                 unit ID.
#
#  Revision     :
#        Date        Programmer        Description
#        2007/03/21  Scott             Newly created    
#
################################################################################

#*************************  Start of Test case  ********************************
NtgrDumpLog  $NTGR_LOG_TERSE  "STK-012.tcl" "******************** Starting Test case STK-012.tcl ********************"

CALResetConfiguration $ntgrDUT
CALDisableConsoleTimeout $ntgrDUT

CALCreateAllPortChannels $ntgrDUT
CALCreateVlan "VLAN200"

set bRet 1
set bRet [expr $bRet & [CALCheckExpect $ntgrDUT $ntgrCommand $ntgrExpect1]]

CALStkRenumberUnit $ntgrDUT $ntgrUnitRenumFrom $ntgrUnitRenumTo
sleep 90

set bRet [expr $bRet & [CALCheckExpect $ntgrDUT $ntgrCommand $ntgrExpect2]]


if {$bRet == 1} {
    set NtgrTestResult(STK-012.tcl) "OK"
} else {
    set NtgrTestResult(STK-012.tcl) "NG"
}

NtgrDumpLog  $NTGR_LOG_TERSE  "STK-012.tcl" "******************** Complete Test case STK-012.tcl ********************"
#*************************  End of Test case  **********************************