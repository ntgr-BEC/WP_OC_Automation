################################################################################
#
#  File Name    : DHCP-034.tcl
#
#  Description  : This testcase used to test thousands of clients' request address.
#
#  Revision     :
#        Date        Programmer        Description
#        2007/01/29  Scott             Newly created    
#
################################################################################

#*************************  Start of Test case  ********************************
NtgrDumpLog  $NTGR_LOG_TERSE  "DHCP-034.tcl" "******************** Starting Test case DHCP-034.tcl ********************"

CALResetConfiguration $ntgrDUT
CALSetSwitchMngrIPAddr $ntgrDUT
CALDisableConsoleTimeout $ntgrDUT

CALEnableDHCP $ntgrDUT
CALCreateDhcpAddrPool $ntgrDUT $ntgrPoolName

NtgrDumpLog  $NTGR_LOG_TERSE  "DHCP-034.tcl" "Using ixLoad to simulate thousands of clients to request address."
NtgrDumpLog  $NTGR_LOG_TERSE  "DHCP-034.tcl" "Press 'y' if DUT could process all those requests and allocate address correctly."

set key [gets stdin]

if {$key == "y" || $key == "Y"} {
    set NtgrTestResult(DHCP-034.tcl) "OK"
} else {
    set NtgrTestResult(DHCP-034.tcl) "NG"
}


NtgrDumpLog  $NTGR_LOG_TERSE  "DHCP-034.tcl" "******************** Complete Test case DHCP-034.tcl ********************"
#*************************  End of Test case  **********************************