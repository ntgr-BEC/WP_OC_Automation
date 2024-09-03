#!/bin/sh
################################################################################   
#
#  File Name		: lib-ixia.tcl
#
#  Description       	:
#         This TCL contains functions to control ixia Test Center
#
#  Revision History 	:
#         Date         Programmer        Description
#        -----------------------------------------------------------------------
#        02-May-06     Rajeswari V       Created
#        08-Oct-06     Nina Cheng        Modified for RIP
#        29-Jan-07     Nina Cheng        Modified for OSPF
#
#
################################################################################
global ixia_exstreamId
set ixia_exstreamId 0

#*******************************************************************************
#
#  Function Name	: _ixiaConnect
#
#  Description    : This API is called to connect and initialise  ixia for 
#			  generating the traffic.
#         
#  Usage          : _ixiaConnect <chassis_id>
# 
#*******************************************************************************
proc _ixiaConnect {chassis_id} {
	set chassis_ip_addr [_ntgrGetTGIPAddr $chassis_id]
	Netgear_log_file "lib-ixia.tcl" "Initialize the automation framework"
	ixInitialize $chassis_ip_addr
	Netgear_log_file "lib-ixia.tcl" "Connecting to chassis $chassis_ip_addr"	
      Netgear_log_file "lib-ixia.tcl" "Connect to $chassis_ip_addr successful!"
      set chassisId 0
	set chassisId [ixGetChassisID $chassis_ip_addr]
	Netgear_log_file "lib-ixia.tcl" "Chassis ID - $chassisId"
	ixGlobalSetDefault
      return $chassisId
}

#*******************************************************************************
#
#  Function Name	: _ixiaLoadConfig
#
#  Description    : This API is called to load the configuration on the port
#         
#  Usage          : _ixiaLoadConfig <chassis_id> <port_list>
# 
#*******************************************************************************
proc _ixiaLoadConfig {chassis_id port_list} {
	Netgear_log_file "lib-ixia.tcl" "Configure the ports, load traffic and reserve the ports"
	_ixiaCreatePortAndLoadStream $chassis_id $port_list
}

#*******************************************************************************
#
#  Function Name	: _ixiaDisconnect
#
#  Description    : This API is called to disconnect from the traffic generator
#         
#  Usage          : _ixiaDisconnect <chassis_id>
# 
#*******************************************************************************
proc _ixiaDisconnect {chassis_id} {
	set chassis_ip_addr [_ntgrGetTGIPAddr $chassis_id]
	Netgear_log_file "lib-ixia.tcl" "Disconnecting chassis"
	ixDisconnectFromChassis $chassis_ip_addr
}

#*******************************************************************************
#
#  Function Name	: _ixiaReportTotalLossFrame
#
#  Description    : This function determines frame loss using port counters
#         
#  Usage          : _ixiaReportTotalLossFrame <chassis_id>
# 
#*******************************************************************************
proc _ixiaReportTotalLossFrame {chassis_id} {
	set total_tx 0
	set total_rx 0

	set chassis_ip_addr [_ntgrGetTGIPAddr $chassis_id]
	chassis get $chassis_ip_addr
	set chassis	  [chassis cget -id]
	set port_list [_ntgrGetTrafficTestPortList $chassis_id]
 	Netgear_log_file "lib-ixia.tcl" "Port List = $port_list on chassis $chassis_id"
	foreach pt $port_list {
		set slot    [_ntgrGetTrafficPortSlotId $chassis_id $pt]
		set port    [_ntgrGetTrafficPortId $chassis_id $pt]
		if [stat get statAllStats $chassis $slot $port] {
			Netgear_log_file "lib-ixia.tcl" "Failed to get statistics on Port $chassis $slot $port"
		} else {
			set tx [stat cget -framesSent]
			Netgear_log_file "lib-ixia.tcl" "TX on $chassis $slot $port = $tx"
			set rx [stat cget -framesReceived]
			Netgear_log_file "lib-ixia.tcl" "RX on $chassis $slot $port = $rx"
			set total_tx [expr $total_tx + $tx]
			set total_rx [expr $total_rx + $rx]
		}
	}
	set loss [expr $total_tx - $total_rx]
	if {$loss < 0} {
		set loss 0
	}
	Netgear_log_file "lib-ixia.tcl" "total TX = $total_tx  total RX = $total_rx   loss=$loss"
	return $loss
} 

#*******************************************************************************
#
#  Function Name	: _ixiaReportTotalFrameTx
#
#  Description    : This function retruns number of frames transmitted
#         
#  Usage          : _ixiaReportTotalFrameTx <chassis_id>
# 
#*******************************************************************************
proc _ixiaReportTotalFrameTx {chassis_id} {
	set total_tx 0
	set chassis_ip_addr [_ntgrGetTGIPAddr $chassis_id]
	chassis get $chassis_ip_addr
	set chassis	  [chassis cget -id]
	set port_list [_ntgrGetTrafficTestPortList $chassis_id]
 	Netgear_log_file "lib-ixia.tcl" "Port List = $port_list on chassis $chassis_id"
	foreach pt $port_list {
		set slot    [_ntgrGetTrafficPortSlotId $chassis_id $pt]
		set port    [_ntgrGetTrafficPortId $chassis_id $pt]
		if [stat get statAllStats $chassis $slot $port] {
			Netgear_log_file "lib-ixia.tcl" "Failed to get statistics on Port $chassis $slot $port"
		} else {
			set tx [stat cget -framesSent]
			Netgear_log_file "lib-ixia.tcl" "TX on $chassis $slot $port = $tx"
			set total_tx [expr $total_tx + $tx]
		}
	}
	Netgear_log_file "lib-ixia.tcl" "total TX = $total_tx "
	return $total_tx
} 

#*******************************************************************************
#
#  Function Name	: _ixiaReportTotalFrameRx
#
#  Description    : This function retruns number of frames Received
#         
#  Usage          : _ixiaReportTotalFrameRx <chassis_id>
# 
#*******************************************************************************
proc _ixiaReportTotalFrameRx {chassis_id} {
	set total_rx 0
	set chassis_ip_addr [_ntgrGetTGIPAddr $chassis_id]
	chassis get $chassis_ip_addr
	set chassis	  [chassis cget -id]
	set port_list [_ntgrGetTrafficTestPortList $chassis_id]
 	Netgear_log_file "lib-ixia.tcl" "Port List = $port_list on chassis $chassis_id"
	foreach pt $port_list {
		set slot    [_ntgrGetTrafficPortSlotId $chassis_id $pt]
		set port    [_ntgrGetTrafficPortId $chassis_id $pt]
		if [stat get statAllStats $chassis $slot $port] {
			Netgear_log_file "lib-ixia.tcl" "Failed to get statistics on Port $chassis $slot $port"
		} else {
			set rx [stat cget  -framesReceived]
			Netgear_log_file "lib-ixia.tcl" "RX on $chassis $slot $port = $rx"
			set total_rx [expr $total_rx + $rx]
		}
	}
	Netgear_log_file "lib-ixia.tcl" "total RX = $total_rx "
	return $total_rx
} 

#*******************************************************************************
#
#  Function Name	: _ixiaReportLossFramePerPort
#
#  Description    : This function determines frame loss using port counters
#         
#  Usage          : _ixiaReportLossFramePerPort <chassis_id> <tx_port> <rx_port>
# 
#*******************************************************************************
proc _ixiaReportLossFramePerPort {chassis_id tx_port rx_port} {
	set total_tx 0
	set total_rx 0
	
	set chassis_ip_addr [_ntgrGetTGIPAddr $chassis_id]
	chassis get $chassis_ip_addr
	set chassis	  [chassis cget -id]

	set tx_chassis $chassis
	set tx_slot    [_ntgrGetTrafficPortSlotId $chassis_id $tx_port]
	set tx_pt    [_ntgrGetTrafficPortId $chassis_id $tx_port]
	if [stat get statAllStats $tx_chassis $tx_slot $tx_pt] {
		Netgear_log_file "lib-ixia.tcl" "Failed to get statistics on Port $tx_chassis $tx_slot $tx_pt"
	} else {
		set tx [stat cget -framesSent]
		Netgear_log_file "lib-ixia.tcl" "TX on $tx_chassis $tx_slot $tx_pt = $tx"
	}

	set rx_chassis $chassis
	set rx_slot    [_ntgrGetTrafficPortSlotId $chassis_id $rx_port]
	set rx_pt    [_ntgrGetTrafficPortId $chassis_id $rx_port]
	if [stat get statAllStats $rx_chassis $rx_slot $rx_pt] {
		Netgear_log_file "lib-ixia.tcl" "Failed to get statistics on Port $rx_chassis $rx_slot $rx_pt"
	} else {
		set rx [stat cget -framesReceived]
		Netgear_log_file "lib-ixia.tcl" "RX on $rx_chassis $rx_slot $rx_pt = $rx"
	}
	set loss [expr $tx - $rx]
	if {$loss < 0} {
		set loss 0
	}
	Netgear_log_file "lib-ixia.tcl" "TX = $tx  RX = $rx   loss=$loss"
	return "$loss"
} 

#*******************************************************************************
#
#  Function Name	: _ixiaReportTxRxFramePerPort
#
#  Description    : This function returns number of frames transmitted and received 
#			  per port 
#         
#  Usage          : _ixiaReportTxRxFramePerPort <chassis_id> <port>
# 
#*******************************************************************************
proc _ixiaReportTxRxFramePerPort {chassis_id port} {
	set total_tx 0
	set total_rx 0
	
	set chassis_ip_addr [_ntgrGetTGIPAddr $chassis_id]
	chassis get $chassis_ip_addr
	set chassis	  [chassis cget -id]

	set tx_chassis $chassis
	set tx_slot    [_ntgrGetTrafficPortSlotId $chassis_id $port]
	set tx_pt    [_ntgrGetTrafficPortId $chassis_id $port]
	if [stat get statAllStats $tx_chassis $tx_slot $tx_pt] {
		Netgear_log_file "lib-ixia.tcl" "Failed to get statistics on Port $tx_chassis $tx_slot $tx_pt"
	} else {
		set tx [stat cget -framesSent]
		Netgear_log_file "lib-ixia.tcl" "TX on $tx_chassis $tx_slot $tx_pt = $tx"
	}

	set rx_chassis $chassis
	set rx_slot    [_ntgrGetTrafficPortSlotId $chassis_id $port]
	set rx_pt    [_ntgrGetTrafficPortId $chassis_id $port]
	if [stat get statAllStats $rx_chassis $rx_slot $rx_pt] {
		Netgear_log_file "lib-ixia.tcl" "Failed to get statistics on Port $rx_chassis $rx_slot $rx_pt"
	} else {
		set rx [stat cget -framesReceived]
		Netgear_log_file "lib-ixia.tcl" "RX on $rx_chassis $rx_slot $rx_pt = $rx"
	}
	Netgear_log_file "lib-ixia.tcl" "TX = $tx  RX = $rx "
	return "$tx $rx"
} 

#*******************************************************************************
#
#  Function Name	: _ixiaCreatePortAndLoadStream
#
#  Description    : This function loads the stream on the port
#         
#  Usage          : _ixiaCreatePortAndLoadStream <chassis_id> <port_list>
# 
#*******************************************************************************
proc _ixiaCreatePortAndLoadStream {chassis_id port_list} {

 
	set chassis_ip_addr [_ntgrGetTGIPAddr $chassis_id]
	chassis get $chassis_ip_addr
	set chassis	  [chassis cget -id]
	_ixiaTakeOwnership $chassis_id $port_list
 
	foreach pt $port_list {
  		set slot        [_ntgrGetTrafficPortSlotId $chassis_id $pt]
  		set port        [_ntgrGetTrafficPortId $chassis_id $pt]
  		set port_lists  [_ntgrGetTrafficTestPortList $chassis_id]
  		set ret         [lsearch -exact $port_lists $pt] 
  		
  		if {$ret<0} { _ntgrAppendTrafficTestPortList $chassis_id $pt} 
  		_ntgrSetTrafficPortHandle $chassis_id $pt "$chassis/$slot/$port/$chassis_id/$pt"
  		
  	  port reset $chassis $slot $port
  		port setDefault
  		set port_phymode [_ntgrGetTrafficPortPMode $chassis_id $pt]
  		switch -exact -- $port_phymode {
  		    Copper  {port setPhyMode portPhyModeCopper $chassis $slot $port}
          Fiber   {port setPhyMode portPhyModeFiber  $chassis $slot $port}
          default {port setPhyMode portPhyModeCopper $chassis $slot $port} 		   
  		}
  		
  		set speed [_ntgrGetTrafficPortSpeed $chassis_id $pt]
  	  set lduplex "advertise100FullDuplex advertise100HalfDuplex \
  	               advertise10FullDuplex advertise10HalfDuplex \
  	               advertise1000FullDuplex"
  		switch -exact -- $speed {
        10GLAN  { port config -portMode port10GigLanMode;
                  Netgear_log_file "lib-ixia.tcl" "portMode - 10GigLanMode";
                }
        10GWAN  { port config -portMode port10GigWanMode;
                  Netgear_log_file "lib-ixia.tcl" "portMode - 10GigWanMode";
                }
        default {
                  set duplex [_ntgrGetTrafficPortDuplex $chassis_id $pt];		
                  _ixiaSetTrafficPortDuplexSpeed duplex $speed;
                  set auto_neg [_ntgrGetTrafficPortAutoNeg $chassis_id $pt];
  		            _ixiaSetTrafficPortAutoNeg auto_neg;
  		            port config -speed 			            $speed;
  		            port config -autonegotiate 		      $auto_neg;
  		            if { $auto_neg == "true" } {
  		                foreach oneduplex $lduplex {
  		                      if {$oneduplex == $duplex} {
              		              port config -$duplex	true;
              		          } else {
              		              port config -$oneduplex	false;
              		          }
              		    }
              		}
              		port config -negotiateMasterSlave 	1;
                  Netgear_log_file "lib-ixia.tcl" "DuplexMode - $duplex, AutoNeg - $auto_neg, Speed - $speed";
                }
      }
  	
  		set flow_ctrl [_ntgrGetTrafficPortFlowCtrl $chassis_id $pt]
  		_ixiaSetTrafficPortFlowCtrl flow_ctrl		
  		set ipAddress [_ntgrGetTrafficPortIpAddress $chassis_id $pt]
  		set gwIpAddress [_ntgrGetTrafficPortGwIpAddress $chassis_id $pt]
  		set macAddress [_ntgrGetTrafficPortMacAddress $chassis_id $pt]
  
  		Netgear_log_file "lib-ixia.tcl" "Port - $port, Chassis - $chassis, Slot - $slot, flow-ctrl - $flow_ctrl mode - $port_phymode"
 	 
	  ##  modify by jim.xie begin
	  if {$flow_ctrl} {
          port config -flowControl         true
	      port config -advertiseAbilities  portAdvertiseSendAndOrReceive
	  } else {
	      port config -advertiseAbilities  portAdvertiseNone
	  }
	  ##  modify by jim.xie end
	  
      # Add by jason for support "1000M Port Capture Enable" start
      set ReceiveMode [_ntgrGetTrafficPortReceiveMode $chassis_id $pt $speed]
      port config -receiveMode $ReceiveMode
      # Add by jason for support "1000M Port Capture Enable" end
      
      port config -transmitMode portTxModeAdvancedScheduler
      port set $chassis $slot $port
  
  		ipAddressTable setDefault        
  		ipAddressTable set $chassis $slot $port
  
  		arpServer setDefault        
  		arpServer config -rate 1488095
  		arpServer set $chassis $slot $port
  
  		_ixiaProtocolInterConfig $chassis_id $pt
  
      set flag_ospf [llength [_ntgrGetRouterOSPFList $chassis_id $pt]]
      set flag_rip  [llength [_ntgrGetRouterRipList $chassis_id $pt]]
      # modify by jim.xie begin
      # after ixia upgrate to 8.01, IxNProtocol can't used.
      set flag_ospf 0
      set flag_rip 0
      # modify by jim.xie end
      if {$flag_ospf != 0} {
          global auto_path
          # modify by jim.xie for bug-1141 begain
          # lappend auto_path {C:/Program Files/Ixia/IxNProtocols/5.60.140.4-EB/TclScripts/Lib}
          package require registry
          set productKey    "HKEY_LOCAL_MACHINE\\SOFTWARE\\Ixia Communications\\IxNProtocols"
          set versionKey     [ registry keys $productKey ]
          set latestKey      [ lindex $versionKey end ]
          set mutliVKeyIndex [ lsearch $versionKey "Multiversion" ]
          if { $mutliVKeyIndex > 0 } {
              set latestKey   [ lindex $versionKey [ expr $mutliVKeyIndex - 1 ] ]
          }
          set installInfo    [ append productKey \\ $latestKey \\ InstallInfo ]    
          lappend auto_path "[ registry get $installInfo  HOMEDIR ]/TclScripts/Lib"
          #lappend auto_path {C:/Program Files/Ixia/IxNProtocols/6.30.370.16/TclScripts/Lib}
          # modify by jim.xie for bug-1141 end
          package require IxTclProtocol
          _ixiaLoadOSPFConfig $chassis_id $pt
      }
      if {$flag_rip != 0} {
          global auto_path
          # modify by jim.xie for bug-1141 begain
          # lappend auto_path {C:/Program Files/Ixia/IxNProtocols/5.60.140.4-EB/TclScripts/Lib}
          package require registry
          set productKey    "HKEY_LOCAL_MACHINE\\SOFTWARE\\Ixia Communications\\IxNProtocols"
          set versionKey     [ registry keys $productKey ]
          set latestKey      [ lindex $versionKey end ]
          set mutliVKeyIndex [ lsearch $versionKey "Multiversion" ]
          if { $mutliVKeyIndex > 0 } {
              set latestKey   [ lindex $versionKey [ expr $mutliVKeyIndex - 1 ] ]
          }
          set installInfo    [ append productKey \\ $latestKey \\ InstallInfo ]    
          lappend auto_path "[ registry get $installInfo  HOMEDIR ]/TclScripts/Lib"
          #lappend auto_path {C:/Program Files/Ixia/IxNProtocols/6.30.370.16/TclScripts/Lib}
          # modify by jim.xie for bug-1141 end

          package require IxTclProtocol
          _ixiaLoadRipConfig $chassis_id $pt
      }
  		
  		_ixiaCreateFilter $chassis_id $pt $chassis $slot $port
  		
  		set chassis_slot_port_list {}
  		lappend chassis_slot_port_list [list $chassis $slot $port]
  		ixWritePortsToHardware chassis_slot_port_list
  		
  		ixCheckLinkState chassis_slot_port_list
  		#ixTransmitPortArpRequest $chassis $slot $port
  		_ixiaCreateTraffic $chassis_id $pt $chassis $slot $port
  } 
  # end of foreach 
  # wait for 5 seconds,to take effect on ixia 
  sleep 5
}

