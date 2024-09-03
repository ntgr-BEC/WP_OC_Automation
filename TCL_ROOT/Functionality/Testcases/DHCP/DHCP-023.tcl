################################################################################
#
#  File Name    : DHCP-023.tcl
#
#  Description  : This testcase used to test DHCP relay.
#
#  Revision     :
#        Date        Programmer        Description
#        2007/01/29  Scott             Newly created    
#
################################################################################

#*************************  Start of Test case  ********************************
NtgrDumpLog  $NTGR_LOG_TERSE  "DHCP-023.tcl" "******************** Starting Test case DHCP-023.tcl ********************"

NtgrDumpLog  $NTGR_LOG_TERSE  "DHCP-023.tcl" "Start a DHCP server on PC with network 200.1.1.0/24; Press any key when you finished."
gets stdin

CALResetConfiguration $ntgrDUT
CALSetSwitchMngrIPAddr $ntgrDUT
CALDisableConsoleTimeout $ntgrDUT

CALCreateVlan "VLAN200"
CALEnableDHCP $ntgrDUT
CALEnableDhcpRelay $ntgrDUT
CALDhcpRelayServer $ntgrDUT $ntgrServerIP

CALResetConfiguration $ntgrDUT2;
sleep 120

set bRet 1
set bRet [expr $bRet & [CALCheckExpect $ntgrDUT2 $ntgrCmdList $ntgrExpect]];# Check whether it can obtain address


if {$bRet == 1} {
    set NtgrTestResult(DHCP-023.tcl) "OK"
} else {
    set NtgrTestResult(DHCP-023.tcl) "NG"
}

NtgrDumpLog  $NTGR_LOG_TERSE  "DHCP-023.tcl" "******************** Complete Test case DHCP-023.tcl ********************"
#*************************  End of Test case  **********************************