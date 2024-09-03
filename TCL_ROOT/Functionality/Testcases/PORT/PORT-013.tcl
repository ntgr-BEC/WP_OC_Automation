################################################################################
#
#  File Name    : PORT-013.tcl
#
#  Description  : This testcase used to test SFP module's negotiation while
#                 setting another side to 1000M full-duplex arbitrarily.
#
#  Revision     :
#        Date        Programmer        Description
#        2007/04/02  Scott             Newly created    
#
################################################################################

#*************************  Start of Test case  ********************************
NtgrDumpLog  $NTGR_LOG_TERSE  "PORT-013.tcl" "******************** Starting Test case PORT-013.tcl ********************"

## Initial configuration for each DUT
foreach ntgrDUT $ntgrDUTList {
    CALResetConfiguration $ntgrDUT
    CALSetSwitchMngrIPAddr $ntgrDUT
    CALDisableConsoleTimeout $ntgrDUT
}

## Setting each port's working mode
foreach {pt mode} [array get ntgrPortPhysicalMode_$ntgrDUT2] {
    CALAllPortsLinkDown $ntgrDUT2
    CALSetSpeed $ntgrDUT2 $pt [lindex $mode 0] [lindex $mode 1]
    CALPortLinkUp $ntgrDUT2 $pt
}

sleep 5
set bRet 1
set bRet [expr $bRet & [CALCheckExpect $ntgrDUT1 $ntgrCmd1 $ntgrExpect1]]
set bRet [expr $bRet & [CALCheckExpect $ntgrDUT2 $ntgrCmd2 $ntgrExpect2]]
set bRet [expr $bRet & [CALCheckExpect $ntgrDUT1 $ntgrCmd3 $ntgrExpect3]]



if {$bRet == 1} {
    set NtgrTestResult(PORT-013.tcl) "OK"
} else {
    set NtgrTestResult(PORT-013.tcl) "NG"
}

NtgrDumpLog  $NTGR_LOG_TERSE  "PORT-013.tcl" "******************** Complete Test case PORT-013.tcl ********************"
#*************************  End of Test case  **********************************