#!/bin/sh
################################################################################   
#
#  File Name   : TC-OSPF-Del-Area-Authentication-066.tcl
#
#  Description :
#         This TCL contains APIs to delete OSPF authentication for Area 0.
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

Netgear_log_file "TC-OSPF-Del-Area-Authentication-066.tcl" "******* Starting Test case TC-OSPF-Del-Area-Authentication-066.tcl *******"

foreach switch_name $ntgr_OSPF_AA_List {

    Netgear_connect_switch $switch_name	  
    
	  Netgear_log_file "TC-OSPF-Del-Area-Authentication-066.tcl" "Started delete OSPF authentication for Area 0 on switch $switch_name"
	  set interface_list [getOspfInterfaceList $switch_name]
	  CALOspfDeleteIFProperty $switch_name $interface_list
    
    Netgear_disconnect_switch $switch_name
}

Netgear_log_file "TC-OSPF-Del-Area-Authentication-066.tcl" "****** Completed Test case TC-OSPF-Del-Area-Authentication-066.tcl ******"

#*************************  End of Test case  ****************************************************************