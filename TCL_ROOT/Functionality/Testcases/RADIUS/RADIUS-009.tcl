################################################################################
#
#  File Name    : RADIUS-009.tcl
#
#  Description  : This testcase used to test using non-default port of accounting.
#
#  Revision     :
#        Date        Programmer        Description
#        2007/04/13  Scott             Newly created    
#
################################################################################

#*************************  Start of Test case  ********************************
NtgrDumpLog  $NTGR_LOG_TERSE  "RADIUS-009.tcl" "******************** Starting Test case RADIUS-009.tcl ********************"
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

set bRet 1
## Configure authentication radius server
CALAddRadiusServer $ntgrDUT $ntgrAuthServerMode $ntgrRadiusServer
CALEnableAccounting $ntgrDUT
CALAddRadiusServer $ntgrDUT $ntgrAcctServerMode $ntgrRadiusServer

CALSetRadiusServerPort $ntgrDUT $ntgrAcctServerMode $ntgrRadiusServer "55000"
NtgrDumpLog  $NTGR_LOG_TERSE  "RADIUS-009.tcl" "Using accounting port to 55000, please use the name 'aaa' and password 'aaa' to logon on DUT manually by 802.1X client on Windows XP."
NtgrDumpLog  $NTGR_LOG_TERSE  "RADIUS-009.tcl" "Capture packtes on windows2003, if you succeed logon and captured accounting start/stop packets, press 'y' to continue; otherwise press any key."
set key [gets stdin]

if {$key == "Y" || $key == "y"} {
    set bRet [expr $bRet & 1]
} else {
    set bRet [expr $bRet & 0]
}

CALSetRadiusServerPort $ntgrDUT $ntgrAcctServerMode $ntgrRadiusServer "15000"
NtgrDumpLog  $NTGR_LOG_TERSE  "RADIUS-009.tcl" "Using accounting port to 15000, please use the name 'aaa' and password 'aaa' to logon on DUT manually by 802.1X client on Windows XP."
NtgrDumpLog  $NTGR_LOG_TERSE  "RADIUS-009.tcl" "Capture packtes on windows2003, if you succeed logon and captured accounting start/stop packets, press 'y' to continue; otherwise press any key."
set key [gets stdin]

if {$key == "Y" || $key == "y"} {
    set bRet [expr $bRet & 1]
} else {
    set bRet [expr $bRet & 0]
}

CALSetRadiusServerPort $ntgrDUT $ntgrAcctServerMode $ntgrRadiusServer "20000"
NtgrDumpLog  $NTGR_LOG_TERSE  "RADIUS-009.tcl" "Set accounting port to 20000 on DUT, 30000 on radius server, please use the name 'aaa' and password 'aaa' to logon on DUT manually by 802.1X client on Windows XP."
NtgrDumpLog  $NTGR_LOG_TERSE  "RADIUS-009.tcl" "Capture packtes on windows2003, if you succeed logon but no accounting response captured, press 'y' to continue; otherwise press any key."
set key [gets stdin]

if {$key == "Y" || $key == "y"} {
    set bRet [expr $bRet & 1]
} else {
    set bRet [expr $bRet & 0]
}

CALSetRadiusServerPort $ntgrDUT $ntgrAcctServerMode $ntgrRadiusServer "1813"
NtgrDumpLog  $NTGR_LOG_TERSE  "RADIUS-009.tcl" "Using accounting port to default, please use the name 'aaa' and password 'aaa' to logon on DUT manually by 802.1X client on Windows XP."
NtgrDumpLog  $NTGR_LOG_TERSE  "RADIUS-009.tcl" "Capture packtes on windows2003, if you succeed logon and captured accounting start/stop packets, press 'y' to continue; otherwise press any key."
set key [gets stdin]

if {$key == "Y" || $key == "y"} {
    set bRet [expr $bRet & 1]
} else {
    set bRet [expr $bRet & 0]
}


if {$bRet ==1} {
    set NtgrTestResult(RADIUS-009.tcl) "OK"
} else {
    set NtgrTestResult(RADIUS-009.tcl) "NG"
}

NtgrDumpLog  $NTGR_LOG_TERSE  "RADIUS-009.tcl" "******************** Complete Test case RADIUS-009.tcl ********************"
#*************************  End of Test case  **********************************