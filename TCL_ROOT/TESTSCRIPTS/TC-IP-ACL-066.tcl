#!/bin/sh
################################################################################   
#
#  File Name   : TC-IP-ACL-066.tcl
#
#  Description :
#         This TCL contains APIs to configure IP ACL on the switches.
#
#  Revision History 	:
#        Date         Programmer        Description
#        -----------------------------------------------------------------------
#        28/8/06      Nina Cheng        Created
#
#
#
#
################################################################################

#*************************  Start of Test case  ********************************

Netgear_log_file "TC-IP-ACL-066.tcl" "*************** Starting Test case TC-IP-ACL-066.tcl ********************"

Netgear_log_file "TC-IP-ACL-066.tcl" "Delete IP ACL Configuration on the Switches"


for_array_keys switch_name ntgr_IP_ACL_List {
  
  set ip_acl_id_list [getIPACLID $switch_name]
  set ip_acl_rule_list [ getIPACLRule $switch_name]
  set ip_acl_interface_list [getIPACLInterface $switch_name]
   
  Netgear_log_file "TC-IP-ACL-066.tcl" "Started delete of IP ACL on interface"
  CALDisableIPACLOninterface $switch_name $ip_acl_interface_list
  Netgear_log_file "TC-IP-ACL-066.tcl" "Started delete of IP ACL on switch $switch_name"
  CALDeleteIPACL $switch_name $ip_acl_id_list   
}

Netgear_log_file "TC-IP-ACL-066.tcl" "Completed IP ACL Configuration on the Switch $switch_name"

Netgear_log_file "TC-IP-ACL-066.tcl" "************* Completed Test case TC-IP-ACL-066.tcl ********************"

#*************************  End of Test case  ****************************************************************