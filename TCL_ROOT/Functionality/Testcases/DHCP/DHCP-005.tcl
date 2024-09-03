################################################################################
#
#  File Name    : DHCP-005.tcl
#
#  Description  : This testcase used to test the netbios name server of DHCP service.
#
#  Revision     :
#        Date        Programmer        Description
#        2007/01/29  Scott             Newly created    
#
################################################################################

#*************************  Start of Test case  ********************************
NtgrDumpLog  $NTGR_LOG_TERSE  "DHCP-005.tcl" "******************** Starting Test case DHCP-005.tcl ********************"

CALResetConfiguration $ntgrDUT
CALSetSwitchMngrIPAddr $ntgrDUT
CALDisableConsoleTimeout $ntgrDUT

CALEnableDHCP $ntgrDUT
CALCreateDhcpAddrPool $ntgrDUT $ntgrPoolName

NtgrDumpLog  $NTGR_LOG_TERSE  "DHCP-005.tcl" "Configuration complete, please manually obtain address from PC."
NtgrDumpLog  $NTGR_LOG_TERSE  "DHCP-005.tcl" "If PC gained address with the specified netbios name servers, press 'y'; otherwise, press other key."

set bRet 1
set key [gets stdin]
if {$key == "y" || $key == "Y"} {
    set bRet [expr $bRet & 1]
} else {
    set bRet [expr $bRet & 0]
}

CALResetDhcpNNS $ntgrDUT $ntgrPoolName
NtgrDumpLog  $NTGR_LOG_TERSE  "DHCP-005.tcl" "Delete the configured netbios name servers; release and renew address on PC again."
NtgrDumpLog  $NTGR_LOG_TERSE  "DHCP-005.tcl" "Press 'y' if no netbios name server exists; otherwise, press any other key."

set key [gets stdin]
if {$key == "y" || $key == "Y"} {
    set bRet [expr $bRet & 1]
} else {
    set bRet [expr $bRet & 0]
}

if {$bRet == 1} {
    set NtgrTestResult(DHCP-005.tcl) "OK"
} else {
    set NtgrTestResult(DHCP-005.tcl) "NG"
}

NtgrDumpLog  $NTGR_LOG_TERSE  "DHCP-005.tcl" "******************** Complete Test case DHCP-005.tcl ********************"
#*************************  End of Test case  **********************************