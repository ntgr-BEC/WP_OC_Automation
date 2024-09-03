################################################################################
#
#  File Name    : SNMP-008.tcl
#
#  Description  : This testcase used to test limitting client to access/configure
#                 DUT with community thru MIB Browser.
#
#  Revision     :
#        Date        Programmer        Description
#        2007/03/12  Scott             Newly created    
#
################################################################################

#*************************  Start of Test case  ********************************
NtgrDumpLog  $NTGR_LOG_TERSE  "SNMP-008.tcl" "******************** Starting Test case SNMP-008.tcl ********************"

CALResetConfiguration $ntgrDUT
CALSetSwitchMngrIPAddr $ntgrDUT
CALDisableConsoleTimeout $ntgrDUT

set bRet 1
CALConfigCommunity $ntgrDUT $ntgrComminity
CALSetCommunityReadWrite $ntgrDUT $ntgrComminity
CALLimitSnmpClient $ntgrDUT $ntgrComminity $ntgrAddr1 $ntgrMask1

NtgrDumpLog  $NTGR_LOG_TERSE  "SNMP-008.tcl" "Configuration complete, please manually get/set information with '$ntgrComminity' thru MIB Browser from PC."
NtgrDumpLog  $NTGR_LOG_TERSE  "SNMP-008.tcl" "Press 'y' if you can finish the operation; otherwise, press other key."

set key [gets stdin]
if {$key == "y" || $key == "Y"} {
    set bRet [expr $bRet & 1]
} else {
    set bRet [expr $bRet & 0]
}


CALLimitSnmpClient $ntgrDUT $ntgrComminity $ntgrAddr2 $ntgrMask1
NtgrDumpLog  $NTGR_LOG_TERSE  "SNMP-008.tcl" "Deny access/control DUT from PC thru SNMP MIB Browser."
NtgrDumpLog  $NTGR_LOG_TERSE  "SNMP-008.tcl" "Press 'y' if you can not finish the operation; otherwise, press other key."

set key [gets stdin]
if {$key == "y" || $key == "Y"} {
    set bRet [expr $bRet & 1]
} else {
    set bRet [expr $bRet & 0]
}


CALLimitSnmpClient $ntgrDUT $ntgrComminity $ntgrAddr1 $ntgrMask2
NtgrDumpLog  $NTGR_LOG_TERSE  "SNMP-008.tcl" "Permit a subnet including the PC and try to access/control DUT from PC thru SNMP MIB Browser."
NtgrDumpLog  $NTGR_LOG_TERSE  "SNMP-008.tcl" "Press 'y' if you can finish the operation; otherwise, press other key."

set key [gets stdin]
if {$key == "y" || $key == "Y"} {
    set bRet [expr $bRet & 1]
} else {
    set bRet [expr $bRet & 0]
}


CALLimitSnmpClient $ntgrDUT $ntgrComminity $ntgrAddr3 $ntgrMask2
NtgrDumpLog  $NTGR_LOG_TERSE  "SNMP-008.tcl" "Configure a permitted subnet not including the PC and try to access/control DUT from PC thru SNMP MIB Browser."
NtgrDumpLog  $NTGR_LOG_TERSE  "SNMP-008.tcl" "Press 'y' if you can not finish the operation; otherwise, press other key."

set key [gets stdin]
if {$key == "y" || $key == "Y"} {
    set bRet [expr $bRet & 1]
} else {
    set bRet [expr $bRet & 0]
}

if {$bRet == 1} {
    set NtgrTestResult(SNMP-008.tcl) "OK"
} else {
    set NtgrTestResult(SNMP-008.tcl) "NG"
}

NtgrDumpLog  $NTGR_LOG_TERSE  "SNMP-008.tcl" "******************** Complete Test case SNMP-008.tcl ********************"
#*************************  End of Test case  **********************************