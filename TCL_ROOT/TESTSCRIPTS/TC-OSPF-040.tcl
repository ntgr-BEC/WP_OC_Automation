#!/bin/sh
################################################################################   
#
#  File Name   : TC-OSPF-040.tcl
#
#  Description :
#         This TCL contains APIs to configure OSPF on the switches.
#
#  Revision History 	:
#        Date         Programmer        Description
#        -----------------------------------------------------------------------
#        04/9/06      Nina Cheng        Created
#
#
#
#
################################################################################

#*************************  Start of Test case  ********************************

Netgear_log_file "TC-OSPF-040.tcl" "******************** Starting Test case TC-OSPF-040.tcl ********************"

Netgear_log_file "TC-OSPF-040.tcl" "Started OSPF Configuration on the Switches"

for_array_keys switch_name ntgr_OSPF_List {
    Netgear_connect_switch $switch_name
    
    Netgear_log_file "TC-OSPF-040.tcl" "Started Configuration of OSPF on switch $switch_name"
    
    Netgear_log_file "TC-OSPF-040.tcl" "Started enable OSPF on switch $switch_name"
    
	  set enable_flag [getOspfGlobalStatus $switch_name]	
	  CALOspfEnable $switch_name $enable_flag
	  
	  Netgear_log_file "TC-OSPF-040.tcl" "Started configure router id for OSPF on switch $switch_name"
	  set router_id [getOspfRouterID $switch_name]
	  CALConfigOspfRouterID $switch_name $router_id
	  
	  Netgear_log_file "TC-OSPF-040.tcl" "Started configure 1583 compatibility for OSPF on switch $switch_name"
	  set compatibility [getOspf1583Compatibility $switch_name]
	  CAL1583CompatibilityEnable $switch_name  $compatibility
	  
	  Netgear_log_file "TC-OSPF-040.tcl" "Started configure default cost for OSPF on switch $switch_name"
	  set default_cost [getOspfAreaDefaultCost $switch_name]
	  CALAreaDefaultCost $switch_name $default_cost
	  
	  Netgear_log_file "TC-OSPF-040.tcl" "Started enable default information originate for OSPF on switch $switch_name"
	  set defaultinfor_flag [getOspfDefaultinformationStatus $switch_name]
	  CALDefaultInforEnable $switch_name $defaultinfor_flag
	  
	  Netgear_log_file "TC-OSPF-040.tcl" "Started configure default metric for OSPF on switch $switch_name"
	  set default_metric [getOspfDefaultMetric $switch_name]
	  CALAddDefaultMetric $switch_name $default_metric
	  
	  Netgear_log_file "TC-OSPF-040.tcl" "Started configure distance for OSPF on switch $switch_name"
	  set distance [getOspfDistance $switch_name]
	  CALOspfDistanceEnable $switch_name $distance
	  
	  Netgear_log_file "TC-OSPF-040.tcl" "Started configure distribute list for OSPF on switch $switch_name"
	  set distribute_list [getOspfDistributeList $switch_name]
	  CALOspfDistributeListOutEnable $switch_name $distribute_list
	  
	  Netgear_log_file "TC-OSPF-040.tcl" "Started configure exit overflow internal for OSPF on switch $switch_name"
	  set exit_int [getOspfExitOverflowInterval $switch_name]
	  CALOspfExitOverflowIntEnable $switch_name $exit_int
	  
	  Netgear_log_file "TC-OSPF-040.tcl" "Started configure external LSDB limit for OSPF on switch $switch_name"
	  set external_limit [getOspfExternalLSBDLimit $switch_name]
	  CALOspfConfigExternalLSDBLimit $switch_name $external_limit
	  
	  Netgear_log_file "TC-OSPF-040.tcl" "Started configure redistribute for OSPF on switch $switch_name"
	  set redistribute [getOspfRedistribute $switch_name]
	  CALOspfRedistributeEnable $switch_name $redistribute
	  
	  Netgear_log_file "TC-OSPF-040.tcl" "Started configure max path for OSPF on switch $switch_name"
	  set max_path [getOspfMaxPath $switch_name]
	  CALOspfMaxPathEnable $switch_name $max_path
	  
	  Netgear_log_file "TC-OSPF-040.tcl" "Started configure trap for OSPF on switch $switch_name"
	  set trap_flag [getOspfTrapStatus $switch_name]
	  CALOspfTrapEnable $switch_name $trap_flag
	  
	  Netgear_log_file "TC-OSPF-040.tcl" "Started configure NSSA for OSPF on switch $switch_name"
	  set nssa_infor [getOspfNssaInfor $switch_name]
	  CALOspfNSSAEnable $switch_name $nssa_infor
	  CALOspfAddNSSAProperty $switch_name $nssa_infor
	  
	  Netgear_log_file "TC-OSPF-040.tcl" "Started configure range for OSPF on switch $switch_name"
	  set range [getOspfRangeInfor $switch_name]
	  CALOspfAreaRangeEnable $switch_name $range
	  
	  Netgear_log_file "TC-OSPF-040.tcl" "Started configure Stub for OSPF on switch $switch_name"
	  set stub_infor [getOspfStubInfor $switch_name]
	  CALOspfAreaStubEnable $switch_name $stub_infor
	  
	  Netgear_log_file "TC-OSPF-040.tcl" "Started configure virtual link for OSPF on switch $switch_name"
	  set vl_flag [getOspfVirtualLinkStatus $switch_name]
	  CALOspfVLinkEnable $switch_name $vl_flag
	  CALOspfAddVLinkProperty $switch_name $vl_flag
	  
	  Netgear_log_file "TC-OSPF-040.tcl" "Started configure OSPF for interface on switch $switch_name"
	  set interface_list [getOspfInterfaceList $switch_name]
	  CALOspfInterfaceEnable $switch_name $interface_list
	  CALOspfConfigAreaID $switch_name $interface_list
	  CALOspfConfigIFProperty $switch_name $interface_list
	     
    Netgear_disconnect_switch $switch_name
}

Netgear_log_file "TC-OSPF-040.tcl" "Completed OSPF Configuration on the Switch $switch_name"

Netgear_log_file "TC-OSPF-040.tcl" "******************** Completed Test case TC-OSPF-040.tcl ********************"

#*************************  End of Test case  ****************************************************************