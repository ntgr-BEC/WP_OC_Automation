#################################################################################
#
#  File Name	    	: TC-OneWayACL-026.tcl
#
#  Description     	: deny tcp flag -- syn,ack,fin,rst,psh,urg
#
#  Config file          : TC-OneWayACL-026.cfg
#				  
#  Revision History 	:
#        Date          Programmer          Description
#        -----------------------------------------------------------------------
#        09/04/07      Nina Cheng          Created
#
#
##################################################################################

########################### start to configuration ###############################

Netgear_log_file "TC-OneWayACL-026.tcl" "******** Starting Test case TC-OneWayACL-026.tcl **********"

foreach switch_name $MTSW_OneWayACL_026 {
    Netgear_log_file "TC-OneWayACL-026.tcl" "Clear current configuration on switch $switch_name and configure some initial info"
    CALResetConfiguration $switch_name
    CALSetSwitchPrompt $switch_name
    CALSetSwitchMngrIPAddr $switch_name
}

Netgear_log_file "TC-OneWayACL-026.tcl" "Started routing on the Switches"

foreach {switch_name portlist} [array get ntgr_L3SwitchList_026] {
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

Netgear_log_file "TC-OneWayACL-026.tcl" "Started configuration of one-way ACL on the Switches"

foreach switch_name $ntgr_OneWayACL_List_026 {
  
  set ip_acl_id_list [getIPACLID $switch_name]
  set ip_acl_rule_list [ getIPACLRule $switch_name]
  CALAddIPACL $switch_name $ip_acl_rule_list
}

Netgear_log_file "TC-OneWayACL-026.tcl" "Started Configuration of IXIA"

foreach {chassis_id portlist} [array get ntgr_RouterPortList_026] {
	UALConnectTester $chassis_id
	foreach port $portlist {
		UALTakeOwnership $chassis_id $port
		UALLoadPort $chassis_id $port
	}
}

sleep 10
Netgear_log_file "TC-OneWayACL-026.tcl" "Start to send traffic from IXIA"

UALStartTrafficPerPort $ntgrTG $ntgrPort1
set result 0
sleep 50

set dif 0
set TxRx1 [UALReportPortRate $ntgrTG $ntgrPort1]
set TxRx2 [UALReportPortRate $ntgrTG $ntgrPort2]

set dif [expr [lindex $TxRx2 1]/ [lindex $TxRx1 0]]

if {$dif >= 0.9 && $dif <= 1.1} {set result1 1} else {set result1 0}
set result $result1

Netgear_log_file "TC-OneWayACL-026.tcl" "Enable one-way ACL to deny stream with TCP falg SYN=1,ACK=1,FIN=1,RST=1,PSH=1,URG=1"

foreach switch_name $ntgr_OneWayACL_List_026 {
  CALEnableIPACLOninterface $switch_name $acl(1)
}

sleep 50

set dif 0
set TxRx1 [UALReportPortRate $ntgrTG $ntgrPort1]
set TxRx2 [UALReportPortRate $ntgrTG $ntgrPort2]

set dif [expr [lindex $TxRx2 1]/ [lindex $TxRx1 0]]

if {$dif >= 0.857 && $dif <= 0.858} {set result2 1} else {set result2 0}
set result [expr {$result * $result2}]

foreach switch_name $ntgr_OneWayACL_List_026 {
  CALDisableIPACLOninterface $switch_name $acl(1)
}

Netgear_log_file "TC-OneWayACL-026.tcl" "Enable one-way ACL to deny stream with TCP falg SYN=0,ACK=0,FIN=0,RST=0,PSH=0,URG=0"

foreach switch_name $ntgr_OneWayACL_List_026 {
  CALEnableIPACLOninterface $switch_name $acl(2)
}

sleep 20

set dif 0
set TxRx1 [UALReportPortRate $ntgrTG $ntgrPort1]
set TxRx2 [UALReportPortRate $ntgrTG $ntgrPort2]

set dif [expr [lindex $TxRx2 1]/ [lindex $TxRx1 0]]

if {$dif >= 0.857 && $dif <= 0.858} {set result3 1} else {set result3 0}
set result [expr {$result * $result3}]

foreach switch_name $ntgr_OneWayACL_List_026 {
  CALDisableIPACLOninterface $switch_name $acl(2)
}

Netgear_log_file "TC-OneWayACL-026.tcl" "Enable one-way ACL to deny stream with TCP falg SYN=1,ACK=0,FIN=1,RST=0,PSH=1,URG=0"

foreach switch_name $ntgr_OneWayACL_List_026 {
  CALEnableIPACLOninterface $switch_name $acl(3)
}

sleep 50

set dif 0
set TxRx1 [UALReportPortRate $ntgrTG $ntgrPort1]
set TxRx2 [UALReportPortRate $ntgrTG $ntgrPort2]

set dif [expr [lindex $TxRx2 1]/ [lindex $TxRx1 0]]

if {$dif >= 0.857 && $dif <= 0.858} {set result4 1} else {set result4 0}
set result [expr {$result * $result4}]

foreach switch_name $ntgr_OneWayACL_List_026 {
  CALDisableIPACLOninterface $switch_name $acl(3)
}

Netgear_log_file "TC-OneWayACL-026.tcl" "Enable one-way ACL to deny stream with TCP falg SYN=0,ACK=1,FIN=0,RST=1,PSH=0,URG=1"

foreach switch_name $ntgr_OneWayACL_List_026 {
  CALEnableIPACLOninterface $switch_name $acl(4)
}

sleep 50

set dif 0
set TxRx1 [UALReportPortRate $ntgrTG $ntgrPort1]
set TxRx2 [UALReportPortRate $ntgrTG $ntgrPort2]

set dif [expr [lindex $TxRx2 1]/ [lindex $TxRx1 0]]

if {$dif >= 0.857 && $dif <= 0.858} {set result5 1} else {set result5 0}
set result [expr {$result * $result5}]

foreach switch_name $ntgr_OneWayACL_List_026 {
  CALDisableIPACLOninterface $switch_name $acl(4)
}

Netgear_log_file "TC-OneWayACL-026.tcl" "Enable one-way ACL to deny stream with TCP falg SYN=1,ACK=1,FIN=1,RST=0,PSH=0,URG=0"

foreach switch_name $ntgr_OneWayACL_List_026 {
  CALEnableIPACLOninterface $switch_name $acl(5)
}

sleep 50

set dif 0
set TxRx1 [UALReportPortRate $ntgrTG $ntgrPort1]
set TxRx2 [UALReportPortRate $ntgrTG $ntgrPort2]

set dif [expr [lindex $TxRx2 1]/ [lindex $TxRx1 0]]

if {$dif >= 0.857 && $dif <= 0.858} {set result6 1} else {set result6 0}
set result [expr {$result * $result6}]

foreach switch_name $ntgr_OneWayACL_List_026 {
  CALDisableIPACLOninterface $switch_name $acl(5)
}

Netgear_log_file "TC-OneWayACL-026.tcl" "Enable one-way ACL to deny stream with TCP falg SYN=0,ACK=0,FIN=0,RST=1,PSH=1,URG=1"

foreach switch_name $ntgr_OneWayACL_List_026 {
  CALEnableIPACLOninterface $switch_name $acl(6)
}

sleep 50

set dif 0
set TxRx1 [UALReportPortRate $ntgrTG $ntgrPort1]
set TxRx2 [UALReportPortRate $ntgrTG $ntgrPort2]

set dif [expr [lindex $TxRx2 1]/ [lindex $TxRx1 0]]

if {$dif >= 0.857 && $dif <= 0.858} {set result7 1} else {set result7 0}
set result [expr {$result * $result7}]

foreach switch_name $ntgr_OneWayACL_List_026 {
  CALDisableIPACLOninterface $switch_name $acl(6)
}

Netgear_log_file "TC-OneWayACL-026.tcl" "Enable one-way ACL to deny stream with TCP falg SYN=0,ACK=1,FIN=1,RST=1,PSH=1,URG=0"

foreach switch_name $ntgr_OneWayACL_List_026 {
  CALEnableIPACLOninterface $switch_name $acl(7)
}

sleep 50

set dif 0
set TxRx1 [UALReportPortRate $ntgrTG $ntgrPort1]
set TxRx2 [UALReportPortRate $ntgrTG $ntgrPort2]

set dif [expr [lindex $TxRx2 1]/ [lindex $TxRx1 0]]

if {$dif >= 0.857 && $dif <= 0.858} {set result8 1} else {set result8 0}
set result [expr {$result * $result8}]

foreach switch_name $ntgr_OneWayACL_List_026 {
  CALDisableIPACLOninterface $switch_name $acl(7)
}

Netgear_log_file "TC-OneWayACL-026.tcl" "Enable one-way ACL to deny stream with TCP falg SYN,ACK,FIN,RST,PSH,URG = ignore"

sleep 50

set dif 0
set TxRx1 [UALReportPortRate $ntgrTG $ntgrPort1]
set TxRx2 [UALReportPortRate $ntgrTG $ntgrPort2]

set dif [expr [lindex $TxRx2 1]/ [lindex $TxRx1 0]]

if {$dif >= 0.9 && $dif <= 1.1} {set result9 1} else {set result9 0}
set result [expr {$result * $result9}]

if {$result == 1} {
	Netgear_log_file "TC-OneWayACL-026.tcl" "***** TC-OneWayACL-026.tcl passed *****"
} else {
	Netgear_log_file "TC-OneWayACL-026.tcl" "***** TC-OneWayACL-026.tcl failed *****"
}

Netgear_log_file "TC-OneWayACL-026.tcl" "Stop to send traffic from IXIA"

UALStopTrafficPerPort $ntgrTG $ntgrPort1

foreach {chassis_id portlist} [array get ntgr_RouterPortList_026] {
	foreach port $portlist {
		UALClearOwnership $chassis_id $port
	}
	UALDisconnectTester $chassis_id
}

Netgear_log_file "TC-OneWayACL-026.tcl" "***** Completed Test case TC-OneWayACL-026.tcl *****"

#*********************  End of Test case  ***************************************




