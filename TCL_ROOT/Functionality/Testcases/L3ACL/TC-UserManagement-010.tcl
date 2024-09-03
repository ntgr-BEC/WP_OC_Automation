#################################################################################
#
#  File Name	    	: TC-UserManagement-010.tcl
#
#  Description     	: Save and reboot switch with maximum number of users
#
#  Config file          : TC-UserManagement-010.cfg
#				  
#  Revision History 	:
#        Date          Programmer          Description
#        -----------------------------------------------------------------------
#        21/05/07      Nina Cheng          Created
#
#
##################################################################################

########################### start to configuration ###############################

Netgear_log_file "TC-UserManagement-010.tcl" "******** Starting Test case TC-UserManagement-010.tcl **********"

Netgear_log_file "TC-UserManagement-010.tcl" "Clear current configuration on switch $ntgrDUT and configure some initial info"
CALResetConfiguration $ntgrDUT
CALSetSwitchPrompt $ntgrDUT
CALSetSwitchMngrIPAddr $ntgrDUT

Netgear_log_file "TC-UserManagement-010.tcl" "add a new user on switch"
for {set i 1} {$i <= 5} {incr i} {
	CALAddUser $ntgrDUT user$i psw$i
	for {set j 1} {$j <= 2} {incr j} {
		CALAddAuthLoginList $ntgrDUT list$i$j $mode
		CALAddUsertoList $ntgrDUT user$i list$i$j
	}
}

Netgear_log_file "TC-UserManagement-010.tcl" "copy and apply current configuration to a script on switch"
CALSaveConfig $ntgrDUT
CALRebootSwitch $ntgrDUT

Netgear_log_file "TC-UserManagement-010.tcl" "telnet the switch by new user"
for {set i 1} {$i <= 4} {incr i} { 
	Netgear_connect_switch_1 $ntgrDUT user$i psw$i
	set result [CALCheckExpect $ntgrDUT $cmds user$i]
	if {$result == 1} {
		Netgear_log_file "TC-UserManagement-010.tcl" "login successfully by new user$i"
	} else {
		Netgear_log_file "TC-UserManagement-010.tcl" "fail to login by new user$i"
	}
	Netgear_disconnect_switch_1 $ntgrDUT
}

Netgear_log_file "TC-UserManagement-010.tcl" "***** Completed Test case TC-UserManagement-010.tcl *****"

#*********************  End of Test case  ***************************************
