#!/bin/sh
################################################################################   
#
#  File Name		: lib-tg-support.tcl
#
#  Description       	:
#         This TCL contains functions commonly used by traffic generators.
#
#  Revision History 	:
#         Date         Programmer        Description
#        -----------------------------------------------------------------------
#        02-May-06     Rajeswari V       Created
#	 08-Oct-07     Nina Cheng        Modified for RIP
#        29-Jan-07     Nina Cheng        Modified for OSPF
#	 15-Apr-08     Nina Cheng        Modified for TCP flag
#
################################################################################

#*******************************************************************************
#
#  Function Name	: _ntgrGetTGVendorName
#
#  Description    : This function is called to get the chassis vendor name from
#			  the global ntgr_tgList
#         
#  Usage          : _ntgrGetTGVendorName <tg_id>
# 
#*******************************************************************************
proc _ntgrGetTGVendorName {tg_id} {
	global _ntgr_tgList
	return [keylget _ntgr_tgList($tg_id) CHASSIS_VENDOR]
}

#*******************************************************************************
#
#  Function Name	: _ntgrGetTGModel
#
#  Description    : This function is called to get the chassis model from
#			  the global ntgr_tgList
#         
#  Usage          : _ntgrGetTGModel <tg_id>
# 
#*******************************************************************************
proc _ntgrGetTGModel {tg_id} {
	global _ntgr_tgList
	return [keylget _ntgr_tgList($tg_id) CHASSIS_MODEL]
}

#*******************************************************************************
#
#  Function Name	: _ntgrGetTGIPAddr
#
#  Description    : This function is called to get the chassis IP address from
#			  the global ntgr_tgList
#         
#  Usage          : _ntgrGetTGIPAddr <tg_id>
# 
#*******************************************************************************
proc _ntgrGetTGIPAddr {tg_id} {
	global _ntgr_tgList
	return [keylget _ntgr_tgList($tg_id) CHASSIS_IP_ADDR]
}

#*******************************************************************************
#
#  Function Name	: _ntgrGetTGHandle
#
#  Description    : This function is called to get the chassis handle from
#			  the global ntgr_tgList
#         
#  Usage          : _ntgrGetTGHandle <tg_id>
# 
#*******************************************************************************
proc _ntgrGetTGHandle {tg_id} {
	global _ntgr_tgList
	return [keylget _ntgr_tgList($tg_id) CHASSIS_HANDLE]
}

#*******************************************************************************
#
#  Function Name	: _ntgrGetTGProjectHandle
#
#  Description    : This function is called to get the switch project handle from
#			  the global ntgr_swList
#         
#  Usage          : _ntgrGetTGProjectHandle <switch_id>
# 
#*******************************************************************************
proc _ntgrGetTGProjectHandle {switch_id} {
	global _ntgr_tgList
	return [keylget _ntgr_tgList($switch_id) CHASSIS_PROJECT_HANDLE]
}

#*******************************************************************************
#
#  Function Name	: _ntgrSetTGHandle
#
#  Description    : This function is called to set the chassis handle in the 
#			  global ntgr_tgList
#         
#  Usage          : _ntgrSetTGHandle <tg_id> <handle_id>
# 
#*******************************************************************************
proc _ntgrSetTGHandle {tg_id handle_id} {
	global _ntgr_tgList
	keylset _ntgr_tgList($tg_id) CHASSIS_HANDLE $handle_id
}

#*******************************************************************************
#
#  Function Name	: _ntgrSetTGProjectHandle
#
#  Description    : This function is called to set the switch project handle in the 
#			  global ntgr_swList
#         
#  Usage          : _ntgrSetTGProjectHandle <switch_id> <handle_id>
# 
#*******************************************************************************
proc _ntgrSetTGProjectHandle {switch_id handle_id} {
	global _ntgr_tgList
	keylset _ntgr_tgList($switch_id) CHASSIS_PROJECT_HANDLE $handle_id
}

#*******************************************************************************
#
#  Function Name	: _ntgrGetTrafficPortChassisId
#
#  Description    : This function is called to get the chassis id from 
#			  the global ntgr_trafficPortInfo_<TGName>
#         
#  Usage          : _ntgrGetTrafficPortChassisId <chassis_id> <port> 
# 
#*******************************************************************************
proc _ntgrGetTrafficPortChassisId {chassis_id port} {
	global ntgr_trafficPortInfo_$chassis_id
	set port_info [keylget ntgr_trafficPortInfo_${chassis_id}($port) PORT_INFO]
	return [lindex $port_info 0]
}

#*******************************************************************************
#
#  Function Name	: _ntgrGetTrafficPortSlotId
#
#  Description    : This function is called to get the Slot from 
#			  the global ntgr_trafficPortInfo_<TGName>
#         
#  Usage          : _ntgrGetTrafficPortSlotId <chassis_id> <port> 
# 
#*******************************************************************************
proc _ntgrGetTrafficPortSlotId {chassis_id port} {
	global ntgr_trafficPortInfo_$chassis_id
	set port_info [keylget ntgr_trafficPortInfo_${chassis_id}($port) PORT_INFO]
	return [lindex $port_info 1]
}

#*******************************************************************************
#
#  Function Name	: _ntgrGetTrafficPortId
#
#  Description    : This function is called to get the Port from 
#			  the global ntgr_trafficPortInfo_<TGName>
#         
#  Usage          : _ntgrGetTrafficPortId <chassis_id> <port> 
# 
#*******************************************************************************
proc _ntgrGetTrafficPortId {chassis_id port} {
	global ntgr_trafficPortInfo_$chassis_id
	set port_info [keylget ntgr_trafficPortInfo_${chassis_id}($port) PORT_INFO]
	return [lindex $port_info 2]
}

#*******************************************************************************
#
#  Function Name	: _ntgrGetTrafficPortDuplex
#
#  Description    : This function is called to get the port duplex info from 
#			  the global ntgr_trafficPortInfo_<TGName>
#         
#  Usage          : _ntgrGetTrafficPortDuplex <chassis_id> <port> 
# 
#*******************************************************************************
proc _ntgrGetTrafficPortDuplex {chassis_id port} {
	global ntgr_trafficPortInfo_$chassis_id
	set ret "full"
	keylget ntgr_trafficPortInfo_${chassis_id}($port) TRAFFIC_PORT_DUPLEX ret
	return $ret
}

#--------- Added by kenddy --------------#

proc _ntgrGetTrafficPortTrig1Status {chassis_id port} {
	global ntgr_trafficPortInfo_$chassis_id
	set ret "FALSE"
	keylget ntgr_trafficPortInfo_${chassis_id}($port) TRAFFIC_PORT_TRIG1_FLAG ret
	return $ret

}

proc _ntgrGetTrafficPortTrig1OffSet {chassis_id port} {
	global ntgr_trafficPortInfo_$chassis_id
	set ret 0
	keylget ntgr_trafficPortInfo_${chassis_id}($port) TRAFFIC_PORT_TRIG1_OFFSET ret
	return $ret
}

proc _ntgrGetTrafficPortTrig1Pattern {chassis_id port} {
	global ntgr_trafficPortInfo_$chassis_id
	set ret ""
	keylget ntgr_trafficPortInfo_${chassis_id}($port) TRAFFIC_PORT_TRIG1_PATTERN ret
	return $ret
}

proc _ntgrGetTrafficPortTrig1Range {chassis_id port} {
	global ntgr_trafficPortInfo_$chassis_id
	set ret 4
	keylget ntgr_trafficPortInfo_${chassis_id}($port) TRAFFIC_PORT_TRIG1_RANGE ret
	return $ret
}

proc _ntgrGetTrafficPortTrig1Mode {chassis_id port} {
	global ntgr_trafficPortInfo_$chassis_id
	set ret "START_OF_FRAME"
	keylget ntgr_trafficPortInfo_${chassis_id}($port) TRAFFIC_PORT_TRIG1_MODE ret
	return $ret
}

proc _ntgrGetTrafficPortTrig2Status {chassis_id port} {
	global ntgr_trafficPortInfo_$chassis_id
	set ret "FALSE"
	keylget ntgr_trafficPortInfo_${chassis_id}($port) TRAFFIC_PORT_TRIG2_FLAG ret
	return $ret
	
}

proc _ntgrGetTrafficPortTrig2OffSet {chassis_id port} {
	global ntgr_trafficPortInfo_$chassis_id
	set ret 0
	keylget ntgr_trafficPortInfo_${chassis_id}($port) TRAFFIC_PORT_TRIG2_OFFSET ret
	return $ret
}

proc _ntgrGetTrafficPortTrig2Pattern {chassis_id port} {
	global ntgr_trafficPortInfo_$chassis_id
	set ret ""
	keylget ntgr_trafficPortInfo_${chassis_id}($port) TRAFFIC_PORT_TRIG2_PATTERN ret
	return $ret
}

proc _ntgrGetTrafficPortTrig2Range {chassis_id port} {
	global ntgr_trafficPortInfo_$chassis_id
	set ret 4
	keylget ntgr_trafficPortInfo_${chassis_id}($port) TRAFFIC_PORT_TRIG2_RANGE ret
	return $ret
}

proc _ntgrGetTrafficPortTrig2Mode {chassis_id port} {
	global ntgr_trafficPortInfo_$chassis_id
	set ret "START_OF_FRAME"
	keylget ntgr_trafficPortInfo_${chassis_id}($port) TRAFFIC_PORT_TRIG2_MODE ret
	return $ret
}

proc _ntgrGetTrafficPortTrig3Status {chassis_id port} {
	global ntgr_trafficPortInfo_$chassis_id
	set ret "FALSE"
	keylget ntgr_trafficPortInfo_${chassis_id}($port) TRAFFIC_PORT_TRIG3_FLAG ret
	return $ret
	
}

proc _ntgrGetTrafficPortTrig3OffSet {chassis_id port} {
	global ntgr_trafficPortInfo_$chassis_id
	set ret 0
	keylget ntgr_trafficPortInfo_${chassis_id}($port) TRAFFIC_PORT_TRIG3_OFFSET ret
	return $ret
}

proc _ntgrGetTrafficPortTrig3Pattern {chassis_id port} {
	global ntgr_trafficPortInfo_$chassis_id
	set ret ""
	keylget ntgr_trafficPortInfo_${chassis_id}($port) TRAFFIC_PORT_TRIG3_PATTERN ret
	return $ret
}

proc _ntgrGetTrafficPortTrig3Range {chassis_id port} {
	global ntgr_trafficPortInfo_$chassis_id
	set ret 4
	keylget ntgr_trafficPortInfo_${chassis_id}($port) TRAFFIC_PORT_TRIG3_RANGE ret
	return $ret
}

proc _ntgrGetTrafficPortTrig3Mode {chassis_id port} {
	global ntgr_trafficPortInfo_$chassis_id
	set ret "START_OF_FRAME"
	keylget ntgr_trafficPortInfo_${chassis_id}($port) TRAFFIC_PORT_TRIG3_MODE ret
	return $ret
}

proc _ntgrGetTrafficPortTrig4Status {chassis_id port} {
	global ntgr_trafficPortInfo_$chassis_id
	set ret "FALSE"
	keylget ntgr_trafficPortInfo_${chassis_id}($port) TRAFFIC_PORT_TRIG4_FLAG ret
	return $ret
	
}

proc _ntgrGetTrafficPortTrig4OffSet {chassis_id port} {
	global ntgr_trafficPortInfo_$chassis_id
	set ret 0
	keylget ntgr_trafficPortInfo_${chassis_id}($port) TRAFFIC_PORT_TRIG4_OFFSET ret
	return $ret
}

proc _ntgrGetTrafficPortTrig4Pattern {chassis_id port} {
	global ntgr_trafficPortInfo_$chassis_id
	set ret ""
	keylget ntgr_trafficPortInfo_${chassis_id}($port) TRAFFIC_PORT_TRIG4_PATTERN ret
	return $ret
}

proc _ntgrGetTrafficPortTrig4Range {chassis_id port} {
	global ntgr_trafficPortInfo_$chassis_id
	set ret 4
	keylget ntgr_trafficPortInfo_${chassis_id}($port) TRAFFIC_PORT_TRIG4_RANGE ret
	return $ret
}

proc _ntgrGetTrafficPortTrig4Mode {chassis_id port} {
	global ntgr_trafficPortInfo_$chassis_id
	set ret "START_OF_FRAME"
	keylget ntgr_trafficPortInfo_${chassis_id}($port) TRAFFIC_PORT_TRIG4_MODE ret
	return $ret
}

proc _ntgrGetTrafficPortTrig5Status {chassis_id port} {
	global ntgr_trafficPortInfo_$chassis_id
	set ret "FALSE"
	keylget ntgr_trafficPortInfo_${chassis_id}($port) TRAFFIC_PORT_TRIG5_FLAG ret
	return $ret
	
}

proc _ntgrGetTrafficPortTrig5OffSet {chassis_id port} {
	global ntgr_trafficPortInfo_$chassis_id
	set ret 0
	keylget ntgr_trafficPortInfo_${chassis_id}($port) TRAFFIC_PORT_TRIG5_OFFSET ret
	return $ret
}

proc _ntgrGetTrafficPortTrig5Pattern {chassis_id port} {
	global ntgr_trafficPortInfo_$chassis_id
	set ret ""
	keylget ntgr_trafficPortInfo_${chassis_id}($port) TRAFFIC_PORT_TRIG5_PATTERN ret
	return $ret
}

proc _ntgrGetTrafficPortTrig5Range {chassis_id port} {
	global ntgr_trafficPortInfo_$chassis_id
	set ret 4
	keylget ntgr_trafficPortInfo_${chassis_id}($port) TRAFFIC_PORT_TRIG5_RANGE ret
	return $ret
}

proc _ntgrGetTrafficPortTrig5Mode {chassis_id port} {
	global ntgr_trafficPortInfo_$chassis_id
	set ret "START_OF_FRAME"
	keylget ntgr_trafficPortInfo_${chassis_id}($port) TRAFFIC_PORT_TRIG5_MODE ret
	return $ret
}

proc _ntgrGetTrafficPortTrig6Status {chassis_id port} {
	global ntgr_trafficPortInfo_$chassis_id
	set ret "FALSE"
	keylget ntgr_trafficPortInfo_${chassis_id}($port) TRAFFIC_PORT_TRIG6_FLAG ret
	return $ret
	
}

proc _ntgrGetTrafficPortTrig6OffSet {chassis_id port} {
	global ntgr_trafficPortInfo_$chassis_id
	set ret 0
	keylget ntgr_trafficPortInfo_${chassis_id}($port) TRAFFIC_PORT_TRIG6_OFFSET ret
	return $ret
}

proc _ntgrGetTrafficPortTrig6Pattern {chassis_id port} {
	global ntgr_trafficPortInfo_$chassis_id
	set ret ""
	keylget ntgr_trafficPortInfo_${chassis_id}($port) TRAFFIC_PORT_TRIG6_PATTERN ret
	return $ret
}

proc _ntgrGetTrafficPortTrig6Range {chassis_id port} {
	global ntgr_trafficPortInfo_$chassis_id
	set ret 4
	keylget ntgr_trafficPortInfo_${chassis_id}($port) TRAFFIC_PORT_TRIG6_RANGE ret
	return $ret
}

proc _ntgrGetTrafficPortTrig6Mode {chassis_id port} {
	global ntgr_trafficPortInfo_$chassis_id
	set ret "START_OF_FRAME"
	keylget ntgr_trafficPortInfo_${chassis_id}($port) TRAFFIC_PORT_TRIG6_MODE ret
	return $ret
}

proc _ntgrGetTrafficPortTrig7Status {chassis_id port} {
	global ntgr_trafficPortInfo_$chassis_id
	set ret "FALSE"
	keylget ntgr_trafficPortInfo_${chassis_id}($port) TRAFFIC_PORT_TRIG7_FLAG ret
	return $ret
	
}

proc _ntgrGetTrafficPortTrig7OffSet {chassis_id port} {
	global ntgr_trafficPortInfo_$chassis_id
	set ret 0
	keylget ntgr_trafficPortInfo_${chassis_id}($port) TRAFFIC_PORT_TRIG7_OFFSET ret
	return $ret
}

proc _ntgrGetTrafficPortTrig7Pattern {chassis_id port} {
	global ntgr_trafficPortInfo_$chassis_id
	set ret ""
	keylget ntgr_trafficPortInfo_${chassis_id}($port) TRAFFIC_PORT_TRIG7_PATTERN ret
	return $ret
}

proc _ntgrGetTrafficPortTrig7Range {chassis_id port} {
	global ntgr_trafficPortInfo_$chassis_id
	set ret 4
	keylget ntgr_trafficPortInfo_${chassis_id}($port) TRAFFIC_PORT_TRIG7_RANGE ret
	return $ret
}

proc _ntgrGetTrafficPortTrig7Mode {chassis_id port} {
	global ntgr_trafficPortInfo_$chassis_id
	set ret "START_OF_FRAME"
	keylget ntgr_trafficPortInfo_${chassis_id}($port) TRAFFIC_PORT_TRIG7_MODE ret
	return $ret
}

proc _ntgrGetTrafficPortTrig8Status {chassis_id port} {
	global ntgr_trafficPortInfo_$chassis_id
	set ret "FALSE"
	keylget ntgr_trafficPortInfo_${chassis_id}($port) TRAFFIC_PORT_TRIG8_FLAG ret
	return $ret
	
}

proc _ntgrGetTrafficPortTrig8OffSet {chassis_id port} {
	global ntgr_trafficPortInfo_$chassis_id
	set ret 0
	keylget ntgr_trafficPortInfo_${chassis_id}($port) TRAFFIC_PORT_TRIG8_OFFSET ret
	return $ret
}

proc _ntgrGetTrafficPortTrig8Pattern {chassis_id port} {
	global ntgr_trafficPortInfo_$chassis_id
	set ret ""
	keylget ntgr_trafficPortInfo_${chassis_id}($port) TRAFFIC_PORT_TRIG8_PATTERN ret
	return $ret
}

proc _ntgrGetTrafficPortTrig8Range {chassis_id port} {
	global ntgr_trafficPortInfo_$chassis_id
	set ret 4
	keylget ntgr_trafficPortInfo_${chassis_id}($port) TRAFFIC_PORT_TRIG8_RANGE ret
	return $ret
}

proc _ntgrGetTrafficPortTrig8Mode {chassis_id port} {
	global ntgr_trafficPortInfo_$chassis_id
	set ret "START_OF_FRAME"
	keylget ntgr_trafficPortInfo_${chassis_id}($port) TRAFFIC_PORT_TRIG8_MODE ret
	return $ret
}


#*******************************************************************************
#
#  Function Name	: _ntgrGetTrafficPortAutoNeg
#
#  Description    : This function is called to get the  Auto Negotiation from 
#			  the global ntgr_trafficPortInfo_<TGName>
#         
#  Usage          : _ntgrGetTrafficPortAutoNeg <chassis_id> <port> 
# 
#*******************************************************************************
proc _ntgrGetTrafficPortAutoNeg {chassis_id port} {
	global ntgr_trafficPortInfo_$chassis_id
	set ret "enable"
	keylget ntgr_trafficPortInfo_${chassis_id}($port) TRAFFIC_PORT_AUTO_NEG ret
	return $ret
}

#*******************************************************************************
#
#  Function Name	: _ntgrGetTrafficPortFlowCtrl
#
#  Description    : This function is called to get the  Flow Control from 
#			  the global ntgr_trafficPortInfo_<TGName>
#         
#  Usage          : _ntgrGetTrafficPortFlowCtrl <chassis_id> <port> 
# 
#*******************************************************************************
proc _ntgrGetTrafficPortFlowCtrl {chassis_id port} {
	global ntgr_trafficPortInfo_$chassis_id
	set ret "disable"
	keylget ntgr_trafficPortInfo_${chassis_id}($port) TRAFFIC_PORT_FLOW_CTRL ret
	return $ret
}

#*******************************************************************************
#
#  Function Name	: _ntgrGetTrafficPortSpeed
#
#  Description    : This function is called to get the  Speed from 
#			  the global ntgr_trafficPortInfo_<TGName>
#         
#  Usage          : _ntgrGetTrafficPortSpeed <chassis_id> <port> 
# 
#*******************************************************************************
proc _ntgrGetTrafficPortSpeed {chassis_id port} {
	global ntgr_trafficPortInfo_$chassis_id
	set ret "1000"
	keylget ntgr_trafficPortInfo_${chassis_id}($port) TRAFFIC_PORT_SPEED ret
	set ret [string trim $ret]
	return $ret
}

#*******************************************************************************
#
#  Function Name	: _ntgrGetTrafficPortPMode
#
#  Description    : This function is called to get the P Mode from 
#			  the global ntgr_trafficPortInfo_<TGName>
#         
#  Usage          : _ntgrGetTrafficPortPMode <chassis_id> <port> 
# 
#*******************************************************************************
proc _ntgrGetTrafficPortPMode {chassis_id port} {
  set ret ""
  global ntgr_trafficPortInfo_$chassis_id
  if { $port <10 } {set tgport 0$port } else {set tgport $port}
  global PHY$tgport
	if {![info exists PHY$tgport]} {
	   keylget ntgr_trafficPortInfo_${chassis_id}($port) TRAFFIC_PORT_P_MODE ret
	} else {
	   set ret [subst  \$PHY$tgport]
	}
	return $ret
}

