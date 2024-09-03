#################################################################################
#
#  File Name	    	: TC-SR-013.tcl
#
#  Description     	: Add and delete VLAN
#
#  Config file          : TC-SR-013.cfg
#				  
#  Revision History 	:
#        Date          Programmer          Description
#        -----------------------------------------------------------------------
#        28/03/07      Nina Cheng          Created
#
#
##################################################################################

########################### start to configuration ###############################

Netgear_log_file "TC-SR-013.tcl" "******** Starting Test case TC-SR-013.tcl **********"

foreach switch_name $NTGR_CLEAR_CONFIG_SW_LIST_013 {
    Netgear_log_file "TC-SR-013.tcl" "Clear current configuration on switch $switch_name and configure some initial info"
    CALResetConfiguration $switch_name
    CALSetSwitchPrompt $switch_name
    CALSetSwitchMngrIPAddr $switch_name
}

Netgear_log_file "TC-SR-013.tcl" "Started routing on the Switches"

foreach {switch_name portlist} [array get ntgr_L3SwitchList_013] {
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

Netgear_log_file "TC-SR-013.tcl" "Started Configuration of VLAN"

foreach vlan_index $VLAN_SR_list_013 {
	Netgear_log_file "TC-SR-013.tcl" "Started Configuration of VLAN $vlan_index"
	CALCreateVlan $vlan_index
}

Netgear_log_file "TC-SR-013.tcl" "Started configuration of static routes"

foreach switch_name $DUT_SR_013 {
    set address_list [getAddressList $switch_name]	
    CALAddStaticRoute $switch_name $address_list
}

Netgear_log_file "TC-RIP-013.tcl" "Started Configuration of RIP on IXIA"

foreach {chassis_id portlist} [array get ntgr_RouterPortList_013] {
	UALConnectTester $chassis_id
	foreach port $portlist {
		UALTakeOwnership $chassis_id $port
		UALLoadPort $chassis_id $port
	}
}

sleep 50

UALStartTrafficPerPort $ntgrTG $ntgrPort1

for {set i 1} {$i <= 2} {incr i} {
	CALDeleteVlan $vlan_test
	sleep 1
	CALCreateVlan $vlan_test
	sleep 1
}
sleep 10

set dif 0
set TxRx4 [UALReportPortRate $ntgrTG $ntgrPort4]
set TxRx2 [UALReportPortRate $ntgrTG $ntgrPort2]
set TxRx1 [UALReportPortRate $ntgrTG $ntgrPort1]

set Rate_half [expr [lindex $TxRx1 0]/ 2]

if {[lindex $TxRx2 1] >= $Rate_half} {set result1 1} else {set result1 0}

if {[lindex $TxRx4 1] >= $Rate_half} {set result2 1} else {set result2 0}

if {$result1 == 1 && $result2 == 1} {
	Netgear_log_file "TC-SR-013.tcl" "***** TC-SR-013.tcl passed *****"
} else {
	Netgear_log_file "TC-SR-013.tcl" "***** TC-SR-013.tcl failed *****"
}

Netgear_log_file "TC-SR-013.tcl" "***** Completed Test case TC-SR-013.tcl *****"


UALStopTrafficPerPort $ntgrTG $ntgrPort1

foreach {chassis_id portlist} [array get ntgr_RouterPortList_013] {
	foreach port $portlist {
		UALStopRIPRouter $chassis_id $port
		UALClearOwnership $chassis_id $port
	}
	UALDisconnectTester $chassis_id
}
###############################Check the Configuration ################################# 	

#*********************  End of Test case  ***************************************
