################################################################################
#
#  File Name    : DHCP-009.tcl
#
#  Description  : This testcase used to test the option code of DHCP service.
#
#  Revision     :
#        Date        Programmer        Description
#        2007/01/29  Scott             Newly created    
#
################################################################################

#*************************  Start of Test case  ********************************
NtgrDumpLog  $NTGR_LOG_TERSE  "DHCP-009.tcl" "******************** Starting Test case DHCP-009.tcl ********************"

CALResetConfiguration $ntgrDUT
CALSetSwitchMngrIPAddr $ntgrDUT
CALDisableConsoleTimeout $ntgrDUT

CALEnableDHCP $ntgrDUT
CALCreateDhcpAddrPool $ntgrDUT $ntgrPoolName

NtgrDumpLog  $NTGR_LOG_TERSE  "DHCP-009.tcl" "Configuration complete, please manually obtain address from PC and capture packets on PC."
NtgrDumpLog  $NTGR_LOG_TERSE  "DHCP-009.tcl" "If options with code 19 and 60 exist in captured packets, press 'y'; otherwise, press other key."

set bRet 1
set key [gets stdin]
if {$key == "y" || $key == "Y"} {
    set bRet [expr $bRet & 1]
} else {
    set bRet [expr $bRet & 0]
}

NtgrDumpLog  $NTGR_LOG_TERSE  "DHCP-009.tcl" "Add more options with code 69 and 70; release and renew address on PC again."
CALAddDhcpOption $ntgrDUT $ntgrPoolName $op_s3
CALAddDhcpOption $ntgrDUT $ntgrPoolName $op_s4

NtgrDumpLog  $NTGR_LOG_TERSE  "DHCP-009.tcl" "Press 'y' if options 19, 60, 69 and 70 are in captured packets; otherwise, press any other key."

set key [gets stdin]
if {$key == "y" || $key == "Y"} {
    set bRet [expr $bRet & 1]
} else {
    set bRet [expr $bRet & 0]
}

NtgrDumpLog  $NTGR_LOG_TERSE  "DHCP-009.tcl" "Delete all configured options; release and renew address on PC again."
CALDelDhcpOption $ntgrDUT $ntgrPoolName $op_1
CALDelDhcpOption $ntgrDUT $ntgrPoolName $op_2
CALDelDhcpOption $ntgrDUT $ntgrPoolName $op_3
CALDelDhcpOption $ntgrDUT $ntgrPoolName $op_4

NtgrDumpLog  $NTGR_LOG_TERSE  "DHCP-009.tcl" "Press 'y' if no options 19, 60, 69 and 70 in captured packets; otherwise, press any other key."

set key [gets stdin]
if {$key == "y" || $key == "Y"} {
    set bRet [expr $bRet & 1]
} else {
    set bRet [expr $bRet & 0]
}

if {$bRet == 1} {
    set NtgrTestResult(DHCP-009.tcl) "OK"
} else {
    set NtgrTestResult(DHCP-009.tcl) "NG"
}

NtgrDumpLog  $NTGR_LOG_TERSE  "DHCP-009.tcl" "******************** Complete Test case DHCP-009.tcl ********************"
#*************************  End of Test case  **********************************