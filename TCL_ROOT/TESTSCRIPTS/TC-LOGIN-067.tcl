#!/bin/sh
################################################################################   
#
#  File Name     : TC-LOGIN-067.tcl
#
#  Description   :
#         This testcase is trying to login switch with wrong ID
#
#  Config File   : TC-LOGIN-067.cfg
#
#  History       :
#        Date          Programmer        Description
#        -----------------------------------------------------------------------
#        15-9-2006     Nina Cheng        Created
#
################################################################################
Netgear_log_file "TC-LOGIN-067.tcl" "************ Starting Test case TC-LOGIN-067.tcl ****************"

Netgear_log_file "TC-LOGIN-067.tcl" "Started routing on the Switches"

foreach {switch_name portlist} [array get ntgr_LoginSWList] {
    
    foreach port_property $portlist {
         set username [lindex $port_property 0]
         set password [lindex $port_property 1]
         set num1 [llength $port_property]
         Netgear_connect_switch_1 $switch_name $username $password
         Netgear_disconnect_switch_1 $switch_name
    }
    
}
Netgear_log_file "TC-LOGIN-067.tcl" "Completed Routing Configuration on the Switch $switch_name"

Netgear_log_file "TC-LOGIN-067.tcl" "*************** Completed Test case TC-LOGIN-067.tcl *****************"

#*************************  End of Test case  ****************************************************************