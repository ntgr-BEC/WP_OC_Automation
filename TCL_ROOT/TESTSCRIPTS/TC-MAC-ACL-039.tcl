#!/bin/sh
################################################################################   
#
#  File Name   : TC-MAC-ACL-039.tcl
#
#  Description :
#         This TCL contains APIs to configure MAC ACL on the switches.
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

Netgear_log_file "TC-MAC-ACL-039.tcl" "************ Starting Test case TC-MAC-ACL-039.tcl ****************"

Netgear_log_file "TC-MAC-ACL-039.tcl" "Started MAC ACL Configuration on the Switches"

for_array_keys switch_name ntgr_MAC_ACL_List {
  
  set mac_acl_name_list [getMACACLID $switch_name]
  set mac_acl_rule_list [getMACACLRule $switch_name]
  set mac_acl_interface_list [getMACACLInterface $switch_name]
  
  Netgear_log_file "TC-MAC-ACL-039.tcl" "Started Configuration of MAC ACL on switch $switch_name"
  CALAddMacACL  $switch_name $mac_acl_rule_list
  Netgear_log_file "TC-MAC-ACL-039.tcl" "Started Enable of MAC ACL on interface"
  CALInterfaceMACACLEnable $switch_name $mac_acl_interface_list
  
  Netgear_log_file "TC-MAC-ACL-039.tcl" "Started delete of MAC ACL on interface"
  CALInterfaceMACACLDisable $switch_name $mac_acl_interface_list
  Netgear_log_file "TC-MAC-ACL-039.tcl" "Started delete of MAC ACL on switch $switch_name"
  CALDeleteMacACL  $switch_name $mac_acl_name_list
}

Netgear_log_file "TC-MAC-ACL-039.tcl" "Completed ACL Configuration on the Switch $switch_name"

Netgear_log_file "TC-MAC-ACL-039.tcl" "*************** Completed Test case TC-MAC-ACL-039.tcl *****************"

#*************************  End of Test case  ****************************************************************
