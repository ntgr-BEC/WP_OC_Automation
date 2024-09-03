#!/bin/sh
################################################################################   
#
#  File Name		: lib-spirent.tcl
#
#  Description       	:
#         This TCL contains functions to control Spirent Test Center
#
#  Revision History 	:
#         Date         Programmer        Description
#        -----------------------------------------------------------------------
#        02-May-06     Rajeswari V       Created
#
#
#
#
################################################################################

#*******************************************************************************
#
#  Function Name	: _spirentConnect
#
#  Description    : This API is called to connect and initialise  spirent for 
#			  generating the traffic.
#         
#  Usage          : _spirentConnect <chassis_id>
# 
#*******************************************************************************
proc _spirentConnect {chassis_id} {
	Netgear_log_file "lib-spirent.tcl" "Initialize the automation framework"
	_spirentInitialize

	Netgear_log_file "lib-spirent.tcl" "create a project"
	set projectHandle 0
	set projectHandle [_spirentCreateProject]

	Netgear_log_file "lib-spirent.tcl" "create a test object"
	_spirentCreateTest $chassis_id $projectHandle

	Netgear_log_file "lib-spirent.tcl" "connect to each chassis IP address"
      Netgear_log_file "lib-spirent.tcl" "About to connect"
      set chassisId 0
	set chassis_ip_addr [_ntgrGetTGIPAddr $chassis_id]
	Netgear_log_file "lib-spirent.tcl" "Connecting to chassis $chassis_ip_addr"	
      set chassisId [stc::connect -chassis $chassis_ip_addr]
      Netgear_log_file "lib-spirent.tcl" "Connect to $chassis_ip_addr successful!"
	Netgear_log_file "lib-spirent.tcl" "Chassis ID - $chassisId , ProjectHandle - $projectHandle"

      set return_list "$chassisId $projectHandle"
	return $return_list
}

#*******************************************************************************
#
#  Function Name	: _spirentLoadConfig
#
#  Description    : This API is called to load the configuration on the port
#         
#  Usage          : _spirentLoadConfig <chassis_id> <port_list>
# 
#*******************************************************************************
proc _spirentLoadConfig {chassis_id port_list} {
	Netgear_log_file "lib-spirent.tcl" "Configure the ports, load traffic and reserve the ports"
	_spirentCreatePortAndLoadStream $chassis_id $port_list
}

#*******************************************************************************
#
#  Function Name	: _spirentDisconnect
#
#  Description    : This API is called to disconnect from the traffic generator
#         
#  Usage          : _spirentDisconnect <chassis_id>
# 
#*******************************************************************************
proc _spirentDisconnect {chassis_id } {
	Netgear_log_file "lib-spirent.tcl" "Release the ports"
	set chassis_handle [_ntgrGetTGHandle $chassis_id]
	set port_list [_ntgrGetTrafficTestPortList $chassis_id]

	foreach pt $port_list {
		set slot [_ntgrGetTrafficPortSlotId $chassis_id $pt]
		set port [_ntgrGetTrafficPortId $chassis_id $pt]
   	stc::release -ports "$chassis_handle,$slot,$port"
		_ntgrSetTrafficPortHandle $chassis_id $pt -1
	}
 	Netgear_log_file "lib-spirent.tcl" "Disconnecting chassis"
  stc::disconnect $chassis_handle
	#stc::delete $project_handle
	stc::destroy
}


#*******************************************************************************
#
#  Function Name	: _spirentReportTotalLossFrame
#
#  Description    : This function determines frame loss using port counters
#         
#  Usage          : _spirentReportTotalLossFrame <chassis_id>
# 
#*******************************************************************************
proc _spirentReportTotalLossFrame {chassis_id} {
	set port_list [_ntgrGetTrafficTestPortList $chassis_id]
	set totaltx 0
	set totalrx 0

	foreach pt $port_list {
		set port_handle [_ntgrGetTrafficPortHandle $chassis_id $pt]
		set tx 0
		set rx 0
		
		set tx [stc::get txFrameCnt -from $port_handle]
      	set rx [stc::get rxFrameCnt -from $port_handle]

		Netgear_log_file "lib-spirent.tcl" "Port $pt -> tx=$tx rx=$rx"

      	set totaltx [expr $totaltx + $tx]
	      set totalrx [expr $totalrx + $rx]
	}

	set loss [expr $totaltx - $totalrx]
	if {$loss < 0} {
		set loss 0
	}
	Netgear_log_file "lib-spirent.tcl" "total tx=$totaltx rx=$totalrx loss=$loss"
      return $loss
} 

#*******************************************************************************
#
#  Function Name	: _spirentReportTotalFrameTx
#
#  Description    : This function retruns number of frames transmitted
#         
#  Usage          : _spirentReportTotalFrameTx <chassis_id>
# 
#*******************************************************************************
proc _spirentReportTotalFrameTx {chassis_id} {
	set port_list [_ntgrGetTrafficTestPortList $chassis_id]
	set totaltx 0

	foreach pt $port_list {
		set port_handle [_ntgrGetTrafficPortHandle $chassis_id $pt]
		set tx 0
		
		set tx [stc::get txFrameCnt -from $port_handle]
		Netgear_log_file "lib-spirent.tcl" "Port $pt -> tx=$tx"

      	set totaltx [expr $totaltx + $tx]
	}

	Netgear_log_file "lib-spirent.tcl" "total tx=$totaltx "
      return $totaltx
} 

#*******************************************************************************
#
#  Function Name	: _spirentReportTotalFrameRx
#
#  Description    : This function retruns number of frames received
#         
#  Usage          : _spirentReportTotalFrameRx <chassis_id>
# 
#*******************************************************************************
proc _spirentReportTotalFrameRx {chassis_id} {
	set port_list [_ntgrGetTrafficTestPortList $chassis_id]
	set totalrx 0

	foreach pt $port_list {
		set port_handle [_ntgrGetTrafficPortHandle $chassis_id $pt]
		set rx 0
		
		set rx [stc::get rxFrameCnt -from $port_handle]
		Netgear_log_file "lib-spirent.tcl" "Port $pt -> rx=$rx"

      	set totalrx [expr $totalrx + $rx]
	}

	Netgear_log_file "lib-spirent.tcl" "total rx=$totalrx "
      return $totalrx
} 

#*******************************************************************************
#
#  Function Name	: _spirentReportPortLossFrame
#
#  Description    : This function return port counters info
#         
#  Usage          : _spirentReportPortLossFrame <chassis_id> <port>
# 
#*******************************************************************************
proc _spirentReportPortLossFrame {chassis_id port} {
	set port_handle [_ntgrGetTrafficPortHandle $chassis_id $port]
	set totaltx [stc::get txFrameCnt -from $port_handle]
	set totalrx [stc::get rxFrameCnt -from $port_handle]
	Netgear_log_file "lib-spirent.tcl" "Port $port: Tx=$totaltx, Rx=$totalrx"
	return "$totaltx $totalrx"
} 

#*******************************************************************************
#
#  Function Name	: _spirentReportLossFramePerPort
#
#  Description    : This function determines frame loss using port counters
#         
#  Usage          : _spirentReportLossFramePerPort <chassis_id> <tx_port> <rx_port>
# 
#*******************************************************************************
proc _spirentReportLossFramePerPort {chassis_id tx_port rx_port} {
	set port_handle_tx [_ntgrGetTrafficPortHandle $chassis_id $tx_port]
	set totaltx [stc::get txFrameCnt -from $port_handle_tx]
	set port_handle_rx [_ntgrGetTrafficPortHandle $chassis_id $rx_port]
	set totalrx [stc::get rxFrameCnt -from $port_handle_rx]
	set loss [expr $totaltx - $totalrx]
	if {$loss < 0} {
		set loss 0
	}
	Netgear_log_file "lib-spirent.tcl" "total   tx=$totaltx  rx=$totalrx  loss=$loss"
      return $loss
} 

#*******************************************************************************
#
#  Function Name	: _spirentInitialize
#
#  Description    : This function initialize the automation framework environment
#			  for Spirent
#         
#  Usage          : _spirentInitialize
# 
#*******************************************************************************
proc _spirentInitialize {} {
      Netgear_log_file "lib-spirent.tcl" "About to call spirent init"
package require SpirentTestCenter
	stc::init
      Netgear_log_file "lib-spirent.tcl" "Spirent init done!"
      stc::logOptions -LogTo file -Level Debug -FileName "spirent.log"
     	# set automation options
      # this script does no explicit error checking so -ErrorOptions RaiseException
     	# should be used!
      stc::config automationOptions -ResponseMode NonInteractive \
     	                  	      -DisplayStatus False \
           	                        -ResultMessage False \
                 	                  -ErrorOptions RaiseException \
                       	            -logErrorsToFile True
     	Netgear_log_file "lib-spirent.tcl" "Configuration of  automationOptions done!"
 }

#*******************************************************************************
#
#  Function Name	: _spirentCreateProject
#
#  Description    : This function create a project and place the handle in the 
#			  argument variable
#         
#  Usage          : _spirentCreateProject
# 
#*******************************************************************************
proc _spirentCreateProject {} {
	set projectHandle 0
	Netgear_log_file "lib-spirent.tcl" "About to create project"
   	set projectHandle [stc::create Project \
                        -testName "SPIRENT_TEST" \
                        -trafficStartDelay 1 \
                        -trafficStartDelayMode TRAFFIC_START_AFTER_TEST \
                        -delayAfterTransmissionInSec 1 \
                        -enableStreamTracking TRUE \
                        ]
	return $projectHandle

}

#*******************************************************************************
#
#  Function Name	: _spirentCreateTest
#
#  Description    : This function create a test under project projectHandle and 
#			  place the handle in the argument variable
#         
#  Usage          : _spirentCreateTest <product_id> <projectHandle>
# 
#*******************************************************************************
proc _spirentCreateTest {product_id projectHandle} {
	set testHandle 0   
	set test_type [_ntgrGetTrafficTestType $product_id]
	if {$test_type == "fixed"} {
		set duration [_ntgrGetTrafficTestDuration $product_id]
		Netgear_log_file "lib-spirent.tcl" "About to create Fixed test duration = $duration"
   		set testHandle [stc::create FixedTest -under $projectHandle \
      	                -durationMode DURATION_MODE_SEC \
				    -duration $duration		\
            	          -useFrameSizeOnTest FALSE \
                  	    -useLoadOnTest FALSE ]
	} elseif {$test_type == "continuous"} {
	   	Netgear_log_file "lib-spirent.tcl" "About to create continous test"
   		set testHandle [stc::create ContinuousTest -under $projectHandle \
            	          -useFrameSizeOnTest FALSE \
                  	    -useLoadOnTest FALSE ]
	} else {
	   	Netgear_log_file "lib-spirent.tcl" "Error : Test Type value should be <fixed> or <continous>"
		Netgear_log_file "lib-spirent.tcl" "Default is Continous test. Hence Creating Continous test"
   		set testHandle [stc::create ContinuousTest -under $projectHandle \
            	          -useFrameSizeOnTest FALSE \
                  	    -useLoadOnTest FALSE ]
	}

   	#
	# Make the test the active test in the project
	#
  	stc::config $projectHandle -activeTestHandle $testHandle
}

