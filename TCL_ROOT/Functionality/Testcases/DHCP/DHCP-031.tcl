################################################################################
#
#  File Name    : DHCP-031.tcl
#
#  Description  : This testcase used to test add/delete DHCP pool
#                 repeatedly while it was used.
#
#  Revision     :
#        Date        Programmer        Description
#        2007/01/29  Scott             Newly created    
#
################################################################################

#*************************  Start of Test case  ********************************
NtgrDumpLog  $NTGR_LOG_TERSE  "DHCP-031.tcl" "******************** Starting Test case DHCP-031.tcl ********************"

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

    CALDelDhcpAddrPool $ntgrDUT $ntgrPoolName
    sleep 120
    set bRet [expr $bRet & [CALCheckExpect $ntgrDUT2 $ntgrCmdList $ntgrExpect 0]]

    CALCreateDhcpAddrPool $ntgrDUT $ntgrPoolName
}

if {$bRet == 1} {
    set NtgrTestResult(DHCP-031.tcl) "OK"
} else {
    set NtgrTestResult(DHCP-031.tcl) "NG"
}

NtgrDumpLog  $NTGR_LOG_TERSE  "DHCP-031.tcl" "******************** Complete Test case DHCP-031.tcl ********************"
#*************************  End of Test case  **********************************