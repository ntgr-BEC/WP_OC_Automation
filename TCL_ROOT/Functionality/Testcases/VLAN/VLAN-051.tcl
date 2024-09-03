################################################################################
#
#  File Name    : VLAN-051.tcl
#
#  Description  : This testcase used to verify all possible layer3 VLAN ID on each platform.
#
#  Revision     :
#        Date        Programmer        Description
#        2006/11/21  Scott             Newly created    
#
################################################################################

#*************************  Start of Test case  ********************************
NtgrDumpLog  $NTGR_LOG_TERSE  "VLAN-051.tcl" "******************** Starting Test case VLAN-051.tcl ********************"
CALResetConfiguration $ntgrDUT

Netgear_connect_switch $ntgrDUT
CALRoutingEnable $ntgrDUT
CALRoutingPortEnable $ntgrDUT $srcPort
CALAddIPtoPort $ntgrDUT $srcPort $srcIP
Netgear_disconnect_switch $ntgrDUT

CALCreateVlan "VLAN1"

# Loading traffic
foreach {chassis_id portlist} [array get ntgrTGPortList051] {
    UALConnectTester $chassis_id
    foreach pt $portlist {
        UALLoadPort $chassis_id $pt
    }
}

# Flowing traffic
foreach {chassis_id portlist} [array get ntgrTGPortList051] {
    foreach pt $portlist {
        UALStartTrafficPerPort $chassis_id $pt
    }
}

set bRet 1
# Create VLANs
foreach vlan_index $ntgrVlanIndexList051 {
    CALCreateVlan $vlan_index

    sleep 60
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
    set NtgrTestResult(VLAN-051.tcl) "OK"
} else {
    set NtgrTestResult(VLAN-051.tcl) "NG"
}

NtgrDumpLog  $NTGR_LOG_TERSE  "VLAN-051.tcl" "******************** Complete Test case VLAN-051.tcl ********************"
#*************************  End of Test case  **********************************