#*******************************************************************************
#
#  Function Name	: _ntgrGetTrafficPortStreamCount
#
#  Description    : This function is called to get the  stream count from 
#			  the global ntgr_trafficPortInfo_<TGName>
#         
#  Usage          : _ntgrGetTrafficPortStreamCount <chassis_id> <port> 
# 
#*******************************************************************************
proc _ntgrGetTrafficPortStreamCount {chassis_id port} {
	global ntgr_trafficPortInfo_$chassis_id
##	return [keylget ntgr_trafficPortInfo_${chassis_id}($port) TRAFFIC_PORT_STREAM_COUNT]
    set streamlist [_ntgrGetTrafficPortStreamNameList $chassis_id $port]
    return [llength $streamlist]; ## So we should not set TRAFFIC_PORT_STREAM_COUNT in definition. 
}

#*******************************************************************************
#
#  Function Name	: _ntgrGetTrafficPortStreamNameList
#
#  Description    : This function is called to get the  stream Name List from 
#			  the global ntgr_trafficPortInfo_<TGName>
#         
#  Usage          : _ntgrGetTrafficPortStreamNameList <chassis_id> <port> 
# 
#*******************************************************************************
proc _ntgrGetTrafficPortStreamNameList {chassis_id port} {
	global ntgr_trafficPortInfo_$chassis_id
	set ret {}
	keylget ntgr_trafficPortInfo_${chassis_id}($port) TRAFFIC_PORT_STREAM_LIST ret
	return $ret
}

#*******************************************************************************
#
#  Function Name	: _ntgrSetTrafficPortStreamNameList
#
#  Description    : This function is called to set stream Name List to
#			  the global ntgr_trafficPortInfo_<TGName>
#         
#  Usage          : _ntgrSetTrafficPortStreamNameList <chassis_id> <port> <streamlist>
# 
#*******************************************************************************
proc _ntgrSetTrafficPortStreamNameList {chassis_id port streamlist} {
	global ntgr_trafficPortInfo_$chassis_id
	keylset ntgr_trafficPortInfo_${chassis_id}($port) TRAFFIC_PORT_STREAM_LIST $streamlist
	keylset ntgr_trafficPortInfo_${chassis_id}($port) TRAFFIC_PORT_STREAM_COUNT [llength $streamlist]
}

#*******************************************************************************
#
#  Function Name	: _ntgrGetTrafficPortStreamName
#
#  Description    : This function is called to get the  stream Name from 
#			  the global ntgr_trafficPortInfo_<TGName>
#         
#  Usage          : _ntgrGetTrafficPortStreamName <chassis_id> <port> <stream>
# 
#*******************************************************************************
proc _ntgrGetTrafficPortStreamName {chassis_id port stream} {
	global ntgr_trafficPortInfo_$chassis_id
	set temp [keylget ntgr_trafficPortInfo_${chassis_id}($port) TRAFFIC_PORT_STREAM_LIST]
	incr stream -1
	return [lindex $temp $stream]
}

#*******************************************************************************
#
#  Function Name	: _ntgrGetTrafficPortHandle
#
#  Description    : This function is called to get the Handle  from 
#			  the global ntgr_trafficPortInfo_<TGName>
#         
#  Usage          : _ntgrGetTrafficPortHandle <chassis_id> <port> 
# 
#*******************************************************************************
proc _ntgrGetTrafficPortHandle {chassis_id port} {
	global _ntgr_tgList
	set existingHandle [keylget _ntgr_tgList($chassis_id) TRAFFIC_PORT_HANDLE]
	foreach portHandlePair $existingHandle {
		set port1 [lindex $portHandlePair 0 ]
		set handle [lrange $portHandlePair 1 end ]
		if { $port1 == $port } { 
			return $handle
		}
	}
}

#*******************************************************************************
#
#  Function Name	: _ntgrGetTrafficPortDurationType
#
#  Description    : This function is called to get the Duration Type  from 
#			  the global ntgr_trafficPortInfo_<TGName>
#         
#  Usage          : _ntgrGetTrafficPortDurationType <chassis_id> <port> 
# 
#*******************************************************************************
proc _ntgrGetTrafficPortDurationType {chassis_id port} {
	global ntgr_trafficPortInfo_$chassis_id
	set ret "continuous"
	keylget ntgr_trafficPortInfo_${chassis_id}($port) TRAFFIC_PORT_DURATION_TYPE ret
	return $ret
}

#*******************************************************************************
#
#  Function Name	: _ntgrGetTrafficPortDuration
#
#  Description    : This function is called to get the Duration  from 
#			  the global ntgr_trafficPortInfo_<TGName>
#         
#  Usage          : _ntgrGetTrafficPortDuration <chassis_id> <port> 
# 
#*******************************************************************************
proc _ntgrGetTrafficPortDuration {chassis_id port} {
	global ntgr_trafficPortInfo_$chassis_id
	return [keylget ntgr_trafficPortInfo_${chassis_id}($port) TRAFFIC_PORT_DURATION]
}

#*******************************************************************************
#
#  Function Name	: _ntgrGetTrafficPortBurstSize
#
#  Description    : This function is called to get the Burst Size  from 
#			  the global ntgr_trafficPortInfo_<TGName>
#         
#  Usage          : _ntgrGetTrafficPortBurstSize <chassis_id> <port> 
# 
#*******************************************************************************
proc _ntgrGetTrafficPortBurstSize {chassis_id port} {
	global ntgr_trafficPortInfo_$chassis_id
	return [keylget ntgr_trafficPortInfo_${chassis_id}($port) TRAFFIC_PORT_BURST_SIZE]
}

#*******************************************************************************
#
#  Function Name	: _ntgrGetTrafficPortFrameSizeMin
#
#  Description    : This function is called to get the min frame Size  from 
#			  the global ntgr_trafficPortInfo_<TGName>
#         
#  Usage          : _ntgrGetTrafficPortFrameSizeMin <chassis_id> <port> 
# 
#*******************************************************************************
proc _ntgrGetTrafficPortFrameSizeMin {chassis_id port} {
	global ntgr_trafficPortInfo_$chassis_id
	set ret 64
	keylget ntgr_trafficPortInfo_${chassis_id}($port) TRAFFIC_PORT_MIN_FRAME ret
	return $ret
}

#*******************************************************************************
#
#  Function Name	: _ntgrGetTrafficPortFrameSizeMax
#
#  Description    : This function is called to get the max Frame Size  from 
#			  the global ntgr_trafficPortInfo_<TGName>
#         
#  Usage          : _ntgrGetTrafficPortFrameSizeMax <chassis_id> <port>
# 
#*******************************************************************************
proc _ntgrGetTrafficPortFrameSizeMax {chassis_id port} {
	global ntgr_trafficPortInfo_$chassis_id
	set ret 1500
	keylget ntgr_trafficPortInfo_${chassis_id}($port) TRAFFIC_PORT_MAX_FRAME ret
	return $ret
}

#*******************************************************************************
#
#  Function Name	: _ntgrGetTrafficStreamFrameSizeMin
#
#  Description    : This function is called to get the min frame Size  from 
#			  the stream.
#                   It obtains stream through the global ntgr_trafficPortInfo_<TGName>
#			  and fetches Frame Min Size from the respective stream.
#         
#  Usage          : _ntgrGetTrafficStreamFrameSizeMin <chassis_id> <port> <streamId>
# 
#*******************************************************************************
proc _ntgrGetTrafficStreamFrameSizeMin {chassis_id port streamId } {
	global ntgr_trafficPortInfo_${chassis_id}
	set stream_name [_ntgrGetTrafficPortStreamName $chassis_id $port $streamId]
	global $stream_name
	return [keylget $stream_name TRAFFIC_STREAM_MIN_FRAME]
}

#*******************************************************************************
#
#  Function Name	: _ntgrGetTrafficStreamFrameSizeMax
#
#  Description    : This function is called to get the max frame Size  from 
#			  the stream.
#                   It obtains stream through the global ntgr_trafficPortInfo_<TGName>
#			  and fetches Frame Max Size from the respective stream.
#         
#  Usage          : _ntgrGetTrafficStreamFrameSizeMax <chassis_id> <port> <streamId>
# 
#*******************************************************************************
proc _ntgrGetTrafficStreamFrameSizeMax {chassis_id port streamId } {
	global ntgr_trafficPortInfo_${chassis_id}
	set stream_name [_ntgrGetTrafficPortStreamName $chassis_id $port $streamId]
	global $stream_name
	return [keylget $stream_name TRAFFIC_STREAM_MAX_FRAME]
}

#*******************************************************************************
#
#  Function Name	: _ntgrGetTrafficPortLoadMin
#
#  Description    : This function is called to get the min load from 
#			  the global ntgr_trafficPortInfo_<TGName>
#         
#  Usage          : _ntgrGetTrafficPortLoadMin <chassis_id> <port>
# 
#*******************************************************************************
proc _ntgrGetTrafficPortLoadMin {chassis_id port} {
	global ntgr_trafficPortInfo_$chassis_id
	return [keylget ntgr_trafficPortInfo_${chassis_id}($port) TRAFFIC_PORT_MIN_LOAD]
}

#*******************************************************************************
#
#  Function Name	: _ntgrGetTrafficPortLoadMax
#
#  Description    : This function is called to get the max load from 
#			  the global ntgr_trafficPortInfo_<TGName>
#         
#  Usage          : _ntgrGetTrafficPortLoadMax <chassis_id> <port>
# 
#*******************************************************************************
proc _ntgrGetTrafficPortLoadMax {chassis_id port} {
	global ntgr_trafficPortInfo_$chassis_id
	return [keylget ntgr_trafficPortInfo_${chassis_id}($port) TRAFFIC_PORT_MAX_LOAD]
}

#*******************************************************************************
#
#  Function Name	: _ntgrGetTrafficPortLoadMode
#
#  Description    : This function is called to get the load mode from 
#			  the global ntgr_trafficPortInfo_<TGName>
#         
#  Usage          : _ntgrGetTrafficPortLoadMode <chassis_id> <port>
# 
#*******************************************************************************
proc _ntgrGetTrafficPortLoadMode {chassis_id port} {
	global ntgr_trafficPortInfo_$chassis_id
	set ret "fixed"
	keylget ntgr_trafficPortInfo_${chassis_id}($port) TRAFFIC_PORT_LOAD_MODE ret
	return $ret
}

#*******************************************************************************
#
#  Function Name	: _ntgrGetTrafficPortLoad
#
#  Description    : This function is called to get the load from 
#			  the global ntgr_trafficPortInfo_<TGName>
#         
#  Usage          : _ntgrGetTrafficPortLoad <chassis_id> <port>
# 
#*******************************************************************************
proc _ntgrGetTrafficPortLoad {chassis_id port} {
	global ntgr_trafficPortInfo_$chassis_id
	set ret "100"
	keylget ntgr_trafficPortInfo_${chassis_id}($port) TRAFFIC_PORT_LOAD ret
	return $ret
}

#*******************************************************************************
#
#  Function Name	: _ntgrGetTrafficPortLoadType
#
#  Description    : This function is called to get the load mode type from 
#			  the global ntgr_trafficPortInfo_<TGName>
#         
#  Usage          : _ntgrGetTrafficPortLoadType <chassis_id> <port>
# 
#*******************************************************************************
proc _ntgrGetTrafficPortLoadType {chassis_id port} {
	global ntgr_trafficPortInfo_$chassis_id
	set ret "percentage"
	keylget ntgr_trafficPortInfo_${chassis_id}($port) TRAFFIC_PORT_LOAD_TYPE ret
	return $ret
}

#*******************************************************************************
#
#  Function Name	: _ntgrGetTrafficPortFrameMode
#
#  Description    : This function is called to get the frame mode from 
#			  the global ntgr_trafficPortInfo_<TGName>
#         
#  Usage          : _ntgrGetTrafficPortFrameMode <chassis_id> <port>
# 
#*******************************************************************************
proc _ntgrGetTrafficPortFrameMode {chassis_id port} {
	global ntgr_trafficPortInfo_$chassis_id
	set ret "random"
	keylget ntgr_trafficPortInfo_${chassis_id}($port) TRAFFIC_PORT_FRAME_MODE ret
	return $ret
}

#*******************************************************************************
#
#  Function Name	: _ntgrGetTrafficStreamFrameMode
#
#  Description    : This function is called to get the frame mode.
#			  It obtains stream name from the global ntgr_trafficPortInfo_<TGName>
#		        and it obtains Stream mode from respective stream global variable  
#         
#  Usage          : _ntgrGetTrafficStreamFrameMode <chassis_id> <port> <streamId> 
# 
#*******************************************************************************
proc _ntgrGetTrafficStreamFrameMode {chassis_id port streamId} {
    set ret 0
	global ntgr_trafficPortInfo_${chassis_id}
	set stream_name [_ntgrGetTrafficPortStreamName $chassis_id $port $streamId]
	global $stream_name
	keylget $stream_name TRAFFIC_STREAM_FRAME_MODE ret
	return $ret
}

#*******************************************************************************
#
#  Function Name	: _ntgrGetTrafficPortIpAddress
#
#  Description    : This function is called to get the IP Address  from 
#			  the global ntgr_trafficPortInfo_<TGName>
#         
#  Usage          : _ntgrGetTrafficPortIpAddress <chassis_id> <port>
# 
#*******************************************************************************
proc _ntgrGetTrafficPortIpAddress {chassis_id port} {
	global ntgr_trafficPortInfo_$chassis_id
	set ret [_rnd10To99].[_rnd10To99].[_rnd10To99].[_rnd10To99]
	keylget ntgr_trafficPortInfo_${chassis_id}($port) IP_ADDRESS ret
	return $ret
}

#*******************************************************************************
#
#  Function Name	: _ntgrGetTrafficPortCapture
#
#  Description    : This function is called to get Capture status  from 
#			  the global ntgr_trafficPortInfo_<TGName>
#         
#  Usage          : _ntgrGetTrafficPortCapture <chassis_id> <port>
# 
#*******************************************************************************
proc _ntgrGetTrafficPortCapture {chassis_id port} {
	global ntgr_trafficPortInfo_$chassis_id
	set ret "FALSE"
	keylget ntgr_trafficPortInfo_${chassis_id}($port) TRAFFIC_PORT_CAPTURE ret
	return $ret
}

#*******************************************************************************
#
#  Function Name	: _ntgrGetTrafficPortCaptureType
#
#  Description    : This function is called to get Capture Type  from 
#			  the global ntgr_trafficPortInfo_<TGName>
#         
#  Usage          : _ntgrGetTrafficPortCaptureType <chassis_id> <port>
# 
#*******************************************************************************
proc _ntgrGetTrafficPortCaptureType {chassis_id port} {
	global ntgr_trafficPortInfo_$chassis_id
	set ret "CAPT_TYPE_HW"
	keylget ntgr_trafficPortInfo_${chassis_id}($port) TRAFFIC_PORT_CAPTURE_TYPE ret
	return $ret
}

#*******************************************************************************
#
#  Function Name	: _ntgrGetTrafficPortCaptureFile
#
#  Description    : This function is called to get Capture Type  from 
#			  the global ntgr_trafficPortInfo_<TGName>
#         
#  Usage          : _ntgrGetTrafficPortCaptureFile <chassis_id> <port>
# 
#*******************************************************************************
proc _ntgrGetTrafficPortCaptureFile {chassis_id port} {
	global ntgr_trafficPortInfo_$chassis_id
	set ret "capture.pcap"
	keylget ntgr_trafficPortInfo_${chassis_id}($port) TRAFFIC_PORT_CAPTURE_FILE ret
	return $ret
}

#*******************************************************************************
#
#  Function Name	: _ntgrGetTrafficPortAddIxiaSig
#
#  Description    : This function is called to get ixia signature offset 's increment
#         
#  Usage          : _ntgrGetTrafficPortAddIxiaSig <chassis_id> <port>
# 
#*******************************************************************************
proc _ntgrGetTrafficPortAddIxiaSig {chassis_id port} {
	global ntgr_trafficPortInfo_$chassis_id
	set ret 0
	keylget ntgr_trafficPortInfo_${chassis_id}($port) TRAFFIC_PORT_ADD_IXIASIG ret
	return $ret
}

#*******************************************************************************
#
#  Function Name	: _ntgrGetTrafficPortAddIxiaPgid
#
#  Description    : This function is called to get ixia Pgid offset 's increment
#         
#  Usage          : _ntgrGetTrafficPortAddIxiaPgid <chassis_id> <port>
# 
#*******************************************************************************
proc _ntgrGetTrafficPortAddIxiaPgid {chassis_id port} {
	global ntgr_trafficPortInfo_$chassis_id
	set ret 0
	keylget ntgr_trafficPortInfo_${chassis_id}($port) TRAFFIC_PORT_ADD_IXIAPGID ret
	return $ret
}

#*******************************************************************************
#
#  Function Name	: _ntgrGetTrafficPortMinIxiaSig
#
#  Description    : This function is called to get ixia signature offset 's decrement
#         
#  Usage          : _ntgrGetTrafficPortMinIxiaSig <chassis_id> <port>
# 
#*******************************************************************************
proc  _ntgrGetTrafficPortMinIxiaSig {chassis_id port} {
	global ntgr_trafficPortInfo_$chassis_id
	set ret 0
	keylget ntgr_trafficPortInfo_${chassis_id}($port) TRAFFIC_PORT_MIN_IXIASIG ret
	return $ret
}

#*******************************************************************************
#
#  Function Name	: _ntgrGetTrafficPortMinIxiaPgid
#
#  Description    : This function is called to get ixia Pgid offset 's decrement
#         
#  Usage          : _ntgrGetTrafficPortMinIxiaPgid <chassis_id> <port>
# 
#*******************************************************************************
proc _ntgrGetTrafficPortMinIxiaPgid {chassis_id port} {
	global ntgr_trafficPortInfo_$chassis_id
	set ret 0
	keylget ntgr_trafficPortInfo_${chassis_id}($port) TRAFFIC_PORT_MIN_IXIAPGID ret
	return $ret
}


#*******************************************************************************
#
#  Function Name	: _ntgrGetTrafficPortIPv6Addr
#
#  Description    : This function is called to get the IPv6 Address  from 
#			  the global ntgr_trafficPortInfo_<TGName>
#         
#  Usage          : _ntgrGetTrafficPortIPv6Addr<chassis_id> <port>
# 
#*******************************************************************************
proc _ntgrGetPortIPv6Addr {chassis_id port} {
	global ntgr_trafficPortInfo_$chassis_id
	set ret 2000:2000:2000:2000:[_rnd10To99][_rnd10To99]:[_rnd10To99][_rnd10To99]:[_rnd10To99][_rnd10To99]:[_rnd10To99][_rnd10To99]
	keylget ntgr_trafficPortInfo_${chassis_id}($port) IPV6_ADDRESS ret
	return $ret
}

#*******************************************************************************
#
#  Function Name	: _ntgrGetPortIPv6MaskLen
#
#  Description    : This function is called to get the IPv6 Mask Len  from 
#			  the global ntgr_trafficPortInfo_<TGName>
#         
#  Usage          : _ntgrGetPortIPv6MaskLen <chassis_id> <port>
# 
#*******************************************************************************
proc _ntgrGetPortIPv6MaskLen {chassis_id port} {
	global ntgr_trafficPortInfo_$chassis_id
	set ret 64
	keylget ntgr_trafficPortInfo_${chassis_id}($port) IPV6_MASK_LEN ret
	return $ret
}

#*******************************************************************************
#
#  Function Name	: _ntgrGetTrafficPortGwIPv6Addr
#
#  Description    : This function is called to get the Gateway IPv6 Address from 
#			  the global ntgr_trafficPortInfo_<TGName>
#         
#  Usage          : _ntgrGetTrafficPortGwIPv6Addr<chassis_id> <port>
# 
#*******************************************************************************
proc _ntgrGetPortGwIPv6Addr {chassis_id port} {
	global ntgr_trafficPortInfo_$chassis_id
	set ret 2000:2000:2000:2000:[_rnd10To99][_rnd10To99]:[_rnd10To99][_rnd10To99]:[_rnd10To99][_rnd10To99]:[_rnd10To99][_rnd10To99]
	keylget ntgr_trafficPortInfo_${chassis_id}($port) GW_IPV6_ADDRESS ret
	return $ret
}

#*******************************************************************************
#
#  Function Name	: _ntgrGetTrafficPortIpAddressMask
#
#  Description    : This function is called to get the IP Address  from 
#			  the global ntgr_trafficPortInfo_<TGName>
#         
#  Usage          : _ntgrGetTrafficPortIpAddressMask <chassis_id> <port>
# 
#*******************************************************************************
proc _ntgrGetTrafficPortIpAddressMask {chassis_id port} {
	global ntgr_trafficPortInfo_$chassis_id
	set ret "255.255.255.0"
	keylget ntgr_trafficPortInfo_${chassis_id}($port) MASK_LENGTH ret
	return $ret
}

#*******************************************************************************
#
#  Function Name	: _ntgrGetTrafficPortGwIpAddress
#
#  Description    : This function is called to get the Gateway IP Address from 
#			  the global ntgr_trafficPortInfo_<TGName>
#         
#  Usage          : _ntgrGetTrafficPortGwIpAddress <chassis_id> <port>
# 
#*******************************************************************************
proc _ntgrGetTrafficPortGwIpAddress {chassis_id port} {
	global ntgr_trafficPortInfo_$chassis_id
	set ret [_rnd10To99].[_rnd10To99].[_rnd10To99].[_rnd10To99]
	keylget ntgr_trafficPortInfo_${chassis_id}($port) GW_IP_ADDRESS ret
	return $ret
}


