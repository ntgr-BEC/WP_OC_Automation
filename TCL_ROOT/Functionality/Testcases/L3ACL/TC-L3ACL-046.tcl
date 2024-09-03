#################################################################################
#
#  File Name	    	: TC-L3ACL-046.tcl
#
#  Description     	: Match IP when disable routing
#
#  Config file          : TC-L3ACL-046.cfg
#				  
#  Revision History 	:
#        Date          Programmer          Description
#        -----------------------------------------------------------------------
#        19/04/07      Nina Cheng          Created
#
#
##################################################################################

########################### start to configuration ###############################

Netgear_log_file "TC-L3ACL-046.tcl" "******** Starting Test case TC-L3ACL-046.tcl **********"

foreach switch_name $MTSW_L3ACL_046 {
    Netgear_log_file "TC-L3ACL-046.tcl" "Clear current configuration on switch $switch_name and configure some initial info"
    CALResetConfiguration $switch_name
    CALSetSwitchPrompt $switch_name
    CALSetSwitchMngrIPAddr $switch_name
}

Netgear_log_file "TC-L3ACL-046.tcl" "Started Configuration of RIP on IXIA"

foreach {chassis_id portlist} [array get ntgr_RouterPortList_046] {
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

Netgear_log_file "TC-L3ACL-046.tcl" "Started configuration of ACL on the Switches"

CALCheckExpect $MTSW_L3ACL_046 $ACL_046 ""
sleep 1
CALCheckExpect $MTSW_L3ACL_046 $ACL_in ""

set dif1 1
set dif2 1
set TxRx4 [UALReportPortRate $ntgrTG $ntgrPort4]
set TxRx2 [UALReportPortRate $ntgrTG $ntgrPort2]

set dif1 [lindex $TxRx4 1]
set dif2 [expr [lindex $TxRx2 1]/ [lindex $TxRx4 0]]
if {$dif1 >= 0 && $dif2 >= 0.95} {set result2 1} else {set result2 0}

Netgear_log_file "TC-L3ACL-046.tcl" "Deleting ACL on the Switches"
CALCheckExpect $MTSW_L3ACL_046 $ACL_no ""
sleep 1

set dif1 0
set dif2 0
set TxRx4 [UALReportPortRate $ntgrTG $ntgrPort4]
set TxRx2 [UALReportPortRate $ntgrTG $ntgrPort2]

set dif1 [expr [lindex $TxRx4 1]/ [lindex $TxRx2 0]]
set dif2 [expr [lindex $TxRx2 1]/ [lindex $TxRx4 0]]
if {$dif1 >= 0.95 && $dif2 >= 0.95} {set result3 1} else {set result3 0}

if {$result1 == 1 && $result2 == 1 && $result3 == 1} {
	Netgear_log_file "TC-L3ACL-046.tcl" "***** TC-L3ACL-046.tcl passed *****"
} else {
	Netgear_log_file "TC-L3ACL-046.tcl" "***** TC-L3ACL-046.tcl failed *****"
}

UALStopTrafficPerPort $ntgrTG $ntgrPort2
UALStopTrafficPerPort $ntgrTG $ntgrPort4

foreach {chassis_id portlist} [array get ntgr_RouterPortList_046] {
	foreach port $portlist {
		UALClearOwnership $chassis_id $port
	}
	UALDisconnectTester $chassis_id
}

Netgear_log_file "TC-L3ACL-046.tcl" "***** Completed Test case TC-L3ACL-046.tcl *****"

#*********************  End of Test case  ***************************************
