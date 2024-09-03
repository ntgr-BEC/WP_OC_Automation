#################################################################################
#
#  File Name	    	: TC-L3ACL-037.tcl
#
#  Description     	: Modify action for ACL
#
#  Config file          : TC-L3ACL-037.cfg
#				  
#  Revision History 	:
#        Date          Programmer          Description
#        -----------------------------------------------------------------------
#        12/04/07      Nina Cheng          Created
#
#
##################################################################################

########################### start to configuration ###############################

Netgear_log_file "TC-L3ACL-037.tcl" "******** Starting Test case TC-L3ACL-037.tcl **********"

foreach switch_name $MTSW_L3ACL_037 {
    Netgear_log_file "TC-L3ACL-037.tcl" "Clear current configuration on switch $switch_name and configure some initial info"
    CALResetConfiguration $switch_name
    CALSetSwitchPrompt $switch_name
    CALSetSwitchMngrIPAddr $switch_name
}

Netgear_log_file "TC-L3ACL-037.tcl" "Started routing on the Switches"

foreach {switch_name portlist} [array get ntgr_L3SwitchList_037] {
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

Netgear_log_file "TC-L3ACL-037.tcl" "Started configuration of ACL on the Switches"

CALCheckExpect $MTSW_L3ACL_037 $ACL_037_old_first ""
sleep 1
CALCheckExpect $MTSW_L3ACL_037 $ACL_037_old_last ""
sleep 1
CALCheckExpect $MTSW_L3ACL_037 $ACL_in ""

Netgear_log_file "TC-L3ACL-037.tcl" "Started Configuration of RIP on IXIA"

foreach {chassis_id portlist} [array get ntgr_RouterPortList_037] {
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
set dif [expr [lindex $TxRx4 1]]
if {$dif == 0} {set result1 1} else {set result1 0}


CALCheckExpect $MTSW_L3ACL_037 $delete_ACL ""
sleep 1

CALCheckExpect $MTSW_L3ACL_037 $ACL_037_new_first ""
sleep 1
CALCheckExpect $MTSW_L3ACL_037 $ACL_037_new_last ""
sleep 1
CALCheckExpect $MTSW_L3ACL_037 $ACL_in ""
sleep 10

set dif 0
set TxRx4 [UALReportPortRate $ntgrTG $ntgrPort4]
set TxRx2 [UALReportPortRate $ntgrTG $ntgrPort2]

set dif [expr [lindex $TxRx4 1]/ [lindex $TxRx2 0]]

if {$dif >= 0.9} {set result2 1} else {set result2 0}

if {$result2 == 1 && $result1 == 1} {
	Netgear_log_file "TC-L3ACL-037.tcl" "***** TC-L3ACL-037.tcl passed *****"
} else {
	Netgear_log_file "TC-L3ACL-037.tcl" "***** TC-L3ACL-037.tcl failed *****"
}

UALStopTrafficPerPort $ntgrTG $ntgrPort2

foreach {chassis_id portlist} [array get ntgr_RouterPortList_037] {
	foreach port $portlist {
		UALClearOwnership $chassis_id $port
	}
	UALDisconnectTester $chassis_id
}

Netgear_log_file "TC-L3ACL-037.tcl" "***** Completed Test case TC-L3ACL-037.tcl *****"

#*********************  End of Test case  ***************************************
