#################################################################################
#
#  File Name	    	: TC-UserManagement-007.tcl
#
#  Description     	: Add maximum number of users per login list
#
#  Config file          : TC-UserManagement-007.cfg
#				  
#  Revision History 	:
#        Date          Programmer          Description
#        -----------------------------------------------------------------------
#        21/05/07      Nina Cheng          Created
#
#
##################################################################################

########################### start to configuration ###############################

Netgear_log_file "TC-UserManagement-007.tcl" "******** Starting Test case TC-UserManagement-007.tcl **********"

Netgear_log_file "TC-UserManagement-007.tcl" "Clear current configuration on switch $ntgrDUT and configure some initial info"
CALResetConfiguration $ntgrDUT
CALSetSwitchPrompt $ntgrDUT
CALSetSwitchMngrIPAddr $ntgrDUT

Netgear_log_file "TC-UserManagement-007.tcl" "add a new user on switch"
CALAddAuthLoginList $ntgrDUT $list_name $mode
for {set i 1} {$i <= 4} {incr i} {
	CALAddUser $ntgrDUT user$i psw$i
	CALAddUsertoList $ntgrDUT user$i $list_name
}

Netgear_log_file "TC-UserManagement-007.tcl" "telnet the switch by new user"
for {set i 1} {$i <= 4} {incr i} { 
	Netgear_connect_switch_1 $ntgrDUT user$i psw$i
	set result [CALCheckExpect $ntgrDUT $cmds user$i]
	if {$result == 1} {
		Netgear_log_file "TC-UserManagement-007.tcl" "login successfully by new user$i"
	} else {
		Netgear_log_file "TC-UserManagement-007.tcl" "fail to login by new user$i"
	}
	Netgear_disconnect_switch_1 $ntgrDUT
}

Netgear_log_file "TC-UserManagement-007.tcl" "***** Completed Test case TC-UserManagement-007.tcl *****"

#*********************  End of Test case  ***************************************
