#################################################################################
#
#  File Name	    	: TC-DOT1X-016.tcl
#
#  Description     	: Enable and disable dot1x frequently
#
#  Config file          : TC-DOT1X-016.cfg
#				  
#  Revision History 	:
#        Date          Programmer          Description
#        -----------------------------------------------------------------------
#        14/05/07      Nina Cheng          Created
#
#
##################################################################################

########################### start to configuration ###############################

Netgear_log_file "TC-DOT1X-016.tcl" "******** Starting Test case TC-DOT1X-016.tcl **********"

Netgear_log_file "TC-DOT1X-016.tcl" "Clear current configuration on switch $ntgrDUT and configure some initial info"
CALResetConfiguration $ntgrDUT
CALSetSwitchPrompt $ntgrDUT
CALSetSwitchMngrIPAddr $ntgrDUT

Netgear_log_file "TC-DOT1X-016.tcl" "Configure DHCP on switch"
CALEnableDHCP $ntgrDUT
CALCreateDhcpAddrPool $ntgrDUT $ntgrPoolName

Netgear_log_file "TC-DOT1X-016.tcl" "Configure users on switch"
CALAddUser $ntgrDUT $username $password
CALAddAuthLoginList $ntgrDUT $userlist $auth_mode
CALSetDot1XLogin $ntgrDUT $username $userlist

Netgear_log_file "TC-DOT1X-016.tcl" "Configure DOT1X on switch"
CALEnableDot1X $ntgrDUT
set portlist [getdot1xInterfaceList $ntgrDUT]
CALSetDot1XIF $ntgrDUT $portlist

Netgear_log_file  "TC-DOT1X-016.tcl" "Configuration complete, please manually obtain address from PC. user name is nina"

set bRet 1
set key [gets stdin]
if {$key == "y" || $key == "Y"} {
    set bRet [expr $bRet & 1]
} else {
    set bRet [expr $bRet & 0]
}

Netgear_log_file "TC-DOT1X-016.tcl" "Enable and disable DOT1X on switch frequently"
Netgear_connect_switch $ntgrDUT 
for {set i 1} {$i <= 10} {incr i} {
CALDisableDot1X $ntgrDUT
sleep 2
CALEnableDot1X $ntgrDUT
sleep 2
}
Netgear_disconnect_switch $ntgrDUT

Netgear_log_file "TC-DOT1X-014.tcl" "Shutdown/no shutdown the port applied ACL on switch"
Netgear_connect_switch $ntgrDUT 
CALPortLinkDown $ntgrDUT $port_down
CALPortLinkUp $ntgrDUT $port_down
Netgear_disconnect_switch $ntgrDUT

Netgear_log_file  "TC-DOT1X-016.tcl" "Configuration complete, please manually obtain address from PC."
set bRet 1
set key [gets stdin]
if {$key == "y" || $key == "Y"} {
    set bRet [expr $bRet & 1]
} else {
    set bRet [expr $bRet & 0]
}

if {$bRet == 1} {
    set NtgrTestResult(TC-DOT1X-016.tcl) "OK"
} else {
    set NtgrTestResult(TC-DOT1X-016.tcl) "NG"
}

Netgear_log_file "TC-DOT1X-016.tcl" "***** Completed Test case TC-DOT1X-016.tcl *****"

#*********************  End of Test case  ***************************************
