#################################################################################
#
#  File Name	    	: TC-OSPF-040.tcl
#
#  Description     	: mtu-ignore
#
#  Config file       	: TC-OSPF-040.cfg
#				  
#  Revision History 	:
#        Date          Programmer          Description
#        -----------------------------------------------------------------------
#        02/02/07      Nina Cheng          Created
#
#
##################################################################################

#########################start to configure the TOPO##############################

Netgear_log_file "TC-OSPF-040.tcl" "******** Starting Test case TC-OSPF-040.tcl **********"

foreach switch_name $NTGR_CLEAR_CONFIG_SW_LIST_040 {
    Netgear_log_file "TC-OSPF-040.tcl" "Clear current configuration on switch $switch_name and configure some initial info"
    CALResetConfiguration $switch_name
    CALSetSwitchPrompt $switch_name
    CALSetSwitchMngrIPAddr $switch_name
}

Netgear_log_file "TC-OSPF-040.tcl" "Started routing on the Switches"

foreach switch_name $NTGR_CLEAR_CONFIG_SW_LIST_040 {
	Netgear_connect_switch $switch_name 
	CALRoutingEnable $switch_name
	Netgear_disconnect_switch $switch_name
}

Netgear_log_file "TC-OSPF-040.tcl" "Started Configuration of OSPF"

foreach switch_name $ntgr_OSPF_List_040 {
	
	set enable_flag [getOspfGlobalStatus $switch_name]
	set router_id [getOspfRouterID $switch_name]
	set interface_list [getOspfInterfaceList $switch_name]
	
  
	Netgear_connect_switch $switch_name 
	
	CALOspfEnable $switch_name $enable_flag
	CALConfigOspfRouterID $switch_name $router_id		
	CALOspfInterfaceEnable $switch_name $interface_list
	CALOspfConfigAreaID $switch_name $interface_list
	CALOspfConfigIFProperty $switch_name $interface_list
        
	Netgear_disconnect_switch $switch_name
}

###############################Check the Configuration ################################# 

set switch_checked [lindex $ntgr_OSPF_List_040 0]
set result [CALCheckExpect $switch_checked $cmds $expect_string_list] 
	
if {$result == 1} {Netgear_log_file "TC-OSPF-040.tcl" "***** TC-OSPF-040.tcl passed *****"} else {
Netgear_log_file "TC-OSPF-040.tcl" "***** TC-OSPF-040.tcl failed *****"}

Netgear_log_file "TC-OSPF-040.tcl" "***** Completed Test case TC-OSPF-040.tcl *****"

#*********************  End of Test case  ***************************************
