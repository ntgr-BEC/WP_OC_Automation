#################################################################################
#
#  File Name	    	: TC-SR-020.tcl
#
#  Description     	: Next hop is broadcast address
#
#  Config file          : TC-SR-020.cfg
#				  
#  Revision History 	:
#        Date          Programmer          Description
#        -----------------------------------------------------------------------
#        29/03/07      Nina Cheng          Created
#
#
##################################################################################

########################### start to configuration ###############################

Netgear_log_file "TC-SR-020.tcl" "******** Starting Test case TC-SR-020.tcl **********"

foreach switch_name $NTGR_CLEAR_CONFIG_SW_LIST_020 {
    Netgear_log_file "TC-SR-020.tcl" "Clear current configuration on switch $switch_name and configure some initial info"
    CALResetConfiguration $switch_name
    CALSetSwitchPrompt $switch_name
    CALSetSwitchMngrIPAddr $switch_name
}

Netgear_log_file "TC-SR-020.tcl" "Started routing on the Switches"

foreach {switch_name portlist} [array get ntgr_L3SwitchList_020] {
    Netgear_connect_switch $switch_name
    CALRoutingEnable $switch_name
    foreach port_property $portlist {
        set port [lindex $port_property 0]
        CALRoutingPortEnable $switch_name $port
        set num1 [llength $port_property]
        set num [expr $num1 - 1]
        for {set i 1} {$i <= $num} {incr i} {
            set ip_addr [lindex $port_property $i]
            CALAddIPtoPort $switch_name $port $ip_addr
        }
    }
    Netgear_disconnect_switch $switch_name
}

Netgear_log_file "TC-SR-020.tcl" "Started configuration of static routes"

foreach switch_name $DUT_SR_020 {
    set address_list [getAddressList $switch_name]	
    CALAddStaticRoute $switch_name $address_list 
}

###############################Check the Configuration ################################# 
for {set i 1} {$i <= 3} {incr i} {
	sleep 2
	set result [CALCheckExpect $switch_checked $cmds $expect_string_list 0] 
}	
if {$result == 1} {
	Netgear_log_file "TC-SR-020.tcl" "***** TC-SR-020.tcl passed *****"
} else {
	Netgear_log_file "TC-SR-020.tcl" "***** TC-SR-020.tcl failed *****"
}

Netgear_log_file "TC-SR-020.tcl" "***** Completed Test case TC-SR-020.tcl *****"

#*********************  End of Test case  ***************************************
