################################################################################
#
#  File Name    : PORT-030.tcl
#
#  Description  : This testcase used to test clearing the ports statistics.
#
#  Revision     :
#        Date        Programmer        Description
#        2007/04/02  Scott             Newly created    
#
################################################################################

#*************************  Start of Test case  ********************************
NtgrDumpLog  $NTGR_LOG_TERSE  "PORT-030.tcl" "******************** Starting Test case PORT-030.tcl ********************"
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

foreach ntgrDUT $ntgrDUTList {
    CALCheckExpect $ntgrDUT "$ntgrCmd5$ntgrPort($ntgrDUT)\ny" ""
    set bRet [expr $bRet & [CALCheckExpect $ntgrDUT "$ntgrCmd2$ntgrPort($ntgrDUT)" "$ntgrExpect2"]]
    CALCheckExpect $ntgrDUT "$ntgrCmd5$lagif($ntgrDUT)\ny" ""
    set bRet [expr $bRet & [CALCheckExpect $ntgrDUT "$ntgrCmd2$lagif($ntgrDUT)" "$ntgrExpect2"]]
    CALCheckExpect $ntgrDUT "$ntgrCmd5$ntgrPort($ntgrDUT)\ny" ""
    set bRet [expr $bRet & [CALCheckExpect $ntgrDUT "$ntgrCmd3$ntgrPort($ntgrDUT)" "$ntgrExpect3"]]
    CALCheckExpect $ntgrDUT "$ntgrCmd5$lagif($ntgrDUT)\ny" ""
    set bRet [expr $bRet & [CALCheckExpect $ntgrDUT "$ntgrCmd3$lagif($ntgrDUT)" "$ntgrExpect3"]]
}



if {$bRet == 1} {
    set NtgrTestResult(PORT-030.tcl) "OK"
} else {
    set NtgrTestResult(PORT-030.tcl) "NG"
}

NtgrDumpLog  $NTGR_LOG_TERSE  "PORT-030.tcl" "******************** Complete Test case PORT-030.tcl ********************"
#*************************  End of Test case  **********************************