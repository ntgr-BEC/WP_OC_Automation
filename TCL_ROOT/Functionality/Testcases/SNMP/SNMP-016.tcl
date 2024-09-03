################################################################################
#
#  File Name    : SNMP-016.tcl
#
#  Description  : This testcase used to test login trap messages.
#
#  Revision     :
#        Date        Programmer        Description
#        2007/03/12  Scott             Newly created    
#
################################################################################

#*************************  Start of Test case  ********************************
NtgrDumpLog  $NTGR_LOG_TERSE  "SNMP-016.tcl" "******************** Starting Test case SNMP-016.tcl ********************"

CALResetConfiguration $ntgrDUT
CALSetSwitchMngrIPAddr $ntgrDUT
CALDisableConsoleTimeout $ntgrDUT

set bRet 1

NtgrDumpLog  $NTGR_LOG_TERSE  "SNMP-016.tcl" "Start a trap receiver/capture packets on PC, Press any key when you done."
gets stdin

CALAddTrapReceiver $ntgrDUT $ntgrComminity $ntgrReceiver

NtgrDumpLog  $NTGR_LOG_TERSE  "SNMP-016.tcl" "Try to login DUT with incorrect user/password from PC."
NtgrDumpLog  $NTGR_LOG_TERSE  "SNMP-016.tcl" "Press 'y' if authentication trap messages send to PC; otherwise, press other key."
set key [gets stdin]
if {$key == "y" || $key == "Y"} {
    set bRet [expr $bRet & 1]
} else {
    set bRet [expr $bRet & 0]
}


CALDisableAuthTrap $ntgrDUT

NtgrDumpLog  $NTGR_LOG_TERSE  "SNMP-016.tcl" "Try to login DUT with incorrect user/password from PC again."
NtgrDumpLog  $NTGR_LOG_TERSE  "SNMP-016.tcl" "Press 'y' if NO authentication trap messages send to PC; otherwise, press other key."

set key [gets stdin]
if {$key == "y" || $key == "Y"} {
    set bRet [expr $bRet & 1]
} else {
    set bRet [expr $bRet & 0]
}


CALEnableAuthTrap $ntgrDUT

NtgrDumpLog  $NTGR_LOG_TERSE  "SNMP-016.tcl" "Try to login DUT with incorrect user/password from PC again."
NtgrDumpLog  $NTGR_LOG_TERSE  "SNMP-016.tcl" "Press 'y' if authentication trap messages send to PC; otherwise, press other key."

set key [gets stdin]
if {$key == "y" || $key == "Y"} {
    set bRet [expr $bRet & 1]
} else {
    set bRet [expr $bRet & 0]
}


if {$bRet == 1} {
    set NtgrTestResult(SNMP-016.tcl) "OK"
} else {
    set NtgrTestResult(SNMP-016.tcl) "NG"
}

NtgrDumpLog  $NTGR_LOG_TERSE  "SNMP-016.tcl" "******************** Complete Test case SNMP-016.tcl ********************"
#*************************  End of Test case  **********************************