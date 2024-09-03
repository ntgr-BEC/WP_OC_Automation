#################################################################################
#
#  File Name	    	: TC-OneWayACL-023.tcl
#
#  Description     	: Deny tcp flag -- FIN,ACK,RST,PSH
#
#  Config file          : TC-OneWayACL-023.cfg
#				  
#  Revision History 	:
#        Date          Programmer          Description
#        -----------------------------------------------------------------------
#        09/04/07      Nina Cheng          Created
#
#
##################################################################################

########################### start to configuration ###############################

Netgear_log_file "TC-OneWayACL-023.tcl" "******** Starting Test case TC-OneWayACL-023.tcl **********"

foreach switch_name $MTSW_OneWayACL_023 {
    Netgear_log_file "TC-OneWayACL-023.tcl" "Clear current configuration on switch $switch_name and configure some initial info"
    CALResetConfiguration $switch_name
    CALSetSwitchPrompt $switch_name
    CALSetSwitchMngrIPAddr $switch_name
}

Netgear_log_file "TC-OneWayACL-023.tcl" "Started routing on the Switches"

foreach {switch_name portlist} [array get ntgr_L3SwitchList_023] {
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

Netgear_log_file "TC-OneWayACL-023.tcl" "Started configuration of one-way ACL on the Switches"

foreach switch_name $ntgr_OneWayACL_List_023 {
  
  set ip_acl_id_list [getIPACLID $switch_name]
  set ip_acl_rule_list [ getIPACLRule $switch_name]
  CALAddIPACL $switch_name $ip_acl_rule_list
}

Netgear_log_file "TC-OneWayACL-023.tcl" "Started Configuration of IXIA"

foreach {chassis_id portlist} [array get ntgr_RouterPortList_023] {
	UALConnectTester $chassis_id
	foreach port $portlist {
		UALTakeOwnership $chassis_id $port
		UALLoadPort $chassis_id $port
	}
}

sleep 10
Netgear_log_file "TC-OneWayACL-023.tcl" "Start to send traffic from IXIA"

UALStartTrafficPerPort $ntgrTG $ntgrPort1
set result 0
sleep 50

set dif 0
set TxRx1 [UALReportPortRate $ntgrTG $ntgrPort1]
set TxRx2 [UALReportPortRate $ntgrTG $ntgrPort2]

set dif [expr [lindex $TxRx2 1]/ [lindex $TxRx1 0]]

if {$dif >= 0.9 && $dif <= 1.1} {set result1 1} else {set result1 0}
set result $result1

Netgear_log_file "TC-OneWayACL-023.tcl" "Enable one-way ACL to deny stream with TCP falg FIN=1,ACK=1,RST=1,PSH=1"

foreach switch_name $ntgr_OneWayACL_List_023 {
  CALEnableIPACLOninterface $switch_name $acl(1)
}

sleep 50

set dif 0
set TxRx1 [UALReportPortRate $ntgrTG $ntgrPort1]
set TxRx2 [UALReportPortRate $ntgrTG $ntgrPort2]

set dif [expr [lindex $TxRx2 1]/ [lindex $TxRx1 0]]

if {$dif >= 0.937 && $dif <= 0.938} {set result2 1} else {set result2 0}
set result [expr {$result * $result2}]

foreach switch_name $ntgr_OneWayACL_List_023 {
  CALDisableIPACLOninterface $switch_name $acl(1)
}

Netgear_log_file "TC-OneWayACL-023.tcl" "Enable one-way ACL to deny stream with TCP falg FIN=1,ACK=1,RST=1,PSH=0"

foreach switch_name $ntgr_OneWayACL_List_023 {
  CALEnableIPACLOninterface $switch_name $acl(2)
}

sleep 20

set dif 0
set TxRx1 [UALReportPortRate $ntgrTG $ntgrPort1]
set TxRx2 [UALReportPortRate $ntgrTG $ntgrPort2]

set dif [expr [lindex $TxRx2 1]/ [lindex $TxRx1 0]]

if {$dif >= 0.937 && $dif <= 0.938} {set result3 1} else {set result3 0}
set result [expr {$result * $result3}]

foreach switch_name $ntgr_OneWayACL_List_023 {
  CALDisableIPACLOninterface $switch_name $acl(2)
}

Netgear_log_file "TC-OneWayACL-023.tcl" "Enable one-way ACL to deny stream with TCP falg FIN=1,ACK=1,RST=0,PSH=1"

foreach switch_name $ntgr_OneWayACL_List_023 {
  CALEnableIPACLOninterface $switch_name $acl(3)
}

sleep 50

set dif 0
set TxRx1 [UALReportPortRate $ntgrTG $ntgrPort1]
set TxRx2 [UALReportPortRate $ntgrTG $ntgrPort2]

set dif [expr [lindex $TxRx2 1]/ [lindex $TxRx1 0]]

if {$dif >= 0.937 && $dif <= 0.938} {set result4 1} else {set result4 0}
set result [expr {$result * $result4}]

foreach switch_name $ntgr_OneWayACL_List_023 {
  CALDisableIPACLOninterface $switch_name $acl(3)
}

Netgear_log_file "TC-OneWayACL-023.tcl" "Enable one-way ACL to deny stream with TCP falg FIN=1,ACK=1,RST=0,PSH=0"

foreach switch_name $ntgr_OneWayACL_List_023 {
  CALEnableIPACLOninterface $switch_name $acl(4)
}

sleep 50

set dif 0
set TxRx1 [UALReportPortRate $ntgrTG $ntgrPort1]
set TxRx2 [UALReportPortRate $ntgrTG $ntgrPort2]

set dif [expr [lindex $TxRx2 1]/ [lindex $TxRx1 0]]

if {$dif >= 0.937 && $dif <= 0.938} {set result5 1} else {set result5 0}
set result [expr {$result * $result5}]

foreach switch_name $ntgr_OneWayACL_List_023 {
  CALDisableIPACLOninterface $switch_name $acl(4)
}

Netgear_log_file "TC-OneWayACL-023.tcl" "Enable one-way ACL to deny stream with TCP falg FIN=1,ACK=0,RST=1,PSH=1"

foreach switch_name $ntgr_OneWayACL_List_023 {
  CALEnableIPACLOninterface $switch_name $acl(5)
}

sleep 50

set dif 0
set TxRx1 [UALReportPortRate $ntgrTG $ntgrPort1]
set TxRx2 [UALReportPortRate $ntgrTG $ntgrPort2]

set dif [expr [lindex $TxRx2 1]/ [lindex $TxRx1 0]]

if {$dif >= 0.937 && $dif <= 0.938} {set result6 1} else {set result6 0}
set result [expr {$result * $result6}]

foreach switch_name $ntgr_OneWayACL_List_023 {
  CALDisableIPACLOninterface $switch_name $acl(5)
}

Netgear_log_file "TC-OneWayACL-023.tcl" "Enable one-way ACL to deny stream with TCP falg FIN=1,ACK=0,RST=1,PSH=0"

foreach switch_name $ntgr_OneWayACL_List_023 {
  CALEnableIPACLOninterface $switch_name $acl(6)
}

sleep 50

set dif 0
set TxRx1 [UALReportPortRate $ntgrTG $ntgrPort1]
set TxRx2 [UALReportPortRate $ntgrTG $ntgrPort2]

set dif [expr [lindex $TxRx2 1]/ [lindex $TxRx1 0]]

if {$dif >= 0.937 && $dif <= 0.938} {set result7 1} else {set result7 0}
set result [expr {$result * $result7}]

foreach switch_name $ntgr_OneWayACL_List_023 {
  CALDisableIPACLOninterface $switch_name $acl(6)
}

Netgear_log_file "TC-OneWayACL-023.tcl" "Enable one-way ACL to deny stream with TCP falg FIN=1,ACK=0,RST=0,PSH=1"

foreach switch_name $ntgr_OneWayACL_List_023 {
  CALEnableIPACLOninterface $switch_name $acl(7)
}

sleep 50

set dif 0
set TxRx1 [UALReportPortRate $ntgrTG $ntgrPort1]
set TxRx2 [UALReportPortRate $ntgrTG $ntgrPort2]

set dif [expr [lindex $TxRx2 1]/ [lindex $TxRx1 0]]

if {$dif >= 0.937 && $dif <= 0.938} {set result8 1} else {set result8 0}
set result [expr {$result * $result8}]

foreach switch_name $ntgr_OneWayACL_List_023 {
  CALDisableIPACLOninterface $switch_name $acl(7)
}

Netgear_log_file "TC-OneWayACL-023.tcl" "Enable one-way ACL to deny stream with TCP falg FIN=1,ACK=0,RST=0,PSH=0"

foreach switch_name $ntgr_OneWayACL_List_023 {
  CALEnableIPACLOninterface $switch_name $acl(8)
}

sleep 50

set dif 0
set TxRx1 [UALReportPortRate $ntgrTG $ntgrPort1]
set TxRx2 [UALReportPortRate $ntgrTG $ntgrPort2]

set dif [expr [lindex $TxRx2 1]/ [lindex $TxRx1 0]]

if {$dif >= 0.937 && $dif <= 0.938} {set result9 1} else {set result9 0}
set result [expr {$result * $result9}]

foreach switch_name $ntgr_OneWayACL_List_023 {
  CALDisableIPACLOninterface $switch_name $acl(8)
}

Netgear_log_file "TC-OneWayACL-023.tcl" "Enable one-way ACL to deny stream with TCP falg FIN=0,ACK=1,RST=1,PSH=1"

foreach switch_name $ntgr_OneWayACL_List_023 {
  CALEnableIPACLOninterface $switch_name $acl(9)
}

sleep 50

set dif 0
set TxRx1 [UALReportPortRate $ntgrTG $ntgrPort1]
set TxRx2 [UALReportPortRate $ntgrTG $ntgrPort2]

set dif [expr [lindex $TxRx2 1]/ [lindex $TxRx1 0]]

if {$dif >= 0.937 && $dif <= 0.938} {set result10 1} else {set result10 0}
set result [expr {$result * $result10}]

foreach switch_name $ntgr_OneWayACL_List_023 {
  CALDisableIPACLOninterface $switch_name $acl(9)
}


Netgear_log_file "TC-OneWayACL-023.tcl" "Enable one-way ACL to deny stream with TCP falg FIN=0,ACK=1,RST=1,PSH=0"

foreach switch_name $ntgr_OneWayACL_List_023 {
  CALEnableIPACLOninterface $switch_name $acl(10)
}

sleep 50

set dif 0
set TxRx1 [UALReportPortRate $ntgrTG $ntgrPort1]
set TxRx2 [UALReportPortRate $ntgrTG $ntgrPort2]

set dif [expr [lindex $TxRx2 1]/ [lindex $TxRx1 0]]

if {$dif >= 0.937 && $dif <= 0.938} {set result11 1} else {set result11 0}
set result [expr {$result * $result11}]

foreach switch_name $ntgr_OneWayACL_List_023 {
  CALDisableIPACLOninterface $switch_name $acl(10)
}
Netgear_log_file "TC-OneWayACL-023.tcl" "Enable one-way ACL to deny stream with TCP falg FIN=0,ACK=1,RST=0,PSH=1"

foreach switch_name $ntgr_OneWayACL_List_023 {
  CALEnableIPACLOninterface $switch_name $acl(11)
}

sleep 50

set dif 0
set TxRx1 [UALReportPortRate $ntgrTG $ntgrPort1]
set TxRx2 [UALReportPortRate $ntgrTG $ntgrPort2]

set dif [expr [lindex $TxRx2 1]/ [lindex $TxRx1 0]]

if {$dif >= 0.937 && $dif <= 0.938} {set result12 1} else {set result12 0}
set result [expr {$result * $result12}]

foreach switch_name $ntgr_OneWayACL_List_023 {
  CALDisableIPACLOninterface $switch_name $acl(11)
}
Netgear_log_file "TC-OneWayACL-023.tcl" "Enable one-way ACL to deny stream with TCP falg FIN=0,ACK=1,RST=0,PSH=0"

foreach switch_name $ntgr_OneWayACL_List_023 {
  CALEnableIPACLOninterface $switch_name $acl(12)
}

sleep 50

set dif 0
set TxRx1 [UALReportPortRate $ntgrTG $ntgrPort1]
set TxRx2 [UALReportPortRate $ntgrTG $ntgrPort2]

set dif [expr [lindex $TxRx2 1]/ [lindex $TxRx1 0]]

if {$dif >= 0.937 && $dif <= 0.938} {set result13 1} else {set result13 0}
set result [expr {$result * $result13}]

foreach switch_name $ntgr_OneWayACL_List_023 {
  CALDisableIPACLOninterface $switch_name $acl(12)
}

Netgear_log_file "TC-OneWayACL-023.tcl" "Enable one-way ACL to deny stream with TCP falg FIN=0,ACK=0,RST=1,PSH=1"

foreach switch_name $ntgr_OneWayACL_List_023 {
  CALEnableIPACLOninterface $switch_name $acl(13)
}

sleep 50

set dif 0
set TxRx1 [UALReportPortRate $ntgrTG $ntgrPort1]
set TxRx2 [UALReportPortRate $ntgrTG $ntgrPort2]

set dif [expr [lindex $TxRx2 1]/ [lindex $TxRx1 0]]

if {$dif >= 0.937 && $dif <= 0.938} {set result14 1} else {set result14 0}
set result [expr {$result * $result14}]

foreach switch_name $ntgr_OneWayACL_List_023 {
  CALDisableIPACLOninterface $switch_name $acl(13)
}
Netgear_log_file "TC-OneWayACL-023.tcl" "Enable one-way ACL to deny stream with TCP falg FIN=0,ACK=0,RST=1,PSH=0"

foreach switch_name $ntgr_OneWayACL_List_023 {
  CALEnableIPACLOninterface $switch_name $acl(14)
}

sleep 50

set dif 0
set TxRx1 [UALReportPortRate $ntgrTG $ntgrPort1]
set TxRx2 [UALReportPortRate $ntgrTG $ntgrPort2]

set dif [expr [lindex $TxRx2 1]/ [lindex $TxRx1 0]]

if {$dif >= 0.937 && $dif <= 0.938} {set result15 1} else {set result15 0}
set result [expr {$result * $result15}]

foreach switch_name $ntgr_OneWayACL_List_023 {
  CALDisableIPACLOninterface $switch_name $acl(14)
}
Netgear_log_file "TC-OneWayACL-023.tcl" "Enable one-way ACL to deny stream with TCP falg FIN=0,ACK=0,RST=0,PSH=1"

foreach switch_name $ntgr_OneWayACL_List_023 {
  CALEnableIPACLOninterface $switch_name $acl(15)
}

sleep 50

set dif 0
set TxRx1 [UALReportPortRate $ntgrTG $ntgrPort1]
set TxRx2 [UALReportPortRate $ntgrTG $ntgrPort2]

set dif [expr [lindex $TxRx2 1]/ [lindex $TxRx1 0]]

if {$dif >= 0.937 && $dif <= 0.938} {set result16 1} else {set result16 0}
set result [expr {$result * $result16}]

foreach switch_name $ntgr_OneWayACL_List_023 {
  CALDisableIPACLOninterface $switch_name $acl(15)
}
Netgear_log_file "TC-OneWayACL-023.tcl" "Enable one-way ACL to deny stream with TCP falg FIN=0,ACK=0,RST=0,PSH=0"

foreach switch_name $ntgr_OneWayACL_List_023 {
  CALEnableIPACLOninterface $switch_name $acl(16)
}

sleep 50

set dif 0
set TxRx1 [UALReportPortRate $ntgrTG $ntgrPort1]
set TxRx2 [UALReportPortRate $ntgrTG $ntgrPort2]

set dif [expr [lindex $TxRx2 1]/ [lindex $TxRx1 0]]

if {$dif >= 0.937 && $dif <= 0.938} {set result17 1} else {set result17 0}
set result [expr {$result * $result17}]

Netgear_log_file "TC-OneWayACL-023.tcl" "Enable one-way ACL to deny stream with TCP falg FIN,ACK,RST,PSH = ignore"

foreach switch_name $ntgr_OneWayACL_List_023 {
  CALDisableIPACLOninterface $switch_name $acl(16)
}

sleep 50

set dif 0
set TxRx1 [UALReportPortRate $ntgrTG $ntgrPort1]
set TxRx2 [UALReportPortRate $ntgrTG $ntgrPort2]

set dif [expr [lindex $TxRx2 1]/ [lindex $TxRx1 0]]

if {$dif >= 0.9 && $dif <= 1.1} {set result18 1} else {set result18 0}
set result [expr {$result * $result18}]

if {$result == 1} {
	Netgear_log_file "TC-OneWayACL-023.tcl" "***** TC-OneWayACL-023.tcl passed *****"
} else {
	Netgear_log_file "TC-OneWayACL-023.tcl" "***** TC-OneWayACL-023.tcl failed *****"
}

Netgear_log_file "TC-OneWayACL-023.tcl" "Stop to send traffic from IXIA"

UALStopTrafficPerPort $ntgrTG $ntgrPort1

foreach {chassis_id portlist} [array get ntgr_RouterPortList_023] {
	foreach port $portlist {
		UALClearOwnership $chassis_id $port
	}
	UALDisconnectTester $chassis_id
}

Netgear_log_file "TC-OneWayACL-023.tcl" "***** Completed Test case TC-OneWayACL-023.tcl *****"

#*********************  End of Test case  ***************************************

