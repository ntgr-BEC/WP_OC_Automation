################################################################################
#
#  File Name	    	: TC-OSPF-057.tcl
#
#  Description     	: Neighbor and adjacency building with maximum external routes and maximum neighbors
#
#  Config file       	: TC-OSPF-057.cfg
#				  
#  Revision History 	:
#        Date          Programmer          Description
#        -----------------------------------------------------------------------
#        14/02/07      Nina Cheng          Created
#
#
##################################################################################

#########################start to configure the TOPO##############################

Netgear_log_file "TC-OSPF-057.tcl" "******** Starting Test case TC-OSPF-057.tcl **********"

foreach switch_name $NTGR_CLEAR_CONFIG_SW_LIST_057 {
    Netgear_log_file "TC-OSPF-057.tcl" "Clear current configuration on switch $switch_name and configure some initial info"
    CALResetConfiguration $switch_name
    CALSetSwitchPrompt $switch_name
    CALSetSwitchMngrIPAddr $switch_name
}
Netgear_log_file "TC-OSPF-057.tcl" "Started to Modify TOPO"
	Netgear_connect_switch $MT_SW_057
	CALPortLinkDown $MT_SW_057 $port_down
	Netgear_disconnect_switch $MT_SW_057
	
Netgear_log_file "TC-OSPF-057.tcl" "Started routing on the Switches"
foreach switch_name $NTGR_CLEAR_CONFIG_SW_LIST_057 {
    Netgear_connect_switch $switch_name
    CALRoutingEnable $switch_name
    Netgear_disconnect_switch $switch_name
}

Netgear_log_file "TC-OSPF-057.tcl" "Started Configuration of VLAN"

foreach vlan_index $VLAN_index_list_057 {
	Netgear_log_file "TC-OSPF-057.tcl" "Started Configuration of VLAN $vlan_index"
	CALCreateVlan $vlan_index
}

Netgear_log_file "TC-OSPF-057.tcl" "Started Configuration of OSPF"

foreach switch_name $ntgr_OSPF_List_057 {
	
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

Netgear_log_file "TC-OSPF-057.tcl" "Started Configuration of OSPF on IXIA"

foreach {chassis_id portlist} [array get ntgr_OspfPortList_057] {
	UALConnectTester $chassis_id
	foreach port $portlist {
		UALTakeOwnership $chassis_id $port
		UALLoadPort $chassis_id $port
		UALStartOSPFRouter $chassis_id $port
	}
}

sleep 50
Netgear_log_file "TC-OSPF-057.tcl" "Modify TOPO for second time"
	Netgear_connect_switch $MT_SW_057
	CALPortLinkUp $MT_SW_057 $port_up
	Netgear_disconnect_switch $MT_SW_057
	
sleep 30

###############################Check the Configuration ################################# 
for {set i 1} {$i <= 2} {incr i} {
	sleep 2
	set result [CALCheckExpect $MT_SW_057 $cmds $expect_string_list] 
}	
if {$result == 1} {Netgear_log_file "TC-OSPF-057.tcl" "***** TC-OSPF-057.tcl passed *****"} else {
Netgear_log_file "TC-OSPF-057.tcl" "***** TC-OSPF-057.tcl failed *****"}
Netgear_log_file "TC-OSPF-057.tcl" "***** Completed Test case TC-OSPF-057.tcl *****"

Netgear_log_file "TC-OSPF-057.tcl" "Started Configuration of OSPF on IXIA"

#foreach {chassis_id port} [array get ntgr_OspfPortList_057] {
#
#	UALStopOSPFRouter $chassis_id $port
#	UALClearOwnership $chassis_id $port
#	UALDisconnectTester $chassis_id
#}
#*********************  End of Test case  ***************************************
