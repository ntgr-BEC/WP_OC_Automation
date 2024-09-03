#################################################################################
#
#  File Name	    	: TC-SR-009.tcl
#
#  Description     	: Load balance test
#
#  Config file          : TC-SR-009.cfg
#				  
#  Revision History 	:
#        Date          Programmer          Description
#        -----------------------------------------------------------------------
#        14/03/07      Nina Cheng          Created
#
#
##################################################################################

########################### start to configuration ###############################

Netgear_log_file "TC-SR-009.tcl" "******** Starting Test case TC-SR-009.tcl **********"

foreach switch_name $NTGR_CLEAR_CONFIG_SW_LIST_009 {
    Netgear_log_file "TC-SR-009.tcl" "Clear current configuration on switch $switch_name and configure some initial info"
    CALResetConfiguration $switch_name
    CALSetSwitchPrompt $switch_name
    CALSetSwitchMngrIPAddr $switch_name
}

Netgear_log_file "TC-SR-009.tcl" "Started routing on the Switches"

foreach {switch_name portlist} [array get ntgr_L3SwitchList_009] {
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

Netgear_log_file "TC-SR-009.tcl" "Started configuration of static routes"

foreach switch_name $DUT_SR_009 {
    set address_list [getAddressList $switch_name]	
    CALAddStaticRoute $switch_name $address_list
}

Netgear_log_file "TC-RIP-009.tcl" "Started Configuration of RIP on IXIA"

foreach {chassis_id portlist} [array get ntgr_RouterPortList_009] {
	UALConnectTester $chassis_id
	foreach port $portlist {
		UALTakeOwnership $chassis_id $port
		UALLoadPort $chassis_id $port
	}
}

sleep 50

UALStartTrafficPerPort $ntgrTG $ntgrPort1

sleep 10

set dif 0
set TxRx4 [UALReportPortRate $ntgrTG $ntgrPort4]
set TxRx2 [UALReportPortRate $ntgrTG $ntgrPort2]
set TxRx1 [UALReportPortRate $ntgrTG $ntgrPort1]

set Rate_half [expr [lindex $TxRx1 0]/ 2]

if {[lindex $TxRx2 1] >= $Rate_half} {set result1 1} else {set result1 0}

if {[lindex $TxRx4 1] >= $Rate_half} {set result2 1} else {set result2 0}

if {$result1 == 1 && $result2 == 1} {
	Netgear_log_file "TC-SR-009.tcl" "***** TC-SR-009.tcl passed *****"
} else {
	Netgear_log_file "TC-SR-009.tcl" "***** TC-SR-009.tcl failed *****"
}

Netgear_log_file "TC-SR-009.tcl" "***** Completed Test case TC-SR-009.tcl *****"


UALStopTrafficPerPort $ntgrTG $ntgrPort1

foreach {chassis_id portlist} [array get ntgr_RouterPortList_009] {
	foreach port $portlist {
		UALStopRIPRouter $chassis_id $port
		UALClearOwnership $chassis_id $port
	}
	UALDisconnectTester $chassis_id
}
###############################Check the Configuration ################################# 	

#*********************  End of Test case  ***************************************
