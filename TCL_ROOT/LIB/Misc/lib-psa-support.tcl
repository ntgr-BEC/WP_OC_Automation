#!/bin/sh
################################################################################   
#
#  File Name		: lib-psa-support.tcl
#
#  Description       	:
#         This TCL contains functions commonly used by sifos psa support(PoE tester).
#
#  Revision History 	:
#         Date         Programmer        Description
#        -----------------------------------------------------------------------
#        02-Dec-2012     andy.xie       Created
#
################################################################################

#*******************************************************************************
#
#  Function Name	: _ntgrGetPGVendorName
#
#  Description    : This function is called to get the chassis vendor name from
#			  the global _ntgrGetPGVendorName
#         
#  Usage          : _ntgrGetPGVendorName <pg_id>
# 
#*******************************************************************************
proc _ntgrGetPGVendorName {pg_id} {
	global _ntgr_pgList
	return [keylget _ntgr_pgList($pg_id) CHASSIS_VENDOR]
}

#*******************************************************************************
#
#  Function Name	: _ntgrGetPGModel
#
#  Description    : This function is called to get the chassis model from
#			  the global ntgr_tgList
#         
#  Usage          : _ntgrGetPGModel <pg_id>
# 
#*******************************************************************************
proc _ntgrGetPGModel {pg_id} {
	global _ntgr_pgList
	return [keylget _ntgr_pgList($pg_id) CHASSIS_MODEL]
}

#*******************************************************************************
#
#  Function Name	: _ntgrGetPGIPAddr
#
#  Description    : This function is called to get the chassis IP address from
#			  the global _ntgr_pgList
#         
#  Usage          : _ntgrGetPGIPAddr <tg_id>
# 
#*******************************************************************************
proc _ntgrGetPGIPAddr {pg_id} {
	global _ntgr_pgList
	return [keylget _ntgr_pgList($pg_id) CHASSIS_IP_ADDR]
}

#*******************************************************************************
#
#  Function Name	: _ntgrGetPGHandle
#
#  Description    : This function is called to get the chassis handle from
#			              the global _ntgr_tgList
#         
#  Usage          : _ntgrGetPGHandle <pg_id>
# 
#*******************************************************************************
proc _ntgrGetPGHandle {pg_id} {
	global _ntgr_pgList
	return [keylget _ntgr_pgList($pg_id) CHASSIS_HANDLE]
}


proc _ntgrSetPGHandle {pg_id handle_id} {
	global _ntgr_pgList
	keylset _ntgr_pgList($pg_id) CHASSIS_HANDLE $handle_id
}


#*******************************************************************************
#
#  Function Name	: _ntgrGetPGPortChassisId
#
#  Description    : This function is called to get the chassis id from 
#			  the global ntgr_PGPortInfo_<PGName>
#         
#  Usage          : _ntgrGetPGPortChassisId <chassis_id> <port> 
# 
#*******************************************************************************
proc _ntgrGetPGPortChassisId {chassis_id port} {
	global ntgr_PGPortInfo_$chassis_id
	set port_info [keylget ntgr_PGPortInfo_${chassis_id}($port) PORT_INFO]
	return [lindex $port_info 0]
}

#*******************************************************************************
#
#  Function Name	: _ntgrGetPGPortSlotId
#
#  Description    : This function is called to get the Slot from 
#			  the global ntgr_PGPortInfo_<PGName>
#         
#  Usage          : _ntgrGetTrafficPortSlotId <chassis_id> <port> 
# 
#*******************************************************************************
proc _ntgrGetPGPortSlotId {chassis_id port} {
	global ntgr_PGPortInfo_$chassis_id
	set port_info [keylget ntgr_PGPortInfo_${chassis_id}($port) PORT_INFO]
	return [lindex $port_info 1]
}

#*******************************************************************************
#
#  Function Name	: _ntgrGetPGPortId
#
#  Description    : This function is called to get the Port from 
#			  the global ntgr_PGPortInfo_<TGName>
#         
#  Usage          : _ntgrGetPGPortId <chassis_id> <port> 
# 
#*******************************************************************************
proc _ntgrGetPGPortId {chassis_id port} {
	global ntgr_PGPortInfo_$chassis_id
	set port_info [keylget ntgr_PGPortInfo_${chassis_id}($port) PORT_INFO]
	return [lindex $port_info 2]
}

