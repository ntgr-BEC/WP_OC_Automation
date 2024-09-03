################################################################################
#
#  File Name    : SNMP-020.tcl
#
#  Description  : This testcase used to test MAC violation trap messages.
#
#  Revision     :
#        Date        Programmer        Description
#        2007/03/12  Scott             Newly created    
#
################################################################################

#*************************  Start of Test case  ********************************
NtgrDumpLog  $NTGR_LOG_TERSE  "SNMP-020.tcl" "******************** Starting Test case SNMP-020.tcl ********************"

CALResetConfiguration $ntgrDUT
CALSetSwitchMngrIPAddr $ntgrDUT
CALDisableConsoleTimeout $ntgrDUT

set bRet 1
NtgrDumpLog  $NTGR_LOG_TERSE  "SNMP-020.tcl" "Start a trap receiver/capture packets on PC, Press any key when you done."
gets stdin

## Port security configuration
CALEnablePortSecurity $ntgrDUT
CALEnablePortSecurity $ntgrDUT $ntgrPortSecure
CALSetMaxDynamic $ntgrDUT $ntgrPortSecure 0
CALSetMaxStatic $ntgrDUT $ntgrPortSecure 0


CALAddTrapReceiver $ntgrDUT $ntgrComminity $ntgrReceiver
CALEnableTrapViolation $ntgrDUT $ntgrPortSecure

NtgrDumpLog  $NTGR_LOG_TERSE  "SNMP-020.tcl" "Press 'y' if MAC violation trap messages send to PC; otherwise, press other key."
set key [gets stdin]
if {$key == "y" || $key == "Y"} {
    set bRet [expr $bRet & 1]
} else {
    set bRet [expr $bRet & 0]
}

CALDisableTrapViolation $ntgrDUT $ntgrPortSecure
NtgrDumpLog  $NTGR_LOG_TERSE  "SNMP-020.tcl" "Disable MAC violation trap, press 'y' if no MAC violation trap messages send to PC; otherwise, press other key."

set key [gets stdin]
if {$key == "y" || $key == "Y"} {
    set bRet [expr $bRet & 1]
} else {
    set bRet [expr $bRet & 0]
}

CALEnableTrapViolation $ntgrDUT $ntgrPortSecure
NtgrDumpLog  $NTGR_LOG_TERSE  "SNMP-020.tcl" "Enable MAC violation trap again, press 'y' if MAC violation trap messages send to PC; otherwise, press other key."

set key [gets stdin]
if {$key == "y" || $key == "Y"} {
    set bRet [expr $bRet & 1]
} else {
    set bRet [expr $bRet & 0]
}


if {$bRet == 1} {
    set NtgrTestResult(SNMP-020.tcl) "OK"
} else {
    set NtgrTestResult(SNMP-020.tcl) "NG"
}

NtgrDumpLog  $NTGR_LOG_TERSE  "SNMP-020.tcl" "******************** Complete Test case SNMP-020.tcl ********************"
#*************************  End of Test case  **********************************