UALConnectTester $TG


UALLoadPort $TG 1
UALLoadPort $TG 2
UALStartTrafficPerPort $TG 1
sleep 10
#puts "one send traffic"
UALStopTrafficPerPort $TG 1

set PortHandle(1) [_ntgrGetTrafficPortHandle $TG 1]
set PortHandle(2) [_ntgrGetTrafficPortHandle $TG 2]
set TxPort1Cnt    [UALReportTxSigFrameCnt $TG $PortHandle(1)]
set RxPort2Cnt    [UALReportRxSigFrameCnt $TG $PortHandle(2)]

UALClearPortCounter $TG "$PortHandle(1)"
UALClearPortCounter $TG "$PortHandle(2)"
set result1 0
if { $RxPort2Cnt == $TxPort1Cnt && $TxPort1Cnt > 0} {
set result1 1
} else {    
set result1 0
Netgear_log_file "CheckPoint-1 ERROR:" "TCP Expect Result: RxTraffic1Cnt = TxPort1Cnt, RxTraffic1Cnt == $RxPort2Cnt,TxTraffic1Cnt == $TxPort1Cnt" "YES"  
}


UALUnLoadStream $TG 1
set ntgr_trafficPortInfo_${TG}(1) "[set ntgr_trafficPortInfo_${TG}(1)] \{TRAFFIC_PORT_STREAM_LIST  \{STREAM_2\}\}"
UALLoadStream $TG 1 
UALStartTrafficPerPort $TG 1
sleep 10
puts "one send traffic"
UALStopTrafficPerPort $TG 1

set PortHandle(1) [_ntgrGetTrafficPortHandle $TG 1]
set PortHandle(2) [_ntgrGetTrafficPortHandle $TG 2]
set TxPort1Cnt    [UALReportTxSigFrameCnt $TG $PortHandle(1)]
set RxPort2Cnt    [UALReportRxSigFrameCnt $TG $PortHandle(2)]

UALClearPortCounter $TG "$PortHandle(1)"
UALClearPortCounter $TG "$PortHandle(2)"
set result2 0
if { $RxPort2Cnt == 0 && $TxPort1Cnt > 0} {
set result2 1
} else {    
set result2 0
Netgear_log_file "CheckPoint-1 ERROR:" "UDP Expect Result: RxTraffic1Cnt = 0, RxTraffic1Cnt == $RxPort2Cnt,TxTraffic1Cnt == $TxPort1Cnt" "YES"  
}
UALUnLoadStream $TG 1
set ntgr_trafficPortInfo_${TG}(1) "[set ntgr_trafficPortInfo_${TG}(1)] \{TRAFFIC_PORT_STREAM_LIST  \{STREAM_3\}\}"
 UALLoadStream $TG 1
UALStartTrafficPerPort $TG 1
sleep 10
puts "one send traffic"
UALStopTrafficPerPort $TG 1

set PortHandle(1) [_ntgrGetTrafficPortHandle $TG 1]
set PortHandle(2) [_ntgrGetTrafficPortHandle $TG 2]
set TxPort1Cnt    [UALReportTxSigFrameCnt $TG $PortHandle(1)]
set RxPort2Cnt    [UALReportRxSigFrameCnt $TG $PortHandle(2)]

UALClearPortCounter $TG "$PortHandle(1)"
UALClearPortCounter $TG "$PortHandle(2)"
set result3 0
if {$RxPort2Cnt == 0 && $TxPort1Cnt > 0} {
set result3 1
} else {    
set result3 0
Netgear_log_file "CheckPoint-1 ERROR:" "ICMP Expect Result: RxTraffic1Cnt = 0, RxTraffic1Cnt == $RxPort2Cnt,TxTraffic1Cnt == $TxPort1Cnt" "YES"  
}

if {$result1 && $result2 && $result3 } {
    set bFlag 1
} else {
    set bFlag 0
}

# Checking
if { $bFlag } {
    NtgrDumpLog $NTGR_LOG_TERSE "" "$NTGR_OK_MSG"
} else {
    NtgrDumpLog $NTGR_LOG_TERSE "" "$NTGR_NG_MSG"
}
IPCServer_SetResult
