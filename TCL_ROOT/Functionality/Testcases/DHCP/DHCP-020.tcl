################################################################################
#
#  File Name    : DHCP-020.tcl
#
#  Description  : This testcase used to test clear operation of DHCP statistics.
#
#  Revision     :
#        Date        Programmer        Description
#        2007/01/29  Scott             Newly created    
#
################################################################################

#*************************  Start of Test case  ********************************
NtgrDumpLog  $NTGR_LOG_TERSE  "DHCP-020.tcl" "******************** Starting Test case DHCP-020.tcl ********************"

CALResetConfiguration $ntgrDUT
CALSetSwitchMngrIPAddr $ntgrDUT
CALDisableConsoleTimeout $ntgrDUT
CALResetConfiguration $ntgrDUT2;# Reset DUT2 to let it learning address by DHCP


CALEnableDHCP $ntgrDUT
CALCreateDhcpAddrPool $ntgrDUT $ntgrPoolName
sleep 120

set bRet 1
set bRet [expr $bRet & [CALCheckExpect $ntgrDUT $ntgrCmdList $ntgrExpect1 0]]

CALClearDhcpStatistics $ntgrDUT
set bRet [expr $bRet & [CALCheckExpect $ntgrDUT $ntgrCmdList $ntgrExpect2]]

if {$bRet == 1} {
    set NtgrTestResult(DHCP-020.tcl) "OK"
} else {
    set NtgrTestResult(DHCP-020.tcl) "NG"
}

NtgrDumpLog  $NTGR_LOG_TERSE  "DHCP-020.tcl" "******************** Complete Test case DHCP-020.tcl ********************"
#*************************  End of Test case  **********************************