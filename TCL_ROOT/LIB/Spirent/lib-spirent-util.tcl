#!/bin/sh
################################################################################   
#
#  File Name		: lib-spirent-util.tcl
#
#  Description       	:
#         This TCL contains utility functions to used by Spirent Test center
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
#  Function Name	: _spirentSetTrafficPortDuplex
#
#  Description    : This functions sets the Spirent equivalent for port duplexity
#         
#  Usage          : _spirentSetTrafficPortDuplex <temp>
# 
#*******************************************************************************
proc _spirentSetTrafficPortDuplex {temp} {
	upvar $temp duplex
	if {$duplex == "half"} {
		set duplex HALF_DUPLEX
	} elseif {$duplex == "full"} {
		set duplex FULL_DUPLEX	
	} else {
		Netgear_log_file "lib-spirent-util.tcl" "ERROR: Duplex value should be <half> or <full>"
		Netgear_log_file "lib-spirent-util.tcl" "Default is FULL_DUPLEX"
		set duplex FULL_DUPLEX	
	}
}

#*******************************************************************************
#
#  Function Name	: _spirentSetTrafficPortFlowCtrl
#
#  Description    : This functions sets the Spirent equivalent for port flow control
#         
#  Usage          : _spirentSetTrafficPortFlowCtrl <temp>
# 
#*******************************************************************************
proc _spirentSetTrafficPortFlowCtrl {temp} {
	upvar $temp flow_ctrl
	if {$flow_ctrl == "disable"} {
		set flow_ctrl FALSE
	} elseif {$flow_ctrl == "enable"} {
		set flow_ctrl TRUE
	} else {
		Netgear_log_file "lib-spirent-util.tcl" "ERROR: FLow control value should be <enable> or <disable>"
		Netgear_log_file "lib-spirent-util.tcl" "Default is <disable>"
		set flow_ctrl FALSE
	}
}

#*******************************************************************************
#
#  Function Name	: _spirentSetTrafficPortAutoNeg
#
#  Description    : This functions sets the Spirent equivalent for port auto neg
#         
#  Usage          : _spirentSetTrafficPortAutoNeg <temp>
# 
#*******************************************************************************
proc _spirentSetTrafficPortAutoNeg {temp} {
	upvar $temp auto_neg
	if {$auto_neg == "disable"} {
		set auto_neg AUTONEG_DISABLE
	} elseif {$auto_neg == "enable"} {
		set auto_neg AUTONEG_ENABLE
	} elseif {$auto_neg == "force"} {
	  set auto_neg AUTONEG_FORCE
	}	else {
		Netgear_log_file "lib-spirent-util.tcl" "ERROR: AutoNeg value should be <enable> or <disable> or <force>"
		Netgear_log_file "lib-spirent-util.tcl" "Default is AUTONEG_ENABLE"
		set auto_neg AUTONEG_ENABLE
	}
}

#*******************************************************************************
#
#  Function Name	: _spirentSetTrafficPortSpeed
#
#  Description    : This functions sets the Spirent equivalent for port speed
#         
#  Usage          : _spirentSetTrafficPortSpeed <temp>
# 
#*******************************************************************************
proc _spirentSetTrafficPortSpeed {temp} {
	upvar $temp speed
	if {$speed == 10} {
		set speed 10MBPS
	} elseif {$speed == 100} {
		set speed 100MBPS
	} elseif {$speed == 10000} {
		set speed 10GBPS
	} elseif {$speed == 1000} {
		set speed 1GBPS
	} else {	
		set speed 1GBPS
		Netgear_log_file "lib-spirent-util.tcl" "ERROR: Speed value should be <10> or <100> or <1000> or <10000>"
		Netgear_log_file "lib-spirent-util.tcl" "Default is 1GPBS"
	}
}

#*******************************************************************************
#
#  Function Name	: _spirentSetTrafficPortPMode
#
#  Description    : This functions sets the Spirent equivalent for port P Mode
#         
#  Usage          : _spirentSetTrafficPortPMode <temp>
# 
#*******************************************************************************
proc _spirentSetTrafficPortPMode {temp} {
	upvar $temp mode
	if {$mode == "fiber"} {
		set mode PORT_MODE_FIBER
	} elseif {$mode == "copper"} {
		set mode PORT_MODE_COPPER	
	} else {
		Netgear_log_file "lib-spirent-util.tcl" "ERROR: PMODE value should be <copper> or <fiber>"
		Netgear_log_file "lib-spirent-util.tcl" "Default is PORT_MODE_COPPER"
		set mode PORT_MODE_COPPER	
	}
}

