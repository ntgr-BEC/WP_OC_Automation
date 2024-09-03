################################################################################
#
#  File Name    : DHCP-014.tcl
#
#  Description  : This testcase used to test the exclusion of the specified address.
#
#  Revision     :
#        Date        Programmer        Description
#        2007/01/29  Scott             Newly created    
#
################################################################################

#*************************  Start of Test case  ********************************
NtgrDumpLog  $NTGR_LOG_TERSE  "DHCP-014.tcl" "******************** Starting Test case DHCP-014.tcl ********************"

CALResetConfiguration $ntgrDUT
CALSetSwitchMngrIPAddr $ntgrDUT
CALDisableConsoleTimeout $ntgrDUT

CALEnableDHCP $ntgrDUT
CALCreateDhcpAddrPool $ntgrDUT $ntgrPoolName1

NtgrDumpLog  $NTGR_LOG_TERSE  "DHCP-014.tcl" "Configuration complete, please manually obtain address from PC and capture packets on PC."
NtgrDumpLog  $NTGR_LOG_TERSE  "DHCP-014.tcl" "If PC got the specified address, press 'y'; otherwise, press other key."

set bRet 1
set key [gets stdin]
if {$key == "y" || $key == "Y"} {
    set bRet [expr $bRet & 1]
} else {
    set bRet [expr $bRet & 0]
}


CALAddDhcpExcludeAddr $ntgrDUT $ntgr_DHCP_ExcludeAddr_1
NtgrDumpLog  $NTGR_LOG_TERSE  "DHCP-014.tcl" "Exclude the specified address; release and renew address on PC again."
NtgrDumpLog  $NTGR_LOG_TERSE  "DHCP-014.tcl" "Press 'y' if PC could not get address; otherwise, press any other key."

set key [gets stdin]
if {$key == "y" || $key == "Y"} {
    set bRet [expr $bRet & 1]
} else {
    set bRet [expr $bRet & 0]
}


CALCreateDhcpAddrPool $ntgrDUT $ntgrPoolName2
NtgrDumpLog  $NTGR_LOG_TERSE  "DHCP-014.tcl" "Create a dynamic address pool, obtain address from PC again."
NtgrDumpLog  $NTGR_LOG_TERSE  "DHCP-014.tcl" "If PC could not get any address, press 'y'; otherwise, press other key."

set bRet 1
set key [gets stdin]
if {$key == "y" || $key == "Y"} {
    set bRet [expr $bRet & 1]
} else {
    set bRet [expr $bRet & 0]
}


CALDelDhcpExcludeAddr $ntgrDUT $ntgr_DHCP_ExcludeAddr_1
NtgrDumpLog  $NTGR_LOG_TERSE  "DHCP-014.tcl" "Delete the excluded specified address, obtain address from PC again."
NtgrDumpLog  $NTGR_LOG_TERSE  "DHCP-014.tcl" "If PC got the specified address again, press 'y'; otherwise, press other key."

set bRet 1
set key [gets stdin]
if {$key == "y" || $key == "Y"} {
    set bRet [expr $bRet & 1]
} else {
    set bRet [expr $bRet & 0]
}

if {$bRet == 1} {
    set NtgrTestResult(DHCP-014.tcl) "OK"
} else {
    set NtgrTestResult(DHCP-014.tcl) "NG"
}

NtgrDumpLog  $NTGR_LOG_TERSE  "DHCP-014.tcl" "******************** Complete Test case DHCP-014.tcl ********************"
#*************************  End of Test case  **********************************