#*******************************************************************************
#
#  Function Name	: _ntgrGetTrafficPortMacAddress
#
#  Description    : This function is called to get the MAC Addres from 
#			  the global ntgr_trafficPortInfo_<TGName>
#         
#  Usage          : _ntgrGetTrafficPortMacAddress <chassis_id> <port>
# 
#*******************************************************************************
proc _ntgrGetTrafficPortMacAddress {chassis_id port} {
	global ntgr_trafficPortInfo_$chassis_id
	set ret "00:00:[_rnd10To99]:[_rnd10To99]:[_rnd10To99]:[_rnd10To99]"
	keylget ntgr_trafficPortInfo_${chassis_id}($port) MAC_ADDRESS ret
	return $ret
}

#*******************************************************************************
#
#  Function Name	: _ntgrSetTrafficPortHandle
#
#  Description    : This function is called to Set the Handle  from 
#			  the global ntgr_trafficPortInfo_<TGName>
#         
#  Usage          : _ntgrSetTrafficPortHandle <chassis_id> <port> <handle>
# 
#*******************************************************************************
proc _ntgrSetTrafficPortHandle {chassis_id port handle} {
	global _ntgr_tgList
	set existingHandle [keylget _ntgr_tgList($chassis_id) TRAFFIC_PORT_HANDLE]
	foreach portHandlePair $existingHandle {
		set port1 [lindex $portHandlePair 0]
		if { $port1 == $port } { 
			set idx [lsearch $existingHandle $portHandlePair]
			set existingHandle [lreplace $existingHandle $idx $idx "$port $handle"]
			keylset _ntgr_tgList($chassis_id) TRAFFIC_PORT_HANDLE $existingHandle
			return
		}
	}
	set currentHandle [lappend existingHandle "$port $handle" ]
	keylset _ntgr_tgList($chassis_id) TRAFFIC_PORT_HANDLE $currentHandle
}

#*******************************************************************************
#
#  Function Name	: _ntgrAppendPortStreamHandle
#
#  Description    : This function is called to append the stream handles of the
#			  port in the global ntgr_trafficPortInfo_<TGName>
#         
#  Usage          : _ntgrAppendPortStreamHandle <chassis_id> <port> <handle>
# 
#*******************************************************************************
proc _ntgrAppendPortStreamHandle {chassis_id port handle} {
	global ntgr_trafficPortInfo_$chassis_id
	set handle "[_ntgrGetPortStreamHandle $chassis_id $port ] $handle"
	keylset ntgr_trafficPortInfo_${chassis_id}($port) TRAFFIC_PORT_STREAM_HANDLE $handle
}

#*******************************************************************************
#
#  Function Name	: _ntgrGetPortStreamHandle
#
#  Description    : This function is called to get the stream handles of the
#			  port in the global ntgr_trafficPortInfo_<TGName>
#         
#  Usage          : _ntgrGetPortStreamHandle <chassis_id> <port> 
# 
#*******************************************************************************
proc _ntgrGetPortStreamHandle {chassis_id port} {
	global ntgr_trafficPortInfo_$chassis_id
	return [keylget ntgr_trafficPortInfo_${chassis_id}($port) TRAFFIC_PORT_STREAM_HANDLE ]
}

#*******************************************************************************
#
#  Function Name	: _ntgrClearPortStreamHandle
#
#  Description    : This function is called to clear the stream handles entries 
#			  of the port in the global ntgr_trafficPortInfo_<TGName>
#         
#  Usage          : _ntgrClearPortStreamHandle <chassis_id> <port> 
# 
#*******************************************************************************
proc _ntgrClearPortStreamHandle {chassis_id port } {
	global ntgr_trafficPortInfo_$chassis_id
	keylset ntgr_trafficPortInfo_${chassis_id}($port) TRAFFIC_PORT_STREAM_HANDLE ""
}

#*******************************************************************************
#
#  Function Name	: _ntgrGetTrafficTestPortList
#
#  Description    : This function is called to get the port list  from 
#			  the global ntgr_trafficTestInfo
#         
#  Usage          : _ntgrGetTrafficTestPortList <chassis_id>
# 
#*******************************************************************************
proc _ntgrGetTrafficTestPortList {chassis_id} {
  global  ntgr_trafficTestInfo
  set ret {}
	if { [info exists ntgr_trafficTestInfo] == 0 } return {}
  keylget ntgr_trafficTestInfo($chassis_id) TG_PORT_LIST ret
  if {$ret==0} {
     set ret {}
	   Netgear_log_file "lib-tg-support.tcl" " _ntgrGetTrafficTestPortList - Warning :TG_PORT_LIST hasn't value"  
	}
	return $ret
}

#*******************************************************************************
#
#  Function Name	: _ntgrAppendTrafficTestPortList
#
#  Description    : This function is called to add the port list  to
#			  the global ntgr_trafficTestInfo
#         
#  Usage          : _ntgrAppendTrafficTestPortList <chassis_id> <port>
# 
#*******************************************************************************
proc _ntgrAppendTrafficTestPortList {chassis_id port} {
 
  global ntgr_trafficTestInfo
  if { [info exists ntgr_trafficTestInfo] == 0 } {
      keylset ntgr_trafficTestInfo($chassis_id) TG_PORT_LIST $port
      Netgear_log_file "lib-tg-support.tcl" " _ntgrAppendTrafficTestPortList: add new port $port"
      return
  }
  keylget ntgr_trafficTestInfo($chassis_id) TG_PORT_LIST ret
  if {$ret == 0} {
      keylset ntgr_trafficTestInfo($chassis_id) TG_PORT_LIST $port
      Netgear_log_file "lib-tg-support.tcl" " _ntgrAppendTrafficTestPortList: add port $port"
  } else { 
      lappend ret $port
      keylset ntgr_trafficTestInfo($chassis_id) TG_PORT_LIST $ret
      Netgear_log_file "lib-tg-support.tcl" " _ntgrAppendTrafficTestPortList: append port $port"
  }
}

#*******************************************************************************
#
#  Function Name	: _ntgrGetTrafficTestType
#
#  Description    : This function is called to get the traffic type  from 
#			  the global ntgr_trafficTestInfo
#         
#  Usage          : _ntgrGetTrafficTestType <chassis_id>
# 
#*******************************************************************************
proc _ntgrGetTrafficTestType {chassis_id} {
	global ntgr_trafficTestInfo
	if { [info exists ntgr_trafficTestInfo] == 0 } return {}
	keylget ntgr_trafficTestInfo($chassis_id) TG_TRAFFIC_TYPE ret
	return $ret
}

#*******************************************************************************
#
#  Function Name	: _ntgrGetTrafficTestDuration
#
#  Description    : This function is called to get the duration  from 
#			  the global ntgr_trafficTestInfo
#         
#  Usage          : _ntgrGetTrafficTestDuration <chassis_id>
# 
#*******************************************************************************
proc _ntgrGetTrafficTestDuration {chassis_id} {
	global ntgr_trafficTestInfo
	return [keylget ntgr_trafficTestInfo($chassis_id) TG_TRAFFIC_DURATION]
}

#*******************************************************************************
#
#  Function Name	: _ntgrGetTrafficStreamLoadType
#
#  Description    : This function is called to get the load Type  from 
#			  the global ntgr_trafficStreamInfo_<TGName>_<Port>
#         
#  Usage          : _ntgrGetTrafficStreamLoadType <chassis_id> <port> <stream>
# 
#*******************************************************************************
proc _ntgrGetTrafficStreamLoadType {chassis_id port streamId} {
  set ret 0
	global ntgr_trafficPortInfo_${chassis_id}
	set stream_name [_ntgrGetTrafficPortStreamName $chassis_id $port $streamId]
	global $stream_name
	keylget $stream_name TRAFFIC_STREAM_LOAD_TYPE ret
	return $ret
}

#*******************************************************************************
#
#  Function Name	: _ntgrGetTrafficStreamLoad
#
#  Description    : This function is called to get the load  from 
#			  the global ntgr_trafficStreamInfo_<TGName>_<Port>
#         
#  Usage          : _ntgrGetTrafficStreamLoad <chassis_id> <port> <stream>
# 
#*******************************************************************************
proc _ntgrGetTrafficStreamLoad {chassis_id port stream} {
    set ret 0
	global ntgr_trafficPortInfo_${chassis_id}
	set stream_name [_ntgrGetTrafficPortStreamName $chassis_id $port $stream]
	global $stream_name
	keylget $stream_name TRAFFIC_STREAM_LOAD ret
	return $ret
}

#*******************************************************************************
#
#  Function Name	: _ntgrGetTrafficStreamFrameType
#
#  Description    : This function is called to get the Frame Type  from 
#			  the global ntgr_trafficStreamInfo_<TGName>_<Port>
#         
#  Usage          : _ntgrGetTrafficStreamFrameType <chassis_id> <port> <stream>
# 
#*******************************************************************************
proc _ntgrGetTrafficStreamFrameType {chassis_id port stream} {
global ntgr_trafficPortInfo_${chassis_id}
	set stream_name [_ntgrGetTrafficPortStreamName $chassis_id $port $stream]
	global $stream_name
	return [keylget $stream_name TRAFFIC_STREAM_FRAME_TYPE]
}

#*******************************************************************************
#
#  Function Name	: _ntgrGetTrafficPortStreamBurstSize
#
#  Description    : This function is called to get the stream burst size from 
#			  the global ntgr_trafficStreamInfo_<TGName>_<Port>
#         
#  Usage          : _ntgrGetTrafficPortStreamBurstSize <chassis_id> <port> <stream>
# 
#*******************************************************************************
proc _ntgrGetTrafficPortStreamBurstSize {chassis_id port stream} {
	set ret 0
	global ntgr_trafficPortInfo_${chassis_id}
	set stream_name [_ntgrGetTrafficPortStreamName $chassis_id $port $stream]
	global $stream_name
	keylget $stream_name TRAFFIC_STREAM_BURST_SIZE ret
	return $ret
}

#*******************************************************************************
#
#  Function Name	: _ntgrGetTrafficStreamFramePAD
#
#  Description    : This function is called to get the frame fillPattern  from 
#			  the global ntgr_trafficStreamInfo_<TGName>_<Port>
#         
#  Usage          : _ntgrGetTrafficStreamFramePAD <chassis_id> <port> <stream>
# 
#*******************************************************************************
proc _ntgrGetTrafficStreamFramePAD {chassis_id port stream} {
	global ntgr_trafficPortInfo_${chassis_id}
	set stream_name [_ntgrGetTrafficPortStreamName $chassis_id $port $stream]
	global $stream_name
	
	set retValue "AA-BB"
	keylget $stream_name TRAFFIC_STREAM_FRAME_PAD retValue
	return $retValue
}

#*******************************************************************************
#
#  Function Name	: _ntgrGetTrafficStreamFrameCRCError
#
#  Description    : This function is called to get the frame genFCSError  from 
#			  the global ntgr_trafficStreamInfo_<TGName>_<Port>
#         
#  Usage          : _ntgrGetTrafficStreamFrameCRCError <chassis_id> <port> <stream>
# 
#*******************************************************************************
proc _ntgrGetTrafficStreamFrameCRCError {chassis_id port stream} {
	global ntgr_trafficPortInfo_${chassis_id}
	set stream_name [_ntgrGetTrafficPortStreamName $chassis_id $port $stream]
	global $stream_name
	
	set retValue "disable"
	keylget $stream_name TRAFFIC_STREAM_FRAME_CRCERROR retValue
	return $retValue
}

#*******************************************************************************
#
#  Function Name	: _ntgrGetTrafficStreamFrameSize
#
#  Description    : This function is called to get the frame size  from 
#			  the global ntgr_trafficStreamInfo_<TGName>_<Port>
#         
#  Usage          : _ntgrGetTrafficStreamFrameSize <chassis_id> <port> <stream>
# 
#*******************************************************************************
proc _ntgrGetTrafficStreamFrameSize {chassis_id port stream} {
    set ret 0
	global ntgr_trafficPortInfo_${chassis_id}
	set stream_name [_ntgrGetTrafficPortStreamName $chassis_id $port $stream]
	global $stream_name
	keylget $stream_name TRAFFIC_STREAM_FRAME_SIZE ret    
	return $ret
}

#*******************************************************************************
#
#  Function Name	: _ntgrGetTrafficStreamL2Header
#
#  Description    : This function is called to get the L2 header  from 
#			  the global ntgr_trafficStreamInfo_<TGName>_<Port>
#         
#  Usage          : _ntgrGetTrafficStreamL2Header <chassis_id> <port> <stream>
# 
#*******************************************************************************
proc _ntgrGetTrafficStreamL2Header {chassis_id port stream} {
	global ntgr_trafficPortInfo_${chassis_id}
	set stream_name [_ntgrGetTrafficPortStreamName $chassis_id $port $stream]
	global $stream_name
	return [keylget $stream_name TRAFFIC_L2_STREAM_GEN]
}

#*******************************************************************************
#
#  Function Name	: _ntgrGetTrafficStreamLLCSNAPHeader
#
#  Description    : This function is called to get the LLC SNAP Header  from 
#			  the global ntgr_trafficStreamInfo_<TGName>_<Port>
#         
#  Usage          : _ntgrGetTrafficStreamLLCSNAPHeader <chassis_id> <port> <stream>
# 
#*******************************************************************************
proc _ntgrGetTrafficStreamLLCSNAPHeader {chassis_id port stream} {
	global ntgr_trafficPortInfo_${chassis_id}
	set stream_name [_ntgrGetTrafficPortStreamName $chassis_id $port $stream]
	global $stream_name
  set retValue {}
  keylget $stream_name TRAFFIC_L2_STREAM_LLCSNAP retValue
  return $retValue
}

#*******************************************************************************
#
#  Function Name	: _ntgrGetTrafficStreamVlanHeader
#
#  Description    : This function is called to get the VLAN Header  from 
#			  the global ntgr_trafficStreamInfo_<TGName>_<Port>
#         
#  Usage          : _ntgrGetTrafficStreamVlanHeader <chassis_id> <port> <stream>
# 
#*******************************************************************************
proc _ntgrGetTrafficStreamVlanHeader {chassis_id port stream} {
	global ntgr_trafficPortInfo_${chassis_id}
	set stream_name [_ntgrGetTrafficPortStreamName $chassis_id $port $stream]
	global $stream_name
    set retValue {}
    keylget $stream_name TRAFFIC_L2_STREAM_VLAN retValue
    return $retValue
}

#*******************************************************************************
#
#  Function Name	: _ntgrGetTrafficStreamL2HeaderSrcMacAddr
#
#  Description    : This function is called to get the SRC MAC  from 
#			  given list
#         
#  Usage          : _ntgrGetTrafficStreamL2HeaderSrcMacAddr <l2_header>
# 
#*******************************************************************************
proc _ntgrGetTrafficStreamL2HeaderSrcMacAddr {l2_header} {
	return [keylget l2_header SRC_MAC_ADDR]
}

#*******************************************************************************
#
#  Function Name	: _ntgrGetTrafficStreamL2HeaderDstMacAddr
#
#  Description    : This function is called to get the DST MAC  from 
#			  given list
#         
#  Usage          : _ntgrGetTrafficStreamL2HeaderDstMacAddr <l2_header>
# 
#*******************************************************************************
proc _ntgrGetTrafficStreamL2HeaderDstMacAddr {l2_header} {
	return [keylget l2_header DST_MAC_ADDR]
}

#*******************************************************************************
#
#  Function Name	: _ntgrGetTrafficStreamL2HeaderDstMacCnt
#
#  Description    : This function is called to get the DST MAC count from 
#			  given list
#         
#  Usage          : _ntgrGetTrafficStreamL2HeaderDstMacCnt <l2_header>
# 
#*******************************************************************************
proc _ntgrGetTrafficStreamL2HeaderDstMacCnt {l2_header} {
	return [keylget l2_header DST_MAC_ADDR_CNT]
}

#*******************************************************************************
#
#  Function Name	: _ntgrGetTrafficStreamL2HeaderSrcMacCnt
#
#  Description    : This function is called to get the SRC MAC count from 
#			  given list
#         
#  Usage          : _ntgrGetTrafficStreamL2HeaderSrcMacCnt <l2_header>
# 
#*******************************************************************************
proc _ntgrGetTrafficStreamL2HeaderSrcMacCnt {l2_header} {
	return [keylget l2_header SRC_MAC_ADDR_CNT]
}

#*******************************************************************************
#
#  Function Name	: _ntgrGetTrafficStreamL2HeaderDstMacInc
#
#  Description    : This function is called to get the DST MAC Increment by from 
#			  given list
#         
#  Usage          : _ntgrGetTrafficStreamL2HeaderDstMacInc <l2_header>
# 
#*******************************************************************************
proc _ntgrGetTrafficStreamL2HeaderDstMacInc {l2_header} {
	return [keylget l2_header DST_MAC_INC_BY]
}

#*******************************************************************************
#
#  Function Name	: _ntgrGetTrafficStreamL2HeaderSrcMacInc
#
#  Description    : This function is called to get the SRC MAC Increment by from 
#			  given list
#         
#  Usage          : _ntgrGetTrafficStreamL2HeaderSrcMacInc <l2_header>
# 
#*******************************************************************************
proc _ntgrGetTrafficStreamL2HeaderSrcMacInc {l2_header} {
	return [keylget l2_header SRC_MAC_INC_BY]
}

#*******************************************************************************
#
#  Function Name	: _ntgrGetTrafficStreamL2HeaderSrcMacUseDefault
#
#  Description    : This function is called to get the SRC MAC USE DEFAULT by from 
#			  given list
#         
#  Usage          : _ntgrGetTrafficStreamL2HeaderSrcMacUseDefault <l2_header>
# 
#*******************************************************************************
proc _ntgrGetTrafficStreamL2HeaderSrcMacUseDefault {l2_header} {
	return [keylget l2_header SRC_MAC_USE_DEFAULT]
}

#*******************************************************************************
#
#  Function Name	: _ntgrGetTrafficStreamL2HeaderDstMacUseDefault
#
#  Description    : This function is called to get the DST MAC USE DEFAULT by from 
#			  given list
#         
#  Usage          : _ntgrGetTrafficStreamL2HeaderDstMacUseDefault <l2_header>
# 
#*******************************************************************************
proc _ntgrGetTrafficStreamL2HeaderDstMacUseDefault {l2_header} {
	return [keylget l2_header DST_MAC_USE_DEFAULT]
}

#*******************************************************************************
#
#  Function Name	: _ntgrGetTrafficStreamL2HeaderEthernetType
#
#  Description    : This function is called to get the ethernet type from layer2 
#                   header.
#         
#  Usage          : _ntgrGetTrafficStreamL2HeaderEthernetType <l2_header>
# 
#*******************************************************************************
proc _ntgrGetTrafficStreamL2HeaderEthernetType {l2_header} {
    set retValue 0x0800
    keylget l2_header ETHERNET_TYPE retValue
    return $retValue
}

#*******************************************************************************
#
#  Function Name	: _ntgrGetTrafficStreamLLCSNAPHeaderControl
#
#  Description    : This function is called to get LLCSnap header's control filed from llcsnap
#                   header.
#         
#  Usage          : _ntgrGetTrafficStreamLLCSNAPHeaderControl <llcsnap_header>
# 
#*******************************************************************************
proc _ntgrGetTrafficStreamLLCSNAPHeaderControl {llcsnap_header} {
  set retVal {}
	set retVal [keylget llcsnap_header CONTROL_FIELD]
	return $retVal
}

#*******************************************************************************
#
#  Function Name	: _ntgrGetTrafficStreamLLCSNAPHeaderDSAP
#
#  Description    : This function is called to get LLCSnap header's LLC DSAP from llcsnap
#                   header.
#         
#  Usage          : _ntgrGetTrafficStreamLLCSNAPHeaderDSAP <llcsnap_header>
# 
#*******************************************************************************
proc _ntgrGetTrafficStreamLLCSNAPHeaderDSAP {llcsnap_header} {
  set retVal {}
	set retVal [keylget llcsnap_header  LLC_DSAP]
	return $retVal
}

#*******************************************************************************
#
#  Function Name	: _ntgrGetTrafficStreamLLCSNAPHeaderSSAP
#
#  Description    : This function is called to get LLCSnap header's LLC SSAP from llcsnap
#                   header.
#         
#  Usage          : _ntgrGetTrafficStreamLLCSNAPHeaderSSAP <llcsnap_header>
# 
#*******************************************************************************
proc _ntgrGetTrafficStreamLLCSNAPHeaderSSAP {llcsnap_header} {
  set retVal {}
	set retVal [keylget llcsnap_header LLC_SSAP]
	return $retVal
}

#*******************************************************************************
#
#  Function Name	: _ntgrGetTrafficStreamLLCSNAPHeaderEthertype
#
#  Description    : This function is called to get LLCSnap header's snap Ethertype from llcsnap
#                   header.
#         
#  Usage          : _ntgrGetTrafficStreamLLCSNAPHeaderEthertype <llcsnap_header>
# 
#*******************************************************************************
proc _ntgrGetTrafficStreamLLCSNAPHeaderEthertype {llcsnap_header} {
  set retVal {}
	set retVal [keylget llcsnap_header SNAP_ETHERTYPE]
	return $retVal
}

#*******************************************************************************
#
#  Function Name	: _ntgrGetTrafficStreamLLCSNAPHeaderVendor
#
#  Description    : This function is called to get LLCSnap header's snap Ethertype from llcsnap
#                   header.
#         
#  Usage          : _ntgrGetTrafficStreamLLCSNAPHeaderVendor <llcsnap_header>
# 
#*******************************************************************************
proc _ntgrGetTrafficStreamLLCSNAPHeaderVendor {llcsnap_header} {
  set retVal {}
	set retVal [keylget llcsnap_header VENDOR_CODE]
	return $retVal
}