#*******************************************************************************
#
#  Function Name	: _spirentSetTrafficPortDurationParams
#
#  Description    : This functions sets the Spirent equivalent for Duration parameters
#         
#  Usage          : _spirentSetTrafficPortDurationParams <temp1> <temp2> <temp3>
#			                                       <chassis_id> <pt>
# 
#*******************************************************************************
proc _spirentSetTrafficPortDurationParams {temp1 temp2 temp3 chassis_id pt} {
	upvar $temp1 duration_type
	upvar $temp2 burst_size
	upvar $temp3 duration
	if {$duration_type == "continuous"} {
		set duration_type DURATION_MODE_CONTINUOUS
  	} elseif {$duration_type == "burst"} {
		set duration_type DURATION_MODE_BURSTS
		set burst_size [_ntgrGetTrafficPortBurstSize $chassis_id $pt]
	} elseif {$duration_type == "seconds"} {
		set duration_type DURATION_MODE_SECS
		set duration [_ntgrGetTrafficPortDuration $chassis_id $pt]
	} else {
		Netgear_log_file "lib-spirent-util.tcl" "ERROR: Duration type value should be <continous> or <burst> or <second>"
		Netgear_log_file "lib-spirent-util.tcl" "Default is DURATION_MODE_CONTINOUS"
		set duration_type DURATION_MODE_CONTINUOUS
	}
}

#*******************************************************************************
#
#  Function Name	: _spirentSetTrafficPortLoadType
#
#  Description    : This functions sets the Spirent equivalent for Load Type
#         
#  Usage          : _spirentSetTrafficPortLoadType <temp>
# 
#*******************************************************************************
proc _spirentSetTrafficPortLoadType {temp} {
	upvar $temp load_type
	if {$load_type == "packets_per_sec"} {
		set load_type LOAD_UNIT_FRAMES_PER_SEC
	} elseif {$load_type =="percentage"}  {
		set load_type LOAD_UNIT_PERCENT_LINE_RATE	
	} else {
		Netgear_log_file "lib-spirent-util.tcl" "ERROR: Load type value should be <packets_per_sec> or <percentage>"
		Netgear_log_file "lib-spirent-util.tcl" "Default is  <percentage> "
		set load_type LOAD_UNIT_PERCENT_LINE_RATE
	}
}

#*******************************************************************************
#
#  Function Name	: _spirentSetTrafficPortCRCError
#
#  Description    : This functions sets the Spirent equivalent for CRC Error
#         
#  Usage          : _spirentSetTrafficPortCRCError <temp>
# 
#*******************************************************************************
proc _spirentSetTrafficPortCRCError {temp} {
	upvar $temp crc_error
	if {$crc_error == "disable"} {
		set crc_error FALSE
	} elseif {$crc_error == "enable"} {
		set crc_error TRUE
	} else {
		Netgear_log_file "lib-spirent-util.tcl" "ERROR: CRC error value should be <enable> or <disable>"
		Netgear_log_file "lib-spirent-util.tcl" "Default is <disable>"
		set crc_error FALSE
	}
}

#*******************************************************************************
#
#  Function Name	: _spirentSetTrafficPortFrameType
#
#  Description    : This functions sets the Spirent equivalent for Frame Type
#         
#  Usage          : _spirentSetTrafficPortFrameType <temp>
# 
#*******************************************************************************
proc _spirentSetTrafficPortFrameType {temp} {
	upvar $temp frame_type
	if {$frame_type == "random"} {
		set frame_type FRAME_SIZE_MODE_RAND
	} elseif {$frame_type == "fixed"} {
		set frame_type FRAME_SIZE_MODE_FIXED
	} else {
		Netgear_log_file "lib-spirent-util.tcl" "ERROR: Frame type value should be <fixed> or <random>"
		Netgear_log_file "lib-spirent-util.tcl" "Default is FRAME_SIZE_MODE_FIXED"
		set frame_type FRAME_SIZE_MODE_FIXED
	}
}

