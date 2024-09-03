################################################################################
#
#  File Name    : RADIUS-006.tcl
#
#  Description  : This testcase used to test using non-default port of authentication.
#
#  Revision     :
#        Date        Programmer        Description
#        2007/04/13  Scott             Newly created    
#
################################################################################

#*************************  Start of Test case  ********************************
NtgrDumpLog  $NTGR_LOG_TERSE  "RADIUS-006.tcl" "******************** Starting Test case RADIUS-006.tcl ********************"
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
CALAddRadiusServer $ntgrDUT $ntgrRadiusServerMode $ntgrRadiusServer
CALSetRadiusServerPort $ntgrDUT $ntgrRadiusServerMode $ntgrRadiusServer "10000"

NtgrDumpLog  $NTGR_LOG_TERSE  "RADIUS-006.tcl" "Using authentication port 10000, please use the name 'aaa' and password 'aaa' to logon on DUT manually by 802.1X client on Windows XP."
NtgrDumpLog  $NTGR_LOG_TERSE  "RADIUS-006.tcl" "If you succeed logon and ping 10.1.1.1 thru, press 'y' to continue; otherwise press any key."
set key [gets stdin]

if {$key == "Y" || $key == "y"} {
    set bRet [expr $bRet & 1]
} else {
    set bRet [expr $bRet & 0]
}

CALSetRadiusServerPort $ntgrDUT $ntgrRadiusServerMode $ntgrRadiusServer "50000"
NtgrDumpLog  $NTGR_LOG_TERSE  "RADIUS-006.tcl" "Using authentication port 50000, please use the name 'aaa' and password 'aaa' to logon on DUT manually by 802.1X client on Windows XP."
NtgrDumpLog  $NTGR_LOG_TERSE  "RADIUS-006.tcl" "If you succeed logon and ping 10.1.1.1 thru, press 'y' to continue; otherwise press any key."
set key [gets stdin]

if {$key == "Y" || $key == "y"} {
    set bRet [expr $bRet & 1]
} else {
    set bRet [expr $bRet & 0]
}

CALSetRadiusServerPort $ntgrDUT $ntgrRadiusServerMode $ntgrRadiusServer "20000"
NtgrDumpLog  $NTGR_LOG_TERSE  "RADIUS-006.tcl" "Using authentication port to 20000 on DUT, 30000 on radius, please use the name 'aaa' and password 'aaa' to logon on DUT manually by 802.1X client on Windows XP."
NtgrDumpLog  $NTGR_LOG_TERSE  "RADIUS-006.tcl" "If you could not succeed logon, press 'y' to continue; otherwise press any key."
set key [gets stdin]

if {$key == "Y" || $key == "y"} {
    set bRet [expr $bRet & 1]
} else {
    set bRet [expr $bRet & 0]
}

CALSetRadiusServerPort $ntgrDUT $ntgrRadiusServerMode $ntgrRadiusServer "1812"
NtgrDumpLog  $NTGR_LOG_TERSE  "RADIUS-006.tcl" "Using authentication port to default 1812, please use the name 'aaa' and password 'aaa' to logon on DUT manually by 802.1X client on Windows XP."
NtgrDumpLog  $NTGR_LOG_TERSE  "RADIUS-006.tcl" "If you could not succeed logon, press 'y' to continue; otherwise press any key."
set key [gets stdin]

if {$key == "Y" || $key == "y"} {
    set bRet [expr $bRet & 1]
} else {
    set bRet [expr $bRet & 0]
}



if {$bRet ==1} {
    set NtgrTestResult(RADIUS-006.tcl) "OK"
} else {
    set NtgrTestResult(RADIUS-006.tcl) "NG"
}

NtgrDumpLog  $NTGR_LOG_TERSE  "RADIUS-006.tcl" "******************** Complete Test case RADIUS-006.tcl ********************"
#*************************  End of Test case  **********************************