#!/bin/sh
################################################################################   
#
#  File Name			: lib-ip-acl-support.tcl
#
#  Description			:
#         This TCL contains functions commonly used for IP ACL.
#
#  Revision History 	:
#        Date          Programmer        Description
#        -----------------------------------------------------------------------
#        28-8-06       Nina Cheng        Created
#
#
#
#
#1*******************************************************************************
#
#  Function Name	: getIPACLID
#
#  Description    : This function is used to get ip acl id list
#         
#  Usage          : getIPACLID <switch_name>
# 
#*******************************************************************************
proc getIPACLID {switch_name} {
	global ntgr_IP_ACL_List
	return [keylget ntgr_IP_ACL_List($switch_name) IP_ACL_ID_LIST]
}

#2*******************************************************************************
#
#  Function Name	: getIPACLRule
#
#  Description    : This function is used to get ip acl rule list
#         
#  Usage          : getIPACLRule <switch_name>
# 
#*******************************************************************************
proc getIPACLRule {switch_name} {
	global ntgr_IP_ACL_List
	return [keylget ntgr_IP_ACL_List($switch_name) IP_ACL_RULE_LIST]
}

#3*******************************************************************************
#
#  Function Name	: getIPACLInterface
#
#  Description    : This function is used to get ip acl interface list
#         
#  Usage          : getIPACLInterface <switch_name>
# 
#*******************************************************************************
proc getIPACLInterface {switch_name} {
	global ntgr_IP_ACL_List
	return [keylget ntgr_IP_ACL_List($switch_name) IP_ACL_INTERFACE_LIST]
}
#4*******************************************************************************
#
#  Function Name	: getIPACLRemark
#
#  Description    : This function is used to get ip acl remark 
#         
#  Usage          : getIPACLRemark <switch_name>
# 
#*******************************************************************************
proc getIPACLRemark {switch_name} {
	global ntgr_IP_ACL_List
	return [keylget ntgr_IP_ACL_List($switch_name) IP_ACL_REMARK]
}