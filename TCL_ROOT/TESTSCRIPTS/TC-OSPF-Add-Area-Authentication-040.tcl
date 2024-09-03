#!/bin/sh
################################################################################   
#
#  File Name   : TC-OSPF-Add-Area-Authentication-040.tcl
#
#  Description :
#         This TCL contains APIs to configure OSPF authentication on Area 0.
#
#  Revision History 	:
#        Date         Programmer        Description
#        -----------------------------------------------------------------------
#        20/9/06      Nina Cheng        Created
#
#
#
#
################################################################################

#*************************  Start of Test case  ********************************

Netgear_log_file "TC-OSPF-Add-Area-Authentication-040.tcl" "******** Starting Test case TC-OSPF-Add-Area-Authentication-040.tcl ********"

Netgear_log_file "TC-OSPF-Add-Area-Authentication-040.tcl" ""

foreach switch_name $ntgr_OSPF_AA_List {

    Netgear_connect_switch $switch_name
    	  
	  Netgear_log_file "TC-OSPF-Add-Area-Authentication-040.tcl" "Started OSPF authentication Configuration for Area 0 on switch $switch_name"
	  set interface_list [getOspfInterfaceList $switch_name]
	  CALOspfConfigIFProperty $switch_name $interface_list
	     
    Netgear_disconnect_switch $switch_name
}

Netgear_log_file "TC-OSPF-Add-Area-Authentication-040.tcl" "******* Completed Test case TC-OSPF-Add-Area-Authentication-040.tcl *******"

#*************************  End of Test case  ***********************************