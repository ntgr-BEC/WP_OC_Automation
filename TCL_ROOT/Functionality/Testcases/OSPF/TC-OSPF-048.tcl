#################################################################################
#
#  File Name	    	: TC-OSPF-048.tcl
#
#  Description     	: Rebooting the switch when running OSPF
#
#  Config file       	: TC-OSPF-048.cfg
#				  
#  Revision History 	:
#        Date          Programmer          Description
#        -----------------------------------------------------------------------
#        07/02/07      Nina Cheng          Created
#
#
##################################################################################

#########################start to configure the TOPO##############################

Netgear_log_file "TC-OSPF-048.tcl" "******** Starting Test case TC-OSPF-048.tcl **********"

foreach switch_name $NTGR_CLEAR_CONFIG_SW_LIST_048 {
    Netgear_log_file "TC-OSPF-048.tcl" "Clear current configuration on switch $switch_name and configure some initial info"
    CALResetConfiguration $switch_name
    CALSetSwitchPrompt $switch_name
    CALSetSwitchMngrIPAddr $switch_name
}

Netgear_log_file "TC-OSPF-048.tcl" "Started to Modify TOPO"
	Netgear_connect_switch $MT_SW_048
	CALPortLinkDown $MT_SW_048 $port_down
	Netgear_disconnect_switch $MT_SW_048

Netgear_log_file "TC-OSPF-048.tcl" "Started Configuration of VLAN"

foreach vlan_index $VLAN_index_list_048 {
	Netgear_log_file "TC-OSPF-048.tcl" "Started Configuration of VLAN $vlan_index"
	CALCreateVlan $vlan_index
}

Netgear_log_file "TC-OSPF-048.tcl" "Started routing on the Switches"

foreach switch_name $NTGR_CLEAR_CONFIG_SW_LIST_048 {
	Netgear_connect_switch $switch_name 
	CALRoutingEnable $switch_name
	Netgear_disconnect_switch $switch_name
}

Netgear_log_file "TC-OSPF-048.tcl" "Started Configuration of OSPF"

foreach switch_name $ntgr_OSPF_List_048 {
	
	set enable_flag [getOspfGlobalStatus $switch_name]
	set router_id [getOspfRouterID $switch_name]
	set interface_list [getOspfInterfaceList $switch_name]
	  
	Netgear_connect_switch $switch_name 
	
	CALOspfEnable $switch_name $enable_flag
	CALConfigOspfRouterID $switch_name $router_id		
	CALOspfInterfaceEnable $switch_name $interface_list
	CALOspfConfigAreaID $switch_name $interface_list
	
	Netgear_disconnect_switch $switch_name
}

CALSaveConfig $switch_checked
CALRebootSwitch $switch_checked
sleep 30
###############################Check the Configuration ################################# 
for {set i 1} {$i <= 3} {incr i} {
	sleep 2
	set switch_checked [lindex $ntgr_OSPF_List_048 0]
	set result [CALCheckExpect $switch_checked $cmds $expect_string_list] 
}	
if {$result == 1} {Netgear_log_file "TC-OSPF-048.tcl" "***** TC-OSPF-048.tcl passed *****"} else {
Netgear_log_file "TC-OSPF-048.tcl" "***** TC-OSPF-048.tcl failed *****"}

Netgear_log_file "TC-OSPF-048.tcl" "***** Completed Test case TC-OSPF-048.tcl *****"

#*********************  End of Test case  ***************************************