#*******************************************************************************
#
#  Function Name	: _ixiaCreateTraffic
#
#  Description    : This function creates traffic on the port
#
#  Usage          : _ixiaCreateTraffic <chassis_id> <pt> <chassis> <slot> <port>
# 
#*******************************************************************************
proc _ixiaCreateTraffic {chassis_id pt chassis slot port} {
	Netgear_log_file "lib-ixia.tcl" "About to create traffic"
	
	global ixia_exstreamId
	global ixia_tcp_offset
	set protocol {}
	set enable_pgid 0
	set ixia_tcp_offset 0
	set defaultpsigOffset 104
	set defaultpgidOffset 108
	
	
	set ixia_add_psig_offset  [_ntgrGetTrafficPortAddIxiaSig  $chassis_id $pt]
	set ixia_add_pgid_offset  [_ntgrGetTrafficPortAddIxiaPgid $chassis_id $pt]
	set ixia_min_psig_offset  [_ntgrGetTrafficPortMinIxiaSig  $chassis_id $pt]
	set ixia_min_pgid_offset  [_ntgrGetTrafficPortMinIxiaPgid $chassis_id $pt]
	if {$ixia_add_psig_offset !=0} {
	    set psigOffset [expr $defaultpsigOffset + $ixia_add_psig_offset]
	} elseif {$ixia_min_psig_offset !=0} {
	    set psigOffset [expr $defaultpsigOffset - $ixia_min_psig_offset]
	} else {
	    set psigOffset  $defaultpsigOffset
	}
	if {$ixia_add_pgid_offset !=0} {
	    set pgidOffset [expr $defaultpgidOffset + $ixia_add_pgid_offset]
	} elseif {$ixia_min_pgid_offset !=0} {
	    set pgidOffset [expr $defaultpgidOffset - $ixia_min_pgid_offset]
	} else {
	    set pgidOffset  $defaultpgidOffset
	}
  _ixiaCreatePacketGroup
	
	set noOfStream [ _ntgrGetTrafficPortStreamCount $chassis_id $pt ]
	if {$noOfStream > 64} { Netgear_log_file "lib-ixia.tcl" "Warning stream count exceed spirent limits"}
	for {set streamId 1}  { $streamId <= $noOfStream }  { incr streamId } { 
		set l2_stream      [_ntgrGetTrafficStreamL2Header $chassis_id $pt $streamId ]
		set vlan_stream    [_ntgrGetTrafficStreamVlanHeader $chassis_id $pt $streamId]
		set l3_stream      [_ntgrGetTrafficStreamL3Header $chassis_id $pt $streamId]
		set ipv6_stream    [_ntgrGetIPv6Hdr $chassis_id $pt $streamId]
		set tcp_udp_stream [_ntgrGetTrafficStreamTcpUdpHeader $chassis_id $pt $streamId]
		set igmp_stream    [_ntgrGetTrafficStreamIgmpHeader $chassis_id $pt $streamId]
		set icmp_stream    [_ntgrGetTrafficStreamIcmpHeader $chassis_id $pt $streamId]
		set custom_stream  [_ntgrGetTrafficStreamCustomHeader $chassis_id $pt $streamId]
		
		set ethtype    [_ntgrGetTrafficStreamL2HeaderEthernetType $l2_stream]
		set frame_size [_ntgrGetTrafficStreamFrameSize $chassis_id $pt $streamId]
		set frame_pad  [_ntgrGetTrafficStreamFramePAD $chassis_id $pt $streamId]
		#add by jim.xie for scalability case-013 begin
		set l3arp_stream      [_ntgrGetTrafficStreamL3ARPHeader $chassis_id $pt $streamId]
		#add by jim.xie for scalability case-013 end
		
    if {[llength $custom_stream] == 0 && $frame_size > [expr $pgidOffset + 4]} {  
  	   set enable_pgid 1 
  	}
  	if {[llength $custom_stream] != 0 && $frame_pad!="00-00"} {
  	   set enable_pgid 1
  	}
  	_ixiaCreateStream $chassis_id $pt $chassis $slot $port $streamId   
		set pgId [expr $streamId + $ixia_exstreamId]
		set stream_handle "s$pgId "
		_ntgrAppendPortStreamHandle $chassis_id $pt $stream_handle
		Netgear_log_file "lib-ixia.tcl" "Add stream $streamId streamhandle $stream_handle"
		_ixiaSetStreamInfo $pgId "$chassis/$slot/$port/$chassis_id/$ixia_exstreamId/$pt"			
		if {$ethtype != {}} {
		  set mac_type [_ntgrdeletehexprefix $ethtype]
		} else {
		    set mac_type "0800"
		}
		if {[llength $l2_stream] != 0} {
		  protocol	     	setDefault
		  protocol config -ethernetType ethernetII
      
		  set ether_type_offset 12	  
		       #    leave below code for reference  
    			 #    protocol config -ethernetType protocolOffsetType
        	 #		protocolOffset 	setDefault
        	 #	  protocolOffset  config -userDefinedTag $mac_type
        	 #		protocolOffset 	set $chassis $slot $port
			_ixiaCreateTrafficL2Stream $l2_stream
			if {[llength $vlan_stream] != 0} {
				_ixiaCreateTrafficVlanStream $vlan_stream
				vlan set $chassis $slot $port
			}
			#_ixiaSetEthernetTypeByUDF1 $ether_type_offset $mac_type
			#_ixiaSetIcmpv6ByUDF2
			# Create IPv6 Stream
		  Netgear_log_file "lib-ixia.tcl" "Check ipV6 header if or not existed"
  		
  		
  		if {[llength $ipv6_stream] != 0 && $ethtype == 0x86DD} {
          _ixiaBuildIpv6stream
        	if {[ipV6 set $chassis $slot $port]} {
              Netgear_log_file "lib-ixia.tcl" "fail: create ipV6 header"
          } else {
              Netgear_log_file "lib-ixia.tcl" "success: create ipV6 header"
          }    
      }
      #Create IP Stream
      Netgear_log_file "lib-ixia.tcl" "Check ip header if or not existed"
			if { [llength $l3_stream] != 0 } {
  			_ixiaCreateTrafficL3Stream $l3_stream	
  			set protocol [_ntgrGetStreamL3Protocol $l3_stream]
  		}
	  #add by jim.xie for scalability case-013 begin
	  #Create ARP Stream
	  if { [llength $l3arp_stream] != 0 } {
        _ixiaCreateTrafficL3ArpStream $l3arp_stream	
      }
	  #add by jim.xie for scalability case-013 end
      if { [llength $icmp_stream] != 0 && $protocol==1} {
      	_ixiaCreateTrafficIcmpStream $icmp_stream 
        if {[icmp set $chassis $slot $port]} {
            Netgear_log_file "lib-ixia.tcl" "Fail - Add ICMP header"
        } else {
            Netgear_log_file "lib-ixia.tcl" "Success - Add ICMP header"
        }
      }
      if { [llength $icmp_stream] != 0 && $protocol==58} {
          _ixiaCreateTrafficIcmpv6Stream $icmp_stream 
          if {[icmpV6 set $chassis $slot $port]} {
              Netgear_log_file "lib-ixia.tcl" "Fail - Add ICMPV6 header"
          } else {
              Netgear_log_file "lib-ixia.tcl" "Success - Add ICMPV6 header"
          }
      }
	  
      if { [llength $igmp_stream] != 0} {
      	_ixiaCreateTrafficIgmpStream $igmp_stream
      		if {[igmp set $chassis $slot $port]} {
      		    Netgear_log_file "lib-ixia.tcl" "Fail - Add IGMP header"
      		} else {
      		    Netgear_log_file "lib-ixia.tcl" "Success - Add IGMP header"
      		}
      }
			if {[llength $tcp_udp_stream] != 0} {
					_ixiaBuildTcpUdpstream
			}
    	if {[llength $custom_stream] != 0} {  
    		_ixiaCreateTrafficCustomStream $custom_stream 
    	}		 
		}
		
		if { [llength $l3_stream] != 0 } {
  		if {[ip set $chassis $slot $port]} {
  		 Netgear_log_file "lib-ixia.tcl" "fail: create ipV4 header"
      } else {
          Netgear_log_file "lib-ixia.tcl" "success: create ipV4 header"
      }
  	}
  
	# add by jim.xie for scalability begin
	if { [llength $l3arp_stream] != 0 } {
  		if {[arp set $chassis $slot $port]} {
  		 Netgear_log_file "lib-ixia.tcl" "fail: create ARP header"
      } else {
          Netgear_log_file "lib-ixia.tcl" "success: create ARP header"
      }
  	}
    stream config -enableSourceInterface              $::true
    stream config -sourceInterfaceDescription         "PortocolInterface"
	# add by jim.xie for scalability end
  	ipAddressTable set $chassis $slot $port
		if [stream set $chassis $slot $port $streamId] {
		    Netgear_log_file "lib-ixia.tcl" "Error in stream set"
		}  
	
		if {$enable_pgid} {
    		packetGroup config -groupId  $pgId
    		if [packetGroup setTx  $chassis $slot $port $streamId] {
    		   Netgear_log_file "lib-ixia.tcl" "Error in packetGroup setTx"
    		}	
		}
		Netgear_log_file "lib-ixia.tcl" "setting packetGroup setTx  $chassis $slot $port $streamId"
	  if [stream write $chassis $slot $port $streamId] {
	     Netgear_log_file "lib-ixia.tcl" "Error in stream write"
	  }
	}
	
	#end for
	
	# add by jim.xie 2015/07/23 begin
	# add Item <TRAFFIC_GROUP_RECEIVE_OFFSET> to Wide Packet Groups signature offset of Receive Mode 
	set signature_offset [_ntgrGetTrafficPortGroupReceiveModeOffset $chassis_id $pt]
	if {$signature_offset != 0} {
	    set groupId_offset [expr $signature_offset + 4]
	    _ixiaCreatePacketGroupRX
	}
	# add by jim.xie 2015/07/23 end

	set ixia_exstreamId [expr $noOfStream + $ixia_exstreamId]
	if [packetGroup setRx  $chassis $slot $port] {
		   Netgear_log_file "lib-ixia.tcl" "Error in packetGroup setRx"
	}	
	
	set chassis_slot_port_list {}
	lappend chassis_slot_port_list [list $chassis $slot $port]
	ixWriteConfigToHardware chassis_slot_port_list
	Netgear_log_file "lib-ixia.tcl" "ixiaCreateTraffic done"
	#Check setting for packetgroup rx
  if [packetGroup getRx  $chassis $slot $port] {
     Netgear_log_file "lib-ixia.tcl" "Error in packetGroup getRx"
  }
  ixStartPortPacketGroups $chassis $slot $port
  Netgear_log_file "lib-ixia.tcl" "ixia start packetGroup on $chassis $slot $port"
} 

#*******************************************************************************
#
#  Function Name	: _ixiaStartTraffic
#
#  Description    : This API is called to start the traffic on all the ports
#         
#  Usage          : _ixiaStartTraffic <chassis_id>
# 
#*******************************************************************************
proc _ixiaStartTraffic {chassis_id} {
	set chassis_ip_addr [_ntgrGetTGIPAddr $chassis_id]
	chassis get $chassis_ip_addr
	set chassis	  [chassis cget -id]

	set chassis_slot_port_list {}
	set port_list [_ntgrGetTrafficTestPortList $chassis_id]
	foreach pt $port_list {
		set slot [_ntgrGetTrafficPortSlotId $chassis_id $pt]
		set port [_ntgrGetTrafficPortId $chassis_id $pt]
		lappend chassis_slot_port_list [list $chassis $slot $port]
	}
 	Netgear_log_file "lib-ixia.tcl" "Port List $chassis_slot_port_list"
	ixClearStats {chassis_slot_port_list}
	if [ixStartTransmit chassis_slot_port_list] {
	    	Netgear_log_file "lib-ixia.tcl" "Error : Cannot Start traffic on Ixia Chassis $chassis_id"
	} 
}

#*******************************************************************************
#
#  Function Name	: _ixiaStartTrafficPerPort
#
#  Description    : This API is called to start the traffic on the given port
#         
#  Usage          : _ixiaStartTrafficPerPort <chassis_id> <port_list>
# 
#*******************************************************************************
proc _ixiaStartTrafficPerPort {chassis_id port_list} {
	set chassis_slot_port_list {}
	set chassis_ip_addr [_ntgrGetTGIPAddr $chassis_id]
	chassis get $chassis_ip_addr
	set chassis	  [chassis cget -id]
	foreach port $port_list {
	    set slot [_ntgrGetTrafficPortSlotId $chassis_id $port]
    	set pt [_ntgrGetTrafficPortId $chassis_id $port]
    	lappend chassis_slot_port_list [list $chassis $slot $pt]
     	Netgear_log_file "lib-ixia.tcl" "Port = $chassis_slot_port_list"
    	if [ixStartPortTransmit $chassis $slot $pt] {
	    	Netgear_log_file "lib-ixia.tcl" "Error : Cannot Start traffic on Ixia Chassis $chassis_id"
	    	exit
	    } 
	    Netgear_log_file "lib-ixia.tcl" "Success :  Start traffic on Ixia Chassis $chassis_id $slot $pt"
	}
}

#*******************************************************************************
#
#  Function Name	: _ixiaStopTraffic
#
#  Description    : This API is called to stop the traffic on all ports
#         
#  Usage          : _ixiaStopTraffic <chassis_id>
# 
#*******************************************************************************
proc _ixiaStopTraffic {chassis_id} {
	set chassis_ip_addr [_ntgrGetTGIPAddr $chassis_id]
	chassis get $chassis_ip_addr
	set chassis	  [chassis cget -id]

	set chassis_slot_port_list {}
	set port_list [_ntgrGetTrafficTestPortList $chassis_id]

	foreach pt $port_list {
		set slot [_ntgrGetTrafficPortSlotId $chassis_id $pt]
		set port [_ntgrGetTrafficPortId $chassis_id $pt]
		lappend chassis_slot_port_list [list $chassis $slot $port]
	}

 	Netgear_log_file "lib-ixia.tcl" "Port List $chassis_slot_port_list"
	ixStopTransmit {chassis_slot_port_list}
}

#*******************************************************************************
#
#  Function Name	: _ixiaStopTrafficPerPort
#
#  Description    : This API is called to start the traffic on the given port
#         
#  Usage          : _ixiaStopTrafficPerPort <chassis_id> <port_list>
# 
#*******************************************************************************
proc _ixiaStopTrafficPerPort {chassis_id port_list} {
	set chassis_slot_port_list {}
	set chassis_ip_addr [_ntgrGetTGIPAddr $chassis_id]
	chassis get $chassis_ip_addr
	set chassis	  [chassis cget -id]
	foreach port $port_list {
  	set slot [_ntgrGetTrafficPortSlotId $chassis_id $port]
  	set pt [_ntgrGetTrafficPortId $chassis_id $port]
  	lappend chassis_slot_port_list [list $chassis $slot $pt]  
   	Netgear_log_file "lib-ixia.tcl" "Port = $chassis_slot_port_list"
  	#ixStopTransmit {chassis_slot_port_list}
  	ixStopPortTransmit $chassis $slot $pt
  	 Netgear_log_file "lib-ixia.tcl" "Success :  Stop traffic on Ixia Chassis $chassis_id $slot $pt"
	}
}

#*******************************************************************************
#
#  Function Name	: _ixiaCreateTrafficL2Stream
#
#  Description    : This function is called to create L2 stream header
#         
#  Usage          : _ixiaCreateTrafficL2Stream <l2_stream>
# 
#*******************************************************************************
proc _ixiaCreateTrafficL2Stream {l2_stream} {
	uplevel 1 {
        set dstMode [_ntgrGetStreamDstMacMode $l2_stream]
        set srcMode [_ntgrGetStreamSrcMacMode $l2_stream]
        set dstStep [_ntgrGetStreamDstMacStep $l2_stream]
        set srcStep [_ntgrGetStreamSrcMacStep $l2_stream]
        set dstAmnt [_ntgrGetStreamDstMacAmount $l2_stream]
        set srcAmnt [_ntgrGetStreamSrcMacAmount $l2_stream]
        set dstMask [_ntgrGetStreamDstMacMask $l2_stream]
        set srcMask [_ntgrGetStreamSrcMacMask $l2_stream]
        set dstSelectMask [_ntgrGetStreamDstMacSelectMask $l2_stream]
        set srcSelectMask [_ntgrGetStreamSrcMacSelectMask $l2_stream]  
        set dstMac  [_ntgrGetTrafficStreamL2HeaderDstMacAddr $l2_stream]
        set srcMac  [_ntgrGetTrafficStreamL2HeaderSrcMacAddr $l2_stream]
       
        Netgear_log_file "lib-ixia.tcl" "DST_MAC_INFO: Mode - $dstMode, MAC - $dstMac, Step - $dstStep, Amount - $dstAmnt, Mask - $dstMask, SelectMask - $dstSelectMask"
        Netgear_log_file "lib-ixia.tcl" "SRC_MAC_INFO: Mode - $srcMode, MAC - $srcMac, Step - $srcStep, Amount - $srcAmnt, Mask - $srcMask, SelectMask - $srcSelectMask"
         
        stream config -da                    $dstMac
        stream config -daRepeatCounter       "daArp"
        stream config -daStep                $dstStep
        stream config -numDA                 $dstAmnt
        stream config -daMaskValue           $dstMask
        stream config -daMaskSelect          $dstSelectMask
        
        
        stream config -sa                    $srcMac
        stream config -saRepeatCounter       $srcMode
        stream config -saStep                $srcStep
        stream config -numSA                 $srcAmnt
        stream config -saMaskValue           $srcMask
        stream config -saMaskSelect          $srcSelectMask
        
        stream config -numBursts              100  
    
        Netgear_log_file "lib-ixia.tcl" "_ixiaCreateTrafficL2Stream finished"
	}
}

#*******************************************************************************
#
#  Function Name	: _ixiaCreateTrafficVlanStream
#
#  Description    : This function is called to create VLAN stream header
#         
#  Usage          : _ixiaCreateTrafficVlanStream <vlan_stream>
# 
#*******************************************************************************
proc _ixiaCreateTrafficVlanStream {vlan_stream} {
	uplevel {
        protocol config -enable802dot1qTag true
        set vlan2ID       [_ntgrGetStreamVlan2ID $vlan_stream]
        if {$vlan2ID == 0} {
            protocol config -enable802dot1qTag vlanSingle
            set vlanMode     [_ntgrGetStreamVlanMode $vlan_stream]
            set vlanID       [_ntgrGetStreamVlanID $vlan_stream]
            set vlanStep     [_ntgrGetStreamVlanStep $vlan_stream]
            set vlanRepeat   [_ntgrGetStreamVlanRepeat $vlan_stream]
            set vlanPrio     [_ntgrGetStreamVlanPriority $vlan_stream]
            set vlanCFI      [_ntgrGetStreamVlanCFI $vlan_stream]
            set vlanBitmask  [_ntgrGetStreamVlanBitmask $vlan_stream]
            Netgear_log_file "lib-ixia.tcl" "VLAN_INFO: Mode - $vlanMode, ID - $vlanID, Step - $vlanStep, Repeat - $vlanRepeat, Priority - $vlanPrio, CFI - $vlanCFI, Bitmask - $vlanBitmask"
            
            vlan setDefault
            vlan config -mode           $vlanMode
            vlan config -vlanID         $vlanID
            vlan config -step           $vlanStep
            vlan config -repeat         $vlanRepeat
            vlan config -cfi            $vlanCFI
            vlan config -userPriority   $vlanPrio
            if {$vlanBitmask !=0 } {vlan config -maskval        $vlanBitmask}
            set ether_type_offset [expr $ether_type_offset + 4]
            vlan set $chassis $slot $port
        } else {
            
            protocol config -enable802dot1qTag vlanStacked
            set vlanMode     [_ntgrGetStreamVlanMode $vlan_stream]
            set vlanID       [_ntgrGetStreamVlanID $vlan_stream]
            set vlanStep     [_ntgrGetStreamVlanStep $vlan_stream]
            set vlanRepeat   [_ntgrGetStreamVlanRepeat $vlan_stream]
            set vlanPrio     [_ntgrGetStreamVlanPriority $vlan_stream]
            set vlanCFI      [_ntgrGetStreamVlanCFI $vlan_stream]
            set vlanBitmask  [_ntgrGetStreamVlanBitmask $vlan_stream]
            Netgear_log_file "lib-ixia.tcl" "VLAN_INFO: Mode - $vlanMode, ID - $vlanID, Step - $vlanStep, Repeat - $vlanRepeat, Priority - $vlanPrio, CFI - $vlanCFI, Bitmask - $vlanBitmask"
            
            set vlan2Mode     [_ntgrGetStreamVlan2Mode $vlan_stream]
            set vlan2ID       [_ntgrGetStreamVlan2ID $vlan_stream]
            set vlan2Step     [_ntgrGetStreamVlan2Step $vlan_stream]
            set vlan2Repeat   [_ntgrGetStreamVlan2Repeat $vlan_stream]
            set vlan2Prio     [_ntgrGetStreamVlan2Priority $vlan_stream]
            set vlan2CFI      [_ntgrGetStreamVlan2CFI $vlan_stream]
            set vlan2Bitmask  [_ntgrGetStreamVlan2Bitmask $vlan_stream]
            Netgear_log_file "lib-ixia.tcl" "VLAN2_INFO: Mode - $vlan2Mode, ID - $vlan2ID, Step - $vlan2Step, Repeat - $vlan2Repeat, Priority - $vlan2Prio, CFI - $vlan2CFI, Bitmask - $vlan2Bitmask"
            
            stackedVlan setDefault
            vlan setDefault
            vlan config -mode           $vlanMode
            vlan config -vlanID         $vlanID
            vlan config -step           $vlanStep
            vlan config -repeat         $vlanRepeat
            vlan config -cfi            $vlanCFI
            vlan config -userPriority   $vlanPrio
            if {$vlanBitmask !=0 } {vlan config -maskval  $vlanBitmask}
            set ether_type_offset [expr $ether_type_offset + 4]
            stackedVlan setVlan 1
            
            vlan setDefault
            vlan config -mode           $vlan2Mode
            vlan config -vlanID         $vlan2ID
            vlan config -step           $vlan2Step
            vlan config -repeat         $vlan2Repeat
            vlan config -cfi            $vlan2CFI
            vlan config -userPriority   $vlan2Prio
            if {$vlanBitmask !=0 } {vlan config -maskval $vlan2Bitmask}
            set ether_type_offset [expr $ether_type_offset + 4]
            stackedVlan setVlan 2
            stackedVlan set $chassis $slot $port 
        }
	}
}

#*******************************************************************************
#
#  Function Name	: _ixiaCreateTrafficL3Stream
#
#  Description    : This function is called to create L3 stream header
#         
#  Usage          : _ixiaCreateTrafficL3Stream <l3_stream>
# 
#*******************************************************************************
proc _ixiaCreateTrafficL3Stream {l3_stream} {
	uplevel 1 {
        set dstMode         [_ntgrGetStreamL3DstIpMode $l3_stream]
        set dstIP           [_ntgrGetStreamL3DstIpAddr $l3_stream]
        set dstMask         [_ntgrGetStreamL3DstIpMask $l3_stream]
        set dstCnt          [_ntgrGetStreamL3DstIpRepeatCnt $l3_stream]
        if {$dstCnt == 0} {set dstCnt [_ntgrGetTrafficStreamL3HeaderDstIPCnt $l3_stream]}
        set dstClass        [_ntgrGetStreamL3DstIpClass $l3_stream]
        set srcMode         [_ntgrGetStreamL3SrcIpMode $l3_stream]
        set srcIP           [_ntgrGetStreamL3SrcIpAddr $l3_stream]
        set srcMask         [_ntgrGetStreamL3SrcIpMask $l3_stream]
        set srcCnt          [_ntgrGetStreamL3SrcIpRepeatCnt $l3_stream]
        if {$dstCnt == 0} {set dstCnt [_ntgrGetTrafficStreamL3HeaderSrcIPCnt $l3_stream]}
        set srcClass        [_ntgrGetStreamL3SrcIpClass $l3_stream]
        set ipProtocol      [_ntgrGetStreamL3Protocol $l3_stream]
        if {$ipProtocol == 0} {
             set ipProtocol [_ntgrGetTrafficStreamL3HeaderProtocol $l3_stream]
        }
        if {[llength $tcp_udp_stream] == 0} {
           if {$ipProtocol ==6 ||$ipProtocol==17} {
               _ixiaSetL4ProtocolByUDF2 $ipProtocol $ether_type_offset 
               set ipProtocol 255
           }
        }
        set ipTOS           [_ntgrGetStreamL3TOS $l3_stream]
        set ipTOS [_ntgrdec2bin $ipTOS 2]
        set ipTOS [_ntgraddzeroleft $ipTOS 8]
        set ipTTL           [_ntgrGetStreamL3TTL $l3_stream]
        set ipID            [_ntgrGetStreamL3Identification $l3_stream]
        if {$ipID  == 0} {set ipID 0} 
        set ipFlag          [_ntgrGetStreamL3Flag $l3_stream]
        if {$ipFlag == 0} {
            set ipFlag [_ntgrGetTrafficStreamL3HeaderFlag $l3_stream] 
        }
        set ipFlag [_ntgraddzeroleft $ipFlag 3]
        set ipOffset        [_ntgrGetStreamL3Offset $l3_stream]
        if {$ipOffset ==0 } {set ipOffset [_ntgrGetTrafficStreamL3HeaderFragOffset $l3_stream]}
        set ipChecksum      [_ntgrIfValidChecksum $l3_stream]
        set ipGateway       [_ntgrGetStreamL3Gateway $l3_stream]
        if {$ipGateway == 0} {set ipGateway [_ntgrGetTrafficStreamL3HeaderGwIP $l3_stream]}
        
		# add by jim.xie for scalability cases begin
		# add IP_OPTIONS to add ip options&padding info
		set ipOptions  [_ntgrGetStreamL3Options $l3_stream]
		# add by jim.xie for scalability cases end
        Netgear_log_file "lib-ixia.tcl" "DST_IP_INFO: Mode - $dstMode, IP - $dstIP, Mask - $dstMask, RepeatCount - $dstCnt, Class - $dstClass"
        Netgear_log_file "lib-ixia.tcl" "SRC_IP_INFO: Mode - $srcMode, IP - $srcIP, Mask - $srcMask, RepeatCount - $srcCnt, Class - $srcClass"
        Netgear_log_file "lib-ixia.tcl" "IP_HDR_INFO: Protocol - $ipProtocol, TTL - $ipTTL, Identification - $ipID, TOS - $ipTOS, useValidChecksum - $ipChecksum, Flag - $ipFlag, fragmentOffset - $ipOffset"

  
  		protocol config -name 			ipV4
        
        ip setDefault
        ip config -destIpAddr                             $dstIP
        ip config -destIpMask                             $dstMask
        ip config -destIpAddrMode                         $dstMode
        ip config -destIpAddrRepeatCount                  $dstCnt
        ip config -destClass                              $dstClass
        ip config -sourceIpAddr                           $srcIP
        ip config -sourceIpMask                           $srcMask
        ip config -sourceIpAddrMode                       $srcMode
        ip config -sourceIpAddrRepeatCount                $srcCnt
        ip config -sourceClass                            $srcClass
        ip config -ipProtocol                             $ipProtocol
        ip config -ttl                                    $ipTTL
        ip config -identifier                             $ipID
        ip config -fragmentOffset                         $ipOffset
        ip config -useValidChecksum                       true
        if { [string length $ipTOS] != 8 } {
            Netgear_log_file "lib-ixia.tcl" "Error configuration: TOS value should be 8 bytes long and have a format like '01010101', using default."
        } else {
            set ipPrecedence [expr [string index $ipTOS 0]*4 + [string index $ipTOS 1]*2 + [string index $ipTOS 2]]
            ip config -precedence                         $ipPrecedence
            ip config -delay                              [string index $ipTOS 3]
            ip config -throughput                         [string index $ipTOS 4]
            ip config -reliability                        [string index $ipTOS 5]
            ip config -cost                               [string index $ipTOS 6]
            ip config -reserved                           [string index $ipTOS 7]
        }
        if { [string length $ipFlag] != 3 } {
            Netgear_log_file "lib-ixia.tcl" "Error configuration: IP flag value should be 3 bytes long and have a format like '010', using default."
        } else {
            ip config -fragment                           [string index $ipFlag 1]
            ip config -lastFragment                       [string index $ipFlag 2]
        } 
            
		if {$ipOptions != 0} {
            # add by jim.xie for scalability cases begin
		    # add IP_OPTIONS to add ip options&padding info
		    ip config -options $ipOptions
		    # add by jim.xie for scalability cases end		
		}
        ipAddressTable setDefault
        ipAddressTable config -defaultGateway             $ipGateway
	}
  
}


