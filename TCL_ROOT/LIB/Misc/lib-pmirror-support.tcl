#!/bin/sh
################################################################################   
#
#  File Name		: lib-pmirror-support.tcl
#
#  Description       	:
#         This TCL contains functions commonly used by Port Mirroring
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
#  Function Name	: _ntgrGetPortMirrorStatus
#
#  Description    : This function is called to get the global status from
#			  the global ntgr_portMirrorList
#         
#  Usage          : _ntgrGetPortMirrorStatus <switch>
# 
#*******************************************************************************
proc _ntgrGetPortMirrorStatus {switch} {
	global ntgr_portMirrorList
	return [keylget ntgr_portMirrorList($switch) PORT_MIRROR_STATUS]
}

#*******************************************************************************
#
#  Function Name	: _ntgrGetPortMirrorProbePort
#
#  Description    : This function is called to get the probe port from
#			  the global ntgr_portMirrorList
#         
#  Usage          : _ntgrGetPortMirrorProbePort <switch>
# 
#*******************************************************************************
proc _ntgrGetPortMirrorProbePort {switch} {
	global ntgr_portMirrorList
	return [keylget ntgr_portMirrorList($switch) PORT_MIRROR_PROBE_PORT]
}

#*******************************************************************************
#
#  Function Name	: _ntgrGetPortMirrorAddList 
#
#  Description    : This function is called to get the mirror port list from
#			  the global ntgr_portMirrorList
#         
#  Usage          : _ntgrGetPortMirrorAddList <switch>
# 
#*******************************************************************************
proc _ntgrGetPortMirrorAddList {switch} {
	global ntgr_portMirrorList
	return [keylget ntgr_portMirrorList($switch) PORT_MIRROR_ADD_LIST]
}

#*******************************************************************************
#
#  Function Name	: _ntgrGetPortMirrorDelList 
#
#  Description    : This function is called to get the mirror port list from
#			  the global ntgr_portMirrorList
#         
#  Usage          : _ntgrGetPortMirrorDelList <switch>
# 
#*******************************************************************************
proc _ntgrGetPortMirrorDelList {switch} {
	global ntgr_portMirrorList
	return [keylget ntgr_portMirrorList($switch) PORT_MIRROR_DEL_LIST]
}