#################################################################################
#
#  File Name	    	: TC-OSPF-002.tcl
#
#  Description     	: Produce new router-lsa when the network type is changed
#
#  Config file       	: TC-OSPF-002.cfg
#
#  Global Variables	: ntgr_poChanList (defined in CFG file)
#				  
#  Revision History 	:
#        Date          Programmer          Description
#        -----------------------------------------------------------------------
#        30/01/07      Nina Cheng          Created
#
#
##################################################################################

#########################start to configure the TOPO##############################

Netgear_log_file "TC-OSPF-002.tcl" "******** Starting Test case TC-OSPF-002.tcl **********"

foreach switch_name $ntgr_OSPF_List_002 {
    Netgear_log_file "TC-OSPF-002.tcl" "Clear current configuration on switch $switch_name and configure some initial info"
    CALResetConfiguration $switch_name
    CALSetSwitchPrompt $switch_name
    CALSetSwitchMngrIPAddr $switch_name
}

Netgear_log_file "TC-OSPF-002.tcl" "Started routing on the Switches"

foreach {switch_name portlist} [array get ntgr_L3SwitchList_002] {
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
Netgear_log_file "TC-OSPF-002.tcl" "Started Configuration of OSPF"

foreach switch_name $ntgr_OSPF_List_002 {
	
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

############################### Get the sequence number of one lsa ######################## 
for {set i 1} {$i <= 3} {incr i} {
	sleep 2
	set result [CALCheckExpect $switch_checkedby $cmds $expect_string_list] 
}	
if {$result == 1} {Netgear_log_file "TC-OSPF-002.tcl" "***** Neighbors are full *****"
        set router_id_checked [getOspfRouterID $switch_checkedby]
	set s_number_old [CALGetSequenceNumberOfOSPF $switch_checkedon $area_id_checked "Router Link States" $router_id_checked $router_id_checked]
	} else {
Netgear_log_file "TC-OSPF-002.tcl" "***** Neighbors are not full, test faied *****"}

Netgear_connect_switch $switch_checkedby
CALPortLinkDown $switch_checkedby $port_down
Netgear_disconnect_switch $switch_checkedby
sleep 2
set s_number_new [CALGetSequenceNumberOfOSPF $switch_checkedon $area_id_checked "Router Link States" $router_id_checked $router_id_checked]

set dif_value 0
set dif_value [expr 0x$s_number_new - 0x$s_number_old]


if {$dif_value == 1} {
Netgear_log_file "TC-OSPF-002.tcl" "***** TC-OSPF-002.tcl passed *****"} else {
Netgear_log_file "TC-OSPF-002.tcl" "***** TC-OSPF-002.tcl failed *****"
}	 
Netgear_log_file "TC-OSPF-002.tcl" "***** Completed Test case TC-OSPF-002.tcl *****"

#*********************  End of Test case  ***************************************