#*******************************************************************************
#
#  Function Name	: _ixiaCreateIPv6Stream
#
#  Description    : This function is called to create L3 stream header
#         
#  Usage          : _ixiaCreateIPv6Stream <>
# 
#*******************************************************************************
proc _ixiaCreateIPv6Stream {ipv6_stream} {
  uplevel 1 {
    # Call this function to create ipv6 stream
    
    if { $ipv6_stream == {} } return;
  
    #set src_addr_mode "ipV6Idle"
    #set dst_addr_mode "ipV6Idle"
	set src_addr_mode [_ntgrGetIPv6SrcAddrMode $ipv6_stream ]
	set dst_addr_mode [_ntgrGetIPv6DstAddrMode $ipv6_stream ]
    set src_step 1
    set dst_step 1
    set src_addr [ _ntgrGetIPv6SrcAddr $ipv6_stream ]
    set src_plen [ _ntgrGetIPv6SrcPrefixLength $ipv6_stream ]
    set src_hcnt [ _ntgrGetIPv6SRCHostCnt $ipv6_stream ]
    set src_ncnt [ _ntgrGetIPv6SrcNetCnt $ipv6_stream ] 
    set src_ninc [ _ntgrGetIPv6SrcNetInc $ipv6_stream ]
    
    set dst_addr [ _ntgrGetIPv6DstAddr $ipv6_stream ]
    set dst_plen [ _ntgrGetIPv6DstPrefixLength $ipv6_stream ]
    set dst_hcnt [ _ntgrGetIPv6DstHostCnt $ipv6_stream ]
    set dst_ncnt [ _ntgrGetIPv6DstNetCnt $ipv6_stream ]
    set dst_ninc [ _ntgrGetIPv6DstNetInc $ipv6_stream ]
   
    set traf_cls [ _ntgrGetIPv6TrafficClass $ipv6_stream ]
    set flow_lbl [ _ntgrGetIPv6FlowLabel $ipv6_stream ]
    set payl_len [ _ntgrGetIPv6PayloadLength $ipv6_stream ]
    if {$payl_len !=0 } { Netgear_log_file "lib-ixia.tcl" "payloadLength read only in ixia, it will calculated payload length"}
    set next_hdr [ _ntgrGetIPv6NextHdr $ipv6_stream ]
    set hop_limi [ _ntgrGetIPv6HopLimit $ipv6_stream ]
    set gateway  [ _ntgrGetIPv6Gateway $ipv6_stream ]
    set src_defa [ _ntgrDefaultSrcIp $ipv6_stream ]
    set gw_defau [ _ntgrDefaultGateway $ipv6_stream ]
    
    switch -exact -- $next_hdr {
        0  {set nextHd "ipV6HopByHopOptions"}
        43 {set nextHd "ipV6Routing"}
        44 {set nextHd "ipV6Fragment"}
        50 {set nextHd "ipV6EncapsulatingSecurityPayload"}
        51 {set nextHd "ipV6Authentication"}
        59 {set nextHd "ipV6NoNextHeader"}
        60 {set nextHd "ipV6DestinationOptions"}
        6  {set nextHd "tcp"}
        17 {set nextHd "udp"}
        58 { if {[llength $custom_stream] == 0} { set nextHd "icmpV6"
             } else { set nextHd "ipV6NoNextHeader" 
#                      _ixiaSetIcmpv6ByUDF2
             }
           }
        default {set nextHd "ipV6NoNextHeader"; _ixiaSetNexthdrByUDF2 $next_hdr}
    }
    
   
    # Don't define host incr step in spirent
    # Don't define host src_hcnt in spirent
    
    if {$dst_hcnt !=1 && $dst_addr_mode == "ipV6Idle"} {
      set dst_addr_mode 5
     # set dst_step $dst_ninc
    }

    if {$src_hcnt !=1 && $src_addr_mode == "ipV6Idle"} {
      set src_addr_mode 5
     # set dst_step $dst_ninc
    }
      
  	protocol config -name 			ipV6
    
    #configure ipV6
    ipV6  setDefault
    ipV6  config -sourceAddr             $src_addr
    ipV6  config -sourceMask             96
    ipV6  config -sourceAddrMode         $src_addr_mode
    ipV6  config -sourceStepSize         $src_step
    ipV6  config -sourceAddrRepeatCount  $src_hcnt

    ipV6  config -destAddr               $dst_addr
    ipV6  config -destMask               96
    ipV6  config -destAddrMode           $dst_addr_mode
    ipV6  config -destStepSize           1
    ipV6  config -destAddrRepeatCount    $dst_hcnt
    
   #payloadLength read only in ixia, it will calculated payload length
    ipV6  config -flowLabel       $flow_lbl
    ipV6  config -hopLimit        $hop_limi
    ipV6  config -nextHeader      $nextHd
    ipV6  config -trafficClass    $traf_cls
  }
}

#*******************************************************************************
#
#  Function Name	: _ixiaCreateTrafficTcpUdpStream
#
#  Description    : This function is called to create Tcp Udp stream header
#         
#  Usage          : _ixiaCreateTrafficTcpUdpStream <tcp_udp_stream> <protocol>
# 
#*******************************************************************************
proc _ixiaCreateTrafficTcpUdpStream {tcp_udp_stream protocol} {
	uplevel 1 {
		set dst_port  [_ntgrGetTrafficStreamTcpUdpHeaderDstPort $tcp_udp_stream]
		set src_port  [_ntgrGetTrafficStreamTcpUdpHeaderSrcPort $tcp_udp_stream]
  	set tcp_flags [_ntgrGetTrafficStreamTcpUdpHeaderTCPFlag $tcp_udp_stream]
		set tcp_flag_syn [_ntgrGetTrafficStreamTcpUdpHeaderTCPFlagSYN $tcp_udp_stream]
		set tcp_flag_ack [_ntgrGetTrafficStreamTcpUdpHeaderTCPFlagACK $tcp_udp_stream]
		set tcp_flag_fin [_ntgrGetTrafficStreamTcpUdpHeaderTCPFlagFIN $tcp_udp_stream]
		set tcp_flag_rst [_ntgrGetTrafficStreamTcpUdpHeaderTCPFlagRST $tcp_udp_stream]
		set tcp_flag_psh [_ntgrGetTrafficStreamTcpUdpHeaderTCPFlagPSH $tcp_udp_stream]
		set tcp_flag_urg [_ntgrGetTrafficStreamTcpUdpHeaderTCPFlagURG $tcp_udp_stream]

		if {$tcp_flag_syn == {} && $tcp_flag_ack == {} &&  $tcp_flag_fin == {} && \
		    $tcp_flag_rst == {} && $tcp_flag_psh == {} &&  $tcp_flag_urg == {}} {
  		     switch -exact -- $tcp_flags {
              0x1  {set tcp_flag_fin 1}
              0x2  {set tcp_flag_syn 1}
              0x3  {set tcp_flag_fin 1; set tcp_flag_syn 1}
              0x4  {set tcp_flag_rst 1}
              0x8  {set tcp_flag_psh 1}
              0x10 {set tcp_flag_ack 1}
              0x20 {set tcp_flag_urg 1}
              0x01 {set tcp_flag_fin 1}
              0x02 {set tcp_flag_syn 1}
              0x03 {set tcp_flag_fin 1; set tcp_flag_syn 1}
              0x04 {set tcp_flag_rst 1}
              0x08 {set tcp_flag_psh 1}
           }
		}
	
		Netgear_log_file "lib-ixia.tcl" "SRCPORT - $src_port, DSTPORT - $dst_port"
		if {$protocol == 6} {
			set l4_command tcp
		} elseif {$protocol == 17} {
			set l4_command udp
		} else {
			Netgear_log_file "lib-ixia.tcl" "ERROR - Unknown Protocol. Default is taken to be TCP"
			set l4_command "tcp"
		}
		$l4_command setDefault
		if {$protocol == 6} {$l4_command config -offset    $ixia_tcp_offset}
		$l4_command config -destPort 	  $dst_port
		$l4_command config -sourcePort	$src_port
		
		if {$protocol == 6 && $tcp_flag_urg == 1} {
			$l4_command config -urgentPointerValid	"true"
		}
		if {$protocol == 6 && $tcp_flag_ack == 1} {
			$l4_command config -acknowledgeValid	"true"
		}
		if {$protocol == 6 && $tcp_flag_psh == 1} {
			$l4_command config -pushFunctionValid	"true"
		}
		if {$protocol == 6 && $tcp_flag_rst == 1} {
			$l4_command config -resetConnection	"true"
		}
		if {$protocol == 6 && $tcp_flag_syn == 1} {
			$l4_command config -synchronize	"true"
		}
		if {$protocol == 6 && $tcp_flag_fin == 1} {
			$l4_command config -finished	"true"
		}
		if {$protocol == 6} {
			$l4_command config -useValidChecksum	"true"
		} else {
		  $l4_command config -enableChecksum	"true"
		}
		
	}
}


#*******************************************************************************
#
#  Function Name	: _ixiaCreateStream
#
#  Description    : This function is called to create ixia stream 
#         
#  Usage          : _ixiaCreateTraffic <chassis_id> <pt> <chassis> <slot> <port> <streamid>
# 
#*******************************************************************************
proc _ixiaCreateStream {chassis_id pt chassis slot port streamId} {
	uplevel 1 {
		set frame_min_size 64
		set frame_max_size 1518
		if {!$frame_size} {set $frame_size 64}
		#if {$frame_size < 64} {set $frame_size 64}
		set frame_mode [_ntgrGetTrafficStreamFrameMode $chassis_id $pt $streamId]
		if {$frame_mode == 0} {set frame_mode [_ntgrGetTrafficPortFrameMode $chassis_id $pt]}
		#_ixiaSetTrafficPortFrameMode frame_mode frame_size frame_min_size frame_max_size $chassis_id $pt
		_ixiaSetTrafficStreamFrameMode frame_mode frame_size frame_min_size frame_max_size $chassis_id $pt $streamId	
				
		set load_type [_ntgrGetTrafficStreamLoadType $chassis_id $pt $streamId]
		if {$load_type == 0} { set load_type  [_ntgrGetTrafficPortLoadType $chassis_id $pt]}
	  set load  [_ntgrGetTrafficStreamLoad $chassis_id $pt $streamId]
	  if {$load == 0} {set load [_ntgrGetTrafficPortLoad $chassis_id $pt]}
		_ixiaSetTrafficPortLoadMode load_type
		Netgear_log_file "lib-ixia.tcl" "Load Type - $load_type, Load - $load"

		set duration_type [_ntgrGetTrafficPortDurationType $chassis_id $pt]
		_ixiaSetTrafficPortDurationType duration_type burst_size $chassis_id $pt
		
		set stream_burst_size [_ntgrGetTrafficPortStreamBurstSize $chassis_id $pt $streamId] 
	
		if {$stream_burst_size != 0} {
		   set burst_size $stream_burst_size
		   Netgear_log_file "lib-ixia.tcl" "update stream burst size as burst size, burstsize - $burst_size"
		}
		
		##============added by Tony on 2012-09-14============================
		set frame_crc  [_ntgrGetTrafficStreamFrameCRCError $chassis_id $pt $streamId]
		_ixiaSetTrafficPortCRCError frame_crc
		
		stream setDefault
    
		stream config -frameSizeType        $frame_mode
		stream config -framesize		        $frame_size
		stream config -frameSizeMIN         $frame_min_size
		stream config -frameSizeMAX         $frame_max_size
		stream config -rateMode			        $load_type
		stream config -percentPacketRate    $load
		stream config -fpsRate			        $load
 
		if {[llength $custom_stream] != 0} {  
 #   		_ixiaCreateTrafficCustomStream $custom_stream 
    	}	else {
  		stream config -dataPattern          userpattern
   	  stream config -pattern              "AA BB AA BB"
  		stream config -patternType          repeat
		}
		stream config -asyncIntEnable       true
		#stream config -dma                 $duration_type
		stream config -numFrames		        $burst_size
		stream config -dma                  contPacket
		stream config -fcs                  $frame_crc
    
    
		#if { $streamId == $noOfStream } { 
    #  		stream config -dma gotoFirst
		#} else {
		#	stream config -dma advance
		#}
	}
	# end of uplevel
}

#*******************************************************************************
#
#  Function Name	: _ixiaClearCounter
#
#  Description    : This API is called to clear the counters from the traffic 
#			  Generators
#         
#  Usage          : _ixiaClearCounter <chassis_id>
# 
#*******************************************************************************
proc _ixiaClearCounter {chassis_id} {
	set chassis_ip_addr [_ntgrGetTGIPAddr $chassis_id]
	chassis get $chassis_ip_addr
	set chassis	  [chassis cget -id]

	set chassis_slot_port_list {}
	set port_list [_ntgrGetTrafficTestPortList $chassis_id]
	foreach pt $port_list {
		set slot [_ntgrGetTrafficPortSlotId $chassis_id $pt]
		set port [_ntgrGetTrafficPortId $chassis_id $pt]
		lappend chassis_slot_port_list [list $chassis $slot $port]
	}
 	Netgear_log_file "lib-ixia.tcl" "Port List $chassis_slot_port_list"
	ixClearStats {chassis_slot_port_list}
}

#*******************************************************************************
#
#  Function Name  : _ixiaPortLinkDown
#
#  Description    : This function is called to set the port's status to down.
#
#  Usage          : _ixiaPortLinkDown <tester_id port>
#
#*******************************************************************************
proc _ixiaPortLinkDown { tester_id port } {
    set chassisID [_ntgrGetTGHandle $tester_id]
    if {[llength $port] == 1} {
        set port "$chassisID [_ntgrGetTrafficPortSlotId $tester_id $port] [_ntgrGetTrafficPortId $tester_id $port]"
    }
    Netgear_log_file "lib-ixia.tcl" "Setting port($port) status to down."
    port config -enableSimulateCableDisconnect true
    port set [lindex $port 0] [lindex $port 1] [lindex $port 2]
    set portlist [list [lindex $port 0],[lindex $port 1],[lindex $port 2]]
    if { [ixWritePortsToHardware portlist] } {
        Netgear_log_file "lib-ixia.tcl" "Error writing port status to hardware."
    }
}

#*******************************************************************************
#
#  Function Name  : _ixiaPortLinkUP
#
#  Description    : This function is called to set the port's status to up.
#
#  Usage          : _ixiaPortLinkUP <tester_id port>
#
#*******************************************************************************
proc _ixiaPortLinkUP { tester_id port } {
    set chassisID [_ntgrGetTGHandle $tester_id]
    if {[llength $port] == 1} {
        set port "$chassisID [_ntgrGetTrafficPortSlotId $tester_id $port] [_ntgrGetTrafficPortId $tester_id $port]"
    }
    Netgear_log_file "lib-ixia.tcl" "Setting port($port) status to up."
    port config -enableSimulateCableDisconnect false
    port set [lindex $port 0] [lindex $port 1] [lindex $port 2]
    set portlist [list [lindex $port 0],[lindex $port 1],[lindex $port 2]]
    if { [ixWritePortsToHardware portlist] } {
        Netgear_log_file "lib-ixia.tcl" "Error writing port status to hardware."
    }
}

#*******************************************************************************
#
#  Function Name	: _ixiaReportRatePerPort
#
#  Description    : This function return port rate info
#         
#  Usage          : _ixiaReportRatePerPort <chassis_id> <port>
# 
#*******************************************************************************
proc _ixiaReportRatePerPort {chassis_id port} {
    set chassisID [_ntgrGetTGHandle $chassis_id]
    set card [_ntgrGetTrafficPortSlotId $chassis_id $port]
    set pt [_ntgrGetTrafficPortId $chassis_id $port]
    stat getRate allStats $chassisID $card $pt
    set ratetx [expr double([stat cget -framesSent])]
    set raterx [expr double([stat cget -framesReceived])]
    Netgear_log_file "lib-ixia.tcl" "Port $port: TxRate=$ratetx, RxRate=$raterx"
    return "$ratetx $raterx"
}

#*******************************************************************************
#
#  Function Name  : _ixiaTakeOwnership
#
#  Description    : This function is called to take ownership of one port
#
#  Usage          : _ixiaTakeOwnership <chassis_id> <port_local>
#
#*******************************************************************************
proc _ixiaTakeOwnership { chassis_id port_list } {
	foreach port_local $port_list {
  	set chas [_ntgrGetTrafficPortChassisId $chassis_id $port_local]
  	set card [_ntgrGetTrafficPortSlotId $chassis_id $port_local]
  	set port [_ntgrGetTrafficPortId $chassis_id $port_local]
  	
  	set username "Automation"	
  	set pl [list [list $chas $card $port]]
  	
  	if [ixLogin $username] {
  	ixPuts $::ixErrorInfo
  	return 1
  	}
  	
  	if [ixTakeOwnership $pl] {
  	ixPuts $::ixErrorInfo
  	return 1
  	}
  }
}

#*******************************************************************************
#
#  Function Name  : _ixiaClearOwnership
#
#  Description    : This function is called to clear ownership of one port
#
#  Usage          : _ixiaClearOwnership <chassis_id> <port_local>
#
#*******************************************************************************
proc _ixiaClearOwnership { chassis_id port_list } {
  foreach port_local $port_list {
  	set chas [_ntgrGetTrafficPortChassisId $chassis_id $port_local]
  	set card [_ntgrGetTrafficPortSlotId $chassis_id $port_local]
  	set port [_ntgrGetTrafficPortId $chassis_id $port_local]
  	
  	set pl [list [list $chas $card $port]]
  	
  	ixClearOwnership $pl
  }
}

#*******************************************************************************
#
#  Function Name  : _ixiaReleasePort
#
#  Description    : This function is called to release ixia port
#
#  Usage          : _ixiaClearOwnership <chassis_id> <port_local>
#
#*******************************************************************************
proc _ixiaReleasePort { chassis_id port_list } {
     _ixiaClearOwnership $chassis_id $port_list
     global ixia_exstreamId
     set ixia_exstreamId 0
}

#*******************************************************************************
#
#  Function Name  : _ixiaLoadRipConfig
#
#  Description    : This function is called to create router rip on ethernet port.
#
#  Usage          : _ixiaLoadRipConfig <chassis_id> <port_local>
#
#*******************************************************************************
proc _ixiaLoadRipConfig { chassis_id port_local } {
    
    set chas [_ntgrGetTrafficPortChassisId $chassis_id $port_local]
    set card [_ntgrGetTrafficPortSlotId $chassis_id $port_local]
    set port [_ntgrGetTrafficPortId $chassis_id $port_local]

    set description_if [_ntgrGetDescriptionforMaininterface $chassis_id $port_local]
    set router_list [_ntgrGetRouterRipList $chassis_id $port_local]
    set myMac [_ntgrGetTrafficPortMacAddress $chassis_id $port_local]
    
    set pl [list [list $chas $card $port]]
   
#    port setFactoryDefaults $chas $card $port
#    port setDefault
    port set $chas $card $port

    ipAddressTable               setDefault        
    ipAddressTable               config            -defaultGateway                     "0.0.0.0"
    ipAddressTable               set               $chas $card $port
    
    arpServer                    setDefault        
    arpServer                    config            -mode                               arpGatewayOnly
    arpServer                    set               $chas $card $port
    
    _ixiaProtocolInterConfig $chassis_id $port_local

#    interfaceEntry        setDefault
#    interfaceEntry        config        -enable             true
#    interfaceEntry        config        -description        $description_if
#    interfaceEntry        config        -macAddress         $myMac 
#    interfaceTable        addInterface      interfaceTypeConnected
#    interfaceTable        write
    
    ripServer             select            $chas $card $port
    ripServer             clearAllRouters

    _ixiaRipRouterConfig $router_list $description_if
}

#*******************************************************************************
#
#  Function Name  : _ixiaProtocolInterConfig
#
#  Description    : This function is called to configure protocol interface.
#
#  Usage          : _ixiaProtocolInterConfig <chassis_id> <port>
#
#*******************************************************************************
proc _ixiaProtocolInterConfig { chassis_id port_local } {
	
    set sub_interface [_ntgrGetSubIfList $chassis_id $port_local]
    set num_sub [llength $sub_interface]
  
    set chas [_ntgrGetTrafficPortChassisId $chassis_id $port_local]
    set card [_ntgrGetTrafficPortSlotId $chassis_id $port_local]
    set port [_ntgrGetTrafficPortId $chassis_id $port_local]

    set router [_ntgrGetTrafficPortIpAddress $chassis_id $port_local]
    set mask [_ntgrGetTrafficPortIpAddressMask $chassis_id $port_local]
    set neighbor [_ntgrGetTrafficPortGwIpAddress $chassis_id $port_local]
    set description_if [_ntgrGetDescriptionforMaininterface $chassis_id $port_local]
    set myMac [_ntgrGetTrafficPortMacAddress $chassis_id $port_local]    


    ##--- Set ipv6 interface, added by andy xie 2013-03-14 ----#
    set ipv6_addr [_ntgrGetPortIPv6Addr $chassis_id $port_local]
    set ipv6_mask [_ntgrGetPortIPv6MaskLen $chassis_id $port_local]
    set ipv6_gate_way [_ntgrGetPortGwIPv6Addr $chassis_id $port_local]
    
    interfaceTable               select            $chas $card $port
    interfaceTable               setDefault        
    interfaceTable               config            -dhcpV4RequestRate          0
    interfaceTable               config            -dhcpV6RequestRate          0  
    interfaceTable               set               
    interfaceTable               clearAllInterfaces 
    interfaceEntry               clearAllItems     addressTypeIpV6
    interfaceEntry               clearAllItems     addressTypeIpV4
    interfaceEntry               setDefault
            
	if {$num_sub == 0} {
		interfaceIpV4                setDefault        
		interfaceIpV4                config            -gatewayIpAddress     $neighbor
		interfaceIpV4                config            -maskWidth            $mask
		interfaceIpV4                config            -ipAddress            $router
		interfaceEntry               addItem           addressTypeIpV4
    
    ## Config ipv6 interface for port, Added by andy xie 2013-03-14
    interfaceIpV6                setDefault        
    interfaceIpV6                config            -ipAddress            $ipv6_addr
    interfaceIpV6                config            -maskWidth            $ipv6_mask
    interfaceEntry               addItem           addressTypeIpV6

		interfaceEntry       	 config            -enable                     true
		interfaceEntry       	 config            -description                $description_if
		interfaceEntry       	 config            -macAddress                 $myMac
		interfaceEntry       	 config            -ipV6Gateway                $ipv6_gate_way
		interfaceTable       	 addInterface      interfaceTypeConnected
	}
    
	if {$num_sub != 0} {
    		  foreach sub_int $sub_interface {
    			
      			set vlan_id [lindex $sub_int 0]
      			set ixia_ip [lindex $sub_int 1]
      			set gw_ip [lindex $sub_int 2]
      			set mac_address [lindex $sub_int 3]
      			set description [lindex $sub_int 4]	
  		
      			interfaceEntry       clearAllItems     addressTypeIpV6
      			interfaceEntry       clearAllItems     addressTypeIpV4
      			interfaceEntry       setDefault 
      			
      			interfaceIpV4        setDefault        
      			interfaceIpV4        config     -gatewayIpAddress       "$gw_ip"
      			interfaceIpV4        config     -ipAddress              "$ixia_ip"
      			interfaceEntry       addItem    addressTypeIpV4
      			
      			dhcpV4Properties     removeAllTlvs     
      			dhcpV4Properties     setDefault        
      			
      			dhcpV6Properties     removeAllTlvs     
      			dhcpV6Properties     setDefault        
      			
      			interfaceEntry       config         -enable                  true
      			interfaceEntry       config         -description             "$description"
      			interfaceEntry       config         -macAddress              "$mac_address"
      			interfaceEntry       config         -enableVlan              true
      			interfaceEntry       config         -vlanId                  "$vlan_id"
      			interfaceTable       addInterface   interfaceTypeConnected
      	}
    }
    interfaceEntry       config         -enable                  $::true
    interfaceEntry       config         -description                "PortocolInterface"        
    interfaceEntry       config         -connectedVia                "PortocolInterface"
    
    if {[interfaceTable addInterface interfaceTypeConnected]} {
		        Netgear_log_file "lib-ixia.tcl" "error: add dhcp host interface on port"
		        return 0
		    }
		    
		    # Send the interface table to the chassis by 50 interfaces and wait for 10 seconds
		    #if {[expr $i % 50] == 0} {
		        if {[interfaceTable write]} {
                Netgear_log_file "lib-ixia.tcl" "error: add dhcp host interfaces on port to chassis"
                break
            }
            after 10000
		    #}
    set flag_ospf [llength [_ntgrGetRouterOSPFList $chassis_id $port_local]]
    set flag_rip  [llength [_ntgrGetRouterRipList $chassis_id $port_local]]
   
    protocolServer setDefault        
		protocolServer config -enableArpResponse true
		protocolServer config -enablePingResponse true
		
		if {$flag_ospf != 0} {
			protocolServer config -enableOspfService true
		}
		if {$flag_rip != 0} {
			protocolServer config -enableRipService true
		}
		protocolServer set $chas $card $port
}

