################################################################################
#
#  File Name    : SNMP-011.tcl
#
#  Description  : This testcase used to test create/delete SNMP
#                 trap receiver.
#
#  Revision     :
#        Date        Programmer        Description
#        2007/03/12  Scott             Newly created    
#
################################################################################

#*************************  Start of Test case  ********************************
NtgrDumpLog  $NTGR_LOG_TERSE  "SNMP-011.tcl" "******************** Starting Test case SNMP-011.tcl ********************"

CALResetConfiguration $ntgrDUT
CALSetSwitchMngrIPAddr $ntgrDUT
CALDisableConsoleTimeout $ntgrDUT

set bRet 1

NtgrDumpLog  $NTGR_LOG_TERSE  "SNMP-011.tcl" "Start a trap receiver/capture packets on PC, Press any key when you done."
gets stdin

CALAddTrapReceiver $ntgrDUT $ntgrComminity $ntgrReceiver
CALPortLinkDown $ntgrDUT $ntgrLinkUpdown

NtgrDumpLog  $NTGR_LOG_TERSE  "SNMP-011.tcl" "Press 'y' if trap messages received/trap packets captured on PC; otherwise, press other key."
set key [gets stdin]
if {$key == "y" || $key == "Y"} {
    set bRet [expr $bRet & 1]
} else {
    set bRet [expr $bRet & 0]
}


CALDelTrapReceiver $ntgrDUT $ntgrComminity $ntgrReceiver
CALPortLinkUp $ntgrDUT $ntgrLinkUpdown

NtgrDumpLog  $NTGR_LOG_TERSE  "SNMP-011.tcl" "Delete the trap receiver, press 'y' if no trap messages received/trap packets captured on PC; otherwise, press other key."

set key [gets stdin]
if {$key == "y" || $key == "Y"} {
    set bRet [expr $bRet & 1]
} else {
    set bRet [expr $bRet & 0]
}


CALAddTrapReceiver $ntgrDUT $ntgrComminity $ntgrReceiver
CALPortLinkDown $ntgrDUT $ntgrLinkUpdown
NtgrDumpLog  $NTGR_LOG_TERSE  "SNMP-011.tcl" "Re-create the trap receiver again, press 'y' if trap messages received/trap packets captured on PC; otherwise, press other key."

set key [gets stdin]
if {$key == "y" || $key == "Y"} {
    set bRet [expr $bRet & 1]
} else {
    set bRet [expr $bRet & 0]
}

if {$bRet == 1} {
    set NtgrTestResult(SNMP-011.tcl) "OK"
} else {
    set NtgrTestResult(SNMP-011.tcl) "NG"
}

NtgrDumpLog  $NTGR_LOG_TERSE  "SNMP-011.tcl" "******************** Complete Test case SNMP-011.tcl ********************"
#*************************  End of Test case  **********************************