#!/bin/sh
################################################################################   
#
#  File Name		: lib-ixia-util.tcl
#
#  Description       	:
#         This TCL contains utility functions to used by IXIA
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
#  Function Name	: _ixiaSetTrafficPortDuplexSpeed
#
#  Description    : This functions sets the IXIA equivalent for port duplexity
#         
#  Usage          : _ixiaSetTrafficPortDuplexSpeed <temp> <speed>
# 
#*******************************************************************************
proc _ixiaSetTrafficPortDuplexSpeed {temp speed} {
	upvar $temp duplex
	if {$duplex == "half"} {
		if {$speed == 10} {
			set duplex "advertise10HalfDuplex"
		} elseif {$speed == 100} {
			set duplex "advertise100HalfDuplex"
		} else {
		  set duplex "advertise1000FullDuplex"
			Netgear_log_file "lib-ixia-util.tcl" "Half Duplex cannot be configured for 1G"
		}
	} else {
		if {$speed == 10} {
			set duplex "advertise10FullDuplex"
		} elseif {$speed == 100} {
			set duplex "advertise100FullDuplex"
		} else {
			set duplex "advertise1000FullDuplex"
		}
	}
}

#*******************************************************************************
#
#  Function Name	: _ixiaSetTrafficPortFlowCtrl
#
#  Description    : This functions sets the IXIA equivalent for port flow control
#         
#  Usage          : _ixiaSetTrafficPortFlowCtrl <temp>
# 
#*******************************************************************************
proc _ixiaSetTrafficPortFlowCtrl {temp} {
	upvar $temp flow_ctrl
	if {$flow_ctrl == "disable"} {
		set flow_ctrl false
	} elseif {$flow_ctrl == "enable"} {
		set flow_ctrl true
	} else {
		Netgear_log_file "lib-ixia-util.tcl" "ERROR: FLow control value should be <enable> or <disable>"
		Netgear_log_file "lib-ixia-util.tcl" "Default is <disable>"
		set flow_ctrl false
	}
}

#*******************************************************************************
#
#  Function Name	: _ixiaSetTrafficPortAutoNeg
#
#  Description    : This functions sets the IXIA equivalent for port auto neg
#         
#  Usage          : _ixiaSetTrafficPortAutoNeg <temp>
# 
#*******************************************************************************
proc _ixiaSetTrafficPortAutoNeg {temp} {
	upvar $temp auto_neg
	
	if {$auto_neg == "disable"} {
		set auto_neg false
	} elseif {$auto_neg == "enable"} {
		set auto_neg true
	} elseif {$auto_neg == "force"} {
		set auto_neg true
	} else {
		Netgear_log_file "lib-ixia-util.tcl" "ERROR: AutoNeg value should be <enable> or <disable>"
		Netgear_log_file "lib-ixia-util.tcl" "Default is <enable>"
		set auto_neg true
	}
}

#*******************************************************************************
#
#  Function Name	: _ixiaSetTrafficPortFrameMode
#
#  Description    : This functions sets the IXIA equivalent for port Frame Mode
#         
#  Usage          : _ixiaSetTrafficPortFrameMode <temp1> <temp2> <temp3> <temp4> <chassis_id> <port>
# 
#*******************************************************************************
proc _ixiaSetTrafficPortFrameMode {temp1 temp2 temp3 temp4 chassis_id port} {
	upvar $temp1 frame_mode
	upvar $temp2 frame_size
	upvar $temp3 frame_min_size
	upvar $temp4 frame_max_size
	
	if {$frame_mode == "fixed"} {
		set frame_mode "sizeFixed"
		set frame_size [_ntgrGetTrafficStreamFrameSize $chassis_id $port 1]
		set frame_min_size $frame_size
		set frame_max_size $frame_size
	} elseif {$frame_mode == "random"} {
		set frame_mode "sizeRandom"
		set frame_size 64
		set frame_min_size [_ntgrGetTrafficPortFrameSizeMin $chassis_id $port]
		set frame_max_size [_ntgrGetTrafficPortFrameSizeMax $chassis_id $port]

	} else {
		Netgear_log_file "lib-ixia-util.tcl" "ERROR: frame_mode value should be <fixed> or <random>"
		Netgear_log_file "lib-ixia-util.tcl" "Default is <fixed>"
		set frame_mode "sizeFixed"
		set frame_size [_ntgrGetTrafficStreamFrameSize $chassis_id $port 1]
		set frame_min_size $frame_size
		set frame_max_size $frame_size
	}
	Netgear_log_file "lib-ixia-util.tcl" "Frame Mode - $frame_mode, Frame Size - $frame_size"
	Netgear_log_file "lib-ixia-util.tcl" "FrameMinSize - $frame_min_size, FrameMaxSize - $frame_max_size"
}

