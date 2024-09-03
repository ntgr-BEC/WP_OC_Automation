################################################################################
#
#  File Name    : SNMP-025.tcl
#
#  Description  : This testcase used to test deleting a VLAN's community, then
#                 delete the VLAN repeatedly.
#
#  Revision     :
#        Date        Programmer        Description
#        2007/03/12  Scott             Newly created    
#
################################################################################

#*************************  Start of Test case  ********************************
NtgrDumpLog  $NTGR_LOG_TERSE  "SNMP-025.tcl" "******************** Starting Test case SNMP-025.tcl ********************"

CALResetConfiguration $ntgrDUT
CALDisableConsoleTimeout $ntgrDUT
Netgear_connect_switch $ntgrDUT
CALRoutingEnable $ntgrDUT
Netgear_disconnect_switch $ntgrDUT

NtgrDumpLog  $NTGR_LOG_TERSE  "SNMP-025.tcl" "Start a trap receiver/capture packets on PC, Press any key when you done."
gets stdin

NtgrDumpLog  $NTGR_LOG_TERSE  "SNMP-025.tcl" "Please walk MIB information with $ntgrComminity during deleting."
for {set t 0} {$t<$ntgrRepeatTime} {incr t} {
    CALCreateVlan "VLAN200"
    CALSetCommunityReadWrite $ntgrDUT $ntgrComminity
    CALDeleteCommunity $ntgrDUT $ntgrComminity
    CALDeleteVlan "VLAN200"
}

CALCreateVlan "VLAN200"

set bRet 1

CALAddTrapReceiver $ntgrDUT $ntgrComminity $ntgrReceiver
CALSetCommunityReadWrite $ntgrDUT $ntgrComminity
CALPortLinkDown $ntgrDUT $ntgrLinkUpdown

NtgrDumpLog  $NTGR_LOG_TERSE  "SNMP-025.tcl" "Press 'y' if link up/down trap messages send to PC; otherwise, press other key."
set key [gets stdin]
if {$key == "y" || $key == "Y"} {
    set bRet [expr $bRet & 1]
} else {
    set bRet [expr $bRet & 0]
}

NtgrDumpLog  $NTGR_LOG_TERSE  "SNMP-025.tcl" "Try to set/get some information with community $ntgrComminity from PC"
NtgrDumpLog  $NTGR_LOG_TERSE  "SNMP-025.tcl" "Press 'y' if you can do set/get correctly; otherwise, press other key."

set key [gets stdin]
if {$key == "y" || $key == "Y"} {
    set bRet [expr $bRet & 1]
} else {
    set bRet [expr $bRet & 0]
}


if {$bRet == 1} {
    set NtgrTestResult(SNMP-025.tcl) "OK"
} else {
    set NtgrTestResult(SNMP-025.tcl) "NG"
}

NtgrDumpLog  $NTGR_LOG_TERSE  "SNMP-025.tcl" "******************** Complete Test case SNMP-025.tcl ********************"
#*************************  End of Test case  **********************************