#*******************************************************************************
#
#  Function Name	: _spirentCreatePortAndLoadStream
#
#  Description    : This function loop through the port list and create a port 
#			  object for each port. Argument phandle is the name of an array 
#			  to hold the port handles, index (port#) is 0 to (number of ports - 1).
#			  port mac address is set as: 00:C0:F3:port#:00:00 ip address is set 
#			  to: 10.1.port#.2 gateway is set to: 10.1.port#.2
#         
#  Usage          : _spirentCreatePortAndLoadStream <chassis_id> <port_list
# 
#*******************************************************************************
proc _spirentCreatePortAndLoadStream {chassis_id port_list} {
	
  set project_handle [_ntgrGetTGProjectHandle $chassis_id]
	set chassis_handle [_ntgrGetTGHandle $chassis_id]

	Netgear_log_file "lib-spirent.tcl" "About to create port objects - Project Handle : $project_handle"

	foreach pt $port_list {
		set chassis [_ntgrGetTrafficPortChassisId $chassis_id $pt]
		set slot [_ntgrGetTrafficPortSlotId $chassis_id $pt]
		set port [_ntgrGetTrafficPortId $chassis_id $pt]
		
		_spirentDeletePort $chassis_id $pt
		
		set duplex [_ntgrGetTrafficPortDuplex $chassis_id $pt]
		_spirentSetTrafficPortDuplex duplex

		set flow_ctrl [_ntgrGetTrafficPortFlowCtrl $chassis_id $pt]
		_spirentSetTrafficPortFlowCtrl flow_ctrl

		set auto_neg [_ntgrGetTrafficPortAutoNeg $chassis_id $pt]
		_spirentSetTrafficPortAutoNeg auto_neg

		set speed [_ntgrGetTrafficPortSpeed $chassis_id $pt]
		_spirentSetTrafficPortSpeed speed

		set mode [_ntgrGetTrafficPortPMode $chassis_id $pt]
		_spirentSetTrafficPortPMode mode
		
		set duration 1000
		set burst_size 1
		set duration_type [_ntgrGetTrafficPortDurationType $chassis_id $pt]
		_spirentSetTrafficPortDurationParams duration_type burst_size duration $chassis_id $pt
		
		set cap_enable [_ntgrGetTrafficPortCapture $chassis_id $pt]
		set cap_type [_ntgrGetTrafficPortCaptureType $chassis_id $pt]
		set cap_file [_ntgrGetTrafficPortCaptureFile  $chassis_id $pt]

		set frame_mode [_ntgrGetTrafficPortFrameMode $chassis_id $pt]
		set frame_min_size 128
		set frame_max_size 256
		set use_frame_on_port FALSE
		set use_load_on_port FALSE
		set load 10
		set load_type LOAD_UNIT_PERCENT_LINE_RATE
		_spirentSetTrafficPortFrameParams frame_mode frame_min_size frame_max_size load load_type use_frame_on_port use_load_on_port $chassis_id $pt
		
		set load_mode [_ntgrGetTrafficPortLoadMode $chassis_id $pt]
		set load_min 10
		set load_max 10
		_spirentSetTrafficPortLoadParams load_mode load_min load_max load load_type use_frame_on_port use_load_on_port $chassis_id $pt
		
		Netgear_log_file "lib-spirent.tcl" "Chassis - $chassis, Slot - $slot, Port - $port"
		Netgear_log_file "lib-spirent.tcl" "DuplexMode - $duplex, AutoNeg- $auto_neg, Speed - $speed, PortMode - $mode"
		Netgear_log_file "lib-spirent.tcl" "duration type - $duration_type, Burst size - $burst_size, duration - $duration"
		Netgear_log_file "lib-spirent.tcl" "frame mode - $frame_mode, Load Mode - $load_mode"
		Netgear_log_file "lib-spirent.tcl" "use frame on port - $use_frame_on_port, use load on port - $use_load_on_port"
		Netgear_log_file "lib-spirent.tcl" "load - $load, Load Mode - $load_mode, LoadMin = $load_min, LoadMax = $load_max, load type - $load_type"
		Netgear_log_file "lib-spirent.tcl" "frame mode - $frame_mode, FrameMinSize = $frame_min_size, FrameMaxSize = $frame_max_size"
		Netgear_log_file "lib-spirent.tcl" "Capture mode - $cap_enable, Capture Type - $cap_type, Capture file - $cap_file"
		
		set ipAddress [_ntgrGetTrafficPortIpAddress $chassis_id $pt]
		set gwIpAddress [_ntgrGetTrafficPortGwIpAddress $chassis_id $pt]
		set macAddress [_ntgrGetTrafficPortMacAddress $chassis_id $pt]
		
		Netgear_log_file "lib-spirent.tcl" "Port IP Address $ipAddress"
		Netgear_log_file "lib-spirent.tcl" "Gateway IP Address $gwIpAddress"
		Netgear_log_file "lib-spirent.tcl" "MAC Address $macAddress"
		set stream_count [_ntgrGetTrafficPortStreamCount $chassis_id $pt]
		Netgear_log_file "lib-spirent.tcl" "Stream Count on port $chassis,$slot,$port = $stream_count"
#		if {$stream_count < 1} {
#			Netgear_log_file "lib-spirent.tcl" "ERROR: Stream Count cannot be less than 1"
#			exit 1
#		}

		set ipv6Addr [_ntgrGetPortIPv6Addr $chassis_id $pt]
		set ipv6GwAddr [_ntgrGetPortGwIPv6Addr $chassis_id $pt]	
#		set port_handle [_ntgrGetTrafficPortHandle $chassis_id $pt]
#		if {$port_handle=={}} {
		set port_handle [stc::create Ethernet -under $project_handle \
					    	-name 		eth$pt \
						-portType         PORT_TYPE_ETH \
	            			-location         "$chassis_handle,$slot,$port"\
						-macAddr          "$macAddress" \
						-ipAddr           "$ipAddress" \
					      -gateway          "$gwIpAddress" \
				            -arpRetryCnt      10 \
				            -arpRetryInt      500 \
				            -arpGateway       TRUE \
						-durationMode	$duration_type \
						-duration         $duration \
						-burstSize        $burst_size \
					      -ipVer            IP_V4V6	\
					-ipv6Addr $ipv6Addr					\
					-ipv6Gateway $ipv6GwAddr 		\
						-duplex           $duplex \
						-autoNegot        $auto_neg \
				  	    	-speed            $speed \
						-flowControl      $flow_ctrl \
				   	    	-pollCounter      TRUE 	\
						-pollChart        FALSE	\
						-pollLed          TRUE	\
					    	-portMode         $mode	\
					    	-portTrafficType   PORT_XMIT_AND_RECV \
	    					-useFrameSizeOnPort $use_frame_on_port \
				            -useLoadOnPort      $use_load_on_port\
						-randMinFrameSize  $frame_min_size \
						-randMaxFrameSize $frame_max_size \
						-startCaptOnTest   $cap_enable  \
						-captType          $cap_type    \
						-captFileName      $cap_file \
						-loadMode          $load_mode \
						-loadUnit          $load_type \
						-load              $load\
						-randMaxLoad	 $load_max\
						-randMinLoad       $load_min]

		Netgear_log_file "lib-spirent.tcl" "Port handle for $chassis_handle,$slot,$port = $port_handle"
		_ntgrSetTrafficPortHandle $chassis_id $pt $port_handle
# 		Netgear_log_file "lib-spirent.tcl" "Reserve the ports - $chassis_handle,$slot,$port"
# 		stc::reserve -ports "$chassis_handle,$slot,$port"
#		}

    ##------------ Added trig by kenddy -----------------##
    _spirentSetTrigForPort $chassis_id $pt
    
		_spirentCreateSubInterface $chassis_id $pt
		for {set i 1} {$i <= $stream_count} {incr i}	{
			_spirentCreateTraffic $chassis_id $pt $i $port_handle
			##-- add get stream_name by kenddy xie --##
    	
    	set stream_handle [lindex [_ntgrGetPortStreamHandle $chassis_id $pt] [expr $i-1]]
    	set stream_name [_ntgrGetTrafficPortStreamName $chassis_id $pt $i]
    	global stream_info
    	set stream_info($project_handle,$port_handle,$stream_handle) $stream_name

    	##-- End of get stream_name by kenddy xie --##			
		}
  } 

	foreach pt $port_list {
		set slot [_ntgrGetTrafficPortSlotId $chassis_id $pt]
		set port [_ntgrGetTrafficPortId $chassis_id $pt]
		Netgear_log_file "lib-spirent.tcl" "Reserve the ports - $chassis_handle,$slot,$port"
		stc::reserve -ports "$chassis_handle,$slot,$port"
	}

	Netgear_log_file "lib-spirent.tcl" "Apply the configuration"
	stc::apply $project_handle
}

