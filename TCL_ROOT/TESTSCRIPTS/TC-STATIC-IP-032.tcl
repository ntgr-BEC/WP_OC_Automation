#!/bin/sh
################################################################################   
#
#  File Name   : TC-STATIC-IP-032.tcl
#
#  Description :
#         This TCL contains APIs to configure static routes on the switches.
#
#  Revision History 	:
#        Date         Programmer        Description
#        -----------------------------------------------------------------------
#        23/8/06      Nina Cheng        Created
#
#
#
#
################################################################################

#*************************  Start of Test case  ********************************

Netgear_log_file "TC-STATIC-IP-032.tcl" "******************** Starting Test case TC-STATIC-IP-032.tcl ********************"

Netgear_log_file "TC-STATIC-IP-032.tcl" "Started static routes Configuration on the Switches"

for_array_keys switch_name ntgr_StaticIPList {
    Netgear_log_file "TC-STATIC-IP-032.tcl" "Started Configuration of static routes on switch $switch_name"
  
    Netgear_log_file "TC-STATIC-IP-032.tcl" "Started add static routes on switch $switch_name"
    set address_list [getAddressList $switch_name]	
    CALAddStaticRoute $switch_name $address_list
    
    Netgear_log_file "TC-STATIC-IP-032.tcl" "Started add default routes on switch $switch_name"
    set default_route  [getDefaultRoute $switch_name]
    CALAddDefaultRoute  $switch_name $default_route
    
    
    Netgear_log_file "TC-STATIC-IP-032.tcl" "Started to modify static route distance on switch $switch_name"
    set distance [getStaticRouteDistance $switch_name]
    CALModifyStaticRouteDistance $switch_name $distance
 
#     ##Below is the script to delete the static route
#     Netgear_log_file "TC-STATIC-IP-032.tcl" "Started delete static routes on switch $switch_name"
#     set address_list [getAddressList $switch_name]	
#     CALDeleteStaticRoute $switch_name $address_list
#     
#     Netgear_log_file "TC-STATIC-IP-032.tcl" "Started delete default routes on switch $switch_name"
#     CALDeleteDefaultRoute  $switch_name 
#     
#     
#     Netgear_log_file "TC-STATIC-IP-032.tcl" "Started to set distance for static route to default on switch $switch_name"
#     CALSetStaticRouteDistanceToDefault $switch_name 
}

Netgear_log_file "TC-STATIC-IP-032.tcl" "Completed static route Configuration on the Switch $switch_name"

Netgear_log_file "TC-STATIC-IP-032.tcl" "******************** Completed Test case TC-STATIC-IP-032.tcl ********************"

#*************************  End of Test case  ****************************************************************
