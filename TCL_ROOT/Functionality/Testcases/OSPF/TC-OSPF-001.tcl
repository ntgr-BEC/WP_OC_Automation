#################################################################################
#
#  File Name	    	: TC-OSPF-001.tcl
#
#  Description     	: simple text authentication test
#
#  Config file       : TC-OSPF-001.cfg
#
#  Global Variables	: ntgr_poChanList (defined in CFG file)
#				  
#  Revision History 	:
#        Date          Programmer          Description
#        -----------------------------------------------------------------------
#        30/01/07      Nina Cheng          Created
#
#
##################################################################################

#########################start to configure the TOPO##############################

Netgear_log_file "TC-OSPF-001.tcl" "******** Starting Test case TC-OSPF-001.tcl **********"

foreach switch_name $NTGR_CLEAR_CONFIG_SW_LIST_001 {
    Netgear_log_file "TC-OSPF-001.tcl" "Clear current configuration on switch $switch_name and configure some initial info"
    CALResetConfiguration $switch_name
    CALSetSwitchPrompt $switch_name
    CALSetSwitchMngrIPAddr $switch_name
}

Netgear_log_file "TC-OSPF-001.tcl" "Started Configuration of VLAN"

foreach vlan_index $VLAN_index_list_001 {
	Netgear_log_file "TC-OSPF-001.tcl" "Started Configuration of VLAN $vlan_index"
	CALCreateVlan $vlan_index
}

Netgear_log_file "TC-OSPF-001.tcl" "Started routing on the Switches"

foreach switch_name $NTGR_CLEAR_CONFIG_SW_LIST_001 {
	Netgear_connect_switch $switch_name 
	CALRoutingEnable $switch_name
	Netgear_disconnect_switch $switch_name
}

Netgear_log_file "TC-OSPF-001.tcl" "Started Configuration of OSPF"

foreach switch_name $ntgr_OSPF_List_001 {
	
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
for {set i 1} {$i <= 3} {incr i} {
	sleep 2
	set switch_checked [lindex $ntgr_OSPF_List_001 0]
	set result [CALCheckExpect $switch_checked $cmds $expect_string_list] 
}	
if {$result == 1} {Netgear_log_file "TC-OSPF-001.tcl" "***** TC-OSPF-001.tcl passed *****"} else {
Netgear_log_file "TC-OSPF-001.tcl" "***** TC-OSPF-001.tcl failed *****"}

Netgear_log_file "TC-OSPF-001.tcl" "***** Completed Test case TC-OSPF-001.tcl *****"

#*********************  End of Test case  ***************************************