#*******************************************************************************
#
#  Function Name	: _spirentCreateTraffic
#
#  Description    : This function create traffic streams on each port 
#         
#  Usage          : _spirentCreateTraffic <chassis_id> <port> <stream> <port_handle>
# 
#*******************************************************************************
proc _spirentCreateTraffic {chassis_id port stream port_handle} {
	Netgear_log_file "lib-spirent.tcl" "About to create traffic"
	set traffic 0	
	set load 		100
	set load_type 	LOAD_UNIT_PERCENT_LINE_RATE
	set frame_type 	FRAME_SIZE_MODE_FIXED
	set frame_size    128

	set port_load_mode [_ntgrGetTrafficPortLoadMode $chassis_id $port]
	set port_frame_mode [_ntgrGetTrafficPortFrameMode $chassis_id $port]
	
	if {$port_frame_mode == "fixed"} {
		set frame_size [_ntgrGetTrafficStreamFrameSize $chassis_id $port $stream]	
		if {$port_load_mode == "fixed"} {
			set load [_ntgrGetTrafficStreamLoad $chassis_id $port $stream]
			set load_type  [_ntgrGetTrafficStreamLoadType $chassis_id $port $stream]
			_spirentSetTrafficPortLoadType load_type
		}
	}

	if {$port_frame_mode == "both"} {
		set frame_type [_ntgrGetTrafficStreamFrameType $chassis_id $port $stream]
		_spirentSetTrafficPortFrameType frame_type
	}

  #==========added by Tony on 2010-11-16=================#
  set frame_pad [_ntgrGetTrafficStreamFramePAD $chassis_id $port $stream]
  set frame_crc [_ntgrGetTrafficStreamFrameCRCError $chassis_id $port $stream]
  _spirentSetTrafficPortCRCError frame_crc
  
	Netgear_log_file "lib-spirent.tcl" "FrameSize - $frame_size, FrameType - $frame_type"
	Netgear_log_file "lib-spirent.tcl" "Load - $load, LoadType - $load_type"
	Netgear_log_file "lib-spirent.tcl" "FramePAD - $frame_pad"
  Netgear_log_file "lib-spirent.tcl" "FrameCRCError - $frame_crc"

	set traffic [stc::create Traffic -under $port_handle \
						   -name		 stream_${stream} \
			                     -frameSize 	 $frame_size \
		                           -frameSizeMode  $frame_type \
			      		   -loadUnit	 $load_type	\
						   -load           $load \
		                           -fillpattern 	 $frame_pad \
		                           -genFCSError    $frame_crc \
		                           -pollCounter   TRUE]
	
	_ntgrAppendPortStreamHandle $chassis_id $port $traffic
	
	set l2_stream [_ntgrGetTrafficStreamL2Header $chassis_id $port $stream]
	if {[llength $l2_stream] != 0} {
		_spirentCreateTrafficL2Stream $l2_stream $traffic
		
		set LLCSNAP_stream [_ntgrGetTrafficStreamLLCSNAPHeader $chassis_id $port $stream]
		if {[llength $LLCSNAP_stream] != 0} {
			_spirentCreateTrafficLLCSNAPStream $LLCSNAP_stream $traffic
		}
		
		set vlan_stream [_ntgrGetTrafficStreamVlanHeader $chassis_id $port $stream]
		if {[llength $vlan_stream] != 0} {
			_spirentCreateTrafficVlanStream $vlan_stream $traffic
		}

		set l3_stream [_ntgrGetTrafficStreamL3Header $chassis_id $port $stream]
		if {[llength $l3_stream] != 0} {
			_spirentCreateTrafficL3Stream $l3_stream $traffic
		}
		# Create IPv6 Stream
		
		set ethtype [_ntgrGetTrafficStreamL2HeaderEthernetType $l2_stream]
		set ipv6_stream [_ntgrGetIPv6Hdr $chassis_id $port $stream]
		set ipv6_stream_exists 0
		if {[llength $ipv6_stream] != 0 && $ethtype == 0x86DD} {
		  
		    _spirentCreateIPv6Stream $chassis_id $port $stream $traffic
		    set ipv6_stream_exists 1
		}
		
		set tcp_udp_stream [_ntgrGetTrafficStreamTcpUdpHeader $chassis_id $port $stream]
		if {[llength $tcp_udp_stream] != 0} {
			set protocol [_ntgrGetTrafficStreamL3HeaderProtocol $l3_stream]
			if {$protocol == {}} {
			  
			  if { $ipv6_stream_exists } {
    				set protocol [_ntgrGetIPv6NextHdr [_ntgrGetIPv6Hdr $chassis_id $port $stream]] 
            set protocol [_spirentSearchExtHead $protocol $chassis_id $port $stream]
            Netgear_log_file "lib-spirent.tcl" "ExtHeader include protocol - $protocol"	         
			  }
			}
			_spirentCreateTrafficTcpUdpStream $tcp_udp_stream $traffic $protocol
		}
		
		#=================added by Tony on 2010-11-12============================#
		set igmp_stream [_ntgrGetTrafficStreamIgmpHeader $chassis_id $port $stream]
		if {[llength $igmp_stream] != 0} {
		    set igmp_data [_spirentCreateTrafficIgmpStream $igmp_stream]
		    
		    set stream_name [_ntgrGetTrafficPortStreamName $chassis_id $port $stream]
	      global $stream_name
	      keylset $stream_name TRAFFIC_CUSTOM_INFO "\{CUSTOM_DATA    \"$igmp_data\"\}"
		}
    #=================added by kenddy on 2010-11-21============================#
		set icmp_stream [_ntgrGetTrafficStreamIcmpHeader $chassis_id $port $stream]
		if {[llength $icmp_stream] != 0} {
		    set icmp_pkt [_spirentCreateTrafficIcmpStream $icmp_stream]
		    set stream_name [_ntgrGetTrafficPortStreamName $chassis_id $port $stream]
	      global $stream_name
	      keylset $stream_name TRAFFIC_CUSTOM_INFO "\{CUSTOM_DATA    \"$icmp_pkt\"\}"
		}		
	}
	
	set custom_stream [_ntgrGetTrafficStreamCustomHeader $chassis_id $port $stream]
	if {[llength $custom_stream] != 0} {  
		_spirentCreateTrafficCustomStream $custom_stream $traffic
	}
}

#*******************************************************************************
#
#  Function Name	: _spirent_search_ext_head
#
#  Description    : This API is called to search extension header
#         
#  Usage          : _spirent_search_ext_head <protocol>
#
#  Reversion      : Create by kenddy xie 2010-07-22
#
#*******************************************************************************
proc _spirentSearchExtHead {protocol_num chassis_id port stream} {

      set no_next_head         59
      set hop_by_hop_head      0
      set dst_head             60
      set rout_head            43
      set frag_head            44
      set auth_head            51
      set esp_head             50
      set icmpv6_head          58
      set tcp_head             6
      set udp_head             17
    
  		if { ($protocol_num == $udp_head) || ($protocol_num == $tcp_head) || ($protocol_num == $icmpv6_head) } {
  		  return $protocol_num
  		}
  		if { $protocol_num == $no_next_head } {
  		  Netgear_log_file "lib-spirent.tcl" "waning - the ipv6 extension header is: $protocol_num, will be create L4 stream in default UDP"
  		  set protocol_num $udp_head
  		  return $protocol_num
  		}
  		puts "searching extension header..."
			## Hop-by-Hop option
			if { $protocol_num == $hop_by_hop_head } {
			  set protocol_num [_ntgrGetIPv6ExtHbhNextHeadType [_ntgrGetIPv6ExtHdr $chassis_id $port $stream]]
			  if { ($protocol_num == $no_next_head) || ($protocol_num != $hop_by_hop_head) } {
			      _spirentSearchExtHead $protocol_num $chassis_id $port $stream
			  } else {
			    return $protocol_num
			  }	
			
			##Destination Options 
			} elseif { $protocol_num == $dst_head } {
			  set protocol_num [_ntgrGetIPv6ExtDstNextHeadType [_ntgrGetIPv6ExtHdr $chassis_id $port $stream]]
			  if { ($protocol_num == $no_next_head) || ($protocol_num != $dst_head) } {
			      _spirentSearchExtHead $protocol_num $chassis_id $port $stream
			  } else {
			    return $protocol_num
			  }	
			##ext Routing Header
			} elseif { $protocol_num == $rout_head } {
			  set protocol_num [_ntgrGetIPv6ExtRoutingNextHeadType [_ntgrGetIPv6ExtHdr $chassis_id $port $stream]]
			  if { ($protocol_num == $no_next_head) || ($protocol_num != $rout_head) } {
			      _spirentSearchExtHead $protocol_num $chassis_id $port $stream
			  } else {
			    return $protocol_num
			  }				  
			##Fragment Header
			} elseif { $protocol_num == $frag_head } {
			  set protocol_num [_ntgrGetIPv6ExtFragmentNextHeadType [_ntgrGetIPv6ExtHdr $chassis_id $port $stream]]
			  if { ($protocol_num == $no_next_head) || ($protocol_num != $frag_head) } {
			      _spirentSearchExtHead $protocol_num $chassis_id $port $stream
			  } else {
			    return $protocol_num
			  }				  
			##Authentication Header  
			} elseif { $protocol_num == $auth_head } {
			  set protocol_num [_ntgrGetIPv6ExtAuthNextHeadType [_ntgrGetIPv6ExtHdr $chassis_id $port $stream]]
			  if { ($protocol_num == $no_next_head) || ($protocol_num != $auth_head) } {
			      _spirentSearchExtHead $protocol_num $chassis_id $port $stream
			  } else {
			    return $protocol_num
			  }		  
			
			##Encapsulation Security Payload Header
			} elseif { $protocol_num == $esp_head } {
			  set protocol_num [_ntgrGetIPv6ExtEspNextHeadType [_ntgrGetIPv6ExtHdr $chassis_id $port $stream]]
			  if { ($protocol_num == $no_next_head) || ($protocol_num != $esp_head) } {
			      _spirentSearchExtHead $protocol_num $chassis_id $port $stream
			  } else {
			    return $protocol_num
			  }
			} else {
			  
			  Netgear_log_file "lib-spirent.tcl" "ERROR - not support the ipv6 extension header $protocol_num, set default UDP"
			  set protocol_num $udp_head
			  return $protocol_num
			}
      
}

#*******************************************************************************
#
#  Function Name	: _spirentStartTraffic
#
#  Description    : This API is called to start the traffic on all the ports
#         
#  Usage          : _spirentStartTraffic <chassis_id>
# 
#*******************************************************************************
proc _spirentStartTraffic {chassis_id} {
	set project_handle [_ntgrGetTGProjectHandle $chassis_id]
	set test_type [_ntgrGetTrafficTestType $chassis_id]

	Netgear_log_file "lib-spirent.tcl" "Clear the statistics before starting test"
	stc::perform Clear_Stats $project_handle

	Netgear_log_file "lib-spirent.tcl" "Project_handle - $project_handle"
	stc::start Start_Test $project_handle

	if {$test_type == "fixed"} {
		_spirentWaitForFixedTestToComplete $project_handle
	} else {
		# Some delay is there in starting the traffic for continous test. Hence wait
		# for about 20 seconds to compensate  that delay
		sleep 20
        # Loop to wait traffic started on all ports(sleeping 20 seconds is not enough for many ports).
        # added by Scott, 20060807
        set port_list [_ntgrGetTrafficTestPortList $chassis_id]
        set oldPort -1
        while {1} {
            set bTrafficAllStarted TRUE
            foreach port $port_list {
                set stream_count [_ntgrGetTrafficPortStreamCount $chassis_id $port]
                set port_handle [_ntgrGetTrafficPortHandle $chassis_id $port]
                set tx [stc::get txFrameCnt -from $port_handle]
                if { $stream_count > 0 && $tx < 1 } {
                    set bTrafficAllStarted FALSE
                    
                    if { $oldPort != $port } {
                        Netgear_log_file "lib-spirent.tcl" "Waiting traffic to be started on port $port."
                    }
                    set oldPort $port
                    break
                }
            }
            if { $bTrafficAllStarted == TRUE } {
                Netgear_log_file "lib-spirent.tcl" "Traffic are started on all ports."
                break
            }
            stc::sleep 1
        }
	}
}

#*******************************************************************************
#
#  Function Name	: _spirentStartTrafficPerPort
#
#  Description    : This API is called to start the traffic on the given port
#         
#  Usage          : _spirentStartTrafficPerPort <chassis_id> <port>
# 
#*******************************************************************************
proc _spirentStartTrafficPerPort {chassis_id port_list} {
	set test_type [_ntgrGetTrafficTestType $chassis_id]	
	set project_handle [_ntgrGetTGProjectHandle $chassis_id]	
	foreach port $port_list {
      set port_handle [_ntgrGetTrafficPortHandle $chassis_id $port]
	    stc::start Start_Traffic $port_handle
	    Netgear_log_file "lib-spirent.tcl" "start sending traffic -port $port!"
	}

	if {$test_type == "fixed"} {
		_spirentWaitForFixedTestToComplete $project_handle
	} 
}

