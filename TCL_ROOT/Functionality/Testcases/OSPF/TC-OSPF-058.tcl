################################################################################
#
#  File Name	    	: TC-OSPF-058.tcl
#
#  Description     	: Database synchronization with maximum external routes 
#			  and maximum neighbors
#
#  Config file       	: TC-OSPF-058.cfg
#				  
#  Revision History 	:
#        Date          Programmer          Description
#        -----------------------------------------------------------------------
#        14/02/07      Nina Cheng          Created
#
#
##################################################################################

#########################start to configure the TOPO##############################

Netgear_log_file "TC-OSPF-058.tcl" "******** Starting Test case TC-OSPF-058.tcl **********"

foreach switch_name $NTGR_CLEAR_CONFIG_SW_LIST_058 {
    Netgear_log_file "TC-OSPF-058.tcl" "Clear current configuration on switch $switch_name and configure some initial info"
    CALResetConfiguration $switch_name
    CALSetSwitchPrompt $switch_name
    CALSetSwitchMngrIPAddr $switch_name
}
Netgear_log_file "TC-OSPF-058.tcl" "Started to Modify TOPO"
	Netgear_connect_switch $MT_SW_058
	CALPortLinkDown $MT_SW_058 $port_down
	Netgear_disconnect_switch $MT_SW_058
	
Netgear_log_file "TC-OSPF-058.tcl" "Started routing on the Switches"
foreach switch_name $NTGR_CLEAR_CONFIG_SW_LIST_058 {
    Netgear_connect_switch $switch_name
    CALRoutingEnable $switch_name
    Netgear_disconnect_switch $switch_name
}

Netgear_log_file "TC-OSPF-058.tcl" "Started Configuration of VLAN"

foreach vlan_index $VLAN_index_list_058 {
	Netgear_log_file "TC-OSPF-058.tcl" "Started Configuration of VLAN $vlan_index"
	CALCreateVlan $vlan_index
}

Netgear_log_file "TC-OSPF-058.tcl" "Started Configuration of OSPF"

foreach switch_name $ntgr_OSPF_List_058 {
	
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

Netgear_log_file "TC-OSPF-058.tcl" "Started Configuration of OSPF on IXIA"

foreach {chassis_id portlist} [array get ntgr_OspfPortList_058] {
	UALConnectTester $chassis_id
	foreach port $portlist {
		UALTakeOwnership $chassis_id $port
		UALLoadPort $chassis_id $port
		UALStartOSPFRouter $chassis_id $port
	}
}

sleep 50
Netgear_log_file "TC-OSPF-058.tcl" "Modify TOPO for second time"
	Netgear_connect_switch $MT_SW_058
	CALPortLinkUp $MT_SW_058 $port_up
	Netgear_disconnect_switch $MT_SW_058
	
sleep 50

###############################Check the Configuration ################################# 
for {set i 1} {$i <= 3} {incr i} {
	sleep 2
	set result1 [CALCheckExpect $MT_SW_058 $cmds $expect_string_list1] 
}
for {set i 1} {$i <= 3} {incr i} {
	sleep 2
	set result2 [CALCheckExpect $MT_SW_058 $cmds $expect_string_list2] 
}	
if {$result1 == 1 && $result2 == 1} {Netgear_log_file "TC-OSPF-058.tcl" "***** TC-OSPF-058.tcl passed *****"} else {
Netgear_log_file "TC-OSPF-058.tcl" "***** TC-OSPF-058.tcl failed *****"}
Netgear_log_file "TC-OSPF-058.tcl" "***** Completed Test case TC-OSPF-058.tcl *****"

Netgear_log_file "TC-OSPF-058.tcl" "Started Configuration of OSPF on IXIA"

#foreach {chassis_id port} [array get ntgr_OspfPortList_058] {
#
#	UALStopOSPFRouter $chassis_id $port
#	UALClearOwnership $chassis_id $port
#	UALDisconnectTester $chassis_id
#}
#*********************  End of Test case  ***************************************