#*******************************************************************************
#
#  Function Name	: _ntgrGetTrafficStreamVlanHeaderId
#
#  Description    : This function is called to get the VLAN ID from 
#			  given list
#         
#  Usage          : _ntgrGetTrafficStreamVlanHeaderId <vlan_header>
# 
#*******************************************************************************
proc _ntgrGetTrafficStreamVlanHeaderId {vlan_header} {
	return [keylget vlan_header VLAN_ID]
}

#*******************************************************************************
#
#  Function Name	: _ntgrGetTrafficStreamVlanHeaderPri
#
#  Description    : This function is called to get the vlan priority from 
#			  given list
#         
#  Usage          : _ntgrGetTrafficStreamVlanHeaderPri <vlan_header>
# 
#*******************************************************************************
proc _ntgrGetTrafficStreamVlanHeaderPri {vlan_header} {
	return [keylget vlan_header VLAN_PRI]
}

#*******************************************************************************
#
#  Function Name	: _ntgrGetTrafficStreamVlanHeaderCnt
#
#  Description    : This function is called to get the vlan count from 
#			  given list
#         
#  Usage          : _ntgrGetTrafficStreamVlanHeaderCnt <vlan_header>
# 
#*******************************************************************************
proc _ntgrGetTrafficStreamVlanHeaderCnt {vlan_header} {
	return [keylget vlan_header VLAN_ID_COUNT]
}

#*******************************************************************************
#
#  Function Name	: _ntgrGetTrafficStreamVlanHeaderInc
#
#  Description    : This function is called to get the vlan increment by from 
#			  given list
#         
#  Usage          : _ntgrGetTrafficStreamVlanHeaderInc <vlan_header>
# 
#*******************************************************************************
proc _ntgrGetTrafficStreamVlanHeaderInc {vlan_header} {
	return [keylget vlan_header VLAN_ID_INC_BY]
}

#*******************************************************************************
#
#  Function Name	: _ntgrGetTrafficStreamL3Header
#
#  Description    : This function is called to get the L3 header from 
#			  ntgr_trafficStreamInfo_<TGName>_<port>
#         
#  Usage          : _ntgrGetTrafficStreamL3Header <chassis_id> <port> <stream>
# 
#*******************************************************************************
proc _ntgrGetTrafficStreamL3Header {chassis_id port stream} {	
	global ntgr_trafficPortInfo_${chassis_id}
	set stream_name [_ntgrGetTrafficPortStreamName $chassis_id $port $stream]
	global $stream_name
    set retValue {}
    keylget $stream_name TRAFFIC_L3_STREAM_GEN retValue
    return $retValue
}

#*******************************************************************************
#  Function Name : _ntgrGetStreamL3DstIpMode
#
#  Description   : Called to get destination ip address mode.
#
#  Usage         : _ntgrGetStreamL3DstIpMode <l3_header>
# 
#*******************************************************************************
proc _ntgrGetStreamL3DstIpMode {l3_header} {	
    set ret 0
    keylget l3_header DST_IP_MODE ret
    if {$ret == 0} {
         set ret "ipIdle"
         set dst_host_cnt [_ntgrGetTrafficStreamL3HeaderDstHostCnt $l3_header]
         set dst_ip_incr  [_ntgrGetTrafficStreamL3HeaderDstIPInc $l3_header]
         if {$dst_host_cnt > 1 && $dst_ip_incr >1} {set ret "ipIncrHost"}
         if {$dst_host_cnt > 1 && $dst_ip_incr == 1} {set ret "ipContIncrHost"}
         set dst_net_cnt [_ntgrGetTrafficStreamL3HeaderDstIPCnt $l3_header]
	       if {$dst_net_cnt >1 && $dst_ip_incr >1} {set ret "ipIncrNetwork"}
	       if {$dst_net_cnt >1 && $dst_ip_incr ==1} {set ret "ipContIncrNetwork"}
    }
    return $ret
}

#*******************************************************************************
#  Function Name : _ntgrGetStreamL3DstIpAddr
#
#  Description   : Called to get destination ip address.
#
#  Usage         : _ntgrGetStreamL3DstIpAddr <l3_header>
# 
#*******************************************************************************
proc _ntgrGetStreamL3DstIpAddr {l3_header} {	
    return [keylget l3_header DST_IP_ADDR]
}

#*******************************************************************************
#  Function Name : _ntgrGetStreamL3DstIpMask
#
#  Description   : Called to get destination ip address mask.
#
#  Usage         : _ntgrGetStreamL3DstIpMask <l3_header>
# 
#*******************************************************************************
proc _ntgrGetStreamL3DstIpMask {l3_header} {	
    return [keylget l3_header DST_IP_MASK]
}

#*******************************************************************************
#  Function Name : _ntgrGetStreamL3DstIpRepeatCnt
#
#  Description   : Called to get destination ip address repeat count.
#
#  Usage         : _ntgrGetStreamL3DstIpRepeatCnt <l3_header>
# 
#*******************************************************************************
proc _ntgrGetStreamL3DstIpRepeatCnt {l3_header} {	
    set ret 0
    keylget l3_header DST_IP_REPEATCOUNT ret
    return $ret
}

#*******************************************************************************
#  Function Name : _ntgrGetStreamL3DstIpClass
#
#  Description   : Called to get destination ip address 's class.
#
#  Usage         : _ntgrGetStreamL3DstIpClass <l3_header>
# 
#*******************************************************************************
proc _ntgrGetStreamL3DstIpClass {l3_header} {	
    set ret 0
    keylget l3_header DST_IP_CLASS ret
    if {$ret == 0} {set ret "classA"}
    return $ret
}

#*******************************************************************************
#  Function Name : _ntgrGetStreamL3SrcIpMode
#
#  Description   : Called to get source ip address mode.
#
#  Usage         : _ntgrGetStreamL3SrcIpMode <l3_header>
# 
#*******************************************************************************
proc _ntgrGetStreamL3SrcIpMode {l3_header} {
    set ret 0
    keylget l3_header SRC_IP_MODE ret
    if {$ret == 0} { 
         set ret "ipIdle"
         set src_host_cnt [_ntgrGetTrafficStreamL3HeaderSrcHostCnt $l3_header]
         set src_ip_incr  [_ntgrGetTrafficStreamL3HeaderSrcIPInc $l3_header]
         if {$src_host_cnt > 1 && $src_ip_incr >1} {set ret "ipIncrHost"}
         if {$src_host_cnt > 1 && $src_ip_incr == 1} {set ret "ipContIncrHost"}
         set src_net_cnt [_ntgrGetTrafficStreamL3HeaderSrcIPCnt $l3_header]
	       if {$src_net_cnt >1 && $src_ip_incr >1} {set ret "ipIncrNetwork"}
	       if {$src_net_cnt >1 && $src_ip_incr ==1} {set ret "ipContIncrNetwork"}       
    }
    return $ret
}

#*******************************************************************************
#  Function Name : _ntgrGetStreamL3SrcIpAddr
#
#  Description   : Called to get source ip address.
#
#  Usage         : _ntgrGetStreamL3SrcIpAddr <l3_header>
# 
#*******************************************************************************
proc _ntgrGetStreamL3SrcIpAddr {l3_header} {	
    return [keylget l3_header SRC_IP_ADDR]
}

#*******************************************************************************
#  Function Name : _ntgrGetStreamL3SrcIpMask
#
#  Description   : Called to get source ip address mask.
#
#  Usage         : _ntgrGetStreamL3SrcIpMask <l3_header>
# 
#*******************************************************************************
proc _ntgrGetStreamL3SrcIpMask {l3_header} {	
    return [keylget l3_header SRC_IP_MASK]
}

#*******************************************************************************
#  Function Name : _ntgrGetStreamL3SrcIpRepeatCnt
#
#  Description   : Called to get source ip address repeat count.
#
#  Usage         : _ntgrGetStreamL3SrcIpRepeatCnt <l3_header>
# 
#*******************************************************************************
proc _ntgrGetStreamL3SrcIpRepeatCnt {l3_header} {	
    set ret 0
    keylget l3_header SRC_IP_REPEATCOUNT ret
    if {$ret == 0} {
        set ret [_ntgrGetTrafficStreamL3HeaderSrcIPInc $l3_header]
    }
    return $ret
}

#*******************************************************************************
#  Function Name : _ntgrGetStreamL3SrcIpClass
#
#  Description   : Called to get source ip address 's class.
#
#  Usage         : _ntgrGetStreamL3SrcIpClass <l3_header>
# 
#*******************************************************************************
proc _ntgrGetStreamL3SrcIpClass {l3_header} {	
    set ret 0
    keylget l3_header SRC_IP_CLASS ret
    if {$ret == 0} {set ret "classA"}
    return $ret
}

#*******************************************************************************
#  Function Name : _ntgrGetStreamL3Protocol
#
#  Description   : Called to get ip protocol.
#
#  Usage         : _ntgrGetStreamL3Protocol <l3_header>
# 
#*******************************************************************************
proc _ntgrGetStreamL3Protocol {l3_header} {	
    set ret 0
    keylget l3_header IP_PROTOCOL ret
    if {$ret ==0} {
      keylget l3_header PROTOCOL ret
    }
    return $ret
}

#*******************************************************************************
#  Function Name : _ntgrGetStreamL3TOS
#
#  Description   : Called to get ip type of service.
#
#  Usage         : _ntgrGetStreamL3TOS <l3_header>
# 
#*******************************************************************************
proc _ntgrGetStreamL3TOS {l3_header} {	
    return [keylget l3_header IP_TOS]
}

#*******************************************************************************
#  Function Name : _ntgrGetStreamL3TTL
#
#  Description   : Called to get ip TTL info.
#
#  Usage         : _ntgrGetStreamL3TTL <l3_header>
# 
#*******************************************************************************
proc _ntgrGetStreamL3TTL {l3_header} {	
    return [keylget l3_header IP_TTL]
}

#*******************************************************************************
#  Function Name : _ntgrGetStreamL3Identification
#
#  Description   : Called to get ip identification field value.
#
#  Usage         : _ntgrGetStreamL3Identification <l3_header>
# 
#*******************************************************************************
proc _ntgrGetStreamL3Identification {l3_header} {	
    set ret 0
    keylget l3_header IP_IDENTIFICATION ret
    return $ret
}

#*******************************************************************************
#  Function Name : _ntgrGetStreamL3Flag
#
#  Description   : Called to get ip flag field info.
#
#  Usage         : _ntgrGetStreamL3Flag <l3_header>
# 
#*******************************************************************************
proc _ntgrGetStreamL3Flag {l3_header} {
    set ret 0
    keylget l3_header IP_FLAG ret
    return $ret
}

#*******************************************************************************
#  Function Name : _ntgrGetStreamL3Offset
#
#  Description   : Called to get ip fragment offset.
#
#  Usage         : _ntgrGetStreamL3Offset <l3_header>
# 
#*******************************************************************************
proc _ntgrGetStreamL3Offset {l3_header} {
    set ret 0
    keylget l3_header IP_FRAGMENT_OFFSET ret
    return $ret
}

#*******************************************************************************
#  Function Name : _ntgrIfValidChecksum
#
#  Description   : Called to check if generate correctly ip checksum.
#
#  Usage         : _ntgrIfValidChecksum <l3_header>
# 
#*******************************************************************************
proc _ntgrIfValidChecksum {l3_header} {
    set ret 0
    keylget l3_header IP_CHECKSUM_VALID ret
    return $ret
}

#*******************************************************************************
#  Function Name : _ntgrGetStreamL3Gateway
#
#  Description   : Called to get ip gateway info.
#
#  Usage         : _ntgrGetStreamL3Gateway <l3_header>
# 
#*******************************************************************************
proc _ntgrGetStreamL3Gateway {l3_header} {	
    set ret 0	
    keylget l3_header IP_GATEWAY ret
    return $ret
}

#*******************************************************************************
#  Function Name : _ntgrGetStreamL3Options
#
#  Description   : Called to get ip Options&Padding info.Only used for Ixia
#
#  Usage         : _ntgrGetStreamL3Options <l3_header>
# 
#  Author        : jim.xie
#*******************************************************************************
proc _ntgrGetStreamL3Options {l3_header} {	
    set ret 0	
    keylget l3_header IP_OPTION ret
    return $ret
}

#*******************************************************************************
#
#  Function Name	: _ntgrGetTrafficStreamTcpUdpHeader
#
#  Description    : This function is called to get the TCP/UDP from 
#			  ntgr_trafficStreamInfo_<TGName>_<port>
#         
#  Usage          : _ntgrGetTrafficStreamTcpUdpHeader <chassis_id> <port> <stream>
# 
#*******************************************************************************
proc _ntgrGetTrafficStreamTcpUdpHeader {chassis_id port stream} {	
	global ntgr_trafficPortInfo_${chassis_id}
	set stream_name [_ntgrGetTrafficPortStreamName $chassis_id $port $stream]
	global $stream_name
	# return [keylget $stream_name TRAFFIC_L3_STREAM_TCP_UDP]
    set retValue {}
    keylget $stream_name TRAFFIC_L3_STREAM_TCP_UDP retValue
    return $retValue
}

#*******************************************************************************
#
#  Function Name	: _ntgrGetTrafficStreamIcmpHeader
#
#  Description    : This function is called to get the ICMP header from 
#			  ntgr_trafficStreamInfo_<TGName>_<port>
#         
#  Usage          : _ntgrGetTrafficStreamIcmpHeader <chassis_id> <port> <stream>
# 
#*******************************************************************************
proc _ntgrGetTrafficStreamIcmpHeader {chassis_id port stream} {	
	global ntgr_trafficPortInfo_${chassis_id}
	set stream_name [_ntgrGetTrafficPortStreamName $chassis_id $port $stream]
	global $stream_name
	# return [keylget $stream_name TRAFFIC_L3_STREAM_ICMP]
    set retValue {}
    keylget $stream_name TRAFFIC_L3_STREAM_ICMP retValue
    return $retValue
}

#*******************************************************************************
#
#  Function Name	: _ntgrGetTrafficStreamIgmpHeader
#
#  Description    : This function is called to get the IGMP header from 
#			  ntgr_trafficStreamInfo_<TGName>_<port>
#         
#  Usage          : _ntgrGetTrafficStreamIgmpHeader <chassis_id> <port> <stream>
# 
#*******************************************************************************
proc _ntgrGetTrafficStreamIgmpHeader {chassis_id port stream} {	
	global ntgr_trafficPortInfo_${chassis_id}
	set stream_name [_ntgrGetTrafficPortStreamName $chassis_id $port $stream]
	global $stream_name
    set retValue {}
    keylget $stream_name TRAFFIC_L3_STREAM_IGMP retValue
    return $retValue
}

#*******************************************************************************
#
#  Function Name	: _ntgrGetTrafficStreamTcpUdpHeaderSrcPort
#
#  Description    : This function is called to get Src port from given list
#         
#  Usage          : _ntgrGetTrafficStreamTcpUdpHeaderSrcPort <tcp_udp_header>
# 
#*******************************************************************************
proc _ntgrGetTrafficStreamTcpUdpHeaderSrcPort {tcp_udp_header} {	
	return [keylget tcp_udp_header SRC_PORT]
}

#*******************************************************************************
#
#  Function Name	: _ntgrGetTrafficStreamTcpUdpHeaderDstPort
#
#  Description    : This function is called to get Dst port from given list
#         
#  Usage          : _ntgrGetTrafficStreamTcpUdpHeaderDstPort <tcp_udp_header>
# 
#*******************************************************************************
proc _ntgrGetTrafficStreamTcpUdpHeaderDstPort {tcp_udp_header} {	
	return [keylget tcp_udp_header DST_PORT]
}

#*******************************************************************************
#
#  Function Name	: _ntgrGetTrafficStreamTcpUdpHeaderSrcPortCnt
#
#  Description    : This function is called to get Src port count from given list
#         
#  Usage          : _ntgrGetTrafficStreamTcpUdpHeaderSrcPortCnt <tcp_udp_header>
# 
#*******************************************************************************
proc _ntgrGetTrafficStreamTcpUdpHeaderSrcPortCnt {tcp_udp_header} {	
	return [keylget tcp_udp_header SRC_PORT_COUNT]
}

#*******************************************************************************
#
#  Function Name	: _ntgrGetTrafficStreamTcpUdpHeaderDstPortCnt
#
#  Description    : This function is called to get Dst port count from given list
#         
#  Usage          : _ntgrGetTrafficStreamTcpUdpHeaderDstPortCnt <tcp_udp_header>
# 
#*******************************************************************************
proc _ntgrGetTrafficStreamTcpUdpHeaderDstPortCnt {tcp_udp_header} {	
	return [keylget tcp_udp_header DST_PORT_COUNT]
}

#*******************************************************************************
#
#  Function Name	: _ntgrGetTrafficStreamTcpUdpHeaderSrcPortInc
#
#  Description    : This function is called to get Src port increment from given list
#         
#  Usage          : _ntgrGetTrafficStreamTcpUdpHeaderSrcPortInc <tcp_udp_header>
# 
#*******************************************************************************
proc _ntgrGetTrafficStreamTcpUdpHeaderSrcPortInc {tcp_udp_header} {	
	return [keylget tcp_udp_header SRC_PORT_INC_BY]
}

#*******************************************************************************
#
#  Function Name	: _ntgrGetTrafficStreamTcpUdpHeaderDstPortInc
#
#  Description    : This function is called to get Dst port increment from given list
#         
#  Usage          : _ntgrGetTrafficStreamTcpUdpHeaderDstPortInc <tcp_udp_header>
# 
#*******************************************************************************
proc _ntgrGetTrafficStreamTcpUdpHeaderDstPortInc {tcp_udp_header} {	
	return [keylget tcp_udp_header DST_PORT_INC_BY]
}

#*******************************************************************************
#
#  Function Name	: _ntgrGetTrafficStreamTcpUdpHeaderTCPFlag
#
#  Description    : This function is called to get TCP flag from given list
#         
#  Usage          : _ntgrGetTrafficStreamTcpUdpHeaderTCPFlag  <tcp_udp_header>
# 
#*******************************************************************************
proc _ntgrGetTrafficStreamTcpUdpHeaderTCPFlag {tcp_udp_header} {
	set retValue 0x10
    	keylget tcp_udp_header TCP_FLAG retValue
	return $retValue
}
##------------- Added by kenddy for Dos test-----------------#
proc _ntgrGetTrafficStreamTcpUdpHeaderTcpSeq {tcp_udp_header} {
	set retValue 1
    	keylget tcp_udp_header TCP_SEQ retValue
	return $retValue
}
#*******************************************************************************
#
#  Function Name	: _ntgrGetTrafficStreamTcpUdpHeaderTCPFlagURG
#
#  Description    : This function is called to get TCP URG flag from given list
#         
#  Usage          : _ntgrGetTrafficStreamTcpUdpHeaderTCPFlagURG <tcp_udp_header>
# 
#*******************************************************************************
proc _ntgrGetTrafficStreamTcpUdpHeaderTCPFlagURG {tcp_udp_header} {
	set retValue {}
    	keylget tcp_udp_header TCP_FLAG_URG retValue
	return $retValue
}

#*******************************************************************************
#
#  Function Name	: _ntgrGetTrafficStreamTcpUdpHeaderTCPFlagACK
#
#  Description    : This function is called to get TCP ACK flag from given list
#         
#  Usage          : _ntgrGetTrafficStreamTcpUdpHeaderTCPFlagACK <tcp_udp_header>
# 
#*******************************************************************************
proc _ntgrGetTrafficStreamTcpUdpHeaderTCPFlagACK {tcp_udp_header} {
	set retValue {}
    	keylget tcp_udp_header TCP_FLAG_ACK retValue
	return $retValue
}

#*******************************************************************************
#
#  Function Name	: _ntgrGetTrafficStreamTcpUdpHeaderTCPFlagFIN
#
#  Description    : This function is called to get TCP FIN flag from given list
#         
#  Usage          : _ntgrGetTrafficStreamTcpUdpHeaderTCPFlagFIN <tcp_udp_header>
# 
#*******************************************************************************
proc _ntgrGetTrafficStreamTcpUdpHeaderTCPFlagFIN {tcp_udp_header} {
	set retValue {}
    	keylget tcp_udp_header TCP_FLAG_FIN retValue
	return $retValue
}

#*******************************************************************************
#
#  Function Name	: _ntgrGetTrafficStreamTcpUdpHeaderTCPFlagRST
#
#  Description    : This function is called to get TCP RST flag from given list
#         
#  Usage          : _ntgrGetTrafficStreamTcpUdpHeaderTCPFlagRST <tcp_udp_header>
# 
#*******************************************************************************
proc _ntgrGetTrafficStreamTcpUdpHeaderTCPFlagRST {tcp_udp_header} {
	set retValue {}
    	keylget tcp_udp_header TCP_FLAG_RST retValue
	return $retValue
}


#*******************************************************************************
#
#  Function Name	: _ntgrGetTrafficStreamTcpUdpHeaderTCPFlagPSH
#
#  Description    : This function is called to get TCP PSH flag from given list
#         
#  Usage          : _ntgrGetTrafficStreamTcpUdpHeaderTCPFlagPSH <tcp_udp_header>
# 
#*******************************************************************************
proc _ntgrGetTrafficStreamTcpUdpHeaderTCPFlagPSH {tcp_udp_header} {
	set retValue {}
    	keylget tcp_udp_header TCP_FLAG_PSH retValue
	return $retValue
}

