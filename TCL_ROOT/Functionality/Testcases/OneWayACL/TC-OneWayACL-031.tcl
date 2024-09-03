#################################################################################
#
#  File Name	    	: TC-OneWayACL-031.tcl
#
#  Description     	: TCP flag negative test - psh
#
#  Config file          : TC-OneWayACL-031.cfg
#				  
#  Revision History 	:
#        Date          Programmer          Description
#        -----------------------------------------------------------------------
#        09/04/07      Nina Cheng          Created
#
#
##################################################################################

########################### start to configuration ###############################

Netgear_log_file "TC-OneWayACL-031.tcl" "******** Starting Test case TC-OneWayACL-031.tcl **********"

foreach switch_name $MTSW_OneWayACL_031 {
    Netgear_log_file "TC-OneWayACL-031.tcl" "Clear current configuration on switch $switch_name and configure some initial info"
    CALResetConfiguration $switch_name
    CALSetSwitchPrompt $switch_name
}

sleep 5

Netgear_log_file "TC-OneWayACL-031.tcl" "Started configuration of one-way ACL on the Switches"

set result 1

foreach switch_name $MTSW_OneWayACL_031 {
	foreach commands $cmds {
  		set result1 [CALCheckExpect $switch_name $commands $expect_string_list]
  		set result [expr {$result * $result1}]
  		sleep 1
  	} 
}

if {$result == 1} {
	Netgear_log_file "TC-OneWayACL-031.tcl" "***** TC-OneWayACL-031.tcl passed *****"
} else {
	Netgear_log_file "TC-OneWayACL-031.tcl" "***** TC-OneWayACL-031.tcl failed *****"
}

Netgear_log_file "TC-OneWayACL-031.tcl" "***** Completed Test case TC-OneWayACL-031.tcl *****"

#*********************  End of Test case  ***************************************