#*******************************************************************************
#
#  Function Name	: _spirentStartCapturePerPort
#
#  Description    : This API is called to capture the traffic on the given port
#         
#  Usage          : _spirentStartCapturePerPort <chassis_id> <port>
# 
#*******************************************************************************
proc _spirentStartCapturePerPort {chassis_id port_list} {
	foreach port $port_list {
	    set port_handle [_ntgrGetTrafficPortHandle $chassis_id $port]
      set capture_handle [_ntgrGetTrafficPortCapture $chassis_id $port]
      if {$capture_handle== "TRUE"} {
          stc::start Start_Capt $port_handle
          Netgear_log_file "lib-spirent.tcl" "start capture traffic!"
      } else {
          Netgear_log_file "lib-spirent.tcl" "Can't start capture traffic without setting of TRAFFIC_PORT_CAPTURE to TRUE!"
      }
	}
}


#*******************************************************************************
#
#  Function Name	: _spirentSetTrigForPort
#
#  Description    : This API is called to trig the traffic on the given port
#         
#  Usage          : _spirentSetTrigForPort <chassis_id> <port>
#  
#  Revision       : Added by kenddy.Xie
#
#*******************************************************************************

proc _spirentSetTrigForPort {chassis_id port} {
  	
    set port_handle [_ntgrGetTrafficPortHandle $chassis_id $port]
    
    for { set i 1 } { $i <= 8 } { incr i } {
        set [subst trig[subst $i]_enable]   [_ntgrGetTrafficPortTrig[subst $i]Status $chassis_id $port]  
        set [subst trig[subst $i]_offset]   [_ntgrGetTrafficPortTrig[subst $i]OffSet $chassis_id $port]
        set [subst trig[subst $i]_range]    [_ntgrGetTrafficPortTrig[subst $i]Range $chassis_id $port]
        set [subst trig[subst $i]_pattern]  [_ntgrGetTrafficPortTrig[subst $i]Pattern $chassis_id $port]
        set [subst trig[subst $i]_mode]     [_ntgrGetTrafficPortTrig[subst $i]Mode $chassis_id $port]
        
        if {[subst $[subst trig[subst $i]_enable]] == "TRUE" } {
            
            set [subst trig$i] [stc::get [subst trig$i] -from $port_handle]
            stc::config [subst $[subst trig$i]] -enable true \
                                                -offset [subst $[subst trig[subst $i]_offset]] \
                                                -range  [subst $[subst trig[subst $i]_range]]  \
                                                -pattern [subst $[subst trig[subst $i]_pattern]] \
                                                -startMode [subst $[subst trig[subst $i]_mode]] 
           
           Netgear_log_file "lib-spirent.tcl" "[subst trig[subst $i]_offset]------- [subst $[subst trig[subst $i]_offset]]"
           Netgear_log_file "lib-spirent.tcl" "[subst trig[subst $i]_pattern]------ [subst $[subst trig[subst $i]_pattern]]"
           Netgear_log_file "lib-spirent.tcl" "[subst trig[subst $i]_range] ------- [subst $[subst trig[subst $i]_range]]"
           Netgear_log_file "lib-spirent.tcl" "[subst trig[subst $i]_mode]--------- [subst $[subst trig[subst $i]_mode]]"
           
#           puts "[subst trig[subst $i]_offset]------- [subst $[subst trig[subst $i]_offset]]"          
#           puts "[subst trig[subst $i]_range] ------- [subst $[subst trig[subst $i]_range]]"                           
#           puts "[subst trig[subst $i]_pattern]------ [subst $[subst trig[subst $i]_pattern]]"
#           puts "[subst trig[subst $i]_mode]--------- [subst $[subst trig[subst $i]_mode]]"
           
        } else {
            Netgear_log_file "lib-spirent.tcl" "trig[subst $i] flag not been set to TRUE!"
        }      
    }  	
}


#*******************************************************************************
#
#  Function Name	: _ntgrGetL2L3CountArray
#
#  Description    : This API is called to get statistics array for testcenter port. 
#         
#  Usage          : _ntgrGetL2L3CountArray <port_handle> <aCountArray>
#  
#  Revision       : Added by kenddy.Xie
#
#*******************************************************************************

proc _ntgrGetL2L3CountArray {port_handle aCountArray} {
  
  upvar 1 $aCountArray aTmpArray
  array set aTmpArray [stc::get L2L3Counters -from $port_handle]

  Netgear_log_file "lib-spirent.tcl" "txsigframecnt is: $aTmpArray(txsigframecnt)"
  Netgear_log_file "lib-spirent.tcl" "rxsigframecnt is: $aTmpArray(rxsigframecnt)"
}

#*******************************************************************************
#
#  Function Name	: _spirentGetL2L3CountArrayEx
#
#  Description    : This API is called to get statistics array for testcenter port. 
#         
#  Usage          : _spirentGetL2L3CountArrayEx <chassis_id> <port> <aCountArray>
#  
#  Revision       : Added by kenddy.Xie
#
#*******************************************************************************
proc _spirentGetL2L3CountArrayEx {chassis_id port aCountArray} {

  upvar 1 $aCountArray aTmpArray  
  set port_handle [_ntgrGetTrafficPortHandle $chassis_id $port]
  array set aTmpArray [stc::get L2L3Counters -from $port_handle]

  for { set i 1 } { $i <= 8 } { incr i } {
      set [subst trig[subst $i]_enable]   [_ntgrGetTrafficPortTrig[subst $i]Status $chassis_id $port]  
      if {[subst $[subst trig[subst $i]_enable]] == "TRUE"} {
          Netgear_log_file "lib-spirent.tcl" "port $port rxtrig[subst $i]cnt is: $aTmpArray(rxtrig[subst $i]cnt)"
      }    
  }
  
  set aTmpArray(rxsigframecnt) [_spirentReportRxSigCount $port_handle]
  set aTmpArray(rxsigframecnt) [_spirentReportTxSigCount $port_handle]
  Netgear_log_file "lib-spirent.tcl" "port $port txsigframecnt is: $aTmpArray(txsigframecnt)"
  Netgear_log_file "lib-spirent.tcl" "port $port rxsigframecnt is: $aTmpArray(rxsigframecnt)"
}

#*******************************************************************************
#
#  Function Name	: _ntgrGetL2L3RateArray
#
#  Description    : This API is called to get rate statistics array for testcenter port. 
#         
#  Usage          : _ntgrGetL2L3CountArray <port_handle> <aRateArray>
#  
#  Revision       : Added by kenddy.Xie
#
#*******************************************************************************

proc _ntgrGetL2L3RateArray {port_handle aRateArray} {
  upvar 1 $aRateArray aTmpArray
  array set aTmpArray [stc::get L2L3Rates -from $port_handle]

  Netgear_log_file "lib-spirent.tcl" "txsigframerate is: $aTmpArray(txsigframerate)"
  Netgear_log_file "lib-spirent.tcl" "rxsigframerate is: $aTmpArray(rxsigframerate)"
}

#*******************************************************************************
#
#  Function Name	: _spirentGetL2L3RateArrayEx
#
#  Description    : This API is called to get rate statistics array for testcenter port. 
#         
#  Usage          : _spirentGetL2L3CountArray <chassis_id> <port> <aRateArray>
#  
#  Revision       : Added by kenddy.Xie
#
#*******************************************************************************
proc _spirentGetL2L3RateArrayEx {chassis_id port aRateArray} {
  upvar 1 $aRateArray aTmpArray
  set port_handle [_ntgrGetTrafficPortHandle $chassis_id $port]
  array set aTmpArray [stc::get L2L3Rates -from $port_handle]

  for { set i 1 } { $i <= 8 } { incr i } {
      set [subst trig[subst $i]_enable] [_ntgrGetTrafficPortTrig[subst $i]Status $chassis_id $port]
      if {[subst $[subst trig[subst $i]_enable]] == "TRUE"} {
          Netgear_log_file "lib-spirent.tcl" "port $port rxtrig[subst $i]rate is: $aTmpArray(rxtrig[subst $i]rate)"
      }    
  }
  Netgear_log_file "lib-spirent.tcl" "port $port txsigframerate is: $aTmpArray(txsigframerate)"
  Netgear_log_file "lib-spirent.tcl" "port $port rxsigframerate is: $aTmpArray(rxsigframerate)"
}
#*******************************************************************************
#
#  Function Name	: _spirentStopCapturePerPort
#
#  Description    : This API is called to start the traffic on the given port
#         
#  Usage          : _spirentStopCapturePerPort <chassis_id> <port>
# 
#*******************************************************************************
proc _spirentStopCapturePerPort {chassis_id port_list} {
	foreach port $port_list {
      set port_handle [_ntgrGetTrafficPortHandle $chassis_id $port]
      set capture_handle [_ntgrGetTrafficPortCapture $chassis_id $port]
      if {$capture_handle== "TRUE"} {
	          stc::stop Stop_Capt $port_handle
	          Netgear_log_file "lib-spirent.tcl" "Stop capture traffic!"
	          stc::perform Save_Capt $port_handle
	          Netgear_log_file "lib-spirent.tcl" "Save capture file!"
	    } else { 
	          Netgear_log_file "lib-spirent.tcl" "Can't stop capture traffic without setting of TRAFFIC_PORT_CAPTURE to TRUE!"
	    }
	}
}
#*******************************************************************************
#
#  Function Name	: _spirentStopTraffic
#
#  Description    : This API is called to stop the traffic on all ports
#         
#  Usage          : _spirentStopTraffic <chassis_id>
# 
#*******************************************************************************
proc _spirentStopTraffic {chassis_id} {
	set project_handle [_ntgrGetTGProjectHandle $chassis_id]
	Netgear_log_file "lib-spirent.tcl" "Project_handle - $project_handle"
	stc::stop Stop_Test $project_handle
    # Waiting for test stop, added by scott, Aug 7,2006
    set cnt 0
    while {1} {
        set state [stc::get testState -from $project_handle]
        if {[string compare "TEST_STATE_RUNNING" $state] == 0} {
            Netgear_log_file "lib-spirent.tcl" "Test is running."
        } elseif {[string compare "TEST_STATE_COMPLETING" $state] == 0} {
            incr cnt
            if { $cnt == 1 } {
                Netgear_log_file "lib-spirent.tcl" "Test is completing..."
            }
        } elseif {[string compare "TEST_STATE_COMPLETED" $state] == 0} {
            Netgear_log_file "lib-spirent.tcl" "Test complete!"
            break
        } elseif {[string compare "TEST_STATE_NOT_RUNNING" $state] == 0} {
            Netgear_log_file "lib-spirent.tcl" "Test is not running!"
            break
        } elseif {$cnt > 300} {
            # It takes long time to stop, maybe somewhere goes wrong, just stop this loop.
            break
        }
        stc::sleep 1
    }
}
#*******************************************************************************
#
#  Function Name	: _spirentStopTrafficPerPort
#
#  Description    : This API is called to start the traffic on the given port
#         
#  Usage          : _spirentStopTrafficPerPort <chassis_id> <port>
# 
#*******************************************************************************
proc _spirentStopTrafficPerPort {chassis_id port_list} {

  foreach port $port_list {
	    set port_handle [_ntgrGetTrafficPortHandle $chassis_id $port]

	    Netgear_log_file "lib-spirent.tcl" "Stop traffic on port - $port"
	    stc::stop Stop_Traffic $port_handle
	}

}

