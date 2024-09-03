################################################################################
#
#  File Name    : VLAN-055.tcl
#
#  Description  : This testcase used to test shutdown/no shutdown layer2 VLAN member ports
#                 repeatly with traffic flowing.
#
#  Revision     :
#        Date        Programmer        Description
#        2006/11/21  Scott             Newly created    
#
################################################################################

#*************************  Start of Test case  ********************************
NtgrDumpLog  $NTGR_LOG_TERSE  "VLAN-055.tcl" "******************** Starting Test case VLAN-055.tcl ********************"
CALResetConfiguration $ntgrDUT
CALCreateVlan "VLAN1"
CALCreateVlan "VLAN111"

# Loading traffic
foreach {chassis_id portlist} [array get ntgrTGPortList055] {
    UALConnectTester $chassis_id
    foreach pt $portlist {
        UALLoadPort $chassis_id $pt
    }
}

# Flowing traffic
foreach {chassis_id portlist} [array get ntgrTGPortList055] {
    foreach pt $portlist {
        UALStartTrafficPerPort $chassis_id $pt
    }
}

set bRet 1
# Shut/No shut VLAN member port
Netgear_connect_switch $ntgrDUT
for {set i 0} {$i<$ntgrRepeatTimes} {incr i} {
    CALPortLinkUp $ntgrDUT $ntgrShutNoShutPort
    sleep 60

    # Collect rate info
    set txRate  [lindex [UALReportPortRate $ntgrTG $ntgrTxPort]  0]
    set rxRate1 [lindex [UALReportPortRate $ntgrTG $ntgrRxPort1] 1]
    set rxRate2 [lindex [UALReportPortRate $ntgrTG $ntgrRxPort2] 1]
    if {[expr abs([expr $txRate - $rxRate1])/$txRate]<0.01 && [expr abs([expr $txRate - $rxRate2])/$txRate]>0.5} {
        set bRet [expr $bRet & 1]
    } else {
        set bRet [expr $bRet & 0]
        NtgrDumpLog  $NTGR_LOG_TERSE  "VLAN-055.tcl" "Getting unexpected result: $bRet"
        break
    }
    CALPortLinkDown $ntgrDUT $ntgrShutNoShutPort
    sleep 10
    set txRate  [lindex [UALReportPortRate $ntgrTG $ntgrTxPort]  0]
    set rxRate1 [lindex [UALReportPortRate $ntgrTG $ntgrRxPort1] 1]
    set rxRate2 [lindex [UALReportPortRate $ntgrTG $ntgrRxPort2] 1]
    if {[expr abs([expr $txRate - $rxRate1])/$txRate]>0.5 && [expr abs([expr $txRate - $rxRate2])/$txRate]<0.01} {
        set bRet [expr $bRet & 1]
    } else {
        set bRet [expr $bRet & 0]
        NtgrDumpLog  $NTGR_LOG_TERSE  "VLAN-055.tcl" "Getting unexpected result: $bRet"
        break
    }
}
Netgear_disconnect_switch $ntgrDUT

if {$bRet == 1} {
    set NtgrTestResult(VLAN-055.tcl) "OK"
} else {
    set NtgrTestResult(VLAN-055.tcl) "NG"
}

NtgrDumpLog  $NTGR_LOG_TERSE  "VLAN-055.tcl" "******************** Complete Test case VLAN-055.tcl ********************"
#*************************  End of Test case  **********************************