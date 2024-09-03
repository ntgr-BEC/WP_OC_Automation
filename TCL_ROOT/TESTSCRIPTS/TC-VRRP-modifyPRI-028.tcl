#!/bin/sh
################################################################################   
#
#  File Name   : TC-VRRP-028.tcl
#
#  Description :
#         This TCL contains APIs to configure VRRP on the switches.
#
#  Revision History 	:
#        Date         Programmer        Description
#        -----------------------------------------------------------------------
#        21/8/06      Nina Cheng        Created
#
#
#
#
################################################################################

#*************************  Start of Test case  ********************************

Netgear_log_file "TC-VRRP-028.tcl" "******************** Starting Test case TC-VRRP-028.tcl ********************"

Netgear_log_file "TC-VRRP-028.tcl" "Started VRRP Configuration on the Switches"

for_array_keys switch_name ntgr_VrrpList {
  
  Netgear_connect_switch $switch_name
  Netgear_log_file "TC-VRRP-028.tcl" "Started Configuration of VRRP on switch $switch_name"
  
	set vrid_list [getVrrpVID $switch_name]	
	set pri_list  [getVrrpPriority $switch_name]
	set interface_list [getVrrpInterface $switch_name]

   Netgear_log_file "TC-VRRP-028.tcl" "Started Configuration Priority on switch $switch_name"
   CALAddVrrpPriority $switch_name $interface_list $vrid_list $pri_list
   Netgear_disconnect_switch $switch_name
}

Netgear_log_file "TC-VRRP-028.tcl" "Completed VRRP Configuration on the Switch $switch_name"

Netgear_log_file "TC-VRRP-028.tcl" "******************** Completed Test case TC-VRRP-028.tcl ********************"

#*************************  End of Test case  ****************************************************************