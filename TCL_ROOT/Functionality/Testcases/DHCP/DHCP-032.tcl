################################################################################
#
#  File Name    : DHCP-032.tcl
#
#  Description  : This testcase used to test allocation handling 
#                 while address exhausting.
#
#  Revision     :
#        Date        Programmer        Description
#        2007/01/29  Scott             Newly created    
#
################################################################################

#*************************  Start of Test case  ********************************
NtgrDumpLog  $NTGR_LOG_TERSE  "DHCP-032.tcl" "******************** Starting Test case DHCP-032.tcl ********************"

CALResetConfiguration $ntgrDUT
CALSetSwitchMngrIPAddr $ntgrDUT
CALDisableConsoleTimeout $ntgrDUT

CALEnableDHCP $ntgrDUT
CALCreateDhcpAddrPool $ntgrDUT $ntgrPoolName
NtgrDumpLog  $NTGR_LOG_TERSE  "DHCP-032.tcl" "Using ixLoad to simulate clients to exhaust all configured addresses, press any key when you finished."
gets stdin

CALResetConfiguration $ntgrDUT2;# Reset DUT2 to let it learning address by DHCP
sleep 120
set bRet 1
set bRet [expr $bRet & [CALCheckExpect $ntgrDUT2 $ntgrCmdList $ntgrExpect 0]]


if {$bRet == 1} {
    set NtgrTestResult(DHCP-032.tcl) "OK"
} else {
    set NtgrTestResult(DHCP-032.tcl) "NG"
}

NtgrDumpLog  $NTGR_LOG_TERSE  "DHCP-032.tcl" "******************** Complete Test case DHCP-032.tcl ********************"
#*************************  End of Test case  **********************************