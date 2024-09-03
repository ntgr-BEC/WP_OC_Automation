#################################################################################
#
#  File Name	    	: TC-OSPF-042.tcl
#
#  Description     	: OSPF on routing port
#
#  Config file       	: TC-OSPF-042.cfg
#				  
#  Revision History 	:
#        Date          Programmer          Description
#        -----------------------------------------------------------------------
#        06/02/07      Nina Cheng          Created
#
#
##################################################################################

#########################start to configure the TOPO##############################

Netgear_log_file "TC-OSPF-042.tcl" "******** Starting Test case TC-OSPF-042.tcl **********"

foreach switch_name $ntgr_OSPF_List_042 {
    Netgear_log_file "TC-OSPF-042.tcl" "Clear current configuration on switch $switch_name and configure some initial info"
    CALResetConfiguration $switch_name
    CALSetSwitchPrompt $switch_name
    CALSetSwitchMngrIPAddr $switch_name
}
Netgear_log_file "TC-OSPF-042.tcl" "Started to Modify TOPO"
	Netgear_connect_switch $MT_SW_042
	CALPortLinkDown $MT_SW_042 $port_down
	Netgear_disconnect_switch $MT_SW_042

Netgear_log_file "TC-OSPF-042.tcl" "Started routing on the Switches"

foreach {switch_name portlist} [array get ntgr_L3SwitchList_042] {
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
Netgear_log_file "TC-OSPF-042.tcl" "Started Configuration of OSPF"

foreach switch_name $ntgr_OSPF_List_042 {
	
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

################################ Get the result ################################## 
for {set i 1} {$i <= 3} {incr i} {
	sleep 3
	set result [CALCheckExpect $switch_checkedon $cmds $expect_string_list] 
}	

if {$result == 1} {
Netgear_log_file "TC-OSPF-042.tcl" "***** TC-OSPF-042.tcl passed *****"} else {
Netgear_log_file "TC-OSPF-042.tcl" "***** TC-OSPF-042.tcl failed *****"
}	 
Netgear_log_file "TC-OSPF-042.tcl" "***** Completed Test case TC-OSPF-042.tcl *****"

#*********************  End of Test case  ***************************************

