#!/bin/sh
################################################################################   
#
#  File Name   : TC-CLEAR-ARP-038.tcl
#
#  Description :
#        This testcase clear the arp information regularly.
#
#  History     :
#        Date          Programmer        Description
#        -----------------------------------------------------------------------
#        Aug 28, 2006  Scott Zhang       Created
#
################################################################################

Netgear_log_file "TC-CLEAR-ARP-038.tcl" "****************** Starting Test case TC-CLEAR-ARP-038.tcl ******************"

foreach switch_name $NTGR_CLEAR_ARP_SW_LIST {
    Netgear_log_file "TC-CLEAR-ARP-038.tcl" "Clear ARP information on switch $switch_name"
    CALClearArpCache $switch_name
}

Netgear_log_file "TC-CLEAR-ARP-038.tcl" "******************** Completed Test case TC-CLEAR-ARP-038.tcl ********************"
#*************************  End of Test case  ****************************************************************