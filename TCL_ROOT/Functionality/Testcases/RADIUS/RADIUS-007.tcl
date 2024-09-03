################################################################################
#
#  File Name    : RADIUS-007.tcl
#
#  Description  : This testcase used to test DUT should select another
#                 reachable server while the primary server is deleted.
#
#  Revision     :
#        Date        Programmer        Description
#        2007/04/13  Scott             Newly created    
#
################################################################################

#*************************  Start of Test case  ********************************
NtgrDumpLog  $NTGR_LOG_TERSE  "RADIUS-007.tcl" "******************** Starting Test case RADIUS-007.tcl ********************"
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
CALAddRadiusServer $ntgrDUT $ntgrRadiusServerMode $ntgrRadiusServer1
CALAddRadiusServer $ntgrDUT $ntgrRadiusServerMode $ntgrRadiusServer2
CALAddRadiusServer $ntgrDUT $ntgrRadiusServerMode $ntgrRadiusServer3
CALSetPrimaryRadiusServer $ntgrDUT $ntgrPrimaryRadiusServer
CALDelRadiusServer $ntgrDUT $ntgrRadiusServerMode $ntgrPrimaryRadiusServer

## In this case, there are 10.1.1.1/2/3 on windows 2003
NtgrDumpLog  $NTGR_LOG_TERSE  "RADIUS-007.tcl" "Complete configuration, use the name 'aaa' and password 'aaa' to logon on DUT manually by 802.1X client on Windows XP."
NtgrDumpLog  $NTGR_LOG_TERSE  "RADIUS-007.tcl" "Capture packets on Windows 2003, if you could succeed logon and radius packets were sent to another IP, press 'y' to continue; otherwise press any key."
set key [gets stdin]

if {$key == "Y" || $key == "y"} {
    set NtgrTestResult(RADIUS-007.tcl) "OK"
} else {
    set NtgrTestResult(RADIUS-007.tcl) "NG"
}

NtgrDumpLog  $NTGR_LOG_TERSE  "RADIUS-007.tcl" "******************** Complete Test case RADIUS-007.tcl ********************"
#*************************  End of Test case  **********************************