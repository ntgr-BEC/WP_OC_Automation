#################################################################################
#
#  File Name	    	: TC-OSPF-013.tcl
#
#  Description     	: Prefer intra-area route than inter-area one when reaching the same ASBR or network
#
#  Config file       	: TC-OSPF-013.cfg
#
#  Global Variables	: ntgr_poChanList (defined in CFG file)
#				  
#  Revision History 	:
#        Date          Programmer          Description
#        -----------------------------------------------------------------------
#        06/02/07      Nina Cheng          Created
#
#
##################################################################################

#########################start to configure the TOPO##############################

Netgear_log_file "TC-OSPF-013.tcl" "******** Starting Test case TC-OSPF-013.tcl **********"

foreach switch_name $ntgr_OSPF_List_013 {
    Netgear_log_file "TC-OSPF-013.tcl" "Clear current configuration on switch $switch_name and configure some initial info"
    CALResetConfiguration $switch_name
    CALSetSwitchPrompt $switch_name
    CALSetSwitchMngrIPAddr $switch_name
}

Netgear_log_file "TC-OSPF-013.tcl" "Started routing on the Switches"

foreach {switch_name portlist} [array get ntgr_L3SwitchList_013] {
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
Netgear_log_file "TC-OSPF-013.tcl" "Started Configuration of OSPF"

foreach switch_name $ntgr_OSPF_List_013 {
	
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
Netgear_log_file "TC-OSPF-013.tcl" "Started configuration static routes on the switches"

foreach switch_name $ntgr_StaticIPList_013 {
    set address_list [getAddressList $switch_name]	
    CALAddStaticRoute $switch_name $address_list
}
######################## check the result ######################################
for {set i 1} {$i <= 3} {incr i} {
	sleep 2
	set result1 [CALCheckExpect $SW_checked $cmds1 $expect_string_list1] 
}	
if {$result1 == 1} {
	Netgear_log_file "TC-OSPF-013.tcl" "***** Neighbor is full.*****"
} else {
	Netgear_log_file "TC-OSPF-013.tcl" "***** Neighbor isn't full.*****"
}

Netgear_log_file "TC-OSPF-013.tcl" "Started to Modify TOPO"
	Netgear_connect_switch $SW_checked
	CALPortLinkDown $SW_checked $port_down
	Netgear_disconnect_switch $SW_checked

sleep 2
set result2 [CALCheckExpect $SW_checked $cmds2 $expect_string_list2] 

Netgear_log_file "TC-OSPF-013.tcl" "Started to Modify TOPO"
	Netgear_connect_switch $SW_checked
	CALPortLinkUp $SW_checked $port_down
	Netgear_disconnect_switch $SW_checked

for {set i 1} {$i <= 3} {incr i} {
	sleep 20
	set result3 [CALCheckExpect $SW_checked $cmds2 $expect_string_list3] 
}

if {$result2 == 1 && $result3 == 1} {
	Netgear_log_file "TC-OSPF-013.tcl" "***** TC-OSPF-013.tcl passed *****"
} else {
	Netgear_log_file "TC-OSPF-013.tcl" "***** TC-OSPF-013.tcl failed *****"
}
Netgear_log_file "TC-OSPF-013.tcl" "***** Completed Test case TC-OSPF-013.tcl *****"

#*********************  End of Test case  ***************************************

