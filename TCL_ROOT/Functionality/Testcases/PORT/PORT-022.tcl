################################################################################
#
#  File Name    : PORT-022.tcl
#
#  Description  : This testcase used to test the 'interface range ' command by
#                 do some configurations at the range config mode.
#
#  Revision     :
#        Date        Programmer        Description
#        2007/04/02  Scott             Newly created    
#
################################################################################

#*************************  Start of Test case  ********************************
NtgrDumpLog  $NTGR_LOG_TERSE  "PORT-022.tcl" "******************** Starting Test case PORT-022.tcl ********************"

## Initial configuration for DUT
CALResetConfiguration $ntgrDUT
CALSetSwitchMngrIPAddr $ntgrDUT
CALDisableConsoleTimeout $ntgrDUT

## Execute configuration at the 'interface range ' mode
CALCheckExpect $ntgrDUT $ntgrRangeCmd ""

set bRet 1
set bRet [expr $bRet & [CALCheckExpect $ntgrDUT $ntgrCmd $ntgrExpect]]


if {$bRet == 1} {
    set NtgrTestResult(PORT-022.tcl) "OK"
} else {
    set NtgrTestResult(PORT-022.tcl) "NG"
}

NtgrDumpLog  $NTGR_LOG_TERSE  "PORT-022.tcl" "******************** Complete Test case PORT-022.tcl ********************"
#*************************  End of Test case  **********************************