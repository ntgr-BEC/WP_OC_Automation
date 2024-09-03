################################################################################
#
#  File Name    : DHCP-022.tcl
#
#  Description  : This testcase used to test clear DHCP binding.
#
#  Revision     :
#        Date        Programmer        Description
#        2007/01/29  Scott             Newly created    
#
################################################################################

#*************************  Start of Test case  ********************************
NtgrDumpLog  $NTGR_LOG_TERSE  "DHCP-022.tcl" "******************** Starting Test case DHCP-022.tcl ********************"

CALResetConfiguration $ntgrDUT
CALSetSwitchMngrIPAddr $ntgrDUT
CALDisableConsoleTimeout $ntgrDUT
CALResetConfiguration $ntgrDUT2;# Reset DUT2 to let it learning address by DHCP

CALEnableDHCP $ntgrDUT
CALCreateDhcpAddrPool $ntgrDUT $ntgrPoolName
sleep 120

set bRet 1
set bRet [expr $bRet & [CALCheckExpect $ntgrDUT $ntgrCmdList $ntgrExpect]]

CALClearDhcpBind $ntgrDUT
set bRet [expr $bRet & [CALCheckExpect $ntgrDUT $ntgrCmdList $ntgrExpect 0]]

if {$bRet == 1} {
    set NtgrTestResult(DHCP-022.tcl) "OK"
} else {
    set NtgrTestResult(DHCP-022.tcl) "NG"
}

NtgrDumpLog  $NTGR_LOG_TERSE  "DHCP-022.tcl" "******************** Complete Test case DHCP-022.tcl ********************"
#*************************  End of Test case  **********************************