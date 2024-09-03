#################################################################################
#
#  File Name	    	: TC-L3ACL-036.tcl
#
#  Description     	: ACL service on IP protocol number
#
#  Config file       	: TC-L3ACL-036.cfg
#				  
#  Revision History 	:
#        Date          Programmer          Description
#        -----------------------------------------------------------------------
#        12/04/07      Nina Cheng          Created
#
#
##################################################################################

#########################start to configure the TOPO##############################

Netgear_log_file "TC-L3ACL-036.tcl" "******** Starting Test case TC-L3ACL-036.tcl **********"

foreach switch_name $ntgr_OSPF_List_036 {
    Netgear_log_file "TC-L3ACL-036.tcl" "Clear current configuration on switch $switch_name and configure some initial info"
    CALResetConfiguration $switch_name
    CALSetSwitchPrompt $switch_name
    CALSetSwitchMngrIPAddr $switch_name
}

Netgear_log_file "TC-L3ACL-036.tcl" "Started routing on the Switches"

foreach {switch_name portlist} [array get ntgr_L3SwitchList_036] {
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
Netgear_log_file "TC-L3ACL-036.tcl" "Started Configuration of OSPF"

foreach switch_name $ntgr_OSPF_List_036 {
	
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
Netgear_log_file "TC-L3ACL-036.tcl" "Started configuration of ACL on the Switches"

foreach switch_name $ntgr_L3ACL_List_036 {
  
  set ip_acl_id_list [getIPACLID $switch_name]
  set ip_acl_rule_list [ getIPACLRule $switch_name]
  set ip_acl_interface_list [getIPACLInterface $switch_name]
  
  CALAddIPACL $switch_name $ip_acl_rule_list
  CALEnableIPACLOninterface $switch_name $ip_acl_interface_list
}

sleep 10

for {set i 1} {$i <= 3} {incr i} {
	sleep 2
	set result1 [CALCheckExpect $switch_checked $cmds $expect_string_list 0] 
}

Netgear_log_file "TC-L3ACL-036.tcl" "Started deleting ACL on the Switches"

foreach switch_name $ntgr_L3ACL_List_036 {
	set ip_acl_interface_list [getIPACLInterface $switch_name]
	CALDisableIPACLOninterface $switch_name $ip_acl_interface_list
}

sleep 10
for {set i 1} {$i <= 3} {incr i} {
	sleep 2
	set result2 [CALCheckExpect $switch_checked $cmds $expect_string_list] 
}

foreach switch_name $ntgr_L3ACL_List_036 {
	set ip_acl_interface_list [getIPACLInterface $switch_name]
	CALEnableIPACLOninterface $switch_name $ip_acl_interface_list
}

Netgear_connect_switch $switch_checked
CALOspfDisable $switch_checked ""
CALOspfEnable $switch_checked ""
Netgear_disconnect_switch $switch_checked
sleep 10
for {set i 1} {$i <= 3} {incr i} {
	sleep 2
	set result3 [CALCheckExpect $switch_checked $cmds $expect_string_list 0] 
}

if {$result1 == 1 && $result2 == 1 && $result3 == 1} {
	Netgear_log_file "TC-L3ACL-036.tcl" "***** TC-L3ACL-036.tcl passed *****"
} else {
	Netgear_log_file "TC-L3ACL-036.tcl" "***** TC-L3ACL-036.tcl failed *****"
}
	 
Netgear_log_file "TC-L3ACL-036.tcl" "***** Completed Test case TC-L3ACL-036.tcl *****"

#*********************  End of Test case  ***************************************

