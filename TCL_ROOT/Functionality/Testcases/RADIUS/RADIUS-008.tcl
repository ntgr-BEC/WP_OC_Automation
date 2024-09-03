################################################################################
#
#  File Name    : RADIUS-008.tcl
#
#  Description  : This testcase used to test accounting function to start/stop
#                 accounting.
#
#  Revision     :
#        Date        Programmer        Description
#        2007/04/13  Scott             Newly created    
#
################################################################################

#*************************  Start of Test case  ********************************
NtgrDumpLog  $NTGR_LOG_TERSE  "RADIUS-008.tcl" "******************** Starting Test case RADIUS-008.tcl ********************"
## Initialize the DUT
CALResetConfiguration $ntgrDUT
CALDisableConsoleTimeout $ntgrDUT
CALSetSwitchMngrIPAddr $ntgrDUT

## Configure DHCP service
CALEnableDHCP $ntgrDUT
CALCreateDhcpAddrPool $ntgrDUT $ntgrPoolName

## Configure 802.1X
CALEnableDot1X $ntgrDUT
CALSetDot1XPortAuthMode $ntgrDUT $ntgrRadiusPort $ntgrRadiusPortAuthMode

## Create authentication login and configure it for 802.1X
CALAddAuthLoginList $ntgrDUT $ntgrLoginList "radius"
CALSetDot1XDefaultLogin $ntgrDUT $ntgrLoginList

## Configure authentication radius server
CALAddRadiusServer $ntgrDUT $ntgrAuthServerMode $ntgrRadiusServer
CALEnableAccounting $ntgrDUT
CALAddRadiusServer $ntgrDUT $ntgrAcctServerMode $ntgrRadiusServer

NtgrDumpLog  $NTGR_LOG_TERSE  "RADIUS-008.tcl" "Complete configuration, please use the name 'aaa' and password 'aaa' to logon on DUT manually by 802.1X client on Windows XP."
NtgrDumpLog  $NTGR_LOG_TERSE  "RADIUS-008.tcl" "Capture packets on windows2003, if you succeed logon and accounting start packets were captured, press 'y' to continue; otherwise press any key."
set key [gets stdin]

set bRet 1
if {$key == "Y" || $key == "y"} {
    set bRet [expr $bRet & 1]
} else {
    set bRet [expr $bRet & 0]
}

NtgrDumpLog  $NTGR_LOG_TERSE  "RADIUS-008.tcl" "Disconnect 802.1X client, if accounting stop packets were captured, press 'y' to continue; otherwise press any key."
set key [gets stdin]
if {$key == "Y" || $key == "y"} {
    set bRet [expr $bRet & 1]
} else {
    set bRet [expr $bRet & 0]
}


if {$bRet ==1} {
    set NtgrTestResult(RADIUS-008.tcl) "OK"
} else {
    set NtgrTestResult(RADIUS-008.tcl) "NG"
}

NtgrDumpLog  $NTGR_LOG_TERSE  "RADIUS-008.tcl" "******************** Complete Test case RADIUS-008.tcl ********************"
#*************************  End of Test case  **********************************