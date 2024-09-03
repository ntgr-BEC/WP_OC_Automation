################################################################################
#
#  File Name    : PORT-025.tcl
#
#  Description  : This testcase used to test add/delete/modify LAG logical
#                 interface's description.
#
#  Revision     :
#        Date        Programmer        Description
#        2007/04/02  Scott             Newly created    
#
################################################################################

#*************************  Start of Test case  ********************************
NtgrDumpLog  $NTGR_LOG_TERSE  "PORT-025.tcl" "******************** Starting Test case PORT-025.tcl ********************"
CALResetConfiguration $ntgrDUT1
CALResetConfiguration $ntgrDUT2
CALCreatePortChannel "POCH111"
set lagif [CALGetLAGLogicalIF $ntgrDUT1 "POCH111"]

set bRet 1
## Configure ports' description
CALSetPortDescription $ntgrDUT1 $lagif $ntgrPortDescription(1)
set bRet [expr $bRet & [CALCheckExpect $ntgrDUT1 "$ntgrCmd$lagif" "\{$ntgrExpect$ntgrPortDescription(1)\}"]]
CALSetPortDescription $ntgrDUT1 $lagif $ntgrPortDescription(2)
set bRet [expr $bRet & [CALCheckExpect $ntgrDUT1 "$ntgrCmd$lagif" "\{$ntgrExpect$ntgrPortDescription(2)\}"]]


CALDeletePortChannel "POCH111"
CALCreatePortChannel "POCH111"
set lagif [CALGetLAGLogicalIF $ntgrDUT1 "POCH111"]
CALSetPortDescription $ntgrDUT1 $lagif $ntgrPortDescription(3)
set bRet [expr $bRet & [CALCheckExpect $ntgrDUT1 "$ntgrCmd$lagif" "\{$ntgrExpect$ntgrPortDescription(3)\}"]]
CALSetPortDescription $ntgrDUT1 $lagif $ntgrPortDescription(4)
set bRet [expr $bRet & [CALCheckExpect $ntgrDUT1 "$ntgrCmd$lagif" "\{$ntgrExpect$ntgrPortDescription(4)\}"]]


if {$bRet == 1} {
    set NtgrTestResult(PORT-025.tcl) "OK"
} else {
    set NtgrTestResult(PORT-025.tcl) "NG"
}

NtgrDumpLog  $NTGR_LOG_TERSE  "PORT-025.tcl" "******************** Complete Test case PORT-025.tcl ********************"
#*************************  End of Test case  **********************************