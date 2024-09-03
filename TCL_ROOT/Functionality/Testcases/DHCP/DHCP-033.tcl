################################################################################
#
#  File Name    : DHCP-033.tcl
#
#  Description  : This testcase used to test released addresses reallocation.
#
#  Revision     :
#        Date        Programmer        Description
#        2007/01/29  Scott             Newly created    
#
################################################################################

#*************************  Start of Test case  ********************************
NtgrDumpLog  $NTGR_LOG_TERSE  "DHCP-033.tcl" "******************** Starting Test case DHCP-033.tcl ********************"

CALResetConfiguration $ntgrDUT
CALSetSwitchMngrIPAddr $ntgrDUT
CALDisableConsoleTimeout $ntgrDUT

CALEnableDHCP $ntgrDUT
CALCreateDhcpAddrPool $ntgrDUT $ntgrPoolName
NtgrDumpLog  $NTGR_LOG_TERSE  "DHCP-033.tcl" "Using ixLoad to simulate clients to exhaust all configured addresses, press any key when you finished."
gets stdin

CALResetConfiguration $ntgrDUT2;# Reset DUT2 to let it learning address by DHCP
sleep 120
set bRet 1
set bRet [expr $bRet & [CALCheckExpect $ntgrDUT2 $ntgrCmdList $ntgrExpect 0]]

sleep 180
set bRet 1
set bRet [expr $bRet & [CALCheckExpect $ntgrDUT2 $ntgrCmdList $ntgrExpect]]; # Address reuse


if {$bRet == 1} {
    set NtgrTestResult(DHCP-033.tcl) "OK"
} else {
    set NtgrTestResult(DHCP-033.tcl) "NG"
}

NtgrDumpLog  $NTGR_LOG_TERSE  "DHCP-033.tcl" "******************** Complete Test case DHCP-033.tcl ********************"
#*************************  End of Test case  **********************************
