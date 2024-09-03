################################################################################
#
#  File Name    : SNMP-014.tcl
#
#  Description  : This testcase used to test modification of SNMP receiver's IP
#                 address.
#
#  Revision     :
#        Date        Programmer        Description
#        2007/03/12  Scott             Newly created    
#
################################################################################

#*************************  Start of Test case  ********************************
NtgrDumpLog  $NTGR_LOG_TERSE  "SNMP-014.tcl" "******************** Starting Test case SNMP-014.tcl ********************"

CALResetConfiguration $ntgrDUT
CALSetSwitchMngrIPAddr $ntgrDUT
CALDisableConsoleTimeout $ntgrDUT

set bRet 1

NtgrDumpLog  $NTGR_LOG_TERSE  "SNMP-014.tcl" "Start a trap receiver/capture packets on PC, Press any key when you done."
gets stdin

CALAddTrapReceiver $ntgrDUT $ntgrComminity $ntgrReceiver(1)
CALPortLinkDown $ntgrDUT $ntgrLinkUpdown

NtgrDumpLog  $NTGR_LOG_TERSE  "SNMP-014.tcl" "Press 'y' if trap messages send to IP $ntgrReceiver(1); otherwise, press other key."
set key [gets stdin]
if {$key == "y" || $key == "Y"} {
    set bRet [expr $bRet & 1]
} else {
    set bRet [expr $bRet & 0]
}


CALModifyTrapReceiver $ntgrDUT $ntgrComminity $ntgrReceiver(1) $ntgrReceiver(2)
CALPortLinkUp $ntgrDUT $ntgrLinkUpdown

NtgrDumpLog  $NTGR_LOG_TERSE  "SNMP-014.tcl" "Modify the receiver IP to $ntgrReceiver(2), press 'y' if trap messages send to it; otherwise, press other key."

set key [gets stdin]
if {$key == "y" || $key == "Y"} {
    set bRet [expr $bRet & 1]
} else {
    set bRet [expr $bRet & 0]
}


if {$bRet == 1} {
    set NtgrTestResult(SNMP-014.tcl) "OK"
} else {
    set NtgrTestResult(SNMP-014.tcl) "NG"
}

NtgrDumpLog  $NTGR_LOG_TERSE  "SNMP-014.tcl" "******************** Complete Test case SNMP-014.tcl ********************"
#*************************  End of Test case  **********************************