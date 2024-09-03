################################################################################
#
#  File Name    : RADIUS-020.tcl
#
#  Description  : This testcase used to test modification of re-transmit interval
#                 time.
#
#  Revision     :
#        Date        Programmer        Description
#        2007/04/13  Scott             Newly created    
#
################################################################################

#*************************  Start of Test case  ********************************
NtgrDumpLog  $NTGR_LOG_TERSE  "RADIUS-020.tcl" "******************** Starting Test case RADIUS-020.tcl ********************"
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

## Note that the interval time you seen is not very precise
## Just compare the effect of modification
CALSetRadiusTimeout $ntgrDUT "15"
set bRet 1
set bRet [expr $bRet & [CALCheckExpect $ntgrDUT "show radius" "{Timeout Duration............................... 15}"]]

NtgrDumpLog  $NTGR_LOG_TERSE  "RADIUS-020.tcl" "Setting timeout to 15 seconds, please set radius server with 'Request must contain the Message Authenticator attribute' and use the name 'aaa' and password 'aaa' to logon on DUT by Telnet 10.1.1.10."
NtgrDumpLog  $NTGR_LOG_TERSE  "RADIUS-020.tcl" "Capture packets on windows2003, if you can not logon and access-requests were sent with interval about 15 seconds, press 'y' to continue; otherwise press any key."
set key [gets stdin]
set bRet 1
if {$key == "Y" || $key == "y"} {
    set bRet [expr $bRet & 1]
} else {
    set bRet [expr $bRet & 0]
}

CALSetRadiusTimeout $ntgrDUT "30"
set bRet [expr $bRet & [CALCheckExpect $ntgrDUT "show radius" "{Timeout Duration............................... 30}"]]
NtgrDumpLog  $NTGR_LOG_TERSE  "RADIUS-020.tcl" "Setting timeout to 30 seconds, please set radius server with 'Request must contain the Message Authenticator attribute' and use the name 'aaa' and password 'aaa' to logon on DUT by Telnet 10.1.1.10."
NtgrDumpLog  $NTGR_LOG_TERSE  "RADIUS-020.tcl" "If you can not logon and access-requests were sent with interval about 30 seconds, press 'y' to continue; otherwise press any key."
set key [gets stdin]
if {$key == "Y" || $key == "y"} {
    set bRet [expr $bRet & 1]
} else {
    set bRet [expr $bRet & 0]
}

CALResetRadiusTimeout $ntgrDUT
set bRet [expr $bRet & [CALCheckExpect $ntgrDUT "show radius" "{Timeout Duration............................... 5}"]]
NtgrDumpLog  $NTGR_LOG_TERSE  "RADIUS-020.tcl" "Setting timeout to default, please set radius server with 'Request must contain the Message Authenticator attribute' and use the name 'aaa' and password 'aaa' to logon on DUT by Telnet 10.1.1.10."
NtgrDumpLog  $NTGR_LOG_TERSE  "RADIUS-020.tcl" "If you can not logon and access-requests were sent with interval about 5 seconds, press 'y' to continue; otherwise press any key."
set key [gets stdin]
set bRet 1
if {$key == "Y" || $key == "y"} {
    set bRet [expr $bRet & 1]
} else {
    set bRet [expr $bRet & 0]
}



if {$bRet ==1} {
    set NtgrTestResult(RADIUS-020.tcl) "OK"
} else {
    set NtgrTestResult(RADIUS-020.tcl) "NG"
}

NtgrDumpLog  $NTGR_LOG_TERSE  "RADIUS-020.tcl" "******************** Complete Test case RADIUS-020.tcl ********************"
#*************************  End of Test case  **********************************