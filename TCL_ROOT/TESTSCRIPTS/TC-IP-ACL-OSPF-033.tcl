#!/bin/sh
################################################################################   
#
#  File Name   : TC-IP-ACL-OSPF-033.tcl
#
#  Description :
#         This TCL contains APIs to configure IP ACL for OSPF on the switches.
#
#  Revision History 	:
#        Date         Programmer        Description
#        -----------------------------------------------------------------------
#        18/9/06      Nina Cheng        Created
#
#
#
#
################################################################################

#*************************  Start of Test case  ********************************

Netgear_log_file "TC-IP-ACL-OSPF-033.tcl" "********** Starting Test case TC-IP-ACL-OSPF-033.tcl **********"

Netgear_log_file "TC-IP-ACL-OSPF-033.tcl" "Started IP ACL Configuration on the Switches"

for_array_keys switch_name ntgr_IP_ACL_List {

  set ip_acl_rule_list [ getIPACLRule $switch_name]
  
  Netgear_log_file "TC-IP-ACL-OSPF-033.tcl" "Started Configuration of IP ACL on switch $switch_name"
  CALAddIPACL $switch_name $ip_acl_rule_list

}

Netgear_log_file "TC-IP-ACL-OSPF-033.tcl" "Completed IP ACL Configuration on the Switch $switch_name"

Netgear_log_file "TC-IP-ACL-OSPF-033.tcl" "********** Completed Test case TC-IP-ACL-OSPF-033.tcl **********"

#*************************  End of Test case  *************************************