################################################################################
#
#  File Name    : RADIUS-014.tcl
#
#  Description  : This testcase used to test secret key between DUT and radius
#                 server: DUT with blank key and radius with non-blank key.
#
#  Revision     :
#        Date        Programmer        Description
#        2007/04/13  Scott             Newly created    
#
################################################################################

#*************************  Start of Test case  ********************************
NtgrDumpLog  $NTGR_LOG_TERSE  "RADIUS-014.tcl" "******************** Starting Test case RADIUS-014.tcl ********************"
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
CALSetRadiusServerKey $ntgrDUT $ntgrAuthServerMode $ntgrRadiusServer $ntgrSecretKey
CALSetRadiusServerKey $ntgrDUT $ntgrAcctServerMode $ntgrRadiusServer $ntgrSecretKey

NtgrDumpLog  $NTGR_LOG_TERSE  "RADIUS-014.tcl" "Complete configuration, please use the name 'aaa' and password 'aaa' to logon on DUT manually by 802.1X client on Windows XP."
NtgrDumpLog  $NTGR_LOG_TERSE  "RADIUS-014.tcl" "Capture packets on windows2003, if can't logon and no response packets from server were captured , press 'y' to continue; otherwise press any key."
set key [gets stdin]

set bRet 1
if {$key == "Y" || $key == "y"} {
    set bRet [expr $bRet & 1]
} else {
    set bRet [expr $bRet & 0]
}


if {$bRet ==1} {
    set NtgrTestResult(RADIUS-014.tcl) "OK"
} else {
    set NtgrTestResult(RADIUS-014.tcl) "NG"
}

NtgrDumpLog  $NTGR_LOG_TERSE  "RADIUS-014.tcl" "******************** Complete Test case RADIUS-014.tcl ********************"
#*************************  End of Test case  **********************************