################################################################################
#
#  File Name	    	: TC-POCH-005.tcl
#
#  Description     	:
#         This TCL tests configures port channel on switches.
#
#  Config file       : TC-POCH-005.cfg
#
#  Global Variables	: ntgr_poChanList (defined in CFG file)
#				  
#  Revision History 	:
#        Date          Programmer          Description
#        -----------------------------------------------------------------------
#        22-May-06     Scott Zhang         Created
#
#
#
#
################################################################################

#*************************  Start of Test case  ********************************

Netgear_log_file "TC-POCH-005.tcl" "******************** Starting Test case TC-POCH-005.tcl ********************"

Netgear_log_file "TC-POCH-005.tcl" "Started Port Channel Configuration on the Switches"
set channel_list [lsort -dictionary [array names ntgr_poChanList ] ]

foreach channel_name $channel_list {
	Netgear_log_file "TC-POCH-005.tcl" "Configure port channel($channel_name) on each switch(es)."
	CALCreatePortChannel $channel_name
}

#
set packet_loss 0
if {$packet_loss == 0} {
	Netgear_log_file "TC-TFT-001.tcl" "******************** TEST CASE TC-POCH-005 PASSED ********************"
	set NTGR_TEST_RESULT TRUE
} else {
	Netgear_log_file "TC-TFT-001.tcl" "******************** TEST CASE TC-POCH-005 FAILED ********************"
	Netgear_log_file "TC-TFT-001.tcl" "         Failed Reason : Frame_loss = $packet_loss"
	Netgear_log_file "TC-TFT-001.tcl" "*********************************************************************"
	set NTGR_TEST_RESULT FALSE
}

Netgear_log_file "TC-POCH-005.tcl" "******************** Completed Test case TC-POCH-005.tcl ********************"

#*************************  End of Test case  ****************************************************************