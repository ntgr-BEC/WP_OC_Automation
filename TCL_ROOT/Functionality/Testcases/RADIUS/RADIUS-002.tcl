################################################################################
#
#  File Name    : RADIUS-002.tcl
#
#  Description  : This testcase used to test a invalid user should not be
#                 authenticated to access network resource.
#
#  Revision     :
#        Date        Programmer        Description
#        2007/04/13  Scott             Newly created    
#
################################################################################

#*************************  Start of Test case  ********************************
NtgrDumpLog  $NTGR_LOG_TERSE  "RADIUS-002.tcl" "******************** Starting Test case RADIUS-002.tcl ********************"
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
CALAddRadiusServer $ntgrDUT $ntgrRadiusServerMode $ntgrRadiusServer

NtgrDumpLog  $NTGR_LOG_TERSE  "RADIUS-002.tcl" "Complete configuration, please use the name 'aaab' and password 'aaa' or name 'aaa' with password 'aaabb' to logon on DUT manually by 802.1X client on Windows XP."
NtgrDumpLog  $NTGR_LOG_TERSE  "RADIUS-002.tcl" "If you could not succeed logon to access network resource, press 'y' to continue; otherwise press any key."
set key [gets stdin]

if {$key == "Y" || $key == "y"} {
    set NtgrTestResult(RADIUS-002.tcl) "OK"
} else {
    set NtgrTestResult(RADIUS-002.tcl) "NG"
}

NtgrDumpLog  $NTGR_LOG_TERSE  "RADIUS-002.tcl" "******************** Complete Test case RADIUS-002.tcl ********************"
#*************************  End of Test case  **********************************