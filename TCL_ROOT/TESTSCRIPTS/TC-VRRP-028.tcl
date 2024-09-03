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
	set pre_list  [getVrrpPreemptMode $switch_name]
	set pri_list  [getVrrpPriority $switch_name]
	set interface_list [getVrrpInterface $switch_name]
	set time_list [getVrrpTimerAdvertise $switch_name]
	set auth_list [getVrrpAuthenticationMode $switch_name] 
  set vip_list  [getVrrpVIP $switch_name]
  
  set vrid_number [llength $vrid_list]
  set interface_number [llength $interface_list] 
  set vip_number [llength $vip_list]
  set auth_number [llength $auth_list]
  set pre_number [llength $pre_list]
  set pri_number [llength $pri_list]
  set timer_number [llength $time_list]
 
   CALVRRPErrorImformation $switch_name $vrid_number $interface_number $vip_number $auth_number $pre_number $pri_number $timer_number
   
   Netgear_log_file "TC-VRRP-028.tcl" "Started enable VRRP on switch $switch_name"
   CALVRRPEnable $switch_name
   
   Netgear_log_file "TC-VRRP-028.tcl" "Started Configuration vrid on switch $switch_name"
   CALAddVRRPVrid $switch_name $interface_list $vrid_list
   
   Netgear_log_file "TC-VRRP-028.tcl" "Started Configuration vrip on switch $switch_name"
   CALAddVRRPVip $switch_name $interface_list $vrid_list $vip_list
   
   Netgear_log_file "TC-VRRP-028.tcl" "Started Configuration authentication on switch $switch_name"
   CALVRRPAuthenticationEnable $switch_name $interface_list $vrid_list $auth_list
   
   Netgear_log_file "TC-VRRP-028.tcl" "Started Configuration enable preempt on switch $switch_name"
   CALVRRPPreemptEnable $switch_name $interface_list $vrid_list $pre_list
   
   Netgear_log_file "TC-VRRP-028.tcl" "Started Configuration Priority on switch $switch_name"
   CALAddVrrpPriority $switch_name $interface_list $vrid_list $pri_list
   
   Netgear_log_file "TC-VRRP-028.tcl" "Started Configuration Timer advertised on switch $switch_name"
   CALAddVrrpAdvertiseTimer $switch_name $interface_list $vrid_list $time_list
  
############################################################################################################
   
   #Netgear_log_file "TC-VRRP-028.tcl" "Started Delete the configuration of VRRP on switch $switch_name"
   #
   #Netgear_log_file "TC-VRRP-028.tcl" "Started disable VRRP on switch $switch_name"
   #CALVRRPDisable $switch_name
   #
   #Netgear_log_file "TC-VRRP-028.tcl" "Started disable authentication on switch $switch_name"
   #CALVRRPAuthenticationDisable  $switch_name $interface_list $vrid_list
   #
   #Netgear_log_file "TC-VRRP-028.tcl" "Started Configuration disable preempt on switch $switch_name"
   #CALVRRPPreemptDisable $switch_name $interface_list $vrid_list
   #
   #Netgear_log_file "TC-VRRP-028.tcl" "Started delete Priority on switch $switch_name"
   #CALDeleteVrrpPriority $switch_name $interface_list $vrid_list
   #
   #Netgear_log_file "TC-VRRP-028.tcl" "Started delete Timer advertised on switch $switch_name"
   #CALDeleteVrrpAdvertiseTimer $switch_name $interface_list $vrid_list
   #
   #Netgear_log_file "TC-VRRP-028.tcl" "Started delete vrid on switch $switch_name"
   #CALDeleteVRRPVrid $switch_name $interface_list $vrid_list
   Netgear_disconnect_switch $switch_name
}

Netgear_log_file "TC-VRRP-028.tcl" "Completed VRRP Configuration on the Switch $switch_name"

Netgear_log_file "TC-VRRP-028.tcl" "******************** Completed Test case TC-VRRP-028.tcl ********************"

#*************************  End of Test case  ****************************************************************