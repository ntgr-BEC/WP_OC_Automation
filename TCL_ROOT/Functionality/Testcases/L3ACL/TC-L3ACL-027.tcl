#################################################################################
#
#  File Name	    	: TC-L3ACL-027.tcl
#
#  Description     	: Match the keyword --- match preference(udp)
#
#  Config file          : TC-L3ACL-027.cfg
#				  
#  Revision History 	:
#        Date          Programmer          Description
#        -----------------------------------------------------------------------
#        10/04/07      Nina Cheng          Created
#
#
##################################################################################

########################### start to configuration ###############################

Netgear_log_file "TC-L3ACL-027.tcl" "******** Starting Test case TC-L3ACL-027.tcl **********"

foreach switch_name $MTSW_L3ACL_027 {
    Netgear_log_file "TC-L3ACL-027.tcl" "Clear current configuration on switch $switch_name and configure some initial info"
    CALResetConfiguration $switch_name
    CALSetSwitchPrompt $switch_name
    CALSetSwitchMngrIPAddr $switch_name
}

Netgear_log_file "TC-L3ACL-027.tcl" "Started routing on the Switches"

foreach {switch_name portlist} [array get ntgr_L3SwitchList_027] {
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

Netgear_log_file "TC-L3ACL-027.tcl" "Started configuration of ACL on the Switches"

foreach switch_name $ntgr_L3ACL_List_027 {
  
  set ip_acl_id_list [getIPACLID $switch_name]
  set ip_acl_rule_list [ getIPACLRule $switch_name]
  set ip_acl_interface_list [getIPACLInterface $switch_name]
  
  CALAddIPACL $switch_name $ip_acl_rule_list
  CALEnableIPACLOninterface $switch_name $ip_acl_interface_list
}

Netgear_log_file "TC-L3ACL-027.tcl" "Started Configuration of RIP on IXIA"

foreach {chassis_id portlist} [array get ntgr_RouterPortList_027] {
	UALConnectTester $chassis_id
	foreach port $portlist {
		UALTakeOwnership $chassis_id $port
		UALLoadPort $chassis_id $port
	}
}

sleep 10

UALStartTrafficPerPort $ntgrTG $ntgrPort2

sleep 50

set TxRx4 [UALReportPortRate $ntgrTG $ntgrPort4]
set TxRx2 [UALReportPortRate $ntgrTG $ntgrPort2]

set dif [expr [lindex $TxRx4 1]/[lindex $TxRx2 0]]

if {$dif >= 0.45 && $dif <= 0.55} {
	Netgear_log_file "TC-L3ACL-027.tcl" "***** TC-L3ACL-027.tcl passed *****"
} else {
	Netgear_log_file "TC-L3ACL-027.tcl" "***** TC-L3ACL-027.tcl failed *****"
}

Netgear_log_file "TC-L3ACL-027.tcl" "***** Completed Test case TC-L3ACL-027.tcl *****"


UALStopTrafficPerPort $ntgrTG $ntgrPort2

foreach {chassis_id portlist} [array get ntgr_RouterPortList_027] {
	foreach port $portlist {
		UALClearOwnership $chassis_id $port
	}
	UALDisconnectTester $chassis_id
}

Netgear_log_file "TC-L3ACL-027.tcl" "***** Completed Test case TC-L3ACL-027.tcl *****"

#*********************  End of Test case  ***************************************
