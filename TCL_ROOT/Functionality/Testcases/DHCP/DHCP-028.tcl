################################################################################
#
#  File Name    : DHCP-028.tcl
#
#  Description  : This testcase used to test switch as DHCP client to
#                 release address when its lease time expired.
#
#  Revision     :
#        Date        Programmer        Description
#        2007/01/29  Scott             Newly created    
#
################################################################################

#*************************  Start of Test case  ********************************
NtgrDumpLog  $NTGR_LOG_TERSE  "DHCP-028.tcl" "******************** Starting Test case DHCP-028.tcl ********************"

CALResetConfiguration $ntgrDUT
CALSetSwitchMngrIPAddr $ntgrDUT
CALDisableConsoleTimeout $ntgrDUT
CALResetConfiguration $ntgrDUT2;# Reset DUT2 to let it learning address by DHCP

CALEnableDHCP $ntgrDUT
CALCreateDhcpAddrPool $ntgrDUT $ntgrPoolName
sleep 120

set bRet 1
set bRet [expr $bRet & [CALCheckExpect $ntgrDUT2 $ntgrCmdList $ntgrExpect]]

CALDisableDHCP $ntgrDUT
sleep 120
set bRet [expr $bRet & [CALCheckExpect $ntgrDUT2 $ntgrCmdList $ntgrExpect 0]]


if {$bRet == 1} {
    set NtgrTestResult(DHCP-028.tcl) "OK"
} else {
    set NtgrTestResult(DHCP-028.tcl) "NG"
}

NtgrDumpLog  $NTGR_LOG_TERSE  "DHCP-028.tcl" "******************** Complete Test case DHCP-028.tcl ********************"
#*************************  End of Test case  **********************************