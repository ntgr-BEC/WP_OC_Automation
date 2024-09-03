################################################################################
#
#  File Name    : PORT-019.tcl
#
#  Description  : This testcase used to test shutdown/no shutdown the 10G modules.
#
#  Revision     :
#        Date        Programmer        Description
#        2007/04/02  Scott             Newly created    
#
################################################################################

#*************************  Start of Test case  ********************************
NtgrDumpLog  $NTGR_LOG_TERSE  "PORT-019.tcl" "******************** Starting Test case PORT-019.tcl ********************"

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
    CALPortLinkDown $ntgrDUT $ntgrPort($ntgrDUT)
    CALPortLinkUp $ntgrDUT $ntgrPort($ntgrDUT)
}
set bRet [expr $bRet & [CALCheckExpect $ntgrDUT1 $ntgrCmd1 $ntgrExpect1]]
set bRet [expr $bRet & [CALCheckExpect $ntgrDUT2 $ntgrCmd2 $ntgrExpect2]]
set bRet [expr $bRet & [CALCheckExpect $ntgrDUT1 $ntgrCmd3 $ntgrExpect3]]


## Setting each port's working mode
foreach ntgrDUT $ntgrDUTList {
    CALSetSpeed $ntgrDUT $ntgrPort($ntgrDUT) [lindex $ntgrSfpMode 0] [lindex $ntgrSfpMode 1]
}
set bRet [expr $bRet & [CALCheckExpect $ntgrDUT1 $ntgrCmd1 $ntgrExpect1]]
set bRet [expr $bRet & [CALCheckExpect $ntgrDUT2 $ntgrCmd2 $ntgrExpect2]]
set bRet [expr $bRet & [CALCheckExpect $ntgrDUT1 $ntgrCmd3 $ntgrExpect3]]


foreach ntgrDUT $ntgrDUTList {
    CALPortLinkDown $ntgrDUT $ntgrPort($ntgrDUT)
    CALPortLinkUp $ntgrDUT $ntgrPort($ntgrDUT)
}
set bRet [expr $bRet & [CALCheckExpect $ntgrDUT1 $ntgrCmd1 $ntgrExpect1]]
set bRet [expr $bRet & [CALCheckExpect $ntgrDUT2 $ntgrCmd2 $ntgrExpect2]]
set bRet [expr $bRet & [CALCheckExpect $ntgrDUT1 $ntgrCmd3 $ntgrExpect3]]


if {$bRet == 1} {
    set NtgrTestResult(PORT-019.tcl) "OK"
} else {
    set NtgrTestResult(PORT-019.tcl) "NG"
}

NtgrDumpLog  $NTGR_LOG_TERSE  "PORT-019.tcl" "******************** Complete Test case PORT-019.tcl ********************"
#*************************  End of Test case  **********************************