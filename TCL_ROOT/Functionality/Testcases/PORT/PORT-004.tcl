################################################################################
#
#  File Name    : PORT-004.tcl
#
#  Description  : This testcase used to test both sides' ports working at
#                 100M full-duplex mode arbitrarily.
#
#  Revision     :
#        Date        Programmer        Description
#        2007/04/02  Scott             Newly created    
#
################################################################################

#*************************  Start of Test case  ********************************
NtgrDumpLog  $NTGR_LOG_TERSE  "PORT-004.tcl" "******************** Starting Test case PORT-004.tcl ********************"

## Initial configuration for each DUT
foreach ntgrDUT $ntgrDUTList {
    CALResetConfiguration $ntgrDUT
    CALSetSwitchMngrIPAddr $ntgrDUT
    CALDisableConsoleTimeout $ntgrDUT
}

## Setting each port's working mode
foreach ntgrDUT $ntgrDUTList {
    foreach {pt mode} [array get ntgrPortPhysicalMode_$ntgrDUT] {
        CALSetSpeed $ntgrDUT $pt [lindex $mode 0] [lindex $mode 1]
        CALPortLinkDown $ntgrDUT $pt
        CALPortLinkUp $ntgrDUT $pt
    }
}

sleep 5
set bRet 1
set bRet [expr $bRet & [CALCheckExpect $ntgrDUT1 $ntgrCmd1 $ntgrExpect1]]
set bRet [expr $bRet & [CALCheckExpect $ntgrDUT2 $ntgrCmd2 $ntgrExpect2]]
set bRet [expr $bRet & [CALCheckExpect $ntgrDUT1 $ntgrCmd3 $ntgrExpect3]]



if {$bRet == 1} {
    set NtgrTestResult(PORT-004.tcl) "OK"
} else {
    set NtgrTestResult(PORT-004.tcl) "NG"
}

NtgrDumpLog  $NTGR_LOG_TERSE  "PORT-004.tcl" "******************** Complete Test case PORT-004.tcl ********************"
#*************************  End of Test case  **********************************