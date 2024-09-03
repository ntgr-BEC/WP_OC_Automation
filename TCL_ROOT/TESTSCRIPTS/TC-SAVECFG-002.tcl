################################################################################
#
#  File Name		: TC-SAVECFG-002.tcl
#
#  Description       	:
#         This will save the configuration
#
#  Usage                : TC-SAVECFG-002.tcl
#
#  Global Variables	: ntgr_switchList
#				  
#  Revision History 	:
#         Date         Programmer          Description
#        -----------------------------------------------------------------------
#     
#
#
#
################################################################################

#*************************  Start of Test case  ********************************

Netgear_log_file "TC-SAVECF-002.tcl" "******************** Starting Test case TC-SAVECFG-002 ********************"
foreach switchID $ntgrSaveList {
	CALSaveConfig $switchID
}
Netgear_log_file "TC-SAVECF-002.tcl" "******************** Complete Test case TC-SAVECFG-002 ********************"
