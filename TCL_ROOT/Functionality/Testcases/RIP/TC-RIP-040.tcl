#################################################################################
#
#  File Name	    	: TC-RIP-040.tcl
#
#  Description     	: Working in the routing port
#
#  Config file          : TC-RIP-040.cfg
#				  
#  Revision History 	:
#        Date          Programmer          Description
#        -----------------------------------------------------------------------
#        14/03/07      Nina Cheng          Created
#
#
##################################################################################

########################### start to configuration ###############################

Netgear_log_file "TC-RIP-040.tcl" "******** Starting Test case TC-RIP-040.tcl **********"

foreach switch_name $NTGR_CLEAR_CONFIG_SW_LIST_040 {
    Netgear_log_file "TC-RIP-040.tcl" "Clear current configuration on switch $switch_name and configure some initial info"
    CALResetConfiguration $switch_name
    CALSetSwitchPrompt $switch_name
    CALSetSwitchMngrIPAddr $switch_name
}

foreach switch_name $MTSW_RIP_040 {
	Netgear_log_file "TC-RIP-040.tcl" "Started to Modify TOPO"
	CALAllPortsLinkDown $MTSW_RIP_040
}

Netgear_log_file "TC-RIP-001.tcl" "Started routing on the Switches"

foreach {switch_name portlist} [array get ntgr_L3SwitchList_040] {
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

Netgear_log_file "TC-RIP-040.tcl" "Started Configuration of RIP"

foreach switch_name $ntgr_RIPList_040 {
	
	set flag_enable [getRipGlobalStatus $switch_name]
        set interface_list [getRipInterface $switch_name]
        set flag_defroute  [getRipDefaultInformation $switch_name]

	Netgear_connect_switch $switch_name 
	
	CALRipEnableSwitch $switch_name $flag_enable
	CALRipEnableInterface $switch_name $interface_list
    	CALRipAddPropertyforLF $switch_name $interface_list
        CALRipEnableDefaultinformation $switch_name  $flag_defroute        
        
	Netgear_disconnect_switch $switch_name
}

###############################Check the Configuration ################################# 
for {set i 1} {$i <= 3} {incr i} {
	sleep 2
	set result [CALCheckExpect $switch_checked $cmds $expect_string_list] 
}	
if {$result == 1} {Netgear_log_file "TC-RIP-040.tcl" "***** TC-RIP-040.tcl passed *****"} else {
Netgear_log_file "TC-RIP-040.tcl" "***** TC-RIP-040.tcl failed *****"}

Netgear_log_file "TC-RIP-040.tcl" "***** Completed Test case TC-RIP-040.tcl *****"

#*********************  End of Test case  ***************************************
