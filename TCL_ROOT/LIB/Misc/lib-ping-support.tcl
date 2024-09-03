#!/bin/sh
################################################################################   
#
#  File Name			: lib-ping-support.tcl
#
#  Description			:
#         This TCL contains functions commonly used for ping.
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
#  Function Name	: _getPingIPAddr
#
#  Description    : This function is called to get the ip address from 
#	                the global ntgr_pingList
#         
#  Usage          : _getPingIPAddr <switch>
# 
#*******************************************************************************
proc _getPingIPAddr {switch} {
	global ntgr_pingList
	return [keylget ntgr_pingList($switch) PING_IPADDR]
}


#*******************************************************************************
#
#  Function Name	: _getPingCount
#
#  Description    : This function is called to get the count from 
#	                the global ntgr_pingList
#         
#  Usage          : _getPingCount <switch>
# 
#*******************************************************************************
proc _getPingCount {switch} {
	global ntgr_pingList
    set count ""
	keylget ntgr_pingList($switch) PING_COUNT count
	return $count	
}


#*******************************************************************************
#
#  Function Name	: _getPingInterval
#
#  Description    : This function is called to get the interval from 
#	                the global ntgr_pingList
#         
#  Usage          : _getPingInterval <switch>
# 
#*******************************************************************************
proc _getPingInterval {switch} {
	global ntgr_pingList
	set interval ""
	keylget ntgr_pingList($switch) PING_INTERVAL interval
	return $interval
}


#*******************************************************************************
#
#  Function Name	: _getPingSize
#
#  Description    : This function is called to get the size from 
#	                the global ntgr_pingList
#         
#  Usage          : _getPingSize <switch>
# 
#*******************************************************************************
proc _getPingSize {switch} {
	global ntgr_pingList
	set size ""
	keylget ntgr_pingList($switch) PING_SIZE size
	return $size
}

#*******************************************************************************
#
#  Function Name	: _getPingSource
#
#  Description    : This function is called to get the source from 
#	                the global ntgr_pingList
#         
#  Usage          : _getPingSource <switch>
# 
#*******************************************************************************
proc _getPingSource {switch} {
	global ntgr_pingList
	set source_adr ""
	keylget ntgr_pingList($switch) PING_SOURCE source_adr
	return $source_adr
}



#*******************************************************************************
#
#  Function Name	: _getPingIPv6IPAddr
#
#  Description    : This function is called to get the ip address from 
#	                the global ntgr_pingIPv6List
#         
#  Usage          : _getPingIPv6IPAddr <switch>
# 
#*******************************************************************************
proc _getPingIPv6IPAddr {switch} {
	global ntgr_pingIPv6List
	return [keylget ntgr_pingIPv6List($switch) PING_IPADDR]
}

#*******************************************************************************
#
#  Function Name	: _getPingIPv6Size
#
#  Description    : This function is called to get the size from 
#	                the global ntgr_pingIPv6List
#         
#  Usage          : _getPingIPv6Size <switch>
# 
#*******************************************************************************
proc _getPingIPv6Size {switch} {
	global ntgr_pingIPv6List
	set size ""
	keylget ntgr_pingIPv6List($switch) PING_SIZE size
	return $size
}

#*******************************************************************************
#
#  Function Name	: _getPingIPv6Intferface
#
#  Description    : This function is called to get the interface from 
#	                the global ntgr_pingIPv6List
#         
#  Usage          : _getPingIPv6Intferface <switch>
# 
#*******************************************************************************
proc _getPingIPv6Intferface {switch} {
	global ntgr_pingIPv6List
	set intf ""
	keylget ntgr_pingIPv6List($switch) PING_INTERFACE intf
	return $intf
}

#*******************************************************************************
#
#  Function Name	: _getPingIPv6Source
#
#  Description    : This function is called to get the source_adr from 
#	                the global ntgr_pingIPv6List
#         
#  Usage          : _getPingIPv6Source <switch>
# 
#*******************************************************************************
proc _getPingIPv6Source {switch} {
    global ntgr_pingIPv6List
    set source_adr ""
    keylget ntgr_pingIPv6List($switch) PING_SOURCE source_adr
    return $source_adr
}

#*******************************************************************************
#
#  Function Name	: _getPingIPv6Count
#
#  Description    : This function is called to get the count from 
#	                the global ntgr_pingIPv6List
#         
#  Usage          : _getPingIPv6Count <switch>
# 
#*******************************************************************************
proc _getPingIPv6Count {switch} {
    global ntgr_pingIPv6List
    set count ""
    keylget ntgr_pingIPv6List($switch) PING_COUNT count
    return $count
}

#*******************************************************************************
#
#  Function Name	: _getPingIPv6Interval
#
#  Description    : This function is called to get the interval from 
#	                the global ntgr_pingIPv6List
#         
#  Usage          : _getPingIPv6Interval <switch>
# 
#*******************************************************************************
proc _getPingIPv6Interval {switch} {
    global ntgr_pingIPv6List
    set interval ""
    keylget ntgr_pingIPv6List($switch) PING_INTERVAL interval
    return $interval
}
