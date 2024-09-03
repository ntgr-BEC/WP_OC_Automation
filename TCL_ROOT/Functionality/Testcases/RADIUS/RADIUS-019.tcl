################################################################################
#
#  File Name    : RADIUS-019.tcl
#
#  Description  : This testcase used to test enable/disable message authenticator
#                 attribute for a authentication server.
#
#  Revision     :
#        Date        Programmer        Description
#        2007/04/13  Scott             Newly created    
#
################################################################################

#*************************  Start of Test case  ********************************
NtgrDumpLog  $NTGR_LOG_TERSE  "RADIUS-019.tcl" "******************** Starting Test case RADIUS-019.tcl ********************"
## Initialize the DUT
CALResetConfiguration $ntgrDUT
CALDisableConsoleTimeout $ntgrDUT
CALSetSwitchMngrIPAddr $ntgrDUT

## Create authentication login and configure it system login
CALAddAuthLoginList $ntgrDUT $ntgrLoginList "radius"
CALSetUserDefaultLogin $ntgrDUT $ntgrLoginList

## Configure authentication radius server
CALAddRadiusServer $ntgrDUT $ntgrAuthServerMode $ntgrRadiusServer
CALEnableRadiusMsgAuthAttr $ntgrDUT $ntgrRadiusServer

NtgrDumpLog  $NTGR_LOG_TERSE  "RADIUS-019.tcl" "Complete configuration, please set radius server with 'Request must contain the Message Authenticator attribute' and use the name 'aaa' and password 'aaa' to logon on DUT by Telnet 10.1.1.10."
NtgrDumpLog  $NTGR_LOG_TERSE  "RADIUS-019.tcl" "Capture packets on windows2003, if you succeed logon and there are 'Message Authenticator attribute' in captured packets, press 'y' to continue; otherwise press any key."
set key [gets stdin]
set bRet 1
if {$key == "Y" || $key == "y"} {
    set bRet [expr $bRet & 1]
} else {
    set bRet [expr $bRet & 0]
}

CALDisableRadiusMsgAuthAttr $ntgrDUT $ntgrRadiusServer
NtgrDumpLog  $NTGR_LOG_TERSE  "RADIUS-019.tcl" "Disable Message Authenticator attribute, please use the name 'aaa' and password 'aaa' to logon on DUT by Telnet 10.1.1.10 again."
NtgrDumpLog  $NTGR_LOG_TERSE  "RADIUS-019.tcl" "Capture packets on windows2003, if you can not logon and no 'Message Authenticator attribute' in captured packets, press 'y' to continue; otherwise press any key."
set key [gets stdin]

set bRet 1
if {$key == "Y" || $key == "y"} {
    set bRet [expr $bRet & 1]
} else {
    set bRet [expr $bRet & 0]
}


if {$bRet ==1} {
    set NtgrTestResult(RADIUS-019.tcl) "OK"
} else {
    set NtgrTestResult(RADIUS-019.tcl) "NG"
}

NtgrDumpLog  $NTGR_LOG_TERSE  "RADIUS-019.tcl" "******************** Complete Test case RADIUS-019.tcl ********************"
#*************************  End of Test case  **********************************