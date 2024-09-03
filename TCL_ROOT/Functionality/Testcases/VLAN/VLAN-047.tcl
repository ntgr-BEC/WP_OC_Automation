################################################################################
#
#  File Name    : VLAN-047.tcl
#
#  Description  : This testcase used to test forwarding layer2 traffic in hundreds of VLANs.
#
#  Revision     :
#        Date        Programmer        Description
#        2006/11/20  Scott             Newly created    
#
################################################################################

#*************************  Start of Test case  ********************************
NtgrDumpLog  $NTGR_LOG_TERSE  "VLAN-047.tcl" "******************** Starting Test case VLAN-047.tcl ********************"
CALResetConfiguration $ntgrDUT

## Create VLANs
foreach vlan_index $ntgrVlanIndexList047  {
	CALCreateVlan $vlan_index
}

## Loading traffic
foreach {chassis_id portlist} [array get ntgrTGPortList047] {
    UALConnectTester $chassis_id
    foreach pt $portlist {
        UALLoadPort $chassis_id $pt
    }
}

# Flowing traffic
foreach {chassis_id portlist} [array get ntgrTGPortList047] {
    foreach pt $portlist {
        UALStartTrafficPerPort $chassis_id $pt
    }
}
sleep 20
# Collect rate info several times
set txRate  [lindex [UALReportPortRate $ntgrTG $ntgrTxPort]  0]
set rxRate1 [lindex [UALReportPortRate $ntgrTG $ntgrRxPort1] 1]
set rxRate2 [lindex [UALReportPortRate $ntgrTG $ntgrRxPort2] 1]
if {[expr abs([expr $txRate - $rxRate1])/$txRate]<0.05 && [expr abs([expr $txRate - $rxRate2])/$txRate]>0.05} {
    set NtgrTestResult(VLAN-047.tcl) "OK"
} else {
    set NtgrTestResult(VLAN-047.tcl) "NG"
}

UALSetTrafficPortStreamNameList $ntgrTG $ntgrTxPort "L2_VLAN456"
UALLoadPort $ntgrTG $ntgrTxPort
UALStartTrafficPerPort $ntgrTG $ntgrTxPort
sleep 20
set txRate  [lindex [UALReportPortRate $ntgrTG $ntgrTxPort]  0]
set rxRate1 [lindex [UALReportPortRate $ntgrTG $ntgrRxPort1] 1]
set rxRate2 [lindex [UALReportPortRate $ntgrTG $ntgrRxPort2] 1]
if {[expr $rxRate1/$txRate]>0.95 && [expr $rxRate2/$txRate]>0.65 && [expr $rxRate2/$txRate]<0.69} {
    set NtgrTestResult(VLAN-047.tcl) "OK"
} else {
    set NtgrTestResult(VLAN-047.tcl) "NG"
}


NtgrDumpLog  $NTGR_LOG_TERSE  "VLAN-047.tcl" "******************** Complete Test case VLAN-047.tcl ********************"
#*************************  End of Test case  **********************************