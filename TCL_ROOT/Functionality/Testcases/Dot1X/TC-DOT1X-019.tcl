#################################################################################
#
#  File Name	    	: TC-DOT1X-019.tcl
#
#  Description     	: Authentication with wrong username and password ¨C port control is port based
#
#  Config file          : TC-DOT1X-019.cfg
#				  
#  Revision History 	:
#        Date          Programmer          Description
#        -----------------------------------------------------------------------
#        14/05/07      Nina Cheng          Created
#
#
##################################################################################

########################### start to configuration ###############################

Netgear_log_file "TC-DOT1X-019.tcl" "******** Starting Test case TC-DOT1X-019.tcl **********"

Netgear_log_file "TC-DOT1X-019.tcl" "Clear current configuration on switch $ntgrDUT and configure some initial info"
CALResetConfiguration $ntgrDUT
CALSetSwitchPrompt $ntgrDUT
CALSetSwitchMngrIPAddr $ntgrDUT

Netgear_log_file "TC-DOT1X-019.tcl" "Configure DHCP on switch"
CALEnableDHCP $ntgrDUT
CALCreateDhcpAddrPool $ntgrDUT $ntgrPoolName

Netgear_log_file "TC-DOT1X-019.tcl" "Configure users on switch"
CALAddUser $ntgrDUT $username $password
CALAddAuthLoginList $ntgrDUT $userlist $auth_mode
CALSetDot1XLogin $ntgrDUT $username $userlist

Netgear_log_file "TC-DOT1X-019.tcl" "Configure DOT1X on switch"
CALEnableDot1X $ntgrDUT
set portlist [getdot1xInterfaceList $ntgrDUT]
CALSetDot1XIF $ntgrDUT $portlist

Netgear_log_file  "TC-DOT1X-019.tcl" "Configuration complete, please manually obtain address from PC.login with wrong username and pwd"

set bRet 1
set key [gets stdin]
if {$key == "y" || $key == "Y"} {
    set bRet [expr $bRet & 1]
} else {
    set bRet [expr $bRet & 0]
}

if {$bRet == 1} {
    set NtgrTestResult(TC-DOT1X-019.tcl) "OK"
} else {
    set NtgrTestResult(TC-DOT1X-019.tcl) "NG"
}

Netgear_log_file "TC-DOT1X-019.tcl" "***** Completed Test case TC-DOT1X-019.tcl *****"

#*********************  End of Test case  ***************************************
