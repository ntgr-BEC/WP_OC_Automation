################################################################################
#
#  File Name    : RADIUS-001.tcl
#
#  Description  : This testcase used to test a valid user could be authenticated 
#                 to access network resource.
#
#  Revision     :
#        Date        Programmer        Description
#        2007/04/13  Scott             Newly created    
#
################################################################################

#*************************  Start of Test case  ********************************
NtgrDumpLog  $NTGR_LOG_TERSE  "RADIUS-001.tcl" "******************** Starting Test case RADIUS-001.tcl ********************"
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

NtgrDumpLog  $NTGR_LOG_TERSE  "RADIUS-001.tcl" "Complete configuration, please use the name 'aaa' and password 'aaa' to logon on DUT manually by 802.1X client on Windows XP."
NtgrDumpLog  $NTGR_LOG_TERSE  "RADIUS-001.tcl" "If you succeed logon and ping 10.1.1.1 thru, press 'y' to continue; otherwise press any key."
set key [gets stdin]

if {$key == "Y" || $key == "y"} {
    set NtgrTestResult(RADIUS-001.tcl) "OK"
} else {
    set NtgrTestResult(RADIUS-001.tcl) "NG"
}

NtgrDumpLog  $NTGR_LOG_TERSE  "RADIUS-001.tcl" "******************** Complete Test case RADIUS-001.tcl ********************"
#*************************  End of Test case  **********************************