#*******************************************************************************
#
#  Function Name  : _ixiaRipRouterConfig
#
#  Description    : This function is called to configure RIP router.
#
#  Usage          : _ixiaRipRouterConfig <router_list> <description_if>
#
#*******************************************************************************
proc _ixiaRipRouterConfig {router_list description_if} {

    set RouterID 1
    foreach router $router_list {        
        set router_ip [_ntgrGetRipRouterIP $router]
        set subnet_mask [_ntgrGetRipRouterIPMask $router]
               
        set send_version [_ntgrGetRipSendVer $router]
        set sendType [_ntgrGetRipCastType $router]
        set sendType [_ixiaSetRouterRipSendVersion $sendType $send_version]
    
        set receiveType [_ntgrGetRipRecVer $router]
        set receiveType [_ixiaSetRouterRipRecVersion $receiveType]
       
        set updateInterval [_ntgrGetRipUpdateInterval $router]
        set updateIntervalOffset [_ntgrGetRipUpdateIntervalOffset $router]
        set routeRanges_list [_ntgrGetRipRouteList $router]

        set RangeID 1        
        foreach routerange $routeRanges_list {
        
            set tag [_ntgrGetRipRouteTag $routerange] 
            set start_ip [_ntgrGetRipRouteStartIP $routerange]
            set route_num [_ntgrGetRipRouteCount $routerange] 
            set mask_width [_ntgrGetRipRoutePrefixLength $routerange]
            set next_ip [_ntgrGetRipRouteNexthop $routerange]
            set metric [_ntgrGetRipRouteMetric $routerange]
            
            ripRouteRange        setDefault        
            ripRouteRange        config            -enableRouteRange       true
            ripRouteRange        config            -routeTag               $tag
            ripRouteRange        config            -networkIpAddress       $start_ip
            ripRouteRange        config            -networkMaskWidth       $mask_width
            ripRouteRange        config            -numberOfNetworks       $route_num
            ripRouteRange        config            -nextHop                $next_ip
            ripRouteRange        config            -metric                 $metric
            ripInterfaceRouter   addRouteRange     routeRange$RangeID                        
            incr RangeID 
        }
        ripInterfaceRouter       setDefault
        ripInterfaceRouter       config       -enableRouter                   true
        ripInterfaceRouter       config       -ipAddress                      $router_ip
        ripInterfaceRouter       config       -ipMask                         $subnet_mask
        ripInterfaceRouter       config       -protocolInterfaceDescription   $description_if
        ripInterfaceRouter       config       -sendType                       $sendType
        ripInterfaceRouter       config       -receiveType                    $receiveType
        ripInterfaceRouter       config       -updateInterval                 $updateInterval
        ripInterfaceRouter       config       -updateIntervalOffset           $updateIntervalOffset        
        ripServer addRouter      router$RouterID        
        incr RouterID    
    }
    
}

#*******************************************************************************
#
#  Function Name  : _ixiaStartRIPRouter
#
#  Description    : This function is called to start router process on an ethernet port.
#                   It will start the configured RIP router on that port
#
#  Usage          : _ixiaStartRIPRouter <chassis port>
#
#*******************************************************************************
proc _ixiaStartRIPRouter {chassis_id port_local} {

    set chas [_ntgrGetTrafficPortChassisId $chassis_id $port_local]
    set card [_ntgrGetTrafficPortSlotId $chassis_id $port_local]
    set port [_ntgrGetTrafficPortId $chassis_id $port_local]

    set pl [list [list $chas $card $port]]
    ixStartRip pl
}

#*******************************************************************************
#
#  Function Name  : _ixiaStopRIPRouter
#
#  Description    : This function is called to stop router process on an ethernet port.
#                   It will stop the configured RIP router on that port
#
#  Usage          : _ixiaStopRIPRouter <chassis port>
#
#*******************************************************************************
proc _ixiaStopRIPRouter {chassis_id port_local} {
    set chas [_ntgrGetTrafficPortChassisId $chassis_id $port_local]
    set card [_ntgrGetTrafficPortSlotId $chassis_id $port_local]
    set port [_ntgrGetTrafficPortId $chassis_id $port_local]

    set pl [list [list $chas $card $port]]
    if {[ixStopRip pl] != 0} {
        puts "Could not Stop Rip for $pl"
    }
}

#*******************************************************************************
#
#  Function Name  : _ixiaOspfRouterConfig
#
#  Description    : This function is called to configure ospf router.
#
#  Usage          : _ixiaOspfRouterConfig <router_list> <description_if>
#
#*******************************************************************************
proc _ixiaOspfRouterConfig {router_list description_if} {
    set RouterID 1
    foreach router $router_list {
    	     
        set router_id [_ntgrGetOSPFRouterID $router]
        set area_id [_ntgrGetOSPFAreaID $router]
        set network_type [_ntgrGetOSPFInterfaceNetworkType $router]
        set routeRanges_list [_ntgrGetOSPFRouteRange $router]
  
        ospfInterface        setDefault        
      	ospfInterface        config            -enable                         true
      	ospfInterface        config            -connectToDut                   true
      	ospfInterface        config            -areaId                         $area_id
      	ospfInterface        config            -protocolInterfaceDescription   $description_if
      	ospfRouter           addInterface      interface1
      		     
        set RangeID 1        
        foreach routerange $routeRanges_list {
            set first_ip [_ntgrGetOSPFFirstRouteINRouteRange $routerange]
            set route_count [_ntgrGetOSPFRouteCount $routerange]
            set route_metric [_ntgrGetOSPFRouteMetric $routerange]
            set route_origin [_ntgrGetOSPFRouteOrigin $routerange]
            set route_prefix [_ntgrGetOSPFRouteMask $routerange]
            
      	    ospfRouteRange     setDefault        
      	    ospfRouteRange     config    -enable                  true
      	    ospfRouteRange     config    -routeOrigin             $route_origin
      	    ospfRouteRange     config    -metric                  $route_metric
      	    ospfRouteRange     config    -numberOfNetworks        $route_count
      	    ospfRouteRange     config    -prefix                  $route_prefix
      	    ospfRouteRange     config    -networkIpAddress        $first_ip
      	    ospfRouter         addRouteRange     router$RangeID                        
            incr RangeID
        }        
        ospfRouter   setDefault        
        ospfRouter   config   -routerId         $router_id
        ospfRouter   config   -enable           true
        ospfServer   addRouter   router$RouterID        
        incr RouterID  
    }   
}

#*******************************************************************************
#
#  Function Name  : _ixiaOspfSubinterfaceRouterConfig
#
#  Description    : This function is called to configure ospf router on sub interface.
#
#  Usage          : _ixiaOspfSubinterfaceRouterConfig <router_list>
#
#*******************************************************************************
proc _ixiaOspfSubinterfaceRouterConfig {router_list} {
    set RouterID 1
    foreach router $router_list {
    	     
        set router_id [_ntgrGetOSPFRouterID $router]
        set area_id [_ntgrGetOSPFAreaID $router]
        set network_type [_ntgrGetOSPFInterfaceNetworkType $router]
        set routeRanges_list [_ntgrGetOSPFRouteRange $router]
        set description [_ntgrGetOSPFIFdescription $router]
  
        ospfInterface        setDefault        
      	ospfInterface        config            -enable                         true
      	ospfInterface        config            -connectToDut                   true
      	ospfInterface        config            -areaId                         $area_id
      	ospfInterface        config            -protocolInterfaceDescription   $description
      	ospfRouter           addInterface      interface1
		     
        set RangeID 1        
        foreach routerange $routeRanges_list {
            set first_ip [_ntgrGetOSPFFirstRouteINRouteRange $routerange]
            set route_count [_ntgrGetOSPFRouteCount $routerange]
            set route_metric [_ntgrGetOSPFRouteMetric $routerange]
            set route_origin [_ntgrGetOSPFRouteOrigin $routerange]
            set route_prefix [_ntgrGetOSPFRouteMask $routerange]

      	    ospfRouteRange     setDefault        
      	    ospfRouteRange     config    -enable                  true
      	    ospfRouteRange     config    -routeOrigin             $route_origin
      	    ospfRouteRange     config    -metric                  $route_metric
      	    ospfRouteRange     config    -numberOfNetworks        $route_count
      	    ospfRouteRange     config    -prefix                  $route_prefix
      	    ospfRouteRange     config    -networkIpAddress        $first_ip
      	    ospfRouter         addRouteRange     router$RangeID                        
            incr RangeID
        }        
        ospfRouter   setDefault        
        ospfRouter   config   -routerId         $router_id
        ospfRouter   config   -enable           true
        ospfServer   addRouter   router$RouterID        
        incr RouterID  
    }   
}

#*******************************************************************************
#
#  Function Name  : _ixiaLoadOSPFConfig
#
#  Description    : This function is called to create router ospf on ethernet port.
#
#  Usage          : _ixiaLoadOSPFConfig <chassis_id> <port_local>
#
#*******************************************************************************
proc _ixiaLoadOSPFConfig { chassis_id port_local } {
    
    set chas [_ntgrGetTrafficPortChassisId $chassis_id $port_local]
    set card [_ntgrGetTrafficPortSlotId $chassis_id $port_local]
    set port [_ntgrGetTrafficPortId $chassis_id $port_local]

    set description_if [_ntgrGetDescriptionforMaininterface $chassis_id $port_local]
    set myMac [_ntgrGetTrafficPortMacAddress $chassis_id $port_local]
    set router_list [_ntgrGetRouterOSPFList $chassis_id $port_local]
    set sub_interface [_ntgrGetSubIfList $chassis_id $port_local]
    set num_sub [llength $sub_interface]
    
    set pl [list [list $chas $card $port]]
   
#    port setFactoryDefaults $chas $card $port
#    port setDefault
    port set $chas $card $port

    ipAddressTable     setDefault        
    ipAddressTable     config    -defaultGateway       "0.0.0.0"
    ipAddressTable     set       $chas $card $port   
    arpServer          setDefault        
    arpServer          config    -mode                 arpGatewayOnly
    arpServer          set       $chas $card $port
  
    _ixiaProtocolInterConfig $chassis_id $port_local

    ospfServer           select            $chas $card $port
    ospfServer           clearAllRouters      
    if { $num_sub != 0 } {
        _ixiaOspfSubinterfaceRouterConfig $router_list
    } else {
        _ixiaOspfRouterConfig $router_list $description_if    	
    }
}

#*******************************************************************************
#
#  Function Name  : _ixiaStartOSPFRouter
#
#  Description    : This function is called to start router process on an ethernet port.
#                   It will start the configured OSPF router on that port
#
#  Usage          : _ixiaStartOSPFRouter <chassis> <port> 
#
#*******************************************************************************
proc _ixiaStartOSPFRouter {chassis_id port_local} {

    set chas [_ntgrGetTrafficPortChassisId $chassis_id $port_local]
    set card [_ntgrGetTrafficPortSlotId $chassis_id $port_local]
    set port [_ntgrGetTrafficPortId $chassis_id $port_local]

    set pl [list [list $chas $card $port]]
    ixStartOspf pl
}

#*******************************************************************************
#
#  Function Name  : _ixiaStopOSPFRouter
#
#  Description    : This function is called to stop router process on an ethernet port.
#                   It will stop the configured OSPF router on that port
#
#  Usage          : _ixiaStopOSPFRouter <chassis> <port>
#
#*******************************************************************************
proc _ixiaStopOSPFRouter {chassis_id port_local} {
    set chas [_ntgrGetTrafficPortChassisId $chassis_id $port_local]
    set card [_ntgrGetTrafficPortSlotId $chassis_id $port_local]
    set port [_ntgrGetTrafficPortId $chassis_id $port_local]

    set pl [list [list $chas $card $port]]
    if {[ixStopOspf pl] != 0} {
        puts "Could not Stop Ospf for $pl"
    }
}


#*******************************************************************************
#
#  Function Name  : _ixiaClearPortCounter
#
#  Description    : This function is called to clear port statistic 
#
#  Usage          : _ixiaClearPortCounter <port_handle_list>
#
#*******************************************************************************
proc _ixiaClearPortCounter {port_handle_list} {
    foreach port_handle $port_handle_list {
        set port [split $port_handle /]  
        set chas [lindex $port 0] 
        set slot [lindex $port 1] 
        set port [lindex $port 2] 
        ixClearPortStats $chas $slot $port
        for {set i 1} {$i <=32} {incr i} {
          set j [expr $i+1]
          packetGroupStats clear $chas $slot $port "$i $j"
        }      
        Netgear_log_file "lib-ixia.tcl" "clean port $chas $slot $port statistic "
    }
}

#*******************************************************************************
#
#  Function Name  : _ixiaReportRxCount
#
#  Description    : This function is called to get port statistic via stream handle or port handle
#
#  Usage          : _ixiaReportRxCount <cnt_handle>
#
#*******************************************************************************
proc _ixiaReportRxCount {cnt_handle} {
    set classify [lindex $cnt_handle 0]
    set ret [string first "s" $classify]
    set totalFrame 0
    if {$ret== -1} {
        #get port statistics
          set port_handle [split $cnt_handle /]  
          set chas [lindex $port_handle 0] 
          set slot [lindex $port_handle 1] 
          set port [lindex $port_handle 2] 
          stat get statAllStats $chas $slot $port 
          set totalFrame [stat cget -framesReceived]
          Netgear_log_file "lib-ixia.tcl" "port $chas $slot $port receive frame $totalFrame "
       
    } else {
        # get stream statistics
          set totalOversize 0
          set pgId        [string trimleft $cnt_handle "s"]
          set streamInfo  [_ixiaGetStreamInfo $pgId]
          set streamInfo  [split $streamInfo /]  
          set chassis_id  [lindex $streamInfo 3]
          set baseId      [lindex $streamInfo 4]   
          set port_lists  [_ntgrGetTrafficTestPortList $chassis_id]     
          set streamId [expr $pgId - $baseId]
          foreach rxport $port_lists {
              set chas    [_ntgrGetTrafficPortChassisId $chassis_id $rxport]
              set slot    [_ntgrGetTrafficPortSlotId $chassis_id $rxport]
              set port    [_ntgrGetTrafficPortId $chassis_id $rxport]
            
              if [packetGroupStats get $chas $slot $port 0 16384] {
                  Netgear_log_file "lib-ixia.tcl" "Fail to get packetGroupStats of $chas $slot $port "
              }
              # get again for a valid reading
              if [packetGroupStats get $chas $slot $port 0 16384] {
                  Netgear_log_file "lib-ixia.tcl" "Fail to get packetGroupStats of $chas $slot $port "
              }
              # set numGroups [packetGroupStats cget -numGroups] 
              # Netgear_log_file "lib-ixia.tcl" "numGroups is $numGroups, pgId is $pgId"
              packetGroupStats getGroup $pgId
              set framecounter [packetGroupStats cget -totalFrames]
              Netgear_log_file "lib-ixia.tcl" "Port $chas $slot $port receive stream $streamId frame number is $framecounter"
              set totalFrame [expr $totalFrame + $framecounter]
              stat get statAllStats $chas $slot $port
              set oversize [stat cget -oversize]
              if {$oversize!=0} { Netgear_log_file "lib-ixia.tcl" "Port $chas $slot $rxport recv oversize $oversize"}
              set totalOversize [expr $oversize + $totalOversize] 
          }
          if {$totalFrame ==0 } {
                  set totalFrame  $totalOversize
          }
          Netgear_log_file "lib-ixia.tcl" "All ports receviced stream $streamId frame count $totalFrame"
    }
    return $totalFrame
}

#*******************************************************************************
#
#  Function Name  : _ixiaReportTxCount
#
#  Description    : This function is called to get TX port statistic via stream handle or port handle
#
#  Usage          : _ixiaReportTxCount <cnt_handle>
#
#*******************************************************************************

proc _ixiaReportTxCount {cnt_handle} {
    set classify [lindex $cnt_handle 0]
    set ret [string first "s" $classify]
    set totalFrame 0
    if {$ret== -1} {
        #get port statistics 
          set port_handle [split $cnt_handle /]  
          set chas [lindex $cnt_handle 0] 
          set slot [lindex $port_handle 1] 
          set port [lindex $port_handle 2] 
          stat get statAllStats $chas $slot $port 
          set totalFrame [stat cget -framesSent]
          Netgear_log_file "lib-ixia.tcl" "Port $chas $slot $port sent frame $totalFrame "
      
    } else {
        # get stream statistics
          set pgId          [string trimleft $cnt_handle "s"]
          set streamInfo    [_ixiaGetStreamInfo $pgId]
          set streamInfo    [split $streamInfo /]  
          set chas          [lindex $streamInfo 0] 
          set slot          [lindex $streamInfo 1] 
          set port          [lindex $streamInfo 2]
          set chassis_id    [lindex $streamInfo 3]
          set baseId        [lindex $streamInfo 4]   
          set pt            [lindex $streamInfo 5]  
         
          set maxStream [_ntgrGetTrafficPortStreamCount $chassis_id $pt]
          if [streamTransmitStats get $chas $slot $port 1 $maxStream] {
             Netgear_log_file "lib-ixia.tcl" "Fail : streamTransmitStats get $chas $slot $port "
          }
          # get all stream stat again for a valid reading
          if [streamTransmitStats get $chas $slot $port 1 $maxStream] {
             Netgear_log_file "lib-ixia.tcl" "Fail : streamTransmitStats get $chas $slot $port "
          }
          set streamId [expr $pgId - $baseId]
          if [streamTransmitStats getGroup $streamId] {
              Netgear_log_file "lib-ixia.tcl" "Fail : Port $chas $slot $port to get stream stats"
          }
          set totalFrame [streamTransmitStats cget -framesSent]   
          Netgear_log_file "lib-ixia.tcl" "Port $chas $slot $port sent Stream $streamId frame count $totalFrame"
    }
    return $totalFrame
}

#*******************************************************************************
#
#  Function Name  :_ixiaSetStreamInfo
#
#  Description    : This function is called to set streamInfo via packetgroup ID
#
#  Usage          : _ixiaSetStreamInfo <pgId> <streamInfo>
#
#*******************************************************************************
proc _ixiaSetStreamInfo {ID streamInfo} {
  global _ixiaStreamInfo
  keylset _ixiaStreamInfo $ID $streamInfo
}

#*******************************************************************************
#
#  Function Name  :_ixiaGetStreamInfo
#
#  Description    : This function is called to get streamInfo via packetgroup ID
#
#  Usage          : _ixiaGetStreamInfo <pgID> 
#
#*******************************************************************************
proc _ixiaGetStreamInfo {ID} {
  global _ixiaStreamInfo
  set ret 0
  keylget _ixiaStreamInfo $ID  ret
  return $ret
}

#*******************************************************************************
#
#  Function Name  : _ixiaReportRxRate
#
#  Description    : This function is called to get port frame rev rate via stream handle or port handle
#
#  Usage          : _ixiaReportRxRate <cnt_handle>
#
#*******************************************************************************
proc _ixiaReportRxRate {cnt_handle} {
    set classify [lindex $cnt_handle 0]
    set ret [string first "s" $classify]
    set totalFrameRate 0
    if {$ret== -1} {
        #get port statistics
          set port_handle [split $cnt_handle /]  
          set chas [lindex $port_handle 0] 
          set slot [lindex $port_handle 1] 
          set port [lindex $port_handle 2] 
          if [stat getRate statAllStats $chas $slot $port] {
               Netgear_log_file "lib-ixia.tcl" "Fail $chas $slot $port to get rate"
          } 
          set totalFrameRate [expr double([stat cget -framesReceived])]
          Netgear_log_file "lib-ixia.tcl" "port $chas $slot $port receive frame rate $totalFrameRate "
       
    } else {
        # get stream statistics
         
          set pgId        [string trimleft $cnt_handle "s"]
          set streamInfo  [_ixiaGetStreamInfo $pgId]
          set streamInfo  [split $streamInfo /] 
          set tx_chas [lindex $streamInfo 0] 
          set tx_slot [lindex $streamInfo 1] 
          set tx_port [lindex $streamInfo 2] 
          set chassis_id  [lindex $streamInfo 3]
          set baseId      [lindex $streamInfo 4]   
          set port_lists  [_ntgrGetTrafficTestPortList $chassis_id]     
    
          foreach rxport $port_lists {
              set chas    [_ntgrGetTrafficPortChassisId $chassis_id $rxport]
              set slot    [_ntgrGetTrafficPortSlotId $chassis_id $rxport]
              set port    [_ntgrGetTrafficPortId $chassis_id $rxport]
              
              if {$tx_chas == $chas && $tx_slot == $slot && $tx_port == $port} {continue}
              if [packetGroupStats get $chas $slot $port 0 16384] {
                  Netgear_log_file "lib-ixia.tcl" "Fail to get packetGroupStats of $chas $slot $port "
              }
               # get again for a valid reading
              if [packetGroupStats get $chas $slot $port 0 16384] {
                  Netgear_log_file "lib-ixia.tcl" "Fail to get packetGroupStats of $chas $slot $port "
              }
              packetGroupStats getGroup $pgId
              set frameRate [packetGroupStats cget -frameRate]
              set totalFrameRate [expr double([expr $totalFrameRate + $frameRate])]
              set streamId [expr $pgId - $baseId]
              Netgear_log_file "lib-ixia.tcl" "Port $chas $slot $port receive stream $streamId rate is $frameRate"
          }
        Netgear_log_file "lib-ixia.tcl" "receive stream $streamId rate is $totalFrameRate"
    }
    return $totalFrameRate
}

#*******************************************************************************
#
#  Function Name  : _ixiaReportTxRate
#
#  Description    : This function is called to get TX rate via stream handle or port handle
#
#  Usage          : _ixiaReportTxRate <cnt_handle>
#
#*******************************************************************************

