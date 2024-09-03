################################################################################
#
#  File Name		: TC-TCP-Burst-Low-TrafficStop-041.tcl.tcl
#
#  Description       	: This test stops the TCP Burts during off peak hours.
#	
#  Config File          : None
#
#  Global Variables	: ntgr_trafficTestInfo
#				  
#  Revision History 	:
#         Date         Programmer          Description
#        -----------------------------------------------------------------------
#
################################################################################

#*************************  Start of Test case  ********************************
set tx 0
set rx 0
for_array_keys chassis_id ntgr_trafficTestInfo { 
	NtgrDumpLog  $NTGR_LOG_TERSE "TC-TCP-Burst-Low-TrafficStop-041.tcl" "Stopped TCP Burst Low Traffic on chassis $chassis_id"
	UALStopTraffic $chassis_id
	NtgrDumpLog  $NTGR_LOG_TERSE "TC-TCP-Burst-Low-TrafficStop-041.tcl" "Disconnecting from chassis $chassis_id"
	UALDisconnectTester $chassis_id
	incr tx [UALGetTotalFrameTx $chassis_id]
	incr rx [UALGetTotalFrameRx $chassis_id]
}
NtgrDumpLog  $NTGR_LOG_TERSE "TC-TCP-Burst-Low-TrafficStop-041.tcl" "Frame TX : $tx "
NtgrDumpLog  $NTGR_LOG_TERSE "TC-TCP-Burst-Low-TrafficStop-041.tcl" "Frame RX : $rx "

set loss [expr $tx - $rx ]
if { $loss > 0 } { 
	set lossPercent [expr ( $tx - $rx ) / $tx * 100 ]
	NtgrDumpLog  $NTGR_LOG_ERROR "TC-TCP-Burst-Low-TrafficStop-041.tcl" "Frame Loss : $loss Loss % $lossPercent ********************"
} 
NtgrDumpLog  $NTGR_LOG_TERSE "TC-TCP-Burst-Low-TrafficStop-041.tcl" "******************** Completed Test case TC-UnknownUnicastTrafficStop-026.tcl ********************"
#*************************  End of Test case  ****************************************************************
