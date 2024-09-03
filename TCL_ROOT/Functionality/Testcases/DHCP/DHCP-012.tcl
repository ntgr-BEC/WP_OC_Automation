################################################################################
#
#  File Name    : DHCP-012.tcl
#
#  Description  : This testcase used to test static DHCP: configure more
#                 static clients.
#
#  Revision     :
#        Date        Programmer        Description
#        2007/01/29  Scott             Newly created
#
################################################################################

#*************************  Start of Test case  ********************************
NtgrDumpLog  $NTGR_LOG_TERSE  "DHCP-012.tcl" "******************** Starting Test case DHCP-012.tcl ********************"

CALResetConfiguration $ntgrDUT
CALSetSwitchMngrIPAddr $ntgrDUT
CALDisableConsoleTimeout $ntgrDUT

CALEnableDHCP $ntgrDUT
CALCreateDhcpAddrPool $ntgrDUT $ntgrPoolName1
CALCreateDhcpAddrPool $ntgrDUT $ntgrPoolName2


NtgrDumpLog  $NTGR_LOG_TERSE  "DHCP-012.tcl" "Configuration complete, please manually obtain address from PC and DUT2."
NtgrDumpLog  $NTGR_LOG_TERSE  "DHCP-012.tcl" "If they both obtained their specified address, press 'y'; otherwise, press other key."

set bRet 1
set key [gets stdin]
if {$key == "y" || $key == "Y"} {
    set bRet [expr $bRet & 1]
} else {
    set bRet [expr $bRet & 0]
}


if {$bRet == 1} {
    set NtgrTestResult(DHCP-012.tcl) "OK"
} else {
    set NtgrTestResult(DHCP-012.tcl) "NG"
}

NtgrDumpLog  $NTGR_LOG_TERSE  "DHCP-012.tcl" "******************** Complete Test case DHCP-012.tcl ********************"
#*************************  End of Test case  **********************************