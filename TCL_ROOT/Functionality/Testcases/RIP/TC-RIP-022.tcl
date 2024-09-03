#################################################################################
#
#  File Name	    	: TC-RIP-022.tcl
#
#  Description     	: Distance test --- RIP management distance setting
#
#  Config file          : TC-RIP-022.cfg
#				  
#  Revision History 	:
#        Date          Programmer          Description
#        -----------------------------------------------------------------------
#        16/03/07      Nina Cheng          Created
#
##################################################################################

########################### start to configuration ###############################

Netgear_log_file "TC-RIP-022.tcl" "******** Starting Test case TC-RIP-022.tcl **********"

foreach switch_name $NTGR_CLEAR_CONFIG_SW_LIST_022 {
    Netgear_log_file "TC-RIP-022.tcl" "Clear current configuration on switch $switch_name and configure some initial info"
    CALResetConfiguration $switch_name
    CALSetSwitchPrompt $switch_name
    CALSetSwitchMngrIPAddr $switch_name
}

Netgear_log_file "TC-RIP-022.tcl" "Started to Modify TOPO"
CALAllPortsLinkDown $MTSW_RIP_022

Netgear_log_file "TC-RIP-022.tcl" "Started routing on the Switches"

foreach {switch_name portlist} [array get ntgr_L3SwitchList_022] {
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

Netgear_log_file "TC-RIP-022.tcl" "Started Configuration of RIP"

foreach switch_name $ntgr_RIPList_022 {
	
	set flag_enable [getRipGlobalStatus $switch_name]
        set interface_list [getRipInterface $switch_name]
	  
	Netgear_connect_switch $switch_name 
	
	CALRipEnableSwitch $switch_name $flag_enable
	CALRipEnableInterface $switch_name $interface_list
    	CALRipAddPropertyforLF $switch_name $interface_list
	
	Netgear_disconnect_switch $switch_name 
}

Netgear_log_file "TC-RIP-022.tcl" "Started Configuration of RIP on IXIA"

foreach {chassis_id portlist} [array get ntgr_RouterPortList_022] {
	UALConnectTester $chassis_id
	foreach port $portlist {
	    UALTakeOwnership $chassis_id $port
	    UALLoadPort $chassis_id $port
	    UALStartRIPRouter $chassis_id $port
	}
}
sleep 5

for {set i 1} {$i <= 3} {incr i} {
	sleep 2
	set result1 [CALCheckExpect $switch_checked $cmds $expect_string_list] 
}

Netgear_log_file "TC-RIP-022.tcl" "Started modify the distance for RIP"

Netgear_connect_switch $SW_RD
CALRipEnableDistance $SW_RD $distance_new
Netgear_disconnect_switch $SW_RD

sleep 5

for {set i 1} {$i <= 3} {incr i} {
	sleep 2
	set result2 [CALCheckExpect $switch_checked $cmds $expect_string_list 0] 
}

Netgear_connect_switch $SW_RD
CALRipDisableDistance $SW_RD $distance_new
Netgear_disconnect_switch $SW_RD

sleep 5

for {set i 1} {$i <= 3} {incr i} {
	sleep 2
	set result3 [CALCheckExpect $switch_checked $cmds $expect_string_list] 
}
###############################Check the Configuration ################################# 	
if {$result1 == 1 && $result2 == 1 && $result3 == 1} {
	Netgear_log_file "TC-RIP-022.tcl" "***** TC-RIP-022.tcl passed *****"} else {
Netgear_log_file "TC-RIP-022.tcl" "***** TC-RIP-022.tcl failed *****"}

foreach {chassis_id portlist} [array get ntgr_RouterPortList_022] {
	foreach port $portlist {
		UALStopRIPRouter $chassis_id $port
		UALClearOwnership $chassis_id $port
	}
	UALDisconnectTester $chassis_id
}

Netgear_log_file "TC-RIP-022.tcl" "***** Completed Test case TC-RIP-022.tcl *****"

#*********************  End of Test case  ***************************************
