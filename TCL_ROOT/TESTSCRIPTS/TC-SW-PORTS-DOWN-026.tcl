#!/bin/sh
################################################################################   
#
#  File Name     : TC-SW-PORTS-DOWN-026.tcl
#
#  Description   :
#         This testcase is to set DUT ports' status to down. ports up/down could 
#         be used to simulate the network instability.
#
#  Config File   : TC-SW-PORTS-DOWN-026.cfg
#
#  History       :
#        Date          Programmer        Description
#        -----------------------------------------------------------------------
#        Aug 21, 2006  Scott Zhang       Created
#
################################################################################

NtgrDumpLog $NTGR_LOG_TERSE "TC-SW-PORTS-DOWN-026.tcl" "******************** Starting Test case TC-SW-PORTS-DOWN-026.tcl ********************"

foreach {switch_name portlist} [array get ntgr_swUpDownPortList] {
    NtgrDumpLog $NTGR_LOG_TERSE  "TC-SW-PORTS-DOWN-026.tcl" "Setting ports' status to down on switch $switch_name"
    Netgear_connect_switch $switch_name
    foreach port $portlist {
        CALPortLinkDown $switch_name $port
    }
    Netgear_disconnect_switch $switch_name
}

NtgrDumpLog $NTGR_LOG_TERSE "TC-SW-PORTS-DOWN-026.tcl" "******************** Completed Test case TC-SW-PORTS-DOWN-026.tcl ********************"
#*************************  End of Test case  ****************************************************************