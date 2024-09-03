################################################################################
#
#  File Name	    	: TC-OSPF-055.tcl
#
#  Description     	: Configure OSPF with maximum external routes and maximum neighbors
#
#  Config file       	: TC-OSPF-055.cfg
#				  
#  Revision History 	:
#        Date          Programmer          Description
#        -----------------------------------------------------------------------
#        06/02/07      Nina Cheng          Created
#
#
##################################################################################

#########################start to configure the TOPO##############################

Netgear_log_file "TC-OSPF-055.tcl" "******** Starting Test case TC-OSPF-055.tcl **********"

foreach switch_name $NTGR_CLEAR_CONFIG_SW_LIST_055 {
    Netgear_log_file "TC-OSPF-055.tcl" "Clear current configuration on switch $switch_name and configure some initial info"
    CALResetConfiguration $switch_name
    CALSetSwitchPrompt $switch_name
    CALSetSwitchMngrIPAddr $switch_name
}

Netgear_log_file "TC-OSPF-055.tcl" "Started routing on the Switches"

    Netgear_connect_switch $NTGR_CLEAR_CONFIG_SW_LIST_055
    CALRoutingEnable $NTGR_CLEAR_CONFIG_SW_LIST_055
    Netgear_disconnect_switch $NTGR_CLEAR_CONFIG_SW_LIST_055


Netgear_log_file "TC-OSPF-055.tcl" "Started Configuration of VLAN"

foreach vlan_index $VLAN_index_list_055 {
	Netgear_log_file "TC-OSPF-055.tcl" "Started Configuration of VLAN $vlan_index"
	CALCreateVlan $vlan_index
}

Netgear_log_file "TC-OSPF-055.tcl" "Started Configuration of OSPF"

foreach switch_name $ntgr_OSPF_List_055 {
	
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

Netgear_log_file "TC-OSPF-055.tcl" "Started Configuration of OSPF on IXIA"

foreach {chassis_id portlist} [array get ntgr_OspfPortList_055] {
	UALConnectTester $chassis_id
	foreach port $portlist {
		UALTakeOwnership $chassis_id $port
		UALLoadPort $chassis_id $port
		UALStartOSPFRouter $chassis_id $port
	}
}

sleep 20

###############################Check the Configuration ################################# 
for {set i 1} {$i <= 3} {incr i} {
	sleep 2
	set switch_checked [lindex $ntgr_OSPF_List_055 0]
	set result1 [CALCheckExpect $switch_checked $cmds $expect_string_list1] 
}

for {set i 1} {$i <= 3} {incr i} {
	sleep 2
	set switch_checked [lindex $ntgr_OSPF_List_055 0]
	set result2 [CALCheckExpect $switch_checked $cmds $expect_string_list2] 
}
	
if {$result1 == 1 && $result2 == 1} {Netgear_log_file "TC-OSPF-055.tcl" "***** TC-OSPF-055.tcl passed *****"} else {
Netgear_log_file "TC-OSPF-055.tcl" "***** TC-OSPF-055.tcl failed *****"}
Netgear_log_file "TC-OSPF-055.tcl" "***** Completed Test case TC-OSPF-055.tcl *****"

Netgear_log_file "TC-OSPF-055.tcl" "Started Configuration of OSPF on IXIA"

#foreach {chassis_id port} [array get ntgr_OspfPortList_055] {
#
#	UALStopOSPFRouter $chassis_id $port
#	UALClearOwnership $chassis_id $port
#	UALDisconnectTester $chassis_id
#}
#*********************  End of Test case  ***************************************
