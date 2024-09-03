################################################################################
#
#  File Name    : SNMP-003.tcl
#
#  Description  : This testcase used to test configure the SNMP system contact and
#                 verify the configured contact thru MIB Browser.
#
#  Revision     :
#        Date        Programmer        Description
#        2007/03/12  Scott             Newly created    
#
################################################################################

#*************************  Start of Test case  ********************************
NtgrDumpLog  $NTGR_LOG_TERSE  "SNMP-003.tcl" "******************** Starting Test case SNMP-003.tcl ********************"

CALResetConfiguration $ntgrDUT
CALSetSwitchMngrIPAddr $ntgrDUT
CALDisableConsoleTimeout $ntgrDUT

set bRet 1
CALConfigSnmpContact $ntgrDUT $ntgrContact
set bRet [expr $bRet & [CALCheckExpect $ntgrDUT $ntgrCmd $ntgrExpect]]

NtgrDumpLog  $NTGR_LOG_TERSE  "SNMP-003.tcl" "Configuration complete, please manually obtain system contact thru MIB Browser from PC."
NtgrDumpLog  $NTGR_LOG_TERSE  "SNMP-003.tcl" "Press 'y' if you got the configured system contact; otherwise, press other key."

set key [gets stdin]
if {$key == "y" || $key == "Y"} {
    set bRet [expr $bRet & 1]
} else {
    set bRet [expr $bRet & 0]
}


NtgrDumpLog  $NTGR_LOG_TERSE  "SNMP-003.tcl" "Set the system contact to empty, press any key when you done."
gets stdin

set bRet [expr $bRet & [CALCheckExpect $ntgrDUT $ntgrCmd $ntgrExpect 0]]


if {$bRet == 1} {
    set NtgrTestResult(SNMP-003.tcl) "OK"
} else {
    set NtgrTestResult(SNMP-003.tcl) "NG"
}

NtgrDumpLog  $NTGR_LOG_TERSE  "SNMP-003.tcl" "******************** Complete Test case SNMP-003.tcl ********************"
#*************************  End of Test case  **********************************