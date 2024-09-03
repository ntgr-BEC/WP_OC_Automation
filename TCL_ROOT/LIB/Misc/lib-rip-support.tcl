#!/bin/sh
################################################################################   
#
#  File Name			: lib-rip-support.tcl
#
#  Description			:
#         This TCL contains functions commonly used for RIP.
#
#  Revision History 	:
#        Date          Programmer        Description
#        -----------------------------------------------------------------------
#        24-May-06     Nina Cheng        Created
#
#
#
#
################################################################################
#1*******************************************************************************
#
#  Function Name	: getRipGlobalStatus
#
#  Description    : This function is used to get rip global status
#         
#  Usage          : getRipGlobalStatus <switch_name>
# 
#*******************************************************************************
proc getRipGlobalStatus {switch_name} {
	global ntgr_RipList
	return [keylget ntgr_RipList($switch_name) RIP_GLOBAL_STATUS]
}

#2*******************************************************************************
#
#  Function Name	: getRipAutoSummary
#
#  Description    : This function is used to get rip autosummary status
#         
#  Usage          : getRipAutoSummary <switch_name>
# 
#*******************************************************************************
proc getRipAutoSummary {switch_name} {
	global ntgr_RipList
	return [keylget ntgr_RipList($switch_name) RIP_AUTO_SUMMARY]
}
#3*******************************************************************************
#
#  Function Name	: getRipDefaultInformation
#
#  Description    : This function is used to get rip default information status
#         
#  Usage          : getRipDefaultInformation <switch_name>
# 
#*******************************************************************************
proc getRipDefaultInformation {switch_name} {
	global ntgr_RipList
	return [keylget ntgr_RipList($switch_name) RIP_DEFAULT_INFORMATION]
}

#4*******************************************************************************
#
#  Function Name	: getRipHostRoutesAccept
#
#  Description    : This function is used to get rip host route accept status
#         
#  Usage          : getRipHostRoutesAccept <switch_name>
# 
#*******************************************************************************
proc getRipHostRoutesAccept {switch_name} {
	global ntgr_RipList
	return [keylget ntgr_RipList($switch_name) RIP_HOSTROUTESACCEPT]
}

#5*******************************************************************************
#
#  Function Name	: getRipDefaultMetric
#
#  Description    : This function is used to get rip default metric of switch
#         
#  Usage          : getRipDefaultMetric <switch_name>
# 
#*******************************************************************************
proc getRipDefaultMetric {switch_name} {
	global ntgr_RipList
	return [keylget ntgr_RipList($switch_name) RIP_DEFAULT_METRIC]
}

#6*******************************************************************************
#
#  Function Name	: getRipDistance
#
#  Description    : This function is used to get rip distance
#         
#  Usage          : getRipDistance <switch_name>
# 
#*******************************************************************************
proc getRipDistance {switch_name} {
	global ntgr_RipList
	return [keylget ntgr_RipList($switch_name) RIP_DISTANCE]
}
#7*******************************************************************************
#
#  Function Name	: getRipSplitHorizon
#
#  Description    : This function is used to get rip slipt horizon mode
#         
#  Usage          : getRipSplitHorizon <switch_name>
# 
#*******************************************************************************
proc getRipSplitHorizon {switch_name} {
	global ntgr_RipList
	return [keylget ntgr_RipList($switch_name) RIP_SPLIT_HORIZON]
}

#8*******************************************************************************
#
#  Function Name	: getRipDistributeListOut
#
#  Description    : This function is used to get distribute list of RIP
#         
#  Usage          : getRipDistributeListOut <switch_name>
# 
#*******************************************************************************
proc getRipDistributeListOut {switch_name} {
	global ntgr_RipList
	return [keylget ntgr_RipList($switch_name) RIP_DISTRIBUTE_LIST_OUT]
}

#9*******************************************************************************
#
#  Function Name	: getRipRedistribute
#
#  Description    : This function is used to get rip redistribute status
#         
#  Usage          : getRipRedistribute <switch_name>
# 
#*******************************************************************************
proc getRipRedistribute {switch_name} {
	global ntgr_RipList
	return [keylget ntgr_RipList($switch_name) RIP_REDISTRIBUTE]
}

#10*******************************************************************************
#
#  Function Name	: getRipInterface
#
#  Description    : This function is used to get interface to be configured RIP
#         
#  Usage          : getRipInterface <switch_name>
# 
#*******************************************************************************
proc getRipInterface {switch_name} {
	global ntgr_RipList
	return [keylget ntgr_RipList($switch_name) RIP_INTERFACE]
}

#11*******************************************************************************
#
#  Function Name	: getRipAuthforInterface
#
#  Description    : This function is used to get authentication for interface
#         
#  Usage          : getRipAuthforInterface <IF_Property>
# 
#*******************************************************************************
proc getRipAuthforInterface {IF_Property} {
	global ntgr_RipInterfaceProperty
	return [keylget ntgr_RipInterfaceProperty($IF_Property) RIP_AUTHENTICATION]
}


#12*******************************************************************************
#
#  Function Name	: getRipSendVerforInterface
#
#  Description    : This function is used to get send version for interface
#         
#  Usage          : getRipSendVerforInterface <IF_Property>
# 
#*******************************************************************************
proc getRipSendVerforInterface {IF_Property} {
	global ntgr_RipInterfaceProperty
	return [keylget ntgr_RipInterfaceProperty($IF_Property) RIP_SENDVERSION]
}

#13*******************************************************************************
#
#  Function Name	: getRipReveiVerforInterface
#
#  Description    : This function is used to get receive version for interface
#         
#  Usage          : getRipReveiVerforInterface <IF_Property>
# 
#*******************************************************************************
proc getRipReveiVerforInterface {IF_Property} {
	global ntgr_RipInterfaceProperty
	return [keylget ntgr_RipInterfaceProperty($IF_Property) RIP_RECEIVEVERSION]
}