proc _ixiaReportTxRate {cnt_handle} {
    set classify [lindex $cnt_handle 0]
    set ret [string first "s" $classify]
    if {$ret== -1} {
        # get port statistics
          set port_handle [split $cnt_handle /]  
          set chas [lindex $port_handle 0] 
          set slot [lindex $port_handle 1] 
          set port [lindex $port_handle 2] 
          if [stat getRate statAllStats $chas $slot $port] {
              Netgear_log_file "lib-ixia.tcl" "Fail $chas $slot $port to get rate"
          } 
          set frameRate [expr double([stat cget -framesSent])]
          Netgear_log_file "lib-ixia.tcl" "Port $chas $slot $port sent frame rate $frameRate "
      
    } else {
        # get stream statistics
          set pgId          [string trimleft $cnt_handle "s"]
          set streamInfo    [_ixiaGetStreamInfo $pgId]
          set streamInfo    [split $streamInfo /]  
          set chas          [lindex $streamInfo 0] 
          set slot          [lindex $streamInfo 1] 
          set port          [lindex $streamInfo 2]
          set chassis_id    [lindex $streamInfo 3]
          set baseId        [lindex $streamInfo 4]   
          set pt            [lindex $streamInfo 5]  
         
          set maxStream [_ntgrGetTrafficPortStreamCount $chassis_id $pt]
          if [streamTransmitStats get $chas $slot $port 1 $maxStream] {
             Netgear_log_file "lib-ixia.tcl" "Fail : streamTransmitStats get $chas $slot $port "
          }
          # get all stream stat again for a valid reading
          if [streamTransmitStats get $chas $slot $port 1 $maxStream] {
             Netgear_log_file "lib-ixia.tcl" "Fail : streamTransmitStats get $chas $slot $port "
          }
          
          set streamId [expr $pgId - $baseId]
          if [streamTransmitStats getGroup $streamId] {
              Netgear_log_file "lib-ixia.tcl" "Fail : Port $chas $slot $port to get stream stats"
          }
          set frameRate [expr double([streamTransmitStats cget -frameRate])]   
          
          Netgear_log_file "lib-ixia.tcl" "Port $chas $slot $port stream $streamId sent rate $frameRate"
    }
    return $frameRate
}

#*******************************************************************************
#
#  Function Name  : _ixiaReportTxSigCount
#
#  Description    : This function is called to get TX rate via port handle
#
#  Usage          : _ixiaReportTxSigCount <port_handle>
#
#*******************************************************************************
proc _ixiaReportTxSigCount {port_handle} {
    set port_handle [split $port_handle /]  
    set chas        [lindex $port_handle 0] 
    set slot        [lindex $port_handle 1] 
    set port        [lindex $port_handle 2] 
    set totalFrame 0
    # maxStream limited by Spirent
    set maxStream 64
    streamTransmitStats get $chas $slot $port 1 $maxStream
    #get again for valid reading
    streamTransmitStats get $chas $slot $port 1 $maxStream 
    set maxnum [streamTransmitStats cget -numGroups]
    for {set streamId 1} {$streamId <= $maxnum} {incr streamId} {
        if [streamTransmitStats getGroup $streamId] {
              Netgear_log_file "lib-ixia.tcl" "Fail : Port $chas $slot $port to get stream stats"
          }
        set framesSent [streamTransmitStats cget -framesSent]
        set totalFrame [expr $framesSent + $totalFrame ]  
    } 
    
    Netgear_log_file "lib-ixia.tcl" "Port $chas $slot $port sent sig framecnt $totalFrame "
    return $totalFrame
}

#*******************************************************************************
#
#  Function Name  : _ixiaReportRxSigCount
#
#  Description    : This function is called to get TX rate via port handle
#
#  Usage          : _ixiaReportRxSigCount <port_handle>
#
#*******************************************************************************

proc _ixiaReportRxSigCount {port_handle} {
     set port_handle [split $port_handle /]  
    set chas        [lindex $port_handle 0] 
    set slot        [lindex $port_handle 1] 
    set rxport      [lindex $port_handle 2] 
    set totalFrame  0
    if [packetGroupStats get $chas $slot $rxport 0 16384] {
        Netgear_log_file "lib-ixia.tcl" "Fail to get packetGroupStats of $chas $slot $rxport "
    }
    if [packetGroupStats get $chas $slot $rxport 0 16384] {
        Netgear_log_file "lib-ixia.tcl" "Fail to get packetGroupStats of $chas $slot $rxport "
    }
    set numGroups [packetGroupStats cget -numGroups] 
    for {set pgId 1} {$pgId <$numGroups} {incr pgId} {
        packetGroupStats getGroup $pgId
        set framecounter [packetGroupStats cget -totalFrames]
        set totalFrame [expr $totalFrame + $framecounter]
    }
    stat get statAllStats $chas $slot $rxport
    set oversize [stat cget -oversize]
    if {$oversize!=0} { Netgear_log_file "lib-ixia.tcl" "Port $chas $slot $rxport recv oversize $totalFrame "}
    if {$totalFrame == 0} {set totalFrame $oversize}
    Netgear_log_file "lib-ixia.tcl" "Port $chas $slot $rxport receive sig frame $totalFrame"  
    return $totalFrame

}

#*******************************************************************************
#
#  Function Name  : _ixiaReportTxSigRate
#
#  Description    : This function is called to get TX rate via port handle
#
#  Usage          : _ixiaReportTxSigRate <port_handle>
#
#*******************************************************************************
proc _ixiaReportTxSigRate {port_handle} {
    set port_handle [split $port_handle /]  
    set chas        [lindex $port_handle 0] 
    set slot        [lindex $port_handle 1] 
    set port        [lindex $port_handle 2] 
    
    stat getRate statAllStats $chas $slot $port
    set frameRate  [stat cget -framesSent]  
    set frameRate [expr double($frameRate)]
    Netgear_log_file "lib-ixia.tcl" "Port $chas $slot $port sent sig frame rate is $frameRate"
    return $frameRate
}

#*******************************************************************************
#
#  Function Name  : _ixiaReportRxSigRate
#
#  Description    : This function is called to get TX rate via port handle
#
#  Usage          : _ixiaReportRxSigRate <port_handle>
#
#*******************************************************************************

proc _ixiaReportRxSigRate {port_handle} {
    set port_handle [split  $port_handle /]  
    set chas        [lindex $port_handle 0] 
    set slot        [lindex $port_handle 1] 
    set rxport      [lindex $port_handle 2] 
    stat getRate statAllStats $chas $slot $rxport
    set frameRate    [stat cget -framesReceived]
    set oversizeRate [stat cget -oversize]
    if {$oversizeRate != 0} {
        Netgear_log_file "lib-ixia.tcl" "Port $chas $slot $rxport recv oversize rate $oversizeRate"
    }
    if {$frameRate < 5} {set frameRate $oversizeRate}
    set frameRate [expr double($frameRate)]
    Netgear_log_file "lib-ixia.tcl" "Port $chas $slot $rxport receive sig frame rate $frameRate "
    return $frameRate
}

#*******************************************************************************
#
#  Function Name  : _ntgrdec2bin
#
#  Description    : This function is called to covert decimal  to other format
#
#  Usage          : _ntgrdec2bin <dec other>
#
#*******************************************************************************
proc _ntgrdec2bin {dec other} {
  set bin {}
  set a 1
  while {$a >0} {
      set a [expr $dec/$other]
      set b [expr $dec%$other]
      set dec $a
      set bin "$b$bin"
  }
  return $bin
}

#*******************************************************************************
#
#  Function Name  : _ntgraddzeroleft
#
#  Description    : This function is called to add zero left number
#                  
#  Usage          : _ntgraddzeroleft <number> <len>
#                  number: input value ; length : expected number length
#
#*******************************************************************************
proc _ntgraddzeroleft {number len} {
 
  set zero 0
  set ret $number
  set length [string length $number]
  set max [expr $len - $length]
  for {set ni 0} {$ni <$max} {incr ni} {
    set ret $zero$ret
  }
  return $ret
}

#*******************************************************************************
#
#  Function Name  : _ntgrdeletehexprefix
#
#  Description    : This function is called to delete a hex number 's prefix "0x"
#                  
#  Usage          : _ntgrdeletehexprefix <number>
#                 
#
#*******************************************************************************
proc _ntgrdeletehexprefix {number} {
    set begin [string first "x" $number]
    set begin [expr $begin +1]
    set retVal [string range $number $begin end]
    return $retVal
}

#*******************************************************************************
#
#  Function Name  : _ixiaSearchExtHead
#
#  Description    : This API is called to search extension header
#                  
#  Usage          :_ixiaSearchExtHead <protocol> <chassis_id> <port> <stream>
#
#*******************************************************************************

proc _ixiaSearchExtHead {protocol_num chassis_id port stream} {
   set ret [_spirentSearchExtHead $protocol_num $chassis_id $port $stream]
   return $ret
}


#*******************************************************************************
#
#  Function Name	: _ixiaCreateIPv6ExtStream
#
#  Description    : This function is called to create ipv6 extension header
#         
#  Usage          : _ixiaCreateIPv6ExtStream <next_hdr> <chassis_id> <port> <stream> 
#
#  Revison        
#
#*******************************************************************************

proc _ixiaCreateIPv6ExtStream { next_hdr chassis_id port ipv6ExtHdr stream } {
   
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
    Netgear_log_file "lib-ixia.tcl" "_ixiaCreateIPv6ExtStream, begin"
    ## Creat ipv6 Extension Header stream 
    
    if { $next_hdr == $no_next_head || $next_hdr == $tcp_head || \
         $next_hdr == $udp_head || $next_hdr == $icmpv6_head } {
      return
    }
 
    if { $next_hdr == $hop_by_hop_head } {
      set hbh_next_type   [_ntgrGetIPv6ExtHbhNextHeadType $ipv6ExtHdr]
      
      set hopbyhoplen 4
      global ixia_tcp_offset
      set ixia_tcp_offset [expr $ixia_tcp_offset + $hopbyhoplen +1]      
      ipV6HopByHop clearAllOptions 
      ipV6OptionPADN setDefault 
      ipV6OptionPADN config -length                             $hopbyhoplen
      ipV6OptionPADN config -value                              "00 00 00 00"
      ipV6HopByHop addOption ipV6OptionPADN
     
      if {[ipV6 addExtensionHeader ipV6HopByHopOptions]} {
           Netgear_log_file "lib-ixia.tcl" "ERROR - AddExtensionHeader ipV6HopByHop"
       } else {
           Netgear_log_file "lib-ixia.tcl" "success - AddExtensionHeader ipV6HopByHop"
      }
      if {$hbh_next_type == $no_next_head || $hbh_next_type == $hop_by_hop_head } {
	      return
      } else {
	      _ixiaCreateIPv6ExtStream $hbh_next_type $chassis_id $port $ipv6ExtHdr $stream 
      }	  

    } elseif { $next_hdr == $dst_head } {
      set dst_next_type   [_ntgrGetIPv6ExtDstNextHeadType $ipv6ExtHdr]
      set ipV6Destlen 4
      global ixia_tcp_offset
      set ixia_tcp_offset [expr $ixia_tcp_offset + $ipV6Destlen +1]  
      ipV6Destination clearAllOptions
      ipV6OptionPADN setDefault 
      ipV6OptionPADN config -length                             $ipV6Destlen
      ipV6OptionPADN config -value                              "00 00 00 00"
      ipV6Destination  addOption ipV6OptionPADN
      if {[ipV6 addExtensionHeader ipV6DestinationOptions]} {
           Netgear_log_file "lib-ixia.tcl" "ERROR - AddExtensionHeader ipV6Destination"
      } else {
           Netgear_log_file "lib-ixia.tcl" "success - AddExtensionHeader ipV6Destination"
      }
      if {$dst_next_type == $no_next_head  || $dst_next_type == $dst_head } {
	      return
      } else {
	      _ixiaCreateIPv6ExtStream $dst_next_type $chassis_id $port $ipv6ExtHdr $stream 
      }	

    } elseif { $next_hdr == $frag_head } {
      set frag_next_type  [_ntgrGetIPv6ExtFragmentNextHeadType $ipv6ExtHdr]
      set frag_offset     [_ntgrGetIPv6ExtFragmentOffset $ipv6ExtHdr]
      set frag_id         [_ntgrGetIPv6ExtFragmentId $ipv6ExtHdr]
      set more_frag_flag  [_ntgrGetIPv6ExtMoreFragFlag $ipv6ExtHdr]

      ipV6Fragment  config  -enableFlag      false
      ipV6Fragment  config  -fragmentOffset  $frag_offset
      ipV6Fragment  config  -identification  $frag_id 
      if {[ipV6 addExtensionHeader ipV6Fragment]} {
         Netgear_log_file "lib-ixia.tcl" "ERROR - AddExtensionHeader ipV6Fragment"
      } else {
           Netgear_log_file "lib-ixia.tcl" "success - AddExtensionHeader ipV6Fragment"
      }
      
      if {$frag_next_type == $no_next_head || $frag_next_type == $frag_head} {
	      return
      } else {
	      _ixiaCreateIPv6ExtStream $frag_next_type $chassis_id $port $ipv6ExtHdr $stream 
      }	

    } elseif { $next_hdr == $rout_head } {
      Netgear_log_file "lib-spirent.tcl" "ipV6Routing"
      set rout_next_type  [_ntgrGetIPv6ExtRoutingNextHeadType $ipv6ExtHdr]
      set rout_type       [_ntgrGetIPv6ExtRoutingType $ipv6ExtHdr]
      set rout_seg_left   [_ntgrGetIPv6ExtRoutingSegLeft $ipv6ExtHdr]
      
      set routinglen 24
      global ixia_tcp_offset
      set ixia_tcp_offset [expr $ixia_tcp_offset + $routinglen +1]   
      
      ipV6Routing setDefault
      ipV6Routing config  -reserved "00 00 00 00"
      ipV6Routing config  -nodeList "0:0:0:0:0:0:0:0"
      if {[ipV6 addExtensionHeader ipV6Routing]} {
           Netgear_log_file "lib-spirent.tcl" "ERROR - AddExtensionHeader ipV6Routing"
      } else {
           Netgear_log_file "lib-ixia.tcl" "success - AddExtensionHeader ipV6Routing"
      }
      
      if { $rout_next_type == $no_next_head || $rout_next_type == $rout_head } {
	        return
      } else {
	        _ixiaCreateIPv6ExtStream $rout_next_type $chassis_id $port $ipv6ExtHdr $stream 
      }

    } elseif { $next_hdr == $auth_head } {
      
      set auth_next_type  [_ntgrGetIPv6ExtAuthNextHeadType $ipv6ExtHdr]
      set auth_head_len   [_ntgrGetIPv6ExtAuthHeadLen $ipv6ExtHdr]
      set auth_data       [_ntgrGetIPv6ExtAuthdata $ipv6ExtHdr] 
      set auth_seq_num    [_ntgrGetIPv6ExtAuthSeqNumber $ipv6ExtHdr]
      set auth_spi        [_ntgrGetIPv6ExtAuthSPI $ipv6ExtHdr]
      
      ipV6Authentication setDefault
      ipV6Authentication config -payloadLength        $auth_head_len
      ipV6Authentication config -securityParamIndex   $auth_spi
      ipV6Authentication config -sequenceNumberField  $auth_seq_num 
      ipV6Authentication config -authentication       $auth_data
      
      if {[ipV6 addExtensionHeader ipV6Authentication]} {
         Netgear_log_file "lib-spirent.tcl" "ERROR - AddExtensionHeader ipV6Authentication"
       } else {
           Netgear_log_file "lib-ixia.tcl" "success - AddExtensionHeader ipV6Authentication"
      }
      
      if {$auth_next_type == $no_next_head || $auth_next_type == $auth_head} {
	      return
      } else {
	       _ixiaCreateIPv6ExtStream $auth_next_type $chassis_id $port $ipv6ExtHdr $stream 
      }

    } elseif { $next_hdr == $esp_head } {
      set esp_next_type   [_ntgrGetIPv6ExtEspNextHeadType $ipv6ExtHdr]
      set esp_data        [_ntgrGetIPv6ExtEspData $ipv6ExtHdr]
      set esp_pad         [_ntgrGetIPv6ExtEspPad $ipv6ExtHdr]
      set esp_payload     [_ntgrGetIPv6ExtEspPayLoad $ipv6ExtHdr]
      set esp_seq_num     [_ntgrGetIPv6ExtEspSeqNum $ipv6ExtHdr]
      set esp_spi         [_ntgrGetIPv6ExtEspSpi   $ipv6ExtHdr]
      
      Netgear_log_file "lib-ixia.tcl" "ERROR - ixia don't support AddExtensionHeader ipV6Encapsulatingr"
     
      if {$esp_next_type == $no_next_head || $esp_next_type == $esp_head} {
	      return
      } else {
	      _ixiaCreateIPv6ExtStream $esp_next_type $chassis_id $port $ipv6ExtHdr $stream 
      }
      Netgear_log_file "lib-ixia.tcl" "_ixiaCreateIPv6ExtStream, finished"

    } else {
	    Netgear_log_file "lib-ixia.tcl" "ERROR - not support the ipv6 extension $next_hdr, set next head $no_next_head"
	    set next_hdr $no_next_head
	    return
    }
}

#*******************************************************************************
#
#  Function Name	: _ixiaCreateTrafficIcmpv6Stream
#
#  Description    : This function is called to create icmpv6 header
#         
#  Usage          : _ixiaCreateTrafficIcmpv6Stream <icmp_stream>
# 
#*******************************************************************************
proc _ixiaCreateTrafficIcmpv6Stream {icmp_stream} {
    icmpV6 setDefault
    set icmp_type [_ntgrGetTrafficStreamIcmpType $icmp_stream]
	set icmp_multicast_Address [_ntgrGetTrafficStreamIcmpMulticastAddress $icmp_stream]
    switch -exact -- $icmp_type {
      1   {set icmp_type icmpV6DestUnreachableMessage}
      2   {set icmp_type icmpV6PacketTooBigMessage}
      3   {set icmp_type icmpV6TimeExceededMessage}
      4   {set icmp_type icmpV6ParameterProblemMessage}
      128 {set icmp_type icmpV6EchoRequestMessage}
      129 {set icmp_type icmpV6EchoReplyMessage}
      130 {set icmp_type icmpV6MulticastListenerQueryMessage}
      131 {set icmp_type icmpV6MulticastListenerReportMessage}
      132 {set icmp_type icmpV6MulticastListenerDoneMessage}
      133 {set icmp_type icmpV6RouterSolicitationMessage}
      134 {set icmp_type icmpV6RouterAdvertisementMessage}
      135 {set icmp_type icmpV6NeighborSolicitationMessage}
      136 {set icmp_type icmpV6NeighborAdvertisementMessage}
      137 {set icmp_type icmpV6RedirectMessage}
    }
    icmpV6 setType  $icmp_type
	icmpV6MulticastListener config -multicastAddress $icmp_multicast_Address
}

#*******************************************************************************
#
#  Function Name	: _ixiaCreateTrafficIcmpStream
#
#  Description    : This function is called to create icmp header
#         
#  Usage          : _ixiaCreateTrafficIcmpStream <icmp_stream>
# 
#*******************************************************************************
proc _ixiaCreateTrafficIcmpStream {icmp_stream} {
  
    set icmp_type [_ntgrGetTrafficStreamIcmpType $icmp_stream]
    set icmp_code [_ntgrGetTrafficStreamIcmpCode $icmp_stream]
    set icmp_id   [_ntgrGetTrafficStreamIcmpId   $icmp_stream]
    set icmp_seq  [_ntgrGetTrafficStreamIcmpSeq  $icmp_stream]
    set icmp_data [_ntgrGetTrafficStreamIcmpData $icmp_stream]
    
	switch -exact -- $icmp_type {
        0  {set icmp_type "echoReply"}
        3  {set icmp_type "destUnreachable"}
        4  {set icmp_type "sourceQuench"}
        5  {set icmp_type "redirect"}
        8  {set icmp_type "echoRequest"}
        11 {set icmp_type "timeExceeded"}
        12 {set icmp_type "parameterProblem"}
        13 {set icmp_type "timeStampRequest"}
        14 {set icmp_type "timeStampReply"}
        15 {set icmp_type "infoRequest"}
        16 {set icmp_type "infoReply"}
        17 {set icmp_type "maskRequest"}
        18 {set icmp_type "maskReply"}
         default { Netgear_log_file "lib-ixia.tcl" "Warning: don't get icmp type set default";\
         set icmp_type "echoReply"}
         
    }
    
    Netgear_log_file "lib-ixia.tcl" "ICMP_TYPE - $icmp_type, ICMP_CODE - $icmp_code, ICMP_ID - $icmp_id, ICMP_SEQUENCE - $icmp_seq, ICMP_DATA - $icmp_data"
     
    icmp setDefault
    icmp config -type       $icmp_type
    icmp config -code       $icmp_code
    icmp config -id         $icmp_id
    icmp config -sequence   $icmp_seq
}

#*******************************************************************************
#
#  Function Name	: _ixiaCreateTrafficIgmpStream
#
#  Description    : This function is called to create Igmp header
#         
#  Usage          : _ixiaCreateTrafficIgmpStream <igmp_stream>
# 
#*******************************************************************************
proc _ixiaCreateTrafficIgmpStream {igmp_stream} {
	set igmp_ver  [_ntgrGetTrafficStreamIgmpVer $igmp_stream]
	set igmp_type [_ntgrGetTrafficStreamIgmpType $igmp_stream]

	set igmp_type [format "%d" $igmp_type]
    
    #support igmpv3, add by luiz.yao begin 2015/9/25
    set recordsInfo ""
    keylget igmp_stream IGMP_RECORDS_INCR recordsInfo
    if {[format "%d" $igmp_ver] == 3 && $recordsInfo != ""} {
       igmp setDefault
       igmp config -version         igmpVersion3 

       switch -exact -- $igmp_type {
            34 {
                set recordsType [lindex $recordsInfo [expr [lsearch $recordsInfo TYPE] + 1]]
                set recordsStartIp [lindex $recordsInfo [expr [lsearch $recordsInfo START_GIP] + 1]]
                set recordsCounts [lindex $recordsInfo [expr [lsearch $recordsInfo COUNTS] + 1]]

                switch -exact -- $recordsType {
                    1 {set recordsType "igmpModeIsInclude"}
                    2 {set recordsType "igmpModeIsExclude"}
                    3 {set recordsType "igmpChangeToIncludeMode"}
                    4 {set recordsType "igmpChangeToExcludeMode"}
                    default {}
                }
                
                igmp config -type           "membershipReport3"                 
                igmp clearGroupRecords
                
                for {set i 0} {$i < $recordsCounts} {incr i} {
                    set ipaddr [_ntgrIpv4AddrIncr $recordsStartIp $i]
                    Netgear_log_file "lib-ixia.tcl" "Type----$recordsType; GroupIP----$ipaddr;"
                    igmpGroupRecord setDefault 
                    igmpGroupRecord config -type    $recordsType
                    igmpGroupRecord config -multicastAddress $ipaddr 
                    igmpGroupRecord config -sourceIpAddressList    ""  
                    if {[igmp addGroupRecord]} {
                    	Netgear_log_file "lib-ixia.tcl" "Error calling igmp addGroupRecord"
                    	return 0
                    }
                }   
            }
            default {
                Netgear_log_file "lib-ixia.tcl" "Only Support IGMPv3 Join Report Packets Now. igmp_type is: $igmp_type"
            }
        }       
        return 1   
    } 
    #support igmpv3, add by luiz.yao end 2015/9/25
    
    set igmp_maxresptime  [_ntgrGetTrafficStreamIgmpMaxResponse $igmp_stream]
	set igmp_gip  [_ntgrGetTrafficStreamIgmpGrpIP $igmp_stream]
    
  switch -exact -- $igmp_type {
      17  {set igmp_type "membershipQuery"}
      18  {set igmp_type "membershipReport1"}
      19  {set igmp_type "dvmrpMessage"}
      22  {set igmp_type "membershipReport2"}
      23  {set igmp_type "leaveGroup"}
      34  {set igmp_type "membershipReport3"}
      default { Netgear_log_file "lib-ixia.tcl" "Warning: don't get igmp type set default";\
       set igmp_type "membershipQuery"}
  }
	# add by jim.xie begin
	set igmp_mode [_ntgrGetTrafficStreamIgmpGrpMode $igmp_stream]
	set igmp_cnt  [_ntgrGetTrafficStreamIgmpGrpCnt  $igmp_stream]
    # add by jim.xie end	
	Netgear_log_file "lib-ixia.tcl" "IGMP-TYPE - $igmp_type, IGMP-MAXRESPTime - $igmp_maxresptime, IGMP-GIP - $igmp_gip"
	
	igmp setDefault
	
	# add by jim.xie begin
	#igmp config -mode            igmpIdle
	igmp config -mode            $igmp_mode
	igmp config -repeatCount     $igmp_cnt
	# add by jim.xie end
	
	igmp config -version         $igmp_ver
	igmp config -maxResponseTime $igmp_maxresptime
	igmp config -groupIpAddress  $igmp_gip
	igmp config -type            $igmp_type
}

