################################################################################
#
#  File Name    : VLAN-037.tcl
#
#  Description  : This testcase used to untagged frames' forwarding.
#
#  Revision     :
#        Date        Programmer        Description
#        2006/11/15  Scott             Newly created    
#
################################################################################

#*************************  Start of Test case  ********************************
NtgrDumpLog  $NTGR_LOG_TERSE  "VLAN-037.tcl" "******************** Starting Test case VLAN-037.tcl ********************"
CALResetConfiguration $ntgrDUT

# Create VLANs
foreach vlan_index $ntgrVlanIndexList037  {
	CALCreateVlan $vlan_index
}

# Loading traffic
foreach {chassis_id portlist} [array get ntgrTGPortList037] {
    UALConnectTester $chassis_id
    foreach pt $portlist {
        UALLoadPort $chassis_id $pt
    }
}

# Flowing traffic
foreach {chassis_id portlist} [array get ntgrTGPortList037] {
    foreach pt $portlist {
        UALStartTrafficPerPort $chassis_id $pt
    }
}
sleep 2
# Collect rate info several times
set txRate  [lindex [UALReportPortRate $ntgrTG $ntgrTxPort]  0]
set rxRate1 [lindex [UALReportPortRate $ntgrTG $ntgrRxPort1] 1]
set rxRate2 [lindex [UALReportPortRate $ntgrTG $ntgrRxPort2] 1]
if {[expr abs([expr $txRate - $rxRate1])/$txRate]<0.05 && [expr $rxRate2/$txRate]<0.01} {
    set NtgrTestResult(VLAN-037.tcl) "OK"
} else {
    set NtgrTestResult(VLAN-037.tcl) "NG"
}

NtgrDumpLog  $NTGR_LOG_TERSE  "VLAN-037.tcl" "******************** Complete Test case VLAN-037.tcl ********************"
#*************************  End of Test case  **********************************