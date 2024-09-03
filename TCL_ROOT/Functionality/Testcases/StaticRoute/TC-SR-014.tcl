#################################################################################
#
#  File Name	    	: TC-SR-014.tcl
#
#  Description     	: Shutdown\no shutdown the port that is the next hop of the static route
#
#  Config file          : TC-SR-014.cfg
#				  
#  Revision History 	:
#        Date          Programmer          Description
#        -----------------------------------------------------------------------
#        28/03/07      Nina Cheng          Created
#
#
##################################################################################

########################### start to configuration ###############################

Netgear_log_file "TC-SR-014.tcl" "******** Starting Test case TC-SR-014.tcl **********"

foreach switch_name $NTGR_CLEAR_CONFIG_SW_LIST_014 {
    Netgear_log_file "TC-SR-014.tcl" "Clear current configuration on switch $switch_name and configure some initial info"
    CALResetConfiguration $switch_name
    CALSetSwitchPrompt $switch_name
    CALSetSwitchMngrIPAddr $switch_name
}

Netgear_log_file "TC-SR-014.tcl" "Started routing on the Switches"

foreach {switch_name portlist} [array get ntgr_L3SwitchList_014] {
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

Netgear_log_file "TC-SR-014.tcl" "Started configuration of static routes"

foreach switch_name $DUT_SR_014 {
    set address_list [getAddressList $switch_name]	
    CALAddStaticRoute $switch_name $address_list
}

Netgear_log_file "TC-RIP-014.tcl" "Started Configuration of RIP on IXIA"

foreach {chassis_id portlist} [array get ntgr_RouterPortList_014] {
	UALConnectTester $chassis_id
	foreach port $portlist {
		UALTakeOwnership $chassis_id $port
		UALLoadPort $chassis_id $port
	}
}

sleep 50

UALStartTrafficPerPort $ntgrTG $ntgrPort1

Netgear_connect_switch $SW_test
for {set i 1} {$i <= 20} {incr i} {
	CALPortLinkDown $SW_test $port_down
	sleep 5
	CALPortLinkUp $SW_test $port_down
	sleep 5
}
Netgear_disconnect_switch $SW_test

sleep 50

set dif 0
set TxRx2 [UALReportPortRate $ntgrTG $ntgrPort2]
set TxRx1 [UALReportPortRate $ntgrTG $ntgrPort1]
set sum_div [lindex $TxRx2 1]
if {$sum_div == 0} {set sum_div 1}
set dif [expr [lindex $TxRx1 0]/ $sum_div]

if {$dif >=0.9} {
	Netgear_log_file "TC-SR-014.tcl" "***** TC-SR-014.tcl passed *****"
} else {
	Netgear_log_file "TC-SR-014.tcl" "***** TC-SR-014.tcl failed *****"
}

Netgear_log_file "TC-SR-014.tcl" "***** Completed Test case TC-SR-014.tcl *****"


UALStopTrafficPerPort $ntgrTG $ntgrPort1

foreach {chassis_id portlist} [array get ntgr_RouterPortList_014] {
	foreach port $portlist {
		UALStopRIPRouter $chassis_id $port
		UALClearOwnership $chassis_id $port
	}
	UALDisconnectTester $chassis_id
}
###############################Check the Configuration ################################# 	

#*********************  End of Test case  ***************************************