#*******************************************************************************
#
#  Function Name	: _spirentWaitForFixedTestToComplete
#
#  Description    : This function is called in StartTraffic if it is a fixed test
#         
#  Usage          : _spirentWaitForFixedTestToComplete <project_handle>
# 
#*******************************************************************************
proc _spirentWaitForFixedTestToComplete {project_handle} {
	Netgear_log_file "lib-spirent.tcl" "Waiting for Fixed Test to complete"
	while {1} {
	   	set state [stc::get testState -from $project_handle]
		if {[string compare "TEST_STATE_COMPLETED" $state] == 0} {
		      Netgear_log_file "lib-spirent.tcl" "Fixed Test completed"
		      break
		} elseif {[string compare "TEST_STATE_NOT_RUNNING" $state] == 0} {
		      Netgear_log_file "lib-spirent.tcl" "Error - Waiting for Fixed Test Not running"
		      break
		}
		stc::sleep 1
	}
}

#*******************************************************************************
#
#  Function Name	: _spirentCreateTrafficL2Stream
#
#  Description    : This function is called to create L2 stream header
#         
#  Usage          : _spirentCreateTrafficL2Stream <l2_stream> <traffic>
# 
#*******************************************************************************
proc _spirentCreateTrafficL2Stream {l2_stream traffic} {
	set dst_mac_addr [_ntgrGetTrafficStreamL2HeaderDstMacAddr $l2_stream]
	set src_mac_addr [_ntgrGetTrafficStreamL2HeaderSrcMacAddr $l2_stream]
	set src_mac_cnt [_ntgrGetTrafficStreamL2HeaderSrcMacCnt $l2_stream]
	set dst_mac_cnt [_ntgrGetTrafficStreamL2HeaderDstMacCnt $l2_stream]
	set src_mac_inc [_ntgrGetTrafficStreamL2HeaderSrcMacInc $l2_stream]
	set dst_mac_inc [_ntgrGetTrafficStreamL2HeaderDstMacInc $l2_stream]
	set srcMacUseDefault [_ntgrGetTrafficStreamL2HeaderSrcMacUseDefault $l2_stream]
 	set dstMacUseDefault [_ntgrGetTrafficStreamL2HeaderDstMacUseDefault $l2_stream]	
 	set type [_ntgrGetTrafficStreamL2HeaderEthernetType $l2_stream]	

	Netgear_log_file "lib-spirent.tcl" "SRC_MAC_ADDR - $src_mac_addr, DST_MAC_ADDR - $dst_mac_addr"
	Netgear_log_file "lib-spirent.tcl" "SRC_MAC_ADDR - DEFAULT $srcMacUseDefault , DST_MAC_ADDR  DEFAULT - $dstMacUseDefault"
	Netgear_log_file "lib-spirent.tcl" "SRC_MAC_COUNT - $src_mac_cnt, DST_MAC_COUNT - $dst_mac_cnt, Ethernet Type - $type"

      stc::create EthernetIIHeader -under $traffic \
	                             -type $type \
      	                        -useDefaultSrcMacAddr $srcMacUseDefault \
            	                  -useDefaultDstMacAddr $dstMacUseDefault \
						-dstMacAddr $dst_mac_addr \
						-srcMacAddr $src_mac_addr \
						-dstMacAddrCnt $dst_mac_cnt \
						-srcMacAddrCnt $src_mac_cnt \
						-dstMacAddrInc $dst_mac_inc \
						-srcMacAddrInc $src_mac_inc
}

#*******************************************************************************
#
#  Function Name	: _spirentCreateTrafficLLCSNAPStream
#
#  Description    : This function is called to create LLCSNAP_stream header
#         
#  Usage          : _spirentCreateTrafficLLCSNAPStream <LLCSNAP_stream> <traffic>
# 
#*******************************************************************************
proc _spirentCreateTrafficLLCSNAPStream {LLCSNAP_stream traffic} {
 
  set llc_ControlField [_ntgrGetTrafficStreamLLCSNAPHeaderControl $LLCSNAP_stream]
  set llc_Dsap [_ntgrGetTrafficStreamLLCSNAPHeaderDSAP $LLCSNAP_stream]
  set llc_Ssap [_ntgrGetTrafficStreamLLCSNAPHeaderSSAP $LLCSNAP_stream]
  set snap_Ethertype [_ntgrGetTrafficStreamLLCSNAPHeaderEthertype $LLCSNAP_stream]
  set snap_Vendor [_ntgrGetTrafficStreamLLCSNAPHeaderVendor $LLCSNAP_stream]
  Netgear_log_file "lib-spirent.tcl" "LLC llcControlField - $llc_ControlField, llcDsap - $llc_Dsap, llcSsap- $llc_Ssap"
  Netgear_log_file "lib-spirent.tcl" "SNAP Ethertype - $snap_Ethertype, VendorCode - $snap_Vendor"
    
  stc::create LLCSNAPheader -under $traffic \
      -llcControlField  $llc_ControlField   \
      -llcDsap $llc_Dsap                    \
      -llcSsap $llc_Ssap                    \
      -snapEthertype  $snap_Ethertype       \
      -snapVendorCode $snap_Vendor
}

#*******************************************************************************
#
#  Function Name	: _spirentCreateTrafficVlanStream
#
#  Description    : This function is called to create VLAN stream header
#         
#  Usage          : _spirentCreateTrafficVlanStream <vlan_stream> <traffic>
# 
#*******************************************************************************
proc _spirentCreateTrafficVlanStream {vlan_stream traffic} {
	set vlan_id [_ntgrGetTrafficStreamVlanHeaderId $vlan_stream]
	set vlan_pri [_ntgrGetTrafficStreamVlanHeaderPri $vlan_stream]
	set vlan_cnt [_ntgrGetTrafficStreamVlanHeaderCnt $vlan_stream]
	set vlan_inc [_ntgrGetTrafficStreamVlanHeaderInc $vlan_stream]
	Netgear_log_file "lib-spirent.tcl" "VLANID - $vlan_id, PRIORITY - $vlan_pri, COUNT - $vlan_cnt"
      stc::create VlanHeader   -under $traffic \
     		                   -id    $vlan_id \
                               -pri   $vlan_pri \
					 -idCnt $vlan_cnt \
					 -idInc $vlan_inc

    set vlan2_id [_ntgrGetTrafficStreamVlanHeader2Id $vlan_stream]
    set vlan2_pri [_ntgrGetTrafficStreamVlanHeader2Pri $vlan_stream]
    set vlan2_cnt [_ntgrGetTrafficStreamVlanHeader2Cnt $vlan_stream]
    set vlan2_inc [_ntgrGetTrafficStreamVlanHeader2Inc $vlan_stream]
    if {$vlan2_id != {}} {
        Netgear_log_file "lib-spirent.tcl" "VLAN2ID - $vlan2_id, PRIORITY2 - $vlan2_pri, COUNT2 - $vlan2_cnt"
        stc::create VlanHeader -under $traffic       \
                               -id    $vlan2_id      \
                               -pri   $vlan2_pri     \
                               -idCnt $vlan2_cnt     \
                               -idInc $vlan2_inc
    }
}

#*******************************************************************************
#
#  Function Name	: _spirentCreateTrafficL3Stream
#
#  Description    : This function is called to create L3 stream header
#         
#  Usage          : _spirentCreateTrafficL3Stream <l3_stream> <traffic>
# 
#*******************************************************************************
proc _spirentCreateTrafficL3Stream {l3_stream traffic} {
	set src_ip_addr [_ntgrGetTrafficStreamL3HeaderSrcIPAddr $l3_stream]
	set dst_ip_addr [_ntgrGetTrafficStreamL3HeaderDstIPAddr $l3_stream]
	set src_ip_mask [_ntgrGetTrafficStreamL3HeaderSrcIPMask $l3_stream]
	set dst_ip_mask [_ntgrGetTrafficStreamL3HeaderDstIPMask $l3_stream]
	set src_ip_cnt [_ntgrGetTrafficStreamL3HeaderSrcIPCnt $l3_stream]
	set dst_ip_cnt [_ntgrGetTrafficStreamL3HeaderDstIPCnt $l3_stream]
	set src_ip_inc [_ntgrGetTrafficStreamL3HeaderSrcIPInc $l3_stream]
	set dst_ip_inc [_ntgrGetTrafficStreamL3HeaderDstIPInc $l3_stream]
	set src_host_cnt [_ntgrGetTrafficStreamL3HeaderSrcHostCnt $l3_stream]
	set dst_host_cnt [_ntgrGetTrafficStreamL3HeaderDstHostCnt $l3_stream]
	set gw_ip [_ntgrGetTrafficStreamL3HeaderGwIP $l3_stream]
	set protocol [_ntgrGetTrafficStreamL3HeaderProtocol $l3_stream]
	set tos [_ntgrGetTrafficStreamL3HeaderTOS $l3_stream]
	set ttl [_ntgrGetTrafficStreamL3HeaderTTL $l3_stream]
	#-------- Added by kenddy ----------#
	set ip_len [_ntgrGetTrafficStreamL3HeaderLen $l3_stream]
	set ip_flag [_ntgrGetTrafficStreamL3HeaderFlag $l3_stream]
	set ip_frag_offset [_ntgrGetTrafficStreamL3HeaderFragOffset $l3_stream]
	
	Netgear_log_file "lib-spirent.tcl" "SRCIP - $src_ip_addr, DST_IP - $dst_ip_addr, SRCMASK - $src_ip_mask, DSTMASK - $dst_ip_mask"
	Netgear_log_file "lib-spirent.tcl" "SRCCNT - $src_ip_cnt, DSTCNT - $dst_ip_cnt, SRCINC - $src_ip_inc, DSTINC - $dst_ip_inc"
	Netgear_log_file "lib-spirent.tcl" "SRCHOSTCNT - $src_host_cnt, DSTHOSTCNT - $dst_host_cnt, gw_ip - $gw_ip, PROTO - $protocol"		
	Netgear_log_file "lib-spirent.tcl" "TTL - $ttl, TOS - $tos"
	
	Netgear_log_file "lib-spirent.tcl" "IP_FLAG - $ip_flag"
	Netgear_log_file "lib-spirent.tcl" "IP_FRAG_OFFSET - $ip_frag_offset"
	
	stc::create Ipv4Header -under $traffic \
					-protocol        	$protocol \
					-srcIp           	$src_ip_addr \
	                        -srcIpPrefixLen  	$src_ip_mask \
      	                  -srcIpNetworkCnt 	$src_ip_cnt \
               		      -srcIpNetworkInc 	$src_ip_inc \
					-srcIpHostCnt    	$src_host_cnt \
                		     	-dstIp           	$dst_ip_addr \
	                       	-dstIpPrefixLen  	$dst_ip_mask \
      	                 	-dstIpNetworkCnt 	$dst_ip_cnt \
                 		     	-dstIpNetworkInc 	$dst_ip_inc \
	                       	-dstIpHostCnt    	$dst_host_cnt \
      	                	-gateway         	$gw_ip \
                 		     	-tos            	$tos \
		                      -ttl            	$ttl \
                		      -ipFlag          	$ip_flag \
	                        -fragmentOffset  	$ip_frag_offset\
                 		      -useDefaultSrcIp 	FALSE\
	                        -useDefaultGateway FALSE
}

