#################################################################################
#
#  File Name	    	: TC-L3ACL-047.tcl
#
#  Description     	: Delete ACL not existing
#
#  Config file          : TC-L3ACL-047.cfg
#				  
#  Revision History 	:
#        Date          Programmer          Description
#        -----------------------------------------------------------------------
#        19/04/07      Nina Cheng          Created
#
#
##################################################################################

########################### start to configuration ###############################

Netgear_log_file "TC-L3ACL-047.tcl" "******** Starting Test case TC-L3ACL-047.tcl **********"

foreach switch_name $MTSW_L3ACL_047 {
    Netgear_log_file "TC-L3ACL-047.tcl" "Clear current configuration on switch $switch_name and configure some initial info"
    CALResetConfiguration $switch_name
    CALSetSwitchPrompt $switch_name
    CALSetSwitchMngrIPAddr $switch_name
}

Netgear_log_file "TC-L3ACL-047.tcl" "Started Configuration of RIP on IXIA"

foreach {chassis_id portlist} [array get ntgr_RouterPortList_047] {
	UALConnectTester $chassis_id
	foreach port $portlist {
		UALTakeOwnership $chassis_id $port
		UALLoadPort $chassis_id $port
	}
}

UALStartTrafficPerPort $ntgrTG $ntgrPort2
UALStartTrafficPerPort $ntgrTG $ntgrPort4
sleep 50

set dif1 0
set dif2 0
set TxRx4 [UALReportPortRate $ntgrTG $ntgrPort4]
set TxRx2 [UALReportPortRate $ntgrTG $ntgrPort2]

set dif1 [expr [lindex $TxRx4 1]/ [lindex $TxRx2 0]]
set dif2 [expr [lindex $TxRx2 1]/ [lindex $TxRx4 0]]
if {$dif1 >= 0.95 && $dif2 >= 0.95} {set result1 1} else {set result1 0}


Netgear_log_file "TC-L3ACL-047.tcl" "Deleting ACL on the Switches"
CALCheckExpect $MTSW_L3ACL_047 $ACL_no ""
sleep 5

set dif1 0
set dif2 0
set TxRx4 [UALReportPortRate $ntgrTG $ntgrPort4]
set TxRx2 [UALReportPortRate $ntgrTG $ntgrPort2]

set dif1 [expr [lindex $TxRx4 1]/ [lindex $TxRx2 0]]
set dif2 [expr [lindex $TxRx2 1]/ [lindex $TxRx4 0]]
if {$dif1 >= 0.95 && $dif2 >= 0.95} {set result2 1} else {set result2 0}

if {$result1 == 1 && $result2 == 1} {
	Netgear_log_file "TC-L3ACL-047.tcl" "***** TC-L3ACL-047.tcl passed *****"
} else {
	Netgear_log_file "TC-L3ACL-047.tcl" "***** TC-L3ACL-047.tcl failed *****"
}

UALStopTrafficPerPort $ntgrTG $ntgrPort2
UALStopTrafficPerPort $ntgrTG $ntgrPort4

foreach {chassis_id portlist} [array get ntgr_RouterPortList_047] {
	foreach port $portlist {
		UALClearOwnership $chassis_id $port
	}
	UALDisconnectTester $chassis_id
}

Netgear_log_file "TC-L3ACL-047.tcl" "***** Completed Test case TC-L3ACL-047.tcl *****"

#*********************  End of Test case  ***************************************
