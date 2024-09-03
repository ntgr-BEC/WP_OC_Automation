#################################################################################
#
#  File Name	    	: TC-OSPF-053.tcl
#
#  Description     	: Delete static routes when redistribution
#
#  Config file       	: TC-OSPF-053.cfg
#				  
#  Revision History 	:
#        Date          Programmer          Description
#        -----------------------------------------------------------------------
#        07/02/07      Nina Cheng          Created
#
#
##################################################################################

#########################start to configure the TOPO##############################

Netgear_log_file "TC-OSPF-053.tcl" "******** Starting Test case TC-OSPF-053.tcl **********"

foreach switch_name $NTGR_CLEAR_CONFIG_SW_LIST_053 {
	Netgear_log_file "TC-OSPF-053.tcl" "Clear current configuration on switch $switch_name and configure some initial info"
	CALResetConfiguration $switch_name
	CALSetSwitchPrompt $switch_name
	CALSetSwitchMngrIPAddr $switch_name
}

Netgear_log_file "TC-OSPF-053.tcl" "Started to Modify TOPO"
Netgear_connect_switch $MT_SW_053
CALPortLinkDown $MT_SW_053 $port_down
Netgear_disconnect_switch $MT_SW_053

Netgear_log_file "TC-OSPF-053.tcl" "Started Configuration of VLAN"

foreach vlan_index $VLAN_index_list_053 {
	Netgear_log_file "TC-OSPF-053.tcl" "Started Configuration of VLAN $vlan_index"
	CALCreateVlan $vlan_index
}

Netgear_log_file "TC-OSPF-053.tcl" "Started routing on the Switches"

foreach switch_name $NTGR_CLEAR_CONFIG_SW_LIST_053 {
	Netgear_connect_switch $switch_name 
	CALRoutingEnable $switch_name
	Netgear_disconnect_switch $switch_name
}

Netgear_log_file "TC-STATIC-IP-053.tcl" "Started add static routes on switch"
foreach switch_name $ntgr_StaticIPList_053 {
    set address_list [getAddressList $switch_name]	
    CALAddStaticRoute $switch_name $address_list 
}
Netgear_log_file "TC-OSPF-053.tcl" "Started Configuration of OSPF"
foreach switch_name $ntgr_OSPF_List_053 {
	
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

sleep 5

set result3 [CALCheckExpect $SW_checked $cmds2 $expect_string_list2]

if {$result3 == 1} {
	Netgear_log_file "TC-OSPF-053.tcl" "***** static route has been received *****"
} else {
	Netgear_log_file "TC-OSPF-053.tcl" "***** static route has not been received *****"
}
################################ Delete static routes #################################
Netgear_log_file "TC-STATIC-IP-053.tcl" "Started to delete static routes on switch"
foreach switch_name $ntgr_StaticIPList_053 {
    set address_list [getAddressList $switch_name]	
    CALDeleteStaticRoute $switch_name $address_list 
}
###############################Check the Configuration ################################# 
for {set i 1} {$i <= 3} {incr i} {
	sleep 2
	set result1 [CALCheckExpect $SW_checked $cmds1 $expect_string_list1] 
}	
if {$result1 == 1} {
	set result2 [CALCheckExpect $SW_checked $cmds2 $expect_string_list2 0]
	if {$result2 == 1} {
		Netgear_log_file "TC-OSPF-053.tcl" "***** TC-OSPF-053.tcl passed *****"
	} else {
		Netgear_log_file "TC-OSPF-053.tcl" "***** TC-OSPF-053.tcl failed, connected route can not be received. *****"
	}
} else {
	Netgear_log_file "TC-OSPF-053.tcl" "***** TC-OSPF-053.tcl failed, neighbor is not full.*****"
}

Netgear_log_file "TC-OSPF-053.tcl" "***** Completed Test case TC-OSPF-053.tcl *****"

#*********************  End of Test case  ***************************************
