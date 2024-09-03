#!/bin/sh
################################################################################   
#
#  File Name			: lib-stp-support.tcl
#
#  Description			:
#         This TCL contains functions commonly used for spanning-tree.
#
#  Revision History 	:
#  Revision   Date         Programmer        Description
#  -------------------------------------------------------------------------------
#  1.0       18-May-06     Scott Zhang       Created
# 
#  1.1      27-June-06     Selva Kumar     1. New procedures getMstpInstanceList,
#                                          getMstpInstancePriorityList, 
#                                          getMstpInstanceVlanList and 
#                                          getMstpInstancePortAndCost added to fetch 
#                                          elements from MSTP data sturecture.
#
#
################################################################################

#*******************************************************************************
#
#  Function Name	: getStpBridgePriority
#
#  Description    : This function is used to get bridge's priority for
#                   spanning-tree
#         
#  Usage          : getStpBridgePriority <switch_name>
# 
#*******************************************************************************
proc getStpBridgePriority {switch_name} {
	global ntgr_stpList
	return [keylget ntgr_stpList($switch_name) STP_BRIDGE_PRIORITY]
}

#*******************************************************************************
#
#  Function Name	: getStpPortList
#
#  Description    : This function is used to get bridge's portlist for
#                   spanning-tree
#         
#  Usage          : getStpPortList <switch_name>
# 
#*******************************************************************************
proc getStpPortList {switch_name} {
	global ntgr_stpList
	return [keylget ntgr_stpList($switch_name) STP_PORT_LIST]
}

#*******************************************************************************
#
#  Function Name	: getStpPortCostList
#
#  Description    : This function is used to get bridge ports' cost list for
#                   spanning-tree
#         
#  Usage          : getStpPortCostList <switch_name>
# 
#*******************************************************************************
proc getStpPortCostList {switch_name} {
	global ntgr_stpList
	return [keylget ntgr_stpList($switch_name) STP_PORT_COST_LIST]
}

#*******************************************************************************
#
#  Function Name	: getStpFwdTime
#
#  Description    : This function is used to get bridge's forward delay for
#                   spanning-tree
#         
#  Usage          : getStpFwdTime <switch_name>
# 
#*******************************************************************************
proc getStpFwdTime {switch_name} {
	global ntgr_stpList
	return [keylget ntgr_stpList($switch_name) STP_BRIDGE_FWD_TIME]
}

#*******************************************************************************
#
#  Function Name	: getStpMaxAge
#
#  Description    : This function is used to get bridge's max age for
#                   spanning-tree
#         
#  Usage          : getStpMaxAge <switch_name>
# 
#*******************************************************************************
proc getStpMaxAge {switch_name} {
	global ntgr_stpList
	return [keylget ntgr_stpList($switch_name) STP_BRIDGE_MAX_AGE]
}

#*******************************************************************************
#
#  Function Name	: getStpHelloTime
#
#  Description    : This function is used to get bridge's hello interval for
#                   spanning-tree
#         
#  Usage          : getStpHelloTime <switch_name>
# 
#*******************************************************************************
proc getStpHelloTime {switch_name} {
	global ntgr_stpList
	return [keylget ntgr_stpList($switch_name) STP_BRIDGE_HELLO_TIME]
}

#*******************************************************************************
#
#  Function Name	: getMstpInstanceList
#
#  Description    : This function is used to get Mutliple spanning instances 
#                   to be added for a switch. 
#         
#  Usage          : getMstpInstanceList <switch_name>
# 
#*******************************************************************************
proc getMstpInstanceList {switch_name} {
	global ntgr_mstpList
	return [keylget ntgr_mstpList($switch_name) MSTP_INSTANCE_LIST]
}

#*******************************************************************************
#
#  Function Name	: getMstpInstancePriorityList
#
#  Description    : This function is used to get priority of each MSTP instance in 
#                   a switch.
#         
#  Usage          : getMstpInstancePriorityList <switch_name>
# 
#*******************************************************************************
proc getMstpInstancePriorityList {switch_name} {
    global ntgr_mstpList
    return [keylget ntgr_mstpList($switch_name) MSTP_INSTANCE_PRIORITY_LIST ]
}

#*******************************************************************************
#
#  Function Name	: getMstpInstanceVlanList
#
#  Description    : This function is used to get VLAN ids of each MSTP instance 
#                   in a switch.
#         
#  Usage          : getMstpInstanceVlanList <switch_name>
# 
#*******************************************************************************
proc getMstpInstanceVlanList {switch_name} {
    global ntgr_mstpList
    return [keylget ntgr_mstpList($switch_name) MSTP_INSTANCE_VLAN_LIST ]
}

#*******************************************************************************
#
#  Function Name	: getMstpInstancePortAndCost
#
#  Description    : This function is used to get the Ports and their respective 
#                   costs of each MSTP instance in a switch.
#         
#  Usage          : getMstpInstancePortAndCost <switch_name>
# 
#*******************************************************************************
proc getMstpInstancePortAndCost {switch_name} {
    global ntgr_mstpList
    return [keylget ntgr_mstpList($switch_name) MSTP_INSTANCE_PORT_AND_COST ]
}
