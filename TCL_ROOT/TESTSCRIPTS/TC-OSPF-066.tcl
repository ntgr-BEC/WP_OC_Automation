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
      
    Netgear_log_file "TC-OSPF-040.tcl" "Started delete configuration for OSPF on switch $switch_name"
    
    Netgear_log_file "TC-OSPF-040.tcl" "Started disable 1583 compatibility for OSPF on switch $switch_name"
    set compatibility [getOspf1583Compatibility $switch_name]
    CAL1583CompatibilityDisable $switch_name $compatibility
    
    Netgear_log_file "TC-OSPF-040.tcl" "Started disable default information originate for OSPF on switch $switch_name"
    set defaultinfor_flag [getOspfDefaultinformationStatus $switch_name]
    CALDefaultInfOrDisable $switch_name $defaultinfor_flag
    
    Netgear_log_file "TC-OSPF-040.tcl" "Started disable default metric for OSPF on switch $switch_name"
    set default_metric [getOspfDefaultMetric $switch_name]
    CALDeleteDefaultMetric $switch_name $default_metric
    
    Netgear_log_file "TC-OSPF-040.tcl" "Started disable distance for OSPF on switch $switch_name"
    set distance [getOspfDistance $switch_name]
    CALOspfDistanceDisable $switch_name $distance
    
    Netgear_log_file "TC-OSPF-040.tcl" "Started disable distribute list for OSPF on switch $switch_name"
    set distribute_list [getOspfDistributeList $switch_name]
    CALOspfDistributeListOutDisable $switch_name $distribute_list
	  
	  Netgear_log_file "TC-OSPF-040.tcl" "Started disable exit overflow internal for OSPF on switch $switch_name"
	  set exit_int [getOspfExitOverflowInterval $switch_name]
	  CALOspfExitOverflowIntDisable $switch_name $exit_int
	  
	  Netgear_log_file "TC-OSPF-040.tcl" "Started disable external LSDB limit for OSPF on switch $switch_name"
	  set external_limit [getOspfExternalLSBDLimit $switch_name]
	  CALOspfDeleteExternalLSDBLimit $switch_name $external_limit
	  
	  Netgear_log_file "TC-OSPF-040.tcl" "Started disable redistribute for OSPF on switch $switch_name"
	  set redistribute [getOspfRedistribute $switch_name]
	  CALOspfRedistributeDisable $switch_name $redistribute
	  
	  Netgear_log_file "TC-OSPF-040.tcl" "Started disable max path for OSPF on switch $switch_name"
	  set max_path [getOspfMaxPath $switch_name]
	  CALOspfMaxPathDisable $switch_name $max_path
	  
	  Netgear_log_file "TC-OSPF-040.tcl" "Started disable trap for OSPF on switch $switch_name"
	  set trap_flag [getOspfTrapStatus $switch_name]
	  CALOspfTrapDisable $switch_name $trap_flag
	  
	  Netgear_log_file "TC-OSPF-040.tcl" "Started disable NSSA for OSPF on switch $switch_name"
	  set nssa_infor [getOspfNssaInfor $switch_name]
	  CALOspfNSSADisable $switch_name $nssa_infor
	  CALOspfDeleteNSSAProperty $switch_name $nssa_infor
	  
	  Netgear_log_file "TC-OSPF-040.tcl" "Started disable range for OSPF on switch $switch_name"
	  set range [getOspfRangeInfor $switch_name]
	  CALOspfAreaRangeDisable $switch_name $range
	  
	  Netgear_log_file "TC-OSPF-040.tcl" "Started disable Stub for OSPF on switch $switch_name"
	  set stub_infor [getOspfStubInfor $switch_name]
	  CALOspfAreaStubDisable $switch_name $stub_infor
	  CALOspfStubsmmarylsaDisable $switch_name $stub_infor
	  
	  Netgear_log_file "TC-OSPF-040.tcl" "Started disable virtual link for OSPF on switch $switch_name"
	  set vl_flag [getOspfVirtualLinkStatus $switch_name]
	  CALOspfVLinkDisable $switch_name $vl_flag
	  CALOspfDeleteVLinkProperty $switch_name $vl_flag
	  
	  Netgear_log_file "TC-OSPF-040.tcl" "Started disable OSPF for interface on switch $switch_name"
	  set interface_list [getOspfInterfaceList $switch_name]
	  CALOspfDeleteIFProperty $switch_name $interface_list
	  CALOspfInterfaceDisable $switch_name $interface_list
	  
	  Netgear_log_file "TC-OSPF-040.tcl" "Started disable OSPF on switch $switch_name"
	  set enable_flag [getOspfGlobalStatus $switch_name]
    CALOspfDisable $switch_name $enable_flag
    
    Netgear_disconnect_switch $switch_name
}

Netgear_log_file "TC-OSPF-040.tcl" "Completed OSPF Configuration on the Switch $switch_name"

Netgear_log_file "TC-OSPF-040.tcl" "******************** Completed Test case TC-OSPF-040.tcl ********************"

#*************************  End of Test case  ****************************************************************