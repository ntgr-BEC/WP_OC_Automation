#################################################################################
#
#  File Name	    	: TC-L3ACL-041.tcl
#
#  Description     	: Configure a lot of ACL, send traffic match the last rule, save and reboottraffic
#
#  Config file          : TC-L3ACL-041.cfg
#				  
#  Revision History 	:
#        Date          Programmer          Description
#        -----------------------------------------------------------------------
#        12/04/07      Nina Cheng          Created
#
#
##################################################################################

########################### start to configuration ###############################

Netgear_log_file "TC-L3ACL-041.tcl" "******** Starting Test case TC-L3ACL-041.tcl **********"

foreach switch_name $MTSW_L3ACL_041 {
    Netgear_log_file "TC-L3ACL-041.tcl" "Clear current configuration on switch $switch_name and configure some initial info"
    CALResetConfiguration $switch_name
    CALSetSwitchPrompt $switch_name
    CALSetSwitchMngrIPAddr $switch_name
}

Netgear_log_file "TC-L3ACL-041.tcl" "Started routing on the Switches"

foreach {switch_name portlist} [array get ntgr_L3SwitchList_041] {
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

Netgear_log_file "TC-L3ACL-041.tcl" "Started Configuration of VLAN"

foreach vlan_index $VLAN_index_list_041 {
	Netgear_log_file "TC-L3ACL-041.tcl" "Started Configuration of VLAN $vlan_index"
	CALCreateVlan $vlan_index
}

Netgear_log_file "TC-L3ACL-041.tcl" "Started configuration of ACL on the Switches"

CALCheckExpect $MTSW_L3ACL_041 $ACL_041 ""
sleep 1
CALCheckExpect $MTSW_L3ACL_041 $ACL_in ""

Netgear_log_file "TC-L3ACL-041.tcl" "Started Configuration of RIP on IXIA"

foreach {chassis_id portlist} [array get ntgr_RouterPortList_041] {
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

if {$dif >= 0.45 && $dif <= 0.55} {set result1 1} else {set result1 0}

CALSaveConfig $MTSW_L3ACL_041
CALRebootSwitch $MTSW_L3ACL_041

sleep 20
set dif_1 0
set TxRx4_1 [UALReportPortRate $ntgrTG $ntgrPort4]
set TxRx2_1 [UALReportPortRate $ntgrTG $ntgrPort2]

set dif_1 [expr [lindex $TxRx4_1 1]/ [lindex $TxRx2_1 0]]
if {$dif_1 >= 0.45 && $dif_1 <= 0.55} {set result2 1} else {set result2 0}

if {$result1 == 1 && $result2 == 1} {
	Netgear_log_file "TC-L3ACL-041.tcl" "***** TC-L3ACL-041.tcl passed *****"
} else {
	Netgear_log_file "TC-L3ACL-041.tcl" "***** TC-L3ACL-041.tcl failed *****"
}

UALStopTrafficPerPort $ntgrTG $ntgrPort2

foreach {chassis_id portlist} [array get ntgr_RouterPortList_041] {
	foreach port $portlist {
		UALClearOwnership $chassis_id $port
	}
	UALDisconnectTester $chassis_id
}

Netgear_log_file "TC-L3ACL-041.tcl" "***** Completed Test case TC-L3ACL-041.tcl *****"

#*********************  End of Test case  ***************************************
