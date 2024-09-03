################################################################################
#
#  File Name    : RADIUS-005.tcl
#
#  Description  : This testcase used to test DUT should select another
#                 reachable server while the primary server is not reachable.
#
#  Revision     :
#        Date        Programmer        Description
#        2007/04/13  Scott             Newly created    
#
################################################################################

#*************************  Start of Test case  ********************************
NtgrDumpLog  $NTGR_LOG_TERSE  "RADIUS-005.tcl" "******************** Starting Test case RADIUS-005.tcl ********************"
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

## In this case, there are 10.1.1.1/2/3 on windows 2003
NtgrDumpLog  $NTGR_LOG_TERSE  "RADIUS-005.tcl" "Complete configuration, please delete the IP $ntgrPrimaryRadiusServer on windows 2003 and use the name 'aaa' and password 'aaa' to logon on DUT manually by 802.1X client on Windows XP."
NtgrDumpLog  $NTGR_LOG_TERSE  "RADIUS-005.tcl" "Capture packets on Windows 2003, if you could succeed logon and radius packets were sent to another IP, press 'y' to continue; otherwise press any key."
set key [gets stdin]

if {$key == "Y" || $key == "y"} {
    set NtgrTestResult(RADIUS-005.tcl) "OK"
} else {
    set NtgrTestResult(RADIUS-005.tcl) "NG"
}

NtgrDumpLog  $NTGR_LOG_TERSE  "RADIUS-005.tcl" "******************** Complete Test case RADIUS-005.tcl ********************"
#*************************  End of Test case  **********************************