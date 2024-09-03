################################################################################
#
#  File Name    : DHCP-004.tcl
#
#  Description  : This testcase used to test the domain name of DHCP service.
#
#  Revision     :
#        Date        Programmer        Description
#        2007/01/29  Scott             Newly created    
#
################################################################################

#*************************  Start of Test case  ********************************
NtgrDumpLog  $NTGR_LOG_TERSE  "DHCP-004.tcl" "******************** Starting Test case DHCP-004.tcl ********************"

CALResetConfiguration $ntgrDUT
CALSetSwitchMngrIPAddr $ntgrDUT
CALDisableConsoleTimeout $ntgrDUT

CALEnableDHCP $ntgrDUT
CALCreateDhcpAddrPool $ntgrDUT $ntgrPoolName

NtgrDumpLog  $NTGR_LOG_TERSE  "DHCP-004.tcl" "Configuration complete, please manually obtain address from PC."
NtgrDumpLog  $NTGR_LOG_TERSE  "DHCP-004.tcl" "If PC gained address with the specified domain name, press 'y'; otherwise, press other key."

set bRet 1
set key [gets stdin]
if {$key == "y" || $key == "Y"} {
    set bRet [expr $bRet & 1]
} else {
    set bRet [expr $bRet & 0]
}

CALResetDhcpDomain $ntgrDUT $ntgrPoolName
NtgrDumpLog  $NTGR_LOG_TERSE  "DHCP-004.tcl" "Delete the configured domain name; release and renew address on PC again."
NtgrDumpLog  $NTGR_LOG_TERSE  "DHCP-004.tcl" "Press 'y' if no domain name exists; otherwise, press any other key."

set key [gets stdin]
if {$key == "y" || $key == "Y"} {
    set bRet [expr $bRet & 1]
} else {
    set bRet [expr $bRet & 0]
}

if {$bRet == 1} {
    set NtgrTestResult(DHCP-004.tcl) "OK"
} else {
    set NtgrTestResult(DHCP-004.tcl) "NG"
}

NtgrDumpLog  $NTGR_LOG_TERSE  "DHCP-004.tcl" "******************** Complete Test case DHCP-004.tcl ********************"
#*************************  End of Test case  **********************************