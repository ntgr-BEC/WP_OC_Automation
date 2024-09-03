################################################################################
#
#  File Name		: TC-L3-ACL-FILTER-DISABLE-083.tcl
#
#  Description       	: This test disable ACL filter and delete ACL configuration.
#	
#  Config File          : TC-L3-ACL-FILTER-DISABLE-083.cfg
#
#  Global Variables	: ntgr_AclFilterDutList
#				  
#  Revision History 	:
#         Date         Programmer          Description
#        -----------------------------------------------------------------------
#
################################################################################

#*************************  Start of Test case  ********************************
NtgrDumpLog  $NTGR_LOG_TERSE  "TC-L3-ACL-FILTER-DISABLE-083.tcl" "******************** Starting Test case TC-L3-ACL-FILTER-DISABLE-083.tcl ********************"
foreach switch_name $ntgr_AclFilterDutList {
    set ip_acl_id_list [getIPACLID $switch_name]
    set ip_acl_rule_list [ getIPACLRule $switch_name]
    set ip_acl_interface_list [getIPACLInterface $switch_name]

    NtgrDumpLog $NTGR_LOG_TERSE "TC-L3-ACL-FILTER-DISABLE-083.tcl" "Start disable ACL filter from interface on $switch_name"
    CALDisableIPACLOninterface $switch_name $ip_acl_interface_list
    NtgrDumpLog $NTGR_LOG_TERSE "TC-L3-ACL-FILTER-DISABLE-083.tcl" "Deleting IP ACL on switch $switch_name"
    CALDeleteIPACL $switch_name $ip_acl_id_list
}
NtgrDumpLog  $NTGR_LOG_TERSE  "TC-L3-ACL-FILTER-DISABLE-083.tcl" "******************** Complete Test case TC-L3-ACL-FILTER-DISABLE-083.tcl ********************"
#*************************  End of Test case  ****************************************************************