#*******************************************************************************
#
#  Function Name	: _ntgrGetTrafficStreamTcpUdpHeaderTCPFlagSYN
#
#  Description    : This function is called to get TCP SYN flag from given list
#         
#  Usage          : _ntgrGetTrafficStreamTcpUdpHeaderTCPFlagSYN <tcp_udp_header>
# 
#*******************************************************************************
proc _ntgrGetTrafficStreamTcpUdpHeaderTCPFlagSYN {tcp_udp_header} {
	set retValue {}
    	keylget tcp_udp_header TCP_FLAG_SYN retValue
	return $retValue
}

##----------Added by kenddy for icmp --------#
#*******************************************************************************
#
#  Function Name	: _ntgrGetTrafficStreamIcmpType
#
#  Description    : This function is called to get ICMP Type from given list
#         
#  Usage          : _ntgrGetTrafficStreamIcmpType <icmp_stream>
# 
#*******************************************************************************
proc _ntgrGetTrafficStreamIcmpType {icmp_stream} {	
  set retValue 8
	keylget  icmp_stream ICMP_TYPE retValue
	return $retValue
}

#*******************************************************************************
#
#  Function Name	: _ntgrGetTrafficStreamIcmpCode
#
#  Description    : This function is called to get ICMP Code from given list
#         
#  Usage          : _ntgrGetTrafficStreamIcmpCode <icmp_stream>
# 
#*******************************************************************************
proc _ntgrGetTrafficStreamIcmpCode {icmp_stream} {	
  set retValue 0
	keylget  icmp_stream ICMP_CODE retValue
	return $retValue
}

#*******************************************************************************
#
#  Function Name	: _ntgrGetTrafficStreamIcmpMulticastAddress
#
#  Description    : This function is called to get ICMP Multicast Address from given list
#         
#  Usage          : _ntgrGetTrafficStreamIcmpMulticastAddress <icmp_stream>
# 
#*******************************************************************************
proc _ntgrGetTrafficStreamIcmpMulticastAddress {icmp_stream} {	
  set retValue 0
	keylget  icmp_stream ICMP_Multicast_Address retValue
	return $retValue
}

#*******************************************************************************
#
#  Function Name	: _ntgrGetTrafficStreamIcmpId
#
#  Description    : This function is called to get ping Id from given list
#         
#  Usage          : _ntgrGetTrafficStreamIcmpId <icmp_stream>
# 
#*******************************************************************************
proc _ntgrGetTrafficStreamIcmpId {icmp_stream} {
  set retValue 200
	keylget  icmp_stream ICMP_ID retValue
	return $retValue
}

#*******************************************************************************
#
#  Function Name	: _ntgrGetTrafficStreamIcmpSeq
#
#  Description    : This function is called to get ICMP Sequence from given list
#         
#  Usage          : _ntgrGetTrafficStreamIcmpSeq <icmp_stream>
# 
#*******************************************************************************
proc _ntgrGetTrafficStreamIcmpSeq {icmp_stream} {	
  set retValue 300
	keylget  icmp_stream ICMP_SEQUENCE retValue
	return $retValue
}


#*******************************************************************************
#
#  Function Name	: _ntgrGetTrafficStreamIcmpData
#
#  Description    : This function is called to get ICMP Data from given list
#         
#  Usage          : _ntgrGetTrafficStreamIcmpData <icmp_stream>
# 
#*******************************************************************************
proc _ntgrGetTrafficStreamIcmpData {icmp_stream} {	
  set retValue {}
	keylget  icmp_stream ICMP_DATA retValue
	return $retValue
}

#*******************************************************************************
#
#  Function Name	: _ntgrGetTrafficStreamIgmpVer
#
#  Description    : This function is called to get IGMP version from given list
#         
#  Usage          : _ntgrGetTrafficStreamIgmpVer <igmp_stream>
# 
#*******************************************************************************
proc _ntgrGetTrafficStreamIgmpVer {igmp_stream} {	
	return [keylget igmp_stream IGMP_VERSION]
}

#*******************************************************************************
#
#  Function Name	: _ntgrGetTrafficStreamIgmpType
#
#  Description    : This function is called to get IGMP type from given list
#         
#  Usage          : _ntgrGetTrafficStreamIgmpType <igmp_stream>
# 
#*******************************************************************************
proc _ntgrGetTrafficStreamIgmpType {igmp_stream} {	
	return [keylget igmp_stream IGMP_TYPE]
}

#*******************************************************************************
#
#  Function Name	: _ntgrGetTrafficStreamIgmpGrpIP
#
#  Description    : This function is called to get IGMP group ip from given list
#         
#  Usage          : _ntgrGetTrafficStreamIgmpGrpIP <igmp_stream>
# 
#*******************************************************************************
proc _ntgrGetTrafficStreamIgmpGrpIP {igmp_stream} {	
	return [keylget igmp_stream IGMP_GRP_IP]
}

#*******************************************************************************
#
#  Function Name	: _ntgrGetTrafficStreamIgmpGrpMode
#
#  Description    : This function is called to get IGMP group mode from given list
#         
#  Usage          : _ntgrGetTrafficStreamIgmpGrpMode <igmp_stream>
# 
#*******************************************************************************
proc _ntgrGetTrafficStreamIgmpGrpMode {igmp_stream} {	
    #modify by jim.xie begin
	# add default value
    set retValue "igmpIdle"
    keylget igmp_stream IGMP_GRP_MODE retValue
	return $retValue
	#modify by jim.xie end
}

#*******************************************************************************
#
#  Function Name	: _ntgrGetTrafficStreamIgmpGrpCnt
#
#  Description    : This function is called to get IGMP group count from given list
#         
#  Usage          : _ntgrGetTrafficStreamIgmpGrpCnt <igmp_stream>
# 
#*******************************************************************************
proc _ntgrGetTrafficStreamIgmpGrpCnt {igmp_stream} {
    #modify by jim.xie begin
	# add default value
	set retValue 1
	keylget igmp_stream IGMP_GRP_CNT retValue
	return $retValue
	#modify by jim.xie end
}

#*******************************************************************************
#
#  Function Name	: _ntgrGetTrafficStreamIgmpMaxResponse
#
#  Description    : This function is called to get IGMP max response from given list
#         
#  Usage          : _ntgrGetTrafficStreamIgmpMaxResponse <igmp_stream>
# 
#*******************************************************************************
proc _ntgrGetTrafficStreamIgmpMaxResponse {igmp_stream} {	
	return [keylget igmp_stream IGMP_MAX_RESPONSE]
}

#*******************************************************************************
#
#  Function Name	: _ntgrGetTrafficStreamCustomHeader
#
#  Description    : This function is called to get the Custom from 
#			  ntgr_trafficStreamInfo_<TGName>_<port>
#         
#  Usage          : _ntgrGetTrafficStreamCustomHeader <chassis_id> <port> <stream>
# 
#*******************************************************************************
proc _ntgrGetTrafficStreamCustomHeader {chassis_id port stream} {	
	global ntgr_trafficPortInfo_${chassis_id}
	set stream_name [_ntgrGetTrafficPortStreamName $chassis_id $port $stream]
	global $stream_name
	# return [keylget $stream_name TRAFFIC_CUSTOM_INFO]
    set retValue {}
    keylget $stream_name TRAFFIC_CUSTOM_INFO retValue
    return $retValue
}

#*******************************************************************************
#
#  Function Name	: _ntgrGetTrafficStreamCustomHeaderData
#
#  Description    : This function is called to get data from given list
#         
#  Usage          : _ntgrGetTrafficStreamCustomHeaderData <custom_header>
# 
#*******************************************************************************
proc _ntgrGetTrafficStreamCustomHeaderData {custom_header} {	
	return [keylget custom_header CUSTOM_DATA]
}

#*******************************************************************************
#  Function Name : _ntgrGetStreamDstMacMode
#
#  Description   : This function is called to get destination mac address mode.
#
#  Usage         : _ntgrGetStreamDstMacMode <l2_header>
# 
#*******************************************************************************
proc _ntgrGetStreamDstMacMode {l2_header} {	
    set ret 0
    keylget l2_header DST_MODE ret
    if {$ret==0} {
         Netgear_log_file "lib-tg-support.tcl" "running ixia lib don't define DstMacMode then try DST_MAC_ADDR_CN"
         keylget l2_header DST_MAC_ADDR_CNT ret
         switch -exact -- $ret {
            0 {  
                set ret "idle";
                Netgear_log_file "lib-tg-support.tcl" "don't define DST_MAC_ADDR_CN, set default -idle"
            }
            1 {set ret "idle"}
            default {set ret "increment"}
         }
    }
    return $ret
}

#*******************************************************************************
#  Function Name : _ntgrGetStreamSrcMacMode
#
#  Description   : This function is called to get source mac address mode.
#
#  Usage         : _ntgrGetStreamSrcMacMode <l2_header>
# 
#*******************************************************************************
proc _ntgrGetStreamSrcMacMode {l2_header} {	
    set ret 0
    keylget l2_header SRC_MODE ret
    if {$ret==0} {
         Netgear_log_file "lib-tg-support.tcl" "running ixia lib don't define DstMacMode then try SRC_MAC_ADDR_CN"
         keylget l2_header SRC_MAC_ADDR_CNT ret
         switch -exact -- $ret {
            0 {  
                set ret "idle";
                Netgear_log_file "lib-tg-support.tcl" "don't define SRC_MAC_ADDR_CN, set default -idle"
            }
            1 {set ret "idle"}
            default {set ret "increment"}
         }
    }
    return $ret
}

#*******************************************************************************
#  Function Name : _ntgrGetStreamDstMacStep
#
#  Description   : Called to get destination mac's increase/decrease step.
#                  Not used in random mode.
#
#  Usage         : _ntgrGetStreamDstMacStep <l2_header>
# 
#*******************************************************************************
proc _ntgrGetStreamDstMacStep {l2_header} {	
    set ret 0
    keylget l2_header DST_STEP ret
    if {$ret==0} {
         Netgear_log_file "lib-tg-support.tcl" "running ixia lib don't define DST_STEP then try DST_MAC_INC_BY"
         keylget l2_header DST_MAC_INC_BY ret
         if {$ret==0} { 
            set ret 1
            Netgear_log_file "lib-tg-support.tcl" "Don't define DST_MAC_INC_BY, set default 1" 
         }
    }
    return $ret
}

#*******************************************************************************
#  Function Name : _ntgrGetStreamSrcMacStep
#
#  Description   : Called to get source mac's increase/decrease step.
#                  Not used in random mode.
#
#  Usage         : _ntgrGetStreamSrcMacStep <l2_header>
# 
#*******************************************************************************
proc _ntgrGetStreamSrcMacStep {l2_header} {	
    set ret 0
    keylget l2_header SRC_STEP ret
    if {$ret==0} {
         Netgear_log_file "lib-tg-support.tcl" "running ixia lib don't define SRC_STEP then try SRC_MAC_INC_BY"
         keylget l2_header SRC_MAC_INC_BY ret
         if {$ret==0} { 
             set ret 1
             Netgear_log_file "lib-tg-support.tcl" "Don't define SRC_MAC_INC_BY, set default 1" 
         }
    }
    return $ret
}

#*******************************************************************************
#  Function Name : _ntgrGetStreamDstMacAmount
#
#  Description   : Called to get destination mac's increase/decrease amount.
#                  Not used in random and continuous incr/decr mode.
#
#  Usage         : _ntgrGetStreamDstMacAmount <l2_header>
# 
#*******************************************************************************
proc _ntgrGetStreamDstMacAmount {l2_header} {	
    set ret 0
    keylget l2_header DST_AMOUNT ret
    if {$ret==0} {
         Netgear_log_file "lib-tg-support.tcl" "running ixia lib don't define DST_AMOUNT then try DST_MAC_ADDR_CNT"
         keylget l2_header DST_MAC_ADDR_CNT ret
         if {$ret==0} { 
            set ret 1
            Netgear_log_file "lib-tg-support.tcl" "Don't define DstMacMode or DST_MAC_ADDR_CN, set default 1" 
         }
    }
    return $ret
}

#*******************************************************************************
#  Function Name : _ntgrGetStreamSrcMacAmount
#
#  Description   : Called to get source mac's increase/decrease amount.
#                  Not used in random and continuous incr/decr mode.
#
#  Usage         : _ntgrGetStreamSrcMacAmount <l2_header>
# 
#*******************************************************************************
proc _ntgrGetStreamSrcMacAmount {l2_header} {	
    set ret 0
    keylget l2_header SRC_AMOUNT ret
    if {$ret==0} {
         Netgear_log_file "lib-tg-support.tcl" "running ixia lib don't define SRC_AMOUNT then try SRC_MAC_ADDR_CNT"
         keylget l2_header SRC_MAC_ADDR_CNT ret
         if {$ret==0} { 
            set ret 1
            Netgear_log_file "lib-tg-support.tcl" "Don't define SRC_MODE or SRC_MAC_ADDR_CNT, set default 1" 
         }
    }
    return $ret
}

#*******************************************************************************
#  Function Name : _ntgrGetStreamDstMacMask
#
#  Description   : Called to get destination mac's mask.
#                  Not used in random mode.
#
#  Usage         : _ntgrGetStreamDstMacMask <l2_header>
# 
#*******************************************************************************
proc _ntgrGetStreamDstMacMask {l2_header} {	
    set ret 0
    keylget l2_header DST_MAC_MASK ret
    if {$ret==0} {
          Netgear_log_file "lib-tg-support.tcl" "running ixia lib don't define DST_MAC_MASK "
          set ret {00 00 00 00 00 00}
          Netgear_log_file "lib-tg-support.tcl" " Set DST_MAC_MASk to default value {00 00 00 00 00 00 00}" 
    }
    return $ret
}

#*******************************************************************************
#  Function Name : _ntgrGetStreamSrcMacMask
#
#  Description   : Called to get source mac's mask.
#                  Not used in random mode.
#
#  Usage         : _ntgrGetStreamSrcMacMask <l2_header>
# 
#*******************************************************************************
proc _ntgrGetStreamSrcMacMask {l2_header} {	
    set ret 0
    keylget l2_header SRC_MAC_MASK ret
    if {$ret==0} {
          Netgear_log_file "lib-tg-support.tcl" "running ixia lib don't define SRC_MAC_MASK "
          set ret {00 00 00 00 00 00}
          Netgear_log_file "lib-tg-support.tcl" " Set SRC_MAC_MASK to default value {00 00 00 00 00 00 00}" 
    }
    return $ret
}

#*******************************************************************************
#  Function Name : _ntgrGetStreamDstMacSelectMask
#
#  Description   : Called to get destination mac mask's mask value.
#                  Not used in random mode.
#
#  Usage         : _ntgrGetStreamDstMacSelectMask <l2_header>
# 
#*******************************************************************************
proc _ntgrGetStreamDstMacSelectMask {l2_header} {	
    set ret 0
    keylget l2_header DST_SELECT_MASK ret
    if {$ret==0} {
          Netgear_log_file "lib-tg-support.tcl" "running ixia lib don't define DST_SELECT_MASK"
          set ret {00 00 00 00 00 00}
          Netgear_log_file "lib-tg-support.tcl" " Set DST_SELECT_MASK to default value {00 00 00 00 00 00 00}" 
    }
    return $ret
}

#*******************************************************************************
#  Function Name : _ntgrGetStreamSrcMacSelectMask
#
#  Description   : Called to get source mac mask's mask value.
#                  Not used in random mode.
#
#  Usage         : _ntgrGetStreamSrcMacSelectMask <l2_header>
# 
#*******************************************************************************
proc _ntgrGetStreamSrcMacSelectMask {l2_header} {	
    set ret 0
    keylget l2_header SRC_SELECT_MASK ret
    if {$ret==0} {
          Netgear_log_file "lib-tg-support.tcl" "running ixia lib don't define SRC_SELECT_MASK"
          set ret {00 00 00 00 00 00}
          Netgear_log_file "lib-tg-support.tcl" " Set SRC_SELECT_MASK to default value {00 00 00 00 00 00 00}" 
    }
    return $ret
}

#*******************************************************************************
#  Function Name : _ntgrGetStreamVlanMode
#
#  Description   : Called to get vlan mode of the stream.
#
#  Usage         : _ntgrGetStreamVlanMode <vlan_header>
# 
#*******************************************************************************
proc _ntgrGetStreamVlanMode {vlan_header} {	
    set ret 0
    keylget vlan_header VLAN_MODE ret
    if {$ret==0} {
          Netgear_log_file "lib-tg-support.tcl" "running ixia lib don't define VLAN_MODE"
          keylget vlan_header VLAN_ID_COUNT ret
          if {$ret==1} {
              set ret "vIdle"
              Netgear_log_file "lib-tg-support.tcl" " Set VLAN_MODE to vIdle" 
          } else {
              set ret "vIdle"
              Netgear_log_file "lib-tg-support.tcl" "  Set VLAN_MODE to vIdle,beacause ixia can't change vlan ID in scope" 
          }    
    }
    return $ret
}

#*******************************************************************************
#  Function Name : _ntgrGetStreamVlan2Mode
#
#  Description   : Called to get vlan 2 mode of the stream.
#
#  Usage         : _ntgrGetStreamVlan2Mode <vlan_header>
# 
#*******************************************************************************
proc _ntgrGetStreamVlan2Mode {vlan_header} {	
    set ret 0
    keylget vlan_header VLAN2_MODE ret
    if {$ret==0} {
          Netgear_log_file "lib-tg-support.tcl" "running ixia lib don't define VLAN_MODE"
          keylget vlan_header VLAN2_ID_COUNT ret
          if {$ret==1} {
              set ret "vIdle"
              Netgear_log_file "lib-tg-support.tcl" " Set VLAN_MODE to vIdle" 
          } else {
              set ret "vIncrement"
              Netgear_log_file "lib-tg-support.tcl" " vContIncrement" 
          }    
    }
    return $ret
}

#*******************************************************************************
#  Function Name : _ntgrGetStreamVlanID
#
#  Description   : Called to get vlan ID from configuration variable.
#
#  Usage         : _ntgrGetStreamVlanID <vlan_header>
# 
#*******************************************************************************
proc _ntgrGetStreamVlanID {vlan_header} {	
    return [keylget vlan_header VLAN_ID]
}

#*******************************************************************************
#  Function Name : _ntgrGetStreamVlan2ID
#
#  Description   : Called to get vlan 2 ID from configuration variable.
#
#  Usage         : _ntgrGetStreamVlan2ID <vlan_header>
# 
#*******************************************************************************
proc _ntgrGetStreamVlan2ID {vlan_header} {	
    set ret 0
    keylget vlan_header VLAN2_ID ret
    return $ret
}

#*******************************************************************************
#  Function Name : _ntgrGetStreamVlanStep
#
#  Description   : Called to get vlan incr/decr step from configuration variable.
#
#  Usage         : _ntgrGetStreamVlanStep <vlan_header>
# 
#*******************************************************************************
proc _ntgrGetStreamVlanStep {vlan_header} {	
    set ret 0
    keylget vlan_header VLAN_STEP ret
    if {$ret==0} {
          Netgear_log_file "lib-tg-support.tcl" "running ixia lib don't define VlanStep, try to get VLAN_ID_INC_BY"
          keylget vlan_header VLAN_ID_INC_BY ret
          Netgear_log_file "lib-tg-support.tcl" "Get VLAN_ID_INC_BY $ret"
    }
    return $ret
}

#*******************************************************************************
#  Function Name : _ntgrGetStreamVlan2Step
#
#  Description   : Called to get vlan 2 incr/decr step from configuration variable.
#
#  Usage         : _ntgrGetStreamVlan2Step <vlan_header>
# 
#*******************************************************************************
proc _ntgrGetStreamVlan2Step {vlan_header} {	
    set ret 0
    keylget vlan_header VLAN2_STEP ret
    if {$ret==0} {
          Netgear_log_file "lib-tg-support.tcl" "running ixia lib don't define Vlan2Step, try to get VLAN_ID_INC_BY"
          keylget vlan_header VLAN2_ID_INC_BY ret
          Netgear_log_file "lib-tg-support.tcl" "Get VLAN2_ID_INC_BY $ret"
    }
    return $ret
}

#*******************************************************************************
#  Function Name : _ntgrGetStreamVlanRepeat
#
#  Description   : Called to get vlan repeat count from configuration variable.
#
#  Usage         : _ntgrGetStreamVlanRepeat <vlan_header>
# 
#*******************************************************************************
proc _ntgrGetStreamVlanRepeat {vlan_header} {	
    set ret 0
    keylget vlan_header VLAN_REPEAT ret
    if {$ret==0} {
          Netgear_log_file "lib-tg-support.tcl" "Running ixia lib don't define VLAN_REPEAT, set default"
          set ret 10000
    }
    return $ret
}

#*******************************************************************************
#  Function Name : _ntgrGetStreamVlan2Repeat
#
#  Description   : Called to get vlan repeat count from configuration variable.
#
#  Usage         : _ntgrGetStreamVlan2Repeat <vlan_header>
# 
#*******************************************************************************
proc _ntgrGetStreamVlan2Repeat {vlan_header} {	
    set ret 0
    keylget vlan_header VLAN2_REPEAT ret
    if {$ret==0} {
          Netgear_log_file "lib-tg-support.tcl" "Running ixia lib don't define VLAN_REPEAT, set default"
          set ret 10
    }
    return $ret
}

