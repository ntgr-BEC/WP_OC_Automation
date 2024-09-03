#!/bin/sh
################################################################################   
#
#  File Name   : TC-IGMPSNP-008.tcl
#
#  Description :
#         This testcase is to configure Igmp snooping according to the
#         corresponding configuration file.
#         Because of dependence on vlan and port channel, it should be
#         run after corresponding testcases if there are igmp configuration
#         on vlan interface or port channel interface.
#
#  History     :
#        Date         Programmer        Description
#        -----------------------------------------------------------------------
#        07/06/2006   Scott Zhang        Created
#
#
################################################################################

#*************************  Start of Test case  ********************************

Netgear_log_file "TC-IGMPSNP-008.tcl" "******************** Starting Test case TC-IGMPSNP-008.tcl ********************"

Netgear_log_file "TC-IGMPSNP-008.tcl" "Started configure igmp snooping on the Switches"
for_array_keys switch_name ntgr_igmpSnoopList {
	Netgear_log_file "TC-IGMPSNP-008.tcl" "Enable igmp snooping on $switch_name globally"
	CALIgmpSnpEnable $switch_name
	Netgear_log_file "TC-IGMPSNP-008.tcl" "Enable igmp snooping on all interfaces"
	CALIgmpSnpInterfaceModeEnable $switch_name
	Netgear_log_file "TC-IGMPSNP-008.tcl" "Configure igmp snooping global properties"
	CALIgmpSnpConfigGlobalProperties $switch_name
	Netgear_log_file "TC-IGMPSNP-008.tcl" "Specially configure some interfaces' igmp properties."
	CALIgmpSnpConfigInterfaceProperties $switch_name
}
Netgear_log_file "TC-IGMPSNP-008.tcl" "Completed igmp snooping configuration on the Switches"

set packet_loss 0
if {$packet_loss == 0} {
	Netgear_log_file "TC-IGMPSNP-008.tcl" "******************** TEST CASE TC-IGMPSNP-008 PASSED ********************"
	set NTGR_TEST_RESULT TRUE
} else {
	Netgear_log_file "TC-IGMPSNP-008.tcl" "******************** TEST CASE TC-IGMPSNP-008 FAILED ********************"
	Netgear_log_file "TC-IGMPSNP-008.tcl" "         Failed Reason : Frame_loss = $packet_loss"
	Netgear_log_file "TC-IGMPSNP-008.tcl" "*********************************************************************"
	set NTGR_TEST_RESULT FALSE
}

Netgear_log_file "TC-IGMPSNP-008.tcl" "******************** Completed Test case TC-IGMPSNP-008.tcl ********************"

#*************************  End of Test case  ****************************************************************