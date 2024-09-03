################################################################################
#
#  File Name    : PORT-020.tcl
#
#  Description  : This testcase used to test the ethernet encapsulation of ports.
#
#  Revision     :
#        Date        Programmer        Description
#        2007/04/02  Scott             Newly created    
#
################################################################################

#*************************  Start of Test case  ********************************
NtgrDumpLog  $NTGR_LOG_TERSE  "PORT-020.tcl" "******************** Starting Test case PORT-020.tcl ********************"

## Initial configuration for each DUT
foreach ntgrDUT $ntgrDUTList {
    CALResetConfiguration $ntgrDUT
    CALSetSwitchMngrIPAddr $ntgrDUT
    CALDisableConsoleTimeout $ntgrDUT
    CALAllPortsLinkDown $ntgrDUT
    CALPortLinkUp $ntgrDUT $ntgrPort($ntgrDUT)
}

sleep 5
set bRet 1
set bRet [expr $bRet & [CALCheckExpect $ntgrDUT1 $ntgrCmd1 $ntgrExpect1]]
set bRet [expr $bRet & [CALCheckExpect $ntgrDUT2 $ntgrCmd2 $ntgrExpect2]]
set bRet [expr $bRet & [CALCheckExpect $ntgrDUT1 $ntgrCmd3 $ntgrExpect3]]


foreach ntgrDUT $ntgrDUTList {
    CALSetPortEncapsulation $ntgrDUT $ntgrPort($ntgrDUT) "ethernet"
}
set bRet [expr $bRet & [CALCheckExpect $ntgrDUT1 $ntgrCmd1 $ntgrExpect1]]
set bRet [expr $bRet & [CALCheckExpect $ntgrDUT2 $ntgrCmd2 $ntgrExpect2]]
set bRet [expr $bRet & [CALCheckExpect $ntgrDUT1 $ntgrCmd3 $ntgrExpect3]]


foreach ntgrDUT $ntgrDUTList {
    CALSetPortEncapsulation $ntgrDUT $ntgrPort($ntgrDUT) "snap"
}
set bRet [expr $bRet & [CALCheckExpect $ntgrDUT1 $ntgrCmd1 $ntgrExpect1 0]]
set bRet [expr $bRet & [CALCheckExpect $ntgrDUT2 $ntgrCmd2 $ntgrExpect2 0]]
set bRet [expr $bRet & [CALCheckExpect $ntgrDUT1 $ntgrCmd3 $ntgrExpect3]]


foreach ntgrDUT $ntgrDUTList {
    CALSetPortEncapsulation $ntgrDUT $ntgrPort($ntgrDUT) "ethernet"
}
set bRet [expr $bRet & [CALCheckExpect $ntgrDUT1 $ntgrCmd1 $ntgrExpect1]]
set bRet [expr $bRet & [CALCheckExpect $ntgrDUT2 $ntgrCmd2 $ntgrExpect2]]
set bRet [expr $bRet & [CALCheckExpect $ntgrDUT1 $ntgrCmd3 $ntgrExpect3]]


if {$bRet == 1} {
    set NtgrTestResult(PORT-020.tcl) "OK"
} else {
    set NtgrTestResult(PORT-020.tcl) "NG"
}

NtgrDumpLog  $NTGR_LOG_TERSE  "PORT-020.tcl" "******************** Complete Test case PORT-020.tcl ********************"
#*************************  End of Test case  **********************************