#################################################################################
#
#  File Name	    	: TC-OSPF-070.tcl
#
#  Description     	: Summary routes in no-contiguous rang
#
#  Config file       	: TC-OSPF-070.cfg
#				  
#  Revision History 	:
#        Date          Programmer          Description
#        -----------------------------------------------------------------------
#        27/02/07      Nina Cheng          Created
#
#
##################################################################################

#########################start to configure the TOPO##############################

Netgear_log_file "TC-OSPF-070.tcl" "******** Starting Test case TC-OSPF-070.tcl **********"

foreach switch_name $ntgr_OSPF_List_070 {
    Netgear_log_file "TC-OSPF-070.tcl" "Clear current configuration on switch $switch_name and configure some initial info"
    CALResetConfiguration $switch_name
    CALSetSwitchPrompt $switch_name
    CALSetSwitchMngrIPAddr $switch_name
}

Netgear_log_file "TC-OSPF-070.tcl" "Started to Modify TOPO"
Netgear_connect_switch $TOPO_SW
CALPortLinkDown $TOPO_SW $port_down
Netgear_disconnect_switch $TOPO_SW

Netgear_log_file "TC-OSPF-070.tcl" "Started routing on the Switches"

foreach {switch_name portlist} [array get ntgr_L3SwitchList_070] {
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
Netgear_log_file "TC-OSPF-070.tcl" "Started Configuration of OSPF"

foreach switch_name $ntgr_OSPF_List_070 {
	
	set enable_flag [getOspfGlobalStatus $switch_name]
	set router_id [getOspfRouterID $switch_name]
	set interface_list [getOspfInterfaceList $switch_name]
	set nssa_infor [getOspfNssaInfor $switch_name]
	set redistribute [getOspfRedistribute $switch_name]
	set range [getOspfRangeInfor $switch_name]
	
	Netgear_connect_switch $switch_name 
	
	CALOspfEnable $switch_name $enable_flag
	CALConfigOspfRouterID $switch_name $router_id		
	CALOspfInterfaceEnable $switch_name $interface_list
	CALOspfConfigAreaID $switch_name $interface_list
	CALOspfConfigIFProperty $switch_name $interface_list
	CALOspfNSSAEnable $switch_name $nssa_infor
        CALOspfRedistributeEnable $switch_name $redistribute
        CALOspfAreaRangeEnable $switch_name $range 
        
	Netgear_disconnect_switch $switch_name
}
Netgear_log_file "TC-OSPF-070.tcl" "Started Configuration of RIP on DUT"
foreach switch_name $switch_RIP_list_070 {
	
	Netgear_connect_switch $switch_name 
	
	set flag_enable [getRipGlobalStatus $switch_name]
	set interface_list [getRipInterface $switch_name]
	
	CALRipEnableSwitch $switch_name $flag_enable
	CALRipEnableInterface $switch_name $interface_list
	 	
	Netgear_disconnect_switch $switch_name   
}
Netgear_log_file "TC-OSPF-070.tcl" "Started Configuration of RIP on IXIA"

foreach {chassis_id portlist} [array get ntgr_RipPortList_070] {
	UALConnectTester $chassis_id
	foreach port $portlist {
		UALTakeOwnership $chassis_id $port		
		UALLoadPort $chassis_id $port
		UALStartRIPRouter $chassis_id $port
	}
}
sleep 30
############################### Check the default route ######################## 
for { set i 1} { $i<=3 } {incr i} {
	sleep 2
	set result1 [CALCheckExpect $SW_checked $cmds $expect_string_list1]
	set result2 [CALCheckExpect $SW_checked $cmds $expect_string_list2]	
}

if { $result1 == 1 && $result2 == 1} {
	Netgear_log_file "TC-OSPF-070.tcl" "***** TC-OSPF-070.tcl passed *****"
} else {
	Netgear_log_file "TC-OSPF-070.tcl" "***** TC-OSPF-070.tcl failed *****"
	}
		 
Netgear_log_file "TC-OSPF-070.tcl" "***** Completed Test case TC-OSPF-070.tcl *****"

#Netgear_log_file "TC-OSPF-070.tcl" "clear owner ship of RIP on IXIA"
#
#foreach {chassis_id port} [array get ntgr_RipPortList_070] {
#
#	UALStopRIPRouter $chassis_id $port
#	UALClearOwnership $chassis_id $port
#	UALDisconnectTester $chassis_id
#}
#*********************  End of Test case  ***************************************

