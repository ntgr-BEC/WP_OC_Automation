################################################################################
#
#  File Name	         : TC-L3-ACL-FILTER-ENABLE-082.tcl
#
#  Description       	: This test configure acl to filter traffic.
#	
#  Config File          : TC-L3-ACL-FILTER-ENABLE-082.cfg
#
#  Global Variables	: ntgr_AclFilterDutList
#				  
#  Revision History 	:
#         Date         Programmer          Description
#        -----------------------------------------------------------------------
#
################################################################################

#*************************  Start of Test case  ********************************
NtgrDumpLog  $NTGR_LOG_TERSE  "TC-L3-ACL-FILTER-ENABLE-082.tcl" "******************** Starting Test case TC-L3-ACL-FILTER-ENABLE-082.tcl ********************"
foreach switch_name $ntgr_AclFilterDutList {
    set ip_acl_id_list [getIPACLID $switch_name]
    set ip_acl_rule_list [ getIPACLRule $switch_name]
    set ip_acl_interface_list [getIPACLInterface $switch_name]
    
    NtgrDumpLog  $NTGR_LOG_TERSE  "TC-L3-ACL-FILTER-ENABLE-082.tcl" "Starting configuration of IP ACL on switch $switch_name"
    CALAddIPACL $switch_name $ip_acl_rule_list
    NtgrDumpLog  $NTGR_LOG_TERSE  "TC-L3-ACL-FILTER-ENABLE-082.tcl" "Enable of IP ACL on interface to filter traffic"
    CALEnableIPACLOninterface $switch_name $ip_acl_interface_list
}
NtgrDumpLog  $NTGR_LOG_TERSE  "TC-L3-ACL-FILTER-ENABLE-082.tcl" "******************** Complete Test case TC-L3-ACL-FILTER-ENABLE-082.tcl ********************"
#*************************  End of Test case  ****************************************************************