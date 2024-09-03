#################################################################################
#
#  File Name	    	: TC-L3ACL-045.tcl
#
#  Description     	: Configure ACL with conflicting rules
#
#  Config file          : TC-L3ACL-045.cfg
#				  
#  Revision History 	:
#        Date          Programmer          Description
#        -----------------------------------------------------------------------
#        19/04/07      Nina Cheng          Created
#
#
##################################################################################

########################### start to configuration ###############################

Netgear_log_file "TC-L3ACL-045.tcl" "******** Starting Test case TC-L3ACL-045.tcl **********"

foreach switch_name $MTSW_L3ACL_045 {
    Netgear_log_file "TC-L3ACL-045.tcl" "Clear current configuration on switch $switch_name and configure some initial info"
    CALResetConfiguration $switch_name
    CALSetSwitchPrompt $switch_name
    CALSetSwitchMngrIPAddr $switch_name
}

Netgear_log_file "TC-L3ACL-045.tcl" "Started routing on the Switches"

foreach {switch_name portlist} [array get ntgr_L3SwitchList_045] {
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

Netgear_log_file "TC-L3ACL-045.tcl" "Started Configuration of VLAN"

foreach vlan_index $VLAN_index_list_045 {
	Netgear_log_file "TC-L3ACL-045.tcl" "Started Configuration of VLAN $vlan_index"
	CALCreateVlan $vlan_index
}

Netgear_log_file "TC-L3ACL-045.tcl" "Started configuration of ACL on the Switches"

CALCheckExpect $MTSW_L3ACL_045 $ACL_045_permit ""
sleep 1
CALCheckExpect $MTSW_L3ACL_045 $ACL_in ""

Netgear_log_file "TC-L3ACL-045.tcl" "Started Configuration of RIP on IXIA"

foreach {chassis_id portlist} [array get ntgr_RouterPortList_045] {
	UALConnectTester $chassis_id
	foreach port $portlist {
		UALTakeOwnership $chassis_id $port
		UALLoadPort $chassis_id $port
	}
}

UALStartTrafficPerPort $ntgrTG $ntgrPort2
sleep 50

set dif 0
set TxRx4 [UALReportPortRate $ntgrTG $ntgrPort4]
set TxRx2 [UALReportPortRate $ntgrTG $ntgrPort2]

set dif [expr [lindex $TxRx4 1]/ [lindex $TxRx2 0]]

if {$dif >= 0.95} {set result1 1} else {set result1 0}

Netgear_log_file "TC-L3ACL-045.tcl" "Change ACL to deny traffic on the Switches"

CALCheckExpect $MTSW_L3ACL_045 $ACL_no ""
sleep 1
CALCheckExpect $MTSW_L3ACL_045 $ACL_045_deny ""
sleep 1
CALCheckExpect $MTSW_L3ACL_045 $ACL_in ""

set dif 1
set TxRx4 [UALReportPortRate $ntgrTG $ntgrPort4]
set dif [lindex $TxRx4 1]
if {$dif == 0} {set result2 1} else {set result2 0}

Netgear_log_file "TC-L3ACL-045.tcl" "Change ACL to permit traffic on the Switches"
CALCheckExpect $MTSW_L3ACL_045 $ACL_no ""
sleep 1
CALCheckExpect $MTSW_L3ACL_045 $ACL_045_permit ""
sleep 1
CALCheckExpect $MTSW_L3ACL_045 $ACL_in ""

set dif 0
set TxRx4 [UALReportPortRate $ntgrTG $ntgrPort4]
set TxRx2 [UALReportPortRate $ntgrTG $ntgrPort2]

set dif [expr [lindex $TxRx4 1]/ [lindex $TxRx2 0]]

if {$dif >= 0.95} {set result3 1} else {set result3 0}

Netgear_log_file "TC-L3ACL-045.tcl" "Delete ACL on the Switches"

CALCheckExpect $MTSW_L3ACL_045 $ACL_no ""
sleep 1
set dif 0
set TxRx4 [UALReportPortRate $ntgrTG $ntgrPort4]
set TxRx2 [UALReportPortRate $ntgrTG $ntgrPort2]

set dif [expr [lindex $TxRx4 1]/ [lindex $TxRx2 0]]

if {$dif >= 0.95} {set result4 1} else {set result4 0}

if {$result1 == 1 && $result2 == 1 && $result3 == 1 && $result4 == 1} {
	Netgear_log_file "TC-L3ACL-045.tcl" "***** TC-L3ACL-045.tcl passed *****"
} else {
	Netgear_log_file "TC-L3ACL-045.tcl" "***** TC-L3ACL-045.tcl failed *****"
}
UALStopTrafficPerPort $ntgrTG $ntgrPort2

foreach {chassis_id portlist} [array get ntgr_RouterPortList_045] {
	foreach port $portlist {
		UALClearOwnership $chassis_id $port
	}
	UALDisconnectTester $chassis_id
}

Netgear_log_file "TC-L3ACL-045.tcl" "***** Completed Test case TC-L3ACL-045.tcl *****"

#*********************  End of Test case  ***************************************
