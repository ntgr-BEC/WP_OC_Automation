#################################################################################
#
#  File Name	    	: TC-RIP-033.tcl
#
#  Description     	: Memory leakage test
#
#  Config file          : TC-RIP-033.cfg
#				  
#  Revision History 	:
#        Date          Programmer          Description
#        -----------------------------------------------------------------------
#        19/03/07      Nina Cheng          Created
#
#
##################################################################################

########################### start to configuration ###############################

Netgear_log_file "TC-RIP-033.tcl" "******** Starting Test case TC-RIP-033.tcl **********"

foreach switch_name $NTGR_CLEAR_CONFIG_SW_LIST_033 {
    Netgear_log_file "TC-RIP-033.tcl" "Clear current configuration on switch $switch_name and configure some initial info"
    CALResetConfiguration $switch_name
    CALSetSwitchPrompt $switch_name
    CALSetSwitchMngrIPAddr $switch_name
}

foreach switch_name $MTSW_RIP_033 {
	Netgear_log_file "TC-RIP-033.tcl" "Started to Modify TOPO"
	CALAllPortsLinkDown $MTSW_RIP_033
}

Netgear_log_file "TC-RIP-033.tcl" "Started Configuration of VLAN"

foreach vlan_index $VLAN_RIP_list_033 {
	Netgear_log_file "TC-RIP-033.tcl" "Started Configuration of VLAN $vlan_index"
	CALCreateVlan $vlan_index
}

Netgear_log_file "TC-RIP-033.tcl" "Started routing on the Switches"

foreach {switch_name portlist} [array get ntgr_L3SwitchList_033] {
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

Netgear_log_file "TC-RIP-033.tcl" "Started Configuration of RIP"

foreach switch_name $ntgr_RIPList_033 {
	
	set flag_enable [getRipGlobalStatus $switch_name]
        set interface_list [getRipInterface $switch_name]
	  
	Netgear_connect_switch $switch_name 
	
	CALRipEnableSwitch $switch_name $flag_enable
	CALRipEnableInterface $switch_name $interface_list
    	CALRipAddPropertyforLF $switch_name $interface_list
        
	Netgear_disconnect_switch $switch_name
}

Netgear_log_file "TC-RIP-033.tcl" "Started Configuration of RIP on IXIA"

foreach {chassis_id portlist} [array get ntgr_RipPortList_033] {
	UALConnectTester $chassis_id
	foreach port $portlist {
		UALTakeOwnership $chassis_id $port
		UALLoadPort $chassis_id $port
		UALStartRIPRouter $chassis_id $port
	}
}

sleep 50

UALStartTrafficPerPort $ntgrTG $ntgrPort2

sleep 10

set dif 0
set TxRx1 [UALReportPortRate $ntgrTG $ntgrPort1]
set TxRx2 [UALReportPortRate $ntgrTG $ntgrPort2]

set dif [expr [lindex $TxRx1 1]/[lindex $TxRx2 0]]

if {$dif < 0.5} {
	Netgear_log_file "TC-OSPF-033.tcl" "***** TC-OSPF-033.tcl failed, packets are lost. *****"
	} else {
	Netgear_connect_switch $SW_TPCHANGED
	for {set i 1} { $i <= 10 } {incr i} {
		CALRipDisableSwitch $SW_TPCHANGED ""
		sleep 2
		CALRipEnableSwitch $SW_TPCHANGED ""
		sleep 2
	}
	Netgear_disconnect_switch $SW_TPCHANGED
	sleep 50
	set dif_1 0
	set TxRx1 [UALReportPortRate $ntgrTG $ntgrPort1]
	set TxRx2 [UALReportPortRate $ntgrTG $ntgrPort2]
	set dif_1 [expr [lindex $TxRx1 1]/[lindex $TxRx2 0]]
		
	if {$dif_1 > 0.9} {
		Netgear_log_file "TC-OSPF-033.tcl" "***** TC-OSPF-033.tcl passed. *****"	
	} else {
		Netgear_log_file "TC-OSPF-033.tcl" "***** TC-OSPF-033.tcl failed, packets are lost after flapping *****"
	}	
}

UALStopTrafficPerPort $ntgrTG $ntgrPort2

foreach {chassis_id portlist} [array get ntgr_RipPortList_033] {
	foreach port $portlist {
		UALStopRIPRouter $chassis_id $port
		UALClearOwnership $chassis_id $port
	}
	UALDisconnectTester $chassis_id
}

Netgear_log_file "TC-RIP-033.tcl" "***** Completed Test case TC-RIP-033.tcl *****"

#*********************  End of Test case  ***************************************
