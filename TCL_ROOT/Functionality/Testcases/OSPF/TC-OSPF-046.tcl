#################################################################################
#
#  File Name	    	: TC-OSPF-046.tcl
#
#  Description     	: Delete VLAN with OSPF 
#
#  Config file          : TC-OSPF-046.cfg
#				  
#  Revision History 	:
#        Date          Programmer          Description
#        -----------------------------------------------------------------------
#        31/01/07      Nina Cheng          Created
#
#
##################################################################################

#########################start to configure the TOPO##############################
Netgear_log_file "TC-OSPF-046.tcl" "******** Starting Test case TC-OSPF-046.tcl **********"

foreach switch_name $NTGR_CLEAR_CONFIG_SW_LIST_046 {
	Netgear_log_file "TC-OSPF-046.tcl" "Clear current configuration on switch $switch_name and configure some initial info"
	CALResetConfiguration $switch_name
	CALSetSwitchPrompt $switch_name
	CALSetSwitchMngrIPAddr $switch_name
}

Netgear_log_file "TC-OSPF-046.tcl" "Started to Modify TOPO"
Netgear_connect_switch $MT_SW_046
CALPortLinkDown $MT_SW_046 $port_down
Netgear_disconnect_switch $MT_SW_046

Netgear_log_file "TC-OSPF-046.tcl" "Started Configuration of VLAN"

foreach vlan_index $VLAN_index_list_046 {
	Netgear_log_file "TC-OSPF-046.tcl" "Started Configuration of VLAN $vlan_index"
	CALCreateVlan $vlan_index
}

Netgear_log_file "TC-OSPF-046.tcl" "Started routing on the Switches"

foreach switch_name $NTGR_CLEAR_CONFIG_SW_LIST_046 {
	Netgear_connect_switch $switch_name 
	CALRoutingEnable $switch_name
	Netgear_disconnect_switch $switch_name
}

Netgear_log_file "TC-OSPF-046.tcl" "Started Configuration of OSPF"

foreach switch_name $ntgr_OSPF_List_046 {
	
	set enable_flag [getOspfGlobalStatus $switch_name]
	set router_id [getOspfRouterID $switch_name]
	set interface_list [getOspfInterfaceList $switch_name]
	  
	Netgear_connect_switch $switch_name 
	
	CALOspfEnable $switch_name $enable_flag
	CALConfigOspfRouterID $switch_name $router_id		
	CALOspfInterfaceEnable $switch_name $interface_list
	CALOspfConfigAreaID $switch_name $interface_list
	CALOspfConfigIFProperty $switch_name $interface_list
	
	Netgear_disconnect_switch $switch_name
}
###############################Check the Configuration ################################# 

Netgear_log_file "TC-OSPF-046.tcl" "***** Delete the irrespective VLAN *****"
CALDeleteVlan $vlan_index_delete

set result1 [CALCheckExpect $SW_checked $cmds1 $expect_string_list1]		
if {$result1 == 1} {
	Netgear_log_file "TC-OSPF-046.tcl" "***** TC-OSPF-046.tcl passed *****"
} else {
	Netgear_log_file "TC-OSPF-046.tcl" "***** TC-OSPF-046.tcl failed.*****"
}

Netgear_log_file "TC-OSPF-046.tcl" "***** Completed Test case TC-OSPF-046.tcl *****"

#****************************************  End of Test case  ***************************************

