################################################################################
#
#  File Name    : DHCP-006.tcl
#
#  Description  : This testcase used to test the netbios node type of DHCP service.
#
#  Revision     :
#        Date        Programmer        Description
#        2007/01/29  Scott             Newly created    
#
################################################################################

#*************************  Start of Test case  ********************************
NtgrDumpLog  $NTGR_LOG_TERSE  "DHCP-006.tcl" "******************** Starting Test case DHCP-006.tcl ********************"

CALResetConfiguration $ntgrDUT
CALSetSwitchMngrIPAddr $ntgrDUT
CALDisableConsoleTimeout $ntgrDUT

CALEnableDHCP $ntgrDUT
CALCreateDhcpAddrPool $ntgrDUT $ntgrPoolName

NtgrDumpLog  $NTGR_LOG_TERSE  "DHCP-006.tcl" "Configuration complete, please manually obtain address from PC and capture packets on PC."
NtgrDumpLog  $NTGR_LOG_TERSE  "DHCP-006.tcl" "If in captured packets has the specified netbios node type, press 'y'; otherwise, press other key."

set bRet 1
set key [gets stdin]
if {$key == "y" || $key == "Y"} {
    set bRet [expr $bRet & 1]
} else {
    set bRet [expr $bRet & 0]
}

CALConfigureDhcpNNT $ntgrDUT $ntgrPoolName "b-node"
NtgrDumpLog  $NTGR_LOG_TERSE  "DHCP-006.tcl" "Modify netbios node type to 'b-node'; release and renew address on PC again."
NtgrDumpLog  $NTGR_LOG_TERSE  "DHCP-006.tcl" "If in captured packets has the specified netbios node type, press 'y'; otherwise, press any other key."

set key [gets stdin]
if {$key == "y" || $key == "Y"} {
    set bRet [expr $bRet & 1]
} else {
    set bRet [expr $bRet & 0]
}

CALConfigureDhcpNNT $ntgrDUT $ntgrPoolName "h-node"
NtgrDumpLog  $NTGR_LOG_TERSE  "DHCP-006.tcl" "Modify netbios node type to 'h-node'; release and renew address on PC again."
NtgrDumpLog  $NTGR_LOG_TERSE  "DHCP-006.tcl" "If in captured packets has the specified netbios node type, press 'y'; otherwise, press any other key."

set key [gets stdin]
if {$key == "y" || $key == "Y"} {
    set bRet [expr $bRet & 1]
} else {
    set bRet [expr $bRet & 0]
}

CALConfigureDhcpNNT $ntgrDUT $ntgrPoolName "m-node"
NtgrDumpLog  $NTGR_LOG_TERSE  "DHCP-006.tcl" "Modify netbios node type to 'm-node'; release and renew address on PC again."
NtgrDumpLog  $NTGR_LOG_TERSE  "DHCP-006.tcl" "If in captured packets has the specified netbios node type, press 'y'; otherwise, press any other key."

set key [gets stdin]
if {$key == "y" || $key == "Y"} {
    set bRet [expr $bRet & 1]
} else {
    set bRet [expr $bRet & 0]
}

CALConfigureDhcpNNT $ntgrDUT $ntgrPoolName
NtgrDumpLog  $NTGR_LOG_TERSE  "DHCP-006.tcl" "Delete the configured netbios node type; release and renew address on PC again."
NtgrDumpLog  $NTGR_LOG_TERSE  "DHCP-006.tcl" "Press 'y' if no netbios node type in captured packets; otherwise, press any other key."

set key [gets stdin]
if {$key == "y" || $key == "Y"} {
    set bRet [expr $bRet & 1]
} else {
    set bRet [expr $bRet & 0]
}

if {$bRet == 1} {
    set NtgrTestResult(DHCP-006.tcl) "OK"
} else {
    set NtgrTestResult(DHCP-006.tcl) "NG"
}

NtgrDumpLog  $NTGR_LOG_TERSE  "DHCP-006.tcl" "******************** Complete Test case DHCP-006.tcl ********************"
#*************************  End of Test case  **********************************