#*******************************************************************************
#
#  Function Name	: _ixiaSetEthernetTypeByUDF1 
#
#  Description    : This function is to set ethnettype by udf1
#         
#  Usage          : _ixiaSetEthernetTypeByUDF1  <offset> <value>
# 
#*******************************************************************************
proc _ixiaSetEthernetTypeByUDF1 {offset value} {
     udf setDefault
     udf config -enable           true
     udf config -offset           $offset
     udf config -udfSize          c16
     udf config -counterMode      udfCounterMode
     udf config -countertype      c16
     udf config -initval          $value 
    
     if [udf set 1] {
        Netgear_log_file "lib-ixia.tcl" "Error in udf Set"
     }
}

#*******************************************************************************
#
#  Function Name	: _ixiaSetIcmpv6ByUDF2 
#
#  Description    : This function is to set icmpv6 protocol by udf2
#         
#  Usage          : _ixiaSetIcmpv6ByUDF2 
# 
#*******************************************************************************
proc _ixiaSetIcmpv6ByUDF2 {} {
     set offset 20
     set value "3A"
     udf setDefault
     udf config -enable           true
     udf config -offset           $offset
     udf config -udfSize          8
     udf config -counterMode      udfCounterMode
     udf config -initval          $value 
    
     if [udf set 2] {
        Netgear_log_file "lib-ixia.tcl" "Error in udf Set"
     } else { Netgear_log_file "lib-ixia.tcl" "Setting icmpv6 with in udf2" }
}

#*******************************************************************************
#
#  Function Name	: _ixiaSetNexthdrByUDF2 
#
#  Description    : This function is to set nexthdr protocol by udf2
#         
#  Usage          : _ixiaSetNexthdrByUDF2  <nexthdr>
# 
#*******************************************************************************
proc _ixiaSetNexthdrByUDF2 {nexthdr} {
     set offset 20
     set value [format "%x" $nexthdr]
     udf setDefault
     udf config -enable           true
     udf config -offset           $offset
     udf config -udfSize          8
     udf config -counterMode      udfCounterMode
     udf config -initval          $value 
    
     if [udf set 2] {
        Netgear_log_file "lib-ixia.tcl" "Error in udf Set"
     } else { Netgear_log_file "lib-ixia.tcl" "Setting icmpv6 with in udf2" }
}

#*******************************************************************************
#
#  Function Name	: _ixiaSetL4ProtocolByUDF2 
#
#  Description    : This function is to set L4 protocol in L3 header by udf2
#         
#  Usage          : _ixiaSetL4ProtocolByUDF2 
# 
#*******************************************************************************
proc _ixiaSetL4ProtocolByUDF2 {protocol eth_offset} {
     set offset_formIPheader 9
     set eth_type_length 2
     if {$protocol == 17} {set protocol 11}
     set offset [expr $offset_formIPheader + $eth_offset + $eth_type_length]
     udf setDefault
     udf config -enable           true
     udf config -offset           $offset
     udf config -udfSize          8
     udf config -counterMode      udfCounterMode
     udf config -initval          $protocol 
    
     if [udf set 2] {
        Netgear_log_file "lib-ixia.tcl" "Error in udf Set"
     } else { Netgear_log_file "lib-ixia.tcl" "Setting L4 with in udf2" }
}

#*******************************************************************************
#
#  Function Name	: _ixiaStartCapturePerPort
#
#  Description    : This function is called to capture packet
#         
#  Usage          : _ixiaStartCapturePerPort <product_id> <port>
# 
#*******************************************************************************
proc _ixiaStartCapturePerPort {product_id port} {

  Netgear_log_file "lib-ixia.tcl" "Start capture traffic"

}

#*******************************************************************************
#
#  Function Name	: _ixiaStopCapturePerPort
#
#  Description    : This function is called to capture packet
#         
#  Usage          : _ixiaStopCapturePerPort <product_id> <port>
# 
#*******************************************************************************
proc _ixiaStopCapturePerPort {product_id port} {

  Netgear_log_file "lib-ixia.tcl" "Stop capture traffic"

}

#*******************************************************************************
#
#  Function Name	: _ixiaCreateFilter
#
#  Description    : This function is called to capture packet
#         
#  Usage          : _ixiaCreateFilter chassis_id pt chassis slot port
# 
#*******************************************************************************

proc _ixiaCreateFilter {chassis_id pt chassis slot port}  {
    Netgear_log_file "lib-ixia.tcl" "Start create filter"
    set patNum 2
    filter setDefault
    filter config -userDefinedStat1Pattern  pattern1
    filter config -userDefinedStat1FrameSizeEnable true
    filter config -userDefinedStat1FrameSizeFrom 64
    filter config -userDefinedStat1FrameSizeTo 9612
    filter config -userDefinedStat1Enable   true          
    filter config -userDefinedStat2Pattern  pattern2 
    filter config -userDefinedStat2FrameSizeEnable true
    filter config -userDefinedStat2FrameSizeFrom 64
    filter config -userDefinedStat2FrameSizeTo 9612
    filter config -userDefinedStat2Enable   true  
    if {[filter set $chassis $slot $port]} {
         Netgear_log_file "lib-ixia.tcl" "fail to set filter"
    }  
    filterPallette setDefault
    for { set i 1 } { $i <= $patNum } { incr i } {
        set trig_mode [_ntgrGetTrafficPortTrig[subst $i]Status $chassis_id $pt]  
        if { $trig_mode == "TRUE" } {
             set trig_offset   [_ntgrGetTrafficPortTrig[subst $i]OffSet $chassis_id $pt]
             set trig_range    [_ntgrGetTrafficPortTrig[subst $i]Range $chassis_id $pt]
             set trig_pattern  [_ntgrGetTrafficPortTrig[subst $i]Pattern $chassis_id $pt]
             set trig_pattern  [_ixiaConverPatternFormat $trig_pattern]
             set trig_mask     [_ixiaConverRangeToMask $trig_range]
                       
            switch -exact -- $i {
                  1  { filterPallette config -pattern1       $trig_pattern;   \
                       filterPallette config -patternMask1   $trig_mask;      \
                       filterPallette config -patternOffset1 $trig_offset     \
                     }  
                  2  { filterPallette config -pattern2       $trig_pattern;   \
                       filterPallette config -patternMask2   $trig_mask;      \
                       filterPallette config -patternOffset2 $trig_offset     \
                     }   
              }            
        }     
    }  
    if {[filterPallette set $chassis $slot $port]} {
        Netgear_log_file "lib-ixia.tcl" "fail to set filterPallette"
    }    
}
#*******************************************************************************
#
#  Function Name  : _ixiaConverPatternFormat
#
#  Description    : This function is called to covert filter pattern format
#
#  Usage          : _ixiaConverPatternFormat <pattern>
#
#*******************************************************************************
proc _ixiaConverPatternFormat {pattern} {
  set patt [split $pattern :]  
  return $patt   
}

#*******************************************************************************
#
#  Function Name  : _ixiaConverRangeToMask
#
#  Description    : This function is called to covert filter pattern format
#
#  Usage          : _ixiaConverRangeToMask <range_num>
#
#*******************************************************************************
proc _ixiaConverRangeToMask {range_num} {
   set mask "00"
   for {set i 0} {$i < $range_num} {incr i} {
      lappend retval $mask
   }
   return $retval
}

#*******************************************************************************
#
#  Function Name  :_ixiaGetL2L3RateArrayEx
#
#  Description    : This function is called to get trigger statistics
#
#  Usage          : _ixiaGetL2L3RateArrayEx <chassis_id> <pt> <aRateArray>
#                 
#*******************************************************************************
proc _ixiaGetL2L3RateArrayEx  {chassis_id pt aRateArray} {
   upvar 1 $aRateArray aTmpArray
   set chassis_ip_addr [_ntgrGetTGIPAddr $chassis_id]
	 chassis get $chassis_ip_addr
	 set chassis	  [chassis cget -id]
	 set slot     [_ntgrGetTrafficPortSlotId $chassis_id $pt]
   set port     [_ntgrGetTrafficPortId $chassis_id $pt]

   stat getRate statAllStats $chassis $slot $port
   set aTmpArray(rxtrig1rate) [expr double([stat cget -userDefinedStat1])]  
   set aTmpArray(rxtrig2rate) [expr double([stat cget -userDefinedStat2])] 
   Netgear_log_file "lib-ixia.tcl" "rxtrig1rate is: $aTmpArray(rxtrig1rate)"
   Netgear_log_file "lib-ixia.tcl" "rxtrig2rate is: $aTmpArray(rxtrig2rate)"   
   #Netgear_log_file "lib-ixia.tcl" " ixia can't support trigger more than 2"
   for {set i 3} {$i <=8} {incr i} {
       set aTmpArray(rxtrig[subst $i]rate) 0
   }   
   set portHandle [_ntgrGetTrafficPortHandle $chassis_id $pt]
   set aTmpArray(txsigframerate) [_ixiaReportTxSigRate $portHandle]
   set aTmpArray(rxsigframerate) [_ixiaReportRxSigRate $portHandle]
   Netgear_log_file "lib-ixia.tcl" "txsigframerate is: $aTmpArray(txsigframerate)"
   Netgear_log_file "lib-ixia.tcl" "rxsigframerate is: $aTmpArray(rxsigframerate)"
}

#*******************************************************************************
#
#  Function Name  : _ixiaGetL2L3CountArrayEx
#
#  Description    : This function is called to get trigger statistics
#
#  Usage          : _ixiaGetL2L3CountArrayEx  <chassis_id> <port> <aCountArray>
#
#*******************************************************************************
proc _ixiaGetL2L3CountArrayEx  {chassis_id pt aCountArray} {
    upvar 1 $aCountArray aTmpArray  
   
   set chassis_ip_addr [_ntgrGetTGIPAddr $chassis_id]
	 chassis get $chassis_ip_addr
	 set chassis	  [chassis cget -id]
	 set slot     [_ntgrGetTrafficPortSlotId $chassis_id $pt]
   set port     [_ntgrGetTrafficPortId $chassis_id $pt]

   stat get statAllStats $chassis $slot $port
   set aTmpArray(rxtrig1cnt) [expr double([stat cget -userDefinedStat1])]  
   set aTmpArray(rxtrig2cnt) [expr double([stat cget -userDefinedStat2])] 
   Netgear_log_file "lib-ixia.tcl" "rxtrig1cnt is: $aTmpArray(rxtrig1cnt)"
   Netgear_log_file "lib-ixia.tcl" "rxtrig2cnt is: $aTmpArray(rxtrig2cnt)" 
   #Netgear_log_file "lib-ixia.tcl" " ixia can't support trigger more than 2"  
   
    for {set i 3} {$i <=8} {incr i} {
       set aTmpArray(rxtrig[subst $i]cnt) 0
   }   
         
   set portHandle [_ntgrGetTrafficPortHandle $chassis_id $pt]
   set aTmpArray(txsigframecnt) [_ixiaReportTxSigCount $portHandle]
   set aTmpArray(rxsigframecnt) [_ixiaReportRxSigCount $portHandle]
   Netgear_log_file "lib-ixia.tcl" "txsigframecnt is: $aTmpArray(txsigframecnt)"
   Netgear_log_file "lib-ixia.tcl" "rxsigframecnt is: $aTmpArray(rxsigframecnt)"
}

#*******************************************************************************
#
#  Function Name  :  _ixiaBuildIpv6stream
#
#  Description    : This function is called to build ipv6 header
#
#  Usage          :  _ixiaBuildIpv6stream  
#
#*******************************************************************************
proc _ixiaBuildIpv6stream {} {
    uplevel {
        _ixiaCreateIPv6Stream $ipv6_stream
        set protocol [_ntgrGetIPv6NextHdr $ipv6_stream] 
        Netgear_log_file "lib-ixia.tcl" "ipv6 next head is: $protocol"    
        # Create IPv6 Extend Header
        set ipv6Exthdr [_ntgrGetIPv6ExtHdr $chassis_id $pt $streamId]
        
        if { $ipv6Exthdr != {}} {
            _ixiaCreateIPv6ExtStream $protocol $chassis_id $pt $ipv6Exthdr $streamId
            set protocol [_ixiaSearchExtHead $protocol $chassis_id $pt $streamId]
            switch -exact -- $protocol {
              6   {ipV6 addExtensionHeader tcp; Netgear_log_file "lib-ixia.tcl" "add ipv6 tcp extend header"}
              17  {ipV6 addExtensionHeader udp; Netgear_log_file "lib-ixia.tcl" "add ipv6 udp extend header"}
              58  {ipV6 addExtensionHeader IpV4ProtocolIpv6Icmp; Netgear_log_file "lib-ixia.tcl" "add ipv6 \
                   icmp extend header" }
            }
            Netgear_log_file "lib-ixia.tcl" "success: create ipV6 extend header"
        }
    }
}

#*******************************************************************************
#
#  Function Name  :  _ixiaBuildTcpUdpstream
#
#  Description    : This function is called to build tcpudp header
#
#  Usage          :  _ixiaBuildTcpUdpstream 
#
#*******************************************************************************
proc _ixiaBuildTcpUdpstream {} {
  uplevel {
    _ixiaCreateTrafficTcpUdpStream $tcp_udp_stream $protocol
    if {$protocol == 6} {
    	if {[tcp set $chassis $slot $port]} {
    	    Netgear_log_file "lib-ixia.tcl" "Fail - Add TCP header"
    	} else {
    	    Netgear_log_file "lib-ixia.tcl" "Success - Add TCP header"
    	}
    } elseif {$protocol == 17} {
    	if {[udp set $chassis $slot $port]} {
    	    Netgear_log_file "lib-ixia.tcl" "Fail - Add UDP header"
    	} else {
    	    Netgear_log_file "lib-ixia.tcl" "Success - Add UDP header"
    	}
    } else {
    	  Netgear_log_file "lib-ixia.tcl" "ERROR - Unknown Protocol. Default is taken to be TCP"
        if {[tcp set $chassis $slot $port]} {
           Netgear_log_file "lib-ixia.tcl" "Fail - Add TCP header"
        } else {
           Netgear_log_file "lib-ixia.tcl" "Success - Add TCP header"
        }
    }	
  }
}

#*******************************************************************************
#
#  Function Name  :  _ixiaCreateTrafficCustomStream
#
#  Description    : This function is called to build custom header
#
#  Usage          :  _ixiaCreateTrafficCustomStream
#
#*******************************************************************************

proc _ixiaCreateTrafficCustomStream { custom_stream } {
    uplevel {
        set datapattern [_ntgrGetTrafficStreamCustomHeaderData $custom_stream]
        set frame_pad [_ntgrGetTrafficStreamFramePAD $chassis_id $pt $streamId]
        if {$frame_pad != {}} {
            set datalength [string length $datapattern]
            set pad_size [expr $frame_size-$datalength]
            set pad [string tolower $frame_pad 0 end]
            
            set pad [split $pad -]  
            for {set i 0} {$i < $pad_size} {set i [expr $i+2]} {
               for {set j 0} {$j < [llength $pad]} {incr j} {
                  append datapattern [lindex $pad $j]
               }
            }
        }
    	  stream config -dataPattern          userpattern
    		stream config -pattern              $datapattern
    		stream config -patternType          nonRepeat
    	  Netgear_log_file "lib-ixia.tcl" "Success - Add Custom header - $datapattern"
	  }
}

#*******************************************************************************
#
#  Function Name  :  _ixiaCreatePacketGroup
#
#  Description    : This function is called to create packet group
#                 Insert a PG signature , PGID = streamID
#  Usage          :  _ixiaCreatePacketGroup
#
#*******************************************************************************
proc _ixiaCreatePacketGroup {} {
  uplevel {
    packetGroup setDefault 
  	packetGroup config -insertSignature   true
  	packetGroup config -enableInsertPgid  true
  	packetGroup config -groupIdOffset     $pgidOffset
  	packetGroup config -signatureOffset   $psigOffset
  	Netgear_log_file "lib-ixia.tcl" "finished in create packet group ,signature offset $psigOffset"
	}
}

#*******************************************************************************
#
#  Function Name  :  _ixiaCreatePacketGroupRX
#
#  Description    : This function is configure signature offset and Group id offset of the port. in the Receive Mode.
#
#  Usage          :  _ixiaCreatePacketGroupRX signature_offset
#
#  Author         :  jim.xie
#
#*******************************************************************************
proc _ixiaCreatePacketGroupRX {} {
  uplevel {
  	packetGroup config -groupIdOffset     $groupId_offset
  	packetGroup config -signatureOffset   $signature_offset
    	
  	Netgear_log_file "lib-ixia.tcl" "finished set group RX ,signature offset $signature_offset"
	}
}

#*******************************************************************************
#
#  Function Name  : _ixiaUnLoadStream
#
#  Description    : This function is called to delete the streams created in the
#			  specified ports list.
#
#  Usage          : _ixiaUnLoadStream <chassis_id> <port>
#
#*******************************************************************************
proc _ixiaUnLoadStream { chassis_id portlist } {
	foreach port $portlist {
		_ntgrClearPortStreamHandle $chassis_id $port
	}
}

#*******************************************************************************
#
#  Function Name  : _ixiaLoadStream
#
#  Description    : This function is called to create the streams  in the
#			  specified ports list.
#
#  Usage          : _ixiaLoadStream <chassis_id> <port>
#
#*******************************************************************************
proc _ixiaLoadStream { chassis_id portlist } {
  set chassis_ip_addr [_ntgrGetTGIPAddr $chassis_id]
	chassis get $chassis_ip_addr
	set chassis	  [chassis cget -id]
	foreach pt $portlist {
      set slot        [_ntgrGetTrafficPortSlotId $chassis_id $pt]
      set port        [_ntgrGetTrafficPortId $chassis_id $pt]
      set port_lists  [_ntgrGetTrafficTestPortList $chassis_id]
      set ret         [lsearch -exact $port_lists $pt] 
  		if {$ret<0} { _ntgrAppendTrafficTestPortList $chassis_id $pt} 
  		_ntgrSetTrafficPortHandle $chassis_id $pt "$chassis/$slot/$port/$chassis_id/$pt"
  		set stream_count [_ntgrGetTrafficPortStreamCount $chassis_id $pt]
  		for {set i 1} {$i <= $stream_count} {incr i}	{
  				_ixiaCreateTraffic $chassis_id $pt $chassis $slot $port
  		}
	}
}







#=====================================dhcp functions========================================
#*******************************************************************************
#
#  Function Name  : _ixiaLoadDHCPClientConfig
#
#  Description    : This function is called to simulate dhcp client on one ixia port
#
#  Usage          : _ixiaLoadDHCPClientConfig <chassis_id> <port_local>
#
#*******************************************************************************
proc _ixiaLoadDHCPClientConfig { chassis_id port_local } {
    
    set flag_dhcp [llength [_ntgrGetDHCPCLIENTList $chassis_id $port_local]]
  	if {$flag_dhcp == 0} {
        return 0
  	}
  		
    #set chas [_ntgrGetTrafficPortChassisId $chassis_id $port_local]
    set chassis_ip_addr [_ntgrGetTGIPAddr $chassis_id]
	  chassis get $chassis_ip_addr
	  set chas [chassis cget -id]
    set card [_ntgrGetTrafficPortSlotId $chassis_id $port_local]
    set port [_ntgrGetTrafficPortId $chassis_id $port_local]
    
    Netgear_log_file "lib-ixia.tcl" "dhcp client config on port:  $chas-$card-$port"

    set dhcpclient_name  [lindex [_ntgrGetDHCPCLIENTList $chassis_id $port_local] 0]
    set dhcpclient_count [_ntgrGetDHCPCLIENTCount $dhcpclient_name]
    set dhcpclient_desc  [_ntgrGetDHCPCLIENTDescription $dhcpclient_name]
    set dhcpclient_mac   [_ntgrGetDHCPCLIENTMacAddress $dhcpclient_name]
	set dhcpincr_vlanid   [_ntgrGetDHCPINCRVLANID $dhcpclient_name]
    Netgear_log_file "lib-ixia.tcl" "dhcp client info:  name: $dhcpclient_name,  count: $dhcpclient_count, description: $dhcpclient_desc, mac: $dhcpclient_mac"
    
    if {$dhcpclient_count == 0} {
        Netgear_log_file "lib-ixia.tcl" "error: dhcp client count = 0"
        return 0
    }
    
    # initial interfaceTable
    interfaceTable select     $chas $card $port
    interfaceTable clearAllInterfaces
    interfaceTable setDefault
    interfaceTable config     -dhcpV4RequestRate   0
    interfaceTable config     -dhcpV6RequestRate   0
    interfaceTable set
    
    # Enable statistics
    stat config -enableDhcpStats true
    stat set $chas $card $port
    stat setDefault
    stat write $chas $card $port
    
    # add interface host
	set vlan_incr_index 0
    for {set i 0} {$i < $dhcpclient_count} {incr i} {
        set dhcpclient_desc_host "$dhcpclient_desc-$i"
        set dhcpclient_mac_host [_ntgrMacIncr $dhcpclient_mac $i]
        if {$dhcpincr_vlanid != ""} {
			set vlan_id [expr $dhcpincr_vlanid + $i]
		}
        # initial interface host
        interfaceEntry clearAllItems addressTypeIpV6
        interfaceEntry clearAllItems addressTypeIpV4
        interfaceEntry setDefault
        
        dhcpV4Properties setDefault
        dhcpV4Properties removeAllTlvs
        dhcpV4Properties config  -clientId ""
        dhcpV4Properties config  -serverId "0.0.0.0"
        dhcpV4Properties config  -vendorId ""
        dhcpV4Properties config  -renewTimer 0
        
        interfaceEntry config  -enable       true
		    interfaceEntry config  -description  "$dhcpclient_desc_host"
		    interfaceEntry config  -macAddress   "$dhcpclient_mac_host"
		    interfaceEntry config  -enableDhcp   true
			if {$dhcpincr_vlanid != ""} {
			    interfaceEntry config  -enableVlan   true
			    interfaceEntry config  -vlanId       $vlan_id
			}
		    
		    # add interface host
		    if {[interfaceTable addInterface interfaceTypeConnected]} {
		        Netgear_log_file "lib-ixia.tcl" "error: add dhcp host interface on port"
		        return 0
		    }
		    
		    # Send the interface table to the chassis by 50 interfaces and wait for 10 seconds
		    if {[expr $i % 50] == 0} {
		        if {[interfaceTable write]} {
                Netgear_log_file "lib-ixia.tcl" "error: add dhcp host interfaces on port to chassis"
                break
            }
            after 10000
		    }
    }
    
    # Send the interface table to the chassis by other interfaces
    if {[interfaceTable write]} {
        Netgear_log_file "lib-ixia.tcl" "error: add dhcp host interfaces on port to chassis"
		    return 0
    }
    after 10000
    
    
    # Wait for 60 seconds
    for {set i 0} {$i < 12} {incr i} {
        after 5000
        stat get statAllStats $chas $card $port
        set count_str [stat cget -dhcpV4EnabledInterfaces]
        if {$dhcpclient_count == $count_str} {
            break
        }
    }
    Netgear_log_file "lib-ixia.tcl" "dhcp client config finished $count_str on port:  $chas-$card-$port"
    return 1
}


