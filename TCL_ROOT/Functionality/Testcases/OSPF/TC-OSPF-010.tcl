#################################################################################
#
#  File Name	    	: TC-OSPF-010.tcl
#
#  Description     	: Network-summary-lsa summary
#
#  Config file          : TC-OSPF-010.cfg
#				  
#  Revision History 	:
#        Date          Programmer          Description
#        -----------------------------------------------------------------------
#        02/02/07      Nina Cheng          Created
#
#
##################################################################################

#########################start to configure the TOPO##############################

Netgear_log_file "TC-OSPF-010.tcl" "******** Starting Test case TC-OSPF-010.tcl **********"

foreach switch_name $ntgr_OSPF_List_010 {
    Netgear_log_file "TC-OSPF-010.tcl" "Clear current configuration on switch $switch_name and configure some initial info"
    CALResetConfiguration $switch_name
    CALSetSwitchPrompt $switch_name
    CALSetSwitchMngrIPAddr $switch_name
}

Netgear_log_file "TC-OSPF-010.tcl" "Started to Modify TOPO"
Netgear_connect_switch $TOPO_SW
CALPortLinkDown $TOPO_SW $port_down
Netgear_disconnect_switch $TOPO_SW

Netgear_log_file "TC-OSPF-010.tcl" "Started routing on the Switches"

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
Netgear_log_file "TC-OSPF-010.tcl" "Started Configuration of OSPF"

foreach switch_name $ntgr_OSPF_List_010 {
	
	set enable_flag [getOspfGlobalStatus $switch_name]
	set router_id [getOspfRouterID $switch_name]
	set interface_list [getOspfInterfaceList $switch_name]
	set range [getOspfRangeInfor $switch_name]
	
	Netgear_connect_switch $switch_name 
	
	CALOspfEnable $switch_name $enable_flag
	CALConfigOspfRouterID $switch_name $router_id		
	CALOspfInterfaceEnable $switch_name $interface_list
	CALOspfConfigAreaID $switch_name $interface_list
	CALOspfConfigIFProperty $switch_name $interface_list
  	CALOspfAreaRangeEnable $switch_name $range 
  	 	
	Netgear_disconnect_switch $switch_name
}
sleep 30
########################################## Check results ########################################## 
for {set i 1} {$i <= 3} {incr i} {
	sleep 3
	set result [CALCheckExpect $switch_checked $cmds $expect_string_list] 
}

if {$result == 1} {
	Netgear_log_file "TC-OSPF-010.tcl" "***** TC-OSPF-010.tcl passed *****"
} else {
	Netgear_log_file "TC-OSPF-010.tcl" "***** TC-OSPF-010.tcl failed *****"
}
	 
Netgear_log_file "TC-OSPF-010.tcl" "***** Completed Test case TC-OSPF-010.tcl *****"
#****************************************  End of Test case  ***************************************

