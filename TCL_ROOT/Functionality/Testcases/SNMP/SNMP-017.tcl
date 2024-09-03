################################################################################
#
#  File Name    : SNMP-017.tcl
#
#  Description  : This testcase used to test link status up/down trap messages.
#
#  Revision     :
#        Date        Programmer        Description
#        2007/03/12  Scott             Newly created    
#
################################################################################

#*************************  Start of Test case  ********************************
NtgrDumpLog  $NTGR_LOG_TERSE  "SNMP-017.tcl" "******************** Starting Test case SNMP-017.tcl ********************"

CALResetConfiguration $ntgrDUT
CALSetSwitchMngrIPAddr $ntgrDUT
CALDisableConsoleTimeout $ntgrDUT

set bRet 1

NtgrDumpLog  $NTGR_LOG_TERSE  "SNMP-017.tcl" "Start a trap receiver/capture packets on PC, Press any key when you done."
gets stdin

CALAddTrapReceiver $ntgrDUT $ntgrComminity $ntgrReceiver
CALPortLinkDown $ntgrDUT $ntgrLinkUpdown

NtgrDumpLog  $NTGR_LOG_TERSE  "SNMP-017.tcl" "Press 'y' if link up/down trap messages send to PC; otherwise, press other key."
set key [gets stdin]
if {$key == "y" || $key == "Y"} {
    set bRet [expr $bRet & 1]
} else {
    set bRet [expr $bRet & 0]
}

CALDisableTrapLink $ntgrDUT
CALPortLinkUp $ntgrDUT $ntgrLinkUpdown

NtgrDumpLog  $NTGR_LOG_TERSE  "SNMP-017.tcl" "Disable link up/down trap, press 'y' if no link up/down trap messages send to PC; otherwise, press other key."

set key [gets stdin]
if {$key == "y" || $key == "Y"} {
    set bRet [expr $bRet & 1]
} else {
    set bRet [expr $bRet & 0]
}

CALEnableTrapLink $ntgrDUT
CALPortLinkDown $ntgrDUT $ntgrLinkUpdown

NtgrDumpLog  $NTGR_LOG_TERSE  "SNMP-017.tcl" "Enable link up/down trap again, press 'y' if link up/down trap messages send to PC; otherwise, press other key."

set key [gets stdin]
if {$key == "y" || $key == "Y"} {
    set bRet [expr $bRet & 1]
} else {
    set bRet [expr $bRet & 0]
}

CALDisableTrapPort $ntgrDUT $ntgrLinkUpdown
CALPortLinkUp $ntgrDUT $ntgrLinkUpdown

NtgrDumpLog  $NTGR_LOG_TERSE  "SNMP-017.tcl" "Disable link up/down trap on $ntgrLinkUpdown, press 'y' if no link up/down trap messages send to PC; otherwise, press other key."

set key [gets stdin]
if {$key == "y" || $key == "Y"} {
    set bRet [expr $bRet & 1]
} else {
    set bRet [expr $bRet & 0]
}

CALEnableTrapPort $ntgrDUT $ntgrLinkUpdown
CALPortLinkDown $ntgrDUT $ntgrLinkUpdown

NtgrDumpLog  $NTGR_LOG_TERSE  "SNMP-017.tcl" "Enable link up/down trap on $ntgrLinkUpdown, press 'y' if link up/down trap messages send to PC; otherwise, press other key."

set key [gets stdin]
if {$key == "y" || $key == "Y"} {
    set bRet [expr $bRet & 1]
} else {
    set bRet [expr $bRet & 0]
}


if {$bRet == 1} {
    set NtgrTestResult(SNMP-017.tcl) "OK"
} else {
    set NtgrTestResult(SNMP-017.tcl) "NG"
}

NtgrDumpLog  $NTGR_LOG_TERSE  "SNMP-017.tcl" "******************** Complete Test case SNMP-017.tcl ********************"
#*************************  End of Test case  **********************************