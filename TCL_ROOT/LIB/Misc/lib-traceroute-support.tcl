#!/bin/sh
################################################################################   
#
#  File Name			: lib-traceroute-support.tcl
#
#  Description			:
#         This TCL contains functions commonly used for traceroute.
#
#  Revision History 	:
#        Date          Programmer        Description
#        -----------------------------------------------------------------------
#        21-Apr-11     Tony              Created
#
#
#
#
################################################################################

#*******************************************************************************
#
#  Function Name	: _getTraceRouteIPAddr
#
#  Description    : This function is called to get the ip address from 
#	                the global ntgr_tracerouteList
#         
#  Usage          : _getTraceRouteIPAddr <switch>
# 
#*******************************************************************************
proc _getTraceRouteIPAddr {switch} {
	global ntgr_tracerouteList
	return [keylget ntgr_tracerouteList($switch) TRACEROUTE_IPADDR]
}


#*******************************************************************************
#
#  Function Name	: _getTraceRouteCount
#
#  Description    : This function is called to get the count from 
#	                the global ntgr_tracerouteList
#         
#  Usage          : _getTraceRouteCount <switch>
# 
#*******************************************************************************
proc _getTraceRouteCount {switch} {
	global ntgr_tracerouteList
	set count ""
    keylget ntgr_tracerouteList($switch) TRACEROUTE_COUNT count
    return $count
}

#*******************************************************************************
#
#  Function Name	: _getTraceRouteIPType
#
#  Description    : This function is called to get the count from 
#	                the global ntgr_tracerouteList
#         
#  Usage          : _getTraceRouteIPType <switch>
# 
#*******************************************************************************
proc _getTraceRouteIPType {switch} {
	global ntgr_tracerouteList
	set iptype ""
    keylget ntgr_tracerouteList($switch) TRACEROUTE_IPTYPE iptype
    return $iptype
}

#*******************************************************************************
#
#  Function Name	: _getTraceRouteInterval
#
#  Description    : This function is called to get the interval from 
#	                the global ntgr_tracerouteList
#         
#  Usage          : _getTraceRouteInterval <switch>
# 
#*******************************************************************************
proc _getTraceRouteInterval {switch} {
	global ntgr_tracerouteList
	set interval ""
    keylget ntgr_tracerouteList($switch) TRACEROUTE_INTERVAL interval
    return $interval
}


#*******************************************************************************
#
#  Function Name	: _getTraceRouteInitTtl
#
#  Description    : This function is called to get the initttl from 
#	                the global ntgr_tracerouteList
#         
#  Usage          : _getTraceRouteInitTtl <switch>
# 
#*******************************************************************************
proc _getTraceRouteInitTtl {switch} {
	global ntgr_tracerouteList
	set initttl ""
    keylget ntgr_tracerouteList($switch) TRACEROUTE_INITTTL initttl
    return $initttl
}


#*******************************************************************************
#
#  Function Name	: _getTraceRouteMaxTtl
#
#  Description    : This function is called to get the maxttl from 
#	                the global ntgr_tracerouteList
#         
#  Usage          : _getTraceRouteMaxTtl <switch>
# 
#*******************************************************************************
proc _getTraceRouteMaxTtl {switch} {
	global ntgr_tracerouteList
	set maxttl ""
    keylget ntgr_tracerouteList($switch) TRACEROUTE_MAXTTL maxttl
    return $maxttl
}


#*******************************************************************************
#
#  Function Name	: _getTraceRouteMaxFail
#
#  Description    : This function is called to get the maxfail from 
#	                the global ntgr_tracerouteList
#         
#  Usage          : _getTraceRouteMaxFail <switch>
# 
#*******************************************************************************
proc _getTraceRouteMaxFail {switch} {
	global ntgr_tracerouteList
	set maxfail ""
    keylget ntgr_tracerouteList($switch) TRACEROUTE_MAXFAIL maxfail
    return $maxfail
}


#*******************************************************************************
#
#  Function Name	: _getTraceRoutePort
#
#  Description    : This function is called to get the port from 
#	                the global ntgr_tracerouteList
#         
#  Usage          : _getTraceRoutePort <switch>
# 
#*******************************************************************************
proc _getTraceRoutePort {switch} {
	global ntgr_tracerouteList
	set port ""
    keylget ntgr_tracerouteList($switch) TRACEROUTE_PORT port
    return $port
}


#*******************************************************************************
#
#  Function Name	: _getTraceRouteSize
#
#  Description    : This function is called to get the size from 
#	                the global ntgr_tracerouteList
#         
#  Usage          : _getTraceRouteSize <switch>
# 
#*******************************************************************************
proc _getTraceRouteSize {switch} {
	global ntgr_tracerouteList
	set size ""
    keylget ntgr_tracerouteList($switch) TRACEROUTE_SIZE size
    return $size
}


#*******************************************************************************
#
#  Function Name	: _getTraceRouteCheckIP
#
#  Description    : This function is called to get the checked ip address from 
#	                the global ntgr_tracerouteList
#         
#  Usage          : _getTraceRouteCheckIP <switch>
# 
#*******************************************************************************
proc _getTraceRouteCheckIP {switch} {
	global ntgr_tracerouteList
	set ip ""
    keylget ntgr_tracerouteList($switch) TRACEROUTE_CHECKIP ip
    return $ip
}

#*******************************************************************************
#
#  Function Name	: _getTraceRouteSource
#
#  Description    : This function is called to get the size from 
#	                the global ntgr_tracerouteList
#         
#  Usage          : _getTraceRouteSource <switch>
# 
#*******************************************************************************
proc _getTraceRouteSource {switch} {
	global ntgr_tracerouteList
	set source ""
    keylget ntgr_tracerouteList($switch) TRACEROUTE_SOURCE source
    return $source
}

