################################################################################
#
#  File Name    : PORT-014.tcl
#
#  Description  : This testcase used to test trying to set unsupported speed/mode
#                 such as 10/100 full/half duplex to the specified SFP module.
#
#  Revision     :
#        Date        Programmer        Description
#        2007/04/02  Scott             Newly created    
#
################################################################################

#*************************  Start of Test case  ********************************
NtgrDumpLog  $NTGR_LOG_TERSE  "PORT-014.tcl" "******************** Starting Test case PORT-014.tcl ********************"

## Initial configuration for each DUT
foreach ntgrDUT $ntgrDUTList {
    CALResetConfiguration $ntgrDUT
    CALSetSwitchMngrIPAddr $ntgrDUT
    CALDisableConsoleTimeout $ntgrDUT
}

## Setting each port's working mode
foreach ntgrDUT $ntgrDUTList {
    CALAllPortsLinkDown $ntgrDUT
    CALPortLinkUp $ntgrDUT $ntgrPort($ntgrDUT)
    foreach mode $ntgrUnsupportMode {
        CALSetSpeed $ntgrDUT $ntgrPort($ntgrDUT) [lindex $mode 0] [lindex $mode 1]
    }
}

sleep 5
set bRet 1
set bRet [expr $bRet & [CALCheckExpect $ntgrDUT1 $ntgrCmd1 $ntgrExpect1]]
set bRet [expr $bRet & [CALCheckExpect $ntgrDUT2 $ntgrCmd2 $ntgrExpect2]]
set bRet [expr $bRet & [CALCheckExpect $ntgrDUT1 $ntgrCmd3 $ntgrExpect3]]



if {$bRet == 1} {
    set NtgrTestResult(PORT-014.tcl) "OK"
} else {
    set NtgrTestResult(PORT-014.tcl) "NG"
}

NtgrDumpLog  $NTGR_LOG_TERSE  "PORT-014.tcl" "******************** Complete Test case PORT-014.tcl ********************"
#*************************  End of Test case  **********************************