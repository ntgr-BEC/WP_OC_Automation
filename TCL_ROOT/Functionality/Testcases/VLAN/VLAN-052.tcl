################################################################################
#
#  File Name    : VLAN-052.tcl
#
#  Description  : This testcase used to test interoperability between VLAN and LAG.
#
#  Revision     :
#        Date        Programmer        Description
#        2006/11/21  Scott             Newly created    
#
################################################################################

#*************************  Start of Test case  ********************************
NtgrDumpLog  $NTGR_LOG_TERSE  "VLAN-052.tcl" "******************** Starting Test case VLAN-052.tcl ********************"
CALResetConfiguration $ntgrDUT

CALCreatePortChannel $ntgrChannelName
CALCreateVlan "$ntgrVlanIndex"

set bRet 1
set bRet [expr $bRet & [CALCheckExpect $ntgrDUT $ntgrCmdList052(1) $ntgrExpect052(1)]]
CALDeletePortChannel $ntgrChannelName
set bRet [expr $bRet & [CALCheckExpect $ntgrDUT $ntgrCmdList052(1) $ntgrExpect052(1) 0]]
CALRebootSwitch $ntgrDUT
sleep 180
set bRet [expr $bRet & [CALCheckExpect $ntgrDUT $ntgrCmdList052(1) $ntgrExpect052(1) 0]]

if {$bRet == 1} {
    set NtgrTestResult(VLAN-052.tcl) "OK"
} else {
    set NtgrTestResult(VLAN-052.tcl) "NG"
}

NtgrDumpLog  $NTGR_LOG_TERSE  "VLAN-052.tcl" "******************** Complete Test case VLAN-052.tcl ********************"
#*************************  End of Test case  **********************************