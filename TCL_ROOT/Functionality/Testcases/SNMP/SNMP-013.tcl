################################################################################
#
#  File Name    : SNMP-013.tcl
#
#  Description  : This testcase used to test DUT could send SNMP
#                 trap messages to more receivers.
#
#  Revision     :
#        Date        Programmer        Description
#        2007/03/12  Scott             Newly created    
#
################################################################################

#*************************  Start of Test case  ********************************
NtgrDumpLog  $NTGR_LOG_TERSE  "SNMP-013.tcl" "******************** Starting Test case SNMP-013.tcl ********************"

CALResetConfiguration $ntgrDUT
CALSetSwitchMngrIPAddr $ntgrDUT
CALDisableConsoleTimeout $ntgrDUT

set bRet 1

NtgrDumpLog  $NTGR_LOG_TERSE  "SNMP-013.tcl" "Start a trap receiver/capture packets on PC, Press any key when you done."
gets stdin

foreach {idx IP} [array get ntgrReceiver] {
    CALAddTrapReceiver $ntgrDUT $ntgrComminity $IP
}
CALPortLinkDown $ntgrDUT $ntgrLinkUpdown

NtgrDumpLog  $NTGR_LOG_TERSE  "SNMP-013.tcl" "Press 'y' if trap messages send to all those IP; otherwise, press other key."
set key [gets stdin]
if {$key == "y" || $key == "Y"} {
    set bRet [expr $bRet & 1]
} else {
    set bRet [expr $bRet & 0]
}


CALDisableTrapReceiver $ntgrDUT $ntgrComminity $ntgrReceiver(5)
CALPortLinkUp $ntgrDUT $ntgrLinkUpdown

NtgrDumpLog  $NTGR_LOG_TERSE  "SNMP-013.tcl" "Disable the receiver $ntgrReceiver(5), press 'y' if trap messages do not send to it; otherwise, press other key."

set key [gets stdin]
if {$key == "y" || $key == "Y"} {
    set bRet [expr $bRet & 1]
} else {
    set bRet [expr $bRet & 0]
}


CALDelTrapReceiver $ntgrDUT $ntgrComminity $ntgrReceiver(6)
CALPortLinkDown $ntgrDUT $ntgrLinkUpdown
NtgrDumpLog  $NTGR_LOG_TERSE  "SNMP-013.tcl" "Delete the receiver $ntgrReceiver(6), press 'y' if trap messages do not send to it; otherwise, press other key."

set key [gets stdin]
if {$key == "y" || $key == "Y"} {
    set bRet [expr $bRet & 1]
} else {
    set bRet [expr $bRet & 0]
}

if {$bRet == 1} {
    set NtgrTestResult(SNMP-013.tcl) "OK"
} else {
    set NtgrTestResult(SNMP-013.tcl) "NG"
}

NtgrDumpLog  $NTGR_LOG_TERSE  "SNMP-013.tcl" "******************** Complete Test case SNMP-013.tcl ********************"
#*************************  End of Test case  **********************************