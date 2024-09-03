#!/bin/sh
################################################################################   
#
#  File Name			: lib-vrrp-support.tcl
#
#  Description			:
#         This TCL contains functions commonly used for VRRP.
#
#  Revision History 	:
#        Date          Programmer        Description
#        -----------------------------------------------------------------------
#        18-8-06       Nina Cheng        Created
#
#
#
#
#1*******************************************************************************
#
#  Function Name	: getVrrpVID
#
#  Description    : This function is used to get virtual id of the VRRP
#         
#  Usage          : getVrrpVID <switch_name>
# 
#*******************************************************************************
proc getVrrpVID {switch_name} {
	global ntgr_VrrpList
	return [keylget ntgr_VrrpList($switch_name) VRRP_VIRTUAL_ID_LIST]
}

#2*******************************************************************************
#
#  Function Name	: getVrrpPreemptMode
#
#  Description    : This function is used to get preempt mode of the VRRP
#         
#  Usage          : getVrrpPreemptMode <switch_name>
# 
#*******************************************************************************
proc getVrrpPreemptMode {switch_name} {
	global ntgr_VrrpList
	return [keylget ntgr_VrrpList($switch_name) VRRP_PREEMPT_LIST]
}

#3*******************************************************************************
#
#  Function Name	: getVrrpPriority
#
#  Description    : This function is used to get priority value of the VRRP
#         
#  Usage          : getVrrpPriority <switch_name>
# 
#*******************************************************************************
proc getVrrpPriority {switch_name} {
	global ntgr_VrrpList
	return [keylget ntgr_VrrpList($switch_name) VRRP_PRIORITY_LIST]
}

#4*******************************************************************************
#
#  Function Name	: getVrrpInterface
#
#  Description    : This function is used to get interface to be configured VRRP
#         
#  Usage          : getVrrpInterface <switch_name>
# 
#*******************************************************************************
proc getVrrpInterface {switch_name} {
	global ntgr_VrrpList
	return [keylget ntgr_VrrpList($switch_name) VRRP_INTERFACE_LIST]
}

#5*******************************************************************************
#
#  Function Name	: getVrrpTimerAdvertise
#
#  Description    : This function is used to get timers for master to advertise VRRP
#         
#  Usage          : getVrrpTimerAdvertise <switch_name>
# 
#*******************************************************************************
proc getVrrpTimerAdvertise {switch_name} {
	global ntgr_VrrpList
	return [keylget ntgr_VrrpList($switch_name) VRRP_ADVERTISE_TIME_LIST]
}


#6*******************************************************************************
#
#  Function Name	: getVrrpAuthenticationMode
#
#  Description    : This function is used to get authentication mode of VRRP
#         
#  Usage          : getVrrpAuthenticationMode <switch_name>
# 
#*******************************************************************************
proc getVrrpAuthenticationMode {switch_name} {
	global ntgr_VrrpList
	return [keylget ntgr_VrrpList($switch_name) VRRP_AUTHENTICATION_LIST]
}

#7*******************************************************************************
#
#  Function Name	: getVrrpVIP
#
#  Description    : This function is used to get virtual IP of VRRP
#         
#  Usage          : getVrrpVIP <switch_name>
# 
#*******************************************************************************
proc getVrrpVIP {switch_name} {
	global ntgr_VrrpList
	return [keylget ntgr_VrrpList($switch_name) VRRP_VIRTUAL_IP_LIST]
}