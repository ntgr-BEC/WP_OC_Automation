################################################################################
#
#  File Name    : SNMP-015.tcl
#
#  Description  : This testcase used to test modification of receiver's
#                 SNMP version.
#
#  Revision     :
#        Date        Programmer        Description
#        2007/03/12  Scott             Newly created    
#
################################################################################

#*************************  Start of Test case  ********************************
NtgrDumpLog  $NTGR_LOG_TERSE  "SNMP-015.tcl" "******************** Starting Test case SNMP-015.tcl ********************"

CALResetConfiguration $ntgrDUT
CALSetSwitchMngrIPAddr $ntgrDUT
CALDisableConsoleTimeout $ntgrDUT

set bRet 1

NtgrDumpLog  $NTGR_LOG_TERSE  "SNMP-015.tcl" "Start a trap receiver/capture packets on PC, Press any key when you done."
gets stdin

CALAddTrapReceiver $ntgrDUT $ntgrComminity $ntgrReceiver
CALPortLinkDown $ntgrDUT $ntgrLinkUpdown

NtgrDumpLog  $NTGR_LOG_TERSE  "SNMP-015.tcl" "Press 'y' if trap messages send with $ntgrVersion2; otherwise, press other key."
set key [gets stdin]
if {$key == "y" || $key == "Y"} {
    set bRet [expr $bRet & 1]
} else {
    set bRet [expr $bRet & 0]
}


CALModifyTrapReceiverVer $ntgrDUT $ntgrComminity $ntgrReceiver $ntgrVersion1
CALPortLinkUp $ntgrDUT $ntgrLinkUpdown

NtgrDumpLog  $NTGR_LOG_TERSE  "SNMP-015.tcl" "Modify SNMP version to $ntgrVersion1, press 'y' if trap messages send with it; otherwise, press other key."

set key [gets stdin]
if {$key == "y" || $key == "Y"} {
    set bRet [expr $bRet & 1]
} else {
    set bRet [expr $bRet & 0]
}


CALModifyTrapReceiverVer $ntgrDUT $ntgrComminity $ntgrReceiver $ntgrVersion2
CALPortLinkDown $ntgrDUT $ntgrLinkUpdown

NtgrDumpLog  $NTGR_LOG_TERSE  "SNMP-015.tcl" "Modify SNMP version to $ntgrVersion2, press 'y' if trap messages send with it; otherwise, press other key."

set key [gets stdin]
if {$key == "y" || $key == "Y"} {
    set bRet [expr $bRet & 1]
} else {
    set bRet [expr $bRet & 0]
}


if {$bRet == 1} {
    set NtgrTestResult(SNMP-015.tcl) "OK"
} else {
    set NtgrTestResult(SNMP-015.tcl) "NG"
}

NtgrDumpLog  $NTGR_LOG_TERSE  "SNMP-015.tcl" "******************** Complete Test case SNMP-015.tcl ********************"
#*************************  End of Test case  **********************************