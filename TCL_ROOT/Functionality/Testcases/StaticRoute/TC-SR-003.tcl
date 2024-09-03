#################################################################################
#
#  File Name	    	: TC-SR-003.tcl
#
#  Description     	: Route preference test - compare with OSPF inter route
#
#  Config file          : TC-SR-003.cfg
#				  
#  Revision History 	:
#        Date          Programmer          Description
#        -----------------------------------------------------------------------
#        14/03/07      Nina Cheng          Created
#
#
##################################################################################

########################### start to configuration ###############################

Netgear_log_file "TC-SR-003.tcl" "******** Starting Test case TC-SR-003.tcl **********"

foreach switch_name $NTGR_CLEAR_CONFIG_SW_LIST_003 {
    Netgear_log_file "TC-SR-003.tcl" "Clear current configuration on switch $switch_name and configure some initial info"
    CALResetConfiguration $switch_name
    CALSetSwitchPrompt $switch_name
    CALSetSwitchMngrIPAddr $switch_name
}

Netgear_log_file "TC-SR-003.tcl" "Started routing on the Switches"

foreach {switch_name portlist} [array get ntgr_L3SwitchList_003] {
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

Netgear_log_file "TC-RIP-003.tcl" "Started Configuring OSPF"

foreach switch_name $ntgr_OSPFList_003 {
	
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

Netgear_log_file "TC-SR-003.tcl" "Started Configuration of OSPF on IXIA"

foreach {chassis_id portlist} [array get ntgr_RouterPortList_003] {
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
	set result1 [CALCheckExpect $switch_checked $cmds $expect_string_list1] 
}
Netgear_log_file "TC-SR-003.tcl" "Started configuration of static routes"

foreach switch_name $DUT_SR_003 {
    set address_list [getAddressList $switch_name]	
    CALAddStaticRoute $switch_name $address_list
}

for {set i 1} {$i <= 3} {incr i} {
	sleep 2
	set result2 [CALCheckExpect $switch_checked $cmds $expect_string_list2] 
}
###############################Check the Configuration ################################# 	
if {$result1 == 1 && $result2 == 1} {
	Netgear_log_file "TC-SR-003.tcl" "***** TC-SR-003.tcl passed *****"
} else {
	Netgear_log_file "TC-SR-003.tcl" "***** TC-SR-003.tcl failed *****"
}

foreach {chassis_id portlist} [array get ntgr_RouterPortList_003] {
	foreach port $portlist {
		UALStopOSPFRouter $chassis_id $port
		UALClearOwnership $chassis_id $port
	}
	UALDisconnectTester $chassis_id
}
Netgear_log_file "TC-SR-003.tcl" "***** Completed Test case TC-SR-003.tcl *****"

#*********************  End of Test case  ***************************************
