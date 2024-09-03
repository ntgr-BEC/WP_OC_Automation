################################################################################
#
#  File Name    : MAC-011.tcl
#
#  Description  : This testcase used to test MAC table size of switch.
#
#  Revision     :
#        Date        Programmer        Description
#        2006/12/27  Scott             Newly created    
#
################################################################################

#*************************  Start of Test case  ********************************
NtgrDumpLog  $NTGR_LOG_TERSE  "MAC-011.tcl" "******************** Starting Test case MAC-011.tcl ********************"
CALResetConfiguration $ntgrDUT

set bStop 1
while {$bStop != 0} {
    source $NTGR_FUNC_CFG/$NTGR_TEST_ENVIRONMENT/MAC/MAC-011.cfg
    # Loading traffic
    foreach {chassis_id portlist} [array get ntgrTGPortList] {
        UALConnectTester $chassis_id
        foreach pt $portlist {
            UALLoadPort $chassis_id $pt
        }
    }

    # Flowing traffic
    foreach {chassis_id portlist} [array get ntgrTGPortList] {
        foreach pt $portlist {
            UALStartTrafficPerPort $chassis_id $pt
        }
    }

    sleep 10

    # Retrieve statistics 5 times
    for {set i 0} {$i<5} {incr i} {
        NtgrDumpLog  $NTGR_LOG_TERSE  "MAC-011.tcl" "Retrieve statistics of Tester's ports: [expr $i+1]"
        set rxRate1 [lindex [UALReportPortRate $ntgrTG $ntgrPort1] 1]
        set txRate2 [lindex [UALReportPortRate $ntgrTG $ntgrPort2] 0]
        set rxRate3 [lindex [UALReportPortRate $ntgrTG $ntgrPort3] 1]
        set rx1 [expr $rxRate1/$txRate2]

        if { $rx1<1.05 && $rx1>0.95 && $rxRate3<5 } {
            set ntgrTestedSize   $ntgrSizeMacTable
            set ntgrSizeMacTable [expr int($ntgrSizeMacTable*$ntgrIncrTime)]
            set ntgrIncrTime [expr $ntgrIncrTime*0.75]
            if {$ntgrIncrTime<1} {set ntgrIncrTime 1.05}
            break;
        } elseif {$i==4} {
            set ntgrSizeMacTable [expr int(($ntgrTestedSize+$ntgrSizeMacTable)/2)]
        }
        sleep 2
    }
    NtgrDumpLog  $NTGR_LOG_TERSE  "MAC-011.tcl" "-------- Cycle: $bStop, MAC table size: $ntgrTestedSize, next try size: $ntgrSizeMacTable --------"
    if {[expr $ntgrSizeMacTable-$ntgrTestedSize]<10} {
        set bStop 0
    } else {
        incr bStop
    }
}

set NtgrTestResult(MAC-011.tcl) "$ntgrTestedSize"

NtgrDumpLog  $NTGR_LOG_TERSE  "MAC-011.tcl" "******************** Complete Test case MAC-011.tcl ********************"
#*************************  End of Test case  **********************************