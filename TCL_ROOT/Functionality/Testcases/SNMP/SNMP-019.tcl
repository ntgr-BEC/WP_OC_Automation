################################################################################
#
#  File Name    : SNMP-019.tcl
#
#  Description  : This testcase used to test spanning-tree changing trap messages.
#
#  Revision     :
#        Date        Programmer        Description
#        2007/03/12  Scott             Newly created    
#
################################################################################

#*************************  Start of Test case  ********************************
NtgrDumpLog  $NTGR_LOG_TERSE  "SNMP-019.tcl" "******************** Starting Test case SNMP-019.tcl ********************"

CALResetConfiguration $ntgrDUT
CALSetSwitchMngrIPAddr $ntgrDUT
CALDisableConsoleTimeout $ntgrDUT
CALStpConfigBridgePriority $ntgrDUT 4096

set bRet 1

NtgrDumpLog  $NTGR_LOG_TERSE  "SNMP-019.tcl" "Start a trap receiver/capture packets on PC, Press any key when you done."
gets stdin

CALAddTrapReceiver $ntgrDUT $ntgrComminity $ntgrReceiver
CALStpConfigBridgePriority $ntgrDUT 40960

NtgrDumpLog  $NTGR_LOG_TERSE  "SNMP-019.tcl" "Press 'y' if spanning-tree changing trap messages send to PC; otherwise, press other key."
set key [gets stdin]
if {$key == "y" || $key == "Y"} {
    set bRet [expr $bRet & 1]
} else {
    set bRet [expr $bRet & 0]
}

CALDisableTrapSTP $ntgrDUT
CALStpConfigBridgePriority $ntgrDUT 4096

NtgrDumpLog  $NTGR_LOG_TERSE  "SNMP-019.tcl" "Disable spanning-tree changing trap, press 'y' if no spanning-tree changing trap messages send to PC; otherwise, press other key."

set key [gets stdin]
if {$key == "y" || $key == "Y"} {
    set bRet [expr $bRet & 1]
} else {
    set bRet [expr $bRet & 0]
}

CALEnableTrapSTP $ntgrDUT
CALStpConfigBridgePriority $ntgrDUT 40960
NtgrDumpLog  $NTGR_LOG_TERSE  "SNMP-019.tcl" "Enable spanning-tree changing trap again, press 'y' if spanning-tree changing trap messages send to PC; otherwise, press other key."

set key [gets stdin]
if {$key == "y" || $key == "Y"} {
    set bRet [expr $bRet & 1]
} else {
    set bRet [expr $bRet & 0]
}


if {$bRet == 1} {
    set NtgrTestResult(SNMP-019.tcl) "OK"
} else {
    set NtgrTestResult(SNMP-019.tcl) "NG"
}

NtgrDumpLog  $NTGR_LOG_TERSE  "SNMP-019.tcl" "******************** Complete Test case SNMP-019.tcl ********************"
#*************************  End of Test case  **********************************