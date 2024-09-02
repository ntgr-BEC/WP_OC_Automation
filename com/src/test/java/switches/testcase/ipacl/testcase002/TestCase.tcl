UALConnectTester $TG
UALLoadPort $TG 1
set PortHandle(0) [_ntgrGetTrafficPortHandle $TG 1]
UALClearPortCounter $TG "$PortHandle(0)"
UALStartTrafficPerPort $TG 1
sleep 3
puts "one send traffic"
UALStopTrafficPerPort $TG 1
IPCServer_WakeUpGUI
IPCServer_WaitForGUI
UALStartTrafficPerPort $TG 1
sleep 3
puts "two send traffic"
UALStopTrafficPerPort $TG 1
IPCServer_RecvParam portA
puts "$portA"
UALReleasePort $TG 1
set  bFlag 1
IPCServer_SetResult
