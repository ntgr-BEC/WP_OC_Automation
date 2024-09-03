#################################################################################
#
#  File Name	    	: TC-RIP-016.tcl
#
#  Description     	: Introduction of OSPF routes conflicting with RIP routes
#
#  Config file          : TC-RIP-016.cfg
#				  
#  Revision History 	:
#        Date          Programmer          Description
#        -----------------------------------------------------------------------
#        15/03/07      Nina Cheng          Created
#
##################################################################################

########################### start to configuration ###############################

Netgear_log_file "TC-RIP-016.tcl" "******** Starting Test case TC-RIP-016.tcl **********"

foreach switch_name $NTGR_CLEAR_CONFIG_SW_LIST_016 {
    Netgear_log_file "TC-RIP-016.tcl" "Clear current configuration on switch $switch_name and configure some initial info"
    CALResetConfiguration $switch_name
    CALSetSwitchPrompt $switch_name
    CALSetSwitchMngrIPAddr $switch_name
}

Netgear_log_file "TC-RIP-016.tcl" "Started to Modify TOPO"
CALAllPortsLinkDown $MTSW_RIP_016
CALPortLinkDown $SW_TC_016 $port_down

Netgear_log_file "TC-RIP-016.tcl" "Started Configuration of VLAN"

foreach vlan_index $VLAN_RIP_list_016 {
	Netgear_log_file "TC-RIP-016.tcl" "Started Configuration of VLAN $vlan_index"
	CALCreateVlan $vlan_index
}

Netgear_log_file "TC-RIP-016.tcl" "Started routing on the Switches"

foreach {switch_name portlist} [array get ntgr_L3SwitchList_016] {
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

Netgear_log_file "TC-RIP-016.tcl" "Started Configuring OSPF"

foreach switch_name $ntgr_OSPFList_016 {
	
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

Netgear_log_file "TC-RIP-016.tcl" "Started Configuration of RIP"

foreach switch_name $ntgr_RIPList_016 {
	
	set flag_enable [getRipGlobalStatus $switch_name]
        set interface_list [getRipInterface $switch_name]
	set redistribute_list [getRipRedistribute $switch_name]
    
	  
	Netgear_connect_switch $switch_name 
	
	CALRipEnableSwitch $switch_name $flag_enable
	CALRipEnableInterface $switch_name $interface_list
    	CALRipAddPropertyforLF $switch_name $interface_list
	CALRipEnableRedistribute $switch_name $redistribute_list
        
	Netgear_disconnect_switch $switch_name
}

Netgear_log_file "TC-OSPF-016.tcl" "Started Configuration of OSPF on IXIA"

foreach {chassis_id portlist} [array get ntgr_RouterPortList_016] {
	UALConnectTester $chassis_id
	foreach port $portlist {
	    UALTakeOwnership $chassis_id $port
	    UALLoadPort $chassis_id $port
	    UALStartOSPFRouter $chassis_id $port
	}
}

sleep 90
set result 0;
set result1 0;
set result2 0;

for {set i 1} {$i <= 3} {incr i} {
	sleep 1
	set result [CALCheckExpect $switch_checked $cmds $expect_string_list]
}

if {$result == 1} {
	for {set i 1} {$i <= 3} {incr i} {
		sleep 2
		set result1 [CALCheckExpect $switch_checked $cmds $expect_string_list1]
	}
}
CALPortLinkUp $SW_TC_016 $port_down

sleep 90

for {set i 1} {$i <= 3} {incr i} {
	set result [CALCheckExpect $switch_checked $cmds $expect_string_list]
}

if {$result == 1} {
	for {set i 1} {$i <= 3} {incr i} {
		sleep 2
		set result2 [CALCheckExpect $switch_checked $cmds $expect_string_list2]
	}
}

###############################Check the Configuration ################################# 	
if {$result1 == 1 && $result2 == 1} {Netgear_log_file "TC-RIP-016.tcl" "***** TC-RIP-016.tcl passed *****"} else {
Netgear_log_file "TC-RIP-016.tcl" "***** TC-RIP-016.tcl failed *****"}

foreach {chassis_id portlist} [array get ntgr_RouterPortList_016] {
	foreach port $portlist {
		UALStopOSPFRouter $chassis_id $port
		UALClearOwnership $chassis_id $port
	}
	UALDisconnectTester $chassis_id
}

Netgear_log_file "TC-RIP-016.tcl" "***** Completed Test case TC-RIP-016.tcl *****"

#*********************  End of Test case  ***************************************