# Call this function to create ipv6 stream
proc _spirentCreateIPv6Stream { chassis_id port stream traffic } {
    set ipv6hdr [_ntgrGetIPv6Hdr $chassis_id $port $stream]
    if { $ipv6hdr == {} } return;

    set src_addr [ _ntgrGetIPv6SrcAddr $ipv6hdr ]
    set src_plen [ _ntgrGetIPv6SrcPrefixLength $ipv6hdr ]
    set src_hcnt [ _ntgrGetIPv6SRCHostCnt $ipv6hdr ]
    set src_ncnt [ _ntgrGetIPv6SrcNetCnt $ipv6hdr ]
    set src_ninc [ _ntgrGetIPv6SrcNetInc $ipv6hdr ]
    set dst_addr [ _ntgrGetIPv6DstAddr $ipv6hdr ]
    set dst_plen [ _ntgrGetIPv6DstPrefixLength $ipv6hdr ]
    set dst_hcnt [ _ntgrGetIPv6DstHostCnt $ipv6hdr ]
    set dst_ncnt [ _ntgrGetIPv6DstNetCnt $ipv6hdr ]
    set dst_ninc [ _ntgrGetIPv6DstNetInc $ipv6hdr ]
    set traf_cls [ _ntgrGetIPv6TrafficClass $ipv6hdr ]
    set flow_lbl [ _ntgrGetIPv6FlowLabel $ipv6hdr ]
    set payl_len [ _ntgrGetIPv6PayloadLength $ipv6hdr ]
    set next_hdr [ _ntgrGetIPv6NextHdr $ipv6hdr ]
    set hop_limi [ _ntgrGetIPv6HopLimit $ipv6hdr ]
    set gateway  [ _ntgrGetIPv6Gateway $ipv6hdr ]
    set src_defa [ _ntgrDefaultSrcIp $ipv6hdr ]
    set gw_defau [ _ntgrDefaultGateway $ipv6hdr ]

    stc::create  Ipv6Header  -under  $traffic    \
                 -ipv6SrcIp          $src_addr   \
                 -srcIpPrefixLen     $src_plen   \
                 -srcIpHostCnt       $src_hcnt   \
                 -srcIpNetworkCnt    $src_ncnt   \
                 -srcIpNetworkInc    $src_ninc   \
                 -ipv6DstIp          $dst_addr   \
                 -dstIpPrefixLen     $dst_plen   \
                 -dstIpHostCnt       $dst_hcnt   \
                 -dstIpNetworkCnt    $dst_ncnt   \
                 -dstIpNetworkInc    $dst_ninc   \
                 -trafficClass       $traf_cls   \
                 -flowLabel          $flow_lbl   \
                 -len                $payl_len   \
                 -nextHeaderType     $next_hdr   \
                 -hopLimit           $hop_limi   \
                 -ipv6Gateway        $gateway    \
                 -useDefaultSrcIp    $src_defa   \
                 -useDefaultGateway  $gw_defau

    ## Added ipv6 extension header by kenddy xie 2010-07-22
    set ipv6Exthdr [_ntgrGetIPv6ExtHdr $chassis_id $port $stream]
    if { $ipv6Exthdr == {} } {
      return
    } else {
      
			  set protocol [_ntgrGetIPv6NextHdr [_ntgrGetIPv6Hdr $chassis_id $port $stream]] 
			  
			  ###----- Added ipv6 extension header by kenddy.xie 2010-07-22 --------##
#			  puts "ipv6 next head is: $protocol"
#       set protocol [_spirentSearchExtHead $protocol $chassis_id $port $stream]
        ###----- End of added ipv6 extension header --------##
              
      _spirentCreateIPv6ExtStream $protocol $chassis_id $port $stream $traffic
    }
    ## End of added ipv6 extension header                  
}

#*******************************************************************************
#
#  Function Name	: _spirentCreateIPv6ExtStream
#
#  Description    : This function is called to create ipv6 extension header in recursively.
#         
#  Usage          : _spirentCreateIPv6ExtStream <next_hdr> <chassis_id> <port> <stream> <traffic>
#
#  Revison        : Create by kenddy.xie 2010-07-22
#
#*******************************************************************************

proc _spirentCreateIPv6ExtStream { next_hdr chassis_id port stream traffic } {
    
    set no_next_head         59
    set hop_by_hop_head      0
    set dst_head             60
    set rout_head            43
    set frag_head            44
    set auth_head            51
    set esp_head             50
    set icmpv6_head          58
    set tcp_head             6
    set udp_head             17
    
    set ipv6ExtHdr [_ntgrGetIPv6ExtHdr $chassis_id $port $stream]
        
    ## Creat ipv6 Extension Header stream 
    if { $next_hdr == $no_next_head || $next_hdr == $tcp_head || \
         $next_hdr == $udp_head || $next_hdr == $icmpv6_head } {
      return
    }

    if { $next_hdr == $hop_by_hop_head } {
      set hbh_next_type   [_ntgrGetIPv6ExtHbhNextHeadType $ipv6ExtHdr]
      stc::create  Ipv6ExtHeaderHopByHop   -under  $traffic     \
                   -nextHeaderType          $hbh_next_type
      if {$hbh_next_type == $no_next_head || $hbh_next_type == $hop_by_hop_head } {
	      return
      } else {
	      _spirentCreateIPv6ExtStream $hbh_next_type $chassis_id $port $stream $traffic
      }	  

    } elseif { $next_hdr == $dst_head } {
      set dst_next_type   [_ntgrGetIPv6ExtDstNextHeadType $ipv6ExtHdr]
      stc::create  Ipv6ExtHeaderDestination -under  $traffic    \
                   -nextHeaderType          $dst_next_type
      if {$dst_next_type == $no_next_head  || $dst_next_type == $dst_head } {
	      return
      } else {
	      _spirentCreateIPv6ExtStream $dst_next_type $chassis_id $port $stream $traffic
      }	

    } elseif { $next_hdr == $frag_head } {
      set frag_next_type  [_ntgrGetIPv6ExtFragmentNextHeadType $ipv6ExtHdr]
      set frag_offset     [_ntgrGetIPv6ExtFragmentOffset $ipv6ExtHdr]
      set frag_id         [_ntgrGetIPv6ExtFragmentId $ipv6ExtHdr]
      set more_frag_flag  [_ntgrGetIPv6ExtMoreFragFlag $ipv6ExtHdr]

      stc::create  Ipv6ExtHeaderFragment    -under  $traffic    \
                   -nextHeaderType          $frag_next_type     \
                   -fragmentOffset          $frag_offset        \
                   -moreFragmentFlag        $more_frag_flag     \
                   -id                      $frag_id
      if {$frag_next_type == $no_next_head || $frag_next_type == $frag_head} {
	      return
      } else {
	      _spirentCreateIPv6ExtStream $frag_next_type $chassis_id $port $stream $traffic
      }	

    } elseif { $next_hdr == $rout_head } {
      set rout_next_type  [_ntgrGetIPv6ExtRoutingNextHeadType $ipv6ExtHdr]
      set rout_type       [_ntgrGetIPv6ExtRoutingType $ipv6ExtHdr]
      set rout_seg_left   [_ntgrGetIPv6ExtRoutingSegLeft $ipv6ExtHdr]
      stc::create  Ipv6ExtHeaderRouting     -under  $traffic    \
                   -nextHeaderType          $rout_next_type     \
                   -routingType             $rout_type          \
                   -segmentLeft             $rout_seg_left

      if { $rout_next_type == $no_next_head || $rout_next_type == $rout_head } {
	      return
      } else {
	      _spirentCreateIPv6ExtStream $rout_next_type $chassis_id $port $stream $traffic
      }

    } elseif { $next_hdr == $auth_head } {
      
      set auth_next_type  [_ntgrGetIPv6ExtAuthNextHeadType $ipv6ExtHdr]
      set auth_head_len   [_ntgrGetIPv6ExtAuthHeadLen $ipv6ExtHdr]
      set auth_data       [_ntgrGetIPv6ExtAuthdata $ipv6ExtHdr] 
      set auth_seq_num    [_ntgrGetIPv6ExtAuthSeqNumber $ipv6ExtHdr]
      set auth_spi        [_ntgrGetIPv6ExtAuthSPI $ipv6ExtHdr]
      stc::create  Ipv6ExtHeaderAuthentication     -under  $traffic     \
                   -nextHeaderType          $auth_next_type             \
                   -headerExtensionLen      $auth_head_len              \
                   -data                    $auth_data                  \
                   -seqNum                  $auth_seq_num               \
                   -spi                     $auth_spi
      if {$auth_next_type == $no_next_head || $auth_next_type == $auth_head} {
	      return
      } else {
	      _spirentCreateIPv6ExtStream $auth_next_type $chassis_id $port $stream $traffic
      }

    } elseif { $next_hdr == $esp_head } {
      set esp_next_type   [_ntgrGetIPv6ExtEspNextHeadType $ipv6ExtHdr]
      set esp_data        [_ntgrGetIPv6ExtEspData $ipv6ExtHdr]
      set esp_pad         [_ntgrGetIPv6ExtEspPad $ipv6ExtHdr]
      set esp_payload     [_ntgrGetIPv6ExtEspPayLoad $ipv6ExtHdr]
      set esp_seq_num     [_ntgrGetIPv6ExtEspSeqNum $ipv6ExtHdr]
      set esp_spi         [_ntgrGetIPv6ExtEspSpi   $ipv6ExtHdr]
      
      stc::create  Ipv6ExtHeaderEsp         -under  $traffic            \
                   -nextHeaderType          $esp_next_type              \
                   -data                    $esp_data                   \
                   -pad                     $esp_pad                    \
                   -payload                 $esp_payload                \
                   -seqNum                  $esp_seq_num                \
                   -spi                     $esp_spi
      if {$esp_next_type == $no_next_head || $esp_next_type == $esp_head} {
	      return
      } else {
	      _spirentCreateIPv6ExtStream $esp_next_type $chassis_id $port $stream $traffic
      }

    } else {
	
	    Netgear_log_file "lib-spirent.tcl" "ERROR - not support the ipv6 extension $next_hdr, set next head $no_next_head"
	    set next_hdr $no_next_head
	    return
    }
}

