################################################################################
#
#  File Name    : VLAN-049.tcl
#
#  Description  : This testcase used to test MAC table size of a VLAN.
#                 This case should be execute for several loops to get the size
#                 of MAC table.
#
#  Revision     :
#        Date        Programmer        Description
#        2006/11/20  Scott             Newly created    
#
################################################################################

#*************************  Start of Test case  ********************************
NtgrDumpLog  $NTGR_LOG_TERSE  "VLAN-049.tcl" "******************** Starting Test case VLAN-049.tcl ********************"
NtgrDumpLog  $NTGR_LOG_TERSE  "VLAN-049.tcl" "Trying MAC table size: [expr $ntgrSizeMacTable*2]"
CALResetConfiguration $ntgrDUT

# Create VLANs
foreach vlan_index $ntgrVlanIndexList049  {
	CALCreateVlan $vlan_index
}

# Loading traffic
foreach {chassis_id portlist} [array get ntgrTGPortList049] {
    UALConnectTester $chassis_id
    foreach pt $portlist {
        UALLoadPort $chassis_id $pt
    }
}

# Flowing traffic
foreach {chassis_id portlist} [array get ntgrTGPortList049] {
    foreach pt $portlist {
        UALStartTrafficPerPort $chassis_id $pt
    }
}

sleep 20
# Collect rate info several times
set txRate  [lindex [UALReportPortRate $ntgrTG $ntgrTxPort]  0]
set rxRate1 [lindex [UALReportPortRate $ntgrTG $ntgrRxPort1] 1]
set rxRate2 [lindex [UALReportPortRate $ntgrTG $ntgrRxPort2] 1]
if {[expr $rxRate1/$txRate]>0.98 && [expr $rxRate2/$txRate]<0.01} {
    if {$ntgrSizeMacTable > [expr $ntgrTestedSize/2]} {
        set ntgrTestedSize [expr $ntgrSizeMacTable*2]
    }
    set NtgrTestResult(VLAN-049.tcl) "$ntgrTestedSize"
    set ntgrSizeMacTable [expr int($ntgrSizeMacTable * 2)]
} else {
    set NtgrTestResult(VLAN-049.tcl) "$ntgrTestedSize"
    set ntgrSizeMacTable [expr int($ntgrSizeMacTable * 0.75)]
}

NtgrDumpLog  $NTGR_LOG_TERSE  "VLAN-049.tcl" "******************** Complete Test case VLAN-049.tcl ********************"
#*************************  End of Test case  **********************************