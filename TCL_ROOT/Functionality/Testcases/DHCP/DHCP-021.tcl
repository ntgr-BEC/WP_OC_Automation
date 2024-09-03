################################################################################
#
#  File Name    : DHCP-021.tcl
#
#  Description  : This testcase used to test clear DHCP conflict logging function.
#
#  Revision     :
#        Date        Programmer        Description
#        2007/01/29  Scott             Newly created    
#
################################################################################

#*************************  Start of Test case  ********************************
NtgrDumpLog  $NTGR_LOG_TERSE  "DHCP-021.tcl" "******************** Starting Test case DHCP-021.tcl ********************"

CALResetConfiguration $ntgrDUT
CALSetSwitchMngrIPAddr $ntgrDUT
CALDisableConsoleTimeout $ntgrDUT
CALResetConfiguration $ntgrDUT2;# Reset DUT2 to let it learning address by DHCP

CALEnableDHCP $ntgrDUT
CALCreateDhcpAddrPool $ntgrDUT $ntgrPoolName1
sleep 120

CALDelDhcpAddrPool $ntgrDUT $ntgrPoolName1
CALCreateDhcpAddrPool $ntgrDUT $ntgrPoolName2
NtgrDumpLog  $NTGR_LOG_TERSE  "DHCP-021.tcl" "Obtain address from PC, let PC and DUT2 to get the same address. Press any key when you finished."
gets stdin

set bRet 1
set bRet [expr $bRet & [CALCheckExpect $ntgrDUT $ntgrCmdList $ntgrExpect]]

CALClearDhcpConflict $ntgrDUT
set bRet [expr $bRet & [CALCheckExpect $ntgrDUT $ntgrCmdList $ntgrExpect 0]]

NtgrDumpLog  $NTGR_LOG_TERSE  "DHCP-021.tcl" "Again obtain address from PC, let PC and DUT2 to get the same address. Press any key when you finished."
gets stdin

set bRet [expr $bRet & [CALCheckExpect $ntgrDUT $ntgrCmdList $ntgrExpect]]
CALClearDhcpConflict $ntgrDUT $ntgrConflictIP
set bRet [expr $bRet & [CALCheckExpect $ntgrDUT $ntgrCmdList $ntgrExpect 0]]


if {$bRet == 1} {
    set NtgrTestResult(DHCP-021.tcl) "OK"
} else {
    set NtgrTestResult(DHCP-021.tcl) "NG"
}

NtgrDumpLog  $NTGR_LOG_TERSE  "DHCP-021.tcl" "******************** Complete Test case DHCP-021.tcl ********************"
#*************************  End of Test case  **********************************