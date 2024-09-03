#################################################################################
#
#  File Name	    	: TC-RIP-003.tcl
#
#  Description     	: default route test
#
#  Config file          : TC-RIP-003.cfg
#				  
#  Revision History 	:
#        Date          Programmer          Description
#        -----------------------------------------------------------------------
#        14/03/07      Nina Cheng          Created
#
#
##################################################################################

########################### start to configuration ###############################

Netgear_log_file "TC-RIP-003.tcl" "******** Starting Test case TC-RIP-003.tcl **********"

foreach switch_name $NTGR_CLEAR_CONFIG_SW_LIST_003 {
    Netgear_log_file "TC-RIP-003.tcl" "Clear current configuration on switch $switch_name and configure some initial info"
    CALResetConfiguration $switch_name
    CALSetSwitchPrompt $switch_name
    CALSetSwitchMngrIPAddr $switch_name
}

foreach switch_name $MTSW_RIP_003 {
	Netgear_log_file "TC-RIP-003.tcl" "Started to Modify TOPO"
	CALAllPortsLinkDown $MTSW_RIP_003
}
Netgear_log_file "TC-RIP-003.tcl" "Started routing on the Switches"

foreach switch_name $NTGR_CLEAR_CONFIG_SW_LIST_003 {
    Netgear_connect_switch $switch_name
    CALRoutingEnable $switch_name
    Netgear_disconnect_switch $switch_name
}

Netgear_log_file "TC-RIP-003.tcl" "Started Configuration of VLAN"

foreach vlan_index $VLAN_RIP_list_003 {
	Netgear_log_file "TC-RIP-003.tcl" "Started Configuration of VLAN $vlan_index"
	CALCreateVlan $vlan_index
}

Netgear_log_file "TC-RIP-003.tcl" "Started Configuration of RIP"

foreach switch_name $ntgr_RIPList_003 {
	
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
if {$result == 1} {Netgear_log_file "TC-RIP-003.tcl" "***** TC-RIP-003.tcl passed *****"} else {
Netgear_log_file "TC-RIP-003.tcl" "***** TC-RIP-003.tcl failed *****"}

Netgear_log_file "TC-RIP-003.tcl" "***** Completed Test case TC-RIP-003.tcl *****"

#*********************  End of Test case  ***************************************
