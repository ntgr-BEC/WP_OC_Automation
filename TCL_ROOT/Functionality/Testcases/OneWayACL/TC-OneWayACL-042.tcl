#################################################################################
#
#  File Name	    	: TC-OneWayACL-042.tcl
#
#  Description     	: Apply one-way ACL with MAC ACL together
#
#  Config file          : TC-OneWayACL-042.cfg
#				  
#  Revision History 	:
#        Date          Programmer          Description
#        -----------------------------------------------------------------------
#        09/04/07      Nina Cheng          Created
#
#
##################################################################################

########################### start to configuration ###############################

Netgear_log_file "TC-OneWayACL-042.tcl" "******** Starting Test case TC-OneWayACL-042.tcl **********"

foreach switch_name $MTSW_OneWayACL_042 {
    Netgear_log_file "TC-OneWayACL-042.tcl" "Clear current configuration on switch $switch_name and configure some initial info"
    CALResetConfiguration $switch_name
    CALSetSwitchPrompt $switch_name
    CALSetSwitchMngrIPAddr $switch_name
}

Netgear_log_file "TC-OneWayACL-042.tcl" "Started configuration of one-way ACL on the Switches"

foreach switch_name $ntgr_OneWayACL_List_042 {
  
  set ip_acl_id_list [getIPACLID $switch_name]
  set ip_acl_rule_list [ getIPACLRule $switch_name]
  set mac_acl_name_list [getMACACLID $switch_name]
  set mac_acl_rule_list [getMACACLRule $switch_name]
  CALAddIPACL $switch_name $ip_acl_rule_list
  CALAddMacACL  $switch_name $mac_acl_rule_list
}

Netgear_log_file "TC-OneWayACL-042.tcl" "Started Configuration of IXIA"

foreach {chassis_id portlist} [array get ntgr_RouterPortList_042] {
	UALConnectTester $chassis_id
	foreach port $portlist {
		UALTakeOwnership $chassis_id $port
		UALLoadPort $chassis_id $port
	}
}

sleep 10
Netgear_log_file "TC-OneWayACL-042.tcl" "Start to send traffic from IXIA"

UALStartTrafficPerPort $ntgrTG $ntgrPort1
UALStartTrafficPerPort $ntgrTG $ntgrPort2

sleep 50

set dif 0
set TxRx1 [UALReportPortRate $ntgrTG $ntgrPort1]
set TxRx2 [UALReportPortRate $ntgrTG $ntgrPort2]

set dif [expr [lindex $TxRx2 1]/ [lindex $TxRx1 0]]

if {$dif >= 0.9 && $dif <= 1.1} {set result1 1} else {set result1 0}

Netgear_log_file "TC-OneWayACL-042.tcl" "Enable one-way ACL to permit stream with TCP falg URG=1"

foreach switch_name $ntgr_OneWayACL_List_042 {
  CALEnableIPACLOninterface $switch_name $acl(1)
  CALInterfaceMACACLEnable $switch_name $MAC_acl(1)
}

sleep 50

set dif 0
set TxRx1 [UALReportPortRate $ntgrTG $ntgrPort1]
set TxRx2 [UALReportPortRate $ntgrTG $ntgrPort2]

set dif [expr [lindex $TxRx2 1]/ [lindex $TxRx1 0]]

if {$dif >= 0.4 && $dif <= 0.6} {set result2 1} else {set result2 0}

foreach switch_name $ntgr_OneWayACL_List_042 {
  CALDisableIPACLOninterface $switch_name $acl(1)
  CALInterfaceMACACLDisable $switch_name $MAC_acl(1)
}

Netgear_log_file "TC-OneWayACL-042.tcl" "Enable one-way ACL to permit stream with TCP falg URG=0"

foreach switch_name $ntgr_OneWayACL_List_042 {
  CALEnableIPACLOninterface $switch_name $acl(2)
  CALInterfaceMACACLEnable $switch_name $MAC_acl(2)
}

sleep 20

set dif 0
set TxRx1 [UALReportPortRate $ntgrTG $ntgrPort1]
set TxRx2 [UALReportPortRate $ntgrTG $ntgrPort2]

set dif [expr [lindex $TxRx2 1]/ [lindex $TxRx1 0]]

if {$dif >= 0.4 && $dif <= 0.6} {set result3 1} else {set result3 0}

foreach switch_name $ntgr_OneWayACL_List_042 {
  CALDisableIPACLOninterface $switch_name $acl(2)
  CALInterfaceMACACLDisable $switch_name $MAC_acl(2)
}

sleep 50

set dif 0
set TxRx1 [UALReportPortRate $ntgrTG $ntgrPort1]
set TxRx2 [UALReportPortRate $ntgrTG $ntgrPort2]

set dif [expr [lindex $TxRx2 1]/ [lindex $TxRx1 0]]

if {$dif >= 0.9 && $dif <= 1.1} {set result4 1} else {set result4 0}


if {$result1 == 1 && $result2 == 1 && $result3 == 1 && $result4 == 1} {
	Netgear_log_file "TC-OneWayACL-042.tcl" "***** TC-OneWayACL-042.tcl passed *****"
} else {
	Netgear_log_file "TC-OneWayACL-042.tcl" "***** TC-OneWayACL-042.tcl failed *****"
}

Netgear_log_file "TC-OneWayACL-042.tcl" "Stop to send traffic from IXIA"

UALStopTrafficPerPort $ntgrTG $ntgrPort1
UALStopTrafficPerPort $ntgrTG $ntgrPort2

foreach {chassis_id portlist} [array get ntgr_RouterPortList_042] {
	foreach port $portlist {
		UALClearOwnership $chassis_id $port
	}
	UALDisconnectTester $chassis_id
}

Netgear_log_file "TC-OneWayACL-042.tcl" "***** Completed Test case TC-OneWayACL-042.tcl *****"

#*********************  End of Test case  ***************************************




