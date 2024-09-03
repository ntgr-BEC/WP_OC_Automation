################################################################################
#
#  File Name	    	: TC-OSPF-066.tcl
#
#  Description     	: specification test - number of type-3 lsa
#
#  Config file       	: TC-OSPF-066.cfg
#				  
#  Revision History 	:
#        Date          Programmer          Description
#        -----------------------------------------------------------------------
#        06/02/07      Nina Cheng          Created
#
#
##################################################################################

#########################start to configure the TOPO##############################

Netgear_log_file "TC-OSPF-066.tcl" "******** Starting Test case TC-OSPF-066.tcl **********"

foreach switch_name $NTGR_CLEAR_CONFIG_SW_LIST_066 {
    Netgear_log_file "TC-OSPF-066.tcl" "Clear current configuration on switch $switch_name and configure some initial info"
    CALResetConfiguration $switch_name
    CALSetSwitchPrompt $switch_name
    CALSetSwitchMngrIPAddr $switch_name
}

Netgear_log_file "TC-OSPF-066.tcl" "Started routing on the Switches"

foreach {switch_name portlist} [array get ntgr_L3SwitchList_066] {
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

Netgear_log_file "TC-OSPF-066.tcl" "Started Configuration of OSPF"

foreach switch_name $ntgr_OSPF_List_066 {
	
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

Netgear_log_file "TC-OSPF-066.tcl" "Started Configuration of OSPF on IXIA"

foreach {chassis_id port} [array get ntgr_OspfPortList_066] {
	UALConnectTester $chassis_id
	UALTakeOwnership $chassis_id $port
	UALLoadPort $chassis_id $port
	UALStartOSPFRouter $chassis_id $port
}

sleep 100

###############################Check the Configuration ################################# 
for {set i 1} {$i <= 1} {incr i} {
	sleep 2
	set switch_checked [lindex $ntgr_OSPF_List_066 0]
	set result [CALCheckExpect $switch_checked $cmds $expect_string_list] 
}	
if {$result == 1} {Netgear_log_file "TC-OSPF-066.tcl" "***** TC-OSPF-066.tcl passed *****"} else {
Netgear_log_file "TC-OSPF-066.tcl" "***** TC-OSPF-066.tcl failed *****"}
Netgear_log_file "TC-OSPF-066.tcl" "***** Completed Test case TC-OSPF-066.tcl *****"

Netgear_log_file "TC-OSPF-066.tcl" "Started Configuration of OSPF on IXIA"

foreach {chassis_id port} [array get ntgr_OspfPortList_066] {

	UALStopOSPFRouter $chassis_id $port
	UALClearOwnership $chassis_id $port
	UALDisconnectTester $chassis_id
}
#*********************  End of Test case  ***************************************