#*******************************************************************************
#
#  Function Name	: _spirentCreateTrafficTcpUdpStream
#
#  Description    : This function is called to create Tcp Udp stream header
#         
#  Usage          : _spirentCreateTrafficTcpUdpStream <tcp_udp_stream> <traffic> <protocol>
# 
#*******************************************************************************
proc _spirentCreateTrafficTcpUdpStream {tcp_udp_stream traffic protocol} {
	set dst_port [_ntgrGetTrafficStreamTcpUdpHeaderDstPort $tcp_udp_stream]
	set src_port [_ntgrGetTrafficStreamTcpUdpHeaderSrcPort $tcp_udp_stream]
	set src_port_cnt [_ntgrGetTrafficStreamTcpUdpHeaderSrcPortCnt $tcp_udp_stream]
	set dst_port_cnt [_ntgrGetTrafficStreamTcpUdpHeaderDstPortCnt $tcp_udp_stream]
	set dst_port_inc [_ntgrGetTrafficStreamTcpUdpHeaderDstPortInc $tcp_udp_stream]
	set src_port_inc [_ntgrGetTrafficStreamTcpUdpHeaderSrcPortInc $tcp_udp_stream]
  set tcp_seq [_ntgrGetTrafficStreamTcpUdpHeaderTcpSeq $tcp_udp_stream]
  
	Netgear_log_file "lib-spirent.tcl" "SRCPORT - $src_port, DSTPORT - $dst_port"
	Netgear_log_file "lib-spirent.tcl" "SRCPCNT - $src_port_cnt, DSTPCNT - $dst_port_cnt"
	Netgear_log_file "lib-spirent.tcl" "SRCPINC - $src_port_inc, DSTPINC - $dst_port_inc"

	if {$protocol == 6} {
		set L4_Header TcpHeader
	  set tcp_flags [_ntgrGetTrafficStreamTcpUdpHeaderTCPFlag $tcp_udp_stream]
#	  if {$tcp_flags == 0} {set tcp_flags 0x10}
		stc::create $L4_Header -under $traffic \
				    -dstPort	$dst_port \
				    -dstPortCnt	$dst_port_cnt \
				    -dstPortInc	$dst_port_inc \
				    -srcPort	$src_port \
				    -srcPortCnt	$src_port_cnt \
				    -srcPortInc	$src_port_inc \
				    -srcPortInc	$src_port_inc \
				    -flags $tcp_flags     \
				    -seq $tcp_seq
	} else {
		set L4_Header UdpHeader
		if {$protocol != 17} {Netgear_log_file "lib-spirent.tcl" "ERROR - Unknown Protocol. Default is taken to be UDP"}
		stc::create $L4_Header -under $traffic \
				    -dstPort	$dst_port \
				    -dstPortCnt	$dst_port_cnt \
				    -dstPortInc	$dst_port_inc \
				    -srcPort	$src_port \
				    -srcPortCnt	$src_port_cnt \
				    -srcPortInc	$src_port_inc \
				    -srcPortInc	$src_port_inc
	}
}

#*******************************************************************************
#
#  Function Name	: _spirentCreateTrafficIgmpStream
#
#  Description    : This function is called to create Igmp header
#         
#  Usage          : _spirentCreateTrafficIgmpStream <igmp_stream>
# 
#*******************************************************************************
proc _spirentCreateTrafficIgmpStream {igmp_stream} {
	set igmp_ver  [_ntgrGetTrafficStreamIgmpVer $igmp_stream]
	set igmp_type [_ntgrGetTrafficStreamIgmpType $igmp_stream]
	set igmp_maxresptime  [_ntgrGetTrafficStreamIgmpMaxResponse $igmp_stream]
	set igmp_gip  [_ntgrGetTrafficStreamIgmpGrpIP $igmp_stream]
		
	Netgear_log_file "lib-spirent.tcl" "IGMP-TYPE - $igmp_type, IGMP-MAXRESPTime - $igmp_maxresptime, IGMP-GIP - $igmp_gip"
	
	set igmp_pkt_h ""
	if {$igmp_ver == 2 || $igmp_ver == 1} {
      set igmp_type "0x[format "%02x" $igmp_type]"
      set igmp_maxresptime "0x[format "%02x" $igmp_maxresptime]"
      set igmp_checksum "0x00 0x00"
      set iplist {}
      foreach i [split $igmp_gip "."] {
          lappend iplist "0x[format "%02x" $i]"
      }
      set igmp_gip $iplist
      set igmp_pkt_data [concat $igmp_type $igmp_maxresptime $igmp_checksum $igmp_gip]
      
      set igmp_checksum [_doCheckSum $igmp_pkt_data]
      set igmp_pkt_h [lreplace $igmp_pkt_data 2 3 [lindex $igmp_checksum 0] [lindex $igmp_checksum 1]]
      regsub -all {0x} $igmp_pkt_h "" igmp_pkt_h
	}
	return $igmp_pkt_h
}

#*******************************************************************************
#
#  Function Name	: _spirentCreateTrafficIcmpStream
#
#  Description    : This function is called to create icmp header
#         
#  Usage          : _spirentCreateTrafficIcmpStream <icmp_stream>
# 
#*******************************************************************************
proc _spirentCreateTrafficIcmpStream {icmp_stream} {
  
    set icmp_type [_ntgrGetTrafficStreamIcmpType $icmp_stream]
    set icmp_code [_ntgrGetTrafficStreamIcmpCode $icmp_stream]
    set icmp_id   [_ntgrGetTrafficStreamIcmpId $icmp_stream]
    set icmp_seq  [_ntgrGetTrafficStreamIcmpSeq $icmp_stream]
    set icmp_data [_ntgrGetTrafficStreamIcmpData $icmp_stream]
    
    set icmp_pkt_h ""
    set icmp_type "0x[format "%02x" $icmp_type]"
    set icmp_code "0x[format "%02x" $icmp_code]"
    set icmp_checksum "0x00 0x00"
    set icmp_id_tmp [format "%04x" $icmp_id]
    set icmp_id "0x[string range $icmp_id_tmp 0 1] 0x[string range $icmp_id_tmp 2 3]"
    set icmp_seq_tmp [format "%04x" $icmp_seq]
    set icmp_seq "0x[string range $icmp_seq_tmp 0 1] 0x[string range $icmp_seq_tmp 2 3]"
    if {[llength $icmp_data] > 0} {
        foreach i $icmp_data {
            lappend icmp_data_list 0x$i
        }
        set icmp_data $icmp_data_list
    }
    
    Netgear_log_file "lib-spirent.tcl" "ICMP_TYPE - $icmp_type, ICMP_CODE - $icmp_code, ICMP_ID - $icmp_id, ICMP_SEQUENCE - $icmp_seq, ICMP_DATA - $icmp_data"
    set icmp_pkt_head [concat $icmp_type $icmp_code $icmp_checksum $icmp_id $icmp_seq $icmp_data]
    set icmp_checksum [_doCheckSum $icmp_pkt_head]
    set icmp_pkt_h [lreplace $icmp_pkt_head 2 3 [lindex $icmp_checksum 0] [lindex $icmp_checksum 1]]    
    regsub -all {0x} $icmp_pkt_h "" icmp_pkt_h

    return $icmp_pkt_h
}

#*******************************************************************************
#
#  Function Name	: _doCheckSum
#
#  Description    : This function is called to caculae checksum value for hex string
#         
#  Usage          : _doCheckSum <dataStr> 
#                   dataStr - "0x00 0x01 0x02 0x03 ..."
# 
#*******************************************************************************
proc _doCheckSum {dataStr} {
   set sum 0
   for {set i 0} {$i < [llength $dataStr]} {incr i 2} {
      set j [expr {$i + 1}]
      set h_octet [lindex $dataStr $i]
      set l_octet [lindex $dataStr $j]
      set 16bitword [expr {$h_octet * 0x100 + $l_octet}]
     
      set sum [expr {$sum + 0xFFFF - $16bitword}]
      }

   set hex_sum [format "%08x" $sum]
   set chksum [expr {"0x[string range $hex_sum 0 3]" + "0x[string range $hex_sum 4 7]"}]
   set chksum [format "%04x" $chksum]
   return "0x[string range $chksum 0 1] 0x[string range $chksum 2 3]"
}

#*******************************************************************************
#
#  Function Name	: _spirentCreateTrafficCustomStream
#
#  Description    : This function is called to create Custom stream header
#         
#  Usage          : _spirentCreateTrafficCustomStream <custom_stream> <traffic>
# 
#*******************************************************************************
proc _spirentCreateTrafficCustomStream {custom_stream traffic} {
	set data [_ntgrGetTrafficStreamCustomHeaderData $custom_stream]
	
	Netgear_log_file "lib-spirent.tcl" "CUSTOM DATA - $data"
	
	stc::create CustomHeader -under $traffic \
					 -data $data
}

#*******************************************************************************
#
#  Function Name	: _spirentClearCounter
#
#  Description    : This API is called to clear the counters from the traffic 
#			  Generators
#
#  Usage          : _spirentClearCounter <chassis_id>
# 
#*******************************************************************************
proc _spirentClearCounter {chassis_id} {
	set project_handle [_ntgrGetTGProjectHandle $chassis_id]
	set test_type [_ntgrGetTrafficTestType $chassis_id]

	Netgear_log_file "lib-spirent.tcl" "Clear the statistics before starting test"
	stc::perform Clear_Stats $project_handle

}

#*******************************************************************************
#
#  Function Name  : _spirentCreateSubInterface
#
#  Description    : This function is called to create subinterface on ethernet port.
#
#  Usage          : _spirentCreateSubInterface <chassis port>
#
#*******************************************************************************
proc _spirentCreateSubInterface { chassis port } {
    set hPort [_ntgrGetTrafficPortHandle $chassis $port]
    set subif_list [_ntgrGetSubIfList $chassis $port]
    foreach subif $subif_list {
        set vlanID     [lindex $subif 0]
        set ipAddr     [lindex $subif 1]
        set ipGW       [lindex $subif 2]
        set macSubif   [lindex $subif 3]
        set ipv6Addr   [lindex $subif 4]
        set ipv6GW     [lindex $subif 5]
        set ipv6Local  [lindex $subif 6]

        if {$ipAddr    == ""} {set ipAddr 10.10.[_rnd10To99].[_rnd10To99]}
        if {$ipGW      == ""} {set ipGW   10.10.[_rnd10To99].[_rnd10To99]}
        if {$macSubif  == ""} {set macSubif  00:ab:cd:[_rnd10To99]:[_rnd10To99]:[_rnd10To99]}
        if {$ipv6Addr  == ""} {set ipv6Addr  2222::abcd:[_rnd10To99]:[_rnd10To99]:[_rnd10To99]}
        if {$ipv6GW    == ""} {set ipv6GW    2222::abcd:[_rnd10To99]:[_rnd10To99]:[_rnd10To99]}
        if {$ipv6Local == ""} {set ipv6Local FE80::abcd:[_rnd10To99]:[_rnd10To99]:[_rnd10To99]}


        Netgear_log_file "lib-spirent.tcl" "SubInterface Info: vlanID = $vlanID, ipAddr = $ipAddr, ipGW = $ipGW, macSubif = $macSubif, ipv6Addr = $ipv6Addr, ipv6GW = $ipv6GW, ipv6Local = $ipv6Local"
        stc::create Vlan -under         $hPort     \
                         -vlanIdStr     $vlanID    \
                         -macAddr       $macSubif  \
                         -ipVer         IP_V4V6    \
                         -ipAddr        $ipAddr    \
                         -gateway       $ipGW      \
                         -gateway       $ipGW      \
    }
}

#*******************************************************************************
#
#  Function Name  : _spirentPortLinkDown
#
#  Description    : This function is called to set the port's status to down.
#
#  Usage          : _spirentPortLinkDown <tester_id port>
#
#*******************************************************************************
proc _spirentPortLinkDown { tester_id port } {
    set hPort [_ntgrGetTrafficPortHandle $tester_id $port]
    Netgear_log_file "lib-spirent.tcl" "Setting port=$port status to down."
    ::stc::perform Teardown_Link $hPort
}

