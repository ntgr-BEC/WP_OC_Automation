#################################################################################
#
#  File Name	    	: TC-UserManagement-004.tcl
#
#  Description     	: Delete a non-exiting user
#
#  Config file          : TC-UserManagement-004.cfg
#				  
#  Revision History 	:
#        Date          Programmer          Description
#        -----------------------------------------------------------------------
#        21/05/07      Nina Cheng          Created
#
#
##################################################################################

########################### start to configuration ###############################

Netgear_log_file "TC-UserManagement-004.tcl" "******** Starting Test case TC-UserManagement-004.tcl **********"

Netgear_log_file "TC-UserManagement-004.tcl" "Clear current configuration on switch $ntgrDUT and configure some initial info"
CALResetConfiguration $ntgrDUT
CALSetSwitchPrompt $ntgrDUT
CALSetSwitchMngrIPAddr $ntgrDUT

Netgear_log_file "TC-UserManagement-004.tcl" "add a new user on switch"
CALAddUser $ntgrDUT $user $psw_old

Netgear_log_file "TC-UserManagement-004.tcl" "telnet the switch by new user"
Netgear_connect_switch_1 $ntgrDUT $user $psw_old
set result1 [CALCheckExpect $ntgrDUT $cmds $expect_string_list]
Netgear_disconnect_switch_1 $ntgrDUT

Netgear_log_file "TC-UserManagement-004.tcl" "delete the new user"
CALModifyPassword $ntgrDUT $user $psw_old $psw_new

Netgear_connect_switch_1 $ntgrDUT $user $psw_new
set result2 [CALCheckExpect $ntgrDUT $cmds $expect_string_list]
Netgear_disconnect_switch_1 $ntgrDUT

if {$result1 == 1 && $result2 == 1} {
	set NtgrTestResult(TC-UserManagement-004.tcl) "OK"
} else {
	set NtgrTestResult(TC-UserManagement-004.tcl) "NG"
}
Netgear_log_file "TC-UserManagement-004.tcl" "***** Completed Test case TC-UserManagement-004.tcl *****"

#*********************  End of Test case  ***************************************
