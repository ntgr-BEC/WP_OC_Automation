#################################################################################
#
#  File Name	    	: TC-OSPF-014.tcl
#
#  Description     	: Interface cost test 
#
#  Config file       	: TC-OSPF-014.cfg
#				  
#  Revision History 	:
#        Date          Programmer          Description
#        -----------------------------------------------------------------------
#        01/02/07      Nina Cheng          Created
#
#
##################################################################################

#########################start to configure the TOPO##############################

Netgear_log_file "TC-OSPF-014.tcl" "******** Starting Test case TC-OSPF-014.tcl **********"

foreach switch_name $NTGR_CLEAR_CONFIG_SW_LIST_014 {
	Netgear_log_file "TC-OSPF-014.tcl" "Clear current configuration on switch $switch_name and configure some initial info"
	CALResetConfiguration $switch_name
	CALSetSwitchPrompt $switch_name
	CALSetSwitchMngrIPAddr $switch_name
}

Netgear_log_file "TC-OSPF-014.tcl" "Started to Modify TOPO"
	Netgear_connect_switch $MT_SW_014
	CALPortLinkDown $MT_SW_014 $port_down
	Netgear_disconnect_switch $MT_SW_014

Netgear_log_file "TC-OSPF-014.tcl" "Started routing on the Switches"

foreach {switch_name portlist} [array get ntgr_L3SwitchList_010] {
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

Netgear_log_file "TC-STATIC-IP-014.tcl" "Started add static routes on switch"
foreach switch_name $ntgr_StaticIPList_014 {
    set address_list [getAddressList $switch_name]	
    CALAddStaticRoute $switch_name $address_list 
}
Netgear_log_file "TC-OSPF-014.tcl" "Started Configuration of OSPF"
foreach switch_name $ntgr_OSPF_List_014 {
	
	set enable_flag [getOspfGlobalStatus $switch_name]
	set router_id [getOspfRouterID $switch_name]
	set interface_list [getOspfInterfaceList $switch_name]
	set redistribute [getOspfRedistribute $switch_name]
	  
	Netgear_connect_switch $switch_name 
	
	CALOspfEnable $switch_name $enable_flag
	CALConfigOspfRouterID $switch_name $router_id		
	CALOspfInterfaceEnable $switch_name $interface_list
	CALOspfConfigAreaID $switch_name $interface_list
	CALOspfConfigIFProperty $switch_name $interface_list
	CALOspfRedistributeEnable $switch_name $redistribute
	
	Netgear_disconnect_switch $switch_name
}

###############################Check the Configuration ################################# 
sleep 15

for {set i 1} {$i <= 3 } {incr i} {
	sleep 2
	set result1 [CALCheckExpect $SW_checked $cmds1 $expect_string_list1] 
}	
if {$result1 == 1} {
	set result2 [CALCheckExpect $SW_checked $cmds2 $expect_string_list2]
	if {$result2 == 1} {
		Netgear_log_file "TC-OSPF-014.tcl" "***** TC-OSPF-014.tcl passed *****"
	} else {
		Netgear_log_file "TC-OSPF-014.tcl" "***** TC-OSPF-014.tcl failed, connected route can not be received. *****"
	}
} else {
	Netgear_log_file "TC-OSPF-014.tcl" "***** TC-OSPF-014.tcl failed, neighbor is not full.*****"
}

Netgear_log_file "TC-OSPF-014.tcl" "***** Completed Test case TC-OSPF-014.tcl *****"

#*********************  End of Test case  ***************************************
