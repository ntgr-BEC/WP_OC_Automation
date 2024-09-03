################################################################################
#
#  File Name    : RADIUS-023.tcl
#
#  Description  : This testcase used to reloading a DUT with radius configuration.
#
#  Revision     :
#        Date        Programmer        Description
#        2007/04/13  Scott             Newly created    
#
################################################################################

#*************************  Start of Test case  ********************************
NtgrDumpLog  $NTGR_LOG_TERSE  "RADIUS-023.tcl" "******************** Starting Test case RADIUS-023.tcl ********************"
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
CALAddRadiusServer $ntgrDUT $ntgrAuthServerMode $ntgrRadiusServerBackup
CALSetPrimaryRadiusServer $ntgrDUT $ntgrRadiusServer
CALDisableRadiusMsgAuthAttr $ntgrDUT $ntgrRadiusServer

CALEnableAccounting $ntgrDUT
CALAddRadiusServer $ntgrDUT $ntgrAcctServerMode $ntgrRadiusServer
CALSetRadiusServerKey $ntgrDUT $ntgrAuthServerMode $ntgrRadiusServer $ntgrSecretKey
CALSetRadiusServerKey $ntgrDUT $ntgrAcctServerMode $ntgrRadiusServer $ntgrSecretKey

CALSetRadiusTimeout $ntgrDUT "10"
CALSetRadiusRetransmitTime $ntgrDUT "8"

NtgrDumpLog  $NTGR_LOG_TERSE  "RADIUS-023.tcl" "Complete configuration, please use the name 'aaa' and password 'aaa' to logon on DUT manually by 802.1X client on Windows XP."
NtgrDumpLog  $NTGR_LOG_TERSE  "RADIUS-023.tcl" "If you succeed logon, press any key to continue; otherwise please check where goes wrong and correct it before continue."
gets stdin

CALRebootSwitch $ntgrDUT
sleep 120

NtgrDumpLog  $NTGR_LOG_TERSE  "RADIUS-023.tcl" "Generate script file and apply it again, please use the name 'aaa' and password 'aaa' to logon on DUT manually by 802.1X client on Windows XP."
NtgrDumpLog  $NTGR_LOG_TERSE  "RADIUS-023.tcl" "If you succeed logon, press 'y'; otherwise press any other key."
set key [gets stdin]

set bRet 1
if {$key == "Y" || $key == "y"} {
    set bRet [expr $bRet & 1]
} else {
    set bRet [expr $bRet & 0]
}


if {$bRet ==1} {
    set NtgrTestResult(RADIUS-023.tcl) "OK"
} else {
    set NtgrTestResult(RADIUS-023.tcl) "NG"
}

NtgrDumpLog  $NTGR_LOG_TERSE  "RADIUS-023.tcl" "******************** Complete Test case RADIUS-023.tcl ********************"
#*************************  End of Test case  **********************************