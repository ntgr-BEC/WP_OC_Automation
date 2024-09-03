#!/bin/sh
################################################################################   
#
#  File Name   : TC-OSPF-DelFilterforRoutes-040.tcl
#
#  Description :
#         This TCL contains APIs to configure OSPF on the switches.
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

#******************************************  Start of Test case  ***********************************************************

Netgear_log_file "TC-OSPF-DelFilterforRoutes-040.tcl" "*******Starting Test case TC-OSPF-DelFilterforRoutes-040.tcl ********"

Netgear_log_file "TC-OSPF-DelFilterforRoutes-040.tcl" "Started OSPF Configuration on the Switches"

foreach switch_name $ntgr_OSPF_Filter_List {
    
    Netgear_connect_switch $switch_name
   
    Netgear_log_file "TC-OSPF-DelFilterforRoutes-040.tcl" "Started disable distribute list for OSPF on switch $switch_name"
    set distribute_list [getOspfDistributeList $switch_name]
    CALOspfDistributeListOutDisable $switch_name $distribute_list
	  
    Netgear_disconnect_switch $switch_name
}

Netgear_log_file "TC-OSPF-DelFilterforRoutes-040.tcl" "****** Completed Test case TC-OSPF-DelFilterforRoutes-040.tcl ******"

#*****************************************  End of Test case  ****************************************************************