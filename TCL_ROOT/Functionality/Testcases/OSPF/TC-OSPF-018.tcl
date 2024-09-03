#################################################################################
#
#  File Name	    	: TC-OSPF-018.tcl
#
#  Description     	: Filter connected routes
#
#  Config file       	: TC-OSPF-018.cfg
#				  
#  Revision History 	:
#        Date          Programmer          Description
#        -----------------------------------------------------------------------
#        06/02/07      Nina Cheng          Created
#
#
##################################################################################

#########################start to configure the TOPO##############################

Netgear_log_file "TC-OSPF-018.tcl" "******** Starting Test case TC-OSPF-018.tcl **********"

foreach switch_name $NTGR_CLEAR_CONFIG_SW_LIST_018 {
	Netgear_log_file "TC-OSPF-018.tcl" "Clear current configuration on switch $switch_name and configure some initial info"
	CALResetConfiguration $switch_name
	CALSetSwitchPrompt $switch_name
	CALSetSwitchMngrIPAddr $switch_name
}

Netgear_log_file "TC-OSPF-018.tcl" "Started to Modify TOPO"
Netgear_connect_switch $MT_SW_018
CALPortLinkDown $MT_SW_018 $port_down
Netgear_disconnect_switch $MT_SW_018

Netgear_log_file "TC-OSPF-018.tcl" "Started Configuration of VLAN"

foreach vlan_index $VLAN_index_list_018 {
	Netgear_log_file "TC-OSPF-018.tcl" "Started Configuration of VLAN $vlan_index"
	CALCreateVlan $vlan_index
}

Netgear_log_file "TC-OSPF-018.tcl" "Started routing on the Switches"

foreach {switch_name portlist} [array get ntgr_L3SwitchList_018] {
    Netgear_connect_switch $switch_name
    CALRoutingEnable $switch_name
    if {$switch_name == $SW_CROUTE} {
    	foreach port_property $portlist {
        	set port [lindex $port_property 0]
        	CALRoutingPortEnable $switch_name $port
        	set num1 [llength $port_property]
        	set num [expr $num1 - 1]
        	for {set i 1} {$i <= $num} {incr i} {
            		set ip_addr [lindex $port_property $i]
            		CALAddIPtoPort $switch_name $port $ip_addr
        	}
    	}
    }
    Netgear_disconnect_switch $switch_name
}

Netgear_log_file "TC-OSPF-018.tcl" "Started Configuration of OSPF"

foreach switch_name $ntgr_OSPF_List_018 {
	
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
		Netgear_log_file "TC-OSPF-018.tcl" "***** Connected routes have been received. *****"
	} else {
		Netgear_log_file "TC-OSPF-018.tcl" "***** Connected routes have not been received. *****"
	}
} else {
	Netgear_log_file "TC-OSPF-018.tcl" "***** Neighbor isn't full.*****"
}
if {$result1 == 1 && $result2 == 1} {
	Netgear_log_file "TC-OSPF-018.tcl" "Started Configuration of IP ACL"

	foreach switch_name $ntgr_IP_ACL_List_018 {
  
  		set ip_acl_id_list [getIPACLID $switch_name]
  		set ip_acl_rule_list [ getIPACLRule $switch_name]
 
  		CALAddIPACL $switch_name $ip_acl_rule_list
	}
}

sleep 5
set result3 [CALCheckExpect $SW_checked $cmds2 $expect_string_list2 0]

if {$result3 == 1} {
	Netgear_log_file "TC-OSPF-018.tcl" "***** TC-OSPF-018.tcl passed *****"
} else {
	Netgear_log_file "TC-OSPF-018.tcl" "***** TC-OSPF-018.tcl failed *****"
}
Netgear_log_file "TC-OSPF-018.tcl" "***** Completed Test case TC-OSPF-018.tcl *****"

#*********************  End of Test case  ***************************************
