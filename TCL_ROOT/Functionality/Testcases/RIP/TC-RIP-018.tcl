#################################################################################
#
#  File Name	    	: TC-RIP-018.tcl
#
#  Description     	: distribute-list test --- source routes are static
#
#  Config file          : TC-RIP-018.cfg
#				  
#  Revision History 	:
#        Date          Programmer          Description
#        -----------------------------------------------------------------------
#        15/03/07      Nina Cheng          Created
#
##################################################################################

########################### start to configuration ###############################

Netgear_log_file "TC-RIP-018.tcl" "******** Starting Test case TC-RIP-018.tcl **********"

foreach switch_name $NTGR_CLEAR_CONFIG_SW_LIST_018 {
    Netgear_log_file "TC-RIP-018.tcl" "Clear current configuration on switch $switch_name and configure some initial info"
    CALResetConfiguration $switch_name
    CALSetSwitchPrompt $switch_name
    CALSetSwitchMngrIPAddr $switch_name
}

Netgear_log_file "TC-RIP-018.tcl" "Started to Modify TOPO"
CALAllPortsLinkDown $MTSW_RIP_018

Netgear_log_file "TC-RIP-018.tcl" "Started Configuration of VLAN"

foreach vlan_index $VLAN_RIP_list_018 {
	Netgear_log_file "TC-RIP-018.tcl" "Started Configuration of VLAN $vlan_index"
	CALCreateVlan $vlan_index
}

Netgear_log_file "TC-RIP-018.tcl" "Started configuration static routes on the switches"

foreach switch_name $ntgr_StaticIPList_018 {
    set address_list [getAddressList $switch_name]	
    CALAddStaticRoute $switch_name $address_list
}

Netgear_log_file "TC-RIP-018.tcl" "Started routing on the Switches"

foreach {switch_name portlist} [array get ntgr_L3SwitchList_018] {
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


Netgear_log_file "TC-RIP-018.tcl" "Started Configuration of RIP"

foreach switch_name $ntgr_RIPList_018 {
	
	set flag_enable [getRipGlobalStatus $switch_name]
        set interface_list [getRipInterface $switch_name]
	set redistribute_list [getRipRedistribute $switch_name]
        set distribute_list [getRipDistributeListOut $switch_name]
    
	  
	Netgear_connect_switch $switch_name 
	
	CALRipEnableSwitch $switch_name $flag_enable
	CALRipEnableInterface $switch_name $interface_list
    	CALRipAddPropertyforLF $switch_name $interface_list
	CALRipEnableRedistribute $switch_name $redistribute_list
        CALRipEnableDistributelistOut $switch_name $distribute_list
        
	Netgear_disconnect_switch $switch_name
}

sleep 5

for {set i 1} {$i <= 3} {incr i} {
	sleep 2
	set result1 [CALCheckExpect $switch_checked $cmds $expect_string_list] 
}

foreach switch_name $ntgr_IP_ACL_List_018 {
  
	set ip_acl_id_list [getIPACLID $switch_name]
	set ip_acl_rule_list [ getIPACLRule $switch_name]
	
	CALAddIPACL $switch_name $ip_acl_rule_list
}

sleep 5

for {set i 1} {$i <= 3} {incr i} {
	sleep 2
	set result2 [CALCheckExpect $switch_checked $cmds $expect_string_list 0] 
}
###############################Check the Configuration ################################# 	
if {$result1 == 1 && $result2 == 1} {
	Netgear_log_file "TC-RIP-018.tcl" "***** TC-RIP-018.tcl passed *****"} else {
Netgear_log_file "TC-RIP-018.tcl" "***** TC-RIP-018.tcl failed *****"}

Netgear_log_file "TC-RIP-018.tcl" "***** Completed Test case TC-RIP-018.tcl *****"

#*********************  End of Test case  ***************************************
