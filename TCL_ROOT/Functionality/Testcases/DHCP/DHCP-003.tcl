################################################################################
#
#  File Name    : DHCP-003.tcl
#
#  Description  : This testcase used to test the DNS server of DHCP service.
#
#  Revision     :
#        Date        Programmer        Description
#        2007/01/29  Scott             Newly created    
#
################################################################################

#*************************  Start of Test case  ********************************
NtgrDumpLog  $NTGR_LOG_TERSE  "DHCP-003.tcl" "******************** Starting Test case DHCP-003.tcl ********************"

CALResetConfiguration $ntgrDUT
CALSetSwitchMngrIPAddr $ntgrDUT
CALDisableConsoleTimeout $ntgrDUT

CALEnableDHCP $ntgrDUT
CALCreateDhcpAddrPool $ntgrDUT $ntgrPoolName

NtgrDumpLog  $NTGR_LOG_TERSE  "DHCP-003.tcl" "Configuration complete, please manually obtain address from PC."
NtgrDumpLog  $NTGR_LOG_TERSE  "DHCP-003.tcl" "If PC gained address with the specified DNS server, press 'y'; otherwise, press other key."

set bRet 1
set key [gets stdin]
if {$key == "y" || $key == "Y"} {
    set bRet [expr $bRet & 1]
} else {
    set bRet [expr $bRet & 0]
}

CALResetDhcpDNS $ntgrDUT $ntgrPoolName
NtgrDumpLog  $NTGR_LOG_TERSE  "DHCP-003.tcl" "Delete the configured DNS servers; release and renew address on PC again."
NtgrDumpLog  $NTGR_LOG_TERSE  "DHCP-003.tcl" "Press 'y' if no DNS server exists; otherwise, press any other key."

set key [gets stdin]
if {$key == "y" || $key == "Y"} {
    set bRet [expr $bRet & 1]
} else {
    set bRet [expr $bRet & 0]
}

if {$bRet == 1} {
    set NtgrTestResult(DHCP-003.tcl) "OK"
} else {
    set NtgrTestResult(DHCP-003.tcl) "NG"
}

NtgrDumpLog  $NTGR_LOG_TERSE  "DHCP-003.tcl" "******************** Complete Test case DHCP-003.tcl ********************"
#*************************  End of Test case  **********************************