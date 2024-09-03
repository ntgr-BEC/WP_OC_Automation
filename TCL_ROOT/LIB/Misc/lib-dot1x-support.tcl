#!/bin/sh
################################################################################   
#
#  File Name			: lib-dot1x-support.tcl
#
#  Descdot1xtion			:
#         This TCL contains functions commonly used for dot1x.
#
#  Revision History 	:
#        Date          Programmer        Descdot1xtion
#        -----------------------------------------------------------------------
#        14-May-07     Nina Cheng        Created
#
################################################################################
#*******************************************************************************
#
#  Function Name	: getdot1xGlobalStatus
#
#  Descdot1xtion    : This function is used to get dot1x global status
#         
#  Usage          : getdot1xGlobalStatus <switch_name>
# 
#*******************************************************************************
proc getdot1xGlobalStatus {switch_name} {
	global ntgr_dot1xList
	return [keylget ntgr_dot1xList($switch_name) DOT1X_GLOBAL]
}

################################################################################
#*******************************************************************************
#
#  Function Name	: getdot1xDefaultLogin
#
#  Descdot1xtion    : This function is used to get dot1x default login
#         
#  Usage          : getdot1xDefaultLogin <switch_name>
# 
#*******************************************************************************
proc getdot1xDefaultLogin {switch_name} {
	global ntgr_dot1xList
	return [keylget ntgr_dot1xList($switch_name) DOT1X_DEFAULTLOGIN]
}

################################################################################
#*******************************************************************************
#
#  Function Name	: getdot1xInterfaceList
#
#  Descdot1xtion    : This function is used to get dot1x interface list
#         
#  Usage          : getdot1xInterfaceList <switch_name>
# 
#*******************************************************************************
proc getdot1xInterfaceList {switch_name} {
	global ntgr_dot1xList
	return [keylget ntgr_dot1xList($switch_name) DOT1X_INTERFACE_LIST]
}

################################################################################
#*******************************************************************************
#
#  Function Name	: getdot1xMaxreq
#
#  Descdot1xtion    : This function is used to get dot1x max req
#         
#  Usage          : getdot1xMaxreq <IF>
# 
#*******************************************************************************
proc getdot1xMaxreq {IF} {
	global ntgr_dot1xIfProperties
	return [keylget ntgr_dot1xIfProperties($IF) DOT1X_MAX_REQ]
}

################################################################################
#*******************************************************************************
#
#  Function Name	: getdot1xPortcontrol
#
#  Descdot1xtion    : This function is used to get dot1x port control
#         
#  Usage          : getdot1xPortcontrol <IF>
# 
#*******************************************************************************
proc getdot1xPortcontrol {IF} {
	global ntgr_dot1xIfProperties
	return [keylget ntgr_dot1xIfProperties($IF) DOT1X_PORT_CONTROL]
}

################################################################################
#*******************************************************************************
#
#  Function Name	: getdot1xPortReauth
#
#  Descdot1xtion    : This function is used to get dot1x port reauth
#         
#  Usage          : getdot1xPortReauth <IF>
# 
#*******************************************************************************
proc getdot1xPortReauth {IF} {
	global ntgr_dot1xIfProperties
	return [keylget ntgr_dot1xIfProperties($IF) DOT1X_REAUTH]
}

################################################################################
#*******************************************************************************
#
#  Function Name	: getdot1xPortTimeout
#
#  Descdot1xtion    : This function is used to get dot1x port timeout
#         
#  Usage          : getdot1xPortTimeout <IF>
# 
#*******************************************************************************
proc getdot1xPortTimeout {IF} {
	global ntgr_dot1xIfProperties
	return [keylget ntgr_dot1xIfProperties($IF) DOT1X_TIMEOUT]
}

################################################################################
#*******************************************************************************
#
#  Function Name	: getdot1xPortMaxuser
#
#  Descdot1xtion    : This function is used to get dot1x max user
#         
#  Usage          : getdot1xPortMaxuser <IF>
# 
#*******************************************************************************
proc getdot1xPortMaxuser {IF} {
	global ntgr_dot1xIfProperties
	return [keylget ntgr_dot1xIfProperties($IF) DOT1X_MAXUSER]
}

################################################################################
#*******************************************************************************
#
#  Function Name	: getdot1xPortMethod
#
#  Descdot1xtion    : This function is used to get dot1x port method
#         
#  Usage          : getdot1xPortMethod <IF>
# 
#*******************************************************************************
proc getdot1xPortMethod {IF} {
	global ntgr_dot1xIfProperties
	return [keylget ntgr_dot1xIfProperties($IF) DOT1X_PORT_METHOD]
}
