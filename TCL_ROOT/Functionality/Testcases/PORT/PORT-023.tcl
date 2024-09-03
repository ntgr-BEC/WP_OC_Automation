################################################################################
#
#  File Name    : PORT-023.tcl
#
#  Description  : This testcase used to test add/delete physical port description.
#
#  Revision     :
#        Date        Programmer        Description
#        2007/04/02  Scott             Newly created    
#
################################################################################

#*************************  Start of Test case  ********************************
NtgrDumpLog  $NTGR_LOG_TERSE  "PORT-023.tcl" "******************** Starting Test case PORT-023.tcl ********************"

## Configure ports' description
foreach {pt dscription} [array get ntgrPortDescription_$ntgrDUT] {
    CALSetPortDescription $ntgrDUT $pt $dscription
}

set bRet 1
foreach {pt dscription} [array get ntgrPortDescription_$ntgrDUT] {
    set bRet [expr $bRet & [CALCheckExpect $ntgrDUT "$ntgrCmd$pt" "\{$ntgrExpect$dscription\}"]]
}

## Delete ports' description
foreach {pt dscription} [array get ntgrPortDescription_$ntgrDUT] {
    CALSetPortDescription $ntgrDUT $pt
}

foreach {pt dscription} [array get ntgrPortDescription_$ntgrDUT] {
    set bRet [expr $bRet & [CALCheckExpect $ntgrDUT "$ntgrCmd$pt" "\{$ntgrExpect$dscription\}" 0]]
}


if {$bRet == 1} {
    set NtgrTestResult(PORT-023.tcl) "OK"
} else {
    set NtgrTestResult(PORT-023.tcl) "NG"
}

NtgrDumpLog  $NTGR_LOG_TERSE  "PORT-023.tcl" "******************** Complete Test case PORT-023.tcl ********************"
#*************************  End of Test case  **********************************