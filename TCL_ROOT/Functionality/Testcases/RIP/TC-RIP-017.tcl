#################################################################################
#
#  File Name	    	: TC-RIP-017.tcl
#
#  Description     	: OSPF route metric value import test
#
#  Config file          : TC-RIP-017.cfg
#				  
#  Revision History 	:
#        Date          Programmer          Description
#        -----------------------------------------------------------------------
#        15/03/07      Nina Cheng          Created
#
##################################################################################

########################### start to configuration ###############################

Netgear_log_file "TC-RIP-017.tcl" "******** Starting Test case TC-RIP-017.tcl **********"

foreach switch_name $NTGR_CLEAR_CONFIG_SW_LIST_017 {
    Netgear_log_file "TC-RIP-017.tcl" "Clear current configuration on switch $switch_name and configure some initial info"
    CALResetConfiguration $switch_name
    CALSetSwitchPrompt $switch_name
    CALSetSwitchMngrIPAddr $switch_name
}

Netgear_log_file "TC-RIP-017.tcl" "Started to Modify TOPO"
CALAllPortsLinkDown $MTSW_RIP_017

Netgear_log_file "TC-RIP-017.tcl" "Started Configuration of VLAN"

foreach vlan_index $VLAN_RIP_list_017 {
	Netgear_log_file "TC-RIP-017.tcl" "Started Configuration of VLAN $vlan_index"
	CALCreateVlan $vlan_index
}

Netgear_log_file "TC-RIP-017.tcl" "Started routing on the Switches"

foreach {switch_name portlist} [array get ntgr_L3SwitchList_017] {
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

Netgear_log_file "TC-RIP-017.tcl" "Started Configuring OSPF"

foreach switch_name $ntgr_OSPFList_017 {
	
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

Netgear_log_file "TC-RIP-017.tcl" "Started Configuration of RIP"

foreach switch_name $ntgr_RIPList_017 {
	
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

Netgear_log_file "TC-OSPF-067.tcl" "Started Configuration of OSPF on IXIA"

foreach {chassis_id portlist} [array get ntgr_RouterPortList_017] {
	UALConnectTester $chassis_id
	foreach port $portlist {
	    UALTakeOwnership $chassis_id $port
	    UALLoadPort $chassis_id $port
	    UALStartOSPFRouter $chassis_id $port
	}
}
sleep 50

for {set i 1} {$i <= 3} {incr i} {
	sleep 2
	set result [CALCheckExpect $switch_checked $cmds $expect_string_list] 
}

###############################Check the Configuration ################################# 	
if {$result == 1} {Netgear_log_file "TC-RIP-017.tcl" "***** TC-RIP-017.tcl passed *****"} else {
Netgear_log_file "TC-RIP-017.tcl" "***** TC-RIP-017.tcl failed *****"}

foreach {chassis_id portlist} [array get ntgr_RouterPortList_017] {
	foreach port $portlist {
		UALStopOSPFRouter $chassis_id $port
		UALClearOwnership $chassis_id $port
	}
	UALDisconnectTester $chassis_id
}

Netgear_log_file "TC-RIP-017.tcl" "***** Completed Test case TC-RIP-017.tcl *****"

#*********************  End of Test case  ***************************************