#*******************************************************************************
#
#  Function Name	: _spirentSetTrafficPortFrameParams
#
#  Description    : This functions sets the Spirent equivalent for Frame parameters
#         
#  Usage          : _spirentSetTrafficPortFrameParams <temp1> <temp2> <temp3>...<temp7>
#			                                     <chassis_id> <pt>
# 
#*******************************************************************************
proc _spirentSetTrafficPortFrameParams {temp1 temp2 temp3 temp4 temp5 temp6 temp7 chassis_id pt} {
	upvar $temp1 frame_mode
	upvar $temp2 frame_min_size
	upvar $temp3 frame_max_size
	upvar $temp4 load
	upvar $temp5 load_type
	upvar $temp6 use_frame_on_port
	upvar $temp7 use_load_on_port

	if {$frame_mode == "random"} {
		set frame_mode FRAME_SIZE_MODE_RAND
		set frame_min_size [_ntgrGetTrafficPortFrameSizeMin $chassis_id $pt]
		set frame_max_size [_ntgrGetTrafficPortFrameSizeMax $chassis_id $pt]
		set use_frame_on_port TRUE
		set use_load_on_port TRUE
		set load [_ntgrGetTrafficPortLoad $chassis_id $pt]
		set load_type [_ntgrGetTrafficPortLoadType $chassis_id $pt]
		_spirentSetTrafficPortLoadType load_type
	} elseif {$frame_mode == "fixed"} {
		set frame_mode FRAME_SIZE_MODE_FIXED
	} elseif {$frame_mode == "both"} {
		set frame_mode FRAME_SIZE_MODE_RAND
		set frame_min_size [_ntgrGetTrafficPortFrameSizeMin $chassis_id $pt]
		set frame_max_size [_ntgrGetTrafficPortFrameSizeMax $chassis_id $pt]
		set use_load_on_port TRUE
		set load [_ntgrGetTrafficPortLoad $chassis_id $pt]
		set load_type [_ntgrGetTrafficPortLoadType $chassis_id $pt]
		_spirentSetTrafficPortLoadType load_type
	} else {
		Netgear_log_file "lib-spirent-util.tcl" "ERROR: Frame Size mode can be <fixed> or <random> or <both>"
		Netgear_log_file "lib-spirent-util.tcl" "Default is <no>"
		set frame_mode FRAME_SIZE_MODE_FIXED
	}
}

#*******************************************************************************
#
#  Function Name	: _spirentSetTrafficPortLoadParams
#
#  Description    : This functions sets the Spirent equivalent for Load parameters
#         
#  Usage          : _spirentSetTrafficPortLoadParams <temp1> <temp2> <temp3>...<temp7>
#			                                     <chassis_id> <pt>
# 
#*******************************************************************************
proc _spirentSetTrafficPortLoadParams {temp1 temp2 temp3 temp4 temp5 temp6 temp7 chassis_id pt} {
	upvar $temp1 load_mode
	upvar $temp2 load_min
	upvar $temp3 load_max
	upvar $temp4 load
	upvar $temp5 load_type
	upvar $temp6 use_frame_on_port
	upvar $temp7 use_load_on_port
	if {$load_mode == "random"} {
		set load_mode LOAD_MODE_RAND
		set load_min [_ntgrGetTrafficPortLoadMin $chassis_id $pt]
		set load_max [_ntgrGetTrafficPortLoadMax $chassis_id $pt]
		set use_load_on_port TRUE
		set load [_ntgrGetTrafficPortLoad $chassis_id $pt]
		set load_type [_ntgrGetTrafficPortLoadType $chassis_id $pt]
		set load_type [_ntgrGetTrafficPortLoadType $chassis_id $pt]
		_spirentSetTrafficPortLoadType load_type
	} elseif {$load_mode == "fixed"} {
		set load_mode LOAD_MODE_FIXED
	} else {
		Netgear_log_file "lib-spirent-util.tcl" "ERROR: Load mode can be <yes> or <no>"
		Netgear_log_file "lib-spirent-util.tcl" "Default is <fixed>"
		set load_mode LOAD_MODE_FIXED
	}
}