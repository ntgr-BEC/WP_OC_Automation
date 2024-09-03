################################################################################
#
#  File Name    : PORT-SEC-014.tcl
#
#  Description  : This testcase used to test static port security over LAG.
#
#  Revision     :
#        Date        Programmer        Description
#        2007/04/13  Scott             Newly created    
#
################################################################################

#*************************  Start of Test case  ********************************
NtgrDumpLog  $NTGR_LOG_TERSE  "PORT-SEC-014.tcl" "******************** Starting Test case PORT-SEC-014.tcl ********************"
## Initialize the DUTs
foreach ntgrDUT $ntgrDUTList {
    CALResetConfiguration $ntgrDUT
    CALDisableConsoleTimeout $ntgrDUT
}
sleep 60

## Create LAG and VLAN
CALCreatePortChannel "POCH111"
CALCreateVlan "VLAN1000"
set lag_if [CALGetLAGLogicalIF $ntgrDUT1 "POCH111"]

## Configure port security
CALEnablePortSecurity $ntgrDUT1
CALEnablePortSecurity $ntgrDUT1 $lag_if
CALSetMaxDynamic $ntgrDUT1 $lag_if 0
CALConfigStaticAddr $ntgrDUT1 $lag_if $ntgrStaticAddr

## Loading traffic
foreach {chassis_id portlist} [array get ntgrTGPortList] {
    UALConnectTester $chassis_id
    foreach pt $portlist {
        UALLoadPort $chassis_id $pt
    }
}

UALStartTrafficPerPort $ntgrTG $ntgrPort6
UALStartTrafficPerPort $ntgrTG $ntgrPort4
sleep 5

set bRet 1
set TxRx4 [UALReportPortRate $ntgrTG $ntgrPort4]
set TxRx6 [UALReportPortRate $ntgrTG $ntgrPort6]
set rx4 [expr [lindex $TxRx4 1]/[lindex $TxRx6 0]]
if {$rx4>0.63 && $rx4<0.70} {
    set bRet [expr $bRet & 1]
} else {
    set bRet [expr $bRet & 0]
}
puts "--------------$bRet------------"
gets stdin
CALDeleteStaticAddr $ntgrDUT1 $lag_if $ntgrDelStaticAddr
set TxRx4 [UALReportPortRate $ntgrTG $ntgrPort4]
set TxRx6 [UALReportPortRate $ntgrTG $ntgrPort6]
set rx4 [expr [lindex $TxRx4 1]/[lindex $TxRx6 0]]
if {$rx4>0.45 && $rx4<0.55} {
    set bRet [expr $bRet & 1]
} else {
    set bRet [expr $bRet & 0]
}

puts "--------------$bRet------------"
gets stdin
CALSetMaxStatic $ntgrDUT1 $lag_if 10
set TxRx4 [UALReportPortRate $ntgrTG $ntgrPort4]
set TxRx6 [UALReportPortRate $ntgrTG $ntgrPort6]
set rx4 [expr [lindex $TxRx4 1]/[lindex $TxRx6 0]]
if {$rx4>0.30 && $rx4<0.35} {
    set bRet [expr $bRet & 1]
} else {
    set bRet [expr $bRet & 0]
}

puts "--------------$bRet------------"
gets stdin
CALSetMaxStatic $ntgrDUT1 $lag_if 0
set TxRx4 [UALReportPortRate $ntgrTG $ntgrPort4]
set TxRx6 [UALReportPortRate $ntgrTG $ntgrPort6]
set rx4 [expr [lindex $TxRx4 1]/[lindex $TxRx6 0]]
if {$rx4<0.05} {
    set bRet [expr $bRet & 1]
} else {
    set bRet [expr $bRet & 0]
}

puts "--------------$bRet------------"
gets stdin
CALResetMaxStatic $ntgrDUT1 $lag_if
CALConfigStaticAddr $ntgrDUT1 $lag_if $ntgrStaticAddr
set TxRx4 [UALReportPortRate $ntgrTG $ntgrPort4]
set TxRx6 [UALReportPortRate $ntgrTG $ntgrPort6]
set rx4 [expr [lindex $TxRx4 1]/[lindex $TxRx6 0]]
if {$rx4>0.63 && $rx4<0.70} {
    set bRet [expr $bRet & 1]
} else {
    set bRet [expr $bRet & 0]
}


## Stop traffic on ports
foreach {chassis_id portlist} [array get ntgrTGPortList] {
    foreach pt $portlist {
        UALStopTrafficPerPort $chassis_id $pt
    }
}


if {$bRet == 1} {
    set NtgrTestResult(PORT-SEC-014.tcl) "OK"
} else {
    set NtgrTestResult(PORT-SEC-014.tcl) "NG"
}

NtgrDumpLog  $NTGR_LOG_TERSE  "PORT-SEC-014.tcl" "******************** Complete Test case PORT-SEC-014.tcl ********************"
#*************************  End of Test case  **********************************