################################################################################
#
#  File Name    : PORT-027.tcl
#
#  Description  : This testcase used to test generating port relatives' script
#                 configuration file and apply it again.
#
#  Revision     :
#        Date        Programmer        Description
#        2007/04/02  Scott             Newly created    
#
################################################################################

#*************************  Start of Test case  ********************************
NtgrDumpLog  $NTGR_LOG_TERSE  "PORT-027.tcl" "******************** Starting Test case PORT-027.tcl ********************"

## Initial configuration for DUT
CALResetConfiguration $ntgrDUT
CALSetSwitchMngrIPAddr $ntgrDUT
CALDisableConsoleTimeout $ntgrDUT

## Execute configuration at the 'interface range ' mode
CALCheckExpect $ntgrDUT $ntgrRangeCmd ""

NtgrDumpLog  $NTGR_LOG_TERSE  "PORT-027.tcl" "Configuration complete, please generate the script file and apply it mannually."
NtgrDumpLog  $NTGR_LOG_TERSE  "PORT-027.tcl" "Press 'y' if the script file could be executed completely, otherwise press any key."

set key [gets stdin]
if {$key == "Y" || $key == "y"} {
    set NtgrTestResult(PORT-027.tcl) "OK"
} else {
    set NtgrTestResult(PORT-027.tcl) "NG"
}

NtgrDumpLog  $NTGR_LOG_TERSE  "PORT-027.tcl" "******************** Complete Test case PORT-027.tcl ********************"
#*************************  End of Test case  **********************************