#################################################################################
#
#  File Name	    	: TC-OneWayACL-037.tcl
#
#  Description     	: Apply many one-way ACLs to the same port
#
#  Config file          : TC-OneWayACL-037.cfg
#				  
#  Revision History 	:
#        Date          Programmer          Description
#        -----------------------------------------------------------------------
#        09/04/07      Nina Cheng          Created
#
#
##################################################################################

########################### start to configuration ###############################

Netgear_log_file "TC-OneWayACL-037.tcl" "******** Starting Test case TC-OneWayACL-037.tcl **********"

foreach switch_name $MTSW_OneWayACL_037 {
    Netgear_log_file "TC-OneWayACL-037.tcl" "Clear current configuration on switch $switch_name and configure some initial info"
    CALResetConfiguration $switch_name
    CALSetSwitchPrompt $switch_name
    CALSetSwitchMngrIPAddr $switch_name
}

Netgear_log_file "TC-OneWayACL-037.tcl" "Started routing on the Switches"

foreach {switch_name portlist} [array get ntgr_L3SwitchList_037] {
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

sleep 20

Netgear_log_file "TC-OneWayACL-037.tcl" "Started configuration of one-way ACL on the Switches"

foreach switch_name $ntgr_OneWayACL_List_037 {
  
  set ip_acl_id_list [getIPACLID $switch_name]
  set ip_acl_rule_list [ getIPACLRule $switch_name]
  set ip_acl_interface_list [getIPACLInterface $switch_name]
  
  CALAddIPACL $switch_name $ip_acl_rule_list
  CALEnableIPACLOninterface $switch_name $ip_acl_interface_list
  sleep 3
}

Netgear_log_file "TC-OneWayACL-037.tcl" "Started Configuration of IXIA"

foreach {chassis_id portlist} [array get ntgr_RouterPortList_037] {
	UALConnectTester $chassis_id
	foreach port $portlist {
		UALTakeOwnership $chassis_id $port
		UALLoadPort $chassis_id $port
	}
}

sleep 10
Netgear_log_file "TC-OneWayACL-037.tcl" "Start to send traffic from IXIA"

UALStartTrafficPerPort $ntgrTG $ntgrPort1

sleep 50

set dif 0
set TxRx1 [UALReportPortRate $ntgrTG $ntgrPort1]
set TxRx2 [UALReportPortRate $ntgrTG $ntgrPort2]

set dif [expr [lindex $TxRx2 1]/ [lindex $TxRx1 0]]

if {$dif >= 0.4 && $dif <= 0.6} {set result 1} else {set result 0}

if {$result == 1} {
	Netgear_log_file "TC-OneWayACL-037.tcl" "***** TC-OneWayACL-037.tcl passed *****"
} else {
	Netgear_log_file "TC-OneWayACL-037.tcl" "***** TC-OneWayACL-037.tcl failed *****"
}

Netgear_log_file "TC-OneWayACL-037.tcl" "Stop to send traffic from IXIA"

UALStopTrafficPerPort $ntgrTG $ntgrPort1

foreach {chassis_id portlist} [array get ntgr_RouterPortList_037] {
	foreach port $portlist {
		UALClearOwnership $chassis_id $port
	}
	UALDisconnectTester $chassis_id
}

Netgear_log_file "TC-OneWayACL-037.tcl" "***** Completed Test case TC-OneWayACL-037.tcl *****"

#*********************  End of Test case  ***************************************



