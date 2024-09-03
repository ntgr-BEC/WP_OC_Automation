################################################################################
#
#  File Name    : DHCP-019.tcl
#
#  Description  : This testcase used to test Enable/disable automatic address 
#                 allocation for bootp clients.
#
#  Revision     :
#        Date        Programmer        Description
#        2007/01/29  Scott             Newly created    
#
################################################################################

#*************************  Start of Test case  ********************************
NtgrDumpLog  $NTGR_LOG_TERSE  "DHCP-019.tcl" "******************** Starting Test case DHCP-019.tcl ********************"

CALResetConfiguration $ntgrDUT
CALSetSwitchMngrIPAddr $ntgrDUT
CALDisableConsoleTimeout $ntgrDUT


CALEnableDHCP $ntgrDUT
CALCreateDhcpAddrPool $ntgrDUT $ntgrPoolName

CALEnableBootp $ntgrDUT
set bRet 1
set bRet [expr $bRet & [CALCheckExpect $ntgrDUT $ntgrCmdList $ntgrExpect1]]

CALDisableBootp $ntgrDUT
set bRet [expr $bRet & [CALCheckExpect $ntgrDUT $ntgrCmdList $ntgrExpect2]]

if {$bRet == 1} {
    set NtgrTestResult(DHCP-019.tcl) "OK"
} else {
    set NtgrTestResult(DHCP-019.tcl) "NG"
}

NtgrDumpLog  $NTGR_LOG_TERSE  "DHCP-019.tcl" "******************** Complete Test case DHCP-019.tcl ********************"
#*************************  End of Test case  **********************************