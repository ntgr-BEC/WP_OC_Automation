#################################################################################
#
#  File Name	    	: TC-DOT1X-011.tcl
#
#  Description     	: Interaction test with L2 ACL
#
#  Config file          : TC-DOT1X-011.cfg
#				  
#  Revision History 	:
#        Date          Programmer          Description
#        -----------------------------------------------------------------------
#        14/05/07      Nina Cheng          Created
#
#
##################################################################################

########################### start to configuration ###############################

Netgear_log_file "TC-DOT1X-011.tcl" "******** Starting Test case TC-DOT1X-011.tcl **********"

Netgear_log_file "TC-DOT1X-011.tcl" "Clear current configuration on switch $ntgrDUT and configure some initial info"
CALResetConfiguration $ntgrDUT
CALSetSwitchPrompt $ntgrDUT
CALSetSwitchMngrIPAddr $ntgrDUT

Netgear_log_file "TC-DOT1X-011.tcl" "Configure DHCP on switch"
CALEnableDHCP $ntgrDUT
CALCreateDhcpAddrPool $ntgrDUT $ntgrPoolName

Netgear_log_file "TC-DOT1X-011.tcl" "Configure users on switch"
CALAddUser $ntgrDUT $username $password
CALAddAuthLoginList $ntgrDUT $userlist $auth_mode
CALSetDot1XLogin $ntgrDUT $username $userlist

Netgear_log_file "TC-DOT1X-011.tcl" "Configure DOT1X on switch"
CALEnableDot1X $ntgrDUT
set portlist [getdot1xInterfaceList $ntgrDUT]
CALSetDot1XIF $ntgrDUT $portlist

Netgear_log_file  "TC-DOT1X-011.tcl" "Configuration complete, please manually obtain address from PC. username is nina"

set bRet 1
set key [gets stdin]
if {$key == "y" || $key == "Y"} {
    set bRet [expr $bRet & 1]
} else {
    set bRet [expr $bRet & 0]
}

Netgear_log_file "TC-DOT1X-011.tcl" "Configure L2ACL on switch"
set mac_acl_name_list [getMACACLID $ntgrDUT]
set mac_acl_rule_list [getMACACLRule $ntgrDUT]
set mac_acl_interface_list [getMACACLInterface $ntgrDUT]
CALAddMacACL  $ntgrDUT $mac_acl_rule_list
CALInterfaceMACACLEnable $ntgrDUT $mac_acl_interface_list

Netgear_log_file "TC-DOT1X-011.tcl" "Shutdown/no shutdown the port applied ACL on switch"
Netgear_connect_switch $ntgrDUT 
CALPortLinkDown $ntgrDUT $port_down
CALPortLinkUp $ntgrDUT $port_down
Netgear_disconnect_switch $ntgrDUT

Netgear_log_file  "TC-DOT1X-011.tcl" "Configuration complete, please manually obtain address from PC. user name is nina"
set bRet 1
set key [gets stdin]
if {$key == "y" || $key == "Y"} {
    set bRet [expr $bRet & 1]
} else {
    set bRet [expr $bRet & 0]
}

if {$bRet == 1} {
    set NtgrTestResult(TC-DOT1X-011.tcl) "OK"
} else {
    set NtgrTestResult(TC-DOT1X-011.tcl) "NG"
}

Netgear_log_file "TC-DOT1X-011.tcl" "***** Completed Test case TC-DOT1X-011.tcl *****"

#*********************  End of Test case  ***************************************