#*******************************************************************************
#
#  Function Name  : _ixiaUnloadDHCPClientConfig
#
#  Description    : This function is called to delete dhcp client on one ixia port
#
#  Usage          : _ixiaUnloadDHCPClientConfig <chassis_id> <port_local>
#
#*******************************************************************************
proc _ixiaUnloadDHCPClientConfig { chassis_id port_local } {
    
    #set chas [_ntgrGetTrafficPortChassisId $chassis_id $port_local]
    set chassis_ip_addr [_ntgrGetTGIPAddr $chassis_id]
	  chassis get $chassis_ip_addr
	  set chas [chassis cget -id]
    set card [_ntgrGetTrafficPortSlotId $chassis_id $port_local]
    set port [_ntgrGetTrafficPortId $chassis_id $port_local]
    
    Netgear_log_file "lib-ixia.tcl" "delete dhcp client config on port:  $chas-$card-$port"

    # initial interfaceTable
    interfaceTable select $chas $card $port
    
    #interfaceTable clearAllInterfaces
    #after 5000
    set i 1
    for {set bRes [interfaceTable getFirstInterface]} {$bRes == 0} {set bRes [interfaceTable getNextInterface]} {
        interfaceTable delInterface
        after 50
        
        # Send the interface table to the chassis by 50 interfaces and wait for 10 seconds
		    if {[expr $i % 50] == 0} {
            if {[interfaceTable write]} {
                Netgear_log_file "lib-ixia.tcl" "error: delete dhcp host interface on port to chassis"
            }
            after 10000
        }
        incr i
    }
    
    # Send the interface table to the chassis by other interfaces
    if {[interfaceTable write]} {
        Netgear_log_file "lib-ixia.tcl" "error: delete dhcp host interface on port to chassis"
		    return 0
    }
    after 10000
    
    interfaceTable clearAllInterfaces
    
    # Send the interface table to the chassis
    if {[interfaceTable write]} {
        Netgear_log_file "lib-ixia.tcl" "error: clear all dhcp host interface on port to chassis"
		    return 0
    }
    after 10000
    
    Netgear_log_file "lib-ixia.tcl" "delete dhcp client config finished on port:  $chas-$card-$port"
    return 1
}

#*******************************************************************************
#
#  Function Name  : _ixiaReportDHCPClientAddrCount
#
#  Description    : This function is called to get dhcp client learned address count on one ixia port
#
#  Usage          : _ixiaReportDHCPClientAddrCount <chassis_id> <port_local>
#
#*******************************************************************************
proc _ixiaReportDHCPClientAddrCount { chassis_id port_local } {
    
    #set chas [_ntgrGetTrafficPortChassisId $chassis_id $port_local]
    set chassis_ip_addr [_ntgrGetTGIPAddr $chassis_id]
	  chassis get $chassis_ip_addr
	  set chas [chassis cget -id]
    set card [_ntgrGetTrafficPortSlotId $chassis_id $port_local]
    set port [_ntgrGetTrafficPortId $chassis_id $port_local]
    
    interfaceTable select $chas $card $port
    interfaceTable requestDiscoveredTable
    
    stat get statAllStats $chas $card $port
    after 2000
    set countStr [stat cget -dhcpV4AddressesLearned]
    
    Netgear_log_file "lib-ixia.tcl" "get dhcp client learned address count $countStr on port:  $chas-$card-$port"    
    return $countStr
}


#*******************************************************************************
#
#  Function Name  : _ixiaCheckDHCPClientInfo
#
#  Description    : This function is called to check dhcp client info on one ixia port
#
#  Usage          : _ixiaCheckDHCPClientInfo <chassis_id> <port_local> <checkinfo_list>
#
#                 the format of checkinfo_list:  type1 value1 tyep2 value2 ...
#                 usually type and value are hex or hex list, for example:
#                     set checkinfo_list "01" "00" "40" "00 00 00 01"
#                 if checking value of ip, networklength, gateway ip, lease time from dhcp server, please using format as follows:
#                     set checkinfo_list "ipStr" "1.1.1.1" "netLengthStr" "24" "gatewayStr" "1.1.1.254" "leaseStr" "600"
#
#*******************************************************************************
proc _ixiaCheckDHCPClientInfo { chassis_id port_local {checkinfo_list ""} } {
    
    set des_port ""
    set dhcp_ipaddr ""
    set dhcp_netmask ""
    set dhcp_gateway ""
    set dhcp_lease ""
    set dhcp_option_list {}
    
    set chassis_ip_addr [_ntgrGetTGIPAddr $chassis_id]
	  chassis get $chassis_ip_addr
	  set chas [chassis cget -id]
    set card [_ntgrGetTrafficPortSlotId $chassis_id $port_local]
    set port [_ntgrGetTrafficPortId $chassis_id $port_local]
    
    # initial interfaceTable
    interfaceTable select $chas $card $port
    interfaceTable getFirstInterface interfaceTypeConnected
    set des_port [interfaceEntry cget -description]
    if {[string length [string trim $des_port]] == 0} {
        Netgear_log_file "lib-ixia.tcl" "error: get dhcp client interface description on port:  $chas-$card-$port"
    }
    
    # get dhcp info
    if [interfaceTable requestDiscoveredTable] {
        Netgear_log_file "lib-ixia.tcl" "error: get dhcp client discovered table on port:  $chas-$card-$port"
    }
    after 5000
    
    set time_elapsed_ms 0
    while {[interfaceTable getDhcpV4DiscoveredInfo "$des_port"]} {
        if {$time_elapsed_ms > 10000} {
             Netgear_log_file "lib-ixia.tcl" "error: get dhcp client discovered info on port:  $chas-$card-$port"
             break
        }
        incr time_elapsed_ms 100
        after 100
    }
    
    after 5000
    set dhcp_ipaddr  [dhcpV4DiscoveredInfo cget -ipAddress]
    set dhcp_netmask [dhcpV4DiscoveredInfo cget -prefixLength]
    set dhcp_gateway [dhcpV4DiscoveredInfo cget -gatewayIpAddress]
    set dhcp_lease   [dhcpV4DiscoveredInfo cget -leaseDuration]
    Netgear_log_file "lib-ixia.tcl" "get dhcp client basic info on port: $chas-$card-$port,  dhcp_ipaddr: $dhcp_ipaddr,   dhcp_netmask: $dhcp_netmask,  dhcp_gateway: $dhcp_gateway,  dhcp_lease:  $dhcp_lease"
    
    for {set bRes [dhcpV4DiscoveredInfo getFirstTlv]} {$bRes == 0} {set bRes [dhcpV4DiscoveredInfo getNextTlv]} {
        set dhcp_opt_type  [dhcpV4Tlv cget -type]
        set dhcp_opt_value [dhcpV4Tlv cget -value]
        Netgear_log_file "lib-ixia.tcl" "get dhcp client option info on port: $chas-$card-$port,  dhcp_opt_type: $dhcp_opt_type;  dhcp_opt_value: $dhcp_opt_value"
        
        lappend dhcp_option_list $dhcp_opt_type $dhcp_opt_value
    }
    
    
    # checking info
    set result 1
    if {[llength $checkinfo_list] > 0} {
        foreach {typeItem valueItem} $checkinfo_list {
            switch $typeItem {
                "ipStr"         {
                    if {[string equal $valueItem $dhcp_ipaddr] == 0} {
                        set result 0
                        break
                    }
                }
                "netLengthStr"  {
                    if {$valueItem != $dhcp_netmask} {
                        set result 0
                        break 
                    }
                }
                "gatewayStr"    {
                    if {[string equal $valueItem $dhcp_gateway] == 0} {
                        set result 0
                        break
                    }
                }
                "leaseStr"      {
                    if {$valueItem != $dhcp_lease} {
                        set result 0
                        break
                    }
                }
                default  {
                    set flag_opt 0
                    foreach {i j} $dhcp_option_list {
                        if {[string equal $i $typeItem] == 1 && [string equal $j $valueItem] == 1} {
                            set flag_opt 1
                            break
                        }
                    }
                    if {$flag_opt == 0} {
                        set result 0
                        break
                    }
                }
            }
        }
    }
    return $result
}


#*******************************************************************************
#
#  Function Name  : _ixiaCheckDHCPClientInfoNotExist
#
#  Description    : This function is called to check dhcp client info not exist on one ixia port
#
#  Usage          : _ixiaCheckDHCPClientInfoNotExist <chassis_id> <port_local> <checkinfo_list>
#
#                 the format of checkinfo_list:  type1 value1 tyep2 value2 ...
#                 usually type and value are hex or hex list, for example:
#                     set checkinfo_list "01" "00" "40" "00 00 00 01"
#                 if checking value of ip, networklength, gateway ip, lease time from dhcp server, please using format as follows:
#                     set checkinfo_list "ipStr" "1.1.1.1" "netLengthStr" "24" "gatewayStr" "1.1.1.254" "leaseStr" "600"
#
#*******************************************************************************
proc _ixiaCheckDHCPClientInfoNotExist { chassis_id port_local {checkinfo_list ""} } {
    
    set des_port ""
    set dhcp_ipaddr ""
    set dhcp_netmask ""
    set dhcp_gateway ""
    set dhcp_lease ""
    set dhcp_option_list {}
    
    set chassis_ip_addr [_ntgrGetTGIPAddr $chassis_id]
	  chassis get $chassis_ip_addr
	  set chas [chassis cget -id]
    set card [_ntgrGetTrafficPortSlotId $chassis_id $port_local]
    set port [_ntgrGetTrafficPortId $chassis_id $port_local]
    
    # initial interfaceTable
    interfaceTable select $chas $card $port
    interfaceTable getFirstInterface interfaceTypeConnected
    set des_port [interfaceEntry cget -description]
    if {[string length [string trim $des_port]] == 0} {
        Netgear_log_file "lib-ixia.tcl" "error: get dhcp client interface description on port:  $chas-$card-$port"
    }
    
    # get dhcp info
    if [interfaceTable requestDiscoveredTable] {
        Netgear_log_file "lib-ixia.tcl" "error: get dhcp client discovered table on port:  $chas-$card-$port"
    }
    after 5000
    
    set time_elapsed_ms 0
    while {[interfaceTable getDhcpV4DiscoveredInfo "$des_port"]} {
        if {$time_elapsed_ms > 10000} {
             Netgear_log_file "lib-ixia.tcl" "error: get dhcp client discovered info on port:  $chas-$card-$port"
             break
        }
        incr time_elapsed_ms 100
        after 100
    }
    
    after 5000
    set dhcp_ipaddr  [dhcpV4DiscoveredInfo cget -ipAddress]
    set dhcp_netmask [dhcpV4DiscoveredInfo cget -prefixLength]
    set dhcp_gateway [dhcpV4DiscoveredInfo cget -gatewayIpAddress]
    set dhcp_lease   [dhcpV4DiscoveredInfo cget -leaseDuration]
    Netgear_log_file "lib-ixia.tcl" "get dhcp client basic info on port: $chas-$card-$port,  dhcp_ipaddr: $dhcp_ipaddr,   dhcp_netmask: $dhcp_netmask,  dhcp_gateway: $dhcp_gateway,  dhcp_lease:  $dhcp_lease"
    
    for {set bRes [dhcpV4DiscoveredInfo getFirstTlv]} {$bRes == 0} {set bRes [dhcpV4DiscoveredInfo getNextTlv]} {
        set dhcp_opt_type  [dhcpV4Tlv cget -type]
        set dhcp_opt_value [dhcpV4Tlv cget -value]
        Netgear_log_file "lib-ixia.tcl" "get dhcp client option info on port: $chas-$card-$port,  dhcp_opt_type: $dhcp_opt_type;  dhcp_opt_value: $dhcp_opt_value"
        
        lappend dhcp_option_list $dhcp_opt_type $dhcp_opt_value
    }
    
    
    # checking info
    set result 1
    if {[llength $checkinfo_list] > 0} {
        foreach {typeItem valueItem} $checkinfo_list {
            switch $typeItem {
                "ipStr"         {
                    if {[string equal $valueItem $dhcp_ipaddr] == 1} {
                        set result 0
                        break
                    }
                }
                "netLengthStr"  {
                    if {$valueItem == $dhcp_netmask} {
                        set result 0
                        break 
                    }
                }
                "gatewayStr"    {
                    if {[string equal $valueItem $dhcp_gateway] == 1} {
                        set result 0
                        break
                    }
                }
                "leaseStr"      {
                    if {$valueItem == $dhcp_lease} {
                        set result 0
                        break
                    }
                }
                default  {
                    set flag_opt 0
                    foreach {i j} $dhcp_option_list {
                        if {[string equal $i $typeItem] == 1} {
                            set flag_opt 1
                            break
                        }
                    }
                    if {$flag_opt == 1} {
                        set result 0
                        break
                    }
                }
            }
        }
    }
    return $result
}

#*******************************************************************************
#
#  Function Name	: _ixiaCreateTrafficL3ArpStream
#
#  Description    : This function is called to create ARP stream header
#         
#  Usage          : _ixiaCreateTrafficL3ArpStream <l3arp_stream>
# 
#*******************************************************************************
proc _ixiaCreateTrafficL3ArpStream {l3arp_stream} {
	uplevel 1 {
        set arpOperation         [_ntgrGetStreamL3ARPOperation $l3arp_stream]
        set arpSrcHwAddr         [_ntgrGetStreamL3ARPSrcHwAddr $l3arp_stream]
        set arpSrcHwMode         [_ntgrGetStreamL3ARPSrcHwMode $l3arp_stream]
        set arpSrcHwCnt          [_ntgrGetStreamL3ARPSrcHwCnt $l3arp_stream]
        set arpSrcProAddr        [_ntgrGetStreamL3ARPSrcProtocolAddr $l3arp_stream]
        set arpSrcProMode        [_ntgrGetStreamL3ARPSrcProtocolMode $l3arp_stream]
        set arpSrcProCnt         [_ntgrGetStreamL3ARPSrcProtocolCnt $l3arp_stream]
        set arpDstHwAddr         [_ntgrGetStreamL3ARPDstHwAddr $l3arp_stream]
        set arpDstHwMode         [_ntgrGetStreamL3ARPDstHwMode $l3arp_stream]
        set arpDstHwCnt          [_ntgrGetStreamL3ARPDstHwCnt $l3arp_stream]
		set arpDstProAddr        [_ntgrGetStreamL3ARPDstProtocolAddr $l3arp_stream]
        set arpDstProMode        [_ntgrGetStreamL3ARPDstProtocolMode $l3arp_stream]
        set arpDstProCnt         [_ntgrGetStreamL3ARPDstProtocolCnt $l3arp_stream]
		
        #Netgear_log_file "lib-ixia.tcl" "ARP_INFO: Mode - $arpOperation, IP - $dstIP, Mask - $dstMask, RepeatCount - $dstCnt, Class - $dstClass"
       # Netgear_log_file "lib-ixia.tcl" "SRC_IP_INFO: Mode - $srcMode, IP - $srcIP, Mask - $srcMask, RepeatCount - $srcCnt, Class - $srcClass"
       # Netgear_log_file "lib-ixia.tcl" "IP_HDR_INFO: Protocol - $ipProtocol, TTL - $ipTTL, Identification - $ipID, TOS - $ipTOS, useValidChecksum - $ipChecksum, Flag - $ipFlag, fragmentOffset - $ipOffset"

  		protocol config -name                               mac
  		protocol config -appName     Arp
      
        arp setDefault
        arp config -operation                             $arpOperation
        arp config -sourceHardwareAddr                         $arpSrcHwAddr
        arp config -sourceHardwareAddrMode                  $arpSrcHwMode
        arp config -sourceHardwareAddrRepeatCount                              $arpSrcHwCnt
        arp config -sourceProtocolAddr                           $arpSrcProAddr
        arp config -sourceProtocolAddrMode                           $arpSrcProMode
        arp config -sourceProtocolAddrRepeatCount                       $arpSrcProCnt
        arp config -destHardwareAddr                $arpDstHwAddr
        arp config -destHardwareAddrMode                            $arpDstHwMode
        arp config -destHardwareAddrRepeatCount                             $arpDstHwCnt
        arp config -destProtocolAddr                                    $arpDstProAddr
        arp config -destProtocolAddrMode                             $arpDstProMode
        arp config -destProtocolAddrRepeatCount                         $arpDstProCnt
        
	}
}

#*******************************************************************************
#
#  Function Name  : _ixiaReportArpReplyRxSigCount
#
#  Description    : This function is called to get TX rate via port handle
#
#  Usage          : _ixiaReportArpReplyRxSigCount <port_handle>
#  Add by         duke
#*******************************************************************************

proc _ixiaReportArpReplyRxSigCount {port_handle} {
    set port_handle [split $port_handle /]  
    set chas        [lindex $port_handle 0] 
    set slot        [lindex $port_handle 1] 
    set rxport      [lindex $port_handle 2] 
    set totalFrame  0
    stat get statAllStats $chas $slot $rxport
    set totalFrame [stat cget -rxArpReply]
    return $totalFrame

}

#*******************************************************************************
#
#  Function Name  : _ixiaReportArpRequestRxSigCount
#
#  Description    : This function is called to get TX rate via port handle
#
#  Usage          : _ixiaReportArpRequestRxSigCount <port_handle>
#  Add by         duke
#*******************************************************************************

proc _ixiaReportArpRequestRxSigCount {port_handle} {
    set port_handle [split $port_handle /]  
    set chas        [lindex $port_handle 0] 
    set slot        [lindex $port_handle 1] 
    set rxport      [lindex $port_handle 2] 
    set totalFrame  0
    stat get statAllStats $chas $slot $rxport
    set totalFrame [stat cget -rxArpRequest]
    return $totalFrame

}

#*******************************************************************************
#
#  Function Name  : _ixiaReportPingRequestRxSigCount
#
#  Description    : This function is called to get Ping receive package via port handle
#
#  Usage          : _ixiaReportPingRequestRxSigCount <port_handle>
#  Add by         jim.xie
#*******************************************************************************

proc _ixiaReportPingRequestRxSigCount {port_handle} {
    set port_handle [split $port_handle /]  
    set chas        [lindex $port_handle 0] 
    set slot        [lindex $port_handle 1] 
    set rxport      [lindex $port_handle 2] 
    set totalFrame  0
    stat get statAllStats $chas $slot $rxport
    set totalFrame [stat cget -rxPingRequest]
    return $totalFrame

} 


#=====================================dhcpv6 functions========================================
#*******************************************************************************
#
#  Function Name  : _ixiaLoadDHCPv6ClientConfig
#
#  Description    : This function is called to simulate dhcpv6 client on one ixia port
#
#  Usage          : _ixiaLoadDHCPv6ClientConfig <chassis_id> <port_local>
#
#  Author        :     jason
#*******************************************************************************
proc _ixiaLoadDHCPv6ClientConfig { chassis_id port_local } { 

    set flag_dhcpv6 [llength [_ntgrGetDHCPv6CLIENTList $chassis_id $port_local]]
    if {$flag_dhcpv6 == 0} {
        return 0
    }

    set chassis_ip_addr [_ntgrGetTGIPAddr $chassis_id]
    chassis get $chassis_ip_addr
    set chas [chassis cget -id]
    set card [_ntgrGetTrafficPortSlotId $chassis_id $port_local]
    set port [_ntgrGetTrafficPortId $chassis_id $port_local]
    
    Netgear_log_file "lib-ixia.tcl" "dhcpv6 client config on port:  $chas-$card-$port"

    set dhcpv6client_name  [lindex [_ntgrGetDHCPv6CLIENTList $chassis_id $port_local] 0]
    set dhcpv6client_count [_ntgrGetDHCPv6CLIENTCount $dhcpv6client_name]
    set dhcpv6client_desc  [_ntgrGetDHCPv6CLIENTDescription $dhcpv6client_name] 
    set dhcpv6client_mac  [_ntgrGetDHCPv6CLIENTMacAddress $dhcpv6client_name]    
    set dhcpv6client_iatype [_ntgrGetDHCPv6CLIENTIATYPE $dhcpv6client_name] 
    set dhcpv6client_iaid   [_ntgrGetDHCPv6CLIENTIAID $dhcpv6client_name] 
    set dhcpv6client_renewtimer   [_ntgrGetDHCPv6CLIENTRenewtimer $dhcpv6client_name]
    set dhcpv6client_relaylinkaddress   [_ntgrGetDHCPv6CLIENTRelayLinkAddress $dhcpv6client_name]
    set dhcpv6client_relaydstaddress   [_ntgrGetDHCPv6CLIENTRelaydstaddress $dhcpv6client_name]
    set dhcpv6client_eui64id   [_ntgrGetDHCPv6CLIENTeui64id $dhcpv6client_name]
    set dhcpv6client_ipv6gateway   [_ntgrGetDHCPv6CLIENTipv6gateway $dhcpv6client_name]
    set dhcpv6incr_vlanid   [_ntgrGetDHCPv6INCRVLANID $dhcpv6client_name]
    if {$dhcpv6client_count == 0} {
        Netgear_log_file "lib-ixia.tcl" "error: dhcp client count = 0"
        return 0
    }

    interfaceTable select     $chas $card $port
    interfaceTable clearAllInterfaces
    interfaceTable setDefault
    interfaceTable config     -dhcpV4RequestRate   0
    interfaceTable config     -dhcpV6RequestRate   0
    interfaceTable set

    # Enable statistics
    
    stat config -enableDhcpV6Stats true 
    stat set $chas $card $port
    stat setDefault
    stat write $chas $card $port
    for {set i 0} {$i < $dhcpv6client_count} {incr i} {
   
        set dhcpv6client_mac_host [_ntgrMacIncr $dhcpv6client_mac $i]
        if {$dhcpv6incr_vlanid != ""} {
            set vlan_id [expr $dhcpv6incr_vlanid + $i]
        }
        interfaceEntry clearAllItems addressTypeIpV6
        interfaceEntry clearAllItems addressTypeIpV4
        interfaceEntry setDefault 

        dhcpV6Properties removeAllTlvs 
        dhcpV6Properties setDefault 
        dhcpV6Properties config -iaType                             $dhcpv6client_iatype
        if {$dhcpv6client_iaid != ""} {
            dhcpV6Properties config -iaId                               $dhcpv6client_iaid
        }
       dhcpV6Properties config -renewTimer                         $dhcpv6client_renewtimer
       dhcpV6Properties config -relayLinkAddress                   "$dhcpv6client_relaylinkaddress"
       if {$dhcpv6client_relaydstaddress != ""} {
            dhcpV6Properties config -relayDestinationAddress            "$dhcpv6client_relaydstaddress"
        }
       interfaceEntry config -enable                             true 
       interfaceEntry config -description                        "$dhcpv6client_desc-$i" 
       interfaceEntry config -macAddress                         $dhcpv6client_mac_host
       if {$dhcpv6client_eui64id != ""} {
           interfaceEntry config -eui64Id                            $dhcpv6client_eui64id
       }
       interfaceEntry config -mtu                                1500
       interfaceEntry config -enableDhcp                         false
       if {$dhcpv6incr_vlanid != ""} {
           interfaceEntry config -enableVlan                         true
           interfaceEntry config -vlanId                             $vlan_id
       } else {
           interfaceEntry config -enableVlan                         false
           interfaceEntry config -vlanId                             0
       }
       interfaceEntry config -vlanPriority                       0
       interfaceEntry config -vlanTPID                           0x8100
       interfaceEntry config -enableDhcpV6                       true
       interfaceEntry config -ipV6Gateway                        $dhcpv6client_ipv6gateway 
       
       if {[interfaceTable addInterface interfaceTypeConnected]} { 
           Netgear_log_file "lib-ixia.tcl" "error: add dhcpv6 host interface on port"
           break
       }
       
       if {[interfaceTable write]} {
           Netgear_log_file "lib-ixia.tcl" "error: add dhcpv6 host interfaces on port to chassis"
           break
       }
       after 10000
    } 
}

