#################################################################################
#
#  File Name	    	: TC-OSPF-017.tcl
#
#  Description     	: Filter static routes
#
#  Config file       	: TC-OSPF-017.cfg
#				  
#  Revision History 	:
#        Date          Programmer          Description
#        -----------------------------------------------------------------------
#        06/02/07      Nina Cheng          Created
#
#
##################################################################################

#########################start to configure the TOPO##############################

Netgear_log_file "TC-OSPF-017.tcl" "******** Starting Test case TC-OSPF-017.tcl **********"

foreach switch_name $NTGR_CLEAR_CONFIG_SW_LIST_017 {
	Netgear_log_file "TC-OSPF-017.tcl" "Clear current configuration on switch $switch_name and configure some initial info"
	CALResetConfiguration $switch_name
	CALSetSwitchPrompt $switch_name
	CALSetSwitchMngrIPAddr $switch_name
}

Netgear_log_file "TC-OSPF-017.tcl" "Started to Modify TOPO"
Netgear_connect_switch $MT_SW_017
CALPortLinkDown $MT_SW_017 $port_down
Netgear_disconnect_switch $MT_SW_017

Netgear_log_file "TC-OSPF-017.tcl" "Started Configuration of VLAN"

foreach vlan_index $VLAN_index_list_017 {
	Netgear_log_file "TC-OSPF-017.tcl" "Started Configuration of VLAN $vlan_index"
	CALCreateVlan $vlan_index
}

Netgear_log_file "TC-OSPF-017.tcl" "Started routing on the Switches"

foreach switch_name $NTGR_CLEAR_CONFIG_SW_LIST_017 {
	Netgear_connect_switch $switch_name 
	CALRoutingEnable $switch_name
	Netgear_disconnect_switch $switch_name
}

Netgear_log_file "TC-OSPF-017.tcl" "Started configuration static routes on the switches"

foreach switch_name $ntgr_StaticIPList_017 {
    set address_list [getAddressList $switch_name]	
    CALAddStaticRoute $switch_name $address_list
}

Netgear_log_file "TC-OSPF-017.tcl" "Started Configuration of OSPF"

foreach switch_name $ntgr_OSPF_List_017 {
	
	set enable_flag [getOspfGlobalStatus $switch_name]
	set router_id [getOspfRouterID $switch_name]
	set interface_list [getOspfInterfaceList $switch_name]
	set redistribute [getOspfRedistribute $switch_name]
	set distribute_list [getOspfDistributeList $switch_name]
	
	Netgear_connect_switch $switch_name 
	
	CALOspfEnable $switch_name $enable_flag
	CALConfigOspfRouterID $switch_name $router_id		
	CALOspfInterfaceEnable $switch_name $interface_list
	CALOspfConfigAreaID $switch_name $interface_list
	CALOspfConfigIFProperty $switch_name $interface_list
	CALOspfRedistributeEnable $switch_name $redistribute
	CALOspfDistributeListOutEnable $switch_name $distribute_list  
	
	Netgear_disconnect_switch $switch_name
}

###############################Check the Configuration ################################# 
for {set i 1} {$i <= 3} {incr i} {
	sleep 2
	set result1 [CALCheckExpect $SW_checked $cmds1 $expect_string_list1] 
}	
if {$result1 == 1} {
	set result2 [CALCheckExpect $SW_checked $cmds2 $expect_string_list2]
	if {$result2 == 1} {
		Netgear_log_file "TC-OSPF-017.tcl" "***** Connected routes have been received. *****"
	} else {
		Netgear_log_file "TC-OSPF-017.tcl" "***** Connected routes have not been received. *****"
	}
} else {
	Netgear_log_file "TC-OSPF-017.tcl" "***** Neighbor isn't full.*****"
}
if {$result1 == 1 && $result2 == 1} {
	Netgear_log_file "TC-OSPF-017.tcl" "Started Configuration of IP ACL"

	foreach switch_name $ntgr_IP_ACL_List_017 {
  
  		set ip_acl_id_list [getIPACLID $switch_name]
  		set ip_acl_rule_list [ getIPACLRule $switch_name]
 
  		CALAddIPACL $switch_name $ip_acl_rule_list
	}
}

sleep 5
set result3 [CALCheckExpect $SW_checked $cmds2 $expect_string_list2 0]

if {$result3 == 1} {
	Netgear_log_file "TC-OSPF-017.tcl" "***** TC-OSPF-017.tcl passed *****"
} else {
	Netgear_log_file "TC-OSPF-017.tcl" "***** TC-OSPF-017.tcl failed *****"
}
Netgear_log_file "TC-OSPF-017.tcl" "***** Completed Test case TC-OSPF-017.tcl *****"

#*********************  End of Test case  ***************************************
