#!/bin/sh
################################################################################   
#
#  File Name   : TC-SW-PORTS-UP-027.tcl
#
#  Description :
#         This testcase is to set DUT ports' status to up. ports up/down could 
#         be used to simulate the network instability.
#
#  Config File   : TC-SW-PORTS-DOWN-026.cfg
#
#  History     :
#        Date          Programmer        Description
#        -----------------------------------------------------------------------
#        Aug 21, 2006  Scott Zhang       Created
#
################################################################################

NtgrDumpLog $NTGR_LOG_TERSE "TC-SW-PORTS-UP-027.tcl" "******************** Starting Test case TC-SW-PORTS-UP-027.tcl ********************"

foreach {switch_name portlist} [array get ntgr_OSPFUpDownPortList] {
    NtgrDumpLog $NTGR_LOG_TERSE  "TC-SW-PORTS-UP-027.tcl" "Setting ports' status to up on switch $switch_name"
    Netgear_connect_switch $switch_name
    foreach port $portlist {
        CALPortLinkUp $switch_name $port
    }
    Netgear_disconnect_switch $switch_name
}

NtgrDumpLog $NTGR_LOG_TERSE "TC-SW-PORTS-UP-027.tcl" "******************** Completed Test case TC-SW-PORTS-UP-027.tcl ********************"
#*************************  End of Test case  ****************************************************************