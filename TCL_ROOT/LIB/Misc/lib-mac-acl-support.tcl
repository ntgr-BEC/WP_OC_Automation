#!/bin/sh
################################################################################   
#
#  File Name			: lib-mac-acl-support.tcl
#
#  Description			:
#         This TCL contains functions commonly used for MAC ACL.
#
#  Revision History 	:
#        Date          Programmer        Description
#        -----------------------------------------------------------------------
#        28-8-06       Nina Cheng        Created
#
#
#
#
#*******************************************************************************
#
#  Function Name	: getMACACLID
#
#  Description    : This function is used to get mac acl id list
#         
#  Usage          : getMACACLID <switch_name>
# 
#*******************************************************************************
proc getMACACLID {switch_name} {
	global ntgr_MAC_ACL_List
	return [keylget ntgr_MAC_ACL_List($switch_name) MAC_ACL_NAME_LIST]
}

#*******************************************************************************
#
#  Function Name	: getMACACLRule
#
#  Description    : This function is used to get mac acl rule list
#         
#  Usage          : getMACACLRule <switch_name>
# 
#*******************************************************************************
proc getMACACLRule {switch_name} {
	global ntgr_MAC_ACL_List
	return [keylget ntgr_MAC_ACL_List($switch_name) MAC_ACL_RULE_LIST]
}

#*******************************************************************************
#
#  Function Name	: getMACACLInterface
#
#  Description    : This function is used to get mac acl interface list
#         
#  Usage          : getMACACLInterface <switch_name>
# 
#*******************************************************************************
proc getMACACLInterface {switch_name} {
	global ntgr_MAC_ACL_List
	return [keylget ntgr_MAC_ACL_List($switch_name) MAC_ACL_INTERACE_LIST]
}
#*******************************************************************************
#
#  Function Name	: getMACACLRemark
#
#  Description    : This function is used to get mac acl remark
#         
#  Usage          : getMACACLRemark <switch_name>
# 
#*******************************************************************************
proc getMACACLRemark {switch_name} {
	global ntgr_MAC_ACL_List
	return [keylget ntgr_MAC_ACL_List($switch_name) MAC_ACL_REMARK]
}