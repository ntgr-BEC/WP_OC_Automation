################################################################################
#
#  File Name    : DHCP-029.tcl
#
#  Description  : This testcase used to test rebooting switch acted as DHCP client,
#                 test whether it can otain address again after rebooting.
#
#  Revision     :
#        Date        Programmer        Description
#        2007/01/29  Scott             Newly created    
#
################################################################################

#*************************  Start of Test case  ********************************
NtgrDumpLog  $NTGR_LOG_TERSE  "DHCP-029.tcl" "******************** Starting Test case DHCP-029.tcl ********************"

CALResetConfiguration $ntgrDUT
CALSetSwitchMngrIPAddr $ntgrDUT
CALDisableConsoleTimeout $ntgrDUT
CALResetConfiguration $ntgrDUT2;# Reset DUT2 to let it learning address by DHCP

CALEnableDHCP $ntgrDUT
CALCreateDhcpAddrPool $ntgrDUT $ntgrPoolName
sleep 120

set bRet 1
set bRet [expr $bRet & [CALCheckExpect $ntgrDUT2 $ntgrCmdList $ntgrExpect]]

CALRebootSwitch $ntgrDUT2
sleep 180
set bRet [expr $bRet & [CALCheckExpect $ntgrDUT2 $ntgrCmdList $ntgrExpect]]


if {$bRet == 1} {
    set NtgrTestResult(DHCP-029.tcl) "OK"
} else {
    set NtgrTestResult(DHCP-029.tcl) "NG"
}

NtgrDumpLog  $NTGR_LOG_TERSE  "DHCP-029.tcl" "******************** Complete Test case DHCP-029.tcl ********************"
#*************************  End of Test case  **********************************