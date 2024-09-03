#################################################################################
#
#  File Name	    	: TC-OSPF-003.tcl
#
#  Description     	: Not advertise the summary-asbr-lsa into stub area
#
#  Config file          : TC-OSPF-003.cfg
#				  
#  Revision History 	:
#        Date          Programmer          Description
#        -----------------------------------------------------------------------
#        31/01/07      Nina Cheng          Created
#
#
##################################################################################

#########################start to configure the TOPO##############################

Netgear_log_file "TC-OSPF-003.tcl" "******** Starting Test case TC-OSPF-003.tcl **********"

foreach switch_name $ntgr_OSPF_List_003 {
    Netgear_log_file "TC-OSPF-003.tcl" "Clear current configuration on switch $switch_name and configure some initial info"
    CALResetConfiguration $switch_name
    CALSetSwitchPrompt $switch_name
    CALSetSwitchMngrIPAddr $switch_name
}

Netgear_log_file "TC-OSPF-003.tcl" "Started routing on the Switches"

foreach {switch_name portlist} [array get ntgr_L3SwitchList_003] {
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
Netgear_log_file "TC-OSPF-003.tcl" "Started Configuration of OSPF"

foreach switch_name $ntgr_OSPF_List_003 {
	
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

Netgear_log_file "TC-OSPF-003.tcl" "Started Configuration of RIP on DUT"
foreach switch_name $switch_RIP_list_003 {
	
	Netgear_connect_switch $switch_name 
	
	set flag_enable [getRipGlobalStatus $switch_name]
	set interface_list [getRipInterface $switch_name]
	
	CALRipEnableSwitch $switch_name $flag_enable
	CALRipEnableInterface $switch_name $interface_list
	 	
	Netgear_disconnect_switch $switch_name   
}
Netgear_log_file "TC-OSPF-003.tcl" "Started Configuration of RIP on IXIA"

foreach {chassis_id port} [array get ntgr_RipPortList_003] {

	UALConnectTester $chassis_id	
	UALTakeOwnership $chassis_id $port
	UALLoadPort $chassis_id $port
	UALStartRIPRouter $chassis_id $port
}
########################################## Check results ########################################## 

set temp_string $ntgr_L3SwitchList_003(B1)
set first [lindex $temp_string 0]
set last [lindex $temp_string 1]
set f_inter [lindex $first 0]
set s_inter [lindex $last 0]
set exp_str1 "\{$f_inter            Full\}"
set exp_str0 "\{$s_inter            Full\}"

###################################################################################################
for {set i 1} {$i <= 1} {incr i} {
	sleep 2
	set result1 [CALCheckExpect $switch_checked $cmds1 $exp_str1] 
	set result0 [CALCheckExpect $switch_checked $cmds1 $exp_str0]
}

if {$result1 == 1 && $result0 == 1} {Netgear_log_file "TC-OSPF-003.tcl" "***** Neighbors are full *****"
        set result2 [CALCheckExpect $switch_checked $cmds2 $expect_string_list2 0]
        if {$result2 ==1} {
        	Netgear_log_file "TC-OSPF-003.tcl" "***** Test case TC-OSPF-003.tcl passed *****"
        } else {
        	Netgear_log_file "TC-OSPF-003.tcl" "***** Test case TC-OSPF-003.tcl failed *****"
        }
} else {
	Netgear_log_file "TC-OSPF-003.tcl" "***** Neighbors are not full, Test case TC-OSPF-003.tcl failed *****"
}
	 
Netgear_log_file "TC-OSPF-003.tcl" "***** Completed Test case TC-OSPF-003.tcl *****"
###################################################################################################

Netgear_log_file "TC-OSPF-003.tcl" "clear owner ship of RIP on IXIA"

foreach {chassis_id port} [array get ntgr_RipPortList_003] {

	UALStopRIPRouter $chassis_id $port
	UALClearOwnership $chassis_id $port
	UALDisconnectTester $chassis_id
}
#****************************************  End of Test case  ***************************************