#*******************************************************************************
#
#  Function Name	: _ixiaSetTrafficStreamFrameMode
#
#  Description    : This functions sets the IXIA equivalent for stream Frame Mode
#         
#  Usage          : _ixiaSetTrafficPortFrameMode <temp1> <temp2> <temp3> <temp4> 
#			   <chassis_id> <port> <steamId>
# 
#*******************************************************************************
proc _ixiaSetTrafficStreamFrameMode {temp1 temp2 temp3 temp4 chassis_id port streamId} {
	upvar $temp1 frame_mode
	upvar $temp2 frame_size
	upvar $temp3 frame_min_size
	upvar $temp4 frame_max_size
	
	if {$frame_mode == "fixed"} {
		set frame_mode "sizeFixed"
		set frame_size [_ntgrGetTrafficStreamFrameSize $chassis_id $port $streamId]
		set frame_min_size $frame_size
		set frame_max_size $frame_size
	} elseif {$frame_mode == "random"} {
		set frame_mode "sizeRandom"
		set frame_size [_ntgrGetTrafficStreamFrameSize $chassis_id $port $streamId]
		set frame_min_size [_ntgrGetTrafficStreamFrameSizeMin $chassis_id $port $streamId]
		set frame_max_size [_ntgrGetTrafficStreamFrameSizeMax $chassis_id $port $streamId] 

	} else {
		Netgear_log_file "lib-ixia-util.tcl" "ERROR: frame_mode value should be <fixed> or <random>"
		Netgear_log_file "lib-ixia-util.tcl" "Default is <fixed>"
		set frame_mode "sizeFixed"
		set frame_size [_ntgrGetTrafficStreamFrameSize $chassis_id $port 1]
		set frame_min_size $frame_size
		set frame_max_size $frame_size
	}
	Netgear_log_file "lib-ixia-util.tcl" "Frame Mode - $frame_mode, Frame Size - $frame_size"
	Netgear_log_file "lib-ixia-util.tcl" "FrameMinSize - $frame_min_size, FrameMaxSize - $frame_max_size"
}

#*******************************************************************************
#
#  Function Name	: _ixiaSetTrafficPortLoadMode
#
#  Description    : This functions sets the IXIA equivalent for port Load Mode
#         
#  Usage          : _ixiaSetTrafficPortLoadMode <temp>
# 
#*******************************************************************************
proc _ixiaSetTrafficPortLoadMode {temp} {
	upvar $temp load_type
	if {$load_type == "percentage"} {
		set load_type "streamRateModePercentRate"
	} elseif {$load_type == "packets_per_sec"} {
		set load_type "streamRateModeFps"
	} else {
		Netgear_log_file "lib-ixia-util.tcl" "ERROR: load mode value should be <percentage> or <packets_per_sec>"
		Netgear_log_file "lib-ixia-util.tcl" "Default is <percentage>"
		set load_type "streamRateModePercentRate"
	}
	Netgear_log_file "lib-ixia-util.tcl" "load Mode - $load_type"
}

