################################################################################
#
#  File Name    : DHCP-025.tcl
#
#  Description  : This testcase used to test using different client to obtain address.
#
#  Revision     :
#        Date        Programmer        Description
#        2007/01/29  Scott             Newly created    
#
################################################################################

#*************************  Start of Test case  ********************************
NtgrDumpLog  $NTGR_LOG_TERSE  "DHCP-025.tcl" "******************** Starting Test case DHCP-025.tcl ********************"

CALResetConfiguration $ntgrDUT
CALSetSwitchMngrIPAddr $ntgrDUT
CALDisableConsoleTimeout $ntgrDUT

CALEnableDHCP $ntgrDUT
CALCreateDhcpAddrPool $ntgrDUT $ntgrPoolName

NtgrDumpLog  $NTGR_LOG_TERSE  "DHCP-025.tcl" "Using different client like Linux, Mac OX to obtain address from the server."
NtgrDumpLog  $NTGR_LOG_TERSE  "DHCP-025.tcl" "If they all can gain address from server, press 'y'; otherwise press any other key."
set key [gets stdin]

if {$key == "y" || $key == "Y"} {
    set NtgrTestResult(DHCP-025.tcl) "OK"
} else {
    set NtgrTestResult(DHCP-025.tcl) "NG"
}

NtgrDumpLog  $NTGR_LOG_TERSE  "DHCP-025.tcl" "******************** Complete Test case DHCP-025.tcl ********************"
#*************************  End of Test case  **********************************