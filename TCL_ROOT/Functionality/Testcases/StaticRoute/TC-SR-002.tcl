#################################################################################
#
#  File Name	    	: TC-SR-002.tcl
#
#  Description     	: Next hop is IP address of VLAN
#
#  Config file          : TC-SR-002.cfg
#				  
#  Revision History 	:
#        Date          Programmer          Description
#        -----------------------------------------------------------------------
#        14/03/07      Nina Cheng          Created
#
#
##################################################################################

########################### start to configuration ###############################

Netgear_log_file "TC-SR-002.tcl" "******** Starting Test case TC-SR-002.tcl **********"

foreach switch_name $NTGR_CLEAR_CONFIG_SW_LIST_002 {
    Netgear_log_file "TC-SR-002.tcl" "Clear current configuration on switch $switch_name and configure some initial info"
    CALResetConfiguration $switch_name
    CALSetSwitchPrompt $switch_name
    CALSetSwitchMngrIPAddr $switch_name
}

Netgear_log_file "TC-SR-002.tcl" "Started routing on the Switches"

foreach switch_name $NTGR_CLEAR_CONFIG_SW_LIST_002 {
    Netgear_connect_switch $switch_name
    CALRoutingEnable $switch_name
    Netgear_disconnect_switch $switch_name
}

Netgear_log_file "TC-SR-002.tcl" "Started Configuration of VLAN"

foreach vlan_index $VLAN_SR_list_002 {
	Netgear_log_file "TC-SR-002.tcl" "Started Configuration of VLAN $vlan_index"
	CALCreateVlan $vlan_index
}

Netgear_log_file "TC-SR-002.tcl" "Started configuration of static routes"

foreach switch_name $DUT_SR_002 {
    set address_list [getAddressList $switch_name]	
    CALAddStaticRoute $switch_name $address_list
}

###############################Check the Configuration ################################# 
for {set i 1} {$i <= 3} {incr i} {
	sleep 2
	set result [CALCheckExpect $switch_checked $cmds $expect_string_list] 
}	
if {$result == 1} {Netgear_log_file "TC-SR-002.tcl" "***** TC-SR-002.tcl passed *****"} else {
Netgear_log_file "TC-SR-002.tcl" "***** TC-SR-002.tcl failed *****"}

Netgear_log_file "TC-SR-002.tcl" "***** Completed Test case TC-SR-002.tcl *****"

#*********************  End of Test case  ***************************************
