################################################################################
#
#  File Name		: TC-TFT-001.tcl
#
#  Description       	:
#         This TCL tests the image upgrade process on all switch using the CLI.
#
#  Usage                : TC-TFT-001.tcl 
#
#  Global Variables	: ntgr_tftpList (defined in CFG file)
# 
#  Revision History 	:
#         Date         Programmer          Description
#        -----------------------------------------------------------------------
#        02-May-06     Rajeswari V         Created
#
#
#
#
################################################################################

#*************************  Start of Test case  ********************************

Netgear_log_file "TC-TFT-001.tcl" "******************** Starting Test case TC-TFT-001.tcl ********************"

set SWITCH_LIST {}

for_array_keys switch_id ntgr_tftpList {
	Netgear_log_file "TC-TFT-001.tcl" "Starting Upgrading on Switch $switch_id"
	set result [Netgear_switch_upgrade $switch_id]
	if {$result == FALSE}	{
		Netgear_log_file "TC-TFT-001.tcl" "Switch $switch_id is not Upgraded Successfully"
		lappend SWITCH_LIST "$switch_id,"
	} else {
		Netgear_log_file "TC-TFT-001.tcl" "Switch $switch_id is Upgraded Successfully"
	}
}
	
if {$SWITCH_LIST == {}}	{
	Netgear_log_file "TC-TFT-001.tcl" "******************** TEST CASE TC-TFT-001 PASSED ********************"
	set NTGR_TEST_RESULT TRUE
} else {
	set SWITCH_LIST [lreplace $SWITCH_LIST end end $switch_id]
	Netgear_log_file "TC-TFT-001.tcl" "******************** TEST CASE TC-TFT-001 FAILED ********************"
	Netgear_log_file "TC-TFT-001.tcl" "         Failed Reason : Switches $SWITCH_LIST was not upgraded"
	Netgear_log_file "TC-TFT-001.tcl" "*********************************************************************"
	set NTGR_TEST_RESULT FALSE
}

Netgear_log_file "TC-TFT-001.tcl" "******************** Completed Test case TC-TFT-001.tcl ********************"

#*************************  End of Test case  **********************************