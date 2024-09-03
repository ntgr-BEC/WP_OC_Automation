#################################################################################
#
#  File Name	    	: TC-OneWayACL-039.tcl
#
#  Description     	: Repeat to add and delete many one-way ACLs to the same port 
#
#  Config file          : TC-OneWayACL-039.cfg
#				  
#  Revision History 	:
#        Date          Programmer          Description
#        -----------------------------------------------------------------------
#        09/04/07      Nina Cheng          Created
#
#
##################################################################################

########################### start to configuration ###############################

Netgear_log_file "TC-OneWayACL-039.tcl" "******** Starting Test case TC-OneWayACL-039.tcl **********"

foreach switch_name $MTSW_OneWayACL_039 {
    Netgear_log_file "TC-OneWayACL-039.tcl" "Clear current configuration on switch $switch_name and configure some initial info"
    CALResetConfiguration $switch_name
    CALSetSwitchPrompt $switch_name
    CALSetSwitchMngrIPAddr $switch_name
}

Netgear_log_file "TC-OneWayACL-039.tcl" "Started routing on the Switches"

foreach {switch_name portlist} [array get ntgr_L3SwitchList_039] {
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

Netgear_log_file "TC-OneWayACL-039.tcl" "Started configuration of one-way ACL on the Switches"

foreach switch_name $ntgr_OneWayACL_List_039 {
  
  set ip_acl_id_list [getIPACLID $switch_name]
  set ip_acl_rule_list [ getIPACLRule $switch_name]
  
  CALAddIPACL $switch_name $ip_acl_rule_list
}
Netgear_log_file "TC-OneWayACL-039.tcl" "Started Configuration of IXIA"

foreach {chassis_id portlist} [array get ntgr_RouterPortList_039] {
	UALConnectTester $chassis_id
	foreach port $portlist {
		UALTakeOwnership $chassis_id $port
		UALLoadPort $chassis_id $port
	}
}

sleep 10
Netgear_log_file "TC-OneWayACL-039.tcl" "Start to send traffic from IXIA"

UALStartTrafficPerPort $ntgrTG $ntgrPort1

sleep 10

set dif 0
set TxRx1 [UALReportPortRate $ntgrTG $ntgrPort1]
set TxRx2 [UALReportPortRate $ntgrTG $ntgrPort2]

set dif [expr [lindex $TxRx2 1]/ [lindex $TxRx1 0]]

if {$dif >= 0.9 && $dif <= 1.1} {set result 1} else {set result 0}
sleep 10

for {set i 1} {$i <= 10} {incr i} {
	Netgear_log_file "TC-OneWayACL-039.tcl" "Enable one-way ACL"	
	foreach switch_name $ntgr_OneWayACL_List_039 {
		foreach every_acl $acl {
			CALEnableIPACLOninterface $switch_name $every_acl
		} 
	}
	
	sleep 20
	
	set dif 0
	set TxRx1 [UALReportPortRate $ntgrTG $ntgrPort1]
	set TxRx2 [UALReportPortRate $ntgrTG $ntgrPort2]
	
	set dif [expr [lindex $TxRx2 1]/ [lindex $TxRx1 0]]
	
	if {$dif >= 0.4 && $dif <= 0.6} {set result1 1} else {set result1 0}
	set result [expr {$result * $result1}]
	
	Netgear_log_file "TC-OneWayACL-039.tcl" "Disable one-way ACL"	
	foreach switch_name $ntgr_OneWayACL_List_039 {
		foreach every_acl $acl {
			CALDisableIPACLOninterface $switch_name $every_acl
		} 
	}
	sleep 20
	
	set dif 0
	set TxRx1 [UALReportPortRate $ntgrTG $ntgrPort1]
	set TxRx2 [UALReportPortRate $ntgrTG $ntgrPort2]
	
	set dif [expr [lindex $TxRx2 1]/ [lindex $TxRx1 0]]
	
	if {$dif >= 0.9 && $dif <= 1.1} {set result1 1} else {set result1 0}
	set result [expr {$result * $result1}]
	
	sleep 20
}

if {$result == 1} {
	Netgear_log_file "TC-OneWayACL-039.tcl" "***** TC-OneWayACL-039.tcl passed *****"
} else {
	Netgear_log_file "TC-OneWayACL-039.tcl" "***** TC-OneWayACL-039.tcl failed *****"
}

Netgear_log_file "TC-OneWayACL-039.tcl" "Stop to send traffic from IXIA"

UALStopTrafficPerPort $ntgrTG $ntgrPort1

foreach {chassis_id portlist} [array get ntgr_RouterPortList_039] {
	foreach port $portlist {
		UALClearOwnership $chassis_id $port
	}
	UALDisconnectTester $chassis_id
}

Netgear_log_file "TC-OneWayACL-039.tcl" "***** Completed Test case TC-OneWayACL-039.tcl *****"

#*********************  End of Test case  ***************************************

