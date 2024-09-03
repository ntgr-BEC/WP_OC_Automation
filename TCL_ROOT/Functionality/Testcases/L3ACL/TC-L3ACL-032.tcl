#################################################################################
#
#  File Name	    	: TC-L3ACL-032.tcl
#
#  Description     	: ACL service on ICMP packet ---- Match any, action is deny
#
#  Config file          : TC-L3ACL-032.cfg
#				  
#  Revision History 	:
#        Date          Programmer          Description
#        -----------------------------------------------------------------------
#        11/04/07      Nina Cheng          Created
#
#
##################################################################################

########################### start to configuration ###############################

Netgear_log_file "TC-L3ACL-032.tcl" "******** Starting Test case TC-L3ACL-032.tcl **********"

foreach switch_name $MTSW_L3ACL_032 {
    Netgear_log_file "TC-L3ACL-032.tcl" "Clear current configuration on switch $switch_name and configure some initial info"
    CALResetConfiguration $switch_name
    CALSetSwitchPrompt $switch_name
    CALSetSwitchMngrIPAddr $switch_name
}

Netgear_log_file "TC-L3ACL-032.tcl" "Started routing on the Switches"

foreach {switch_name portlist} [array get ntgr_L3SwitchList_032] {
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

Netgear_log_file "TC-L3ACL-032.tcl" "Started configuration of ACL on the Switches"

sleep 5

for {set i 1} {$i <= 3} {incr i} {
	sleep 2
	set result1 [CALCheckExpect $switch_checked $cmds $expect_string_list 0] 
}

foreach switch_name $ntgr_L3ACL_List_032 {
  
  set ip_acl_id_list [getIPACLID $switch_name]
  set ip_acl_rule_list [ getIPACLRule $switch_name]
  set ip_acl_interface_list [getIPACLInterface $switch_name]
  
  CALAddIPACL $switch_name $ip_acl_rule_list
  CALEnableIPACLOninterface $switch_name $ip_acl_interface_list
}
sleep 5

for {set i 1} {$i <= 3} {incr i} {
	sleep 2
	set result2 [CALCheckExpect $switch_checked $cmds $expect_string_list] 
}
	
if {$result1 == 1 && $result2 == 1} {
	Netgear_log_file "TC-L3ACL-032.tcl" "***** TC-L3ACL-032.tcl passed *****"
} else {
	Netgear_log_file "TC-L3ACL-032.tcl" "***** TC-L3ACL-032.tcl failed *****"
}

Netgear_log_file "TC-L3ACL-032.tcl" "***** Completed Test case TC-L3ACL-032.tcl *****"
#*********************  End of Test case  ***************************************
