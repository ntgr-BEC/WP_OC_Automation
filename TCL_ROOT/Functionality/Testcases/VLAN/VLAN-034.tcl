################################################################################
#
#  File Name    : VLAN-034.tcl
#
#  Description  : This testcase used to send multicast frames to other ports in a given VLAN.
#
#  Revision     :
#        Date        Programmer        Description
#        2006/11/15  Scott             Newly created    
#
################################################################################

#*************************  Start of Test case  ********************************
NtgrDumpLog  $NTGR_LOG_TERSE  "VLAN-034.tcl" "******************** Starting Test case VLAN-034.tcl ********************"
CALResetConfiguration $ntgrDUT

# Create VLANs
foreach vlan_index $ntgrVlanIndexList034  {
	CALCreateVlan $vlan_index
}

# Loading traffic
foreach {chassis_id portlist} [array get ntgrTGPortList034] {
    UALConnectTester $chassis_id
    foreach pt $portlist {
        UALLoadPort $chassis_id $pt
    }
}

# Flowing traffic
foreach {chassis_id portlist} [array get ntgrTGPortList034] {
    foreach pt $portlist {
        UALStartTrafficPerPort $chassis_id $pt
    }
}
sleep 20
# Collect rate info several times
set txRate  [lindex [UALReportPortRate $ntgrTG $ntgrTxPort]  0]
set rxRate1 [lindex [UALReportPortRate $ntgrTG $ntgrRxPort1] 1]
set rxRate2 [lindex [UALReportPortRate $ntgrTG $ntgrRxPort2] 1]
if {[expr abs([expr $txRate - $rxRate1])/$txRate]<0.05 && [expr abs([expr $txRate - $rxRate2])/$txRate]<0.05} {
    set NtgrTestResult(VLAN-034.tcl) "OK"
} else {
    set NtgrTestResult(VLAN-034.tcl) "NG"
}

NtgrDumpLog  $NTGR_LOG_TERSE  "VLAN-034.tcl" "******************** Complete Test case VLAN-034.tcl ********************"
#*************************  End of Test case  **********************************