#################################################################################
#
#  File Name	    	: TC-UserManagement-001.tcl
#
#  Description     	: Add a new user and password
#
#  Config file          : TC-UserManagement-001.cfg
#				  
#  Revision History 	:
#        Date          Programmer          Description
#        -----------------------------------------------------------------------
#        21/05/07      Nina Cheng          Created
#
#
##################################################################################

########################### start to configuration ###############################

Netgear_log_file "TC-UserManagement-001.tcl" "******** Starting Test case TC-UserManagement-001.tcl **********"

Netgear_log_file "TC-UserManagement-001.tcl" "Clear current configuration on switch $ntgrDUT and configure some initial info"
CALResetConfiguration $ntgrDUT
CALSetSwitchPrompt $ntgrDUT
CALSetSwitchMngrIPAddr $ntgrDUT

Netgear_log_file "TC-UserManagement-001.tcl" "add a new user on switch"
CALAddUser $ntgrDUT $user $psw

Netgear_log_file "TC-UserManagement-001.tcl" "telnet the switch by new user"
Netgear_connect_switch_1 $ntgrDUT $user $psw
set result [CALCheckExpect $ntgrDUT $cmds $expect_string_list]
Netgear_disconnect_switch_1 $ntgrDUT
if {$result == 1} {
	set NtgrTestResult(TC-UserManagement-001.tcl) "OK"
} else {
	set NtgrTestResult(TC-UserManagement-001.tcl) "NG"
}
Netgear_log_file "TC-UserManagement-001.tcl" "***** Completed Test case TC-UserManagement-001.tcl *****"

#*********************  End of Test case  ***************************************
