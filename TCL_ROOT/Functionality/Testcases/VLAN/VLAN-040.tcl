################################################################################
#
#  File Name    : VLAN-040.tcl
#
#  Description  : This testcase used to test whether delete direct routes while
#                 disable ip routing.
#
#  Revision     :
#        Date        Programmer        Description
#        2006/11/16  Scott             Newly created    
#
################################################################################

#*************************  Start of Test case  ********************************
NtgrDumpLog  $NTGR_LOG_TERSE  "VLAN-040.tcl" "******************** Starting Test case VLAN-040.tcl ********************"
CALResetConfiguration $ntgrDut040

# Create VLANs
foreach vlan_index $ntgrVlanIndexList040  {
	CALCreateVlan $vlan_index
}

set bRet 1
set bRet [expr $bRet & [CALCheckExpect $ntgrDut040 $ntgrCmdList040(1) $ntgrExpect040(1) 0]]
CALRebootSwitch $ntgrDut040
sleep 180
set bRet [expr $bRet & [CALCheckExpect $ntgrDut040 $ntgrCmdList040(1) $ntgrExpect040(1) 0]]

if {$bRet == 1} {
    set NtgrTestResult(VLAN-040.tcl) "OK"
} else {
    set NtgrTestResult(VLAN-040.tcl) "NG"
}

NtgrDumpLog  $NTGR_LOG_TERSE  "VLAN-040.tcl" "******************** Complete Test case VLAN-040.tcl ********************"
#*************************  End of Test case  **********************************