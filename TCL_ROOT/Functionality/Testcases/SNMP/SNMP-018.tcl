################################################################################
#
#  File Name    : SNMP-018.tcl
#
#  Description  : This testcase used to test multi users login trap messages.
#
#  Revision     :
#        Date        Programmer        Description
#        2007/03/12  Scott             Newly created    
#
################################################################################

#*************************  Start of Test case  ********************************
NtgrDumpLog  $NTGR_LOG_TERSE  "SNMP-018.tcl" "******************** Starting Test case SNMP-018.tcl ********************"

CALResetConfiguration $ntgrDUT
CALSetSwitchMngrIPAddr $ntgrDUT
CALDisableConsoleTimeout $ntgrDUT

set bRet 1

NtgrDumpLog  $NTGR_LOG_TERSE  "SNMP-018.tcl" "Start a trap receiver/capture packets on PC, Press any key when you done."
gets stdin

CALAddTrapReceiver $ntgrDUT $ntgrComminity $ntgrReceiver


NtgrDumpLog  $NTGR_LOG_TERSE  "SNMP-018.tcl" "Pls access DUT with several users by Telnet from PC."
NtgrDumpLog  $NTGR_LOG_TERSE  "SNMP-018.tcl" "Press 'y' if multi users login trap messages send to PC; otherwise, press other key."
set key [gets stdin]
if {$key == "y" || $key == "Y"} {
    set bRet [expr $bRet & 1]
} else {
    set bRet [expr $bRet & 0]
}

CALDisableTrapMultiUser $ntgrDUT

NtgrDumpLog  $NTGR_LOG_TERSE  "SNMP-018.tcl" "Disable multi users login trap, pls access DUT with several users by Telnet from PC."
NtgrDumpLog  $NTGR_LOG_TERSE  "SNMP-018.tcl" "Press 'y' if no multi users login trap messages send to PC; otherwise, press other key."

set key [gets stdin]
if {$key == "y" || $key == "Y"} {
    set bRet [expr $bRet & 1]
} else {
    set bRet [expr $bRet & 0]
}

CALEnableTrapMultiUser $ntgrDUT
NtgrDumpLog  $NTGR_LOG_TERSE  "SNMP-018.tcl" "Enable multi users login trap again, pls access DUT with several users by Telnet from PC."
NtgrDumpLog  $NTGR_LOG_TERSE  "SNMP-018.tcl" "Press 'y' if multi users login trap messages send to PC; otherwise, press other key."

set key [gets stdin]
if {$key == "y" || $key == "Y"} {
    set bRet [expr $bRet & 1]
} else {
    set bRet [expr $bRet & 0]
}


if {$bRet == 1} {
    set NtgrTestResult(SNMP-018.tcl) "OK"
} else {
    set NtgrTestResult(SNMP-018.tcl) "NG"
}

NtgrDumpLog  $NTGR_LOG_TERSE  "SNMP-018.tcl" "******************** Complete Test case SNMP-018.tcl ********************"
#*************************  End of Test case  **********************************