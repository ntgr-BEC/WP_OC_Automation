################################################################################
#
#  File Name    : SNMP-007.tcl
#
#  Description  : This testcase used to test create/delete community and 
#                 get/set infor from DUT with it thru MIB Browser.
#
#  Revision     :
#        Date        Programmer        Description
#        2007/03/12  Scott             Newly created    
#
################################################################################

#*************************  Start of Test case  ********************************
NtgrDumpLog  $NTGR_LOG_TERSE  "SNMP-007.tcl" "******************** Starting Test case SNMP-007.tcl ********************"

CALResetConfiguration $ntgrDUT
CALSetSwitchMngrIPAddr $ntgrDUT
CALDisableConsoleTimeout $ntgrDUT

set bRet 1
CALConfigCommunity $ntgrDUT $ntgrComminity
CALSetCommunityReadWrite $ntgrDUT $ntgrComminity


NtgrDumpLog  $NTGR_LOG_TERSE  "SNMP-007.tcl" "Configuration complete, please manually get information with '$ntgrComminity' thru MIB Browser from PC."
NtgrDumpLog  $NTGR_LOG_TERSE  "SNMP-007.tcl" "Press 'y' if you got the basic infor with the community; otherwise, press other key."

set key [gets stdin]
if {$key == "y" || $key == "Y"} {
    set bRet [expr $bRet & 1]
} else {
    set bRet [expr $bRet & 0]
}


NtgrDumpLog  $NTGR_LOG_TERSE  "SNMP-007.tcl" "Please manually set system name with '$ntgrComminity' thru MIB Browser from PC."
NtgrDumpLog  $NTGR_LOG_TERSE  "SNMP-007.tcl" "Press 'y' if you set system name correctly with the community; otherwise, press other key."

set key [gets stdin]
if {$key == "y" || $key == "Y"} {
    set bRet [expr $bRet & 1]
} else {
    set bRet [expr $bRet & 0]
}


CALDeleteCommunity $ntgrDUT $ntgrComminity
NtgrDumpLog  $NTGR_LOG_TERSE  "SNMP-007.tcl" "Delete the community, please manually get/set information with '$ntgrComminity' thru MIB Browser from PC."
NtgrDumpLog  $NTGR_LOG_TERSE  "SNMP-007.tcl" "Press 'y' if you can not finish the operation with the community; otherwise, press other key."

set key [gets stdin]
if {$key == "y" || $key == "Y"} {
    set bRet [expr $bRet & 1]
} else {
    set bRet [expr $bRet & 0]
}


CALConfigCommunity $ntgrDUT $ntgrComminity
CALSetCommunityReadWrite $ntgrDUT $ntgrComminity
NtgrDumpLog  $NTGR_LOG_TERSE  "SNMP-007.tcl" "Create the community again, please manually get/set information with '$ntgrComminity' thru MIB Browser from PC."
NtgrDumpLog  $NTGR_LOG_TERSE  "SNMP-007.tcl" "Press 'y' if you can finish the operation with the community; otherwise, press other key."

set key [gets stdin]
if {$key == "y" || $key == "Y"} {
    set bRet [expr $bRet & 1]
} else {
    set bRet [expr $bRet & 0]
}

if {$bRet == 1} {
    set NtgrTestResult(SNMP-007.tcl) "OK"
} else {
    set NtgrTestResult(SNMP-007.tcl) "NG"
}

NtgrDumpLog  $NTGR_LOG_TERSE  "SNMP-007.tcl" "******************** Complete Test case SNMP-007.tcl ********************"
#*************************  End of Test case  **********************************