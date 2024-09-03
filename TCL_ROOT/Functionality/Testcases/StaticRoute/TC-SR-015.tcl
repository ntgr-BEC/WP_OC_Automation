#################################################################################
#
#  File Name	    	: TC-SR-015.tcl
#
#  Description     	: Save and reboot switch after adding many static routes
#
#  Config file          : TC-SR-015.cfg
#				  
#  Revision History 	:
#        Date          Programmer          Description
#        -----------------------------------------------------------------------
#        14/03/07      Nina Cheng          Created
#
#
##################################################################################

########################### start to configuration ###############################

Netgear_log_file "TC-SR-015.tcl" "******** Starting Test case TC-SR-015.tcl **********"

foreach switch_name $NTGR_CLEAR_CONFIG_SW_LIST_015 {
    Netgear_log_file "TC-SR-015.tcl" "Clear current configuration on switch $switch_name and configure some initial info"
    CALResetConfiguration $switch_name
    CALSetSwitchPrompt $switch_name
    CALSetSwitchMngrIPAddr $switch_name
}

Netgear_log_file "TC-SR-015.tcl" "Started routing on the Switches"

foreach {switch_name portlist} [array get ntgr_L3SwitchList_015] {
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

Netgear_log_file "TC-SR-015.tcl" "Started Configuration of VLAN"

foreach vlan_index $VLAN_SR_list_015 {
	Netgear_log_file "TC-SR-015.tcl" "Started Configuration of VLAN $vlan_index"
	CALCreateVlan $vlan_index
}

Netgear_log_file "TC-SR-015.tcl" "Started configuration of static routes"

foreach switch_name $DUT_SR_015 {
    set address_list [getAddressList $switch_name]	
    CALAddStaticRoute $switch_name $address_list 
}

Netgear_log_file "TC-RIP-015.tcl" "Started Configuration of IXIA"

foreach {chassis_id portlist} [array get ntgr_RouterPortList_015] {
	UALConnectTester $chassis_id
	foreach port $portlist {
		UALTakeOwnership $chassis_id $port
		UALLoadPort $chassis_id $port
	}
}

sleep 50

UALStartTrafficPerPort $ntgrTG $ntgrPort4

sleep 10

set dif 0
set TxRx4 [UALReportPortRate $ntgrTG $ntgrPort4]
set TxRx2 [UALReportPortRate $ntgrTG $ntgrPort2]

set dif [expr [lindex $TxRx4 0]/[lindex $TxRx2 1]]

CALSaveConfig $DUT_SR_015
CALRebootSwitch $DUT_SR_015

sleep 30

set dif_1 0
set TxRx4_1 [UALReportPortRate $ntgrTG $ntgrPort4]
set TxRx2_1 [UALReportPortRate $ntgrTG $ntgrPort2]

set dif_1 [expr [lindex $TxRx4_1 0]/[lindex $TxRx2_1 1]]

if {$dif >= 0.9 && $dif_1 >= 0.9} {
	Netgear_log_file "TC-SR-015.tcl" "***** TC-SR-015.tcl passed *****"
} else {
	Netgear_log_file "TC-SR-015.tcl" "***** TC-SR-015.tcl failed *****"
}

UALStopTrafficPerPort $ntgrTG $ntgrPort4

foreach {chassis_id portlist} [array get ntgr_RouterPortList_015] {
	foreach port $portlist {
		UALStopRIPRouter $chassis_id $port
		UALClearOwnership $chassis_id $port
	}
	UALDisconnectTester $chassis_id
}

Netgear_log_file "TC-SR-015.tcl" "***** Completed Test case TC-SR-015.tcl *****"

#*********************  End of Test case  ***************************************
