################################################################################
#
#  File Name    : SNMP-023.tcl
#
#  Description  : This testcase used to test access/configure switch with
#                 other VLAN's community.
#
#  Revision     :
#        Date        Programmer        Description
#        2007/03/12  Scott             Newly created    
#
################################################################################

#*************************  Start of Test case  ********************************
NtgrDumpLog  $NTGR_LOG_TERSE  "SNMP-023.tcl" "******************** Starting Test case SNMP-023.tcl ********************"

CALResetConfiguration $ntgrDUT
CALDisableConsoleTimeout $ntgrDUT

Netgear_connect_switch $ntgrDUT
CALRoutingEnable $ntgrDUT
Netgear_disconnect_switch $ntgrDUT
CALCreateVlan "VLAN200"
CALCreateVlan "VLAN300"

set bRet 1
NtgrDumpLog  $NTGR_LOG_TERSE  "SNMP-023.tcl" "Start a trap receiver/capture packets on PC, Press any key when you done."
gets stdin

CALAddTrapReceiver $ntgrDUT $ntgrComminity1 $ntgrReceiver
CALSetCommunityReadWrite $ntgrDUT $ntgrComminity1
CALPortLinkDown $ntgrDUT $ntgrLinkUpdown

NtgrDumpLog  $NTGR_LOG_TERSE  "SNMP-023.tcl" "Press 'y' if link up/down trap messages send to PC; otherwise, press other key."
set key [gets stdin]
if {$key == "y" || $key == "Y"} {
    set bRet [expr $bRet & 1]
} else {
    set bRet [expr $bRet & 0]
}

NtgrDumpLog  $NTGR_LOG_TERSE  "SNMP-023.tcl" "Try to set/get some information with community $ntgrComminity1 from PC"
NtgrDumpLog  $NTGR_LOG_TERSE  "SNMP-023.tcl" "Press 'y' if you can do set/get correctly; otherwise, press other key."

set key [gets stdin]
if {$key == "y" || $key == "Y"} {
    set bRet [expr $bRet & 1]
} else {
    set bRet [expr $bRet & 0]
}


CALDisableCommunity $ntgrDUT $ntgrComminity1
CALAddTrapReceiver $ntgrDUT $ntgrComminity2 $ntgrReceiver
CALSetCommunityReadWrite $ntgrDUT $ntgrComminity2
CALPortLinkUp $ntgrDUT $ntgrLinkUpdown

NtgrDumpLog  $NTGR_LOG_TERSE  "SNMP-023.tcl" "Press 'y' if no link up/down trap messages send to PC; otherwise, press other key."
set key [gets stdin]
if {$key == "y" || $key == "Y"} {
    set bRet [expr $bRet & 1]
} else {
    set bRet [expr $bRet & 0]
}

NtgrDumpLog  $NTGR_LOG_TERSE  "SNMP-023.tcl" "Try to set/get some information with community $ntgrComminity2 from PC"
NtgrDumpLog  $NTGR_LOG_TERSE  "SNMP-023.tcl" "Press 'y' if you can not do set/get correctly; otherwise, press other key."

set key [gets stdin]
if {$key == "y" || $key == "Y"} {
    set bRet [expr $bRet & 1]
} else {
    set bRet [expr $bRet & 0]
}


if {$bRet == 1} {
    set NtgrTestResult(SNMP-023.tcl) "OK"
} else {
    set NtgrTestResult(SNMP-023.tcl) "NG"
}

NtgrDumpLog  $NTGR_LOG_TERSE  "SNMP-023.tcl" "******************** Complete Test case SNMP-023.tcl ********************"
#*************************  End of Test case  **********************************