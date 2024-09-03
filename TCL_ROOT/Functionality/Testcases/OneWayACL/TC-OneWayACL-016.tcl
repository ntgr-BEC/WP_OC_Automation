#################################################################################
#
#  File Name	    	: TC-OneWayACL-016.tcl
#
#  Description     	: deny TCP flag --- FIN,ACK
#
#  Config file          : TC-OneWayACL-016.cfg
#				  
#  Revision History 	:
#        Date          Programmer          Description
#        -----------------------------------------------------------------------
#        09/04/07      Nina Cheng          Created
#
#
##################################################################################

########################### start to configuration ###############################

Netgear_log_file "TC-OneWayACL-016.tcl" "******** Starting Test case TC-OneWayACL-016.tcl **********"

foreach switch_name $MTSW_OneWayACL_016 {
    Netgear_log_file "TC-OneWayACL-016.tcl" "Clear current configuration on switch $switch_name and configure some initial info"
    CALResetConfiguration $switch_name
    CALSetSwitchPrompt $switch_name
    CALSetSwitchMngrIPAddr $switch_name
}

Netgear_log_file "TC-OneWayACL-016.tcl" "Started routing on the Switches"

foreach {switch_name portlist} [array get ntgr_L3SwitchList_016] {
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

Netgear_log_file "TC-OneWayACL-016.tcl" "Started configuration of one-way ACL on the Switches"

foreach switch_name $ntgr_OneWayACL_List_016 {
  
  set ip_acl_id_list [getIPACLID $switch_name]
  set ip_acl_rule_list [ getIPACLRule $switch_name]
  CALAddIPACL $switch_name $ip_acl_rule_list
}

Netgear_log_file "TC-OneWayACL-016.tcl" "Started Configuration of IXIA"

foreach {chassis_id portlist} [array get ntgr_RouterPortList_016] {
	UALConnectTester $chassis_id
	foreach port $portlist {
		UALTakeOwnership $chassis_id $port
		UALLoadPort $chassis_id $port
	}
}

sleep 10
Netgear_log_file "TC-OneWayACL-016.tcl" "Start to send traffic from IXIA"

UALStartTrafficPerPort $ntgrTG $ntgrPort1
UALStartTrafficPerPort $ntgrTG $ntgrPort2

sleep 50

set dif 0
set TxRx1 [UALReportPortRate $ntgrTG $ntgrPort1]
set TxRx2 [UALReportPortRate $ntgrTG $ntgrPort2]

set dif [expr [lindex $TxRx2 1]/ [lindex $TxRx1 1]]

if {$dif >= 0.9 && $dif <= 1.1} {set result1 1} else {set result1 0}

Netgear_log_file "TC-OneWayACL-016.tcl" "Enable one-way ACL to deny stream with TCP falg FIN,ACK=1"

foreach switch_name $ntgr_OneWayACL_List_016 {
  CALEnableIPACLOninterface $switch_name $acl(1)
}

sleep 50

set dif 0
set TxRx1 [UALReportPortRate $ntgrTG $ntgrPort1]
set TxRx2 [UALReportPortRate $ntgrTG $ntgrPort2]

set dif [expr [lindex $TxRx2 1]/ [lindex $TxRx1 0]]

if {$dif >= 0.74 && $dif <= 0.76} {set result2 1} else {set result2 0}

foreach switch_name $ntgr_OneWayACL_List_016 {
  CALDisableIPACLOninterface $switch_name $acl(1)
}

Netgear_log_file "TC-OneWayACL-016.tcl" "Enable one-way ACL to deny stream with TCP falg FIN=1,ACK=0"

foreach switch_name $ntgr_OneWayACL_List_016 {
  CALEnableIPACLOninterface $switch_name $acl(2)
}

sleep 20

set dif 0
set TxRx1 [UALReportPortRate $ntgrTG $ntgrPort1]
set TxRx2 [UALReportPortRate $ntgrTG $ntgrPort2]

set dif [expr [lindex $TxRx2 1]/ [lindex $TxRx1 0]]

if {$dif >= 0.74 && $dif <= 0.76} {set result3 1} else {set result3 0}

foreach switch_name $ntgr_OneWayACL_List_016 {
  CALDisableIPACLOninterface $switch_name $acl(2)
}

Netgear_log_file "TC-OneWayACL-016.tcl" "Enable one-way ACL to deny stream with TCP falg FIN= 0,ACK=1"

foreach switch_name $ntgr_OneWayACL_List_016 {
  CALEnableIPACLOninterface $switch_name $acl(3)
}

sleep 50

set dif 0
set TxRx1 [UALReportPortRate $ntgrTG $ntgrPort1]
set TxRx2 [UALReportPortRate $ntgrTG $ntgrPort2]

set dif [expr [lindex $TxRx2 1]/ [lindex $TxRx1 0]]

if {$dif >= 0.74 && $dif <= 0.76} {set result4 1} else {set result4 0}

foreach switch_name $ntgr_OneWayACL_List_016 {
  CALDisableIPACLOninterface $switch_name $acl(3)
}

Netgear_log_file "TC-OneWayACL-016.tcl" "Enable one-way ACL to deny stream with TCP falg FIN=0,ACK=0"

foreach switch_name $ntgr_OneWayACL_List_016 {
  CALEnableIPACLOninterface $switch_name $acl(4)
}

sleep 50

set dif 0
set TxRx1 [UALReportPortRate $ntgrTG $ntgrPort1]
set TxRx2 [UALReportPortRate $ntgrTG $ntgrPort2]

set dif [expr [lindex $TxRx2 1]/ [lindex $TxRx1 0]]

if {$dif >= 0.74 && $dif <= 0.76} {set result5 1} else {set result5 0}

Netgear_log_file "TC-OneWayACL-016.tcl" "Enable one-way ACL to deny stream with TCP falg FIN,ACK= ignore"

foreach switch_name $ntgr_OneWayACL_List_016 {
  CALDisableIPACLOninterface $switch_name $acl(4)
}

sleep 50

set dif 0
set TxRx1 [UALReportPortRate $ntgrTG $ntgrPort1]
set TxRx2 [UALReportPortRate $ntgrTG $ntgrPort2]

set dif [expr [lindex $TxRx2 1]/ [lindex $TxRx1 0]]

if {$dif >= 0.9 && $dif <= 1.1} {set result6 1} else {set result6 0}

if {$result1 == 1 && $result2 == 1 && $result3 == 1 && $result4 == 1 && $result5 == 1 && $result6 == 1} {
	Netgear_log_file "TC-OneWayACL-016.tcl" "***** TC-OneWayACL-016.tcl passed *****"
} else {
	Netgear_log_file "TC-OneWayACL-016.tcl" "***** TC-OneWayACL-016.tcl failed *****"
}

Netgear_log_file "TC-OneWayACL-016.tcl" "Stop to send traffic from IXIA"

UALStopTrafficPerPort $ntgrTG $ntgrPort1
UALStopTrafficPerPort $ntgrTG $ntgrPort2

foreach {chassis_id portlist} [array get ntgr_RouterPortList_016] {
	foreach port $portlist {
		UALClearOwnership $chassis_id $port
	}
	UALDisconnectTester $chassis_id
}

Netgear_log_file "TC-OneWayACL-016.tcl" "***** Completed Test case TC-OneWayACL-016.tcl *****"

#*********************  End of Test case  ***************************************





