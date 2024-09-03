#################################################################################
#
#  File Name	    	: TC-OSPF-037.tcl
#
#  Description     	: redistribute [protocol] tag [value]
#
#  Config file       	: TC-OSPF-037.cfg
#				  
#  Revision History 	:
#        Date          Programmer          Description
#        -----------------------------------------------------------------------
#        02/02/07      Nina Cheng          Created
#
#
##################################################################################

#########################start to configure the TOPO##############################

Netgear_log_file "TC-OSPF-037.tcl" "******** Starting Test case TC-OSPF-037.tcl **********"

foreach switch_name $NTGR_CLEAR_CONFIG_SW_LIST_037 {
    Netgear_log_file "TC-OSPF-037.tcl" "Clear current configuration on switch $switch_name and configure some initial info"
    CALResetConfiguration $switch_name
    CALSetSwitchPrompt $switch_name
    CALSetSwitchMngrIPAddr $switch_name
}

Netgear_log_file "TC-OSPF-037.tcl" "Started routing on the Switches"

foreach switch_name $NTGR_CLEAR_CONFIG_SW_LIST_037 {
	Netgear_connect_switch $switch_name 
	CALRoutingEnable $switch_name
	Netgear_disconnect_switch $switch_name
}

Netgear_log_file "TC-OSPF-037.tcl" "Started Configuration of OSPF"

foreach switch_name $ntgr_OSPF_List_037 {
	
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

set switch_checked [lindex $ntgr_OSPF_List_037 0]
set result [CALCheckExpect $switch_checked $cmds $expect_string_list] 
	
if {$result == 1} {Netgear_log_file "TC-OSPF-037.tcl" "***** TC-OSPF-037.tcl passed *****"} else {
Netgear_log_file "TC-OSPF-037.tcl" "***** TC-OSPF-037.tcl failed *****"}

Netgear_log_file "TC-OSPF-037.tcl" "***** Completed Test case TC-OSPF-037.tcl *****"

#*********************  End of Test case  ***************************************
