#!/bin/sh
################################################################################   
#
#  File Name     : TC-ROUTING-060.tcl
#
#  Description   :
#         This testcase is to set routing for switch
#
#  Config File   : TC-ROUTING-060.cfg
#
#  History       :
#        Date          Programmer        Description
#        -----------------------------------------------------------------------
#        15-9-2006     Nina Cheng        Created
#
################################################################################
Netgear_log_file "TC-ROUTING-060.tcl" "************ Starting Test case TC-ROUTING-060.tcl ****************"

Netgear_log_file "TC-ROUTING-060.tcl" "Started routing on the Switches"

foreach {switch_name portlist} [array get ntgr_L3SwitchList] {
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
Netgear_log_file "TC-ROUTING-060.tcl" "Completed Routing Configuration on the Switch $switch_name"

Netgear_log_file "TC-ROUTING-060.tcl" "*************** Completed Test case TC-ROUTING-060.tcl *****************"

#*************************  End of Test case  ****************************************************************