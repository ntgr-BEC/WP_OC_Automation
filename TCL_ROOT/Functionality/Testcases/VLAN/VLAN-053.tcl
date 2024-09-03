################################################################################
#
#  File Name    : VLAN-053.tcl
#
#  Description  : This testcase used to test VLAN deletion repeatly with traffic flowing.
#
#  Revision     :
#        Date        Programmer        Description
#        2006/11/21  Scott             Newly created    
#
################################################################################

#*************************  Start of Test case  ********************************
NtgrDumpLog  $NTGR_LOG_TERSE  "VLAN-053.tcl" "******************** Starting Test case VLAN-053.tcl ********************"
CALResetConfiguration $ntgrDUT
CALCreateVlan "VLAN1"

# Loading traffic
foreach {chassis_id portlist} [array get ntgrTGPortList053] {
    UALConnectTester $chassis_id
    foreach pt $portlist {
        UALLoadPort $chassis_id $pt
    }
}

# Flowing traffic
foreach {chassis_id portlist} [array get ntgrTGPortList053] {
    foreach pt $portlist {
        UALStartTrafficPerPort $chassis_id $pt
    }
}

set bRet 1
# Create VLANs
for {set i 0} {$i<$ntgrRepeatTimes} {incr i} {
    CALCreateVlan "VLAN111"

    sleep 20
    # Collect rate info
    set txRate  [lindex [UALReportPortRate $ntgrTG $ntgrTxPort]  0]
    set rxRate1 [lindex [UALReportPortRate $ntgrTG $ntgrRxPort1] 1]
    set rxRate2 [lindex [UALReportPortRate $ntgrTG $ntgrRxPort2] 1]
    if {[expr abs([expr $txRate - $rxRate1])/$txRate]<0.01 && [expr abs([expr $txRate - $rxRate2])/$txRate]>0.5} {
        set bRet [expr $bRet & 1]
    } else {
        set bRet [expr $bRet & 0]
    }
    CALDeleteVlan "VLAN111"
}

if {$bRet == 1} {
    set NtgrTestResult(VLAN-053.tcl) "OK"
} else {
    set NtgrTestResult(VLAN-053.tcl) "NG"
}

NtgrDumpLog  $NTGR_LOG_TERSE  "VLAN-053.tcl" "******************** Complete Test case VLAN-053.tcl ********************"
#*************************  End of Test case  **********************************