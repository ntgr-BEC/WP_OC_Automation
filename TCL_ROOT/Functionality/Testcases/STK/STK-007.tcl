################################################################################
#
#  File Name    : STK-007.tcl
#
#  Description  : This testcase used to test whether configuration/unit ID
#                 changed while reloading the whole stack.
#
#  Revision     :
#        Date        Programmer        Description
#        2007/03/21  Scott             Newly created    
#
################################################################################

#*************************  Start of Test case  ********************************
NtgrDumpLog  $NTGR_LOG_TERSE  "STK-007.tcl" "******************** Starting Test case STK-007.tcl ********************"

CALResetConfiguration $ntgrDUT
CALDisableConsoleTimeout $ntgrDUT

CALCreateAllPortChannels $ntgrDUT
CALCreateVlan "VLAN200"

set bRet 1
set bRet [expr $bRet & [CALCheckExpect $ntgrDUT $ntgrCommand $ntgrExpect]]

CALRebootSwitch $ntgrDUT
sleep 180;# Wait for the stack finished reloading

set bRet [expr $bRet & [CALCheckExpect $ntgrDUT $ntgrCommand $ntgrExpect]]

if {$bRet == 1} {
    set NtgrTestResult(STK-007.tcl) "OK"
} else {
    set NtgrTestResult(STK-007.tcl) "NG"
}

NtgrDumpLog  $NTGR_LOG_TERSE  "STK-007.tcl" "******************** Complete Test case STK-007.tcl ********************"
#*************************  End of Test case  **********************************