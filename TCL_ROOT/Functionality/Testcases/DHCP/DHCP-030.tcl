################################################################################
#
#  File Name    : DHCP-030.tcl
#
#  Description  : This testcase used to test enable/disable DHCP service
#                 repeatedly while it was used.
#
#  Revision     :
#        Date        Programmer        Description
#        2007/01/29  Scott             Newly created    
#
################################################################################

#*************************  Start of Test case  ********************************
NtgrDumpLog  $NTGR_LOG_TERSE  "DHCP-030.tcl" "******************** Starting Test case DHCP-030.tcl ********************"

CALResetConfiguration $ntgrDUT
CALSetSwitchMngrIPAddr $ntgrDUT
CALDisableConsoleTimeout $ntgrDUT
CALResetConfiguration $ntgrDUT2;# Reset DUT2 to let it learning address by DHCP

CALEnableDHCP $ntgrDUT
CALCreateDhcpAddrPool $ntgrDUT $ntgrPoolName

for {set t 0} {$t<$ntgrRepeatTime} {incr t} {
    sleep 120
    set bRet 1
    set bRet [expr $bRet & [CALCheckExpect $ntgrDUT2 $ntgrCmdList $ntgrExpect]]

    CALDisableDHCP $ntgrDUT
    sleep 120
    set bRet [expr $bRet & [CALCheckExpect $ntgrDUT2 $ntgrCmdList $ntgrExpect 0]]

    CALEnableDHCP $ntgrDUT
}

if {$bRet == 1} {
    set NtgrTestResult(DHCP-030.tcl) "OK"
} else {
    set NtgrTestResult(DHCP-030.tcl) "NG"
}

NtgrDumpLog  $NTGR_LOG_TERSE  "DHCP-030.tcl" "******************** Complete Test case DHCP-030.tcl ********************"
#*************************  End of Test case  **********************************