################################################################################
#
#  File Name    : PORT-026.tcl
#
#  Description  : This testcase used to test modifying physical port description.
#
#  Revision     :
#        Date        Programmer        Description
#        2007/04/02  Scott             Newly created    
#
################################################################################

#*************************  Start of Test case  ********************************
NtgrDumpLog  $NTGR_LOG_TERSE  "PORT-026.tcl" "******************** Starting Test case PORT-026.tcl ********************"


## Configure ports' description
set bRet 1
CALSetPortDescription $ntgrDUT $ntgrPort $ntgrPortDescription(1)
set bRet [expr $bRet & [CALCheckExpect $ntgrDUT "$ntgrCmd$ntgrPort" "\{$ntgrExpect$ntgrPortDescription(1)\}"]]
CALSetPortDescription $ntgrDUT $ntgrPort $ntgrPortDescription(2)
set bRet [expr $bRet & [CALCheckExpect $ntgrDUT "$ntgrCmd$ntgrPort" "\{$ntgrExpect$ntgrPortDescription(2)\}"]]
CALSetPortDescription $ntgrDUT $ntgrPort $ntgrPortDescription(3)
set bRet [expr $bRet & [CALCheckExpect $ntgrDUT "$ntgrCmd$ntgrPort" "\{$ntgrExpect$ntgrPortDescription(3)\}"]]
CALSetPortDescription $ntgrDUT $ntgrPort $ntgrPortDescription(4)
set bRet [expr $bRet & [CALCheckExpect $ntgrDUT "$ntgrCmd$ntgrPort" "\{$ntgrExpect$ntgrPortDescription(4)\}"]]
CALSetPortDescription $ntgrDUT $ntgrPort $ntgrPortDescription(5)
set bRet [expr $bRet & [CALCheckExpect $ntgrDUT "$ntgrCmd$ntgrPort" "\{$ntgrExpect$ntgrPortDescription(5)\}"]]



if {$bRet == 1} {
    set NtgrTestResult(PORT-026.tcl) "OK"
} else {
    set NtgrTestResult(PORT-026.tcl) "NG"
}

NtgrDumpLog  $NTGR_LOG_TERSE  "PORT-026.tcl" "******************** Complete Test case PORT-026.tcl ********************"
#*************************  End of Test case  **********************************