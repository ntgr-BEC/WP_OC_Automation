#################################################################################
#
#  File Name	    	: TC-UserManagement-005.tcl
#
#  Description     	: Login list test -- forbid an user login
#
#  Config file          : TC-UserManagement-005.cfg
#				  
#  Revision History 	:
#        Date          Programmer          Description
#        -----------------------------------------------------------------------
#        21/05/07      Nina Cheng          Created
#
#
##################################################################################

########################### start to configuration ###############################

Netgear_log_file "TC-UserManagement-005.tcl" "******** Starting Test case TC-UserManagement-005.tcl **********"

Netgear_log_file "TC-UserManagement-005.tcl" "Clear current configuration on switch $ntgrDUT and configure some initial info"
CALResetConfiguration $ntgrDUT
CALSetSwitchPrompt $ntgrDUT
CALSetSwitchMngrIPAddr $ntgrDUT

Netgear_log_file "TC-UserManagement-005.tcl" "add a new user on switch"
CALAddUser $ntgrDUT $user $psw
CALAddAuthLoginList $ntgrDUT $name_list $mode
CALAddUsertoList $ntgrDUT $user $name_list

Netgear_log_file "TC-UserManagement-005.tcl" "telnet the switch by new user"
Netgear_connect_switch_1 $ntgrDUT $user $psw
set result [CALCheckExpect $ntgrDUT $cmds $expect_string_list 0]
Netgear_disconnect_switch_1 $ntgrDUT

if {$result == 1} {
	set NtgrTestResult(TC-UserManagement-005.tcl) "OK"
} else {
	set NtgrTestResult(TC-UserManagement-005.tcl) "NG"
}
Netgear_log_file "TC-UserManagement-005.tcl" "***** Completed Test case TC-UserManagement-005.tcl *****"

#*********************  End of Test case  ***************************************
