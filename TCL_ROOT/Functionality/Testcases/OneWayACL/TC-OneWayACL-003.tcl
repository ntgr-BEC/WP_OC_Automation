#################################################################################
#
#  File Name	    	: TC-OneWayACL-003.tcl
#
#  Description     	: Permit TCP flag --- ack pointer
#
#  Config file          : TC-OneWayACL-003.cfg
#				  
#  Revision History 	:
#        Date          Programmer          Description
#        -----------------------------------------------------------------------
#        09/04/07      Nina Cheng          Created
#
#
##################################################################################

########################### start to configuration ###############################

Netgear_log_file "TC-OneWayACL-003.tcl" "******** Starting Test case TC-OneWayACL-003.tcl **********"

foreach switch_name $MTSW_OneWayACL_003 {
    Netgear_log_file "TC-OneWayACL-003.tcl" "Clear current configuration on switch $switch_name and configure some initial info"
    CALResetConfiguration $switch_name
    CALSetSwitchPrompt $switch_name
    CALSetSwitchMngrIPAddr $switch_name
}

Netgear_log_file "TC-OneWayACL-003.tcl" "Started routing on the Switches"

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

sleep 20

Netgear_log_file "TC-OneWayACL-003.tcl" "Started configuration of one-way ACL on the Switches"

foreach switch_name $ntgr_OneWayACL_List_003 {
  
  set ip_acl_id_list [getIPACLID $switch_name]
  set ip_acl_rule_list [ getIPACLRule $switch_name]
  CALAddIPACL $switch_name $ip_acl_rule_list
}

Netgear_log_file "TC-OneWayACL-003.tcl" "Started Configuration of IXIA"

foreach {chassis_id portlist} [array get ntgr_RouterPortList_003] {
	UALConnectTester $chassis_id
	foreach port $portlist {
		UALTakeOwnership $chassis_id $port
		UALLoadPort $chassis_id $port
	}
}

sleep 10
Netgear_log_file "TC-OneWayACL-003.tcl" "Start to send traffic from IXIA"

UALStartTrafficPerPort $ntgrTG $ntgrPort1
UALStartTrafficPerPort $ntgrTG $ntgrPort2

sleep 50

set dif 0
set TxRx1 [UALReportPortRate $ntgrTG $ntgrPort1]
set TxRx2 [UALReportPortRate $ntgrTG $ntgrPort2]

set dif [expr [lindex $TxRx2 1]/ [lindex $TxRx1 1]]

if {$dif >= 0.9 && $dif <= 1.1} {set result1 1} else {set result1 0}

Netgear_log_file "TC-OneWayACL-003.tcl" "Enable one-way ACL to permit stream with TCP falg ACK=1"

foreach switch_name $ntgr_OneWayACL_List_003 {
  CALEnableIPACLOninterface $switch_name $acl(1)
}

sleep 50

set dif 0
set TxRx1 [UALReportPortRate $ntgrTG $ntgrPort1]
set TxRx2 [UALReportPortRate $ntgrTG $ntgrPort2]

set dif [expr [lindex $TxRx2 1]/ [lindex $TxRx1 0]]

if {$dif >= 0.4 && $dif <= 0.6} {set result2 1} else {set result2 0}

foreach switch_name $ntgr_OneWayACL_List_003 {
  CALDisableIPACLOninterface $switch_name $acl(1)
}

Netgear_log_file "TC-OneWayACL-003.tcl" "Enable one-way ACL to permit stream with TCP falg ACK=0"

foreach switch_name $ntgr_OneWayACL_List_003 {
  CALEnableIPACLOninterface $switch_name $acl(2)
}

sleep 20

set dif 0
set TxRx1 [UALReportPortRate $ntgrTG $ntgrPort1]
set TxRx2 [UALReportPortRate $ntgrTG $ntgrPort2]

set dif [expr [lindex $TxRx2 1]/ [lindex $TxRx1 0]]

if {$dif >= 0.4 && $dif <= 0.6} {set result3 1} else {set result3 0}

foreach switch_name $ntgr_OneWayACL_List_003 {
  CALDisableIPACLOninterface $switch_name $acl(2)
}

Netgear_log_file "TC-OneWayACL-003.tcl" "Enable one-way ACL to permit stream with TCP falg ACK = ignore"

foreach switch_name $ntgr_OneWayACL_List_003 {
  CALEnableIPACLOninterface $switch_name $acl(3)
}

sleep 50

set dif 0
set TxRx1 [UALReportPortRate $ntgrTG $ntgrPort1]
set TxRx2 [UALReportPortRate $ntgrTG $ntgrPort2]

set dif [expr [lindex $TxRx2 1]/ [lindex $TxRx1 0]]

if {$dif >= 0.9 && $dif <= 1.1} {set result4 1} else {set result4 0}

foreach switch_name $ntgr_OneWayACL_List_003 {
  CALDisableIPACLOninterface $switch_name $acl(3)
}

if {$result1 == 1 && $result2 == 1 && $result3 == 1 && $result4 == 1} {
	Netgear_log_file "TC-OneWayACL-003.tcl" "***** TC-OneWayACL-003.tcl passed *****"
} else {
	Netgear_log_file "TC-OneWayACL-003.tcl" "***** TC-OneWayACL-003.tcl failed *****"
}

Netgear_log_file "TC-OneWayACL-003.tcl" "Stop to send traffic from IXIA"

UALStopTrafficPerPort $ntgrTG $ntgrPort1
UALStopTrafficPerPort $ntgrTG $ntgrPort2

foreach {chassis_id portlist} [array get ntgr_RouterPortList_003] {
	foreach port $portlist {
		UALClearOwnership $chassis_id $port
	}
	UALDisconnectTester $chassis_id
}

Netgear_log_file "TC-OneWayACL-003.tcl" "***** Completed Test case TC-OneWayACL-003.tcl *****"

#*********************  End of Test case  ***************************************



