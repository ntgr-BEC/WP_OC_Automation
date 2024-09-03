################################################################################
#
#  File Name    : DHCP-017.tcl
#
#  Description  : This testcase used to test ping packets number.
#
#  Revision     :
#        Date        Programmer        Description
#        2007/01/29  Scott             Newly created    
#
################################################################################

#*************************  Start of Test case  ********************************
NtgrDumpLog  $NTGR_LOG_TERSE  "DHCP-017.tcl" "******************** Starting Test case DHCP-017.tcl ********************"

CALResetConfiguration $ntgrDUT
CALSetSwitchMngrIPAddr $ntgrDUT
CALDisableConsoleTimeout $ntgrDUT

CALEnableDHCP $ntgrDUT
CALCreateDhcpAddrPool $ntgrDUT $ntgrPoolName
CALConfigDhcpPingNumber $ntgrDUT $ntgrPingNum

NtgrDumpLog  $NTGR_LOG_TERSE  "DHCP-017.tcl" "Configuration complete, please manually configure PC with the first IP and capture packets on it."
NtgrDumpLog  $NTGR_LOG_TERSE  "DHCP-017.tcl" "Obtain address on DUT2; if we got $ntgrPingNum ping packets captured on PC, press 'y'; otherwise, press other key."

set bRet 1
set key [gets stdin]
if {$key == "y" || $key == "Y"} {
    set bRet [expr $bRet & 1]
} else {
    set bRet [expr $bRet & 0]
}

if {$bRet == 1} {
    set NtgrTestResult(DHCP-017.tcl) "OK"
} else {
    set NtgrTestResult(DHCP-017.tcl) "NG"
}

NtgrDumpLog  $NTGR_LOG_TERSE  "DHCP-017.tcl" "******************** Complete Test case DHCP-017.tcl ********************"
#*************************  End of Test case  **********************************