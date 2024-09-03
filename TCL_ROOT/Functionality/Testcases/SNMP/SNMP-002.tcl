################################################################################
#
#  File Name    : SNMP-002.tcl
#
#  Description  : This testcase used to test configure the SNMP system loacation
#                 and verify the configured loacation thru MIB Browser.
#
#  Revision     :
#        Date        Programmer        Description
#        2007/03/12  Scott             Newly created    
#
################################################################################

#*************************  Start of Test case  ********************************
NtgrDumpLog  $NTGR_LOG_TERSE  "SNMP-002.tcl" "******************** Starting Test case SNMP-002.tcl ********************"

CALResetConfiguration $ntgrDUT
CALSetSwitchMngrIPAddr $ntgrDUT
CALDisableConsoleTimeout $ntgrDUT

set bRet 1
CALConfigSnmpLocation $ntgrDUT $ntgrLocation
set bRet [expr $bRet & [CALCheckExpect $ntgrDUT $ntgrCmd $ntgrExpect]]

NtgrDumpLog  $NTGR_LOG_TERSE  "SNMP-002.tcl" "Configuration complete, please manually obtain system loacation thru MIB Browser from PC."
NtgrDumpLog  $NTGR_LOG_TERSE  "SNMP-002.tcl" "Press 'y' if you got the configured system loacation; otherwise, press other key."

set key [gets stdin]
if {$key == "y" || $key == "Y"} {
    set bRet [expr $bRet & 1]
} else {
    set bRet [expr $bRet & 0]
}


NtgrDumpLog  $NTGR_LOG_TERSE  "SNMP-002.tcl" "Set the system loacation to empty, press any key when you done."
gets stdin

set bRet [expr $bRet & [CALCheckExpect $ntgrDUT $ntgrCmd $ntgrExpect 0]]


if {$bRet == 1} {
    set NtgrTestResult(SNMP-002.tcl) "OK"
} else {
    set NtgrTestResult(SNMP-002.tcl) "NG"
}

NtgrDumpLog  $NTGR_LOG_TERSE  "SNMP-002.tcl" "******************** Complete Test case SNMP-002.tcl ********************"
#*************************  End of Test case  **********************************