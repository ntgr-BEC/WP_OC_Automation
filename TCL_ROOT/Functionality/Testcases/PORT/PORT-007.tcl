################################################################################
#
#  File Name    : PORT-007.tcl
#
#  Description  : This testcase used to test one side's port working at 10M
#                 full-duplex mode arbitrarily, another at auto-negotiate mode.
#
#  Revision     :
#        Date        Programmer        Description
#        2007/04/02  Scott             Newly created    
#
################################################################################

#*************************  Start of Test case  ********************************
NtgrDumpLog  $NTGR_LOG_TERSE  "PORT-007.tcl" "******************** Starting Test case PORT-007.tcl ********************"

## Initial configuration for each DUT
foreach ntgrDUT $ntgrDUTList {
    CALResetConfiguration $ntgrDUT
    CALSetSwitchMngrIPAddr $ntgrDUT
    CALDisableConsoleTimeout $ntgrDUT
}

## Setting each port's working mode
foreach {pt mode} [array get ntgrPortPhysicalMode_$ntgrDUT1] {
    CALSetSpeed $ntgrDUT1 $pt [lindex $mode 0] [lindex $mode 1]
    CALPortLinkDown $ntgrDUT1 $pt
    CALPortLinkUp $ntgrDUT1 $pt
}

sleep 5
set bRet 1
set bRet [expr $bRet & [CALCheckExpect $ntgrDUT1 $ntgrCmd1 $ntgrExpect1]]
set bRet [expr $bRet & [CALCheckExpect $ntgrDUT2 $ntgrCmd2 $ntgrExpect2]]
set bRet [expr $bRet & [CALCheckExpect $ntgrDUT1 $ntgrCmd3 $ntgrExpect3]]



if {$bRet == 1} {
    set NtgrTestResult(PORT-007.tcl) "OK"
} else {
    set NtgrTestResult(PORT-007.tcl) "NG"
}

NtgrDumpLog  $NTGR_LOG_TERSE  "PORT-007.tcl" "******************** Complete Test case PORT-007.tcl ********************"
#*************************  End of Test case  **********************************