#*******************************************************************************
#
#  Function Name  : _ixiaReportDHCPv6ClientAddrCount
#
#  Description    : This function is called to get dhcp client learned address count on one ixia port
#
#  Usage          : _ixiaReportDHCPv6ClientAddrCount <chassis_id> <port_local>
#
#*******************************************************************************
proc _ixiaReportDHCPv6ClientAddrCount { chassis_id port_local } {
    
    #set chas [_ntgrGetTrafficPortChassisId $chassis_id $port_local]
    set chassis_ip_addr [_ntgrGetTGIPAddr $chassis_id]
    chassis get $chassis_ip_addr
    set chas [chassis cget -id]
    set card [_ntgrGetTrafficPortSlotId $chassis_id $port_local]
    set port [_ntgrGetTrafficPortId $chassis_id $port_local]
    
    interfaceTable select $chas $card $port
    interfaceTable requestDiscoveredTable
    
    stat get statAllStats $chas $card $port
    after 2000
    set countStr [stat cget -dhcpV6AddressesLearned]
    
    Netgear_log_file "lib-ixia.tcl" "get dhcpv6 client learned address count $countStr on port:  $chas-$card-$port"    
    return $countStr
} 

#*******************************************************************************
#
#  Function Name  : _ixiaUnloadDHCPv6ClientConfig
#
#  Description    : This function is called to delete dhcp client on one ixia port
#
#  Usage          : _ixiaUnloadDHCPv6ClientConfig <chassis_id> <port_local>
#
#  Author        :     jason
#*******************************************************************************
proc _ixiaUnloadDHCPv6ClientConfig { chassis_id port_local } {
    
    set chassis_ip_addr [_ntgrGetTGIPAddr $chassis_id]
	  chassis get $chassis_ip_addr
	  set chas [chassis cget -id]
    set card [_ntgrGetTrafficPortSlotId $chassis_id $port_local]
    set port [_ntgrGetTrafficPortId $chassis_id $port_local]
    
    Netgear_log_file "lib-ixia.tcl" "delete dhcp client config on port:  $chas-$card-$port"

    interfaceTable select $chas $card $port
    
    set i 1
    for {set bRes [interfaceTable getFirstInterface]} {$bRes == 0} {set bRes [interfaceTable getNextInterface]} { 
        interfaceTable delInterface
        after 50
        
        # Send the interface table to the chassis by 50 interfaces and wait for 10 seconds
		    if {[expr $i % 50] == 0} {
            if {[interfaceTable write]} {
                Netgear_log_file "lib-ixia.tcl" "error: delete dhcp host interface on port to chassis"
            }
            after 10000
        }
        incr i
    }
    
    # Send the interface table to the chassis by other interfaces
    if {[interfaceTable write]} {
        Netgear_log_file "lib-ixia.tcl" "error: delete dhcp host interface on port to chassis"
		    return 0
    }
    after 10000
    
    interfaceTable clearAllInterfaces
    
    # Send the interface table to the chassis
    if {[interfaceTable write]} {
        Netgear_log_file "lib-ixia.tcl" "error: clear all dhcp host interface on port to chassis"
		return 0
    }
    after 10000
    
    Netgear_log_file "lib-ixia.tcl" "delete dhcp client config finished on port:  $chas-$card-$port"
    return 1
}
#*******************************************************************************
#
#  Function Name  : _ixiaCaptureClear
#
#  Description    : This function is called to Clear Statistic one ixia port
#
#  Usage          : _ixiaCaptureClear <chassis_id> <port_local>
#
#  Author        : jason
#*******************************************************************************
 proc _ixiaCaptureClear {chassis_id port_local} { 
     
	  set chassis_ip_addr [_ntgrGetTGIPAddr $chassis_id]
	  chassis get $chassis_ip_addr
	  set Chas [chassis cget -id]
      set Card [_ntgrGetTrafficPortSlotId $chassis_id $port_local]
      set Port [_ntgrGetTrafficPortId $chassis_id $port_local] 
    
        set retVal 0
        if {[ixStartPortCapture $Chas $Card $Port] != 0} {
             Netgear_log_file "lib-ixia.tcl" "Fail to start capture on $Chas $Card $Port"
            set retVal 1
        }
        if {[ixStopPortCapture $Chas $Card $Port] != 0} {
            Netgear_log_file "lib-ixia.tcl"  "Fail to stop capture on $Chas $Card $Port"
            set retVal 1
        }    
        return $retVal
    } 
#*******************************************************************************
#
#  Function Name  : _ixiaCapturePktCount
#
#  Description    : This function is called to Get Packet Statistic one ixia port
#
#  Usage          : _ixiaCapturePktCount <chassis_id> <port_local>
#
#  Author        : jason
#*******************************************************************************   
proc _ixiaCapturePktCount {chassis_id port_local} { 
	  
	  set chassis_ip_addr [_ntgrGetTGIPAddr $chassis_id]
	  chassis get $chassis_ip_addr
	  set Chas [chassis cget -id]
      set Card [_ntgrGetTrafficPortSlotId $chassis_id $port_local]
      set Port [_ntgrGetTrafficPortId $chassis_id $port_local] 
      
        set retVal 0
        set PktCnt 0

        if {[capture get $Chas $Card $Port]} {
            set retVal 1
            Netgear_log_file "lib-ixia.tcl"  "Fail to get capture from $Chas,$Card,$Port"
        } else {      
						set PktCnt [capture cget -nPackets]
    			  Netgear_log_file "lib-ixia.tcl"  "total $PktCnt packets captured"
        }
        lappend retList $retVal
        lappend retList $PktCnt 
        Netgear_log_file "lib-ixia.tcl" "retVal:$retVal"
        Netgear_log_file "lib-ixia.tcl" "PktCnt:$PktCnt"
        return $retList
    } 
#*******************************************************************************
#
#  Function Name  : _ixiaCapturePktGet
#
#  Description    : This function is called to Get Packet Statistic one ixia port
#
#  Usage          : _ixiaCapturePktGet <chassis_id> <port_local> <args>
#
#  Author        : jason
#*******************************************************************************  
proc _ixiaCapturePktGet {chassis_id port_local args} { 
  
		  	set chassis_ip_addr [_ntgrGetTGIPAddr $chassis_id]
		  	chassis get $chassis_ip_addr
		  	set Chas [chassis cget -id]
	      set Card [_ntgrGetTrafficPortSlotId $chassis_id $port_local]
	      set Port [_ntgrGetTrafficPortId $chassis_id $port_local] 
	    
        set retVal     0

        set args [string tolower $args]
        set tmpList    [lrange $args 0 end]
        set tmpllength [llength $tmpList]
        set idxxx      0
            
        #  Set the defaults
        set Index      0
        set Offset     0
        set Len        0
        set PktCnt     0
        set retList    {}
        
        while {$tmpllength > 0} {
            set cmdx [lindex $args $idxxx]
            set argx [lindex $args [expr $idxxx + 1]]  
           
            case $cmdx      {
                 -index     {set Index $argx}
                 -offset    {set Offset $argx}
                 -len       {set Len $argx}
                 default     { 
                     	set retVal 1
                      Netgear_log_file  "lib-ixia.tcl" "Error : cmd option $cmdx does not exist"
                     	return $retVal
                 }
            }
            incr idxxx  +2
            incr tmpllength -2
        }

        if {$retVal == 1} {
            lappend retList $retVal
            lappend retList $PktCnt
            return $retList
        }
     
        if {[capture get $Chas $Card $Port]} {
            set retVal 1
            Netgear_log_file  "lib-ixia.tcl" "Fail to get capture from $Chas,$Card,$Port"
        } else {
            set PktCnt [capture cget -nPackets]
            Netgear_log_file  "lib-ixia.tcl" "total $PktCnt packets captured"
        }
            
        #Get all the packet from Chassis to pc.
        if {($PktCnt > 0) && ($Index < $PktCnt)} {            
         
            if {[captureBuffer get $Chas $Card $Port 1 $PktCnt]} {
                set retVal 1
                Netgear_log_file  "lib-ixia.tcl"  "Fail to retrieve packets"
            } else {
              
                if {[captureBuffer getframe [expr $Index + 1]]} {
                    set retVal 1
                    Netgear_log_file  "lib-ixia.tcl"  "Fail to read Index $Index packet"
                }
            }            
            
            set data  [captureBuffer cget -frame]
            
            if {$Len == 0} {
                set Len [llength $data]
            }
            
            set byteList [list]
            for {set i $Offset} {$i < $Len} {incr i} {
              lappend byteList [lindex $data $i]
            }
            
            lappend retList $retVal
            lappend retList $byteList
            Netgear_log_file  "lib-ixia.tcl" "retVal=$retVal"
            Netgear_log_file  "lib-ixia.tcl" "byteList=$byteList"
        } elseif {$PktCnt == 0} {
            lappend retList $retVal
            lappend retList $PktCnt
        } else {
            
            lappend retList  1
            lappend retList 0
        }
        return $retList
    } 
#*******************************************************************************
#
#  Function Name  : _ixiaCheckPktInCaptureBuffer
#
#  Description    : This function is called to Check args in ixia port Packet Capture Buffer
#
#  Usage          : _ixiaCheckPktInCaptureBuffer <chassis_id> <port_local> <args>
#
#  Author        : jason
#*******************************************************************************  
proc _ixiaCheckPktInCaptureBuffer {chassis_id port_local args} { 
        
        set chassis_ip_addr [_ntgrGetTGIPAddr $chassis_id]
		    chassis get $chassis_ip_addr
		    set Chas [chassis cget -id]
	      set Card [_ntgrGetTrafficPortSlotId $chassis_id $port_local]
	      set Port [_ntgrGetTrafficPortId $chassis_id $port_local] 
       
        # set pkt flag 
        set result     0
        set args [string tolower $args]
        set tmpList    [lrange $args 0 end]
        set tmpllength [llength $tmpList]
        set idxxx      0
        set Pkt_str1 ""
        set Pkt_str2 ""
        set Pkt_str3 ""
        set Pkt_str4 ""
        set Pkt_str5 ""
        
        while {$tmpllength > 0} {
            set cmdx [lindex $args $idxxx]
            set argx [lindex $args [expr $idxxx + 1]]
            case $cmdx      {
                 -offset    {set Offset [expr $argx * 2]}
                 -len       {set Len $argx}
                 -pkt_str1       {set Pkt_str1 [string toupper $argx]}
                 -pkt_str2       {set Pkt_str2 [string toupper $argx]}
                 -pkt_str3       {set Pkt_str3 [string toupper $argx]}
                 -pkt_str4       {set Pkt_str4 [string toupper $argx]}
                 -pkt_str5       {set Pkt_str5 [string toupper $argx]}    
                 default     { 
                     set result 0
                     Netgear_log_file  "lib-ixia.tcl" "Error : cmd option $cmdx does not exist"
                     return $result
                 }
            }
            incr idxxx  +2
            incr tmpllength -2
        }
        
        if {[capture get $Chas $Card $Port]} {
            Netgear_log_file  "lib-ixia.tcl" "get capture from $Chas,$Card,$Port failed..."
        } else {
            set PktCnt [capture cget -nPackets]
            Netgear_log_file  "lib-ixia.tcl" "total $PktCnt packets captured"
            while {$PktCnt > 0} {
                #get packet from buffer
                 if {[captureBuffer get $Chas $Card $Port 1 $PktCnt]} {
                      set result 0
                      Netgear_log_file  "lib-ixia.tcl"  "retrieve packets failed..."
                  } else {
                      if {[captureBuffer getframe $PktCnt]} {
                        set result 0
                        Netgear_log_file  "lib-ixia.tcl"  "read PktCnt = $PktCnt packet failed..."
                         }
                  }           
                  set data  [captureBuffer cget -frame]    
                  regsub -all {[\s]} $data "" data  
                  if {$Len == 0} {
                         #set Len [string length $data]  
                         set SearchHdr [string range $data $Offset end]
                  } else {
                         set SearchHdr [string range $data $Offset $Len]
                  }
                # Netgear_log_file  "lib-ixia.tcl" "packet $PktCnt as :$data"
                
                # set SearchHdr [string range $data $Offset $Len]
                # Netgear_log_file  "lib-ixia.tcl"  "Offset =$Offset Len=$Len Search Header in :$SearchHdr" 
                 
                 # check packet hdr
                  set   result1 0
                 if {[string length $Pkt_str1] > 0} {
                     set result1 [string first $Pkt_str1 $SearchHdr]
                     Netgear_log_file  "lib-ixia.tcl"  "Packet $PktCnt:  $Pkt_str1 in Site $result1"
                     Netgear_log_file  "lib-ixia.tcl"  "Packet Header is :$SearchHdr" 
                 } 
                  set   result2 0
                 if {[string length $Pkt_str2] > 0} {
                      set result2 [string first $Pkt_str2 $SearchHdr]
                      Netgear_log_file  "lib-ixia.tcl"  "Packet $PktCnt:  $Pkt_str2 in Site $result2"
                      Netgear_log_file  "lib-ixia.tcl"  "Packet Header is :$SearchHdr"     
                 }
                  set   result3 0
                 if {[string length $Pkt_str3] > 0} {
                     set result3 [string first $Pkt_str3 $SearchHdr]
                      Netgear_log_file  "lib-ixia.tcl"  "Packet $PktCnt:  $Pkt_str3 in Site $result3"
                      Netgear_log_file  "lib-ixia.tcl"  "Packet Header is :$SearchHdr" 
                 }
                  set   result4 0
                 if {[string length $Pkt_str4] > 0} {
                     set result4 [string first $Pkt_str4 $SearchHdr]
                      Netgear_log_file  "lib-ixia.tcl"  "Packet $PktCnt:  $Pkt_str4 in Site $result4"
                      Netgear_log_file  "lib-ixia.tcl"  "Packet Header is :$SearchHdr" 
                 }
                  set   result5 0
                 if {[string length $Pkt_str5] > 0} {
                      set result5 [string first $Pkt_str5 $SearchHdr]
                      Netgear_log_file  "lib-ixia.tcl"  "Packet $PktCnt:  $Pkt_str5 in Site $result5"
                      Netgear_log_file  "lib-ixia.tcl"  "Packet Header is :$SearchHdr"
                 }
                 if {$result1 > -1 && $result2 > -1 && $result3 > -1 && $result4 > -1 && $result5 > -1 } {
                     set result 1
                     break
                 }
                 
                 set PktCnt [expr $PktCnt - 1]
            }
        }  
        return  $result
}

#*******************************************************************************
#
#  Function Name  : _ixiaCapturePortsStart
#
#  Description    : This function is called to Start Capture on  ixia port
#
#  Usage          : _ixiaCapturePortsStart <chassis_id> <port_local> 
#
#  Author        : jason
#*******************************************************************************
 proc _ixiaCaptureStart {chassis_id port_local} { 
 		 		set chassis_ip_addr [_ntgrGetTGIPAddr $chassis_id]
		  	chassis get $chassis_ip_addr
		  	set Chas [chassis cget -id]
	      set Card [_ntgrGetTrafficPortSlotId $chassis_id $port_local]
	      set Port [_ntgrGetTrafficPortId $chassis_id $port_local] 
	      
        set retVal 0
        Netgear_log_file "lib-ixia.tcl"  "start capture on $Chas $Card $Port"
               
        capture config -continuousFilter captureContinuousAll
        capture set $Chas $Card $Port
        lappend portList [list $Chas $Card $Port]
        if {[ixWriteConfigToHardware portList -noProtocolServer ]} {
            Netgear_log_file "lib-ixia.tcl"  "Unable to write configs to hardware!"
            set retVal 1
        }

        if {[ixStartPortCapture $Chas $Card $Port] != 0} {
            Netgear_log_file "lib-ixia.tcl"  "Fail to stop capture on $Chas $Card $Port"
            set retVal 1
        }
        return $retVal
    } 
    
 #*******************************************************************************
#
#  Function Name  : _ixiaCaptureStop
#
#  Description    : This function is called to Stop Capture on  ixia port
#
#  Usage          : _ixiaCaptureStop <chassis_id> <port_local> 
#
#  Author        : jason
#*******************************************************************************  
  proc _ixiaCaptureStop {chassis_id port_local} { 
   		  set chassis_ip_addr [_ntgrGetTGIPAddr $chassis_id]
		  	chassis get $chassis_ip_addr
		  	set Chas [chassis cget -id]
	      set Card [_ntgrGetTrafficPortSlotId $chassis_id $port_local]
	      set Port [_ntgrGetTrafficPortId $chassis_id $port_local]  
	      
        set retVal 0
        if {[ixStopPortCapture $Chas $Card $Port] != 0} {
            Netgear_log_file "lib-ixia.tcl" "Fail to start capture on $Chas $Card $Port"
            set retVal 1
        }
        return $retVal
    } 
#*******************************************************************************
#
#  Function Name  : _ixiafilterSet
#
#  Description    : This function is called to Stop Capture on  ixia port
#
#  Usage          : _ixiafilterSet <chassis_id> <port_local> <Mode> <args>
#
#  Author        : jason
#******************************************************************************* 
  
proc _ixiafilterSet {chassis_id port_local Mode args} { 

 	     	set chassis_ip_addr [_ntgrGetTGIPAddr $chassis_id]
		 		chassis get $chassis_ip_addr
		  	set Chas [chassis cget -id]
	      set Card [_ntgrGetTrafficPortSlotId $chassis_id $port_local]
	      set Port [_ntgrGetTrafficPortId $chassis_id $port_local]  
	      
        set args [string tolower $args]
        set retVal     0

        set tmpList    [lrange $args 0 end]
        set idxxx      0
        set tmpllength [llength $tmpList]
        
        
        #  Set the defaults
        set Offset1      0
        set Mask1        {00}
        set Value1       0
        set Offset2      0
        set Mask2        {00}
        set Value2       0
        while { $tmpllength > 0  } {
            set cmdx [lindex $args $idxxx]
            set argx [lindex $args [expr $idxxx + 1]]
    
            case $cmdx      {
                -offset1      {set Offset1 $argx}
                -value1       {set Value1 $argx}
                -mask1        {set Mask1 $argx}
                -offset2      {set Offset2 $argx}
                -value2       {set Value2 $argx}
                -mask2        {set Mask2 $argx}
                default     {
                     set  retVal 1
                     Netgear_log_file "lib-ixia.tcl" "Error : cmd option $cmdx does not exist"
                    return $retVal
                }
            }
            incr idxxx  +2
            incr tmpllength -2
        }        
        
        set Mode [string toupper $Mode]
        switch $Mode {
            "F1" {
                set TriggerMode pattern1 
            }
            "F2" {
                set TriggerMode pattern2
            }
            "F1ANDF2" {
                set TriggerMode pattern1AndPattern2
            }
            "F1ORF2" {
                set TriggerMode 6
            }
            
        }
        capture                      setDefault        
        capture                      set               $Chas $Card $Port
        filter                       setDefault        
				filter config -userDefinedStat1Pattern $TriggerMode
				filter config -userDefinedStat1Enable true
				filter config -captureFilterPattern $TriggerMode
				filter config -captureFilterEnable true
				filter config -captureTriggerPattern $TriggerMode
				filter config -captureTriggerEnable true
        filter                       set               $Chas $Card $Port
        filterPallette setDefault        
        filterPallette config -pattern1 $Value1
        filterPallette config -pattern2 $Value2
        filterPallette config -patternOffset1 $Offset1
        filterPallette config -patternOffset2 $Offset2
 				filterPallette config -patternMask1 $Mask1
				filterPallette config -patternMask2 $Mask2

        filterPallette set $Chas $Card $Port
        lappend portList [list $Chas $Card $Port]
       
        if [ixWriteConfigToHardware portList -noProtocolServer ] {
            Netgear_log_file "lib-ixia.tcl" "Fail to write configs to hardware!"
            set retVal 1
        }
        return $retVal       
    }


#*******************************************************************************
#
#  Function Name  : _ixiaLoadIPv6SubInterface
#
#  Description    : This function is called to load ipv6 sub-interface 
#
#  Usage          : _ixiaLoadIPv6SubInterface <chassis_id> <port_local>
#
#  Author        : jim.xie
#******************************************************************************* 
proc _ixiaLoadIPv6SubInterface { chassis_id port_local } {

    set sub_interface [_ntgrGetSubIfList $chassis_id $port_local]
    set num_sub [llength $sub_interface]
    
    set chas [_ntgrGetTrafficPortChassisId $chassis_id $port_local]
    set card [_ntgrGetTrafficPortSlotId $chassis_id $port_local]
    set port [_ntgrGetTrafficPortId $chassis_id $port_local]

    set ipv6_addr [_ntgrGetPortIPv6Addr $chassis_id $port_local]
    set ipv6_mask [_ntgrGetPortIPv6MaskLen $chassis_id $port_local]
    set ipv6_gate_way [_ntgrGetPortGwIPv6Addr $chassis_id $port_local]
    
    interfaceTable               select            $chas $card $port
    interfaceTable               setDefault        
    interfaceTable               config            -dhcpV4RequestRate          0
    interfaceTable               config            -dhcpV6RequestRate          0
    interfaceTable               set               
    interfaceTable               clearAllInterfaces 
    interfaceEntry               clearAllItems     addressTypeIpV6
    interfaceEntry               clearAllItems     addressTypeIpV4
    interfaceEntry               setDefault

    
    if {$num_sub != 0} {
        foreach sub_int $sub_interface {
            set ipv6_addr [lindex $sub_int 0]
            set ipv6_mask [lindex $sub_int 1]
            set ipv6_gate_way [lindex $sub_int 2]
            set myMac [lindex $sub_int 3]
            set description_if [lindex $sub_int 4]
            Netgear_log_file "lib-ixia.tcl" "ipv6 sub interface info: ipv6_addr=$ipv6_addr ipv6_mask=$ipv6_mask ipv6_gate_way=$ipv6_gate_way"
            interfaceEntry       clearAllItems     addressTypeIpV6
            interfaceEntry       clearAllItems     addressTypeIpV4
            interfaceEntry       setDefault 

            interfaceIpV6                setDefault
            interfaceIpV6                config            -ipAddress            "$ipv6_addr"
            interfaceIpV6                config            -maskWidth            "$ipv6_mask"
            interfaceEntry               addItem           addressTypeIpV6

            interfaceEntry               config            -enable                     true
            interfaceEntry               config            -description                "$description_if"
            interfaceEntry               config            -macAddress                 "$myMac"
            interfaceEntry               config            -ipV6Gateway                "$ipv6_gate_way"
            
            if {[interfaceTable addInterface interfaceTypeConnected]} { 
                Netgear_log_file "lib-ixia.tcl" "error: add ipv6 sub interface on port"
                break
            }
       
            if {[interfaceTable write]} {
                Netgear_log_file "lib-ixia.tcl" "error: add ipv6 sub interfaces on port to chassis"
                break
            }
            after 10000
        }
    }
}
