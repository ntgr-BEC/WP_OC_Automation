################################################################################
#
#  File Name    : DHCP-026.tcl
#
#  Description  : This testcase used to test DHCP client to obtain address from server.
#
#  Revision     :
#        Date        Programmer        Description
#        2007/01/29  Scott             Newly created    
#
################################################################################

#*************************  Start of Test case  ********************************
NtgrDumpLog  $NTGR_LOG_TERSE  "DHCP-026.tcl" "******************** Starting Test case DHCP-026.tcl ********************"

CALResetConfiguration $ntgrDUT
CALSetSwitchMngrIPAddr $ntgrDUT
CALDisableConsoleTimeout $ntgrDUT
CALResetConfiguration $ntgrDUT2;# Reset DUT2 to let it learning address by DHCP

CALEnableDHCP $ntgrDUT
CALCreateDhcpAddrPool $ntgrDUT $ntgrPoolName
sleep 120

set bRet 1
set bRet [expr $bRet & [CALCheckExpect $ntgrDUT2 $ntgrCmdList $ntgrExpect]]

if {$bRet == 1} {
    set NtgrTestResult(DHCP-026.tcl) "OK"
} else {
    set NtgrTestResult(DHCP-026.tcl) "NG"
}

NtgrDumpLog  $NTGR_LOG_TERSE  "DHCP-026.tcl" "******************** Complete Test case DHCP-026.tcl ********************"
#*************************  End of Test case  **********************************