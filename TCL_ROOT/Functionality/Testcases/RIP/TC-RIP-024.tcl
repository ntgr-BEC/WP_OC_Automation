#################################################################################
#
#  File Name	    	: TC-RIP-024.tcl
#
#  Description     	: Routes flapping by shutdown/ no shutdown ports frequently
#
#  Config file          : TC-RIP-024.cfg
#				  
#  Revision History 	:
#        Date          Programmer          Description
#        -----------------------------------------------------------------------
#        16/03/07      Nina Cheng          Created
#
#
##################################################################################

########################### start to configuration ###############################

Netgear_log_file "TC-RIP-024.tcl" "******** Starting Test case TC-RIP-024.tcl **********"

foreach switch_name $NTGR_CLEAR_CONFIG_SW_LIST_024 {
    Netgear_log_file "TC-RIP-024.tcl" "Clear current configuration on switch $switch_name and configure some initial info"
    CALResetConfiguration $switch_name
    CALSetSwitchPrompt $switch_name
    CALSetSwitchMngrIPAddr $switch_name
}

foreach switch_name $MTSW_RIP_024 {
	Netgear_log_file "TC-RIP-024.tcl" "Started to Modify TOPO"
	CALAllPortsLinkDown $MTSW_RIP_024
}

Netgear_log_file "TC-RIP-024.tcl" "Started Configuration of VLAN"

foreach vlan_index $VLAN_RIP_list_024 {
	Netgear_log_file "TC-RIP-024.tcl" "Started Configuration of VLAN $vlan_index"
	CALCreateVlan $vlan_index
}

Netgear_log_file "TC-RIP-024.tcl" "Started routing on the Switches"

foreach {switch_name portlist} [array get ntgr_L3SwitchList_024] {
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

Netgear_log_file "TC-RIP-024.tcl" "Started Configuration of RIP"

foreach switch_name $ntgr_RIPList_024 {
	
	set flag_enable [getRipGlobalStatus $switch_name]
        set interface_list [getRipInterface $switch_name]
	  
	Netgear_connect_switch $switch_name 
	
	CALRipEnableSwitch $switch_name $flag_enable
	CALRipEnableInterface $switch_name $interface_list
    	CALRipAddPropertyforLF $switch_name $interface_list
        
	Netgear_disconnect_switch $switch_name
}

Netgear_log_file "TC-RIP-024.tcl" "Started Configuration of RIP on IXIA"

foreach {chassis_id portlist} [array get ntgr_RipPortList_024] {
	UALConnectTester $chassis_id
	foreach port $portlist {
		UALTakeOwnership $chassis_id $port
		UALLoadPort $chassis_id $port
		UALStartRIPRouter $chassis_id $port
	}
}

sleep 15

for {set i 1} {$i <= 3} {incr i} {
	sleep 2
	set result1 [CALCheckExpect $switch_checked $cmds $expect_string_list] 
}

for {set i 1} {$i <= 5} {incr i} {
sleep 1
CALPortLinkDown $SW_TPCHANGED $port_updown
sleep 1
CALPortLinkUp $SW_TPCHANGED $port_updown
}

for {set i 1} {$i <= 3} {incr i} {
	sleep 2
	set result2 [CALCheckExpect $switch_checked $cmds $expect_string_list] 
}

###############################Check the Configuration ################################# 
	
if {$result1 == 1 && $result2 == 1} {
	Netgear_log_file "TC-RIP-024.tcl" "***** TC-RIP-024.tcl passed *****"} else {
	Netgear_log_file "TC-RIP-024.tcl" "***** TC-RIP-024.tcl failed *****"
}

foreach {chassis_id portlist} [array get ntgr_RipPortList_024] {
	foreach port $portlist {
		UALStopRIPRouter $chassis_id $port
		UALClearOwnership $chassis_id $port
	}
	UALDisconnectTester $chassis_id
}

Netgear_log_file "TC-RIP-024.tcl" "***** Completed Test case TC-RIP-024.tcl *****"

#*********************  End of Test case  ***************************************
