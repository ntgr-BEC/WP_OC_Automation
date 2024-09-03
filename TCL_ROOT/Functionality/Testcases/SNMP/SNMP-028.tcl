################################################################################
#
#  File Name    : SNMP-028.tcl
#
#  Description  : This testcase used to test saving SNMP configuration to
#                 a script file and apply it again.
#
#  Revision     :
#        Date        Programmer        Description
#        2007/03/12  Scott             Newly created    
#
################################################################################

#*************************  Start of Test case  ********************************
NtgrDumpLog  $NTGR_LOG_TERSE  "SNMP-028.tcl" "******************** Starting Test case SNMP-028.tcl ********************"

CALResetConfiguration $ntgrDUT
CALDisableConsoleTimeout $ntgrDUT

Netgear_connect_switch $ntgrDUT
CALRoutingEnable $ntgrDUT
Netgear_disconnect_switch $ntgrDUT
CALCreateVlan "VLAN200"

set bRet 1
NtgrDumpLog  $NTGR_LOG_TERSE  "SNMP-028.tcl" "Start a trap receiver/capture packets on PC, Press any key when you done."
gets stdin


CALAddTrapReceiver $ntgrDUT $ntgrComminity $ntgrReceiver
CALPortLinkDown $ntgrDUT $ntgrLinkUpdown

NtgrDumpLog  $NTGR_LOG_TERSE  "SNMP-028.tcl" "Press 'y' if link up/down trap messages send to PC; otherwise, press other key."
set key [gets stdin]
if {$key == "y" || $key == "Y"} {
    set bRet [expr $bRet & 1]
} else {
    set bRet [expr $bRet & 0]
}

NtgrDumpLog  $NTGR_LOG_TERSE  "SNMP-028.tcl" "Try to get some information from PC and set system name, contact, location with some value"
NtgrDumpLog  $NTGR_LOG_TERSE  "SNMP-028.tcl" "Press 'y' if you can do set/get correctly; otherwise, press other key."

set key [gets stdin]
if {$key == "y" || $key == "Y"} {
    set bRet [expr $bRet & 1]
} else {
    set bRet [expr $bRet & 0]
}

NtgrDumpLog  $NTGR_LOG_TERSE  "SNMP-028.tcl" "Save current configuration into a script file and then apply it again"
NtgrDumpLog  $NTGR_LOG_TERSE  "SNMP-028.tcl" "Press 'y' if SNMP configuration applied correctly; otherwise, press other key."

set key [gets stdin]
if {$key == "y" || $key == "Y"} {
    set bRet [expr $bRet & 1]
} else {
    set bRet [expr $bRet & 0]
}

if {$bRet == 1} {
    set NtgrTestResult(SNMP-028.tcl) "OK"
} else {
    set NtgrTestResult(SNMP-028.tcl) "NG"
}

NtgrDumpLog  $NTGR_LOG_TERSE  "SNMP-028.tcl" "******************** Complete Test case SNMP-028.tcl ********************"
#*************************  End of Test case  **********************************