################################################################################
#
#  File Name    : DHCP-011.tcl
#
#  Description  : This testcase used to test static DHCP: allocate special address for
#                 specified host.
#
#  Revision     :
#        Date        Programmer        Description
#        2007/01/29  Scott             Newly created
#
################################################################################

#*************************  Start of Test case  ********************************
NtgrDumpLog  $NTGR_LOG_TERSE  "DHCP-011.tcl" "******************** Starting Test case DHCP-011.tcl ********************"

CALResetConfiguration $ntgrDUT
CALSetSwitchMngrIPAddr $ntgrDUT $ntgrDUTMgrIP
CALDisableConsoleTimeout $ntgrDUT

CALEnableDHCP $ntgrDUT
CALCreateDhcpAddrPool $ntgrDUT $ntgrPoolName1

NtgrDumpLog  $NTGR_LOG_TERSE  "DHCP-011.tcl" "Configuration complete, please manually obtain address from PC."
NtgrDumpLog  $NTGR_LOG_TERSE  "DHCP-011.tcl" "If PC got the first address of address pool, press 'y'; otherwise, press other key."

set bRet 1
set key [gets stdin]
if {$key == "y" || $key == "Y"} {
    set bRet [expr $bRet & 1]
} else {
    set bRet [expr $bRet & 0]
}

CALCreateDhcpAddrPool $ntgrDUT $ntgrPoolName2
NtgrDumpLog  $NTGR_LOG_TERSE  "DHCP-011.tcl" "Create static address pool, allocate specified address for the PC."
NtgrDumpLog  $NTGR_LOG_TERSE  "DHCP-011.tcl" "Press 'y' if PC obtained the specified address; otherwise, press any other key."

set key [gets stdin]
if {$key == "y" || $key == "Y"} {
    set bRet [expr $bRet & 1]
} else {
    set bRet [expr $bRet & 0]
}

CALDelDhcpAddrPool $ntgrDUT $ntgrPoolName2
NtgrDumpLog  $NTGR_LOG_TERSE  "DHCP-011.tcl" "Delete static address pool, obtain address from the PC."
NtgrDumpLog  $NTGR_LOG_TERSE  "DHCP-011.tcl" "Press 'y' if PC obtained is not the specified address; otherwise, press any other key."

set key [gets stdin]
if {$key == "y" || $key == "Y"} {
    set bRet [expr $bRet & 1]
} else {
    set bRet [expr $bRet & 0]
}

if {$bRet == 1} {
    set NtgrTestResult(DHCP-011.tcl) "OK"
} else {
    set NtgrTestResult(DHCP-011.tcl) "NG"
}

NtgrDumpLog  $NTGR_LOG_TERSE  "DHCP-011.tcl" "******************** Complete Test case DHCP-011.tcl ********************"
#*************************  End of Test case  **********************************