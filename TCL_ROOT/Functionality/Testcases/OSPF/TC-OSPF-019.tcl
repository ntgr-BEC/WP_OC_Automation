#################################################################################
#
#  File Name	    	: TC-OSPF-019.tcl
#
#  Description     	: Stub area test
#
#  Config file       	: TC-OSPF-019.cfg
#				  
#  Revision History 	:
#        Date          Programmer          Description
#        -----------------------------------------------------------------------
#        01/02/07      Nina Cheng          Created
#
#
##################################################################################

#########################start to configure the TOPO##############################

Netgear_log_file "TC-OSPF-019.tcl" "******** Starting Test case TC-OSPF-019.tcl **********"

foreach switch_name $ntgr_OSPF_List_019 {
    Netgear_log_file "TC-OSPF-019.tcl" "Clear current configuration on switch $switch_name and configure some initial info"
    CALResetConfiguration $switch_name
    CALSetSwitchPrompt $switch_name
    CALSetSwitchMngrIPAddr $switch_name
}

Netgear_log_file "TC-OSPF-019.tcl" "Started routing on the Switches"

foreach {switch_name portlist} [array get ntgr_L3SwitchList_019] {
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
Netgear_log_file "TC-OSPF-019.tcl" "Started Configuration of OSPF"

foreach switch_name $ntgr_OSPF_List_019 {
	
	set enable_flag [getOspfGlobalStatus $switch_name]
	set router_id [getOspfRouterID $switch_name]
	set interface_list [getOspfInterfaceList $switch_name]
	set stub_infor [getOspfStubInfor $switch_name]
	set redistribute [getOspfRedistribute $switch_name]
	  
	Netgear_connect_switch $switch_name 
	
	CALOspfEnable $switch_name $enable_flag
	CALConfigOspfRouterID $switch_name $router_id		
	CALOspfInterfaceEnable $switch_name $interface_list
	CALOspfConfigAreaID $switch_name $interface_list
	CALOspfConfigIFProperty $switch_name $interface_list
	CALOspfAreaStubEnable $switch_name $stub_infor
        CALOspfRedistributeEnable $switch_name $redistribute
        
	Netgear_disconnect_switch $switch_name
}
Netgear_log_file "TC-OSPF-019.tcl" "Started Configuration of RIP on DUT"
foreach switch_name $switch_RIP_list_019 {
	
	Netgear_connect_switch $switch_name 
	
	set flag_enable [getRipGlobalStatus $switch_name]
	set interface_list [getRipInterface $switch_name]
	
	CALRipEnableSwitch $switch_name $flag_enable
	CALRipEnableInterface $switch_name $interface_list
	 	
	Netgear_disconnect_switch $switch_name   
}
Netgear_log_file "TC-OSPF-019.tcl" "Started Configuration of RIP on IXIA"

foreach {chassis_id port} [array get ntgr_RipPortList_019] {

	UALConnectTester $chassis_id	
	UALTakeOwnership $chassis_id $port
	UALLoadPort $chassis_id $port
	UALStartRIPRouter $chassis_id $port
}
############################### Check the default route ######################## 
set result1 [CALCheckExpect $SW_checked $cmds $expect_string_list1 0]
set result2 [CALCheckExpect $SW_checked $cmds $expect_string_list2 1] 
if { $result1 == 1 && $result2 == 1} {
Netgear_log_file "TC-OSPF-019.tcl" "***** TC-OSPF-019.tcl passed *****"} else {
Netgear_log_file "TC-OSPF-019.tcl" "***** TC-OSPF-019.tcl failed *****"
}	 
Netgear_log_file "TC-OSPF-019.tcl" "***** Completed Test case TC-OSPF-019.tcl *****"

#Netgear_log_file "TC-OSPF-019.tcl" "clear owner ship of RIP on IXIA"
#
#foreach {chassis_id port} [array get ntgr_RipPortList_019] {
#
#	UALStopRIPRouter $chassis_id $port
#	UALClearOwnership $chassis_id $port
#	UALDisconnectTester $chassis_id
#}
#*********************  End of Test case  ***************************************

