#################################################################################
#
#  File Name	    	: TC-UserManagement-003.tcl
#
#  Description     	: Delete a non-exiting user
#
#  Config file          : TC-UserManagement-003.cfg
#				  
#  Revision History 	:
#        Date          Programmer          Description
#        -----------------------------------------------------------------------
#        21/05/07      Nina Cheng          Created
#
#
##################################################################################

########################### start to configuration ###############################

Netgear_log_file "TC-UserManagement-003.tcl" "******** Starting Test case TC-UserManagement-003.tcl **********"

Netgear_log_file "TC-UserManagement-003.tcl" "Clear current configuration on switch $ntgrDUT and configure some initial info"
CALResetConfiguration $ntgrDUT
CALSetSwitchPrompt $ntgrDUT
CALSetSwitchMngrIPAddr $ntgrDUT

Netgear_log_file "TC-UserManagement-003.tcl" "add a new user on switch"
CALAddUser $ntgrDUT $user $psw

Netgear_log_file "TC-UserManagement-003.tcl" "telnet the switch by new user"
Netgear_connect_switch_1 $ntgrDUT $user $psw
set result1 [CALCheckExpect $ntgrDUT $cmds $expect_string_list]
Netgear_disconnect_switch_1 $ntgrDUT

Netgear_log_file "TC-UserManagement-003.tcl" "delete the new user"
CALDeleteUser $ntgrDUT "abc"

Netgear_connect_switch_1 $ntgrDUT $user $psw
set result2 [CALCheckExpect $ntgrDUT $cmds $expect_string_list]
Netgear_disconnect_switch_1 $ntgrDUT

if {$result1 == 1 && $result2 == 1} {
	set NtgrTestResult(TC-UserManagement-003.tcl) "OK"
} else {
	set NtgrTestResult(TC-UserManagement-003.tcl) "NG"
}
Netgear_log_file "TC-UserManagement-003.tcl" "***** Completed Test case TC-UserManagement-003.tcl *****"

#*********************  End of Test case  ***************************************
