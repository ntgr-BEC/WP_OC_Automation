#################################################################################
#
#  File Name	    	: TC-SR-017.tcl
#
#  Description     	: Configure a large number of static routes and redistribute
#			  them to RIP by ACL, running a long time
#
#  Config file          : TC-SR-017.cfg
#				  
#  Revision History 	:
#        Date          Programmer          Description
#        -----------------------------------------------------------------------
#        28/03/07      Nina Cheng          Created
#
#
##################################################################################

########################### start to configuration ###############################

Netgear_log_file "TC-SR-017.tcl" "******** Starting Test case TC-SR-017.tcl **********"

foreach switch_name $NTGR_CLEAR_CONFIG_SW_LIST_017 {
    Netgear_log_file "TC-SR-017.tcl" "Clear current configuration on switch $switch_name and configure some initial info"
    CALResetConfiguration $switch_name
    CALSetSwitchPrompt $switch_name
    CALSetSwitchMngrIPAddr $switch_name
}

Netgear_log_file "TC-SR-017.tcl" "Started routing on the Switches"

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

Netgear_log_file "TC-SR-017.tcl" "Started Configuration of VLAN"

foreach vlan_index $VLAN_SR_list_017 {
	Netgear_log_file "TC-SR-017.tcl" "Started Configuration of VLAN $vlan_index"
	CALCreateVlan $vlan_index
}

Netgear_log_file "TC-SR-017.tcl" "Started configuration of static routes"

foreach switch_name $DUT_SR_017 {
    set address_list [getAddressList $switch_name]	
    CALAddStaticRoute $switch_name $address_list 
}

Netgear_log_file "TC-RIP-017.tcl" "Started Configuration of RIP"

foreach switch_name $ntgr_RIPList_017 {
	
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

foreach switch_name $ntgr_IP_ACL_List_017 {
  
	set ip_acl_id_list [getIPACLID $switch_name]
	set ip_acl_rule_list [ getIPACLRule $switch_name]
	
	CALAddIPACL $switch_name $ip_acl_rule_list
}

sleep 50

Netgear_log_file "TC-RIP-017.tcl" "Started Configuration of IXIA"

foreach {chassis_id portlist} [array get ntgr_RouterPortList_017] {
	UALConnectTester $chassis_id
	foreach port $portlist {
		UALTakeOwnership $chassis_id $port
		UALLoadPort $chassis_id $port
	}
}

UALStartTrafficPerPort $ntgrTG $ntgrPort1

sleep 10

set dif 0
set TxRx1 [UALReportPortRate $ntgrTG $ntgrPort1]
set TxRx2 [UALReportPortRate $ntgrTG $ntgrPort2]

set dif [expr [lindex $TxRx1 0]/[lindex $TxRx2 1]]

for {set i 1} {$i <= 2} {incr i} {
	foreach switch_name $DUT_SR_017 {
    		set address_list [getAddressList $switch_name]
    		CALDeleteStaticRoute $switch_name $address_list
    		sleep 2	
    		CALAddStaticRoute $switch_name $address_list
    		sleep 2 
	}	
}	

sleep 50

set dif_1 0
set TxRx1_1 [UALReportPortRate $ntgrTG $ntgrPort1]
set TxRx2_1 [UALReportPortRate $ntgrTG $ntgrPort2]

set dif_1 [expr [lindex $TxRx1_1 0]/[lindex $TxRx2_1 1]]

if {$dif >= 0.9 && $dif_1 >= 0.9} {
	Netgear_log_file "TC-SR-017.tcl" "***** TC-SR-017.tcl passed *****"
} else {
	Netgear_log_file "TC-SR-017.tcl" "***** TC-SR-017.tcl failed *****"
}

UALStopTrafficPerPort $ntgrTG $ntgrPort1

foreach {chassis_id portlist} [array get ntgr_RouterPortList_017] {
	foreach port $portlist {
		UALStopRIPRouter $chassis_id $port
		UALClearOwnership $chassis_id $port
	}
	UALDisconnectTester $chassis_id
}

Netgear_log_file "TC-SR-017.tcl" "***** Completed Test case TC-SR-017.tcl *****"

#*********************  End of Test case  ***************************************
