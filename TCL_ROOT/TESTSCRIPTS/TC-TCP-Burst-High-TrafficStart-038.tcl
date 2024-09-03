################################################################################
#
#  File Name		: TC-TCP-Burst-High-TrafficStart-038.tcl
#
#  Description       	: This test script is used for generating TCP Busrt Traffic 
#                         during peak hours. It is using one port of Spirent Tester 
#		              1G port and 9 ports of IXIA 100 Mbps ports. It grenerate 
#                         simulates TCP FTP. Each port generate traffic at the rate of 40%.
#			        It has two kind of streams 
#			           1. Control packets with size of 64 bytes 
#			           2. Data Packet at the size of 1500. 
#	                    It generate traffic at the ratio of 1:3, that is for every control packet three 
#			        data packets are generated. 
#			        Each port involved in this sends 2 stream (Control Stream and Data Stream) 
#                         to other 9 ports. 
#			  	  Total number of streams defined is 180. 	
#
#  Config File          : TC-TCP-Burst-High-TrafficStart-038.cfg
#
#  Global Variables	: ntgr_trafficTestInfo
#				  
#  Revision History 	:
#         Date         Programmer          Description
#        -----------------------------------------------------------------------
#
#
#
#
################################################################################

#*************************  Start of Test case  ********************************


# Connect and Load the stream in the first cylce of Time Wheel
NtgrDumpLog  $NTGR_LOG_TERSE  "TC-TCP-Burst-High-TrafficStart-038.tcl" "******************** Starting Test case TC-TCP-Burst-High-TrafficStart-038.tcl ********************"
package require SpirentTestCenter
for_array_keys chassis_id ntgr_trafficTestInfo {
	set duration [keylget ntgr_trafficTestInfo($chassis_id) TG_TRAFFIC_DURATION]
	NtgrDumpLog  $NTGR_LOG_TERSE "TC-TCP-Burst-High-TrafficStart-038.tcl" "Connecting to Traffic Generator $chassis_id"
	UALConnectTester $chassis_id
	NtgrDumpLog  $NTGR_LOG_TERSE "TC-TCP-Burst-High-TrafficStart-038.tcl" "Loading the configuration on ports"
	UALLoadTester $chassis_id
	NtgrDumpLog  $NTGR_LOG_TERSE "TC-TCP-Burst-High-TrafficStart-038.tcl" "Cycle Started Traffic on chassis $chassis_id"
	UALStartTraffic $chassis_id
}
#*************************  End of Test case  ****************************************************************