#*******************************************************************************
#
#  Function Name  : _spirentPortLinkUP
#
#  Description    : This function is called to set the port's status to up.
#
#  Usage          : _spirentPortLinkUP <tester_id port>
#
#*******************************************************************************
proc _spirentPortLinkUP { tester_id port } {
    set hPort [_ntgrGetTrafficPortHandle $tester_id $port]
    Netgear_log_file "lib-spirent.tcl" "Setting port=$port status to up."
    ::stc::perform Setup_Link $hPort
}

#*******************************************************************************
#
#  Function Name  : _spirentUnLoadStream
#
#  Description    : This function is called to delete the streams created in the
#			  specified ports list.
#
#  Usage          : _spirentUnLoadStream <chassis_id> <port>
#
#*******************************************************************************
proc _spirentUnLoadStream { chassis_id portlist } {
   	set project_handle [_ntgrGetTGProjectHandle $chassis_id]
	foreach port $portlist {
		set streamHandles [_ntgrGetPortStreamHandle $chassis_id $port ]
		foreach streamHandle $streamHandles {
			Netgear_log_file "lib-spirent.tcl" "Unloading Stream from the tester - $chassis_id port - $port Stream Handle - $streamHandle"
			stc::delete $streamHandle
		}
		_ntgrClearPortStreamHandle $chassis_id $port
	}
	stc::apply $project_handle
}

#*******************************************************************************
#
#  Function Name  : _spirentLoadStream
#
#  Description    : This function is called to create the streams  in the
#			  specified ports list.
#
#  Usage          : _spirentLoadStream <chassis_id> <port>
#
#*******************************************************************************
proc _spirentLoadStream { chassis_id portlist } {
   	set project_handle [_ntgrGetTGProjectHandle $chassis_id]
	foreach port $portlist {
		set port_handle [_ntgrGetTrafficPortHandle $chassis_id $port]
		set stream_count [_ntgrGetTrafficPortStreamCount $chassis_id $port]
		for {set i 1} {$i <= $stream_count} {incr i}	{
			_spirentCreateTraffic $chassis_id $port $i $port_handle
		}
	}
	stc::apply $project_handle
}

#*******************************************************************************
#
#  Function Name  : _spirentDeletePort
#
#  Description    : This function is called to delete handle of port if it exists.
#
#  Usage          : _spirentDeletePort <tester_id pt>
#
#*******************************************************************************
proc _spirentDeletePort { tester_id pt } {
    set chassis [_ntgrGetTrafficPortChassisId $tester_id $pt]
    set slot [_ntgrGetTrafficPortSlotId $tester_id $pt]
    set port [_ntgrGetTrafficPortId $tester_id $pt]

    set hProject [_ntgrGetTGProjectHandle $tester_id]
    set children [stc::show -allchildren $hProject]
    foreach {type hdl} $children {
        if {$type == "ethernet"} {
            array set portAttr [stc::show -allAttr $hdl]
            if {$portAttr(location) == "$chassis,$slot,$port"} {
                stc::delete $hdl
                stc::apply $hProject
                break
            }
        }
    }
}

#*******************************************************************************
#
#  Function Name	: _spirentReportRatePerPort
#
#  Description    : This function return port rate info
#         
#  Usage          : _spirentReportRatePerPort <chassis_id> <port>
# 
#*******************************************************************************
proc _spirentReportRatePerPort {chassis_id port} {
	set port_handle [_ntgrGetTrafficPortHandle $chassis_id $port]
	set ratetx [stc::get txFrameRate -from $port_handle]
	set raterx [stc::get rxFrameRate -from $port_handle]
	Netgear_log_file "lib-spirent.tcl" "Port $port: Tr=$ratetx, Rr=$raterx"
	if {$ratetx < 1} {
	  set ratetx 1
	}
	return "$ratetx $raterx"
}

#*******************************************************************************
#
#  Function Name	: _spirentReportSigRatePerPort
#
#  Description    : This function return port rate info
#         
#  Usage          : _spirentReportSigRatePerPort <chassis_id> <port>
# 
#*******************************************************************************
proc _spirentReportSigRatePerPort {chassis_id port} {
	set port_handle [_ntgrGetTrafficPortHandle $chassis_id $port]
	set ratetx [stc::get txSigFrameRate -from $port_handle]
	set raterx [stc::get rxSigFrameRate -from $port_handle]
	Netgear_log_file "lib-spirent.tcl" "Port $port: Tr=$ratetx, Rr=$raterx"
	return "$ratetx $raterx"
}

#*******************************************************************************
#
#  Function Name  : _spirentReleasePort
#
#  Description    : This function is called to release a reserved port.
#
#  Usage          : _spirentReleasePort <tester_id port_list>
#
#*******************************************************************************
proc _spirentReleasePort { tester_id port_list } {    
    set chassis_handle [_ntgrGetTGHandle $tester_id]
    foreach pt $port_list {
        set slot [_ntgrGetTrafficPortSlotId $tester_id $pt]
        set port [_ntgrGetTrafficPortId $tester_id $pt]
        stc::release -ports "$chassis_handle,$slot,$port"

        Netgear_log_file "lib-spirent.tcl" "Release port=$chassis_handle,$slot,$port"
    }
}

#*******************************************************************************
#
#  Function Name  : _spirentModifyPortLoad
#
#  Description    : This function is called to modify total load of a port.
#
#  Usage          : _spirentModifyPortLoad <tester_id pt ld>
#
#*******************************************************************************
proc _spirentModifyPortLoad { tester_id pt ld} {
    set chassis [_ntgrGetTrafficPortChassisId $tester_id $pt]
    set slot [_ntgrGetTrafficPortSlotId $tester_id $pt]
    set port [_ntgrGetTrafficPortId $tester_id $pt]

    set hProject [_ntgrGetTGProjectHandle $tester_id]
    set children [stc::show -allchildren $hProject]
    foreach {type hdl} $children {
        if {$type == "ethernet"} {
            array set portAttr [stc::show -allAttr $hdl]
            if {$portAttr(location) == "$chassis,$slot,$port"} {
                stc::config $hdl -load $ld
                stc::apply $hProject
                break
            }
        }
    }
}

#*******************************************************************************
#
#  Function Name	: _spirentReportTxCount
#
#  Description    : This function returns tx frame count for a handle(port or stream)
#         
#  Usage          : _spirentReportTxCount <cnt_handle>
# 
#*******************************************************************************
proc _spirentReportTxCount {cnt_handle} {
	set txCnt [stc::get txFrameCnt -from $cnt_handle]
	##---- Added by kenddy for record stream name  ----##
	global stream_info
  foreach iHandle [lsort -di [array names stream_info]] {
    set streamName ""
    if {$cnt_handle == [lindex [split $iHandle ","] 2]} {
      set streamName $stream_info($iHandle)
      break
    }
  }
  ##----End of added by kenddy ----##
	Netgear_log_file "lib-spirent.tcl" "Tx Count for $cnt_handle -- $streamName : $txCnt"
	return $txCnt
}


#*******************************************************************************
#
#  Function Name	: _spirentReportRxCount
#
#  Description    : This function returns rx frame count for a handle(port or stream)
#         
#  Usage          : _spirentReportRxCount <cnt_handle>
# 
#*******************************************************************************
proc _spirentReportRxCount {cnt_handle} {
	set rxCnt [stc::get rxFrameCnt -from $cnt_handle]
	
	##---- Added by kenddy for record stream name  ----##
	global stream_info
  foreach iHandle [lsort -di [array names stream_info]] {
    set streamName ""
    if {$cnt_handle == [lindex [split $iHandle ","] 2]} {
      set streamName $stream_info($iHandle)
      break
    }
  }
  ##----End of added by kenddy ----##
	Netgear_log_file "lib-spirent.tcl" "Rx Count for $cnt_handle -- $streamName : $rxCnt"
	return $rxCnt
}

#*******************************************************************************
#
#  Function Name	: _spirentReportTxRate
#
#  Description    : This function returns tx frame Rate for a handle(port or stream)
#         
#  Usage          : _spirentReportTxRate <cnt_handle>
# 
#*******************************************************************************
proc _spirentReportTxRate {cnt_handle} {
	set txRate [stc::get txFrameRate -from $cnt_handle]
	Netgear_log_file "lib-spirent.tcl" "Tx Rate for $cnt_handle: $txRate"
	return $txRate
}


#*******************************************************************************
#
#  Function Name	: _spirentReportRxRate
#
#  Description    : This function returns rx frame Rate for a handle(port or stream)
#         
#  Usage          : _spirentReportRxRate <cnt_handle>
# 
#*******************************************************************************
proc _spirentReportRxRate {cnt_handle} {
	set rxRate [stc::get rxFrameRate -from $cnt_handle]
	Netgear_log_file "lib-spirent.tcl" "Rx Rate for $cnt_handle: $rxRate"
	return $rxRate
}

#*******************************************************************************
#
#  Function Name	: _spirentReportTxSigCount
#
#  Description    : This function returns tx frame count for a handle(port or stream)
#         
#  Usage          : _spirentReportTxSigCount <cnt_handle>
# 
#*******************************************************************************
proc _spirentReportTxSigCount {cnt_handle} {
	set txCnt [stc::get txSigFrameCnt -from $cnt_handle]
	Netgear_log_file "lib-spirent.tcl" "Tx Count for $cnt_handle: $txCnt"
	return $txCnt
}


#*******************************************************************************
#
#  Function Name	: _spirentReportRxSigCount
#
#  Description    : This function returns rx frame count for a handle(port or stream)
#         
#  Usage          : _spirentReportRxSigCount <cnt_handle>
# 
#*******************************************************************************
proc _spirentReportRxSigCount {cnt_handle} {
	set rxCnt [stc::get rxSigFrameCnt -from $cnt_handle]
	Netgear_log_file "lib-spirent.tcl" "Rx Count for $cnt_handle: $rxCnt"
	return $rxCnt
}

#*******************************************************************************
#
#  Function Name	: _spirentSetRxPorts
#
#  Description    : This function setting rxPortListIterator 
#         
#  Usage          : _spirentSetRxPorts <PortHandleLists> <TrafficHandle>
# 
#*******************************************************************************
proc _spirentSetRxPorts {PortHandleLists THandle} {
	foreach PHandle $PortHandleLists {
	  set iterator [stc::get rxPortListIterator -from $THandle]
	  stc::config $iterator -addData $PHandle
    stc::perform Add $iterator 
	  Netgear_log_file "lib-spirent.tcl" "Setting rxPortList for port $PHandle in Traffic $THandle"
	}
}

#*******************************************************************************
#
#  Function Name	: _spirentClearPortCounter
#
#  Description    : This function clear port counting 
#         
#  Usage          : _spirentClearPortCounter <PortHandleLists> 
# 
#*******************************************************************************
proc _spirentClearPortCounter {PortHandleLists} {
  foreach port_handle $PortHandleLists {
  	Netgear_log_file "lib-spirent.tcl" "Clear the statistics on port,port handle - $port_handle"
  	stc::perform Clear_PortCounter $port_handle
  }
}	