#*******************************************************************************
#  Function Name : _ntgrGetStreamVlanPriority
#
#  Description   : Called to get vlan priority from the configuration variable.
#
#  Usage         : _ntgrGetStreamVlanPriority <vlan_header>
# 
#*******************************************************************************
proc _ntgrGetStreamVlanPriority {vlan_header} {	
    set ret 0
    keylget vlan_header VLAN_PRIO ret
    if {$ret==0} {
          Netgear_log_file "lib-tg-support.tcl" "Running ixia lib don't define VLAN_PRIO, try to get VLAN_PRI"
          keylget vlan_header VLAN_PRI ret
          Netgear_log_file "lib-tg-support.tcl" "Get VLAN_PRI $ret"
    }
    return $ret
}

#*******************************************************************************
#  Function Name : _ntgrGetStreamVlan2Priority
#
#  Description   : Called to get vlan 2 priority from the configuration variable.
#
#  Usage         : _ntgrGetStreamVlan2Priority <vlan_header>
# 
#*******************************************************************************
proc _ntgrGetStreamVlan2Priority {vlan_header} {	
    set ret 0
    keylget vlan_header VLAN2_PRIO ret
    if {$ret==0} {
          Netgear_log_file "lib-tg-support.tcl" "Running ixia lib don't define VLAN_PRIO, try to get VLAN_PRI"
          keylget vlan_header VLAN2_PRI ret
          Netgear_log_file "lib-tg-support.tcl" "Get VLAN2_PRI $ret"
    }
    return $ret
}

#*******************************************************************************
#  Function Name : _ntgrGetStreamVlanCFI
#
#  Description   : Called to get vlan CFI from the configuration variable.
#
#  Usage         : _ntgrGetStreamVlanCFI <vlan_header>
# 
#*******************************************************************************
proc _ntgrGetStreamVlanCFI {vlan_header} {
    set ret 0
    keylget vlan_header VLAN_CFI ret
    if {0 == $ret } {
        return resetCFI
    } else {
        return $ret
    }
}

#*******************************************************************************
#  Function Name : _ntgrGetStreamVlan2CFI
#
#  Description   : Called to get vlan 2 CFI from the configuration variable.
#
#  Usage         : _ntgrGetStreamVlan2CFI <vlan_header>
# 
#*******************************************************************************
proc _ntgrGetStreamVlan2CFI {vlan_header} {
    set ret 0
    keylget vlan_header VLAN2_CFI ret
    if {0 == $ret } {
        return resetCFI
    } else {
        return $ret
    }
}

#*******************************************************************************
#  Function Name : _ntgrGetStreamVlanBitmask
#
#  Description   : Called to get vlan bitmask from the configuration variable.
#
#  Usage         : _ntgrGetStreamVlanBitmask <vlan_header>
# 
#*******************************************************************************
proc _ntgrGetStreamVlanBitmask {vlan_header} {	
    set ret 0
    keylget vlan_header VLAN_BITMASK ret
    return $ret
}

#*******************************************************************************
#  Function Name : _ntgrGetStreamVlan2Bitmask
#
#  Description   : Called to get vlan bitmask from the configuration variable.
#
#  Usage         : _ntgrGetStreamVlan2Bitmask <vlan_header>
# 
#*******************************************************************************
proc _ntgrGetStreamVlan2Bitmask {vlan_header} {	
    set ret 0
    keylget vlan_header VLAN2_BITMASK ret
    return $ret
}


#*******************************************************************************
#
#  Function Name	: _ntgrGetTrafficStreamL3HeaderSrcIPAddr
#
#  Description    : This function is called to get Src IP Addr from given list
#         
#  Usage          : _ntgrGetTrafficStreamL3HeaderSrcIPAddr <l3_header>
# 
#*******************************************************************************
proc _ntgrGetTrafficStreamL3HeaderSrcIPAddr {l3_header} {	
	return [keylget l3_header SRC_IP_ADDR]
}

#*******************************************************************************
#
#  Function Name	: _ntgrGetTrafficStreamL3HeaderDstIPAddr
#
#  Description    : This function is called to get Dst IP Addr from given list
#         
#  Usage          : _ntgrGetTrafficStreamL3HeaderDstIPAddr <l3_header>
# 
#*******************************************************************************
proc _ntgrGetTrafficStreamL3HeaderDstIPAddr {l3_header} {	
	return [keylget l3_header DST_IP_ADDR]
}

#*******************************************************************************
#
#  Function Name	: _ntgrGetTrafficStreamL3HeaderSrcIPMask
#
#  Description    : This function is called to get Src IP MASK from given list
#         
#  Usage          : _ntgrGetTrafficStreamL3HeaderSrcIPMask <l3_header>
# 
#*******************************************************************************
proc _ntgrGetTrafficStreamL3HeaderSrcIPMask {l3_header} {	
	return [keylget l3_header SRC_IP_MASK]
}

#*******************************************************************************
#
#  Function Name	: _ntgrGetTrafficStreamL3HeaderDstIPMask
#
#  Description    : This function is called to get DST IP MASK from given list
#         
#  Usage          : _ntgrGetTrafficStreamL3HeaderDstIPMask <l3_header>
# 
#*******************************************************************************
proc _ntgrGetTrafficStreamL3HeaderDstIPMask {l3_header} {	
	return [keylget l3_header DST_IP_MASK]
}

#*******************************************************************************
#
#  Function Name	: _ntgrGetTrafficStreamL3HeaderSrcIPCnt
#
#  Description    : This function is called to get Src IP Count from given list
#         
#  Usage          : _ntgrGetTrafficStreamL3HeaderSrcIPCnt <l3_header>
# 
#*******************************************************************************
proc _ntgrGetTrafficStreamL3HeaderSrcIPCnt {l3_header} {	
	return [keylget l3_header SRC_IP_COUNT]
}

#*******************************************************************************
#
#  Function Name	: _ntgrGetTrafficStreamL3HeaderDstIPCnt
#
#  Description    : This function is called to get DST IP Count from given list
#         
#  Usage          : _ntgrGetTrafficStreamL3HeaderDstIPCnt <l3_header>
# 
#*******************************************************************************
proc _ntgrGetTrafficStreamL3HeaderDstIPCnt {l3_header} {	
	return [keylget l3_header DST_IP_COUNT]
}

#*******************************************************************************
#
#  Function Name	: _ntgrGetTrafficStreamL3HeaderSrcIPInc
#
#  Description    : This function is called to get Src IP Increment from given list
#         
#  Usage          : _ntgrGetTrafficStreamL3HeaderSrcIPInc <l3_header>
# 
#*******************************************************************************
proc _ntgrGetTrafficStreamL3HeaderSrcIPInc {l3_header} {	
	return [keylget l3_header SRC_IP_INC_BY]
}

#*******************************************************************************
#
#  Function Name	: _ntgrGetTrafficStreamL3HeaderDstIPInc
#
#  Description    : This function is called to get DST IP increment from given list
#         
#  Usage          : _ntgrGetTrafficStreamL3HeaderDstIPInc <l3_header>
# 
#*******************************************************************************
proc _ntgrGetTrafficStreamL3HeaderDstIPInc {l3_header} {	
	return [keylget l3_header DST_IP_INC_BY]
}

#*******************************************************************************
#
#  Function Name	: _ntgrGetTrafficStreamL3HeaderSrcHostCnt
#
#  Description    : This function is called to get Src IP Host count from given list
#         
#  Usage          : _ntgrGetTrafficStreamL3HeaderSrcHostCnt <l3_header>
# 
#*******************************************************************************
proc _ntgrGetTrafficStreamL3HeaderSrcHostCnt {l3_header} {	
	return [keylget l3_header SRC_IP_HOST_COUNT]
}

#*******************************************************************************
#
#  Function Name	: _ntgrGetTrafficStreamL3HeaderDstHostCnt
#
#  Description    : This function is called to get DST IP host count from given list
#         
#  Usage          : _ntgrGetTrafficStreamL3HeaderDstHostCnt <l3_header>
# 
#*******************************************************************************
proc _ntgrGetTrafficStreamL3HeaderDstHostCnt {l3_header} {	
	return [keylget l3_header DST_IP_HOST_COUNT]
}

#*******************************************************************************
#
#  Function Name	: _ntgrGetTrafficStreamL3HeaderGwIP
#
#  Description    : This function is called to get gateway IP from given list
#         
#  Usage          : _ntgrGetTrafficStreamL3HeaderGwIP <l3_header>
# 
#*******************************************************************************
proc _ntgrGetTrafficStreamL3HeaderGwIP {l3_header} {	
	return [keylget l3_header GATEWAY_IP]
}

#*******************************************************************************
#
#  Function Name	: _ntgrGetTrafficStreamL3HeaderProtocol
#
#  Description    : This function is called to get protocol from given list
#         
#  Usage          : _ntgrGetTrafficStreamL3HeaderProtocol <l3_header>
# 
#*******************************************************************************
proc _ntgrGetTrafficStreamL3HeaderProtocol {l3_header} {	
    set ret {}
    keylget l3_header PROTOCOL ret
    return $ret
}

#*******************************************************************************
#
#  Function Name	: _ntgrGetTrafficStreamL3HeaderTTL
#
#  Description    : This function is called to get TTL from given list
#         
#  Usage          : _ntgrGetTrafficStreamL3HeaderTTL <l3_header>
# 
#*******************************************************************************
proc _ntgrGetTrafficStreamL3HeaderTTL {l3_header} {	
	return [keylget l3_header IP_TTL]
}


#*******************************************************************************
#
#  Function Name	: _ntgrGetTrafficStreamL3HeaderPROTOCOL
#
#  Description    : This function is called to get TTL from given list
#         
#  Usage          : _ntgrGetTrafficStreamL3HeaderTTL <l3_header>
# 
#*******************************************************************************
proc _ntgrGetTrafficStreamL3HeaderTTL {l3_header} {	
	return [keylget l3_header IP_TTL]
}

#*******************************************************************************
#
#  Function Name	: _ntgrGetTrafficStreamL3HeaderTOS
#
#  Description    : This function is called to get TOS from given list
#         
#  Usage          : _ntgrGetTrafficStreamL3HeaderTOS <l3_header>
# 
#*******************************************************************************
proc _ntgrGetTrafficStreamL3HeaderTOS {l3_header} {	
	return [keylget l3_header IP_TOS]
}

#------------- Added by kenddy for Dos ------------#
proc _ntgrGetTrafficStreamL3HeaderLen {l3_header} {	
  
  set ret 128
	keylget l3_header IP_LEN ret
	return $ret
}

proc _ntgrGetTrafficStreamL3HeaderFlag {l3_header} {	
  
  set ret 0
	keylget l3_header IP_FLAG ret
	return $ret
}

proc _ntgrGetTrafficStreamL3HeaderFragOffset {l3_header} {	
  
  set ret 0
	keylget l3_header IP_FRAG_OFFSET ret
	return $ret
}


#*******************************************************************************
#  Function Name : _ntgrGetStreamEthernetProtocol
#
#  Description   : Called to get the ethernet protocol number.
#
#  Usage         : _ntgrGetStreamEthernetProtocol <chassis_id port stream>
# 
#*******************************************************************************
proc _ntgrGetStreamEthernetProtocol {chassis_id port stream} {
    global ntgr_trafficPortInfo_${chassis_id}
    set stream_name [_ntgrGetTrafficPortStreamName $chassis_id $port $stream]
    global $stream_name
    return [keylget $stream_name TRAFFIC_STREAM_ETHERNET_PROTOCOL]
}

#*******************************************************************************
#  Function Name : _ntgrGetStreamArpHdr
#
#  Description   : Called to get ARP header info from configuration file.
#
#  Usage         : _ntgrGetStreamArpHdr <chassis_id port stream>
# 
#*******************************************************************************
proc _ntgrGetStreamArpHdr {chassis_id port stream} {
    global ntgr_trafficPortInfo_${chassis_id}
    set stream_name [_ntgrGetTrafficPortStreamName $chassis_id $port $stream]
    global $stream_name
    return [keylget $stream_name TRAFFIC_ARP_INFO]
}

#*******************************************************************************
#  Function Name : _ntgrGetStreamArpOperation
#
#  Description   : Called to get ARP stream's operation.
#
#  Usage         : _ntgrGetStreamArpOperation <arp_header>
# 
#*******************************************************************************
proc _ntgrGetStreamArpOperation {arp_header} {	
    return [keylget arp_header ARP_OPERATION]
}

#*******************************************************************************
#  Function Name : _ntgrGetStreamArpDstMacMode
#
#  Description   : Called to get ARP target mac address's mode.
#
#  Usage         : _ntgrGetStreamArpDstMacMode <arp_header>
# 
#*******************************************************************************
proc _ntgrGetStreamArpDstMacMode {arp_header} {	
    return [keylget arp_header ARP_T_MAC_MODE]
}

#*******************************************************************************
#  Function Name : _ntgrGetStreamArpSrcMacMode
#
#  Description   : Called to get ARP sender mac address's mode.
#
#  Usage         : _ntgrGetStreamArpSrcMacMode <arp_header>
# 
#*******************************************************************************
proc _ntgrGetStreamArpSrcMacMode {arp_header} {	
    return [keylget arp_header ARP_S_MAC_MODE]
}

#*******************************************************************************
#  Function Name : _ntgrGetStreamArpDstMac
#
#  Description   : Called to get ARP stream's target mac address.
#
#  Usage         : _ntgrGetStreamArpDstMac <arp_header>
# 
#*******************************************************************************
proc _ntgrGetStreamArpDstMac {arp_header} {	
    return [keylget arp_header ARP_T_MAC]
}

#*******************************************************************************
#  Function Name : _ntgrGetStreamArpSrcMac
#
#  Description   : Called to get ARP stream's sender mac address.
#
#  Usage         : _ntgrGetStreamArpSrcMac <arp_header>
# 
#*******************************************************************************
proc _ntgrGetStreamArpSrcMac {arp_header} {	
    return [keylget arp_header ARP_S_MAC]
}

#*******************************************************************************
#  Function Name : _ntgrGetStreamArpDstMacCnt
#
#  Description   : Called to get ARP target mac address' count.
#
#  Usage         : _ntgrGetStreamArpDstMacCnt <arp_header>
# 
#*******************************************************************************
proc _ntgrGetStreamArpDstMacCnt {arp_header} {	
    return [keylget arp_header ARP_T_MAC_CNT]
}

#*******************************************************************************
#  Function Name : _ntgrGetStreamArpSrcMacCnt
#
#  Description   : Called to get ARP sender mac address' count.
#
#  Usage         : _ntgrGetStreamArpSrcMacCnt <arp_header>
# 
#*******************************************************************************
proc _ntgrGetStreamArpSrcMacCnt {arp_header} {	
    return [keylget arp_header ARP_S_MAC_CNT]
}

#*******************************************************************************
#  Function Name : _ntgrGetStreamArpDstIPMode
#
#  Description   : Called to get ARP target ip address' mode.
#
#  Usage         : _ntgrGetStreamArpDstIPMode <arp_header>
# 
#*******************************************************************************
proc _ntgrGetStreamArpDstIPMode {arp_header} {	
    return [keylget arp_header ARP_T_IP_MODE]
}

#*******************************************************************************
#  Function Name : _ntgrGetStreamArpSrcIPMode
#
#  Description   : Called to get ARP sender ip address' mode.
#
#  Usage         : _ntgrGetStreamArpSrcIPMode <arp_header>
# 
#*******************************************************************************
proc _ntgrGetStreamArpSrcIPMode {arp_header} {	
    return [keylget arp_header ARP_S_IP_MODE]
}

#*******************************************************************************
#  Function Name : _ntgrGetStreamArpDstIP
#
#  Description   : Called to get ARP stream's target ip address.
#
#  Usage         : _ntgrGetStreamArpDstIP <arp_header>
# 
#*******************************************************************************
proc _ntgrGetStreamArpDstIP {arp_header} {	
    return [keylget arp_header ARP_T_IP]
}

#*******************************************************************************
#  Function Name : _ntgrGetStreamArpSrcIP
#
#  Description   : Called to get ARP stream's sender ip address.
#
#  Usage         : _ntgrGetStreamArpSrcIP <arp_header>
# 
#*******************************************************************************
proc _ntgrGetStreamArpSrcIP {arp_header} {	
    return [keylget arp_header ARP_S_IP]
}

#*******************************************************************************
#  Function Name : _ntgrGetStreamArpDstIPCnt
#
#  Description   : Called to get ARP target ip address' count.
#
#  Usage         : _ntgrGetStreamArpDstIPCnt <arp_header>
# 
#*******************************************************************************
proc _ntgrGetStreamArpDstIPCnt {arp_header} {	
    return [keylget arp_header ARP_T_IP_CNT]
}

#*******************************************************************************
#  Function Name : _ntgrGetStreamArpSrcIPCnt
#
#  Description   : Called to get ARP sender ip address' count.
#
#  Usage         : _ntgrGetStreamArpSrcIPCnt <arp_header>
# 
#*******************************************************************************
proc _ntgrGetStreamArpSrcIPCnt {arp_header} {	
    return [keylget arp_header ARP_S_IP_CNT]
}

#*******************************************************************************
#  Function Name : _ntgrGetSubIfList
#
#  Description   : Called to get subinterface's parameter info.
#
#  Usage         : _ntgrGetSubIfList <chassis_id port>
# 
#*******************************************************************************
proc _ntgrGetSubIfList {chassis_id port} {
    global ntgr_trafficPortInfo_$chassis_id
    set retValue {}
    keylget ntgr_trafficPortInfo_${chassis_id}($port) PORT_SUBIF_LIST retValue
    return $retValue
}
#*******************************************************************************
#  Function Name : _ntgrGetRouterRipList
#
#  Description   : Called to get emulated router list on a port.
#
#  Usage         : _ntgrGetRouterRipList <chassis_id port>
# 
#*******************************************************************************
proc _ntgrGetRouterRipList {chassis_id port} {
    global ntgr_trafficPortInfo_$chassis_id
    set retValue {}
    keylget ntgr_trafficPortInfo_${chassis_id}($port) ROUTER_RIP_LIST retValue
    return $retValue
}

#*******************************************************************************
#  Function Name : _ntgrGetRipSendVer
#
#  Description   : Called to get RIP version from RIP header definition.
#
#  Usage         : _ntgrGetRipSendVer <rip_header>
# 
#*******************************************************************************
proc _ntgrGetRipSendVer {rip_header} {
    global $rip_header
    return [keylget $rip_header RIP_SEND_VERSION]
}

#*******************************************************************************
#  Function Name : _ntgrGetRipRecVer
#
#  Description   : Called to get RIP version from RIP header definition.
#
#  Usage         : _ntgrGetRipRecVer <rip_header>
# 
#*******************************************************************************
proc _ntgrGetRipRecVer {rip_header} {
    global $rip_header
    return [keylget $rip_header RIP_REC_VERSION]
}
#*******************************************************************************
#  Function Name : _ntgrGetRipAuth
#
#  Description   : Called to get RIP authentication type from RIP header definition.
#
#  Usage         : _ntgrGetRipAuth <rip_header>
# 
#*******************************************************************************
proc _ntgrGetRipAuth {rip_header} {
    global $rip_header
    return [keylget $rip_header RIP_AUTH]
}

#*******************************************************************************
#  Function Name : _ntgrGetRipAuthPass
#
#  Description   : Called to get RIP authentication password from RIP header definition.
#
#  Usage         : _ntgrGetRipAuthPass <rip_header>
# 
#*******************************************************************************
proc _ntgrGetRipAuthPass {rip_header} {
    set ret ""
    global $rip_header
    keylget $rip_header RIP_AUTH_PASS ret
    return $ret  
}

#*******************************************************************************
#  Function Name : _ntgrGetRipAuthMd5key
#
#  Description   : Called to get RIP authentication md5 key from RIP header definition.
#
#  Usage         : _ntgrGetRipAuthMd5key <rip_header>
# 
#*******************************************************************************
proc _ntgrGetRipAuthMd5key {rip_header} {
    set ret ""
    global $rip_header
    keylget $rip_header RIP_AUTH_MD5KEY ret
    return $ret
}

#*******************************************************************************
#  Function Name : _ntgrGetRipCastType
#
#  Description   : Called to get RIP packets' cast type from RIP header definition.
#
#  Usage         : _ntgrGetRipCastType <rip_header>
# 
#*******************************************************************************
proc _ntgrGetRipCastType {rip_header} {
    set ret "default"
    global $rip_header
    keylget $rip_header RIP_CAST_TYPE ret
    return $ret
}

#*******************************************************************************
#  Function Name : _ntgrGetRipRouterIP
#
#  Description   : Called to get tester ip from RIP header definition.
#
#  Usage         : _ntgrGetRipRouterIP <rip_header>
# 
#*******************************************************************************
proc _ntgrGetRipRouterIP {rip_header} {
    global $rip_header
    return [keylget $rip_header RIP_ROUTER_IP]
}

#*******************************************************************************
#  Function Name : _ntgrGetRipRouterIPMask
#
#  Description   : Called to get tester ip mask from RIP header definition.
#
#  Usage         : _ntgrGetRipRouterIPMask <rip_header>
# 
#*******************************************************************************
proc _ntgrGetRipRouterIPMask {rip_header} {
    global $rip_header
    return [keylget $rip_header RIP_ROUTER_IP_MASK]
}

#*******************************************************************************
#  Function Name : _ntgrGetRipDutIP
#
#  Description   : Called to get DUT ip from RIP header definition.
#
#  Usage         : _ntgrGetRipDutIP <rip_header>
# 
#*******************************************************************************
proc _ntgrGetRipDutIP {rip_header} {
    global $rip_header
    return [keylget $rip_header RIP_DUT_IP]
}

