################################################################################
#
#  File Name	    	: TC-OSPF-060.tcl
#
#  Description     	: Virtual link with maximum external routes and maximum 
#			  neighbors
#
#  Config file       	: TC-OSPF-060.cfg
#				  
#  Revision History 	:
#        Date          Programmer          Description
#        -----------------------------------------------------------------------
#        14/02/07      Nina Cheng          Created
#
#
##################################################################################

#########################start to configure the TOPO##############################

Netgear_log_file "TC-OSPF-060.tcl" "******** Starting Test case TC-OSPF-060.tcl **********"

foreach switch_name $NTGR_CLEAR_CONFIG_SW_LIST_060 {
    Netgear_log_file "TC-OSPF-060.tcl" "Clear current configuration on switch $switch_name and configure some initial info"
    CALResetConfiguration $switch_name
    CALSetSwitchPrompt $switch_name
    CALSetSwitchMngrIPAddr $switch_name
}

Netgear_log_file "TC-OSPF-060.tcl" "Started routing on the Switches"

    foreach {switch_name portlist} [array get ntgr_L3SwitchList_060] {
    Netgear_connect_switch $switch_name
    CALRoutingEnable $switch_name
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
    Netgear_disconnect_switch $switch_name
}

Netgear_log_file "TC-OSPF-060.tcl" "Started Configuration of VLAN"

foreach vlan_index $VLAN_index_list_060 {
	Netgear_log_file "TC-OSPF-060.tcl" "Started Configuration of VLAN $vlan_index"
	CALCreateVlan $vlan_index
}

Netgear_log_file "TC-OSPF-060.tcl" "Started Configuration of OSPF"

foreach switch_name $ntgr_OSPF_List_060 {
	
	set enable_flag [getOspfGlobalStatus $switch_name]
	set router_id [getOspfRouterID $switch_name]
	set interface_list [getOspfInterfaceList $switch_name]
	set vl_flag [getOspfVirtualLinkStatus $switch_name]
	  
	Netgear_connect_switch $switch_name 
	
	CALOspfEnable $switch_name $enable_flag
	CALConfigOspfRouterID $switch_name $router_id		
	CALOspfInterfaceEnable $switch_name $interface_list
	CALOspfConfigAreaID $switch_name $interface_list
	CALOspfVLinkEnable $switch_name $vl_flag
	
	Netgear_disconnect_switch $switch_name
}

Netgear_log_file "TC-OSPF-060.tcl" "Started Configuration of OSPF on IXIA"

foreach {chassis_id portlist} [array get ntgr_OspfPortList_060] {
	UALConnectTester $chassis_id
	foreach port $portlist {
		UALTakeOwnership $chassis_id $port
		UALLoadPort $chassis_id $port
		UALStartOSPFRouter $chassis_id $port
	}
}

sleep 50

###############################Check the Configuration ################################# 
for {set i 1} {$i <= 3} {incr i} {
	sleep 3
	set switch_checked [lindex $ntgr_OSPF_List_060 0]
	set result [CALCheckExpect $SW_checked $cmds $expect_string_list] 
}	
if {$result == 1} {Netgear_log_file "TC-OSPF-060.tcl" "***** TC-OSPF-060.tcl passed *****"} else {
Netgear_log_file "TC-OSPF-060.tcl" "***** TC-OSPF-060.tcl failed *****"}
Netgear_log_file "TC-OSPF-060.tcl" "***** Completed Test case TC-OSPF-060.tcl *****"

Netgear_log_file "TC-OSPF-060.tcl" "Started Configuration of OSPF on IXIA"

#foreach {chassis_id port} [array get ntgr_OspfPortList_060] {
#
#	UALStopOSPFRouter $chassis_id $port
#	UALClearOwnership $chassis_id $port
#	UALDisconnectTester $chassis_id
#}
#*********************  End of Test case  ***************************************