#*******************************************************************************
#
#  Function Name	: _ixiaSetTrafficPortCRCError
#
#  Description    : This functions sets the IXIA equivalent for port CRC Error
#         
#  Usage          : _ixiaSetTrafficPortCRCError <temp>
# 
#*******************************************************************************
proc _ixiaSetTrafficPortCRCError {temp} {
  upvar $temp crc_error
	if {$crc_error == "disable"} {
		set crc_error "streamErrorGood"
	} elseif {$crc_error == "enable"} {
		set crc_error "streamErrorBadCRC"
	} else {
		Netgear_log_file "lib-ixia-util.tcl" "ERROR: CRC error value should be <enable> or <disable>"
		Netgear_log_file "lib-ixia-util.tcl" "Default is <disable>"
		set crc_error "streamErrorGood"
	}
	Netgear_log_file "lib-ixia-util.tcl" "CRC Error - $crc_error"
}

#*******************************************************************************
#
#  Function Name	: _ixiaSetTrafficPortDurationType
#
#  Description    : This functions sets the IXIA equivalent for port duration type
#         
#  Usage          : _ixiaSetTrafficPortDurationType <temp1> <temp2>
# 
#*******************************************************************************
proc _ixiaSetTrafficPortDurationType {temp1 temp2 chassis_id port} {
	upvar $temp1 duration_type
	upvar $temp2 burst_size

	if {$duration_type == "continuous"} {
		set duration_type "contPacket"
		set burst_size 1
	} elseif {$duration_type == "burst"} {
		set duration_type "contBurst"
		set burst_size [_ntgrGetTrafficPortBurstSize $chassis_id $port]
	} else {
		Netgear_log_file "lib-ixia-util.tcl" "ERROR: duration type value should be <continuous> or <burst>"
		Netgear_log_file "lib-ixia-util.tcl" "Default is <continuous>"
		set duration_type "contPacket"
		set burst_size 1
	}
	Netgear_log_file "lib-ixia-util.tcl" "Duration Type - $duration_type, burst size - $burst_size"
}

#*******************************************************************************
#
#  Function Name	: _ixiaSetRouterRipRecVersion
#
#  Description    : This functions sets the IXIA equivalent for port duration type
#         
#  Usage          : _ixiaSetRouterRipRecVersion <temp1>
# 
#*******************************************************************************
proc _ixiaSetRouterRipRecVersion {temp1} {
  #set rec_version ""
	#upvar $temp1 rec_version

	if {$temp1 == "RIP_V1"} {
		set rec_version "ripReceiveVersion1"
	} elseif {$temp1 == "RIP_V2"} {
		set rec_version "ripReceiveVersion2"
	} elseif {$temp1 == "RIP_V1+2"} {
		set rec_version "ripReceiveVersion1And2"
	} else {
	  Netgear_log_file "lib-ixia-util.tcl" "default of receive version is 2 and not support RIPNG"
	  set rec_version "ripReceiveVersion2"
	}
	return $rec_version
}

#*******************************************************************************
#
#  Function Name	: _ixiaSetRouterRipSendVersion
#
#  Description    : This functions sets the IXIA equivalent for port duration type
#         
#  Usage          : _ixiaSetRouterRipSendVersion <temp1>
# 
#*******************************************************************************
proc _ixiaSetRouterRipSendVersion {temp1 send_version} {
  #set send_type ""
	#upvar $temp1 send_type

	if {$temp1 == "CAST_TYPE_MULTICAST"} {
      set send_type "ripMulticast"
	} elseif {$temp1 == "CAST_TYPE_BROADCAST"} {
	    if {$send_version == "RIP_V1"} {
	        set send_type "ripBroadcastV1"
	    } else {
	        set send_type "ripBroadcastV2"
	    }
  } else {
      if {$send_version == "RIP_V1"} {
	        set send_type "ripBroadcastV1"
	    } else {
	        set send_type "ripMulticast"
	    }
	    #Netgear_log_file "lib-ixia-util.tcl" "Only support RIP on ethernet links"    
  }
	return $send_type
	
}

