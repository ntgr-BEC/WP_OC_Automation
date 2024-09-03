################################################################################
#
#  File Name    : VLAN-039.tcl
#
#  Description  : This testcase used to test whether generate direct routes while
#                 enable ip routing.
#
#  Revision     :
#        Date        Programmer        Description
#        2006/11/16  Scott             Newly created    
#
################################################################################

#*************************  Start of Test case  ********************************
NtgrDumpLog  $NTGR_LOG_TERSE  "VLAN-039.tcl" "******************** Starting Test case VLAN-039.tcl ********************"
CALResetConfiguration $ntgrDut039
CALRoutingEnable $ntgrDut039

# Create VLANs
foreach vlan_index $ntgrVlanIndexList039  {
	CALCreateVlan $vlan_index
}

set bRet 1
set bRet [expr $bRet & [CALCheckExpect $ntgrDut039 $ntgrCmdList039(1) $ntgrExpect039(1)]]
CALRebootSwitch $ntgrDut039
sleep 180
set bRet [expr $bRet & [CALCheckExpect $ntgrDut039 $ntgrCmdList039(1) $ntgrExpect039(1)]]

if {$bRet == 1} {
    set NtgrTestResult(VLAN-039.tcl) "OK"
} else {
    set NtgrTestResult(VLAN-039.tcl) "NG"
}

NtgrDumpLog  $NTGR_LOG_TERSE  "VLAN-039.tcl" "******************** Complete Test case VLAN-039.tcl ********************"
#*************************  End of Test case  **********************************