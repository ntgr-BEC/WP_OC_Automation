#################################################################################
#
#  File Name	    	: TC-UserManagement-008.tcl
#
#  Description     	: Add user and password with abnormal characters
#
#  Config file          : TC-UserManagement-008.cfg
#				  
#  Revision History 	:
#        Date          Programmer          Description
#        -----------------------------------------------------------------------
#        21/05/07      Nina Cheng          Created
#
#
##################################################################################

########################### start to configuration ###############################

Netgear_log_file "TC-UserManagement-008.tcl" "******** Starting Test case TC-UserManagement-008.tcl **********"

Netgear_log_file "TC-UserManagement-008.tcl" "Clear current configuration on switch $ntgrDUT and configure some initial info"
CALResetConfiguration $ntgrDUT
CALSetSwitchPrompt $ntgrDUT
CALSetSwitchMngrIPAddr $ntgrDUT

Netgear_log_file "TC-UserManagement-008.tcl" "add a new user on switch"
CALAddUser $ntgrDUT $user $psw

Netgear_log_file "TC-UserManagement-008.tcl" "telnet the switch by new user"
Netgear_connect_switch $ntgrDUT
set result [CALCheckExpect $ntgrDUT $cmds $expect_string_list 0]
Netgear_disconnect_switch $ntgrDUT
if {$result == 1} {
	set NtgrTestResult(TC-UserManagement-008.tcl) "OK"
} else {
	set NtgrTestResult(TC-UserManagement-008.tcl) "NG"
}
Netgear_log_file "TC-UserManagement-008.tcl" "***** Completed Test case TC-UserManagement-008.tcl *****"

#*********************  End of Test case  ***************************************