#*******************************************************************************
#  Function Name : _ntgrGetRipMaxRoutePerUpdate
#
#  Description   : Called to get the max number of routes in one update from 
#                  RIP header definition.
#
#  Usage         : _ntgrGetRipMaxRoutePerUpdate <rip_header>
# 
#*******************************************************************************
proc _ntgrGetRipMaxRoutePerUpdate {rip_header} {
    global $rip_header
    return [keylget $rip_header RIP_MAX_ROUTE_PER_UPDATE]
}

#*******************************************************************************
#  Function Name : _ntgrGetRipUpdateInterval
#
#  Description   : Called to get rip route update interval from 
#                  RIP header definition.
#
#  Usage         : _ntgrGetRipUpdateInterval <rip_header>
# 
#*******************************************************************************
proc _ntgrGetRipUpdateInterval {rip_header} {
    set ret 30
    global $rip_header
    keylget $rip_header RIP_UPDATE_INTERVAL ret
    return $ret
}

#*******************************************************************************
#  Function Name : _ntgrGetRipUpdateIntervalOffset
#
#  Description   : Called to get rip route update interval offset from 
#                  RIP header definition.
#
#  Usage         : _ntgrGetRipUpdateIntervalOffset <rip_header>
# 
#*******************************************************************************
proc _ntgrGetRipUpdateIntervalOffset {rip_header} {
    set ret 5
    global $rip_header
    keylget $rip_header RIP_UPDATE_INTERVAL_OFFSET ret
    return $ret
}

#*******************************************************************************
#  Function Name : _ntgrGetRipRouteList
#
#  Description   : Called to get rip routes definition list from 
#                  RIP header definition.
#
#  Usage         : _ntgrGetRipRouteList <rip_header>
# 
#*******************************************************************************
proc _ntgrGetRipRouteList {rip_header} {
    global $rip_header
    return [keylget $rip_header RIP_ROUTE_LIST]
}

#*******************************************************************************
#  Function Name : _ntgrGetRipResponseMode
#
#  Description   : Called to get rip response mode
#
#  Usage         : _ntgrGetRipResponseMode <rip_header>
# 
#*******************************************************************************
proc _ntgrGetRipResponseMode {rip_header} {
    global $rip_header
    return [keylget $rip_header RIP_RESPONCE_MODE]
}

#*******************************************************************************
#  Function Name : _ntgrGetRipRouteNexthop
#
#  Description   : Called to get rip routes nexthop from RIP header definition.
#
#  Usage         : _ntgrGetRipRouteNexthop <rip_route>
# 
#*******************************************************************************
proc _ntgrGetRipRouteNexthop {rip_route} {
    global $rip_route
    return [keylget $rip_route RIP_ROUTE_NEXTHOP]
}

#*******************************************************************************
#  Function Name : _ntgrGetRipRouteMetric
#
#  Description   : Called to get rip routes metric from RIP header definition.
#
#  Usage         : _ntgrGetRipRouteMetric <rip_route>
# 
#*******************************************************************************
proc _ntgrGetRipRouteMetric {rip_route} {
    global $rip_route
    return [keylget $rip_route RIP_ROUTE_METRIC]
}

#*******************************************************************************
#  Function Name : _ntgrGetRipRouteTag
#
#  Description   : Called to get rip routes Tag value from 
#                  RIP header definition.
#
#  Usage         : _ntgrGetRipRouteTag <rip_route>
# 
#*******************************************************************************
proc _ntgrGetRipRouteTag {rip_route} {
    set ret 0
    global $rip_route
    keylget $rip_route RIP_ROUTE_TAG ret
    return $ret
}

#*******************************************************************************
#  Function Name : _ntgrGetRipRouteStartIP
#
#  Description   : Called to get rip routes start ip address from 
#                  RIP header definition.
#
#  Usage         : _ntgrGetRipRouteStartIP <rip_route>
# 
#*******************************************************************************
proc _ntgrGetRipRouteStartIP {rip_route} {
    global $rip_route
    return [keylget $rip_route RIP_ROUTE_START_IP]
}

#*******************************************************************************
#  Function Name : _ntgrGetRipRoutePrefixLength
#
#  Description   : Called to get rip routes prefix length from 
#                  RIP header definition.
#
#  Usage         : _ntgrGetRipRoutePrefixLength <rip_route>
# 
#*******************************************************************************
proc _ntgrGetRipRoutePrefixLength {rip_route} {
    global $rip_route
    return [keylget $rip_route RIP_ROUTE_PREFIX_LENGTH]
}

#*******************************************************************************
#  Function Name : _ntgrGetRipRouteCount
#
#  Description   : Called to get rip routes total count from 
#                  RIP header definition.
#
#  Usage         : _ntgrGetRipRouteCount <rip_route>
# 
#*******************************************************************************
proc _ntgrGetRipRouteCount {rip_route} {
    global $rip_route
    return [keylget $rip_route RIP_ROUTE_COUNT]
}

#*******************************************************************************
#  Function Name : _ntgrGetRipRouteStep
#
#  Description   : Called to get rip routes increasing step from 
#                  RIP header definition.
#
#  Usage         : _ntgrGetRipRouteStep <rip_route>
# 
#*******************************************************************************
proc _ntgrGetRipRouteStep {rip_route} {
    global $rip_route
    return [keylget $rip_route RIP_ROUTE_STEP]
}

#*******************************************************************************
#  Function Name : _ntgrGetDescriptionforMaininterface
#
#  Description   : Called to get description for main interface
#
#  Usage         : _ntgrGetDescriptionforMaininterface <chassis_id> <port>
# 
#*******************************************************************************
proc _ntgrGetDescriptionforMaininterface {chassis_id port} {
    global ntgr_trafficPortInfo_$chassis_id
    set ret ""
    keylget ntgr_trafficPortInfo_${chassis_id}($port) INTERFACE_DESCRIPTION ret
    return $ret
}
#*******************************************************************************
#  Function Name : _ntgrGetRouterOSPFList
#
#  Description   : Called to get emulated ospf router list on a port.
#
#  Usage         : _ntgrGetRouterOSPFList <chassis_id port>
# 
#*******************************************************************************
proc _ntgrGetRouterOSPFList {chassis_id port} {
    global ntgr_trafficPortInfo_$chassis_id
    set retValue {}
    keylget ntgr_trafficPortInfo_${chassis_id}($port) ROUTER_OSPF_LIST retValue
    return $retValue
}

#*******************************************************************************
#  Function Name : _ntgrGetOSPFRouterID
#
#  Description   : Called to get OSPF router_id from OSPF header definition.
#
#  Usage         : _ntgrGetOSPFRouterID <ospf_header>
# 
#*******************************************************************************
proc _ntgrGetOSPFRouterID {ospf_header} {
    global $ospf_header
    return [keylget $ospf_header OSPF_ROUTER_ID]
}

#*******************************************************************************
#  Function Name : _ntgrGetOSPFAreaID
#
#  Description   : Called to get OSPF area_id from OSPF header definition.
#
#  Usage         : _ntgrGetOSPFAreaID <ospf_header>
# 
#*******************************************************************************
proc _ntgrGetOSPFAreaID {ospf_header} {
    global $ospf_header
    return [keylget $ospf_header OSPF_AREA_ID]
}

#*******************************************************************************
#  Function Name : _ntgrGetOSPFInterfaceNetworkType
#
#  Description   : Called to get OSPF interface network type from OSPF header definition.
#
#  Usage         : _ntgrGetOSPFInterfaceNetworkType <ospf_header>
# 
#*******************************************************************************
proc _ntgrGetOSPFInterfaceNetworkType {ospf_header} {
    global $ospf_header
    return [keylget $ospf_header OSPF_NETWORK_TYPE]
}

#*******************************************************************************
#  Function Name : _ntgrGetOSPFRouteRange
#
#  Description   : Called to get OSPF route range from header definition.
#
#  Usage         : _ntgrGetOSPFRouteRange <ospf_header>
# 
#*******************************************************************************
proc _ntgrGetOSPFRouteRange {ospf_header} {
    global $ospf_header
    return [keylget $ospf_header OSPF_ROUTE_RANGE]
}

#*******************************************************************************
#  Function Name : _ntgrGetOSPFIFdescription
#
#  Description   : Called to get description for the interface running OSPF from header definition.
#
#  Usage         : _ntgrGetOSPFIFdescription <ospf_header>
# 
#*******************************************************************************
proc _ntgrGetOSPFIFdescription {ospf_header} {
    global $ospf_header
    return [keylget $ospf_header OSPF_IF_DESCRIPTION]
}
#*******************************************************************************
#  Function Name : _ntgrGetOSPFFirstRouteINRouteRange
#
#  Description   : Called to get ospf first route from RIP header definition.
#
#  Usage         : _ntgrGetOSPFFirstRouteINRouteRange <ospf_route>
# 
#*******************************************************************************
proc _ntgrGetOSPFFirstRouteINRouteRange {ospf_route} {
    global $ospf_route
    return [keylget $ospf_route OSPF_FIRST_ROUTE]
}

#*******************************************************************************
#  Function Name : _ntgrGetOSPFRouteMask
#
#  Description   : Called to get ospf route mask from RIP header definition.
#
#  Usage         : _ntgrGetOSPFRouteMask <ospf_route>
# 
#*******************************************************************************
proc _ntgrGetOSPFRouteMask {ospf_route} {
    global $ospf_route
    return [keylget $ospf_route OSPF_ROUTE_MASK]
}

#*******************************************************************************
#  Function Name : _ntgrGetOSPFRouteCount
#
#  Description   : Called to get ospf route count from RIP header definition.
#
#  Usage         : _ntgrGetOSPFRouteCount <ospf_route>
# 
#*******************************************************************************
proc _ntgrGetOSPFRouteCount {ospf_route} {
    global $ospf_route
    return [keylget $ospf_route OSPF_ROUTE_COUNT]
}

#*******************************************************************************
#  Function Name : _ntgrGetOSPFRouteMetric
#
#  Description   : Called to get ospf route metric from RIP header definition.
#
#  Usage         : _ntgrGetOSPFRouteMetric <ospf_route>
# 
#*******************************************************************************
proc _ntgrGetOSPFRouteMetric {ospf_route} {
    global $ospf_route
    return [keylget $ospf_route OSPF_ROUTE_METRIC]
}

#*******************************************************************************
#  Function Name : _ntgrGetOSPFRouteOrigin
#
#  Description   : Called to get ospf route origin from RIP header definition.
#
#  Usage         : _ntgrGetOSPFRouteOrigin <ospf_route>
# 
#*******************************************************************************
proc _ntgrGetOSPFRouteOrigin {ospf_route} {
    global $ospf_route
    return [keylget $ospf_route OSPF_ROUTE_ORIGIN]
}

#*******************************************************************************
#
#  Function Name	: _ntgrGetTrafficStreamVlanHeader2Id
#
#  Description    : This function is called to get the 2nd VLAN ID from 
#			  given list
#         
#  Usage          : _ntgrGetTrafficStreamVlanHeader2Id <vlan_header>
# 
#*******************************************************************************
proc _ntgrGetTrafficStreamVlanHeader2Id {vlan_header} {
	set ret {}
	keylget vlan_header VLAN2_ID ret
	return $ret
}

#*******************************************************************************
#
#  Function Name	: _ntgrGetTrafficStreamVlanHeader2Pri
#
#  Description    : This function is called to get the 2nd vlan priority from 
#			  given list
#         
#  Usage          : _ntgrGetTrafficStreamVlanHeader2Pri <vlan_header>
# 
#*******************************************************************************
proc _ntgrGetTrafficStreamVlanHeader2Pri {vlan_header} {
	set ret {}
	keylget vlan_header VLAN2_PRI ret
	return $ret
}

#*******************************************************************************
#
#  Function Name	: _ntgrGetTrafficStreamVlanHeader2Cnt
#
#  Description    : This function is called to get the 2nd vlan count from 
#			  given list
#         
#  Usage          : _ntgrGetTrafficStreamVlanHeader2Cnt <vlan_header>
# 
#*******************************************************************************
proc _ntgrGetTrafficStreamVlanHeader2Cnt {vlan_header} {
	set ret {}
	keylget vlan_header VLAN2_ID_COUNT ret
	return $ret
}

#*******************************************************************************
#
#  Function Name	: _ntgrGetTrafficStreamVlanHeader2Inc
#
#  Description    : This function is called to get the 2nd vlan increment by from 
#			  given list
#         
#  Usage          : _ntgrGetTrafficStreamVlanHeader2Inc <vlan_header>
# 
#*******************************************************************************
proc _ntgrGetTrafficStreamVlanHeader2Inc {vlan_header} {
	set ret {}
	keylget vlan_header VLAN2_ID_INC_BY ret
	return $ret
}

## Generate random number between 10~99, for IP Addr or MAC Addr
proc _rnd10To99 {} {
    return [expr { int(90 * rand()+10) }]
}

# Get IPv6 Header from stream definition
proc _ntgrGetIPv6Hdr {chassis_id port stream} {
    global ntgr_trafficPortInfo_${chassis_id}
    set stream_name [_ntgrGetTrafficPortStreamName $chassis_id $port $stream]
    global $stream_name
    set retValue {}
    keylget $stream_name TRAFFIC_IPV6HDR retValue
    return $retValue
}

proc _ntgrGetIPv6SrcAddr {ipv6hdr} {
	set ret "FE80:00:00:00:00:[_rnd10To99]:[_rnd10To99]:[_rnd10To99]"
	keylget ipv6hdr SRC_ADDR ret
	return $ret
}

proc _ntgrGetIPv6SrcPrefixLength {ipv6hdr} {
	set ret 64
	keylget ipv6hdr SRC_PREFIX_LEN ret
	return $ret
}

proc _ntgrGetIPv6SRCHostCnt {ipv6hdr} {
	set ret 1
	keylget ipv6hdr SRC_HOST_CNT ret
	return $ret
}

proc _ntgrGetIPv6SrcNetCnt {ipv6hdr} {
	set ret 1
	keylget ipv6hdr SRC_NETWORK_CNT ret
	return $ret
}

proc _ntgrGetIPv6SrcNetInc {ipv6hdr} {
	set ret 1
	keylget ipv6hdr SRC_NETWORK_INC ret
	return $ret
}

proc _ntgrGetIPv6DstAddr {ipv6hdr} {
	set ret "FE80:00:00:00:00:[_rnd10To99]:[_rnd10To99]:[_rnd10To99]"
	keylget ipv6hdr DST_ADDR ret
	return $ret
}

proc _ntgrGetIPv6DstPrefixLength {ipv6hdr} {
	set ret 64
	keylget ipv6hdr DST_PREFIX_LEN ret
	return $ret
}

proc _ntgrGetIPv6DstAddrMode {ipv6hdr} {
	set ret "ipV6Idle"
	keylget ipv6hdr DST_ADDR_MODE ret
	return $ret
}

proc _ntgrGetIPv6SrcAddrMode {ipv6hdr} {
	set ret "ipV6Idle"
	keylget ipv6hdr SRC_ADDR_MODE ret
	return $ret
}

proc _ntgrGetIPv6DstHostCnt {ipv6hdr} {
	set ret 1
	keylget ipv6hdr DST_HOST_CNT ret
	return $ret
}

proc _ntgrGetIPv6DstNetCnt {ipv6hdr} {
	set ret 1
	keylget ipv6hdr DST_NETWORK_CNT ret
	return $ret
}

proc _ntgrGetIPv6DstNetInc {ipv6hdr} {
	set ret 1
	keylget ipv6hdr DST_NETWORK_INC ret
	return $ret
}

proc _ntgrGetIPv6TrafficClass {ipv6hdr} {
	set ret 0
	keylget ipv6hdr TRAFFIC_CLASS ret
	return $ret
}

proc _ntgrGetIPv6FlowLabel {ipv6hdr} {
	set ret 0
	keylget ipv6hdr FLOW_LABEL ret
	return $ret
}

proc _ntgrGetIPv6PayloadLength {ipv6hdr} {
	set ret 128
	keylget ipv6hdr PAYLOAD_LENGTH ret
	return $ret
}

proc _ntgrGetIPv6NextHdr {ipv6hdr} {
	set ret 59
	keylget ipv6hdr NEXT_HDR ret
	return $ret
}

proc _ntgrGetIPv6HopLimit {ipv6hdr} {
	set ret 64
	keylget ipv6hdr HOP_LIMIT ret
	return $ret
}

proc _ntgrGetIPv6Gateway {ipv6hdr} {
	set ret "FE80:00:00:00:00:[_rnd10To99]:[_rnd10To99]:[_rnd10To99]"
	keylget ipv6hdr GATEWAY ret
	return $ret
}

proc _ntgrDefaultSrcIp {ipv6hdr} {
	set ret FALSE
	keylget ipv6hdr SRC_DEFAULT ret
	return $ret
}

proc _ntgrDefaultGateway {ipv6hdr} {
	set ret FALSE
	keylget ipv6hdr GW_DEFAULT ret
	return $ret
}

##------------ Added Ipv6 Extension Header by kenddy ---------------------##

proc _ntgrGetIPv6ExtHdr {chassis_id port stream} {
    global ntgr_trafficPortInfo_${chassis_id}
    set stream_name [_ntgrGetTrafficPortStreamName $chassis_id $port $stream]
    global $stream_name
    set retValue {}
    keylget $stream_name TRAFFIC_IPV6EXTHDR retValue
    return $retValue
}

proc _ntgrGetIPv6ExtHbhNextHeadType {ipv6ExtHdr} {
	set ret 59
	keylget ipv6ExtHdr HBH_NH_TYPE ret
	return $ret
}
proc _ntgrGetIPv6ExtDstNextHeadType {ipv6ExtHdr} {
	set ret 59
	keylget ipv6ExtHdr DST_NH_TYPE ret
	return $ret
}

proc _ntgrGetIPv6ExtFragmentNextHeadType {ipv6ExtHdr} {
	set ret 59
	keylget ipv6ExtHdr FRAG_NH_TYPE ret
	return $ret
}

proc _ntgrGetIPv6ExtFragmentOffset {ipv6ExtHdr} {
	set ret 0
	keylget ipv6ExtHdr FRAG_OFFSET ret
	return $ret
}
proc _ntgrGetIPv6ExtFragmentId {ipv6ExtHdr} {
	set ret 0
	keylget ipv6ExtHdr FRAG_ID ret
	return $ret
}

proc _ntgrGetIPv6ExtMoreFragFlag {ipv6ExtHdr} {
	set ret 0
	keylget ipv6ExtHdr MORE_FRAG_FLAG ret
	return $ret
}


proc _ntgrGetIPv6ExtRoutingNextHeadType {ipv6ExtHdr} {
	set ret 59
	keylget ipv6ExtHdr ROUTING_NH_TYPE ret
	return $ret
}

proc _ntgrGetIPv6ExtRoutingType {ipv6ExtHdr} {
	set ret 0
	keylget ipv6ExtHdr ROUTING_TYPE ret
	return $ret
}
proc _ntgrGetIPv6ExtRoutingSegLeft {ipv6ExtHdr} {
	set ret 0
	keylget ipv6ExtHdr SEG_LEFT ret
	return $ret
}

proc _ntgrGetIPv6ExtAuthNextHeadType {ipv6ExtHdr} {
	set ret 59
	keylget ipv6ExtHdr AUTH_NH_TYPE ret
	return $ret
}

proc _ntgrGetIPv6ExtAuthHeadLen {ipv6ExtHdr} {
	set ret 0
	keylget ipv6ExtHdr AUTH_HEAD_EXT_LEN ret
	return $ret
}

proc _ntgrGetIPv6ExtAuthSeqNumber {ipv6ExtHdr} {
	set ret 0
	keylget ipv6ExtHdr AUTH_SEQ_NUMBER ret
	return $ret
}
proc _ntgrGetIPv6ExtAuthSPI {ipv6ExtHdr} {
	set ret 256
	keylget ipv6ExtHdr AUTH_SPI ret
	return $ret
}
proc _ntgrGetIPv6ExtAuthdata {ipv6ExtHdr} {
	set ret 0
	keylget ipv6ExtHdr AUTH_DATA ret
	return $ret
}

proc _ntgrGetIPv6ExtEspNextHeadType {ipv6ExtHdr} {
	set ret 59
	keylget ipv6ExtHdr ESP_NH_TYPE ret
	return $ret
}

proc _ntgrGetIPv6ExtEspData {ipv6ExtHdr} {
	set ret 0
	keylget ipv6ExtHdr ESP_DATA ret
	return $ret
}
proc _ntgrGetIPv6ExtEspPad {ipv6ExtHdr} {
	set ret ff
	keylget ipv6ExtHdr ESP_PAD ret
	return $ret
}
proc _ntgrGetIPv6ExtEspPayLoad {ipv6ExtHdr} {
	set ret ff
	keylget ipv6ExtHdr ESP_PAY_LOAD ret
	return $ret
}

proc _ntgrGetIPv6ExtEspSeqNum {ipv6ExtHdr} {
	set ret 0
	keylget ipv6ExtHdr ESP_SEQ_NUM ret
	return $ret
}
proc _ntgrGetIPv6ExtEspSpi {ipv6ExtHdr} {
	set ret 256
	keylget ipv6ExtHdr ESP_SPI ret
	return $ret
}




#====================================dhcp info added by Tony========================================
#*******************************************************************************
#  Function Name : _ntgrGetDHCPCLIENTList
#
#  Description   : Called to get emulated dhcp client list on ixia port.
#
#  Usage         : _ntgrGetDHCPCLIENTList <chassis_id port>
# 
#*******************************************************************************
proc _ntgrGetDHCPCLIENTList {chassis_id port} {
    global ntgr_trafficPortInfo_$chassis_id
    set retValue {}
    keylget ntgr_trafficPortInfo_${chassis_id}($port) HOST_DHCP_CLIENT_LIST retValue
    return $retValue
}

