################################################################################
#
#  File Name    : SNMP-010.tcl
#
#  Description  : This testcase used to test enable/disable/re-enable community
#                 while walking MIB infor from DUT with it thru MIB Browser.
#
#  Revision     :
#        Date        Programmer        Description
#        2007/03/12  Scott             Newly created    
#
################################################################################

#*************************  Start of Test case  ********************************
NtgrDumpLog  $NTGR_LOG_TERSE  "SNMP-010.tcl" "******************** Starting Test case SNMP-010.tcl ********************"

CALResetConfiguration $ntgrDUT
CALSetSwitchMngrIPAddr $ntgrDUT
CALDisableConsoleTimeout $ntgrDUT

set bRet 1
CALConfigCommunity $ntgrDUT $ntgrComminity


NtgrDumpLog  $NTGR_LOG_TERSE  "SNMP-010.tcl" "Configuration complete, please manually walk information with '$ntgrComminity' thru MIB Browser from PC."
NtgrDumpLog  $NTGR_LOG_TERSE  "SNMP-010.tcl" "Please ensure the walking will be continued several minutes, press any key when you start walking MIB informations."

gets stdin


CALDisableCommunity $ntgrDUT $ntgrComminity
NtgrDumpLog  $NTGR_LOG_TERSE  "SNMP-010.tcl" "Disable the community, press 'y' if walking process paused; otherwise, press other key."

set key [gets stdin]
if {$key == "y" || $key == "Y"} {
    set bRet [expr $bRet & 1]
} else {
    set bRet [expr $bRet & 0]
}


CALEnableCommunity $ntgrDUT $ntgrComminity
NtgrDumpLog  $NTGR_LOG_TERSE  "SNMP-010.tcl" "Enable the community again, press 'y' if walking process continued again; otherwise, press other key."

set key [gets stdin]
if {$key == "y" || $key == "Y"} {
    set bRet [expr $bRet & 1]
} else {
    set bRet [expr $bRet & 0]
}

if {$bRet == 1} {
    set NtgrTestResult(SNMP-010.tcl) "OK"
} else {
    set NtgrTestResult(SNMP-010.tcl) "NG"
}

NtgrDumpLog  $NTGR_LOG_TERSE  "SNMP-010.tcl" "******************** Complete Test case SNMP-010.tcl ********************"
#*************************  End of Test case  **********************************