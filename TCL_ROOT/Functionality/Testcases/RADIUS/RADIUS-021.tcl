################################################################################
#
#  File Name    : RADIUS-021.tcl
#
#  Description  : This testcase used to test modification of how many times
#                 re-transmit should be retried.
#
#  Revision     :
#        Date        Programmer        Description
#        2007/04/13  Scott             Newly created    
#
################################################################################

#*************************  Start of Test case  ********************************
NtgrDumpLog  $NTGR_LOG_TERSE  "RADIUS-021.tcl" "******************** Starting Test case RADIUS-021.tcl ********************"
## Initialize the DUT
CALResetConfiguration $ntgrDUT
CALDisableConsoleTimeout $ntgrDUT
CALSetSwitchMngrIPAddr $ntgrDUT

## Create authentication login and configure it system login
CALAddAuthLoginList $ntgrDUT $ntgrLoginList "radius"
CALSetUserDefaultLogin $ntgrDUT $ntgrLoginList

## Configure authentication radius server
CALAddRadiusServer $ntgrDUT $ntgrAuthServerMode $ntgrRadiusServer
CALDisableRadiusMsgAuthAttr $ntgrDUT $ntgrRadiusServer

## Note that the total requests you seen including the first request
CALSetRadiusRetransmitTime $ntgrDUT "1"
set bRet 1
set bRet [expr $bRet & [CALCheckExpect $ntgrDUT "show radius" "{Number of Retransmits.......................... 1}"]]

NtgrDumpLog  $NTGR_LOG_TERSE  "RADIUS-021.tcl" "Setting retried time to 1 seconds, please set radius server with 'Request must contain the Message Authenticator attribute' and use the name 'aaa' and password 'aaa' to logon on DUT by Telnet 10.1.1.10."
NtgrDumpLog  $NTGR_LOG_TERSE  "RADIUS-021.tcl" "Capture packets on windows2003, if you can not logon and 2 requests were captured, press 'y' to continue; otherwise press any key."
set key [gets stdin]
set bRet 1
if {$key == "Y" || $key == "y"} {
    set bRet [expr $bRet & 1]
} else {
    set bRet [expr $bRet & 0]
}

CALSetRadiusRetransmitTime $ntgrDUT "15"
set bRet [expr $bRet & [CALCheckExpect $ntgrDUT "show radius" "{Number of Retransmits.......................... 15}"]]
NtgrDumpLog  $NTGR_LOG_TERSE  "RADIUS-021.tcl" "Setting retried time to 15 seconds, please set radius server with 'Request must contain the Message Authenticator attribute' and use the name 'aaa' and password 'aaa' to logon on DUT by Telnet 10.1.1.10."
NtgrDumpLog  $NTGR_LOG_TERSE  "RADIUS-021.tcl" "Capture packets on windows2003, if you can not logon and 16 requests were captured, press 'y' to continue; otherwise press any key."
set key [gets stdin]
if {$key == "Y" || $key == "y"} {
    set bRet [expr $bRet & 1]
} else {
    set bRet [expr $bRet & 0]
}

CALResetRadiusRetransmitTime $ntgrDUT
set bRet [expr $bRet & [CALCheckExpect $ntgrDUT "show radius" "{Number of Retransmits.......................... 4}"]]
NtgrDumpLog  $NTGR_LOG_TERSE  "RADIUS-021.tcl" "Setting retried time to default 4, please set radius server with 'Request must contain the Message Authenticator attribute' and use the name 'aaa' and password 'aaa' to logon on DUT by Telnet 10.1.1.10."
NtgrDumpLog  $NTGR_LOG_TERSE  "RADIUS-021.tcl" "Capture packets on windows2003, if you can not logon and 5 requests were captured, press 'y' to continue; otherwise press any key."
set key [gets stdin]
set bRet 1
if {$key == "Y" || $key == "y"} {
    set bRet [expr $bRet & 1]
} else {
    set bRet [expr $bRet & 0]
}



if {$bRet ==1} {
    set NtgrTestResult(RADIUS-021.tcl) "OK"
} else {
    set NtgrTestResult(RADIUS-021.tcl) "NG"
}

NtgrDumpLog  $NTGR_LOG_TERSE  "RADIUS-021.tcl" "******************** Complete Test case RADIUS-021.tcl ********************"
#*************************  End of Test case  **********************************