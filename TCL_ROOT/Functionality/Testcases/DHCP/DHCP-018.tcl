################################################################################
#
#  File Name    : DHCP-018.tcl
#
#  Description  : This testcase used to test conflict infor logging function.
#
#  Revision     :
#        Date        Programmer        Description
#        2007/01/29  Scott             Newly created    
#
################################################################################

#*************************  Start of Test case  ********************************
NtgrDumpLog  $NTGR_LOG_TERSE  "DHCP-018.tcl" "******************** Starting Test case DHCP-018.tcl ********************"

CALResetConfiguration $ntgrDUT
CALSetSwitchMngrIPAddr $ntgrDUT
CALDisableConsoleTimeout $ntgrDUT


CALEnableDHCP $ntgrDUT
CALCreateDhcpAddrPool $ntgrDUT $ntgrPoolName
CALDisableConflictLogging $ntgrDUT
NtgrDumpLog  $NTGR_LOG_TERSE  "DHCP-018.tcl" "Configuration complete, please manually obtain address from PC."
NtgrDumpLog  $NTGR_LOG_TERSE  "DHCP-018.tcl" "Press 'y' if PC obtaind the first IP; otherwise, press other key."

set bRet 1
set key [gets stdin]
if {$key == "y" || $key == "Y"} {
    set bRet [expr $bRet & 1]
} else {
    set bRet [expr $bRet & 0]
}

CALSetSwitchMngrIPAddr $ntgrDUT2 "$ntgrFirstIP 255.255.255.0"
NtgrDumpLog  $NTGR_LOG_TERSE  "DHCP-018.tcl" "Configure DUT2 with the IP PC obtained, please manually obtain address from PC again."
NtgrDumpLog  $NTGR_LOG_TERSE  "DHCP-018.tcl" "Press 'y' if PC obtaind the second IP and no conflict infor logged; otherwise, press other key."

set bRet 1
set key [gets stdin]
if {$key == "y" || $key == "Y"} {
    set bRet [expr $bRet & 1]
} else {
    set bRet [expr $bRet & 0]
}

CALEnableConflictLogging $ntgrDUT
CALSetSwitchMngrIPAddr $ntgrDUT2 "$ntgrSecondIP 255.255.255.0"
NtgrDumpLog  $NTGR_LOG_TERSE  "DHCP-018.tcl" "Configure DUT2 with the IP PC obtained and enable conflict logging, please manually obtain address from PC again."
NtgrDumpLog  $NTGR_LOG_TERSE  "DHCP-018.tcl" "Press 'y' if there are conflict infor logged; otherwise, press other key."

set bRet 1
set key [gets stdin]
if {$key == "y" || $key == "Y"} {
    set bRet [expr $bRet & 1]
} else {
    set bRet [expr $bRet & 0]
}


if {$bRet == 1} {
    set NtgrTestResult(DHCP-018.tcl) "OK"
} else {
    set NtgrTestResult(DHCP-018.tcl) "NG"
}

NtgrDumpLog  $NTGR_LOG_TERSE  "DHCP-018.tcl" "******************** Complete Test case DHCP-018.tcl ********************"
#*************************  End of Test case  **********************************