################################################################################
#
#  File Name    : DHCP-010.tcl
#
#  Description  : This testcase used to test exclusion of some addresses from pool.
#
#  Revision     :
#        Date        Programmer        Description
#        2007/01/29  Scott             Newly created    
#
################################################################################

#*************************  Start of Test case  ********************************
NtgrDumpLog  $NTGR_LOG_TERSE  "DHCP-010.tcl" "******************** Starting Test case DHCP-010.tcl ********************"

CALResetConfiguration $ntgrDUT
CALSetSwitchMngrIPAddr $ntgrDUT
CALDisableConsoleTimeout $ntgrDUT

CALEnableDHCP $ntgrDUT
CALCreateDhcpAddrPool $ntgrDUT $ntgrPoolName

NtgrDumpLog  $NTGR_LOG_TERSE  "DHCP-010.tcl" "Configuration complete, please manually obtain address from PC."
NtgrDumpLog  $NTGR_LOG_TERSE  "DHCP-010.tcl" "If PC obtained the first address, press 'y'; otherwise, press other key."

set bRet 1
set key [gets stdin]
if {$key == "y" || $key == "Y"} {
    set bRet [expr $bRet & 1]
} else {
    set bRet [expr $bRet & 0]
}

CALAddDhcpExcludeAddr $ntgrDUT $ntgr_DHCP_ExcludeAddr_1
NtgrDumpLog  $NTGR_LOG_TERSE  "DHCP-010.tcl" "Add excluded address $ntgr_DHCP_ExcludeAddr_1; release and renew address on PC again."
NtgrDumpLog  $NTGR_LOG_TERSE  "DHCP-010.tcl" "Press 'y' if PC obtained address is not $ntgr_DHCP_ExcludeAddr_1; otherwise, press any other key."

set key [gets stdin]
if {$key == "y" || $key == "Y"} {
    set bRet [expr $bRet & 1]
} else {
    set bRet [expr $bRet & 0]
}

CALDelDhcpExcludeAddr $ntgrDUT $ntgr_DHCP_ExcludeAddr_1
NtgrDumpLog  $NTGR_LOG_TERSE  "DHCP-010.tcl" "Delete excluded address $ntgr_DHCP_ExcludeAddr_1; release and renew address on PC again."
NtgrDumpLog  $NTGR_LOG_TERSE  "DHCP-010.tcl" "Press 'y' if PC obtained address as before; otherwise, press any other key."

set key [gets stdin]
if {$key == "y" || $key == "Y"} {
    set bRet [expr $bRet & 1]
} else {
    set bRet [expr $bRet & 0]
}

CALAddDhcpExcludeAddr $ntgrDUT $ntgr_DHCP_ExcludeAddr_2
NtgrDumpLog  $NTGR_LOG_TERSE  "DHCP-010.tcl" "Add excluded address range $ntgr_DHCP_ExcludeAddr_2; release and renew address on PC again."
NtgrDumpLog  $NTGR_LOG_TERSE  "DHCP-010.tcl" "Press 'y' if PC obtained address is followed the range $ntgr_DHCP_ExcludeAddr_2; otherwise, press any other key."

set key [gets stdin]
if {$key == "y" || $key == "Y"} {
    set bRet [expr $bRet & 1]
} else {
    set bRet [expr $bRet & 0]
}

CALDelDhcpExcludeAddr $ntgrDUT $ntgr_DHCP_ExcludeAddr_2
NtgrDumpLog  $NTGR_LOG_TERSE  "DHCP-010.tcl" "Delete excluded address range $ntgr_DHCP_ExcludeAddr_2; release and renew address on PC again."
NtgrDumpLog  $NTGR_LOG_TERSE  "DHCP-010.tcl" "Press 'y' if PC obtained address as before; otherwise, press any other key."

set key [gets stdin]
if {$key == "y" || $key == "Y"} {
    set bRet [expr $bRet & 1]
} else {
    set bRet [expr $bRet & 0]
}

if {$bRet == 1} {
    set NtgrTestResult(DHCP-010.tcl) "OK"
} else {
    set NtgrTestResult(DHCP-010.tcl) "NG"
}

NtgrDumpLog  $NTGR_LOG_TERSE  "DHCP-010.tcl" "******************** Complete Test case DHCP-010.tcl ********************"
#*************************  End of Test case  **********************************