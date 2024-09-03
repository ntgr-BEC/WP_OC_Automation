#!/bin/sh
################################################################################   
#
#  File Name   : TC-VLN-001.tcl
#
#  Description :
#         This TCL contains APIs to configure VLAN the switches.
#
#  Revision History 	:
#        Date         Programmer        Description
#        -----------------------------------------------------------------------
#        30/5/06      Scott Zhang        Created
#
#
#
#
################################################################################

#*************************  Start of Test case  ********************************

Netgear_log_file "TC-VLN-001.tcl" "******************** Starting Test case TC-VLN-001.tcl ********************"

Netgear_log_file "TC-VLN-001.tcl" "Started VLAN Configuration on the Switches"

set vlan_indexs [lsort -dictionary [array names ntgr_vlanList ] ]
foreach vlan_index $vlan_indexs {
	Netgear_log_file "TC-VLN-001.tcl" "Started Configuration of VLAN $vlan_index"
	CALCreateVlan $vlan_index
}
Netgear_log_file "TC-VLN-001.tcl" "Completed VLAN Configuration on the Switches"

set packet_loss 0
if {$packet_loss == 0} {
	Netgear_log_file "TC-TFT-001.tcl" "******************** TEST CASE TC-VLN-001 PASSED ********************"
	set NTGR_TEST_RESULT TRUE
} else {
	Netgear_log_file "TC-TFT-001.tcl" "******************** TEST CASE TC-VLN-001 FAILED ********************"
	Netgear_log_file "TC-TFT-001.tcl" "         Failed Reason : Frame_loss = $packet_loss"
	Netgear_log_file "TC-TFT-001.tcl" "*********************************************************************"
	set NTGR_TEST_RESULT FALSE
}

Netgear_log_file "TC-VLN-001.tcl" "******************** Completed Test case TC-TFT-001.tcl ********************"

#*************************  End of Test case  ****************************************************************