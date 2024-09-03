################################################################################
#
#  File Name    : DHCP-007.tcl
#
#  Description  : This testcase used to test the bootfile name of DHCP service.
#
#  Revision     :
#        Date        Programmer        Description
#        2007/01/29  Scott             Newly created    
#
################################################################################

#*************************  Start of Test case  ********************************
NtgrDumpLog  $NTGR_LOG_TERSE  "DHCP-007.tcl" "******************** Starting Test case DHCP-007.tcl ********************"

CALResetConfiguration $ntgrDUT
CALSetSwitchMngrIPAddr $ntgrDUT
CALDisableConsoleTimeout $ntgrDUT

CALEnableDHCP $ntgrDUT
CALCreateDhcpAddrPool $ntgrDUT $ntgrPoolName

NtgrDumpLog  $NTGR_LOG_TERSE  "DHCP-007.tcl" "Configuration complete, please manually obtain address from PC and capture packets on PC."
NtgrDumpLog  $NTGR_LOG_TERSE  "DHCP-007.tcl" "If the specified bootfile name exist in captured packets, press 'y'; otherwise, press other key."

set bRet 1
set key [gets stdin]
if {$key == "y" || $key == "Y"} {
    set bRet [expr $bRet & 1]
} else {
    set bRet [expr $bRet & 0]
}

CALResetDhcpBootfile $ntgrDUT $ntgrPoolName
NtgrDumpLog  $NTGR_LOG_TERSE  "DHCP-007.tcl" "Delete the configured bootfile name; release and renew address on PC again."
NtgrDumpLog  $NTGR_LOG_TERSE  "DHCP-007.tcl" "Press 'y' if no bootfile name exists in captured packets; otherwise, press any other key."

set key [gets stdin]
if {$key == "y" || $key == "Y"} {
    set bRet [expr $bRet & 1]
} else {
    set bRet [expr $bRet & 0]
}

if {$bRet == 1} {
    set NtgrTestResult(DHCP-007.tcl) "OK"
} else {
    set NtgrTestResult(DHCP-007.tcl) "NG"
}

NtgrDumpLog  $NTGR_LOG_TERSE  "DHCP-007.tcl" "******************** Complete Test case DHCP-007.tcl ********************"
#*************************  End of Test case  **********************************