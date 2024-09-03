################################################################################
#
#  File Name    : SNMP-027.tcl
#
#  Description  : This testcase used to test save and reboot SNMP agent.
#
#  Revision     :
#        Date        Programmer        Description
#        2007/03/12  Scott             Newly created    
#
################################################################################

#*************************  Start of Test case  ********************************
NtgrDumpLog  $NTGR_LOG_TERSE  "SNMP-027.tcl" "******************** Starting Test case SNMP-027.tcl ********************"

CALResetConfiguration $ntgrDUT
CALDisableConsoleTimeout $ntgrDUT

Netgear_connect_switch $ntgrDUT
CALRoutingEnable $ntgrDUT
Netgear_disconnect_switch $ntgrDUT
CALCreateVlan "VLAN200"

set bRet 1
NtgrDumpLog  $NTGR_LOG_TERSE  "SNMP-027.tcl" "Start a trap receiver/capture packets on PC, Press any key when you done."
gets stdin


CALAddTrapReceiver $ntgrDUT $ntgrComminity $ntgrReceiver
CALPortLinkDown $ntgrDUT $ntgrLinkUpdown

NtgrDumpLog  $NTGR_LOG_TERSE  "SNMP-027.tcl" "Press 'y' if link up/down trap messages send to PC; otherwise, press other key."
set key [gets stdin]
if {$key == "y" || $key == "Y"} {
    set bRet [expr $bRet & 1]
} else {
    set bRet [expr $bRet & 0]
}

NtgrDumpLog  $NTGR_LOG_TERSE  "SNMP-027.tcl" "Try to set/get some information from PC"
NtgrDumpLog  $NTGR_LOG_TERSE  "SNMP-027.tcl" "Press 'y' if you can do set/get correctly; otherwise, press other key."

set key [gets stdin]
if {$key == "y" || $key == "Y"} {
    set bRet [expr $bRet & 1]
} else {
    set bRet [expr $bRet & 0]
}

CALRebootSwitch $ntgrDUT
sleep 120
CALPortLinkUp $ntgrDUT $ntgrLinkUpdown

NtgrDumpLog  $NTGR_LOG_TERSE  "SNMP-027.tcl" "Press 'y' if link up/down trap messages send to PC; otherwise, press other key."
set key [gets stdin]
if {$key == "y" || $key == "Y"} {
    set bRet [expr $bRet & 1]
} else {
    set bRet [expr $bRet & 0]
}

NtgrDumpLog  $NTGR_LOG_TERSE  "SNMP-027.tcl" "Try to set/get some information from PC"
NtgrDumpLog  $NTGR_LOG_TERSE  "SNMP-027.tcl" "Press 'y' if you can do set/get correctly; otherwise, press other key."

set key [gets stdin]
if {$key == "y" || $key == "Y"} {
    set bRet [expr $bRet & 1]
} else {
    set bRet [expr $bRet & 0]
}

if {$bRet == 1} {
    set NtgrTestResult(SNMP-027.tcl) "OK"
} else {
    set NtgrTestResult(SNMP-027.tcl) "NG"
}

NtgrDumpLog  $NTGR_LOG_TERSE  "SNMP-027.tcl" "******************** Complete Test case SNMP-027.tcl ********************"
#*************************  End of Test case  **********************************