#*******************************************************************************
#  Function Name : _ntgrGetDHCPCLIENTCount
#
#  Description   : Called to get dhcp host count.
#
#  Usage         : _ntgrGetDHCPCLIENTCount <dhcp_name>
# 
#*******************************************************************************
proc _ntgrGetDHCPCLIENTCount {dhcp_name} {
	  global $dhcp_name
    set retValue 0
    keylget $dhcp_name DHCP_CLIENT_COUNT retValue
    return $retValue
}
#*******************************************************************************
#  Function Name : _ntgrGetDHCPCLIENTDescription
#
#  Description   : Called to get dhcp host description.
#
#  Usage         : _ntgrGetDHCPCLIENTDescription <dhcp_name>
# 
#*******************************************************************************
proc _ntgrGetDHCPCLIENTDescription {dhcp_name} {
	  global $dhcp_name
    set retValue ""
    keylget $dhcp_name DHCP_CLIENT_DESCRIPTION retValue
    return $retValue
}
#*******************************************************************************
#  Function Name : _ntgrGetDHCPCLIENTMacAddress
#
#  Description   : Called to get dhcp host mac address.
#
#  Usage         : _ntgrGetDHCPCLIENTMacAddress <dhcp_name>
# 
#*******************************************************************************
proc _ntgrGetDHCPCLIENTMacAddress {dhcp_name} {
	  global $dhcp_name
    set retValue ""
    keylget $dhcp_name DHCP_CLIENT_MAC_ADDR retValue
    return $retValue
}

#*******************************************************************************
#  Function Name : _ntgrGetDHCPINCRVLANID
#
#  Description   : Called to get dhcp incr init vlan id.
#
#  Usage         : _ntgrGetDHCPINCRVLANID <dhcp_name>
# 
#*******************************************************************************
proc _ntgrGetDHCPINCRVLANID {dhcp_name} {
	global $dhcp_name
    set retValue ""
    keylget $dhcp_name DHCP_INCR_VLAN_ID retValue
    return $retValue
}


#***************************** add by jim.xie for scalability case-013 begin **********
#*******************************************************************************
#
#  Function Name	: _ntgrGetTrafficStreamL3ARPHeader
#
#  Description    : This function is called to get the ARP header from 
#			  ntgr_trafficStreamInfo_<TGName>_<port>
#         
#  Usage          : _ntgrGetTrafficStreamL3ARPHeader <chassis_id> <port> <stream>
# 
#
#  Author         : jim.xie
#*******************************************************************************
proc _ntgrGetTrafficStreamL3ARPHeader {chassis_id port stream} {	
	global ntgr_trafficPortInfo_${chassis_id}
	set stream_name [_ntgrGetTrafficPortStreamName $chassis_id $port $stream]
	global $stream_name
    set retValue {}
    keylget $stream_name TRAFFIC_L3_STREAM_ARP retValue
    return $retValue
}

#*******************************************************************************
#  Function Name : _ntgrGetStreamL3ARPOperation
#
#  Description   : Called to get operation of arp .
#
#  Usage         : _ntgrGetStreamL3ARPOperation <l3arp_header>
# 
#  Author        : jim.xie
#  
#*******************************************************************************
proc _ntgrGetStreamL3ARPOperation {l3arp_header} {	
	set ret ""
    keylget l3arp_header ARP_OPERATION ret
	if {$ret == "request"} {
	    set ret "arpRequest"
	} elseif {$ret == "reply"} {
	    set ret "arpReply"
	}
    return $ret
}

#*******************************************************************************
#  Function Name : _ntgrGetStreamL3ARPSrcHwAddr
#
#  Description   : Called to get source Hardware ip address .
#
#  Usage         : _ntgrGetStreamL3ARPSrcHwAddr <l3arp_header>
# 
#  Author        : jim.xie
#  
#*******************************************************************************
proc _ntgrGetStreamL3ARPSrcHwAddr {l3arp_header} {	
    return [keylget l3arp_header ARP_SRC_HW_ADDR]
}

#*******************************************************************************
#  Function Name : _ntgrGetStreamL3ARPSrcHwCnt
#
#  Description   : Called to get count of source Hardware ip address .
#
#  Usage         : _ntgrGetStreamL3ARPSrcHwCnt <l3arp_header>
# 
#  Author        : jim.xie
#  
#*******************************************************************************
proc _ntgrGetStreamL3ARPSrcHwCnt {l3arp_header} {	
    return [keylget l3arp_header ARP_SRC_HW_CNT]
}

#*******************************************************************************
#  Function Name : _ntgrGetStreamL3ARPSrcHwMode
#
#  Description   : Called to get source ip address mode.
#
#  Usage         : _ntgrGetStreamL3ARPSrcHwMode <l3arp_header>
#
#
#  Author        : jim.xie 
#*******************************************************************************
proc _ntgrGetStreamL3ARPSrcHwMode {l3arp_header} {	
    set ret ""
    keylget l3arp_header ARP_SRC_HW_MODE ret
	if {$ret == "fix"} {
	    set ret "arpIdle"
	} elseif {$ret == "increment"} {
	    set ret "arpIncrement"
	} elseif {$ret == "decrement"} {
	    set ret "arpDecrement"
	} elseif {$ret == "continuousincrement"} {
	    set ret "arpContinuousIncrement"
	} elseif {$ret == "continuousdecrement"} {
	    set ret "arpContinuousDecrement"
	}
    
    return $ret
}

#*******************************************************************************
#  Function Name : _ntgrGetStreamL3ARPSrcProtocolAddr
#
#  Description   : Called to get source protocol ip address .
#
#  Usage         : _ntgrGetStreamL3ARPSrcProtocolAddr <l3arp_header>
# 
#  Author        : jim.xie
#  
#*******************************************************************************
proc _ntgrGetStreamL3ARPSrcProtocolAddr {l3arp_header} {	
    return [keylget l3arp_header ARP_SRC_PROTOCOL_ADDR]
}

#*******************************************************************************
#  Function Name : _ntgrGetStreamL3ARPSrcProtocolCnt
#
#  Description   : Called to get count of source protocol ip address .
#
#  Usage         : _ntgrGetStreamL3ARPSrcProtocolCnt <l3arp_header>
# 
#  Author        : jim.xie
#  
#*******************************************************************************
proc _ntgrGetStreamL3ARPSrcProtocolCnt {l3arp_header} {	
    return [keylget l3arp_header ARP_SRC_PROTOCOL_CNT]
}

#*******************************************************************************
#  Function Name : _ntgrGetStreamL3ARPSrcProtocolMode
#
#  Description   : Called to get source protocol ip address mode.
#
#  Usage         : _ntgrGetStreamL3ARPSrcProtocolMode <l3arp_header>
#
#
#  Author        : jim.xie 
#*******************************************************************************
proc _ntgrGetStreamL3ARPSrcProtocolMode {l3arp_header} {	
    set ret ""
    keylget l3arp_header ARP_SRC_PROTOCOL_MODE ret
	if {$ret == "fix"} {
	    set ret "arpIdle"
	} elseif {$ret == "increment"} {
	    set ret "arpIncrement"
	} elseif {$ret == "decrement"} {
	    set ret "arpDecrement"
	} elseif {$ret == "continuousincrement"} {
	    set ret "arpContinuousIncrement"
	} elseif {$ret == "continuousdecrement"} {
	    set ret "arpContinuousDecrement"
	}
    
    return $ret
}

#*******************************************************************************
#  Function Name : _ntgrGetStreamL3ARPDstHwAddr
#
#  Description   : Called to get destination Hardware ip address .
#
#  Usage         : _ntgrGetStreamL3ARPDstHwAddr <l3arp_header>
# 
#  Author        : jim.xie
#  
#*******************************************************************************
proc _ntgrGetStreamL3ARPDstHwAddr {l3arp_header} {	
    return [keylget l3arp_header ARP_DST_HW_ADDR]
}

#*******************************************************************************
#  Function Name : _ntgrGetStreamL3ARPDstHwCnt
#
#  Description   : Called to get count of destination Hardware ip address .
#
#  Usage         : _ntgrGetStreamL3ARPDstHwCnt <l3arp_header>
# 
#  Author        : jim.xie
#  
#*******************************************************************************
proc _ntgrGetStreamL3ARPDstHwCnt {l3arp_header} {	
    return [keylget l3arp_header ARP_DST_HW_CNT]
}	

#*******************************************************************************
#  Function Name : _ntgrGetStreamL3ARPDstHwMode
#
#  Description   : Called to get destination hardware ip address mode.
#
#  Usage         : _ntgrGetStreamL3ARPDstHwMode <l3arp_header>
#
#
#  Author        : jim.xie 
#*******************************************************************************
proc _ntgrGetStreamL3ARPDstHwMode {l3arp_header} {	
    set ret ""
    keylget l3arp_header ARP_DST_HW_MODE ret
	if {$ret == "fix"} {
	    set ret "arpIdle"
	} elseif {$ret == "increment"} {
	    set ret "arpIncrement"
	} elseif {$ret == "decrement"} {
	    set ret "arpDecrement"
	} elseif {$ret == "continuousincrement"} {
	    set ret "arpContinuousIncrement"
	} elseif {$ret == "continuousdecrement"} {
	    set ret "arpContinuousDecrement"
	}
    
    return $ret
}	

#*******************************************************************************
#  Function Name : _ntgrGetStreamL3ARPDstProtocolAddr
#
#  Description   : Called to get destination protocol ip address .
#
#  Usage         : _ntgrGetStreamL3ARPDstProtocolAddr <l3arp_header>
# 
#  Author        : jim.xie
#  
#*******************************************************************************
proc _ntgrGetStreamL3ARPDstProtocolAddr {l3arp_header} {	
    return [keylget l3arp_header ARP_DST_PROTOCOL_ADDR]
}

#*******************************************************************************
#  Function Name : _ntgrGetStreamL3ARPDstProtocolCnt
#
#  Description   : Called to get count of destinaton protocol ip address .
#
#  Usage         : _ntgrGetStreamL3ARPDstProtocolCnt <l3arp_header>
# 
#  Author        : jim.xie
#  
#*******************************************************************************
proc _ntgrGetStreamL3ARPDstProtocolCnt {l3arp_header} {	
    return [keylget l3arp_header ARP_DST_PROTOCOL_CNT]
}

#*******************************************************************************
#  Function Name : _ntgrGetStreamL3ARPDstProtocolMode
#
#  Description   : Called to get destination protocol ip address mode.
#
#  Usage         : _ntgrGetStreamL3ARPDstProtocolMode <l3arp_header>
#
#
#  Author        : jim.xie 
#*******************************************************************************
proc _ntgrGetStreamL3ARPDstProtocolMode {l3arp_header} {	
    set ret ""
    keylget l3arp_header ARP_DST_PROTOCOL_MODE ret
	if {$ret == "fix"} {
	    set ret "arpIdle"
	} elseif {$ret == "increment"} {
	    set ret "arpIncrement"
	} elseif {$ret == "decrement"} {
	    set ret "arpDecrement"
	} elseif {$ret == "continuousincrement"} {
	    set ret "arpContinuousIncrement"
	} elseif {$ret == "continuousdecrement"} {
	    set ret "arpContinuousDecrement"
	}
    
    return $ret
}

#***************************** add by jim.xie for scalability case-013 end ********** 

#====================================dhcpv6 info added by Jason========================================
#*******************************************************************************
#  Function Name : _ntgrGetDHCPv6CLIENTList
#
#  Description   : Called to get emulated dhcp client list on ixia port.
#
#  Usage         : _ntgrGetDHCPv6CLIENTList <chassis_id port>
# 
#*******************************************************************************
proc _ntgrGetDHCPv6CLIENTList {chassis_id port} {
    global ntgr_trafficPortInfo_$chassis_id
    set retValue {}
    keylget ntgr_trafficPortInfo_${chassis_id}($port) HOST_DHCPv6_CLIENT_LIST retValue
    return $retValue
} 


#*******************************************************************************
#  Function Name : _ntgrGetDHCPv6CLIENTCount
#
#  Description   : Called to get dhcp host count.
#
#  Usage         : _ntgrGetDHCPv6CLIENTCount <dhcp_name>
# 
#*******************************************************************************
proc _ntgrGetDHCPv6CLIENTCount {dhcp_name} {
	  global $dhcp_name
    set retValue 0
    keylget $dhcp_name DHCPv6_CLIENT_COUNT retValue
    return $retValue
}
#*******************************************************************************
#  Function Name : _ntgrGetDHCPv6CLIENTDescription
#
#  Description   : Called to get dhcp host description.
#
#  Usage         : _ntgrGetDHCPv6CLIENTDescription <dhcp_name>
# 
#*******************************************************************************
proc _ntgrGetDHCPv6CLIENTDescription {dhcp_name} {
	  global $dhcp_name
    set retValue ""
    keylget $dhcp_name DHCPv6_CLIENT_DESCRIPTION retValue
    return $retValue
}
#*******************************************************************************
#  Function Name : _ntgrGetDHCPv6CLIENTMacAddress
#
#  Description   : Called to get dhcp host mac address.
#
#  Usage         : _ntgrGetDHCPv6CLIENTMacAddress <dhcp_name>
# 
#*******************************************************************************
proc _ntgrGetDHCPv6CLIENTMacAddress {dhcp_name} {
	  global $dhcp_name
    set retValue ""
    keylget $dhcp_name DHCPv6_CLIENT_MAC_ADDR retValue
    return $retValue
}
#*******************************************************************************
#  Function Name : _ntgrGetDHCPv6CLIENTIATYPE
#
#  Description   : Called to get dhcp host type.
#
#  Usage         : _ntgrGetDHCPv6CLIENTIATYPE <dhcp_name>
# 
#*******************************************************************************
proc _ntgrGetDHCPv6CLIENTIATYPE {dhcp_name} {
	  global $dhcp_name
    set retValue ""
    keylget $dhcp_name DHCPv6_CLIENT_IA_TYPE retValue
    return $retValue
}
#*******************************************************************************
#  Function Name : _ntgrGetDHCPv6CLIENTIAID
#
#  Description   : Called to get dhcpv6 host IA ID.
#
#  Usage         : _ntgrGetDHCPv6CLIENTIAID <dhcp_name>
# 
#*******************************************************************************
proc _ntgrGetDHCPv6CLIENTIAID {dhcp_name} {
	  global $dhcp_name
    set retValue ""
    keylget $dhcp_name DHCPv6_CLIENT_IA_ID retValue
    return $retValue
}
#*******************************************************************************
#  Function Name : _ntgrGetDHCPv6CLIENTRenewtimer
#
#  Description   : Called to get dhcpv6 Renew timer.
#
#  Usage         : _ntgrGetDHCPv6CLIENTRenewtimer <dhcp_name>
# 
#*******************************************************************************
proc _ntgrGetDHCPv6CLIENTRenewtimer {dhcp_name} {
	  global $dhcp_name
    set retValue ""
    keylget $dhcp_name DHCPv6_CLIENT_RENEW_TIMER retValue
    return $retValue
}
#*******************************************************************************
#  Function Name : _ntgrGetDHCPv6CLIENTRelayLinkAddress
#
#  Description   : Called to get dhcpv6 Relay Link Address.
#
#  Usage         : _ntgrGetDHCPv6CLIENTRelayLinkAddress <dhcp_name>
# 
#*******************************************************************************
proc _ntgrGetDHCPv6CLIENTRelayLinkAddress {dhcp_name} {
	  global $dhcp_name
    set retValue ""
    keylget $dhcp_name DHCPv6_CLIENT_RELAY_LINK_ADDRESS retValue
    return $retValue
}
#*******************************************************************************
#  Function Name : _ntgrGetDHCPv6CLIENTRelaydstaddress
#
#  Description   : Called to get dhcpv6 Relay Destination Address.
#
#  Usage         : _ntgrGetDHCPv6CLIENTRelaydstaddress <dhcp_name>
# 
#*******************************************************************************
proc _ntgrGetDHCPv6CLIENTRelaydstaddress {dhcp_name} {
	  global $dhcp_name
    set retValue ""
    keylget $dhcp_name DHCPv6_CLIENT_RELAY_DESTINATION_ADDRESS retValue
    return $retValue
}
#*******************************************************************************
#  Function Name : _ntgrGetDHCPv6CLIENTeui64id
#
#  Description   : Called to get dhcpv6 eui64id.
#
#  Usage         : _ntgrGetDHCPv6CLIENTeui64id <dhcp_name>
# 
#*******************************************************************************
proc _ntgrGetDHCPv6CLIENTeui64id {dhcp_name} {
	  global $dhcp_name
    set retValue ""
    keylget $dhcp_name DHCPv6_CLIENT_EUI64ID retValue
    return $retValue
}
#*******************************************************************************
#  Function Name : _ntgrGetDHCPv6CLIENTipv6gateway
#
#  Description   : Called to get dhcpv6 gateway.
#
#  Usage         : _ntgrGetDHCPv6CLIENTipv6gateway <dhcp_name>
# 
#*******************************************************************************
proc _ntgrGetDHCPv6CLIENTipv6gateway {dhcp_name} {
	global $dhcp_name
    set retValue ""
    keylget $dhcp_name DHCPv6_CLIENT_IPV6_GATEWAY retValue
    return $retValue
}

#*******************************************************************************
#  Function Name : _ntgrGetDHCPv6INCRVLANID
#
#  Description   : Called to get dhcpv6 incr vlan id.
#
#  Usage         : _ntgrGetDHCPv6INCRVLANID <dhcp_name>
# 
#*******************************************************************************
proc _ntgrGetDHCPv6INCRVLANID {dhcp_name} {
	global $dhcp_name
    set retValue ""
    keylget $dhcp_name DHCPv6_CLIENT_INCR_VLANID retValue
    return $retValue
}

#*******************************************************************************
#
#  Function Name	: _ntgrGetTrafficPortReceiveMode
#
#  Description    : This function is called to get the Receive Mode from 
#			  the global ntgr_trafficPortInfo_<TGName>
#  Author:   jason.li
#         
#  Usage          : _ntgrGetTrafficPortReceiveMode <chassis_id> <port> <speed>
# 
#*******************************************************************************
proc _ntgrGetTrafficPortReceiveMode {chassis_id port speed} {
	global ntgr_trafficPortInfo_$chassis_id
	set ret portRxModeWidePacketGroup
  if {$speed == 1000} {
      keylget ntgr_trafficPortInfo_${chassis_id}($port) TRAFFIC_PORT_RECEIVE_MODE ret
      if {[string tolower $ret] == "portcapture"} {
         set ret   portCapture
      }
  }
	return $ret
}

#*******************************************************************************
#
#  Function Name	: _ntgrGetTrafficPortGroupReceiveModeOffset
#
#  Description    : This function is called to get the Receive Mode from 
#			  the global ntgr_trafficPortInfo_<TGName>
#  Author:   jason.li
#         
#  Usage          : _ntgrGetTrafficPortGroupReceiveModeOffset <chassis_id> <port> <speed>
# 
#*******************************************************************************
proc _ntgrGetTrafficPortGroupReceiveModeOffset {chassis_id port} {
    global ntgr_trafficPortInfo_$chassis_id
	set ret 0
	keylget ntgr_trafficPortInfo_${chassis_id}($port) TRAFFIC_GROUP_RECEIVE_OFFSET ret
	return $ret
}

#*******************************************************************************
#
#  Function Name    :   _ntgrGetTrafficPortAdvertiseList
#
#  Description      :   This function is called to get the Advertise List from 
#			            the global ntgr_trafficPortInfo_<TGName>
#
#  Author           :   luiz.yao
#         
#  Usage            :   _ntgrGetTrafficPortAdvertiseList <chassis_id> <port>
# 
#*******************************************************************************
proc _ntgrGetTrafficPortAdvertiseList {chassis_id port} {
    global ntgr_trafficPortInfo_$chassis_id
    set ret ""
	keylget ntgr_trafficPortInfo_${chassis_id}($port) ADVERTISE_LIST ret
    set ret [string trim $ret]
	return $ret
}

#*******************************************************************************
#  Function Name : _ntgrGetMLDGROUPCOUNT
#
#  Description   : Called to get MLD group count from MLD header definition.
#
#  Usage         : _ntgrGetMLDGROUPCOUNT <mld_header>
# 
#*******************************************************************************
proc _ntgrGetMLDGROUPCOUNT {mld_header} {
    global $mld_header
    return [keylget $mld_header MLD_NUM_GROUPS]
}

#*******************************************************************************
#  Function Name : _ntgrGetMLDADDRSTART
#
#  Description   : Called to get MLD_ADDR_START from MLD header definition.
#
#  Usage         : _ntgrGetMLDADDRSTART <mld_header>
# 
#*******************************************************************************
proc _ntgrGetMLDADDRSTART {mld_header} {
    global $mld_header
    return [keylget $mld_header MLD_ADDR_START]
}

#*******************************************************************************
#  Function Name : _ntgrGetMLDADDRSTEP
#
#  Description   : Called to get MLD_ADDR_STEP from MLD header definition.
#
#  Usage         : _ntgrGetMLDADDRSTEP <mld_header>
# 
#*******************************************************************************
proc _ntgrGetMLDADDRSTEP {mld_header} {
    global $mld_header
    return [keylget $mld_header MLD_ADDR_STEP]
}
