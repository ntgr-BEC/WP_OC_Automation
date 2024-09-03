################################################################################
#
#  File Name	    	: TC-OSPF-068.tcl
#
#  Description     	: Convergence time of 300 routes
#
#  Config file       	: TC-OSPF-068.cfg
#				  
#  Revision History 	:
#        Date          Programmer          Description
#        -----------------------------------------------------------------------
#        14/02/07      Nina Cheng          Created
#
#
##################################################################################

#########################start to configure the TOPO##############################

Netgear_log_file "TC-OSPF-068.tcl" "******** Starting Test case TC-OSPF-068.tcl **********"

foreach switch_name $NTGR_CLEAR_CONFIG_SW_LIST_068 {
    Netgear_log_file "TC-OSPF-068.tcl" "Clear current configuration on switch $switch_name and configure some initial info"
    CALResetConfiguration $switch_name
    CALSetSwitchPrompt $switch_name
    CALSetSwitchMngrIPAddr $switch_name
}

Netgear_log_file "TC-OSPF-068.tcl" "Started routing on the Switches"

foreach {switch_name portlist} [array get ntgr_L3SwitchList_068] {
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

Netgear_log_file "TC-OSPF-068.tcl" "Started Configuration of OSPF"

foreach switch_name $ntgr_OSPF_List_068 {
	
	set enable_flag [getOspfGlobalStatus $switch_name]
	set router_id [getOspfRouterID $switch_name]
	set interface_list [getOspfInterfaceList $switch_name]
	  
	Netgear_connect_switch $switch_name 
	
	CALOspfEnable $switch_name $enable_flag
	CALConfigOspfRouterID $switch_name $router_id		
	CALOspfInterfaceEnable $switch_name $interface_list
	CALOspfConfigAreaID $switch_name $interface_list
	
	Netgear_disconnect_switch $switch_name
}

Netgear_log_file "TC-OSPF-068.tcl" "Started Configuration of OSPF on IXIA"

foreach {chassis_id portlist} [array get ntgr_OspfPortList_068] {
	UALConnectTester $chassis_id
	foreach port $portlist {
		UALTakeOwnership $chassis_id $port		
		UALLoadPort $chassis_id $port
		UALStartOSPFRouter $chassis_id $port
	}
}
sleep 300
#	UALStartTrafficPerPort $ntgrTG $ntgrPort4
	UALStartTrafficPerPort $ntgrTG $ntgrPort2

#################################Check the Results #################################
sleep 10
set dif 0
set TxRx2 [UALReportPortRate $ntgrTG $ntgrPort2]
set TxRx4 [UALReportPortRate $ntgrTG $ntgrPort4]

set dif [expr [lindex $TxRx4 1]/[lindex $TxRx2 0]]

if {$dif < 0.5} {
	Netgear_log_file "TC-OSPF-068.tcl" "***** TC-OSPF-068.tcl failed, packets are lost. *****"
	} else {
	sleep 5
	Netgear_connect_switch $NTGR_CLEAR_CONFIG_SW_LIST_068
	CALPortLinkDown $NTGR_CLEAR_CONFIG_SW_LIST_068 $port_down
	Netgear_disconnect_switch $NTGR_CLEAR_CONFIG_SW_LIST_068
	sleep 10
	set TxRx2_1 [UALReportPortRate $ntgrTG $ntgrPort2]
	set dif_1 [expr [lindex $TxRx2_1 1]/[lindex $TxRx2_1 0]]
	if {$dif_1 > 0.9} {
		UALStopTrafficPerPort $ntgrTG $ntgrPort2
		set TfRf2 [UALGetPortFrameLoss $ntgrTG $ntgrPort2]
		set TfRf4 [UALGetPortFrameLoss $ntgrTG $ntgrPort4]
		set loss [expr [lindex $TfRf2 0] - [lindex $TfRf2 1] - [lindex $TfRf4 1]]
		set rate_send [lindex $TxRx2 0]
		set time_con 0
		set time_con [expr $loss / $rate_send]
		Netgear_log_file "TC-OSPF-068.tcl" "***** TC-OSPF-068.tcl passed, the convergence time is $time_con s. *****"	
	} else {
		Netgear_log_file "TC-OSPF-068.tcl" "***** TC-OSPF-068.tcl failed, packets are lost after changing next-hop. *****"
	}
		
}
Netgear_log_file "TC-OSPF-068.tcl" "***** Completed Test case TC-OSPF-068.tcl *****"

#foreach {chassis_id port} [array get ntgr_OspfPortList_068] {
#
#	UALStopOSPFRouter $chassis_id $port
#	UALClearOwnership $chassis_id $port
#	UALDisconnectTester $chassis_id
#}
#*********************  End of Test case  ***************************************
