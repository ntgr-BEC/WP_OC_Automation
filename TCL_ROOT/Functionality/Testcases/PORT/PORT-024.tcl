################################################################################
#
#  File Name    : PORT-024.tcl
#
#  Description  : This testcase used to test add/delete/modify VLAN logical
#                 interface's description.
#
#  Revision     :
#        Date        Programmer        Description
#        2007/04/02  Scott             Newly created    
#
################################################################################

#*************************  Start of Test case  ********************************
NtgrDumpLog  $NTGR_LOG_TERSE  "PORT-024.tcl" "******************** Starting Test case PORT-024.tcl ********************"
CALResetConfiguration $ntgrDUT
CALCreateVlan "VLAN200"
set vlanif [CALGetLFofVLAN $ntgrDUT "VLAN200"]

set bRet 1
## Configure ports' description
CALSetPortDescription $ntgrDUT $vlanif $ntgrPortDescription(1)
set bRet [expr $bRet & [CALCheckExpect $ntgrDUT "$ntgrCmd$vlanif" "\{$ntgrExpect$ntgrPortDescription(1)\}"]]
CALSetPortDescription $ntgrDUT $vlanif $ntgrPortDescription(2)
set bRet [expr $bRet & [CALCheckExpect $ntgrDUT "$ntgrCmd$vlanif" "\{$ntgrExpect$ntgrPortDescription(2)\}"]]


CALDeleteVlan "VLAN200"
CALCreateVlan "VLAN200"
set vlanif [CALGetLFofVLAN $ntgrDUT "VLAN200"]
CALSetPortDescription $ntgrDUT $vlanif $ntgrPortDescription(3)
set bRet [expr $bRet & [CALCheckExpect $ntgrDUT "$ntgrCmd$vlanif" "\{$ntgrExpect$ntgrPortDescription(3)\}"]]
CALSetPortDescription $ntgrDUT $vlanif $ntgrPortDescription(4)
set bRet [expr $bRet & [CALCheckExpect $ntgrDUT "$ntgrCmd$vlanif" "\{$ntgrExpect$ntgrPortDescription(4)\}"]]


if {$bRet == 1} {
    set NtgrTestResult(PORT-024.tcl) "OK"
} else {
    set NtgrTestResult(PORT-024.tcl) "NG"
}

NtgrDumpLog  $NTGR_LOG_TERSE  "PORT-024.tcl" "******************** Complete Test case PORT-024.tcl ********************"
#*************************  End of Test case  **********************************