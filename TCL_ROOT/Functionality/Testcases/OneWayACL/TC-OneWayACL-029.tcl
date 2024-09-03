#################################################################################
#
#  File Name	    	: TC-OneWayACL-029.tcl
#
#  Description     	: TCP flag negative test - rst
#
#  Config file          : TC-OneWayACL-029.cfg
#				  
#  Revision History 	:
#        Date          Programmer          Description
#        -----------------------------------------------------------------------
#        09/04/07      Nina Cheng          Created
#
#
##################################################################################

########################### start to configuration ###############################

Netgear_log_file "TC-OneWayACL-029.tcl" "******** Starting Test case TC-OneWayACL-029.tcl **********"

foreach switch_name $MTSW_OneWayACL_029 {
    Netgear_log_file "TC-OneWayACL-029.tcl" "Clear current configuration on switch $switch_name and configure some initial info"
    CALResetConfiguration $switch_name
    CALSetSwitchPrompt $switch_name
}

sleep 5

Netgear_log_file "TC-OneWayACL-029.tcl" "Started configuration of one-way ACL on the Switches"

set result 1

foreach switch_name $MTSW_OneWayACL_029 {
	foreach commands $cmds {
  		set result1 [CALCheckExpect $switch_name $commands $expect_string_list]
  		set result [expr {$result * $result1}]
  		sleep 1
  	} 
}

if {$result == 1} {
	Netgear_log_file "TC-OneWayACL-029.tcl" "***** TC-OneWayACL-029.tcl passed *****"
} else {
	Netgear_log_file "TC-OneWayACL-029.tcl" "***** TC-OneWayACL-029.tcl failed *****"
}

Netgear_log_file "TC-OneWayACL-029.tcl" "***** Completed Test case TC-OneWayACL-029.tcl *****"

#*********************  End of Test case  ***************************************


