#################################################################################
#
#  File Name	    	: TC-OSPF-051.tcl
#
#  Description     	: Delete RIP routes when redistribution
#
#  Config file          : TC-OSPF-051.cfg
#				  
#  Revision History 	:
#        Date          Programmer          Description
#        -----------------------------------------------------------------------
#        07/02/07      Nina Cheng          Created
#
#
##################################################################################

#########################start to configure the TOPO##############################
Netgear_log_file "TC-OSPF-051.tcl" "******** Starting Test case TC-OSPF-051.tcl **********"

foreach switch_name $NTGR_CLEAR_CONFIG_SW_LIST_051 {
	Netgear_log_file "TC-OSPF-051.tcl" "Clear current configuration on switch $switch_name and configure some initial info"
	CALResetConfiguration $switch_name
	CALSetSwitchPrompt $switch_name
	CALSetSwitchMngrIPAddr $switch_name
}

Netgear_log_file "TC-OSPF-051.tcl" "Started to Modify TOPO"
Netgear_connect_switch $MT_SW_051
CALPortLinkDown $MT_SW_051 $port_down
Netgear_disconnect_switch $MT_SW_051

Netgear_log_file "TC-OSPF-051.tcl" "Started Configuration of VLAN"

foreach vlan_index $VLAN_index_list_051 {
	Netgear_log_file "TC-OSPF-051.tcl" "Started Configuration of VLAN $vlan_index"
	CALCreateVlan $vlan_index
}

Netgear_log_file "TC-OSPF-051.tcl" "Started routing on the Switches"

foreach switch_name $NTGR_CLEAR_CONFIG_SW_LIST_051 {
	Netgear_connect_switch $switch_name 
		CALRoutingEnable $switch_name
	if {$switch_name == $SW_RROUTE} {
		CALRoutingPortEnable $switch_name $port_up
		CALAddIPtoPort $switch_name $port_up $IP_address		
	}
	Netgear_disconnect_switch $switch_name
}

Netgear_log_file "TC-OSPF-051.tcl" "Started Configuration of OSPF"

foreach switch_name $ntgr_OSPF_List_051 {
	
	set enable_flag [getOspfGlobalStatus $switch_name]
	set router_id [getOspfRouterID $switch_name]
	set interface_list [getOspfInterfaceList $switch_name]
	set redistribute [getOspfRedistribute $switch_name]
	  
	Netgear_connect_switch $switch_name 
	
	CALOspfEnable $switch_name $enable_flag
	CALConfigOspfRouterID $switch_name $router_id		
	CALOspfInterfaceEnable $switch_name $interface_list
	CALOspfConfigAreaID $switch_name $interface_list
	CALOspfConfigIFProperty $switch_name $interface_list
	CALOspfRedistributeEnable $switch_name $redistribute
	
	Netgear_disconnect_switch $switch_name
}

Netgear_log_file "TC-OSPF-051.tcl" "Started Configuration of RIP on DUT"
foreach switch_name $switch_RIP_list_051 {
	
	Netgear_connect_switch $switch_name 
	
	set flag_enable [getRipGlobalStatus $switch_name]
	set interface_list [getRipInterface $switch_name]
	
	CALRipEnableSwitch $switch_name $flag_enable
	CALRipEnableInterface $switch_name $interface_list
	 	
	Netgear_disconnect_switch $switch_name   
}
Netgear_log_file "TC-OSPF-051.tcl" "Started Configuration of RIP on IXIA"

foreach {chassis_id port} [array get ntgr_RipPortList_051] {

	UALConnectTester $chassis_id	
	UALTakeOwnership $chassis_id $port
	UALLoadPort $chassis_id $port
	UALStartRIPRouter $chassis_id $port
}
###############################Check the Configuration ################################# 
for {set i 1} {$i <= 3} {incr i} {
	sleep 2
	set result2 [CALCheckExpect $SW_checked $cmds2 $expect_string_list2] 
}	
if {$result2 == 1} {
	Netgear_log_file "TC-OSPF-051.tcl" "***** TC-OSPF-051.tcl passed.*****"
} else {
	Netgear_log_file "TC-OSPF-051.tcl" "***** TC-OSPF-051.tcl failed, RIP routes can't be received.*****"
}
##################################### Disable RIP routes ################################
foreach {chassis_id port} [array get ntgr_RipPortList_051] {

	UALStopRIPRouter $chassis_id $port
	UALClearOwnership $chassis_id $port
	UALDisconnectTester $chassis_id
}	
sleep 3

set result1 [CALCheckExpect $SW_checked $cmds1 $expect_string_list1]
	if {$result1 == 1} {
		Netgear_log_file "TC-OSPF-051.tcl" "***** TC-OSPF-051.tcl passed *****"
	} else {
		Netgear_log_file "TC-OSPF-051.tcl" "***** TC-OSPF-051.tcl failed, neighbor isn't full. *****"
	}
Netgear_log_file "TC-OSPF-051.tcl" "***** Completed Test case TC-OSPF-051.tcl *****"
#****************************************  End of Test case  ***************************************

