################################################################################
#
#  File Name    : PORT-028.tcl
#
#  Description  : This testcase used to test saving and reloading switch, port
#                 relative configurations should not be lost.
#
#  Revision     :
#        Date        Programmer        Description
#        2007/04/02  Scott             Newly created    
#
################################################################################

#*************************  Start of Test case  ********************************
NtgrDumpLog  $NTGR_LOG_TERSE  "PORT-028.tcl" "******************** Starting Test case PORT-028.tcl ********************"

## Initial configuration for DUT
CALResetConfiguration $ntgrDUT
CALSetSwitchMngrIPAddr $ntgrDUT
CALDisableConsoleTimeout $ntgrDUT

## Execute configuration at the 'interface range ' mode
CALCheckExpect $ntgrDUT $ntgrRangeCmd ""
CALRebootSwitch $ntgrDUT

NtgrDumpLog  $NTGR_LOG_TERSE  "PORT-028.tcl" "Configuration complete, mannually check whether port relative configurations were lost."
NtgrDumpLog  $NTGR_LOG_TERSE  "PORT-028.tcl" "Press 'y' if no configuration lost, otherwise press any key."

set key [gets stdin]
if {$key == "Y" || $key == "y"} {
    set NtgrTestResult(PORT-028.tcl) "OK"
} else {
    set NtgrTestResult(PORT-028.tcl) "NG"
}

NtgrDumpLog  $NTGR_LOG_TERSE  "PORT-028.tcl" "******************** Complete Test case PORT-028.tcl ********************"
#*************************  End of Test case  **********************************