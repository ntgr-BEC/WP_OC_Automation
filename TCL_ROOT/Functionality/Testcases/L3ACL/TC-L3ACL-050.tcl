#################################################################################
#
#  File Name	    	: TC-L3ACL-050.tcl
#
#  Description     	: Rule number of a ACL(23)
#
#  Config file          : TC-L3ACL-050.cfg
#				  
#  Revision History 	:
#        Date          Programmer          Description
#        -----------------------------------------------------------------------
#        19/04/07      Nina Cheng          Created
#
#
##################################################################################

########################### start to configuration ###############################

Netgear_log_file "TC-L3ACL-050.tcl" "******** Starting Test case TC-L3ACL-050.tcl **********"

foreach switch_name $MTSW_L3ACL_050 {
    Netgear_log_file "TC-L3ACL-050.tcl" "Clear current configuration on switch $switch_name and configure some initial info"
    CALResetConfiguration $switch_name
    CALSetSwitchPrompt $switch_name
    CALSetSwitchMngrIPAddr $switch_name
}

Netgear_log_file "TC-L3ACL-050.tcl" "Started routing on the Switches"

foreach {switch_name portlist} [array get ntgr_L3SwitchList_050] {
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

Netgear_log_file "TC-L3ACL-050.tcl" "Started Configuration of VLAN"

foreach vlan_index $VLAN_index_list_050 {
	Netgear_log_file "TC-L3ACL-050.tcl" "Started Configuration of VLAN $vlan_index"
	CALCreateVlan $vlan_index
}
Netgear_log_file "TC-L3ACL-050.tcl" "Started configuration of ACL on the Switches"

CALCheckExpect $MTSW_L3ACL_050 $ACL_050 ""
sleep 1
CALCheckExpect $MTSW_L3ACL_050 $ACL_in ""

Netgear_log_file "TC-L3ACL-050.tcl" "Started Configuration of IXIA"

foreach {chassis_id portlist} [array get ntgr_RouterPortList_050] {
	UALConnectTester $chassis_id
	foreach port $portlist {
		UALTakeOwnership $chassis_id $port
		UALLoadPort $chassis_id $port
	}
}

UALStartTrafficPerPort $ntgrTG $ntgrPort2
UALStartTrafficPerPort $ntgrTG $ntgrPort4
sleep 50

set dif 0
set TxRx4 [UALReportPortRate $ntgrTG $ntgrPort4]
set TxRx2 [UALReportPortRate $ntgrTG $ntgrPort2]

set dif [expr [lindex $TxRx4 1]/ [lindex $TxRx2 0]]
if {$dif >= 0.45 && $dif <= 0.65} {
	Netgear_log_file "TC-L3ACL-050.tcl" "***** TC-L3ACL-050.tcl passed *****"
} else {
	Netgear_log_file "TC-L3ACL-050.tcl" "***** TC-L3ACL-050.tcl failed *****"
}

UALStopTrafficPerPort $ntgrTG $ntgrPort2
UALStopTrafficPerPort $ntgrTG $ntgrPort4

UALStopTrafficPerPort $ntgrTG $ntgrPort2

foreach {chassis_id portlist} [array get ntgr_RouterPortList_050] {
	foreach port $portlist {
		UALClearOwnership $chassis_id $port
	}
	UALDisconnectTester $chassis_id
}

Netgear_log_file "TC-L3ACL-050.tcl" "***** Completed Test case TC-L3ACL-050.tcl *****"

#*********************  End of Test case  ***************************************
