################################################################################
#
#  File Name    : VLAN-050.tcl
#
#  Description  : This testcase used to verify all possible layer2 VLAN ID on each platform.
#
#  Revision     :
#        Date        Programmer        Description
#        2006/11/21  Scott             Newly created    
#
################################################################################

#*************************  Start of Test case  ********************************
NtgrDumpLog  $NTGR_LOG_TERSE  "VLAN-050.tcl" "******************** Starting Test case VLAN-050.tcl ********************"
CALResetConfiguration $ntgrDUT

# Loading traffic
foreach {chassis_id portlist} [array get ntgrTGPortList050] {
    UALConnectTester $chassis_id
    foreach pt $portlist {
        UALLoadPort $chassis_id $pt
    }
}

# Flowing traffic
foreach {chassis_id portlist} [array get ntgrTGPortList050] {
    foreach pt $portlist {
        UALStartTrafficPerPort $chassis_id $pt
    }
}

set bRet 1
# Create VLANs
CALCreateVlan "VLAN1"
foreach vlan_index $ntgrVlanIndexList050  {
    CALCreateVlan $vlan_index

    sleep 20
    # Collect rate info
    set txRate  [lindex [UALReportPortRate $ntgrTG $ntgrTxPort]  0]
    set rxRate1 [lindex [UALReportPortRate $ntgrTG $ntgrRxPort1] 1]
    set rxRate2 [lindex [UALReportPortRate $ntgrTG $ntgrRxPort2] 1]
    if {[expr abs([expr $txRate - $rxRate1])/$txRate]<0.01 && [expr abs([expr $txRate - $rxRate2])/$txRate]>0.5} {
        set bRet [expr $bRet & 1]
    } else {
        set bRet [expr $bRet & 0]
        break
    }
    CALDeleteVlan $vlan_index
}

if {$bRet == 1} {
    set NtgrTestResult(VLAN-050.tcl) "OK"
} else {
    set NtgrTestResult(VLAN-050.tcl) "NG"
}

NtgrDumpLog  $NTGR_LOG_TERSE  "VLAN-050.tcl" "******************** Complete Test case VLAN-050.tcl ********************"
#*************************  End of Test case  **********************************