#################################################################################
#
#  File Name	    	: TC-OSPF-008.tcl
#
#  Description     	: Default route for totally stub area
#
#  Config file       	: TC-OSPF-008.cfg
#				  
#  Revision History 	:
#        Date          Programmer          Description
#        -----------------------------------------------------------------------
#        01/02/07      Nina Cheng          Created
#
#
##################################################################################

#########################start to configure the TOPO##############################

Netgear_log_file "TC-OSPF-008.tcl" "******** Starting Test case TC-OSPF-008.tcl **********"

foreach switch_name $ntgr_OSPF_List_008 {
    Netgear_log_file "TC-OSPF-008.tcl" "Clear current configuration on switch $switch_name and configure some initial info"
    CALResetConfiguration $switch_name
    CALSetSwitchPrompt $switch_name
    CALSetSwitchMngrIPAddr $switch_name
}

Netgear_log_file "TC-OSPF-008.tcl" "Started routing on the Switches"

foreach {switch_name portlist} [array get ntgr_L3SwitchList_008] {
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
Netgear_log_file "TC-OSPF-008.tcl" "Started Configuration of OSPF"

foreach switch_name $ntgr_OSPF_List_008 {
	
	set enable_flag [getOspfGlobalStatus $switch_name]
	set router_id [getOspfRouterID $switch_name]
	set interface_list [getOspfInterfaceList $switch_name]
	set stub_infor [getOspfStubInfor $switch_name]
	  
	Netgear_connect_switch $switch_name 
	
	CALOspfEnable $switch_name $enable_flag
	CALConfigOspfRouterID $switch_name $router_id		
	CALOspfInterfaceEnable $switch_name $interface_list
	CALOspfConfigAreaID $switch_name $interface_list
	CALOspfConfigIFProperty $switch_name $interface_list
	CALOspfAreaStubEnable $switch_name $stub_infor
	CALOspfStubsmmarylsaDisable $switch_name $stub_infor
	Netgear_disconnect_switch $switch_name
}

############################### Check the default route ######################## 
set result [CALCheckExpect $SW_checked $cmds $expect_string_list] 
if { $result == 1} {
Netgear_log_file "TC-OSPF-008.tcl" "***** TC-OSPF-008.tcl passed *****"} else {
Netgear_log_file "TC-OSPF-008.tcl" "***** TC-OSPF-008.tcl failed *****"
}	 
Netgear_log_file "TC-OSPF-008.tcl" "***** Completed Test case TC-OSPF-008.tcl *****"

#*********************  End of Test case  ***************************************

