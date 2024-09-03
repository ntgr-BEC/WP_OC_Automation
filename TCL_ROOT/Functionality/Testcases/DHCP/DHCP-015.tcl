################################################################################
#
#  File Name    : DHCP-015.tcl
#
#  Description  : This testcase used to test ping function to check conflict.
#
#  Revision     :
#        Date        Programmer        Description
#        2007/01/29  Scott             Newly created    
#
################################################################################

#*************************  Start of Test case  ********************************
NtgrDumpLog  $NTGR_LOG_TERSE  "DHCP-015.tcl" "******************** Starting Test case DHCP-015.tcl ********************"

CALResetConfiguration $ntgrDUT
CALSetSwitchMngrIPAddr $ntgrDUT
CALDisableConsoleTimeout $ntgrDUT

CALEnableDHCP $ntgrDUT
CALCreateDhcpAddrPool $ntgrDUT $ntgrPoolName

NtgrDumpLog  $NTGR_LOG_TERSE  "DHCP-015.tcl" "Configuration complete, please manually configure PC with the first IP and capture packets on it."
NtgrDumpLog  $NTGR_LOG_TERSE  "DHCP-015.tcl" "Obtain address on DUT2, If it got the next address, press 'y'; otherwise, press other key."

set bRet 1
set key [gets stdin]
if {$key == "y" || $key == "Y"} {
    set bRet [expr $bRet & 1]
} else {
    set bRet [expr $bRet & 0]
}

if {$bRet == 1} {
    set NtgrTestResult(DHCP-015.tcl) "OK"
} else {
    set NtgrTestResult(DHCP-015.tcl) "NG"
}

NtgrDumpLog  $NTGR_LOG_TERSE  "DHCP-015.tcl" "******************** Complete Test case DHCP-015.tcl ********************"
#*************************  End of Test case  **********************************