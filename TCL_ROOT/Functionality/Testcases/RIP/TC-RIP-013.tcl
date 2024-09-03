################################################################################
#
#  File Name	    	: TC-RIP-013.tcl
#
#  Description     	: Connected route metric value import test
#
#  Config file          : TC-RIP-013.cfg
#				  
#  Revision History 	:
#        Date          Programmer          Description
#        -----------------------------------------------------------------------
#        14/03/07      Nina Cheng          Created
#
#
##################################################################################

########################### start to configuration ###############################

Netgear_log_file "TC-RIP-013.tcl" "******** Starting Test case TC-RIP-013.tcl **********"

foreach switch_name $NTGR_CLEAR_CONFIG_SW_LIST_013 {
    Netgear_log_file "TC-RIP-013.tcl" "Clear current configuration on switch $switch_name and configure some initial info"
    CALResetConfiguration $switch_name
    CALSetSwitchPrompt $switch_name
    CALSetSwitchMngrIPAddr $switch_name
}

Netgear_log_file "TC-RIP-013.tcl" "Started to Modify TOPO"
foreach switch_name $MTSW_RIP_013 {
	CALAllPortsLinkDown $MTSW_RIP_013
}

Netgear_log_file "TC-RIP-013.tcl" "Started Configuration of VLAN"

foreach vlan_index $VLAN_RIP_list_013 {
	Netgear_log_file "TC-RIP-013.tcl" "Started Configuration of VLAN $vlan_index"
	CALCreateVlan $vlan_index
}

Netgear_log_file "TC-RIP-013.tcl" "Started routing on the Switches"

foreach {switch_name portlist} [array get ntgr_L3SwitchList_013] {
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

Netgear_log_file "TC-RIP-013.tcl" "Started Configuration of RIP"

foreach switch_name $ntgr_RIPList_013 {
	
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

for {set i 1} {$i <= 3} {incr i} {
	sleep 2
	set result [CALCheckExpect $switch_checked $cmds $expect_string_list] 
}
###############################Check the Configuration ################################# 	
if {$result == 1} {Netgear_log_file "TC-RIP-013.tcl" "***** TC-RIP-013.tcl passed *****"} else {
Netgear_log_file "TC-RIP-013.tcl" "***** TC-RIP-013.tcl failed *****"}

Netgear_log_file "TC-RIP-013.tcl" "***** Completed Test case TC-RIP-013.tcl *****"

#*********************  End of Test case  ***************************************
