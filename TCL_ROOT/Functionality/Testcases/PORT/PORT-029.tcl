################################################################################
#
#  File Name    : PORT-029.tcl
#
#  Description  : This testcase used to test whether ports have correct statistics.
#
#  Revision     :
#        Date        Programmer        Description
#        2007/04/02  Scott             Newly created    
#
################################################################################

#*************************  Start of Test case  ********************************
NtgrDumpLog  $NTGR_LOG_TERSE  "PORT-029.tcl" "******************** Starting Test case PORT-029.tcl ********************"
foreach ntgrDUT $ntgrDUTList {
    CALResetConfiguration $ntgrDUT
    CALAllPortsLinkDown $ntgrDUT
    CALRoutingEnable $ntgrDUT
}

CALCreatePortChannel "POCH111"
CALCreateVlan "VLAN200"

foreach ntgrDUT $ntgrDUTList {
    set lagif($ntgrDUT)  [CALGetLAGLogicalIF $ntgrDUT "POCH111"]
    set vlanif($ntgrDUT) [CALGetLFofVLAN $ntgrDUT "VLAN200"]
}

set bRet 1
set bRet [expr $bRet & [CALCheckExpect $ntgrDUT1 "$ntgrCmd1" "$ntgrExpect1"]]

## Display statistics information
foreach ntgrDUT $ntgrDUTList {
    CALCheckExpect $ntgrDUT "$ntgrCmd2$ntgrPort($ntgrDUT)" ""
    CALCheckExpect $ntgrDUT "$ntgrCmd2$lagif($ntgrDUT)" ""
    CALCheckExpect $ntgrDUT "$ntgrCmd3$ntgrPort($ntgrDUT)" ""
    CALCheckExpect $ntgrDUT "$ntgrCmd3$lagif($ntgrDUT)" ""
    CALCheckExpect $ntgrDUT "$ntgrCmd4$vlanif($ntgrDUT)" ""
}

NtgrDumpLog  $NTGR_LOG_TERSE  "PORT-029.tcl" "Press 'y' if the statistics are correct, otherwise press any key"
set key [gets stdin]
if {$key == "Y" || $key == "y"} {
    set bRet [expr $bRet & 1]
} else {
    set bRet [expr $bRet & 0]
}


if {$bRet == 1} {
    set NtgrTestResult(PORT-029.tcl) "OK"
} else {
    set NtgrTestResult(PORT-029.tcl) "NG"
}

NtgrDumpLog  $NTGR_LOG_TERSE  "PORT-029.tcl" "******************** Complete Test case PORT-029.tcl ********************"
#*************************  End of Test case  **********************************